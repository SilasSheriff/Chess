package org.chess.util;

import org.chess.model.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KingUtils {

    public static String askDirectionInput(HashMap<String, String> directionDistanceMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("In welche Richtung soll der König sich bewegen? ");
        for (Map.Entry<String, String> entry : directionDistanceMap.entrySet()) {
            System.out.print("<" + entry.getKey() + "> " + entry.getValue() + ", ");
        }
        return scanner.nextLine().trim().toLowerCase();
    }

    public static boolean isLegalMove(HashMap<String, Pieces> piecesMap, King king, Point moveVector) {
        ArrayList<Integer> result = PieceUtils.checkObstacle(piecesMap, king, moveVector);
        if (result == null) return false;

        boolean noNeighbouringKing = inhibitKingsAsNeighbours(king, piecesMap, moveVector);
        return !result.isEmpty() && noNeighbouringKing;
    }

    public static HashMap<String, String> checkPossibleMove(King king, HashMap<String, Pieces> piecesMap) {
        HashMap<String, String> directionDistanceMap = new HashMap<>();
        for (Map.Entry<String, Point> entry : king.getDirectionMap().entrySet()) {
            boolean allowedMove = isLegalMove(piecesMap, king, entry.getValue());
            if (allowedMove) {
                directionDistanceMap.put(entry.getKey(), "(Schlagen: " + king.isMayBeat() + ")");
            }
        }
        if (directionDistanceMap.isEmpty()) {
            System.out.println("Der König kann nicht bewegt werden");
        }
        return directionDistanceMap;
    }

    public static King getKingPosition(HashMap<String, Pieces> piecesMap, char colour) {
        for (Pieces figure : piecesMap.values()) {
            if (figure instanceof King && figure.getColour() == colour) {
                return (King) figure;
            }
        }
        return null;
    }

    private static boolean inhibitKingsAsNeighbours(King king, HashMap<String, Pieces> piecesMap, Point direction){
        ArrayList<Point> neighbourPoints = PointUtils.straightAndDiagonal();
        Point targetPoint = PointUtils.add(king.getPosition(),direction);
        for (Point p : neighbourPoints){
            String coordinate = (targetPoint.x + p.x) + "," + (targetPoint.y + p.y);
            if (piecesMap.get(coordinate) instanceof King && piecesMap.get(coordinate).getColour() != king.getColour()){
                return false;
            }
        }
        return true;
    }


    public static ArrayList<Point> threatDirections(King king, HashMap<String,Pieces> piecesMap){
        ArrayList<Point> threatDirections = new ArrayList<>();
        boolean threats;
        // Überprüfe, ob eine Figur mit diagonaler oder gerader Bewegung den König Schach stellt.
        for (Point dir : PointUtils.straightAndDiagonal()){
             threats = checkForRookBishopQueenCheck(king,piecesMap,dir);
             if(threats){
                 threatDirections.add(dir);
             }
        }
        // Überprüfe, ob der König durch einen Springer im Schach steht.
        for (Point dir : PointUtils.knightsDirections()){
            threats = checkForKnightCheck(king,piecesMap,dir);
            if (threats){
                threatDirections.add(dir);
            }
        }

        threatDirections.addAll(checkForPawnCheck(king,piecesMap));

        return threatDirections;
    }

    private static boolean checkForRookBishopQueenCheck(King king, HashMap<String, Pieces> piecesMap, Point direction) {
        String coordinate;
        Point positionIncrement = new Point(king.getPosition());
        boolean isDiagonal = Math.abs(direction.x) == Math.abs(direction.y);

        for(int i = 0; i < 8; i++) {
            positionIncrement = PointUtils.add(positionIncrement,direction);
            coordinate = positionIncrement.x + "," + positionIncrement.y;

            // Spielfeldrand
            if(PointUtils.checkOutsideBoardEdge(positionIncrement)){
                return false;
            }
            Pieces piece = piecesMap.get(coordinate);
            // keine Figur
            if(piece == null){
                continue;
            }
            // Eigene Figur
            if(piece.getColour() == king.getColour()) {
                return false;
            }
            // Potenziell gefährliche gegnerische Figur
            if(((piece instanceof Bishop && isDiagonal)
                     || (piece instanceof Rook && !isDiagonal)||
                    piece instanceof Queen)) {
                return true;
            }
            // Ungefährliche gegnerische Figur
            if(piece.getColour() != king.getColour()){
                return false;
            }
        }
        return false; // nichts gefunden
    }

    private static boolean checkForKnightCheck(King king, HashMap<String, Pieces> piecesMap, Point direction) {
        String coordinate;
        Point targetPosition = king.getPosition();

            targetPosition = PointUtils.add(targetPosition,direction);
            coordinate = targetPosition.x + "," + targetPosition.y;
            if(PointUtils.checkOutsideBoardEdge(targetPosition)){
                return false;
            }
        return piecesMap.get(coordinate) instanceof Knight && piecesMap.get(coordinate).getColour() != king.getColour();
    }

    private static ArrayList<Point> checkForPawnCheck(King king, HashMap<String, Pieces> piecesMap) {
        int y = (king.getColour() == 'w') ? -1:1;
        ArrayList<Point> threatDir = new ArrayList<>();
        Point west = PointUtils.add(new Point(-1,y),king.getPosition());
        Point east = PointUtils.add(new Point(1,y),king.getPosition());
        String westCoordinate = west.x + "," + west.y;
        String eastCoordinate = east.x + "," + east.y;

        if(piecesMap.get(westCoordinate) instanceof Pawn && piecesMap.get(westCoordinate).getColour() != king.getColour()){
            threatDir.add(west);
        }
        if(piecesMap.get(eastCoordinate) instanceof Pawn && piecesMap.get(eastCoordinate).getColour() != king.getColour()){
            threatDir.add(east);
        }
        return threatDir;
    }

}