package main.primary;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import main.primary.scene.SceneLoader;

public class SpaceTrader extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private Stage stage;
	private AssetManager assetManager;
	private BitmapFont font;

	private SceneLoader sceneLoader;

	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		font = new BitmapFont();

		Global.init();
		this.stage = Global.app.stage;
		this.assetManager = Global.app.assetManager;

		// Texture loading
		assetManager.load("images/menu_background.jpg", Texture.class);
		assetManager.load("images/map_background.jpg", Texture.class);
		assetManager.load("images/bandit.png", Texture.class);
		assetManager.load("images/MarketPlace_Icon.png", Texture.class);
		assetManager.load("images/Map_Icon.png", Texture.class);

		// Music loading
		assetManager.load("default_music.mp3", Music.class);

		assetManager.load("skins/neonui/neon-ui.json", Skin.class);
	}

	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    if (!assetManager.update()) {
			// Simple loading screen
			float progress = assetManager.getProgress();
			batch.begin();
			font.draw(batch, progress * 100 + "% loaded...", 10, 100);
			batch.end();
			return;
		}
	   	if (sceneLoader == null) {
	   		sceneLoader = new SceneLoader();
		}


		batch.begin();

	    sceneLoader.render(batch);

		stage.draw();

		batch.end();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}
	
	public void dispose () {
		batch.dispose();
		img.dispose();

		stage.dispose();
		assetManager.dispose();
	}
}
