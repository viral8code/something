import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);
	static Random rm = new Random();

	static int[][] map;
	static int[][] map_original;

	static boolean[][][] connected;

	static LinkedList<Integer> moving;
	static LinkedList<Integer> connecting;

	static int odds_move = 10;
	static int odds_connect = 20;

	static int now_point = Integer.MIN_VALUE;

	static StringBuilder answer;

	static boolean debug = false;

	public static void main(String[] args)throws IOException{
		int N = System.in.nextInt();
		int K = System.in.nextInt();
		map_original = new int[N][N];
		map = new int[N][N];
		if(debug)java.lang.System.out.println("N,K Gotcha!");
		for(int i=0;i<N;i++){
			String[] str = System.in.next().split("");
			for(int j=0;j<N;j++){
				map_original[i][j] = Integer.parseInt(str[j]);
			}
		}
		if(debug)java.lang.System.out.println("map Gotcha!");
		long startTime = java.lang.System.nanoTime();


		int num = 0;
		while((java.lang.System.nanoTime()-startTime)/1e9<2.8){


			rm = new Random(Math.abs(rm.nextInt()));


			moving = new LinkedList<Integer>();
			connecting = new LinkedList<Integer>();
			connected = new boolean[N][N][4];

			for(int i=0;i<map.length;i++){
				for(int j=0;j<map[i].length;j++){
					map[i][j] = map_original[i][j];
				}
			}


			for(int i=0;i<N;i++){
				for(int j=0;j<N;j++){
					if(moving.size()>=200*K)continue;
					move(i,j);
				}
			}
			if(debug){
				System.out.println("--moved--------------------------------");
				for(int i=0;i<N;i++){
					for(int j=0;j<N;j++){
						System.out.print(map[i][j]);
					}
					System.out.println();
				}
				System.out.println("--moved--------------------------------");
			}
			for(int i=0;i<N;i++){
				for(int j=0;j<N;j++){
					if(moving.size()+connecting.size()==400*K)continue;
					connect(i,j);
				}
			}
			if(debug){
				System.out.println("--connected--------------------------------");
				for(int i=0;i<N;i++){
					for(int j=0;j<N;j++){
						System.out.print(map[i][j]<0?map[i][j]:" "+map[i][j]);
					}
					System.out.println();
				}
				System.out.println("--connected--------------------------------");
			}

			int point = point(K);

			if(!debug)
				java.lang.System.out.println(point+"“_");

			if(point<=now_point){
				continue;
			}
			now_point = point;

			answer = new StringBuilder();

			answer.append(moving.size()/4);
			answer.append("\n");

			int count = 0;

			for(int temp:moving){
				if((count%4)==3){
					answer.append(temp);
					answer.append("\n");
				}else{
					answer.append(temp);
					answer.append(' ');
				}
				count++;
			}


			answer.append(Math.min(connecting.size()/4,100*K-moving.size()/4));
			answer.append("\n");

			for(int temp:connecting){
				if(count==400*K)continue;
				if((count%4)==3){
					answer.append(temp);
					answer.append("\n");
				}else{
					answer.append(temp);
					answer.append(' ');
				}
				count++;
			}


			if(!debug)break;


		}

		if(debug)System.out.println("‘’Tõ”F"+num);

		System.out.println(answer.toString());
		System.out.close();
	}

	public static void connect(int i,int j){

		if(debug)java.lang.System.out.println("("+i+","+j+")");

		if(map[i][j]<=0)return;

		if(!connected[i][j][0]){
			int ii = i-1;
			while(true){
				if(ii<0)break;
				if(map[ii][j]<0)break;
				if(map[ii][j]>0){
					if(map[ii][j]==map[i][j]||rm.nextInt(odds_connect)==0){
						if(debug)java.lang.System.out.println("("+i+","+j+")"+"("+ii+","+j+")");
						connected[i][j][0] = connected[ii][j][2] = true;
						connecting.add(i);
						connecting.add(j);
						connecting.add(ii);
						connecting.add(j);
						for(int k=i-1;k>ii;k--){
							map[k][j] = -1;
						}
					}
					break;
				}
				ii--;
			}
		}
		if(!connected[i][j][1]){
			int jj = j+1;
			while(true){
				if(jj==map.length)break;
				if(map[i][jj]<0)break;
				if(map[i][jj]>0){
					if(map[i][jj]==map[i][j]){
						if(debug)java.lang.System.out.println("("+i+","+j+")"+"("+i+","+jj+")");
						connected[i][j][1] = connected[i][jj][3] = true;
						connecting.add(i);
						connecting.add(j);
						connecting.add(i);
						connecting.add(jj);
						for(int k=j+1;k<jj;k++){
							map[i][k] = -1;
						}
					}
					break;
				}
				jj++;
			}
		}
		if(!connected[i][j][2]){
			int ii = i+1;
			while(true){
				if(ii==map.length)break;
				if(map[ii][j]<0)break;
				if(map[ii][j]>0){
					if(map[ii][j]==map[i][j]){
						if(debug)java.lang.System.out.println("("+i+","+j+")"+"("+ii+","+j+")");
						connected[i][j][2] = connected[ii][j][0] = true;
						connecting.add(i);
						connecting.add(j);
						connecting.add(ii);
						connecting.add(j);
						for(int k=i+1;k<ii;k++){
							map[k][j] = -1;
						}
					}
					break;
				}
				ii++;
			}
		}
		if(!connected[i][j][3]){
			int jj = j-1;
			while(true){
				if(jj<0)break;
				if(map[i][jj]<0)break;
				if(map[i][jj]>0){
					if(map[i][jj]==map[i][j]||rm.nextInt(odds_connect)==0){
						if(debug)java.lang.System.out.println("("+i+","+j+")"+"("+i+","+jj+")");
						connected[i][j][3] = connected[i][jj][1] = true;
						connecting.add(i);
						connecting.add(j);
						connecting.add(i);
						connecting.add(jj);
						for(int k=j-1;k>jj;k--){
							map[i][k] = -1;
						}
					}
					break;
				}
				jj--;
			}
		}
	}
	public static void move(int i,int j){

		if(map[i][j]==0)return;

		if(i>0&&map[i-1][j]==0&&rm.nextInt(odds_move)==0){
			moving.add(i);
			moving.add(j);
			moving.add(i-1);
			moving.add(j);
			map[i-1][j] = map[i][j];
			map[i][j] = 0;
		}
		else if(j<map.length-1&&map[i][j+1]==0&&rm.nextInt(odds_move)==0){
			moving.add(i);
			moving.add(j);
			moving.add(i);
			moving.add(j+1);
			map[i][j+1] = map[i][j];
			map[i][j] = 0;
		}
		else if(i<map.length-1&&map[i+1][j]==0&&rm.nextInt(odds_move)==0){
			moving.add(i);
			moving.add(j);
			moving.add(i+1);
			moving.add(j);
			map[i+1][j] = map[i][j];
			map[i][j] = 0;
		}
		else if(j>0&&map[i][j-1]==0&&rm.nextInt(odds_move)==0){
			moving.add(i);
			moving.add(j);
			moving.add(i);
			moving.add(j-1);
			map[i][j-1] = map[i][j];
			map[i][j] = 0;
		}
	}
	public static int point(int k){

		boolean[][] checked = new boolean[map.length][map.length];
		int sum = 0;


		for(int i=0;i<checked.length;i++){
			for(int j=0;j<checked[i].length;j++){

				if(map[i][j]<1)continue;
				if(checked[i][j])continue;

				checked[i][j] = true;

				int[] counter = new int[k+1];

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
