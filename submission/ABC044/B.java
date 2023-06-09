import java.io.*;
import java.util.*;
class subMain{
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] w = subMain.parStr(br.readLine());
		if(w.length%2==1){
			System.out.println("No");
			System.exit(0);
		}
		Arrays.sort(w);
		for(int i=0;i<w.length;i+=2){
			if(!(w[i].equals(w[i+1]))){
				System.out.println("No");
				System.exit(0);
			}
		}
		System.out.println("Yes");
	}
}
