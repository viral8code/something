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
		String decoy = br.readLine();
		String[] str = subMain.parStr(br.readLine());
		int to = 0;
		int x = 0;
		int y = 0;
		for(int i=0;i<str.length;i++){
			if(str[i].equals("S")){
				switch(to){
					case 0:
						x++;
						break;
					case 1:
						y--;
						break;
					case 2:
						x--;
						break;
					case 3:
						y++;
						break;
					default:
						break;
				}
			}
			else{
				to = (to+1) % 4;
			}
		}
		System.out.println(x + " " + y);
	}
}
