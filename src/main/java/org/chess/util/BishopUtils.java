package org.chess.util;

import org.chess.model.Bishop;
import org.chess.model.Figures;
import org.chess.model.Queen;
import org.chess.model.Rook;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BishopUtils {
    public static String askDirectionInput(HashMap<String,String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll der Läufer sich bewegen? ");
        for (Map.Entry<String,String> entry : directionDistanceMap.entrySet()){
            System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
        }
        return scanner.nextLine().trim().toLowerCase();
    }

    public static int calculateAllowedDistance(Point moveVector, HashMap<String, Figures> figuresMap, Bishop bishop){
        return FiguresUtils.checkObstacle(figuresMap, bishop, moveVector);
    }


    public static int askDistanceInput(int allowedDistance) {

        Scanner scanner = new Scanner(System.in);

        if (allowedDistance < 1){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll der Läufer sich bewegen?");
        return scanner.nextInt();
    }

    public static HashMap<String,String> checkPossibleMove(Bishop bishop, HashMap<String, Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : bishop.getDirectionMap().entrySet()){
            int allowedDistance = calculateAllowedDistance(entry.getValue(), figuresMap, bishop);
            if (allowedDistance > 0){
                directionDistanceMap.put(entry.getKey(),"(max: " + allowedDistance + " Schlagen: " + bishop.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Der Läufer kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

}
