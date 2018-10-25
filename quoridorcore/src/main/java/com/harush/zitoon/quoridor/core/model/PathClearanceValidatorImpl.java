package com.harush.zitoon.quoridor.core.model;

import com.rits.cloning.Cloner;

import java.util.List;
import java.util.stream.Collectors;

public class PathClearanceValidatorImpl implements PathClearanceValidator {

    private WinnerDecider winnerDecider;

    private Cloner cloner;
    private final OurCloner ourCloner;

    PathClearanceValidatorImpl(WinnerDecider winnerDecider) {
        this.winnerDecider = winnerDecider;
        this.cloner = new Cloner();
        this.ourCloner = new OurClonerImpl();
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
            PawnLogic pawnLogic= ourCloner.clone(board,player.getPawn());
            //pawnLogic.setBoard(board);

            Player potentialMovePlayer = ourCloner.clone(player);
            Board potentialMoveBoard = ourCloner.clone(board);
            Pawn potentialMovePawn = pawnLogic;
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