package org.gamerex.btl2.states;

import org.gamerex.btl2.handlers.MyInput;
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
	private Texture texPause;
	public GameState(GSM gsm) {
		super(gsm);
		tetris = new TetrisPiece();
		if(rainbow)
	    	colors = new Color[]{Color.RED,new Color(1, 0.6f, 0, 1),Color.YELLOW,Color.GREEN,Color.CYAN,new Color(0, 0.3f, 0.9f, 1),new Color(0.588f, 0, 0.8f, 1)};
	    else
	    	colors = new Color[]{Color.CYAN,Color.MAGENTA,Color.BLUE,Color.GREEN,Color.PURPLE,Color.PINK,Color.ORANGE};
		
		cell = new Texture("cell.png");
		texPause = new Texture("pause.png");
		
		scW = Gdx.graphics.getWidth();
		scH = Gdx.graphics.getHeight();	
		pbSize = scH/10;
		pauseBounds = new Rectangle((scW-pbSize)/2, pbSize/2, pbSize, pbSize);
		size = (int) (scW/tetris.brdW);
		countdown = (float)(0.1*(11-(int)(tetris.lines/5)));
		
		tetris.beginPath();
	}

	public void handleInput() {
		tap=MyInput.getTap();
		if(MyInput.isPressed(MyInput.LEFT))
			tetris.move(-1);
		if(MyInput.isPressed(MyInput.RIGHT))
			tetris.move(1);
		if(MyInput.isDown(MyInput.DOWN))
			tetris.moveY(-1);
		if(MyInput.isPressed(MyInput.SPACE))
			tetris.rotate();
		if(tap!=null)
		if(pauseBounds.contains(tap.x,tap.y))
			gsm.push(new MenuPause(gsm));			
		else
			tetris.rotate();
	}

	public void update(float dt) {
		handleInput();
		countdown-=dt;
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
		
		//текущая фигура
		sb.setColor(colors[tetris.type]);
		sb.draw(cell,tetris.X*size, tetris.Y*size,size,size);
		for(int i =0;i<3;i++)
			sb.draw(cell,(tetris.X*size+tetris.StatesX.get(tetris.type)[tetris.rotateState][i]*size), (tetris.Y*size+tetris.StatesY.get(tetris.type)[tetris.rotateState][i]*size),size,size);

		//следующая фигура
		sb.setColor(colors[tetris.typeNext]);
		int sd=2;
		sb.draw(cell,size, scH-size,size/sd,size/sd);
		for(int i=0;i<3;i++)
			sb.draw(cell,size +tetris.StatesX.get(tetris.typeNext)[0][i]*(size/sd), scH-size +tetris.StatesY.get(tetris.typeNext)[0][i]*(size/sd),size/sd,size/sd);
		//кнопа паузы
		sb.setColor(1,1,1,0.1f);
		sb.draw(texPause,pauseBounds.x, scH-pauseBounds.y-pauseBounds.height, pauseBounds.width, pauseBounds.height);
		sb.setColor(1,1,1,1f);
		
		if(countdown>0)
			return;
		tetris.moveY(-1);
		if(tetris.lines<=50)
			countdown = (float)(0.1*(11-(int)(tetris.lines/5)));
		else
			countdown=0.1f;
		
	}

}
