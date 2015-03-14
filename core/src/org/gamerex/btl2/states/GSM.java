package org.gamerex.btl2.states;

import java.util.Stack;

import org.gamerex.btl2.handlers.MyInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GSM {

	public int scW;
	public int scH;
	private Stack<State> states;
	private State bg;
	public int Speed;
	public int Score = 0;
	public int defSpeed=1;
	public ActionResolver ar;
	public static GSM instance;

	public Texture cloud;
	public Texture pixel;
	public Texture cell;
	public Color[] colors;

	public GSM(ActionResolver actionResolver){
		this.ar = actionResolver;

		loadTexture();
		defSpeed = actionResolver.getIntSettings("Speed");
		Speed = defSpeed;
		instance=this;
		states = new Stack<State>();
		scW = Gdx.graphics.getWidth();
		scH = Gdx.graphics.getHeight();
		push(new MainMenu(this));
		bg = new Background(this);

	}

	public void loadTexture() {
		cloud = new Texture("cloud.png");
		pixel = new Texture("pixel.png");
		cell = new Texture("cell.png");
		colors = new Color[]{Color.RED,new Color(1, 0.6f, 0, 1),Color.YELLOW,Color.GREEN,Color.CYAN,new Color(0, 0.3f, 0.9f, 1),new Color(0.588f, 0, 0.8f, 1)};
	}

	public void push(State s){
		states.push(s);
	}
	public void pop(){
		states.pop();
	}
	public State getState(){
		return states.peek();
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
	public void dispose(){
		cloud.dispose();;
		pixel.dispose();;
		cell.dispose();
	}
}
