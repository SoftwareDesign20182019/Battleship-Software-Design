import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelpGUI extends Application {
    private GUI_Interface previousGUI;
    private Stage previousStage;

    public void setPreviousGUI(GUI_Interface previousGUI, Stage previousStage) {
        this.previousGUI = previousGUI;
        this.previousStage = previousStage;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);

        Label helpLabel = new Label("Help");
        Label instructionsLabel1 = new Label("Select New Game from the Main Menu and choose a difficulty to start a new game.");
        Label instructionsLabel2 = new Label("During the deployment phase of the game, CLICK on a ship to select it, and hover.");
        Label instructionsLabel3 = new Label("over the player's board, and CLICK to place a ship (only in valid locations,");
        Label instructionsLabel4 = new Label("represented by green tiles). Pressing SPACEBAR will rotate the ship's position clockwise.");
        Label instructionsLabel5 = new Label("Once placing all five ships, the deployment phase is over. CLICK on a tile on the");
        Label instructionsLabel6 = new Label("opponents board to fire the first shot of the game. The game will continue until either");
        Label instructionsLabel7 = new Label("the computer's fleet is completely destroyed, or until the user's fleet is destoryed.");
        Button goBackButton = new Button("Go Back");

        helpLabel.setFont(new Font("Arial", 60));
        instructionsLabel1.setFont(new Font("Arial", 20));
        instructionsLabel2.setFont(new Font("Arial", 20));
        instructionsLabel3.setFont(new Font("Arial", 20));
        instructionsLabel4.setFont(new Font("Arial", 20));
        instructionsLabel5.setFont(new Font("Arial", 20));
        instructionsLabel6.setFont(new Font("Arial", 20));
        instructionsLabel7.setFont(new Font("Arial", 20));

        goBackButton.setFont(new Font("Arial", 20));

        goBackButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(previousGUI.getClass().equals(InGameMenuGUI.class)) {
                    stage.hide();
                }
                previousGUI.start(previousStage);
            }
        });

        root.getChildren().addAll(helpLabel, instructionsLabel1, instructionsLabel2, instructionsLabel3, instructionsLabel4, instructionsLabel5, instructionsLabel6, instructionsLabel7, goBackButton);

        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Help - Battleship");
        stage.setScene(scene);
        stage.show();
    }
}
