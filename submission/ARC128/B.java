import java.util.*;
class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		int[][] cases = new int[T][3];
		for(int i=0;i<T;i++){
			int[] temp = new int[3];
			temp[0] = sc.nextInt();
			temp[1] = sc.nextInt();
			temp[2] = sc.nextInt();
			Arrays.sort(temp);
			cases[i] = temp;
		}
		for(int i=0;i<T;i++){
			int[] temp = new int[3];
			boolean cantChange = true;
			if((cases[i][0]%3)==(cases[i][1]%3)){
				temp[0] = cases[i][1];
				cantChange = false;
			}
			else{
				temp[0] = 100000001;
			}
			if((cases[i][0]%3)==(cases[i][2]%3)){
				temp[1] = cases[i][2];
				cantChange = false;
			}
			else{
				temp[1] = 100000001;
			}
			if((cases[i][1]%3)==(cases[i][2]%3)){
				temp[2] = cases[i][2];
				cantChange = false;
			}
			else{
				temp[2] = 100000001;
			}
			if(cantChange){
				System.out.println(-1);
				continue;
			}
			Arrays.sort(temp);
			System.out.println(temp[0]);
		}
	}
}