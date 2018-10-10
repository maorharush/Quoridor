package com.harush.zitoon.quoridor.ui.view;

import com.harush.zitoon.quoridor.core.model.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 
 * The initial start point in the game.
 *
 */
public class MainMenu extends Application implements GameScreen {

	@Override
	public void start(Stage primaryStage){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/resources/layouts/mainmenu.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Quoridor");


			primaryStage.setFullScreenExitHint("");
			primaryStage.setFullScreen(true);

			primaryStage.getIcons().add(new Image("resources/icons/favicon.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
			//Setup the default settings
			Settings.getSingleton();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void main(String... args) {
		launch(args);
	}
	
	
	
}