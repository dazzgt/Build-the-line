package org.gamerex.btl2;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

	public static final AssetManager manager = new AssetManager();
	public static final String cloud = "cloud.png";
	public static final String pixel = "pixel.png";
	public static final String cell = "cell.png";
	//public static TextureRegion cloud;
	//public static TextureRegion pixel;
	//public static TextureRegion cell;
	//public static Color[] colors;
	
	

	public static void load(){
		manager.load(cloud, Texture.class);
		manager.load(pixel, Texture.class);
		manager.load(cell, Texture.class);
		manager.update();
		//cloud = new TextureRegion(new Texture(Gdx.files.internal("cloud.png")));
		//pixel = new TextureRegion(new Texture(Gdx.files.internal("pixel.png")));
		//cell = new TextureRegion(new Texture(Gdx.files.internal("cell.png")));
		//colors = new Color[]{Color.RED,new Color(1, 0.6f, 0, 1),Color.YELLOW,Color.GREEN,Color.CYAN,new Color(0, 0.3f, 0.9f, 1),new Color(0.588f, 0, 0.8f, 1)};
		
	}
}
