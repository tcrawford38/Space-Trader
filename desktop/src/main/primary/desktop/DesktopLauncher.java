package main.primary.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import main.primary.Global;
import main.primary.Main;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.title = "Space Trader";
        config.width = Global.WIDTH;
        config.height = Global.HEIGHT;
        config.forceExit = true;
        new LwjglApplication(new Main(), config);
    }
}
