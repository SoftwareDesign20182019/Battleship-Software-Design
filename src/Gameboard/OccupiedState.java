
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
	
	public String getState() {
		return "Occupied";
	}
	
}
