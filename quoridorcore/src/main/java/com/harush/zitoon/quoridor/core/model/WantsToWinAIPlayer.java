package com.harush.zitoon.quoridor.core.model;

/**
 * A very simple AI player.
 */
public class WantsToWinAIPlayer extends Player {

    public WantsToWinAIPlayer(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        super(name, pawn, verticalWalls, horizontalWalls, true);
    }

    @Override
    public void play() {

        System.out.println("I am wants to win AI... `Thinking`...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done thinking, now moving...");

        System.out.println("Moving pawn");
        pawn.move(pawn.getX(), pawn.getY() + 1);

    }
}
