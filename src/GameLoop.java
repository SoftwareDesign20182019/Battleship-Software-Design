import javafx.stage.Stage;

public class GameLoop {
	private BoardGUI boardGUI;
	private Gameboard gameBoard;
	private Stage guiStage;
	private Gameboard.PlayerType playerType;
	
	public GameLoop(Stage stage) {
		this.guiStage = stage;
	}
	
	public void newGame() {
		boardGUI = new BoardGUI();
		gameBoard = new Gameboard(boardGUI);
		boardGUI.start(guiStage);
		gameBoard.deploy(playerType.HUMAN, 0, 20);
		gameBoard.deploy(playerType.HUMAN, 9, 39);
		//gameBoard.deploy(playerType.HUMAN, 3, 7);
		gameBoard.deploy(playerType.OPPONENT, 1, 21);
		gameBoard.deploy(playerType.OPPONENT, 4, 24);
		//gameBoard.deploy(playerType.OPPONENT, 9, 6);
		
		gameBoard.fireShot(playerType.HUMAN, 1);
		gameBoard.fireShot(playerType.OPPONENT, 4);
		gameBoard.fireShot(playerType.HUMAN, 69);
		gameBoard.fireShot(playerType.OPPONENT, 45);
		
		
	}
}
