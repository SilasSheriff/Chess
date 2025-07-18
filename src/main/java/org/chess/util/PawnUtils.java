package org.chess.util;

import org.chess.model.Figures;
import org.chess.model.King;
import org.chess.model.Pawn;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PawnUtils {

    public static String askDirectionInput(HashMap<String,String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll der Bauer sich bewegen? ");
        for (Map.Entry<String,String> entry : directionDistanceMap.entrySet()){
            System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
        }
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
        Point direction = switch (input) {
            case "v", "2v" -> new Point(0,1);
            case "aw" -> new Point(-1,1);
            case "ao" -> new Point(1,1);
            default -> null;
        };

        if (direction == null) {
            System.out.println("Ungültige Richtung");
            return new Point(0, 0);
        }

        if (pawn.getColour() == 'w') {
            direction.y *= -1;
        }

        if (input.equals("2v")) {
            if (pawn.getPosition().y == pawn.getBaseline()) {
                direction.y *= 2;
            } else {
                System.out.println("Doppelschritt nur von der Grundlinie erlaubt.");
                return new Point(0, 0);
            }
        }

        return direction;
    }
    public static HashMap<String,String> checkPossibleMove(Pawn pawn, HashMap<String, Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : pawn.getDirectionMap().entrySet()){
            Point moveVector = handlePawnMove(pawn,entry.getKey());
            pawn.setMayBeat(false);
            int allowedDistance = checkObstaclePawn(figuresMap,pawn,moveVector);
            if (allowedDistance > 0){
                directionDistanceMap.put(entry.getKey(),"(max: " + allowedDistance + " Schlagen: " + pawn.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Der Bauer kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

}
