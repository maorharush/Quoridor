package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.theirs.Human;

import java.util.HashMap;
import java.util.Map;


public class CloneUtilImpl implements CloneUtil {

    private static final Map<Class, CloneHandler> class2cloneHandlerMap = new HashMap<>();

    public CloneUtilImpl() {
        class2cloneHandlerMap.put(Pawn.class, new PawnCloneHandler());
        class2cloneHandlerMap.put(Player.class, new PlayerCloneHandler(this));
        class2cloneHandlerMap.put(Board.class, new BoardCloneHandler(this));
        class2cloneHandlerMap.put(Tile.class, new TileCloneHandler());
        class2cloneHandlerMap.put(WallData.class, new WallDataCloneHandler());
    }

    @Override
    public <T> T clone(T t, Class clazz) {
        return (T) class2cloneHandlerMap.get(clazz).clone(t);
    }
}
