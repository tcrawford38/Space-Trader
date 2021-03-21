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

public class NameSelectionScene extends Scene {

    public void create() {
        table.defaults().pad(70f);

        // Title
        table.add(title("What is your name Trader?", Color.YELLOW));
        table.padBottom(150f);
        table.row();

        // Text input for player name
        final VisTextField nameInput = nameInput();
        table.add(nameInput).width(Global.WIDTH);
        nameInput.focusField();
        sceneLoader.requestFocus(nameInput);
        table.row();

        // Check name to make sure it is valid before switching to skill level selection scene
        table.add(textButton("Continue", Color.RED, () -> {
            if (nameInput.getText() == null || nameInput.getText().trim().isEmpty()) {
                nameInput.setText("");
                nameInput.setMessageText("Enter a valid name");
            } else {
                int credits = 0;
                int skillPoints = 0;
                switch (Global.app.difficulty) {
                    case EASY:
                        credits = 10_000;
                        skillPoints = 8;
                        break;
                    case MEDIUM:
                        credits = 6000;
                        skillPoints = 5;
                        break;
                    case HARD:
                        credits = 4000;
                        skillPoints = 3;
                        break;
                }
                Global.app.player = new Player(nameInput.getText().trim(), credits, skillPoints);
                sceneLoader.setScene(new SkillsLevelSelectionScene());
            }
        })).pad(10);
        table.row();

        // Go back to difficulty scene
        table.add(textButton("Back", Color.RED, () -> sceneLoader.setScene(new DifficultyScene()))).pad(10);
    }

    // Helper method to create a correctly styled textField
    private VisTextField nameInput() {
        VisTextField nameInput = new VisTextField();

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
