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
		while(answer.length()<200){
			int Value = random.nextInt(100);
			switch((int)((Value/25.0)*(Value/25.0)*4)%11){
				case 0:
				case 6:
				case 8:
				case 9:
					answer = answer + "D";
					break;
				case 2:
				case 4:
				case 5:
					answer = answer + "R";
					break;
				case 1:
				case 10:
					answer = answer + "L";
					break;
				case 3:
				case 7:
				default:
					answer = answer + "U";
					break;
			}
		}
		System.out.println(answer);
	}
}
