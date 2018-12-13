/**
 * Tile class contains tile's current state and tile number. Contains methods 
 * to shootThisTile(), occupyThisTile(), and clearThisTile().
 * @author owenmiller
 *
 */
public class Tile {
	
	private TileState currentState;
	private int tileNumber;
	
	public Tile(int tileNumber, TileState currentState) {
		
		this.tileNumber = tileNumber;
		this.currentState = currentState;
	}
	
	/**
	 * Change current state according to shootTile() method interaction
	 * with current state.
	 * @return	true if new state is HitState(), false otherwise.
	 */
	public boolean shootThisTile() {
		
		if(currentState.shootTile() != null) {
			currentState = currentState.shootTile();
			return true;
		}
		else {
			return false;	
		}
		
	}
	
	/**
	 * Change current state according to occupyTile() method interaction with
	 * current state.
	 * @return	true if occupy if valid.
	 */
	public boolean occupyThisTile() {
		
		if(currentState.occupyTile() != null) {
			currentState = currentState.occupyTile();
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void clearThisTile() {
		
		currentState = currentState.clearTile();
	}
	
	
}
