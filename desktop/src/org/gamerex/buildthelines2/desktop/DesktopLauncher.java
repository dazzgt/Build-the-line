package org.gamerex.buildthelines2.desktop;

import org.gamerex.buildthelines2.BTL2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = BTL2.WIDTH;
		cfg.height = BTL2.HEIGHT;
		cfg.title = BTL2.TITLE;
		new LwjglApplication(new BTL2(), cfg);
	}
}
