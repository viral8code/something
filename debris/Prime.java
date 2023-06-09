import java.util.Arrays;
import java.util.Random;
import java.math.BigInteger;
class Main{
	public static void main(String[] args){
		Random rm = new Random(0);
		long num = 4;
		while(true){
			long time1 = System.nanoTime();
			isPrime(num);
			long time2 = System.nanoTime();
			long sum = time2-time1;
			BigInteger n = BigInteger.valueOf(num);
			time1 = System.nanoTime();
			n.isProbablePrime(20);
			time2 = System.nanoTime();
			if(sum>time2-time1)
				break;
			num <<= 1;
		}
		System.out.println(num);
	}
	public static boolean isCertaintyPrime(final long n){
		if(n<2L)
			return false;
		for(long i=2;i<=n/i;i++)
			if(n%i==0)
				return false;
		return true;
	}
	public static boolean isPrime(final long n){
		if(n<2L)
			return false;
		if(isPower(n))
			return false;
		System.out.println("get r");
		final int r = getSmallOrder(n);
		int limit = (int)Math.min(r+1,n);
		System.out.println("check small cases");
		for(int i=2;i<limit;i++)
			if(n%i==0L)
				return false;
		if(n<=r)
			return true;
		System.out.println("get tortient");
		final int range = (int)Math.sqrt(tortient(r)*Math.log(n))+1;
		System.out.println("check modulo(len="+r+")(range="+range+")");
		for(int i=1;i<range;i++)
			if(!checkPolynomialModulo(i,n,r))
				return false;
		return true;
	}
	public static boolean isPower(final long n){
		final int limit = (int)(Math.log(n)/Math.log(2)+1);
		for(int i=2;i<limit;i++){
			final long num = (long)Math.pow(n,1.0/i);
			if(pow(num,i)==n||pow(num+1,i)==n)
				return true;
		}
		return false;
	}
	private static long pow(long a,int b){
		long ans = 1;
		while(b>0){
			if((b&1)==1)
				ans *= a;
			a = a*a;
			b >>= 1;
		}
		return ans;
	}
	private static int getSmallOrder(final long n){
		final int limit = (int)(Math.log(n)*Math.log(n));
		BigInteger num = BigInteger.valueOf(n);
		for(int i=2;i<n;i++){
			int order = 0;
			BigInteger prod = BigInteger.ONE;
			BigInteger mod = BigInteger.valueOf(i);
			for(int j=1;j<i;j++){
				prod = prod.multiply(num).mod(mod);
				if(prod.equals(BigInteger.ONE)){
					order = j;
					break;
				}
			}
			if(order>limit)
				return i;
		}
		System.out.println(n+": can't find r");
		return (int)n;
	}
	private static double tortient(int r){
		double ans = r;
		final int loop = (int)Math.sqrt(r)+1;
		for(int i=2;i<loop;i++){
			if(r%i==0){
				ans = ans*(i-1)/i;
				do{
					r /= i;
				}while(r%i==0);
			}
			if(r==1)
				break;
		}
		return ans;
	}
	private static boolean checkPolynomialModulo(final long a,final long n,final int r){
		final long[] array = new PolynomialModulo(new long[]{a,1},n,r).power(n);
		final int p = (int)(n%r);
		if(array.length!=p+1)
			return false;
		if(array[0]!=a%n)
			return false;
		if(array[p]!=1)
			return false;
		for(int i=1;i<p;i++)
			if(array[i]!=0)
				return false;
		return true;
	}
}
final class PolynomialModulo{
	private final long[] arr;
	private final long n;
	private final int r;
	public PolynomialModulo(final long[] arr,final long n,final int r){
		this.arr = arr;
		this.n = n;
		this.r = r;
	}
	public long[] power(final long m){
		if(m==1L)
			return arr;
		if((m&1L)==0L){
			final long[] small = power(m>>1);
			return productOfPolynomialModulo(small,small);
		}
		return productOfPolynomialModulo(power(m-1),arr);
	}
	private long[] productOfPolynomialModulo(final long[] arr1,final long[] arr2){
		final BigInteger[] mult = new BigInteger[Math.min(arr1.length+arr2.length-1,r)];
		for(int i=0;i<mult.length;i++)
			mult[i] = BigInteger.ZERO;
		for(int i=0;i<arr1.length;i++)
			for(int j=0;j<arr2.length;j++)
				mult[(i+j)%r] = mult[(i+j)%r].add(BigInteger.valueOf(arr1[i]).multiply(BigInteger.valueOf(arr2[j])));
		int len = mult.length;
		final BigInteger mod = BigInteger.valueOf(n);
		for(int i=len-1;i>=0;i--){
			mult[i] = mult[i].mod(mod);
			if(mult.length-1==len&&mult.equals(BigInteger.ZERO))
				len--;
		}
		final long[] ans = new long[len];
		for(int i=0;i<len;i++)
			ans[i] = mult[i].longValue();
		return ans;
	}
}