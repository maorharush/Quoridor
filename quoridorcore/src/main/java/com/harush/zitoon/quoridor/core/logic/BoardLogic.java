package com.harush.zitoon.quoridor.core.logic;

import com.harush.zitoon.quoridor.core.Position;
import com.harush.zitoon.quoridor.core.model.LogicResult;
import org.springframework.lang.NonNull;

import static com.harush.zitoon.quoridor.core.Position.Orientation.*;

public class BoardLogic implements Board {

    private BoardBox[][] boardBoxes;

    public BoardLogic(@NonNull BoardBox[][] boardBoxes) {
        this.boardBoxes = boardBoxes;
    }

    @Override
    public LogicResult setAtLocation(@NonNull BoardPiece boardPiece, int x, int y) {
        if (!isValidCoordinate(x, y) || isBoardBoxOccupied(x, y)) {
            return new LogicResult(false, String.format("Cannot set board piece at (%d,%d)", x, y));
        }

        if (boardPiece.getOrientation() == NONE) {
            boardBoxes[boardPiece.getX()][boardPiece.getY()].setBoardPiece(null);
            boardBoxes[x][y].setBoardPiece(boardPiece);
        } else {
            if (boardPiece.getOrientation() == Horizontal) {
                setHorizontal(boardPiece, x, y);
            } else {
                setVertical(boardPiece, x, y);
            }
        }

        return new LogicResult(true);
    }

    private void setVertical(BoardPiece boardPiece, int x, int y) {
        for (int i = 0; i < boardPiece.getLength(); i++) {
            boardBoxes[boardPiece.getX()][boardPiece.getY() + i].setBoardPiece(null);
        }

        for (int i = 0; i < boardPiece.getLength(); i++) {
            boardBoxes[x][y + i].setBoardPiece(boardPiece);
        }
    }

    private void setHorizontal(BoardPiece boardPiece, int x, int y) {
        for (int i = 0; i < boardPiece.getLength(); i++) {
            boardBoxes[boardPiece.getX() + i][boardPiece.getY()].setBoardPiece(null);
        }

        for (int i = 0; i < boardPiece.getLength(); i++) {
            boardBoxes[x + i][y].setBoardPiece(boardPiece);
        }
    }

    private boolean isBoardBoxOccupied(int x, int y) {
        return boardBoxes[x][y].isOccupied();
    }

    private boolean isValidCoordinate(int x, int y) {
        return (isCoordinateWithinBorders(x) && isCoordinateWithinBorders(y));
    }

    private boolean isCoordinateWithinBorders(int coordinate) {
        return coordinate >= 0 && coordinate <= 9;
    }
}
