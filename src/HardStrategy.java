
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 * This is one of the implemented Strategies, Hard Strategy
 * Hard strategy uses the find + hunting methods, but uses a different surveying method.
 * The Surveying method goes through the board two tiles, three tiles, four tiles, or five tiles at a time
 *  depending on what ship is left
 */
public class HardStrategy implements OpponentStrategy {
    private int[] tiles = new int[100];
    private int EMPTY = 0;
    private int HIT_UNSUNK = 1;
    private int MISS = 2;
    private int HIT_SUNK = 3;
    private int BOARD_SIZE = 99;
    private int lastShot = -1; //this value changes every turn
    private boolean lastShotHit;
    private int MARK = 4;

    private int root = -1;

    private int SHIPHITS = 0;
    private int MAX_SHIP = 5;

    private boolean up;
    private boolean right;
    private boolean left;
    private boolean down;

    private boolean battleShip;
    private boolean carrier; //if sunk, these change to true
    private boolean oneMid;
    private boolean twoMid;
    private boolean smallShip;

    private int nextShot;
    private int[] gridTiles = new int[100]; //this populates a board with available blocks following the surveying patter


    /**
     * Choose block chooses which block to hit out of the available shots.
     * @param wasHit - a boolean which indicates if the last shot sent was a hit or not
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        lastShotHit = wasHit; //the following three blocks of code update the board based on if the last hit was true or not
        if (wasHit && root == -1){
            root = lastShot;
            SHIPHITS++;
            updateMaxShip();
        }
        if (lastShot == -1) { //populates grid in the beginning
            populateGrid();
        }
        updateTiles();
        if (SHIPHITS >= MAX_SHIP) {
            shipSunk();
        }
        //if the last shot hits something
        if (root != -1) {
            if (lastShotHit && ((lastShot == root - 10) || (lastShot == root - 20) || (lastShot == root - 30) || (lastShot == root - 40))) { //going down
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
            } else if (lastShotHit && ((lastShot == root + 10) || (lastShot == root + 20) || (lastShot == root + 30) || (lastShot == root + 40))) { //up
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
            } else { //last shot hit, but direction has not been established.
                System.out.println("Commence Find");
                return find();
            }
        } else {
            return survey();
        }
    }

    /**
     * update ships is a small method which just checks to see what ships have been sunk
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
     * Checks to see if ship hits is larger than the max ship
     */
    private void checkSunk(){
        if (SHIPHITS >= MAX_SHIP) {
            shipSunk();
        }
    }

    /**
     * this updates the ships, and updates MaxShips
     */
    private void updateMaxShip(){
        updateShips();
        if(carrier && !battleShip){
            MAX_SHIP = 4;
            System.out.println("Carrier Sunk");
        }
        if(carrier && battleShip){
            MAX_SHIP = 3;
            System.out.println("Carrier and BattleShip Sunk");
        }
        if(carrier && battleShip && oneMid && twoMid){
            MAX_SHIP = 2;
            System.out.println("Carrier and BattleShip and Midsized Ships Sunk");
        }
        if(smallShip && !twoMid && carrier && battleShip){
            MAX_SHIP = 3;
        }
    }

    /**
     * This method looks in the available tiles surrounding the root tile
     * and gives back the next shot to take
     * @return lastShot
     */
    private int find() {
        //if last shot is miss, and next shot is not in AL, then shipSunk
        ArrayList<Integer> adj = getAdjacents(root);
        System.out.println("Weird, next Shot is " + nextShot);
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
                        System.out.println("One Mid Sunk");
                        oneMid = true;
                        shipSunk();
                        return survey();
                    }
                    if (SHIPHITS == 3 && oneMid) {
                        System.out.println("Two Mids Sunk");
                        twoMid = true;
                        shipSunk();
                        return survey();
                    }
                    if (SHIPHITS == 4) {
                        System.out.println("BattleShip Sunk");
                        battleShip = true;
                        shipSunk();
                        return survey();
                    }
                    if (SHIPHITS == 2){
                        System.out.println("Small ship sunk");
                        smallShip = true;
                        shipSunk();
                        return survey();
                    }
                    lastShot = linedUp;
                    return lastShot;
                }
            }

            if (SHIPHITS == 3 && !oneMid) {
                System.out.println("One Mid Sunk");
                oneMid = true;
                shipSunk();
                return survey();
            }
            if (SHIPHITS == 3 && oneMid) {
                System.out.println("Two Mids Sunk");
                twoMid = true;
                shipSunk();
                return survey();
            }
            if (SHIPHITS == 4) {
                System.out.println("BattleShip Sunk");
                battleShip = true;
                shipSunk();
                return survey();
            }
            if (SHIPHITS == 2){
                System.out.println("Small ship sunk");
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
     * This algorithm is a smarter way of selecting the next shot to take
     * only if the last shot was missed and the root has not been established
     * @return next number to hit
     */
    private int survey(){
        System.out.println("Surveying");
        ArrayList<Integer> remainingGrid = new ArrayList<>();
        for(int i = 0; i < gridTiles.length; i++ ){
            if(gridTiles[i] == EMPTY){
                remainingGrid.add(i);
            }
        }

        Random rand = new Random();
        int firedTile;
        firedTile = rand.nextInt(remainingGrid.size());
        //randomly select a number from the arraylist
        lastShot = remainingGrid.get(firedTile);
        gridTiles[lastShot] = MARK;
        System.out.println("Should return: " + lastShot);
        return lastShot;
    }

    /**
     * This populates the the next shot grid
     */
    public void populateGrid(){
        if(!smallShip) {
            populate(2);
        } else if (smallShip && (!oneMid || !twoMid)){
            populate(3);
        } else if (smallShip && (oneMid && twoMid)) { //battleship and carrier still out there
            populate(4);
        } else if(smallShip && (oneMid && twoMid) && battleShip){
            populate(5);
        }
    }


    /**
     *  The root block has been established, this method tries to go down the line to eliminate the rest of the ship
     * @return the next shot to take
     */
    private int hunt(){
        System.out.println("Hunting");
        ArrayList<Integer> adjAL;
        boolean done = false;
        int i = 0;
        while(!done) {
            i++;
            if (i > 10){
                return find(); //change back to random survey if caught in the loop. Sketchy solution
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
        System.out.println("if you're seeing this things went awry");
        return root = -1;
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
     * updates tiles based on the last shot taken to fill EMPTY
     */
    private void updateTiles(){
        System.out.printf("Last Shot = %d \n", lastShot);
        if (lastShot == -1){
            lastShot = -1;
        } else {
            if(lastShotHit){
                tiles[lastShot] = HIT_UNSUNK;
                gridTiles[lastShot] = MARK;
            } else { //if the last shot was a miss
                tiles[lastShot] = MISS;
                gridTiles[lastShot] = MARK;
            }
        }
    }

    /**
     * resets root, shipHits, repopulates grid
     */
    private void shipSunk(){
        shipStatus();
        root = -1;
        SHIPHITS = 0;
        populateGrid();

        for(int i = 0; i < BOARD_SIZE + 1; i++) {
            if (tiles[i] == HIT_UNSUNK){
                tiles[i] = HIT_SUNK;
            }
        }
    }

    /**
     * Prints out the status of the ships from the AI's perspective
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

    /**
     * Calculates how spread out the grid should be depending on what ships are left
     * @param smallestShip - the smallest ship left on the board
     */
    public void populate(int smallestShip){
        int  indent = 0;
        int line = 0;
        while (line<10){
            int index = 0;
            while(index<10){
                if((index - indent) % smallestShip == 0 ) {
                    if(tiles[index + (line * 10)] == EMPTY) {
                        gridTiles[index + (line * 10)] = EMPTY;
                    }
                }
                else{
                    gridTiles[index + (line * 10)] = MARK;
                }
                index++;
            }
            line++;
            indent ++;
            if(indent >= smallestShip){
                indent = 0;
            }
        }
    }
}

