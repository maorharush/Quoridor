package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public class PathClearanceValidatorImpl implements PathClearanceValidator {

    public boolean opponentPathIsClear(GameSession copyOfGameSession) {

        for (Player player : copyOfGameSession.getPlayers()) {
            List<Coordinate> validMoves = player.pawn.getValidMoves();
            if (validMoves.isEmpty()) return false;
            for (Coordinate potentialMove : validMoves
            ) {
                LogicResult movement = player.pawn.move(potentialMove.getX(), potentialMove.getY());
                if (movement.isSuccess()) {
                    player.pawn.setCurrentCoordinate(new Coordinate(potentialMove.getX(), potentialMove.getY()));
                }
                if (copyOfGameSession.checkForWinner()) {
                    return true;
                } else {
                    opponentPathIsClear(copyOfGameSession);
                }

            }
        }


        return true;
    }
}