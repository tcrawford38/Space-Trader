package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.widget.VisTable;
import main.primary.Global;
import main.primary.gameplay.Skill;
import main.primary.gameplay.Skill.SkillType;

public class SkillsLevelSelectionScene extends Scene {

    private Label skillPointsLabel;

    public void create() {
        // Create counters for each different skill
        final int COUNTER_SPACING_WIDTH = 250;
        for (SkillType skillType : SkillType.values()) {
            table.add(skillCounter(Skill.typeToStr(skillType), skillType)).width(COUNTER_SPACING_WIDTH);
        }
        table.row().pad(50);

        // Display total remianing skill points
        this.skillPointsLabel = label("Skill points: " + Global.app.player.getSkillPoints(), Color.YELLOW, 0.7f);
        table.add(skillPointsLabel).colspan(4);
        table.row();

        // Button to go to characterSheetScene
        table.add(textButton("Finish", Color.RED, () -> sceneLoader.setScene(new CharacterSheetScene()))).colspan(4);
        table.row().pad(50);

        //Button to go back to nameSelectionScene
        table.add(textButton("Back", Color.RED, () -> sceneLoader.setScene(new NameSelectionScene()))).colspan(4);
    }

    // Helper method to create a table with increment and decrement values for a counter
    private VisTable skillCounter(String text, SkillType skillType) {
        VisTable counter = new VisTable();
        counter.add(label(text, Color.YELLOW)).colspan(3);
        counter.row();

        Label skillLevel = label(Global.app.player.getSkillLevel(skillType) + "", Color.YELLOW);

        // Note that unicode characters like ⬇ are not registered in the default font
        counter.add(textButton("+", Color.GREEN, () -> {
            Global.app.player.convertPointsToLevel(1, skillType);
            skillLevel.setText(Global.app.player.getSkillLevel(skillType));
            skillPointsLabel.setText("Skill points: " + Global.app.player.getSkillPoints());
        })).width(60);

        counter.add(skillLevel).width(60);

        counter.add(textButton("-", Color.RED, () -> {
            Global.app.player.convertPointsToLevel(-1, skillType);
            skillLevel.setText(Global.app.player.getSkillLevel(skillType));
            skillPointsLabel.setText("Skill points: " + Global.app.player.getSkillPoints());
        })).width(60);
        return counter;
    }
}
