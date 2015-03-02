package org.gamerex.btl2.desktop;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = BTL2.WIDTH;
		cfg.height = BTL2.HEIGHT;
		cfg.title = BTL2.TITLE;
		new LwjglApplication(new BTL2(new ActionResolver() {
			public void showInterstital() {}
			public void setTrackerScreenName(String path) {}
			public String getStringResourceByName(String aString) {
				return aString;
			}
		}), cfg);
	}

}
