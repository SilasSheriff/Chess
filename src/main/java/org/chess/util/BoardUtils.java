package org.chess.util;

import org.chess.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BoardUtils {

    public static HashMap<String, Figures> mapFiguresToString(ArrayList<Figures> figures) {
        HashMap<String, Figures> positionMap = new HashMap<>();

        for (Figures f : figures) {
            String key = f.getPosition().x + "," + f.getPosition().y;
            positionMap.put(key, f);
        }
        return positionMap;
    }

    public static int chooseFigureToMove(HashMap<String,Figures> figuresMap, String activePlayer){
        Scanner scanner = new Scanner(System.in);
        System.out.println(activePlayer +  ", geben Sie die Koordinaten der Figur, die Sie bewegen möchten! (x,y), " +
                "\n für eine Rochade: <KlRochade> bzw. <GrRochade>");
        String chosenCoordinates = scanner.nextLine().trim().toLowerCase();

if (chosenCoordinates.contains("rochade")) {
    int gameStatus  = FiguresUtils.handleCastling(figuresMap, activePlayer, chosenCoordinates);
    if (gameStatus == -1) return -1;
    else return 0;
}

        Figures figure = figuresMap.get(chosenCoordinates);
        if (figure == null) {
            System.out.println("Keine Figur an dieser Position!");
            return -1;
        }

        if (!(figure.getColour()  == (activePlayer.toLowerCase().charAt(0)))){
            System.out.println("Diese Figur gehört nicht dem aktuellen Spieler!");
            return -1;
        }

        return figure.move(figuresMap);
    }

    public static void displayBoard(HashMap<String,Figures> positionMap){
        final String separator = "_________________________________________________";

        System.out.println(separator);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print("| ");
                String pos = j + "," + i;

                if (positionMap.containsKey(pos)) {
                    Figures f = positionMap.get(pos);
                    System.out.print(f.getShortcut() + "_" + f.getColour());
                } else {
                    System.out.print("   ");
                }

                System.out.print(" ");
            }
            System.out.println();
            System.out.println(separator);

        }
    }

    public static ArrayList<Figures> implementStandardChessBoard(){
        ArrayList<Figures> figures = new ArrayList<>();

        Pawn pawnS1 = new Pawn(new Point(0,1), 's');
        Pawn pawnS2 = new Pawn(new Point(1,1), 's');
        Pawn pawnS3 = new Pawn(new Point(2,1), 's');
        Pawn pawnS4 = new Pawn(new Point(3,1), 's');
        Pawn pawnS5 = new Pawn(new Point(4,1), 's');
        Pawn pawnS6 = new Pawn(new Point(5,1), 's');
        Pawn pawnS7 = new Pawn(new Point(6,1), 's');
        Pawn pawnS8 = new Pawn(new Point(7,1), 's');

        figures.add(pawnS1);
        figures.add(pawnS2);
        figures.add(pawnS3);
        figures.add(pawnS4);
        figures.add(pawnS5);
        figures.add(pawnS6);
        figures.add(pawnS7);
        figures.add(pawnS8);

        Pawn pawnW1 = new Pawn(new Point(0,6), 'w');
        Pawn pawnW2 = new Pawn(new Point(1,6), 'w');
        Pawn pawnW3 = new Pawn(new Point(2,6), 'w');
        Pawn pawnW4 = new Pawn(new Point(3,6), 'w');
        Pawn pawnW5 = new Pawn(new Point(4,6), 'w');
        Pawn pawnW6 = new Pawn(new Point(5,6), 'w');
        Pawn pawnW7 = new Pawn(new Point(6,6), 'w');
        Pawn pawnW8 = new Pawn(new Point(7,6), 'w');

        figures.add(pawnW1);
        figures.add(pawnW2);
        figures.add(pawnW3);
        figures.add(pawnW4);
        figures.add(pawnW5);
        figures.add(pawnW6);
        figures.add(pawnW7);
        figures.add(pawnW8);

        Rook rookS1 = new Rook(new Point(0,0),'s');
        Rook rookS2 = new Rook(new Point(7,0),'s');

        figures.add(rookS1);
        figures.add(rookS2);

        Rook rookW1 = new Rook(new Point(0,7),'w');
        Rook rookW2 = new Rook(new Point(7,7),'w');

        figures.add(rookW1);
        figures.add(rookW2);

        Knight knightS1 = new Knight(new Point(1,0),'s');
        Knight knightS2 = new Knight(new Point(6,0),'s');

        figures.add(knightS1);
        figures.add(knightS2);

        Knight knightW1 = new Knight(new Point(1,7),'w');
        Knight knightW2 = new Knight(new Point(6,7),'w');

        figures.add(knightW1);
        figures.add(knightW2);

        Bishop bishopS1 = new Bishop(new Point(2,0),'s');
        Bishop bishopS2 = new Bishop(new Point(5,0),'s');

        figures.add(bishopS1);
        figures.add(bishopS2);

        Bishop bishopW1 = new Bishop(new Point(2,7),'w');
        Bishop bishopW2 = new Bishop(new Point(5,7),'w');

        figures.add(bishopW1);
        figures.add(bishopW2);

        Queen queenS = new Queen(new Point(3,0),'s');
        Queen queenW = new Queen(new Point(3,7),'w');

        figures.add(queenS);
        figures.add(queenW);

        King kingS = new King(new Point(4,0),'s');
        King kingW = new King(new Point(4,7),'w');

        figures.add(kingS);
        figures.add(kingW);
        return figures;
    }

}
