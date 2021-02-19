package main.primary.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Responsible for all scenes throughout the lifetime of the application
public class SceneLoader {

    private Scene currentScene;

    public SceneLoader() {
        this.setCurrentStage(new WelcomeScene());
    }

    public void setCurrentStage(Scene currentScene) {
        this.currentScene = currentScene;
        this.currentScene.setActive();
    }

    public void render(SpriteBatch batch) {
        currentScene.render(batch);
    }
}
