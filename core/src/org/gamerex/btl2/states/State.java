package org.gamerex.btl2.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {
	
	protected GSM gsm;
	protected Vector2 tap;
	public State(GSM gsm){
		this.gsm = gsm;
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
