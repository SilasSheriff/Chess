package org.chess.model;

import java.awt.Point;
import java.util.HashMap;


/**
 * Dieses Interface stellt die Grundlage für die Figureneigenschaften.
 */
public interface Pieces{
    // Bewegungseigenschaften
    int move(HashMap<String, Pieces> piecesMap);
    // Überprüft, ob die Bewegung möglich ist
    boolean movePossible(HashMap<String, Pieces> piecesMap);
    // erfragt die Position
    Point getPosition();
    // erfragt die Farbe (schwarz/weiß)
    char getColour();
    // erfragt das Figurenkürzel
    String getShortcut();

    boolean isMayBeat();
    HashMap<String, Point> getDirectionMap();
    Pieces clone();

    void setMayBeat(boolean mayBeat);
}
