package com.harush.zitoon.quoridor.core.model;

public class TileCloneHandler implements CloneHandler<Tile> {

    @Override
    public Tile clone(Tile tile, Object... args) {
        Tile clone = new Tile(tile.getX(), tile.getY());
        clone.setContainsPawn(tile.containsPawn());
        return clone;
    }
}
