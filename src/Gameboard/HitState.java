
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

}
