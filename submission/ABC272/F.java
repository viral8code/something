import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);

	public static void main(String[] args)throws IOException{

		/*����////////

		*/////////////

		int N = System.in.nextInt();
		int[] strs = new int[N];
		StringBuilder sb = new StringBuilder(System.in.next());
		strs[0] = sb.toString().hashCode();
		for(int i=1;i<N;i++){
			char c = sb.charAt(0);
			sb.deleteCharAt(0);
			sb.append(c);
			strs[i] = sb.toString().hashCode();
		}
		Arrays.sort(strs);
		int answer = 0;
		StringBuilder sb2 = new StringBuilder(System.in.next());
		for(int i=0;i<N;i++){
			int str = sb2.toString().hashCode();
			int a=0,b=strs.length-1,ans=strs.length,s;
			while(a-b<1){
				s=(a+b)/2;
				if(strs[s]>str)b=(ans=s)-1;
				else a=s+1;
			}
			answer += ans;
			char c = sb2.charAt(0);
			sb2.deleteCharAt(0);
			sb2.append(c);
		}
		System.out.println(answer);

		System.out.close();
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
	public Library(int fac,long mod){
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
	 * downSearch�ł�num�ȉ��ōő�̗v�f���A
	 * upSearch�ł�num�ȏ�ōŏ��̗v�f��T���܂��B
	 * ���l��underSearch�ł�num�����ōő�̗v�f���A
	 * overSearch�ł�num���傫���ŏ��̗v�f��T���܂��B
	 * �Ȃ��Aunder��down�͏����𖞂����ő��index���A
	 * up��over�͏����𖞂����ŏ���index��Ԃ��܂��B
	 * �߂�l�͈����̔z��(List)��index�ł��B
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
	 * �Œ�����������̒�����Ԃ��܂��B
	 * ������boolean�^�ϐ���n����
	 * �����l�������Ɣ��肷�邩�w��ł��܂��B
	 * �f�t�H���g�ł͑����Ɣ��肵�܂���B
	 */
	public static int lis(int[] nums){
		int[] list = new int[nums.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int i=0;i<nums.length;i++){
			int index = upSearch(list,nums[i]);
			list[index] = Math.min(list[index],nums[i]);
		}
		int answer = underSearch(list,Integer.MAX_VALUE);
		return answer+1;
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
	public static int lis(int[][] nums,int p){
		int[] list = new int[nums.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int i=0;i<nums.length;i++){
			int index = upSearch(list,nums[i][p]);
			list[index] = Math.min(list[index],nums[i][p]);
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

	public void exit(int num){
		try{
			in.close();
			out.close();
		}catch(IOException e){
			System.err.println(e);
		}finally{
			System.exit(num);
		}
	}
}
//Union-Find
class UnionFind{
	private int[] par,rank,size;
	private ArrayList<HashSet<Integer>> groupList;
	private int N,count;
	public UnionFind(int N){
		this.N = N;
		count = N;
		par = new int[N];
		rank = new int[N];
		size = new int[N];
		groupList = new ArrayList<HashSet<Integer>>();
		for(int i=0;i<N;i++){
			HashSet<Integer> temp = new HashSet<Integer>();
			temp.add(i);
			groupList.add(temp);
		}
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
			rank[rx]++;
		size[rx] += size[ry];
		groupList.get(rx).addAll(groupList.get(ry));
		groupList.set(ry,null);
		--count;
		return true;
	}
	public HashSet<Integer> getMember(int x){
		int r = root(x);
		return groupList.get(r);
	}
	public ArrayList<HashSet<Integer>> getAllGroup(){
		ArrayList<HashSet<Integer>> ans = new ArrayList<HashSet<Integer>>();
		for(int i=0;i<N;i++){
			HashSet<Integer> temp = groupList.get(i);
			if(temp!=null)
				ans.add(temp);
		}
		return ans;
	}
	public int groupCount(){
		return count;
	}
	public int size(int x){
		return size[root(x)];
	}
}
//Pair
class Pair<K extends Comparable<K>,V extends Comparable<V>>
	implements Comparable<Pair<K,V>>{
	private AbstractMap.SimpleEntry<K,V> map;
	public Pair(K key,V value){
		map = new AbstractMap.SimpleEntry<K,V>(key,value);
	}
	public K getKey(){
		return map.getKey();
	}
	public V getValue(){
		return map.getValue();
	}
	public K setKey(K key){
		K oldKey = map.getKey();
		V value = map.getValue();
		map = new AbstractMap.SimpleEntry<K,V>(key,value);
		return oldKey;
	}
	public V setValue(V value){
		return map.setValue(value);
	}
	@Override
	public int compareTo(Pair<K,V> pair){
		int com = getKey().compareTo(pair.getKey());
		return com!=0?com:getValue().compareTo(pair.getValue());
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Map.Entry))
			return false;
		Pair<?,?> pair = (Pair<?,?>)o;
		return getKey()==pair.getKey()&&getValue()==pair.getValue();
	}
	@Override
	public int hashCode(){
		K k = getKey();
		V v = getValue();
		return (k==null?0:k.hashCode())^(v==null?0:v.hashCode());
	}
	@Override
	public String toString(){
		return getKey()+"="+getValue();
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
