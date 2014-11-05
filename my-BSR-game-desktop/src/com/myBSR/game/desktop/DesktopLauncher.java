package com.myBSR.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.myBSR.game.BSRmenu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "AssFuckRobot";
		config.width = 900;
		config.height = 400;
		new LwjglApplication(new BSRmenu(), config);
	}
}
