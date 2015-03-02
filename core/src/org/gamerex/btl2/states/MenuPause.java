package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.Chars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MenuPause extends State{

	Texture tex;
	
	public MenuPause(GSM gsm) {
		super(gsm);
		tex = new Texture("cell.png");
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
			for(int j=0;j<6;j++)
				sb.draw(tex,Chars.chA[0][j]*10,Chars.chA[1][j]*10,10,10);
	}

}
