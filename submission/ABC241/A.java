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
		int[] num = subMain.parsingInt(br.readLine());
		int answer = 0;
		int number = 0;
		for(int i=0;i<3;i++){
			answer = num[number];
			number = answer;
		}
		System.out.println(answer);
	}
}
