package com.harush.zitoon.quoridor.ui.controller;

import com.harush.zitoon.quoridor.ui.model.GameSession;
import com.harush.zitoon.quoridor.ui.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController extends AbstractController implements Initializable {
	
	@FXML
	private Label winnerLabel;
	@FXML
	private TextArea statsArea;
	
	private GameSession gameSession;
	
	@FXML
	public void onMenuBtn(ActionEvent action) {
    	Stage stage = (Stage) winnerLabel.getScene().getWindow();
    	loadScreen(stage, "mainmenu.fxml");
	}
	
	public void setGameSession(GameSession gs) {
		this.gameSession = gs;
		winnerLabel.setText(gameSession.getWinner().getName() + " won!");
		for(Player p : gs.getPlayers()) {
			statsArea.appendText("---------- \n");	
			statsArea.appendText(p.getName() + "'s stats \n");
			statsArea.appendText("Walls used: " + p.getStatistics().getNumOfWallsUsed() + "\n");	
			statsArea.appendText("Total moves: " + p.getStatistics().getTotalMoves() + "\n");
			statsArea.appendText("---------- \n");				
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
