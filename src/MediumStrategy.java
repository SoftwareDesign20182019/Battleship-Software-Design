import java.lang.reflect.Array;
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Wyatt Newhall
 */
public class MediumStrategy implements OpponentStrategy {
    private int[] tiles = new int[100];
    private int EMPTY = 0;
    private int HIT = 1;
    private int MISS = 2;
    private int BOARD_SIZE = 99;

    /**
     * method to return a random empty coordinate
     * @return the position of the fired shot
     */
    public int chooseBlock() {
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
                                                                        if (tiles[FOURUP] != HIT) {
                                                                            tiles[adj5] = HIT;
                                                                            return adj5;

                                                                        } else {
                                                                            up = false;
                                                                        }
                                                                    }
                                                                }
                                                                up = false;

                                                            } else {
                                                                tiles[adj4] = HIT;
                                                                return adj4;
                                                            }
                                                        }
                                                    }
                                                    up = false;

                                                } else {
                                                    tiles[adj3] = HIT;
                                                    return adj3;
                                                }
                                            }
                                        }
                                        up = false;

                                    } else {
                                        tiles[adj2] = HIT;
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
                                    adjAL = getAdjacents(dj2);
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
                                                                    if (tiles[FOURDOWN] != HIT) {
                                                                        tiles[dj5] = HIT;
                                                                        return dj5;

                                                                    } else {
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            break;

                                                        } else {
                                                            tiles[dj4] = HIT;
                                                            return dj4;
                                                        }
                                                    }
                                                }
                                                break;

                                            } else {
                                                tiles[dj3] = HIT;
                                                return dj3;
                                            }
                                        }
                                    }
                                    break;

                                } else {
                                    tiles[dj2] = HIT;
                                    return dj2;
                                }
                            }
                        }
                        break


                    } else {
                        tiles[adj1] = HIT;
                        return adj1;
                    }

                }
            }
        }

        firedTile = rand.nextInt(BOARD_SIZE); //should be returned
        tiles[firedTile] = HIT;
        return firedTile;
    }


    private ArrayList<Integer> getAdjacents(int centerTile) {
        ArrayList<Integer> adjacents = new ArrayList<Integer>();
        int UP = centerTile - 10;
        int DOWN = centerTile + 10;
        int RIGHT = centerTile + 1;
        int LEFT = centerTile - 1;

        if (UP >= 0 && UP <= 99) {
            adjacents.add(UP);
        }
        if (DOWN >= 0 && DOWN <= 99) {
            adjacents.add(DOWN);
        }
        if (RIGHT <= 99 && RIGHT / 10 == centerTile / 10) {
            adjacents.add(RIGHT);
        }
        if (LEFT >= 0 && LEFT / 10 == centerTile / 10) {
            adjacents.add(LEFT);
        }
        return adjacents;
    }

}

