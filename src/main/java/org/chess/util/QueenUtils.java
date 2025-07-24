package org.chess.util;

import org.chess.model.Pieces;
import org.chess.model.Queen;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QueenUtils {
    public static String askDirectionInput(HashMap<String,String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll die Dame sich bewegen? ");
        for (Map.Entry<String,String> entry : directionDistanceMap.entrySet()){
            System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
        }
        return scanner.nextLine().trim().toLowerCase();
    }


    public static ArrayList<Integer> calculateAllowedDistance(HashMap<String, Pieces> piecesMap,  Queen queen, Point moveVector){
        return PieceUtils.checkObstacle(piecesMap, queen, moveVector);
    }

    public static int askDistanceInput( ArrayList<Integer> allowedDistance) {

        Scanner scanner = new Scanner(System.in);
        if (allowedDistance.isEmpty()){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll die Dame sich bewegen? ");
        return scanner.nextInt();
    }

    public static HashMap<String,String> checkPossibleMove(Queen queen, HashMap<String, Pieces> piecesMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();

        System.out.println(queen.isMayBeat());
        for(Map.Entry<String,Point> entry : queen.getDirectionMap().entrySet()){
            ArrayList<Integer> allowedPos = calculateAllowedDistance(piecesMap,  queen,entry.getValue());
            if (!allowedPos.isEmpty()){
                directionDistanceMap.put(entry.getKey(),"(" + PieceUtils.arraylistUnwrapping(allowedPos) + " Schlagen: " + queen.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Die Dame kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }
}
