import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.awt.Dimension;
class Main{
	public static void main(String[] args){
		SimpleScanner sc = new SimpleScanner(System.in);
		int A = sc.nextInt();
		long X = sc.nextLong();
		int M = sc.nextInt();
		Matrix mat1 = new Matrix(new long[][]{
			{0},
			{1}
		});
		Matrix mat2 = new Matrix(new long[][]{
			{A,1},
			{0,1}
		});
		Matrix ans = Matrix.modPow(mat1,mat2,X,M);
		System.out.println(ans.get(0,0));
	}
}
final class Matrix{
	private long[][] matrix;
	public Matrix(int H,int W,long def){
		matrix = new long[H][W];
		if(def!=0)
			for(long[] mat:matrix)
				Arrays.fill(mat,def);
	}
	public Matrix(int H,int W){
		this(H,W,0);
	}
	public Matrix(Dimension d,long def){
		this(d.height,d.width,def);
	}
	public Matrix(long[][] mat){
		matrix = new long[mat.length][];
		for(int i=0;i<mat.length;i++)
			matrix[i] = Arrays.copyOf(mat[i],mat[i].length);
	}
	public long get(int i,int j){
		return matrix[i][j];
	}
	public long set(int i,int j,long value){
		return matrix[i][j] = value;
	}
	public Matrix copy(){
		return new Matrix(matrix);
	}
	public Dimension size(){
		return new Dimension(matrix[0].length,matrix.length);
	}
	public Matrix add(Matrix m){
		if(!size().equals(m.size()))
			throw new IllegalArgumentException("matrix size is not same");
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[i].length;j++)
				matrix[i][j] += m.get(i,j);
		return this;
	}
	public Matrix subtract(Matrix m){
		if(!size().equals(m.size()))
			throw new IllegalArgumentException("matrix size is not same");
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[i].length;j++)
				matrix[i][j] -= m.get(i,j);
		return this;
	}
	public Matrix multiply(Matrix m){
		if(size().width!=m.size().height)
			throw new IllegalArgumentException("matrix length is not same");
		Matrix ans = new Matrix(size().height,m.size().width);
		Dimension size = ans.size();
		int len = size().width;
		for(int i=0;i<size.height;i++)
			for(int j=0;j<size.width;j++){
				long sum = 0;
				for(int k=0;k<len;k++)
					sum += matrix[i][k]*m.get(k,j);
				ans.set(i,j,sum);
			}
		return ans;
	}
	public Matrix modAdd(Matrix m,long mod){
		if(!size().equals(m.size()))
			throw new IllegalArgumentException("matrix size is not same");
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[i].length;j++)
				matrix[i][j] = remainder(matrix[i][j]+m.get(i,j),mod);
		return this;
	}
	public Matrix modSubtract(Matrix m,long mod){
		if(!size().equals(m.size()))
			throw new IllegalArgumentException("matrix size is not same");
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[i].length;j++)
				matrix[i][j] = remainder(matrix[i][j]-m.get(i,j),mod);
		return this;
	}
	public Matrix modMultiply(Matrix m,long mod){
		if(size().width!=m.size().height)
			throw new IllegalArgumentException("matrix length is not same");
		Matrix ans = new Matrix(size().height,m.size().width);
		Dimension size = ans.size();
		int len = size().width;
		for(int i=0;i<size.height;i++)
			for(int j=0;j<size.width;j++){
				long sum = 0;
				for(int k=0;k<len;k++)
					sum = remainder(sum+matrix[i][k]*m.get(k,j),mod);
				ans.set(i,j,sum);
			}
		return ans;
	}
	public static Matrix pow(Matrix original,Matrix pw,long exp){
		Matrix b = pw.copy();
		while(0<exp){
			if((exp&1)==1)
				original = b.multiply(original);
			b = b.multiply(b);
			exp >>= 1;
		}
		return original;
	}
	public static Matrix modPow(Matrix original,Matrix pw,long exp,long mod){
		Matrix b = pw.copy();
		while(0<exp){
			if((exp&1)==1)
				original = b.modMultiply(original,mod);
			b = b.modMultiply(b,mod);
			exp >>= 1;
		}
		return original;
	}
	private static long remainder(long num,long mod){
		num %= mod;
		if(num<0)
			num += mod;
		return num;
	}
	@Override
	public String toString(){
		StringBuilder ans = new StringBuilder();
		ans.append(Arrays.toString(matrix[0]));
		for(int i=1;i<matrix.length;i++){
			ans.append("\n");
			ans.append(Arrays.toString(matrix[i]));
		}
		return ans.toString();
	}
}
final class SimpleScanner {
	final private int buff_size = 1 << 15;
	private final InputStream is;
	private final byte[] buff;
	private int point, length;

	public SimpleScanner ( InputStream is ) {
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

	public byte nextByte () {
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		return c;
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

	public char nextChar () {
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		return ( char )c;
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

	public byte[] nextByte ( int n ) {
		byte[] ans = new byte[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextByte();
		}
		return ans;
	}

	public int[] nextInt ( int n ) {
		int[] ans = new int[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextInt();
		}
		return ans;
	}

	public long[] nextLong ( int n ) {
		long[] ans = new long[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextLong();
		}
		return ans;
	}

	public String[] next ( int n ) {
		String[] ans = new String[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = next();
		}
		return ans;
	}

	public byte[][] nextByte ( int n, int m ) {
		byte[][] ans = new byte[n][];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextByte( m );
		}
		return ans;
	}

	public int[][] nextInt ( int n, int m ) {
		int[][] ans = new int[n][];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextInt( m );
		}
		return ans;
	}

	public long[][] nextLong ( int n, int m ) {
		long[][] ans = new long[n][];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextLong( m );
		}
		return ans;
	}

	public String[][] next ( int n, int m ) {
		String[][] ans = new String[n][];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = next( m );
		}
		return ans;
	}

	public char[] nextCharArray () {
		return next().toCharArray();
	}

	public char[][] nextCharArray ( int n ) {
		char[][] ans = new char[n][];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextCharArray();
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
