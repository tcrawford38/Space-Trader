package main.primary.scene;

import java.io.File;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import main.primary.Global;
import main.primary.gameplay.Player;
import main.primary.gameplay.Save;

public class SaveGameScene extends Scene {

    Save save;

    public void create() {
        table.defaults().pad(70f);

        // Title
        table.add(title("Save Game", Color.YELLOW));
        table.padBottom(100f);
        table.row();

        // Save name logic

        String directoryName = System.getProperty("user.home") + File.separator + "Space-trader-saves" + File.separator + "saves";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] saves = directory.listFiles();
        
        table.add(textButton(saves.length >= 1 ? saves[0].getName() : "Save Slot 1" , Color.GREEN, () -> {
                Save.setSelectedSave(0);
                sceneLoader.setScene(new SaveNameScene());   
        })).pad(60);
        table.row();

        table.add(textButton(saves.length >= 2 ? saves[1].getName() : "Save Slot 2", Color.GREEN, () -> {
            Save.setSelectedSave(1);
            sceneLoader.setScene(new SaveNameScene());   
        })).pad(60);
        table.row();

        table.add(textButton(saves.length == 3 ? saves[2].getName() : "Save Slot 3", Color.GREEN, () -> {
            Save.setSelectedSave(2);
            sceneLoader.setScene(new SaveNameScene());   
        })).pad(60);
        table.row().colspan(2);

        // Go back to map scene
        table.add(textButton("Back", Color.RED, () -> sceneLoader.setScene(new MapScene())));
    }

    // Helper method to create a correctly styled textField
    private VisTextField nameInput() {
        VisTextField nameInput = new VisTextField();
        nameInput.setMaxLength(12);

        VisTextField.VisTextFieldStyle nameInputStyle = new VisTextField.VisTextFieldStyle();
        nameInputStyle.font = nameInput.getStyle().font;
        nameInputStyle.fontColor = Color.YELLOW;

        // Create a custom cursor
        final int CURSOR_WIDTH = 2;
        VisLabel throwawayBarText = new VisLabel("|");
        Pixmap cursorColor = new Pixmap(CURSOR_WIDTH, (int) throwawayBarText.getHeight(), Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
        nameInputStyle.cursor = new Image(new Texture(cursorColor)).getDrawable();
        cursorColor.dispose();

        nameInput.setStyle(nameInputStyle);
        nameInput.setColor(Color.YELLOW);
        nameInput.setAlignment(Align.center);
        return nameInput;
    }

}
