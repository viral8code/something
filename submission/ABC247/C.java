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
	public static int[] readInts(int num){
		Scanner sc = new Scanner(System.in);
		int[] ans = new int[num];
		for(int i=0;i<num;i++){
			ans[i] = sc.nextInt();
		}
		return ans;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		for(int i=1;i<(int)Math.pow(2,N);i++){
			if(i%32768==0)
				System.out.print(16+" ");
			else if(i%16384==0)
				System.out.print(15+" ");
			else if(i%8192==0)
				System.out.print(14+" ");
			else if(i%4096==0)
				System.out.print(13+" ");
			else if(i%2048==0)
				System.out.print(12+" ");
			else if(i%1024==0)
				System.out.print(11+" ");
			else if(i%512==0)
				System.out.print(10+" ");
			else if(i%256==0)
				System.out.print(9+" ");
			else if(i%128==0)
				System.out.print(8+" ");
			else if(i%64==0)
				System.out.print(7+" ");
			else if(i%32==0)
				System.out.print(6+" ");
			else if(i%16==0)
				System.out.print(5+" ");
			else if(i%8==0)
				System.out.print(4+" ");
			else if(i%4==0)
				System.out.print(3+" ");
			else if(i%2==0)
				System.out.print(2+" ");
			else
				System.out.print(1+" ");
		}
		System.out.println();
	}
}