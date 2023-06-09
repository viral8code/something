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
		int N = Integer.parseInt(br.readLine());
		if(N<=3){
			if(N==1)
				System.out.println(9);
			else if(N==2)
				System.out.println(25);
			else
				System.out.println(71);
			System.exit(0);
		}
		long answer = 71;
		int[] stack = {0,2,2,1,0,0,0,1,2,2,0};
		for(int l=4;l<=N;l++){
			int[] stack2 = new int[9];
			for(int j=1,jj=0;j<9;j++,jj++){
				stack2[jj] += stack[j-1] + stack[j+1];
			}
			long s = 0;
			for(int j=0;j<9;j++){
				s += stack2[j];
				stack[j+1] = stack2[j];
			}
			answer = s + (( answer - (s/2)) * 3);
		}
		answer %= 998244353;
		System.out.println(answer);
	}
}
