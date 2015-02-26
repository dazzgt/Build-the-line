package org.gamerex.btl2.ui;

import org.gamerex.btl2.states.GSM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cloud extends Box{
	private static float per = 0.2f;
	
	public Cloud(GSM gsm){
		super(gsm);
		height = (float) (gsm.scH*per);
		width = cloud.getWidth()/(cloud.getHeight()/height);
		y = (float) (gsm.scH*(1-per));
		x = rand.nextInt(gsm.scW);
		scale = (float)(rand.nextInt(50)+25)/100;
	}
	public void render(SpriteBatch sb) {
		sb.draw(cloud, x, y, width*scale, height*scale);
	}

	public void update(float dt) {
		if(x<gsm.scW)
			x+=speed*dt*scale;
		else 
		{
			scale = (float)(rand.nextInt(50)+25)/100;
			y = (float) (gsm.scH*(1-per))+rand.nextInt((int) (height - height*scale));
			x = -width*scale;
		}
	}	
}
