package org.chess.util;

import org.chess.model.Bishop;
import org.chess.model.Figures;
import org.chess.model.King;
import org.chess.model.Queen;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KingUtils {

    public static String askDirectionInput(HashMap<String, String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll der König sich bewegen? ");
        for (Map.Entry<String, String> entry : directionDistanceMap.entrySet()) {
            System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
        }
        return scanner.nextLine().trim().toLowerCase();
    }

    public static boolean isPathFree(HashMap<String, Figures> figuresMap, Figures figure, Point moveVector) {
        return FiguresUtils.checkObstacle(figuresMap, figure, moveVector) != 0;
    }

    public static HashMap<String, String> checkPossibleMove(King king, HashMap<String, Figures> figuresMap) {
        HashMap<String, String> directionDistanceMap = new HashMap<>();
        for (Map.Entry<String, Point> entry : king.getDirectionMap().entrySet()) {
            boolean allowedMove = isPathFree(figuresMap, king, entry.getValue());
            if (allowedMove) {
                directionDistanceMap.put(entry.getKey(), "(Schlagen: " + king.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()) {
            System.out.println("Der König kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

    public static King geKingPosition(HashMap<String, Figures> figuresMap, char colour) {
        for (Figures figure : figuresMap.values()) {
            if (figure instanceof King && figure.getColour() == colour) {
                return (King) figure;
            }
        }
        return null;
    }

    public static boolean checkForBishopCheck(King king, HashMap<String, Figures> figuresMap) {
        boolean checkBishop = false;
        String coordinate = " ";
        Point positionIncrement = king.getPosition();
        for(int i = 0; i < 8; i++) {
            positionIncrement =PointUtils.add(positionIncrement,new Point(1,1));
            coordinate = positionIncrement.x + "," + positionIncrement.y;
            if(figuresMap.get(coordinate) instanceof Bishop && figuresMap.get(coordinate).getColour() != king.getColour()) {
                return true;
            }
            if(figuresMap.get(coordinate) instanceof Queen && figuresMap.get(coordinate).getColour() != king.getColour()) {
                return true;
            }
            if(figuresMap.get(coordinate).getColour() == king.getColour()) {
                return false;
            }
        }
        return false;
    }
}