import java.io.*;
class subMain{
	public static int[][] parsingInt(String someInt){
		String[] str = someInt.split(" ");
		int[][] Intel = new int[str.length][7];
		for(int j=0;j<str.length;j++){
			String[] strr = str[j].split("");
			int[] Intell = new int[7];
			for(int i=0;i<(6-strr.length);i++){
				Intell[i] = 0;
			}
			for(int i=(6-strr.length),l=0;i<6;i++,l++){
				Intell[i] = Integer.parseInt(strr[l]);
			}
			Intell[6] = strr.length;
			Intel[j] = Intell;
		}
		return Intel;
	}
	public static boolean check(int[] num1,int[] num2,int limit){
		boolean notCarry = true;
		for(int i=5;i>=limit;i--){
			if((num1[i]+num2[i])>9){
				notCarry = false;
				break;
			}
		}
		return notCarry;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] nums = subMain.parsingInt(br.readLine());
		int count = 0;
		for(int i=0;i<N;i++){
			for(int j=(i+1);j<N;j++){
				int limit = 6 - Math.min(nums[i][6],nums[j][6]);
				if(subMain.check(nums[i],nums[j],limit))
					count++;
			}
		}
		System.out.println(count);
	}
}
