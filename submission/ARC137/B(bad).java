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
		int decoy = Integer.parseInt(br.readLine());
		int[] A = subMain.parIntWithS(br.readLine());
		int[] count = {0,0};
		for(int i=0;i<A.length;i++){
			int temp = 1;
			check:for(int j=i+1;j<A.length;j++){
				if(A[i]!=A[j])
					break check;
				temp++;
			}
			if(count[A[i]]<temp)
				count[A[i]] = temp;
			i += (temp - 1);
		}
		System.out.println(count[0] + count[1] + 1);
	}
}
