import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N];
		for(int i=0;i<N;i++){
			A[i] = sc.nextInt();
		}
		boolean[] ans = new boolean[N];
		for(int i=1;i<N;i++){
			if(A[i-1]>A[i]){
				ans[i-1] = !ans[i-1];
				ans[i] = !ans[i];
			}
		}
		for(int i=0;i<N;i++){
			if(ans[i])
				System.out.print(1+" ");
			else
				System.out.print(0+" ");
		}
	}
}