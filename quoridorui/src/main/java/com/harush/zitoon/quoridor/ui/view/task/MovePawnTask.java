package com.harush.zitoon.quoridor.ui.view.task;

import com.harush.zitoon.quoridor.core.model.LogicResult;
import com.harush.zitoon.quoridor.core.model.Pawn;
import javafx.concurrent.Task;

public class MovePawnTask extends Task<LogicResult> {

  private Pawn pawn;
  private int x;
  private int y;

  public MovePawnTask(Pawn pawn, int x, int y) {
    this.pawn = pawn;
    this.x = x;
    this.y = y;
  }

  @Override
  protected LogicResult call() {
    return pawn.move(x,y);
  }
}
