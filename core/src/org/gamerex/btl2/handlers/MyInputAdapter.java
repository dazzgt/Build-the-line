package org.gamerex.btl2.handlers;

import org.gamerex.btl2.ui.TetrisPiece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MyInputAdapter extends InputAdapter{

	private int touchDownX;
	private int touchDownY;
	private int touchX;
	private int touchY;
	private int angle;
	private int len;
	private int size;
	
	public MyInputAdapter(){
		size = Gdx.graphics.getWidth()/12;
	}

	public boolean touchDown(int X, int Y, int pointer, int button) {
		touchDownX = X;
		touchDownY = Y;
		TetrisPiece.blocked = false;
		return false;
	}

	public boolean touchUp (int X, int Y, int pointer, int button) {
		touchDownX = X;
		touchDownY = Y;
		TetrisPiece.blocked = false;
		return false;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		touchX = x;
		touchY = y;
		angle = (int) ((Math.atan2(touchDownY - touchY , touchDownX - touchX)/Math.PI) * 180);
		angle = (angle < 0) ? angle + 360 : angle;
		len = (int) Math.sqrt(Math.pow(touchX-touchDownX,2)+Math.pow(touchY-touchDownY,2));
		if(TetrisPiece.blocked) return false;
		if((angle>330 || angle<30) && len>size/2)
		{
			MyInput.keys[MyInput.LEFT]=true;
			MyInput.pkeys[MyInput.LEFT]=false;
			touchDownX-=size*0.75;
		}
		else if(angle>150 && angle<210 && len>size/2)
		{
			MyInput.keys[MyInput.RIGHT]=true;
			MyInput.pkeys[MyInput.RIGHT]=false;
			touchDownX+=size*0.75;
		}
		else if(angle>240 && angle<300 && len>size/2)
		{
			MyInput.keys[MyInput.DOWN]=true;
			MyInput.pkeys[MyInput.DOWN]=false;
			touchDownY+=size*0.3;
		}
		return false;
		
	}
	
	public boolean keyDown(int k){
		switch(k)
		{
		case Keys.LEFT:
			MyInput.setKey(MyInput.LEFT, true);
			break;
		case Keys.RIGHT:
			MyInput.setKey(MyInput.RIGHT, true);
			break;
		case Keys.SPACE:
		case Keys.UP:
			MyInput.setKey(MyInput.SPACE, true);
			break;
		case Keys.DOWN:
			MyInput.setKey(MyInput.DOWN, true);
			break;
		case Keys.BACK:
			MyInput.setKey(MyInput.BACK, true);
			break;
		}
		return true;
	}
	
	
	public boolean keyUp(int k){
		switch(k)
		{
		case Keys.LEFT:
			MyInput.setKey(MyInput.LEFT, false);
			break;
		case Keys.RIGHT:
			MyInput.setKey(MyInput.RIGHT, false);
			break;
		case Keys.SPACE:
		case Keys.UP:
			MyInput.setKey(MyInput.SPACE, false);
			break;
		case Keys.DOWN:
			MyInput.setKey(MyInput.DOWN, false);
			break;
		case Keys.BACK:
			MyInput.setKey(MyInput.BACK, false);
			break;
		}
		return true;
	}
}
