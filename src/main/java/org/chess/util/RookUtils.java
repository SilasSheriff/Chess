package org.chess.util;

import org.chess.model.Figures;
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

        public static int calculateAllowedDistance(Point moveVector, HashMap<String, Figures> figuresMap, Rook rook){
            return FiguresUtils.checkObstacle(figuresMap, rook, moveVector);
        }

        public static HashMap<String,String> checkPossibleMove(Rook rook, HashMap<String, Figures> figuresMap) {
            HashMap<String,String> directionDistanceMap = new HashMap<>();
            for(Map.Entry<String,Point> entry : rook.getDirectionMap().entrySet()){
                int allowedDistance = calculateAllowedDistance(entry.getValue(), figuresMap, rook);
                if (allowedDistance > 0){
                    directionDistanceMap.put(entry.getKey(),"(max: " + allowedDistance + " Schlagen: " + rook.isMayBeat() + ")");
                }
            }
            if (directionDistanceMap.isEmpty()){
            System.out.println("Der Turm kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

    public static int askDistanceInput(int allowedDistance, Rook rook) {

        Scanner scanner = new Scanner(System.in);
        if (allowedDistance < 1){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll der Turm sich bewegen?)");
        return scanner.nextInt();
    }

}
