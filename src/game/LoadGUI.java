

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

public class LoadGUI extends Application {
	private MainMenuGUI mainMenu;
	
	public LoadGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);
    	
    	Label loadGameLabel = new Label("Load Game");
    	Button testSaveButton1 = new Button("Load Game 123");
    	Button testSaveButton2 = new Button("Load Game 321");
    	Button testSaveButton3 = new Button("Load Game 42");
    	Button goBackButton = new Button("Go Back");
    	
    	loadGameLabel.setFont(new Font("Arial", 60));
    	testSaveButton1.setFont(new Font("Arial", 20));
    	testSaveButton2.setFont(new Font("Arial", 20));
    	testSaveButton3.setFont(new Font("Arial", 20));
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
    	
    	root.getChildren().addAll(loadGameLabel, testSaveButton1, testSaveButton2, testSaveButton3, goBackButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Load Game - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
