import java.io.*;
class subMain{
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] str = subMain.parStr(br.readLine());
		if(str.length==2&&str[0].equals(str[1])){
			System.out.println("1 2");
			System.exit(0);
		}
		for(int i=0;i<str.length-2;i++){
			if(str[i].equals(str[i+1])){
				System.out.println((i+1)+" "+(i+2));
				System.exit(0);
			}
			else if(str[i].equals(str[i+2])){
				System.out.println((i+1)+" "+(i+3));
				System.exit(0);
			}
		}
		System.out.println("-1 -1");
	}
}
