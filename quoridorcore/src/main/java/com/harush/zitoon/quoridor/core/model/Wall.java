package com.harush.zitoon.quoridor.core.model;

import java.util.Objects;

/**
 * Represents a wall in the game.
 * @author Moar Harush
 * @version 0.2
 */
public class Wall {
	
	private int x;
	private int y;
	private boolean isFirst;
	private Player placedBy;
	private char orientation;

	public char getOrientation() {
		return orientation;
	}

	public void setOrientation(char orientation) {
		this.orientation = orientation;
	}


	public Wall(int x, int y, boolean isFirst, Player placedBy) {
		this.x = x;
		this.y = y;
		this.isFirst = isFirst;
		this.placedBy = placedBy;
	}
	
	/**
	 * Gets the starting position of the {@link Wall}.
	 * @return the starting position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the ending position of the {@link Wall}.
	 * @return the ending position
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets whether this is the first part of the {@link Wall}.
	 * @return whether this is the first part
	 */
	public boolean getIsFirst() {
		return isFirst;
	}
	
	/**
	 * Gets the {@link Player} who placed the wall.
	 * @return the player who placed the wall
	 */
	public Player getPlacedBy() {
		return placedBy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Wall wall = (Wall) o;
		return x == wall.x &&
				y == wall.y &&
				isFirst == wall.isFirst &&
				orientation == wall.orientation &&
				Objects.equals(placedBy, wall.placedBy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, isFirst, placedBy, orientation);
	}
}
