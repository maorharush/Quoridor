package com.harush.zitoon.quoridor.core.model;

import java.util.*;
import java.util.stream.Collectors;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

/**
 * represent a save\load game handler
 */
public class GamePersistenceServiceImpl implements GamePersistenceService {

    private GameRecDAO gameRecDAO;
    private PlayersHistoryFactory playersHistoryFactory;
    private PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter;
    private PopulateBoardUtil populateBoardUtil;

    public GamePersistenceServiceImpl(GameRecDAO gameRecDAO, PopulateBoardUtil populateBoardUtil, PlayersHistoryFactory playersHistoryFactory, PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter) {
        this.gameRecDAO = gameRecDAO;
        this.populateBoardUtil = populateBoardUtil;
        this.playersHistoryFactory = playersHistoryFactory;
        this.playerAction2GameRecDBOConverter = playerAction2GameRecDBOConverter;
    }

    @Override
    public void saveTurn(PlayerAction playerAction) {
        GameRecDBO gameRecDBO = playerAction2GameRecDBOConverter.toGameRecDBO(playerAction);
        gameRecDAO.insert(gameRecDBO);
    }

    @Override
    public GameSession loadGame() {
        int lastGameID = gameRecDAO.getMaxID();
        List<GameRecDBO> recordedList = gameRecDAO.getGameRecords(lastGameID);
        Set<String> playerNames = recordedList.stream().map(GameRecDBO::getPlayer_name).collect(Collectors.toSet());
        List<PlayerHistory> playerHistories = playersHistoryFactory.getPlayerHistories(lastGameID, playerNames);
        Board board = populateBoardUtil.populateBoard(playerHistories);
        GameSession gameSession = new GameSession(board, Settings.getSingleton().getRuleType(), lastGameID, new WinnerDeciderLogic());
        List<Player> players = playerHistories.stream().map(PlayerHistory::getPlayer).collect(Collectors.toList());
        players.forEach(gameSession::addPlayer);
        gameSession.checkForWinnerAndUpdateTurn();
        return gameSession;
    }

}
