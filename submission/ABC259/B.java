import java.io.*;
import java.util.*;
import java.math.*;
class Main{
	public static void main(String[] args)throws IOException{
		Library Sys = new Library();
		String[] str = Sys.reads(" ");
		int a = Sys.parseInt(str[0]);
		int b = Sys.parseInt(str[1]);
		int d = Sys.parseInt(str[2]);
		if(a==0&&b==0){
			System.out.println(0+" "+0);
			System.exit(0);
		}
		double len = Math.sqrt(a*a+b*b);
		double rad = (a<=0&&b<=0)||(a>=0&&b<=0)?-Math.acos(a/len):Math.acos(a/len);
		double x = len*Math.cos(rad+Math.toRadians(d));
		double y = len*Math.sin(rad+Math.toRadians(d));
		System.out.printf("%8f",x);
		System.out.print(" ");
		System.out.printf("%8f",y);
		Sys.out.close();
	}
}


/*////////////////////////
	My Library
*/////////////////////////

class Library{

	/* Library fields, constructors, and methods
		@フィールド
		BufferedReader in	:入力系のメソッド用
		PrintWriter out		:出力用
		long[] Factorials	:a!を記録している(private)
		long[] InFactorials	:a!^-1を記録している(private)
		long mod		:あまりを求めるとき用(public)
		
		@コンストラクタ@
		Library()		:下準備なしオブジェクト生成
		Library(n,mod)		:下準備ありオブジェクト生成
			※a!、aCr(0<a<=n,0<r<a)を事前に計算しておく
			　呼び出しはgetFact(a)、getCombi(a,b)

		@入力系@
		readInt()		:1行に数字一つのみある時用
		readLong()		:1行に数字一つのみある時用
		read()			:1行をそのまま取得
		readInt(n)		:n型配列として取得(空白区切り)
		readLong(n)		:n個のlong型配列として取得(空白区切り)
		reads(str)		:String型配列として取得(str区切り)

		@計算系@
		gcd(a,b)		:aとbの最大公約数
		lcm(a,b)		:aとbの最小公倍数
		isPrime(num)		:素数判定
		prime(num)		:numまで(num含む)の素数すべてをint型配列にして返す
		pow(a,b)		:aのb乗
		modPow(a,b,mod)		:aのb乗(modで割ったあまり)
		combi(a,b)		:aCb
		modCombi(a,b,mod)	:aCb(modで割ったあまり)
		fact(a)			:a!
		modFact(a,mod)		:a!(modで割ったあまり)
			※もし引数にmodを書かない場合はフィールドのmodが使われる

		@変換系@
		parseInt(str)		:strをintに変換(変換ミスを検知できない)
		parseLong(str)		:strをlongに変換(変換ミスを検知できない)

		@ソート・探索系@
		sort(array)		:ヒープソート(int、longに対応)
		upsearch(array,key)	:keyのindex(なかったらkeyより大きい値の最小index)
						key以上の数字がなければ-1
		downsearch(array,key)	:keyのindex(なかったらkeyより小さい値の最大index)
						key以下の数字がなければ-1
			※同じ値がある場合はどのindexが選ばれるかは保証できない
	*/

	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out = new PrintWriter(System.out);
	private long[] Factorials,InFactorials;
	public long mod = 0;
	public Library(){}
	public Library(int fac,long mod){
		Factorials = new long[fac+1];
		Factorials[1] = 1;
		for(int i=2;i<=fac;i++){
			Factorials[i] = Factorials[i-1]*i%mod;
		}
		InFactorials = new long[fac+1];
		InFactorials[fac] = modPow(Factorials[fac],mod-2,mod);
		for(int i=fac;i>1;i--){
			InFactorials[i-1] = InFactorials[i]*i %mod;
		}
		this.mod = mod;
	}
	public long getFact(int num){
		return Factorials[num];
	}
	public long getCombi(int a,int b){
		return (Factorials[a]*InFactorials[a-b]%mod)*InFactorials[b] % mod;
	}
	public static long modFact(long a,long mod){
		long ans = 1;
		for(long i=2;i<=a;i++){
			ans *= i%mod;
			ans %= mod;
		}
		return ans;
	}
	public long modFact(long a){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans = 1;
		for(long i=2;i<=a;i++){
			ans *= i%mod;
			ans %= mod;
		}
		return ans;
	}
	public static long modCombi(long a,long b,long mod){
		long ans = 1;
		if(b==0||b==a) return 1;
		if(b<0||b>a) return 0;
		b = Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans *= a--;
			ans %= mod;
			ans *= modPow(i,mod-2,mod);
			ans %= mod;
		}
		return ans;
	}
	public long modCombi(long a,long b){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans = 1;
		if(b==0||b==a) return 1;
		if(b<0||b>a) return 0;
		b = Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans *= a--;
			ans %= mod;
			ans *= modPow(i,mod-2,mod);
			ans %= mod;
		}
		return ans;
	}
	public static long fact(long a){
		long ans = 1;
		for(long i=2;i<=a;i++){
			ans *= i;
		}
		return ans;
	}
	public static long combi(long a,long b){
		long ans = 1;
		if(b==0||b==a) return 1;
		if(b<0||b>a) return 0;
		b = Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans *= a--;
			ans /= i;
		}
		return ans;
	}
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
	public int readInt()throws IOException{
		String str = in.readLine();
		return str.length()<4 ? Integer.parseInt(str):parseInt(str);
	}
	public long readLong()throws IOException{
		return parseLong(in.readLine());
	}
	public String read()throws IOException{
		return in.readLine();
	}
	public int[] readInt(int n)throws IOException{
		int[] ans=new int[n];
		String[] str=in.readLine().split(" ");
		for(int i=0;i<n;i++){
			if(str[i].length()<4)
				ans[i]=Integer.parseInt(str[i]);
			else
				ans[i]=parseInt(str[i]);
		}
		return ans;
	}
	public long[] readLong(int n)throws IOException{
		long[] ans=new long[n];
		String[] str=in.readLine().split(" ");
		for(int i=0;i<n;i++)ans[i]=parseLong(str[i]);
		return ans;
	}
	public String[] reads(String str)throws IOException{
		return in.readLine().split(str);
	}
	public static long gcd(long a,long b){
		long temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return b;
	}
	public static long lcm(long a,long b){
		long mult=a*b,temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return mult/b;
	}
	public static boolean isPrime(long num){
		BigInteger bi=BigInteger.valueOf(num);
		return bi.isProbablePrime(20);
	}
	public static int[] prime(int num){
		BitSet nums=new BitSet(num+1);
		nums.set(2,num+1);
		for(int i=2;i<=Math.sqrt(num);i++)
			if(nums.get(i))
				for(int j=i*i;j<=num;j+=i)nums.clear(j);
		int[] answer=new int[nums.cardinality()];
		int i=2,index=0;
		while(true){
			i=nums.nextSetBit(i);
			answer[index++]=i++;
			if(index==answer.length)break;
		}
		return answer;
	}
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
	public static int parseInt(String str){
		char[] nums=str.toCharArray();
		int ans=0;
		boolean plus=true;
		if(nums[0]=='-'){
			plus=false;
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
			plus=false;
			nums[0]='0';
		}
		for(int i=0;i<nums.length;i++)ans=ans*10+nums[i]-'0';
		return plus?ans:-ans;
	}
	public static int downsearch(int[] nums,int num){
		int a=0,b=nums.length-1;
		if(nums[a]==num)return a;
		if(nums[a]>num)return -1;
		if(nums[b]<num)return b;
		int ans = (a+b)/2;
		while(b-a>1){
			if(nums[ans]>num)b=ans-1;
			else a=ans;
			ans = (a+b)/2;
		}
		if(nums[a]==num)return a;
		return b;
	}
	public static int upsearch(int[] nums,int num){
		int a=0,b=nums.length-1;
		if(nums[a]>num)return a;
		if(nums[b]==num)return b;
		if(nums[b]<num)return -1;
		int ans=(a+b)/2;
		while(b-a>1){
			if(nums[ans]<num)a=ans+1;
			else b=ans;
			ans=(a+b)/2;
		}
		if(nums[b]==num)return b;
		return a;
	}
	@Override
	public String toString(){
		String answer = "";
		if(Factorials!=null)
			answer += "Factorials are defined. (You can use 0~"+(Factorials.length-1)+" for index)";
		else
			answer += "Factorial is not defined.";
		answer += "\n";
		if(InFactorials!=null)
			answer += "InFactorials are defined. (You can use 0~"+(InFactorials.length-1)+" for index)";
		else
			answer += "InFactorial is not defined.";
		answer += "\n";
		if(mod!=0)
			answer += "mod is "+mod;
		else
			answer += "mod is not defined.";
		return answer;
	}
}
