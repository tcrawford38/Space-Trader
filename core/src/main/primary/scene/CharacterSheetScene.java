package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import main.primary.Global;
import main.primary.gameplay.Map;
import main.primary.gameplay.Skill.SkillType;

import java.util.Random;

public class CharacterSheetScene extends Scene {

    public void create() {
        // Display player name in title
        table.add(title("Welcome, " + Global.app.player.getName(), Color.YELLOW)).colspan(2);
        table.row().pad(50);

        // Table to show skill levels
        VisTable yourSkills = new VisTable();
        yourSkills.add(label("Your Skills", Color.RED)).colspan(2);
        yourSkills.row();
        yourSkills.add(skillTableRow("Pilot", SkillType.PILOT)).row();
        yourSkills.add(skillTableRow("Fighter", SkillType.FIGHTER)).row();
        yourSkills.add(skillTableRow("Merchant", SkillType.MERCHANT)).row();
        yourSkills.add(skillTableRow("Engineer", SkillType.ENGINEER)).row();
        table.add(yourSkills).width(250).align(Align.top);

        // Table to show credits
        VisTable yourCredits = new VisTable();
        yourCredits.add(label("Your Credits", Color.RED));
        yourCredits.row().pad(100);
        yourCredits.add(label(Global.app.player.credits + "", Color.YELLOW));
        table.add(yourCredits).width(250).align(Align.top);
        table.row();

        // Display difficulty
        String difStr = null;
        switch (Global.app.difficulty) {
            case EASY:
                difStr = "Easy";
                break;
            case MEDIUM:
                difStr = "Medium";
                break;
            case HARD:
                difStr = "Hard";
                break;
        }
        table.add(label("Player on " + difStr, Color.RED, 1)).colspan(2);
        table.row();

        table.add(label("Custom Seed (Optional): ", Color.RED, 1)).colspan(2);
        table.row();
        final VisTextField seedInput = randomSeedInput();
        table.add(seedInput).colspan(2).width(Global.WIDTH);
        seedInput.focusField();
        sceneLoader.requestFocus(seedInput);
        table.row().pad(40);

        // Start Game button to generate a random set of regions and transition to map scene
        table.add(textButton("Start Game", Color.YELLOW, () -> {
            if (seedInput.getText() == null || seedInput.getText().trim().isEmpty()) {
                Global.app.rand = new Random();
                Global.app.map = new Map();
                sceneLoader.setScene(new MapScene());
            } else {
                try {
                    long seed = Long.parseLong(seedInput.getText());
                    Global.app.rand = new Random(seed);
                    Global.app.map = new Map();
                    sceneLoader.setScene(new MapScene());
                } catch (NumberFormatException | NullPointerException exc) {
                    seedInput.setText("");
                    seedInput.setMessageText("Invalid Seed!");
                }
            }
        })).colspan(2);
        table.row();

        // Back button to go back to skill point selection
        table.add(textButton("Back", Color.YELLOW, () -> {
            Global.app.player.resetSkillPoints();
            sceneLoader.setScene(new SkillsLevelSelectionScene());
        })).colspan(2);

        table.padBottom(100f); // Shift the entire user interface upward
    }

    private VisTable skillTableRow(String skillName, SkillType type) {
        VisTable skillTableRow = new VisTable();
        skillTableRow.add(label(skillName, Color.YELLOW)).width(200);
        skillTableRow.add(label(Global.app.player.getSkillLevel(type) + "", Color.YELLOW)).width(200);
        return skillTableRow;
    }

    private VisTextField randomSeedInput() {
        VisTextField seedInput = new VisTextField();

        VisTextField.VisTextFieldStyle seedInputStyle = new VisTextField.VisTextFieldStyle();
        seedInputStyle.font = seedInput.getStyle().font;
        seedInputStyle.fontColor = Color.YELLOW;

        // Create a custom cursor
        final int CURSOR_WIDTH = 2;
        VisLabel throwawayBarText = new VisLabel("|");
        Pixmap cursorColor = new Pixmap(CURSOR_WIDTH, (int) throwawayBarText.getHeight(), Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
        seedInputStyle.cursor = new Image(new Texture(cursorColor)).getDrawable();
        cursorColor.dispose();

        seedInput.setStyle(seedInputStyle);
        seedInput.setColor(Color.YELLOW);
        seedInput.setAlignment(Align.center);
        return seedInput;
    }
}
