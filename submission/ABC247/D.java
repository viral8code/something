import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int Q = sc.nextInt();
		int[][] Cylinder = new int[200000][2];
		int pick1 = 0;
		int pick2 = 0;
		for(int i=0;i<Q;i++){
			int temp = sc.nextInt();
			if(temp==1){
				int x = sc.nextInt();
				int c = sc.nextInt();
				Cylinder[pick1][0] = x;
				Cylinder[pick1][1] = c;
				pick1++;
			}
			else if(temp==2){
				int c = sc.nextInt();
				long ans = 0;
				for(int j=0;j<c;){
					if(Cylinder[pick2][1]>c-j){
						Cylinder[pick2][1] -= (c-j);
						ans += (long)Cylinder[pick2][0]*(c-j);
						j = c;
					}
					else{
						ans += (long)Cylinder[pick2][0]*Cylinder[pick2][1];
						j += Cylinder[pick2][1];
						Cylinder[pick2][1] = 0;
						pick2++;
					}
				}
				System.out.println(ans);
			}
			else{
				System.exit(1);
			}
		}
	}
}