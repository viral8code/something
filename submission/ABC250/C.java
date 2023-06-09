import java.io.*;
import java.util.*;
import java.util.regex.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N];
		int[] place = new int[N];
		for(int i=0;i<N;i++){
			A[i] = i+1;
			place[i] = i;
		}
		int Q = sc.nextInt();
		int[] X = new int[Q];
		for(int i=0;i<Q;i++){
			X[i] = sc.nextInt();
		}
		for(int i=0;i<X.length;i++){
			if(place[X[i]-1]+1==A.length){
				int temp = A[place[X[i]-1]];
				A[place[X[i]-1]] = A[place[X[i]-1]-1];
				A[place[X[i]-1]-1] = temp;
				int temp2 = place[A[place[X[i]-1]]-1];
				place[A[place[X[i]-1]]-1] = place[A[place[X[i]-1]-1]-1];
				place[A[place[X[i]-1]-1]-1] = temp2;
			}
			else{
				int temp = A[place[X[i]-1]];
				A[place[X[i]-1]] = A[place[X[i]-1]+1];
				A[place[X[i]-1]+1] = temp;
				int temp2 = place[A[place[X[i]-1]]-1];
				place[A[place[X[i]-1]]-1] = place[A[place[X[i]-1]+1]-1];
				place[A[place[X[i]-1]+1]-1] = temp2;
			}
		}
		for(int i=0;i<A.length;i++){
			System.out.print(A[i]+" ");
		}
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
