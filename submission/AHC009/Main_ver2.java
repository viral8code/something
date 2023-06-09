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
		for(int i=0;i<count;i++){
			answer = answer + "RDRDRDRDRDRDRD";
			for(int j=0;j<6;j++){
				int Value = random.nextInt(3);
				switch(Value){
					case 0:
						answer = answer + "U";
						break;
					default:
						answer = answer + "L";
						break;
				}
			}
		}
		while(answer.length()<200){
			int Value = random.nextInt(5);
			switch(Value){
				case 0:
					answer = answer + "U";
					break;
				case 1:
					answer = answer + "D";
					break;
				case 2:
					answer = answer + "R";
					break;
				default:
					answer = answer + "L";
					break;
			}
		}
		if(answer.length()>200)
			answer = answer.substring(0,200);
		System.out.println(answer);
	}
}
