package com.harush.zitoon.quoridor.ui;

import com.harush.zitoon.quoridor.common.logic.PawnLogic;
import com.harush.zitoon.quoridor.common.model.BoardCoordinate;
import com.harush.zitoon.quoridor.common.model.LogicResult;

public class GameUI {

    private PawnLogic pawnLogic;

    public GameUI(PawnLogic pawnLogic) {
        this.pawnLogic = pawnLogic;
    }

    public void movePawnOnScreen(BoardCoordinate coordinate) {
        LogicResult logicResult = pawnLogic.move(coordinate);

        if (!logicResult.isSuccess()) {
            System.out.println(logicResult.getErrMsg());
        }
    }
}
