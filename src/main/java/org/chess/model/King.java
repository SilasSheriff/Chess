package org.chess.model;

import org.chess.util.PieceUtils;
import org.chess.util.KingUtils;
import org.chess.util.PointUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class King implements Pieces {
    private final Point position;
    private boolean mayBeat = false;
    private boolean wasMoved = false;
    private final char colour;
    private boolean isInCheck = false;
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
    public int move(HashMap<String, Pieces> piecesMap) {
        HashMap<String,String> directionDistanceMap = KingUtils.checkPossibleMove(this, piecesMap);
        if (directionDistanceMap.isEmpty()){
            return -1;
        }
        String input = KingUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = PieceUtils.mapInputToVector(input, directionMap);
        if (moveVector == null) {
            System.out.println("Ungültige Richtung.");
            return -1;
        }


        this.setMayBeat(false);

        if (!KingUtils.isLegalMove(piecesMap, this, moveVector)) {
            System.out.println("Der Weg ist blockiert.");
            return -1;
        }

        PieceUtils.updatePosition(this, piecesMap, moveVector);
        return 0;
    }

    @Override
    public boolean movePossible(HashMap<String,Pieces> piecesMap){
        ArrayList<Point> dir = PointUtils.straightAndDiagonal();
        for (Point p : dir){
            if(KingUtils.isLegalMove(piecesMap,this,p)){
                return true;
            }
        }
        return false;
    }

    public King clone(){
        Point clonedPosition = new Point(this.position.x,this.position.y);
        King clonedKing = new King(clonedPosition,this.colour);
        clonedKing.mayBeat = this.mayBeat;
        clonedKing.wasMoved = this.wasMoved;
        clonedKing.isInCheck = this.isInCheck;
        return clonedKing;
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

    public boolean isInCheck() {
        return isInCheck;
    }

    public void setInCheck(boolean inCheck) {
        isInCheck = inCheck;
    }
}
