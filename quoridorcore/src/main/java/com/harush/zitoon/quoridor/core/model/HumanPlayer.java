package com.harush.zitoon.quoridor.core.model;

/**
 * This class creates a new Human Player object.
 * @author Moar Harush
 * @version 0.3
 * Last implemented: 19/11/2015
 */


public class HumanPlayer extends Player {

	public HumanPlayer(String name, Pawn pawn, String pawnColour) {
		super(name, pawn, pawnColour);
	}

	@Override
	public void play() {
		System.out.println(String.format("Waiting for human player %s's input...", getName()));
	}

}
