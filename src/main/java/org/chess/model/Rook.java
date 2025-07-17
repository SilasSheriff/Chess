package org.chess.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.chess.enums.Direction;
import org.chess.util.FiguresUtils;
import org.chess.util.RookUtils;

import java.awt.*;
import java.util.HashMap;
@Data
@Getter
@Setter
@AllArgsConstructor
public class Rook implements Figures{
    private final Point position;
    private boolean mayBeat = false;
    private final char colour;



    private boolean wasMoved = false;

    public Rook(Point position, char colour){
        this.colour = colour;
        this.position = position;
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        String input = RookUtils.askDirectionInput();
        Direction direction = Direction.fromInput(input);

        if (direction == null){
            System.out.println("ungültige Richtung");
            return -1;
        }
        Point moveVector = direction.getVector();
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
    public void setMayBeat(boolean setMayBeat) {
        this.mayBeat = setMayBeat;
    }


    @Override
    public Point getPosition() {
        return position;
    }


    @Override
    public char getColour() {
        return colour;
    }

}
