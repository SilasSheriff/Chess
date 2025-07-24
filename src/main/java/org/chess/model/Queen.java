package org.chess.model;

import org.chess.util.BishopUtils;
import org.chess.util.PieceUtils;
import org.chess.util.PointUtils;
import org.chess.util.QueenUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Queen implements Pieces {
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
    public int move(HashMap<String, Pieces> piecesMap) {
        mayBeat = false;
        HashMap<String,String> directionDistanceMap = QueenUtils.checkPossibleMove(this, piecesMap);
        if (directionDistanceMap.isEmpty()){
            return -1;
        }
        String input = QueenUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = PieceUtils.mapInputToVector(input,directionMap);

        if (moveVector == null){
            System.out.println("ungültige Richtung");
            return -1;
        }
        this.setMayBeat(false);


        ArrayList<Integer> allowedDistance = QueenUtils.calculateAllowedDistance( piecesMap,this,moveVector);
        if(allowedDistance.isEmpty()){
            System.out.println("Ungültiger Zug");
            return -1;
        }
        int distance = QueenUtils.askDistanceInput(allowedDistance);
        if(!allowedDistance.contains(distance)){
            System.out.println("Ungültige Distanz");
            return -1;
        }

        Point translationVector = new Point(moveVector.x*distance, moveVector.y*distance);
        PieceUtils.updatePosition(this, piecesMap,translationVector);
        return 0;
    }

    @Override
    public boolean movePossible(HashMap<String,Pieces> piecesMap){
        ArrayList<Point> dir = PointUtils.straightAndDiagonal();
        for (Point p : dir){
            ArrayList<Integer> allowed = QueenUtils.calculateAllowedDistance(piecesMap,this,p);
            if(allowed != null && !allowed.isEmpty()){
                return true;
            }
        }
        return false;
    }

    @Override
    public Queen clone(){
            Point clonedPosition = new Point(this.position.x,this.position.y);
            Queen clonedQueen = new Queen(clonedPosition,this.colour);
            clonedQueen.mayBeat = this.mayBeat;

            return clonedQueen;
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
