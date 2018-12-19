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
        Label instructionsLabel = new Label("instructions.....");

        Button goBackButton = new Button("Go Back");

        helpLabel.setFont(new Font("Arial", 60));
        instructionsLabel.setFont(new Font("Arial", 20));
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

        root.getChildren().addAll(helpLabel, instructionsLabel, goBackButton);

        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Help - Battleship");
        stage.setScene(scene);
        stage.show();
    }
}
