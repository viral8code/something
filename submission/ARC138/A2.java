import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int[] A = new int[N];
		int min = 1000000001;
		int minmin = 0;
		for(int i=0;i<N;i++){
			A[i] = sc.nextInt();
			if(i<K&&min>A[i]){
				min = A[i];
				minmin = i;
			}
		}
		int pick = N-K+1;
		int ans = N+1;
		for(int i=K-1;i>=minmin;i--){
			Check:for(int j=K;j<(pick+i);j++){
				if(A[i]<A[j]&&ans>j-i){
					pick = j;
					ans = j-i;
					break Check;
				}
			}
		}
		if(ans==N+1)
			ans = -1;
		System.out.println(ans);
	}
}