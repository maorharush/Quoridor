package com.harush.zitoon.quoridor.ui.view.task;

import com.harush.zitoon.quoridor.core.model.LogicResult;
import com.harush.zitoon.quoridor.core.model.Pawn;
import com.harush.zitoon.quoridor.core.model.Wall;
import javafx.concurrent.Task;

public class PlaceWallTask extends Task<LogicResult> {

  private Wall wall;


  public PlaceWallTask(Wall wall) {
    this.wall = wall;
  }

  @Override
  protected LogicResult call() {
    return wall.placeWall();
  }
}
