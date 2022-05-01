package main.primary.desktop;

import com.badlogic.gdx.Files.FileType;
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
        config.addIcon("images/SpaceTrader32.png", FileType.Internal);
        config.addIcon("images/SpaceTrader128.png", FileType.Internal);
        new LwjglApplication(new Main(), config);
    }
}
