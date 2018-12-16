
/**
 * Empty TileState
 * @author owenmiller
 *
 */
public class EmptyState implements TileState {
	
	
	public TileState shootTile() {
		
		//Set GUI ??
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
