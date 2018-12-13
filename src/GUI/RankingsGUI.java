package GUI;

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

public class RankingsGUI extends Application {
	private MainMenuGUI mainMenu;
	
	public RankingsGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);
    	
    	Label rankingsLabel = new Label("Battleship Rankings");
    	Button goBackButton = new Button("Go Back");
    	
    	rankingsLabel.setFont(new Font("Arial", 60));
    	goBackButton.setFont(new Font("Arial", 20));

    	rankingsLabel.setPadding(new Insets(20));
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
    	
    	root.getChildren().addAll(rankingsLabel, goBackButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Rankings - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
