

/**
 * Gameboard object, contains two PlayerBoard objects and operates on them
 * using methods fireShot(int playerNumber, int tileNumber);
 * deploy(int playerNumber, int shipStartTileNum, int shipEndTileNum);
 * and clearBoard();
 * @author owenmiller
 *
 */
public class Gameboard {
	
	private PlayerBoard playerBoard;
	private PlayerBoard opponentBoard;
	
	
	public Gameboard(BoardGUI boardGUI) {
		
		playerBoard = new PlayerBoard(boardGUI, "playerBoard");
		opponentBoard = new PlayerBoard(boardGUI, "opponentBoard" );	
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
				tileHit = opponentBoard.shootTile(tileNumber);
				break;
			case 2:
				tileHit = playerBoard.shootTile(tileNumber);
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
				deployValid = playerBoard.addShip(shipStartTileNum, shipEndTileNum);
				break;
			case 2:
				deployValid = opponentBoard.addShip(shipStartTileNum, shipEndTileNum);
				break;
		}
		
		return deployValid;
	}
	
	
}
