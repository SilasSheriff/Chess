package org.chess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.chess.util.FiguresUtils;
import org.chess.util.PawnUtils;

import java.awt.*;
import java.util.HashMap;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Pawn implements Figures {

    private final Point position;
    private boolean mayBeat = false;
    private final char colour;
    private final int baseline;

    public Pawn(Point position, char colour){
        this.colour = colour;
        this.position = position;
        baseline = position.y;
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        String input = PawnUtils.askDirectionInput();

        Point moveVector = PawnUtils.handlePawnMove(this, input);

        this.setMayBeat(false);

        int allowedDistance = PawnUtils.checkObstaclePawn(figuresMap,this,moveVector);


        if (allowedDistance == 0) {
            System.out.println("Der Weg ist blockiert.");
            return -1;
        }

        FiguresUtils.updatePosition(this, figuresMap, moveVector);
        return 0;   }

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
}
