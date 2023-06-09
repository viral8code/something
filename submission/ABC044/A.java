import java.io.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());
		int X = Integer.parseInt(br.readLine());
		int Y = Integer.parseInt(br.readLine());
		if(N<=K){
			System.out.println(N*X);
		}
		else{
			System.out.println(K*X+(N-K)*Y);
		}
	}
}