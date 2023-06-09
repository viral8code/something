import java.io.*;
import java.util.*;
import java.math.*;
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
		int[] NM = subMain.parIntWithS(br.readLine());
		int[] A = subMain.parIntWithS(br.readLine());
		int[] C = subMain.parIntWithS(br.readLine());
		int lengthOfB = C.length-A.length + 1;
		int[] answer = new int[lengthOfB];
		for(int i=0;i<lengthOfB;i++){
			if(C[C.length-1-i]==0){
				answer[i] = 0;
				continue;
			}
			int temp = C[C.length-1-i] / A[A.length-1];
//			if(C[C.length-1-i]%A[A.length-1]!=0){
//				temp = gcd(C[C.length-1-i],A[A.length-1]);
//				int num = temp / C[C.length-1-i];
//				for(int j=0;j<C.length;j++){
//					C[i] *= num;
//				}
//				num = temp / A[A.length-1];
//				for(int j=0;j<A.length;j++){
//					A[i] *= num;
//				}
//				temp = num;
//			}
			for(int j=0;j<A.length;j++){
				C[C.length-1-i-j] -= A[A.length-1-j] * temp;
			}
			answer[i] = temp;
		}
		for(int i=answer.length-1;i>=0;i--){
			System.out.print(answer[i]+" ");
		}
	}
	public static int gcd(int a,int b){
		BigInteger b1 = BigInteger.valueOf(a);
		BigInteger b2 = BigInteger.valueOf(b);
		BigInteger gcd = b1.gcd(b2);
		return gcd.intValue();
	}
}