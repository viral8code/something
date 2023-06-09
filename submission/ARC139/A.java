import java.io.*;
import java.util.*;
import java.util.regex.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] T = subMain.parIntWithS(br.readLine());
		long answer = (long)Math.pow(2,T[0]);
		for(int i=1;i<N;i++){
			if(T[i-1]>T[i])
				answer += (long)Math.pow(2,T[i]);
			else if(Long.toBinaryString(answer).length()<=T[i])
				answer = (long)Math.pow(2,T[i]);
			else if(T[i-1]==T[i])
				answer += (long)Math.pow(2,T[i]+1);
			else{
				for(int j=T[i-1];j<T[i];j++){
					String str = Long.toBinaryString(answer);
					if(str.substring(str.length()-j-1,str.length()-j).equals("1"))
						answer += (long)Math.pow(2,j);
				}
				String str = Long.toBinaryString(answer);
				if(str.substring(str.length()-T[i]-1,str.length()-T[i]).equals("0"))
					answer += (long)Math.pow(2,T[i]);
			}
		}
		System.out.println(answer);
	}
}
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStrWithS(String someStr){
		String[] str = someStr.split(" ");
		return str;
	}
	public static int[] readInts(int num){
		Scanner sc = new Scanner(System.in);
		int[] ans = new int[num];
		for(int i=0;i<num;i++){
			ans[i] = sc.nextInt();
		}
		return ans;
	}
	public static void mergeSortForFirst(Integer[][] list){
		if(list.length<2)
			return;
		Integer[][] list1 = new Integer[list.length/2][];
		Integer[][] list2 = new Integer[list.length-list1.length][];
		System.arraycopy(list,0,list1,0,list1.length);
		System.arraycopy(list,list1.length,list2,0,list2.length);

		mergeSortForFirst(list1);
		mergeSortForFirst(list2);

		ArrayList<Integer[]> newList = new ArrayList<Integer[]>();

		int i = 0,j = 0;

		while(i<list1.length&&j<list2.length){
			int temp1 = list1[i][0];
			int temp2 = list2[j][0];

			if(temp1<temp2){
				newList.add(list1[i]);
				i++;
			}
			else{
				newList.add(list2[j]);
				j++;
			}
		}
		while(i<list1.length){
			newList.add(list1[i]);
			i++;
		}
		while(j<list2.length){
			newList.add(list2[j]);
			j++;
		}
		for(int k=0;k<list.length;k++){
			list[k] = newList.get(k);
		}
	}
	public static boolean isAlphabetLarge(String value) {
		boolean result = false;
		if (value != null) {
			Pattern pattern = Pattern.compile("^[A-Z]+$");
			result = pattern.matcher(value).matches();
		}
		return result;
	}
}
