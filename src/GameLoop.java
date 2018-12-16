import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
	
	ArrayList<Ship> humanFleet;
	private boolean playerDeploy;
	private boolean playerShipStart;
	private boolean playerShipEnd;
	private int shipStartIndex;
	private int shipEndIndex;
	private int currentShip;
	
	public GameLoop(Stage stage, MainMenuGUI mainMenu) {
		this.guiStage = stage;
		this.mainMenu = mainMenu;
		gameOver = false;
		playerTurn = true;
	}
	
	/**
	 * Helper method for newGame, sets opponent's difficulty
	 * @param opponent	player whose difficulty (strategy) user will set
	 */
	public void setOpponentDifficulty(Player opponent) {
		opponentPlayer.setDifficulty(new MediumStrategy());
	}
	
	/**
	 * Helper method for newGame, deploys computer player ships
	 * @param opponent	computer player whose ships are being deployed
	 */
	public void deployOpponentShips(Player opponent) {
		opponentShips = opponent.getFleet();
		
		for(int i = 0; i < opponentShips.size(); i++) {
			int startTileNumber = opponentShips.get(i).getStartTile();
			int endTileNumber = opponentShips.get(i).getEndTile();
			gameBoard.deploy(opponentPlayer.getType(), startTileNumber, endTileNumber, opponentShips.get(i));
		}
	}
	
	public void newGame() {
		boardGUI = new BoardGUI(this, mainMenu);
		gameBoard = new Gameboard(boardGUI);
		boardGUI.start(guiStage);
		gameOver = false;
		//Deploy Computer
		opponentPlayer = new ComputerPlayer(Gameboard.PlayerType.OPPONENT);
		setOpponentDifficulty(opponentPlayer);
		deployOpponentShips(opponentPlayer);
		//Deploy Human
		humanPlayer = new HumanPlayer(Gameboard.PlayerType.HUMAN);
		humanFleet = humanPlayer.getFleet();
		currentShip = 0;
		while(currentShip <= humanFleet.size()) {
			playerDeploy = true;
			playerShipStart = true;
		}
		playerDeploy = false;
		playerTurn = true;
	
	}
	
	public void computerTurn() {
		wasHit = gameBoard.fireShot(opponentPlayer.getType(), opponentPlayer.chooseTile(wasHit));
	}
	
	public void clickResponse(int index) {
		if(playerDeploy) {
			
			if(playerShipStart) {
				shipStartIndex = index;
				playerShipStart = false;
				playerShipEnd = true;
			}
			else if(playerShipEnd) {
				shipEndIndex = index;
				gameBoard.deploy(humanPlayer.getType(), shipStartIndex, shipEndIndex, humanFleet.get(currentShip));
				currentShip++;
				playerShipStart = true;
				playerShipEnd = false;
			}
			
		}
		else if(playerTurn) {
			gameBoard.fireShot(humanPlayer.getType(), index);
		}
		computerTurn();
	}
}
