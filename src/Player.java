import java.util.ArrayList;

public interface Player {
	
	public int chooseTile(boolean wasHit);
	
	public boolean destroyedFleet();
	
	//NEW changed return type to PlayerType
	public Gameboard.PlayerType getType();
	
	public ArrayList<Ship> getComputerFleet();
	
	public void setDifficulty(OpponentStrategy strategy);	
}
