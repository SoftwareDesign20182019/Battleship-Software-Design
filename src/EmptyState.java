/**
 * TileState initiated when a Tile is unoccupied and has not been shot, or when a Tile has been 
 * occupied but is on the "opponent" PlayerBoard
 * @author owenmiller
 *
 */
public class EmptyState implements TileState {
	
	public TileState shootTile() {
		
		return new MissedState();
	}
	
	public TileState occupyTile() {
		
		return new OccupiedState();
	}
	
	public TileState clearTile() {
		
		return new EmptyState();
	}
	
	public TileState destroyShip() {
		
		return new DestroyedState();
	}
	
	public String getState() {
		
		return "Empty";
	}
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI) {
		boardGUI.setGridElement(boardName, tileNumber, this.getState());
	}
}
