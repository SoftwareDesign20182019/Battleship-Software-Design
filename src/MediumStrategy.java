import javax.print.attribute.IntegerSyntax;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 * @TODO: Communicate with gameboard (through player?) to see if a fired shot was hit or miss, then update Tiles array. Need 1. Tile index and 2. Tile status
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

    private int root = -1;

    private int SHIPHITS = 0;
    private int FULLSHIP = 6;

    private boolean up;
    private boolean right;
    private boolean left;
    private boolean down;

    private int direction = 0;


    /**
     * method to return a random empty coordinate
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        lastShotHit = wasHit;
        System.out.println("Last Shot Hit = " + lastShotHit);
        if (wasHit && root == -1) {
            root = lastShot;
            SHIPHITS++;
        }
        if (wasHit) {
            SHIPHITS++;
        }
        updateTiles();
        if (SHIPHITS >= FULLSHIP) {
            shipSunk();
        }


        if (root != -1) {
            if (lastShotHit && ((lastShot == root - 10) || (lastShot == root - 20) || (lastShot == root - 30) || (lastShot == root - 40))) { //going up
                direction = 0;
                up = true;
                return hunt();
            } else if (lastShotHit && ((lastShot == root - 1) || (lastShot == root - 2) || (lastShot == root - 3) || (lastShot == root - 4))) {//left
                left = true;
                direction = 0;
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 10) || (lastShot == root + 20) || (lastShot == root + 30) || (lastShot == root + 40))) { //right
                down = true;
                direction = 0;
                return hunt();
            } else if (lastShotHit && ((lastShot == root + 1) || (lastShot == root + 2) || (lastShot == root + 3) || (lastShot == root + 4))) { //left
                right = true;
                direction = 0;
                return hunt();
            } else { //why does this happen! LastShotHit is false!
                System.out.println("Commence Find");
                return find();
            }
        } else {
            return random();
        }
    }

    private int find() {
       ArrayList<Integer> adj = getAdjacents(root);
        for(Integer next: adj){
            lastShot = next;
            return lastShot;
        }
        System.out.println("In find, nothing in Adjacents");
        shipSunk();
        return random();
    }

    private int random(){
        Random rand = new Random();
        int firedTile;
        firedTile = rand.nextInt(BOARD_SIZE); //should be returned
        while (tiles[firedTile] != EMPTY) {
            firedTile = rand.nextInt(BOARD_SIZE);
        }
        lastShot = firedTile;
        return firedTile;
    }



    private int hunt(){
        ArrayList<Integer> adjAL;
        boolean done = false;
        int i = 0;
        while(!done) {
            i++;
            if (i > 10){
                return find(); //change back to random if sketchy
            }
            System.out.printf("Loop = %d\n", i);
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


        if (UP >= 0 && UP <= 99 && tiles[UP] != MISS && tiles[UP] != HIT_UNSUNK) {
            adjacents.add(UP);
        }
        if (DOWN >= 0 && DOWN <= 99 && tiles[DOWN] != MISS && tiles[DOWN] != HIT_UNSUNK) {
            adjacents.add(DOWN);
        }
        if (RIGHT <= 99 && RIGHT / 10 == centerTile / 10 && tiles[RIGHT] != MISS && tiles[RIGHT] != HIT_UNSUNK) {
            adjacents.add(RIGHT);
        }
        if (LEFT >= 0 && LEFT / 10 == centerTile / 10 && tiles[LEFT] != MISS && tiles[LEFT] != HIT_UNSUNK) {
            adjacents.add(LEFT);
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
        root = -1;
        SHIPHITS = 0;
        for(int i = 0; i < BOARD_SIZE + 1; i++) {
            if (tiles[i] == HIT_UNSUNK){
                tiles[i] = HIT_SUNK;
            }
        }
    }

    public static void main(String[] args){
        MediumStrategy play = new MediumStrategy();
        int hit1 = play.chooseBlock(false);
        System.out.println(hit1);
        int hit2 = play.chooseBlock(false); //HITS
        System.out.println(hit2);
        int hit3 = play.chooseBlock(true); //miss, tries UP
        System.out.println(hit3);
        int hit4 = play.chooseBlock(true); //miss, tries LEFT
        System.out.println(hit4);
        int hit5 = play.chooseBlock(true); //Hits, should try RIGHT
        System.out.println(hit5);
        int hit6 = play.chooseBlock(false);
        System.out.println(hit6);
        int hit7 = play.chooseBlock(false);
        System.out.println(hit7);


    }


}

