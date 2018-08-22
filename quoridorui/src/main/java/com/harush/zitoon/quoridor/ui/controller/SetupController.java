package com.harush.zitoon.quoridor.ui.controller;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * Handles setup actions
 *
 */
public class SetupController extends AbstractController implements Initializable {
	
	@FXML
	private TableView<Player> multiPlayerTable;
	@FXML
	private TableColumn<Player, String> nameColumn;
	@FXML
	private TableColumn<Player, String> pawnColumn;	
	@FXML
	private AnchorPane multiPlayerPane;
	@FXML
	private Button addPlayerBtn;
	@FXML
	private Button playBtn;
	
	private Color[] defaultColours;
	private int colourIndex;
	private Board board = new Board(Settings.getSingleton().getBoardHeight(), Settings.getSingleton().getBoardWidth());
	
	/**
	 * Action triggered by pressing the back button.
	 * @param event the action
	 */
    @FXML
    private void onBackBtn(ActionEvent event) {
    	Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
    	loadScreen(stage, "mainmenu.fxml");
    }
    
    /**
     * Action triggered by pressing the play button.
     * @param event the action
     */
    @FXML
    private void onPlayBtn(ActionEvent event) {
    	setupGame(multiPlayerTable.getItems());
    }
    
    /**
     * Action triggered by pressing the add player button.
     * Adapted from <a href="http://code.makery.ch/blog/javafx-dialogs-official/">Makery</a>.
     * @param event the action
     */
    @FXML
    private void onAddPlayerBtn(ActionEvent event) {
    	Dialog<Pair<String, String>> popup = new Dialog<>();
    	popup.setTitle("Add a Player");
    	popup.setHeaderText("Add a player to this game session");
    	
    	ButtonType loginButtonType = new ButtonType("Add Player", ButtonData.OK_DONE);
    	popup.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField name = new TextField();
    	name.setPromptText("Name");
    	ColorPicker colorPicker = new ColorPicker(defaultColours[colourIndex]);
    	colorPicker.setPromptText("Pawn colour");

    	grid.add(new Label("Name:"), 0, 0);
    	grid.add(name, 1, 0);
    	grid.add(new Label("Pawn colour:"), 0, 1);
    	grid.add(colorPicker, 1, 1);

    	Node addButton = popup.getDialogPane().lookupButton(loginButtonType);
    	addButton.setDisable(true);

    	name.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(newValue.trim().isEmpty()));

    	popup.getDialogPane().setContent(grid);

    	Platform.runLater(name::requestFocus);
		popup.showAndWait();
    	String hexColor = convertColour(colorPicker);
    	System.out.println(colorPicker.getValue().toString());
    	if(!name.getText().isEmpty() && !hexColor.isEmpty()) {
    		Player player = new HumanPlayer(name.getText(), new PawnLogic(board), hexColor);
    		System.out.println(hexColor);
    		multiPlayerTable.getItems().add(player);
    		colourIndex++;
    	}
    	if(multiPlayerTable.getItems().size() == GameSession.MAX_PLAYERS) {
    		addPlayerBtn.setDisable(true);
    	}
    	
    	if((multiPlayerTable.getItems().size() % 2 == 0) && (multiPlayerTable.getItems().size() != 0)) {
    		playBtn.setDisable(false);
    	} else {
    		playBtn.setDisable(true);
    	}
    	
    }

    @FXML
	private void on1PlayerBtn(ActionEvent actionEvent) {
		List<Player> players = new ArrayList<>(2);
		Player player1 = new HumanPlayer("1" , new PawnLogic(board), "#663366");
		Player player2 = new DumbAIPlayer("2" , new PawnLogic(board), "#b3e6b3");
		players.add(player1);
		players.add(player2);
		setupGame(players);
	}
    
    @FXML
    private void on2PlayerBtn(ActionEvent action) {
    	List<Player> players = new ArrayList<>(2);
    	Player player1 = new HumanPlayer("1" , new PawnLogic(board), "#663366");
    	Player player2 = new HumanPlayer("2" , new PawnLogic(board), "#b3e6b3");
    	players.add(player1);
    	players.add(player2);
    	setupGame(players);
    }
    
    @FXML
    private void on4PlayerBtn(ActionEvent action) {
    	List<Player> players = new ArrayList<>(2);
    	Player player1 = new HumanPlayer("1" , new PawnLogic(board),"#663366");
    	Player player2 = new HumanPlayer("2" , new PawnLogic(board),"#b3e6b3");
    	Player player3 = new HumanPlayer("3" , new PawnLogic(board),"#334db3");
    	Player player4 = new HumanPlayer("4" , new PawnLogic(board),"#ff6666");
    	players.add(player1);
    	players.add(player2);
    	players.add(player3);
    	players.add(player4);
    	setupGame(players);
    } 
    
    /**
     * Sets up a game with {@link Player players}
     * @param players the players
     */
    private void setupGame(List<Player> players) {
    	Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
		new MainGame(stage, board, players);
    	stage.show();
    }
    
    /**
     * Converts a {@link ColorPicker} value to hex.
     * @param colourPicker the {@link ColorPicker}
     * @return the converted colour
     */
    private String convertColour(ColorPicker colourPicker) {
    	int green = (int) (colourPicker.getValue().getGreen() * 255);
    	int blue = (int) (colourPicker.getValue().getBlue() * 255);
    	int red = (int) (colourPicker.getValue().getRed() * 255);
    	return "#" + Integer.toHexString(red) 
    	+ Integer.toHexString(green)
    	+ Integer.toHexString(blue);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		pawnColumn.setCellValueFactory(new PropertyValueFactory<>("pawnColour"));
		pawnColumn.setCellFactory(column -> new TableCell<Player, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : getString());
				setStyle("-fx-background-color:"+getString()+";");
			}

			private String getString() {
				return getItem() == null ? "" : getItem();
			}
		});
		multiPlayerTable.setPlaceholder(new Label("No players yet"));
		defaultColours = new Color[]{Color.valueOf("#663366"), Color.valueOf("#b3e6b3"), Color.valueOf("#334db3"), Color.valueOf("#ff6666")};
		colourIndex = 0;		
		
	}
}
