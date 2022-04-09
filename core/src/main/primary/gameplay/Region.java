package main.primary.gameplay;

import main.primary.Global;

import java.util.Random;

public class Region implements java.io.Serializable {

    private final int regionID;

    private final String name;
    private int techLevel;
    private int x;
    private int y;
    private String description;
    private boolean hasVisited;
    private Market regionMarket;
    // TODO private boolean winningRegion = false;

    public Region(int regionID) {
        Random rand = Global.app.rand;

        this.regionID = regionID;

        String[] regionNames = Global.app.regionNames;
        String[] classNames = Global.app.classNames;
        this.name = regionNames[rand.nextInt(regionNames.length)] + classNames[rand.nextInt(classNames.length)];
        this.techLevel = rand.nextInt(5) + 1;

        // TODO factor out these constants
        final int MAP_WIDTH = 500;
        final int MAP_HEIGHT = 300;
        this.x = rand.nextInt(MAP_WIDTH * 2) - MAP_WIDTH;
        this.y = rand.nextInt(MAP_HEIGHT * 2) - MAP_HEIGHT;

        this.description = name + "\n";
        this.description += "Tech Level: " + techLevel;

        this.hasVisited = false;

        this.regionMarket = new Market(this);
    }

    public String getName() {
        return name;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasVisited() {
        return hasVisited;
    }

    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    public Market getRegionMarket() {
        return regionMarket;
    }

    public boolean equalsRegion(Region r) {
        return r.regionID == this.regionID;
    }

    public double distanceTo(Region otherRegion) {
        int dx = x - otherRegion.getX();
        int dy = y - otherRegion.getY();
        return Math.sqrt((double) dx * dx + dy * dy);
    }

}
