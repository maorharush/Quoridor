package com.harush.zitoon.quoridor.core.model;

import java.util.Random;

/**
 * A very simple AI player.
 */
public class DumbAIPlayer extends Player {

    public DumbAIPlayer(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        super(name, pawn, verticalWalls, horizontalWalls);
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

        boolean shouldMovePawn = randomBoolean();

        if (shouldMovePawn) {
            System.out.println("Moving pawn");
            pawn.move(pawn.getX(), pawn.getY() + 1);
        } else {

            Random random = new Random();
            boolean shouldPlaceVertical = randomBoolean();
            if (shouldPlaceVertical) {
                System.out.println("Placing vertical wall");
                int randX = random.nextInt(verticalWalls.length);
                int randY = random.nextInt(verticalWalls[0].length);
                verticalWalls[randX][randY].placeWall();
            } else {
                System.out.println("Placing horizontal wall");
                int randX = random.nextInt(horizontalWalls.length);
                int randY = random.nextInt(horizontalWalls[0].length);
                horizontalWalls[randX][randY].placeWall();
            }
        }
    }

    private boolean randomBoolean() {
        Random random = new Random();
        int randNum = random.nextInt(100);
        return randNum % 2 == 0;
    }

}
