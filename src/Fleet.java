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
		int shipBuilt = 0;
		for(int i = sizeOfFleet; i>0 ; i--) {
			
			myFleet.add(new Ship(i));
			
			System.out.println(myFleet.get(shipBuilt) +" has been made");
			shipBuilt++;
		}
	}
	
	public void printFleetStatus() {
		for(int i = 0 ; i < sizeOfFleet; i++) {
			myFleet.get(i).printStatus();
		}
	}
	
	/**
	 * for testing only will be deleted
	 * @param index
	 * @return
	 */
	public Ship getShip(int index) {
		return myFleet.get(index);
	}
	
	
	public static void main(String[] args) {
		Fleet testFleet = new Fleet();
		testFleet.printFleetStatus();
		testFleet.getShip(0).hitRecived();
		System.out.println();

		testFleet.printFleetStatus();

		
	}
}
