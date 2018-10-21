package com.harush.zitoon.quoridor.core.model;

import com.rits.cloning.Cloner;

import java.util.List;

public class WallMoveValidatorImpl implements WallMoveValidator {
    private GameSession gameSession;
    private PathClearanceValidator pathClearanceValidator;
    private List<Player> players;


    private WallMoveValidatorImpl(GameSession gameSession, PathClearanceValidator pathClearanceValidator) {
        this.gameSession = gameSession;
        this.pathClearanceValidator = pathClearanceValidator;
    }

    public boolean isEnemyPathBlockedAfterWallMove(int wallPlaceInX, int wallPlacedInY, boolean isHorizontal, boolean isFirst) {
        Cloner clone = new Cloner();
        Board copyOfCurrentBoard = clone.deepClone(gameSession.getBoard());
        GameSession copyOfGamesSession = clone.deepClone(gameSession);//maybe place in the constructor?
        copyOfCurrentBoard.setWall(wallPlaceInX, wallPlacedInY, isHorizontal, isFirst, gameSession.getCurrentPlayer());
        players = gameSession.getPlayers();


        for (Player player:players) {
            Coordinate opponentCoordinate = player.pawn.getCurrentCoordinate();
            if (!pathClearanceValidator.opponentPathIsClear(copyOfGamesSession) ){
                return false;
            }
        }

        return true;
    }
}
