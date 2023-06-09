import java.io.*;
import java.util.*;
import java.math.*;
class Main{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);

	public static void main(String[] args)throws IOException{
		String[] str = reads(" ");
		long N = parseLong(str[0]);
		long K = parseLong(str[1]);
		int ans = 0;
		String temp1 = str[1];
		String temp2 = new StringBuilder(str[1]).reverse().toString();
		if(K<=parseLong(temp2)){
			HashSet<String> list = new HashSet<String>();
			while(temp1.length()<=str[0].length()||temp2.length()<=str[0].length()){
				if(N>=parseLong(temp1))list.add(temp1);
				temp1 += "0";
				if(N>=parseLong(temp2))list.add(temp2);
				temp2 += "0";
			}
			ans = list.size();
		}
		println(ans);
		flush();
	}
	
	public static int readInt()throws IOException{return Integer.parseInt(br.readLine());}
	public static long readLong()throws IOException{return Long.parseLong(br.readLine());}
	public static String read()throws IOException{return br.readLine();}
	public static int[] readInt(int n)throws IOException{int[] ans = new int[n];String[] str = br.readLine().split(" "); for(int i=0;i<n;i++)ans[i] = Integer.parseInt(str[i]);return ans;}
	public static long[] readLong(int n)throws IOException{long[] ans = new long[n];String[] str = br.readLine().split(" ");for(int i=0;i<n;i++)ans[i] = Long.parseLong(str[i]);return ans;}
	public static String[] reads(String str)throws IOException{return br.readLine().split(str);}
	public static void print(Object T){pw.print(T);}
	public static void println(Object T){pw.println(T);}
	public static void println(){pw.println();}
	public static void printlnAll(int... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(long... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(char... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(byte... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(Object[] T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(List<?> T){for(int i=0;i<T.size();i++)println(T.get(i));}
	public static void printAll(int[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(long[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(char[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(byte[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(Object[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(List<?> T,String str){for(int i=0;i<T.size()-1;i++){pw.print(T.get(i));pw.print(str);}print(T.get(T.size()-1));}
	public static void printAllln(int[] T,String str){printAll(T,str);println();}
	public static void printAllln(long[] T,String str){printAll(T,str);println();}
	public static void printAllln(char[] T,String str){printAll(T,str);println();}
	public static void printAllln(byte[] T,String str){printAll(T,str);println();}
	public static void printAllln(Object[] T,String str){printAll(T,str);println();}
	public static void printAllln(List<?> T,String str){printAll(T,str);println();}
	public static void flush(){pw.flush();}
	public static long gcd(long a,long b){long temp;while((temp = a % b) != 0){a = b;b = temp;}return b;}
	public static long lcm(long a,long b){return a * b / gcd(a, b);}
	public static boolean isPrime(long num){BigInteger bi = BigInteger.valueOf(num);return bi.isProbablePrime(20);}
	public static int[] prime(int num){BitSet nums = new BitSet(num+1);nums.set(2,num+1);for(int i=2;i<=Math.sqrt(num);i++)if(nums.get(i))for(int j=i*i;j<=num;j+=i)nums.clear(j);int[] answer = new int[nums.cardinality()];int i=2,index=0;while(true){i = nums.nextSetBit(i);answer[index++] = i++;if(index==answer.length)break;}return answer;}
	public static long pow(long a,long b){long ans = 1;while(b>0){if((b&1)==1)ans *= a;a *= a;b >>= 1;}return ans;}
	public static long modPow(long a,long b,long mod){long ans = 1;a %= mod;while(b>0){if((b&1)==1)ans *= a;ans %= mod;a *= a;a %= mod;b >>= 1;}return ans;}
	public static int parseInt(String str){char[] nums = str.toCharArray();int ans = 0;boolean plus = true;if(nums[0]=='-'){plus = false;nums[0]='0';}for(int i=0;i<nums.length;i++)ans = ans*10 + (int)(nums[i]-'0');return plus ? ans:-ans;}
	public static long parseLong(String str){char[] nums = str.toCharArray();long ans = 0;boolean plus = true;if(nums[0]=='-'){plus = false;nums[0]='0';}for(int i=0;i<nums.length;i++)ans = ans*10 + (int)(nums[i]-'0');return plus ? ans:-ans;}

}

