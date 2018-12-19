import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * GameLoop handles all of the games main methods! These methods interact with the appropriate data and view classes.
 * @author WyattNewhall
 * @author OwenMiller
 * @author DrewShipey
 * @author SamDoggett
 */
public class GameLoop {
	private BoardGUI boardGUI;
	private MainMenuGUI mainMenu;
	private Stage guiStage;

	private Gameboard gameBoard;
	private Player humanPlayer;
	private ArrayList<Ship> humanFleet;
	private Player opponentPlayer;
	private ArrayList<Ship> opponentShips;

	private boolean wasHit;

	private MediaPlayer mediaPlayer;
	private boolean audioEnabled;

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

	/**
	 * Constructor
	 * @param stage setter
	 * @param mainMenu setter
	 */
	public GameLoop(Stage stage, MainMenuGUI mainMenu) {
		this.guiStage = stage;
		this.mainMenu = mainMenu;
		playerDeploy = true;
		humanShots = 0;
		humanHits = 0;
		opponentShots = 0;
		opponentHits = 0;
		humanShipsLeft = 5;
		opponentShipsLeft = 5;
		audioEnabled = true;
		mediaPlayer = new MediaPlayer(new Media(Paths.get("hitAudio.mp3").toUri().toString()));
		//Deploy Computer
		opponentPlayer = new ComputerPlayer(Gameboard.PlayerType.OPPONENT);
		//Deploy Human
		humanPlayer = new HumanPlayer(Gameboard.PlayerType.HUMAN);
	}

	/**
	 * Helper method for newGame, sets opponent's difficulty
	 * @param strategy which strategy we want to use
	 * @param scoreMultiplier score multiplier
	 */
	public void setOpponentDifficulty(OpponentStrategy strategy, double scoreMultiplier) {
		opponentPlayer.setDifficulty(strategy);
		this.scoreMultiplier = scoreMultiplier;
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
	 * Sets up the newGame
	 * @param inGameMenu passed to boardGUI
	 */
	public void newGame(InGameMenuGUI inGameMenu) {
		boardGUI = new BoardGUI(this, mainMenu, inGameMenu);
		gameBoard = new Gameboard(boardGUI);
		boardGUI.start(guiStage);
		deployOpponentShips(opponentPlayer);
		humanFleet = humanPlayer.getFleet();
		currentShip = 0;
	}

	/**
	 * Checks for a winner, and updates boardGUI information panel
	 */
	private void updateGameStatus() {
		//Check for destroyed fleets, end game
		if(humanPlayer.destroyedFleet()){
			getScore();
			opponentWins = true;
            boardGUI.setInfoPanelElements(humanWins, opponentWins, humanScore, humanShipsLeft, opponentShipsLeft, humanShots,
                    opponentShots, humanHits, opponentHits, humanHitPercentage, opponentHitPercentage);

		} else if( opponentPlayer.destroyedFleet()) {
			getScore();
			humanWins = true;
            boardGUI.setInfoPanelElements(humanWins, opponentWins, humanScore, humanShipsLeft, opponentShipsLeft, humanShots,
                    opponentShots, humanHits, opponentHits, humanHitPercentage, opponentHitPercentage);

		} else {
            humanShipsLeft = humanPlayer.getShipsLeft();
            opponentShipsLeft = opponentPlayer.getShipsLeft();
            if(humanShots != 0 && opponentShots != 0) {
                humanHitPercentage = ((float)humanHits / (float)humanShots) * 100;
                opponentHitPercentage = ((float)opponentHits / (float)opponentShots) * 100;
            }
            boardGUI.setInfoPanelElements(humanWins, opponentWins, humanScore, humanShipsLeft, opponentShipsLeft, humanShots,
                    opponentShots, humanHits, opponentHits, humanHitPercentage, opponentHitPercentage);
		}
	}

	/**
	 * Fires a computer player shot
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
				if(audioEnabled) {
					Media hit = new Media(Paths.get("hitAudio.mp3").toUri().toString());
					mediaPlayer = new MediaPlayer(hit);
					mediaPlayer.play();
				}
				humanHits++;
			} else if(audioEnabled){
				Media miss = new Media(Paths.get("missAudio.mp3").toUri().toString());
				Media splash = new Media(Paths.get("splashAudio.mp3").toUri().toString());

				mediaPlayer = new MediaPlayer(miss);
				mediaPlayer.play();
				Runnable onEnd = new Runnable() {
					@Override
					public void run() {
						mediaPlayer.dispose();
						mediaPlayer = new MediaPlayer(splash);
						mediaPlayer.play();
					}
				};
				mediaPlayer.setOnEndOfMedia(onEnd);
			}
			humanShots++;
			updateGameStatus();
			computerTurn();
		}
	}

	/**
	 * Sets audio on or off
	 * @param bool state
	 */
	public void audioState(boolean bool){
		if(bool) {
			audioEnabled = true;
		} else {
			audioEnabled = false;
			//mediaPlayer.pause();
			//mediaPlayer.dispose();
		}
	}

	public boolean getAudioState(){
		return audioEnabled;
	}

	/**
	 * Response to player deployment, deploys ship on gameboard and checks if all ships have been deployed.
	 * @param shipStartIndex ship start index for deployment
	 * @param shipEndIndex ship end index for deployment
	 * @param shipType what type of ship is being deployed
	 * @return returns if deployment is over
	 */
	public boolean clickResponsePlayerBoard(int shipStartIndex, int shipEndIndex, int shipType) {
		if(playerDeploy) {
			gameBoard.deploy(humanPlayer.getType(), shipStartIndex, shipEndIndex, humanFleet.get(shipType));
			currentShip++;
			playerDeploy = currentShip < humanFleet.size();
		}
		return playerDeploy;
	}

	/**
	 * Our scoring algorithim! If you get a perfect game on the hardest difficulty you should have 1000 points
	 */
	public void getScore() {
		double score = (17/humanShots)*1000*scoreMultiplier;
		if(opponentPlayer.destroyedFleet()) {
			mainMenu.getSQLAccount().addHighScore(score) ;
			humanScore = score;
		}
	}
}
