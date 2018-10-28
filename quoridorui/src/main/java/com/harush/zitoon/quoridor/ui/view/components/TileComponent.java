package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.ui.view.MainGame;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * Represents a tile component.
 * Adapted from <a href="https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/checkers/Piece.java">AlmasB</a>.
 *
 */
public class TileComponent extends Rectangle {
        
	public TileComponent(int x, int y) {
		setWidth(MainGame.TILE_SIZE);
		setHeight(MainGame.TILE_SIZE);
		relocate(x * MainGame.TILE_SIZE+3, y * MainGame.TILE_SIZE+3);
		setFill(Color.valueOf("#663813"));
	}
	
}
