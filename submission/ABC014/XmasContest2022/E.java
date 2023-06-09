import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.awt.Point;
import java.util.*;
import java.util.stream.*;
import java.util.function.Function;
final class Main{

	static final boolean autoFlush = false;
	static final SimpleScanner sc = new SimpleScanner(java.lang.System.in);
	static final Library System = new Library(java.lang.System.out,autoFlush);
	

	public static void main(String[] args){

		/*メモ////////

		*/////////////

		int N = sc.nextInt();
		int[] P = sc.nextInt(N);
		Random rm = new Random();
		int count

		System.out.close();
	}
}
class BIT{
	int size;
	long[] tree;
	public BIT(int n){
		size = n;
		tree = new long[n+1];
	}
	public long sum(int i){
		long sum = 0;
		while(i>0){
			sum += tree[i];
			i -= i&(-i);
		}
		return sum;
	}
	public void add(int i,long x){
		while(i<=size){
			tree[i] += x;
			i += i&(-i);
		}
	}
}
/*
                     ／）
                  ／／／）
               ／,=ﾞ"／      
  ／         i f  ,.r='"-‐'つ＿＿＿_こまけぇこたぁいいんだよ！！
/           /      _,.‐'￣／⌒     ⌒＼
    ／     ,i      ,二ﾆ⊃（ ⚫）.（⚫）＼
  /       ﾉ       ilﾞフ::⌒（__人__）⌒:: ＼
        ,ｲ｢ﾄ､    ,!|         |r┬-|        |
      /  iﾄヾヽ_/ｨ"＼         `ー'´      ／
*/
/*////////////////////////////////////////////////
	* My Library *

	@author viral
*/////////////////////////////////////////////////
final class Library{
	final SimplePrinter out;
	final PrintStream err = System.err;
	private long[] Factorials,InFactorials;
	public long mod = 0;
	public Library(PrintStream out,boolean bool){
		this.out = new SimplePrinter(out,bool);
	}
	public void makeFact(int fac,long mod){
		Factorials=new long[fac+1];
		Factorials[0]=Factorials[1]=1;
		for(int i=2;i<=fac;i++)Factorials[i]=Factorials[i-1]*i%mod;
		InFactorials=new long[fac+1];
		InFactorials[fac]=modPow(Factorials[fac],mod-2,mod);
		for(int i=fac;i>0;i--)InFactorials[i-1]=InFactorials[i]*i%mod;
		InFactorials[0] = 1;
		this.mod = mod;
	}

	/**
	 * コンストラクタで渡された値までの
	 * num!、aCbをmodで割ったあまりを返します。
	 */
	public long getFact(int num){
		return Factorials[num];
	}
	public long getCombi(int a,int b){
		return (Factorials[a]*InFactorials[a-b]%mod)*InFactorials[b]%mod;
	}

	/**
	 * factではa!を、
	 * modFactでは引数のmodかコンストラクタで渡されたmodで
	 * 割ったあまりを返します。
	 */
	public static long fact(long a){
		long ans=1;
		for(long i=2;i<=a;i++)ans*=i;
		return ans;
	}
	public static long modFact(long a,long mod){
		long ans=1;
		for(long i=2;i<=a;i++){
			ans*=i%mod;
			ans%=mod;
		}
		return ans;
	}
	public long modFact(long a){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans=1;
		for(long i=2;i<=a;i++){
			ans*=i%mod;
			ans%=mod;
		}
		return ans;
	}

	/**
	 * CombiではaCb、
	 * modCombiでは引数のmodかコンストラクタで渡されたmodで
	 * 割ったあまりを返します。
	 */
	public static long combi(long a,long b){
		long ans=1;
		if(b==0||b==a)return 1;
		if(b<0||b>a)return 0;
		b=Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans*=a--;
			ans/=i;
		}
		return ans;
	}
	public static long modCombi(long a,long b,long mod){
		long ans=1;
		if(b==0||b==a)return 1;
		if(b<0||b>a)return 0;
		b=Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans*=a--;
			ans%=mod;
			ans*=modPow(i,mod-2,mod);
			ans%=mod;
		}
		return ans;
	}
	public long modCombi(long a,long b){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans=1;
		if(b==0||b==a)return 1;
		if(b<0||b>a)return 0;
		b=Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans*=a--;
			ans%=mod;
			ans*=modPow(i,mod-2,mod);
			ans%=mod;
		}
		return ans;
	}

	/**
	 * int、long型配列をソートします。
	 * 二次元配列の場合は[i][0]と[i][1]の大きさで
	 * 昇順に並べ替えます。
	 */
	public static void sort(int[] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&list[(j-1)/2]<list[j]){
				int temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			int temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&list[j]<list[2*j+1])||(2*j+2<i-1&&list[j]<list[2*j+2])){
				if(2*j+2==i-1||list[2*j+1]>list[2*j+2]){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	public static void sort(int[][] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&(list[(j-1)/2][0]<list[j][0]||(list[(j-1)/2][0]==list[j][0]&&list[(j-1)/2][1]<list[j][1]))){
				int[] temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			int[] temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&(list[j][0]<list[2*j+1][0]||(list[j][0]==list[2*j+1][0]&&list[j][1]<list[2*j+1][1])))||
				(2*j+2<i-1&&(list[j][0]<list[2*j+2][0]||(list[j][0]==list[2*j+2][0]&&list[j][1]<list[2*j+2][1])))){
				if(2*j+2==i-1||(list[2*j+1][0]>list[2*j+2][0]||(list[2*j+1][0]==list[2*j+2][0]&&list[2*j+1][1]>list[2*j+2][1]))){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	public static void sort(long[] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&list[(j-1)/2]<list[j]){
				long temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			long temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&list[j]<list[2*j+1])||(2*j+2<i-1&&list[j]<list[2*j+2])){
				if(2*j+2==i-1||list[2*j+1]>list[2*j+2]){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	public static void sort(long[][] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&(list[(j-1)/2][0]<list[j][0]||(list[(j-1)/2][0]==list[j][0]&&list[(j-1)/2][1]<list[j][1]))){
				long[] temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			long[] temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&(list[j][0]<list[2*j+1][0]||(list[j][0]==list[2*j+1][0]&&list[j][1]<list[2*j+1][1])))||
				(2*j+2<i-1&&(list[j][0]<list[2*j+2][0]||(list[j][0]==list[2*j+2][0]&&list[j][1]<list[2*j+2][1])))){
				if(2*j+2==i-1||(list[2*j+1][0]>list[2*j+2][0]||(list[2*j+1][0]==list[2*j+2][0]&&list[2*j+1][1]>list[2*j+2][1]))){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}

	/**
	 * int型配列をソートします。
	 * 最大値が小さい場合は有効です。
	 */
	public static void countSort(int[] nums,int limit){
		int[] list=new int[limit+1];
		for(int i=0;i<nums.length;i++)list[nums[i]]++;
		int temp=0;
		for(int i=0;i<list.length;i++)for(int j=0;j<list[i];j++)nums[temp++]=i;
	}

	/**
	 * gcdは最大公約数を、
	 * lcmは最小公倍数を返します。
	 * lcmはオーバーフローの可能性があるのでご注意を。
	 * 戻り値はlong型です。
	 */
	public static long gcd(long a,long b){
		a = Math.abs(a);
		b = Math.abs(b);
		if(a==0||b==0)
			return Math.max(a,b);
		long temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return b;
	}
	public static long lcm(long a,long b){
		if(a==0||b==0)
			return 0;
		long mult=a*b,temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return mult/b;
	}

	/**
	 * BigIntegerクラスのものを使用します。
	 * 素数である確率が高いならtrue、
	 * 確実に合成数ならfalseを返します。
	 * 誤判定の確率は1/2^20以下です。
	 */
	public static boolean isPrime(long num){
		return BigInteger.valueOf(num).isProbablePrime(20);
	}

	/**
	 * 引数まで(引数含む)の素数を返します。
	 * 2^30以上の素数列挙はできないのでご注意を。
	 */
	public static int[] primes(int num){
		BitSet nums=new BitSet(num+1);
		nums.set(2,num+1);
		for(int i=2;i<=Math.sqrt(num);i++)if(nums.get(i))for(int j=i*i;j<=num;j+=i)nums.clear(j);
		int[] answer=new int[nums.cardinality()];
		int i=2,index=0;
		while(true){
			i=nums.nextSetBit(i);
			answer[index++]=i++;
			if(index==answer.length)break;
		}
		return answer;
	}

	/**
	 * powではそのままの結果を、
	 * modPowでは引数のmodかコンストラクタで渡されたmodで
	 * 割ったあまりを返します。
	 */
	public static long pow(long a,long b){
		long ans=1;
		while(b>0){
			if((b&1)==1)ans*=a;
			a*=a;
			b>>=1;
		}
		return ans;
	}
	public static long modPow(long a,long b,long mod){
		long ans=1;
		a%=mod;
		while(b>0){
			if((b&1)==1)ans*=a;
			ans%=mod;
			a*=a;
			a%=mod;
			b>>=1;
		}
		return ans;
	}
	public long modPow(long a,long b){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans=1;
		a%=mod;
		while(b>0){
			if((b&1)==1)ans*=a;
			ans%=mod;
			a*=a;
			a%=mod;
			b>>=1;
		}
		return ans;
	}

	/**
	 * Stringをint、longに変換します。
	 * 数値以外の文字であっても無理矢理数値変換してしまうので
	 * 使用の際はご注意を。
	 */
	public static int parseInt(String str){
		char[] nums=str.toCharArray();
		int ans=0;
		boolean plus=true;
		if(nums[0]=='-'){
			plus=!plus;
			nums[0]='0';
		}
		for(int i=0;i<nums.length;i++)ans=ans*10+nums[i]-'0';
		return plus?ans:-ans;
	}
	public static long parseLong(String str){
		char[] nums=str.toCharArray();
		long ans=0;
		boolean plus=true;
		if(nums[0]=='-'){
			plus=!plus;
			nums[0]='0';
		}
		for(int i=0;i<nums.length;i++)ans=ans*10+nums[i]-'0';
		return plus?ans:-ans;
	}

	/**
	 * downSearchではnum以下で最大の要素を、
	 * upSearchではnum以上で最小の要素を探します。
	 * 同様にunderSearchではnum未満で最大の要素を、
	 * overSearchではnumより大きい最小の要素を探します。
	 * なお、underとdownは条件を満たす最大のindexを、
	 * upとoverは条件を満たす最小のindexを返します。
	 * 戻り値は引数の配列(List)のindexです。
	 */
	//downSearch
	public static int downSearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int downSearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int downSearch(Integer[] nums,Integer num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int downSearch(Long[] nums,Long num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int downSearch(List<Integer> nums,Integer num){
		int a=0,b=nums.size()-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int downSearch(List<Long> nums,Long num){
		int a=0,b=nums.size()-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}

	//upSearch
	public static int upSearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=nums.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int upSearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int upSearch(Integer[] nums,Integer num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>=0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int upSearch(Long[] nums,Long num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>=0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int upSearch(List<Integer> nums,int num){
		int a=0,b=nums.size()-1,ans=nums.size(),c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>=0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int upSearch(List<Long> nums,Long num){
		int a=0,b=nums.size()-1,ans=nums.size(),c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>=0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}

	//underSearch
	public static int underSearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int underSearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int underSearch(Integer[] nums,Integer num){
		int a=0,b=nums.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>=0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int underSearch(Long[] nums,Long num){
		int a=0,b=nums.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>=0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int underSearch(List<Integer> nums,Integer num){
		int a=0,b=nums.size()-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>=0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int underSearch(List<Long> nums,Long num){
		int a=0,b=nums.size()-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>=0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}

	//overSearch
	public static int overSearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=nums.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int overSearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=nums.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int overSearch(Integer[] nums,Integer num){
		int a=0,b=nums.length-1,ans=nums.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int overSearch(Long[] nums,Long num){
		int a=0,b=nums.length-1,ans=nums.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c].compareTo(num)>0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int overSearch(List<Integer> nums,Integer num){
		int a=0,b=nums.size()-1,ans=nums.size(),c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int overSearch(List<Long> nums,Long num){
		int a=0,b=nums.size()-1,ans=nums.size(),c;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c).compareTo(num)>0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}

	/**
	 * 最長増加部分列の長さを返します。
	 * 引数にboolean型変数を渡すと
	 * 同じ値も増加と判定するか指定できます。
	 * デフォルトでは増加と判定しません。
	 */
	public static int lis(int[] nums){
		return lis(nums,false);
	}
	public static int lis(int[][] nums,int p){
		return lis(nums,p,false);
	}
	public static int lis(long[] nums){
		return lis(nums,false);
	}
	public static int lis(long[][] nums,int p){
		return lis(nums,p,false);
	}
	public static int lis(int[] nums,boolean include){
		int[] list = new int[nums.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int i=0;i<nums.length;i++){
			int index = include ? overSearch(list,nums[i]) : upSearch(list,nums[i]);
			list[index] = Math.min(list[index],nums[i]);
		}
		int answer = underSearch(list,Integer.MAX_VALUE);
		return answer+1;
	}
	public static int lis(int[][] nums,int p,boolean include){
		int[] list = new int[nums.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int i=0;i<nums.length;i++){
			int index = include ? overSearch(list,nums[i][p]) : upSearch(list,nums[i][p]);
			list[index] = Math.min(list[index],nums[i][p]);
		}
		int answer = underSearch(list,Integer.MAX_VALUE);
		return answer+1;
	}
	public static int lis(long[] nums,boolean include){
		long[] list = new long[nums.length];
		Arrays.fill(list,Long.MAX_VALUE);
		for(int i=0;i<nums.length;i++){
			int index = include ? overSearch(list,nums[i]) : upSearch(list,nums[i]);
			list[index] = Math.min(list[index],nums[i]);
		}
		int answer = underSearch(list,Long.MAX_VALUE);
		return answer+1;
	}
	public static int lis(long[][] nums,int p,boolean include){
		long[] list = new long[nums.length];
		Arrays.fill(list,Long.MAX_VALUE);
		for(int i=0;i<nums.length;i++){
			int index = include ? overSearch(list,nums[i][p]) : upSearch(list,nums[i][p]);
			list[index] = Math.min(list[index],nums[i][p]);
		}
		int answer = underSearch(list,Long.MAX_VALUE);
		return answer+1;
	}

	/**
	 * 前半2点と後半2点を結ぶ直線が交わっているか返します。
	 * もし同一直線上にある場合は0、交わっているなら1、
	 * どの条件も満たさない場合は-1を返します。
	 */
	public static int isCrossed(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		double s1 = (x1-x2)*(y3-y1)-(y1-y2)*(x3-x1);
		double t1 = (x1-x2)*(y4-y1)-(y1-y2)*(x4-x1);
		double s2 = (x3-x4)*(y1-y3)-(y3-y4)*(x1-x3);
		double t2 = (x3-x4)*(y2-y3)-(y3-y4)*(x2-x3);
		double temp1 = s1*t1;
		double temp2 = s2*t2;
		if(temp1>0||temp2>0)
			return -1;
		if(temp1==0&&temp2==0)
			return 0;
		return 1;
	}
	public static int isCrossed(Point p1,Point p2,Point p3,Point p4){
		return isCrossed(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y,p4.x,p4.y);
	}

	/**
	 * 与えられた点によって構成される多角形が凸多角形か判定します。
	 * 時計回りか反時計回りで渡して下さい。
	 */
	public static boolean isConvex(Point... points){
		int n = points.length;
		if(n<3)
			return false;
		if(n==3)
			return true;
		boolean conv = true;
		for(int i=0;i<n;i++){
			int result = isCrossed(points[i],points[(i+2)%n],points[(i+1)%n],points[(i+1+n/2)%n]);
			conv &= result>=0;
		}
		return conv;
	}

	//以下Systemクラスと同じメソッド
	public static void arraycopy(Object src,int srcPos,Object dest,int destPos,int length){
		System.arraycopy(src,srcPos,dest,destPos,length);
	}
	public static long nanoTime(){
		return System.nanoTime();
	}
	public static long currentTimeMillis(){
		return System.currentTimeMillis();
	}
	public void exit(int num){
		out.close();
		System.exit(num);
	}
}
//TreeList
final class TreeList<E extends Comparable<? super E>>{
	private Node<E>root;
	private boolean hasChanged;
	private int size;
	public TreeList(){
		size=0;
		root=null;
		hasChanged=false;
	}
	public E add(final E x){
		return addLast(x);
	}
	public E addLast(final E x){
		if(root==null)root=new Node<>(null,x);
		else root.addR(x);
		if(hasChanged)fit();
		++size;
		return x;
	}
	public E addFirst(final E x){
		if(root==null)root=new Node<>(null,x);
		else root.addL(x);
		if(hasChanged)fit();
		++size;
		return x;
	}
	public E insert(final int i,final E x){
		if(root==null)root=new Node<>(null,x);
		else if(i<size)root.insert(i,x);
		else if(i==size)root.addR(x);
		else throw new NullPointerException(i+" is out of size.(size="+size+")");
		if(hasChanged)fit();
		++size;
		return x;
	}
	public E get(final int i){return root.get(i);}
	public void remove(final int x){
		if(root==null)return;
		else{
			root.remove(x);
			if(hasChanged)fit();
		}
		--size;
	}
	public E pollFirst(){
		if(root==null)return null;
		--size;
		final E min=root.pollMin();
		if(hasChanged)fit();
		return min;
	}
	public E pollLast(){
		if(root==null)return null;
		--size;
		final E max=root.pollMax();
		if(hasChanged)fit();
		return max;
	}
	private void fit(){
		root=root.parent;
		hasChanged=false;
	}
	public Node root(){
		return root;
	}
	public int size(){return size;}
	@Override
	public String toString(){
		return Arrays.toString(toArray());
	}
	public Object[] toArray(){
		final Object[]list=new Object[size];
		if(root!=null)root.string(list,0);
		return list;
	}
	public class Node<E extends Comparable<? super E>>{
		private E value;
		int height,size;
		Node<E> left,right,parent;
		public Node(final Node<E>p,final E v){
			value=v;
			height=0;
			parent=p;
			size=1;
		}
		private int getHeight(){return Math.max(left==null?-1:left.height,right==null?-1:right.height)+1;}
		private int getSize(){return (left!=null?left.size:0)+(right!=null?right.size:0)+1;}
		public void addR(final E x){
			if(right!=null)right.addR(x);
			else right=new Node<>(this,x);
			check();
		}
		public void addL(final E x){
			if(left!=null)left.addL(x);
			else left=new Node<>(this,x);
			check();
		}
		public void remove(final int i){
			if(i==(left!=null?left.size:0)){
				E temp=left!=null?left.pollMax():right.pollMin();
				value=temp;
			}else if(left!=null&&i<left.size)left.remove(i);
			else right.remove(i-(left!=null?left.size:0)-1);
			check();
		}
		public void insert(final int i,final E x){
			if(i==(left!=null?left.size:0)){
				if(left!=null)left.addR(x);
				else left=new Node<>(this,x);
			}else if(left!=null&&i<left.size)left.insert(i, x);
			else if(right==null)right=new Node<>(this,x);
			else right.insert(i-(left!=null?left.size:0)-1,x);
			check();
		}
		public E get(final int i){
			if(i==(left!=null?left.size:0))return value;
			else if(left!=null&&i<left.size)return left.get(i);
			else return right.get(i-(left!=null?left.size:0)-1);
		}
		public E pollMax(){
			if(right==null){
				if(parent==null){
					hasChanged=true;
					parent=left;
					if(parent!=null)parent.parent=null;
				}
				else{
					if(parent.left==this)parent.left=left;
					else parent.right=left;
					if(left!=null)left.parent=parent;
					parent=null;
				}
				return value;
			}
			final E max=right.pollMax();
			check();
			return max;
		}
		public E pollMin(){
			if(left==null){
				if(parent==null){
					hasChanged=true;
					parent=right;
					if(parent!=null)parent.parent=null;
				}
				else{
					if(parent.left==this)parent.left=right;
					else parent.right=right;
					if(right!=null)right.parent=parent;
					parent=null;
				}
				return value;
			}
			final E min=left.pollMin();
			check();
			return min;
		}
		private void check(){
			fitStatus();
			final int lh=left==null?-1:left.height;
			final int rh=right==null?-1:right.height;
			if(lh-rh>1)rotateR();
			else if(rh-lh>1)rotateL();
		}
		public void fitStatus(){
			height=getHeight();
			size=getSize();
		}
		private void rotateR(){
			final Node<E>temp=left;
			left=left.right;
			if(left!=null)left.parent=this;
			temp.right=this;
			temp.parent=parent;
			if(parent!=null){
				if(parent.left==this)parent.left=temp;
				else parent.right=temp;
			}else hasChanged=true;
			parent=temp;
			if(temp.left!=null)temp.left.check();
			fitStatus();
			temp.fitStatus();
		}
		private void rotateL(){
			final Node<E>temp=right;
			right=right.left;
			if(right!=null)right.parent=this;
			temp.left=this;
			temp.parent=parent;
			if(parent!=null){
				if(parent.left==this)parent.left=temp;
				else parent.right = temp;
			}else hasChanged=true;
			parent=temp;
			fitStatus();
			if(temp.right!=null)temp.right.check();
			temp.fitStatus();
		}
		public int string(final Object[]list,int index){
			if(left!=null)index=left.string(list,index);
			list[index++]=value;
			if(right!=null)index=right.string(list,index);
			return index;
		}
		@Override
		public String toString(){return value.toString();}
	}
}
//AVLTree_Multi
final class AVLTree_Multi<E extends Comparable<? super E>>{
	private Node<E> root;
	private boolean hasChanged;
	private int size;
	public AVLTree_Multi(){
		size=0;
		root=null;
		hasChanged=false;
	}
	public boolean add(final E x){
		boolean bool;
		if(root==null){
			root=new Node<>(null,x);
			bool=true;
		}
		else bool=root.add(x);
		if(hasChanged){
			root=root.parent;
			hasChanged=false;
		}
		if(bool)++size;
		return bool;
	}
	public boolean remove(final E x){
		boolean bool;
		if(root==null)bool=false;
		else{
			bool=root.remove(x);
			if(hasChanged){
				root=root.parent;
				hasChanged=false;
			}
		}
		if(bool)--size;
		return bool;
	}
	public boolean contains(final E x){
		if(root==null)return false;
		return root.find(x);
	}
	public int count(final E x){
		if(root==null)return 0;
		return root.count(x);
	}
	public E pollFirst(){
		if(root==null)return null;
		--size;
		final E min=root.pollMin();
		if(hasChanged){
			root=root.parent;
			hasChanged=false;
		}
		return min;
	}
	public E pollLast(){
		if(root==null)return null;
		--size;
		final E max=root.pollMax();
		if(hasChanged){
			root=root.parent;
			hasChanged=false;
		}
		return max;
	}
	public E ceiling(final E x){
		if(root==null)return null;
		return root.ceiling(x,null);
	}
	public E floor(final E x){
		if(root==null)return null;
		return root.floor(x,null);
	}
	public int size(){
		return size;
	}
	public String toString(){
		final Object[]list=new Object[size];
		if(root!=null)root.string(list,0);
		return Arrays.toString(list);
	}
	private class Node<E extends Comparable<? super E>>{
		private E value;
		int height,count;
		Node<E> left,right,parent;
		public Node(final Node<E>p,final E v){
			value=v;
			height=0;
			parent=p;
			count=1;
		}
		private int getHeight(){
			return Math.max(left==null?-1:left.height,right==null?-1:right.height);
		}
		public boolean add(final E x){
			if(x.compareTo(value)>0)
				if(right==null){
					right=new Node<>(this,x);
					height=getHeight()+1;
					check();
					return true;
				}
				else{
					if(right.add(x)){
						height=getHeight()+1;
						check();
						return true;
					}
				}
			else if(x.compareTo(value)<0)
				if(left==null){
					left=new Node<>(this,x);
					height=getHeight()+1;
					check();
					return true;
				}
				else{
					if(left.add(x)){
						height=getHeight()+1;
						check();
						return true;
					}
				}
			else count++;
			return true;
		}
		public boolean remove(final E x){
			if(x.compareTo(value)>0){
				if(right==null)return false;
				else if(right.remove(x)){
					height=getHeight()+1;
					check();
					return true;
				}
			}
			else if(x.compareTo(value)<0){
				if(left==null)return false;
				else if(left.remove(x)){
					height=getHeight()+1;
					check();
					return true;
				}
			}
			else{
				if(count>1)count--;
				else
					if(parent==null)
						if(left==null&&right==null)hasChanged=true;
						else{
							value=left!=null?left.pollMax():right.pollMin();
							height=getHeight()+1;
							check();
						}
					else{
						final boolean flag=parent.left==this;
						if(left==null&&right==null){
							if(flag)parent.left=null;
							else parent.right=null;
							parent=null;
						}
						else{
							value=left!=null?left.pollMax():right.pollMin();
							height=getHeight()+1;
							check();
						}
					}
				return true;
			}
			return false;
		}
		public boolean find(final E x){
			if(x==value)return true;
			if(x.compareTo(value)>0)
				if(right==null)return false;
				else return right.find(x);
			else
				if(left==null)return false;
				else return left.find(x);
		}
		public int count(final E x){
			if(x==value)return count;
			if(x.compareTo(value)>0)
				if(right==null)return 0;
				else return right.count(x);
			else
				if(left==null)return 0;
				else return left.count(x);
		}
		public E ceiling(final E x,final E before){
			if(x.equals(value))return value;
			if(x.compareTo(value)<0)
				if(left==null)return value;
				else return left.ceiling(x,value);
			else
				if(right==null)return before;
				else return right.ceiling(x,before);
		}
		public E floor(final E x,final E before){
			if(x.equals(value))return value;
			if(x.compareTo(value)>0)
				if(right==null)return value;
				else return right.floor(x,value);
			else
				if(left==null)return before;
				else return left.floor(x,before);
		}
		public E pollMax(){
			if(right==null){
				if(count>1)count--;
				else
					if(parent==null){
						hasChanged=true;
						parent=left;
						if(parent!=null)parent.parent=null;
					}
					else{
						if(parent.left==this)parent.left=left;
						else parent.right=left;
						if(left!=null)left.parent=parent;
						parent=null;
					}
				return value;
			}
			final E max=right.pollMax();
			height=getHeight()+1;
			check();
			return max;
		}
		public E pollMin(){
			if(left==null){
				if(count>1)count--;
				else
					if(parent==null){
						hasChanged=true;
						parent=right;
						if(parent!=null)parent.parent=null;
					}
					else{
						if(parent.left==this)parent.left=right;
						else parent.right=right;
						if(right!=null)right.parent=parent;
						parent=null;
					}
				return value;
			}
			final E min=left.pollMin();
			height=getHeight()+1;
			check();
			return min;
		}
		private void check(){
			final int lh=left==null?-1:left.height;
			final int rh=right==null?-1:right.height;
			if(lh-rh>1)rotateR();
			else if(rh-lh>1)rotateL();
			height=getHeight()+1;
		}
		private void rotateR(){
			final Node<E>temp=left;
			left=left.right;
			if(left!=null)left.parent=this;
			temp.right=this;
			temp.parent=parent;
			if(parent!=null)
				if(parent.left==this)parent.left=temp;
				else parent.right=temp;
			else hasChanged=true;
			parent=temp;
			if(left!=null)left.check();
			if(right!=null)right.check();
		}
		private void rotateL(){
			final Node<E>temp=right;
			right=right.left;
			if(right!=null)right.parent=this;
			temp.left=this;
			temp.parent=parent;
			if(parent!=null)
				if(parent.left==this)parent.left=temp;
				else parent.right=temp;
			else hasChanged=true;
			parent=temp;
			if(left!=null)left.check();
			if(right!=null)right.check();
		}
		public int string(final Object[]list,int index){
			if(left!=null)index=left.string(list,index);
			for(int i=0;i<count;i++)
				list[index++]=value;
			if(right!=null)index=right.string(list,index);
			return index;
		}
		public String toString(){
			return value.toString();
		}
	}
}
//SegmentTree
abstract class SegmentTree<E>{
	int N,size;
	E def;
	Object[] node;
	public SegmentTree(int n,E defa,boolean include){
		N = 2;
		size = 1;
		while(N<n<<1){
			N <<= 1;
			size <<= 1;
		}
		size -= include?1:0;
		node = new Object[N];
		def = defa;
		Arrays.fill(node,def);
	}
	public SegmentTree(int n,E defa){
		this(n,defa,false);
	}
	@SuppressWarnings("unchecked")
	public void update(int n,E value){
		n += size;
		node[n] = value;
		n >>= 1;
		while(n>0){
			node[n] = function((E)node[n<<1],(E)node[(n<<1)+1]);
			n >>= 1;
		}
	}
	@SuppressWarnings("unchecked")
	public E get(int a){
		return (E)node[a+size];
	}
	@SuppressWarnings("unchecked")
	public E answer(int a){
		return (E)node[1];
	}
	@SuppressWarnings("unchecked")
	public E query(int l,int r){
		l += size;
		r += size;
		E answer = def;
		while(l>0&&r>0&&l<=r){
			if(l%2==1)
				answer = function((E)node[l++],answer);
			l >>= 1;
			if(r%2==0)
				answer = function(answer,(E)node[r--]);
			r >>= 1;
		}
		return answer;
	}

	@SuppressWarnings("unchecked")
	abstract public E function(E a,E b);
}
//Union-Find
final class UnionFind{
	private int[] par,rank,size;
	private int N,count;
	public UnionFind(int N){
		this.N = N;
		count = N;
		par = new int[N];
		rank = new int[N];
		size = new int[N];
		Arrays.fill(par,-1);
		Arrays.fill(size,1);
	}
	public int root(int x){
		if(par[x]==-1)
			return x;
		else
			return par[x] = root(par[x]);
	}
	public boolean isSame(int x,int y){
		return root(x)==root(y);
	}
	public boolean unite(int x,int y){
		int rx = root(x);
		int ry = root(y);
		if(rx==ry)
			return false;
		if(rank[rx]<rank[ry]){
			int temp = rx;
			rx = ry;
			ry = temp;
		}
		par[ry] = rx;
		if(rank[rx]==rank[ry])
			++rank[rx];
		size[rx] += size[ry];
		--count;
		return true;
	}
	public int groupCount(){
		return count;
	}
	public int size(int x){
		return size[root(x)];
	}
}
/*////////////////////////////////////////////////
	* My Scanner *

	@author viral
*/////////////////////////////////////////////////
final class SimpleScanner{
	final private int buff_size = 1<<15;
	private InputStream is;
	private byte[] buff;
	private int point,length;
	public SimpleScanner(InputStream is){
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private void reload(){
		do{
			try{
				length = is.read(buff,point = 0,buff_size);
			}catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
		}while(length==-1);
	}
	private byte read(){
		if(point==length)reload();
		return buff[point++];
	}
	public byte nextByte(){
		byte c = read();
		while(c<=' ')c = read();
		return c;
	}
	public int nextInt(){
		int ans = 0;
		byte c = read();
		while(c<=' ')c = read();
		boolean negate = c == '-';
		if(c=='-')c = read();
		while('0'<=c&&c<='9'){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public long nextLong(){
		long ans = 0;
		byte c = read();
		while(c<=' ')c = read();
		boolean negate = c == '-';
		if(c=='-')c = read();
		while('0'<=c&&c<='9'){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public char nextChar(){
		byte c = read();
		while(c<=' ')c = read();
		return (char)c;
	}
	public String next(){
		StringBuilder ans = new StringBuilder();
		byte c = read();
		while(c<=' ')c = read();
		while(c>' '){
			ans.append((char)c);
			c = read();
		}
		return ans.toString();
	}
	public byte[] nextByte(int n){
		byte[] ans = new byte[n];
		for(int i=0;i<n;i++){
			ans[i] = nextByte();
		}
		return ans;
	}
	public int[] nextInt(int n){
		int[] ans = new int[n];
		for(int i=0;i<n;i++){
			ans[i] = nextInt();
		}
		return ans;
	}
	public long[] nextLong(int n){
		long[] ans = new long[n];
		for(int i=0;i<n;i++){
			ans[i] = nextLong();
		}
		return ans;
	}
	public String[] next(int n){
		String[] ans = new String[n];
		for(int i=0;i<n;i++){
			ans[i] = next();
		}
		return ans;
	}
	public byte[][] nextByte(int n,int m){
		byte[][] ans = new byte[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextByte(m);
		}
		return ans;
	}
	public int[][] nextInt(int n,int m){
		int[][] ans = new int[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextInt(m);
		}
		return ans;
	}
	public long[][] nextLong(int n,int m){
		long[][] ans = new long[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextLong(m);
		}
		return ans;
	}
	public String[][] next(int n,int m){
		String[][] ans = new String[n][];
		for(int i=0;i<n;i++){
			ans[i] = next(m);
		}
		return ans;
	}
	public char[] nextCharArray(){
		return next().toCharArray();
	}
	public char[][] nextCharArray(int n){
		char[][] ans = new char[n][];
		for(int i=0;i<n;i++){
			ans[i] = next().toCharArray();
		}
		return ans;
	}
	public Point nextPoint(){
		return new Point(nextInt(),nextInt());
	}
	public Point[] nextPoint(int n){
		Point[] ans = new Point[n];
		for(int i=0;i<n;i++){
			ans[i] = new Point(nextInt(),nextInt());
		}
		return ans;
	}
	public void close(){
		try{
			is.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
/*////////////////////////////////////////////////
	* My Printer *

	@author viral
*/////////////////////////////////////////////////
final class SimplePrinter extends PrintWriter{
	public SimplePrinter(PrintStream os){
		super(os);
	}
	public SimplePrinter(PrintStream os,boolean bool){
		super(os,bool);
	}
	public void println(byte[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(byte[] nums,char c){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(c);
			print(nums[i]);
		}
		println();
	}
	public void println(byte[][] nums,String str){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(str);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(byte[][] nums,char c){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(c);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(int[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(int[] nums,char c){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(c);
			print(nums[i]);
		}
		println();
	}
	public void println(int[][] nums,String str){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(str);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(int[][] nums,char c){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(c);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(long[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(long[] nums,char c){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(c);
			print(nums[i]);
		}
		println();
	}
	public void println(long[][] nums,String str){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(str);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(long[][] nums,char c){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(c);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(Byte[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Byte[] nums,char c){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(c);
			print(nums[i]);
		}
		println();
	}
	public void println(Byte[][] nums,String str){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(str);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(Byte[][] nums,char c){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(c);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(Integer[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Integer[] nums,char c){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(c);
			print(nums[i]);
		}
		println();
	}
	public void println(Integer[][] nums,String str){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(str);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(Integer[][] nums,char c){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(c);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(Long[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Long[] nums,char c){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(c);
			print(nums[i]);
		}
		println();
	}
	public void println(Long[][] nums,String str){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(str);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(Long[][] nums,char c){
		for(int i=0;i<nums.length;i++){
			print(nums[i][0]);
			for(int j=1;j<nums[i].length;j++){
				print(c);
				print(nums[i][j]);
			}
			println();
		}
	}
	public void println(String[] strs,String str){
		println(String.join(str,strs));
	}
	public void println(String[] strs,char c){
		String str = String.valueOf(c);
		println(String.join(str,strs));
	}
	public void println(String[][] strs,String str){
		for(int i=0;i<strs.length;i++){
			println(String.join(str,strs[i]));
		}
	}
	public void println(String[][] strs,char c){
		String str = String.valueOf(c);
		for(int i=0;i<strs.length;i++){
			println(String.join(str,strs[i]));
		}
	}
	public void println(char[] cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(str);
			print(cs[i]);
		}
		println();
	}
	public void println(char[] cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(c);
			print(cs[i]);
		}
		println();
	}
	public void println(char[][] cs){
		for(int i=0;i<cs.length;i++){
			println(cs[i]);
		}
	}
	public void println(char[][] cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(str);
			print(cs[i]);
		}
		println();
	}
	public void println(char[][] cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(c);
			print(cs[i]);
		}
		println();
	}
}
