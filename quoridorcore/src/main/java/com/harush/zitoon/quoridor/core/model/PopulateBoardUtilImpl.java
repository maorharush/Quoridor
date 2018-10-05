package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PopulateBoardUtilImpl implements PopulateBoardUtil {

    @Override
    public Board populateBoard(List<PlayerHistory> playerHistories) {
        Board board = new Board(Settings.getSingleton().getBoardWidth(), Settings.getSingleton().getBoardHeight());
        populateBoardWithHorizontalWalls(board, playerHistories);
        populateBoardWithVerticalWalls(board, playerHistories);
        populateBoardWithPawns(board, playerHistories);
        return board;
    }

    @Override
    public void populateBoardWithPawns(Board board, List<PlayerHistory> playerHistories) {
        for (PlayerHistory playerHistory : playerHistories) {
            List<GameRecDBO> gameRecords = playerHistory.getGameRecords();
            LinkedList<GameRecDBO> pawnMoves = gameRecords.stream().filter(rec -> rec.getPawn_x() != -1).collect(Collectors.toCollection(LinkedList::new));
            GameRecDBO lastPawnMove = pawnMoves.getLast();
            board.setPawn(lastPawnMove.getPawn_x(), lastPawnMove.getPawn_y());
        }
    }

    @Override
    public void populateBoardWithVerticalWalls(Board board, List<PlayerHistory> playerHistories) {
        for (PlayerHistory playerHistory : playerHistories) {
            Player player = playerHistory.getPlayer();
            List<GameRecDBO> gameRecords = playerHistory.getGameRecords();
            List<GameRecDBO> gameRecHWalls = gameRecords.stream().filter(rec -> rec.getFence_orien() == 'v').collect(Collectors.toList());

            for (GameRecDBO gameRecDBO : gameRecHWalls) {
                board.setWall(gameRecDBO.getWall_x(), gameRecDBO.getWall_y(), true, true, player);
                board.setWall(gameRecDBO.getWall_x() + 1, gameRecDBO.getWall_y(), true, false, player);
            }
        }
    }

    @Override
    public void populateBoardWithHorizontalWalls(Board board, List<PlayerHistory> playerHistories) {
        for (PlayerHistory playerHistory : playerHistories) {
            Player player = playerHistory.getPlayer();
            List<GameRecDBO> gameRecords = playerHistory.getGameRecords();
            List<GameRecDBO> gameRecHWalls = gameRecords.stream().filter(rec -> rec.getFence_orien() == 'h').collect(Collectors.toList());

            for (GameRecDBO gameRecDBO : gameRecHWalls) {
                board.setWall(gameRecDBO.getWall_x(), gameRecDBO.getWall_y(), false, true, player);
                board.setWall(gameRecDBO.getWall_x(), gameRecDBO.getWall_y() + 1, false, false, player);
            }
        }
    }
}
