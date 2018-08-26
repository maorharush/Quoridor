package com.harush.zitoon.quoridor.core.model;

/**
 * A very simple AI player.
 */
public class DumbAIPlayer extends Player {

    public DumbAIPlayer(String name, Pawn pawn, String pawnColour) {
        super(name, pawn, pawnColour);
    }

    @Override
    public void play() {
        System.out.println("RANDOMLY SELECTING MOVE!!!! I AM DUMB AHHHHH");

        LogicResult logicResult;
        do {
            logicResult = pawn.move(pawn.getX(), pawn.getY() + 1);
            if (!logicResult.isSuccess()) {
                logicResult = pawn.move(pawn.getX() + 1, pawn.getY());
            }
        } while(!logicResult.isSuccess());
    }

}
