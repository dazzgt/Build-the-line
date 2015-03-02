package org.gamerex.btl2;

import org.gamerex.btl2.handlers.MyGestureAdapter;
import org.gamerex.btl2.handlers.MyInputAdapter;
import org.gamerex.btl2.states.ActionResolver;
import org.gamerex.btl2.states.GSM;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

public class BTL2 extends ApplicationAdapter{
	public static final int WIDTH = 480/2;
	public static final int HEIGHT = 800/2;
	public static final String TITLE = "Build The Line 2";


	private GSM gsm;
	private SpriteBatch sb;
	private OrthographicCamera cam;	
	ActionResolver actionResolver;
	InputMultiplexer im;

	public BTL2(ActionResolver actionResolver) {
		this.actionResolver=actionResolver;
	}

	public void create() {
		Gdx.gl.glClearColor(0f, 0.75f, 1f, 1);
		gsm = new GSM(actionResolver);
		//gsm.push(new MainMenu(gsm));
		sb = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(),	Gdx.graphics.getHeight());
		im = new InputMultiplexer();
		im.addProcessor(new MyInputAdapter());
		im.addProcessor(new GestureDetector(new MyGestureAdapter()));
		Gdx.input.setInputProcessor(im);
		Gdx.input.setCatchBackKey(true);
	}

	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(cam.combined);

		sb.begin();
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
		sb.end();	
	}
}
