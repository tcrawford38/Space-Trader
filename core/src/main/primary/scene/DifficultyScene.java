package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import main.primary.Global;

public class DifficultyScene extends Scene {

    public void create() {
        table.defaults().pad(70f);

        // Title
        table.add(title("Choose a difficult", Color.YELLOW)).colspan(3);
        table.padBottom(150f);
        table.row();

        // Set difficulty to easy
        table.add(textButton("Easy", Color.RED, () -> {
            Global.app.difficulty = Global.Difficulty.EASY;
            sceneLoader.setScene(new NameSelectionScene());
        }));

        // Set difficulty to medium
        table.add(textButton("Medium", Color.RED, () -> {
            Global.app.difficulty = Global.Difficulty.MEDIUM;
            sceneLoader.setScene(new NameSelectionScene());
        }));

        // Set difficulty to hard
        table.add(textButton("Hard", Color.RED, () -> {
            Global.app.difficulty = Global.Difficulty.HARD;
            sceneLoader.setScene(new NameSelectionScene());
        }));
    }

}

