package org.chess;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.chess.model.Figures;
import org.chess.util.BoardUtils;

import java.util.HashMap;
import java.util.Scanner;

import static org.chess.util.BoardUtils.chooseFigureToMove;

@Data
@Getter
@Setter
public class Game {
    private String activePlayer;
    private HashMap<String, Figures> figuresMap;
    private int gameStatus = 0;

    Game(HashMap<String, Figures> figuresMap) {
        this.figuresMap = figuresMap;
        activePlayer = "Weiss";
    }

    public void playGame(HashMap<String, Figures> positionMap){
        String end = "N";
        Scanner scanner = new Scanner(System.in);
        BoardUtils.displayBoard(positionMap);
        while (!end.equals("j")){
            gameStatus = chooseFigureToMove(positionMap,activePlayer);
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
}
