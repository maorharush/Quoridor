package com.harush.zitoon.quoridor.ui;

import com.harush.zitoon.quoridor.core.logic.PawnLogic;
import com.harush.zitoon.quoridor.core.model.BoardCoordinate;
import com.harush.zitoon.quoridor.core.model.LogicResult;

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
