package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public class PathClearanceValidatorImpl implements PathClearanceValidator {
private WinnerDecider winnerDecider;
    public boolean opponentPathIsClear(Board board, List<Player> players) {

        for (Player player : players) {
            List<Coordinate> validMoves = player.pawn.getValidMoves();
            if (validMoves.isEmpty()) {
                return false;
            }
            for (Coordinate potentialMove : validMoves
            ) {
                LogicResult movement = player.pawn.move(potentialMove.getX(), potentialMove.getY());
                if (movement.isSuccess()) {
                    player.pawn.setCurrentCoordinate(new Coordinate(potentialMove.getX(), potentialMove.getY()));
                }


                if (winnerDecider.isWinner(player)) {
                    return true;
                } else {
                    return opponentPathIsClear(board,players);
                }

            }
        }


        return true;
    }
}