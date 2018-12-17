import static org.junit.Assert.*;

import org.junit.Test;

public class EasyStrategyTest {
	
	private OpponentStrategy strategy;
	boolean wasHit;
	
	@Test
	public void validTileIndexTest() {
		
		strategy = new EasyStrategy();
		wasHit = true;
		int tileShot = strategy.chooseBlock(wasHit);
		assertTrue(tileShot <= 99 && tileShot >= 0);
	}
}
