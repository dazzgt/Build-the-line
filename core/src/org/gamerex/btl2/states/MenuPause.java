package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MenuPause extends State{

	private MyString pause,back,menu,restart;
	private Rectangle menuBounds,restartBounds,backBounds;

	public MenuPause(GSM gsm) {
		super(gsm);
		gsm.ar.setTrackerScreenName("Menu Pause");
		pause =  new MyString(new Character[]{'P','A','U','S','E'},gsm.colors, new int[]{1,2,3,4,5,6});
		menu =  new MyString(new Character[]{'M','A','I','N',' ','M','E','N','U'},gsm.colors, new int[]{1,6,5,3,0,1,2,0,6,0,1,2});
		restart =  new MyString(new Character[]{'R','E','S','T','A','R','T'},gsm.colors, new int[]{0,1,2,3,4,5,6});
		back =  new MyString(new Character[]{'B','A','C','K'},gsm.colors, new int[]{0,1,3,5});
		
		pause.size = gsm.scW/35;
		pause.x=(gsm.scW-pause.size*pause.length)/2;
		pause.y=(int) (gsm.scH*0.65);
		
		menu.size = (int)(gsm.scW/(menu.length+15));
		menu.x=(gsm.scW-menu.size*menu.length)/2;
		menu.y=(int) (gsm.scH*0.5);
		
		restart.size = menu.size;
		restart.x=(gsm.scW-restart.size*restart.length)/2;
		restart.y=(int) (gsm.scH*0.4);
		
		back.size = menu.size;
		back.x=(gsm.scW-back.size*back.length)/2;
		back.y=(int) (gsm.scH*0.3);
		
		menuBounds = new Rectangle(menu.x, menu.y, menu.size*menu.length, menu.size*7);
		restartBounds = new Rectangle(restart.x, restart.y, restart.size*restart.length, restart.size*7);
		backBounds = new Rectangle(back.x, back.y, back.size*back.length, back.size*7);
	}

	public void handleInput() {
		super.handleInput();
		tap = MyInput.getTap();
		if(tap==null)
			return;
		if(backBounds.contains(tap))
			gsm.pop();
		if(restartBounds.contains(tap))
			gsm.set( new GameState(gsm));
		if(menuBounds.contains(tap))
			gsm.set(new MainMenu(gsm));
	}

	public void update(float dt) {
		handleInput();	
	}

	public void render(SpriteBatch sb) {
		drawMyString(pause, sb);
		drawMyString(menu, sb);
		drawMyString(restart, sb);
		drawMyString(back, sb);
	}

}
