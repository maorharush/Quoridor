package com.harush.zitoon.quoridor.core.model;

public class BoardCoordinate {

    private BoardColumn boardColumn;

    private BoardRow boardRow;

    public BoardCoordinate(BoardColumn boardColumn, BoardRow boardRow) {
        this.boardColumn = boardColumn;
        this.boardRow = boardRow;
    }

    public BoardColumn getBoardColumn() {
        return boardColumn;
    }

    public BoardRow getBoardRow() {
        return boardRow;
    }

    @Override
    public String toString() {
        return "BoardCoordinate{" +
                "boardColumn=" + boardColumn +
                ", boardRow=" + boardRow +
                '}';
    }
}


