package org.chess.util;
import org.chess.model.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BoardUtils {

    public static void handleCheck(HashMap<String,Pieces> piecesMap, char colour) {
        King king = KingUtils.getKingPosition(piecesMap, colour);
        ArrayList<Point> threats = KingUtils.threatDirections(king, piecesMap);
        boolean isInCheck = !threats.isEmpty();

        if (king != null){
            king.setInCheck(isInCheck);
        }
    }

    public static HashMap<String, Pieces> mapFiguresToString(ArrayList<Pieces> pieces) {
        HashMap<String, Pieces> positionMap = new HashMap<>();

        for (Pieces f : pieces) {
            String key = f.getPosition().x + "," + f.getPosition().y;
            positionMap.put(key, f);
        }
        return positionMap;
    }

    public static HashMap<String,Pieces> copyPiecesMap(HashMap<String,Pieces> oldMap){
        HashMap<String,Pieces> copiedMap = new HashMap<>();
        for (Map.Entry<String,Pieces> entry : oldMap.entrySet()){
            copiedMap.put(entry.getKey(), entry.getValue().clone());
        }
        return copiedMap;
    }

    public static String inputToCoordinate(String input){
        if(input.contains("rochade")){
            return input;
        }
        char xInput = input.charAt(0);
        char yInput = input.charAt(1);

        int xCoord = xInput -'a';
        int yCoord = 8 - (yInput - '0');

        return xCoord + "," + yCoord;
    }

    public static String coordinateToInput(String coordinate){
        String[] parts = coordinate.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());

        char xChar = (char) ('a' + x);
        char yChar = (char) ('8' - y);

        return "" + xChar + yChar;
    }

    public static int chooseFigureToMove(HashMap<String, Pieces> piecesMap, String activePlayer){
        Scanner scanner = new Scanner(System.in);
        System.out.println(activePlayer +  ", wähle Sie die Figur, die sie bewegen möchten. " +
                "\n für eine Rochade: <KlRochade> bzw. <GrRochade>");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.contains("rochade")) {
        int gameStatus  = PieceUtils.handleCastling(piecesMap, activePlayer, input);
        if (gameStatus == -1) return -1;
        else return 0;
         }

        String chosenCoordinates = inputToCoordinate(input);

        Pieces figure = piecesMap.get(chosenCoordinates);
        if (figure == null) {
            System.out.println("Keine Figur an dieser Position!");
            return -1;
        }

        if (!(figure.getColour()  == (activePlayer.toLowerCase().charAt(0)))){
            System.out.println("Diese Figur gehört nicht dem aktuellen Spieler!");
            return -1;
        }

        return figure.move(piecesMap);
    }

    public static void displayBoard(HashMap<String, Pieces> piecesMap) {
        final String RESET = "\u001B[0m";
        final String WHITE_BG = "\u001B[47m";
        final String BLACK_BG = "\u001B[100m";
        final String WHITE_FG = "\u001B[97m";
        final String BLACK_FG = "\u001B[30m";

        System.out.println("   a \u3000b \u3000c \u3000d \u3000e \u3000f \u3000g \u3000h");

        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                String pos = col + "," + row;
                boolean isWhiteField = (row + col) % 2 == 0;
                String bg = isWhiteField ? WHITE_BG : BLACK_BG;

                String fg = WHITE_FG;
                String content = "\u3000"; // Default empty

                if (piecesMap.containsKey(pos)) {
                    Pieces piece = piecesMap.get(pos);
                    content = PieceUtils.getUnicodeSymbol(piece);
                    fg = piece.getColour() == 'w' ? WHITE_FG : BLACK_FG;
                }

                System.out.printf(bg + fg + " %-2s"+ RESET, content);
            }
            System.out.println(" " + (8 - row));
        }

        System.out.println("   a \u3000b \u3000c \u3000d \u3000e \u3000f \u3000g \u3000h");
    }

    public static ArrayList<Pieces> implementStandardChessBoard(){
        ArrayList<Pieces> pieces = new ArrayList<>();

        Pawn pawnS1 = new Pawn(new Point(0,1), 's');
        Pawn pawnS2 = new Pawn(new Point(1,1), 's');
        Pawn pawnS3 = new Pawn(new Point(2,1), 's');
        Pawn pawnS4 = new Pawn(new Point(3,1), 's');
        Pawn pawnS5 = new Pawn(new Point(4,1), 's');
        Pawn pawnS6 = new Pawn(new Point(5,1), 's');
        Pawn pawnS7 = new Pawn(new Point(6,1), 's');
        Pawn pawnS8 = new Pawn(new Point(7,1), 's');

        pieces.add(pawnS1);
        pieces.add(pawnS2);
        pieces.add(pawnS3);
        pieces.add(pawnS4);
        pieces.add(pawnS5);
        pieces.add(pawnS6);
        pieces.add(pawnS7);
        pieces.add(pawnS8);

        Pawn pawnW1 = new Pawn(new Point(0,6), 'w');
        Pawn pawnW2 = new Pawn(new Point(1,6), 'w');
        Pawn pawnW3 = new Pawn(new Point(2,6), 'w');
        Pawn pawnW4 = new Pawn(new Point(3,6), 'w');
        Pawn pawnW5 = new Pawn(new Point(4,6), 'w');
        Pawn pawnW6 = new Pawn(new Point(5,6), 'w');
        Pawn pawnW7 = new Pawn(new Point(6,6), 'w');
        Pawn pawnW8 = new Pawn(new Point(7,6), 'w');

        pieces.add(pawnW1);
        pieces.add(pawnW2);
        pieces.add(pawnW3);
        pieces.add(pawnW4);
        pieces.add(pawnW5);
        pieces.add(pawnW6);
        pieces.add(pawnW7);
        pieces.add(pawnW8);

        Rook rookS1 = new Rook(new Point(0,0),'s');
        Rook rookS2 = new Rook(new Point(7,0),'s');

        pieces.add(rookS1);
        pieces.add(rookS2);

        Rook rookW1 = new Rook(new Point(0,7),'w');
        Rook rookW2 = new Rook(new Point(7,7),'w');

        pieces.add(rookW1);
        pieces.add(rookW2);

        Knight knightS1 = new Knight(new Point(1,0),'s');
        Knight knightS2 = new Knight(new Point(6,0),'s');

        pieces.add(knightS1);
        pieces.add(knightS2);

        Knight knightW1 = new Knight(new Point(1,7),'w');
        Knight knightW2 = new Knight(new Point(6,7),'w');

        pieces.add(knightW1);
        pieces.add(knightW2);

        Bishop bishopS1 = new Bishop(new Point(2,0),'s');
        Bishop bishopS2 = new Bishop(new Point(5,0),'s');

        pieces.add(bishopS1);
        pieces.add(bishopS2);

        Bishop bishopW1 = new Bishop(new Point(2,7),'w');
        Bishop bishopW2 = new Bishop(new Point(5,7),'w');

        pieces.add(bishopW1);
        pieces.add(bishopW2);

        Queen queenS = new Queen(new Point(3,0),'s');
        Queen queenW = new Queen(new Point(3,7),'w');

        pieces.add(queenS);
        pieces.add(queenW);

        King kingS = new King(new Point(4,0),'s');
        King kingW = new King(new Point(4,7),'w');

        pieces.add(kingS);
        pieces.add(kingW);
        return pieces;
    }

    public static ArrayList<Pieces> implementEdgeCases(){
        ArrayList<Pieces> pieces = new ArrayList<>();


        King kingS = new King(new Point(0,7),'s');
        King kingW = new King(new Point(2,5),'w');

        Rook rookW = new Rook(new Point(1,6),'w');
        Rook rookS = new Rook(new Point(3,2),'s');

        pieces.add(kingS);
        pieces.add(kingW);

        Queen queenW = new Queen(new Point(2,1), 'w');

        pieces.add(rookW);
        pieces.add(queenW);
        pieces.add(rookS);

        return pieces;
    }
}
