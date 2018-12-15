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
    private int HIT = 1;
    private int MISS = 2;
    private int BOARD_SIZE = 99;
    private int lastShot = -1;
    private boolean lastShotHit;

    /**
     * method to return a random empty coordinate
     * @return the position of the fired shot
     */
    public int chooseBlock(boolean wasHit) {
        lastShotHit = wasHit;
        System.out.println(wasHit);
        updateTiles();
        Random rand = new Random();
        int firedTile;
        for (int i = 0; i < BOARD_SIZE + 1; i++) {
            if (tiles[i] == HIT) {
                ArrayList<Integer> adjAL;
                adjAL = getAdjacents(i);
                for (Integer adj1 : adjAL) {
                    if (tiles[adj1] == HIT) {
                        int nextTile = adj1 - i;
                        int ONEUP = adj1 + nextTile;
                        int TWOUP = adj1 + nextTile * 2;
                        int THREEUP = adj1 + nextTile * 3;
                        int FOURUP = adj1 + nextTile * 4;

                        int ONEDOWN = i - nextTile;
                        int TWODOWN = i - nextTile * 2;
                        int THREEDOWN = i - nextTile * 3;
                        int FOURDOWN = i - nextTile * 4;

                        boolean up = true;
                        while (up) {
                            adjAL = getAdjacents(adj1);
                            for (Integer adj2 : adjAL) {
                                if (adj2 == ONEUP) {
                                    if (tiles[ONEUP] == HIT) {
                                        adjAL = getAdjacents(adj2);
                                        for (Integer adj3 : adjAL) {
                                            if (adj3 == TWOUP) {
                                                if (tiles[TWOUP] == HIT) {
                                                    adjAL = getAdjacents(adj3);
                                                    for (Integer adj4 : adjAL) {
                                                        if (adj4 == THREEUP) {
                                                            if (tiles[THREEUP] == HIT) {
                                                                adjAL = getAdjacents(adj4);
                                                                for (Integer adj5 : adjAL) {
                                                                    if (adj5 == FOURUP) {
                                                                        if (tiles[FOURUP] != HIT && tiles[FOURUP] != MISS) {
                                                                            tiles[adj5] = HIT;
                                                                            lastShot = adj5;
                                                                            return adj5;

                                                                        } else {
                                                                            up = false;
                                                                        }
                                                                    }
                                                                }
                                                                up = false;

                                                            } else if (tiles[adj4] != MISS){
                                                                tiles[adj4] = HIT;
                                                                lastShot = adj4;
                                                                return adj4;
                                                            }
                                                        }
                                                    }
                                                    up = false;

                                                } else if (tiles[adj3] != MISS) {
                                                    tiles[adj3] = HIT;
                                                    lastShot = adj3;
                                                    return adj3;
                                                }
                                            }
                                        }
                                        up = false;

                                    }else if (tiles[adj2] != MISS){
                                        tiles[adj2] = HIT;
                                        lastShot = adj2;
                                        return adj2;
                                    }
                                }
                            }
                            up = false;
                        } //Time to descend
                        adjAL = getAdjacents(i);
                        for (Integer dj2 : adjAL) {
                            if (dj2 == ONEDOWN) {
                                if (tiles[ONEDOWN] == HIT) {
                                    adjAL = getAdjacents(dj2); //We don't need get adjacents!
                                    for (Integer dj3 : adjAL) {
                                        if (dj3 == TWODOWN) {
                                            if (tiles[TWODOWN] == HIT) {
                                                adjAL = getAdjacents(dj3);
                                                for (Integer dj4 : adjAL) {
                                                    if (dj4 == THREEDOWN) {
                                                        if (tiles[THREEDOWN] == HIT) {
                                                            adjAL = getAdjacents(dj4);
                                                            for (Integer dj5 : adjAL) {
                                                                if (dj5 == FOURDOWN) {
                                                                    if (tiles[FOURDOWN] != HIT && tiles[FOURDOWN] != MISS) {
                                                                        tiles[dj5] = HIT;
                                                                        lastShot = dj5;
                                                                        return dj5;

                                                                    } else { //fourdown is hit or miss
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            break;

                                                        } else if(tiles[dj4] != MISS){
                                                            tiles[dj4] = HIT;
                                                            lastShot = dj4;
                                                            return dj4;
                                                        }
                                                    }
                                                }
                                                break;

                                            } else if (tiles[dj3] != MISS){
                                                tiles[dj3] = HIT;
                                                lastShot = dj3;
                                                return dj3;
                                            }
                                        }
                                    }
                                    break;

                                } else if (tiles[dj2] != MISS){
                                    tiles[dj2] = HIT;
                                    lastShot = dj2;
                                    return dj2;
                                }
                            }
                        }
                        break;


                    } else if(tiles[adj1] == MISS) { //else if empty
                    } else { //if MISS
                        tiles[adj1] = HIT;
                        lastShot = adj1;
                        return adj1;
                    }

                }
            }
        }

        firedTile = rand.nextInt(BOARD_SIZE); //should be returned
        tiles[firedTile] = HIT;
        lastShot = firedTile;
        return firedTile;
    }


    private ArrayList<Integer> getAdjacents(int centerTile) {
        ArrayList<Integer> adjacents = new ArrayList<Integer>();
        int UP = centerTile - 10;
        int DOWN = centerTile + 10;
        int RIGHT = centerTile + 1;
        int LEFT = centerTile - 1;


        if (UP >= 0 && UP <= 99 && tiles[UP] != MISS) {
            adjacents.add(UP);
        }
        if (DOWN >= 0 && DOWN <= 99 && tiles[DOWN] != MISS) {
            adjacents.add(DOWN);
        }
        if (RIGHT <= 99 && RIGHT / 10 == centerTile / 10 && tiles[RIGHT] != MISS) {
            adjacents.add(RIGHT);
        }
        if (LEFT >= 0 && LEFT / 10 == centerTile / 10 && tiles[LEFT] != MISS) {
            adjacents.add(LEFT);
        }
        return adjacents;
    }

    private void updateTiles(){
        System.out.println(lastShot);
        if (lastShot == -1){
            lastShot = -1;
        } else {
            if(lastShotHit){
                tiles[lastShot] = HIT;
            } else { //if the last shot was a miss
                tiles[lastShot] = MISS;
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
        int hit4 = play.chooseBlock(false); //miss, tries LEFT
        System.out.println(hit4);
        int hit5 = play.chooseBlock(false); //Hits, should try RIGHT
        System.out.println(hit5);
        int hit6 = play.chooseBlock(false);
        System.out.println(hit6);
        int hit7 = play.chooseBlock(false);
        System.out.println(hit7);


    }


}

