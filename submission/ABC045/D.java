import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		long H = sc.nextLong()-2;
		long W = sc.nextLong()-2;
		long N = sc.nextLong();
		if(N==0){
			System.out.println(H*W);
			for(int i=0;i<9;i++){
				System.out.println(0);
			}
			System.exit(0);
		}
		int[][] AB = new int[9*(int)N][3];
		for(int l=0;l<N;l++){
			int a = sc.nextInt();
			int b = sc.nextInt();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					Loop:for(int k=0;k<AB.length;k++){
						if(a-i<1||b-j<1||a-i>H||b-j>W)
							break Loop;
						if(AB[k][0]==a-i&&AB[k][1]==b-j){
							AB[k][2]++;
							break Loop;
						}
						if(AB[k][0]==0&&AB[k][1]==0){
							AB[k][0] = a-i;
							AB[k][1] = b-j;
							AB[k][2] = 1;
							break Loop;
						}
					}
				}
			}
		}
		long[] num = new long[10];
		long temp = H*W;
		for(int i=0;i<AB.length;i++){
			if(AB[i][2]==0)
				break;
			num[AB[i][2]]++;
			temp--;
		}
		System.out.println(temp);
		for(int i=1;i<num.length;i++){
			System.out.println(num[i]);
		}
	}
}