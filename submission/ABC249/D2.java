import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int max = 0;
		int[] A = new int[200001];
		Arrays.fill(A, 0);
		for(int i=0;i<N;i++){
			int temp = sc.nextInt();
			A[temp]++;
			if(max<temp)
				max = temp;
		}
		long answer = 0;
		for(int i=1;i<=max;i++){
			for(int j=1;j<=(max/i);j++){
				answer += (long)A[i]*A[j]*A[i*j];
			}
		}
		System.out.println(answer);
	}
}
