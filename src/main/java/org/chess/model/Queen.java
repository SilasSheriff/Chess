package org.chess.model;

import org.chess.util.FiguresUtils;
import org.chess.util.QueenUtils;

import java.awt.*;
import java.util.HashMap;


public class Queen implements Figures{
    private boolean mayBeat = false;
    private final Point position;
    private final char colour;
    private final HashMap<String,Point> directionMap = new HashMap<>();

    public Queen(Point position, char colour){
        this.colour = colour;
        this.position = position;
        directionMap.put("no",new Point(1,-1));
        directionMap.put("so",new Point(1,1));
        directionMap.put("nw",new Point(-1,-1));
        directionMap.put("sw",new Point(-1,1));
        directionMap.put("n",new Point(0,-1));
        directionMap.put("s",new Point(0,1));
        directionMap.put("w",new Point(-1,0));
        directionMap.put("o",new Point(1,0));
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = QueenUtils.checkPossibleMove(this,figuresMap);
        String input = QueenUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = FiguresUtils.mapInputToVector(input,directionMap);

        if (moveVector == null){
            System.out.println("ungültige Richtung");
            return -1;
        }
        this.setMayBeat(false);


        int allowedDistance = QueenUtils.calculateAllowedDistance(moveVector,figuresMap,this);
        if(allowedDistance < 1){
            System.out.println("Ungültiger Zug");
            return -1;
        }
        int distance = QueenUtils.askDistanceInput(allowedDistance,this);
        if(distance > allowedDistance){
            System.out.println("Zu weit");
            return -1;
        }

        Point translationVector = new Point(moveVector.x*distance, moveVector.y*distance);
        FiguresUtils.updatePosition(this,figuresMap,translationVector);
        return 0;
    }

    @Override
    public String getShortcut() {
        return "D";
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
}
