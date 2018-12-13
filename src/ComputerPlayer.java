
public class ComputerPlayer implements Player {
	
	private Fleet computerFleet;
	private String name;
	
	ComputerPlayer(){
		computerFleet = new Fleet();
		setName();// set difficulty idk
		
	}
	public int chooseTile(){
		return 2;/// place strategy goes here
	
	}
	
	public String getName() {
		return name;
	}
	
	public void setDifficulty() {
		// call Strategy constructor
	}
	
	public void setName() {
		// will return difficulty instead of name
		name = "Easy ComputerPlayer";
	}
	
	public boolean fleetDestroyed() {
		
		return computerFleet.isFleetDestroyed();
	}
	
	public boolean destroyedFleet() {
		return computerFleet.isFleetDestroyed();
	}
	
	//public void hitTarget

}
