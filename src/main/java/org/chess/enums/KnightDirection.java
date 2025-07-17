package org.chess.enums;

import lombok.Getter;

import java.awt.*;

public enum KnightDirection {
    NORTH_TWO_EAST(new Point(2, -1), "n2o"),
    SOUTH_TWO_EAST(new Point(2, 1), "s2o"),
    NORTH_TWO_WEST(new Point(-2, -1), "n2w"),
    SOUTH_TWO_WEST(new Point(-2, 1), "s2w"),
    TWO_NORTH_EAST(new Point(1, -2), "2no"),
    TWO_SOUTH_EAST(new Point(1, 2), "2so"),
    TWO_NORTH_WEST(new Point(-1, -2), "2nw"),
    TWO_SOUTH_WEST(new Point(-1, 2), "2sw");

    @Getter
    private final Point vector;
    private final String input;

    KnightDirection(Point vector, String input) {
        this.vector = vector;
        this.input = input;
    }

    public static KnightDirection fromInput(String input) {
        for (KnightDirection dir : values()) {
            if (dir.input.equalsIgnoreCase(input.trim())) {
                return dir;
            }
        }
        return null;
    }
}
