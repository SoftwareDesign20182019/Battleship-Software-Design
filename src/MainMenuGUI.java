

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenuGUI extends Application implements GUI_Interface {
	private LoginGUI loginGUI;
	private GameLoop gameLoop;
	private MainMenuGUI mainMenu;
	private LoadGUI loadGame;
	private RankingsGUI rankings;
	private SettingsGUI settings;
	private InGameMenuGUI inGameMenu;
	private HelpGUI help;
	private SQLAccount account;

	private BackgroundImage backgroundimage;

	public MainMenuGUI(LoginGUI loginGUI) {
		this.loginGUI = loginGUI;
		loadGame = new LoadGUI(this);
		rankings = new RankingsGUI(this);
    	settings = new SettingsGUI(this);
    	help = new HelpGUI();
		mainMenu = this;
		inGameMenu = new InGameMenuGUI(this, loadGame, settings, help);
		backgroundimage = new BackgroundImage(new Image("File:battleship-background.jpg", true),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
	}
	
	public void setSQLAccount(SQLAccount account_) {
		this.account = account_;
	}
	
	public SQLAccount getSQLAccount() {
		return account;
	}
	
	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);

        Background background = new Background(backgroundimage);

        root.setBackground(background);
    	
    	Label battleshipTitle = new Label("BATTLESHIP");
    	Button newGameButton = new Button("New Game");
    	Button loadGameButton = new Button("Load Game");
    	Button rankingsButton = new Button("Rankings");
		Button settingsButton = new Button("Settings");
		Button helpButton = new Button("Help");
		Button signOutButton = new Button("Sign Out");
		Button exitButton = new Button("Quit");

    	battleshipTitle.setFont(new Font("Arial", 60));
    	newGameButton.setFont(new Font("Arial", 20));
    	loadGameButton.setFont(new Font("Arial", 20));
    	rankingsButton.setFont(new Font("Arial", 20));
		settingsButton.setFont(new Font("Arial", 20));
		helpButton.setFont(new Font("Arial", 20));
		signOutButton.setFont(new Font("Arial", 20));
		exitButton.setFont(new Font("Arial", 20));

    	newGameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	gameLoop = new GameLoop(stage, mainMenu);
            	gameLoop.newGame(inGameMenu);
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

		helpButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					help.setPreviousGUI(mainMenu, stage);
					help.start(stage);
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

		exitButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Platform.exit();
			}
		});

    	root.getChildren().addAll(battleshipTitle, newGameButton, loadGameButton, rankingsButton, settingsButton, helpButton, signOutButton);
    	
    	Scene scene = new Scene(root, 800, 500);
		stage.setTitle("Main Menu - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
