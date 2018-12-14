

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

	public int getNum() {
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
	 * Returns true if tile is empty.
	 */
	public boolean isHit() {
		if(this.toString().equals("hit")) {
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

		boolean hit = false;

		if(this.toString().equals("Ship") || this.toString().equals("Empty")) {
			currentState = currentState.shootTile();
			currentState.setTileGUI(boardName, tileNumber, boardGUI);
			if(this.toString().equals("Hit")) {
				hit = true;
			}
		}

		return hit;
	}


	/**
	 * Change current state according to occupyTile() method interaction with
	 * current state.
	 * @return	true if occupy if valid.
	 */
	public boolean occupyThisTile() {

		if(isFree()) {
			currentState = currentState.occupyTile();
			currentState.setTileGUI(boardName, tileNumber, boardGUI);
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
		currentState.setTileGUI(boardName, tileNumber, boardGUI);
	}

	public String toString() {
		return currentState.getState();
	}


}
