

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
	
	public enum PlayerType {
		HUMAN, OPPONENT;
	}
	
	public Gameboard(BoardGUI boardGUI) {
		
		playerBoard = new PlayerBoard(boardGUI, "playerBoard");
		opponentBoard = new PlayerBoard(boardGUI, "opponentBoard");	
	}
	
	/**
	 * Fire a shot at a specified tile on Player Board (based on player number 
	 * [1 or 2])
	 * @param playerNumber	which player is firing the shot
	 * @param tileNumber	which tile is being shot
	 * @return	true if shot was a success
	 */
	public boolean fireShot(PlayerType player, int tileNumber) {
		
		boolean tileHit = false;
		switch(player) {
			case HUMAN:	
				tileHit = opponentBoard.shootTile(tileNumber);
				break;
			case OPPONENT:
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
	public boolean deploy(PlayerType player, int shipStartTileNum,
							int shipEndTileNum) {
		boolean deployValid = false;
		switch(player) {
			case HUMAN:	
				deployValid = playerBoard.addShip(shipStartTileNum, shipEndTileNum);
				break;
			case OPPONENT:
				deployValid = opponentBoard.addShip(shipStartTileNum, shipEndTileNum);
				break;
		}
		
		return deployValid;
	}
	
	
}
