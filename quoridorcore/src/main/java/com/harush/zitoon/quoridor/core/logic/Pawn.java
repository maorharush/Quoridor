package com.harush.zitoon.quoridor.core.logic;


import com.harush.zitoon.quoridor.core.logic.PawnLogic;
import com.harush.zitoon.quoridor.core.model.BoardColumn;
import com.harush.zitoon.quoridor.core.model.BoardCoordinate;
import com.harush.zitoon.quoridor.core.model.BoardRow;
import com.harush.zitoon.quoridor.core.model.LogicResult;

public class Pawn implements PawnLogic {
    @Override
    public LogicResult spawn(BoardCoordinate coordinate) {
        System.out.println("Spawning at coordinate:" + coordinate);
        return new LogicResult(true);
    }

    @Override
    public LogicResult move(BoardCoordinate coordinate) {
        System.out.println("Moving to coordinate:" + coordinate);

        if (coordinate.getBoardColumn() == BoardColumn.B && coordinate.getBoardRow() == BoardRow._1) {
            return new LogicResult(false, "Can't move here!!! are you stupid?!?!");
        }
        return new LogicResult(true);
    }
}
