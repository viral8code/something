import java.io.*;
import java.util.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		subMain sm = new subMain();
		String decoy = br.readLine();
		int[] array = sm.parsingInt(br.readLine());
		Arrays.sort(array);
		int count = 0;
		for(int i=0;i<array.length;i++){
			if(array[i]==0)
				continue;
			for(int j=i+1;j<array.length;j++){
				if(array[i]==array[j])
					array[j]=0;
				else
					break;
			}
			count++;
		}
		System.out.println(count);
	}	
}
class subMain{
	public int[] parsingInt(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
}