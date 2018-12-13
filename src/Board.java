import java.util.Random;
import java.util.ArrayList;

public class Board {
	ArrayList<Tile> map = new ArrayList<Tile>();
	String spaces;
	int height, width, numBombs, totalTiles;
	Board(int height_, int width_, int numBombs_){
		if(height > 500) {
			height = 500;
			System.out.println("Max Height is 500");
		}
		if(width > 26) {
			width = 26;
			System.out.println("Max width is 26");
		}
		height = height_;
		width = width_;
		totalTiles = height * width;
		System.out.println(totalTiles);
		numBombs = numBombs_;
		createMap();
	}
	/**
	 * Checks for number of hidden tiles remaining, used to compare against numBombs for 
	 * win condition
	 * @return number of hidden tiles left
	 */
	public int getNumHidden() {
		int count = 0;
		for(int i = 0; i < totalTiles; i++) {
			if (map.get(i).isHidden()) {
				count ++;
			}
		}
		return count;
	}
	/*
	 * Get a tile from the Board
	 * @param int index, where on the board the tile is
	 * @return Tile at index
	 */
	public Tile getTile(int index) {
		return map.get(index);
	}
	
	/*
	 * Make a Tile visible (isHidden = false)
	 * @param int index, where on the board the tile is
	 */
	public void uncover(int index) {
		map.get(index).uncover();	
	}
	
	/*
	 *  Creates a new board with tiles, bombs, and the numbers for non bomb tiles
	 */
	public void createMap() {
		addTiles();
		addBombs();
		addNumbers();		
	}
	
	/*
	 * Adds the Tile to the game board
	 */
	public void addTiles() {
		for(int i = 0; i < totalTiles; i++) {
			map.add(new Tile(i));
		}
	}
	
	/*
	 * Adds bombs to the board randomly based on numBombs
	 */
	public void addBombs() {
		Random r = new Random();
		int boardPosition = 0;
		for(int i = 0; i < numBombs; i++) {
			
			boardPosition += r.nextInt(totalTiles);
			if(boardPosition >= totalTiles) {
				
				boardPosition -= (totalTiles);
				
			}
			if(map.get(boardPosition).isBomb()) {
				
				i--;
				
			}
			else {
				
				map.get(boardPosition).makeBomb();
				
			}
		}
	}
	/*
	 * Prints the current board state to the terminal
	 */
	public void printBoard() {
		printHeader();
		printTiles();
		System.out.println();	
	}
	
	/*
	 * Helper function for printBoard(), prints the letter headers to the screen 
	 */
	private void printHeader() {
		System.out.print("     ");
		for(int n =0; n < width;n++) {
			System.out.print((char)(97 + n) + " ");
		}
		System.out.println();
	}
	
	/*
	 * Helper function for printBoard(), prints the tiles and the row headers
	 */
	private void printTiles() {
		String row;
		int count = 0;
		for(int i = 0; i < height; i++) {
			row = "";
			for(int j = 0; j< width; j++) {
				row += (map.get(count) + " ");
				count++;
			}
			printRow(row,i);
		}
	}
	
	/*
	 * Helper function for printTiles, helps with spacing the y axis headers to account
	 * for 2 and 3 digit rows
	 */
	private void printRow(String row, int index) {
		if(( index + 1 ) < 1000 && ( index + 1 ) >= 100) {
			System.out.printf("%d  %s\n",(index+1),row);
		}
		else if (100 > (index + 1 ) && (index +1) >= 10) {
			System.out.printf("%d   %s\n",(index +1),row);
		}
		else {
			System.out.printf("%d    %s\n",(index +1),row);

		}
	}
	
	/*
	 * Helper function to createMap, adds the numbers to the non bomb Tiles
	 */
	private void addNumbers() {
		for(Tile e: map) {
			assignNumber(e);
		}
	}
	
	/*
	 * Helper function to addNumbers, gives each non bomb Tiles a proximity number 
	 * @param Tile currentTile
	 */
	public void assignNumber(Tile currentTile) {
		if(currentTile.isBomb()) {
			return;
		}
		if(currentTile.getPosition()% width == 0) {
			
			currentTile.setNumber(assignLeftColumnNumber(currentTile));
		}
		
		else if((currentTile.getPosition() + 1)% width == 0) {
			
			currentTile.setNumber(assignRightColumnNumbers(currentTile));

		}
		else {

			currentTile.setNumber(assignMiddleColumnNumber(currentTile));

		}
	}
	/*
	 * Helper function for assignNumbers(), Gives tiles in the rightmost 
	 * column their numbers 
	 * @param Tile currentTile
	 */
	private int assignRightColumnNumbers(Tile currentTile) {
		int numberOfNeighborBombs = 0;
		try {
			if(map.get(currentTile.getPosition() -1).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() -1 + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() -1 - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		
		return numberOfNeighborBombs;
	}
	
	/*
	 * Helper function for assignNumbers(), Gives tiles in the leftmost 
	 * column their numbers 
	 * @param Tile currentTile
	 */
	private int assignLeftColumnNumber(Tile currentTile) {
		int numberOfNeighborBombs = 0;
		try {
			if(map.get(currentTile.getPosition() +1).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() +1 - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() + 1 + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		return numberOfNeighborBombs;
	}
	
	/*
	 * Helper function for assignNumbers(), Gives tiles in the center 
	 * columns their numbers 
	 * @param Tile currentTile
	 */
	private int assignMiddleColumnNumber(Tile currentTile) {
		int numberOfNeighborBombs = 0;
		try {
			if(map.get(currentTile.getPosition() +1).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() - 1).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() +1 - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() - 1 - width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() +1 + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			if(map.get(currentTile.getPosition() - 1 + width).isBomb()) {
				numberOfNeighborBombs ++;
			}
		}catch(IndexOutOfBoundsException e) {}
		return numberOfNeighborBombs;
	}
	
	
	
	public void digZeros(Tile currentTile) {
		if(currentTile.isBomb()) {
			return;
		}
		currentTile.uncover();
		if(currentTile.getNeighborBombs() > 0) {
			return;
		}
		
		if(currentTile.getPosition()% width == 0) {
			digLeftRow(currentTile);
			return;
		}
		
		else if((currentTile.getPosition() + 1)% (width) == 0) {
			digRightRow(currentTile);
			return;

		}
		else {
			digMiddleRows(currentTile);
			return;

		}
	}
	public void digLeftRow(Tile currentTile) {
		
		try {
			Tile nextTile = map.get(currentTile.getPosition() + 1);
			if(nextTile.isHidden()) {
				
				digZeros(nextTile);

			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition() + width);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition() + width + 1);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
				
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition() - width + 1);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
				
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition() - width);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
				
			}
			
		}catch(IndexOutOfBoundsException e) {}
		return;
	}
	public void digRightRow(Tile currentTile) {
		try {
			Tile nextTile = (map.get(currentTile.getPosition()  - 1));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}
			
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = (map.get(currentTile.getPosition()  - width)) ;
				
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}
			
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition() - width - 1);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition()  + width);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition()  - 1);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}
			
		}catch(IndexOutOfBoundsException e) {}
	}
	public void digMiddleRows(Tile currentTile) {
		try {
			Tile nextTile = map.get(currentTile.getPosition() +1);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = map.get(currentTile.getPosition() - 1);
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = (map.get(currentTile.getPosition() + width));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile =(map.get(currentTile.getPosition() - width));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = (map.get(currentTile.getPosition() +1 - width));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = (map.get(currentTile.getPosition() - 1 - width));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = (map.get(currentTile.getPosition() +1 + width));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
		try {
			Tile nextTile = (map.get(currentTile.getPosition() - 1 + width));
			if(nextTile.isHidden()) {
				digZeros(nextTile);
			}				
		}catch(IndexOutOfBoundsException e) {}
	}
}

