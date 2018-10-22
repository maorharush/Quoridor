package com.harush.zitoon.quoridor.core.model;

import org.junit.Test;
import org.mockito.Mock;

public class PathClearanceValidatorImplTest {
    @Mock
    private GameSession gameSession;

    @Test
    public void getPathClearanceValidatorImplTest_success() {
        Board board = new Board();

        WallData wallPlacement1 = new WallData(1, 5, true, true, null);
        WallData wallPlacement2 = new WallData(3, 5, false, true, null);
        WallData wallPlacement3 = new WallData(1, 5, true, true, null);
        WallData wallPlacement4 = new WallData(3, 5, false, true, null);

    }
}
