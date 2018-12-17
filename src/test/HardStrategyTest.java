import static org.junit.Assert.*;

import org.junit.Test;

public class HardStrategyTest {

	private OpponentStrategy strategy;
	boolean wasHit;
	
	@Test
	public void validTileIndexTest() {
		strategy = new HardStrategy();
		wasHit = false;
		int tileShot = strategy.chooseBlock(wasHit);
		assertTrue(tileShot <= 99 && tileShot >= 0);
	}

}
