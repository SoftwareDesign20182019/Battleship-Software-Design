import java.util.Random;
import java.util.ArrayList;

public class EasyStrategy implements OpponentStrategy {

    //trying to return a chosen tile. Should we select tiles with a constructor (eg. Tile(0-99)  ) or with the gameboard? For now, int


    public int chooseBlock(ArrayList<Tile> availableTiles) {
        Random rand = new Random();
        int firedTile = rand.nextInt(availableTiles.size() - 1);
        return firedTile; //figure out if int or something else should be returned
    }

