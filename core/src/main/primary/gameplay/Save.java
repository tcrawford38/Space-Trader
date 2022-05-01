package main.primary.gameplay;

import main.primary.Global;
import main.primary.resources.ResourceManager;
import main.primary.resources.SaveData;

public class Save {
    private static int selectedSave = 1;
    public static void saveGame(String name) {
        SaveData data;
        data = new SaveData();
        data.difficulty = Global.app.difficulty;
        data.player = Global.app.player;
        data.rand = Global.app.rand;
        data.map = Global.app.map;
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

    public static int getSelectedSave() {
        return selectedSave;
    }

    public static void setSelectedSave(int selectedSave) {
        Save.selectedSave = selectedSave;
    }
}
