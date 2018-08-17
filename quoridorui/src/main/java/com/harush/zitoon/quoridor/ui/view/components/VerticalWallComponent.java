package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.ui.view.MainGame;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a vertical wall component within the board using the {@link Rectangle} shape.
 */
public class VerticalWallComponent extends Rectangle {
		
	public VerticalWallComponent(int x, int y) {	
		setWidth((MainGame.TILE_SIZE / 10));
		setHeight((MainGame.TILE_SIZE / 5) + 40);
		relocate((x * MainGame.TILE_SIZE) + 45, y * MainGame.TILE_SIZE);
		setFill(Color.rgb(153, 217, 234, 0.8));
	}	
}

