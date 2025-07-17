package org.chess.util;

import org.chess.enums.Direction;
import org.chess.model.Figures;
import org.chess.model.Pawn;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class PawnUtils {

    public static String askDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("In welche Richtung soll der Bauer sich bewegen? <v>, <2v>, <ao, <aw>");
        return scanner.nextLine().trim().toLowerCase();
    }

    public static int checkObstaclePawn(HashMap<String, Figures> figuresMap, Pawn pawn, Point moveVector){
        int x = pawn.getPosition().x;
        int y = pawn.getPosition().y;

        x += moveVector.x;
        y += moveVector.y;

        String targetCoordinate = x + "," + y;

        if (moveVector.y == 2){
            String yAhead = x + "," + pawn.getPosition().y + moveVector.y / 2 ;
            if (figuresMap.containsKey(yAhead) || figuresMap.containsKey(targetCoordinate)){
                System.out.println("Doppelzug ist blockiert");
                return 0;
            }
        }

        //Schlagen oder Einzelzug
        if (moveVector.x != 0) {
            Figures f = figuresMap.get(targetCoordinate);
            if(f == null) return 0;
            if (pawn.getColour() != f.getColour()) {
                pawn.setMayBeat(true);
                return 1; //Feld schräg wird vom Gegner besetzt
            }
        }

        if (!figuresMap.containsKey(targetCoordinate)){
            return 1;
        }

        return 0;
    }
    public static Point handlePawnMove(Pawn pawn, String input) {
        Direction direction = switch (input) {
            case "v", "2v" -> Direction.SOUTH;
            case "aw" -> Direction.SOUTH_WEST;
            case "ao" -> Direction.SOUTH_EAST;
            default -> null;
        };

        if (direction == null) {
            System.out.println("Ungültige Richtung");
            return new Point(0, 0);
        }

        // Neue Kopie erstellen, um die enum-Konstanten nicht zu verändern
        Point moveVector = new Point(direction.getVector());

        if (pawn.getColour() == 'w') {
            moveVector.y *= -1;
        }

        if (input.equals("2v")) {
            if (pawn.getPosition().y == pawn.getBaseline()) {
                moveVector.y *= 2;
            } else {
                System.out.println("Doppelschritt nur von der Grundlinie erlaubt.");
                return new Point(0, 0);
            }
        }

        return moveVector;
    }


}
