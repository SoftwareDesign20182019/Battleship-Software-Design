/**
 * 
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
	
	public boolean fireShot(int playerNumber, int tileNumber) {
		
		boolean tileHit = false;
		switch(playerNumber) {
			case 1:	
				tileHit = playerOneBoard.shootTile(tileNumber);
				break;
			case 2:
				tileHit = playerTwoBoard.shootTile(tileNumber);
				break;
		}
		
		return tileHit;	
	}
	
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
