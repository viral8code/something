import java.io.*;
import java.util.*;
class subMain{
	public long[][] stack = new long[5][1000000];
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
	public long DCmini(int a, int n){
		if(a>5)
			a = 10 - a;
		if(a==0)
			return 0;
		if(n==2){
			if(a==1)
				return 2;
			else
				return 3;
		}
		if(n<=1000000&&stack[a-1][n-1]!=0)
			return stack[a-1][n-1];
		long log = DCmini(a-1,n-1) + DCmini(a,n-1) + DCmini(a+1,n-1);
		log %= 998244353;
		if(n<=1000000)
			stack[a-1][n-1] = log;
		return log;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		subMain sm = new subMain();
		int N = Integer.parseInt(br.readLine());
		if(N==1){
			System.out.println(9);
			System.exit(0);
		}
		long answer = 0;
		for(int i=1;i<5;i++){
			answer += sm.DCmini(i,N) * 2;
			answer %= 998244353;
		}
		answer += sm.DCmini(5,N);
		answer %= 998244353;
		System.out.println(answer);
	}
}
			