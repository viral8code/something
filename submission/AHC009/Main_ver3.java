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
		Scanner sc = new Scanner(System.in);
		int[] S = new int[2];
		int[] T = new int[2];
		S[0] = sc.nextInt();
		S[1] = sc.nextInt();
		T[0] = sc.nextInt();
		T[1] = sc.nextInt();
		double p = sc.nextDouble();
		String str = sc.nextLine();
		int[][] H = new int[20][19];
		int[][] V = new int[19][20];
		for(int i=0;i<20;i++){
			H[i] = subMain.parInt(sc.nextLine());
		}
		for(int i=0;i<19;i++){
			V[i] = subMain.parInt(sc.nextLine());
		}
		int count = 0;
		for(int i=S[0];i<T[0];i++){
			for(int j=S[1];j<T[1];j++){
				if(H[i][j]==1&&V[i][j]==1)
					count++;
			}
		}
		String answer = "";
		Random random = new Random();
		while(answer.length()<179){
			int Value = random.nextInt(15);
			switch(Value){
				case 0:
				case 1:
					answer = answer + "U";
					break;
				case 2:
				case 3:
				case 4:
					answer = answer + "L";
					break;
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					answer = answer + "D";
					break;
				case 11:
				case 12:
				case 13:
				default:
					answer = answer + "R";
					break;
			}
		}
		answer = answer + "RRRRRRRRRRULLLLLLLLLL";
		System.out.println(answer);
	}
}
