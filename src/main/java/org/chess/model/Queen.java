package org.chess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.chess.enums.Direction;
import org.chess.util.FiguresUtils;
import org.chess.util.QueenUtils;

import java.awt.*;
import java.util.HashMap;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Queen implements Figures{
    private boolean mayBeat = false;
    private final Point position;
    private final char colour;

    public Queen(Point position, char colour){
        this.colour = colour;
        this.position = position;
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        String input = QueenUtils.askDirectionInput();
        Direction direction = Direction.fromInput(input);

        if (direction == null){
            System.out.println("ungültige Richtung");
            return -1;
        }
        this.setMayBeat(false);

        Point moveVector = direction.getVector();
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
}
