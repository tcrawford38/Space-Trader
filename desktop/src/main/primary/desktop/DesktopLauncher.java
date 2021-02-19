package main.primary.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import main.primary.SpaceTrader;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.title = "Space Trader";
		config.width = 800;
		config.height = 600;
		config.forceExit = true;
		new LwjglApplication(new SpaceTrader(), config);
	}
}
