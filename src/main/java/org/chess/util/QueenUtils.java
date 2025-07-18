package org.chess.util;

import org.chess.model.Bishop;
import org.chess.model.Figures;
import org.chess.model.Queen;


import java.awt.*;
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


    public static int calculateAllowedDistance(Point moveVector, HashMap<String, Figures> figuresMap, Queen queen){
        return FiguresUtils.checkObstacle(figuresMap, queen, moveVector);
    }

    public static int askDistanceInput( int allowedDistance, Queen queen) {

        Scanner scanner = new Scanner(System.in);
        if (allowedDistance < 1){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll die Dame sich bewegen? ");
        return scanner.nextInt();
    }

    public static HashMap<String,String> checkPossibleMove(Queen queen, HashMap<String, Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : queen.getDirectionMap().entrySet()){
            int allowedDistance = calculateAllowedDistance(entry.getValue(), figuresMap, queen);
            if (allowedDistance > 0){
                directionDistanceMap.put(entry.getKey(),"(max: " + allowedDistance + " Schlagen: " + queen.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Die Dame kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

}
