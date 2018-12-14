

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
		
		//Set GUI ??
		return new OccupiedState();
	}
	
	public TileState clearTile() {
		
		//Set GUI ??
		return new EmptyState();
	}
	
	public String getState() {
		
		//Set GUI ??
		return "Empty";
	}

}