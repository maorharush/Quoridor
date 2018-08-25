package com.harush.zitoon.quoridor.core.model;

import java.util.Random;

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
        pawn.move(4, pawn.getY() + 1);
    }

}
