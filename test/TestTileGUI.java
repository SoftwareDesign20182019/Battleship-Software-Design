import static org.junit.Assert.*;

import org.junit.Test;

public class TestTileGUI {

	@Test
	public void testDeploy() {
		BoardGUI boardGUI = new BoardGUI();
		Gameboard gameBoard = new Gameboard(boardGUI);
		
		assertTrue("Deploy Works!", gameBoard.deploy(1, 0, 20));
	}
	
	@Test
	public void testHit() {
		BoardGUI boardGUI = new BoardGUI();
		Gameboard gameBoard = new Gameboard(boardGUI);
		gameBoard.deploy(1, 0, 20);
		assertTrue("Deploy Works!", gameBoard.fireShot(2, 0));
	}

}
