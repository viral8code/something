import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);

	public static void main(String[] args)throws IOException{

		/*����////////

		*/////////////

		int N = System.in.nextInt();
		int now = 1;
		int[][] ans = new int[N][N];
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				ans[i][j] = now++;
		ans = swap(ans);
		int[][] nextAns = new int[N][N];
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				nextAns[i][j] = ans[j][i];
			}
		}
		ans = swap(nextAns);
		

		for(int i=0;i<N;i++){
			System.out.println(ans[i]," ");
		}

		System.out.close();
	}
	public static int[][] swap(int[][] num){
		
	}
}
/*////////////////////////////////////////////////
	* My Library *

	@auther viral
*/////////////////////////////////////////////////
class Library{
	SimpleScanner in;
	SimplePrinter out;
	private long[] Factorials,InFactorials;
	public long mod = 0;
	public Library(InputStream in,OutputStream out){
		this.in = new SimpleScanner(in);
		this.out = new SimplePrinter(out);
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

	/**
	 * �R���X�g���N�^�œn���ꂽ�l�܂ł�
	 * num!�AaCb��mod�Ŋ��������܂��Ԃ��܂��B
	 */
	public long getFact(int num){
		return Factorials[num];
	}
	public long getCombi(int a,int b){
		return (Factorials[a]*InFactorials[a-b]%mod)*InFactorials[b]%mod;
	}

	/**
	 * fact�ł�a!���A
	 * modFact�ł͈�����mod���R���X�g���N�^�œn���ꂽmod��
	 * ���������܂��Ԃ��܂��B
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
	 * Combi�ł�aCb�A
	 * modCombi�ł͈�����mod���R���X�g���N�^�œn���ꂽmod��
	 * ���������܂��Ԃ��܂��B
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
	 * int�Along�^�z����\�[�g���܂��B
	 * �񎟌��z��̏ꍇ��[i][0]��[i][1]�̑傫����
	 * �����ɕ��בւ��܂��B
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
	 * int�^�z����\�[�g���܂��B
	 * �ő�l���������ꍇ�͗L���ł��B
	 */
	public static void countSort(int[] nums,int limit){
		int[] list=new int[limit+1];
		for(int i=0;i<nums.length;i++)list[nums[i]]++;
		int temp=0;
		for(int i=0;i<list.length;i++)for(int j=0;j<list[i];j++)nums[temp++]=i;
	}

	/**
	 * gcd�͍ő���񐔂��A
	 * lcm�͍ŏ����{����Ԃ��܂��B
	 * lcm�̓I�[�o�[�t���[�̉\��������̂ł����ӂ��B
	 * �߂�l��long�^�ł��B
	 */
	public static long gcd(long a,long b){
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
	 * BigInteger�N���X�̂��̂��g�p���܂��B
	 * �f���ł���m���������Ȃ�true�A
	 * �m���ɍ������Ȃ�false��Ԃ��܂��B
	 * �딻��̊m����1/2^20�ȉ��ł��B
	 */
	public static boolean isPrime(long num){
		return BigInteger.valueOf(num).isProbablePrime(20);
	}

	/**
	 * �����܂�(�����܂�)�̑f����Ԃ��܂��B
	 * 2^30�ȏ�̑f���񋓂͂ł��Ȃ��̂ł����ӂ��B
	 */
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

	/**
	 * pow�ł͂��̂܂܂̌��ʂ��A
	 * modPow�ł͈�����mod���R���X�g���N�^�œn���ꂽmod��
	 * ���������܂��Ԃ��܂��B
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
	 * String��int�Along�ɕϊ����܂��B
	 * ���l�ȊO�̕����ł����Ă���������l�ϊ����Ă��܂��̂�
	 * �g�p�̍ۂ͂����ӂ��B
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
	 * downsearch�ł�num�ȉ����A
	 * upsearch�ł�num�ȏ��T���܂��B
	 * �����̔z��(List)�̃C���f�b�N�X��Ԃ��܂��B
	 */
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

	public void exit(int num){
		try{
			in.close();
			out.close();
		}catch(Exception e){
		}finally{
			System.exit(num);
		}
	}
}
/*////////////////////////////////////////////////
	* My Scanner *

	@auther viral
*/////////////////////////////////////////////////
class SimpleScanner{
	final private int buff_size = 1<<12;
	private InputStream is;
	private byte[] buff;
	private int point,length;
	public SimpleScanner(InputStream is){
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private void reload()throws IOException{
		do{
			length = is.read(buff,point = 0,buff_size);
		}while(length==-1);
	}
	private byte read()throws IOException{
		if(point==length)reload();
		return buff[point++];
	}
	public byte nextByte()throws IOException{
		byte c = read();
		while(c<=' ')c = read();
		return c;
	}
	public int nextInt()throws IOException{
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
	public long nextLong()throws IOException{
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
	public char nextChar()throws IOException{
		byte c = read();
		while(c<=' ')c = read();
		return (char)c;
	}
	public String next()throws IOException{
		StringBuilder ans = new StringBuilder();
		byte c = read();
		while(c<=' ')c = read();
		while(c>' '){
			ans.append((char)c);
			c = read();
		}
		return ans.toString();
	}
	public byte[] nextByte(int n)throws IOException{
		byte[] ans = new byte[n];
		for(int i=0;i<n;i++){
			ans[i] = nextByte();
		}
		return ans;
	}
	public int[] nextInt(int n)throws IOException{
		int[] ans = new int[n];
		for(int i=0;i<n;i++){
			ans[i] = nextInt();
		}
		return ans;
	}
	public long[] nextLong(int n)throws IOException{
		long[] ans = new long[n];
		for(int i=0;i<n;i++){
			ans[i] = nextLong();
		}
		return ans;
	}
	public String[] next(int n)throws IOException{
		String[] ans = new String[n];
		for(int i=0;i<n;i++){
			ans[i] = next();
		}
		return ans;
	}
	public byte[][] nextByte(int n,int m)throws IOException{
		byte[][] ans = new byte[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextByte(m);
		}
		return ans;
	}
	public int[][] nextInt(int n,int m)throws IOException{
		int[][] ans = new int[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextInt(m);
		}
		return ans;
	}
	public long[][] nextLong(int n,int m)throws IOException{
		long[][] ans = new long[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextLong(m);
		}
		return ans;
	}
	public String[][] next(int n,int m)throws IOException{
		String[][] ans = new String[n][];
		for(int i=0;i<n;i++){
			ans[i] = next(m);
		}
		return ans;
	}
	public char[] nextCharArray()throws IOException{
		return next().toCharArray();
	}
	public char[][] nextCharArray(int n)throws IOException{
		char[][] ans = new char[n][];
		for(int i=0;i<n;i++){
			ans[i] = next().toCharArray();
		}
		return ans;
	}
	public void close()throws IOException{
		is.close();
	}
}
/*////////////////////////////////////////////////
	* My Printer *

	@auther viral
*/////////////////////////////////////////////////
class SimplePrinter extends PrintWriter{
	public SimplePrinter(OutputStream os){
		super(os);
	}
	public void println(byte[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(int[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(long[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Byte[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Integer[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Long[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(String[] strs,String str){
		println(String.join(str,strs));
	}
	public void println(char[] cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(str);
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
}
