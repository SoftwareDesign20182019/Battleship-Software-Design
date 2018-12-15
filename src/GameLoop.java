import java.util.ArrayList;

import javafx.stage.Stage;

public class GameLoop {
	private BoardGUI boardGUI;
	private MainMenuGUI mainMenu;
	private Gameboard gameBoard;
	private Stage guiStage;

	private Player opponentPlayer;
	private Player humanPlayer;
	
	private ArrayList<Ship> opponentShips;
	private boolean wasHit;

	private boolean gameOver;
	private boolean playerTurn;
	
	public GameLoop(Stage stage, MainMenuGUI mainMenu) {
		this.guiStage = stage;
		this.mainMenu = mainMenu;
		gameOver = false;
		playerTurn = true;
	}
	
	
	public void newGame() {
		boardGUI = new BoardGUI(this, mainMenu);
		gameBoard = new Gameboard(boardGUI);
		opponentPlayer = new ComputerPlayer(Gameboard.PlayerType.OPPONENT);
		opponentPlayer.setDifficulty(new MediumStrategy());
		humanPlayer = new HumanPlayer(Gameboard.PlayerType.HUMAN);
		
		gameOver = false;
		playerTurn = true;

		boardGUI.start(guiStage);
		opponentShips = opponentPlayer.getComputerFleet();
		for(int i = 0; i < opponentShips.size(); i++) {
			int startTileNumber = opponentShips.get(i).getStartTile();
			int endTileNumber = opponentShips.get(i).getEndTile();
			gameBoard.deploy(opponentPlayer.getType(), startTileNumber, endTileNumber);
		}
		
		gameBoard.deploy(humanPlayer.getType(), 10, 40);
		gameBoard.deploy(humanPlayer.getType(), 75, 95);
		gameBoard.deploy(humanPlayer.getType(), 42, 46);	
	}
	
	public void computerTurn() {
		wasHit = gameBoard.fireShot(opponentPlayer.getType(), opponentPlayer.chooseTile(wasHit));
	}
	
	public void clickResponse(int index) {
		gameBoard.fireShot(humanPlayer.getType(), index);
		computerTurn();
	}
}
