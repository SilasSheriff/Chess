package org.chess.model;

import org.chess.util.BishopUtils;
import org.chess.util.FiguresUtils;

import java.awt.*;
import java.util.HashMap;

public class Bishop implements Figures{
    private final Point position;
    private boolean mayBeat = false;
    private final char colour;
    private final HashMap<String,Point> directionMap = new HashMap<>();

    public Bishop(Point position, char colour){
        this.colour = colour;
        this.position = position;
        directionMap.put("no",new Point(1,-1));
        directionMap.put("so",new Point(1,1));
        directionMap.put("nw",new Point(-1,-1));
        directionMap.put("sw",new Point(-1,1));
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {

        HashMap<String,String> directionDistanceMap = BishopUtils.checkPossibleMove(this,figuresMap);
        String input = BishopUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = FiguresUtils.mapInputToVector(input,directionMap);
        if (moveVector == null){
            System.out.println("ungültige Richtung");
            return -1;
        }


        this.setMayBeat(false);

        int allowedDistance = BishopUtils.calculateAllowedDistance(moveVector,figuresMap,this);
        if (allowedDistance <1){
            System.out.println("Ungültiger Zug");
            return -1;
        }

        int distance = BishopUtils.askDistanceInput(allowedDistance);
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
        return "L";
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
    public HashMap<String, Point> getDirectionMap() {
        return directionMap;
    }
}
