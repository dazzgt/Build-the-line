package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LeaveState extends State{

	
	private MyString leave,menu;
	private MyString yes;
	private MyString no;

	private Rectangle boundYes;
	private Rectangle boundNo,boundsMenu;

	public LeaveState(GSM gsm) {
		super(gsm);

		gsm.ar.setTrackerScreenName("Leave State");
		leave =  new MyString(new Character[]{'L','E','A','V','I','N','G','?'},gsm.colors, new int[]{0,1,2,3,4,5,6,1});
		yes =  new MyString(new Character[]{'Y','E','S'},gsm.colors, new int[]{3,2,1});
		no =  new MyString(new Character[]{'N','O'},gsm.colors, new int[]{0,4});
		
		leave.size = gsm.scW/52;
		leave.x=(gsm.scW-leave.size*47)/2;
		leave.y=(int) (gsm.scH/1.5);
		
		no.size = leave.size;
		no.x = leave.x;
		no.y = leave.y - 12*no.size;	
		
		yes.size = leave.size;
		yes.x = leave.x+30*leave.size;	
		yes.y = no.y;
		
		menu = new MyString(new Character[]{'M','A','I','N',' ','M','E','N','U'},gsm.colors, new int[]{1,5,6,4,2,1,0,6,3});
		menu.size = (leave.size*leave.length)/menu.length;
		menu.x = leave.x;
		menu.y=gsm.scH*0.4f;
		
		boundYes = new Rectangle(yes.x-2*yes.size, yes.y-2*yes.size, yes.size*19, yes.size*9);
		boundNo = new Rectangle(no.x-4*no.size, no.y-4*no.size, no.size*15, no.size*11);
		boundsMenu = new Rectangle(menu.x, menu.y, menu.size*menu.length, menu.size*7);
	}

	public void handleInput() {
		super.handleInput();
		tap = MyInput.getTap();
		if(tap==null) return;
		if(boundYes.contains(tap.x, tap.y))
			Gdx.app.exit();
		if(boundNo.contains(tap.x, tap.y))
			gsm.pop();
		if(boundsMenu.contains(tap.x, tap.y)){
			gsm.pop();
			gsm.set(new MainMenu(gsm));
		}
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		drawMyString(leave, sb);
		drawMyString(yes, sb);
		drawMyString(no, sb);
		drawMyString(menu, sb);
	}
}
