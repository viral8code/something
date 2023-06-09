import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long X = sc.nextLong();
		long[][] Bag = new long[N][100000/(int)Math.pow(2,N-1)];
		for(int i=0;i<N;i++){
			int temp = sc.nextInt();
			for(int j=0;j<temp;j++){
				Bag[i][j] = sc.nextLong();
			}
		}
		subMain sm = new subMain(X,Bag);
		long answer = sm.process(1,-1);
		System.out.println(answer);
	}
}
class subMain{
	long x;
	long[][] bag;
	public subMain(long X,long[][] Bag){
		x = X;
		bag = Bag;
	}
	public long process(long num,int pick){
		long ans = 0;
		pick++;
		Loop:for(int i=0;i<bag[pick].length;i++){
			if(bag[pick][i]==0)
				break Loop;
			long temp = num * bag[pick][i];
			if(temp<=0)
				continue;
			if((x%temp)==0){
				if(pick<(bag.length-1))
					ans += process(temp,pick);
				else{
					if(temp==x)
						ans++;
				}
			}
		}
		return ans;
	}
}
