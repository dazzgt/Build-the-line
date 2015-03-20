package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LeaveState extends State{

	
	private MyString leave,menu;
	private MyString yes;
	private MyString no;

	private Texture texRestart;
	private Rectangle boundYes;
	private Rectangle boundNo,boundsMenu,boundsRestart;
	int y,size;

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
		
		menu = new MyString(new Character[]{'M','E','N','U'},gsm.colors, new int[]{1,5,6,4,2,1,0,6,3});
		menu.size = leave.size;
		menu.x = (gsm.scW-menu.size*menu.length)/2;
		menu.y=gsm.scH*0.4f;
		
		texRestart = new Texture(Gdx.files.internal("restart.png"));
		size = gsm.scH/10;
		
		boundYes = new Rectangle(yes.x-2*yes.size, yes.y-2*yes.size, yes.size*19, yes.size*9);
		boundNo = new Rectangle(no.x-2*no.size, no.y-2*no.size, no.size*13, no.size*9);
		boundsMenu = new Rectangle(menu.x, menu.y, menu.size*menu.length, menu.size*7);
		boundsRestart = new Rectangle((gsm.scW-size)/2, size, size, size);
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
		if(boundsRestart.contains(tap.x, tap.y))
			gsm.set(new GameState(gsm));
	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		drawMyString(leave, sb);
		drawMyString(yes, sb);
		drawMyString(no, sb);
		drawMyString(menu, sb);
		sb.setColor(1,1,1,0.5f);
		sb.draw(texRestart, boundsRestart.x, boundsRestart.y, boundsRestart.width, boundsRestart.height);
		sb.setColor(1,1,1,1f);
	}
}
