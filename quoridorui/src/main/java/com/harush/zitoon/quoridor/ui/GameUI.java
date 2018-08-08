package com.harush.zitoon.quoridor.ui;

import com.harush.zitoon.quoridor.core.logic.Board;
import com.harush.zitoon.quoridor.core.logic.Pawn;
import com.harush.zitoon.quoridor.core.model.LogicResult;

import java.util.logging.Logger;

public class GameUI {

    private static final Logger log = Logger.getLogger(GameUI.class.getSimpleName());

    private Pawn pawn;

    private Board board;

    public GameUI(Board board, Pawn pawn) {
        this.pawn = pawn;
        this.board = board;
    }

    public void movePawnOnScreen(int x, int y) {
        LogicResult logicResult = board.setAtLocation(pawn, x, y);

        if (!logicResult.isSuccess()) {
            log.info(logicResult.getErrMsg());
        }
    }
}
