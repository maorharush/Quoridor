package com.harush.zitoon.quoridor.core.logic;

public class BoardBoxLogic implements BoardBox {

    private BoardPiece boardPiece;

    @Override
    public boolean isOccupied() {
        return boardPiece != null;
    }

    @Override
    public BoardPiece getBoardPiece() {
        return boardPiece;
    }

    @Override
    public void setBoardPiece(BoardPiece boardPiece) {
        this.boardPiece = boardPiece;
    }
}
