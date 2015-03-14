package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SettingsState extends State{

	private MyString defaultSpeed,yes,no,ads;

	private Rectangle boundYes, boundNo,boundAds;

	public SettingsState(GSM gsm) {
		super(gsm);

		gsm.ar.setTrackerScreenName("Settings State");
		defaultSpeed =  new MyString(new Character[]{'D','E','F','A','U','L','T',' ','S','P','E','E','D'},gsm.colors, new int[]{0,1,2,3,4,5,6,1,0,1,2,3,4,5,6,1});
		yes =  new MyString(new Character[]{'+'},gsm.colors, new int[]{0});
		no =  new MyString(new Character[]{'-'},gsm.colors, new int[]{0});

		defaultSpeed.size = gsm.scW/83;
		defaultSpeed.x=(int)(gsm.scW-(int)(defaultSpeed.size)*72)/2;
		defaultSpeed.y=(int) (gsm.scH/1.5);

		no.size = defaultSpeed.size;
		no.x = defaultSpeed.x;
		no.y = defaultSpeed.y - 12*no.size;	

		yes.size = defaultSpeed.size;
		yes.x = defaultSpeed.x+68*defaultSpeed.size;	
		yes.y = no.y;

		boundYes = new Rectangle(yes.x-4*yes.size, yes.y-4*yes.size, yes.size*11, yes.size*11);
		boundNo = new Rectangle(no.x-4*no.size, no.y-4*no.size, no.size*11, no.size*11);

		ads = new MyString(new Character[]{'R','E','M','O','V','E',' ','A','D','S'},gsm.colors, new int[]{0,0,0,0,0,0,0,0,0,0,0,0});
		ads.size = defaultSpeed.size;
		ads.x = (gsm.scW-ads.size*59)/2;
		ads.y=gsm.scH*0.1f;
		boundAds = new Rectangle(ads.x-ads.size, ads.y-ads.size, ads.size*62, ads.size*9);
	}

	public void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.BACK)||Gdx.input.isKeyPressed(Keys.ESCAPE)){
			gsm.pop();
		}
		tap = MyInput.getTap();
		if(tap==null)
			return;
		if(boundNo.contains(tap.x, tap.y))
			if(gsm.defSpeed>1){
				gsm.defSpeed-=1;
				gsm.ar.SaveSettings("Speed", String.valueOf(gsm.defSpeed));
			}
		if(boundYes.contains(tap.x, tap.y))
			if(gsm.defSpeed<20){
				gsm.defSpeed+=1;
				gsm.ar.SaveSettings("Speed", String.valueOf(gsm.defSpeed));
			}
		if(gsm.ar.getAds())
			if(boundAds.contains(tap.x, tap.y))
				gsm.ar.bill("Ads");
	}
	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		drawMyString(defaultSpeed, sb);
		drawMyString(yes, sb);
		drawMyString(no, sb);
		drawDynamicString((no.x+yes.x)/2, no.y, no.size, String.valueOf(gsm.defSpeed), Color.RED, sb);
		if(gsm.ar.getAds())drawMyString(ads, sb);
	}

}
