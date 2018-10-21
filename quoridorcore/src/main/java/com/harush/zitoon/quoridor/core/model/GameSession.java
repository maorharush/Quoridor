package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.*;
import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.Player2PlayerDBOConverter;

import java.util.*;

/**
 * Represents a single game session. A game session comprises of 1, 2 or 4 players.
 */
public class GameSession extends Observable {

    public static int MAX_PLAYERS = 4;
    private Board board;
    private GamePersistenceService gamePersistenceService;
    private List<Player> players;
    private RuleType ruleType;
    private Player winner;

    private int currentPlayerIndex = 0;

    private Map<PawnType, Player> pawnType2PlayerMap = new HashMap<>();
    private WinnerDecider winnerDecider;
    private PlayerDAO playerDAO;
    private int gameID;
    private GameDAO gameDAO;
    private Player2PlayerDBOConverter player2PlayerDBOConverter;
    /**
     * private WinnerDecider winnerDecider;
     * <p>
     * /**
     * A {@link Stack} was chosen to store all the moves as an undo function can be
     * implemented in the practise mode.
     */
    private Deque<Move> moves;

    public GameSession(
            int gameID,
            Board board,
            RuleType rule,
            DAOFactory daoFactory,
            WinnerDecider winnerDecider,
            Player2PlayerDBOConverter player2PlayerDBOConverter) {
        this.board = board;
        this.players = new ArrayList<>();
        this.moves = new ArrayDeque<>();
        this.ruleType = rule;
        this.winnerDecider = winnerDecider;
        this.gameID = gameID;
        this.gameDAO = daoFactory.getDAO(GameDAO.TABLE_NAME);
        this.playerDAO = daoFactory.getDAO(PlayerDAO.TABLE_NAME);
        this.player2PlayerDBOConverter = player2PlayerDBOConverter;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
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

    public void checkForWinnerAndUpdateTurn(PlayerAction playerAction) {
        //Check if the pawn is in a winning position on the board
        boolean isWinnerFound = checkForWinner();
        if (!isWinnerFound) {
            //update whose turn it is
            updateTurn(playerAction);
        }
    }

    /**
     * Checks for winner, ends the game if found
     * @return true if winner found, false otherwise
     */
    public boolean checkForWinner() {
        Player currentPlayer = getCurrentPlayer();
        boolean isWinner = winnerDecider.isWinner(currentPlayer);
        if (isWinner) {
            setWinner(currentPlayer);
            endGame();
            return true;
        }
        return false;
    }

    public void endGame() {
        setChanged();

        GameDBO gameDBO = new GameDBO();
        gameDBO.setGame_id(gameID);
        gameDBO.setEnd_date(System.currentTimeMillis());
        gameDBO.setWinner(getCurrentPlayer().getPlayerID());
        //gameDBO.setNum_of_moves(); //TODO MorManush: Collect number of moves and set here
        gameDAO.updateGameRecord(gameDBO);

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

    public void updateTurn(PlayerAction playerAction) {
        recordMove(playerAction);

        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size(); // Increment player index for next turn
        Player newTurnPlayer = getCurrentPlayer();
        newTurnPlayer.getStatistics().incrementTotalMoves();
        System.out.println(newTurnPlayer.getName() + "'s turn");

        setChanged();
        notifyObservers(newTurnPlayer);

        newTurnPlayer.play();
    }

    public void recordMove(PlayerAction playerAction) {
        playerAction.setPlayer(getCurrentPlayer());
        gamePersistenceService.saveTurn(gameID, playerAction);
    }

    public void setGamePersistenceService(GamePersistenceService gamePersistenceService) {
        this.gamePersistenceService = gamePersistenceService;
    }
}
