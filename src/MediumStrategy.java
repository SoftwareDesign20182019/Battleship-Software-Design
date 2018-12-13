import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MediumStrategy implements OpponentStrategy {

    /**
     *  analyzes the gameboard and selects the next best tile
     * @param availableTile gives the whole gameboard
     * @return the selected tile
     */
    public int chooseBlock(ArrayList<Tile> availableTile) { //OR do we want to take in gameBoard
        Random rand = new Random();
        Iterator<Tile> itr = availableTile.iterator();
        while (itr.hasNext()) { //goes over everyblock and looks for hits. This could be done elsewhere.
            Tile currentTile = itr.next();
            if(currentTile.isHit()){ //isHit should be true probably
                ArrayList<Tile> adjTiles = currentTile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                Iterator<Tile> itr2 = adjTiles.iterator();
                while(itr2.hasNext()){ //might want to put this part in the hard strat and make this random
                    Tile adj2Tile = itr.next();
                    if(adj2Tile.isHit()){ //it would be nice to recursively iterate through here. But alas
                        nextTile = adj2Tile.getNum() - currentTile.getNum();

                        //check if the tile next to the next hit tile even exists, maximum 6 tiles
                        int ONEUP = adj2Tile.getNum() + nextTile;
                        int TWOUP = adj2Tile.getNum() + nextTile*2;
                        int THREEUP =adj2Tile.getNum() + nextTile*3;
                        int FOURUP = adj2Tile.getNum() + nextTile*4;

                        int ONEDOWN = currentTile.getNum() - nextTile;
                        int TWODOWN = currentTile.getNum() - nextTile*2;
                        int THREEDOWN =currentTile.getNum() - nextTile*3;
                        int FOURDOWN = currentTile.getNum() - nextTile*4;

                        ArrayList<Tile> adj3Tiles = adj2Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                        Iterator<Tile> itr3 = adj3Tiles.iterator();
                        boolean up = true;
                        while(up) {
                            while (itr3.hasNext()) {
                                itr3.next() = adj3Tile;
                                if (adj3Tile.getNum() = ONEUP) {
                                    if (adj3Tile.isHit()) {
                                        ArrayList<Tile> adj4Tiles = adj3Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                                        Iterator<Tile> itr4 = adj4Tiles.iterator();
                                        while (itr4.hasNext()) {
                                            itr4.next() = adj4Tile;
                                            if (adj4Tile.getNum() = TWOUP) {
                                                if (adj4Tile.isHit()) {
                                                    ArrayList<Tile> adj5Tiles = adj4Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                                                    Iterator<Tile> itr5 = adj5Tiles.iterator();
                                                    while (itr5.hasNext()) {
                                                        itr5.next() = adj5Tile;
                                                        if (adj5Tile.getNum() = THREEUP) {
                                                            if (adj5Tile.isHit()) {
                                                                ArrayList<Tile> adj6Tiles = adj5Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                                                                Iterator<Tile> itr6 = adj6Tiles.iterator();
                                                                while (itr6.hasNext()) {
                                                                    itr6.next() = adj6Tile;
                                                                    if (adj6Tile.getNum() = FOURUP) {
                                                                        if (adj6Tile.isHit()) {
                                                                            up = false;
                                                                            break;
                                                                        } else {
                                                                            return adj6Tile.getNum();
                                                                        }

                                                                        }
                                                                    up = false;
                                                                    break;
                                                                    }


                                                                } else {
                                                                return adj5Tile.getNum();
                                                            }
                                                            }

                                                        up = false;
                                                        break;
                                                    }
                                                    } else {
                                                    return adj4Tile.getNum();
                                                }
                                                }
                                            up = false;
                                            break;
                                            }


                                        } else {
                                            return adj3Tile.getNum();
                                    }
                                }
                                up = false;
                                break;
                                }  //boundary

                            } //Start down path
                             ArrayList<Tile> dj3Tiles = currentTile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                             Iterator<Tile> tr3 = dj3Tiles.iterator();
                            while (tr3.hasNext()) {
                                tr3.next() = dj3Tile;
                                if (dj3Tile.getNum() = ONEDOWN) {
                                    if (dj3Tile.isHit()) {
                                        ArrayList<Tile> dj4Tiles = dj3Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                                        Iterator<Tile> tr4 = dj4Tiles.iterator();
                                        while (tr4.hasNext()) {
                                            tr4.next() = dj4Tile;
                                            if (dj4Tile.getNum() = TWODOWN) {
                                                if (dj4Tile.isHit()) {
                                                    ArrayList<Tile> dj5Tiles = dj4Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                                                    Iterator<Tile> itr5 = dj5Tiles.iterator();
                                                    while (itr5.hasNext()) {
                                                        itr5.next() = dj5Tile;
                                                        if (dj5Tile.getNum() = THREEDOWN) {
                                                            if (dj5Tile.isHit()) {
                                                                ArrayList<Tile> dj6Tiles = dj5Tile.getAdjacents(); //getAdjacents returns an ArrayList<Tile>,
                                                                Iterator<Tile> tr6 = dj6Tiles.iterator();
                                                                while (tr6.hasNext()) {
                                                                    tr6.next() = dj6Tile;
                                                                    if (dj6Tile.getNum() = FOURDOWN) {
                                                                        if (dj6Tile.isHit()) {

                                                                            break;
                                                                        } else {
                                                                            return dj6Tile.getNum();
                                                                        }

                                                                    }

                                                                    break;
                                                                }


                                                            } else {
                                                                return dj5Tile.getNum();
                                                            }
                                                        }


                                                        break;
                                                    }
                                                } else {
                                                    return dj4Tile.getNum();
                                                }
                                            }

                                            break;
                                        }


                                    } else {
                                        return dj3Tile.getNum();
                                    }
                                }

                            }  //boundary

                        } else {
                        return adj2Tile.getNum();
                    }

                    }


                } //keep going!
            return rand.nextInt(adjTiles.size() - 1); //fired tile

            }
        return rand.nextInt(adjTiles.size() - 1);

        } //no hits!
    }
