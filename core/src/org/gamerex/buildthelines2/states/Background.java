package org.gamerex.buildthelines2.states;

import org.gamerex.buildthelines2.SpriteAccessor;
import org.gamerex.buildthelines2.ui.Cloud;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class Background extends State{

	private Cloud[] clouds;
	private int count=7;
	private TweenManager tweenManager = new TweenManager();
	private TextureAtlas atlas;
	private Sprite grassSprite1;
	private Sprite grassSprite2;
	private Sprite grassSprite3;

	public Background(GSM gsm) {
		super(gsm);

		clouds = new Cloud[count];
		for (int i =0;i<count;i++){
			clouds[i] = new Cloud(gsm);
		}
		
		atlas = new TextureAtlas("data/grass.atlas");
		
		grassSprite1 = atlas.createSprite("grass1");
		grassSprite1.setSize(gsm.scW+gsm.scW/10, gsm.scW * grassSprite1.getHeight() / grassSprite1.getWidth());
		grassSprite1.setPosition(-gsm.scW/20, 0);
		
		grassSprite2 = atlas.createSprite("grass2");
		grassSprite2.setSize(gsm.scW+gsm.scW/10, (float) (1.2*gsm.scW * grassSprite2.getHeight() / grassSprite2.getWidth()));
		grassSprite2.setPosition(-gsm.scW/20, 0);
		
		grassSprite3 = atlas.createSprite("grass3");
		grassSprite3.setSize(gsm.scW+gsm.scW/10, (float) (1.4*gsm.scW * grassSprite3.getHeight() / grassSprite3.getWidth()));
		grassSprite3.setPosition(-gsm.scW/20, 0);
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.call(windCallback).start(tweenManager);
	}

	public void handleInput() {}

	public void update(float dt) {
		for (Cloud a : clouds) {
			a.update(dt);
		}
		tweenManager.update(dt);
	}

	public void render(SpriteBatch sb) {
		for (Cloud a : clouds) {
			a.render(sb);
		}
		grassSprite3.draw(sb);
		grassSprite2.draw(sb);
		grassSprite1.draw(sb);
	}
	private final TweenCallback windCallback = new TweenCallback() {

		public void onEvent(int type, BaseTween<?> source) {
			float d = MathUtils.random() * 0.5f + 0.5f;
			float t = 0.5f * grassSprite1.getHeight();
			
			Timeline.createParallel()
				.push(Tween.to(grassSprite1, SpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).repeatYoyo(1, 0).setCallback(windCallback))
				.push(Tween.to(grassSprite2, SpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).delay(d/3).repeatYoyo(1, 0))
				.push(Tween.to(grassSprite3, SpriteAccessor.SKEW_X2X3, d).target(t, t).ease(Sine.INOUT).delay(d/3*2).repeatYoyo(1, 0))
				.start(tweenManager);
		}
	};


}
