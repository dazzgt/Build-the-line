package org.gamerex.btl2.desktop;

import java.util.Random;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher{
	static Random rand = new Random();
	public static float calcCountdown(int speed){
		float res=1.15f;
		for(int i = 0; i<speed;i++)
			res-=(0.12-0.0071*i);
		return res;
	}
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = BTL2.WIDTH;
		cfg.height = BTL2.HEIGHT;
		cfg.title = BTL2.TITLE;
		for(int i=1;i<=20;i++)
			System.out.println(i+": "+calcCountdown(i));
		
		new LwjglApplication(new BTL2(new ActionResolver() {
			public void showInterstital() {}
			public void setTrackerScreenName(String path) {}
			public String getStringResourceByName(String aString) {
				return aString;
			}
			public void bill(String arg) {System.out.println(arg);}
			public void SaveSettings(String name, String Value) {}
			public int getIntSettings(String name) {return 1;}
			public boolean getBoolSettings(String name) {return false;}
			public boolean getAds(){return false;}
			public boolean getSignedInGPGS() {return false;}
			public void loginGPGS() {}
			public void submitScoreGPGS(int score) {}
			public void unlockAchievementGPGS(String achievementId) {}
			public void getLeaderboardGPGS() {}
			public void getAchievementsGPGS() {}
			public void incrementAchievementGPGS(String achievementId) {}
		}), cfg);
	}

}
