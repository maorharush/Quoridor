package com.harush.zitoon.quoridor.ui;

import static org.junit.Assert.*;


import com.harush.zitoon.quoridor.core.model.Board;
import com.harush.zitoon.quoridor.core.model.HumanPlayer;
import com.harush.zitoon.quoridor.core.model.PawnLogic;
import com.harush.zitoon.quoridor.core.model.Tile;
import org.junit.Before;
import org.junit.Test;

public class HumanPlayerTest {

	private Tile tile;
	private HumanPlayer player;
	private Board board;
	
	/**
	 * Initiates new Player instance.
	 */
	@Before
	public void setUp() {
		tile = new Tile(2, 5);		
		player = new HumanPlayer("TestPlayer", new PawnLogic(board), "#ffffff");
	}
	
	/**
	 * Tests getName method.
	 */
	@Test
	public void TestGetName() {
		assertEquals("TestPlayer", player.getName());
	}
	
	
}
