import java.io.*;
import java.util.*;
import java.math.*;
import java.util.regex.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		int[][] cases = new int[T][6];
		for(int i=0;i<T;i++){
			cases[i] = subMain.parIntWithS(br.readLine());
		}
		for(int i=0;i<T;i++){
			int answer = 0;
			int N = cases[i][0];
			int A = cases[i][1];
			int B = cases[i][2];
			int X = cases[i][3];
			int Y = cases[i][4];
			int Z = cases[i][5];
			long AlcmB = (long)A*B/Long.parseLong(new BigInteger(String.valueOf(A)).gcd(new BigInteger(String.valueOf(B))).toString());
			if(AlcmB/A*Y<AlcmB/B*Z&&AlcmB/A*Y<AlcmB*X){
				answer = (N-B/A)*Y;
				N %= A;
			}
			else if(AlcmB/A*Y>AlcmB/B*Z&&AlcmB/B*Z<AlcmB*X){
				answer = (N-A/B)*Z;
				N %= B;
			}
			else{
				System.out.println(N*X);
				continue;
			}
			long Afinish = (N/A)*Y+(N%A)*X;
			long Bfinish = (N/B)*Z+(N%B)*X;
			long onefinish = N*X;
			if(Afinish<Bfinish&&Afinish<onefinish)
				answer += Afinish;
			else if(Afinish>Bfinish&&Bfinish<onefinish)
				answer += Bfinish;
			else
				answer += onefinish;
			System.out.println(answer);
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
		Scanner ssc = new Scanner(System.in);
		int[] ans = new int[num];
		for(int i=0;i<num;i++){
			ans[i] = ssc.nextInt();
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
