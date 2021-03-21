package main.primary.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import main.primary.Global;

import java.util.List;
import java.util.ArrayList;

/**
 * Base class for all scenes
 * <p>
 * Equivalent to "SceneLoader" in original project
 */
public abstract class Scene extends Group {

    protected final Table table;
    protected SceneLoader sceneLoader;

    private static List<Pixmap> bgPixmaps;

    /**
     * Use constructor only for initializing member variables
     */
    Scene() {
        this.table = new Table();
        this.addActor(table);
    }

    protected void resize(int width, int height) {
        this.table.setSize(width, height);
        this.table.setPosition(sceneLoader.xOffset, sceneLoader.yOffset);
    }

    /**
     * Main initialization function of a scene
     * Prefer this method over constructor
     */
    public abstract void create();

    /**
     * Called to remove all actors and assets related to the current scene
     */
    public void reset() {
    }

    protected void render(SpriteBatch batch) {
    }

    /**
     * Dispose of the background pixmaps to free memory
     */
    public static void dispose() {
        if (bgPixmaps != null) {
            for (Pixmap p : bgPixmaps) {
                p.dispose();
            }
        }
    }

    /**
     * Generate a solid color texture which can be used as backgrounds for components
     *
     * @param color solid color of the background
     * @return a drawable object representing the colored texture
     */
    public static Drawable coloredBG(Color color) {
        if (Scene.bgPixmaps == null) Scene.bgPixmaps = new ArrayList<>();

        Pixmap bgColor = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgColor.setColor(color);
        bgColor.fill();
        Scene.bgPixmaps.add(bgColor);
        return new Image(new Texture(bgColor)).getDrawable();
    }

    /**
     * Create a large label typically for titles and other forms of big text
     *
     * @param text  string to be displayed
     * @param color color of the title
     * @return a label object
     */
    public static Label title(String text, Color color) {
        return label(text, color, 2.5f);
    }

    /**
     * Create a label
     *
     * @param text  string to be displayed
     * @param color color of the text
     * @return a label object
     */
    public static Label label(String text, Color color) {
        VisLabel label = new VisLabel(text);
        label.setAlignment(Align.center, Align.center);
        label.setColor(color);
        return label;
    }

    /**
     * Create a label with a specified scale
     *
     * @param text  string to be displayed
     * @param color color of the text
     * @param scale size of the text
     * @return a label object
     */
    public static Label label(String text, Color color, float scale) {
        Label label = label(text, color);
        label.setFontScale(scale);
        return label;
    }

    interface PressCallback {
        void invoke();
    }

    /**
     * Create a textButton with no colored background
     *
     * @param text     string to be displayed
     * @param color    color of the text
     * @param callback function to be called when button is pressed
     * @return a text button object
     */
    public static TextButton textButton(String text, Color color, PressCallback callback) {
        VisTextButton textButton = new VisTextButton(text);
        VisTextButton.VisTextButtonStyle textButtonStyle = new VisTextButton.VisTextButtonStyle();
        textButtonStyle.font = textButton.getStyle().font;
        textButtonStyle.fontColor = color;
        textButton.setFocusBorderEnabled(false);
        textButton.setStyle(textButtonStyle);

        textButton.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                callback.invoke();
                return true;
            }
        });
        return textButton;
    }

    /**
     * Create an imageButton
     *
     * @param imagePath path to the texture file
     * @param callback  function to be called when the button is pressed
     * @return image button object
     */
    public static Button imageButton(String imagePath, PressCallback callback) {
        Texture image = Global.app.assetManager.get(imagePath);
        TextureRegionDrawable imageDrawable = new TextureRegionDrawable(image);
        ImageButton imageButton = new ImageButton(imageDrawable) {
            // HACK draw the imageButton without the font shader
            // Font shader causes all images to go white
            public void draw(Batch batch, float parentAlpha) {
                ShaderProgram s = batch.getShader();
                batch.setShader(null);
                super.draw(batch, parentAlpha);
                batch.setShader(s);
            }
        };
        imageButton.setOrigin(Align.center);
        imageButton.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                callback.invoke();
                return true;
            }
        });
        return imageButton;
    }
}
