import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int[] A = new int[N];
		int min = 1000000001;
		int pick = -1;
		for(int i=0;i<N;i++){
			A[i] = sc.nextInt();
			if(min>A[i]&&i<K){
				min = A[i];
				pick = i;
			}
		}
		int ans = -1;
		for(int i=K;i<N;i++){
			if(A[i]>min){
				ans = i-pick;
				break;
			}
		}
		if(ans==-1){
			System.out.println(-1);
			System.exit(0);
		}
		for(int i=ans;i>ans-K+pick+1;i--){
			if(i<K)
				break;
			Check:for(int j=K-1;j>pick;j--){
				if(A[j]<A[i]&&ans>i-j){
					ans = i-j;
					break Check;
				}
			}
		}
		System.out.println(ans);
	}
}