

/**
 * Missed-Shot TileState
 * @author owenmiller
 *
 */
public class MissedState implements TileState {
	
	
	public TileState shootTile() {
		
		return null;	
	}
	
	public TileState occupyTile() {
		
		return null;
	}
	
	public TileState clearTile() {
		
		return new EmptyState();
	}
	
	public String getState() {
		return "Miss";
	}
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI) {
		boardGUI.setGridElement(boardName, tileNumber, this.getState());
	}
	
}
