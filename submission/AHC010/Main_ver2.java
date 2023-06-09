import java.io.*;
import java.util.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] tail = new int[30][30];
		for(int i=0;i<30;i++){
			tail[i] = subMain.parInt(br.readLine());
		}
		String answer = "";
		Random random = new Random();
		for(int i=0;i<900;i++){
			answer += String.valueOf(random.nextInt(4));
		}
		System.out.println(answer);
	}
}
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
}