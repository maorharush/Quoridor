package com.harush.zitoon.quoridor.core.model;

import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.List;

public class WallMoveValidatorImpl implements WallMoveValidator {
    private GameSession gameSession;
    private PathClearanceValidator pathClearanceValidator;
    private List<Player> players;
    private final OurCloner ourCloner;

    Board board;


    WallMoveValidatorImpl(GameSession gameSession, PathClearanceValidator pathClearanceValidator) {
        this.gameSession = gameSession;
        this.pathClearanceValidator = pathClearanceValidator;
        this.ourCloner=new OurClonerImpl();
    }

    public boolean isEnemyPathBlockedAfterWallMove(int wallPlaceInX, int wallPlacedInY, boolean isHorizontal, boolean isFirst) {
        Cloner cloner = new Cloner();
       // cloner.setDumpClonedClasses(true);
        players= gameSession.getPlayers();


        board = cloner.deepClone(gameSession.getBoard());//maybe place in the constructor?
       // players = cloner.deepClone(gameSession.getPlayers());//maybe place in the constructor?



        board.setWall(wallPlaceInX, wallPlacedInY, isHorizontal, isFirst, gameSession.getCurrentPlayer());


        for (Player player : players) {
            PawnLogic pawnLogic =ourCloner.clone(board,player.getPawn());
            //pawnLogic.setBoard(board);
            Player playerCopy = ourCloner.clone(player);
            playerCopy.pawn.setBoard(board);

            List<Coordinate> validMovesList=new ArrayList<>();
            validMovesList.clear();

            if (!pathClearanceValidator.isPathClearToVictory(board, player,validMovesList)) {
                System.out.println("to console:illegal well placement");
                return false;
            }
        }

        return true;
    }
}
