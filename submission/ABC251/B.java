import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
class Main{
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args){
		int N = readI();
		int W = readI();
		boolean[] isExist = new boolean[W+1];
		int[] A = new int[N];
		for(int i=0;i<N;i++){
			A[i] = readI();
			if(A[i]<=W)
				isExist[A[i]] = true;
		}
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(i==j)
					continue;
				int temp = A[i]+A[j];
				if(temp<=W)
					isExist[temp] = true;
			}
		}
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<N;k++){
					if(i==j||i==k||j==k)
						continue;
					int temp = A[i]+A[j]+A[k];
					if(temp<=W)
						isExist[temp] = true;
				}
			}
		}
		int count = 0;
		for(int i=0;i<isExist.length;i++){
			if(isExist[i])
				count++;
		}
		retE(count);
	}


	public static void exit(){System.exit(0);}
	public static void retE(boolean hantei){
		if(hantei)System.out.println("Yes");else System.out.println("No");System.exit(0);}
	public static void ret(boolean hantei){
		if(hantei)System.out.println("Yes");else System.out.println("No");}
	public static void DretE(boolean hantei){
		if(hantei)System.out.println("yes");else System.out.println("no");System.exit(0);}
	public static void Dret(boolean hantei){
		if(hantei)System.out.println("yes");else System.out.println("no");}
	public static void UretE(boolean hantei){
		if(hantei)System.out.println("YES");else System.out.println("NO");System.exit(0);}
	public static void Uret(boolean hantei){
		if(hantei)System.out.println("YES");else System.out.println("NO");}
	public static void retE(int ret){System.out.println(ret);System.exit(0);}
	public static void retE(long ret){System.out.println(ret);System.exit(0);}
	public static void retE(BigInteger ret){System.out.println(ret);System.exit(0);}
	public static void retE(String ret){System.out.println(ret);System.exit(0);}
	public static void ret(int ret){System.out.println(ret);}
	public static void ret(long ret){System.out.println(ret);}
	public static void ret(Object ret){System.out.println(ret);}
	public static void ret(String ret){System.out.println(ret);}
	public static String read(){return sc.next();}
	public static long readL(){return sc.nextLong();}
	public static int readI(){return sc.nextInt();}
	public static int[] readIs(int num){
		int[] ans = new int[num];for(int i=0;i<num;i++)	ans[i] = sc.nextInt();return ans;}
	public static long[] readLs(int num){
		long[] ans = new long[num];for(int i=0;i<num;i++) ans[i] = sc.nextLong();return ans;}
	public static String[] reads(int num){
		String[] ans = new String[num];for(int i=0;i<num;i++) ans[i] = sc.next();return ans;}
	public static void sort(Integer[][] list){
		if(list.length<2)	return;
		Integer[][] list1 = new Integer[list.length/2][],list2 = new Integer[list.length-list1.length][];
		System.arraycopy(list,0,list1,0,list1.length);
		System.arraycopy(list,list1.length,list2,0,list2.length);
		sort(list1);sort(list2);
		ArrayList<Integer[]> newList = new ArrayList<Integer[]>();
		int i = 0,j = 0;
		while(i<list1.length&&j<list2.length){
			int temp1 = list1[i][0],temp2 = list2[j][0];
			if(temp1<temp2)	newList.add(list1[i++]);
			else	newList.add(list2[j++]);}
		while(i<list1.length) newList.add(list1[i++]);while(j<list2.length) newList.add(list2[j++]);
		for(int k=0;k<list.length;k++)	list[k] = newList.get(k);}
	public static boolean isLarge(String value){
		if (value!=null) return Pattern.compile("^[A-Z]+$").matcher(value).matches();return false;}
	public static int[] prime(int num){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(2);for(int i=3;i<=num;i+=2){
			boolean isPrime = true;
			for(int j=0;j<list.size();j++){if((i%list.get(j))==0){isPrime = false;break;}}
			if(isPrime)	list.add(i);}
		int[] ans = new int[list.size()];
		for(int i=0;i<ans.length;i++)	ans[i] = list.get(i);return ans;}
	public static long[] primeL(long num){
		ArrayList<Long> list = new ArrayList<Long>();
		list.add(new Long(2));
		for(long i=3;i<=num;i+=2){
			boolean isPrime = true;
			for(int j=0;j<list.size();j++){if((i%list.get(j))==0){isPrime = false;break;}}
			if(isPrime)	list.add(i);}
		long[] ans = new long[list.size()];
		for(int i=0;i<ans.length;i++)	ans[i] = list.get(i);return ans;}
	public static long pow(long a,long b){
		int count = Long.toBinaryString(b).length();
		long answer = 1;
		for(int i=0;i<count;i++){
			if((b&1)==1)	answer *= a;
			a *= a;
			b = b >> 1;}
		return answer;}
	public static BigInteger bigPow(BigInteger a,long b){
		int count = Long.toBinaryString(b).length();
		BigInteger answer = BigInteger.ONE,x = new BigInteger(a.toString());
		for(int i=0;i<count;i++){
			if((b&1)==1)	answer.multiply(a);
			x = x.multiply(x);
			b = b >> 1;}
		return answer;}
	public static long[] divMod(long a,long b){
		if(b==0) retE("Oh my god! You tried to divide by zero!");long[] answer = {a/b,a%b};return answer;}
}
