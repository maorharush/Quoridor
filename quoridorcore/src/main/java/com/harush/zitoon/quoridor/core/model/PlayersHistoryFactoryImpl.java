package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.GameDAO;
import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.PlayerDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;

import java.util.*;
import java.util.stream.Collectors;

public class PlayersHistoryFactoryImpl implements PlayersHistoryFactory {

    private GameRecDAO gameRecDAO;
    private PlayerDAO playerDAO;

    public PlayersHistoryFactoryImpl(GameRecDAO gameRecDAO, PlayerDAO playerDAO) {
        this.gameRecDAO = gameRecDAO;
        this.playerDAO = playerDAO;
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

    private PlayerHistory getPlayerHistory(int gameID, String playerName) {
        PlayerDBO playerDBO = playerDAO.getPlayer(playerName);
        List<GameRecDBO> moves = gameRecDAO.getPlayerRecords(gameID, playerName);
        int numOfTotalMoves = moves.size();
        List<WallData> wallPlacements = getWallPlacements(moves);
        int numWallsLeft = Settings.getSingleton().getNumWalls() - wallPlacements.size();
        PawnType pawnType = getPawnType(moves);
        Coordinate initialPawnCoordinate = new Coordinate(getInitPawnX(moves), getInitPawnY(moves));
        Coordinate currentPawnCoordinate = new Coordinate(getCurrentPawnX(moves), getCurrentPawnY(moves));
        return new PlayerHistory(playerName, pawnType, wallPlacements, numWallsLeft, numOfTotalMoves, initialPawnCoordinate, currentPawnCoordinate, playerDBO.getIs_AI() == 1);
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

    private List<WallData> getWallPlacements(List<GameRecDBO> moves) {
        return moves.stream().map(rec -> rec.getWall_x() != -1 ? getWallData(rec) : null).collect(Collectors.toList());
    }

    private WallData getWallData(GameRecDBO rec) {
        return new WallData(rec.getPawn_x(), rec.getPawn_y(), rec.getFence_orien() == 'h', rec.getIs_first() == 1, null);
    }

    private PawnType getPawnType(List<GameRecDBO> moves) {
        return moves.stream().map(rec -> PawnType.valueOf(rec.getPawn_type())).collect(Collectors.toCollection(LinkedList::new)).getFirst();
    }
}
