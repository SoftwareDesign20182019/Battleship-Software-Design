import java.util.ArrayList;

public class Fleet {
	private int sizeOfFleet; 
	private ArrayList<Ship> myFleet;
	
	public Fleet() {
		this.sizeOfFleet = 5;
		this.myFleet = new ArrayList<Ship>();
		buildFleet();

	}
	public Fleet(int sizeOfFleet_) {
		this.sizeOfFleet = sizeOfFleet_;
		this.myFleet = new ArrayList<Ship>();
		buildFleet();
	}
	
	public void placeFleet() {
		
	}
	
	
	public boolean isFleetDestroyed() {
		
		for(int i = 0 ; i<sizeOfFleet; i++) {
			if(!myFleet.get(i).isDestroyed()) {
				return false;
			}
		}
		return true;
	}
	
	public void buildFleet() {
		System.out.println(" has been made");

		for(int i = sizeOfFleet; i>0 ; i--) {
			myFleet.add(new Ship(i));
			System.out.println(myFleet.get(0) +" has been made");
		}
	}
	
	public void printFleetStatus() {
		for(int i = 0 ; i < sizeOfFleet; i++) {
			myFleet.get(i).printStatus();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("HI");
		Fleet testFleet = new Fleet();
		testFleet.printFleetStatus();
		
	}
}
