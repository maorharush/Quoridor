package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public class BoardCloneHandler implements CloneHandler<Board> {

    private final CloneUtil cloneUtil;

    public BoardCloneHandler(CloneUtil cloneUtil) {
        this.cloneUtil = cloneUtil;
    }

    @Override
    public Board clone(Board board, Object... args) {
        Board clone = new Board(board.getHeight(), board.getWidth());
        List<WallData> clonedWalls = cloneUtil.clone(board.getAllWalls());
        List<Tile> clonedTiles = cloneUtil.clone(board.getAllTiles());
        clonedWalls.forEach(clone::setWall);
        clonedTiles.forEach(clone::setTile);

        return clone;
    }
}
