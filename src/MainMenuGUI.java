

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The MainMenuGUI class creates the main menu stage.
 * @author samdoggett
 */
public class MainMenuGUI extends Application implements GUI_Interface {
	private LoginGUI loginGUI;
	private GameLoop gameLoop;
	private MainMenuGUI mainMenu;
	private RankingsGUI rankings;
	private InGameMenuGUI inGameMenu;
	private HelpGUI help;
	private SQLAccount account;

	private BackgroundImage backgroundimage;

	/**
	 * Constructor
	 * @param loginGUI the LoginGUI incase we want to sign out
	 */
	public MainMenuGUI(LoginGUI loginGUI) {
		this.loginGUI = loginGUI;
		rankings = new RankingsGUI(this);
    	help = new HelpGUI();
		mainMenu = this;
		inGameMenu = new InGameMenuGUI(this, help);
		backgroundimage = new BackgroundImage(new Image("File:battleship-background.jpg", true),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
	}

	/**
	 * Setter
	 * @param account_ account
	 */
	public void setSQLAccount(SQLAccount account_) {
		this.account = account_;
	}

	/**
	 * Getter
	 * @return account
	 */
	public SQLAccount getSQLAccount() {
		return account;
	}

	/**
	 * Start method sets up stage and shows it
	 * @param stage stage we are working on
	 */
	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);

        Background background = new Background(backgroundimage);

        root.setBackground(background);
    	
    	Label battleshipTitle = new Label("BATTLESHIP");
    	Button newGameButton = new Button("New Game");
    	Button rankingsButton = new Button("Rankings");
		Button helpButton = new Button("Help");
		Button signOutButton = new Button("Sign Out");
		Button exitButton = new Button("Quit");

    	battleshipTitle.setFont(new Font("Arial", 60));
    	newGameButton.setFont(new Font("Arial", 20));
    	rankingsButton.setFont(new Font("Arial", 20));
		helpButton.setFont(new Font("Arial", 20));
		signOutButton.setFont(new Font("Arial", 20));
		exitButton.setFont(new Font("Arial", 20));

		newGameButton.setPrefWidth(150);
		rankingsButton.setPrefWidth(150);
		helpButton.setPrefWidth(150);
		signOutButton.setPrefWidth(150);
		exitButton.setPrefWidth(150);

		newGameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	//This will create a dialog (alert) window that will ask the user what difficulty they want to use
				//And will start a new game in game loop
				List<String> difficulties = new ArrayList<>();
				difficulties.add("Seaman");
				difficulties.add("Lieutenant");
				difficulties.add("Captain");
				difficulties.add("Admiral");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("Lieutenant", difficulties);
				dialog.setTitle("Difficulty");
				dialog.setHeaderText(null);
				dialog.setContentText("Select a Difficulty");
				dialog.setGraphic(new ImageView(new Image("File:us_navy.png", true)));
				dialog.getDialogPane().setPrefSize(300, 125);
				dialog.initStyle(StageStyle.UTILITY);

				Optional<String> result = dialog.showAndWait();
				result.ifPresent(difficulty -> {
					switch(difficulty) {
						case "Seaman":
							gameLoop = new GameLoop(stage, mainMenu);
							gameLoop.setOpponentDifficulty(new EasyStrategy(), 0.25);
							gameLoop.newGame(inGameMenu);
							break;
						case "Lieutenant":
							gameLoop = new GameLoop(stage, mainMenu);
							gameLoop.setOpponentDifficulty(new MediumStrategy(), 0.5);
							gameLoop.newGame(inGameMenu);
							break;
						case "Captain":
							gameLoop = new GameLoop(stage, mainMenu);
							gameLoop.setOpponentDifficulty(new HardStrategy(), 0.75);
							gameLoop.newGame(inGameMenu);
							break;
						case "Admiral":
							gameLoop = new GameLoop(stage, mainMenu);
							gameLoop.setOpponentDifficulty(new AIStrategy(), 1.0);
							gameLoop.newGame(inGameMenu);
							break;
					}
				});
            }
        	});
    	
    	rankingsButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					rankings.start(stage);
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

    	root.getChildren().addAll(battleshipTitle, newGameButton, rankingsButton, helpButton, signOutButton, exitButton);
    	
    	Scene scene = new Scene(root, 800, 500);
		stage.setTitle("Main Menu - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
