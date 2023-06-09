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
		int[] NK = subMain.parIntWithS(br.readLine());
		int[] A = subMain.parIntWithS(br.readLine());
		int[] B = subMain.parIntWithS(br.readLine());
		int[] temp = {-1,-1};
		for(int i=0;i<NK[0]-1;i++){
			int s1,s2;
			if(temp[0]==0){
				s1 = Math.abs(A[i]-A[i+1]);
				s2 = Math.abs(A[i]-B[i+1]);
			}
			else{
				s1 = Math.abs(B[i]-A[i+1]);
				s2 = Math.abs(B[i]-B[i+1]);
			}
			if(s1<s2){
				if(s1<=NK[0]){
					temp[1] = temp[0];
					temp[0] = 0;
				}
				else{
					System.out.println("No");
					System.exit(0);
				}
			}
			else{
				if(s2<=NK[0]){
					temp[1] = temp[0];
					temp[0] = 1;
				}
				else{
					System.out.println("No");
					System.exit(0);
				}
			}
		}
		System.out.println("Yes");
	}
}
