package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
import org.gamerex.btl2.ui.MyString;
import org.gamerex.btl2.ui.TetrisPiece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameState extends State{

	private Rectangle pauseBounds;
	int pbSize;

	int scW,scH, size;
	int level=1;
	float countdown;
	boolean rainbow = true;
	Color colors[] ;
	private TetrisPiece tetris;
	private Texture cell;
	private Texture texPause,texBack;
	private boolean isGOScreen = true;

	private MyString gameOver;

	public GameState(GSM gsm) {
		super(gsm);
		gsm.ar.setTrackerScreenName("Game State");
		
		tetris = new TetrisPiece();
		if(rainbow)
			colors = new Color[]{Color.RED,new Color(1, 0.6f, 0, 1),Color.YELLOW,Color.GREEN,Color.CYAN,new Color(0, 0.3f, 0.9f, 1),new Color(0.588f, 0, 0.8f, 1)};
		else
			colors = new Color[]{Color.CYAN,Color.MAGENTA,Color.BLUE,Color.GREEN,Color.PURPLE,Color.PINK,Color.ORANGE};

		cell = new Texture("cell.png");
		texPause = new Texture("pause.png");
		texBack = new Texture("pixel.png");


		gameOver =  new MyString(new Character[]{'G','A','M','E',' ','O','V','E','R'},gsm.colors, new int[]{0,1,2,3,3,4,5,6,3});
		gameOver.size = gsm.scW/52;
		gameOver.x=(gsm.scW-gameOver.size*48)/2;
		gameOver.y=(int) (gsm.scH*0.6);
		
		scW = Gdx.graphics.getWidth();
		scH = Gdx.graphics.getHeight();	
		pbSize = scH/10;
		pauseBounds = new Rectangle((scW-pbSize)/2, (float) (scH-pbSize*1.5), pbSize, pbSize);
		size = (int) (scW/tetris.brdW);
		countdown = calcCountdown(gsm.Speed);

		tetris.NewGame();
	}

	public void handleInput() {
		super.handleInput();
		tap=MyInput.getTap();
		if(tap!=null)
		{
			if(tetris.go)
			{
				gsm.ar.setTrackerScreenName("Game State");
				tetris.NewGame();
			}
			else
				if(pauseBounds.contains(tap.x,tap.y))
					gsm.push(new MenuPause(gsm));			
				else
					tetris.rotate();
		}
		if(MyInput.isPressed(MyInput.LEFT))
			tetris.move(-1);
		if(MyInput.isPressed(MyInput.RIGHT))
			tetris.move(1);
		if(MyInput.isPressed(MyInput.DOWN))
			tetris.moveY(-1);
		if(MyInput.isPressed(MyInput.SPACE))
			tetris.rotate();
	}

	public void update(float dt) {
		handleInput();
		if(!tetris.go){
			countdown-=dt;
			isGOScreen=true;
		}
	}

	public void render(SpriteBatch sb) {
		for(int i=0;i<tetris.brdW;i++){
			for(int j=0;j<tetris.brdH;j++){
				if(tetris.board[i][j]==0)
					continue;
				sb.setColor(colors[tetris.board[i][j]-1]);
				sb.draw(cell,(i*size), (j*size),size,size);
			}
		}

		//������� ������
		sb.setColor(colors[tetris.type]);
		sb.draw(cell,tetris.X*size, tetris.Y*size,size,size);
		for(int i =0;i<3;i++)
			sb.draw(cell,(tetris.X*size+tetris.StatesX.get(tetris.type)[tetris.rotateState][i]*size), (tetris.Y*size+tetris.StatesY.get(tetris.type)[tetris.rotateState][i]*size),size,size);

		//��������� ������
		sb.setColor(colors[tetris.typeNext]);
		int sd=2;
		sb.draw(cell,size, scH-size,size/sd,size/sd);
		for(int i=0;i<3;i++)
			sb.draw(cell,size +tetris.StatesX.get(tetris.typeNext)[0][i]*(size/sd), scH-size +tetris.StatesY.get(tetris.typeNext)[0][i]*(size/sd),size/sd,size/sd);
		//������ �����
		sb.setColor(1,1,1,0.1f);
		sb.draw(texPause,pauseBounds.x, pauseBounds.y, pauseBounds.width, pauseBounds.height);
		sb.setColor(1,1,1,1f);

		if(countdown<=0)
		{
			tetris.moveY(-1);
			countdown = calcCountdown(gsm.Speed);
		}
		if(tetris.go)
		{		
			if(isGOScreen){
				if (gsm.ar.getSignedInGPGS()) {
					gsm.ar.submitScoreGPGS(gsm.Score);
					if (gsm.Score >= 100)  gsm.ar.unlockAchievementGPGS("achievement_100");
					if (gsm.Score >= 250)  gsm.ar.unlockAchievementGPGS("achievement_250");
					if (gsm.Score >= 500)  gsm.ar.unlockAchievementGPGS("achievement_500");
					if (gsm.Score >= 1000) gsm.ar.unlockAchievementGPGS("achievement_1000");
					if (gsm.Score >= 2000) gsm.ar.unlockAchievementGPGS("achievement_2000");
					if (gsm.Score >= 3000) gsm.ar.unlockAchievementGPGS("achievement_god");
					if (gsm.Score >= 100)  gsm.ar.incrementAchievementGPGS("achievement_apprentice");
					if (gsm.Score >= 500)  gsm.ar.incrementAchievementGPGS("achievement_skilled");
					if (gsm.Score >= 1000) gsm.ar.incrementAchievementGPGS("achievement_master");
					if (gsm.Score >= 1500) gsm.ar.incrementAchievementGPGS("achievement_pro_gamer");
					if (gsm.Score >= 2500) gsm.ar.incrementAchievementGPGS("achievement_chosen_one");
					gsm.ar.incrementAchievementGPGS("achievement_dedicated");
				}
				gsm.ar.setTrackerScreenName("Game Over");
				if(gsm.ar.getAds())gsm.ar.showInterstital();
				isGOScreen = false;
			}
			sb.setColor(0,0, 0, 0.6f);
			sb.draw(texBack, 0, 0, scW, scH);
			drawMyString(gameOver, sb);
		}
	}
	
	public static float calcCountdown(int speed){
		float res=1.15f;
		for(int i = 0; i<speed;i++)
			res-=(0.12-0.0071*i);
		return res;
	}

}
