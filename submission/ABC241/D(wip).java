import java.io.*;
import java.util.*;
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
		int Q = Integer.parseInt(br.readLine);
		int[][] query = new int[Q][3];
		List<Integer> A = new ArrayList<Integer>;
		for(int i=0;i<Q;i++){
			query = subMain.parsingInt(br.readLine());
			if(query[i][0]==1)
				A.add(query[i][1]);
			else if(query[i][1]==2){
				if(A.length<query[i][2]){
					System.out.println("-1");
					continue;
				}
				
		}