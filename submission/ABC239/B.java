import java.io.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long num = Long.parseLong(br.readLine());
		if(num<0){
			int num2 = (int)(Math.abs(num) % 10);
			if(num2!=0)
				num-=10;
		}
		long ans = num / 10;
		System.out.println(ans);
	}
}
