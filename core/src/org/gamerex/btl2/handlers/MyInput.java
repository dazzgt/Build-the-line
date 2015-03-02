package org.gamerex.btl2.handlers;

import com.badlogic.gdx.math.Vector2;

public class MyInput {
	
	public static boolean[] pkeys;
	public static boolean[] keys;
	public static Vector2 tap;
	private static boolean isTap;
	
	public static final int NUM_KEYS = 5;
	public static final int SPACE = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int BACK = 4;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
		tap = new Vector2(0, 0);
		isTap = false;
	}
	
	public static void update(){
		for(int i = 0;i<NUM_KEYS;i++)
			pkeys[i]=keys[i];
		isTap = false;
	}
	
	
	public static void setKey(int i, boolean b){ keys[i]=b;}
	public static void setTap(float x,float y){ tap.set(x, y); isTap = true;}
	public static boolean isDown(int i){return keys[i];}
	public static boolean isPressed(int i) { return keys[i] && !pkeys[i];}
	public static Vector2 getTap(){
		if(isTap)
			return tap;
		return null;
	}
}
