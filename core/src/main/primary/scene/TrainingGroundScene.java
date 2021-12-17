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
import main.primary.gameplay.Skill.SkillType;

public class TrainingGroundScene extends Scene {

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
        table.add(title("Train your skills", Color.YELLOW)).colspan(3).padBottom(50f);
        table.row();

        // Credits
        table.add(label("Credits: " + player.credits , Color.YELLOW, SECTION_SCALE)).colspan(3);
        table.row();

        // Train Pilot skill
        table.add(label("Train Pilot skill: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
        table.add(label(player.getSkillLevel(Skill.SkillType.PILOT) + " >> " + (player.getSkillLevel(Skill.SkillType.PILOT) + 1), Color.GREEN, SECTION_SCALE)).align(Align.left);
        table.add(textButton("Buy for 2500", Color.GREEN, () -> {
            player.changeSkillLevel(Skill.SkillType.PILOT, player.getSkillLevel(Skill.SkillType.PILOT) + 1);;
            player.credits = player.credits - 2500;
            sceneLoader.setScene(new TrainingGroundScene());
        })).align(Align.right);
        table.row();

        // Train Fighter skill
        table.add(label("Train Fighter skill: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
        table.add(label(player.getSkillLevel(Skill.SkillType.FIGHTER) + " >> " + (player.getSkillLevel(Skill.SkillType.FIGHTER) + 1), Color.GREEN, SECTION_SCALE)).align(Align.left);
        table.add(textButton("Buy for 2500", Color.GREEN, () -> {
            player.changeSkillLevel(Skill.SkillType.FIGHTER, player.getSkillLevel(Skill.SkillType.PILOT) + 1);;
            player.credits = player.credits - 2500;
            sceneLoader.setScene(new TrainingGroundScene());
        })).align(Align.right);
        table.row();

        // Train Merchant skill
        table.add(label("Train Merchant skill: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
        table.add(label(player.getSkillLevel(Skill.SkillType.MERCHANT) + " >> " + (player.getSkillLevel(Skill.SkillType.MERCHANT) + 1), Color.GREEN, SECTION_SCALE)).align(Align.left);
        table.add(textButton("Buy for 2500", Color.GREEN, () -> {
            player.changeSkillLevel(Skill.SkillType.MERCHANT, player.getSkillLevel(Skill.SkillType.PILOT) + 1);;
            player.credits = player.credits - 2500;
            sceneLoader.setScene(new TrainingGroundScene());
        })).align(Align.right);
        table.row();

        // Train Engineer skill
        table.add(label("Train Engineer skill: ", Color.WHITE, SECTION_SCALE)).align(Align.left);
        table.add(label(player.getSkillLevel(Skill.SkillType.ENGINEER) + " >> " + (player.getSkillLevel(Skill.SkillType.ENGINEER) + 1), Color.GREEN, SECTION_SCALE)).align(Align.left);
        table.add(textButton("Buy for 2500", Color.GREEN, () -> {
            player.changeSkillLevel(Skill.SkillType.ENGINEER, player.getSkillLevel(Skill.SkillType.PILOT) + 1);;
            player.credits = player.credits - 2500;
            sceneLoader.setScene(new TrainingGroundScene());
        })).align(Align.right);
        table.row();

        //Back Button
        table.add(textButton("Back", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        })).colspan(3).align(Align.center);
    }

}
