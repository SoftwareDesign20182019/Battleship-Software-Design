
public class HumanPlayer implements Player {
	private Fleet myFleet;
	private String name;
	
	HumanPlayer(String name_){
		this.name = name_;
		myFleet = new Fleet();
	}

	public int chooseTile() {
		// gui will handle?
		return 0;
	}

	public boolean destroyedFleet() {
		return myFleet.isFleetDestroyed();
	}

	public String getName() {
		return name;
	}

}
