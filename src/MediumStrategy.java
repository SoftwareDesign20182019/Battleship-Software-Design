
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 * Medium Strategy randomly finds a tile until it scores a hit
 * Then it "hunts" the next available squares for the ship path
 * Then it follows down the path until the ship is sunk, and continues to randomly look for blocks
 */
public class MediumStrategy implements OpponentStrategy {
    private int[] tiles = new int[100];
    private int EMPTY = 0;
    private int HIT_UNSUNK = 1;
    private int MISS = 2;
    private int HIT_SUNK = 3;
    private int BOARD_SIZE = 99;
    private int lastShot = -1;
    private boolean lastShotHit;
    private int TO_HIT = 4;

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



    /**
     * method to return a random empty coordinate or the rest of a ship
     * @param wasHit - true if the last shot was a hit
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        lastShotHit = wasHit;
        if (wasHit && root == -1){
            root = lastShot;
            SHIPHITS++;
            updateMaxShips();
        }
        updateTiles();
        if (SHIPHITS >= MAX_SHIP) {
            shipSunk();
        }

        if (root != -1) {
            if (lastShotHit && ((lastShot == root - 10) || (lastShot == root - 20) || (lastShot == root - 30) || (lastShot == root - 40))) { //going up
                nextShot = root + 10;
                up = true;
                SHIPHITS++;
                updateMaxShips();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root - 1) || (lastShot == root - 2) || (lastShot == root - 3) || (lastShot == root - 4))) {//left
                nextShot = root + 1;
                left = true;
                SHIPHITS++;
                updateMaxShips();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 10) || (lastShot == root + 20) || (lastShot == root + 30) || (lastShot == root + 40))) { //right
                nextShot = root - 10;
                down = true;
                SHIPHITS++;
                updateMaxShips();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 1) || (lastShot == root + 2) || (lastShot == root + 3) || (lastShot == root + 4))) { //left
                nextShot = root - 1;
                right = true;
                SHIPHITS++;
                updateMaxShips();
                checkSunk();
                return hunt();
            } else { //root is -1;
                return find();
            }
        } else {
            return random();
        }
    }

    /**
     * Helper method of updateMaxShips() to see which ships have been sunk
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
     * just checks to see if ships have been sunk
     */
    private void checkSunk(){
        if (SHIPHITS >= MAX_SHIP) {
            shipSunk();
        }
    }

    /**
     * updates the Max Ship variable
     */
    private void updateMaxShips(){
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
     * This method looks in the available tiles surrounding the root tile
     * and gives back the next shot to take
     * @return lastShot
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
                        return random();
                    }
                    if (SHIPHITS == 3 && oneMid) {
                        twoMid = true;
                        shipSunk();
                        return random();
                    }
                    if (SHIPHITS == 4) {
                        battleShip = true;
                        shipSunk();
                        return random();
                    }
                    if (SHIPHITS == 2){
                        smallShip = true;
                        shipSunk();
                        return random();
                    }
                    lastShot = linedUp;
                    return lastShot; //or return survey!
                }
            }

            if (SHIPHITS == 3 && !oneMid) {
                oneMid = true;
                shipSunk();
                return random();
            }
            if (SHIPHITS == 3 && oneMid) {
                twoMid = true;
                shipSunk();
                return random();
            }
            if (SHIPHITS == 4) {
                battleShip = true;
                shipSunk();
                return random();
            }
            if (SHIPHITS == 2){
                smallShip = true;
                shipSunk();
                return random();
            }
            for (Integer next : adj) {
                lastShot = next;
                return lastShot;
            }
        }
        return random();
    }



    /**
     * Goes into the array and select a random remaining tile
     * @return next number to hit
     */
    private int random(){
        Random rand = new Random();
        int firedTile;
        firedTile = rand.nextInt(BOARD_SIZE); //try not to hit yourself
        while(tiles[firedTile] != EMPTY){
            firedTile = rand.nextInt(BOARD_SIZE);
        }
        tiles[firedTile] = HIT_UNSUNK;
        lastShot = firedTile;
        return firedTile;
    }


    /**
     * This algorithm is a smarter way of selecting the next shot to take
     * if the last shot was missed and the root has not been established
     * @return next number to hit
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
        root = -1;
        return random();
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
     * updates tiles based on incoming information: last shot hit or miss
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
     * resets "root", resets ship hits, prints shit status
     */
    private void shipSunk(){
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
     * Prints out ship status
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

