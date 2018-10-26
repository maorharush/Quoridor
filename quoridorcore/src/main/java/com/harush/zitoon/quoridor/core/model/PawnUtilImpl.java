package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class PawnUtilImpl implements PawnUtil {

    private final Coordinate2PlayerActionConverter coordinateConverter;

    public PawnUtilImpl() {
        coordinateConverter = new Coordinate2PlayerActionConverterImpl();
    }

    @Override
    public List<PlayerAction> generatePawnMoves(Player player) {
        List<PlayerAction> moves = Lists.newArrayList();
        List<PlayerAction> pawnMoves = player.getPawn().getValidMoves().stream().map(coordinateConverter::toPlayerAction).collect(Collectors.toList());
        moves.addAll(pawnMoves);
        return moves;
    }

    @Override
    public PlayerAction generatePawnMove(Player player) {
        PlayerAction playerAction = coordinateConverter.toPlayerAction(player.getPawn().getCurrentCoordinate());
        playerAction.setPlayer(player);
        return playerAction;
    }

    @Override
    public List<PlayerAction> generatePawnMoves(PlayerAction playerAction, Board board) {
        Pawn pawn = playerAction.getPlayer().getPawn();
        PawnLogic pawnLogic = new PawnLogic(board, pawn.getType());
        pawnLogic.setInitialCoordinate(pawn.getInitialCoordinate());
        pawnLogic.setCurrentCoordinate(playerAction.getCoordinate());
        List<PlayerAction> moves = pawnLogic.getValidMoves().stream().map(coordinateConverter::toPlayerAction).collect(Collectors.toList());
        moves.forEach(move -> move.setPlayer(playerAction.getPlayer()));
        return moves;
    }
}
