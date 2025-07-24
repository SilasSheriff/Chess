package org.chess.util;

import org.chess.model.Pieces;
import org.chess.model.Rook;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RookUtils {
    public static String askDirectionInput(HashMap<String,String> directionDistanceMap) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("In welche Richtung soll der Turm sich bewegen? ");
            for (Map.Entry<String,String> entry : directionDistanceMap.entrySet()){
                System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
            }

            return scanner.nextLine().trim().toLowerCase();
        }

        public static ArrayList<Integer> calculateAllowedDistance(HashMap<String, Pieces> piecesMap,  Rook rook,Point moveVector){
            return PieceUtils.checkObstacle(piecesMap, rook, moveVector);
        }

        public static HashMap<String,String> checkPossibleMove(Rook rook, HashMap<String, Pieces> piecesMap) {
            HashMap<String,String> directionDistanceMap = new HashMap<>();
            for(Map.Entry<String,Point> entry : rook.getDirectionMap().entrySet()){
                ArrayList<Integer> allowedDistances = calculateAllowedDistance(piecesMap, rook,entry.getValue());
                if (!allowedDistances.isEmpty()){
                    directionDistanceMap.put(entry.getKey(),"(" + PieceUtils.arraylistUnwrapping(allowedDistances) + "/ Schlagen: " + rook.isMayBeat() + ")");
                }
            }
            if (directionDistanceMap.isEmpty()){
            System.out.println("Der Turm kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

    public static int askDistanceInput(ArrayList<Integer> allowedDistance) {

        Scanner scanner = new Scanner(System.in);
        if (allowedDistance.isEmpty()){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll der Turm sich bewegen?");
        return scanner.nextInt();
    }

}
