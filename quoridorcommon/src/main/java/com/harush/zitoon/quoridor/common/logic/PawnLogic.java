package com.harush.zitoon.quoridor.common.logic;

import com.harush.zitoon.quoridor.common.model.BoardCoordinate;
import com.harush.zitoon.quoridor.common.model.LogicResult;

public interface PawnLogic {

    LogicResult spawn(BoardCoordinate coordinate);

    LogicResult move(BoardCoordinate coordinate);
}
