package org.chess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.chess.enums.Direction;
import org.chess.util.FiguresUtils;
import org.chess.util.KingUtils;

import java.awt.*;
import java.util.HashMap;

@Data
@Getter
@Setter
@AllArgsConstructor
public class King implements Figures {
    private Point position;
    private boolean mayBeat = false;
    private boolean wasMoved = false;
    private final char colour;

    public King(Point position, char colour){

        this.colour = colour;
        this.position = position;
    }

    @Override
    public int move(HashMap<String, Figures> figuresMap) {
        String input = KingUtils.askDirectionInput();
        Direction direction = Direction.fromInput(input);

        if (direction == null) {
            System.out.println("Ungültige Richtung.");
            return -1;
        }

        Point moveVector = direction.getVector();
        this.setMayBeat(false);

        if (!KingUtils.isPathFree(figuresMap, this, moveVector)) {
            System.out.println("Der Weg ist blockiert.");
            return -1;
        }

        FiguresUtils.updatePosition(this, figuresMap, moveVector);
        return 0;
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

}
