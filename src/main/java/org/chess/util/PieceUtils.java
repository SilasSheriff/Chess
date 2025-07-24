package org.chess.util;


import org.chess.model.Pieces;
import org.chess.model.King;
import org.chess.model.Rook;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class PieceUtils {

    public static ArrayList<Integer> checkObstacle(HashMap<String, Pieces> piecesMap, Pieces piece, Point direction) {
        piece.setMayBeat(false);
        ArrayList<Integer> allowedDistances = new ArrayList<>();
        char colour = piece.getColour();
        int x = piece.getPosition().x;
        int y = piece.getPosition().y;
        // Definiere die Maximalreichweite
        int maxRange = (piece instanceof King) ? 1: 7 ;
        // Erstellung des Koordinaten Strings des Ursprungs
        String fromCoord = x + "," + y;


        for (int i = 1; i < maxRange + 1; i++) {

            // Definiere die Zielkoordinaten
            int targetX = x + i*direction.x;
            int targetY = y + i*direction.y;
            // Überprüfe auf Spielfeldrand
            if (targetX < 0 || targetY < 0 || targetX > 7 || targetY > 7) {
                break;
            }
            // Erstellung des Koordinaten Strings des Ziels
            String toCoord = targetX + "," + targetY;
            // Erstelle der Figur auf dem Zielfeld, wenn frei dann null
            Pieces targetPiece = piecesMap.get(toCoord);

            // Überprüfe auf eigene Figur
            if (targetPiece != null && targetPiece.getColour() == colour){
                break;
            }
            // Überprüfe auf Schach
            HashMap<String,Pieces> tempPiecesMap = BoardUtils.copyPiecesMap(piecesMap);
            Pieces copiedPiece = tempPiecesMap.get(fromCoord);
            PieceUtils.updatePosition(copiedPiece,tempPiecesMap,new Point(direction.x*i,direction.y *i));
            BoardUtils.handleCheck(tempPiecesMap,colour);
            King tempKing = KingUtils.getKingPosition(tempPiecesMap, piece.getColour());
            if(tempKing == null) return null;

            // Überprüfe, ob nach dem simulierten Zug der eigene König im Schach steht
            if(tempKing.isInCheck()) continue;

            // Steht eine gegnerische Figur auf dem Zielfeld
            if (targetPiece != null && targetPiece.getColour() != colour && !(targetPiece instanceof King)) {
                piece.setMayBeat(true);
                allowedDistances.add(i);
                break;
            }
            allowedDistances.add(i);
        }
        return allowedDistances;
    }

    public static void updatePosition(Pieces piece, HashMap<String, Pieces> piecesMap, Point moveVector) {
        Point position = piece.getPosition();
        String oldPos = position.x + "," + position.y;
        piecesMap.remove(oldPos);

        if (!(moveVector.x == 0 && moveVector.y == 0)) {
            if (piece instanceof King king) {
                king.setWasMoved(true);
            }
            if (piece instanceof Rook rook) {
                rook.setWasMoved(true);
            }

            position.translate(moveVector.x, moveVector.y);
            String newPos = position.x + "," + position.y;

            if (piece.isMayBeat() && piecesMap.containsKey(newPos)) {
                Pieces beaten = piecesMap.get(newPos);
                System.out.println("Figur geschlagen: " + beaten.getShortcut());
                piecesMap.remove(newPos);
            }
            piecesMap.put(newPos, piece);
        }
    }

    public static boolean checkChess(HashMap<String,Pieces> piecesMap, Pieces piece, Point moveVector){
        char colour = piece.getColour();
        String fromCoord = piece.getPosition().x + "," + piece.getPosition().y;

        // Hashmap kopieren
        HashMap<String,Pieces> tempPiecesMap = BoardUtils.copyPiecesMap(piecesMap);
        // Figur kopieren
        Pieces copiedPiece = tempPiecesMap.get(fromCoord);
        if (copiedPiece == null) return true;
        // Figur bewegen
        PieceUtils.updatePosition(copiedPiece,tempPiecesMap,moveVector);
        // Schach überprüfen
        BoardUtils.handleCheck(tempPiecesMap,colour);
        King tempKing = KingUtils.getKingPosition(tempPiecesMap, colour);
        if(tempKing == null) return true;

        return tempKing.isInCheck();
    }

    public static String arraylistUnwrapping(ArrayList<Integer> distances) {
        StringBuilder distanceOutput = new StringBuilder();
        for (int i : distances) {
            distanceOutput.append(i).append(", ");
        }

        if (!distances.isEmpty()) {
            distanceOutput.setLength(distanceOutput.length() - 2);
        }
        return distanceOutput.toString();
    }

    public static String getUnicodeSymbol(Pieces piece) {
        boolean isWhite = piece.getColour() == 'w';

        return switch (piece.getShortcut()) {
            case "K" -> isWhite ? "♔" : "♚";
            case "D" -> isWhite ? "♕" : "♛";
            case "T" -> isWhite ? "♖" : "♜";
            case "L" -> isWhite ? "♗" : "♝";
            case "S" -> isWhite ? "♘" : "♞";
            case "B" -> isWhite ? "♙" : "♟";
            default  -> "?";
        };
    }

    public static Point mapInputToVector(String input, HashMap<String,Point> directionMap) {
        return directionMap.get(input);
    }

    public static int handleCastling(HashMap<String, Pieces> piecesMap, String activePlayer, String chosenCoordinates) {
        int gameStatus;
        switch (chosenCoordinates) {
            case "klrochade" -> {
                gameStatus = PieceUtils.handleSmallCastling(piecesMap, activePlayer);
                return gameStatus;
            }
            case "grrochade" -> {
                gameStatus = PieceUtils.handleBigCastling(piecesMap, activePlayer);

                return gameStatus;
            }
        }
        return -1;
    }

    public static int checkCastling(Rook rook, King king) {
        if (king.isWasMoved() || rook.isWasMoved()) {
            System.out.println("König oder Turm schon bewegt");
            return -1;
        }
        return 0;
    }

    private static int handleSmallCastling(HashMap<String, Pieces> piecesMap, String activePlayer) {
        King king;
        Rook rook;
        int gameStatus;
        switch (activePlayer) {
            case "Weiss" -> {
                if (piecesMap.get("7,7") instanceof Rook && piecesMap.get("4,7") instanceof King) {
                    rook = (Rook) piecesMap.get("7,7");
                    king = (King) piecesMap.get("4,7");
                } else return -1;

                if (piecesMap.containsKey("5,7") || piecesMap.containsKey("6,7")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                gameStatus = PieceUtils.checkCastling(rook,king);
                if (gameStatus != 0) return gameStatus;
                PieceUtils.updatePosition(rook,piecesMap,new Point(-2,0));
                PieceUtils.updatePosition(king,piecesMap,new Point(2,0));
                return 0;
            }
            case "Schwarz" -> {
                if (piecesMap.get("7,0") instanceof Rook && piecesMap.get("4,0") instanceof King) {
                    rook = (Rook) piecesMap.get("7,0");
                    king = (King) piecesMap.get("4,0");
                } else return -1;

                gameStatus = PieceUtils.checkCastling(rook, king);
                if (gameStatus != 0) return gameStatus;

                if (piecesMap.containsKey("5,0") || piecesMap.containsKey("6,0")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                PieceUtils.updatePosition(rook,piecesMap,new Point(-2,0));
                PieceUtils.updatePosition(king,piecesMap,new Point(2,0));
                return 0;
            }
        }
        return -1;
    }

    private static int handleBigCastling(HashMap<String, Pieces> piecesMap, String activePlayer) {
        King king;
        Rook rook;
        int gameStatus;
        switch (activePlayer) {
            case "Weiss" -> {
                if (piecesMap.get("0,7") instanceof Rook && piecesMap.get("4,7") instanceof King) {
                    rook = (Rook) piecesMap.get("0,7");
                    king = (King) piecesMap.get("4,7");
                } else return -1;
                gameStatus = PieceUtils.checkCastling(rook, king);
                if (gameStatus != 0) return gameStatus;
                if (piecesMap.containsKey("1,7") || piecesMap.containsKey("2,7") || piecesMap.containsKey("3,7")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                PieceUtils.updatePosition(rook,piecesMap,new Point(3,0));
                PieceUtils.updatePosition(king,piecesMap,new Point(-2,0));
                return 0;
            }
            case "Schwarz" -> {
                if (piecesMap.get("0,0") instanceof Rook && piecesMap.get("4,0") instanceof King) {
                    rook = (Rook) piecesMap.get("0,0");
                    king = (King) piecesMap.get("4,0");
                } else return -1;

                gameStatus = PieceUtils.checkCastling(rook,king);

                if (gameStatus != 0) return gameStatus;
                if (piecesMap.containsKey("1,0") || piecesMap.containsKey("2,0") || piecesMap.containsKey("3,0")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                PieceUtils.updatePosition(rook,piecesMap,new Point(3,0));
                PieceUtils.updatePosition(king,piecesMap,new Point(-2,0));
                return 0;
            }
        }
        return -1;
    }
}