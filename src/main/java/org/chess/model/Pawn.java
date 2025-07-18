package org.chess.model;

import org.chess.util.FiguresUtils;
import org.chess.util.PawnUtils;

import java.awt.*;
import java.util.HashMap;

public class Pawn implements Figures {

    private final Point position;
    private boolean mayBeat = false;
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
    public int move(HashMap<String,Figures> figuresMap) {
        HashMap<String,String> directionDistanceMap = PawnUtils.checkPossibleMove(this, figuresMap);
        String input = PawnUtils.askDirectionInput(directionDistanceMap);

        Point moveVector = PawnUtils.handlePawnMove(this, input);

        this.setMayBeat(false);

        int allowedDistance = PawnUtils.checkObstaclePawn(figuresMap,this,moveVector);

        if (allowedDistance == 0) {
            System.out.println("Der Weg ist blockiert.");
            return -1;
        }

        FiguresUtils.updatePosition(this, figuresMap, moveVector);
        return 0;
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
}
