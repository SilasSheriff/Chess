package org.chess.util;

import org.chess.model.Figures;


import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class KingUtils {

    public static String askDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("In welche Richtung soll der König sich bewegen? <NO>, <SO>, <NW>,"
                + "<SW>, <N>, <S>, <W>, <O>");
        return scanner.nextLine().trim().toLowerCase();
    }

    public static boolean isPathFree(HashMap<String, Figures> figuresMap, Figures figure, Point moveVector) {
        return FiguresUtils.checkObstacle(figuresMap, figure, moveVector) != 0;
    }
}