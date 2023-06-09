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
		int N = Integer.parseInt(br.readLine());
		boolean[] ans = new boolean[2*N+1];
		for(int i=0;i<ans.length;i++){
			ans[i] = true;
		}
		while(true){
			int temp = -1;
			for(int i=0;i<ans.length;i++){
				if(ans[i]){
					temp = i;
					ans[i] = false;
					break;
				}
			}
			if(temp==-1)
				System.exit(0);
			System.out.println(temp+1);
			System.out.flush();
			temp = Integer.parseInt(br.readLine());
			if(temp==0)
				System.exit(0);
			ans[temp-1] = false;
		}
	}
}