package com.harush.zitoon.quoridor.ui;

import static org.junit.Assert.*;

import com.harush.zitoon.quoridor.core.model.Tile;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Tile.java.*
 * @version 0.1
 */

public class TileTest {

	private Tile t;

	@Before
	public void setUp() throws Exception {
		t = new Tile(2, 5);
	}

	@Test
	public void testGetX() {
		assertEquals(2, t.getX());
	}

	@Test
	public void testGetY() {
		assertEquals(5, t.getY());
	}

}