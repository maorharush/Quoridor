package com.harush.zitoon.quoridor.core.model;

import java.util.*;
import java.util.stream.Collectors;

import com.harush.zitoon.quoridor.core.dao.*;
import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;

/**
 * represent a save\load game handler
 */
public class GamePersistenceServiceImpl implements GamePersistenceService {

    private GameDAO gameDAO;
    private GameRecDAO gameRecDAO;
    private PlayerDAO playerDAO;
    private PlayersHistoryFactory playersHistoryFactory;
    private PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter;

    public GamePersistenceServiceImpl(DAOFactory daoFactory, PlayersHistoryFactory playersHistoryFactory, PlayerAction2GameRecDBOConverter playerAction2GameRecDBOConverter) {
        this.gameDAO = daoFactory.getDAO(GameDAO.TABLE_NAME);
        this.gameRecDAO = daoFactory.getDAO(GameRecDAO.TABLE_NAME);
        this.playerDAO = daoFactory.getDAO(PlayerDAO.TABLE_NAME);
        this.playersHistoryFactory = playersHistoryFactory;
        this.playerAction2GameRecDBOConverter = playerAction2GameRecDBOConverter;
    }

    @Override
    public void initGamePersistence(GameSession gameSession) {
        GameDBO gameDBO = new GameDBO();
        gameDBO.setGame_id(gameSession.getGameID());
        gameDBO.setStart_date(System.currentTimeMillis());
        gameDAO.insert(gameDBO);

        List<Player> players = gameSession.getPlayers();
        List<PlayerDBO> playerDBOS = new ArrayList<>();
        for (Player player : players) {
            PlayerDBO playerDBO = new PlayerDBO();
            if (player instanceof HumanPlayer) {
                playerDBO.setIs_AI(0);
            } else {
                playerDBO.setIs_AI(1);
            }

            playerDBO.setPlayer_name(player.getName());
            playerDBOS.add(playerDBO);
        }

        playerDAO.insert(playerDBOS);
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
        Set<String> playerNames = recordedList.stream().map(GameRecDBO::getPlayer_name).collect(Collectors.toSet());
        List<PlayerHistory> playerHistories = playersHistoryFactory.getPlayerHistories(lastGameID, playerNames);

        //Board board = populateBoardUtil.populateBoard(playerHistories);
        return new SavedGame(lastGameID, playerHistories);
    }

}
