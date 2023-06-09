import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<N+1;i++){
			ArrayList<Integer> Asub = new ArrayList<Integer>();
			A.add(Asub);
		}
		for(int i=0;i<N;i++){
			int temp = sc.nextInt();
			A.get(temp).add(i+1);
		}
		int Q = sc.nextInt();
		for(int i=0;i<Q;i++){
			int L = sc.nextInt();
			int R = sc.nextInt();
			int X = sc.nextInt();
			int fir = Collections.binarySearch(A.get(X),L,(a,b) -> a>=b ? 1 : -1);
			int fin = Collections.binarySearch(A.get(X),R+1,(a,b) -> a>=b ? 1 : -1);
			System.out.println(fir-fin);
		}
	}
}