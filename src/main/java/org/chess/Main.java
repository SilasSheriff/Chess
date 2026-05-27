package org.chess;

import org.chess.model.*;
import org.chess.util.BoardUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
        public void run() {
            ArrayList<Pieces> figures = BoardUtils.implementStandardChessBoard();
            HashMap<String, Pieces> piecesMap = BoardUtils.mapFiguresToString(figures);

            Game firstGame = new Game(piecesMap);

            firstGame.playGame();
        }


    public static void main(String[] args) {
        new Main().run();
    }
}