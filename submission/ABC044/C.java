import java.io.*;
class subMain{
	long memo[][][];
	boolean debugMemo[][][];
	int[] x;
	int max;
	public subMain(int N,int[] xx){
		x = new int[xx.length+1];
		int temp = 0;
		for(int i=1;i<x.length;i++){
			x[i] = xx[i-1];
			if(temp<xx[i-1])
				temp = xx[i-1];
		}
		max = temp;
		memo = new long[N+1][N+1][N*max+1];
		debugMemo = new boolean[N+1][N+1][N*max+1];
		memo[0][0][0] = 1;
		debugMemo[0][0][0] = true;
	}
	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public long dp(int j,int k,int s){
		long ans = 0;
		if(j==0&&k==0&&s==0)
			ans = 1;
		else if(debugMemo[j][k][s])
			return memo[j][k][s];
		else if(j>0&&s<x[j])
			ans = dp(j-1,k,s);
		else if(j>0&&k>0&&s>=x[j])
			ans = dp(j-1,k,s)+dp(j-1,k-1,s-x[j]);
		memo[j][k][s] = ans;
		debugMemo[j][k][s] = true;
		return ans;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] NA = subMain.parIntWithS(br.readLine());
		int[] x = subMain.parIntWithS(br.readLine());
		subMain sm = new subMain(NA[0],x);
		long answer = 0;
		for(int i=1;i<=NA[0];i++){
			if(i*NA[1]>sm.max*NA[0]+1)
				break;
			answer += sm.dp(NA[0],i,i*NA[1]);
		}
		System.out.println(answer);
	}
}
