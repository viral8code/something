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
		String[] start = subMain.parStrWithS(br.readLine());
		String[] finish = subMain.parStrWithS(br.readLine());
		if(start[0].equals(finish[0])){
			if(start[1].equals(finish[1])){
				System.out.println("Yes");
				System.exit(0);
			}
			else{
				System.out.println("No");
				System.exit(0);
			}
		}
		else{
			if(start[1].equals(finish[0])){
				String temp = start[1];
				start[1] = start[0];
				start[0] = temp;
			}
			else{
				String temp = start[2];
				start[2] = start[0];
				start[0] = temp;
			}
		}
		if(start[1].equals(finish[1])){
			System.out.println("No");
			System.exit(0);
		}
		System.out.println("Yes");
	}
}
