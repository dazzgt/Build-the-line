package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu extends State{

	
	public MainMenu(GSM gsm) {
		super(gsm);
	}

	public void handleInput() {
		tap = MyInput.getTap();
		if(tap!=null) return;
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		
	}

}
