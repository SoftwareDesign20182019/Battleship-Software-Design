import static org.junit.Assert.*;
import org.junit.Test;

public class FleetTest {
	
	private Fleet fleet;
	
	@Test
	public void destroyFleetTest() {
		fleet = new Fleet();
		int fleetSize = fleet.getFleet().size();
		for(int fleetIndex = 0; fleetIndex < fleetSize; fleetIndex++) {
			for(int numberShots = 0; numberShots < fleet.getShip(fleetIndex).getLength(); numberShots++) {
				fleet.getShip(fleetIndex).hitRecived();
			}
		}
		assertTrue(fleet.isFleetDestroyed());
	}
	
	@Test
	public void setFleetSizeInConstructorTest() {
		fleet = new Fleet(10);
		assertTrue(fleet.getFleet().size() == 10);
	}
}
