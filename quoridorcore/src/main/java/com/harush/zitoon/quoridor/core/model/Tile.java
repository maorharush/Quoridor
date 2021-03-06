package com.harush.zitoon.quoridor.core.model;

import java.util.Objects;

/**
 * Represents a Tile on the {@link Board}
 * @author Moar Harush
 * @version 0.3
 */
public class Tile {
	
	private int x;
	private int y;
	private boolean containsPawn;

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x position of this tile
	 * @return the x position of the tile
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y position of this tile
	 * @return the y position of the tile
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets whether this tile contains a pawn.
	 * @param contains whether the tile contains a pawn
	 */
	public void setContainsPawn(boolean contains) {
		this.containsPawn = contains;
	}
	
	/**
	 * Whether this this contains a pawn
	 * @return
	 */
	public boolean containsPawn() {
		return containsPawn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tile tile = (Tile) o;
		return x == tile.x &&
				y == tile.y &&
				containsPawn == tile.containsPawn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, containsPawn);
	}
}
