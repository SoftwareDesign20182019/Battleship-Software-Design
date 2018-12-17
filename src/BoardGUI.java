
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * BoardGUI handles all of the board graphics!
 * @author SamDoggett
 */

public class BoardGUI extends Application {
	private MainMenuGUI mainMenu;
	private GameLoop gameLoop;

	private static final int sceneWidth = 800;
	private static final int sceneHeight = 500;
	private static final int boardWidth = 10;
	private static final int boardHeight = 10;

	private GridPane playerGrid;
	private GridPane opponentGrid;

	private HBox infoPanel;

	private Image empty;
	private Image hit;
	private Image miss;
	private Image ship;
	private Image deploy;
	private Image destroyed;

	private boolean deployPhase;
	private boolean edgeOverlap;

	private int deploySize;
	private int deployIndex;

	private BoardGUI.Rotation currentRotation;
	private BoardGUI.ShipType shipType;
	private ArrayList<ImageView> tempDisplayShip;

	private enum Rotation {
		NORTH, EAST, SOUTH, WEST;
	}

	private enum ShipType {
		PATROL, SUB, DESTROYER, BATTLESHIP, AIRCRAFTCARRIER;
	}

	public BoardGUI(GameLoop gameLoop, MainMenuGUI mainMenu) {
		this.mainMenu = mainMenu;
		this.gameLoop = gameLoop;
		deployPhase = true;
		deploySize = 0;
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
		destroyed = new Image("File:destroyed.png", true);
	}

	/**
	 * Sets a grid element to the specified INDEX
	 *
	 * @param gridName the grid we would like to change
	 * @param newImage the new image we wish to set
	 */
	public ImageView setGridElement(String gridName, int index, String newImage) {
		if(index >= 0 && index <= 99) {
			int[] cords = convertIndexToCord(index);
			int col = cords[0];
			int row = cords[1];

			ImageView gridImage = new ImageView();

			switch (newImage) {
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
			case "Destroyed":
				gridImage.setImage(destroyed);
				break;
			}

			if (gridName.equals("playerBoard")) {
				playerGrid.add(gridImage, col, row);
			} else if (gridName.equals("opponentBoard")) {
				opponentGrid.add(gridImage, col, row);
			}
			return gridImage;
		}
		return null;
	}

	/**
	* setupUI is the intital UI setup of the board. This includes all of the listeners needed to call other methods in gameloop.
	* @param stage the stage we are working on
	* @return did setup UI complete?
	*/
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

		//The bottom stack pane will be somewhat of a information panel at the bottom of the stage.
		//It will contain either the deployment selection or the information panel.
		StackPane bottomStackPane = new StackPane();

		Rectangle stackPaneFrame = new Rectangle(sceneWidth - 25, 80);
		stackPaneFrame.setFill(Color.TRANSPARENT);
		stackPaneFrame.setStroke(Color.BLACK);

		//this is the vbox that will be the layout for the bottomStackPane
		VBox stackVBox = new VBox();
		stackVBox.setSpacing(10);
		stackVBox.setAlignment(Pos.CENTER);

		Label deployFleetLabel = new Label("Select Ship from Fleet to Deploy");
		deployFleetLabel.setFont(new Font("Arial", 24));

		//this is the deployment selection layout. Will contain 5 ships.
		HBox playerFleetHBox = new HBox();
		playerFleetHBox.setSpacing(20);
		playerFleetHBox.setAlignment(Pos.CENTER);

		//this will contain all of the deployment selections that will be placed in the bottom stack pane
		ArrayList<StackPane> playerFleetList = setupPlayerFleetHBox(playerFleetHBox);

		//boolean value is a deployment selection is active
		boolean[] activeDeployShips = new boolean[5];

		for(int b=0; b<activeDeployShips.length; b++) {
			activeDeployShips[b] = true;
		}

		//for each of the ships in the stack, add a mouse pressed listener to select the ship.
		for (StackPane stack : playerFleetList) {
			stack.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (deployPhase && activeDeployShips[playerFleetList.indexOf(stack)]) {
						switch (playerFleetList.indexOf(stack)) {
						case 0:
							shipType = ShipType.PATROL;
							deploySize = 2;
							break;
						case 1:
							shipType = ShipType.SUB;
							deploySize = 3;
							break;
						case 2:
							shipType = ShipType.DESTROYER;
							deploySize = 3;
							break;
						case 3:
							shipType = ShipType.BATTLESHIP;
							deploySize = 4;
							break;
						case 4:
							shipType = ShipType.AIRCRAFTCARRIER;
							deploySize = 5;
							break;
						}
					}
				}
			});
		}

		//create a scene wide listener that will listen for spacebar key event.
		//when key pressed, switch rotation.
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
		//info panel will replace the deployment selection once all ships are selected
		infoPanel = new HBox();
		infoPanel.setPadding(new Insets(10));
		infoPanel.setAlignment(Pos.CENTER);

		//create the gridpanes that will contain all of the ship images
		playerGrid = new GridPane();
		opponentGrid = new GridPane();

		playerGrid.setHgap(5);
		playerGrid.setVgap(5);

		opponentGrid.setHgap(5);
		opponentGrid.setVgap(5);

		Label playerFleetLabel = new Label("Player Fleet");
		Label opponentFleet = new Label("Opponent Fleet");
		Label placeHolderLabel = new Label("Game Information Placeholder");

		playerFleetLabel.setAlignment(Pos.BASELINE_CENTER);
		opponentFleet.setAlignment(Pos.BASELINE_CENTER);
		placeHolderLabel.setAlignment(Pos.BASELINE_CENTER);

		playerFleetLabel.setPadding(new Insets(10));
		opponentFleet.setPadding(new Insets(10));
		placeHolderLabel.setPadding(new Insets(10));

		playerFleetLabel.setFont(new Font("Arial", 24));
		opponentFleet.setFont(new Font("Arial", 24));
		placeHolderLabel.setFont(new Font("Arial", 24));

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

		//playerGrid event listener that checks during deployPhase if ship is placable
		playerGrid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Object source = e.getTarget();
				if (source instanceof ImageView) {
					ImageView sourceImageView = (ImageView)source;
					if(deployPhase) {
						int col = playerGrid.getColumnIndex((ImageView) source);
						int row = playerGrid.getRowIndex((ImageView) source);
						int startIndex = convertCordToIndex(col, row);
						if(!checkOverlap(startIndex)) {
                            int[] indexes = tempShipIndexes(startIndex);
                            int endIndex = indexes[indexes.length - 1];
                            //this will add a X to the deployment selection
                            switch (shipType) {
                                case PATROL:
                                    activeDeployShips[0] = false;
                                    Line cross1 = new Line(0, 0, shipStrokeWidth(2), 35);
                                    Line cross2 = new Line(0, 35, shipStrokeWidth(2), 0);
                                    playerFleetList.get(0).getChildren().addAll(cross1, cross2);
									deployPhase = gameLoop.clickResponsePlayerBoard(startIndex, endIndex, 4);
									break;
                                case SUB:
                                    activeDeployShips[1] = false;
                                    Line cross3 = new Line(0, 0, shipStrokeWidth(3), 35);
                                    Line cross4 = new Line(0, 35, shipStrokeWidth(3), 0);
                                    playerFleetList.get(1).getChildren().addAll(cross3, cross4);
									deployPhase = gameLoop.clickResponsePlayerBoard(startIndex, endIndex, 3);
									break;
                                case DESTROYER:
                                    activeDeployShips[2] = false;
                                    Line cross5 = new Line(0, 0, shipStrokeWidth(3), 35);
                                    Line cross6 = new Line(0, 35, shipStrokeWidth(3), 0);
                                    playerFleetList.get(2).getChildren().addAll(cross5, cross6);
									deployPhase = gameLoop.clickResponsePlayerBoard(startIndex, endIndex, 2);
									break;
                                case BATTLESHIP:
                                    activeDeployShips[3] = false;
                                    Line cross7 = new Line(0, 0, shipStrokeWidth(4), 35);
                                    Line cross8 = new Line(0, 35, shipStrokeWidth(4), 0);
                                    playerFleetList.get(3).getChildren().addAll(cross7, cross8);
									deployPhase = gameLoop.clickResponsePlayerBoard(startIndex, endIndex, 1);
									break;
                                case AIRCRAFTCARRIER:
                                    activeDeployShips[4] = false;
                                    Line cross9 = new Line(0, 0, shipStrokeWidth(5), 35);
                                    Line cross10 = new Line(0, 35, shipStrokeWidth(5), 0);
                                    playerFleetList.get(4).getChildren().addAll(cross9, cross10);
									deployPhase = gameLoop.clickResponsePlayerBoard(startIndex, endIndex, 0);
									break;
                            }
                            tempDisplayShip.clear();
                            deploySize = 0;
                        }
					}
					if (!deployPhase) {
						bottomStackPane.getChildren().remove(stackVBox);
						bottomStackPane.getChildren().add(infoPanel);
					}
				}
			}
		});

		//during deploy phase, we want a mouse hover to display where the ship would be placed if placed on the current cell
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

		//deals with shots at the opponents board!
		opponentGrid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Object source = e.getTarget();
				if (!deployPhase && source instanceof ImageView && ((ImageView) source).getImage() == empty) {
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
		stackVBox.getChildren().addAll(deployFleetLabel, playerFleetHBox);
		bottomStackPane.getChildren().addAll(stackPaneFrame, stackVBox);

		stage.setTitle("Board - Battleship");
		stage.setScene(scene);
		stage.show();
		return true;
	}

	public void setInfoPanelElements(boolean humanWins, boolean opponentWins, double score) {
		if(humanWins) {
			infoPanel.getChildren().clear();
			Label humanWinsLabel = new Label("You destroyed the opponents fleet! Your score: " + (int)score);
			infoPanel.getChildren().add(humanWinsLabel);
		} else if(opponentWins) {
			infoPanel.getChildren().clear();
			Label opponentWinsLabel = new Label("The opponent destroyed your fleet! Your score: " + (int)score);
			infoPanel.getChildren().add(opponentWinsLabel);
		} else {
			infoPanel.getChildren().clear();

			HBox playerHBox = new HBox();
			playerHBox.setPadding(new Insets(10));
			playerHBox.setAlignment(Pos.CENTER);

			HBox opponentHBox = new HBox();
			opponentHBox.setPadding(new Insets(10));
			opponentHBox.setAlignment(Pos.CENTER);

			Label playerScore = new Label("Current Score: " + (int)score);

			playerHBox.getChildren().add(playerScore);
			infoPanel.getChildren().addAll(opponentHBox, playerHBox);
		}
	}

	private void displayTempShip(int index) {
		for (ImageView image : tempDisplayShip) {
			playerGrid.getChildren().remove(image);
		}
		tempDisplayShip = tempDeployGridElements(index);
	}

	/**
	 * Based on rotation, returns an array of indexes for ship placement
	 * @param index index of the piviot point
	 * @return indexes of each ship segement
	 */
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

    /**
     * check if there is overlap with the ship indexes
     * @param index index of first location
     * @return t/f if there are overlaps
     */
	private boolean checkOverlap(int index) {
		int[] indexes = tempShipIndexes(index);

		for(int t=0; t< indexes.length; t++) {
			int indexRow = index / 10;
			int currIndex = indexes[t] / 10;

			switch (currentRotation) {
				case NORTH:
					if(indexes[t] < 0) {
						return true;
					}
                    break;
                case EAST:
					if(indexRow != currIndex) {
						return true;
					}
                    break;
                case SOUTH:
					if(indexes[t] > 99) {
						return true;
					}
					break;
				case WEST:
					if(indexRow != currIndex) {
						return true;
					}
					break;
			}
		}
		return false;
	}

	/**
	 * This method will display where a ship will be deployed if deployed
	 * at this index + rotation.
	 * @param index selected index
	 * @return ArrayList of ImageViews. Each image view is one of the temp deployment images.
	 */
	private ArrayList<ImageView> tempDeployGridElements(int index) {
		ArrayList<ImageView> tempShip = new ArrayList<ImageView>();
        int[] indexes = tempShipIndexes(index);

        edgeOverlap = false;
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

	/**
	 * Helper Method for createShipStack. Takes in the length of the ship
	 * and returns the size of the stroke rectangle.
	 * @param numberOfSegments length of ship
	 * @return width of the stroke rectangle
	 */
	private int shipStrokeWidth(int numberOfSegments) {
		return (numberOfSegments * 25) + (numberOfSegments * 5) + 20;
	}

	/**
	 * Creates a stack that contains a deploy ship model
	 * @param shipLength length of the deploy ship model
	 * @return Stack pane of model with outline
	 */
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

	/**
	 * Returns an arrayList of stack panes. Each stack pane contains a deploy ship model
	 * that will be place in the bottom stack panel (info panel).
	 * @param playerFleetHBox the HBox we want the stack panes to go into
	 * @return ArrayList of all the stack panes
	 */
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

	/**
	 * converts an index to a col and row
	 * @param index index we want to convert
	 * @return int array containing the col and row
	 */
	private int[] convertIndexToCord(int index) {
		int[] cord = new int[2];
		int col = index % 10;
		int row = index / 10;
		cord[0] = col;
		cord[1] = row;
		return cord;
	}

	/**
	 * converts column and rows into an index
	 * @param col column index
	 * @param row row index
	 * @return combined index
	 */
	private int convertCordToIndex(int col, int row) {
		return (row * 10) + col;
	}
}
