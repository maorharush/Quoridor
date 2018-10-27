package com.harush.zitoon.quoridor.core.model;

public class WallDataCloneHandler implements CloneHandler<WallData> {

    @Override
    public WallData clone(WallData wallData, Object... args) {
        return new WallData(wallData.getX(), wallData.getY(), wallData.isHorizontal(), wallData.isFirst(), null);
    }
}
