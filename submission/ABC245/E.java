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
		int[] NM = subMain.parIntWithS(br.readLine());
		int[] A = subMain.parIntWithS(br.readLine());
		int[] B = subMain.parIntWithS(br.readLine());
		int[] C = subMain.parIntWithS(br.readLine());
		int[] D = subMain.parIntWithS(br.readLine());
		int[][] choco = {A,B};
		int[][] box = {C,D};
		choco = sorting(choco);
		box = sorting(box);
		for(int i=0;i<choco.length;i++){
			boolean impossible = true;
			for(int j=0;j<box.length;j++){
				if(choco[0][i]<=box[0][j]&&choco[1][i]<=box[1][j]){
					box[0][j] = -1;
					box[1][j] = -1;
					impossible = false;
				}
			}
			if(impossible){
				System.out.println("No");
				System.exit(0);
			}
		}
		System.out.println("Yes");
	}
	public static int[][] sorting(int[][] num){
		for(int i=1;i<num.length-1;i++){
			for(int j=num.length-1;j>i;j++){
				if(num[j-1][0]>num[j][0]){
					int[] temp = new int[2];
					temp[0] = num[j][0];
					temp[1] = num[j][1];
					num[j][0] = num[j-1][0];
					num[j][1] = num[j-1][0];
					num[j-1] = temp;
				}
				else if(num[j-1][0]==num[j][0]&&num[j-1][1]>num[j][1]){
					int temp = num[j-1][1];
					num[j-1][1] = num[j][1];
					num[j][1] = temp;
				}
			}
		}
		return num;
	}
		
}
