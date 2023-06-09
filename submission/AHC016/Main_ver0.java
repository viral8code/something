import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
class Main{
	static Random rm = new Random(0);
	static int odds;
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Random rm = new Random(0);
		int M = sc.nextInt();
		char[] eps = sc.next().toCharArray();
		odds = (eps[2]-'0')*10+eps[3]-'0';
		int N = M;
		System.out.println(N);
		ArrayList<String> list = new ArrayList<>();
		for(int i=0;i<M;i++){
			int m = i*(i+1)/2;
			StringBuilder sb = new StringBuilder();
			for(int j=0;j<m;j++)
				sb.append(1);
			for(int j=m;j<N*(N-1)/2;j++)
				sb.append(0);
			System.out.println(sb.toString());
			list.add(sb.toString());
		}
		for(int i=0;i<100;i++){
			String H = sc.next();
			int max = -1;
			int ans = -1;
			for(int k=0;k<M;k++){
				String S = list.get(k);
				int count = 0;
				for(int j=0;j<H.length();j++)
					if((S.charAt(j)==H.charAt(j))^(isBroken()))
						count++;
				if(count>max){
					max = count;
					ans = k;
				}
			}
			System.out.println(ans);
		}
	}
	public static boolean isBroken(){
		int num = rm.nextInt(100);
		for(int i=0;i<odds;i++)
			if(i==num)
				return true;
		return false;
	}
}