
public class Ship {
	private int length, numberOfHitsTaken, startTile, endTile;
	private int[] positions;
	private String name;
	
	public Ship(int length_) {
		this.length = length_;
		this.numberOfHitsTaken = 0;
		setName();
	}
	
	public boolean isDestroyed() {
		
		return numberOfHitsTaken == length;
	}
	
	public int getLength() {
		return length;
	}
	
	public void hitRecived() {
		numberOfHitsTaken ++;
	}
	
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
	
	public void printStatus() {
		System.out.println(name  + "  " + numberOfHitsTaken +"/"+ length);
	}
	
	public String toString() {
		return name;
	}
	
	public void setStartIndex(int start) {
		this.startTile = start;
	}
	
	public void setEndIndex(int end) {
		this.endTile = end;
		
	}
	
	//NEW kinda, just making everything a little more streamlined for implementation later
	// 	  in the game loop. these just return the start and end tiles individually
	public int getStartTile() {
		return startTile;
	}
	
	public int getEndTile() {
		return endTile;
	}
	
	
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
