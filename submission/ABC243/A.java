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
		int[] VABC = subMain.parIntWithS(br.readLine());
		while(true){
			VABC[0] -= VABC[1];
			if(VABC[0]<0){
				System.out.println("F");
				System.exit(0);
			}
			VABC[0] -= VABC[2];
			if(VABC[0]<0){
				System.out.println("M");
				System.exit(0);
			}
			VABC[0] -= VABC[3];
			if(VABC[0]<0){
				System.out.println("T");
				System.exit(0);
			}
		}
	}
}