package org.chess.util;


import org.chess.model.Figures;
import org.chess.model.King;
import org.chess.model.Rook;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiguresUtils {

    public static int checkObstacle(HashMap<String, Figures> figuresMap, Figures figure, Point direction) {
        int x = figure.getPosition().x;
        int y = figure.getPosition().y;
        int maxRange = 7;

        if (figure instanceof King ) maxRange = 1;

        for (int i = 1; i < maxRange + 1; i++) {
            x += direction.x;
            y += direction.y;
            String coordinate = x + "," + y;
            if (x < 0 || y < 0 || x > 7 || y > 7) {
                return i - 1;
            }
            if (figuresMap.containsKey(coordinate)) {
                Figures f = figuresMap.get(coordinate);
                if (!(figure.getColour() == (f.getColour()))) {
                    figure.setMayBeat(true);
                    return i;
                }
                return i - 1;
            }
        }
        return maxRange;
    }

    public static void updatePosition(Figures figure, HashMap<String, Figures> figuresMap, Point moveVector) {
        Point position = figure.getPosition();
        String oldPos = position.x + "," + position.y;
        figuresMap.remove(oldPos);

        if (!(moveVector.x == 0 && moveVector.y == 0)) {
            if (figure instanceof King king) {
                king.setWasMoved(true);
            }
            if (figure instanceof Rook rook) {
                rook.setWasMoved(true);
            }

            position.translate(moveVector.x, moveVector.y);
            String newPos = position.x + "," + position.y;

            if (figure.isMayBeat() && figuresMap.containsKey(newPos)) {
                Figures beaten = figuresMap.get(newPos);
                System.out.println("Figur geschlagen: " + beaten.getShortcut());
                figuresMap.remove(newPos);
            }
            figuresMap.put(newPos, figure);
        }
    }


    public static Point mapInputToVector(String input, HashMap<String,Point> directionMap) {
        return directionMap.get(input);
    }

    public static int handleCastling(HashMap<String, Figures> figuresMap, String activePlayer, String chosenCoordinates) {
        int gameStatus;
        switch (chosenCoordinates) {
            case "klrochade" -> {
                gameStatus = FiguresUtils.handleSmallCastling(figuresMap, activePlayer);
                return gameStatus;
            }
            case "grrochade" -> {
                gameStatus = FiguresUtils.handleBigCastling(figuresMap, activePlayer);

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

    private static int handleSmallCastling(HashMap<String, Figures> figuresMap, String activePlayer) {
        King king;
        Rook rook;
        int gameStatus;
        switch (activePlayer) {
            case "Weiss" -> {
                if (figuresMap.get("7,7") instanceof Rook && figuresMap.get("4,7") instanceof King) {
                    rook = (Rook) figuresMap.get("7,7");
                    king = (King) figuresMap.get("4,7");
                } else return -1;

                if (figuresMap.containsKey("5,7") || figuresMap.containsKey("6,7")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                gameStatus = FiguresUtils.checkCastling(rook,king);
                if (gameStatus != 0) return gameStatus;
                FiguresUtils.updatePosition(rook,figuresMap,new Point(-2,0));
                FiguresUtils.updatePosition(king,figuresMap,new Point(2,0));
                return 0;
            }
            case "Schwarz" -> {
                if (figuresMap.get("7,0") instanceof Rook && figuresMap.get("4,0") instanceof King) {
                    rook = (Rook) figuresMap.get("7,0");
                    king = (King) figuresMap.get("4,0");
                } else return -1;

                gameStatus = FiguresUtils.checkCastling(rook, king);
                if (gameStatus != 0) return gameStatus;

                if (figuresMap.containsKey("5,0") || figuresMap.containsKey("6,0")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                FiguresUtils.updatePosition(rook,figuresMap,new Point(-2,0));
                FiguresUtils.updatePosition(king,figuresMap,new Point(2,0));
                return 0;
            }
        }
        return -1;
    }

    private static int handleBigCastling(HashMap<String, Figures> figuresMap, String activePlayer) {
        King king;
        Rook rook;
        int gameStatus;
        switch (activePlayer) {
            case "Weiss" -> {
                if (figuresMap.get("0,7") instanceof Rook && figuresMap.get("4,7") instanceof King) {
                    rook = (Rook) figuresMap.get("0,7");
                    king = (King) figuresMap.get("4,7");
                } else return -1;
                gameStatus = FiguresUtils.checkCastling(rook, king);
                if (gameStatus != 0) return gameStatus;
                if (figuresMap.containsKey("1,7") || figuresMap.containsKey("2,7") || figuresMap.containsKey("3,7")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                FiguresUtils.updatePosition(rook,figuresMap,new Point(3,0));
                FiguresUtils.updatePosition(king,figuresMap,new Point(-2,0));
                return 0;
            }
            case "Schwarz" -> {
                if (figuresMap.get("0,0") instanceof Rook && figuresMap.get("4,0") instanceof King) {
                    rook = (Rook) figuresMap.get("0,0");
                    king = (King) figuresMap.get("4,0");
                } else return -1;

                gameStatus = FiguresUtils.checkCastling(rook,king);

                if (gameStatus != 0) return gameStatus;
                if (figuresMap.containsKey("1,0") || figuresMap.containsKey("2,0") || figuresMap.containsKey("3,0")) {
                    System.out.println("Felder blockiert");
                    return -1;
                }
                FiguresUtils.updatePosition(rook,figuresMap,new Point(3,0));
                FiguresUtils.updatePosition(king,figuresMap,new Point(-2,0));
                return 0;
            }
        }
        return -1;
    }
}