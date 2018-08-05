package com.harush.zitoon.quoridor.core.logic;

import com.harush.zitoon.quoridor.core.model.BoardCoordinate;
import com.harush.zitoon.quoridor.core.model.LogicResult;

public interface PawnLogic {

    LogicResult spawn(BoardCoordinate coordinate);

    LogicResult move(BoardCoordinate coordinate);
}
