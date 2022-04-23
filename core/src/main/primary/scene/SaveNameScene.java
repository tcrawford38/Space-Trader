package main.primary.scene;

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

public class SaveNameScene extends Scene {

    Save save;

    public void create() {
        table.defaults().pad(70f);

        // Title
        table.add(title("Save Name (12 characters max)", Color.YELLOW));
        table.padBottom(150f);
        table.row();

        // Text input for save name
        final VisTextField nameInput = nameInput();
        table.add(nameInput).width(Global.WIDTH);
        nameInput.focusField();
        sceneLoader.requestFocus(nameInput);
        table.row();

        // Check name to make sure it is valid
        table.add(textButton("Continue", Color.RED, () -> {
            if (nameInput.getText() == null || nameInput.getText().trim().isEmpty()) {
                nameInput.setText("");
                nameInput.setMessageText("Enter a valid name");
            } else {
                Save.saveGame(nameInput.getText());
                sceneLoader.setScene(new MapScene());   
            }
        })).pad(10);
        table.row();

        // Go back to map scene
        table.add(textButton("Back", Color.RED, () -> sceneLoader.setScene(new ShipScene()))).pad(10);
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