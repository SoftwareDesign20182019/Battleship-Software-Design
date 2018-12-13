import static org.junit.Assert.*;

import org.junit.Test;

public class TestTile {
	
	private Tile tile;

	@Test
	public void shootThisTileOccupiedTest() {
		tile = new Tile(1, new OccupiedState());
		assertTrue(tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileEmptyTest() {
		tile = new Tile(1, new EmptyState());
		assertTrue(!tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileHitTest() {
		tile = new Tile(1, new HitState());
		assertTrue(!tile.shootThisTile());
	}
	
	@Test
	public void shootThisTileMissedTest() {
		tile = new Tile(1, new MissedState());
		assertTrue(!tile.shootThisTile());
	}

}
