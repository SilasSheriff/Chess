package org.chess.model;

import org.chess.util.PieceUtils;
import org.chess.util.KnightUtils;
import org.chess.util.PointUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Knight implements Pieces {
    private final Point position;
    private boolean mayBeat = false;
    private final char colour;
    private final HashMap<String,Point> directionMap = new HashMap<>();

    public Knight(Point position, char colour){
        this.colour = colour;
        this.position = position;
        directionMap.put("nno",new Point(1,-2));
        directionMap.put("noo",new Point(2,-1));
        directionMap.put("soo",new Point(2,1));
        directionMap.put("sso",new Point(1,2));
        directionMap.put("nnw",new Point(-1,-2));
        directionMap.put("nww",new Point(-2,-1));
        directionMap.put("sww",new Point(-2,1));
        directionMap.put("ssw",new Point(-1,2));
    }

    @Override
    public int move(HashMap<String, Pieces> piecesMap) {
        mayBeat = false;
        HashMap<String,String> directionDistanceMap = KnightUtils.checkPossibleMove(this, piecesMap);
        if (directionDistanceMap.isEmpty()){
            return -1;
        }
        String input = KnightUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = PieceUtils.mapInputToVector(input,directionMap);
        if (moveVector == null){
        System.out.println("Ungültige Richtung");
        return -1;
    }

    boolean allowedMove = KnightUtils.checkObstacleKnight(piecesMap,this,moveVector);
    if(!allowedMove){
        System.out.println("Weg versperrt");
        return -1;
    }
    PieceUtils.updatePosition(this, piecesMap,moveVector);
    return 0;
    }

    @Override
    public boolean movePossible(HashMap<String,Pieces> piecesMap){
        ArrayList<Point> dir = PointUtils.knightsDirections();
        for (Point p : dir){
            if(KnightUtils.checkObstacleKnight(piecesMap,this,p)){
                return true;
            }
        }
        return false;
    }

    public Knight clone(){
        Point clonedPosition = new Point(this.position.x,this.position.y);
        Knight clonedKnight = new Knight(clonedPosition,this.colour);
        clonedKnight.mayBeat = this.mayBeat;

        return clonedKnight;
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
