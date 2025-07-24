package org.chess.util;

import org.chess.model.King;
import org.chess.model.Pieces;
import org.chess.model.Knight;

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

    public static boolean checkObstacleKnight(HashMap<String, Pieces> piecesMap, Knight knight, Point direction) {
        int x = knight.getPosition().x + direction.x;
        int y = knight.getPosition().y + direction.y;

        String coordinate = x + "," + y;
        if (x < 0 || y < 0 || x > 7 || y > 7) {
            return false;
        }
        Pieces target = piecesMap.get(coordinate);

        HashMap<String,Pieces> tempPiecesMap = BoardUtils.copyPiecesMap(piecesMap);
        Pieces copiedKnight = tempPiecesMap.get(knight.getPosition().x + "," + knight.getPosition().y);
        PieceUtils.updatePosition(copiedKnight, tempPiecesMap, direction);
        BoardUtils.handleCheck(tempPiecesMap,knight.getColour());
        King king = KingUtils.getKingPosition(tempPiecesMap, knight.getColour());

        if (king == null){
            System.out.println("Kein König vorhanden");
            return false;
        }
        if (king.isInCheck()){
            return false;
        }

        if (target == null){
            return true;
        } else if (target.getColour() != knight.getColour()) {
            knight.setMayBeat(true);
            return true;
        }
        return false;
    }

    public static HashMap<String,String> checkPossibleMove(Knight knight, HashMap<String, Pieces> piecesMap) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : knight.getDirectionMap().entrySet()){
            knight.setMayBeat(false);
            boolean allowedMove = checkObstacleKnight( piecesMap,knight,entry.getValue());
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
