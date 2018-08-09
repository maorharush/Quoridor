package com.harush.zitoon.quoridor.core.logic;


import com.harush.zitoon.quoridor.core.theirs.Position;
import com.harush.zitoon.quoridor.core.model.*;

import java.util.logging.Logger;

import static com.harush.zitoon.quoridor.core.theirs.Position.Orientation.*;

public class PawnLogic implements Pawn {

    private static final Logger log = Logger.getLogger(PawnLogic.class.getSimpleName());

    private int x;

    private int y;

    public PawnLogic() {
        x = -1;
        y = -1;
    }

    @Override
    public LogicResult spawn(int x, int y, Board board) {
        log.config(String.format("Spawning at coordinate: (%d, %d)", x, y));

        LogicResult logicResult = board.setAtLocation(this, x, y);

        if (!logicResult.isSuccess()) {
            return createFailedLogicResult(String.format("Can't spawn at: (%d, %d)", x, y));
        }

        return new LogicResult(true);
    }

    @Override
    public LogicResult move(int x, int y, Board board) {
        log.config(String.format("Moving to coordinate: (%d, %d)", x, y));

        if (isInvalidPawnMove(x, y)) {
            return createFailedLogicResult(String.format("Moving to: (%d, %d) is an invalid pawn move", x, y));
        }

        LogicResult logicResult = board.setAtLocation(this, x, y);
        if (logicResult.isSuccess()) {
            this.x = x;
            this.y = y;
        }

        return logicResult;
    }

    private LogicResult createFailedLogicResult(String errMsg) {
        return new LogicResult(false, errMsg);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Position.Orientation getOrientation() {
        return NONE;
    }

    @Override
    public int getLength() {
        return 0;
    }

    private boolean isInvalidPawnMove(int x, int y) {
        return !isLeft(x, y) && !isUp(x, y) && !isRight(x, y) && !isDown(x, y);
    }

    private boolean isDown(int x, int y) {
        return this.x == x && this.y - 1 == y;
    }

    private boolean isRight(int x, int y) {
        return this.x + 1 == x && this.y == y;
    }

    private boolean isUp(int x, int y) {
        return this.x == x && this.y + 1 == y;
    }

    private boolean isLeft(int x, int y) {
        return this.x - 1 == x && this.y == y;
    }
}
