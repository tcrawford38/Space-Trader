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
import main.primary.gameplay.Player;
import main.primary.gameplay.Skill;

import java.util.Random;

public class BanditScene extends Scene {

    private Player player;
    private Random rand;

    private Label title;
    private Label npcText;
    private Button continueBtn;

    private Table banditOptions;

    public void create() {
        this.player = Global.app.player;
        this.rand = Global.app.rand;

        // Bandit Encounter title
        table.align(Align.top);
        this.title = label("A Bandit has appeared!", Color.YELLOW, 1.2f);
        table.add(title).pad(50f);
        table.row();

        VisImage banditImage = new VisImage(Global.app.assetManager.get("images/bandit.png", Texture.class)) {
            public void draw(Batch batch, float parentAlpha) {
                ShaderProgram s = batch.getShader();
                batch.setShader(null);
                super.draw(batch, parentAlpha);
                batch.setShader(s);
            }
        };
        table.add(banditImage).padBottom(50f);
        table.row();

        this.npcText = label("Bandit: RUN YO POCKETS!!!", Color.WHITE, 1.2f);
        table.add(npcText);
        table.row();

        this.continueBtn = textButton("Continue", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        });
        continueBtn.setVisible(false);
        table.add(continueBtn);

        // Table containing trader button options
        this.banditOptions = new Table();
        resize((int) table.getWidth(), (int) table.getHeight());
        banditOptions.align(Align.bottom);

        final int SPACING = 80;

        banditOptions.add(payButton()).padRight(SPACING);

        banditOptions.add(fleeButton()).padRight(SPACING);

        banditOptions.add(fightButton());

        banditOptions.bottom().pad(30);
        sceneLoader.addActor(banditOptions);
    }

    protected void resize(int width, int height) {
        super.resize(width, height);
        if (banditOptions != null) {
            banditOptions.setPosition(sceneLoader.xOffset, sceneLoader.yOffset);
            banditOptions.setSize(table.getWidth(), table.getHeight());
        }
    }

    private Button payButton() {
        return textButton("Pay Bandit", Color.RED, () -> {
            if (player.credits < 1000) {
                if (player.getShip().getTotalItems() == 0) {
                    title.setText("You couldn't pay the bandit and your ship took damage");
                    npcText.setText("Bandit: You don't have money or items? I'm damaging your ship.");
                    player.getShip().takeDamage(3);
                } else {
                    title.setText("You couldn't pay the bandit and lost your items");
                    npcText.setText("Bandit: That's not enough money. I'm taking your items");
                    player.getShip().clearItems();
                }
            } else {
                title.setText("You paid the bandit 1000 credits");
                npcText.setText("Bandit: Thank you for donating to the charity. Haha.");
                player.credits -= 1000;
            }
            banditOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }

    private Button fleeButton() {
        return textButton("Flee", Color.RED, () -> {
            Global.Difficulty difficulty = Global.app.difficulty;
            player.getShip().fuel -= rand.nextInt(difficulty.ordinal() + 1);
            if (player.rollSkillCheck(Skill.SkillType.PILOT)) {
                title.setText("You successfully fled from the bandit");
                npcText.setText("Bandit: HEY GET BACK HERE!!!");
            } else {
                title.setText("You failed to flee, lost your credits and your ship took damage");
                npcText.setText("Bandit: Where do you think you're going? You'll pay for that.");
                player.credits = player.credits/4;
                player.getShip().takeDamage(3);
            }
            banditOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }

    private Button fightButton() {
        return textButton("Fight", Color.RED, () -> {
            if (player.rollSkillCheck(Skill.SkillType.FIGHTER)) {
                int wonCredits = rand.nextInt(1000);
                title.setText("You decided to fight the bandit and won " + wonCredits + " credits");
                npcText.setText("Bandit: Woah, chill out man. Here take this.");
                player.credits += wonCredits;
            } else {
                title.setText("You failed to beat the bandit, lost your credits and your ship took damage");
                npcText.setText("Bandit: You're so weak. You'll pay for that.");
                player.credits = player.credits/4;
                player.getShip().takeDamage(3);
            }
            banditOptions.setVisible(false);
            continueBtn.setVisible(true);
        });
    }
}
