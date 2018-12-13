import java.util.ArrayList;

public interface OpponentStrategy {
    public int chooseBlock(ArrayList<Tile> availableTiles);
}
