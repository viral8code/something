import java.io.*;
import java.util.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] tail = new int[30][30];
		for(int i=0;i<30;i++){
			tail[i] = subMain.parInt(br.readLine());
		}
		String answer = "000000000000000000000000000000";
		for(int i=1;i<tail.length-1;i++){
			answer = answer + "0";
			for(int j=1;j<tail[i].length-1;j++){
				if(tail[i][j]==0&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7)&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6)&&
					!((tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7))
				)
					answer = answer + "1";
				else if(tail[i][j]==1&&
					!((tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7))&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
				)
					answer = answer + "1";
				else if(tail[i][j]==2&&
					(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					!((tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7)&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6))&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
				)
					answer = answer + "1";
				else if(tail[i][j]==3&&
					(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7)&&
					!((tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7))
				)
					answer = answer + "1";
				else if(tail[i][j]==6&&
					!((tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6))&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
				)
					answer = answer + "1";
				else if(tail[i][j]==7&&
					(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7)&&
					!((tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7))
				)
					answer = answer + "1";
				else
					answer = answer + "0";
			}
			answer = answer + "0";
		}
		answer = answer + "000000000000000000000000000000";
		System.out.println(answer);
	}
}
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
}
