package main.primary.resources;

import java.util.Random;

import main.primary.Global.Difficulty;
import main.primary.gameplay.Map;
import main.primary.gameplay.Player;


public class SaveData implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    // Global variables
    public Difficulty difficulty;
    public Player player;
    public Random rand;
    public Map map;
    public String[] regionNames;
    public String[] classNames;
    public String[] characterUpgradeDescriptions;
    public String[] itemDescriptions;

}
