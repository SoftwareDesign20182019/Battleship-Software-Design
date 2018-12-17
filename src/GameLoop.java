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
	
	private ArrayList<Ship> humanFleet;
	private boolean playerDeploy;
	private boolean playerShipStart;
	private boolean playerShipEnd;
	private int shipStartIndex;
	private int shipEndIndex;
	private int currentShip;
	private double humanShots;
	private double scoreMultiplier;
	
	public GameLoop(Stage stage, MainMenuGUI mainMenu) {
		this.guiStage = stage;
		this.mainMenu = mainMenu;
		gameOver = false;
		playerTurn = true;
		playerShipStart = true;
	}
	
	/**
	 * Helper method for newGame, sets opponent's difficulty
	 * @param opponent	player whose difficulty (strategy) user will set
	 */
	public void setOpponentDifficulty(Player opponent) {
		opponentPlayer.setDifficulty(new HardStrategy());
		scoreMultiplier = 1;
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
	
	
	/**
	 * Contains game loop
	 */
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
		//Check for destroyed fleets, end game
		if(humanPlayer.destroyedFleet() || opponentPlayer.destroyedFleet()) {
			gameOver = true;
		}
	}
	
	/**
	 * Fire computer player shot
	 */
	public void computerTurn() {
		wasHit = gameBoard.fireShot(opponentPlayer, opponentPlayer.chooseTile(wasHit));
	}
	
<<<<<<< HEAD
	/**
	 * Deploy human player's ships. Helper method for clickResponsePlayerBoard(int index)
	 * @param index	tile chosen for deployment
	 */
	private void playerDeployShip(int index) {
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
	
	/**
	 * clickResponse method for opponent board. Fires shot at opponent board and calls 
	 * computerTurn() method
	 * @param index	tile human player chooses to shoot
	 */
=======
>>>>>>> PlayerDeploymentGUI
	public void clickResponseOpponentBoard(int index) {
		if(!playerDeploy) {
			gameBoard.fireShot(humanPlayer, index);
			humanShots++;
			computerTurn();
		}
	}
	
<<<<<<< HEAD
	/**
	 * clickResponse method for player board. Deploys player fleet if playerDeploy is true
	 * @param index	tile chosen for deployment
	 */
	public void clickResponsePlayerBoard(int index) {
		//if playerDeploy = true, deploy ship, otherwise playerTurn
=======
	public boolean clickResponsePlayerBoard(int shipStartIndex, int shipEndIndex, int shipSize) {
>>>>>>> PlayerDeploymentGUI
		playerDeploy = currentShip <= humanFleet.size();
		if(playerDeploy) {
			gameBoard.deploy(humanPlayer.getType(), shipStartIndex, shipEndIndex, humanFleet.get(shipSize));
			return true;
		}
		return false;
	}
	public double getScore() {
		return (17/humanShots)*1000*scoreMultiplier;
	}
}
