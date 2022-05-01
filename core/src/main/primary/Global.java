package main.primary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import main.primary.gameplay.Map;
import main.primary.gameplay.Player;

import java.util.Random;

/**
 * Holds global state variables for easy access
 * Loose encapsulation in favor of usability
 */
public class Global {

    static class ImpossibleError extends Error {
        public ImpossibleError(String msg) {
            super(msg);
        }
    }

    public static void init() {
        if (Global.app != null) Gdx.app.error("Global", "Reinitialization of global singleton");
        Global.app = new Global();
    }

    public static Global app; // Keep as non-final

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 960;
    public static final float MUSIC_VOLUME = 0.1f;
    public final AssetManager assetManager;
    public final GlyphLayout fontGlyphLayout;

    public final ShapeRenderer shapeRenderer;

    public static final int[][] EVENT_PROBABILITIES = new int[][]{
            {0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4}, //ez
            {0, 0, 1, 1, 2, 2, 3, 3, 3, 3, 3, 4, 4}, // med
            {0, 1, 1, 1, 2, 2, 3, 3, 3, 3, 3, 4}}; // hard

    public enum EventType {TRADER, BANDIT, SPACEPOLICE, MARKET, BENEFACTOR}

    public EventType pickRandomEvent() {
        switch (EVENT_PROBABILITIES[difficulty.ordinal()][rand.nextInt(11)]) {
            case 0:
                return EventType.TRADER;
            case 1:
                return EventType.BANDIT;
            case 2:
                return EventType.SPACEPOLICE;
            case 3:
                return EventType.MARKET;
            case 4:
                return EventType.BENEFACTOR;
            default:
                throw new ImpossibleError("Event probabilities will always be 0 - 4 inclusive");
        }
    }

    public enum Difficulty {EASY, MEDIUM, HARD}

    public Difficulty difficulty;
    public Player player;
    public Random rand;

    public Map map;
    public String[] regionNames;
    public String[] classNames;

    public String[] characterUpgradeDescriptions;
    public String[] itemDescriptions;

    public static final int FUEL_COST_PER_UNIT = 1; // cost per unit of distance
    public static final int PERCENT_FUEL_DISCOUNT_PER_PILOT_LVL = 3; // in percentage

    private Global() {
        this.assetManager = new AssetManager();

        // Used to determine the length of string in the default font
        this.fontGlyphLayout = new GlyphLayout();

        // Utility class for drawing simple shapes
        this.shapeRenderer = new ShapeRenderer();

        // Text data loading
        assetManager.setLoader(Text.class, new TextLoader(new InternalFileHandleResolver()));
        assetManager.load("data/CharacterUpgrade.txt", Text.class);
        assetManager.load("data/Items.txt", Text.class);
        assetManager.load("data/RegionClassNames.txt", Text.class);
        assetManager.load("data/RegionNames.txt", Text.class);

        // Texture loading
        assetManager.load("images/menu_background.jpg", Texture.class);
        assetManager.load("images/map_background.jpg", Texture.class);
        assetManager.load("images/unknown_location.png", Texture.class);
        assetManager.load("images/visited_location.png", Texture.class);
        assetManager.load("images/bandit.png", Texture.class);
        assetManager.load("images/police.png", Texture.class);
        assetManager.load("images/MarketPlace_Icon.png", Texture.class);
        assetManager.load("images/Map_Icon.png", Texture.class);

        // Music loading
        assetManager.load("default_music.mp3", Music.class);
    }

    /**
     * Called when the asset manager has finished loading all the assets
     * Called in LoadScene.java
     */
    public void finishedLoading() {
        // Load region names and class names into string array
        Text regionNameFile = Global.app.assetManager.get("data/RegionNames.txt", Text.class);
        Text classNameFile = Global.app.assetManager.get("data/RegionClassNames.txt", Text.class);
        this.regionNames = regionNameFile.toString().trim().split("\r\n|\n|\r");
        this.classNames = classNameFile.toString().trim().split("\r\n|\n|\r");

        // Load item descriptions into string array
        Text itemsFile = Global.app.assetManager.get("data/Items.txt", Text.class);
        this.itemDescriptions = itemsFile.toString().trim().split("\r\n|\n|\r");

        // Load character upgrade descriptions into string array
        Text characterUpgradeFile = Global.app.assetManager.get("data/CharacterUpgrade.txt", Text.class);
        this.characterUpgradeDescriptions = characterUpgradeFile.toString().trim().split("\r\n|\n|\r");

        // Load and play the music
        Music music = assetManager.get("default_music.mp3", Music.class);
        music.setVolume(MUSIC_VOLUME);
        music.setLooping(true);
        music.play();
    }

    public void dispose() {
        assetManager.dispose();
    }
}

