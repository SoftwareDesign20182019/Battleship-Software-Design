import java.util.Scanner;


public class Driver {
	static int width, height;
	static int numberOfBombs;
	public static void main(String[] args) {
		width = 10;
		height = 10;

		numberOfBombs = 15;
		Board game = new Board(height ,width, numberOfBombs);
		game.printBoard();
		boolean done = false;

		Scanner in = new Scanner(System.in);
		
		while(!done) {
			System.out.println("Pick a tile to check");
			int tileNumber = selectTile();
			game.getTile(tileNumber).uncover();
			if (game.getTile(tileNumber).getNeighborBombs() == 0) {
				game.digZeros(game.getTile(tileNumber));
			}
			if(game.getTile(tileNumber).isBomb()) {
				System.out.println("You LOSE, GOODDAY SIR");
				done = true;
				break;
			}
			game.printBoard();
			if(game.getNumHidden() == numberOfBombs) {
				System.out.println("You win");
				done = true;
				break;
			}
			System.out.print("Do you want to place flags? (y/n)");
			String answer = in.next();
			boolean placeingFlags = answer.equals("y");
			System.out.println(placeingFlags);
			while(placeingFlags) {
				int tileToBeFlag = selectTile();
				game.getTile(tileToBeFlag).flagTile();
				game.printBoard();
				System.out.print("Do you want to place any more flags? (y/n)");
				answer = in.next();
				placeingFlags = answer.equals("y");
			}
			System.out.println("Ok");
			
			
			
			
			
		}
		
	}
	public static int selectTile() {
		int letterNumber = 0;
		int number = 0;
		Scanner in = new Scanner(System.in);
		System.out.println("Select a location (Letter number)");
		String letter = in.next();
		String numberString = in.next();
		for(int i = 0; i<letter.length();i++) {
			letterNumber += (letter.toLowerCase().charAt(i)- 96);
		}
		number = Integer.parseInt(numberString);
		return ((number - 1)*width)+((letterNumber - 1));
	}
}
