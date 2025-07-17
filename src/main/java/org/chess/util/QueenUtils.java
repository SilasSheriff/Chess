package org.chess.util;

import org.chess.model.Figures;
import org.chess.model.Queen;


import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class QueenUtils {
    public static String askDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("In welche Richtung soll die Dame sich bewegen? <NO>, <SO>, <NW>," +
                " <SW>, <N>, <S>, <W>, <O>");
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
        System.out.println("Wie weit soll die Dame sich bewegen? (max: " + allowedDistance + ", Schlagen: " +queen.isMayBeat() + ")");
        return scanner.nextInt();
    }

}
