package com.harush.zitoon.quoridor.core.model;

import java.util.*;
import java.util.stream.Collectors;

import com.harush.zitoon.quoridor.core.dao.*;
import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.Player2PlayerDBOConverter;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.PlayerAction2GameRecDBOConverter;

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
        players.forEach(this::insertPlayerAndSetID);
    }

    /**
     * saves the current turn. to be initiated by gameSession prior to turn update.
     * @param playerAction
     */
    @Override
    public void saveTurn(PlayerAction playerAction) {
        GameRecDBO gameRecDBO = playerAction2GameRecDBOConverter.toGameRecDBO(playerAction);
        gameRecDAO.insert(gameRecDBO);
    }

    /**
     * load to latest turn on the last game.
     * @return GameSession
     */
    @Override
    public SavedGame loadGame() {
        int lastGameID = gameDAO.getLastGameID();
        List<GameRecDBO> recordedList = gameRecDAO.getGameRecords(lastGameID);
        Set<Integer> playerIDs = recordedList.stream().map(GameRecDBO::getPlayer_id).collect(Collectors.toSet());
        List<PlayerHistory> playerHistories = playersHistoryFactory.getPlayerHistories(lastGameID, playerIDs);

        //Board board = populateBoardUtil.populateBoard(playerHistories);
        return new SavedGame(lastGameID, playerHistories);
    }

    private void insertGameRecord(GameSession gameSession) {
        GameDBO gameDBO = new GameDBO();
        gameDBO.setGame_id(gameSession.getGameID());
        gameDBO.setStart_date(System.currentTimeMillis());
        gameDAO.insert(gameDBO);
    }

    private void insertPlayerAndSetID(Player player) {
        PlayerDBO playerDBO = player2PlayerDBOConverter.toDBO(player);
        int playerID = playerDAO.insertAndReturnID(playerDBO);
        player.setPlayerID(playerID);
        recordPawnSpawn(player);
    }

    private void recordPawnSpawn(Player player) {
        Coordinate initialCoordinate = player.getPawn().getInitialCoordinate();
        saveTurn(new PlayerAction(initialCoordinate.getX(), initialCoordinate.getY(), player));
    }

}
