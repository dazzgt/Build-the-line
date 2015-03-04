package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {
	
	protected GSM gsm;
	protected Vector2 tap;
	public State(GSM gsm){
		this.gsm = gsm;
	}
	
	public void handleInput(){
		if(MyInput.isPressed(MyInput.BACK))
			Gdx.app.exit();
	}
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
