package com.harush.zitoon.quoridor.core.logic;

import com.harush.zitoon.quoridor.core.model.LogicResult;

public interface Pawn extends BoardPiece {

    LogicResult spawn(int x, int y, Board boardLogic);

    LogicResult move(int x, int y, Board boardLogic);
}
