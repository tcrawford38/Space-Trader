package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisImage;
import main.primary.Global;
import main.primary.gameplay.Item;
import main.primary.gameplay.Player;
import main.primary.gameplay.Skill;

import java.util.List;
import java.util.Random;

public class SpacePoliceScene extends Scene {

    private Label title;
    private Label title2;
    private Button continueBtn;
    private Button runBtn;
    private Button fightBtn;
    private Button forfeitBtn;

    private Table policeOptions;

    public void create() {
        // Police Encounter title
        table.align(Align.top);
        this.title = label("The Space Police board your ship on account of your extremely on-brand styling,", Color.YELLOW, 1.2f);
        table.add(title);
        table.row();
        this.title = label("and demand you forfeit some cargo!", Color.YELLOW, 1.2f);
        table.add(title).padBottom(50f);
        table.row();

        VisImage policeImage = new VisImage(Global.app.assetManager.get("images/police.png", Texture.class)) {
            public void draw(Batch batch, float parentAlpha) {
                ShaderProgram s = batch.getShader();
                batch.setShader(null);
                super.draw(batch, parentAlpha);
                batch.setShader(s);
            }
        };
        table.add(policeImage).padBottom(100f);
        table.row();
        if (Global.app.player.getShip().getItems().size() == 0) {
            int forfeitedCredits = getCreditsDemanded();
            this.title2 = label("Since you have no items, you must forfeit " + getCreditsDemanded() + " credits.", Color.YELLOW, 1.2f);
            this.forfeitBtn = forfeitCreditsButton(forfeitedCredits);
            table.add(title2);
            table.row();
        } else {
            Item forfeitedItem = getItemsDemanded();
            this.title2 = label("Demanded items:      " + forfeitedItem.getName(), Color.GREEN, 1.2f);
            this.forfeitBtn = forfeitItemButton(forfeitedItem);
            table.add(title2);
            table.row();
        }

        this.continueBtn = textButton("Continue", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        });
        continueBtn.setVisible(false);
        table.add(continueBtn);

        // Table containing police button options
        this.policeOptions = new Table();
        resize((int) table.getWidth(), (int) table.getHeight());
        policeOptions.align(Align.bottom);

        final int SPACING = 80;

        this.runBtn = runButton();

        this.fightBtn = fightButton();

        policeOptions.add(runBtn).padRight(SPACING);

        policeOptions.add(fightBtn).padRight(SPACING);

        policeOptions.add(forfeitBtn).align(Align.center);

        policeOptions.bottom().pad(30);
        sceneLoader.addActor(policeOptions);
    }

    protected void resize(int width, int height) {
        super.resize(width, height);
        if (policeOptions != null) {
            policeOptions.setPosition(sceneLoader.xOffset, sceneLoader.yOffset);
            policeOptions.setSize(table.getWidth(), table.getHeight());
        }
    }

    private Item getItemsDemanded() {
        List<Item> refList = Global.app.player.getShip().getItems();
        Random randObj = new Random();
        Item item = refList.get(randObj.nextInt(refList.size()));
        return item;
    }

    private int getCreditsDemanded() {
        Random randObj = new Random();
        int randCredits = randObj.nextInt(500) + 500;
        return randCredits;
    }

    private Button forfeitItemButton(Item forfeitedItem) {
        return textButton("Forfeit Item", Color.RED, () -> {
            Global.app.player.getShip().removeItem(forfeitedItem);
            title2.setText("You forfeited " + forfeitedItem.getName() + " to the Space Police");
            policeOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }

    private Button forfeitCreditsButton(int forfeitedCredits) {
        return textButton("Forfeit Credits", Color.RED, () -> {
            Global.app.player.credits -= forfeitedCredits;
            title2.setText("You forfeited " + forfeitedCredits + " credits to the Space Police");
            policeOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }

    private Button runButton() {
        return textButton("Run", Color.RED, () -> {
            Global.Difficulty difficulty = Global.app.difficulty;
            Random rand = new Random();
            Global.app.player.getShip().fuel -= rand.nextInt(difficulty.ordinal() + 1);
            if (Global.app.player.rollSkillCheck(Skill.SkillType.PILOT)) {
                title2.setColor(Color.GREEN);
                title2.setText("You successfully fled from the police\nPolice: HEY!!! WE GOT A 10-4!!");
            } else {
                title2.setColor(Color.GREEN);
                title2.setText("You failed to flee, lost your credits and your ship took damage\nPolice: Where do you think you're going? You'll pay for that.");
                Global.app.player.credits = Global.app.player.credits/4;
                Global.app.player.getShip().takeDamage(3);
            }
            policeOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }

    private Button fightButton() {
        return textButton("Fight", Color.RED, () -> {
            Random rand = new Random();
            if (Global.app.player.rollSkillCheck(Skill.SkillType.FIGHTER)) {
                int wonCredits = rand.nextInt(500) + 500;
                title2.setColor(Color.GREEN);
                title2.setText("You decided to fight the bandit and won " + wonCredits + " credits\nPolice: Woah, chill out man. Here take this.");
                Global.app.player.credits += wonCredits;
            } else {
                title2.setColor(Color.GREEN);
                title2.setText("You failed to beat the bandit, lost your credits and your ship took damage\nPolice: You're so weak. You'll pay for that.");
                Global.app.player.credits = Global.app.player.credits/4;
                Global.app.player.getShip().takeDamage(3);
            }
            policeOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }
}
