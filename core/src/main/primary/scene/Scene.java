package main.primary.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import main.primary.Global;

/**
 * Base class for all scenes
 *
 * Equivalent to "SceneLoader" in original project
 */
public abstract class Scene extends Group {

    protected final Table mainTable;

    Scene() {
        this.mainTable = new Table();
        mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.addActor(mainTable);
    }

    public void setActive() {
        Global.app.stage.addActor(this);
    }

    public abstract void render(SpriteBatch batch);

}
