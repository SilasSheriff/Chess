package org.chess.model;

import org.chess.util.PieceUtils;
import org.chess.util.PawnUtils;

import java.awt.*;
import java.util.HashMap;

public class Pawn implements Pieces {

    private final Point position;
    private boolean mayBeat = false;
    private boolean enPassant = false;
    private final char colour;
    private final int baseline;
    private final HashMap<String,Point> directionMap = new HashMap<>();

    public Pawn(Point position, char colour){
        directionMap.put("v", new Point(0,-1));
        directionMap.put("2v", new Point(0,-2));
        directionMap.put("ao", new Point(1,-1));
        directionMap.put("aw", new Point(-1,-1));

        this.colour = colour;
        this.position = position;
        baseline = position.y;
    }

    @Override
    public int move(HashMap<String, Pieces> piecesMap) {
        mayBeat = false;
        HashMap<String,String> directionDistanceMap = PawnUtils.checkPossibleMove(this, piecesMap);
        if (directionDistanceMap.isEmpty()){
            return -1;
        }

        String input = PawnUtils.askDirectionInput(directionDistanceMap);
        Point moveVector = PawnUtils.handlePawnMove(this, input);
        // mayBeat auf false setzen
        mayBeat = false;

        int allowedDistance = PawnUtils.checkObstaclePawn(piecesMap,this,moveVector);

        if (allowedDistance == 0) {
            System.out.println("Der Weg ist blockiert.");
            return -1;
        }

        PieceUtils.updatePosition(this, piecesMap, moveVector);

        return PawnUtils.managePawnSwap(this,piecesMap);
    }

    @Override
    public boolean movePossible(HashMap<String, Pieces> piecesMap){
        HashMap<String,String> directionDistanceMap = PawnUtils.checkPossibleMove(this,piecesMap);
        return !directionDistanceMap.isEmpty();
    }

    @Override
    public Pawn clone(){
        Point clonedPosition = new Point(this.position.x,this.position.y);
        Pawn clonedPawn = new Pawn(clonedPosition,this.colour);
        clonedPawn.mayBeat = this.mayBeat;
        clonedPawn.enPassant = this.enPassant;
        return clonedPawn;
    }

    @Override
    public String getShortcut() {
        return "B";
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

    public int getBaseline() {
        return baseline;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }
}
