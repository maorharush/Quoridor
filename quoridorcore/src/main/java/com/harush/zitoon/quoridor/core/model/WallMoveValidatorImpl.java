package com.harush.zitoon.quoridor.core.model;

import com.rits.cloning.Cloner;

import java.util.List;

public class WallMoveValidatorImpl implements WallMoveValidator {
    private GameSession gameSession;
    private PathClearanceValidator pathClearanceValidator;
    private List<Player> players;
    Board board;



    public WallMoveValidatorImpl(GameSession gameSession, PathClearanceValidator pathClearanceValidator) {
        this.gameSession = gameSession;
        this.pathClearanceValidator = pathClearanceValidator;
    }

    public boolean isEnemyPathBlockedAfterWallMove(int wallPlaceInX, int wallPlacedInY, boolean isHorizontal, boolean isFirst) {
        Cloner clone = new Cloner();
        board=clone.deepClone(gameSession.getBoard());//maybe place in the constructor?
        players=clone.deepClone(gameSession.getPlayers());//maybe place in the constructor?


        board.setWall(wallPlaceInX, wallPlacedInY, isHorizontal, isFirst, gameSession.getCurrentPlayer());
        players = gameSession.getPlayers();


        for (Player player:players) {

            if (!pathClearanceValidator.opponentPathIsClear(board,players) ){
                return false;
            }
        }

        return true;
    }
}
