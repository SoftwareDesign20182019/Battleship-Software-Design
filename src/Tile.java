
public class Tile {
	int position, numberOfNeighborBombs;
	boolean isBomb;
	boolean isHidden;
	boolean isFlag;
	
	/*
	 * Constructor for a Tile
	 * @param int position; where the Tile will be located 
	 */
	Tile(int position_){
		position = position_;
		isHidden = true;
		isFlag = false;
	}
	
	/*
	 * Getter for position
	 * 
	 */
	public int getPosition() {
		return position;
	}
	public void flagTile() {
		if(isFlag && isHidden == true) {
			isFlag = false;
		}
		else if(!isFlag && isHidden) {
			isFlag = true;
		}
			
	}
	public void makeBomb(){
		isBomb = true;
	}
	
	public int getNeighborBombs() {
		return numberOfNeighborBombs;
	}
	public boolean isHidden() {
		return isHidden;
	}
	
	public boolean isBomb() {
		return isBomb;
	}
	
	public void uncover() {
		isHidden = false;
	}
	public void setNumber(int x) {
		numberOfNeighborBombs= x;
	}
	public String toString() {
		if(isFlag) {
			return"F"; 
		}
		else if(isHidden) {
			return "?";
		}
		else if(isBomb) {
			return "b";
		}
		
		else if(!isHidden && !isBomb && (numberOfNeighborBombs > 0)) {
			return Integer.toString(numberOfNeighborBombs);
			
		}
		else if (!isHidden && !isBomb) {
			return" ";
		}
		else {
			return "b";
		}
	}
}
