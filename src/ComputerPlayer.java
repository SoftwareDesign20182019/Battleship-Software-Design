import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer implements Player {
	
	private Fleet computerFleet;
	private int[][] shipLocations;
	//NEW local OpponentStrategy
	private OpponentStrategy strategy;
	private int score;

	//NEW PlayerType
	private Gameboard.PlayerType playerType;
	
	//NEW param playerTyper sets local playerType variable
	ComputerPlayer(Gameboard.PlayerType playerType){
		
		//NEW Renamed setName() to setPlayerType()
		this.playerType = playerType;
		
		computerFleet = new Fleet();
		this.shipLocations = deployShips();	
		score = 0;
	}
	
	
	public int chooseTile(boolean wasHit){
		return strategy.chooseBlock(wasHit);
	
	}
	
	public int getScore() {
		return score;
	}
	
	public void addToScore(int addPoints) {
		score = score + addPoints;
	}
	
	//NEW changed getName to getType
	public Gameboard.PlayerType getType() {
		return playerType;
	}
	
	
	public void setDifficulty(OpponentStrategy strategy) {
		this.strategy = strategy;
	}
	
	public ArrayList<Ship> getFleet() {
		return computerFleet.getFleet();
	}
	

	
	public boolean destroyedFleet() {
		return computerFleet.isFleetDestroyed();
	}
	
	/**
	 * Set start and end tile index for each ship in fleet
	 * @return	Array containing array of ship positions
	 */
	private int[][] deployShips(){
		int[][] deployments = new int[5][2];
		for(int i = 0; i < getFleet().size();i++) {
			deployments[i] = getCoordiantes(getFleet().get(i));
			if(i>0 && !(checkOverlap(i, deployments))) {
				i--;
			}

		}
		
		for(int i = 0; i< deployments.length;i++) {
			int startIndex = deployments[i][0];
			int endIndex = deployments[i][1];
			getFleet().get(i).setStartIndex(startIndex);
			getFleet().get(i).setEndIndex(endIndex);
			getFleet().get(i).setPositions();
		}
		return deployments;
	}
	
	/**
	 * Helper method for deployShips()
	 * @param 	currentShip	ship whose coordinates are being returned
	 * @return	coordinates of currentShip
	 */
	private int[] getCoordiantes(Ship currentShip) {
		Random rand = new Random();
		int startLocation, endLocation;
		startLocation = 0; 
		endLocation = 0;
		boolean valid = false;
		int[] coordinates = new int[2];
		if(willBeVertical()) {
			while(!valid) {
				startLocation = rand.nextInt(99);
				if(startLocation + (10*(currentShip.getLength()-1)) < 100) {
					endLocation = startLocation + (10*(currentShip.getLength()-1));
					valid = true;
				}
			}
		}
		else {
			while(!valid) {
				startLocation = rand.nextInt(99);
				if(startLocation%10 + (currentShip.getLength() - 1) < 10) {
					endLocation = startLocation + (currentShip.getLength() -1);
					valid = true;
				}
			}
		}
		coordinates[0] = startLocation;
		coordinates[1] = endLocation;
		return coordinates;
	}
	
	/**
	 * Helper method for getCoordinates()
	 * @return	true if ship will be vertical
	 */
	private boolean willBeVertical() {
		Random rand = new Random();
		boolean[] bool = {true, false};
		return bool[rand.nextInt(2)];
		
	}
	
	/**
	 * Helper method for deployShips()
	 * @param index			tile chosen for deployment
	 * @param deployments	currently occupied tiles	
	 * @return	true if overlap exists
	 */
	private boolean checkOverlap(int index, int[][] deployments) {
		final int startPos = deployments[index][0];
		final int endPos = deployments[index][1];
		
		int startTest = startPos;
		int increment, otherStartPos, otherEndPos, otherIncrement;
		if(endPos - startPos >=10) {
			increment = 10;
		}
		else {
			increment = 1;
		}
		
		while(startTest <= endPos) {

			for(int i = 0; i< index; i++) {
				otherStartPos = deployments[i][0];
				otherEndPos = deployments[i][1];
				if(otherEndPos - otherStartPos >=10) {
					otherIncrement = 10;
				}
				else {
					otherIncrement = 1;
				}
				while(otherStartPos <= otherEndPos) {
					if(startTest == otherStartPos) {
						return false;
					}
					otherStartPos += otherIncrement;
					//break;
				}
				
			}
			
			startTest += increment;
		}
		return true;
	}
	
	/**
	 * For testing. Print ship locations
	 */
	public void printShipLocations() {
		int[] currentPositions;
		for(int i = 0; i < computerFleet.getFleet().size(); i++ ) {
			Ship currentShip = computerFleet.getFleet().get(i);
			currentPositions = currentShip.getPositions();
			for(int j = 0; j < currentShip.getLength(); j++) {
				System.out.print(currentPositions[j] + " ");
			}
			System.out.println();
			
		}
	}
	
	/**
	 * For testing.
	 * @param args
	 */
	public static void main(String[] args) {
		ComputerPlayer test = new ComputerPlayer(Gameboard.PlayerType.OPPONENT);
		test.printShipLocations();
	}
}
