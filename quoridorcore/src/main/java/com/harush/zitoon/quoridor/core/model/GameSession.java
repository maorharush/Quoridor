package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;

import java.util.*;

/**
 * Represents a single game session. A game session comprises of 1, 2 or 4 players.
 *
 * @author Maor Harush
 * @version 0.6
 */
public class GameSession extends Observable {

    public static int MAX_PLAYERS = 4;
    private Board board;
    private List<Player> players;
    private RuleType ruleType;
    private Player winner;
    private int currentPlayerIndex = 0;
    private Map<PawnType, Player> pawnType2PlayerMap = new HashMap<>();
    private WinnerDecider winnerDecider;
    private int gameID;


    /**
     * private WinnerDecider winnerDecider;
     * <p>
     * /**
     * A {@link Stack} was chosen to store all the moves as an undo function can be
     * implemented in the practise mode.
     */
    private Deque<Move> moves;

    //TODO MorManush: Pass gameRecDAO in constructor
    public GameSession(Board board, RuleType rule, int gameID, WinnerDecider winnerDecider) {
        this.board = board;
        this.players = new ArrayList<>();
        this.moves = new ArrayDeque<>();
        this.ruleType = rule;
        this.winnerDecider = winnerDecider;
        this.gameID = gameID;
    }

    /**
     * get the game id
     *
     * @return Integer
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Gets the {@link Board} used in this session.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Adds a player to the session.
     *
     * @param player the player to add
     * @throws IllegalStateException    when more players than the limit are added
     * @throws IllegalArgumentException if the player is null
     */
    public void addPlayer(Player player) {
        if (players.size() > MAX_PLAYERS) {
            throw new IllegalStateException("There can only be a maximum of " + MAX_PLAYERS + " players");
        }
        if (player == null) {
            throw new IllegalArgumentException("The player cannot be null");
        }
        players.add(player);
        pawnType2PlayerMap.put(player.getPawn().getType(), player);
    }

    /**
     * Gets all the players in this session.
     *
     * @return the players in this session
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets a player with the specified id.
     *
     * @param id the player to get
     * @return the player
     */
    public Player getPlayer(int id) {
        return players.get(id);
    }

    /**
     * Adds a {@link Move} to the move list.
     *
     * @param move the pawn
     * @return true if the move was successful
     */
    public boolean addMove(Move move) {
        moves.push(move);
        return true;
    }

    /**
     * Gets a history of moves that have taken place.
     *
     * @return the history of moves
     */
    public Deque<Move> getMoves() {
        return moves;
    }

    /**
     * Gets the rule type for this particular session.
     *
     * @return the rule type
     */
    public RuleType getRuleType() {
        return ruleType;
    }


    /**
     * Sets the winner for this {@link GameSession}.
     *
     * @param player the winner
     */
    public void setWinner(Player player) {
        winner = player;
    }

    /**
     * Gets the {@link Player} who won the match.
     *
     * @return the winner
     */
    public Player getWinner() {
        return winner;
    }

    public void startGame() {
        getCurrentPlayer().play();
    }

    public Player getCurrentPlayer() {
        return getPlayer(currentPlayerIndex);
    }

    public void checkForWinnerAndUpdateTurn() {
        //Check if the pawn is in a winning position on the board
        checkForWinner();
        //update whose turn it is
        updateTurn();
    }

    public void checkForWinner() {
//		switch (type) {
//			case RED:
//				if (getRuleType() == RuleType.CHALLENGE) {
//					if (newX == (width - 1) && newY == (0)) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				} else if (getRuleType() == RuleType.STANDARD) {
//					if (newY == 0) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				}
//				break;
//			case WHITE:
//				if (getRuleType() == RuleType.CHALLENGE) {
//					if (newX == (0) && newY == (height - 1)) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				} else if (getRuleType() == RuleType.STANDARD) {
//					if (newY == (height - 1)) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				}
//				break;
//			case BLUE:
//				if (getRuleType() == RuleType.CHALLENGE) {
//					if (newX == (width - 1) && newY == (height - 1)) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				} else if (getRuleType() == RuleType.STANDARD) {
//					if (newX == 0) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				}
//				break;
//			case GREEN:
//				if (getRuleType() == RuleType.CHALLENGE) {
//					if (newX == 0 && newY == 0) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				} else if (getRuleType() == RuleType.STANDARD) {
//					if (newX == (width - 1)) {
//						setWinner(getCurrentPlayer());
//						endGame();
//					}
//				}
//				break;
//		}
        Player currentPlayer = getCurrentPlayer();
        boolean isWinner = winnerDecider.isWinner(currentPlayer);
        if (isWinner) {
            setWinner(currentPlayer);
            endGame();
        }
    }

    public void endGame() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Determines whether it is the current {@link Player player's} turn.
     *
     * @return whether it is the current player's turn
     */
    public LogicResult isCurrentTurn(PawnType pawnType) {

        if (pawnType.equals(getCurrentPlayer().getPawn().getType())) {
            return new LogicResult(true);
        }

        return new LogicResult(false, String.format("It is not %s's turn!", pawnType2PlayerMap.get(pawnType).getName()));
    }

    public void updateTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size(); // Increment player index for next turn
        Player newTurnPlayer = getCurrentPlayer();
        newTurnPlayer.getStatistics().incrementTotalMoves();
        System.out.println(newTurnPlayer.getName() + "'s turn");

        setChanged();
        notifyObservers(newTurnPlayer);

        newTurnPlayer.play();
    }
}
