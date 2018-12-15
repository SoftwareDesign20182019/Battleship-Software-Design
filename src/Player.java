
public interface Player {
	
	public int chooseTile();
	
	public boolean destroyedFleet();
	
	//NEW changed return type to PlayerType
	public Gameboard.PlayerType getType();
	
	//public boolean hitTarget(Tile target);
	
	
}
