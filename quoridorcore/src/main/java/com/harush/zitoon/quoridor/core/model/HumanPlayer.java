package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.theirs.Human;

/**
 * This class creates a new Human Player object.
 * @author Moar Harush
 * @version 0.3
 * Last implemented: 01/09/2018
 */


public class HumanPlayer extends Player {

	public HumanPlayer(String name, Pawn pawn) {
		super(name, pawn, false);
	}

	@Override
	public void play() {
		System.out.println(String.format("Waiting for human player %s's input...", getName()));
	}

	@Override
	public String toString() {
		return "HumanPlayer{}";
	}
}
