package main.primary.gameplay;

import main.primary.Global;
import main.primary.resources.ResourceManager;
import main.primary.resources.SaveData;

public class Save {
    SaveData data;
    // For now, only saves Global data for testing
    public Save(String name) {
        data = new SaveData();
        data.difficulty = Global.app.difficulty;
        data.player = Global.app.player;
        data.rand = Global.app.rand;
        data.regionNames = Global.app.regionNames;
        data.classNames = Global.app.classNames;
        data.characterUpgradeDescriptions = Global.app.characterUpgradeDescriptions;
        data.itemDescriptions = Global.app.itemDescriptions;

        try {
            ResourceManager.save(data, name);
        } catch (Exception e) {
            System.out.println("Failed save " + e);
        }
    }
}
