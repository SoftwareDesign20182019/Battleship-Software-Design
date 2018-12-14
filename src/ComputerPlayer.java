import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer implements Player {
	
	private Fleet computerFleet;
	private int[][] shipLocations;
	//NEW local OpponentStrategy
	private OpponentStrategy strategy;

	//NEW PlayerType
	private Gameboard.PlayerType playerType;
	
	//NEW param playerTyper sets local playerType variable
	ComputerPlayer(Gameboard.PlayerType playertype){
		
		//NEW Renamed setName() to setPlayerType()
		this.playerType = playerType;
		
		computerFleet = new Fleet();
		this.shipLocations = deployShips();		
	}
	
	
	public int chooseTile(){
		return strategy.chooseBlock();
	
	}
	
	//NEW changed getName to getType
	public Gameboard.PlayerType getType() {
		return playerType;
	}
	
	//NEW Now takes in OpponentStrategy param to set strategy
	public void setDifficulty(OpponentStrategy strategy) {
		this.strategy = strategy;
	} //where is this called
	
	public ArrayList<Ship> getComputerFleet() {
		return computerFleet.getFleet();
	}
	

	
	public boolean destroyedFleet() {
		return computerFleet.isFleetDestroyed();
	}
	
	public int[][] deployShips(){
		int[][] deployments = new int[5][2];
		for(int i = 0; i < getComputerFleet().size();i++) {
			deployments[i] = getCoordiantes(getComputerFleet().get(i));
			if(i>0 && !(checkOverlap(i, deployments))) {
				i--;
			}

		}
		
		for(int i = 0; i< deployments.length;i++) {
			int startIndex = deployments[i][0];
			int endIndex = deployments[i][1];
			getComputerFleet().get(i).setStartIndex(startIndex);
			getComputerFleet().get(i).setEndIndex(endIndex);
			getComputerFleet().get(i).setPositions();
		}
		return deployments;
	}
	
	public int[] getCoordiantes(Ship currentShip) {
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
	
	public boolean willBeVertical() {
		Random rand = new Random();
		boolean[] bool = {true, false};
		return bool[rand.nextInt(2)];
		
	}
	
	//public void hitTarget


	
	
	public boolean checkOverlap(int index, int[][] deployments) {
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
	
	public static void main(String[] args) {
		ComputerPlayer test = new ComputerPlayer(Gameboard.PlayerType.OPPONENT);
		test.printShipLocations();
	}
}
