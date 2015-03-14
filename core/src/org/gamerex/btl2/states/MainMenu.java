package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu extends State{


	private MyString play,settings;
	private Rectangle boundPlay,boundSettings;

	public MainMenu(GSM gsm) {
		super(gsm);
		gsm.ar.setTrackerScreenName("org.gamerex.btl2.states.MainMenu");
		play =  new MyString(new Character[]{'P','L','A','Y'},gsm.colors, new int[]{0,1,2,3,4,5,6});
		play.size = gsm.scW/35;
		play.x=(gsm.scW-play.size*23)/2;
		play.y=gsm.scH*0.6f;
		
		settings = new MyString(new Character[]{'S','E','T','T','I','N','G','S'},gsm.colors, new int[]{0,5,6,4,2,1,0,6,3});
		settings.size = (play.size*23)/47;
		settings.x = (gsm.scW-settings.size*47)/2;
		settings.y=gsm.scH*0.5f;
		
		boundPlay = new Rectangle(play.x, play.y, play.size*29, play.size*7);
		boundSettings = new Rectangle(settings.x, settings.y, settings.size*50, settings.size*7);
		
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
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		drawMyString(play, sb);
		drawMyString(settings, sb);
	}
}
