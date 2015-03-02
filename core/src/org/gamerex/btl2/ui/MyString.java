package org.gamerex.btl2.ui;

import com.badlogic.gdx.graphics.Color;

public class MyString {

	public int X,Y;
	private MyChar[] arr;
	public MyString(Character[] s,Color[] c,int[] j){
		arr = new MyChar[s.length];
		for (int i=0; i<s.length;i++) {
			arr[i] = Chars.getChar(s[i]);
			arr[i].color = c[j[i]];
		}
	}
	public MyChar[] getArr(){
		return arr;
	}
}
