package com.harush.zitoon.quoridor.core.model;

public interface OurCloner {
    PawnLogic clone(Board board, Pawn pawn);
    Player clone(Player player);
    Board clone(Board board);
}
