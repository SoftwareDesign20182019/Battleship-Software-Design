/**
 * Empty TileState
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
	
	public String getState() {
		return "Empty";
	}

}
