
public class DestroyedState implements TileState {
	
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
		
		return null;
	}
	
	public String getState() {
		return "Destroyed";
	}
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI) {
		boardGUI.setGridElement(boardName, tileNumber, this.getState());
	}

}