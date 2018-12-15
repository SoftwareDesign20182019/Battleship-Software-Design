import java.util.ArrayList;

public interface Player {
	
	public int chooseTile();
	
	public boolean destroyedFleet();
	
	//NEW changed return type to PlayerType
	public Gameboard.PlayerType getType();
	
	public ArrayList<Ship> getComputerFleet();
	
	//public boolean hitTarget(Tile target);
	
	
}
