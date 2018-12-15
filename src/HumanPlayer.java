import java.util.ArrayList;

public class HumanPlayer implements Player {
	private Fleet myFleet;
	//NEW changed String name to local PlayerType
	private Gameboard.PlayerType playerType;
	
	HumanPlayer(Gameboard.PlayerType playerType){
		
		//NEW setting local playerType to param playerType on construction
		this.playerType = playerType;
		myFleet = new Fleet();
	}

	public int chooseTile() {
		// gui will handle?
		return 0;
	}

	public boolean destroyedFleet() {
		return myFleet.isFleetDestroyed();
	}

	public Gameboard.PlayerType getType() {
		return playerType;
	}
	
	public ArrayList<Ship> getComputerFleet() {
		return null;
	}

}
