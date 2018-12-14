

/**
 * Gameboard object, contains two PlayerBoard objects and operates on them
 * using methods fireShot(int playerNumber, int tileNumber);
 * deploy(int playerNumber, int shipStartTileNum, int shipEndTileNum);
 * and clearBoard();
 * @author owenmiller
 *
 */
public class Gameboard {
	
	PlayerBoard playerOneBoard;
	PlayerBoard playerTwoBoard;
	
	public Gameboard() {
		
		playerOneBoard = new PlayerBoard();
		playerTwoBoard = new PlayerBoard();
		
	}
	
	/**
	 * Fire a shot at a specified tile on Player Board (based on player number 
	 * [1 or 2])
	 * @param playerNumber	which player is firing the shot
	 * @param tileNumber	which tile is being shot
	 * @return	true if shot was a success
	 */
	public boolean fireShot(int playerNumber, int tileNumber) {
		
		boolean tileHit = false;
		switch(playerNumber) {
			case 1:	
				tileHit = playerTwoBoard.shootTile(tileNumber);
				break;
			case 2:
				tileHit = playerOneBoard.shootTile(tileNumber);
				break;
		}
		
		return tileHit;	
	}
	
	/**
	 * Deploy a ship on PlayerBoard(based on player number) using the ship's start
	 * tile and end tile.
	 * @param playerNumber	which player is deploying
	 * @param shipStartTileNum	tile at the tip of the ship
	 * @param shipEndTileNum	tile at the end of the ship
	 * @return	true if deploy is valid.
	 */
	public boolean deploy(int playerNumber, int shipStartTileNum,
							int shipEndTileNum) {
		
		boolean deployValid = false;
		switch(playerNumber) {
			case 1:	
				deployValid = playerOneBoard.addShip(shipStartTileNum, shipEndTileNum);
				break;
			case 2:
				deployValid = playerTwoBoard.addShip(shipStartTileNum, shipEndTileNum);
				break;
		}
		
		return deployValid;
	}
	
	
}