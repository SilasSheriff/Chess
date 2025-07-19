package org.chess.util;

import java.awt.*;

public class PointUtils {

    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }
}
