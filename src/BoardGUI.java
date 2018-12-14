

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardGUI extends Application {
	private MainMenuGUI mainMenu;
	
	private static final int boardWidth = 10;
	private static final int boardHeight = 10;
	
	private GridPane playerGrid;
	private GridPane opponentGrid;
	
	private Image empty;
	private Image hit;
	private Image miss;
	private Image ship;
	
	private Label shotLabel;
	
	public BoardGUI() {
		
	}
	
	public BoardGUI(MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	@Override
    public void start(Stage stage) {
		initBoard();
		setupUI(stage);
    }

	private void initBoard() {
		empty = new Image("File:empty.png", true);
		hit = new Image("File:hit.png", true);
		miss = new Image("File:miss.png", true);
		ship = new Image("File:ship.png", true);
	}
	
	/**
	 * Sets a grid element to the specified INDEX
	 * @param gridName the grid we would like to change
	 * @param newImage the new image we wish to set
	 */
	public void setGridElement(String gridName, int index, String shotImage) {
		ImageView gridImage = new ImageView();
		switch(shotImage) {
		case "Hit":
			gridImage.setImage(hit);
			System.out.println("Hit");
			break;
		case "Empty":
			gridImage.setImage(empty);
			System.out.println("Empty");
			break;
		case "Miss":
			gridImage.setImage(miss);
			System.out.println("Miss");
			break;
		case "Ship":
			gridImage.setImage(ship);
			System.out.println("Ship");
			break;
		}
		
		int[] cords = convertIndexToCord(index);
		int col = cords[0];
		int row = cords[1];
		
		if(gridName.equals("playerBoard")) {
			playerGrid.add(gridImage, col, row);
			shotLabel.setText("Opponent shot: " + col + "," + row);
		} else if(gridName.equals("opponentBoard")) {
			playerGrid.add(gridImage, col, row);
			shotLabel.setText("Opponent shot: " + col + "," + row);
		} else if(gridName.equals("opponentBoard")) {
			opponentGrid.add(gridImage, col, row);
			shotLabel.setText("Player shot: " + col + "," + row);
		}
	}
	
    private void setupUI(Stage stage) {
    	VBox root = new VBox();
    	root.setAlignment(Pos.BASELINE_CENTER);  	
    	
    	HBox boards = new HBox();
    	
    	boards.setPadding(new Insets(10));
    	boards.setAlignment(Pos.CENTER);
    	
    	//Each of these VBoxes will contain a gridpane(each board), and also a label above each grid
    	VBox playerBoard = new VBox();
    	VBox opponentBoard = new VBox();
    	
    	playerBoard.setPadding(new Insets(20));
    	opponentBoard.setPadding(new Insets(20));
    	
    	playerBoard.setAlignment(Pos.CENTER);
    	opponentBoard.setAlignment(Pos.CENTER);
    	
    	HBox infoPanel = new HBox();
    	infoPanel.setPadding(new Insets(10));
    	infoPanel.setAlignment(Pos.CENTER);

    	//We will add these to the VBox boards
    	playerGrid = new GridPane();
        opponentGrid = new GridPane();
        
        playerGrid.setHgap(5);
        playerGrid.setVgap(5);

        opponentGrid.setHgap(5);
        opponentGrid.setVgap(5);
        
        Label playerFleet = new Label("Player Fleet");
        Label opponentFleet = new Label("Opponent Fleet");
        shotLabel = new Label("");
        
        playerFleet.setAlignment(Pos.BASELINE_CENTER);
        opponentFleet.setAlignment(Pos.BASELINE_CENTER);
        shotLabel.setAlignment(Pos.BASELINE_CENTER);
        
        playerFleet.setPadding(new Insets(10));
        opponentFleet.setPadding(new Insets(10));
        shotLabel.setPadding(new Insets(10));
        
        playerFleet.setFont(new Font("Arial", 24));
        opponentFleet.setFont(new Font("Arial", 24));
        shotLabel.setFont(new Font("Arial", 24));
                
        //Setup player grid with empties
        for(int row = 0; row < boardHeight; row++) {
        	for(int column = 0; column < boardWidth; column++) {
                playerGrid.add(new ImageView(empty), column, row);
            }
    	}
        
        //Setup opponent grid with empties
        for(int row = 0; row < boardHeight; row++) {
        	for(int column = 0; column < boardWidth; column++) {
                opponentGrid.add(new ImageView(empty), column, row);
            }
    	}
        
        opponentGrid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	
            	//TODO We need to check if it is player turn! Game Loop? Player? Where do we check
            	
                Object source = e.getTarget();
                if(source instanceof ImageView) {
                	int col = opponentGrid.getColumnIndex((ImageView)source);
                	int row = opponentGrid.getRowIndex((ImageView)source);
                    setGridElement("opponentGrid", convertCordToIndex(col, row), "Miss");
                }
            }
            });
        
        //Our main VBox, root layout
    	root.getChildren().addAll(boards, infoPanel);
    	//First element in root, HBox. Inside this HBox contains two VBoxes opponentBoard and playerBoard
    	boards.getChildren().addAll(opponentBoard, playerBoard);
        //For both of the boards, we want to add in first the label of the board, followed by the grid
        playerBoard.getChildren().addAll(playerFleet, playerGrid);
        opponentBoard.getChildren().addAll(opponentFleet, opponentGrid);
        //Add all relevant elements to infoPanel
        infoPanel.getChildren().addAll(shotLabel);
        
        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Board - Battleship");
        stage.setScene(scene);
        stage.show();
    }
    
    private int[] convertIndexToCord(int index) {
    	int[] cord = new int[2];
        int col = index % 10;
    	int row = index / 10; 
    	cord[0] = col;
    	cord[1] = row;
    	return cord;
    }
    
    private int convertCordToIndex(int col, int row) {
      	return (row * 10) + col;
    }
}
