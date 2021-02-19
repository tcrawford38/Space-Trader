package main.primary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Global singleton to quickly access global data.
 * Loose encapsulation in favor of usability
 */
public class Global {

    public static Global app;
    public static void init() { Global.app = new Global(); }

    public final Stage stage;
    public final AssetManager assetManager;

    private Global() {
        this.stage = new Stage(new ScreenViewport());
        // TODO look into how to use an input multiplexer instead
        Gdx.input.setInputProcessor(stage);

        this.assetManager = new AssetManager();
    }
}
