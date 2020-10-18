package ca.sfu.cmpt276.as3.model;

public class Option {
    private int width;
    private int height;
    private int numBugs;
    private int numPlays;
    private int[] bestScores = new int[12];
    //private int[] bestScores = {0,0,0,0,0,0,0,0,0,0,0,0};

    private static Option optionInstance = null;

    private Option(){
    }

    public static Option getInstance(){
        if (optionInstance == null){
            optionInstance = new Option();
        }
        return optionInstance;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setNumBugs(int numBugs) {
        this.numBugs = numBugs;
    }


    public void setBestScoreAt(int index,int val) {
        bestScores[index] = val;
    }

    public void setNumPlays(int numPlays) {
        this.numPlays = numPlays;
    }

    public void eraseNumPlays(){
        this.numPlays = 0;
    }

    public void increaseNumPlays(){
        this.numPlays++;
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

    public int getNumPlays() {
        return numPlays;
    }

    public int getBestScoreAt(int index){
        return bestScores[index];
    }

    public int[] getBestScores() {
        return bestScores;
    }
}
