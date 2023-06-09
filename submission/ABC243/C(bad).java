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
	public static boolean sorting(int X1,int X2,int pick1,int pick2,String[] LR){
		if(X1<X2&&(LR[pick1].equals("R")&&LR[pick2].equals("L")))
			return true;
		else if(X1>X2&&(LR[pick1].equals("L")&&LR[pick2].equals("R")))
			return true;
		return false;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] XY = new int[N][2];
		for(int i=0;i<N;i++){
			XY[i] = subMain.parIntWithS(br.readLine());
		}
		String[] S = subMain.parStr(br.readLine());
		for(int i=0;i<N;i++){
			int[] check = XY[i];
			for(int j=i+1;j<N;j++){
				if(check[1]==XY[j][1]&&Math.abs(check[0]-XY[j][0])<=2){
					if(subMain.sorting(check[0],XY[j][0],i,j,S)){
						System.out.println("Yes");
						System.exit(0);
					}
				}
			}
		}
		System.out.println("No");
	}
}
