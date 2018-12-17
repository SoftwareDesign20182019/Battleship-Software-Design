import static org.junit.Assert.*;

import org.junit.Test;

public class ShipTest {
	
	private Ship ship;

	@Test
	public void destroyShipTest() {
		ship = new Ship(5);
		for(int shots = 0; shots < ship.getLength(); shots++) {
			ship.hitRecived();
		}
		assertTrue(ship.isDestroyed());
	}
	
	@Test
	public void notDestroyShipTest() {
		ship = new Ship(5);
		for(int shots = 0; shots < ship.getLength() - 1; shots++) {
			ship.hitRecived();
		}
		assertTrue(!ship.isDestroyed());
	}
}
