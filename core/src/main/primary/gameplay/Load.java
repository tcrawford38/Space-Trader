package main.primary.gameplay;

import main.primary.Global;
import main.primary.resources.ResourceManager;
import main.primary.resources.SaveData;

public class Load {
    public static void loadGame(String name) {
        try {
            SaveData data = (SaveData) ResourceManager.load(name);
            Global.app.difficulty = data.difficulty;
            Global.app.player = data.player;
            Global.app.rand = data.rand;
            Global.app.map = data.map;
            Global.app.regionNames = data.regionNames;
            Global.app.classNames = data.classNames;
            Global.app.characterUpgradeDescriptions = data.characterUpgradeDescriptions;
            Global.app.itemDescriptions = data.itemDescriptions;
        } catch (Exception e) {
            System.out.println("Failed load " + e);
        }
    }
}
