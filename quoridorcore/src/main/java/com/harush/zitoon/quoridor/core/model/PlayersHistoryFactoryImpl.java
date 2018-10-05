package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PlayersHistoryFactoryImpl implements PlayersHistoryFactory {


    private GameRecDAO gameRecDAO;
    private PlayersFactory playersFactory;

    public PlayersHistoryFactoryImpl(GameRecDAO gameRecDAO, PlayersFactory playersFactory) {
        this.gameRecDAO = gameRecDAO;
        this.playersFactory = playersFactory;
    }

    @Override
    public List<PlayerHistory> getPlayerHistories(int gameID, Set<String> playerNames) {
        List<PlayerHistory> playerHistories = new ArrayList<>();
        for (String playerName : playerNames) {
            PlayerHistory playerHistory = getPlayerHistory(gameID, playerName);
            playerHistories.add(playerHistory);
        }
        return playerHistories;

    }

    private int getCurrentPawnY(List<GameRecDBO> moves) {
        Optional<GameRecDBO> lastPawnMove = moves.stream().filter(move -> move.getPawn_y() != -1).reduce((first, second) -> second);
        if (!lastPawnMove.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find last pawn move for player:%s", moves.get(0).getPlayer_name()));
        }

        return lastPawnMove.get().getPawn_y();
    }

    private int getCurrentPawnX(List<GameRecDBO> moves) {
        Optional<GameRecDBO> lastPawnMove = moves.stream().filter(move -> move.getPawn_x() != -1).reduce((first, second) -> second);
        if (!lastPawnMove.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find last pawn move for player:%s", moves.get(0).getPlayer_name()));
        }

        return lastPawnMove.get().getPawn_x();
    }

    private int getInitPawnY(List<GameRecDBO> moves) {
        Optional<GameRecDBO> pawnSpawnGameRec = moves.stream().filter(move -> move.getPawn_y() != -1).findFirst();
        if (!pawnSpawnGameRec.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find pawn spawn move for player:%s", moves.get(0).getPlayer_name()));
        }
        return pawnSpawnGameRec.get().getPawn_y();
    }

    private int getInitPawnX(List<GameRecDBO> moves) {
        Optional<GameRecDBO> pawnSpawnGameRec = moves.stream().filter(move -> move.getPawn_x() != -1).findFirst();
        if (!pawnSpawnGameRec.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find pawn spawn move for player:%s", moves.get(0).getPlayer_name()));
        }
        return pawnSpawnGameRec.get().getPawn_x();
    }

    private int getNumOfWallsPlacements(List<GameRecDBO> moves) {
        return (int) moves.stream().filter(rec -> rec.getWall_x() != -1).count();
    }

    private PlayerHistory getPlayerHistory(int gameID, String playerName) {
        List<GameRecDBO> moves = gameRecDAO.getPlayerRecords(gameID, playerName); //TODO Manush: Implement - gameRecDAO.getPlayerRecords(lastGameID, playerName)
        int wallPlacements = getNumOfWallsPlacements(moves);
        int numWallsLeft = Settings.getSingleton().getNumWalls() - wallPlacements;
        int initPawnX = getInitPawnX(moves);
        int initPawnY = getInitPawnY(moves);
        int pawnX = getCurrentPawnX(moves);
        int pawnY = getCurrentPawnY(moves);
        Player player = playersFactory.getPlayer(playerName, true, initPawnX, initPawnY, pawnX, pawnY, numWallsLeft);
        return new PlayerHistory(player, moves);
    }
}
