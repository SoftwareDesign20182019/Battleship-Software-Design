

/**
 * Tile class contains tile's current state and tile number. Contains methods 
 * to shootThisTile(), occupyThisTile(), and clearThisTile().
 * @author owenmiller
 *
 */
public class Tile {
	
	private TileState currentState;
	private int tileNumber;
	private BoardGUI boardGUI;
	private String boardName;
	
	public Tile(int tileNumber, TileState currentState, BoardGUI boardGUI, String boardName) {
		
		this.tileNumber = tileNumber;
		this.currentState = currentState;
		this.boardGUI = boardGUI;
		this.boardName = boardName;
	}
	
	public int getTileNumber() {
		return tileNumber;
	}
	
	
	/**
	 * Returns true if tile is empty.
	 */
	public boolean isFree() {
		if(this.toString().equals("Empty")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Change current state according to shootTile() method interaction
	 * with current state.
	 * @return	true if new state is HitState(), false otherwise.
	 */
	public boolean shootThisTile() {
		
		if(currentState.shootTile() != null) {
			currentState = currentState.shootTile();
			currentState.setTileGUI(boardGUI, boardName, tileNumber);
			if(this.toString().equals("Hit")) {
				return true;
			}
			else {
				return false;
			}
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
		
		if(isFree()) {
			currentState = currentState.occupyTile();
			currentState.setTileGUI(boardGUI, boardName, tileNumber);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Set tile to empty
	 */
	public void clearThisTile() {
		
		currentState = currentState.clearTile();
		currentState.setTileGUI(boardGUI, boardName, tileNumber);
	}
	
	public String toString() {
		return currentState.getState();
	}
	
	
}
