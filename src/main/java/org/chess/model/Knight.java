package org.chess.model;

import org.chess.util.FiguresUtils;
import org.chess.util.KnightUtils;

import java.awt.*;
import java.util.HashMap;


public class Knight implements Figures{
    private final Point position;
    private boolean mayBeat = false;
    private final char colour;
    private final HashMap<String,Point> directionMap = new HashMap<>();

    public Knight(Point position, char colour){
        this.colour = colour;
        this.position = position;
        directionMap.put("nno",new Point(1,-2));
        directionMap.put("noo",new Point(2,-1));
        directionMap.put("sso",new Point(2,1));
        directionMap.put("soo",new Point(1,2));
        directionMap.put("nnw",new Point(-1,-2));
        directionMap.put("nww",new Point(-2,-1));
        directionMap.put("sww",new Point(-2,1));
        directionMap.put("ssw",new Point(-1,2));
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = KnightUtils.checkPossibleMove(this,figuresMap);
        String input = KnightUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = FiguresUtils.mapInputToVector(input,directionMap);
        if (moveVector == null){
        System.out.println("Ungültige Richtung");
        return -1;
    }

    boolean allowedMove = KnightUtils.checkObstacleKnight(figuresMap,this,moveVector);
    if(!allowedMove){
        System.out.println("Weg versperrt");
        return -1;
    }
    FiguresUtils.updatePosition(this,figuresMap,moveVector);
    return 0;
    }


    @Override
    public String getShortcut() {
        return "S";
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
    public HashMap<String, Point> getDirectionMap() {
        return directionMap;
    }

    @Override
    public boolean isMayBeat() {
        return mayBeat;
    }

    @Override
    public void setMayBeat(boolean mayBeat) {
        this.mayBeat = mayBeat;
    }
}
