package org.chess.util;

import org.chess.model.Figures;
import org.chess.model.King;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KingUtils {

    public static String askDirectionInput(HashMap<String,String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll der König sich bewegen? ");
        for (Map.Entry<String,String> entry : directionDistanceMap.entrySet()){
            System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
        }
        return scanner.nextLine().trim().toLowerCase();
    }

    public static boolean isPathFree(HashMap<String, Figures> figuresMap, Figures figure, Point moveVector) {
        return FiguresUtils.checkObstacle(figuresMap, figure, moveVector) != 0;
    }

    public static HashMap<String,String> checkPossibleMove(King king, HashMap<String, Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : king.getDirectionMap().entrySet()){
            boolean allowedMove = isPathFree(figuresMap, king, entry.getValue());
            if (allowedMove){
                directionDistanceMap.put(entry.getKey(),"(Schlagen: " + king.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Der König kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }
}