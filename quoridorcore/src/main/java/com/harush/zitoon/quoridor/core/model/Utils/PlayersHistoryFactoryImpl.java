package com.harush.zitoon.quoridor.core.model.Utils;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.PlayerDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.model.*;

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
    public List<PlayerHistory> getPlayerHistories(int gameID, Set<Integer> playerIDs) {
        List<PlayerHistory> playerHistories = new ArrayList<>();
        for (Integer playerID : playerIDs) {
            PlayerHistory playerHistory = getPlayerHistory(gameID, playerID);
            playerHistories.add(playerHistory);
        }
        return playerHistories;

    }

    private PlayerHistory getPlayerHistory(int gameID, int playerID) {
        List<GameRecDBO> moves = gameRecDAO.getPlayerRecords(gameID, playerID);
        PlayerDBO playerDBO = playerDAO.getPlayer(playerID);
        String playerName = playerDBO.getPlayer_name();
        int numOfTotalMoves = moves.size() - 1; // Not counting spawn move
        List<WallData> wallPlacements = getWallPlacements(moves);
        int numWallsLeft = Settings.getSingleton().getNumWalls() - wallPlacements.size();
        PawnType pawnType = getPawnType(moves);
        Coordinate initialPawnCoordinate = new Coordinate(getInitPawnX(moves), getInitPawnY(moves));
        Coordinate currentPawnCoordinate = new Coordinate(getCurrentPawnX(moves), getCurrentPawnY(moves));
        return new PlayerHistory(playerID, playerName, pawnType, wallPlacements, numWallsLeft, numOfTotalMoves, initialPawnCoordinate, currentPawnCoordinate, playerDBO.getIs_AI() == 1);
    }

    private int getCurrentPawnY(List<GameRecDBO> moves) {
        Optional<GameRecDBO> lastPawnMove = moves.stream().filter(move -> move.getPawn_y() != -1).reduce((first, second) -> second);
        if (!lastPawnMove.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find last pawn move for player:%s", moves.get(0).getPlayer_id()));
        }

        return lastPawnMove.get().getPawn_y();
    }

    private int getCurrentPawnX(List<GameRecDBO> moves) {
        Optional<GameRecDBO> lastPawnMove = moves.stream().filter(move -> move.getPawn_x() != -1).reduce((first, second) -> second);
        if (!lastPawnMove.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find last pawn move for player:%s", moves.get(0).getPlayer_id()));
        }

        return lastPawnMove.get().getPawn_x();
    }

    private int getInitPawnY(List<GameRecDBO> moves) {
        Optional<GameRecDBO> pawnSpawnGameRec = moves.stream().filter(move -> move.getPawn_y() != -1).findFirst();
        if (!pawnSpawnGameRec.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find pawn spawn move for player:%s", moves.get(0).getPlayer_id()));
        }
        return pawnSpawnGameRec.get().getPawn_y();
    }

    private int getInitPawnX(List<GameRecDBO> moves) {
        Optional<GameRecDBO> pawnSpawnGameRec = moves.stream().filter(move -> move.getPawn_x() != -1).findFirst();
        if (!pawnSpawnGameRec.isPresent()) {
            throw new RuntimeException(String.format("Couldn't find pawn spawn move for player:%s", moves.get(0).getPlayer_id()));
        }
        return pawnSpawnGameRec.get().getPawn_x();
    }

    private List<WallData> getWallPlacements(List<GameRecDBO> moves) {
        return moves.stream().filter(rec -> rec.getWall_x() != -1).map(this::getWallData).collect(Collectors.toList());
    }

    private WallData getWallData(GameRecDBO rec) {
        return new WallData(rec.getWall_x(), rec.getWall_y(), rec.getFence_orien() == 'h', rec.getIs_first() == 1, null);
    }

    private PawnType getPawnType(List<GameRecDBO> moves) {
        return moves.stream().filter(rec -> rec.getPawn_type() != null).map(rec -> PawnType.valueOf(rec.getPawn_type())).collect(Collectors.toCollection(LinkedList::new)).getFirst();
    }
}
