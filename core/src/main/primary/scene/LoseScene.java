package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import main.primary.Global;
import main.primary.gameplay.Player;

public class LoseScene extends Scene {

	private Player player;

	public void create() {
		this.player = Global.app.player;

		// Temporary background
		sceneLoader.setBackground("images/menu_background.jpg");

		// Game over title
		if (player.credits <= 0) {
			table.add(title("GAMEOVER\nDead broke...", Color.RED));
		} else if (player.getShip().getHP() <= 0) {
			table.add(title("GAME OVER\nBlown to pieces...", Color.RED));
		}

		table.padBottom(210f);
		table.row();

		// Button to start a new game
		table.add(textButton("Restart", Color.WHITE, () -> sceneLoader.setScene(new WelcomeScene())));
	}
}