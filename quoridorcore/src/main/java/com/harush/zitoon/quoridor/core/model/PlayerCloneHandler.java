package com.harush.zitoon.quoridor.core.model;

public class PlayerCloneHandler implements CloneHandler<Player> {

    private final CloneUtil cloneUtil;

    public PlayerCloneHandler(CloneUtil cloneUtil) {
        this.cloneUtil = cloneUtil;
    }

    @Override
    public Player clone(Player player, Object... args) {
        return new ClonedPlayer(cloneUtil.clone(player.getPawn()));
    }

    private class ClonedPlayer extends Player {

        public ClonedPlayer(Pawn pawn) {
            super(pawn);
        }

        @Override
        public void play() {

        }
    }
}
