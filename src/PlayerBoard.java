

import java.util.ArrayList;

/**
 * Player board containing an ArrayList of tiles(100) and methods to 
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
	 * @return	true if tile's new state is HitState()
	 */
	public boolean shootTile(int tileNumber) {
		
		return tileList.get(tileNumber).shootThisTile();
	}
	
	/**
	 * Add a ship using the tile numbers corresponding to the start and
	 * end of the ship.
	 * @param shipStartTileNum	tile at the start of ship
	 * @param shipEndTileNum	tile at the end of ship
	 * @return	true if valid location for ship (none of the tiles 
	 * 			occupied return null).
	 */
	public boolean addShip(int shipStartTileNum, int shipEndTileNum) {
		boolean valid = true;
		
		//If ship is vertical
		if(Math.abs(shipEndTileNum - shipStartTileNum) >= 10) {
			
			//If ship is vertical negatively
			if(shipStartTileNum > shipEndTileNum) {
				
				for(int tileNum = shipStartTileNum; tileNum >= shipEndTileNum; 
					tileNum = tileNum - 10) {
					
					boolean tileValidity = tileList.get(tileNum).occupyThisTile();
					if(tileValidity == false) {
						valid = false;
					}
					
				}
			}
			
			//If ship is vertical positively
			else {
				for(int tileNum = shipStartTileNum; tileNum <= shipEndTileNum; 
						tileNum = tileNum + 10) {
					
					boolean tileValidity = tileList.get(tileNum).occupyThisTile();
					if(tileValidity == false) {
						valid = false;
					}
				}
			}
		}
		
		//If ship is horizontal
		else {
			
			//If ship is horizontal negative
			if(shipStartTileNum > shipEndTileNum) {
				
				for(int tileNum = shipStartTileNum; tileNum >= shipEndTileNum; 
					tileNum = tileNum - 1) {
					
					boolean tileValidity = tileList.get(tileNum).occupyThisTile();
					if(tileValidity == false) {
						valid = false;
					}
				}
			}
			
			//If ship is horizontal positive
			else {
				
				for(int tileNum = shipStartTileNum; tileNum <= shipEndTileNum; 
						tileNum = tileNum + 1) {
						
					boolean tileValidity = tileList.get(tileNum).occupyThisTile();
					if(tileValidity == false) {
						valid = false;
					}
				}
			}
		}
		
		return valid;
	}
}
