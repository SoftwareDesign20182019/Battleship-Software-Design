import javafx.stage.Stage;

public class GameLoop {
	private BoardGUI boardGUI;
	private MainMenuGUI mainMenu;
	private Gameboard gameBoard;
	private Stage guiStage;
	private Gameboard.PlayerType playerType;
	
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
		
		gameOver = false;
		playerTurn = true;

		boardGUI.start(guiStage);
		gameBoard.deploy(playerType.HUMAN, 23, 53);
		gameBoard.deploy(playerType.HUMAN, 27, 57);
		gameBoard.deploy(playerType.HUMAN, 72, 78);
		gameBoard.deploy(playerType.OPPONENT, 1, 21);
		gameBoard.deploy(playerType.OPPONENT, 4, 24);
		gameBoard.deploy(playerType.OPPONENT, 9, 6);
	}
	
	public void computerTurn() {
		gameBoard.fireShot(playerType.OPPONENT, (int)(Math.random() * 100));
	}
	
	public void clickResponse(int index) {
		gameBoard.fireShot(playerType.HUMAN, index);
		computerTurn();
	}
}
