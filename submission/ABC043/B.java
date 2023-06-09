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
		String[] s = subMain.parStr(br.readLine());
		String ans = "";
		for(int i=0;i<s.length;i++){
			if(s[i].equals("B")&&!(ans.equals("")))
				ans = ans.substring(0,ans.length()-1);
			else if(s[i].equals("0"))
				ans += "0";
			else if(s[i].equals("1"))
				ans += "1";
		}
		System.out.println(ans);
	}
}			