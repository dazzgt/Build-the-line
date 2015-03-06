package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu extends State{


	private MyString play;
	private Rectangle boundPlay;

	public MainMenu(GSM gsm) {
		super(gsm);
		gsm.actionResolver.setTrackerScreenName("org.gamerex.btl2.states.MainMenu");
		play =  new MyString(new Character[]{'P','L','A','Y'},gsm.colors, new int[]{0,1,2,3,4,5,6});
		play.size = gsm.scW/35;
		play.x=(gsm.scW-play.size*23)/2;
		play.y=gsm.scH/2;
		boundPlay = new Rectangle(play.x, play.y, play.size*29, play.size*7);
	}

	public void handleInput() {
		super.handleInput();
		tap = MyInput.getTap();
		if(tap==null) return;
		if(boundPlay.contains(tap.x, tap.y))
			gsm.set(new GameState(gsm));
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		drawMyString(play, sb);
	}
}
