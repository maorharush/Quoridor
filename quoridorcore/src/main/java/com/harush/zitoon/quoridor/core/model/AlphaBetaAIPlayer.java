package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A very simple AI player.
 */
public class AlphaBetaAIPlayer extends Player {

    private final GameSession gameSession;

    private final Coordinate2PlayerActionConverter coordinateConverter;

    private final WallUtil wallUtil;

    public AlphaBetaAIPlayer(String name, GameSession gameSession, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        super(name, pawn, verticalWalls, horizontalWalls, true);
        this.gameSession = gameSession;
        coordinateConverter = new Coordinate2PlayerActionConverterImpl();
        wallUtil = new WallUtilImpl();
    }

    @Override
    public void play() {

        System.out.println("I am alpha beta AI... `Thinking`...");

        List<PlayerAction> moves = generateMoves(pawn, gameSession.getBoard());



    }

    private List<PlayerAction> generateMoves(Pawn pawn, Board board) {
        List<PlayerAction> moves = Lists.newArrayList();
        List<PlayerAction> pawnMoves = pawn.getValidMoves().stream().map(coordinateConverter::toPlayerAction).collect(Collectors.toList());
        moves.addAll(pawnMoves);
        List<PlayerAction> wallPlacements = wallUtil.generateValidWallPlacements(verticalWalls, horizontalWalls);
        pawnMoves.addAll(wallPlacements);
        return pawnMoves;
    }
}

