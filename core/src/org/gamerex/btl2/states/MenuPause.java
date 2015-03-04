package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyChar;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuPause extends State{

	Texture tex;
	int size;
	private MyString pause,back,menu,exit,restart;
	
	public MenuPause(GSM gsm) {
		super(gsm);
		tex = new Texture("cell.png");
		Color[] colors= new Color[]{Color.RED,new Color(1, 0.6f, 0, 1),Color.YELLOW,Color.GREEN,Color.CYAN,new Color(0, 0.3f, 0.9f, 1),new Color(0.588f, 0, 0.8f, 1)};
		pause =  new MyString(new Character[]{'P','A','U','S','E'},colors, new int[]{0,1,2,3,4,5,6});
		size = gsm.scW/35;
		pause.x=(gsm.scW-size*29)/2;
		pause.y=(int) (gsm.scH*0.75);
		}

	public void handleInput() {
		tap = MyInput.getTap();
		if(tap!=null)
			gsm.pop();
	}

	public void update(float dt) {
		handleInput();	
	}

	public void render(SpriteBatch sb) {
		int w=0;
		MyChar[] arr=pause.getArr();
		for (int i=0;i<arr.length;i++){
			sb.setColor(arr[i].color);
			for(int j=0;j<arr[i].arr[0].length;j++)
				sb.draw(tex,(arr[i].arr[0][j]+w)*size+pause.x,arr[i].arr[1][j]*size+pause.y,size,size);
			w+=6;
		}
	}

}
