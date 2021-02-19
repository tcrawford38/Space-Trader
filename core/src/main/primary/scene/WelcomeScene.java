package main.primary.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import main.primary.Global;

public class WelcomeScene extends Scene {

    private final Texture backgroundImage;

    public WelcomeScene() {

        // TODO weclome screne animation

        this.backgroundImage = Global.app.assetManager.get("images/menu_background.jpg");
        this.testBandit = Global.app.assetManager.get("images/bandit.png");

        mainTable.defaults().pad(6f);
        mainTable.add(label("Test Label", Color.RED));
        mainTable.row();
    }
    private Label label(String text, Color color) {
        Label label = new Label(text, Global.app.assetManager.get("skins/neonui/neon-ui.json", Skin.class));
        label.setAlignment(Align.center, Align.center);
        label.setColor(color);
        return label;
    }

    Texture testBandit;
    public void render(SpriteBatch batch) {
        batch.draw(backgroundImage, 0, 0, getStage().getWidth(), getStage().getHeight());
        batch.draw(testBandit, 0, 0); // TODO delete
    }
}
