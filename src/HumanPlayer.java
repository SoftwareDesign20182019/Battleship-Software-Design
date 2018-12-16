import java.util.ArrayList;

public class HumanPlayer implements Player {
	private Fleet myFleet;
	//NEW changed String name to local PlayerType
	private Gameboard.PlayerType playerType;
	
	private int score;
	
	HumanPlayer(Gameboard.PlayerType playerType){
		
		//NEW setting local playerType to param playerType on construction
		this.playerType = playerType;
		myFleet = new Fleet();
		score = 0;
	}

	public int chooseTile(boolean wasHit) {
		// gui will handle?
		return 0;
	}

	public boolean destroyedFleet() {
		return myFleet.isFleetDestroyed();
	}

	public Gameboard.PlayerType getType() {
		return playerType;
	}
	
	public ArrayList<Ship> getFleet() {
		return myFleet.getFleet();
	}

	public void setDifficulty(OpponentStrategy strategy) {
		// TODO Auto-generated method stub
	}
	
	public int getScore() {
		return score;
	}
	
	public void addToScore(int addPoints) {
		score = score + addPoints;
	}

}
