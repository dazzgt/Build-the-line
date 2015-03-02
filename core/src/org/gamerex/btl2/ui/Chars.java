package org.gamerex.btl2.ui;

import java.util.HashMap;
import java.util.Map;


public class Chars {
		public static final int[][] latA = new int[][]{{0,0,0,0,0,0,1,2,3,4,4,4,4,4,4,1,2,3},{0,1,2,3,4,5,6,6,6,5,4,3,2,1,0,2,2,2}};
		public static final int[][] latB = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latC = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latD = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//	
		public static final int[][] latE = new int[][]{{0,0,0,0,0,0,0,1,2,3,4,1,2,3,4,1,2,3,4},{0,1,2,3,4,5,6,0,0,0,0,3,3,3,3,6,6,6,6}};//
		public static final int[][] latF = new int[][]{{0,0,0,0,0,0,0,1,2,3,4,1,2,3,4},{0,1,2,3,4,5,6,6,6,6,6,3,3,3,3}};
		public static final int[][] latG = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latH = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latI = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latJ = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latK = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//	
		public static final int[][] latL = new int[][]{{4,3,2,1,0,0,0,0,0,0,0},{0,0,0,0,0,1,2,3,4,5,6}};
		public static final int[][] latM = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latN = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latO = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latP = new int[][]{{0,0,0,0,0,0,0,1,2,3,1,2,3,4,4},{0,1,2,3,4,5,6,6,6,6,3,3,3,4,5}};
		public static final int[][] latQ = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latR = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latS = new int[][]{{0,1,2,3,4,4,3,2,1,0,0,1,2,3,4},{1,0,0,0,1,2,3,3,3,4,5,6,6,6,5}};
		public static final int[][] latT = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latU = new int[][]{{0,0,0,0,0,0,1,2,3,4,4,4,4,4,4},{1,2,3,4,5,6,0,0,0,1,2,3,4,5,6}};
		public static final int[][] latV = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latW = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latX = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static final int[][] latY = new int[][]{{0,0,1,2,3,4,4,2,2,2},{6,5,4,3,4,5,6,2,1,0}};//
		public static final int[][] latZ = new int[][]{{1,2,3,4,5,6},{1,2,3,4,5,6}};//
		public static Map<Character, int[][]> chars =new HashMap<Character, int[][]>();
		
		public static MyChar getChar(Character ch){
			MyChar mc = new MyChar();
			try{
				mc.arr = chars.get(ch);
			}
			catch(Exception e){return null;}
			
			return mc;
		}
	
		static{
			chars.put('A',latA);
			chars.put('B',latB);
			chars.put('C',latC);
			chars.put('D',latD);
			chars.put('E',latE);
			chars.put('F',latF);
			chars.put('G',latG);
			chars.put('H',latH);
			chars.put('I',latI);
			chars.put('J',latJ);
			chars.put('K',latK);
			chars.put('L',latL);
			chars.put('M',latM);
			chars.put('N',latN);
			chars.put('O',latO);
			chars.put('P',latP);
			chars.put('Q',latQ);
			chars.put('R',latR);
			chars.put('S',latS);
			chars.put('T',latT);
			chars.put('U',latU);
			chars.put('V',latV);
			chars.put('W',latW);
			chars.put('X',latX);
			chars.put('Y',latY);
			chars.put('Z',latZ);
			//chars.put('',);
		}
}
