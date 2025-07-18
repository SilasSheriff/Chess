package org.chess.model;

import org.chess.util.FiguresUtils;
import org.chess.util.KingUtils;

import java.awt.*;
import java.util.HashMap;

public class King implements Figures {
    private final Point position;
    private boolean mayBeat = false;
    private boolean wasMoved = false;
    private final char colour;
    private final HashMap<String,Point> directionMap = new HashMap<>();

    public King(Point position, char colour){
        this.colour = colour;
        this.position = position;
        directionMap.put("n", new Point(0,-1));
        directionMap.put("s", new Point(0,1));
        directionMap.put("w", new Point(-1,0));
        directionMap.put("o", new Point(1,0));
        directionMap.put("nw", new Point(-1,-1));
        directionMap.put("no", new Point(1,-1));
        directionMap.put("sw", new Point(-1,1));
        directionMap.put("so", new Point(1,1));
    }

    @Override
    public int move(HashMap<String, Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = KingUtils.checkPossibleMove(this, figuresMap);
        String input = KingUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = FiguresUtils.mapInputToVector(input, directionMap);
        if (moveVector == null) {
            System.out.println("Ungültige Richtung.");
            return -1;
        }


        this.setMayBeat(false);

        if (!KingUtils.isPathFree(figuresMap, this, moveVector)) {
            System.out.println("Der Weg ist blockiert.");
            return -1;
        }

        FiguresUtils.updatePosition(this, figuresMap, moveVector);
        return 0;
    }

    @Override
    public String getShortcut() {
        return "K";
    }


    @Override
    public Point getPosition() {
        return position;
    }


    @Override
    public char getColour() {
        return colour;
    }


    @Override
    public boolean isMayBeat() {
        return mayBeat;
    }

    @Override
    public void setMayBeat(boolean mayBeat) {
        this.mayBeat = mayBeat;
    }

    @Override
    public HashMap<String,Point> getDirectionMap() {
        return directionMap;
    }

    public boolean isWasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }
}
