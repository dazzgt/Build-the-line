package org.gamerex.btl2.ui;

import com.badlogic.gdx.graphics.Color;

public class MyString {

	public float x,y,size,length;
	private MyChar[] arr;
	public MyString(Character[] s,Color[] c,int[] j){
		arr = new MyChar[s.length];
		for (int i=0; i<s.length;i++) {
			arr[i] = Chars.getChar(s[i]);
			if(arr[i]!=null){
				arr[i].color = c[j[i]];
				length+=6;
			}
			else
				length+=2;
		}
		length -=1;
	}
	public MyChar[] getArr(){
		return arr;
	}
}
