import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		String X = Integer.toBinaryString(sc.nextInt());
		int answer = 0;
		while(X.length()<N){
			X = "0" + X;
		}
		for(int i=X.length();i>0;i--){
			int temp = sc.nextInt();
			if(X.substring(i-1,i).equals("1"))
				answer += temp;
		}
		System.out.println(answer);
	}
}