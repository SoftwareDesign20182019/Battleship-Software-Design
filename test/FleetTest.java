import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class FleetTest {
	
	private Fleet fleet;
	
	@Test
	public void destroyFleetTest() {
		fleet = new Fleet();
		ArrayList<Ship> fleetList = fleet.getFleet();
		int fleetSize = fleetList.size();
		for(int fleetIndex = 0; fleetIndex < fleetSize; fleetIndex++) {
			for(int numberShots = 0; numberShots < fleetList.get(fleetIndex).getLength(); numberShots++) {
				fleetList.get(fleetIndex).hitRecived();
			}
		}
		assertTrue(fleet.isFleetDestroyed());
	}
}
