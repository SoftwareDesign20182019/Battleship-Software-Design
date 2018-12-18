/**
 * Tile class contains tile's current state and tile number, as well as the ship occupying
 * the Tile, if applicable. Contains methods to shootThisTile(), occupyThisTile(), 
 * and clearThisTile()
 * @author owenmiller
 *
 */
public class Tile {

	private TileState currentState;
	private int tileNumber;
	private BoardGUI boardGUI;
	private String boardName;
	private Ship ship;

	public Tile(int tileNumber, TileState currentState, BoardGUI boardGUI, String boardName) {

		this.tileNumber = tileNumber;
		this.currentState = currentState;
		this.boardGUI = boardGUI;
		this.boardName = boardName;
	}
	
	public int getShipLength() {
		return ship.getLength();
	}
	
	public int getShipStart() {
		return ship.getStartTile();
	}
	
	public int getShipEnd() {
		return ship.getEndTile();
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
	 * Change currentState according to shootTile() method interaction with currentState. If
	 * Tile is occupied, adds a shot to occupying Ship and, if Ship is destroyed, calls shipDestroyed()
	 * @return	true if new state is HitState(), false otherwise.
	 */
	public boolean shootThisTile() {

		boolean hit = false;

		if(this.toString().equals("Ship") || this.toString().equals("Empty")) {
			currentState = currentState.shootTile();
			currentState.setTileGUI(boardName, tileNumber, boardGUI);
			if(this.toString().equals("Hit")) {
				hit = true;
				ship.hitRecived();
				if(ship.isDestroyed()) {
					shipDestroyed();
				}
			}
		}

		return hit;
	}
	
	/**
	 * Applies destroyShip() TileState method to currentMethod, which always results in the 
	 * DestroyedState 
	 */
	public void shipDestroyed() {
		currentState = currentState.destroyShip();
		currentState.setTileGUI(boardName, tileNumber, boardGUI);
	}


	/**
	 * Change currentState according to occupyTile() method interaction with
	 * currentState.
	 * @return	true if occupy if valid.
	 */
	public boolean occupyThisTile(Ship ship) {

		if(isFree()) {
			this.ship = ship;
			currentState = currentState.occupyTile();
			currentState.setTileGUI(boardName, tileNumber, boardGUI);
			return true;
		}
		else {
			return false;
		}

	}

	/**
	 * Applies clearTile() TileState method to currentState, which always results in the EmptyState
	 */
	public void clearThisTile() {

		currentState = currentState.clearTile();
		currentState.setTileGUI(boardName, tileNumber, boardGUI);
	}

	/**
	 * Returns String version of currentState
	 */
	public String toString() {
		return currentState.getState();
	}
}
