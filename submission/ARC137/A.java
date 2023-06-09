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
	public static long euclid(long a,long b){
		long temp;
		while((temp = a%b) != 0){
			a = b;
			b = temp;
		}
		return b;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		Scanner sc = new Scanner(System.in);
		long L = sc.nextLong();
		long R = sc.nextLong();
		long answer = 0;
		for(long i=R-L;i>0;i--){
			for(long j=L;(j+i)<=R;j++){
				long temp = subMain.euclid(j,j+i);
				if(temp==1){
					System.out.println(i);
					System.exit(0);
				}
			}
		}
	}
}