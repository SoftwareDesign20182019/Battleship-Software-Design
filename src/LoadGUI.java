

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class LoadGUI extends Application implements GUI_Interface {
	private MainMenuGUI mainMenu;
	
	public LoadGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);

		ArrayList<String> getGames = new ArrayList<String>();
		getGames.add("Test1");
		getGames.add("Test2");
    	
    	Label loadGameLabel = new Label("Load Game");
    	root.getChildren().add(loadGameLabel);

    	for(String name : getGames) {
			Button gameButton = new Button(name);
			gameButton.setFont(new Font("Arial", 20));
			gameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					//Handle loading game here
				}
			});
			root.getChildren().add(gameButton);
		}

    	Button goBackButton = new Button("Go Back");
    	
    	loadGameLabel.setFont(new Font("Arial", 60));
    	goBackButton.setFont(new Font("Arial", 20));
    	
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
    	
    	root.getChildren().add(goBackButton);
    	
    	Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Load Game - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
