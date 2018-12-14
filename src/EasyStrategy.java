import java.util.Random;
import java.util.ArrayList;

public class EasyStrategy implements OpponentStrategy {
    private ArrayList<Integer> hitTiles = new ArrayList<Integer>();

    //trying to return a chosen tile. Should we select tiles with a constructor (eg. Tile(0-99)  ) or with the gameboard? For now, int


    public int chooseBlock(ArrayList<Tile> availableTiles) {
        Random rand = new Random();
        int firedTile;
        firedTile = rand.nextInt(availableTiles.size() - 1); //try not to hit yourself
        while(hitTiles.contains(firedTile)){
            firedTile = rand.nextInt(availableTiles.size() - 1);
        }
        hitTiles.add(firedTile);
        return firedTile; //figure out if int or something else should be returned
    }
}

