package org.chess.util;

import org.chess.model.*;

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

    public static int checkObstaclePawn(HashMap<String, Pieces> piecesMap, Pawn pawn, Point moveVector){
        int x = pawn.getPosition().x;
        int y = pawn.getPosition().y;
        x += moveVector.x;
        y += moveVector.y;
        String pawnCoordinate = x + "," + y;

        if (moveVector.y == 2){
            String yAhead = x + "," + pawn.getPosition().y + moveVector.y / 2 ;
            // Überprüfe auf Figur auf dem Zielfeld
            if (piecesMap.containsKey(yAhead) || piecesMap.containsKey(pawnCoordinate)){
                System.out.println("Doppelzug ist blockiert");
                return 0;
            }
            // Überprüfe auf Schach
            return (PieceUtils.checkChess(piecesMap,pawn,moveVector)) ? 0 : 2;

        }
        //Schlagen
        if (moveVector.x != 0) {
            Pieces f = piecesMap.get(pawnCoordinate);
            if(f == null) return 0;
            if (pawn.getColour() != f.getColour()) {
                // Überprüfe auf Schach
                if (PieceUtils.checkChess(piecesMap,pawn,moveVector)){
                    return 0;
                }
                // Überprüfe, ob nach dem simulierten Zug der eigene König im Schach steht
                pawn.setMayBeat(true);
                return 1; //Feld schräg wird vom Gegner besetzt
            }
        }
        // Bewegung nach vorne
        if (!piecesMap.containsKey(pawnCoordinate)){
            // Überprüfe auf Schach
            if (PieceUtils.checkChess(piecesMap,pawn,moveVector)){
                return 0;
            }
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

    public static HashMap<String,String> checkPossibleMove(Pawn pawn, HashMap<String, Pieces> piecesMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : pawn.getDirectionMap().entrySet()){
            Point moveVector = handlePawnMove(pawn,entry.getKey());
            pawn.setMayBeat(false);
            int allowedDistance = checkObstaclePawn(piecesMap,pawn,moveVector);
            if (allowedDistance > 0){
                directionDistanceMap.put(entry.getKey(),"");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Der Bauer kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

    public static int managePawnSwap(Pawn pawn, HashMap<String,Pieces> piecesMap){
        Point pawnPos = pawn.getPosition();
        String pawnCoordinate = pawnPos.x + "," + pawnPos.y;
        Scanner scanner = new Scanner(System.in);
        // Überprüfe ob der Bauer 6 Felder vom Start entfernt ist.
        if(Math.abs(pawn.getBaseline() - pawnPos.y) == 6){
            System.out.println("Ihr Bauer hat die gegnerische Grundlinie erreicht, welche Figur möchten Sie einsetzen \n" +
                    "<Dame>, <Läufer>, <Turm>, <Springer>");

            piecesMap.remove(pawnCoordinate);
            String input = scanner.nextLine();
            switch (input){
                case "dame" -> piecesMap.put(pawnCoordinate,new Queen(pawnPos,pawn.getColour()));
                case "läufer" -> piecesMap.put(pawnCoordinate,new Bishop(pawnPos,pawn.getColour()));
                case "turm" -> piecesMap.put(pawnCoordinate,new Rook(pawnPos,pawn.getColour()));
                case "springer" -> piecesMap.put(pawnCoordinate,new Knight(pawnPos,pawn.getColour()));
                default -> {
                    System.out.println("Keine gültige Figur") ;
                    return -1;
                }
            }

        }
        return 0;
    }

}
