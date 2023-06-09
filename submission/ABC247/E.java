import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int X = sc.nextInt();
		int Y = sc.nextInt();
		int[][] A = new int[N][2];
		int maxx = 0;
		int minn = 200001;
		int pick = 1;
		A[0][0] = sc.nextInt();
		A[0][1] = 1;
		for(int i=1;i<N;i++){
			int temp = sc.nextInt();
			if(A[pick-1][0]==temp){
				A[pick-1][1]++;
			}
			else{
				A[pick][0] = temp;
				A[pick][1] = 1;
				pick++;
			}
			if(maxx<temp)
				maxx = temp;
			if(minn>temp)
				minn = temp;
		}
		long ans = 0;
		for(int i=0;i<pick;i++){
			int max = minn;
			int min = maxx;
			if(A[i][0]==0)
				break;
			Check:for(int j=i;j<pick;j++){
				if(max<A[j][0])
					max = A[j][0];
				if(min>A[j][0])
					min = A[j][0];
				if(max>X||min<Y)
					break Check;
				if(max==X&&min==Y)
					ans += A[j][1];
			}
		}
		System.out.println(ans);
	}
}