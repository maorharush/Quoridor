package com.harush.zitoon.quoridor.core.logic;

import com.harush.zitoon.quoridor.core.Position;

public interface BoardPiece {

    int getX();

    int getY();

    Position.Orientation getOrientation();

    int getLength();
}
