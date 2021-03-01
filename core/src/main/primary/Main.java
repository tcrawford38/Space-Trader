package main.primary;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import main.primary.scene.LoadScene;
import main.primary.scene.Scene;
import main.primary.scene.SceneLoader;

public class Main extends ApplicationAdapter {


    private SpriteBatch batch;
    private ShaderProgram fontShader;
    private Stage stage;
    private SceneLoader sceneLoader;

    public void create() {
        Global.init();
       
        // Load user interface skin
        VisUI.load(VisUI.SkinScale.X2);
        BitmapFont font = VisUI.getSkin().getFont("default-font");
        font.getData().setScale(1.5f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Font shader to make text crispier
        this.fontShader = new ShaderProgram(Gdx.files.internal("shaders/font.vert"), Gdx.files.internal("shaders/font.frag"));
        if (!fontShader.isCompiled()) {
            Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
        }

        this.batch = new SpriteBatch();

        this.stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        this.sceneLoader = new SceneLoader(stage);
        this.sceneLoader.setScene(new LoadScene());
    }

    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Set tint to white to remove previous color filters
        // Scene2D doesn't reset the tint when it's done for whatever reason
        batch.setColor(Color.WHITE);
        sceneLoader.render(batch);
        batch.end();

        batch.setShader(fontShader);
        stage.draw();
        batch.setShader(null);
    }

    public void resize(int width, int height) {
        sceneLoader.resize(width, height);
        stage.getViewport().update(width, height, false);
        Global.app.shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
    }

    public void dispose() {
        Global.app.dispose();
        VisUI.dispose();

        Scene.dispose();
        stage.dispose();
        fontShader.dispose();

        batch.dispose();
    }
}
