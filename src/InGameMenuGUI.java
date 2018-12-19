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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * InGameMenuGUI is a small pop up window that is diplayed in game to exit the game and to access the help page.
 * @author samdoggett
 */
public class InGameMenuGUI extends Application implements GUI_Interface{
    private MainMenuGUI mainMenu;
    private LoadGUI loadGame;
    private HelpGUI help;
    private InGameMenuGUI inGameMenu;
    private BoardGUI boardGUI;
    private Stage boardStage;

    /**
     * Constructor
     * @param mainMenu setter
     * @param help setter
     */
    public InGameMenuGUI(MainMenuGUI mainMenu, HelpGUI help) {
        this.mainMenu = mainMenu;
        this.loadGame = loadGame;
        this.help = help;
        inGameMenu = this;
    }

    /**
     * Setter
     * @param boardGUI setter
     */
    public void setBoardGUI(BoardGUI boardGUI) {
        this.boardGUI = boardGUI;
    }

    /**
     * Setter
     * @param previousStage setter
     */
    public void setBoardStage (Stage previousStage) {
        this.boardStage = previousStage;
    }

    /**
     * Start sets up the stage and displays it.
     * @param stage stage we are working on
     */
    public void start(Stage stage) {
        StackPane outlineStackPane = new StackPane();
        Rectangle outlineRect = new Rectangle(200, 200);
        outlineRect.setFill(Color.TRANSPARENT);
        outlineRect.setStroke(Color.RED);
        outlineRect.setStrokeWidth(4);

        VBox ingameVBox = new VBox();
        ingameVBox.setSpacing(10);
        ingameVBox.setAlignment(Pos.CENTER);

        Stage inGameMenuStage = new Stage();
        inGameMenuStage.initStyle(StageStyle.UNDECORATED);

        Scene inGameScene = new Scene(outlineStackPane, 200, 200);
        inGameMenuStage.setScene(inGameScene);

        Button helpButton = new Button("Help");
        helpButton.setFont(new Font("Arial", 18));
        helpButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                inGameMenuStage.hide();
                try {
                    help.setPreviousGUI(inGameMenu, stage);
                    help.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        Button exitButton = new Button("Exit Game");
        exitButton.setFont(new Font("Arial", 18));
        exitButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                inGameMenuStage.hide();
                try {
                    mainMenu.start(boardStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        Label closeWindowLabel = new Label("Press ESC to Return to Game");

        inGameScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ESCAPE) {
                    inGameMenuStage.hide();
                    boardGUI.setInGameMenuDisabled();
                }
            }
        });

        ingameVBox.getChildren().addAll(helpButton, exitButton, closeWindowLabel);
        outlineStackPane.getChildren().addAll(outlineRect, ingameVBox);

        inGameMenuStage.setAlwaysOnTop(true);
        inGameMenuStage.show();
    }
}
