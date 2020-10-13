package ca.sfu.cmpt276.as3.model;

import java.util.ArrayList;

public class Cell {
    private Coordinate coordinate;
    private ArrayList<Coordinate> neighbourBugs;
    private boolean isScanned;
    private boolean isExplored;
    private boolean isBug;
    private int unknowBugs;

    public Cell(Coordinate coordinate) {
        neighbourBugs = new ArrayList<>();
        this.coordinate = coordinate;
        this.isScanned = false;
        this.isExplored = false;
        this.isBug = false;
    }

    public int getUnknowBugs() {
        return unknowBugs;
    }

    public boolean isExplored() {
        return isExplored;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public boolean isBug() {
        return isBug;
    }

    public void setExplored(boolean explored) {
        isExplored = explored;
    }

    public void setBug(boolean bug) {
        isBug = bug;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public void setNeighbourBugs(Game game) {
        for (int x = 0; x < game.getSizeX(); x++){
            Coordinate map = new Coordinate(x,coordinate.getY());
            if (game.at(map).isBug()) {
                this.neighbourBugs.add(map);
            }
        }
        for (int y = 0; y < game.getSizeY(); y++){
            Coordinate map = new Coordinate(coordinate.getX(),y);
            if (game.at(map).isBug()) {
                this.neighbourBugs.add(map);
            }
        }

        if (game.at(this.coordinate).isBug){
            this.neighbourBugs.add(this.coordinate);
        }
        unknowBugs = neighbourBugs.size();
    }

    public void updateNeighbour(Game game){
        int bugExplored = neighbourBugs.size();
        for (Coordinate map : neighbourBugs){
            if (game.at(map).isExplored){
                bugExplored--;
            }
        }
        unknowBugs = bugExplored;
    }
}
