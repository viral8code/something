import java.io.Writer;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.math.BigInteger;
import java.awt.Point;
import java.awt.Dimension;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
class Main{
	private static boolean autoFlush=false;
	private static SimpleScanner sc=new SimpleScanner(System.in);
	private static SimpleWriter out=new SimpleWriter(System.out,autoFlush);
	public static void main(String[]args){
		char[] S = sc.nextCharArray();
		double ans = 0;
		for(int i=2;i<S.length;i++){
			if(S[i-2]=='A'||S[i-2]=='?'){
				int sub = S[i-2]=='?'?1:0;
				if(S[i-1]=='B'||S[i-1]=='?'){
					sub += S[i-1]=='?'?1:0;
					if(S[i]=='C'||S[i]=='?'){
						sub += S[i]=='?'?1:0;
						ans += (int)Math.pow(3,3-sub);
					}
				}
			}
		}
		out.println(ans/27.0);
		out.close();
	}
}
class Factorial{
	private long[]fact,inFact;
	private long mod;
	public Factorial(int N,long mod){
		fact=new long[N+1];
		fact[0]=fact[1]=1;
		for(int i=2;i<=N;++i)fact[i]=fact[i-1]*i%mod;
		inFact=new long[N+1];
		inFact[N]=MathFunction.modPow(fact[N],mod-2,mod);
		for(int i=N;i>0;--i)inFact[i-1]=inFact[i]*i%mod;
		inFact[0]=1;
		this.mod=mod;
	}
	public long getFact(int num){return fact[num];}
	public long getInFact(int num){return inFact[num];}
	public long getCombi(int a,int b){if(a<b||a<0||b<0)return 0;return (fact[a]*inFact[a-b]%mod)*inFact[b]%mod;}
}
class ArrayFunction{
	public static void countSort(int[]array,int maximumLimit){
		int[]list=new int[maximumLimit+1];
		for(int num:array)++list[num];
		int temp=0;
		for(int i=0;i<list.length;++i)while(list[i]-->0)array[temp++]=i;
	}
	public static int[][]rotateR(int[][]array){
		int[][]ans=new int[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[ans[i].length-j-1][i];
		return ans;
	}
	public static long[][]rotateR(long[][]array){
		long[][]ans=new long[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[ans[i].length-j-1][i];
		return ans;
	}
	public static char[][]rotateR(char[][]array){
		char[][]ans=new char[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[ans[i].length-j-1][i];
		return ans;
	}
	public static double[][]rotateR(double[][]array){
		double[][]ans=new double[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[ans[i].length-j-1][i];
		return ans;
	}
	public static boolean[][]rotateR(boolean[][]array){
		boolean[][]ans=new boolean[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[ans[i].length-j-1][i];
		return ans;
	}
	public static<E>E[][]rotateR(E[][]array,E[][]ans){
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[ans[i].length-j-1][i];
		return ans;
	}
	public static int[][]rotateL(int[][]array){
		int[][]ans=new int[array[0].length][array.length];
		for(int i=0;i<ans.length;++i){int index=i;Arrays.setAll(ans[i],k->array[k][ans.length-index-1]);}
		return ans;
	}
	public static long[][]rotateL(long[][]array){
		long[][]ans=new long[array[0].length][array.length];
		for(int i=0;i<ans.length;++i){int index=i;Arrays.setAll(ans[i],k->array[k][ans.length-index-1]);}
		return ans;
	}
	public static char[][]rotateL(char[][]array){
		char[][]ans=new char[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[j][ans.length-i-1];
		return ans;
	}
	public static double[][]rotateL(double[][]array){
		double[][]ans=new double[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[j][ans.length-i-1];
		return ans;
	}
	public static boolean[][]rotateL(boolean[][]array){
		boolean[][]ans=new boolean[array[0].length][array.length];
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[j][ans.length-i-1];
		return ans;
	}
	public static<E>E[][]rotateL(E[][]array,E[][]ans){
		for(int i=0;i<ans.length;++i)for(int j=0;j<ans[i].length;++j)ans[i][j]=array[j][ans.length-i-1];
		return ans;
	}
	public static int lis(int[]array){return lis(array,false);}
	public static int lis(int[][]arrays,int p){return lis(arrays,p,false);}
	public static int lis(long[]array){return lis(array,false);}
	public static int lis(long[][]arrays,int p){return lis(arrays,p,false);}
	public static int lis(int[]array,boolean include){
		int[]list=new int[array.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int num:array){
			int index=include?Searcher.overSearch(list,num):Searcher.upSearch(list,num);
			list[index]=Math.min(list[index],num);
		}
		int answer=Searcher.underSearch(list,Integer.MAX_VALUE);
		return answer+1;
	}
	public static int lis(int[][]arrays,int p,boolean include){
		int[]list=new int[arrays.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int[]array:arrays){
			int index=include?Searcher.overSearch(list,array[p]):Searcher.upSearch(list,array[p]);
			list[index]=Math.min(list[index],array[p]);
		}
		int answer=Searcher.underSearch(list,Integer.MAX_VALUE);
		return answer+1;
	}
	public static int lis(long[]array,boolean include){
		long[]list=new long[array.length];
		Arrays.fill(list,Long.MAX_VALUE);
		for(long num:array){
			int index=include?Searcher.overSearch(list,num):Searcher.upSearch(list,num);
			list[index]=Math.min(list[index],num);
		}
		int answer=Searcher.underSearch(list,Long.MAX_VALUE);
		return answer+1;
	}
	public static int lis(long[][]arrays,int p,boolean include){
		long[]list=new long[arrays.length];
		Arrays.fill(list,Long.MAX_VALUE);
		for(long[]array:arrays){
			int index=include?Searcher.overSearch(list,array[p]):Searcher.upSearch(list,array[p]);
			list[index]=Math.min(list[index],array[p]);
		}
		int answer=Searcher.underSearch(list,Long.MAX_VALUE);
		return answer+1;
	}
	public static int[][]topologicalSort(ArrayList<ArrayList<Integer>>route){
		int[]count=new int[route.size()];
		int pathCount=0;
		for(ArrayList<Integer>path:route)for(int point:path){++pathCount;++count[point];}
		ArrayDeque<Integer>deq=new ArrayDeque<>();
		for(int i=1;i<count.length;++i)if(count[i]==0)deq.add(i);
		int[][]ans=new int[pathCount][2];
		int index=0;
		while(deq.size()>0){
			int nowP=deq.pollFirst();
			for(int nextP:route.get(nowP)){
				ans[index][0]=nowP;
				ans[index++][1]=nextP;
				if(--count[nextP]==0)deq.add(nextP);
			}
		}
		return ans;
	}
	public static int[][]topologicalSort(int[][]route){
		int[]count=new int[route.length];
		int pathCount=0;
		for(int[]path:route)for(int point:path){++pathCount;++count[point];}
		ArrayDeque<Integer>deq=new ArrayDeque<>();
		for(int i=1;i<count.length;++i)if(count[i]==0)deq.add(i);
		int[][]ans=new int[pathCount][2];
		int index=0;
		while(deq.size()>0){
			int nowP=deq.pollFirst();
			for(int nextP:route[nowP]){
				ans[index][0]=nowP;
				ans[index++][1]=nextP;
				if(--count[nextP]==0)deq.add( nextP );
			}
		}
		return ans;
	}
	public static void swap(int[]array,int a,int b){int temp=array[a];array[a]=array[b];array[b]=temp;}
	public static void swap(long[]array,int a,int b){long temp=array[a];array[a]=array[b];array[b]=temp;}
	public static void swap(double[]array,int a,int b){double temp=array[a];array[a]=array[b];array[b]=temp;}
	public static void swap(char[]array,int a,int b){char temp=array[a];array[a]=array[b];array[b]=temp;}
	public static void swap(boolean[]array,int a,int b){boolean temp=array[a];array[a]=array[b];array[b]=temp;}
	public static<E>void swap(E[]array,int a,int b){E temp=array[a];array[a]=array[b];array[b]=temp;}
	public static void swap(int[][]array,int a,int b,int c,int d){int temp=array[a][b];array[a][b]=array[c][d];array[c][d]=temp;}
	public static void swap(long[][]array,int a,int b,int c,int d){long temp=array[a][b];array[a][b]=array[c][d];array[c][d]=temp;}
	public static void swap(double[][]array,int a,int b,int c,int d){double temp=array[a][b];array[a][b]=array[c][d];array[c][d]=temp;}
	public static void swap(char[][]array,int a,int b,int c,int d){char temp=array[a][b];array[a][b]=array[c][d];array[c][d]=temp;}
	public static void swap(boolean[][]array,int a,int b,int c,int d){boolean temp=array[a][b];array[a][b]=array[c][d];array[c][d]=temp;}
	public static<E>void swap(E[][]array,int a,int b,int c,int d){E temp=array[a][b];array[a][b]=array[c][d];array[c][d]=temp;}
	public static boolean nextPermutation(int[]array){
		int index1=0;
		for(int i=1;i<array.length;++i)if(array[i-1]<array[i])index1=i;
		if(--index1<0)return false;
		int index2=0;
		int min=Integer.MAX_VALUE;
		for(int i=index1+1;i<array.length;++i)if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){min=array[i];index2=i;}
		swap(array,index1,index2);
		Arrays.sort(array,index1+1,array.length);
		return true;
	}
	public static boolean nextPermutation(long[]array){
		int index1=0;
		for(int i=1;i<array.length;++i)if(array[i-1]<array[i])index1=i;
		if(--index1<0)return false;
		int index2=0;
		long min=Long.MAX_VALUE;
		for(int i=index1+1;i<array.length;++i)if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){min=array[i];index2=i;}
		swap(array,index1,index2);
		Arrays.sort(array,index1+1,array.length);
		return true;
	}
	public static boolean nextPermutation(char[]array){
		int index1=0;
		for(int i=1;i<array.length;++i)if(array[i-1]<array[i])index1=i;
		if(--index1<0)return false;
		int index2=0;
		int min=Integer.MAX_VALUE;
		for(int i=index1+1;i<array.length;++i)if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){min=array[i];index2=i;}
		swap(array,index1,index2);
		Arrays.sort(array,index1+1,array.length);
		return true;
	}
	public static<E extends Comparable<E>>boolean nextPermutation(E[]array){
		int index1=0;
		for(int i=1;i<array.length;++i)if(array[i-1].compareTo(array[i])<0)index1=i;
		if(--index1<0)return false;
		int index2=-1;
		E min=MathFunction.max(array);
		int subIndex=-1;
		E max=array[index1];
		for(int i=index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){min=array[i];index2=i;}
			if(max.compareTo(array[i])<0){subIndex=i;max=array[i];}
		}
		if(index2==-1)swap(array,index1,subIndex);
		else swap(array,index1,index2);
		Arrays.sort(array,index1+1,array.length);
		return true;
	}
}
class Converter{public static String reverse(String str){StringBuilder sb=new StringBuilder();for(int i=str.length()-1;i>=0;--i)sb.append(str.charAt(i));return sb.toString();}}
class MathFunction{
	private static long[]numberForPrime={2,7,61,325,9375,28178,450775,9780504,1795265022};
	public static long gcd(long a,long b){
		a=Math.abs(a);
		b=Math.abs(b);
		if(b==0)return a;
		long temp;
		while((temp=a%b)!=0){a=b;b=temp;}
		return b;
	}
	public static long lcm(long a,long b){return a/gcd(a,b)*b;}
	public static boolean isPrime(long n){
		n=Math.abs(n);
		if(n==2L)return true;
		if(n==1L||(n&1L)==0L)return false;
		long d=n-1L;
		while((d&1)==0L)d>>=1;
		BigInteger bigN=BigInteger.valueOf(n);
		BigInteger bigNSubOne=bigN.subtract(BigInteger.ONE);
		BigInteger bigD=BigInteger.valueOf(d);
		for(long a:numberForPrime){
			if(a>=n)return true;
			BigInteger t=bigD;
			BigInteger y=BigInteger.valueOf(a).modPow(t,bigN);
			while(t.compareTo(bigNSubOne)==-1&&!y.equals(BigInteger.ONE)&&!y.equals(bigNSubOne)){
				y=y.multiply(y).mod(bigN);
				t=t.shiftLeft(1);
			}
			if(!y.equals(bigNSubOne)&&(t.intValue()&1)==0)return false;
		}
		return true;
	}
	public static int[]primes(int num){
		if(num<2)return new int[0];
		BitSet numbers=new BitSet(num+1);
		numbers.set(2,num+1);
		int limit=(int)Math.sqrt(num);
		for(int i=2;i<=limit;++i)if(numbers.get(i))for(int j=i*i;j<=num;j+=i)if(numbers.get(j))numbers.clear(j);
		int[]answer=new int[numbers.cardinality()];
		int i=2,index=0;
		do{i=numbers.nextSetBit(i);answer[index++]=i++;}while(index!=answer.length);
		return answer;
	}
	public static long pow(long a,long b){
		long ans=1;
		while(b>0){if((b&1)==1)ans*=a;a*=a;b>>=1;}
		return ans;
	}
	public static long modPow(long a,long b,long mod){
		long ans=1;
		a%=mod;
		while(b>0){if((b&1)==1)ans*=a;ans%=mod;a*=a;a%=mod;b>>=1;}
		return ans;
	}
	public static long fact(int N){
		long ans=1;
		for(int i=2;i<=N;++i)ans*=i;
		return ans;
	}
	public static long modFact(int N,long mod){
		long ans=1;
		for(int i=2;i<=N;++i){ans*=i;ans%=mod;}
		return ans;
	}
	public static long combi(long n,long r){
		if(r<0||n<r)return 0;
		long ans=1;
		r=Math.min(n-r,r);
		for(int i=0;i<r;++i){ans*=n-i;ans/=i+1;}
		return ans;
	}
	public static long modCombi(long n,long r,long mod){
		if(r<0||n<r)return 0;
		long ans=1;
		r=Math.min(n-r,r);
		for(int i=0;i<r;++i){ans*=(n-i)%mod;ans%=mod;ans*=modPow(i+1,mod-2,mod);ans%=mod;}
		return ans;
	}
	public static int isCrossed(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		long s1=(long)(x1-x2)*(y3-y1)-(long)(y1-y2)*(x3-x1);
		long t1=(long)(x1-x2)*(y4-y1)-(long)(y1-y2)*(x4-x1);
		long s2=(long)(x3-x4)*(y1-y3)-(long)(y3-y4)*(x1-x3);
		long t2=(long)(x3-x4)*(y2-y3)-(long)(y3-y4)*(x2-x3);
		long temp1=s1*t1;
		long temp2=s2*t2;
		return temp1>0||temp2>0?-1:temp1==0&&temp2==0?0:1;
	}
	public static int isCrossed(Point p1,Point p2,Point p3,Point p4){
		return isCrossed(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y,p4.x,p4.y);
	}
	public static boolean isConvex(Point...points){
		int n=points.length;
		if(n<3)return false;
		if(n==3)return true;
		boolean conv=true;
		for(int i=0;i<n;++i)conv&=isCrossed(points[i],points[(i+2)%n],points[(i+1)%n],points[(i+1+n/2)%n])>=0;
		return conv;
	}
	public static long remainder(long num,long mod){
		num%=mod;
		if(num<0)num+=mod;
		return num;
	}
	public static int digit(long num){
		if(num<10L)return 1;
		if(num<100L)return 2;
		if(num<1000L)return 3;
		if(num<10000L)return 4;
		if(num<100000L)return 5;
		if(num<1000000L)return 6;
		if(num<10000000L)return 7;
		if(num<100000000L)return 8;
		if(num<1000000000L)return 9;
		if(num<10000000000L)return 10;
		if(num<100000000000L)return 11;
		if(num<1000000000000L)return 12;
		if(num<10000000000000L)return 13;
		if(num<100000000000000L)return 14;
		if(num<1000000000000000L)return 15;
		if(num<10000000000000000L)return 16;
		if(num<100000000000000000L)return 17;
		if(num<1000000000000000000L)return 18;
		return 19;
	}
	public static int max(int...nums){
		int ans=Integer.MIN_VALUE;
		for(int num:nums)ans=Math.max(ans,num);
		return ans;
	}
	public static long max(long...nums){
		long ans=Long.MIN_VALUE;
		for(long num:nums)ans=Math.max(ans,num);
		return ans;
	}
	public static double max(double...nums){
		double ans=-Double.MIN_VALUE;
		for(double num:nums)ans=Math.max(ans,num);
		return ans;
	}
	public static<E extends Comparable<E>>E max(E[]nums){
		E ans=nums[0];
		for(E value:nums)if(ans.compareTo(value)>0)ans=value;
		return ans;
	}
	public static int min(int...nums){
		int ans=Integer.MAX_VALUE;
		for(int num:nums)ans=Math.min(ans,num);
		return ans;
	}
	public static long min(long...nums){
		long ans=Long.MAX_VALUE;
		for(long num:nums)ans=Math.min(ans,num);
		return ans;
	}
	public static double min(double...nums){
		double ans=Double.MAX_VALUE;
		for(double num:nums)ans=Math.min(ans,num);
		return ans;
	}
	public static<E extends Comparable<E>>E min(E[]nums){
		E ans=nums[0];
		for(E value:nums)if(ans.compareTo(value)<0)ans=value;
		return ans;
	}
	public static long sum(int...nums){
		long ans=0;
		for(int num:nums)ans+=num;
		return ans;
	}
	public static long sum(long...nums){
		long ans=0;
		for(long num:nums)ans+=num;
		return ans;
	}
	public static long modSum(long mod,int...nums){
		long ans=0;
		for(int num:nums){ans+=num;ans%=mod;}
		if(ans<0)ans+=mod;
		return ans;
	}
	public static long modSum(long mod,long...nums){
		long ans=0;
		for(long num:nums){ans+=num;ans%=mod;}
		if(ans<0)ans+=mod;
		return ans;
	}
	public static long sum(int[]nums,int from,int to){
		long ans=0;
		for(int i=from;i<to;++i)ans+=nums[i];
		return ans;
	}
	public static long sum(long[]nums,int from,int to){
		long ans=0;
		for(int i=from;i<to;++i)ans+=nums[i];
		return ans;
	}
	public static long modSum(int[]nums,int from,int to,long mod){
		long ans=0;
		for(int i=from;i<to;++i){ans+=nums[i];ans%=mod;}
		if(ans<0)ans+=mod;
		return ans;
	}
	public static long modSum(long[]nums,int from,int to,long mod){
		long ans=0;
		for(int i=from;i<to;++i){ans+=nums[i];ans%=mod;}
		if(ans<0)ans+=mod;
		return ans;
	}
	public static boolean rangeCheck(int num,int l,int r){return l<=num&&num<r;}
	public static boolean rangeCheck(long num,long l,long r){return l<=num&&num<r;}
	public static boolean rangeCheck(double num,double l,double r){return l<=num&&num<r;}
	public static<E extends Comparable<E>>boolean rangeCheck(E num,E l,E r){return 0<=l.compareTo(num)&&0<num.compareTo(r);}
	public static boolean rangeCheckOpen(int num,int l,int r){return l<num&&num<r;}
	public static boolean rangeCheckOpen(long num,long l,long r){return l<num&&num<r;}
	public static boolean rangeCheckOpen(double num,double l,double r){return l<num&&num<r;}
	public static<E extends Comparable<E>>boolean rangeCheckOpen(E num,E l,E r){return 0<l.compareTo(num)&&0<num.compareTo(r);}
	public static boolean rangeCheckClose(int num,int l,int r){return l<=num&&num<=r;}
	public static boolean rangeCheckClose(long num,long l,long r){return l<=num&&num<=r;}
	public static boolean rangeCheckClose(double num,double l,double r){return l<=num&&num<=r;}
	public static<E extends Comparable<E>>boolean rangeCheckClose(E num,E l,E r){return 0<=l.compareTo(num)&&0<=num.compareTo(r);}
	public static int mex(int...nums){
		HashSet<Integer>set=new HashSet<>(nums.length<<1);
		for(int num:nums)set.add(num);
		int ans=0;
		while(set.contains(ans))++ans;
		return ans;
	}
}
class Searcher{
	private static int CYCLE_COUNT=Double.MAX_EXPONENT+53;
	public static int downSearch(int[]array,int value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int downSearch(long[]array,long value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int downSearch(double[]array,double value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int downSearch(char[]array,int value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int downSearch(E[]array,E value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c].compareTo(value)>0)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int downSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(list.get(c).compareTo(value)>0)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int downSearch(int a,int b,int value,IntUnaryOperator func){
		int ans=a-1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsInt(c)>value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static long downSearch(long a,long b,long value,LongUnaryOperator func){
		long ans=a-1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsLong(c)>value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static double search(double a,double b,double value,DoubleUnaryOperator func){
		double ans=a-Math.abs(a),c;
		for(int $=0;$<CYCLE_COUNT;++$){c=(a+b)/2;if(func.applyAsDouble(c)>value)b=c;else a=(ans=c);}
		return ans;
	}
	public static boolean contains(int[]array,int value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else if(array[c]<value)a=c+1;else return true;}
		return false;
	}
	public static boolean contains(long[]array,long value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else if(array[c]<value)a=c+1;else return true;}
		return false;
	}
	public static boolean contains(double[]array,double value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else if(array[c]<value)a=c+1;else return true;}
		return false;
	}
	public static<E extends Comparable<E>>boolean contains(E[]array,E value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;int result=array[c].compareTo(value);if(result>0)b=c-1;else if(result<0)a=c+1;else return true;}
		return false;
	}
	public static<E extends Comparable<E>>boolean contains(List<E>list,E value){
		int a=0,b=list.size()-1,c;
		while(a-b<1){c=(a+b)/2;int result=list.get(c).compareTo(value);if(result>0)b=c-1;else if(result<0)a=c+1;else return true;}
		return false;
	}
	public static boolean contains(int a,int b,int value,IntUnaryOperator func){
		int c;
		while(a-b<1){c=(a+b)/2;int num=func.applyAsInt(c);if(num>value)b=c-1;else if(num<value)a=c+1;else return true;}
		return false;
	}
	public static boolean contains(long a,long b,long value,LongUnaryOperator func){
		long c;
		while(a-b<1){c=(a+b)/2;long num=func.applyAsLong(c);if(num>value)b=c-1;else if(num<value)a=c+1;else return true;}
		return false;
	}
	public static int search(int[]array,int value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else if(array[c]<value)a=c+1;else return c;}
		return -1;
	}
	public static int search(long[]array,long value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else if(array[c]<value)a=c+1;else return c;}
		return -1;
	}
	public static int search(double[]array,double value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=c-1;else if(array[c]<value)a=c+1;else return c;}
		return -1;
	}
	public static<E extends Comparable<E>>int search(E[]array,E value){
		int a=0,b=array.length-1,c;
		while(a-b<1){c=(a+b)/2;int result=array[c].compareTo(value);if(result>0)b=c-1;else if(result<0)a=c+1;else return c;}
		return -1;
	}
	public static<E extends Comparable<E>>int search(List<E>list,E value){
		int a=0,b=list.size()-1,c;
		while(a-b<1){c=(a+b)/2;int result=list.get(c).compareTo(value);if(result>0)b=c-1;else if(result<0)a=c+1;else return c;}
		return -1;
	}
	public static int search(int a,int b,int value,IntUnaryOperator func){
		int c;
		while(a-b<1){c=(a+b)/2;int num=func.applyAsInt(c);if(num>value)b=c-1;else if(num<value)a=c+1;else return c;}
		return a - 1;
	}
	public static long search(long a,long b,long value,LongUnaryOperator func){
		long c;
		while(a-b<1){c=(a+b)/2;long num=func.applyAsLong(c);if(num>value)b=c-1;else if(num<value)a=c+1;else return c;}
		return a-1;
	}
	public static int upSearch(int[]array,int value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>=value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int upSearch(long[]array,long value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>=value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int upSearch(double[]array,double value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>=value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int upSearch(E[]array,E value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c].compareTo(value)>=0)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int upSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=list.size(),c;
		while(a-b<1){c=(a+b)/2;if(list.get(c).compareTo(value)>=0)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int upSearch(int a,int b,int value,IntUnaryOperator func){
		int ans=b+1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsInt(c)>=value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static long upSearch(long a,long b,long value,LongUnaryOperator func){
		long ans=b+1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsLong(c)>=value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int underSearch(int[]array,int value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>=value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int underSearch(long[]array,long value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>=value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int underSearch(double[]array,double value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>=value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int underSearch(E[]array,E value){
		int a=0,b=array.length-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(array[c].compareTo(value)>=0)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int underSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=-1,c;
		while(a-b<1){c=(a+b)/2;if(list.get(c).compareTo(value)>=0)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int underSearch(int a,int b,int value,IntUnaryOperator func){
		int ans=a-1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsInt(c)>=value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static long underSearch(long a,long b,long value,LongUnaryOperator func){
		long ans=a-1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsLong(c)>=value)b=c-1;else a=(ans=c)+1;}
		return ans;
	}
	public static int overSearch(int[]array,int value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int overSearch(long[]array,long value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int overSearch(double[]array,double value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c]>value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int overSearch(E[]array,E value){
		int a=0,b=array.length-1,ans=array.length,c;
		while(a-b<1){c=(a+b)/2;if(array[c].compareTo(value)>0)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static<E extends Comparable<E>>int overSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=list.size(),c;
		while(a-b<1){c=(a+b)/2;if(list.get(c).compareTo(value)>0)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static int overSearch(int a,int b,int value,IntUnaryOperator func){
		int ans=b+1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsInt(c)>value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
	public static long overSearch(long a,long b,long value,LongUnaryOperator func){
		long ans=b+1,c;
		while(a-b<1){c=(a+b)/2;if(func.applyAsLong(c)>value)b=(ans=c)-1;else a=c+1;}
		return ans;
	}
}
class BIT{
	int size;
	private long[]tree;
	public BIT(int n){size=n;tree=new long[n+1];}
	public long sum(int i){long sum=0;while(i>0){sum+=tree[i];i^=i&(-i);}return sum;}
	public void add(int i,long x){while(i<=size){tree[i]+=x;i+=i&(-i);}}
	public void clear(){Arrays.fill(tree,0L);}
}
class Bitset implements Cloneable{
	private long[]bit;
	private int size,len;
	private long MASK;
	public Bitset(int len){
		this.len=len;
		size=(len+63)>>6;
		bit=new long[size];
		MASK=(-1L)>>>((size<<6)-len);
	}
	private Bitset(long[]arr){this(arr.length);System.arraycopy(arr,0,bit,0,size);}
	public void set(int index){
		if(index>=size<<6)throw new ArrayIndexOutOfBoundsException(index+" is out of this bitset's size "+size);
		bit[index>>6]|=(1L<<(index&0b111111));
	}
	public void clear(int index){
		if(index>=size<<6)throw new ArrayIndexOutOfBoundsException(index+" is out of this bitset's size "+size);
		bit[index>>6]&=~(1L<<(index&0b111111));
	}
	public boolean get(int index){
		if(index>=size<<6)throw new ArrayIndexOutOfBoundsException(index+" is out of this bitset's size "+size);
		return (bit[index>>6]&(1L<<(index&0b111111)))!=0;
	}
	public void shiftLeft(int num){
		if(num>=size<<6){Arrays.fill(bit,0L);return;}
		int n=num>>6;
		num&=0b111111;
		for(int i=size-1;i>=n;--i)bit[i]=(bit[i-n]<<num)|(i!=n&&num!=0?bit[i-n-1]>>>(64-num):0L);
		for(int i=0;i<n;++i)bit[i]=0L;
		bit[size-1]&=MASK;
	}
	public void shiftRight(int num){
		if(num>=size<<6){Arrays.fill(bit,0L);return;}
		int n=num>>6;
		num&=0b111111;
		for(int i=0;i<size-n;++i)bit[i]=(bit[i+n]>>>num)|(i+n+1!=size&&num!=0?bit[i+n+1]<<(64-num):0L);
		for(int i=size-1;i>=size-n;--i)bit[i]=0L;
	}
	public long[]longValues(){return bit;}
	public long longValue(){return bit[0];}
	public void and(Bitset b){
		long[]bit2=b.longValues();
		int m=Math.min(bit2.length,size);
		for(int i=0;i<m;++i)bit[i]&=bit2[i];
		for(int i=m;i<size;++i)bit[i]=0;
		bit[size-1]&=MASK;
	}
	public void or(Bitset b){
		long[]bit2=b.longValues();
		int m=Math.min(bit2.length,size);
		for(int i=0;i<m;++i)bit[i]|=bit2[i];
		bit[size-1]&=MASK;
	}
	public void xor(Bitset b){
		long[]bit2=b.longValues();
		int m=Math.min(bit2.length,size);
		for(int i=0;i<m;++i)bit[i]^=bit2[i];
		bit[size-1]&=MASK;
	}
	public Bitset clone()throws CloneNotSupportedException{super.clone();Bitset b=new Bitset(bit);return b;}
}
class RollingHash implements Comparable<RollingHash> {
	private static long BASE = new Random().nextInt( 1000 ) + Character.MAX_VALUE + 1;
	private static long MASK30 = ( 1L << 30 ) - 1;
	private static long MASK31 = ( 1L << 31 ) - 1;
	private static long MOD = ( 1L << 61 ) - 1;
	private static long MASK61 = MOD;
	private long[] hash;
	private String string;
	public RollingHash ( String str ) {
		string = str;
		hash = new long[str.length() + 1];
		roll();
	}
	private void roll () {
		int len = string.length();
		for ( int i = 1; i <= len; ++i ) {
			hash[i] = multiply( hash[i - 1], BASE ) + string.charAt( i - 1 ) - ' ' + 1;
			if ( MOD <= hash[i] ) {
				hash[i] -= MOD;
			}
		}
	}
	private static long multiply ( long a, long b ) {
		long au = a >> 31;
		long ad = a & MASK31;
		long bu = b >> 31;
		long bd = b & MASK31;
		long mid = ad * bu + au * bd;
		long midu = mid >> 30;
		long midd = mid & MASK30;
		return mod( au * bu * 2 + midu + ( midd << 31 ) + ad * bd );
	}
	private static long mod ( long x ) {
		long xu = x >> 61;
		long xd = x & MASK61;
		long ans = xu + xd;
		if ( MOD <= ans ) {
			ans -= MOD;
		}
		return ans;
	}
	public long getHash ( int l, int r ) {
		return ( hash[r] - multiply( hash[l], modBasePow( r - l ) ) + MOD ) % MOD;
	}
	private static long modBasePow ( long b ) {
		long ans = 1;
		long a = BASE;
		while ( b > 0 ) {
			if ( ( b & 1 ) == 1 ) {
				ans = multiply( ans, a );
			}
			a = multiply( a, a );
			b >>= 1;
		}
		return ans;
	}
	public boolean equals ( RollingHash rh, int l1, int r1, int l2, int r2 ) {
		if ( r1 - l1 != r2 - l2 ) {
			return false;
		}
		return getHash( l1, r1 ) == rh.getHash( l2, r2 );
	}
	public int length () {
		return string.length();
	}
	@Override
	public int hashCode () {
		return string.hashCode();
	}
	@Override
	public String toString () {
		return string;
	}
	@Override
	public boolean equals ( Object o ) {
		if ( o instanceof RollingHash rh ) {
			return equals( rh, 0, length(), 0, rh.length() );
		}
		return false;
	}
	@Override
	public int compareTo ( RollingHash rh ) {
		return string.compareTo( rh.toString() );
	}
	public int compareTo ( String str ) {
		return string.compareTo( str );
	}
	public char charAt ( int i ) {
		return string.charAt( i );
	}
	public int compareToIgnoreCase ( RollingHash rh ) {
		return string.compareToIgnoreCase( rh.toString() );
	}
	public int compareToIgnoreCase ( String str ) {
		return string.compareToIgnoreCase( str );
	}
	public void concat ( RollingHash rh ) {
		concat( rh.toString() );
	}
	public void concat ( String str ) {
		string = string.concat( str );
		hash = new long[string.length() + 1];
		roll();
	}
	public boolean contains ( RollingHash rh ) {
		long hash = rh.getHash( 0, rh.length() );
		int len = length() - rh.length();
		for ( int i = 0; i <= len; ++i ) {
			if ( hash == getHash( i, rh.length() + i ) ) {
				return true;
			}
		}
		return false;
	}
	public boolean contains ( String str ) {
		return indexOf( str ) != -1;
	}
	public int indexOf ( int ch ) {
		return indexOf( ch, 0 );
	}
	public int indexOf ( int ch, int fromIndex ) {
		int len = length();
		for ( int i = fromIndex; i < len; ++i ) {
			if ( string.charAt( i ) == ch ) {
				return i;
			}
		}
		return -1;
	}
	public int indexOf ( String str ) {
		return indexOf( str, 0 );
	}
	public int indexOf ( String str, int fromIndex ) {
		long hash = 0;
		for ( char c: str.toCharArray() ) {
			hash = multiply( hash, BASE ) + c - ' ' + 1;
			if ( MOD <= hash ) {
				hash -= MOD;
			}
		}
		int len = length() - str.length();
		for ( int i = fromIndex; i <= len; ++i ) {
			if ( hash == getHash( i, str.length() + i ) ) {
				return i;
			}
		}
		return -1;
	}
	public boolean isEmpty () {
		return length() == 0;
	}
	public int lastIndexOf ( int ch, int fromIndex ) {
		for ( int i = fromIndex; i >= 0; --i ) {
			if ( string.charAt( i ) == ch ) {
				return i;
			}
		}
		return -1;
	}
	public int lastIndexOf ( int ch ) {
		return lastIndexOf( ch, length() - 1 );
	}
	public static RollingHash valueOf ( boolean b ) {
		return new RollingHash( b ? "true" : "false" );
	}
	public static RollingHash valueOf ( char c ) {
		return new RollingHash( "" + c );
	}
	public static RollingHash valueOf ( char[] c ) {
		return new RollingHash( String.valueOf( c, 0, c.length ) );
	}
	public static RollingHash valueOf ( char[] c, int offset, int count ) {
		return new RollingHash( String.valueOf( c, offset, count ) );
	}
	public static RollingHash valueOf ( double d ) {
		return new RollingHash( String.valueOf( d ) );
	}
	public static RollingHash valueOf ( float f ) {
		return new RollingHash( String.valueOf( f ) );
	}
	public static RollingHash valueOf ( int i ) {
		return new RollingHash( String.valueOf( i ) );
	}
	public static RollingHash valueOf ( long l ) {
		return new RollingHash( String.valueOf( l ) );
	}
	public static RollingHash valueOf ( Object obj ) {
		return new RollingHash( String.valueOf( obj ) );
	}
}
abstract class SegmentTree<E> {
	int N, size;
	E def;
	Object[] node;
	public SegmentTree ( int n, E def, boolean include ) {
		int num = 2;
		while ( num < n << 1 ) {
			num <<= 1;
		}
		N = num;
		size = num >> 1 - ( include ? 1 : 0 );
		node = new Object[N];
		this.def = def;
		Arrays.fill( node, this.def );
	}
	public SegmentTree ( E[] arr, E def, boolean include ) {
		int num = 2;
		while ( num < arr.length << 1 ) {
			num <<= 1;
		}
		N = num;
		size = num >> 1 - ( include ? 1 : 0 );
		node = new Object[N];
		this.def = def;
		System.arraycopy( arr, 0, node, N >> 1, arr.length );
		for ( int i = arr.length + ( N >> 1 ); i < N; i++ ) {
			node[i] = def;
		}
		updateAll();
	}
	public SegmentTree ( int n, E def ) {
		this( n, def, false );
	}
	@SuppressWarnings( "unchecked" )
	private void updateAll () {
		for ( int i = ( N >> 1 ) - 1; i > 0; i-- ) {
			node[i] = function( ( E )node[i << 1], ( E )node[( i << 1 ) + 1] );
		}
	}
	@SuppressWarnings( "unchecked" )
	public void update ( int n, E value ) {
		n += size;
		node[n] = value;
		n >>= 1;
		while ( n > 0 ) {
			node[n] = function( ( E )node[n << 1], ( E )node[( n << 1 ) + 1] );
			n >>= 1;
		}
	}
	@SuppressWarnings( "unchecked" )
	public E get ( int a ) {
		return ( E )node[a + size];
	}
	@SuppressWarnings( "unchecked" )
	public E answer () {
		return ( E )node[1];
	}
	@SuppressWarnings( "unchecked" )
	public E query ( int l, int r ) {
		l += size;
		r += size;
		E answer = def;
		while ( l > 0 && r > 0 && l <= r ) {
			if ( l % 2 == 1 ) {
				answer = function( ( E )node[l++], answer );
			}
			l >>= 1;
			if ( r % 2 == 0 ) {
				answer = function( answer, ( E )node[r--] );
			}
			r >>= 1;
		}
		return answer;
	}
	abstract public E function ( E a, E b );
}
class UnionFind {
	private int[] par, rank, size, path;
	private int count;
	public UnionFind ( int N ) {
		count = N;
		par = new int[N];
		rank = new int[N];
		size = new int[N];
		path = new int[N];
		Arrays.fill( par, -1 );
		Arrays.fill( size, 1 );
	}
	public int root ( int ind ) {
		if ( par[ind] == -1 ) {
			return ind;
		}
		else {
			return par[ind] = root( par[ind] );
		}
	}
	public boolean isSame ( int x, int y ) {
		return root( x ) == root( y );
	}
	public boolean unite ( int x, int y ) {
		int rx = root( x );
		int ry = root( y );
		++path[rx];
		if ( rx == ry ) {
			return false;
		}
		if ( rank[rx] < rank[ry] ) {
			int temp = rx;
			rx = ry;
			ry = temp;
		}
		par[ry] = rx;
		if ( rank[rx] == rank[ry] ) {
			++rank[rx];
		}
		path[rx] += path[ry];
		size[rx] += size[ry];
		--count;
		return true;
	}
	public int groupCount () {
		return count;
	}
	public int pathCount ( int x ) {
		return path[root( x )];
	}
	public int size ( int x ) {
		return size[root( x )];
	}
}
class SimpleScanner {
	private int BUFF_SIZE = 1 << 17;
	private InputStream is;
	private byte[] buff;
	private int point, length;
	public SimpleScanner ( InputStream is ) {
		this.is = is;
		buff = new byte[BUFF_SIZE];
		point = length = 0;
	}
	private void reload () {
		do {
			try {
				length = is.read( buff, point = 0, BUFF_SIZE );
			} catch ( IOException e ) {
				e.printStackTrace();
				System.exit( 1 );
			}
		} while ( length == -1 );
	}
	private byte read () {
		if ( point == length ) {
			reload();
		}
		return buff[point++];
	}
	public byte nextByte () {
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		return c;
	}
	public int nextInt () {
		int ans = 0;
		byte c = nextByte();
		boolean negate = c == '-';
		if ( !MathFunction.rangeCheckClose( c, '0', '9' ) ) {
			c = read();
		}
		while ( MathFunction.rangeCheckClose( c, '0', '9' ) ) {
			ans = ans * 10 + c - '0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public long nextLong () {
		long ans = 0;
		byte c = nextByte();
		boolean negate = c == '-';
		if ( !MathFunction.rangeCheckClose( c, '0', '9' ) ) {
			c = read();
		}
		while ( MathFunction.rangeCheckClose( c, '0', '9' ) ) {
			ans = ans * 10 + c - '0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public char nextChar () {
		return ( char )nextByte();
	}
	public String next () {
		StringBuilder ans = new StringBuilder();
		byte c = nextByte();
		while ( c > ' ' ) {
			ans.append( ( char )c );
			c = read();
		}
		return ans.toString();
	}
	public byte[] nextByte ( int n ) {
		byte[] ans = new byte[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextByte();
		}
		return ans;
	}
	public int[] nextInt ( int n ) {
		int[] ans = new int[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextInt();
		}
		return ans;
	}
	public long[] nextLong ( int n ) {
		long[] ans = new long[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextLong();
		}
		return ans;
	}
	public String[] next ( int n ) {
		String[] ans = new String[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = next();
		}
		return ans;
	}
	public byte[][] nextByte ( int n, int m ) {
		byte[][] ans = new byte[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextByte( m );
		}
		return ans;
	}
	public int[][] nextInt ( int n, int m ) {
		int[][] ans = new int[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextInt( m );
		}
		return ans;
	}
	public long[][] nextLong ( int n, int m ) {
		long[][] ans = new long[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextLong( m );
		}
		return ans;
	}
	public String[][] next ( int n, int m ) {
		String[][] ans = new String[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = next( m );
		}
		return ans;
	}
	public char[] nextCharArray () {
		return next().toCharArray();
	}
	public char[][] nextCharArray ( int n ) {
		char[][] ans = new char[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextCharArray();
		}
		return ans;
	}
	public int[][] nextGraph ( int N, int M ) {
		if ( M == 0 ) {
			return new int[N + 1][0];
		}
		int[][] ans = new int[N + 1][];
		int[] count = new int[N + 1];
		int[][] path = nextInt( M, 2 );
		for ( int[] temp: path ) {
			++count[temp[0]];
			++count[temp[1]];
		}
		for ( int i = 1; i <= N; ++i ) {
			ans[i] = new int[count[i]];
		}
		for ( int[] temp: path ) {
			ans[temp[0]][--count[temp[0]]] = temp[1];
			ans[temp[1]][--count[temp[1]]] = temp[0];
		}
		ans[0] = new int[0];
		return ans;
	}
	public Point nextPoint () {
		return new Point( nextInt(), nextInt() );
	}
	public Point[] nextPoint ( int n ) {
		Point[] ans = new Point[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextPoint();
		}
		return ans;
	}
	public void close () {
		try {
			is.close();
		} catch ( IOException e ) {
			e.printStackTrace();
			System.exit( 1 );
		}
	}
}
class SimpleOutputStream extends FilterOutputStream {
	private byte buf[];
	private int count;
	public SimpleOutputStream(OutputStream out) {
		this(out, 1<<17);
	}
	public SimpleOutputStream(OutputStream out, int size) {
		super(out);
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		buf = new byte[size];
	}
	private void flushBuffer() throws IOException {
		if (count > 0) {
			out.write(buf, 0, count);
			count = 0;
		}
	}
	public void write(int b) throws IOException {
		if (count >= buf.length) {
			flushBuffer();
		}
		buf[count++] = (byte)b;
	}
	public void write(byte b[], int off, int len) throws IOException {
		if (len >= buf.length) {
			flushBuffer();
			out.write(b, off, len);
			return;
		}
		if (len > buf.length - count) {
			flushBuffer();
		}
		System.arraycopy(b, off, buf, count, len);
		count += len;
	}
	public void flush() throws IOException {
		flushBuffer();
		out.flush();
	}
}
class SimpleWriter implements Appendable, Closeable, Flushable, AutoCloseable{
	private Writer out;
	private boolean autoFlush;
	private boolean trouble = false;
	private Formatter formatter;
	private PrintStream psOut = null;
	private static Charset toCharset ( String csn ) {
		if ( csn == null ) {
			throw new NullPointerException( "charsetName" );
		}
		try {
			return Charset.forName( csn );
		} catch ( IllegalCharsetNameException |
				  UnsupportedCharsetException e ) {
			e.printStackTrace();
			System.exit( 1 );
			return null;
		}
	}
	public SimpleWriter ( Writer out ) {
		this( out, false );
	}
	public SimpleWriter ( Writer out, boolean autoFlush ) {
		this.out = out;
		this.autoFlush = autoFlush;
	}
	public SimpleWriter ( OutputStream out ) {
		this( out, false );
	}
	public SimpleWriter ( OutputStream out, boolean autoFlush ) {
		this(out, autoFlush, Charset.defaultCharset());
	}
	public SimpleWriter(OutputStream out, boolean autoFlush, Charset charset) {
		this(new BufferedWriter(new OutputStreamWriter(new SimpleOutputStream(out), charset)), autoFlush);
		if (out instanceof PrintStream) {
			psOut = (PrintStream) out;
		}
	}
	private void ensureOpen () throws IOException {
		if ( out == null ) {
			throw new IOException( "Stream closed" );
		}
	}
	public void flush () {
		try {
			ensureOpen();
			out.flush();
		} catch ( IOException x ) {
			trouble = true;
		}
	}
	public void close () {
		try {
			if ( out == null ) {
				return;
			}
			out.close();
			out = null;
		} catch ( IOException x ) {
			trouble = true;
		}
	}
	public boolean checkError () {
		if ( out != null ) {
			flush();
		}
		else if ( psOut != null ) {
			return psOut.checkError();
		}
		return trouble;
	}
	private void setError () {
		trouble = true;
	}
	private void clearError () {
		trouble = false;
	}
	public void write ( int c ) {
		try {
			ensureOpen();
			out.write( c );
		} catch ( InterruptedIOException x ) {
			Thread.currentThread().interrupt();
		} catch ( IOException x ) {
			trouble = true;
		}
	}
	public void write ( char[] buf, int off, int len ) {
		try {
			ensureOpen();
			out.write( buf, off, len );
		} catch ( InterruptedIOException x ) {
			Thread.currentThread().interrupt();
		} catch ( IOException x ) {
			trouble = true;
		}
	}
	public void write ( char[] buf ) {
		write( buf, 0, buf.length );
	}
	public void write ( String s, int off, int len ) {
		try {
			ensureOpen();
			out.write( s, off, len );
		} catch ( InterruptedIOException x ) {
			Thread.currentThread().interrupt();
		} catch ( IOException x ) {
			trouble = true;
		}
	}
	public void write ( String s ) {
		write( s, 0, s.length() );
	}
	private void newLine () {
		try {
			ensureOpen();
			out.write( System.lineSeparator() );
			if ( autoFlush ) {
				out.flush();
			}
		} catch ( InterruptedIOException x ) {
			Thread.currentThread().interrupt();
		} catch ( IOException x ) {
			trouble = true;
		}
	}
	public void print ( boolean b ) {
		write( b ? "true" : "false" );
	}
	public void print ( char c ) {
		write( c );
	}
	public void print ( int i ) {
		write( String.valueOf( i ) );
	}
	public void print ( long l ) {
		write( String.valueOf( l ) );
	}
	public void print ( float f ) {
		write( String.valueOf( f ) );
	}
	public void print ( double d ) {
		write( String.valueOf( d ) );
	}
	public void print ( char[] s ) {
		write( s );
	}
	public void print ( String s ) {
		write( s );
	}
	public void print ( Object obj ) {
		write( obj.toString() );
	}
	public void println () {
		newLine();
	}
	public void println ( boolean x ) {
		print( x );
		println();
	}
	public void println ( char x ) {
		print( x );
		println();
	}
	public void println ( int x ) {
		print( x );
		println();
	}
	public void println ( long x ) {
		print( x );
		println();
	}
	public void println ( float x ) {
		print( x );
		println();
	}
	public void println ( double x ) {
		print( x );
		println();
	}
	public void println ( char[] x ) {
		print( x );
		println();
	}
	public void println ( String x ) {
		print( x );
		println();
	}
	public void println ( Object x ) {
		print( x.toString() );
		println();
	}
	public SimpleWriter printf ( String format, Object... args ) {
		return format( format, args );
	}
	public SimpleWriter printf ( Locale l, String format, Object... args ) {
		return format( l, format, args );
	}
	public SimpleWriter format ( String format, Object... args ) {
		try {
			ensureOpen();
			if ( ( formatter == null )
				 || ( formatter.locale() != Locale.getDefault() ) ) {
				formatter = new Formatter( this );
			}
			formatter.format( Locale.getDefault(), format, args );
			if ( autoFlush ) {
				out.flush();
			}
		} catch ( InterruptedIOException x ) {
			Thread.currentThread().interrupt();
		} catch ( IOException x ) {
			trouble = true;
		}
		return this;
	}
	public SimpleWriter format ( Locale l, String format, Object... args ) {
		try {
			ensureOpen();
			if ( ( formatter == null ) || ( formatter.locale() != l ) ) {
				formatter = new Formatter( this, l );
			}
			formatter.format( l, format, args );
			if ( autoFlush ) {
				out.flush();
			}
		} catch ( InterruptedIOException x ) {
			Thread.currentThread().interrupt();
		} catch ( IOException x ) {
			trouble = true;
		}
		return this;
	}
	public SimpleWriter append ( CharSequence csq ) {
		write( String.valueOf( csq ) );
		return this;
	}
	public SimpleWriter append ( CharSequence csq, int start, int end ) {
		if ( csq == null ) {
			csq = "null";
		}
		return append( csq.subSequence( start, end ) );
	}
	public SimpleWriter append ( char c ) {
		write( c );
		return this;
	}
	public void println ( int[] array ) {
		println( array, ' ' );
	}
	public void println ( int[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( str );
			print( array[i] );
		}
		println();
	}
	public void println ( int[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( c );
			print( array[i] );
		}
		println();
	}
	public void println ( int[][] array ) {
		println( array, ' ' );
	}
	public void println ( int[][] arrays, String str ) {
		for ( int[] array: arrays ) {
			println( array, str );
		}
	}
	public void println ( int[][] arrays, char c ) {
		for ( int[] array: arrays ) {
			println( array, c );
		}
	}
	public void println ( long[] array ) {
		println( array, ' ' );
	}
	public void println ( long[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( str );
			print( array[i] );
		}
		println();
	}
	public void println ( long[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( c );
			print( array[i] );
		}
		println();
	}
	public void println ( long[][] array ) {
		println( array, ' ' );
	}
	public void println ( long[][] arrays, String str ) {
		for ( long[] array: arrays ) {
			println( array, str );
		}
	}
	public void println ( long[][] arrays, char c ) {
		for ( long[] array: arrays ) {
			println( array, c );
		}
	}
	public void println ( double[] array ) {
		println( array, ' ' );
	}
	public void println ( double[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( str );
			print( array[i] );
		}
		println();
	}
	public void println ( double[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( c );
			print( array[i] );
		}
		println();
	}
	public void println ( double[][] array ) {
		println( array, ' ' );
	}
	public void println ( double[][] arrays, String str ) {
		for ( double[] array: arrays ) {
			println( array, str );
		}
	}
	public void println(double[][]arrays,char c){for(double[]array:arrays)println(array,c);}
	public void println(char[]cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){print(str);print(cs[i]);}
		println();
	}
	public void println(char[]cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){print(c);print(cs[i]);}
		println();
	}
	public void println(char[][]cs){for(char[]c:cs)println(c);}
	public void println(char[][]cs,String str){for(char[]c:cs)println(c,str);}
	public void println(char[][]cs,char c){for(char[]cc:cs)println(cc,c);}
	public<E>void println(E[]array){println(array,' ');}
	public<E>void println(E[]array,String str){
		print(array[0]);
		for(int i=1;i<array.length;++i){print(str);print(array[i]);}
		println();
	}
	public<E>void println(E[]array,char c){
		print(array[0]);
		for(int i=1;i<array.length;++i){print(c);print(array[i]);}
		println();
	}
	public<E>void println(E[][]arrays){println(arrays,' ');}
	public<E>void println(E[][]arrays,String str){for(E[]array:arrays)println(array,str);}
	public<E>void println(E[][]arrays,char c){for(E[]array:arrays)println(array,c);}
}
