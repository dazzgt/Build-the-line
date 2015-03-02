package org.gamerex.btl2.states;

import java.util.Stack;

import org.gamerex.btl2.handlers.MyInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GSM {

	public int scW;
	public int scH;
	private Stack<State> states;
	private State bg;
	ActionResolver actionResolver;
	
	public GSM(ActionResolver actionResolver){
		this.actionResolver = actionResolver;
		states = new Stack<State>();
		scW = Gdx.graphics.getWidth();
		scH = Gdx.graphics.getHeight();
		bg = new Background(this);
		push(new GameState(this));
		
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
		MyInput.update();
	}
	
	public void render(SpriteBatch sb){
		bg.render(sb);
		states.peek().render(sb);
	}
}
