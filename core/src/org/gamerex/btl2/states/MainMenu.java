package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyChar;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu extends State{

	
	private Texture tex;
	private MyString play;
	private int size;
	private int X;
	private int Y;
	private Rectangle boundPlay;

	public MainMenu(GSM gsm) {
		super(gsm);
		tex = new Texture("cell.png");
		Color[] colors= new Color[]{Color.RED,new Color(1, 0.6f, 0, 1),Color.YELLOW,Color.GREEN,Color.CYAN,new Color(0, 0.3f, 0.9f, 1),new Color(0.588f, 0, 0.8f, 1)};
		play =  new MyString(new Character[]{'P','L','A','Y'},colors, new int[]{0,1,2,3,4,5,6});
		size = gsm.scW/30;
		X=(gsm.scW-size*23)/2;
		Y=gsm.scH/2;
		boundPlay = new Rectangle(X, Y, size*29, size*7);
	}

	public void handleInput() {
		tap = MyInput.getTap();
		if(tap==null) return;
		if(boundPlay.contains(tap.x, tap.y))
			gsm.set(new GameState(gsm));
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		int w=0;
		MyChar[] arr=play.getArr();
		for (int i=0;i<arr.length;i++){
			sb.setColor(arr[i].color);
			for(int j=0;j<arr[i].arr[0].length;j++)
				sb.draw(tex,(arr[i].arr[0][j]+w)*size+X,arr[i].arr[1][j]*size+Y,size,size);
			w+=6;
		}
	}

}
