
import static org.junit.Assert.*;

import org.junit.Test;

public class TileStateTest {
	
	TileState testState;

	//SHOOT TILE TESTS
	
	@Test
	public void shootEmptyTest() {
		testState = new EmptyState();
		testState = testState.shootTile();
		assertTrue(testState.getState().equals("Miss"));
	}
	
	@Test
	public void shootHitTest() {
		testState = new HitState();
		assertTrue(testState.shootTile() == null);
	}
	
	@Test
	public void shootMissedTest() {
		testState = new MissedState();
		assertTrue(testState.shootTile() == null);
	}
	
	@Test
	public void shootOccupiedTest() {
		testState = new OccupiedState();
		testState = testState.shootTile();
		assertTrue(testState.getState().equals("Hit"));
	}
	
	@Test
	public void shootDestroyedTest() {
		testState = new DestroyedState();
		assertTrue(testState.shootTile() == null);
	}
	
	//OCCUPY TILE TESTS
	
	@Test
	public void occupyEmptyTest() {
		testState = new EmptyState();
		testState = testState.occupyTile();
		assertTrue(testState.getState().equals("Ship"));
	}
	
	@Test
	public void occupyHitTest() {
		testState = new HitState();
		assertTrue(testState.occupyTile() == null);
	}
	
	@Test
	public void occupyMissedTest() {
		testState = new MissedState();
		assertTrue(testState.occupyTile() == null);
	}
	
	@Test
	public void occupyOccupiedTest() {
		testState = new OccupiedState();
		assertTrue(testState.occupyTile() == null);
	}
	
	@Test
	public void occupyDestroyedTest() {
		testState = new DestroyedState();
		assertTrue(testState.occupyTile() == null);
	}
	
	//CLEAR TILE TESTS
	
	@Test
	public void clearEmptyStateTest() {
		testState = new EmptyState();
		testState = testState.clearTile();
		assertTrue(testState.getState().equals("Empty"));
	}
	
	@Test
	public void clearHitStateTest() {
		testState = new HitState();
		testState = testState.clearTile();
		assertTrue(testState.getState().equals("Empty"));
	}
	
	@Test
	public void clearOccupiedStateTest() {
		testState = new OccupiedState();
		testState = testState.clearTile();
		assertTrue(testState.getState().equals("Empty"));
	}
	
	@Test
	public void clearMissedStateTest() {
		testState = new MissedState();
		testState = testState.clearTile();
		assertTrue(testState.getState().equals("Empty"));
	}
	
	@Test
	public void clearDestroyedStateTest() {
		testState = new DestroyedState();
		testState = testState.clearTile();
		assertTrue(testState.getState().equals("Empty"));
	}
	
	//DESTROY TILE TESTS
	
	@Test
	public void destroyEmptyTileTest() {
		testState = new EmptyState();
		testState = testState.destroyShip();
		assertTrue(testState.getState().equals("Destroyed"));
	}
	
	@Test
	public void destroyHitTileTest() {
		testState = new HitState();
		testState = testState.destroyShip();
		assertTrue(testState.getState().equals("Destroyed"));
	}
	
	@Test
	public void destroyMissedTileTest() {
		testState = new MissedState();
		testState = testState.destroyShip();
		assertTrue(testState.getState().equals("Destroyed"));
	}
	
	@Test
	public void destroyOccupiedTileTest() {
		testState = new OccupiedState();
		testState = testState.destroyShip();
		assertTrue(testState.getState().equals("Destroyed"));
	}
	
	@Test
	public void destroyDestroyedTileTest() {
		testState = new DestroyedState();
		assertTrue(testState.destroyShip() == null);
	}
	
	

}
