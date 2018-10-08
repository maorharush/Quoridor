package com.harush.zitoon.quoridor.core.model;

import java.util.*;
import java.util.stream.Collectors;

import com.harush.zitoon.quoridor.core.dao.DAOFactory;
import com.harush.zitoon.quoridor.core.dao.DAOFactoryImpl;
import com.harush.zitoon.quoridor.core.dao.GameDAO;
import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

/**
 * represent a save\load game handler
 */
public class GamePersistenceServiceImpl implements GamePersistenceService {

    private GameDAO gameDAO;
    private GameRecDAO gameRecDAO;
    private PlayersHistoryFactory playersHistoryFactory;
    private PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter;
    private PopulateBoardUtil populateBoardUtil;

    public GamePersistenceServiceImpl(DAOFactory daoFactory, PopulateBoardUtil populateBoardUtil, PlayersHistoryFactory playersHistoryFactory, PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter) {
        this.gameDAO = daoFactory.getDAO(GameDAO.TABLE_NAME);
        this.gameRecDAO = daoFactory.getDAO(GameRecDAO.TABLE_NAME);
        this.populateBoardUtil = populateBoardUtil;
        this.playersHistoryFactory = playersHistoryFactory;
        this.playerAction2GameRecDBOConverter = playerAction2GameRecDBOConverter;
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
    public GameSession loadGame() {
        int lastGameID = gameDAO.getLastGameID();
        List<GameRecDBO> recordedList = gameRecDAO.getGameRecords(lastGameID);
        Set<String> playerNames = recordedList.stream().map(GameRecDBO::getPlayer_name).collect(Collectors.toSet());
        List<PlayerHistory> playerHistories = playersHistoryFactory.getPlayerHistories(lastGameID, playerNames);
        Board board = populateBoardUtil.populateBoard(playerHistories);
        GameSession gameSession = new GameSession(lastGameID, board, Settings.getSingleton().getRuleType(), DAOFactoryImpl.instance(), new WinnerDeciderLogic());
        gameSession.setGamePersistenceService(this);
        List<Player> players = playerHistories.stream().map(PlayerHistory::getPlayer).collect(Collectors.toList());
        players.forEach(gameSession::addPlayer);
        //gameSession.checkForWinnerAndUpdateTurn();
        return gameSession;
    }

}
