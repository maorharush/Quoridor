package com.harush.zitoon.quoridor.ui;

import static org.junit.Assert.*;


import com.harush.zitoon.quoridor.core.model.Board;
import com.harush.zitoon.quoridor.core.model.HumanPlayer;
import com.harush.zitoon.quoridor.core.model.PawnLogic;
import com.harush.zitoon.quoridor.core.model.Player;
import org.junit.Before;
import org.junit.Test;
/**
 * Test for {@link Player} class, incorporates test for {@link HumanPlayer}.
 * @author Moar Harush
 *
 */
public class PlayerTest {

	private Player player;
	private Player otherPlayer;
	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
		player = new HumanPlayer("Roman Reigns", new PawnLogic(board), "#ffffff");
		otherPlayer = new HumanPlayer("Sami Zayn", new PawnLogic(board), "#000000");
	}

	@Test
	public void testGetName() {
		assertEquals("Roman Reigns", player.getName());
		assertEquals("Sami Zayn", otherPlayer.getName());
	}

	@Test
	public void testGetWalls() {
		assertEquals(10, player.getWalls());
		assertEquals(10, otherPlayer.getWalls());
		player.decrementWalls();
		assertEquals(9, player.getWalls());
		otherPlayer.decrementWalls();
		otherPlayer.decrementWalls();
		assertEquals(8, otherPlayer.getWalls());
	}

	@Test
	public void testDecrementWalls() {
		player.decrementWalls();
		otherPlayer.decrementWalls();
		assertEquals(9, player.getWalls());
		assertEquals(9, otherPlayer.getWalls());
		
	}

	@Test
	public void testIncrementWalls() {
		//Decrement first to not trigger exception in the method
		player.decrementWalls();
		otherPlayer.decrementWalls();
		player.incrementWalls();
		otherPlayer.incrementWalls();
		assertEquals(10, player.getWalls());
		assertEquals(10, otherPlayer.getWalls());
	}

	@Test
	public void testGetStatistics() {
		assertNotNull(player.getStatistics());
		assertNotNull(otherPlayer.getStatistics());
	}

	@Test
	public void testGetPawnColour() {
		assertEquals("#ffffff", player.getPawnColour());
		assertEquals("#000000", otherPlayer.getPawnColour());
	}

}
