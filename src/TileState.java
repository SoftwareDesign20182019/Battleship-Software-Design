/**
 * State changing methods for TileState
 * @author owenmiller
 *
 */
public interface TileState {
	
	public TileState shootTile();
	
	public TileState occupyTile();
	
	public TileState clearTile();
	
	public String getState();
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI);

}
