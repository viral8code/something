import java.io.*;
class subMain{
	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] a = subMain.parIntWithS(br.readLine());
		int sum = 0;
		for(int i=0;i<a.length;i++){
			sum += a[i];
		}
		long average = Math.round((double)sum/a.length);
		int ans = 0;
		for(int i=0;i<a.length;i++){
			ans += Math.pow(a[i]-average,2);
		}
		System.out.println(ans);
	}
}
