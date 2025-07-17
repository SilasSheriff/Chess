package org.chess.util;

import org.chess.model.Figures;
import org.chess.model.Knight;
import org.chess.model.Rook;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class KnightUtils {
    public static String askDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("In welche Richtung soll der Springer sich bewegen? <N2O>, <S2O>, <N2W>, " +
                "<S2W>, <2NO>, <2SO>, <2NW>, <2SW>");
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
            knight.setMayBeat(true);
            return true;
        } else if (target.getColour() !=(knight.getColour())) {
            knight.setMayBeat(true);
            return true;
        }
        return false;
    }
}
