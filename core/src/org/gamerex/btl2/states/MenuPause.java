package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuPause extends State{

	private MyString pause;//,back,menu,exit,restart;
	

	public MenuPause(GSM gsm) {
		super(gsm);
		gsm.actionResolver.setTrackerScreenName("org.gamerex.btl2.states.MenuPause");
		pause =  new MyString(new Character[]{'P','A','U','S','E'},gsm.colors, new int[]{0,1,2,3,4,5,6});
		pause.size = gsm.scW/35;
		pause.x=(gsm.scW-pause.size*29)/2;
		pause.y=(int) (gsm.scH*0.75);
		}

	public void handleInput() {
		super.handleInput();
		tap = MyInput.getTap();
		if(tap!=null)
			gsm.pop();
	}

	public void update(float dt) {
		handleInput();	
	}

	public void render(SpriteBatch sb) {
		drawMyString(pause, sb);
	}

}
