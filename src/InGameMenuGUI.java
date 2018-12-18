import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InGameMenuGUI extends Application {
    private MainMenuGUI mainMenu;
    private Stage currBoardStage;

    public InGameMenuGUI(MainMenuGUI mainMenu, Stage currBoardStage) {
        this.mainMenu = mainMenu;
        this.currBoardStage = currBoardStage;
    }

    public void start(Stage stage) {
        StackPane outlineStackPane = new StackPane();
        Rectangle outlineRect = new Rectangle(200, 200);
        outlineRect.setFill(Color.TRANSPARENT);
        outlineRect.setStroke(Color.RED);
        outlineRect.setStrokeWidth(4);
        VBox ingameVBox = new VBox();
        ingameVBox.setSpacing(5);
        ingameVBox.setAlignment(Pos.CENTER);
        Stage inGameMenuStage = new Stage();
        inGameMenuStage.initStyle(StageStyle.UNDECORATED);
        Scene inGameScene = new Scene(outlineStackPane, 200, 200);
        inGameMenuStage.setScene(inGameScene);
        Button saveGameButton = new Button("Save Game");
        saveGameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

            }
        });
        Button loadGameButton = new Button("Load Game");
        loadGameButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

            }
        });
        Button settingsButton = new Button("Settings");
        settingsButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

            }
        });
        Button helpButton = new Button("Help");
        helpButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

            }
        });
        Button exitButton = new Button("Exit Game");
        exitButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                currBoardStage.hide();
                inGameMenuStage.hide();
                try {
                    mainMenu.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        Label closeWindowLabel = new Label("Press ESC to Return to Game");
        ingameVBox.getChildren().addAll(saveGameButton, loadGameButton, settingsButton, helpButton, exitButton, closeWindowLabel);
        outlineStackPane.getChildren().addAll(outlineRect, ingameVBox);
        inGameMenuStage.show();
        inGameScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ESCAPE) {
                    inGameMenuStage.hide();
                }
            }
        });
    }
}
