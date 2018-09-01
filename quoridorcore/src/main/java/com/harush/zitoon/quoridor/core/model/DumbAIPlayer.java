package com.harush.zitoon.quoridor.core.model;

import java.util.Random;

/**
 * A very simple AI player.
 */
public class DumbAIPlayer extends Player {

    public DumbAIPlayer(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls, String pawnColour) {
        super(name, pawn, verticalWalls, horizontalWalls, pawnColour);
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

        boolean shouldMovePawn = decideMovePawnRandomly();

        if (shouldMovePawn) {
            System.out.println("Moving pawn");
            pawn.move(pawn.getX(), pawn.getY() + 1);
        } else {
            Random random = new Random();
            final int width = random.nextInt(verticalWalls.length);
            final int height = random.nextInt(verticalWalls[0].length);

            int randX = random.nextInt(width);
            int randY = random.nextInt(height);

            verticalWalls[randX][randY].placeWall();
        }
    }

    private boolean decideMovePawnRandomly() {
        Random random = new Random();
        int randNum = random.nextInt(100);
        return randNum % 2 == 0;
    }

}
