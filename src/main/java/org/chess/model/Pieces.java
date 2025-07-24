package org.chess.model;

import java.awt.Point;
import java.util.HashMap;


public interface Pieces{
    int move(HashMap<String, Pieces> piecesMap);
    boolean movePossible(HashMap<String, Pieces> piecesMap);
    Point getPosition();
    char getColour();
    String getShortcut();
    boolean isMayBeat();
    HashMap<String, Point> getDirectionMap();
    Pieces clone();

    void setMayBeat(boolean mayBeat);
}
