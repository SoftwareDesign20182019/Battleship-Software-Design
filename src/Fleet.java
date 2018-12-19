import java.util.ArrayList;

/**
 * This class is responsible for holding the fleet for a player, which is an arraylist of Ships
 * @author Drew
 *
 */
public class Fleet {
	private int sizeOfFleet; 
	private ArrayList<Ship> myFleet;
	
	/**
	 * Constructor for a Fleet object, constructs 5 ships
	 */
	public Fleet() {
		this.sizeOfFleet = 5;
		this.myFleet = new ArrayList<Ship>();
		buildFleet();

	}
	
	
	public ArrayList<Ship> getFleet(){
		return myFleet;
	}
	
	/**
	 * Returns the number of ships that have not been destroyed s
	 * @return the remaining ships
	 */
	public int getAliveShips() {
		int shipsLeft = 0;
		for(int shipIndex = 0; shipIndex < myFleet.size(); shipIndex++) {
			if(!myFleet.get(shipIndex).isDestroyed()) {
				shipsLeft++;
			}
		}
		return shipsLeft;
	}
	
	/**
	 * Checks to see if all ships in fleet are destroyed
	 * @return	true if all ships destroyed
	 */
	public boolean isFleetDestroyed() {
		
		for(int i = 0 ; i<sizeOfFleet; i++) {
			if(!myFleet.get(i).isDestroyed()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Initialize all ships in fleet
	 */
	private void buildFleet() {
		int shipBuilt = 0;
		for(int i = sizeOfFleet; i>0 ; i--) {
			
			myFleet.add(new Ship(i));
			
			shipBuilt++;
		}
	}
	

}
