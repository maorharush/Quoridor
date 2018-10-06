package com.harush.zitoon.quoridor.ui.view.utils;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.components.AIPawnComponent;
import com.harush.zitoon.quoridor.ui.view.components.HorizontalWallComponent;
import com.harush.zitoon.quoridor.ui.view.components.HumanPawnComponent;
import com.harush.zitoon.quoridor.ui.view.components.VerticalWallComponent;

public class PlayersFactoryImpl implements PlayersFactory {

    private GameSession gameSession;
    private VerticalWallComponent[][] verticalWallComponents;
    private HorizontalWallComponent[][] horizontalWallComponents;

    public PlayersFactoryImpl(GameSession gameSession, VerticalWallComponent[][] verticalWallComponents, HorizontalWallComponent[][] horizontalWallComponents) {
        this.gameSession = gameSession;
        this.verticalWallComponents = verticalWallComponents;
        this.horizontalWallComponents = horizontalWallComponents;
    }

    @Override
    public Player getPlayer(String playerName, PawnType pawnType, boolean isAI, int initPawnX, int initPawnY, int pawnX, int pawnY, int numWallsLeft) {
        if (!isAI) {
            HumanPawnComponent pawnComponent = new HumanPawnComponent(initPawnX, initPawnY, playerName, new PawnLogic(gameSession, pawnType));
            return new HumanPlayer(playerName, pawnComponent);
        } else {
            // TODO MorManush: Get type of AI from Settings or something
            AIPawnComponent pawnComponent = new AIPawnComponent(initPawnX, initPawnY, playerName, new PawnLogic(gameSession, pawnType));
            return new WantsToWinAIPlayer(playerName, pawnComponent, verticalWallComponents, horizontalWallComponents);
        }
    }
}
