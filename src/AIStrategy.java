


import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 * Strategy which calculates the best shot using an algorithm which checks every possible configuration of ships
 */
public class AIStrategy implements OpponentStrategy {
    private int[] tiles = new int[100];
    private int EMPTY = 0;
    private int HIT_UNSUNK = 1;
    private int MISS = 2;
    private int HIT_SUNK = 3;
    private int BOARD_SIZE = 99;
    private int lastShot = -1;
    private boolean lastShotHit;

    private int root = -1;

    private int SHIPHITS = 0;
    private int MAX_SHIP = 5;

    private boolean up;
    private boolean right;
    private boolean left;
    private boolean down;

    private boolean battleShip; //when ships are sunk, change to true
    private boolean carrier;
    private boolean oneMid;
    private boolean twoMid;
    private boolean smallShip;

    private int nextShot;

    private int[] tileProbabilities = new int[100];


    /**
     * determines the next shot to take based on the status of the board
     * @param wasHit - the status of the last shot taken
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        lastShotHit = wasHit;
        if (wasHit && root == -1){
            root = lastShot; //we are 'anchoring' the root at the first shot so we can come back to it if we need to
            SHIPHITS++;
            updateMaxShip();
        }
        updateTiles();
        if (SHIPHITS >= MAX_SHIP) { //catching sunk ship here just in case, but if all bugs were hammered out we shouldn't need it
            shipSunk();
        }

        if (root != -1) {   //if we are attacking a ship
            if (lastShotHit && ((lastShot == root - 10) || (lastShot == root - 20) || (lastShot == root - 30) || (lastShot == root - 40))) { //going up
                nextShot = root + 10;
                up = true;
                SHIPHITS++;
                updateMaxShip();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root - 1) || (lastShot == root - 2) || (lastShot == root - 3) || (lastShot == root - 4))) {//left
                nextShot = root + 1;
                left = true;
                SHIPHITS++;
                updateMaxShip();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 10) || (lastShot == root + 20) || (lastShot == root + 30) || (lastShot == root + 40))) { //down
                nextShot = root - 10;
                down = true;
                SHIPHITS++;
                updateMaxShip();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 1) || (lastShot == root + 2) || (lastShot == root + 3) || (lastShot == root + 4))) { //right
                nextShot = root - 1;
                right = true;
                SHIPHITS++;
                updateMaxShip();
                checkSunk();
                return hunt();
            } else { //we know we are attacking a ship, but we don't know where it's facing
                return find();
            }
        } else { //we aren't attacking a ship, so survey the rest of the board
            return survey();
        }
    }

    /**
     * helper method to update ship statuses
     */
    private void updateShips(){
        if (SHIPHITS == 5) {
            carrier = true;
        }
        if(SHIPHITS == 4 && carrier){
            battleShip = true;
        }
        if(SHIPHITS == 3 && carrier && battleShip){
            oneMid = true;
        }
        if(SHIPHITS == 3 && carrier && battleShip && oneMid){
            twoMid = true;
        }
    }

    /**
     * checks if ship has been sunk
     */
    private void checkSunk(){
        if (SHIPHITS >= MAX_SHIP) {
            shipSunk();
        }
    }

    /**
     * updates max ship size
     */
    private void updateMaxShip(){
        updateShips();
        if(carrier && !battleShip){
            MAX_SHIP = 4;
        }
        if(carrier && battleShip){
            MAX_SHIP = 3;
        }
        if(carrier && battleShip && oneMid && twoMid){
            MAX_SHIP = 2;
        }
        if(smallShip && !twoMid && carrier && battleShip){
            MAX_SHIP = 3;
        }
    }

    /**
     * Uses the root to find the next shot to take, and determines if the a ship has been destroyed
     * @return next shot
     */
    private int find() {
        //if last shot is miss, and next shot is not in AL, then shipSunk
        ArrayList<Integer> adj = getAdjacents(root);
        if(lastShotHit) {
            for (Integer next : adj) {
                lastShot = next;
                return lastShot;
            }


        }else { //Last Shot Missed
            for (Integer linedUp : adj) {
                if (nextShot != 0) {
                    if (adj.contains(nextShot)) {
                        lastShot = nextShot;
                        nextShot = 0;
                        return lastShot;
                    }
                }
                else {
                        if (SHIPHITS == 3 && !oneMid) {
                            oneMid = true;
                            shipSunk();
                            return survey();
                        }
                        if (SHIPHITS == 3 && oneMid) {
                            twoMid = true;
                            shipSunk();
                            return survey();
                        }
                        if (SHIPHITS == 4) {
                            battleShip = true;
                            shipSunk();
                            return survey();
                        }
                        if (SHIPHITS == 2){
                            smallShip = true;
                            shipSunk();
                            return survey();
                        }
                        lastShot = linedUp;
                        return lastShot; //or return survey!
                    }
                }

            if (SHIPHITS == 3 && !oneMid) {
                oneMid = true;
                shipSunk();
                return survey();
            }
            if (SHIPHITS == 3 && oneMid) {
                twoMid = true;
                shipSunk();
                return survey();
            }
            if (SHIPHITS == 4) {
                battleShip = true;
                shipSunk();
                return survey();
            }
            if (SHIPHITS == 2){
                smallShip = true;
                shipSunk();
                return survey();
            }
                for (Integer next : adj) {
                    lastShot = next;
                    return lastShot;
                }
            }

        return survey();
    }



    /**
     * Go into the array and select a remaining tile based on probability of tile
     * @return next number to hit
     */
    private int survey(){
        System.out.println("Surveying");
        ArrayList<Integer> mostHitSquares = new ArrayList<>();

        for(int i = 0; i < tileProbabilities.length; i++ ){
            tileProbabilities[i] = tiles[i];
            if(tileProbabilities[i] == 0){
                tileProbabilities[i] = 1;
            } else {
                tileProbabilities[i] = 0;
            }
        }

        boolean done = false;
        findDensities();
        //find largest number in tileProbabilities
        int largest = 0;
        for(int n = 0; n < tileProbabilities.length; n ++){
            if(tileProbabilities[n] > tileProbabilities[largest]){
                largest = n;
            }
        }
        for(int j = 0; j < tileProbabilities.length; j ++){
            if(tileProbabilities[j] == tileProbabilities[largest]){
                mostHitSquares.add(j);
            }
        }
        Random rand = new Random();
        int fireShot = rand.nextInt(mostHitSquares.size());

        //first randomly select a number from the arraylist
        //then use that
        lastShot = mostHitSquares.get(fireShot);
        return lastShot;
    }

    /**
     * function to check how many times a ship can fit onto one spot, depending on the remaining ships
     * updates tile probabilities everytime a ship can fit on a square
     */
    private void findDensities() {
        int UP = -10;
        int DOWN = 10;
        int RIGHT = 1;
        int LEFT = -1;

        for (int i = 0; i < tiles.length; i++) {
            //horizontal
            //do this for each ship
            if (tiles[i] == EMPTY) {
                //horizontal
                //right
                if (!smallShip && i + RIGHT <= 99 && i + RIGHT / 10 == i / 10 && tiles[i + RIGHT] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if ((!oneMid && !twoMid) && i + RIGHT*2 <= 99 && (i + RIGHT*2) / 10 == i / 10 && tiles[i + RIGHT] == EMPTY && tiles[i + RIGHT * 2] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!battleShip && (i + RIGHT*3) <= 99 && (i + RIGHT * 3) / 10 == i / 10 && tiles[i + RIGHT] == EMPTY && tiles[i + RIGHT * 2] == EMPTY && tiles[i + RIGHT * 3] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!carrier && (i + RIGHT*4) <= 99 && (i + RIGHT * 4) / 10 == i / 10 && tiles[i + RIGHT] == EMPTY && tiles[i + RIGHT * 2] == EMPTY && tiles[i + RIGHT * 3] == EMPTY && tiles[i + RIGHT * 4] == EMPTY) {
                    tileProbabilities[i]++;
                }
                //LEFT
                if (!smallShip && i + LEFT >= 0 && i + LEFT / 10 == i / 10 && tiles[i + LEFT] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if ((!oneMid && !twoMid) && i + LEFT*2 >= 0 && (i + LEFT * 2) / 10 == i / 10 && tiles[i + LEFT] == EMPTY && tiles[i + LEFT * 2] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!battleShip && i + LEFT*3 >= 0 && (i + LEFT * 3) / 10 == tiles[i] / 10 && tiles[i + LEFT] == EMPTY && tiles[i + LEFT * 2] == EMPTY && tiles[i + LEFT * 3] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!carrier && i + LEFT*4 >= 0 && (i + LEFT * 4) / 10 == tiles[i] / 10 && tiles[i + LEFT] == EMPTY && tiles[i + LEFT * 2] == EMPTY && tiles[i + LEFT * 3] == EMPTY && tiles[i + LEFT * 4] == EMPTY) {
                    tileProbabilities[i]++;
                }
                //down
                if (!smallShip && i + DOWN <= 99 && tiles[i + DOWN] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if ((!oneMid && !twoMid) && i + DOWN * 2 <= 99 &&  tiles[i + DOWN] == EMPTY && tiles[i + DOWN * 2] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!battleShip && i + DOWN*3 <= 99 && tiles[i + DOWN] == EMPTY && tiles[i + DOWN * 2] == EMPTY && tiles[i + DOWN * 3] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!carrier && i + DOWN*4 <= 99  && tiles[i + DOWN] == EMPTY && tiles[i + DOWN * 2] == EMPTY && tiles[i + DOWN * 3] == EMPTY && tiles[i + DOWN * 4] == EMPTY) {
                    tileProbabilities[i]++;
                }
                //up

                if (!smallShip && i + UP >= 0 && tiles[i + UP] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if ((!oneMid && !twoMid) && i + UP*2 >= 0 && tiles[i + UP] == EMPTY && tiles[i + UP * 2] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!battleShip && i + UP*3 >= 0 && tiles[i + UP] == EMPTY && tiles[i + UP * 2] == EMPTY && tiles[i + UP * 3] == EMPTY) {
                    tileProbabilities[i]++;
                }
                if (!carrier && i + UP*4 >= 0 && tiles[i + UP] == EMPTY && tiles[i + UP * 2] == EMPTY && tiles[i + UP * 3] == EMPTY && tiles[i + UP * 4] == EMPTY) {
                    tileProbabilities[i]++;
                }
            }
        }
    }

    /**
     *  Finds the next shot to take if anchor is set, and next the ship path determined
     * @return the next shot to take
     */
    private int hunt(){
        ArrayList<Integer> adjAL;
        boolean done = false;
        int i = 0;
        while(!done) {
            i++;
            if (i > 10){
                return find(); //change back to random if sketchy
            }
            if (up) {
                up = false;
                adjAL = getAdjacents(lastShot);
                if (!adjAL.contains(lastShot - 10)) {
                    down = true; //try going down
                } else {
                    lastShot -= 10;
                    return lastShot;
                }
            } else if (left) {
                left = false;
                adjAL = getAdjacents(lastShot);
                if (!adjAL.contains(lastShot - 1)) {
                    right = true;
                } else {
                    lastShot -= 1;
                    return lastShot;
                }
            } else if (down) {
                down = false;
                adjAL = getAdjacents(lastShot);
                if (!adjAL.contains(lastShot + 10)) {
                    up = true;
                } else {
                    lastShot += 10;
                    return lastShot;
                }
            } else {
                right = false;
                adjAL = getAdjacents(lastShot);
                if (!adjAL.contains(lastShot + 1)) {
                    left = true;
                } else {
                    lastShot += 1;
                    return lastShot;
                }
            }
        }
        return find();
    }

    /**
     *  This determines which shots in the surrounding area are free and returns a list of the free shots
     * @param centerTile - the tile, usually the last shot, around which we want to survey
     * @return ArrayList of adjacent tiles
     */
    private ArrayList<Integer> getAdjacents(int centerTile) {
        ArrayList<Integer> adjacents = new ArrayList<Integer>();
        int UP = centerTile - 10;
        int DOWN = centerTile + 10;
        int RIGHT = centerTile + 1;
        int LEFT = centerTile - 1;


        if (UP >= 0 && tiles[UP] == EMPTY) {
            adjacents.add(UP);
        }

        if (RIGHT <= 99 && (RIGHT / 10 == centerTile / 10) && tiles[RIGHT] == EMPTY) {
            adjacents.add(RIGHT);
        }

        if (DOWN <= 99 && tiles[DOWN] == EMPTY) {
            adjacents.add(DOWN);
        }

        if (LEFT >= 0 && (LEFT / 10 == centerTile / 10) && tiles[LEFT] == EMPTY) {
            adjacents.add(LEFT);
        }
        if (adjacents.size() == 0){
            root = -1;
        }
        return adjacents;
    }

    /**
     * updates the tiles to fit the board using the last shot and if it was true
     */
    private void updateTiles(){
        if (lastShot == -1){
            lastShot = -1;
        } else {
            if(lastShotHit){
                tiles[lastShot] = HIT_UNSUNK;
            } else { //if the last shot was a miss
                tiles[lastShot] = MISS;
            }
        }
    }

    /**
     * if a ship is sunk, reset root, shiphits, and set hits to hits sunk
     */
    private void shipSunk(){
        System.out.println("Ship Sunk");
        shipStatus();
        root = -1;
        SHIPHITS = 0;
        for(int i = 0; i < BOARD_SIZE + 1; i++) {
            if (tiles[i] == HIT_UNSUNK){
                tiles[i] = HIT_SUNK;
            }
        }
    }

    /**
     * prints out ship status if a ship is hit
     */
    private void shipStatus(){
        System.out.println("Ships Sunk:");
        if(carrier){
            System.out.println("Carrier");
        }
        if(battleShip){
            System.out.println("BattleShip");
        }
        if (oneMid && !twoMid){
            System.out.println("One Mid Sized");
        }
        if (oneMid && twoMid){
            System.out.println("Mid Sized Ships");
        }
        if(smallShip){
            System.out.println("Small ship sunk");
        }

    }
}


