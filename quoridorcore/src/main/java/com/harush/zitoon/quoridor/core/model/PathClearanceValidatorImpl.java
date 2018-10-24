package com.harush.zitoon.quoridor.core.model;

import com.rits.cloning.Cloner;

import java.util.List;
import java.util.stream.Collectors;

public class PathClearanceValidatorImpl implements PathClearanceValidator {

    private WinnerDecider winnerDecider;

    private Cloner cloner;

    PathClearanceValidatorImpl(WinnerDecider winnerDecider) {
        this.winnerDecider = winnerDecider;
        this.cloner = new Cloner();
    }

    public boolean isPathClearToVictory(Board board, Player player, List<Coordinate> visitedCoordinates) {
        Pawn pawn = player.getPawn();
        visitedCoordinates.add(pawn.getCurrentCoordinate());

        List<Coordinate> validMoves = pawn.getValidMoves();
        List<Coordinate> validNonVisitedMoves = validMoves.stream().filter(coordinate -> !visitedCoordinates.contains(coordinate)).collect(Collectors.toList());

        if (validNonVisitedMoves.isEmpty()) {
            return false;
        }

        for (Coordinate potentialMove : validNonVisitedMoves) {
            Player potentialMovePlayer = cloner.deepClone(player);
            Board potentialMoveBoard = cloner.deepClone(board);
            Pawn potentialMovePawn = potentialMovePlayer.getPawn();
            potentialMovePawn.setBoard(potentialMoveBoard);

            potentialMovePawn.setCurrentCoordinate(new Coordinate(potentialMove.getX(), potentialMove.getY()));

            if (winnerDecider.isWinner(potentialMovePlayer)) {
                return true;
            }
            if (isPathClearToVictory(potentialMoveBoard, potentialMovePlayer,visitedCoordinates)) {
                return true;
            }
        }
        return false;
    }
}