import java.io.*;
import java.util.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N];
		for(int i=0;i<N;i++){
			A[i] = sc.nextInt();
		}
		int count = 0;
		Arrays.sort(A);
		if(A[0]*A[0]>A[A.length-1]){
			System.out.println(0);
			System.exit(0);
		}
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(A[i]%A[j]!=0)
					continue;
				int temp = A[i]/A[j];
				for(int k=0;k<N;k++){
					if(temp<A[k])
						break;
					if(temp==A[k])
						count++;
				}
			}
		}
		System.out.println(count);
	}
}
