package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import main.primary.Global;
import main.primary.gameplay.*;

public class UpgradeShipScene extends Scene {

    private static final float SECTION_SCALE = 1.2f;
    private static final float ITEM_NAME_SCALE = 0.7f;
    private static final float ITEM_DESC_SCALE = 0.5f;
    private static final float ITEM_SIDE_PADDING = 10;

    private Player player;

    public void create() {
        table.defaults().pad(40f);
        this.player = Global.app.player;
        Ship ship = player.getShip();

        // Title
        table.add(title("Upgrade Ship", Color.YELLOW)).colspan(3).padBottom(50f);
        table.row();

        // Credits
        table.add(label("Credits: " + player.credits , Color.YELLOW, SECTION_SCALE)).colspan(3);
        table.row();

        // Upgrade Ship Max Health
        table.add(label("Upgrade Max Health: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
        table.add(label(ship.getMaxHP() + " >> " + (ship.getMaxHP() + 10), Color.GREEN, SECTION_SCALE)).align(Align.left);
        table.add(textButton("Buy for 1000", Color.RED, () -> {
            ship.increaseMaxHP();
            player.credits = player.credits - 1000;
            sceneLoader.setScene(new UpgradeShipScene());
        })).align(Align.right);
        table.row();

        // Upgrade Ship Max Fuel
        table.add(label("Upgrade Max Fuel: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
        table.add(label(ship.getFuelCapacity() + " >> " + (ship.getFuelCapacity() + 500), Color.GREEN, SECTION_SCALE)).align(Align.left);
        table.add(textButton("Buy for 1000", Color.RED, () -> {
            ship.increaseFuelCapacity();
            player.credits = player.credits - 1000;
            sceneLoader.setScene(new UpgradeShipScene());
        })).align(Align.right);
        table.row();

        // Upgrade Cargo Size
        if (ship.getCargo() == 10) {
            table.add(label("Upgrade Cargo Size: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
            table.add(label(ship.getCargo() + "", Color.RED, SECTION_SCALE)).align(Align.left);
            table.add(label("Max Cargo Size", Color.RED, SECTION_SCALE)).align(Align.right);
            table.row(); 
        } else {
            table.add(label("Upgrade Cargo Size: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
            table.add(label(ship.getCargo() + " >> " + (ship.getCargo() + 1), Color.GREEN, SECTION_SCALE)).align(Align.left);
            table.add(textButton("Buy for 1500", Color.RED, () -> {
            ship.increaseCargoSpace();
            player.credits = player.credits - 1500;
            sceneLoader.setScene(new UpgradeShipScene());
            })).align(Align.right);
            table.row();
        }

        // Upgrade Upgrade Size
        if (ship.getUpgradeSlots() == 7) {
            table.add(label("Increase Upgrade Slots: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
            table.add(label(ship.getUpgradeSlots() + "", Color.RED, SECTION_SCALE)).align(Align.left);
            table.add(label("Max Upgrade Slots", Color.RED, SECTION_SCALE)).align(Align.right);
            table.row(); 
        } else {
            table.add(label("Increase Upgrade Slots: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
            table.add(label(ship.getUpgradeSlots() + " >> " + (ship.getUpgradeSlots() + 1), Color.GREEN, SECTION_SCALE)).align(Align.left);
            table.add(textButton("Buy for 1500", Color.RED, () -> {
            ship.increaseUpgradeSlots();
            player.credits = player.credits - 1500;
            sceneLoader.setScene(new UpgradeShipScene());
            })).align(Align.right);
            table.row();
        }

        //Back Button
        table.add(textButton("Back", Color.RED, () -> {
            sceneLoader.setScene(new ShipScene());
        })).colspan(3).align(Align.center);
    }

}
