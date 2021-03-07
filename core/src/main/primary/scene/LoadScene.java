package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import main.primary.Global;

public class LoadScene extends Scene {

    private Label progressLabel;

    public void create() {
        progressLabel = title("Loading...", Color.WHITE);
        table.add(progressLabel);
    }

    public void render(SpriteBatch batch) {
        if (!Global.app.assetManager.update()) {
            // TODO Improve the loading screen
            float progress = Global.app.assetManager.getProgress();
            progressLabel.setText((int) (progress * 100) + "% loaded...");
        } else {
            Global.app.finishedLoading();
            this.sceneLoader.setScene(new WelcomeScene());
        }
    }

}
