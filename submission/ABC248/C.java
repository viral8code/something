import java.io.*;
import java.util.*;
class subMain{
	static long mod = 998244353;
	int max;
	long[][] memo;
	public subMain(int n,int k,int ma){
		max = ma;
		memo = new long[n+1][k+1];
	}
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
	public static int[] readInts(int num){
		Scanner sc = new Scanner(System.in);
		int[] ans = new int[num];
		for(int i=0;i<num;i++){
			ans[i] = sc.nextInt();
		}
		return ans;
	}
	public long process(int nokori,int limit){
		if(nokori>limit)
			return 0;
		if(memo[nokori][limit]!=0)
			return memo[nokori][limit];
		long count = 0;
		if(nokori==1)
			count =  Math.min(max,limit);
		else{
			for(int i=1;i<=Math.min(max,limit-nokori+1);i++){
				count += process(nokori-1,limit-i);
				count %= mod;
			}
		}
		memo[nokori][limit] = count;
		return count;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		long mod = 998244353;
		int N = sc.nextInt();
		int M = sc.nextInt();
		int K = sc.nextInt();
		M = Math.min(M,K-N+1);
		subMain sm = new subMain(N,K,M);
		long ans = sm.process(N,K);
		System.out.println(ans);
	}
}
 