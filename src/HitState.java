/**
 * TileState initiated when an occupied tile is shot
 * @author owenmiller
 *
 */
public class HitState implements TileState {
	
	public TileState shootTile() {
		
		return null;
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
		return "Hit";
	}
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI) {
		
		boardGUI.setGridElement(boardName, tileNumber, this.getState());
		
	}
}
