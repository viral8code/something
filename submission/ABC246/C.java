import java.io.*;
import java.util.*;
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStrWithS(String someStr){
		String[] str = someStr.split(" ");
		return str;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] NKX = subMain.parIntWithS(br.readLine());
		int[] A = subMain.parIntWithS(br.readLine());
		long K = 0;
		long sum = 0;
		long answer = 0;
		for(int i=0;i<A.length;i++){
			sum += A[i];
			K += A[i] / NKX[2];
			answer += A[i] %= NKX[2];
		}
		if(K>=NKX[1]){
			System.out.println(sum-(long)NKX[1]*NKX[2]);
			System.exit(0);
		}
		if(NKX[1]-K>=A.length){
			System.out.println(0);
			System.exit(0);
		}
		Arrays.sort(A);
		for(int i=0;i<NKX[1]-K;i++){
			answer -= A[A.length-1-i];
		}
		System.out.println(answer);
	}
}
