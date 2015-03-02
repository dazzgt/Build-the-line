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
	public int Speed = 1;
	public int Score = 0;
	public ActionResolver actionResolver;
	public static GSM instance;
	
	public GSM(ActionResolver actionResolver){
		this.actionResolver = actionResolver;
		instance=this;
		states = new Stack<State>();
		scW = Gdx.graphics.getWidth();
		scH = Gdx.graphics.getHeight();
		bg = new Background(this);
		push(new MainMenu(this));
		
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
		sb.setColor(1,1,1,1);
	}
}
