package ca.sfu.cmpt276.as3.model;

import java.util.Random;

public class Game {
    private Cell[][] board;
    private int width;
    private int height;
    private int numBugs;
    private boolean isWon;
    private int numBugsLeft;
    private int scanUsed;

    public Game(Option option) {
        this.numBugs = option.getNumBugs();
        width = option.getWidth();
        height = option.getHeight();
        scanUsed = 0;
        board = new Cell[width][height];
        isWon = false;
        numBugsLeft = numBugs;

        for (int x = 0;x < width;x++){
            for (int y = 0; y < height; y++){
                board[x][y] = new Cell(new Coordinate(x,y));
            }
        }

        setupBug();
        setupNeighbourBug();
    }

    public int getScanUsed() {
        return scanUsed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumBugs() {
        return numBugs;
    }

    public boolean isWon() {
        return isWon;
    }

    public int getNumBugsLeft() {
        return numBugsLeft;
    }

    public Cell at(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height){
            throw new IllegalArgumentException();
        }
        return board[x][y];
    }

    public Cell at(Coordinate map) {
        return at(map.getX(), map.getY());
    }

    public void scan(int x, int y){
        Cell cell = at(x, y);
        if ( !cell.isScanned() && !cell.isBug() || cell.isExplored()){
            cell.setScanned(true);
            scanUsed++;
        }
        else if (cell.isBug() && !cell.isExplored()){
            cell.setExplored(true);
            numBugsLeft--;
        }
        updateGame();
        checkWin();
    }

    private void updateGame(){
        for (int x = 0;x < width;x++){
            for (int y = 0; y < height; y++){
                board[x][y].updateNeighbour(this);
            }
        }
    }

    private void checkWin(){
        if (numBugsLeft==0){
            isWon = true;
        }
    }

    private void setupNeighbourBug() {
        for (int x = 0;x < width;x++){
            for (int y = 0; y < height; y++){
                at(x,y).setNeighbourBugs(this);
            }
        }
    }

    private void setupBug() {
        Random randX = new Random(System.currentTimeMillis());
        Random randY = new Random(System.currentTimeMillis() / 2);
        for (int i = 0;i<numBugs;i++){
            int xPos;
            int yPos;
            do {
                xPos = randX.nextInt(width);
                yPos = randY.nextInt(height);
            } while (board[xPos][yPos].isBug());
            board[xPos][yPos].setBug(true);
        }
    }
}
