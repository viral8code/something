import java.io.*;
import java.math.*;
import java.awt.Point;
import java.awt.Dimension;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
class Main{
	public static void main(String[]args){
		var sc = new SScanner(System.in);var out = new SPrinter(System.out,false);
		int H = sc.nextInt();
		int W = sc.nextInt();
		int N = sc.nextInt();
		HashSet<Point> set = new HashSet<>();
		HashSet<Integer> DR = new HashSet<>();
		HashSet<Integer> DL = new HashSet<>();
		Point[] p = sc.nextPoint(N);
		for(int i=0;i<N;i++){
			set.add(p[i]);
			DR.add(p[i].y-p[i].x);
			DL.add(p[i].x-H+p[i].y);
		}
		int ans = 0;
		for(Point point:p){
			if(!set.contains(new Point(point.x,point.y+2)))
				continue;
			Point keima = new Point(point.x+2,point.y+1);
			if(keima.x>H||keima.y>W)
				continue;
			if(DR.contains(keima.y-keima.x)||DL.contains(keima.x-H+keima.y))
				continue;
			ans++;
		}
		out.println(ans);
		out.close();
	}
}
class Factorial{
	long[]fact,inFact;long mod;
	Factorial(int N,long m){
		fact=new long[N+1];fact[0]=fact[1]=1;
		for(int i=2;i<=N;++i)fact[i]=fact[i-1]*i%m;
		inFact=new long[N+1];inFact[N]=MathFunction.modPow(fact[N],m-2,m);inFact[0]=1;
		for(int i=N;i>0;--i)inFact[i-1]=inFact[i]*i%m;
		mod=m;
	}
	long getFact(int num){return fact[num];}
	long getCombi(int a,int b){
		if(a<b||a<0||b<0)return 0;
		return (fact[a]*inFact[a-b]%mod)*inFact[b]%mod;
	}
}
class ArrayFunction{
	static void sort(int[][]ars){
		for(int i=0;i<ars.length;++i){
			int j=i;
			while(j>0&&compare(ars[(j-1)/2],ars[j])<0){
				var temp=ars[(j-1)/2];
				ars[(j-1)/2]=ars[j];
				ars[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=ars.length;i>0;--i){
			var temp=ars[i-1];
			ars[i-1]=ars[0];
			ars[0]=temp;
			int j=0;
			while(2*j+1<i-1&&compare(ars[j],ars[2*j+1])<0||2*j+2<i-1&&compare(ars[j],ars[2*j+2])<0){
				if(2*j+2==i-1||compare(ars[2*j+1],ars[2*j+2])>0){
					temp=ars[2*j+1];
					ars[2*j+1]=ars[j];
					ars[j]=temp;
					j<<=1;
					++j;
				}else{
					temp=ars[2*j+2];
					ars[2*j+2]=ars[j];
					ars[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	static void sort(long[][]ars){
		for(int i=0;i<ars.length;++i){
			int j=i;
			while(j>0&&compare(ars[(j-1)/2],ars[j])<0){
				var temp=ars[(j-1)/2];
				ars[(j-1)/2]=ars[j];
				ars[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=ars.length;i>0;--i){
			var temp=ars[i-1];
			ars[i-1]=ars[0];
			ars[0]=temp;
			int j=0;
			while(2*j+1<i-1&&compare(ars[j],ars[2*j+1])<0||2*j+2<i-1&&compare(ars[j],ars[2*j+2])<0){
				if(2*j+2==i-1||compare(ars[2*j+1],ars[2*j+2])>0){
					temp=ars[2*j+1];
					ars[2*j+1]=ars[j];
					ars[j]=temp;
					j<<=1;
					++j;
				}else{
					temp=ars[2*j+2];
					ars[2*j+2]=ars[j];
					ars[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	static <E extends Comparable<E>> void sort(E[][]ars){
		for(int i=0;i<ars.length;++i){
			int j=i;
			while(j>0&&compare(ars[(j-1)/2],ars[j])<0){
				E[]temp=ars[(j-1)/2];
				ars[(j-1)/2]=ars[j];
				ars[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=ars.length;i>0;--i){
			E[]temp=ars[i-1];
			ars[i-1]=ars[0];
			ars[0]=temp;
			int j=0;
			while(2*j+1<i-1&&compare(ars[j],ars[2*j+1])<0||2*j+2<i-1&&compare(ars[j],ars[2*j+2])<0){
				if(2*j+2==i-1||compare(ars[2*j+1],ars[2*j+2])>0){
					temp=ars[2*j+1];
					ars[2*j+1]=ars[j];
					ars[j]=temp;
					j<<=1;
					++j;
				}else{
					temp=ars[2*j+2];
					ars[2*j+2]=ars[j];
					ars[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	static int compare(int[]a,int[]b){
		int len=Math.min(a.length,b.length);
		for(int i=0;i<len;++i){
			if(a[i]>b[i])return 1;
			if(a[i]<b[i])return -1;
		}
		return Integer.compare(a.length,b.length);
	}
	static int compare(long[]a,long[]b){
		int len=Math.min(a.length,b.length);
		for(int i=0;i<len;++i){
			if(a[i]>b[i])return 1;
			if(a[i]<b[i])return -1;
		}
		return Integer.compare(a.length,b.length);
	}
	static int compare(double[]a,double[]b){
		int len=Math.min(a.length,b.length);
		for(int i=0;i<len;++i){
			if(a[i]>b[i])return 1;
			if(a[i]<b[i])return -1;
		}
		return Integer.compare(a.length,b.length);
	}
	static<E extends Comparable<E>>int compare(E[]a,E[]b){
		int len=Math.min(a.length,b.length);
		for(int i=0;i<len;++i){
			int result=a[i].compareTo(b[i]);
			if(result!=0)return result;
		}
		return Integer.compare(a.length,b.length);
	}
	static int[][]topologicalSort(ArrayList<ArrayList<Integer>>route){
		var count=new int[route.size()];
		int pathCount=0;
		for(var path:route)for(int point:path){
			++pathCount;
			++count[point];
		}
		var deq=new ArrayDeque<Integer>();
		for(int i=1;i<count.length;++i)if(count[i]==0)deq.add(i);
		var ans=new int[pathCount][2];
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
	static int[][]topologicalSort(int[][]route){
		var count=new int[route.length];
		int pathCount=0;
		for(var path:route)for(int point:path){
			++pathCount;
			++count[point];
		}
		var deq=new ArrayDeque<Integer>();
		for(int i=1;i<count.length;++i)if(count[i]==0)deq.add(i);
		var ans=new int[pathCount][2];
		int index=0;
		while(deq.size()>0){
			int nowP=deq.pollFirst();
			for(int nextP:route[nowP]){
				ans[index][0]=nowP;
				ans[index++][1]=nextP;
				if(--count[nextP]==0)deq.add(nextP);
			}
		}
		return ans;
	}
}
class Searcher{
	static int downSearch(int[]ar,int value){
		int a=0,b=ar.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>value)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static int downSearch(long[]ar,long value){
		int a=0,b=ar.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>value)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int downSearch(E[]ar,E value){
		int a=0,b=ar.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c].compareTo(value)>0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int downSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(list.get(c).compareTo(value)>0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static boolean search(int[]ar,int value){
		int a=0,b=ar.length-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>value)b=c-1;
			else if(ar[c]<value)a=c+1;
			else return true;
		}
		return false;
	}
	static boolean search(long[]ar,long value){
		int a=0,b=ar.length-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>value)b=c-1;
			else if(ar[c]<value)a=c+1;
			else return true;
		}
		return false;
	}
	static<E extends Comparable<E>>boolean search(E[]ar,E value){
		int a=0,b=ar.length-1,c;
		while(a-b<1){
			c=(a+b)/2;
			int result=ar[c].compareTo(value);
			if(result>0)b=c-1;
			else if(result<0)a=c+1;
			else return true;
		}
		return false;
	}
	static int upSearch(int[]ar,int value){
		int a=0,b=ar.length-1,ans=ar.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>=value)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static int upSearch(long[]ar,long value){
		int a=0,b=ar.length-1,ans=ar.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>=value)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int upSearch(E[]ar,E value){
		int a=0,b=ar.length-1,ans=ar.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c].compareTo(value)>=0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int upSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=list.size(),c;
		while(a-b<1){
			c=(a+b)/2;
			if(list.get(c).compareTo(value)>=0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static int underSearch(int[]ar,int value){
		int a=0,b=ar.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>=value)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static int underSearch(long[]ar,long value){
		int a=0,b=ar.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>=value)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int underSearch(E[]ar,E value){
		int a=0,b=ar.length-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c].compareTo(value)>=0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int underSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=-1,c;
		while(a-b<1){
			c=(a+b)/2;
			if(list.get(c).compareTo(value)>=0)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	static int overSearch(int[]ar,int value){
		int a=0,b=ar.length-1,ans=ar.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>value)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static int overSearch(long[]ar,long value){
		int a=0,b=ar.length-1,ans=ar.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c]>value)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int overSearch(E[]ar,E value){
		int a=0,b=ar.length-1,ans=ar.length,c;
		while(a-b<1){
			c=(a+b)/2;
			if(ar[c].compareTo(value)>0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	static<E extends Comparable<E>>int overSearch(List<E>list,E value){
		int a=0,b=list.size()-1,ans=list.size(),c;
		while(a-b<1){
			c=(a+b)/2;
			if(list.get(c).compareTo(value)>0)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
}
class MathFunction{
	static long gcd(long a,long b){
		a=Math.abs(a);
		b=Math.abs(b);
		if(b==0)return a;
		long temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return b;
	}
	static long lcm(long a,long b){return a/gcd(a,b)*b;}
	static boolean isPrime(long num){return BigInteger.valueOf(num).isProbablePrime(20);}
	static int[]primes(int num){
		BitSet numbers=new BitSet(num+1);
		numbers.set(2,num+1);
		for(int i=2;i<=Math.sqrt(num);++i){
			if(numbers.get(i)){
				for(int j=i*i;j<=num;j+=i){
					numbers.clear(j);
				}
			}
		}
		int[]answer=new int[numbers.cardinality()];
		int i=2,index=0;
		do{
			i=numbers.nextSetBit(i);
			answer[index++]=i++;
		}while(index!=answer.length);
		return answer;
	}
	static long pow(long a,long b){
		long ans=1;
		while(b>0){
			if((b&1)==1)ans*=a;
			a*=a;
			b>>=1;
		}
		return ans;
	}
	static long modPow(long a,long b,long mod){
		long ans=1;
		a%=mod;
		while(b>0){
			if((b&1)==1)ans=ans*a%mod;
			a=a*a%mod;
			b>>=1;
		}
		return ans;
	}
	static long combi(long n,long r){
		if(r<0||n<r)return 0;
		long ans=1;
		r=Math.min(n-r,r);
		for(int i=0;i<r;++i)ans=ans*(n-i)/(i+1);
		return ans;
	}
	static long modCombi(long n,long r,long mod){
		if(r<0||n<r)return 0;
		long ans=1;
		r=Math.min(n-r,r);
		for(int i=0;i<r;++i)ans=(ans*(n-i)%mod)*modPow(i+1,mod-2,mod)%mod;
		return ans;
	}
}
class BIT{
	int size;long[]tree;
	BIT(int n){size=n;tree=new long[n+1];}
	long sum(int i){
		long sum=0;
		while(i>0){sum+=tree[i];i^=i&(-i);}
		return sum;
	}
	void add(int i,long x){while(i<=size){tree[i]+=x;i+=i&(-i);}}
}
abstract class SegmentTree<E>{
	int N,size;E def;Object[]node;
	SegmentTree(int n,E def,boolean include){
		N=2;size=1;
		while(N<n<<1){N<<=1;size<<=1;}
		size-=include?1:0;
		node=new Object[N];
		this.def=def;
		Arrays.fill(node,this.def);
	}
	SegmentTree(E[]arr,E def,boolean include){
		N=2;size=1;
		while(N<arr.length<<1){N<<=1;size<<=1;}
		node=new Object[N];
		this.def=def;
		System.arraycopy(arr,0,node,size,arr.length);
		for(int i=arr.length+size;i<N;i++)node[i]=def;
		updateAll();
		size-=include?1:0;
	}
	SegmentTree(int n,E def){this(n,def,false);}
	@SuppressWarnings("unchecked")
	void updateAll(){for(int i=size-1;i>0;i--)node[i]=function((E)node[i<<1],(E)node[(i<<1)+1]);}
	@SuppressWarnings("unchecked")
	void update(int n,E value){
		n+=size;node[n]=value;n>>=1;
		while(n>0){node[n]=function((E)node[n<<1],(E)node[(n<<1)+1]);n>>=1;}
	}
	@SuppressWarnings("unchecked")
	E get(int a){return(E)node[a+size];}
	@SuppressWarnings("unchecked")
	E answer(){return(E)node[1];}
	@SuppressWarnings("unchecked")
	E query(int l,int r){
		l+=size;r+=size;
		E answer=def;
		while(l>0&&r>0&&l<=r){
			if(l%2==1)answer=function((E)node[l++],answer);l>>=1;
			if(r%2==0)answer=function(answer,(E)node[r--]);r>>=1;
		}
		return answer;
	}
	abstract E function(E a,E b);
}
class UnionFind{
	int[]par,rank,size,path;int count;
	UnionFind(int N){
		count=N;
		par=new int[N];rank=new int[N];size=new int[N];path=new int[N];
		Arrays.fill(par,-1);Arrays.fill(size,1);
	}
	int root(int x){if(par[x]==-1)return x;return par[x]=root(par[x]);}
	boolean isSame(int x,int y){return root(x)==root(y);}
	boolean unite(int x,int y){
		int rx=root(x);
		int ry=root(y);
		++path[rx];
		if(rx==ry)return false;
		if(rank[rx]<rank[ry]){
			int temp=rx;
			rx=ry;
			ry=temp;
		}
		par[ry]=rx;
		if(rank[rx]==rank[ry])++rank[rx];
		path[rx]+=path[ry];
		size[rx]+=size[ry];
		--count;
		return true;
	}
	int groupCount(){return count;}
	int pathCount(int x){return path[root(x)];}
	int size(int x){return size[root(x)];}
}
class Pair<K extends Comparable<K>,V extends Comparable<V>>implements Comparable<Pair<K,V>>{
	AbstractMap.SimpleEntry<K,V>map;
	Pair(K key,V value){map=new AbstractMap.SimpleEntry<>(key,value);}
	K getKey(){return map.getKey();}
	V getValue(){return map.getValue();}
	K setKey(K key){
		K oldKey=map.getKey();
		V value=map.getValue();
		map=new AbstractMap.SimpleEntry<>(key,value);
		return oldKey;
	}
	V setValue(V value){return map.setValue(value);}
	public int compareTo(Pair<K,V>pair){
		int com=getKey().compareTo(pair.getKey());
		return com!=0?com:getValue().compareTo(pair.getValue());
	}
	public boolean equals(Object o){
		if(o instanceof Pair<?,?>){
			var pair=(Pair<?,?>)o;
			return getKey().equals(pair.getKey())&&getValue().equals(pair.getValue());
		}
		return false;
	}
	public String toString(){return getKey()+"="+getValue();}
	public int hashCode(){return(getKey().hashCode()<<2)^(getValue().hashCode());}
}
class SScanner{
	int buff_size=1<<12,point,length;InputStream is;byte[]buff;
	SScanner(InputStream is){
		this.is=is;
		buff=new byte[buff_size];
		point=length=0;
	}
	void reload(){do{try{length=is.read(buff,point=0,buff_size);}catch(IOException e){e.printStackTrace();System.exit(1);}}while(length==-1);}
	byte read(){
		if(point==length)reload();
		return buff[point++];
	}
	byte nextByte(){
		var c=read();
		while(c<=' ')c=read();
		return c;
	}
	int nextInt(){
		int ans=0;
		var c=read();
		while(c<=' ')c=read();
		var negate=c=='-';
		if(c=='-')c=read();
		while('0'<=c&&c<='9'){ans=ans*10+c-'0';c=read();}
		return negate?-ans:ans;
	}
	long nextLong(){
		var ans=0L;
		var c=read();
		while(c<=' ')c=read();
		var negate=c=='-';
		if(c=='-')c=read();
		while('0'<=c&&c<='9'){ans=ans*10+c-'0';c=read();}
		return negate?-ans:ans;
	}
	char nextChar(){
		byte c=read();
		while(c<=' ')c=read();
		return(char)c;
	}
	String next(){
		var ans=new StringBuilder();
		byte c=read();
		while(c<=' ')c=read();
		while(c>' '){ans.append((char)c);c=read();}
		return ans.toString();
	}
	byte[]nextByte(int n){
		var ans=new byte[n];
		for(int i=0;i<n;++i)ans[i]=nextByte();
		return ans;
	}
	int[]nextInt(int n){
		var ans=new int[n];
		for(int i=0;i<n;++i)ans[i]=nextInt();
		return ans;
	}
	long[]nextLong(int n){
		var ans=new long[n];
		for(int i=0;i<n;++i)ans[i]=nextLong();
		return ans;
	}
	String[]next(int n){
		var ans=new String[n];
		for(int i=0;i<n;++i)ans[i]=next();
		return ans;
	}
	byte[][]nextByte(int n,int m){
		var ans=new byte[n][];
		for(int i=0;i<n;++i)ans[i]=nextByte(m);
		return ans;
	}
	int[][]nextInt(int n,int m){
		var ans=new int[n][];
		for(int i=0;i<n;++i)ans[i]=nextInt(m);
		return ans;
	}
	long[][]nextLong(int n,int m){
		var ans=new long[n][];
		for(int i=0;i<n;++i)ans[i]=nextLong(m);
		return ans;
	}
	String[][]next(int n,int m){
		var ans=new String[n][];
		for(int i=0;i<n;++i)ans[i]=next(m);
		return ans;
	}
	char[]nextCharArray(){return next().toCharArray();}
	char[][]nextCharArray(int n){
		var ans=new char[n][];
		for(int i=0;i<n;++i)ans[i]=nextCharArray();
		return ans;
	}
	int[][]nextGraph(int N,int M){
		if(M==0)return new int[N+1][0];
		var ans=new int[N+1][];
		var path=nextInt(M,2);
		var count=new int[N+1];
		for(int[]temp:path){++count[temp[0]];++count[temp[1]];}
		for(int i=1;i<=N;++i)ans[i]=new int[count[i]];
		for(int[]temp:path){ans[temp[0]][--count[temp[0]]]=temp[1];ans[temp[1]][--count[temp[1]]]=temp[0];}
		ans[0]=new int[0];
		return ans;
	}
	Point nextPoint(){return new Point(nextInt(),nextInt());}
	Point[]nextPoint(int n){
		var ans=new Point[n];
		for(int i=0;i<n;++i)ans[i]=nextPoint();
		return ans;
	}
	void close(){try{is.close();}catch(IOException e){e.printStackTrace();System.exit(1);}}
}
class SPrinter extends PrintWriter{
	SPrinter(PrintStream os,boolean bool){super(os,bool);}
	void println(int[]ar){println(ar,' ');}
	void println(int[]ar,String str){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(str);
			print(ar[i]);
		}
		println();
	}
	void println(int[]ar,char c){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(c);
			print(ar[i]);
		}
		println();
	}
	void println(int[][]ar){println(ar,' ');}
	void println(int[][]ars,String str){for(var ar:ars)println(ar,str);}
	void println(int[][]ars,char c){for(var ar:ars)println(ar,c);}
	void println(long[]ar){println(ar,' ');}
	void println(long[]ar,String str){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(str);
			print(ar[i]);
		}
		println();
	}
	void println(long[]ar,char c){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(c);
			print(ar[i]);
		}
		println();
	}
	void println(long[][]ar){println(ar,' ');}
	void println(long[][]ars,String str){for(var ar:ars)println(ar,str);}
	void println(long[][]ars,char c){for(var ar:ars)println(ar,c);}
	void println(char[]cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(str);
			print(cs[i]);
		}
		println();
	}
	void println(char[]cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(c);
			print(cs[i]);
		}
		println();
	}
	void println(char[][]cs){for(var c:cs)println(c);}
	void println(char[][]cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(str);
			print(cs[i]);
		}
		println();
	}
	void println(char[][]cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(c);
			print(cs[i]);
		}
		println();
	}
	<E>void println(E[]ar){println(ar,' ');}
	<E>void println(E[]ar,String str){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(str);
			print(ar[i]);
		}
		println();
	}
	<E>void println(E[]ar,char c){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(c);
			print(ar[i]);
		}
		println();
	}
	<E>void println(E[][]ars){println(ars,' ');}
	<E>void println(E[][]ars,String str){for(E[]ar:ars)println(ar,str);}
	<E>void println(E[][]ars,char c){for(E[]ar:ars)println(ar,c);}
}