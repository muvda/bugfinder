package ca.sfu.cmpt276.as3.model;

public class Option {
    private int sizeX;
    private int sizeY;
    private int numBugs;
    private int numPlays;
    private static Option optionInstance = null;

    private Option(){
        sizeX = 4;
        sizeY = 6;
        numBugs = 6;
        numPlays = 0;
    }

    public Option getInstance(){
        if (optionInstance == null){
            optionInstance = new Option();
        }
        return optionInstance;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setNumBugs(int numBugs) {
        this.numBugs = numBugs;
    }

    public void eraseNumPlays(){
        this.numPlays = 0;
    }

    public void increaseNumPlays(){
        this.numPlays++;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getNumBugs() {
        return numBugs;
    }

    public int getNumPlays() {
        return numPlays;
    }
}
