package org.chess.model;

import java.awt.Point;
import java.util.HashMap;


public interface Figures {
    int move(HashMap<String,Figures> figuresMap);

    Point getPosition();
    char getColour();
    String getShortcut();

    void setMayBeat(boolean setter);
    boolean isMayBeat();
}
