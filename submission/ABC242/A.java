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
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] ABCX = subMain.parIntWithS(br.readLine());
		if(ABCX[0]>=ABCX[3]){
			System.out.println(1.0);
			System.exit(0);
		}
		else if(ABCX[1]<ABCX[3]){
			System.out.println(0.0);
			System.exit(0);
		}
		int BA = ABCX[1] - ABCX[0];
		double answer = (double)ABCX[2] / (double)BA;
		System.out.println(answer);
	}
}