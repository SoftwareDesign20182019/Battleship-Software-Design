import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.stage.Stage;

public class GameLoop {
	private BoardGUI boardGUI;
	private MainMenuGUI mainMenu;
	private Stage guiStage;

	private Gameboard gameBoard;
	private Player humanPlayer;
	private ArrayList<Ship> humanFleet;
	private Player opponentPlayer;
	private ArrayList<Ship> opponentShips;

	private boolean playerTurn;
	private boolean wasHit;

	private boolean playerDeploy;
	private int currentShip;

	private int humanShots;
	private int humanHits;
	private double humanHitPercentage;
	private int humanShipsLeft;

	private int opponentShots;
	private int opponentHits;
	private double opponentHitPercentage;
	private int opponentShipsLeft;

	private boolean humanWins;
	private boolean opponentWins;
	private double scoreMultiplier;
	private double humanScore;

	public GameLoop(Stage stage, MainMenuGUI mainMenu) {
		this.guiStage = stage;
		this.mainMenu = mainMenu;
		playerTurn = true;
		playerDeploy = true;
		humanShots = 0;
		humanHits = 0;
		opponentShots = 0;
		opponentHits = 0;
		humanShipsLeft = humanPlayer.getShipsLeft();
		opponentShipsLeft = opponentPlayer.getShipsLeft();
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
		//Deploy Computer
		opponentPlayer = new ComputerPlayer(Gameboard.PlayerType.OPPONENT);
		setOpponentDifficulty(opponentPlayer);
		deployOpponentShips(opponentPlayer);
		//Deploy Human
		humanPlayer = new HumanPlayer(Gameboard.PlayerType.HUMAN);
		humanFleet = humanPlayer.getFleet();
		currentShip = 0;

	}

	private void updateGameStatus() {
		//Check for destroyed fleets, end game
		if(humanPlayer.destroyedFleet()){
			getScore();
			opponentWins = true;
			boardGUI.setInfoPanelElements(humanWins, opponentWins, humanScore);
		} else if( opponentPlayer.destroyedFleet()) {
			getScore();
			humanWins = true;
			boardGUI.setInfoPanelElements(humanWins, opponentWins, humanScore);
		} else {
			boardGUI.setInfoPanelElements(humanWins, opponentWins, humanScore);

		}
		humanShipsLeft = humanPlayer.getShipsLeft();
		opponentShipsLeft = opponentPlayer.getShipsLeft();
		humanHitPercentage = (humanHits / humanShots);
		opponentHitPercentage = (opponentHits / opponentShots);
		//Change to include: humanShipsLeft, opponentShipsLeft, humanShots, opponentShots, humanHits,
		//opponentHits, humanHitPercentage, opponentHitPercentage, getScore()[?]
		boardGUI.setInfoPanelElements(humanWins, opponentWins, getScore());
	}

	/**
	 * Fire computer player shot
	 */
	public void computerTurn() {
		if(!humanWins && !opponentWins) {
			wasHit = gameBoard.fireShot(opponentPlayer, opponentPlayer.chooseTile(wasHit));
			if(wasHit) {
				opponentHits++;
			}
			opponentShots++;
			updateGameStatus();
		}
	}

	/**
	 * clickResponse method for opponent board. Fires shot at opponent board and calls
	 * computerTurn() method
	 * @param index	tile human player chooses to shoot
	 */
	public void clickResponseOpponentBoard(int index) {
		if(!playerDeploy && !humanWins && !opponentWins) {
			boolean humanShotHit = gameBoard.fireShot(humanPlayer, index);
			if(humanShotHit) {
				humanHits++;
			}
			humanShots++;
			updateGameStatus();
			computerTurn();
		}
	}

	public boolean clickResponsePlayerBoard(int shipStartIndex, int shipEndIndex, int shipType) {
		if(playerDeploy) {
			gameBoard.deploy(humanPlayer.getType(), shipStartIndex, shipEndIndex, humanFleet.get(shipType));
			currentShip++;
			playerDeploy = currentShip < humanFleet.size();
		}
		return playerDeploy;
	}

	public void getScore() {
		double score = (17/humanShots)*1000*scoreMultiplier;
		if(opponentPlayer.destroyedFleet()) {
			mainMenu.getSQLAccount().addHighScore(score) ;
			humanScore = score;
		} else {
			//return score;
		}
	}
}
