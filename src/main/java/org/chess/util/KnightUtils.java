package org.chess.util;

import org.chess.model.Figures;
import org.chess.model.Knight;
import org.chess.model.Rook;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KnightUtils {
    public static String askDirectionInput(HashMap<String,String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll der Springer sich bewegen? ");
        for (Map.Entry<String,String> entry : directionDistanceMap.entrySet()){
            System.out.print("<" + entry.getKey() + "> " + entry.getValue());
        }
        return scanner.nextLine().trim().toLowerCase();
    }
    public static boolean checkObstacleKnight(HashMap<String, Figures> figuresMap, Knight knight, Point direction) {
        int x = knight.getPosition().x + direction.x;
        int y = knight.getPosition().y + direction.y;

        String coordinate = x + "," + y;
        if (x < 0 || y < 0 || x > 7 || y > 7) {
            return false;

        }
        Figures target = figuresMap.get(coordinate);

        if (target == null) {
            return true;
        } else if (target.getColour() !=(knight.getColour())) {
            knight.setMayBeat(true);
            return true;
        }
        return false;
    }

    public static HashMap<String,String> checkPossibleMove(Knight knight, HashMap<String, Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : knight.getDirectionMap().entrySet()){
            knight.setMayBeat(false);
            boolean allowedMove = checkObstacleKnight( figuresMap,knight,entry.getValue());
            if (allowedMove){
                directionDistanceMap.put(entry.getKey(),"(Schlagen: " + knight.isMayBeat() + "), ");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Der Springer kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }
}
