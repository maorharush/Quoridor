package com.harush.zitoon.quoridor.core.logic;

import com.harush.zitoon.quoridor.core.model.LogicResult;

public interface Board {

    LogicResult setAtLocation(BoardPiece boardPiece, int x, int y);
}
