
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 *
 */
public class HardStrategy implements OpponentStrategy {
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
    private int MAX_SHIP = 6;

    private boolean up;
    private boolean right;
    private boolean left;
    private boolean down;

    private boolean battleShip;
    private boolean carrier; //if sunk, these change to true
    private boolean oneMid;
    private boolean twoMid;

    private int nextShot;


    private int[] gridTiles = new int[100];


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
        if (lastShot == -1) {
            populateGrid();
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
            return survey();
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
                for (Integer linedUp : adj) {
                    if (nextShot != 0) {
                        if (nextShot == linedUp) {
                            lastShot = linedUp;
                            nextShot = 0;
                            return lastShot;
                        }
                        else {
                            System.out.println("Ship hits = " + SHIPHITS);
                            if (SHIPHITS == 3 && !oneMid) {
                                System.out.println("One Mid Sunk");
                                oneMid = true;
                                shipSunk();
                            }
                            if (SHIPHITS == 3 && oneMid) {
                                System.out.println("Two Mids Sunk");
                                twoMid = true;
                                shipSunk();
                            }
                            if (SHIPHITS == 4) {
                                System.out.println("BattleShip Sunk");
                                battleShip = true;
                                shipSunk();
                            }
                            System.out.println("BUG~?");
                            lastShot = linedUp;
                            return lastShot; //or return survey!
                        }
                    }
                    System.out.println("BUG??");
                    lastShot = linedUp;
                    return lastShot;
                }
        }
//            for (Integer next : adj) {
//                lastShot = next;
//                return lastShot;
//            }
        //shipsunk()
        return survey();
    }



    /**
     * Go into the array and select a random remaining 4
     * @return next number to hit
     */
    private int survey(){
        System.out.println("Surveying");
        ArrayList<Integer> remainingGrid = new ArrayList<>();
        for(int i = 0; i < gridTiles.length; i ++ ){
            if(gridTiles[i] == TO_HIT){
                remainingGrid.add(i);
            }
        }

        Random rand = new Random();
        int firedTile;
        firedTile = rand.nextInt(remainingGrid.size()); //should be returned
        //first randomly select a number from the arraylist
        //then use that
        lastShot = remainingGrid.get(firedTile);
        gridTiles[lastShot] = EMPTY;
        return lastShot;
    }

    public void populateGrid(){
        int next = 0;
        int count = 0;
        while(next < 100){
            gridTiles[next] = TO_HIT;
            next +=2; //going over by two
            count++;
            if (count == 5){ //when count is at 5 add 2
                next++;
            }
            if(count >= 10){ //when count is at 10
                next --;
                count = 0;
            }
        }
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

        System.out.println("UP = " + UP);
        System.out.println("RIGHT " + RIGHT);
        System.out.println("LEFT = " + LEFT);
        System.out.println("DOWN = " + DOWN);


        if (UP >= 0 && tiles[UP] != MISS && tiles[UP] != HIT_UNSUNK) {
            adjacents.add(UP);
        }
        if (DOWN <= 99 && tiles[DOWN] != MISS && tiles[DOWN] != HIT_UNSUNK) {
            adjacents.add(DOWN);
        }
        if (RIGHT <= 99 && RIGHT / 10 == centerTile / 10 && tiles[RIGHT] != MISS && tiles[RIGHT] != HIT_UNSUNK) {
            adjacents.add(RIGHT);
        }
        if (LEFT >= 0 && LEFT / 10 == centerTile / 10 && tiles[LEFT] != MISS && tiles[LEFT] != HIT_UNSUNK) {
            adjacents.add(LEFT);
        }
        System.out.println("Adjacents = " + adjacents);
        return adjacents;
    }

    private void updateTiles(){
        System.out.printf("Last Shot = %d \n", lastShot);
        if (lastShot == -1){
            lastShot = -1;
        } else {
            if(lastShotHit){
                tiles[lastShot] = HIT_UNSUNK;
                gridTiles[lastShot] = EMPTY;
            } else { //if the last shot was a miss
                tiles[lastShot] = MISS;
                gridTiles[lastShot] = EMPTY;
            }
        }
    }

    private void shipSunk(){
        System.out.println("Ship Sunk");
        root = -1;
        SHIPHITS = 0;
        for(int i = 0; i < BOARD_SIZE + 1; i++) {
            if (tiles[i] == HIT_UNSUNK){
                tiles[i] = HIT_SUNK;
            }
        }
    }

    public static void main(String[] args){
        HardStrategy play = new HardStrategy();
//        int hit1 = play.chooseBlock(false);
//        System.out.println(hit1);
//        int hit2 = play.chooseBlock(false); //HITS
//        System.out.println(hit2);
//        int hit3 = play.chooseBlock(true); //miss, tries UP
//        System.out.println(hit3);
//        int hit4 = play.chooseBlock(true); //miss, tries LEFT
//        System.out.println(hit4);
//        int hit5 = play.chooseBlock(true); //Hits, should try RIGHT
//        System.out.println(hit5);
//        int hit6 = play.chooseBlock(false);
//        System.out.println(hit6);
//        int hit7 = play.chooseBlock(false);
//        System.out.println(hit7);



    }


}

