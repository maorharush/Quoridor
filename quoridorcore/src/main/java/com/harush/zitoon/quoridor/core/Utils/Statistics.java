package com.harush.zitoon.quoridor.core.Utils;

import com.harush.zitoon.quoridor.core.model.Wall;

/**
 * 
 * Represents various statistics within the game.
 *
 */
public class Statistics {
	
	private int numOfTotalMoves;
	private int numOfWallsUsed;
	
	public Statistics() {
		numOfTotalMoves = 0;
		numOfWallsUsed = 0;
	}

	public void setNumOfTotalMoves(int numOfTotalMoves) {
		this.numOfTotalMoves = numOfTotalMoves;
	}

	public void setNumOfWallsUsed(int numOfWallsUsed) {
		this.numOfWallsUsed = numOfWallsUsed;
	}

	/**
	 * Increments the total moves.
	 */
	public void incrementTotalMoves() {
		numOfTotalMoves++;
	}
	
	/**
	 * Returns the total moves. 
	 * @return the total moves
	 */
	public int getTotalMoves() {
		return numOfTotalMoves;
	}

	/**
	 * Gets the number of {@link Wall walls} used.
	 * @return the number of walls used
	 */
	public int getNumOfWallsUsed() {
		return numOfWallsUsed;
	}

	/**
	 * Increments the number of {@link Wall walls} used.
	 */
	public void incrementWallsUsed() {
		numOfWallsUsed++;
	}
	
}
