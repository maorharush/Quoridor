package com.harush.zitoon.quoridor.ui.model;

import com.harush.zitoon.quoridor.core.logic.Pawn;

/**
 * 
 * A very simple AI player.
 *
 */
public class DumbAIPlayer extends Player implements AIPlayer {

	public DumbAIPlayer(String name, Pawn pawn, String pawnColour) {
		super(name, pawn, pawnColour);
	}

	@Override
	public Tile determineMove(Tile tile, Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
