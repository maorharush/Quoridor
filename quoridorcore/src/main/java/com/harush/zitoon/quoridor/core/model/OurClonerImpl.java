package com.harush.zitoon.quoridor.core.model;

public class OurClonerImpl implements OurCloner {

    @Override
    public PawnLogic clone(Pawn pawn, Board board) {
        PawnLogic pawnCopy = new PawnLogic(board,pawn.getType());
        pawnCopy.setCurrentCoordinate(pawn.getCurrentCoordinate());
        pawnCopy.setInitialCoordinate(pawn.getInitialCoordinate());
        return pawnCopy;
    }

    @Override
    public Player clone(Player player, PawnLogic pawnLogic) {
        Player playerCopy = new HumanPlayer(player.getName(),pawnLogic);
        playerCopy.setNumWalls(player.getNumWalls());
        playerCopy.setPlayerID(player.getPlayerID());
        playerCopy.setStats(player.getStatistics());
        return playerCopy;
    }

    @Override
    public Board clone(Board board) {
        Board boardCopy = new Board();
        for (WallData wall:board.getAllWalls())
            boardCopy.setWall(wall);

        return boardCopy;
    }
}
