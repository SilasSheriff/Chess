package org.chess.enums;

import lombok.Getter;

import java.awt.*;

public enum Direction {
    NORTH(new Point(0,-1),"n"),
    SOUTH(new Point(0,1),"s"),
    WEST(new Point(-1,0),"w"),
    EAST(new Point(1,0),"o"),
    NORTH_WEST(new Point(-1,-1),"nw"),
    NORTH_EAST(new Point(1,-1),"no"),
    SOUTH_WEST(new Point(-1,1),"sw"),
    SOUTH_EAST(new Point(1,1),"so");

    @Getter
    private final Point vector;
    private final String input;

    Direction(Point vector, String input) {
        this.vector = vector;
        this.input = input;
    }

    public static Direction fromInput(String input) {
        for (Direction dir : values()){
            if(dir.input.equals(input)){
                return dir;
            }
        }
        return null;
    }
}
