import java.io.*;
class subMain{
	static long[][] memo = {
		{1},
		{1,1},
		{1,2,1},
		{1,3,3,1},
		{1,4,6,4,1},
		{1,5,10,10,5,1},
		{1,6,15,20,15,6,1},
		{1,7,21,35,35,21,7,1},
		{1,8,28,56,70,56,28,8,1},
		{1,9,36,84,126,126,84,36,9,1},
		{1,10,45,120,210,252,210,120,45,10,1}
	};
	public static long process(String number,int count){
		if(count==0)
			return Long.parseLong(number);
		long num = 0;
		count--;
		for(int i=1;i<number.length()-count;i++){
			num += Long.parseLong(number.substring(0,i))*memo[number.length()-i-1][count];
			num += process(number.substring(i),count);
		}
		return num;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String number = br.readLine();
		long answer = 0;
		for(int i=0;i<number.length();i++){
			long temp = subMain.process(number,i);
			answer += temp;
		}
		System.out.println(answer);
	}
}
			