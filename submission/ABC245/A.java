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
		int[] ABCD = subMain.parIntWithS(br.readLine());
		if(ABCD[0]>ABCD[2]){
			System.out.println("Aoki");
			System.exit(0);
		}
		if(ABCD[0]<ABCD[2]){
			System.out.println("Takahashi");
			System.exit(0);
		}
		if(ABCD[1]>ABCD[3]){
			System.out.println("Aoki");
			System.exit(0);
		}
		System.out.println("Takahashi");
	}
}
