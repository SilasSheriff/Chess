package org.chess.util;

import org.chess.model.Bishop;
import org.chess.model.Figures;
import org.chess.model.Queen;


import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class BishopUtils {
    public static String askDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("In welche Richtung soll der Läufer sich bewegen? <NW>, <SW>, <NO>, <SO>");
        return scanner.nextLine().trim().toLowerCase();
    }

    public static int calculateAllowedDistance(Point moveVector, HashMap<String, Figures> figuresMap, Bishop bishop){
        return FiguresUtils.checkObstacle(figuresMap, bishop, moveVector);
    }


    public static int askDistanceInput(int allowedDistance, Bishop bishop) {

        Scanner scanner = new Scanner(System.in);

        if (allowedDistance < 1){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll der Läufer sich bewegen? (max: " + allowedDistance + ", Schlagen: " +bishop.isMayBeat() + ")");
        return scanner.nextInt();
    }

}
