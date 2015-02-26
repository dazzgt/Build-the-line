package org.gamerex.btl2.states;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GSM {

	public int scW;
	public int scH;
	private Stack<State> states;
	private State bg;
	ActionResolver ar;
	
	public GSM(ActionResolver ar){
		this.ar = ar;
		states = new Stack<State>();
		scW = Gdx.graphics.getWidth();
		scH = Gdx.graphics.getHeight();
		bg = new Background(this);
		
	}
	
	public void push(State s){
		states.push(s);
	}
	public void pop(){
		states.pop();
	}
	public void set(State s){
		states.pop();
		states.push(s);
	}
	public void update(float dt){
		bg.update(dt);
		states.peek().update(dt);
	}
	
	public void render(SpriteBatch sb){
		bg.render(sb);
		states.peek().render(sb);
	}
}
