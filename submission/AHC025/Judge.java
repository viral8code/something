import java.io.*;
import java.util.Arrays;
import java.util.stream.LongStream;
import java.math.BigInteger;
class Judge extends Thread{
	private static LongStream stream;
	private static File[] files;
	public static void main(String[] args)throws IOException,InterruptedException{

		files = new File("in").listFiles();

		if(files==null){
			System.err.println("Can't find any file");
			System.exit(1);
		}

		var process = new Judge();
		long s = System.nanoTime();
		process.run();
		process.join();

		if(stream!=null){
			long[] array = stream.toArray();
			System.out.println("finished!");
			long sum = 0;
			long max = 0;
			long min = Long.MAX_VALUE;
			for(long num:array){
				sum += num;
				max = Math.max(max,num);
				min = Math.min(min,num);
			}
			System.out.println("average:\t"+(double)sum/files.length);
			System.out.println("max:\t\t"+max);
			System.out.println("min:\t\t"+min);
			System.out.println("sum:\t\t"+sum);
			System.out.println("judge time:\t"+(System.nanoTime()-s)/1e9+"s");
		}
	}
	public void run(){
		stream = Arrays.stream(files)
			.parallel()
			.mapToLong(file->{
				if(!file.getName().endsWith(".txt")){
					System.out.println(file.getName()+":ignored");
					return -1;
				}
				Test test = new Test(file);
				test.start();
				try{
					test.join();
				}catch(Exception e){
					e.printStackTrace();
					return -1;
				}
				String scoreLabel;
				if(test.score==-2){
					scoreLabel = "0(Over Request)";
				}
				else if(test.score==-1){
					scoreLabel = "0(Illegal Output)";
				}
				else{
					scoreLabel = String.valueOf(test.score);
				}
				System.out.println(file.getName()+":"+scoreLabel+"("+(long)(test.time/1e6)+"ms)");
				return test.score;
			})
			.filter(i->i>-1);
	}

}
class Test extends Thread{
	private final File file;
	private final ProcessBuilder pb = new ProcessBuilder(new String[]{
			"java",
			"Main"
		});;
	public int N,D,Q;
	private int[] w;
	public long score;
	public long time;
	public Test(File f){
		file = f;
	}
	public void run(){
		Process process = null;
		try{
			process = pb.start();
			var out = new PrintStream(process.getOutputStream(),true);
			try(
				var in = new Scanner(process.getInputStream());
			){
				long s = System.nanoTime();
				init(in,out);
				calculate(in,out);
				process.waitFor();
				long t = System.nanoTime();
				time = t-s;
				score = score(in,out);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(process!=null)
				process.destroyForcibly();
		}
	}
	private void init(Scanner sc,PrintStream out)throws FileNotFoundException{
		var in = new Scanner(new FileInputStream(file));
		N = in.nextInt();
		D = in.nextInt();
		Q = in.nextInt();
		w = new int[N];
		for(int i=0;i<N;i++){
			w[i] = in.nextInt();
		}
		in.close();
		out.println(N+" "+D+" "+Q);
	}
	private void calculate(Scanner sc,PrintStream out)throws Exception{
		while(Q-->0){
			int nL = sc.nextInt();
			int nR = sc.nextInt();
			long sumL = 0;
			while(nL-->0){
				sumL += sc.nextInt();
			}
			long sumR = 0;
			while(nR-->0){
				sumR += sc.nextInt();
			}
			out.println(sumL<sumR?"<":sumL>sumR?">":"=");
		}
	}
	private long score(Scanner sc,PrintStream out)throws Exception{
		long[] sum = new long[D];
		for(int i=0;i<N;i++){
			sum[sc.nextInt()] += w[i];
		}
		RationalNumber average = RationalNumber.ZERO;
		for(long num:sum){
			average = average.add(RationalNumber.valueOf(num));
		}
		average = average.divide(RationalNumber.valueOf(D));
		RationalNumber variance = RationalNumber.ZERO;
		for(long num:sum){
			variance = variance.add(average.subtract(RationalNumber.valueOf(num)).pow(2));
		}
		variance = variance.divide(RationalNumber.valueOf(D));
		RationalNumber s = variance.sqrt(2).movePointRight(2);
		return s.longValue()+1;
	}
}

/**
 * RationalNumberクラスは任意精度の有理数を扱う不変クラスです。
 *
 * @author viral
 */
final class RationalNumber
		extends Number
		implements Comparable<RationalNumber>, Cloneable {

	/**
	 * 分子を表すBigIntegerです。
	 */
	final BigInteger numerator;
	/**
	 * 分母を表すBigIntegerです。
	 */
	final BigInteger denominator;

	/**
	 * 1/1と同等のRationalNumberです。
	 */
	public static final RationalNumber ONE = new RationalNumber( BigInteger.ONE, BigInteger.ONE );

	/**
	 * 10/1と同等のRationalNumberです。
	 */
	public static final RationalNumber TEN = new RationalNumber( BigInteger.TEN, BigInteger.ONE );

	/**
	 * 0/1と同等のRationalNumberです。
	 */
	public static final RationalNumber ZERO = new RationalNumber( BigInteger.ZERO, BigInteger.ONE );

	/**
	 * n/dの既約分数と同等のRationalNumberを生成します。
	 *
	 * @param n 分子
	 * @param d 分母
	 */
	public RationalNumber ( long n, long d ) {
		this( BigInteger.valueOf( n ), BigInteger.valueOf( d ) );
	}

	/**
	 * n/dの既約分数と同等のRationalNumberを生成します。
	 * dが0の場合はArithmeticExceptionをthrowします。
	 *
	 * @param n 分子
	 * @param d 分母
	 */
	public RationalNumber ( BigInteger n, BigInteger d ) {
		if ( d.equals( BigInteger.ZERO ) ) {
			throw new ArithmeticException( "/ by zero" );
		}
		BigInteger g = n.gcd( d );
		if ( g.equals( BigInteger.ZERO ) ) {
			numerator = BigInteger.ZERO;
			denominator = BigInteger.ONE;
		}
		else {
			n = n.divide( g );
			d = d.divide( g );
			if ( d.compareTo( BigInteger.ZERO ) < 0 ) {
				denominator = d.negate();
				numerator = n.negate();
			}
			else {
				denominator = d;
				numerator = n;
			}
		}
	}

	/**
	 * Stringを10進数の小数と解釈した時のRationalNumberを生成します。
	 *
	 * @param decimalString 10進数表記での小数を表す文字列
	 */
	public RationalNumber ( String decimalString ) {
		this( decimalString, 10 );
	}

	/**
	 * Stringを指定された進数の小数と解釈した時のRationalNumberを生成します。
	 *
	 * @param str 指定された進数表記での小数を表す文字列
	 * @param radix 進数
	 */
	public RationalNumber ( String str, int radix ) {
		BigInteger n, d;
		if ( str.contains( "." ) ) {
			n = new BigInteger( str.replace( ".", "" ), radix );
			d = BigInteger.valueOf( radix ).pow( str.length() - str.indexOf( "." ) - 1 );
			BigInteger g = n.gcd( d );
			if ( g.equals( BigInteger.ZERO ) ) {
				numerator = BigInteger.ZERO;
				denominator = BigInteger.ONE;
			}
			else {
				n = n.divide( g );
				d = d.divide( g );
				if ( d.compareTo( BigInteger.ZERO ) < 0 ) {
					denominator = d.negate();
					numerator = n.negate();
				}
				else {
					denominator = d;
					numerator = n;
				}
			}
		}
		else {
			numerator = new BigInteger( str, radix );
			denominator = BigInteger.ONE;
		}
	}

	/**
	 * 引数と等価なRationalNumberを返します。
	 *
	 * @param num
	 *
	 * @return 引数と等価なRationalNumber
	 */
	public static RationalNumber valueOf ( double num ) {
		long n = Double.doubleToLongBits( num );
		long value = ( ( ( 1L << 52 ) - 1 ) & n ) | ( 1L << 52 );
		long exp = 1075 - ( ( ( 1 << 11 ) - 1 ) & ( n >> 52 ) );
		BigInteger nn = BigInteger.valueOf( value );
		BigInteger dd = BigInteger.ONE.shiftLeft( ( int )exp );
		if ( num < 0 ) {
			nn = nn.negate();
		}
		return new RationalNumber( nn, dd );
	}

	/**
	 * 引数と等価なRationalNumberを返します。
	 *
	 * @param num
	 *
	 * @return 引数と等価なRationalNumber
	 */
	public static RationalNumber valueOf ( long num ) {
		BigInteger n = BigInteger.valueOf( num );
		BigInteger d = BigInteger.ONE;
		return new RationalNumber( n, d );
	}

	/**
	 * このRationalNumberに引数のRationalNumberを加算した値と等価なRationalNumberを返します。
	 *
	 * @param rn 加数
	 *
	 * @return このRationalNumberに引数を加算したRationalNumber
	 */
	public RationalNumber add ( RationalNumber rn ) {
		BigInteger n = numerator.multiply( rn.denominator ).add( rn.numerator.multiply( denominator ) );
		BigInteger d = denominator.multiply( rn.denominator );
		return new RationalNumber( n, d );
	}

	/**
	 * このRationalNumberから引数のRationalNumberを減算した値と等価なRationalNumberを返します。
	 *
	 * @param rn 減数
	 *
	 * @return このRationalNumberから引数を減算したRationalNumber
	 */
	public RationalNumber subtract ( RationalNumber rn ) {
		BigInteger n = numerator.multiply( rn.denominator ).subtract( rn.numerator.multiply( denominator ) );
		BigInteger d = denominator.multiply( rn.denominator );
		return new RationalNumber( n, d );
	}

	/**
	 * このRationalNumberに引数のRationalNumberを乗算した値と等価なRationalNumberを返します。
	 *
	 * @param rn 乗数
	 *
	 * @return このRationalNumberに引数を乗算したRationalNumber
	 */
	public RationalNumber multiply ( RationalNumber rn ) {
		BigInteger n = numerator.multiply( rn.numerator );
		BigInteger d = denominator.multiply( rn.denominator );
		return new RationalNumber( n, d );
	}

	/**
	 * このRationalNumberを引数のRationalNumberで除算した値と等価なRationalNumberを返します。
	 *
	 * @param rn 除数
	 *
	 * @return このRationalNumberを引数で除算したRationalNumber
	 */
	public RationalNumber divide ( RationalNumber rn ) {
		BigInteger n = numerator.multiply( rn.denominator );
		BigInteger d = denominator.multiply( rn.numerator );
		return new RationalNumber( n, d );
	}

	/**
	 * this^eと等価なRationalNumberを返します。
	 *
	 * @param e このRationalNumberを累乗する指数
	 *
	 * @return this^e
	 */
	public RationalNumber pow ( long e ) {
		BigInteger baseN;
		BigInteger baseD;
		if ( e < 0 ) {
			baseN = denominator;
			baseD = numerator;
		}
		else {
			baseN = numerator;
			baseD = denominator;
		}
		if ( e == Long.MIN_VALUE ) {
			int count = 63;
			while ( count-- > 0 ) {
				baseN = baseN.multiply( baseN );
				baseD = baseD.multiply( baseD );
			}
			return new RationalNumber( baseN, baseD );
		}
		else {
			e = Math.abs( e );
			BigInteger ansN = BigInteger.ONE;
			BigInteger ansD = BigInteger.ONE;
			while ( e > 0 ) {
				if ( ( e & 1 ) == 1 ) {
					ansN = ansN.multiply( baseN );
					ansD = ansD.multiply( baseD );
				}
				baseN = baseN.multiply( baseN );
				baseD = baseD.multiply( baseD );
				e >>= 1;
			}
			return new RationalNumber( ansN, ansD );
		}
	}

	/**
	 * このRationalNumberの整数平方根を、指定された桁まで求めます。
	 * 整数部は含めず、小数点以下の桁数を指します。
	 *
	 * @param len 求めたい桁数(小数点以下)
	 *
	 * @return このRationalNumberの整数平方根
	 */
	public RationalNumber sqrt ( int len ) {
		if ( this.signum() < 0 ) {
			throw new ArithmeticException( "Negative RationalNumber" );
		}
		RationalNumber ans = new RationalNumber( numerator.sqrt(), denominator.sqrt() );
		RationalNumber TWO = new RationalNumber( 2, 1 );
		String before = ans.toDecimalString( len );
		for ( int i = 0; i < 1000; i++ ) {
			ans = ans.subtract( ans.multiply( ans ).subtract( this ).divide( ans.multiply( TWO ) ) );
			String str = ans.toDecimalString( len );
			if ( before.equals( str ) ) {
				break;
			}
			before = str;
		}
		return ans;
	}

	/**
	 * このRationalNumberの絶対値を表すRationalNumberを返します。
	 *
	 * @return このRationalNumberの絶対値
	 */
	public RationalNumber abs () {
		return new RationalNumber( numerator.abs(), denominator );
	}

	/**
	 * このRationalNumberの正負を入れ替えたRationalNumberを返します。
	 *
	 * @return このRationalNumberの正負を入れ替えたRationalNumber
	 */
	public RationalNumber negate () {
		return new RationalNumber( numerator.negate(), denominator );
	}

	/**
	 * このRationalNumberをn/dと表した時のnをdで割った商と剰余を表すBigInteger配列を返します。
	 *
	 * @return 2つのBigIntegerの配列。商が最初の要素で、剰余が最後の要素。
	 */
	public BigInteger[] divideAndRemainder () {
		BigInteger[] ans = new BigInteger[2];
		ans[0] = toBigInteger();
		ans[1] = numerator.mod( denominator );
		return ans;
	}

	/**
	 * このRationalNumberの符号要素を返します。
	 *
	 * @return このRationalNumberが負の場合は-1、ゼロの場合は0、正の場合は1
	 */
	public int signum () {
		return numerator.signum();
	}

	/**
	 * このRationalNumberの逆数を表すRationalNumberを返します。
	 *
	 * @return このRationalNumberの逆数
	 */
	public RationalNumber inverse () {
		return new RationalNumber( denominator, numerator );
	}

	/**
	 * 小数点を指定されただけ左にずらします。
	 *
	 * @param n 小数点を左へ移動する桁数
	 *
	 * @return このRationalNumberの小数点をn桁だけ左にずらしたRationalNumber
	 */
	public RationalNumber movePointLeft ( int n ) {
		RationalNumber base = TEN.inverse().pow( n );
		return multiply( base );
	}

	/**
	 * 小数点を指定されただけ右にずらします。
	 *
	 * @param n 小数点を右へ移動する桁数
	 *
	 * @return このRationalNumberの小数点をn桁だけ右にずらしたRationalNumber
	 */
	public RationalNumber movePointRight ( int n ) {
		RationalNumber base = TEN.pow( n );
		return multiply( base );
	}

	/**
	 * このRationalNumberを表す文字列を返します。
	 * これは「numerator.toString() + '/' + denominator.toString()」に等しいです。
	 *
	 * @return このRationalNumberを表す文字列
	 */
	@Override
	public String toString () {
		return numerator.toString() + '/' + denominator.toString();
	}

	/**
	 * このRationalNumberを表す小数点第n桁までの小数を表す文字列を返します。
	 * もしこのRationalNumberが小数点第n桁よりも少ない桁数で表せる場合はその桁までの文字列になります。
	 * 例えば、1/4はnを3以上にしても全て"0.25"になります。
	 *
	 * @param n 桁数
	 *
	 * @return このRationalNumberを表す小数点第n桁までの小数を表す文字列
	 */
	public String toDecimalString ( int n ) {
		StringBuilder ans = new StringBuilder();
		ans.append( numerator.divide( denominator ) );
		BigInteger num = numerator.abs().mod( denominator );
		if ( n > 0 && num.compareTo( BigInteger.ZERO ) > 0 ) {
			ans.append( '.' );
		}
		for ( int i = 0; i < n; i++ ) {
			int count = 0;
			num = num.multiply( BigInteger.TEN );
			while ( num.compareTo( denominator ) >= 0 ) {
				num = num.subtract( denominator );
				count++;
			}
			ans.append( count );
			if ( num.equals( BigInteger.ZERO ) ) {
				break;
			}
		}
		return ans.toString();
	}

	/**
	 * このRationalNumberの整数部を表すBigIntegerを返します。
	 *
	 * @return このRationalNumberの整数部を表すBigInteger
	 */
	public BigInteger toBigInteger () {
		return numerator.divide( denominator );
	}

	/**
	 * このRationalNumberを引数と比較します。
	 *
	 * @param rn 被比較数
	 *
	 * @return このRationalNumberの方が大きいときは1、引数の方が大きいときは-1、等しいときは0
	 */
	@Override
	public int compareTo ( RationalNumber rn ) {
		if ( equals( rn ) ) {
			return 0;
		}
		BigInteger num1 = toBigInteger();
		BigInteger num2 = rn.toBigInteger();
		int result = num1.compareTo( num2 );
		if ( result != 0 ) {
			return result;
		}
		BigInteger n1 = numerator.mod( denominator );
		BigInteger n2 = rn.numerator.mod( rn.denominator );
		while ( true ) {
			int count1 = 0;
			n1 = n1.multiply( BigInteger.TEN );
			while ( n1.compareTo( denominator ) >= 0 ) {
				n1 = n1.subtract( denominator );
				count1++;
			}
			int count2 = 0;
			n2 = n2.multiply( BigInteger.TEN );
			while ( n2.compareTo( rn.denominator ) >= 0 ) {
				n2 = n2.subtract( rn.denominator );
				count2++;
			}
			if ( count1 != count2 ) {
				return Integer.signum( count1 - count2 );
			}
		}
	}

	/**
	 * このRationalNumberを表すハッシュ値を返します。
	 *
	 * @return このRationalNumberを表すハッシュ値
	 */
	@Override
	public int hashCode () {
		return ( numerator.hashCode() << 4 ) ^ denominator.hashCode();
	}

	/**
	 * このRationalNumberのクローンを生成して返します。
	 *
	 * @return このRationalNumberのクローン
	 */
	@Override
	public RationalNumber clone () {
		try {
			return ( RationalNumber )super.clone();
		} catch ( CloneNotSupportedException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * 引数がこのRationalNUmberと等しいか返します。
	 *
	 * @param o 比較対象
	 *
	 * @return 等しいときはtrue、異なるときはfalse
	 */
	@Override
	public boolean equals ( Object o ) {
		if ( o instanceof RationalNumber ) {
			RationalNumber rn = ( RationalNumber )o;
			return numerator.equals( rn.numerator ) && denominator.equals( rn.denominator );
		}
		return false;
	}

	/**
	 * このRationalNumberをdoubleに変換したときの値を返します(誤差が出る可能性があります)。
	 *
	 * @return このRationalNumberをdoubleに変換したときの値
	 */
	@Override
	public double doubleValue () {
		return Double.parseDouble( toDecimalString( 16 ) );
	}

	/**
	 * このRationalNumberをfloatに変換したときの値を返します(誤差が出る可能性があります)。
	 *
	 * @return このRationalNumberをfloatに変換したときの値
	 */
	@Override
	public float floatValue () {
		return Float.parseFloat( toDecimalString( 8 ) );
	}

	/**
	 * このRationalNumberをintに変換したときの値を返します(誤差が出る可能性があります)。
	 * オーバーフローする場合は下位32bitのみで変換を行なうため、正負が等しくない場合があります。
	 * これはtoBigInteger().intValue()と等価です。
	 *
	 * @return このRationalNumberをintに変換したときの値
	 */
	@Override
	public int intValue () {
		return toBigInteger().intValue();
	}

	/**
	 * このRationalNumberをlongに変換したときの値を返します(誤差が出る可能性があります)。
	 * オーバーフローする場合は下位64bitのみで変換を行なうため、正負が等しくない場合があります。
	 * これはtoBigInteger().longValue()と等価です。
	 *
	 * @return このRationalNumberをlongに変換したときの値
	 */
	@Override
	public long longValue () {
		return toBigInteger().longValue();
	}
}

// MyScanner
final class Scanner implements AutoCloseable{
	final private int buff_size = 1 << 12;
	private final InputStream is;
	private final byte[] buff;
	private int point, length;
	public Scanner ( InputStream is ) {
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private void reload () {
		do {
			try {
				length = is.read( buff, point = 0, buff_size );
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
	public int nextInt () {
		int ans = 0;
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		boolean negate = c == '-';
		if ( c == '-' ) {
			c = read();
		}
		while ( '0' <= c && c <= '9' ) {
			ans = ans * 10 + c - '0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public long nextLong () {
		long ans = 0;
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		boolean negate = c == '-';
		if ( c == '-' ) {
			c = read();
		}
		while ( '0' <= c && c <= '9' ) {
			ans = ans * 10 + c - '0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public String next () {
		StringBuilder ans = new StringBuilder();
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		while ( c > ' ' ) {
			ans.append( ( char )c );
			c = read();
		}
		return ans.toString();
	}
	public void close () {
		try {
			is.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}