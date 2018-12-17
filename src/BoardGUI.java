
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardGUI extends Application {
	private MainMenuGUI mainMenu;
	private GameLoop gameLoop;

	private static final int sceneWidth = 800;
	private static final int sceneHeight = 500;
	private static final int boardWidth = 10;
	private static final int boardHeight = 10;

	private GridPane playerGrid;
	private GridPane opponentGrid;

	private Image empty;
	private Image hit;
	private Image miss;
	private Image ship;
	private Image deploy;

	private Label playerShotLabel;
	private Label opponentShotLabel;

	private boolean deployPhase;

	private int deploySize;
	private int deployIndex;
	private BoardGUI.Rotation currentRotation;
	private ArrayList<ImageView> tempDisplayShip;

	private enum Rotation {
		NORTH, EAST, SOUTH, WEST;
	}

	public BoardGUI() {
		// Empty constructor, used for tests
	}

	public BoardGUI(GameLoop gameLoop, MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
		this.gameLoop = gameLoop;
		deployPhase = true;
		deploySize = -1;
		deployIndex = -1;
		tempDisplayShip = new ArrayList<ImageView>();
		currentRotation = Rotation.EAST;
	}

	@Override
	public void start(Stage stage) {
		initResources();
		setupUI(stage);
	}

	private void initResources() {
		empty = new Image("File:empty.png", true);
		hit = new Image("File:hit.png", true);
		miss = new Image("File:miss.png", true);
		ship = new Image("File:ship.png", true);
		deploy = new Image("File:deploy.png", true);
	}

	/**
	 * Sets a grid element to the specified INDEX
	 * 
	 * @param gridName the grid we would like to change
	 * @param newImage the new image we wish to set
	 */
	public ImageView setGridElement(String gridName, int index, String shotType) {
		if(index >= 0 && index <= 99) {
			int[] cords = convertIndexToCord(index);
			int col = cords[0];
			int row = cords[1];

			ImageView gridImage = new ImageView();

			switch (shotType) {
			case "Hit":
				gridImage.setImage(hit);
				break;
			case "Empty":
				gridImage.setImage(empty);
				break;
			case "Miss":
				gridImage.setImage(miss);
				break;
			case "Ship":
				gridImage.setImage(ship);
				break;
			case "Deploy":
				gridImage.setImage(deploy);
				break;
			}

			if (gridName.equals("playerBoard")) {
				playerShotLabel.setText("Player " + shotType + ": " + col + "," + row);
				playerGrid.add(gridImage, col, row);
			} else if (gridName.equals("opponentBoard")) {
				opponentShotLabel.setText("Opponent " + shotType + ": " + col + "," + row);
				opponentGrid.add(gridImage, col, row);
			}
			return gridImage;
		}
		return null;
	}

	private boolean setupUI(Stage stage) {
		VBox root = new VBox();
		root.setAlignment(Pos.BASELINE_CENTER);

		Scene scene = new Scene(root, sceneWidth, sceneHeight);
		
		HBox boards = new HBox();

		boards.setPadding(new Insets(10));
		boards.setAlignment(Pos.CENTER);

		// Each of these VBoxes will contain a gridpane(each board), and also a label
		// above each grid
		VBox playerBoard = new VBox();
		VBox opponentBoard = new VBox();

		playerBoard.setPadding(new Insets(20));
		opponentBoard.setPadding(new Insets(20));

		playerBoard.setAlignment(Pos.CENTER);
		opponentBoard.setAlignment(Pos.CENTER);

		StackPane bottomStackPane = new StackPane();

		Rectangle stackPaneFrame = new Rectangle(sceneWidth - 25, 80);
		stackPaneFrame.setFill(Color.TRANSPARENT);
		stackPaneFrame.setStroke(Color.BLACK);

		VBox stackVBox = new VBox();
		stackVBox.setSpacing(10);
		stackVBox.setAlignment(Pos.CENTER);

		Label deployFleetLabel = new Label("Select Ship from Fleet to Deploy");
		deployFleetLabel.setFont(new Font("Arial", 24));

		HBox playerFleetHBox = new HBox();
		playerFleetHBox.setSpacing(20);
		playerFleetHBox.setAlignment(Pos.CENTER);

		ArrayList<StackPane> playerFleetList = setupPlayerFleetHBox(playerFleetHBox);

		for (StackPane stack : playerFleetList) {
			stack.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (deployPhase) {
						switch (playerFleetList.indexOf(stack)) {
						case 0:
							deploySize = 2;
							break;
						case 1:
							deploySize = 3;
							break;
						case 2:
							deploySize = 3;
							break;
						case 3:
							deploySize = 4;
							break;
						case 4:
							deploySize = 5;
							break;
						}
					}
				}
			});
		}

		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.SPACE) {
					switch (currentRotation) {
					case NORTH:
						currentRotation = Rotation.EAST;
						break;
					case EAST:
						currentRotation = Rotation.SOUTH;
						break;
					case SOUTH:
						currentRotation = Rotation.WEST;
						break;
					case WEST:
						currentRotation = Rotation.NORTH;
						break;
					default:
						currentRotation = Rotation.EAST;
						break;
					}
					
					displayTempShip(deployIndex);
				}
			}
		});

		HBox infoPanel = new HBox();
		infoPanel.setPadding(new Insets(10));
		infoPanel.setAlignment(Pos.CENTER);

		// We will add these to the VBox boards
		playerGrid = new GridPane();
		opponentGrid = new GridPane();

		playerGrid.setHgap(5);
		playerGrid.setVgap(5);

		opponentGrid.setHgap(5);
		opponentGrid.setVgap(5);

		Label playerFleetLabel = new Label("Player Fleet");
		Label opponentFleet = new Label("Opponent Fleet");
		playerShotLabel = new Label("");
		opponentShotLabel = new Label("");

		playerFleetLabel.setAlignment(Pos.BASELINE_CENTER);
		opponentFleet.setAlignment(Pos.BASELINE_CENTER);
		playerShotLabel.setAlignment(Pos.BASELINE_CENTER);
		opponentShotLabel.setAlignment(Pos.BASELINE_CENTER);

		playerFleetLabel.setPadding(new Insets(10));
		opponentFleet.setPadding(new Insets(10));
		playerShotLabel.setPadding(new Insets(10));
		opponentShotLabel.setPadding(new Insets(10));

		playerFleetLabel.setFont(new Font("Arial", 24));
		opponentFleet.setFont(new Font("Arial", 24));
		playerShotLabel.setFont(new Font("Arial", 24));
		opponentShotLabel.setFont(new Font("Arial", 24));

		// Setup player grid with empties
		for (int row = 0; row < boardHeight; row++) {
			for (int column = 0; column < boardWidth; column++) {
				playerGrid.add(new ImageView(empty), column, row);
			}
		}

		// Setup opponent grid with empties
		for (int row = 0; row < boardHeight; row++) {
			for (int column = 0; column < boardWidth; column++) {
				opponentGrid.add(new ImageView(empty), column, row);
			}
		}

		playerGrid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Object source = e.getTarget();
				if (deployPhase && source instanceof ImageView) {
					int col = playerGrid.getColumnIndex((ImageView) source);
					int row = playerGrid.getRowIndex((ImageView) source);
					deployPhase = gameLoop.clickResponsePlayerBoard(convertCordToIndex(col, row));
					if (!deployPhase) {
						bottomStackPane.getChildren().remove(playerFleetHBox);
						bottomStackPane.getChildren().add(infoPanel);
					}
				}
			}
		});

		playerGrid.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			ImageView currImage;

			@Override
			public void handle(MouseEvent e) {
				if (deployPhase) {
					Object source = e.getTarget();
					if (source instanceof ImageView) {
						int col = opponentGrid.getColumnIndex((ImageView) source);
						int row = opponentGrid.getRowIndex((ImageView) source);
						deployIndex = convertCordToIndex(col, row);
						
						if ((ImageView)source != currImage && deploySize > 0) {
							currImage = (ImageView)source;
							displayTempShip(deployIndex);
						}
					}
				}
			}

		});

		opponentGrid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Object source = e.getTarget();
				if (!deployPhase && source instanceof ImageView) {
					int col = opponentGrid.getColumnIndex((ImageView) source);
					int row = opponentGrid.getRowIndex((ImageView) source);
					gameLoop.clickResponseOpponentBoard(convertCordToIndex(col, row));
				}
			}
		});

		// Our main VBox, root layout
		root.getChildren().addAll(boards, bottomStackPane);
		// First element in root, HBox. Inside this HBox contains two VBoxes
		// opponentBoard and playerBoard
		boards.getChildren().addAll(opponentBoard, playerBoard);
		// For both of the boards, we want to add in first the label of the board,
		// followed by the grid
		playerBoard.getChildren().addAll(playerFleetLabel, playerGrid);
		opponentBoard.getChildren().addAll(opponentFleet, opponentGrid);
		// Add all relevant elements to infoPanel. infoPanel will not be added to the
		// stackPane by default until the deployment phase is over
		infoPanel.getChildren().addAll(playerShotLabel, opponentShotLabel);
		stackVBox.getChildren().addAll(deployFleetLabel, playerFleetHBox);
		bottomStackPane.getChildren().addAll(stackPaneFrame, stackVBox);
		
		stage.setTitle("Board - Battleship");
		stage.setScene(scene);
		stage.show();
		return true;
	}
	
	private void displayTempShip(int index) {
		for (ImageView image : tempDisplayShip) {
			playerGrid.getChildren().remove(image);
		}
		tempDisplayShip = tempShipSetGridElements(index);
	}

	private int[] tempShipIndexes(int index) {
		int[] indexes = new int[deploySize];

		switch (currentRotation) {
		case NORTH:
			for (int i = 0; i < deploySize; i++) {
				indexes[i] = index - (i * 10);
			}
			break;
		case EAST:
			for (int i = 0; i < deploySize; i++) {
				indexes[i] = index + i;
			}
			break;
		case SOUTH:
			for (int i = 0; i < deploySize; i++) {
				indexes[i] = index + (i * 10);
			}
			break;
		case WEST:
			for (int i = 0; i < deploySize; i++) {
				indexes[i] = index - i;
			}
			break;
		}
		return indexes;
	}

	private ArrayList<ImageView> tempShipSetGridElements(int index) {
		ArrayList<ImageView> tempShip = new ArrayList<ImageView>();

		int[] indexes = tempShipIndexes(index);
		
		boolean edgeOverlap = false;
		int edgeIndex = -1;
		
		for(int t=0; t< indexes.length; t++) {
			int indexRow = index / 10;
			int currIndex = indexes[t] / 10;
			
			switch (currentRotation) {
			case NORTH:
				if(indexes[t] < 0) {
					edgeOverlap = true;
					edgeIndex = t;
				} else {
					edgeOverlap = false;
				}
				break;
			case EAST:
				if(indexRow != currIndex) {
					edgeOverlap = true;
					edgeIndex = t;
				} else {
					edgeOverlap = false;
				}
				break;
			case SOUTH:
				if(indexes[t] > 99) {
					edgeOverlap = true;
					edgeIndex = t;
				} else {
					edgeOverlap = false;
				}
				break;
			case WEST:
				if(indexRow != currIndex) {
					edgeOverlap = true;
					edgeIndex = t;
				} else {
					edgeOverlap = false;
				}
				break;
			}
			if(edgeOverlap) {
				break;
			}
		}

		if(!edgeOverlap) {
			for (int i = 0; i < indexes.length; i++) {
				tempShip.add(setGridElement("playerBoard", indexes[i], "Deploy"));
			}
		} else {
			for (int i = 0; i < edgeIndex; i++) {
				tempShip.add(setGridElement("playerBoard", indexes[i], "Hit"));
			}
		}
		
		return tempShip;
	}

	private int shipStrokeWidth(int numberOfSegments) {
		return (numberOfSegments * 25) + (numberOfSegments * 5) + 20;
	}

	private StackPane createShipStack(int shipLength) {
		int shipStrokeHeight = 35;

		StackPane shipStack = new StackPane();

		HBox shipHBox = new HBox();
		shipHBox.setSpacing(5);
		shipHBox.setAlignment(Pos.CENTER);

		for (int i = 0; i < shipLength; i++) {
			ImageView segment = new ImageView(ship);
			shipHBox.getChildren().add(segment);
		}

		Rectangle shipStroke = new Rectangle(shipStrokeWidth(shipLength), shipStrokeHeight);
		shipStroke.setFill(Color.TRANSPARENT);
		shipStroke.setStroke(Color.RED);

		shipStack.getChildren().addAll(shipStroke, shipHBox);

		return shipStack;
	}

	private ArrayList<StackPane> setupPlayerFleetHBox(HBox playerFleetHBox) {
		ArrayList<StackPane> fleet = new ArrayList<StackPane>();

		StackPane patrol = createShipStack(2);
		StackPane sub = createShipStack(3);
		StackPane destroyer = createShipStack(3);
		StackPane battleship = createShipStack(4);
		StackPane carrier = createShipStack(5);

		fleet.add(patrol);
		fleet.add(sub);
		fleet.add(destroyer);
		fleet.add(battleship);
		fleet.add(carrier);

		playerFleetHBox.getChildren().addAll(patrol, sub, destroyer, battleship, carrier);

		return fleet;
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
