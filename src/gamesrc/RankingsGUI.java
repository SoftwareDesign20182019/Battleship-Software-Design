import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RankingsGUI extends Application {
	private MainMenuGUI mainMenu;
	private ArrayList<ArrayList> initialList;
	private static final int NUMBER_OF_HIGHSCORES= 10;
	
	public RankingsGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(5);
    	
    	HBox listHBox = new HBox();
    	listHBox.setSpacing(20);
    	listHBox.setAlignment(Pos.CENTER);
    	
    	VBox rankVBox = new VBox();
    	rankVBox.setSpacing(5);
    	rankVBox.setAlignment(Pos.CENTER);
    	
    	VBox usernameVBox = new VBox();
    	usernameVBox.setSpacing(5);
    	usernameVBox.setAlignment(Pos.CENTER);
    	
    	VBox scoreVBox = new VBox();
    	scoreVBox.setSpacing(5);
    	scoreVBox.setAlignment(Pos.CENTER);
       	
    	Label titleLabel = new Label("Battleship Rankings");
    	Label rankLabel = new Label("Rank");
    	Label usernameLabel = new Label("Username");
    	Label scoreLabel = new Label("Score");
    	
    	Button goBackButton = new Button("Go Back");

    	titleLabel.setFont(new Font("Arial", 60));
    	rankLabel.setFont(new Font("Arial", 20));
    	usernameLabel.setFont(new Font("Arial", 20));
    	scoreLabel.setFont(new Font("Arial", 20));
    	goBackButton.setFont(new Font("Arial", 20));
    	
    	goBackButton.setPadding(new Insets(20));
   	
    	goBackButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					mainMenu.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	
    	ListView<Integer> rankList = new ListView<Integer>();
    	ListView<String> usernameList = new ListView<String>();
    	ListView<Integer> scoreList = new ListView<Integer>();
    	
    	ObservableList<Integer> ranks = FXCollections.observableArrayList (
    			1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 42);
    	rankList.setItems(ranks);
    	System.out.println("Perfectly fine");
    	SQLAccount highscoreGetter = mainMenu.getSQLAccount();
    	initialList = highscoreGetter.getHighScores();
    	ObservableList<String> usernames = FXCollections.observableArrayList ();
    	ObservableList<Integer> scores = FXCollections.observableArrayList ();
    	
    	for(int i = 0; i< NUMBER_OF_HIGHSCORES; i++) {
    		
    		scores.add((Integer) initialList.get(0).get(i));
    		usernames.add((String) initialList.get(1).get(i));
    		
    	}
    	
    	usernameList.setItems(usernames);
    	scoreList.setItems(scores);
    	
    	rankList.setPrefWidth(5);
    	usernameList.setPrefWidth(75);
    	scoreList.setPrefWidth(30);
    	
    	rankVBox.getChildren().addAll(rankLabel, rankList);
    	usernameVBox.getChildren().addAll(usernameLabel, usernameList);
    	scoreVBox.getChildren().addAll(scoreLabel, scoreList);
    	
    	listHBox.getChildren().addAll(rankVBox, usernameVBox, scoreVBox);
    	
    	root.getChildren().addAll(titleLabel, listHBox, goBackButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Rankings - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
