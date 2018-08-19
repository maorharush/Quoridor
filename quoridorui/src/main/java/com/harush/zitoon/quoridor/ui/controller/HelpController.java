package com.harush.zitoon.quoridor.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles actions that were made by the help view.
 * @author Moar Harush
 *
 */
public class HelpController extends AbstractController implements Initializable {
	
	@FXML
	private MediaView videoView;
	
	/**
	 * Handles response to on back button action.
	 * @param action
	 */
	@FXML
	private void onBackBtn(ActionEvent action) {
		Stage stage = (Stage) videoView.getScene().getWindow();
		loadScreen(stage, "mainmenu.fxml");
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		MediaPlayer mp = new MediaPlayer(new Media("https://frozen-beach-26335.herokuapp.com/video.mp4"));
		videoView.setMediaPlayer(mp);
		videoView.getMediaPlayer().play();
		
	}

}
