import java.util.ArrayList;

public interface Player {
	
	/**
	 * Choose tile to fire at
	 * @param	wasHit	whether or not last shot was a hit
	 * @return	tile index to fire at
	 */
	public int chooseTile(boolean wasHit);
	
	/**
	 * Checks if player fleet is destroyed
	 * @return	true if all ships are destroyed
	 */
	public boolean destroyedFleet();
	
	public Gameboard.PlayerType getType();
	
	/**
	 * @return	ArrayList containing Player's fleet
	 */
	public ArrayList<Ship> getFleet();
	
	public void setDifficulty(OpponentStrategy strategy);
	
}
