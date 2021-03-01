package main.primary.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisTextField;
import main.primary.Global;

/**
 * Context by which the game switches scenes
 */
public class SceneLoader {

    protected float xOffset; // x pos of bottom right corner
    protected float yOffset; // y pos of bottom right corner

    private final Stage stage;
    private Scene currentScene;
    private Texture currentBackground;

    public SceneLoader(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene nextScene) {
        if (this.currentScene != null) {
            this.currentScene.remove();
            this.currentScene.reset();
        }

        this.currentScene = nextScene;
        this.currentScene.sceneLoader = this;
        this.currentScene.resize((int) stage.getWidth(), (int) stage.getHeight());
        this.currentScene.create();
        this.stage.addActor(nextScene);
    }

    public void addActor(Actor actor) {
        this.stage.addActor(actor);
    }

    public void centerActor(Actor actor) {
        actor.setX(xOffset + (stage.getWidth() - actor.getWidth()) / 2f);
        actor.setY(yOffset + (stage.getHeight() - actor.getHeight()) / 2f);
    }

    public void setBackground(String path) {
        this.currentBackground = Global.app.assetManager.get(path);
    }

    public void resize(int width, int height) {
        xOffset = (Global.WIDTH - width) / 2f;
        yOffset = (Global.HEIGHT - height) / 2f;

        if (this.currentScene == null) return;
        this.currentScene.resize(width, height);
    }

    public void requestFocus(VisTextField textField) {
        stage.setKeyboardFocus(textField);
    }

    public void render(SpriteBatch batch) {
        if (currentBackground != null) {
            Table table = currentScene.table;
            batch.draw(currentBackground, table.getX(), table.getY(), table.getWidth(), table.getHeight());
        }
        currentScene.render(batch);
    }
}
