package GUI;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuGUI extends Application {
	private LoginGUI loginGUI;
	private BoardGUI newGame;
	private LoadGUI loadGame;
	private RankingsGUI rankings;
	private SettingsGUI settings;

	public MainMenuGUI(LoginGUI loginGUI) {
		this.loginGUI = loginGUI;
		newGame = new BoardGUI(this);
		loadGame = new LoadGUI(this);
		rankings = new RankingsGUI(this);
    	settings = new SettingsGUI(this);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);
    	
    	Label battleshipTitle = new Label("BATTLESHIP");
    	Button newGameButton = new Button("New Game");
    	Button loadGameButton = new Button("Load Game");
    	Button rankingsButton = new Button("Rankings");
    	Button settingsButton = new Button("Settings");
    	Button signOutButton = new Button("Sign Out");
    	
    	battleshipTitle.setFont(new Font("Arial", 60));
    	newGameButton.setFont(new Font("Arial", 20));
    	loadGameButton.setFont(new Font("Arial", 20));
    	rankingsButton.setFont(new Font("Arial", 20));
    	settingsButton.setFont(new Font("Arial", 20));
    	signOutButton.setFont(new Font("Arial", 20));

    	battleshipTitle.setPadding(new Insets(20));
    	newGameButton.setPadding(new Insets(20));
    	loadGameButton.setPadding(new Insets(20));
    	rankingsButton.setPadding(new Insets(20));
    	settingsButton.setPadding(new Insets(20));
    	signOutButton.setPadding(new Insets(20));
    	
    	newGameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	newGame.start(stage);
            }
        	});
    	
    	loadGameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					loadGame.start(stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        	});
    	
    	rankingsButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					rankings.start(stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        	});
    	
    	settingsButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					settings.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	signOutButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					loginGUI.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	root.getChildren().addAll(battleshipTitle, newGameButton, loadGameButton, rankingsButton, settingsButton, signOutButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Main Menu - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
