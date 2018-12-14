import static org.junit.Assert.*;

import org.junit.Test;

public class TestTile {
	
	private Tile tile;
	private BoardGUI boardGUI = new BoardGUI();
	
	@Test
	public void shootThisTileOccupiedTest() {
		tile = new Tile(1, new OccupiedState(), boardGUI, "playerBoard");
		assertTrue(tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileEmptyTest() {
		tile = new Tile(1, new EmptyState(), boardGUI, "playerBoard");
		assertTrue(!tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileHitTest() {
		tile = new Tile(1, new HitState(), boardGUI, "playerBoard");
		assertTrue(!tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileMissedTest() {
		tile = new Tile(1, new MissedState(), boardGUI, "playerBoard");
		assertTrue(!tile.shootThisTile());
	}

}
