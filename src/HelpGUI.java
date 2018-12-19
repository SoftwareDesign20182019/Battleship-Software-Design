import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * HelpGUI displays basic help information to users.
 * @author samdoggett
 */
public class HelpGUI extends Application implements  GUI_Interface{
    private GUI_Interface previousGUI;
    private Stage previousStage;

    /**
     * Sets the previous GUI and stage to return too
     * @param previousGUI setter
     * @param previousStage setter
     */
    public void setPreviousGUI(GUI_Interface previousGUI, Stage previousStage) {
        this.previousGUI = previousGUI;
        this.previousStage = previousStage;
    }

    /**
     * Start sets up the stage and displays it.
     * @param stage stage we are working on
     */
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

        VBox bottomVBox = new VBox();
        bottomVBox.setAlignment(Pos.CENTER);
        bottomVBox.setSpacing(15);

        Label tileTypeLabel = new Label("Tile Types");
        tileTypeLabel.setFont(new Font("Arial", 24));

        HBox tileType = new HBox();
        tileType.setSpacing(25);
        tileType.setAlignment(Pos.CENTER);

        VBox emptyVBox = new VBox();
        emptyVBox.setSpacing(10);
        emptyVBox.setAlignment(Pos.CENTER);

        ImageView empty = new ImageView(new Image("File:empty.png", true));
        Label emptyLabel = new Label("Empty");
        emptyVBox.getChildren().addAll(empty, emptyLabel);

        VBox deployVBox = new VBox();
        deployVBox.setSpacing(10);
        deployVBox.setAlignment(Pos.CENTER);

        ImageView deploy = new ImageView(new Image("File:deploy.png", true));
        Label deployLabel = new Label("Deploy");
        deployVBox.getChildren().addAll(deploy, deployLabel);

        VBox shipVBox = new VBox();
        shipVBox.setSpacing(10);
        shipVBox.setAlignment(Pos.CENTER);

        ImageView ship = new ImageView(new Image("File:ship.png", true));
        Label shipLabel = new Label("Ship");
        shipVBox.getChildren().addAll(ship, shipLabel);

        VBox destroyedVBox = new VBox();
        destroyedVBox.setSpacing(10);
        destroyedVBox.setAlignment(Pos.CENTER);

        ImageView destroyed = new ImageView(new Image("File:destroyed.png", true));
        Label destroyedLabel = new Label("Destroyed");
        destroyedVBox.getChildren().addAll(destroyed, destroyedLabel);

        VBox hitVBox = new VBox();
        hitVBox.setSpacing(10);
        hitVBox.setAlignment(Pos.CENTER);

        ImageView hit = new ImageView(new Image("File:hit.png", true));
        Label hitLabel = new Label("Hit/Non-Deployable ");
        hitVBox.getChildren().addAll(hit, hitLabel);

        VBox missVBox = new VBox();
        missVBox.setSpacing(10);
        missVBox.setAlignment(Pos.CENTER);

        ImageView miss = new ImageView(new Image("File:miss.png", true));
        Label missLabel = new Label("Miss");
        missVBox.getChildren().addAll(miss, missLabel);

        tileType.getChildren().addAll(emptyVBox, deployVBox, shipVBox, destroyedVBox, hitVBox, missVBox);

        Button goBackButton = new Button("Go Back");

        helpLabel.setFont(new Font("Arial", 60));
        instructionsLabel1.setFont(new Font("Arial", 14));
        instructionsLabel2.setFont(new Font("Arial", 14));
        instructionsLabel3.setFont(new Font("Arial", 14));
        instructionsLabel4.setFont(new Font("Arial", 14));
        instructionsLabel5.setFont(new Font("Arial", 14));
        instructionsLabel6.setFont(new Font("Arial", 14));
        instructionsLabel7.setFont(new Font("Arial", 14));

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

        bottomVBox.getChildren().addAll(tileTypeLabel, tileType);
        root.getChildren().addAll(helpLabel, instructionsLabel1, instructionsLabel2, instructionsLabel3, instructionsLabel4, instructionsLabel5, instructionsLabel6, instructionsLabel7, bottomVBox, goBackButton);

        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Help - Battleship");
        stage.setScene(scene);
        stage.show();
    }
}
