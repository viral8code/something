import java.util.*;
class Main{
	static int K;
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		K = sc.nextInt();
		boolean[][] str = new boolean[27][N];
		for(int i=0;i<N;i++){
			char[] temp = sc.next().toCharArray();
			for(int j=0;j<temp.length;j++){
				str[temp[j]-'a'][i] = true;
			}
		}
		int answer = process(0,new int[27],str);
		System.out.println(answer);
	}
	public static int process(int pick,int[] now,boolean[][] str){
		int ans = 0;
		if(pick==str[0].length){
			for(int i=0;i<27;i++){
				if(now[i]==K)
					ans++;
			}
		}
		else{
			for(int i=pick;i<str[0].length;i++){
				int temp = process(i+1,now,str);
				int[] s = new int[27];
				for(int j=0;j<27;j++){
					s[j] += str[j][i] ? now[j]+1 : now[j];
				}
				temp = Math.max(process(i+1,s,str),temp);
				if(ans<temp)
					ans = temp;
			}
		}
		return ans;
	}
}
