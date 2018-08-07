package com.harush.zitoon.quoridor.core;

import com.harush.zitoon.quoridor.core.logic.BoardBoxLogic;
import com.harush.zitoon.quoridor.core.logic.BoardLogic;
import com.harush.zitoon.quoridor.core.logic.BoardPiece;
import com.harush.zitoon.quoridor.core.logic.PawnLogic;
import com.harush.zitoon.quoridor.core.model.LogicResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BoardLogicTest {

    private BoardLogic boardLogic;

    @Mock
    private BoardPiece boardPiece;

    private BoardBoxLogic[][] boardBoxLogics;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        boardBoxLogics = new BoardBoxLogic[9][9];
        boardLogic = new BoardLogic(boardBoxLogics);
    }

    @Test
    public void coordinateOutOfBoard_LogicResultFalse() {
        LogicResult logicResult = boardLogic.setAtLocation(boardPiece, -1, -1);

        Assert.assertFalse(logicResult.isSuccess());
        Assert.assertEquals("Cannot set board piece at (-1,-1)", logicResult.getErrMsg());
    }

    @Test
    public void coordinateOccupied_LogicResultFalse() {
        boardBoxLogics[1][1] = new BoardBoxLogic();
        boardBoxLogics[1][1].setBoardPiece(new PawnLogic());

        LogicResult logicResult = boardLogic.setAtLocation(boardPiece, 1, 1);

        Assert.assertFalse(logicResult.isSuccess());
        Assert.assertEquals("Cannot set board piece at (1,1)", logicResult.getErrMsg());
    }
}
