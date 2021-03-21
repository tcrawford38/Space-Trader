package main.primary.scene;

import com.badlogic.gdx.graphics.Color;

public class WelcomeScene extends Scene {

    public void create() {
        // Set menu background
        sceneLoader.setBackground("images/menu_background.jpg");

        table.defaults().pad(70f);

        // Space Trader title
        table.add(title("Space Trader", Color.YELLOW));
        table.padBottom(150f);
        table.row();

        // Button to transition to DifficultyScene
        table.add(textButton("New Game", Color.RED, () -> sceneLoader.setScene(new DifficultyScene())));

        // TODO weclome scene animation
    }
}
