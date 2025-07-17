package org.chess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.chess.enums.KnightDirection;
import org.chess.util.FiguresUtils;
import org.chess.util.KnightUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Knight implements Figures{
    private Point position;
    private boolean mayBeat = false;
    private final char colour;

    public Knight(Point position, char colour){
        this.colour = colour;
        this.position = position;
    }

    @Override
    public int move(HashMap<String,Figures> figuresMap) {
        String input = KnightUtils.askDirectionInput();
        KnightDirection direction = KnightDirection.fromInput(input);
    if (direction == null){
        System.out.println("Ungültige Richtung");
        return -1;
    }
    Point moveVector = direction.getVector();

    boolean allowedMove = KnightUtils.checkObstacleKnight(figuresMap,this,moveVector);
    if(!allowedMove){
        System.out.println("Weg versperrt");
        return -1;
    }
    FiguresUtils.updatePosition(this,figuresMap,moveVector);
    return 0;
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
    public boolean isMayBeat() {
        return mayBeat;
    }

    @Override
    public void setMayBeat(boolean mayBeat) {
        this.mayBeat = mayBeat;
    }
}
