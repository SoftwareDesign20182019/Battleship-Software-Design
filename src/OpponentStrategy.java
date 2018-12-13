import java.util.ArrayList;

public interface OpponentStrategy {
    public Tile chooseBlock(ArrayList<Tile> availableTiles);
}
