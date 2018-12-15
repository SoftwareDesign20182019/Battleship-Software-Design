import java.util.Random;

/**
 * @author Wyatt Newhall
 */
public class EasyStrategy implements OpponentStrategy {
    private int[] tiles = new int[100];
    private int EMPTY = 0;
    private int HIT = 1;
    private int MISS = 2;
    private int BOARD_SIZE = 99;

    /**
     * method to return a random empty coordinate
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        Random rand = new Random();
        int firedTile;
        firedTile = rand.nextInt(BOARD_SIZE); //try not to hit yourself
        while(tiles[firedTile] != EMPTY){
            firedTile = rand.nextInt(BOARD_SIZE);
        }
        tiles[firedTile] = HIT;
        return firedTile;
    }

}

