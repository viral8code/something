import java.io.*;
import java.util.*;
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStrWithS(String someStr){
		String[] str = someStr.split(" ");
		return str;
	}
}
class Map{
	int[] S;
	int[] T;
	int[][] H;
	int[][] V;
	int[][] maze;
	int temp = 201;
	String answer = "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL";
	public Map(int[] s,int[] t,int[][] h,int[][] v){
		S = s;
		T = t;
		H = h;
		V = v;
		maze = new int[43][43];
		for(int i=0;i<43;i++){
			maze[0][i] = 9;
			maze[42][i] = 9;
			maze[i][0] = 9;
			maze[i][42] = 9;
			maze[1][i] = 9;
			maze[41][i] = 9;
			maze[i][1] = 9;
			maze[i][41] = 9;
		}
		for(int i=0;i<20;i++){
			for(int j=0;j<19;j++){
				if(H[i][j]==1)
					maze[2*i+3][2*(j+2)] = 9;
			}
		}
		for(int i=0;i<19;i++){
			for(int j=0;j<20;j++){
				if(V[i][j]==1)
					maze[2*(i+2)][2*j+3] = 9;
			}
		}
		maze[T[0]*2+1][T[1]*2+1] = 1;
	}
	public void execute(int x,int y,int d,String debug){
		if(maze[y][x]==1){
			if(d<answer.length()){
				temp = d;
				answer = debug;
			}
			return;
		}
		if(temp<=d)
			return;
		//System.out.println("("+(x-1)/2+","+(y-1)/2+")試行");
		maze[y][x] = 2;

		if(maze[y-1][x]<2&&maze[y-2][x]<2){
			debug = debug + "U";
			execute(x,y-2,d+1,debug);
		}
		if(maze[y+1][x]<2&&maze[y+2][x]<2){
			debug = debug + "D";
			execute(x,y+2,d+1,debug);
		}
		if(maze[y][x-1]<2&&maze[y][x-2]<2){
			debug = debug + "L";
			execute(x-2,y,d+1,debug);
		}
		if(maze[y][x+1]<2&&maze[y][x+2]<2){
			debug = debug + "R";
			execute(x+2,y,d+1,debug);
		}
		maze[y][x] = 0;
	}
}
		
class Main{
	public static void main(String[] args)throws IOException{
		Scanner sc = new Scanner(System.in);
		int[] S = new int[2];
		int[] T = new int[2];
		S[0] = sc.nextInt();
		S[1] = sc.nextInt();
		T[0] = sc.nextInt();
		T[1] = sc.nextInt();
		double p = sc.nextDouble();
		String str = sc.nextLine();
		int[][] H = new int[20][19];
		int[][] V = new int[19][20];
		for(int i=0;i<20;i++){
			H[i] = subMain.parInt(sc.nextLine());
		}
		for(int i=0;i<19;i++){
			V[i] = subMain.parInt(sc.nextLine());
		}
		Map map = new Map(S,T,H,V);
		map.execute(2*S[0]+1,2*S[1]+1,0,"");
		String ans = map.answer;
		if(p<=0.5){
			if(ans.length()<=200){
				while(ans.length()<199){
					ans = ans + "RD";
				}
				if(ans.length()==199)
					ans = ans + "L";
				System.out.println(ans);
				System.exit(0);
			}
		}
		System.out.println(ans);
		System.out.println("RDRDRDRDRDRDRDRDRDRDRDRDRDRDRDRRDRDRDRDRDRDRDRRDRDRDRDRDRDRDRRDRDRDDRDLLLDDDRDRDRDRURRRDRDRDRDRDRDRDLLLLDDDRDRDRDRDRDRURRURDRDRDURRRRRRRLLLLLLLLLLLULLLRRRRRRRRRRDRDRDRDRDRDRDRDRDRDRDRDRDRDRDRDURDLURDL");
	}
}
