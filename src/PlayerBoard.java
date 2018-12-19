import java.util.ArrayList;

/**
 * Player board containing an ArrayList of Tiles(100) and methods to 
 * shootTile(int tileNumber); addShip(int shipStartTileNum, int shipEndTileNum);
 * 
 * @author owenmiller
 *
 */
public class PlayerBoard {
	
	private ArrayList<Tile> tileList;
	private String boardName;
	
	
	public PlayerBoard(BoardGUI boardGUI, String boardName) {
		
		tileList = new ArrayList<Tile>();
		this.boardName = boardName;
		
		for(int numTiles = 0; numTiles < 100; numTiles++) {
			
			Tile newTile = new Tile(numTiles, new EmptyState(), boardGUI, boardName);
			tileList.add(newTile);
		}
	}
	
	/**
	 * Set all tiles to empty
	 */
	public void clearBoard() {
		
		for(int tileNum = 0; tileNum < 100; tileNum++) {
			tileList.get(tileNum).clearThisTile();
		}
	}
	
	public String getTileStatus(int tileNumber) {
		
		return tileList.get(tileNumber).toString();
	}
	
	/**
	 * Gets specified tile from tileList and uses shootThisTile() method to
	 * change its state.
	 * @param tileNumber	tile to shoot
	 * @param player player who is shooting
	 * @return	true if tile's new state is HitState()
	 */
	public boolean shootTile(Player player, int tileNumber) {
		
		Tile tileShot = tileList.get(tileNumber);
		boolean shotHit = tileShot.shootThisTile();
		if(shotHit && tileShot.toString().equals("Destroyed")) {
			destroyShip(tileShot);
		}
		
		return shotHit;
		 
	}
	
	/**
	 * Helper method for shootTile(), sets all tiles associated with destroyed ship to destroyed
	 * @param tile	first Tile to be report that its ship was destroyed
	 */
	private void destroyShip(Tile tile) {
		int shipStartTileNum = tile.getShipStart();
		int shipEndTileNum = tile.getShipEnd();
		//If ship is vertical
		
		if(Math.abs(shipEndTileNum - shipStartTileNum) >= 10) {
			
			//If ship is vertical negatively
			if(shipStartTileNum > shipEndTileNum) {
				for(int tileNum = shipStartTileNum; tileNum >= shipEndTileNum; 
						tileNum = tileNum - 10) {
						tileList.get(tileNum).shipDestroyed();
					}
			}
			//Vertical Positive
			else {
				for(int tileNum = shipStartTileNum; tileNum <= shipEndTileNum; 
						tileNum = tileNum + 10) {
					tileList.get(tileNum).shipDestroyed();
				}
			}
		}
		//If ship is horizontal
		else {
			//If ship is horizontal negatively
			if(shipStartTileNum > shipEndTileNum) {
				for(int tileNum = shipStartTileNum; tileNum >= shipEndTileNum; 
						tileNum = tileNum - 1) {
						tileList.get(tileNum).shipDestroyed();
				}
			}
			//Horizontal positive
			else {
				for(int tileNum = shipStartTileNum; tileNum <= shipEndTileNum; 
						tileNum = tileNum + 1) {
					tileList.get(tileNum).shipDestroyed();
				}
			}
		}
	}
	
	/**
	 * Add a ship using the tile numbers corresponding to the start and
	 * end of the ship.
	 * @param 	shipStartTileNum	tile at the start of ship
	 * @param 	shipEndTileNum		tile at the end of ship
	 * @param	ship				Ship occupying tiles
	 * @return	true if valid location for ship (none of the tiles 
	 * 			occupied return null).
	 */
	public boolean addShip(int shipStartTileNum, int shipEndTileNum, Ship ship) {
		ship.setStartIndex(shipStartTileNum);
		ship.setEndIndex(shipEndTileNum);

		boolean valid = true;
		//If ship is vertical
		if(Math.abs(shipEndTileNum - shipStartTileNum) >= 10) {
			//If ship is vertical negatively
			if(shipStartTileNum > shipEndTileNum) {
				valid = addShipVerticalNegative(shipStartTileNum, shipEndTileNum, ship);
			}
			
			else {
				valid = addShipVerticalPositive(shipStartTileNum, shipEndTileNum, ship);
			}
		}
		//If ship is horizontal
		else {
			//If ship is horizontal negatively
			if(shipStartTileNum > shipEndTileNum) {
				valid = addShipHorizontalNegative(shipStartTileNum, shipEndTileNum, ship);
			}
			
			else {
				valid = addShipHorizontalPositive(shipStartTileNum, shipEndTileNum, ship);
			}
		}
		return valid;
	}
	
	/**
	 * Helper method for addShip, add ships based on orientation
	 * @param 	shipStartTileNum	tile at the start of ship
	 * @param 	shipEndTileNum		tile at the end of ship
	 * @param	ship				Ship occupying tiles
	 * @return	true if deploy is valid
	 */
	private boolean addShipVerticalNegative(int shipStartTileNum, int shipEndTileNum, Ship ship) {
		boolean valid = true;
		for(int tileNum = shipStartTileNum; tileNum >= shipEndTileNum; 
				tileNum = tileNum - 10) {
				boolean tileValidity = tileList.get(tileNum).occupyThisTile(ship);
				if(tileValidity == false) {
					valid = false;
				}
			}
		return valid;
	}
	
	/**
	 * Helper method for addShip, add ships based on orientation
	 * @param 	shipStartTileNum	tile at the start of ship
	 * @param 	shipEndTileNum		tile at the end of ship
	 * @param	ship				Ship occupying tiles
	 * @return	true if deploy is valid
	 */
	private boolean addShipVerticalPositive(int shipStartTileNum, int shipEndTileNum, Ship ship) {
		boolean valid = true;
		for(int tileNum = shipStartTileNum; tileNum <= shipEndTileNum; 
				tileNum = tileNum + 10) {
			boolean tileValidity = tileList.get(tileNum).occupyThisTile(ship);
			if(tileValidity == false) {
				valid = false;
			}
		}
		return valid;
		
	}
	
	/**
	 * Helper method for addShip, add ships based on orientation
	 * @param 	shipStartTileNum	tile at the start of ship
	 * @param 	shipEndTileNum		tile at the end of ship
	 * @param	ship				Ship occupying tiles
	 * @return	true if deploy is valid
	 */
	private boolean addShipHorizontalNegative(int shipStartTileNum, int shipEndTileNum, Ship ship) {
		boolean valid = true;
		for(int tileNum = shipStartTileNum; tileNum >= shipEndTileNum; 
				tileNum = tileNum - 1) {
				boolean tileValidity = tileList.get(tileNum).occupyThisTile(ship);
				if(tileValidity == false) {
					valid = false;
				}
			}
		return valid;
	}
	
	/**
	 * Helper method for addShip, add ships based on orientation
	 * @param shipStartTileNum
	 * @param shipEndTileNum
	 * @return	true if deploy is valid
	 */
	private boolean addShipHorizontalPositive(int shipStartTileNum, int shipEndTileNum, Ship ship) {
		boolean valid = true;
		for(int tileNum = shipStartTileNum; tileNum <= shipEndTileNum; 
				tileNum = tileNum + 1) {
			boolean tileValidity = tileList.get(tileNum).occupyThisTile(ship);
			if(tileValidity == false) {
				valid = false; ;
			}
		}
		return valid;
	}
}
