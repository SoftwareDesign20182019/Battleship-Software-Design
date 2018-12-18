/**
 * State changing methods for TileState, and method to set the Tile's GUI in real-time
 * @author owenmiller
 *
 */
public interface TileState {
	
	public TileState shootTile();
	
	public TileState occupyTile();
	
	public TileState clearTile();
	
	public TileState destroyShip();
	
	public String getState();
	
	public void setTileGUI(String boardName, int tileNumber, BoardGUI boardGUI);

}
