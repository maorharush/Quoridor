package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;

public abstract class TestHelper {

    private static final int width = Settings.getSingleton().getBoardWidth();

    private static final int height = Settings.getSingleton().getBoardHeight();

    public static GameRecDBO generateGameRecDBO(int gameID, int playerID, String pawnType, int pawnX, int pawnY, int wallX, int wallY, Character orientation) {
        GameRecDBO gameRecDBO = new GameRecDBO();
        gameRecDBO.setGame_id(gameID);
        gameRecDBO.setPlayer_id(playerID);
        gameRecDBO.setPawn_type(pawnType);
        gameRecDBO.setPawn_x(pawnX);
        gameRecDBO.setPawn_y(pawnY);

        gameRecDBO.setWall_x(wallX);
        gameRecDBO.setWall_y(wallY);
        gameRecDBO.setFence_orien(orientation);
        if (wallX != -1) {
            gameRecDBO.setIs_first(1);
        } else {
            gameRecDBO.setIs_first(-1);
        }

        return gameRecDBO;
    }

    public static GameDBO generateGameDBO(int gameID, int winner, int num_of_moves) {
        GameDBO gameDBO = new GameDBO();
        gameDBO.setGame_id(gameID);
        gameDBO.setWinner(winner);
        gameDBO.setNum_of_moves(num_of_moves);
        gameDBO.setStart_date(System.currentTimeMillis());
        gameDBO.setEnd_date(System.currentTimeMillis());
        return gameDBO;
    }

    public static PlayerDBO generatePlayerDBO(int playerID, String playerName, boolean isAI) {
        PlayerDBO playerDBO = new PlayerDBO();
        playerDBO.setPlayer_id(playerID);
        playerDBO.setHighest_score("1111");
        playerDBO.setIs_AI(isAI ? 1 : 0);
        playerDBO.setPlayer_name(playerName);
        return playerDBO;
    }

    public static PlayerDBO generatePlayerDBO(String playerName, boolean isAI) {
        return generatePlayerDBO(-1, playerName, isAI);
    }

    public static Wall[][] generateHorizontalWalls(GameSession gameSession) {
        Wall[][] verticalWalls = new HorizontalWallLogic[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                verticalWalls[x][y] = new HorizontalWallLogic(x, y, gameSession);
            }
        }
        return verticalWalls;
    }

    public static Wall[][] generateVerticalWalls(GameSession gameSession) {
        Wall[][] verticalWalls = new VerticalWallLogic[width][height];
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height; y++) {
                verticalWalls[x][y] = new VerticalWallLogic(x, y, gameSession);
            }
        }
        return verticalWalls;
    }
}
