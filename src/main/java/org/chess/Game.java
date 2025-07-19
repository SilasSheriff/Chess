package org.chess;


import org.chess.model.Figures;
import org.chess.model.King;
import org.chess.util.BoardUtils;
import org.chess.util.KingUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import static org.chess.util.BoardUtils.chooseFigureToMove;

public class Game {
    private String activePlayer;
    private HashMap<String, Figures> figuresMap;
    private boolean checkWhite = false;
    private boolean checkBlack = false;

    Game(HashMap<String, Figures> figuresMap) {
        this.figuresMap = figuresMap;
        activePlayer = "Weiss";
    }

    public void playGame(HashMap<String, Figures> positionMap){
        String end = "N";
        Scanner scanner = new Scanner(System.in);
        BoardUtils.displayBoard(positionMap);
        while (!end.equals("j")){
            int gameStatus = chooseFigureToMove(positionMap, activePlayer);
            BoardUtils.displayBoard(positionMap);
            if (gameStatus == -1) {
                continue;
            }
            System.out.println("Möchten Sie das Spiel beenden (J/N)");
            end = scanner.nextLine().trim().toLowerCase();
            if(activePlayer.equals("Weiss")){
                activePlayer = "Schwarz";
            }
            else{
                activePlayer = "Weiss";
            }
        }
    }

    private void handleCheck(){
        King king = KingUtils.geKingPosition(figuresMap,activePlayer.toLowerCase().charAt(0));
        Point kingPos = Objects.requireNonNull(king).getPosition();
    }






    public HashMap<String, Figures> getFiguresMap() {
        return figuresMap;
    }

    public void setFiguresMap(HashMap<String, Figures> figuresMap) {
        this.figuresMap = figuresMap;
    }

    public boolean isCheckBlack() {
        return checkBlack;
    }

    public void setCheckBlack(boolean checkBlack) {
        this.checkBlack = checkBlack;
    }

    public boolean isCheckWhite() {
        return checkWhite;
    }

    public void setCheckWhite(boolean checkWhite) {
        this.checkWhite = checkWhite;
    }
}
