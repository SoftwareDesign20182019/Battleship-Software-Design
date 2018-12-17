
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 *
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
     * method to return a random empty coordinate
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        lastShotHit = wasHit;
        System.out.println("Root =" + root);
        System.out.println("Last Shot Hit = " + lastShotHit);
        if (wasHit && root == -1){
            root = lastShot;
            SHIPHITS++;
            attack();
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
                attack();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root - 1) || (lastShot == root - 2) || (lastShot == root - 3) || (lastShot == root - 4))) {//left
                nextShot = root + 1;
                left = true;
                SHIPHITS++;
                attack();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 10) || (lastShot == root + 20) || (lastShot == root + 30) || (lastShot == root + 40))) { //right
                nextShot = root - 10;
                down = true;
                SHIPHITS++;
                attack();
                checkSunk();
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 1) || (lastShot == root + 2) || (lastShot == root + 3) || (lastShot == root + 4))) { //left
                nextShot = root - 1;
                right = true;
                SHIPHITS++;
                attack();
                checkSunk();
                return hunt();
            } else { //root is -1;
                System.out.println("Commence Find");
                return find();
            }
        } else {
            return random();
        }
    }

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

    private void checkSunk(){
        if (SHIPHITS >= MAX_SHIP) {
            shipSunk();
        }
    }

    private void attack(){
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

    private int find() {
        //if last shot is miss, and next shot is not in AL, then shipSunk
        ArrayList<Integer> adj = getAdjacents(root);
        if(lastShotHit) {
            for (Integer next : adj) {
                lastShot = next;
                return lastShot;
            }
            System.out.println("In find, nothing in Adjacents");



        }else { //Last Shot Missed
            System.out.println("Next Shot: " + nextShot);

            for (Integer linedUp : adj) {
                if (nextShot != 0) {
                    if (adj.contains(nextShot)) {
                        lastShot = nextShot;
                        nextShot = 0;
                        return lastShot;
                    }
                }
                else {
                    System.out.println("Ship hits = " + SHIPHITS);
                    if (SHIPHITS == 3 && !oneMid) {
                        System.out.println("One Mid Sunk");
                        oneMid = true;
                        shipSunk();
                        return random();
                    }
                    if (SHIPHITS == 3 && oneMid) {
                        System.out.println("Two Mids Sunk");
                        twoMid = true;
                        shipSunk();
                        return random();
                    }
                    if (SHIPHITS == 4) {
                        System.out.println("BattleShip Sunk");
                        battleShip = true;
                        shipSunk();
                        return random();
                    }
                    if (SHIPHITS == 2){
                        System.out.println("Small ship sunk");
                        smallShip = true;
                        shipSunk();
                        return random();
                    }
                    System.out.println("BUG~?");
                    lastShot = linedUp;
                    return lastShot; //or return survey!
                }
            }

            System.out.println("BUG??");
            System.out.println("Ship hits = " + SHIPHITS);
            if (SHIPHITS == 3 && !oneMid) {
                System.out.println("One Mid Sunk");
                oneMid = true;
                shipSunk();
                return random();
            }
            if (SHIPHITS == 3 && oneMid) {
                System.out.println("Two Mids Sunk");
                twoMid = true;
                shipSunk();
                return random();
            }
            if (SHIPHITS == 4) {
                System.out.println("BattleShip Sunk");
                battleShip = true;
                shipSunk();
                return random();
            }
            if (SHIPHITS == 2){
                System.out.println("Small ship sunk");
                smallShip = true;
                shipSunk();
                return random();
            }
            for (Integer next : adj) {
                lastShot = next;
                return lastShot;
            }
        }

        //shipsunk()
        return random();
    }



    /**
     * Go into the array and select a random remaining 4
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




    private int hunt(){
        System.out.println("Hunting");
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
                System.out.println("In Right");
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

    private ArrayList<Integer> getAdjacents(int centerTile) {
        ArrayList<Integer> adjacents = new ArrayList<Integer>();
        int UP = centerTile - 10;
        int DOWN = centerTile + 10;
        int RIGHT = centerTile + 1;
        int LEFT = centerTile - 1;

//        System.out.println("UP = " + UP);
//        System.out.println("RIGHT " + RIGHT);
//        System.out.println("LEFT = " + LEFT);
//        System.out.println("DOWN = " + DOWN);


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
        System.out.println("Adjacents = " + adjacents);
        //@TODO Not sure where to put this but if not adjacents, root should be = -1
        if (adjacents.size() == 0){
            root = -1;
        }
        return adjacents;
    }

    private void updateTiles(){
        System.out.printf("Last Shot = %d \n", lastShot);
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

