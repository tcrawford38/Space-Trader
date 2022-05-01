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
import main.primary.gameplay.Load;

public class LoadGameScene extends Scene {

    public void create() {
        table.defaults().pad(70f);

        // Title
        table.add(title("Load Game", Color.YELLOW));
        table.padBottom(100f);
        table.row();

        // Save name logic

        String directoryName = System.getProperty("user.home") + File.separator + "Space-trader-saves" + File.separator + "saves";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File[] saves = directory.listFiles();
        System.out.println(saves.length);

        for (int i = 0; i < saves.length; i++) {
            String name = saves[i].getName();
            table.add(textButton(name, Color.GREEN, () -> {
                Load.loadGame(name);
                sceneLoader.setScene(new MapScene());   
            })).pad(60);
            table.row();
        }

        // Go back to map scene
        table.add(textButton("Back", Color.RED, () -> sceneLoader.setScene(new WelcomeScene())));
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
