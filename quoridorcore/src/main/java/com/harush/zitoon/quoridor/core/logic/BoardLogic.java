package com.harush.zitoon.quoridor.core.logic;

import com.harush.zitoon.quoridor.core.model.LogicResult;
import org.springframework.lang.NonNull;

import static com.harush.zitoon.quoridor.core.theirs.Position.Orientation.*;

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
            int currentX = boardPiece.getX();
            int currentY = boardPiece.getY();

            if (isValidCoordinate(currentX, currentY)) {
                boardBoxes[currentX][currentY].setBoardPiece(null);
            }
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
        int currentX = boardPiece.getX();
        int currentY = boardPiece.getY();

        if (isValidCoordinate(currentX, currentY)) {
            for (int i = 0; i < boardPiece.getLength(); i++) {
                boardBoxes[boardPiece.getX()][boardPiece.getY() + i].setBoardPiece(null);
            }
        }

        for (int i = 0; i < boardPiece.getLength(); i++) {
            boardBoxes[x][y + i].setBoardPiece(boardPiece);
        }
    }

    private void setHorizontal(BoardPiece boardPiece, int x, int y) {
        int currentX = boardPiece.getX();
        int currentY = boardPiece.getY();

        if (isValidCoordinate(currentX, currentY)) {
            for (int i = 0; i < boardPiece.getLength(); i++) {
                boardBoxes[currentX + i][currentY].setBoardPiece(null);
            }
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
        return coordinate >= 0 && coordinate <= 8;
    }
}
