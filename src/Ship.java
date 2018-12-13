
public class Ship {
	private int length, numberOfHitsTaken;
	private String name;
	
	public Ship(int length_) {
		this.length = length_;
		this.numberOfHitsTaken = 0;
		setName();
	}
	
	public boolean isDestroyed() {
		
		return numberOfHitsTaken == length;
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
	
}
