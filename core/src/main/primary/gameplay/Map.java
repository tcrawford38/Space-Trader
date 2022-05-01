package main.primary.gameplay;

public class Map implements java.io.Serializable {

    private static final int MIN_SPACING = 150; // Min space between regions
    private static final int NUM_REGIONS = 10;
    private Region[] regions;

    private Region currentLocation;

    public Map() {
        this.regions = new Region[NUM_REGIONS];

        this.generateRegions();

        // Set up initial location
        this.currentLocation = this.regions[0];
        this.currentLocation.setHasVisited(true);
        this.currentLocation.getRegionMarket().generateMarket();
    }

    private void generateRegions() {
        boolean tooClose = true;
        for (int i = 0; i < regions.length; i++) {
            Region r1 = null;
            tooClose = true;
            while (tooClose) {
                r1 = new Region(i);
                tooClose = false;
                for (int j = 0; j < i; j++) {
                    Region r2 = regions[j];
                    int dx = r1.getX() - r2.getX();
                    int dy = r1.getY() - r2.getY();
                    if (Math.sqrt((double) dx * dx + dy * dy) < MIN_SPACING) {
                        tooClose = true;
                        break;
                    }
                }
            }
            this.regions[i] = r1;
        }
    }

    public Region[] getRegions() {
        return regions;
    }

    public Region getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Region region) {
        this.currentLocation = region;
    }

}
