package com.harush.zitoon.quoridor.ui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 
 * Represents an abstract controller.
 *
 */
public abstract class AbstractController {
	
	/**
	 * Loads a new screen and assigns it to the current {@link Stage}.
	 * @param primaryStage the stage
	 * @param layoutFile the name of the layout file
	 */
	protected void loadScreen(Stage primaryStage, String layoutFile){
		try {
			FXMLLoader root =  new FXMLLoader(getClass().getResource("/resources/layouts/" + layoutFile));
			Scene scene = new Scene(root.load());
			primaryStage.setTitle("Quoridor");
			primaryStage.getIcons().add(new Image("resources/icons/favicon.png"));
			primaryStage.setScene(scene);
			primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);
            primaryStage.setResizable(false);
            ((AbstractController)root.getController()).postConstruct();
            primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void postConstruct(){

	}
}
