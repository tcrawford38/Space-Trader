package main.primary.scene;

import com.badlogic.gdx.graphics.Color;

public class WinScene extends Scene {

    public void create() {
        table.defaults().pad(70f);

        table.add(title("Congratulations! You won", Color.YELLOW));
        table.padBottom(150f);
        table.row();
    }
}
