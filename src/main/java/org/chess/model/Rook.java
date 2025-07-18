package org.chess.model;


import org.chess.util.FiguresUtils;
import org.chess.util.RookUtils;

import java.awt.*;
import java.util.HashMap;

public class Rook implements Figures{
    private final Point position;
    private boolean mayBeat = false;
    private final char colour;
    private final HashMap<String,Point> directionMap = new HashMap<>();
    private boolean wasMoved = false;

    public Rook(Point position, char colour){
        this.colour = colour;
        this.position = position;
        directionMap.put("n",new Point(0,-1));
        directionMap.put("s",new Point(0,1));
        directionMap.put("o",new Point(1,0));
        directionMap.put("w",new Point(-1,0));
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = RookUtils.checkPossibleMove(this,figuresMap);

        String input = RookUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = FiguresUtils.mapInputToVector(input,directionMap);

        if (moveVector == null){
            System.out.println("ungültige Richtung");
            return -1;
        }

        this.setMayBeat(false);

        int allowedDistance = RookUtils.calculateAllowedDistance(moveVector,figuresMap,this);
        if (allowedDistance < 1){
            System.out.println("Ungültiger Zug");
            return -1;
        }

        int distance = RookUtils.askDistanceInput(allowedDistance,this);
        if(distance > allowedDistance){
            System.out.println("Zu weit");
            return -1;
        }

        Point translationVector = new Point(moveVector.x *distance,moveVector.y*distance);
        FiguresUtils.updatePosition(this,figuresMap,translationVector);

    return 0;
    }

    @Override
    public String getShortcut() {
        return "T";
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
    public void setMayBeat(boolean mayBeat) {
        this.mayBeat = mayBeat;
    }

    @Override
    public boolean isMayBeat() {
        return mayBeat;
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
