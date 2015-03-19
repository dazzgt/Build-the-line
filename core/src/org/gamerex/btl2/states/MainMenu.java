package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu extends State{


	private MyString play,settings,achievements,leaderboard;
	private Rectangle boundPlay,boundSettings,boundsAchievements,boundsLeaderboard;

	public MainMenu(GSM gsm) {
		super(gsm);
		gsm.ar.setTrackerScreenName("Main Menu");
		play =  new MyString(new Character[]{'P','L','A','Y'},gsm.colors, new int[]{0,1,2,3,4,5,6});
		play.size = gsm.scW/35;
		play.x=(gsm.scW-play.size*play.length)/2;
		play.y=gsm.scH*0.6f;

		
		settings = new MyString(new Character[]{'S','E','T','T','I','N','G','S'},gsm.colors, new int[]{0,5,6,4,2,1,0,6,3});
		settings.size = (play.size*play.length)/47;
		settings.x = (gsm.scW-settings.size*47)/2;
		settings.y=gsm.scH*0.5f;
		
		achievements = new MyString(new Character[]{'A','C','H','I','E','V','E','M','E','N','T','S'},gsm.colors, new int[]{0,4,3,4,2,1,2,5,3,4,3,5});
		achievements.size = (play.size*play.length)/achievements.length;
		achievements.x = play.x;
		achievements.y=gsm.scH*0.38f;
		
		leaderboard = new MyString(new Character[]{'L','E','A','D','E','R','B','O','A','R','D'},gsm.colors, new int[]{1,5,4,5,1,0,1,5,0,1,4,1});
		leaderboard.size = (play.size*play.length)/leaderboard.length;
		leaderboard.x = play.x;
		leaderboard.y=gsm.scH*0.3f;
		
		boundPlay = new Rectangle(play.x, play.y, play.size*29, play.size*7);
		boundSettings = new Rectangle(settings.x, settings.y, settings.size*50, settings.size*7);
		boundsAchievements = new Rectangle(achievements.x, achievements.y, achievements.size*achievements.length, achievements.size*7);
		boundsLeaderboard = new Rectangle(leaderboard.x, leaderboard.y, leaderboard.size*leaderboard.length, leaderboard.size*7);
		
	}

	public void handleInput() {
		if(!(MyInput.keys[MyInput.BACK]&&MyInput.pkeys[MyInput.BACK]))
		super.handleInput();
		tap = MyInput.getTap();
		if(tap==null) return;
		
		if(boundPlay.contains(tap.x, tap.y))
			gsm.set(new GameState(gsm));
		
		if(boundSettings.contains(tap.x, tap.y))
			gsm.push(new SettingsState(gsm));
		
		if(boundsAchievements.contains(tap.x, tap.y))
			if (gsm.ar.getSignedInGPGS()) 
			{
				gsm.ar.getAchievementsGPGS();
				gsm.ar.setTrackerScreenName("Achievements");
			}
			else gsm.ar.loginGPGS();
		
		if(boundsLeaderboard.contains(tap.x, tap.y))
			if (gsm.ar.getSignedInGPGS()) 
			{
				gsm.ar.getLeaderboardGPGS();
				gsm.ar.setTrackerScreenName("Leaderboard");
			}
			else gsm.ar.loginGPGS();		
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		drawMyString(play, sb);
		drawMyString(settings, sb);
		drawMyString(achievements, sb);
		drawMyString(leaderboard, sb);
	}
}
