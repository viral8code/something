import java.io.*;
class subMain{
	public static int[] parsingInt(String someInt){
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
		int[] NM = subMain.parsingInt(br.readLine());
		int[] Anum = subMain.parsingInt(br.readLine());
		int[] Bnum = subMain.parsingInt(br.readLine());
		for(int i=0;i<Bnum.length;i++){
			boolean CanEat = false;
			for(int j=0;j<Anum.length;j++){
				if(Anum[j]==Bnum[i]){
					Anum[j] = 0;
					CanEat = true;
					break;
				}
			}
			if(!CanEat){
				System.out.println("No");
				System.exit(0);
			}
		}
		System.out.println("Yes");
	}
}
