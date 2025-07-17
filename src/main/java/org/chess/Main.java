package org.chess;

import org.chess.model.*;
import org.chess.util.BoardUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
        public void run() {
            ArrayList<Figures> figures = BoardUtils.implementStandardChessBoard();
            HashMap<String, Figures> figuresMap = BoardUtils.mapFiguresToString(figures);

            Game firstGame = new Game(figuresMap);

            firstGame.playGame(figuresMap);

        }


    public static void main(String[] args) {
        new Main().run();
    }
}