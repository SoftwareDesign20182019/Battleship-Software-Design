

/**
 * Occupied TileState
 * @author owenmiller
 *
 */
public class OccupiedState implements TileState {

	public TileState shootTile() {
		
		 return new HitState();
	}
	
	public TileState occupyTile() {
		
		return null;
	}
	
	public TileState clearTile() {
		
		return new EmptyState();
	}
	
	public TileState destroyShip() {
		return new DestroyedState();
	}
	
	public String getState() {
		return "Ship";
	}
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI) {
		if(boardName.equals("playerBoard")) {
			boardGUI.setGridElement(boardName, tileNumber, this.getState());
		}
	}
	
}
