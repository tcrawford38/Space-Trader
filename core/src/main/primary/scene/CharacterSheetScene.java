package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisTable;
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
        table.row().pad(40);

        // Start Game button to generate a random set of regions and transition to map scene
        table.add(textButton("Start Game", Color.YELLOW, () -> {
            // TODO could pass in a seed to the random object
            Global.app.rand = new Random();

            Global.app.map = new Map();
            sceneLoader.setScene(new MapScene());
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
}
