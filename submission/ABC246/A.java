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
		int[] xy1 = subMain.parIntWithS(br.readLine());
		int[] xy2 = subMain.parIntWithS(br.readLine());
		int[] xy3 = subMain.parIntWithS(br.readLine());
		if(xy1[0]==xy2[0])
			System.out.print(xy3[0]+" ");
		else if(xy1[0]==xy3[0])
			System.out.print(xy2[0]+" ");
		else
			System.out.print(xy1[0]+" ");
		if(xy1[1]==xy2[1])
			System.out.print(xy3[1]);
		else if(xy1[1]==xy3[1])
			System.out.print(xy2[1]);
		else
			System.out.print(xy1[1]);
		System.out.println();
	}
}