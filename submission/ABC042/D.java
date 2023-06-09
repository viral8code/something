import java.util.*;
class Main{
	static final long mod = 1000000007;
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		long H = sc.nextLong();
		long W = sc.nextLong();
		long A = sc.nextLong();
		long B = sc.nextLong();
		long[] debug = new long[(int)(H-A)];
		subMain sm = new subMain(H+W,mod);
		for(int i=0;i<debug.length;i++){
			debug[i] = sm.choice(i+B-1,i,mod);
		}
		for(long i=H-2,j=0;j<debug.length;i--,j++){
			debug[(int)j] *= sm.choice(i+W-B,W-B-1,mod);
		}
		long answer = 0;
		for(int i=0;i<debug.length;i++){
			answer += debug[i];
			answer %= mod;
		}
		System.out.println(answer);
	}
}
class subMain{
	long[][] memo;
	public subMain(long HW,long mod){
		memo = new long[(int)HW-1][2];
		memo[0][0] = memo[0][1] = memo[1][0] = memo[1][1] = 1;
		for(int i=2;i<memo.length;i++){
			memo[i][0] = memo[i-1][0]*i;
			memo[i][0] %= mod;
		}
		memo[(int)HW-2][1] = extendEuclid(memo[(int)HW-2][0],mod);
		for(int i=memo.length-2;i>=2;i--){
			memo[i][1] = memo[i+1][1]*(i+1);
			memo[i][1] %= mod;
		}
//		System.out.println("---以下デバッグ用-----------------------");
//		for(int i=0;i<memo.length;i++){
//			System.out.print(i+"!\t");
//			System.out.print(memo[i][0]+"\t");
//			System.out.println(memo[i][1]);
//		}
//		System.out.println("----------------------------------------");
	}
	public long choice(long a,long b,long mod){
		long ans = memo[(int)a][0]*memo[(int)b][1] % mod;
		ans *= memo[(int)(a-b)][1];
		ans %= mod;
		return ans;
	}
	public long extendEuclid(long HW,long mod){
		long temp1 = 1;
		long temp2 = 1;
		while(true){
			long ax = HW*temp1;
			long qm = mod*temp2;
			if(ax-qm==1)
				return temp1;
			else if(ax>qm)
				temp2++;
			else if(ax<=qm)
				temp1++;
		}
	}
}