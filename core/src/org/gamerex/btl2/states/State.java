package org.gamerex.btl2.states;

import org.gamerex.btl2.ui.Chars;
import org.gamerex.btl2.ui.MyChar;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {
	
	protected GSM gsm;
	protected Vector2 tap;
	public State(GSM gsm){
		this.gsm = gsm;
	}
	
	public void handleInput(){
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			  gsm.push(new LeaveState(gsm));
			}
	}
	
	public int drawMyString(MyString s,SpriteBatch sb){
		int w=0;
		MyChar[] arr=s.getArr();
		for (int i=0;i<arr.length;i++){
			sb.setColor(arr[i].color);
			for(int j=0;j<arr[i].arr[0].length;j++)
				sb.draw(gsm.cell,(arr[i].arr[0][j]+w)*s.size+s.x,arr[i].arr[1][j]*s.size+s.y,s.size,s.size);
			w+=6;
		}
		return w;
	}
	public int drawDynamicString(int x,int y, int size,String s,Color color,SpriteBatch sb){
		int w=0,z=0;
		MyChar[] arr = new MyChar[]{null,null,null,null};
		for (Character myChar : s.toCharArray()) {
			arr[z++] = Chars.getChar(myChar);
		}
		sb.setColor(color);
		for (int i=0;i<arr.length;i++){
			if(arr[i]==null)break;
			for(int j=0;j<arr[i].arr[0].length;j++)
				sb.draw(gsm.cell,(arr[i].arr[0][j]+w)*size+x,arr[i].arr[1][j]*size+y,size,size);
			w+=6;
		}
		return w;
	}
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
