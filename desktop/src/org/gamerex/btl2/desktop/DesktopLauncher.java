package org.gamerex.btl2.desktop;

import java.util.Random;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher{
	static Random rand = new Random();
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = BTL2.WIDTH;
		cfg.height = BTL2.HEIGHT;
		cfg.title = BTL2.TITLE;
		for(int i=0;i<12;i++)
			System.out.print(rand.nextInt(6)+",");
		
		new LwjglApplication(new BTL2(new ActionResolver() {
			public void showInterstital() {}
			public void setTrackerScreenName(String path) {}
			public String getStringResourceByName(String aString) {
				return aString;
			}
			public void bill(String arg) {System.out.println(arg);}
			public void SaveSettings(String name, String Value) {}
			public int getIntSettings(String name) {return 16;}
			public boolean getBoolSettings(String name) {return false;}
			public boolean getAds(){return false;}
			@Override
			public boolean getSignedInGPGS() {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public void loginGPGS() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void submitScoreGPGS(int score) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void unlockAchievementGPGS(String achievementId) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void getLeaderboardGPGS() {
				
				
			}
			@Override
			public void getAchievementsGPGS() {
				// TODO Auto-generated method stub
				
			}
		}), cfg);
	}

}
