package org.gamerex.btl2.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class MyGestureAdapter extends GestureAdapter{
	
	public boolean tap (float x, float y, int count, int button) {
		MyInput.setTap(x, Gdx.graphics.getHeight()-y);
		return true;
	}

}
