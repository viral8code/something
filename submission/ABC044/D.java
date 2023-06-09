import java.io.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		long s = Long.parseLong(br.readLine());
		if(n==s){
			System.out.println(n+1);
			System.exit(0);
		}
		long check = (long)Math.sqrt(n);
		for(long i=2;i<=check;i++){
			if(func(i,n)==s){
				System.out.println(i);
				System.exit(0);
			}
		}
		for(long i=check;i>0;i--){
			long b= (n - s) / i + 1;
			if(b<2)
				continue;
			if(func(b,n)==s){
				System.out.println(b);
				System.exit(0);
			}
		}
		System.out.println(-1);
	}
	public static long func(long b,long n){
		if(b>n)
			return n;
		return func(b,n/b)+(n%b);
	}
}