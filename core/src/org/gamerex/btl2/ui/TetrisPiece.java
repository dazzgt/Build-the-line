package org.gamerex.btl2.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.gamerex.btl2.states.GSM;

public class TetrisPiece{

	//public enum Type{ O, I, S, Z, L, J, T };
	public int type;
	public int typeNext;
	public int rotateState = 0;
	public int[] mX;
	public int[] mY;
	public int X;
	public int Y;
	public boolean go=false;
	public static boolean blocked = false;

	private int[] statesCount = {1,2,2,2,4,4,4};
	private int cut=0;
	public int lines=5;
	public Map<Integer,int[][]> StatesX;
	public Map<Integer,int[][]> StatesY;
	public int[][] board;
	public int brdW = 12;
	public int brdH = 18;

	//public Integer Score=0;

	//Sound soundDown;
	//Sound soundLine;
	GSM gsm;
	public TetrisPiece(){
		board = new int[brdW][brdH+5]; 

		StatesX = new HashMap<Integer, int[][]>();
		StatesX.put(0, new int[][]{{-1,0,-1}});//O
		StatesX.put(1, new int[][]{{0,0,0},{1,-1,-2}});//I
		StatesX.put(2, new int[][]{{-1,0,1},{1,1,0}});//S
		StatesX.put(3, new int[][]{{-1,0,1},{0,1,1}});//Z
		StatesX.put(4, new int[][]{{-1,-1,1},{0,0,1},{-1,1,1},{-1,0,0}});//L
		StatesX.put(5, new int[][]{{-1,1,1},{0,0,1},{-1,-1,1},{-1,0,0}});//J
		StatesX.put(6, new int[][]{{-1,0,1},{0,0,1},{-1,0,1},{-1,0,0}});//T

		StatesY = new HashMap<Integer, int[][]>();
		StatesY.put(0, new int[][]{{0,-1,-1}});//O
		StatesY.put(1, new int[][]{{1,-1,-2},{0,0,0}});//I
		StatesY.put(2, new int[][]{{-1,-1,0},{-1,0,1}});//S
		StatesY.put(3, new int[][]{{0,-1,-1},{-1,0,1}});//Z
		StatesY.put(4, new int[][]{{0,-1,0},{1,-1,-1},{0,0,1},{1,1,-1}});//L
		StatesY.put(5, new int[][]{{0,0,-1},{1,-1,1},{1,0,0},{-1,1,-1}});//J
		StatesY.put(6, new int[][]{{0,-1,0},{1,-1,0},{0,1,0},{0,1,-1}});//T	

		typeNext = rand.nextInt(7);
		//soundDown = Gdx.audio.newSound(Gdx.files.internal("down.ogg"));
		//soundLine = Gdx.audio.newSound(Gdx.files.internal("slize.mp3"));

		gsm = GSM.instance;

	}
	public void NewGame()
	{
		go = false;
		board = new int[brdW][brdH+7]; 
		gsm.Score = 0;
		gsm.Speed = 1;
		lines = 5;
		type = rand.nextInt(7);
		typeNext = rand.nextInt(7);
		mX = StatesX.get(type)[0];
		mY = StatesY.get(type)[0];
	}
	static Random rand = new Random();
	public void beginPath()
	{
		X=brdW/2;Y=brdH+2;
		rotateState=0;
		type = typeNext;
		typeNext = rand.nextInt(7);
		mX = StatesX.get(type)[0];
		mY = StatesY.get(type)[0];
	}
	public void rotate()
	{
		int prev = rotateState;
		if(rotateState+1<statesCount[type])
			rotateState++;
		else
			rotateState=0;
		mX = StatesX.get(type)[rotateState];
		mY = StatesY.get(type)[rotateState];
		for(int i=2;i>=0;i--)
		{
			if(X+mX[i]<0)
			{
				if(X+mX[i]<-1)
				{
					if(!move(2))
						rotateState=prev;
				}
				else
				{
					if(!move(1))
						rotateState=prev;
				}
			}
			if(X+mX[i]>=brdW)
			{
				if(X+mX[i]>brdW)
				{
					if(!move(-2))
						rotateState=prev;
				}
				else
				{
					if(!move(-1))
						rotateState=prev;
				}
			}
			if(board[X+mX[i]][Y+mY[i]]!=0)
				rotateState = prev;
		}
		mX = StatesX.get(type)[rotateState];
		mY = StatesY.get(type)[rotateState];
	}
	public boolean move(int step)
	{
		boolean can = true;
		for(int i =0;i<3;i++)
		{
			if((X+mX[i]+step)>=0 && (X+mX[i]+step)<brdW && X+step>=0 && X+step<brdW)
			{
				if(board[X+mX[i]+step][Y+mY[i]]!=0||board[X+step][Y]!=0)
					can = false;
			}
			else
				can = false;
		}
		if(can)
			X += step;
		return can;
	}
	public void moveY(int step)
	{
		boolean can = true;
		for(int i =0;i<3;i++)
			if((Y+mY[i]+step)>=0 && (Y+step)>=0)
			{
				if(board[X+mX[i]][Y+mY[i]+step]!=0||board[X][Y+step]!=0)
					can = false;
			}
			else 
				can = false;

		if(can)
			Y+=step;
		else
		{
			blocked = true;
			board[X][Y]=type+1;
			for(int i =0;i<3;i++)
				board[X+mX[i]][Y+mY[i]]=type+1;

			cut=checkBoard();
			lines+=cut;
			if(cut>0)
			{
				for(;cut>0;cut--)
					gsm.Score+=(int)((10+5*(cut-1))+(10+5*(cut-1))*(0.1*gsm.Speed-0.1));
				//soundLine.play();
			}
			else
				;//soundDown.play();
			if(lines>=100)
				gsm.Speed =20;
			else
				gsm.Speed = lines/5;
			for(int i = 0;i<brdW;i++)
				if(board[i][brdH]!=0)
					go = true;
			if(!go)
				beginPath();
		}
	}

	public int checkBoard()
	{
		boolean full;
		int count=0;
		for(int y=0;y<brdH;y++)
		{
			full=true;
			for(int x=0;x<brdW;x++)
				if(board[x][y]==0)
					full=false;
			if(full){
				for(int i =y;i<brdH;i++)
					for(int x=0;x<brdW;x++)
						board[x][i]=board[x][i+1];
				y--;
				count++;
			}
		}
		return count;
	}
}


