package com.harush.zitoon.quoridor.core.model;

public class OurClonerImpl implements OurCloner {

    @Override
    public PawnLogic clone(Board board,Pawn pawn) {
        PawnLogic pawnCopy = new PawnLogic(board,pawn.getType());
        pawnCopy.setCurrentCoordinate(pawn.getCurrentCoordinate());
        pawnCopy.setInitialCoordinate(pawn.getInitialCoordinate());
        return pawnCopy;
    }

    @Override
    public Player clone(Player player) {
        player.setNumWalls(player.getNumWalls());
        player.setPlayerID(player.getPlayerID());
        player.setStats(player.getStatistics());
        return player;
    }

    @Override
    public Board clone(Board board) {
        Board boardCopy = new Board();
        for (WallData wall:board.getAllWalls())
            boardCopy.setWall(wall);

        return board;
    }
}
