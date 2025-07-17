package org.chess.util;

import org.chess.model.Figures;
import org.chess.model.Rook;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class RookUtils {
    public static String askDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("In welche Richtung soll der Turm sich bewegen? <N>, <S>, <W>, <O>");
        return scanner.nextLine().trim().toLowerCase();
    }

    public static int calculateAllowedDistance(Point moveVector, HashMap<String, Figures> figuresMap, Rook rook){
        return FiguresUtils.checkObstacle(figuresMap, rook, moveVector);
    }

    public static int askDistanceInput(int allowedDistance, Rook rook) {

        Scanner scanner = new Scanner(System.in);
        if (allowedDistance < 1){
            System.out.println("Ungültiger Zug");
        }
        System.out.println("Wie weit soll der Turm sich bewegen? (max: " + allowedDistance + ", Schlagen: " +rook.isMayBeat() + ")");
        return scanner.nextInt();
    }

}
