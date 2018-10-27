package com.harush.zitoon.quoridor.core.model.Utils;

import java.util.*;
import java.util.stream.Collectors;

import com.harush.zitoon.quoridor.core.dao.*;
import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.Player2PlayerDBOConverter;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.PlayerAction2GameRecDBOConverter;
import com.harush.zitoon.quoridor.core.model.*;

/**
 * represent a save\load game handler
 */
public class GamePersistenceServiceImpl implements GamePersistenceService {

    private GameDAO gameDAO;
    private GameRecDAO gameRecDAO;
    private PlayerDAO playerDAO;
    private PlayersHistoryFactory playersHistoryFactory;
    private PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter;
    private Player2PlayerDBOConverter player2PlayerDBOConverter;

    public GamePersistenceServiceImpl(DAOFactory daoFactory, PlayersHistoryFactory playersHistoryFactory, PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter, Player2PlayerDBOConverter player2PlayerDBOConverter) {
        this.gameDAO = daoFactory.getDAO(GameDAO.TABLE_NAME);
        this.gameRecDAO = daoFactory.getDAO(GameRecDAO.TABLE_NAME);
        this.playerDAO = daoFactory.getDAO(PlayerDAO.TABLE_NAME);
        this.playersHistoryFactory = playersHistoryFactory;
        this.playerAction2GameRecDBOConverter = playerAction2GameRecDBOConverter;
        this.player2PlayerDBOConverter = player2PlayerDBOConverter;
    }

    @Override
    public void initGamePersistence(List<Player> players, GameSession gameSession) {
        insertGameRecord(gameSession);
        players.forEach(player -> insertPlayerAndSetID(gameSession.getGameID(), player));
    }

    /**
     * saves the current turn. to be initiated by gameSession prior to turn update.
     *
     * @param playerAction
     */
    @Override
    public void saveTurn(int gameID, PlayerAction playerAction) {
        GameRecDBO gameRecDBO = playerAction2GameRecDBOConverter.toGameRecDBO(gameID, playerAction);
        gameRecDAO.insert(gameRecDBO);
    }

    private int getCurrentPlayerIndex(int gameID) {
        List<GameRecDBO> gameRecords = gameRecDAO.getGameRecords(gameID);
        return gameRecords.size() % 2;
    }

    /**
     * load to latest turn on the last game.
     *
     * @return GameSession
     */
    @Override
    public SavedGame loadGame() {
        int lastGameID = gameDAO.getLastGameID();
        List<GameRecDBO> recordedList = gameRecDAO.getGameRecords(lastGameID);
        Set<Integer> playerIDs = recordedList.stream().map(GameRecDBO::getPlayer_id).collect(Collectors.toSet());
        List<PlayerHistory> playerHistories = playersHistoryFactory.getPlayerHistories(lastGameID, playerIDs);

        //Board board = populateBoardUtil.populateBoard(playerHistories);
        return new SavedGame(lastGameID, getCurrentPlayerIndex(lastGameID), playerHistories);
    }

    private void insertGameRecord(GameSession gameSession) {
        GameDBO gameDBO = new GameDBO();
        gameDBO.setGame_id(gameSession.getGameID());
        gameDBO.setStart_date(System.currentTimeMillis());
        gameDBO.setWinner(-1);
        gameDAO.insert(gameDBO);
    }

    private void insertPlayerAndSetID(int gameID, Player player) {
        PlayerDBO playerDBO = player2PlayerDBOConverter.toDBO(player);
        int playerID = playerDAO.insertAndReturnID(playerDBO);
        player.setPlayerID(playerID);
        recordPawnSpawn(gameID, player);
    }

    private void recordPawnSpawn(int gameID, Player player) {
        Coordinate initialCoordinate = player.getPawn().getInitialCoordinate();
        saveTurn(gameID, new PlayerAction(initialCoordinate.getX(), initialCoordinate.getY(), player));
    }

}
