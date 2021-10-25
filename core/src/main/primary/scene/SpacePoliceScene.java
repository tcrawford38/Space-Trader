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

public class SpacePoliceScene extends Scene {

    private Label title;
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
        this.title = label("Demanded items: ", Color.YELLOW, 1.2f);
        table.add(title);
        table.row();

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

        this.runBtn = textButton("Run", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        });
        this.fightBtn = textButton("Fight", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        });
        this.forfeitBtn = textButton("Forfeit items", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        });
        policeOptions.add(runBtn).padRight(SPACING).align(Align.center);

        policeOptions.add(fightBtn).padRight(SPACING).align(Align.center);

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
}
