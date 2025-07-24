package org.chess.model;

import org.chess.util.BishopUtils;
import org.chess.util.PieceUtils;
import org.chess.util.PointUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Bishop implements Pieces {
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
    public int move(HashMap<String, Pieces> piecesMap) {

        HashMap<String,String> directionDistanceMap = BishopUtils.checkPossibleMove(piecesMap,this);
        if (directionDistanceMap.isEmpty()){
            return -1;
        }
        String input = BishopUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = PieceUtils.mapInputToVector(input,directionMap);
        if (moveVector == null){
            System.out.println("ungültige Richtung");
            return -1;
        }

        ArrayList<Integer> allowedDistances = BishopUtils.calculateAllowedDistances(piecesMap,this, moveVector);
        if (allowedDistances.isEmpty()){
            return -1;
        }

        int distance = BishopUtils.askDistanceInput(allowedDistances);
        if (!allowedDistances.contains(distance)) {
            System.out.println("Unzulässige Distanz");
            this.setMayBeat(false);
            return -1;
        }
        Point translationVector = new Point(moveVector.x *distance,moveVector.y*distance);
        PieceUtils.updatePosition(this, piecesMap,translationVector);
        this.setMayBeat(false);
        return 0;
    }

    @Override
    public boolean movePossible(HashMap<String,Pieces> piecesMap){
        ArrayList<Point> dir = PointUtils.diagonal();
        for (Point p : dir){
            ArrayList<Integer> allowed = BishopUtils.calculateAllowedDistances(piecesMap,this,p);
            if(allowed != null && !allowed.isEmpty()){
                return true;
            }
        }
        return false;
    }


    public Bishop clone(){
        Point clonedPosition = new Point(this.position.x,this.position.y);
        Bishop clonedBishop = new Bishop(clonedPosition,this.colour);
        clonedBishop.mayBeat = this.mayBeat;

        return clonedBishop;
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
