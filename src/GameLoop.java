import javafx.stage.Stage;

public class GameLoop {
	private BoardGUI boardGUI;
	private Gameboard gameBoard;
	private Stage guiStage;
	
	public GameLoop(Stage stage) {
		this.guiStage = stage;
	}
	
	public void newGame() {
		boardGUI = new BoardGUI();
		gameBoard = new Gameboard(boardGUI);
		boardGUI.start(guiStage);
		gameBoard.deploy(1, 0, 20);
	}
}
