package org.gamerex.btl2.ui;

import java.util.Random;

import org.gamerex.btl2.states.GSM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Box {
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected int speed = 10;
	protected GSM gsm;
	protected float scale;
	protected static final Texture cloud = new Texture("cloud.png");
	protected static final Random rand = new Random();
	
	public Box(GSM gsm){ 
		this.gsm = gsm; 
		speed = gsm.scW/20;
	}
	
	public abstract void render(SpriteBatch sb);
	public abstract void update(float dt);
	
}
