import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int X = sc.nextInt();
		int Y = sc.nextInt();
		int[] A = new int[N];
		int maxx = 0;
		int minn = 200001;
		for(int i=0;i<N;i++){
			A[i] = sc.nextInt();
			if(maxx<A[i])
				maxx = A[i];
			if(minn>A[i])
				minn = A[i];
		}
		long ans = 0;
		for(int i=0;i<N;i++){
			int max = minn-1;
			int min = maxx+1;
			int mi = i;
			int ma = 0;
			boolean findmax = false,findmin = false,find = true;
			Check:for(int j=i;j<N;j++){
				if(max>X||min<Y){
					if(findmax&&findmin){
						ans += (Math.min(mi,ma)-i+1)*(j-Math.max(mi,ma));
					}
					i = j-1;
					find = false;
					break Check;
				}
				if(max<A[j]){
					max = A[j];
					if(max==X){
						ma = j;
						findmax = true;
					}
				}
				if(min>A[j]){
					min = A[j];
					if(min==Y){
						mi = j;
						findmin = true;
					}
				}
			}
			if(find&&findmax&&findmin)
				ans += N-Math.max(mi,ma);
		}
		System.out.println(ans);
	}
}
