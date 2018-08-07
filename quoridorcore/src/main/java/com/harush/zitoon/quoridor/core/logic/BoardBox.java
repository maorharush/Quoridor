package com.harush.zitoon.quoridor.core.logic;

public interface BoardBox {

    boolean isOccupied();

    BoardPiece getBoardPiece();

    void setBoardPiece(BoardPiece boardPiece);

}
