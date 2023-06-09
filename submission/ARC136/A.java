import java.io.*;
import java.util.*;
class subMain{
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<String> ABC = new ArrayList<String>();
		int N = Integer.parseInt(br.readLine());
		String[] str = subMain.parStr(br.readLine());
		for(int i=0;i<N;i++){
			ABC.add(str[i]);
		}
		int realLength = N-1;
		for(int i=0;i<realLength;i++){
			if((ABC.get(i)+ABC.get(i+1)).equals("BA")){
				ABC.set(i,"A");
				ABC.set(i+1,"B");
			}
			else if((ABC.get(i)+ABC.get(i+1)).equals("BB")){
				ABC.remove(i);
				ABC.set(i,"A");
				realLength--;
			}
		}
		String answer = String.join("",ABC);
		System.out.println(answer);
	}
}
