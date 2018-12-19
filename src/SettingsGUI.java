

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsGUI extends Application implements GUI_Interface {
	private MainMenuGUI mainMenu;
	private GUI_Interface previousGUI;
	private Stage previousStage;


	public SettingsGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void setPreviousGUI(GUI_Interface previousGUI, Stage previousStage) {
		this.previousGUI = previousGUI;
		this.previousStage = previousStage;
	}

	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
    	root.setAlignment(Pos.CENTER);
    	root.setSpacing(10);
    	
    	HBox volumeHBox = new HBox();
    	HBox musicHBox = new HBox();
    	
    	volumeHBox.setPadding(new Insets(10));
    	musicHBox.setPadding(new Insets(10));
    	
    	volumeHBox.setAlignment(Pos.CENTER);
    	musicHBox.setAlignment(Pos.CENTER); 	
    	
    	Label settingsLabel = new Label("Settings");
    	Label volumeLabel = new Label("Volume");
    	Slider volumeSlider = new Slider();
    	Label musicLabel = new Label("Music");
    	Slider musicSlider = new Slider();
    	CheckBox windowModeCheckBox = new CheckBox("Windowed Mode");
    	Button goBackButton = new Button("Go Back");
    	
    	settingsLabel.setFont(new Font("Arial", 60));
    	volumeLabel.setFont(new Font("Arial", 20));
    	musicLabel.setFont(new Font("Arial", 20));
    	windowModeCheckBox.setFont(new Font("Arial", 20));
    	goBackButton.setFont(new Font("Arial", 20));

    	settingsLabel.setPadding(new Insets(20));
    	volumeLabel.setPadding(new Insets(20));
    	volumeSlider.setPadding(new Insets(20));
    	musicLabel.setPadding(new Insets(20));
    	musicSlider.setPadding(new Insets(20));
    	windowModeCheckBox.setPadding(new Insets(20));
    	goBackButton.setPadding(new Insets(20));
    	
    	volumeHBox.getChildren().addAll(volumeLabel, volumeSlider);
    	musicHBox.getChildren().addAll(musicLabel, musicSlider);
   	
    	goBackButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	try {
					if(previousGUI.getClass().equals(InGameMenuGUI.class)) {
						stage.hide();
					}
					previousGUI.start(previousStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        	});
    	
    	root.getChildren().addAll(settingsLabel, volumeHBox, musicHBox, windowModeCheckBox, goBackButton);
    	
    	Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Settings - Battleship");
        stage.setScene(scene);
        stage.show();
	}
}
