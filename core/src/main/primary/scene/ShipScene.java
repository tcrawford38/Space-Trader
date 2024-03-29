package main.primary.scene;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import main.primary.Global;
import main.primary.gameplay.*;

public class ShipScene extends Scene {

    private static final float SECTION_SCALE = 1.2f;
    private static final float ITEM_NAME_SCALE = 0.7f;
    private static final float ITEM_DESC_SCALE = 0.5f;
    private static final float ITEM_SIDE_PADDING = 10;

    private Player player;

    public void create() {
        // Set map background
        sceneLoader.setBackground("images/map_background.jpg");

        table.defaults().pad(40f);

        this.player = Global.app.player;
        Ship ship = player.getShip();


        // Titles
        table.align(Align.top);
        table.add(title("Ship Name: " + ship.getName(), Color.YELLOW)).pad(20);
        table.add(textButton("Change Name", Color.RED, () -> {
            sceneLoader.setScene(new ChangeShipNameScene());
        }).padLeft(200));
        table.row();
        table.add(title("Ship Details", Color.YELLOW)).colspan(2);
        table.row();

        // Fuel and HP
        Color fuelColor;
        if (ship.fuel >= ship.getFuelCapacity() / 2) {
            fuelColor = Color.GREEN;
        } else {
            fuelColor = Color.RED;
        }
        table.add(label("Fuel: " + ship.fuel + "/" + ship.getFuelCapacity(), fuelColor, SECTION_SCALE));
        Color HPColor;
        if (ship.getHP() >= ship.getMaxHP() / 2) {
            HPColor = Color.GREEN;
        } else {
            HPColor = Color.RED;
        }
        table.add(label("HP: " + ship.getHP(), HPColor, SECTION_SCALE));

        //Inventory
        table.row();
        table.add(shipCargo()).colspan(2);
        table.row();
        table.add(upgradeSlots()).colspan(2);
        table.row();

        //Back and Upgrade Buttons
        table.add(textButton("Back", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        })).align(Align.left);
        table.add(textButton("Upgrade Ship", Color.RED, () -> {
            sceneLoader.setScene(new UpgradeShipScene());
        }).padLeft(200));

    }

    public Table shipCargo() {
        Ship ship = player.getShip();

        Table shipCargoTable = new Table();
        shipCargoTable.add(label("-- Ship's Cargo --", Color.WHITE, SECTION_SCALE)).colspan(ship.getCargo());
        shipCargoTable.row();

        for (int i = 0; i < ship.getTotalItems(); i++) {
            Table itemOptions = new Table();
            Item item = ship.getItem(i);
            itemOptions.add(label(item.getName(), Color.WHITE, ITEM_NAME_SCALE)).pad(ITEM_SIDE_PADDING);
            itemOptions.row();
            shipCargoTable.add(itemOptions);
        }
        for (int c = ship.getTotalItems(); c < ship.getCargo(); c++) {
            shipCargoTable.add(label("Empty Slot\n#" + (c + 1), Color.WHITE, ITEM_NAME_SCALE)).pad(ITEM_SIDE_PADDING);
        }

        return shipCargoTable;
    }

    public Table upgradeSlots() {
        Ship ship = player.getShip();

        Table upgradeSlotsTable = new Table();
        upgradeSlotsTable.add(label("-- Upgrades --", Color.WHITE, 1.2f)).colspan(ship.getUpgradeSlots());
        upgradeSlotsTable.row();

        for (int i = 0; i < ship.getTotalUpgrades(); i++) {
            Table upgradeOptions = new Table();
            CharacterUpgrade upgrade = ship.getUpgrade(i);
            upgradeOptions.add(label(upgrade.getName(), Color.WHITE, ITEM_NAME_SCALE));
            upgradeOptions.row();
            upgradeOptions.add(label(
                    "(Tech: " + upgrade.getTechLevel()
                            + " | " + Skill.typeToStr(upgrade.getSkillType())
                            + " +" + upgrade.getIncAmount() + ")",
                    Color.WHITE, ITEM_DESC_SCALE));
            upgradeOptions.row();
            upgradeSlotsTable.add(upgradeOptions);
        }
        for (int c = ship.getTotalUpgrades(); c < ship.getUpgradeSlots(); c++) {
            upgradeSlotsTable.add(label("Empty Slot\n#" + (c + 1), Color.WHITE, ITEM_NAME_SCALE)).pad(ITEM_SIDE_PADDING);
        }

        return upgradeSlotsTable;
    }

}

