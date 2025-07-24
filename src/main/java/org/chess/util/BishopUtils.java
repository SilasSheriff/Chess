package org.chess.util;

import org.chess.model.Bishop;
import org.chess.model.Pieces;


import java.awt.*;
import java.util.ArrayList;
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

    public static ArrayList<Integer> calculateAllowedDistances(HashMap<String, Pieces> piecesMap, Bishop bishop, Point moveVector){
        return PieceUtils.checkObstacle(piecesMap, bishop, moveVector);
    }


    public static int askDistanceInput(ArrayList<Integer> allowedDistance) {

        Scanner scanner = new Scanner(System.in);

        if (allowedDistance.isEmpty()){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll der Läufer sich bewegen?");
        return scanner.nextInt();
    }

    public static HashMap<String,String> checkPossibleMove(HashMap<String, Pieces> piecesMap, Bishop bishop) {
        HashMap<String,String> directionDistanceMap = new HashMap<>();
        for(Map.Entry<String,Point> entry : bishop.getDirectionMap().entrySet()){
            ArrayList<Integer> allowedDistance = calculateAllowedDistances( piecesMap, bishop, entry.getValue());

            if (!allowedDistance.isEmpty()){
                boolean mayBeat = bishop.isMayBeat();
                directionDistanceMap.put(entry.getKey(),"(Erlaubte Distanzen:" + PieceUtils.arraylistUnwrapping(allowedDistance) + "/ Schlagen: " + mayBeat + ")");
            }
        }
        if (directionDistanceMap.isEmpty()){
            System.out.println("Der Läufer kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

}
