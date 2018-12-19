/**
 * A Class to make ships
 * @author Drew
 *
 */
public class Ship {
	private int length, numberOfHitsTaken, startTile, endTile;
	private int[] positions;
	private String name;
	
	/**
	 * Constructor for the ship class,
	 * @param length_ - how many tiles a ship takes up
	 */
	public Ship(int length_) {
		this.length = length_;
		this.numberOfHitsTaken = 0;
		setName();
	}
	
	/**
	 * Returns if the ship is destroyed, if the number of hits is equal to its length
	 * @return true if the ship is destroyed
	 */
	public boolean isDestroyed() {
		
		return numberOfHitsTaken == length; 
	}
	
	public int getLength() {
		return length;
	}
	
	/**
	 * Adds a unit of damage to the ship when called
	 */
	public void hitRecived() {
		numberOfHitsTaken ++;
	}
	
	/**
	 * Uses the length of a ship to give the ship its name
	 */
	public void setName() {
		switch(length){
			case 1:
				name = "Patrol Boat";
				length++;
				break;
			case 2:
				length++;
				name = "Submarine";
				break;
			case 3: 
				name = "Destroyer";
				break;
			case 4:
				name = "Battleship";
				break;
			case 5:
				name = "AircraftCarrier";
				break;
			default:
				for(int i = 0; i < (length-5); i++) {
					name += "Super";
				}
				name += "AircraftCarrier";
		}
	}
		
	public String toString() {
		return name;
	}
	
	/**
	 * Sets the first tile of the Ship
	 * @param start where the ship starts
	 */
	public void setStartIndex(int start) {
		this.startTile = start;
	}
	
	/**
	 * Sets the endpoint of the ship
	 * @param end - endpoint of the ship
	 */
	public void setEndIndex(int end) {
		this.endTile = end;
		
	}
	
	
	public int getStartTile() {
		return startTile;
	}
	
	public int getEndTile() {
		return endTile;
	}
	
	/**
	 * Takes the start and end tile and fills in the space in between
	 */
	public void setPositions() {
		int counter = startTile;
		int increment;
		this.positions = new int[length];
		if(startTile - endTile >=10) {
			increment = 10;
		}
		else {
			increment = 1;
		}
		
		
		for(int i = 0; i < positions.length; i++) {
			positions[i] = counter;
			counter += increment;
		}
		
		
	}
	
	/**
	 * Return the tile indexes that the ship takes up;
	 * @return - list of indexes
	 */
	public int[] getPositions() {
		try { 
			setPositions();
			return positions;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
