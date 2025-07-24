package org.chess;


import org.chess.model.Pieces;
import org.chess.model.King;
import org.chess.util.BoardUtils;
import org.chess.util.KingUtils;
import org.chess.util.PointUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.chess.util.BoardUtils.chooseFigureToMove;

public class Game {
    private String activePlayer;
    private HashMap<String, Pieces> piecesMap;

    Game(HashMap<String, Pieces> piecesMap) {
        this.piecesMap = piecesMap;
        activePlayer = "Weiss";

    }

    // Abspielen eines Spiels
    public void playGame(){
        String end = "N";

        Scanner scanner = new Scanner(System.in);
        BoardUtils.displayBoard(piecesMap);
        while (!end.equalsIgnoreCase("j")){

            handleCheck();

            int gameStatus = handleCheckMateAndStalemate();
            if(gameStatus == 1){
                System.out.println("Schachmatt " + activePlayer);
                end = "j";
                continue;
            } else if (gameStatus == 2) {
                System.out.println("Patt");
                end = "j";
                continue;
            }
            displayCheck();
            HashMap<String,Pieces> mapThisTurn = BoardUtils.copyPiecesMap(piecesMap);

            gameStatus = chooseFigureToMove(piecesMap, activePlayer);


            if (gameStatus == -1) {
                continue;
            }
            gameStatus = handleCheckEndOfTurn();
            if (gameStatus == -1) {
                System.out.println("Verlassen Sie mit Ihrem König die Schach Position");
                piecesMap = mapThisTurn;
                BoardUtils.displayBoard(piecesMap);
                continue;
            }
            BoardUtils.displayBoard(piecesMap);

            System.out.println("Möchten Sie das Spiel beenden (J/N)");
            end = scanner.nextLine().trim().toLowerCase();
            activePlayer = activePlayer.equals("Weiss") ? "Schwarz" : "Weiss";
        }
    }

    // Überprüfe, ob der König des aktiven Spielers Schach steht
    private void handleCheck() {
        King king = KingUtils.getKingPosition(piecesMap, activePlayer.toLowerCase().charAt(0));
        ArrayList<Point> threats = KingUtils.threatDirections(king, piecesMap);
        boolean isInCheck = !threats.isEmpty();

        if (king != null){
            king.setInCheck(isInCheck);
        }

    }

    private void displayCheck(){
        char activeColour = activePlayer.toLowerCase().charAt(0);
        King king = KingUtils.getKingPosition(piecesMap,activeColour);
        if(king == null) return;
        boolean isCheck = king.isInCheck();
        if (!isCheck)return;
        String player = (activeColour == 'w') ? "Weiß" : "Schwarz";
        System.out.println(player + " steht im Schach");
        }


    // Überprüft ein Schachmatt und Patt
    private int handleCheckMateAndStalemate(){
        char activeColour = activePlayer.toLowerCase().charAt(0);
        King king = KingUtils.getKingPosition(piecesMap,activeColour);
        if(king == null) return 0;
        boolean isCheck = king.isInCheck();
        // Gehe alle Figuren durch und überprüfe alle Optionen
        for(Map.Entry<String,Pieces> entry : piecesMap.entrySet()){
            if(entry.getValue().getColour() == activeColour){
                boolean isNotStalemate = entry.getValue().movePossible(piecesMap);
                if (isNotStalemate)
                    return 0;
            }
        }
        // Wenn Schachmatt, dann 1, bei Patt 2
        return (isCheck) ? 1 : 2;
    }


    // Überprüfe am Ende des Zuges, ob der eigene König im Schach steht
    private int handleCheckEndOfTurn(){
        King king = KingUtils.getKingPosition(piecesMap,activePlayer.toLowerCase().charAt(0));
        if (king == null) return -1;

        handleCheck();
        String player = activePlayer.toLowerCase();
        if (player.equals("weiss") && king.isInCheck()){
            return -1;
        }
        if (player.equals("schwarz") && king.isInCheck()){
            return -1;
        }
        return 0;
    }


    public HashMap<String, Pieces> getPiecesMap() {
        return piecesMap;
    }

    public void setPiecesMap(HashMap<String, Pieces> piecesMap) {
        this.piecesMap = piecesMap;
    }

}
