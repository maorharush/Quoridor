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

        System.out.println("I am dumb AI... `Thinking`...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done thinking, now moving...");



        LogicResult logicResult;
        do {
            logicResult = pawn.move(pawn.getX(), pawn.getY() + 1);
            if (!logicResult.isSuccess()) {
                logicResult = pawn.move(pawn.getX() + 1, pawn.getY());
            }
        } while(!logicResult.isSuccess());
    }

}
