//package com.harush.zitoon.quoridor.ui;
//
//import static org.junit.Assert.*;
//
//
//import com.harush.zitoon.quoridor.core.model.*;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayDeque;
//import java.util.Deque;
//
///**
// * Test class for GameSession.java.
// * @author Anas Khan
// * @version 0.1
// */
//
//public class GameSessionTest {
//
//	private Board board;
//	private RuleType ruleType;
//	private GameSession game;
//	private Player player;
//	private Deque<Move> moves;
//	private Move move;
//	private Tile to;
//	private Tile from;
//
//
//	@Before
//	public void setUp() throws Exception {
//		board = new Board();
//		ruleType = RuleType.STANDARD;
//		player = new HumanPlayer("TestPlayer", new PawnLogic(board), "#ffffff");
//		game = new GameSession(board, ruleType);
//		to = new Tile(0,0);
//		from = new Tile(0,1);
//		move = new Move(to, from, player);
//		moves = new ArrayDeque<Move>();
//	}
//
//	@Test
//	public void testGetBoard() {
//		assertEquals(board, game.getBoard());
//	}
//
//	@Test
//	public void testGetPlayers() {
//		assertEquals(0, game.getPlayers().size());
//	}
//
//	@Test
//	public void testAddPlayer() {
//		game.addPlayer(player);
//		assertEquals(1, game.getPlayers().size());
//	}
//
//	@Test
//	public void testGetPlayer() {
//		Player player2 = new HumanPlayer("TestPlayer", new PawnLogic(board), "#ffffff");
//		Player player3 = new HumanPlayer("TestPlayer", new PawnLogic(board),"#ffffff");
//		game.addPlayer(player);
//		game.addPlayer(player2);
//		game.addPlayer(player3);
//		assertEquals(player, game.getPlayer(0));
//		assertEquals(player3, game.getPlayer(2));
//	}
//
//	@Test
//	public void testAddMove() {
//		assertEquals(0, game.getMoves().size());
//		game.addMove(move);
//		assertEquals(1, game.getMoves().size());
//	}
//
//	@Test
//	public void testGetMoves() {
//		assertEquals(0, moves.size());
//	}
//
//
//	@Test
//	public void testGetRuleType() {
//		assertEquals(RuleType.STANDARD, game.getRuleType());
//	}
//
//	@Test
//	public void testSetWinner() {
//		assertEquals(null, game.getWinner());
//		game.setWinner(player);
//		assertEquals(player, game.getWinner());
//	}
//
//	@Test
//	public void testGetWinner() {
//		assertEquals(null, game.getWinner());
//	}
//
//}
