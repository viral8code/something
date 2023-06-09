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
		int N = Integer.parseInt(br.readLine());
		String[][] str = new String[N][2];
		for(int i=0;i<N;i++){
			str[i] = subMain.parStrWithS(br.readLine());
		}
		for(int i=0;i<N;i++){
			boolean first = false;
			boolean family = false;
			for(int j=0;j<N;j++){
				if(i==j)
					continue;
				if(str[i][0].equals(str[j][0])||str[i][0].equals(str[j][1]))
					family = true;
				if(str[i][1].equals(str[j][0])||str[i][1].equals(str[j][1]))
					first = true;
			}
			if(first&&family){
				System.out.println("No");
				System.exit(0);
			}
		}
		System.out.println("Yes");
	}
}