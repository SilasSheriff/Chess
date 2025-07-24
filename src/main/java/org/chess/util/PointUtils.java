package org.chess.util;

import java.awt.*;
import java.util.ArrayList;

public class PointUtils {

    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    public static Point switchNorthSouth(Point point){
        return new Point(point.x,-point.y);
    }

    public static boolean checkOutsideBoardEdge(Point point){
        return point.x < 0 || point.y < 0 || point.x > 7 || point.y > 7;
    }

    public static ArrayList<Point> straightAndDiagonal(){
        ArrayList<Point> straightAndDiagonal = new ArrayList<>(PointUtils.straight());
        straightAndDiagonal.addAll(PointUtils.diagonal());
        return straightAndDiagonal;
    }

    public static ArrayList<Point> straight(){
        ArrayList<Point> straight = new ArrayList<>();
        straight.add(new Point(0,-1));
        straight.add(new Point(0,1));
        straight.add(new Point(-1,0));
        straight.add(new Point(1,0));

        return straight;
    }

    public static ArrayList<Point> diagonal(){
        ArrayList<Point> diagonal = new ArrayList<>();
        diagonal.add(new Point(-1,-1));
        diagonal.add(new Point(-1,1));
        diagonal.add(new Point(1,-1));
        diagonal.add(new Point(1,1));

        return diagonal;
    }

    public static ArrayList<Point> knightsDirections(){
        ArrayList<Point> knightDirection = new ArrayList<>();
        knightDirection.add(new Point(-2,-1));
        knightDirection.add(new Point(-1,-2));
        knightDirection.add(new Point(2,-1));
        knightDirection.add(new Point(1,-2));
        knightDirection.add(new Point(-2,1));
        knightDirection.add(new Point(-1,2));
        knightDirection.add(new Point(2,1));
        knightDirection.add(new Point(1,2));
        return knightDirection;
    }
}
