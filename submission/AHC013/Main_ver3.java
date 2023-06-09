import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);
	static Random rm = new Random();

	static int[][] map;
	static int[][] map_original;

	static int K;

	static int now_point = Integer.MIN_VALUE;

	static LinkedList<Integer> moving;
	static LinkedList<Integer> connecting;

	static boolean[][][] connected;
	static int odds_connect = 10;

	static StringBuilder answer;

	static boolean debug = false;

	static int counter = 0;

	public static void main(String[] args)throws IOException{
		int N = System.in.nextInt();
		K = System.in.nextInt();
		map_original = new int[N][N];
		map = new int[N][N];
		if(debug)java.lang.System.out.println("N,K Gotcha!");
		for(int i=0;i<N;i++){
			char[] str = System.in.next().toCharArray();
			for(int j=0;j<N;j++){
				map_original[i][j] = str[j]-'0';
			}
		}
		if(debug)java.lang.System.out.println("map Gotcha!");
		long startTime = java.lang.System.nanoTime();


		int num = 0;
		while((java.lang.System.nanoTime()-startTime)/1e9<2.8){
			//rm = new Random(num++);

			moving = new LinkedList<Integer>();
			connecting = new LinkedList<Integer>();

			for(int i=0;i<map.length;i++){
				for(int j=0;j<map[i].length;j++){
					map[i][j] = map_original[i][j];
				}
			}

			connected = new boolean[N][N][4];

			move();

			connect();

			int point = point();
			if(point>now_point){
				now_point = point;

				answer = new StringBuilder();

				answer.append(moving.size()/4);
				answer.append('\n');
				int count = 0;
				for(int temp:moving){
					answer.append(temp);
					if((count++%4)==3){
						answer.append('\n');
					}else{
						answer.append(' ');
					}
				}

				answer.append(Math.min(connecting.size()/4,100*K-moving.size()/4));
				answer.append('\n');
				for(int temp:connecting){
					if(count==400*K)break;
					answer.append(temp);
					if((count++%4)==3){
						answer.append('\n');
					}else{
						answer.append(' ');
					}
				}
			}

			if(debug)break;
		}

		if(debug)System.out.println("総探索数："+num);

		if(debug)System.out.println(now_point+"点");

		System.out.println(answer.toString());
		System.out.close();
	}

	public static void move(){

		// 良い策が思いつかん･･･
	}

	public static void connect(){

		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){

				if(debug)java.lang.System.out.println("("+i+","+j+")の試行");

				if(map[i][j] < 1)continue;

				int nowP = point();

				if(!connected[i][j][0]){
					int ii = i;
					while(ii-->0){
						if(map[ii][j]<0)break;
						if(map[ii][j]>0){
							connected[i][j][0] = connected[ii][j][2] = true;
							for(int s=i-1;s>ii;s--)map[s][j]=-1;
							int nextP = point();
							if(nowP<nextP){
								connecting.add(i);
								connecting.add(j);
								connecting.add(ii);
								connecting.add(j);
							}else if(nowP>nextP&&rm.nextInt(odds_connect)==0&&isBetter(ii,j,nowP)){
								connecting.add(i);
								connecting.add(j);
								connecting.add(ii);
								connecting.add(j);
							}else{
								connected[i][j][0] = connected[ii][j][2] = false;
								for(int s=i-1;s>ii;s--)map[s][j]=0;
							}
							break;
						}
					}
				}

				if(!connected[i][j][1]){
					int jj = j;
					while(++jj<map[i].length){
						if(map[i][jj]<0)break;
						if(map[i][jj]>0){
							connected[i][j][1] = connected[i][jj][3] = true;
							for(int s=jj-1;s>j;s--)map[i][s]=-1;
							int nextP = point();
							if(nowP<nextP){
								connecting.add(i);
								connecting.add(j);
								connecting.add(i);
								connecting.add(jj);
							}else if(nowP>nextP&&rm.nextInt(odds_connect)==0&&isBetter(i,jj,nowP)){
								connecting.add(i);
								connecting.add(j);
								connecting.add(i);
								connecting.add(jj);
							}else{
								connected[i][j][1] = connected[i][jj][3] = false;
								for(int s=jj-1;s>j;s--)map[i][s]=0;
							}
							break;
						}
					}
				}

				if(!connected[i][j][2]){
					int ii = i;
					while(++ii<map.length){
						if(map[ii][j]<0)break;
						if(map[ii][j]>0){
							connected[i][j][2] = connected[ii][j][0] = true;
							for(int s=ii-1;s>i;s--)map[s][j]=-1;
							int nextP = point();
							if(nowP<nextP){
								connecting.add(i);
								connecting.add(j);
								connecting.add(ii);
								connecting.add(j);
							}else if(nowP>nextP&&rm.nextInt(odds_connect)==0&&isBetter(ii,j,nowP)){
								connecting.add(i);
								connecting.add(j);
								connecting.add(ii);
								connecting.add(j);
							}else{
								connected[i][j][2] = connected[ii][j][0] = false;
								for(int s=ii-1;s>i;s--)map[s][j]=0;
							}
							break;
						}
					}
				}

				if(!connected[i][j][3]){
					int jj = j;
					while(jj-->0){
						if(map[i][jj]<0)break;
						if(map[i][jj]>0){
							connected[i][j][3] = connected[i][jj][1] = true;
							for(int s=j-1;s>jj;s--)map[i][s]=-1;
							int nextP = point();
							if(nowP<nextP){
								connecting.add(i);
								connecting.add(j);
								connecting.add(i);
								connecting.add(jj);
							}else if(nowP>nextP&&rm.nextInt(odds_connect)==0&&isBetter(i,jj,nowP)){
								connecting.add(i);
								connecting.add(j);
								connecting.add(i);
								connecting.add(jj);
							}else{
								connected[i][j][3] = connected[i][jj][1] = false;
								for(int s=j-1;s>jj;s--)map[i][s]=0;
							}
							break;
						}
					}
				}
			}
		}
	}

	public static boolean isBetter(int i,int j,int before){

		if(!connected[i][j][0]){
			int ii = i;
			while(ii-->0){
				if(map[ii][j]<0)break;
				if(map[ii][j]>0){
					connected[i][j][0] = connected[ii][j][2] = true;
					for(int s=i-1;s>ii;s--)map[s][j]=-1;
					int after = point();
					if(before<after){
						connecting.add(i);
						connecting.add(j);
						connecting.add(ii);
						connecting.add(j);
						return true;
					}else{
						connected[i][j][0] = connected[ii][j][2] = false;
						for(int s=i-1;s>ii;s--)map[s][j]=0;
					}
					break;
				}
			}
		}

		if(!connected[i][j][1]){
			int jj = j;
			while(++jj<map.length){
				if(map[i][jj]<0)break;
				if(map[i][jj]>0){
					connected[i][j][1] = connected[i][jj][3] = true;
					for(int s=jj-1;s>j;s--)map[i][s]=-1;
					int after = point();
					if(before<after){
						connecting.add(i);
						connecting.add(j);
						connecting.add(i);
						connecting.add(jj);
						return true;
					}else{
						connected[i][j][1] = connected[i][jj][3] = false;
						for(int s=jj-1;s>j;s--)map[i][s]=0;
					}
					break;
				}
			}
		}

		if(!connected[i][j][2]){
			int ii = i;
			while(++ii<map.length){
				if(map[ii][j]<0)break;
				if(map[ii][j]>0){
					connected[i][j][2] = connected[ii][j][0] = true;
					for(int s=ii-1;s>i;s--)map[s][j]=-1;
					int after = point();
					if(before<after){
						connecting.add(i);
						connecting.add(j);
						connecting.add(ii);
						connecting.add(j);
						return true;
					}else{
						connected[i][j][2] = connected[ii][j][0] = false;
						for(int s=ii-1;s>i;s--)map[s][j]=0;
					}
					break;
				}
			}
		}

		if(!connected[i][j][3]){
			int jj = j;
			while(jj-->0){
				if(map[i][jj]<0)break;
				if(map[i][jj]>0){
					connected[i][j][3] = connected[i][jj][1] = true;
					for(int s=j-1;s>jj;s--)map[i][s]=-1;
					int after = point();
					if(before<after){
						connecting.add(i);
						connecting.add(j);
						connecting.add(i);
						connecting.add(jj);
						return true;
					}else{
						connected[i][j][3] = connected[i][jj][1] = false;
						for(int s=j-1;s>jj;s--)map[i][s]=0;
					}
					break;
				}
			}
		}
		return false;
	}

	public static int point(){

		if(debug)java.lang.System.out.println(counter++ +"回目のpoint呼びだしです。");

		boolean[][] checked = new boolean[map.length][map.length];
		int sum = 0;


		for(int i=0;i<checked.length;i++){
			for(int j=0;j<checked[i].length;j++){

				if(map[i][j]<1)continue;
				if(checked[i][j])continue;

				checked[i][j] = true;

				if(debug)java.lang.System.out.print("("+i+","+j+")→");

				int[] counter = new int[K+1];

				LinkedList<Integer> list = new LinkedList<Integer>();
				list.addLast(i);
				list.addLast(j);

				while(list.size()>0){

					int x = list.pollFirst();
					int y = list.pollFirst();

					counter[map[x][y]]++;

					if(connected[x][y][0]){
						int xx = x-1;
						while(map[xx][y]<1)xx--;
						if(!checked[xx][y]){
							list.add(xx);
							list.add(y);
							checked[xx][y] = true;
						}
					}

					if(connected[x][y][1]){
						int yy = y+1;
						while(map[x][yy]<1)yy++;
						if(!checked[x][yy]){
							list.add(x);
							list.add(yy);
							checked[x][yy] = true;
						}
					}

					if(connected[x][y][2]){
						int xx = x+1;
						while(map[xx][y]<1)xx++;
						if(!checked[xx][y]){
							list.add(xx);
							list.add(y);
							checked[xx][y] = true;
						}
					}

					if(connected[x][y][3]){
						int yy = y-1;
						while(map[x][yy]<1)yy--;
						if(!checked[x][yy]){
							list.add(x);
							list.add(yy);
							checked[x][yy] = true;
						}
					}
				}


				if(debug)for(int l=1;l<counter.length;l++)java.lang.System.out.print("("+l+","+counter[l]+")");
				if(debug)java.lang.System.out.println();

				for(int s=1;s<counter.length;s++){

					sum += (counter[s]*(counter[s]-1))/2;

					for(int t=s+1;t<counter.length;t++){

						sum -= counter[s]*counter[t];

					}
				}
			}
		}
		return sum;
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
	* 入力用 *
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
