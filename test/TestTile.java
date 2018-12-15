import static org.junit.Assert.*;

import org.junit.Test;

import javafx.stage.Stage;

public class TestTile {
	
	private Tile tile;
	private BoardGUI boardGUI = new BoardGUI();
	
	@Test
	public void shootThisTileOccupiedTest() {
		boardGUI.start(new Stage());
		tile = new Tile(1, new OccupiedState(), boardGUI, "playerBoard");
		assertTrue(tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileEmptyTest() {
		boardGUI.start(new Stage());
		tile = new Tile(2, new EmptyState(), boardGUI, "playerBoard");
		assertTrue(!tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileHitTest() {
		boardGUI.start(new Stage());
		tile = new Tile(3, new HitState(), boardGUI, "playerBoard");
		assertTrue(!tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileMissedTest() {
		boardGUI.start(new Stage());
		tile = new Tile(4, new MissedState(), boardGUI, "playerBoard");
		assertTrue(!tile.shootThisTile());
	}

}
