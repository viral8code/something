import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);

	static ArrayList<Point> list = new ArrayList<Point>();
	static ArrayList<Integer> answer = new ArrayList<Integer>();
	static ArrayDeque<Point> newNail = new ArrayDeque<Point>();

	public static void main(String[] args)throws IOException{

		long startTime = java.lang.System.nanoTime();

		int N = System.in.nextInt();
		int K = System.in.nextInt();
		boolean[][] map = new boolean[1001][1001];
		for(int i=0;i<N;i++){
			int x = System.in.nextInt();
			int y = System.in.nextInt();
			list.add(new Point(x,y));
			map[x][y] = true;
		}

		for(int i=1;i<=N;i++)answer.add(i);

		int now = N+1;

		for(int i=N-1;i>0&&now<=N+K;i--){
			int count = 0;
			ArrayDeque<Point[]> bfs = new ArrayDeque<Point[]>();
			Point q1 = list.get(i);
			Point q2 = list.get(i-1);
			for(int j=0;j<list.size()-1;j++){
				if(i-1==j+1||i-1==j||i==j)continue;
				if(check(q1,q2,list.get(j),list.get(j+1))){
					count++;
					Point[] tmp = new Point[]{list.get(j),list.get(j+1)};
					bfs.add(tmp);
				}
			}
			if(count>0){
				while(bfs.size()>0){
					Point[] p = bfs.pollFirst();
					if(p[0].x!=1000&&!map[p[0].x+1][p[0].y]){
						Point tmp = new Point(p[0].x+1,p[0].y);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[0].x!=0&&!map[p[0].x-1][p[0].y]){
						Point tmp = new Point(p[0].x-1,p[0].y);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[0].y!=1000&&!map[p[0].x][p[0].y+1]){
						Point tmp = new Point(p[0].x,p[0].y+1);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[0].y!=0&&!map[p[0].x][p[0].y-1]){
						Point tmp = new Point(p[0].x,p[0].y-1);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[1].x!=1000&&!map[p[1].x+1][p[1].y]){
						Point tmp = new Point(p[1].x+1,p[1].y);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[1].x!=0&&!map[p[1].x-1][p[1].y]){
						Point tmp = new Point(p[1].x-1,p[1].y);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[1].y!=1000&&!map[p[1].x][p[1].y+1]){
						Point tmp = new Point(p[1].x,p[1].y+1);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
					if(p[1].y!=0&&!map[p[1].x][p[1].y-1]){
						Point tmp = new Point(p[1].x,p[1].y-1);
						if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
							if(checkAll(q1,tmp,q2)<count){
								answer.add(i,0);
								newNail.addFirst(tmp);
								now++;
								map[tmp.x][tmp.y] = true;
								break;
							}
						}
					}
				}
			}
			if((java.lang.System.nanoTime()-startTime)/1e9>4.8)break;
		}
		System.out.println(newNail.size());
		for(Point tmp:newNail){
			System.out.println(tmp.x+" "+tmp.y);
		}
		int counter = N+1;
		for(int tmp:answer){
			if(tmp==0){
				System.out.println(counter++);
			}else{
				System.out.println(tmp);
			}
		}

		System.out.close();
	}
	public static boolean check(Point a,Point b,Point c,Point d){
		int s = (a.x-b.x)*(c.y-a.y)-(a.y-b.y)*(c.x-a.x);
		int t = (a.x-b.x)*(d.y-a.y)-(a.y-b.y)*(d.x-a.x);
		if(s*t>0)return false;
		s = (c.x-d.x)*(a.y-c.y)-(c.y-d.y)*(a.x-c.x);
		t = (c.x-d.x)*(b.y-c.y)-(c.y-d.y)*(b.x-c.x);
		if(s*t>0)return false;
		return true;
	}
	public static int checkAll(Point p,Point newP,Point q){
		int counter = 0;
		Point r = list.get(answer.get(0)-1);
		Iterator<Point> itr = newNail.iterator();
		for(int i=1;i<answer.size();i++){
			Point s;
			if(answer.get(i)==0)s = itr.next();
			else s = list.get(answer.get(i)-1);
			if(	r.x==p.x&&r.y==p.y||
				r.x==q.x&&r.y==q.y||
				s.x==p.x&&s.y==p.y||
				s.x==q.x&&s.y==q.y
			){}
			else{
				if(check(p,newP,r,s))counter++;
				if(check(q,newP,r,s))counter++;
			}
			r = s;
		}
		return counter;
	}
}
class Point{
	int x;
	int y;
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
}
/*////////////////////////////////////////////////
	* My Library *
*/////////////////////////////////////////////////
class Library{
	SimpleScanner in = null;
	PrintWriter out = null;
	private long[] Factorials,InFactorials;
	public long mod = 0;
	public Library(InputStream in,OutputStream out){
		this.in = new SimpleScanner(in);
		this.out = new PrintWriter(out);
	}
	public Library(InputStream in,OutputStream out,int fac,long mod){
		this(in,out);
		Factorials=new long[fac+1];
		Factorials[1]=1;
		for(int i=2;i<=fac;i++)Factorials[i]=Factorials[i-1]*i%mod;
		InFactorials=new long[fac+1];
		InFactorials[fac]=modPow(Factorials[fac],mod-2,mod);
		for(int i=fac;i>1;i--)InFactorials[i-1]=InFactorials[i]*i%mod;
		this.mod = mod;
	}
	public long getFact(int num){
		return Factorials[num];
	}
	public long getCombi(int a,int b){
		return (Factorials[a]*InFactorials[a-b]%mod)*InFactorials[b]%mod;
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
	public static long fact(long a){
		long ans=1;
		for(long i=2;i<=a;i++)ans*=i;
		return ans;
	}
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
	public static void superSort(int[] nums,int limit){
		int[] list=new int[limit+1];
		for(int i=0;i<nums.length;i++)list[nums[i]]++;
		int temp=0;
		for(int i=0;i<list.length;i++)for(int j=0;j<list[i];j++)nums[temp++]=i;
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
		return BigInteger.valueOf(num).isProbablePrime(20);
	}
	public static int[] prime(int num){
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
	public static int downsearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(Integer[] nums,int num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(Integer[] nums,int num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(Long[] nums,long num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(Long[] nums,long num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(ArrayList<Integer> nums,int num){
		int a=0,b=nums.size()-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(ArrayList<Integer> nums,int num){
		int a=0,b=nums.size()-1,ans=nums.size(),c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(ArrayList<Long> nums,long num){
		int a=0,b=nums.size()-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(ArrayList<Long> nums,long num){
		int a=0,b=nums.size()-1,ans=nums.size(),c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	@Override
	public String toString(){
		StringBuilder answer=new StringBuilder();
		if(Factorials!=null)answer.append("Factorials are defined. (You can use 0~"+(Factorials.length-1)+" for index)");
		else answer.append("Factorial is not defined.");
		answer.append("\n");
		if(InFactorials!=null)answer.append("InFactorials are defined. (You can use 0~"+(InFactorials.length-1)+" for index)");
		else answer.append("InFactorial is not defined.");
		answer.append("\n");
		if(mod!=0)answer.append("mod is "+mod);
		else answer.append("mod is not defined.");
		return answer.toString();
	}
}
/*////////////////////////////////////////////////
	* “ü—Í—p *
*/////////////////////////////////////////////////
class SimpleScanner{
	private final int buff_size=1<<16;
	private InputStream is=null;
	private byte[] buff=new byte[buff_size];
	private int length=0,point=0;
	public SimpleScanner(InputStream is){
		this.is = is;
	}
	private void reload()throws IOException{
		point=0;
		length=is.read(buff);
	}
	private void load(int n)throws IOException{
		if(n<=length-point)return;
		for(int i=point,j=0;i<length;i++,j++)buff[j]=buff[i];
		length-=point;
		point=0;
		int temp=is.read(buff,length,buff_size-length);
		if(temp==-1)return;
		length+=temp;
	}
	private void skip()throws IOException{
		while(true){
			while(point<length&&buff[point]<33)point++;
			if(point<length)return;
			reload();
		}
	}
	public long nextLong()throws IOException{
		skip();
		load(20);
		long ans=0;
		boolean negate=false;
		if(buff[point++]=='-')negate=true;
		else point--;
		while(47<buff[point]&&buff[point]<58)ans=ans*10+buff[point++]-48;
		return negate?-ans:ans;
	}
	public int nextInt()throws IOException{
		skip();
		load(12);
		int ans=0;
		boolean negate=false;
		if(buff[point++]=='-')negate=true;
		else point--;
		while(47<buff[point]&&buff[point]<58)ans=ans*10+buff[point++]-48;
		return negate?-ans:ans;
	}
	public String next()throws IOException{
		skip();
		load(1);
		StringBuilder sb=new StringBuilder();
		while(buff[point]>32){
			sb.append((char)buff[point++]);
			if(point==length)reload();
		}
		return sb.toString();
	}
	public int[] nextInt(int num)throws IOException{
		int[] array = new int[num];
		for(int i=0;i<num;i++){
			array[i] = nextInt();
		}
		return array;
	}
	public long[] nextLong(int num)throws IOException{
		long[] array = new long[num];
		for(int i=0;i<num;i++){
			array[i] = nextLong();
		}
		return array;
	}
	public String[] next(int num)throws IOException{
		String[] array = new String[num];
		for(int i=0;i<num;i++){
			array[i] = next();
		}
		return array;
	}
	public String toString(){
		StringBuilder answer = new StringBuilder();
		answer.append("\n*----------------------------------------*\n");
		answer.append("These are SimpleScanner's states\n\n");
		answer.append("BufferSize is "+buff.length+"\n");
		answer.append("Now length is "+length+"\n");
		answer.append("Now point is "+point+"\n");
		answer.append("Buffer---------------\n");
		for(int i=0;i<length+2;i++){
			if(i==point)answer.append("@Point is here@");
			answer.append((char)buff[i]);
		}
		answer.append("---------------------");
		answer.append("\n*----------------------------------------*\n");
		return answer.toString();
	}
}
