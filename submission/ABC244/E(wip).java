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
		int[] NMKSTX = subMain.parIntWithS(br.readLine());
		int[][] UV = new int[NMKSTX[1]][2];
		for(int i=0;i<UV.length;i++){
			UV[i] = subMain.parIntWithS(br.readLine());
		}
		int check = NMKSTX[3];
		boolean[] temp = new boolean[NMKSTX[0]];
		while(true){
			boolean s = true;
			for(int i=0;i<UV.length;i++){
				if(check==UV[i][0]&&!(temp[UV[i][1]])){
					check = UV[i][0];
					s = !(s);
					break;
				}
				else if(check==UV[i][1]&&!(temp[UV[i][0]])){
					check = UV[i][1];
					s = !(s);
					break;
				}
			}
			if(check==NMKSTX[4])
				break;
			if(s){
				System.out.println(0);
				System.exit(0);
			}
		}
	}
}
