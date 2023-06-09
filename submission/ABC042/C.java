import java.io.*;
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
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
		int[] NK = subMain.parIntWithS(br.readLine());
		int[] D = subMain.parIntWithS(br.readLine());
		int ans = NK[0];
		while(true){
			boolean canPay = true;
			int[] temp = subMain.parInt(String.valueOf(ans));
			Label:for(int i=0;i<NK[1];i++){
				for(int j=0;j<temp.length;j++){
					if(temp[j]==D[i]){
						ans++;
						canPay = false;
						break Label;
					}
				}
			}
			if(canPay){
				System.out.println(ans);
				System.exit(0);
			}
		}
	}
}
