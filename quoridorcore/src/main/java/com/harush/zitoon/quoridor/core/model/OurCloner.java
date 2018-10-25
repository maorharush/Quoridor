package com.harush.zitoon.quoridor.core.model;

public interface OurCloner {
    PawnLogic clone(Pawn pawn, Board board);
    Player clone(Player player, PawnLogic pawnLogic);
    Board clone(Board board);
}
