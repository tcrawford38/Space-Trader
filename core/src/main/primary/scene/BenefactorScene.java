package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import main.primary.Global;
import main.primary.gameplay.*;

public class BenefactorScene extends Scene {

    private static final float SECTION_SCALE = 1.2f;
    private static final float ITEM_NAME_SCALE = 0.7f;
    private static final float ITEM_DESC_SCALE = 0.5f;
    private static final float ITEM_SIDE_PADDING = 10;
    private Label title;

    public void create() {
        // Set menu background
        sceneLoader.setBackground("images/menu_background.jpg");
        table.defaults().pad(70f);

        // Benefactor Encounter title
        this.title = title("A Benefactor has appeared!", Color.YELLOW);
        table.add(title).pad(150f).align(Align.top);
        table.row();

        // Benefactor dialogue
        table.add(label("Kind Person: Hello Traveler. Here is 150 credits for the road", Color.WHITE, 1.2f));
        table.row();
        // Go to Welcome Screen
        table.add(textButton("Continue", Color.YELLOW, () -> sceneLoader.setScene(new MapScene()))).pad(10);
    }
}
