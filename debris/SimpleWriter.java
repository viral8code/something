import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Arrays;
final class SimpleWriter extends Writer {
	private final int buffSize = 1 << 20;
	private final OutputStreamWriter os;
	private final char[] buff;
	private int pointer;
	private final boolean autoFlush;
	private final int[] baseInt = {
		1,
		10,
		100,
		1000,
		10000,
		100000,
		1000000,
		10000000,
		100000000,
		1000000000
	};
	private final long[] baseLong = {
		1L,
		10L,
		100L,
		1000L,
		10000L,
		100000L,
		1000000L,
		10000000L,
		100000000L,
		1000000000L,
		10000000000L,
		100000000000L,
		1000000000000L,
		10000000000000L,
		100000000000000L,
		1000000000000000L,
		10000000000000000L,
		100000000000000000L,
		1000000000000000000L
	};
 
	public SimpleWriter ( OutputStream out ) {
		this( out, false );
	}
 
	public SimpleWriter ( OutputStream out, boolean autoFlush ) {
		os = new OutputStreamWriter( out );
		buff = new char[buffSize];
		pointer = 0;
		this.autoFlush = autoFlush;
	}
 
	public void println ( int num ) {
		print( num );
		newLine();
	}
 
	public void print ( int num ) {
		int digit = digit( num );
		if ( buffSize <= pointer + digit ) {
			write( buff, 0, pointer );
			pointer = 0;
		}
		char[] cbuff = new char[digit];
		for ( int i = digit - 1; i >= 0; i-- ) {
			int count = 0;
			while ( num >= baseInt[i] ) {
				num -= baseInt[i];
				count++;
			}
			cbuff[digit - i - 1] = ( char )( '0' + count );
		}
		writeBuff( cbuff );
		if ( autoFlush ) {
			flush();
		}
	}
 
	public void println ( int[] array ) {
		print( array, false );
		newLine();
	}
 
	public void println ( int[] array, boolean useSquareBrackets ) {
		print( array, useSquareBrackets );
		newLine();
	}
 
	public void println ( int[] array, String str ) {
		print( array, str );
		newLine();
	}
 
	public void println ( int[] array, char c ) {
		print( array, c );
		newLine();
	}
 
	public void print ( int[] array ) {
		print( array, false );
		if ( autoFlush ) {
			flush();
		}
	}
 
	public void print ( int[] array, boolean useSquareBrackets ) {
		if ( useSquareBrackets ) {
			print( Arrays.toString( array ) );
		}
		else {
			print( array[0] );
			for ( int i = 1; i < array.length; i++ ) {
				print( ' ' );
				print( array[i] );
			}
		}
	}
 
	public void print ( int[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( str );
			print( array[i] );
		}
	}
 
	public void print ( int[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( c );
			print( array[i] );
		}
	}
 
	public void println ( long num ) {
		print( num );
		newLine();
	}
 
	public void print ( long num ) {
		int digit = digit( num );
		if ( buffSize <= pointer + digit ) {
			write( buff, 0, pointer );
			pointer = 0;
		}
		char[] cbuff = new char[digit];
		for ( int i = digit - 1; i >= 0; i-- ) {
			int count = 0;
			while ( num >= baseLong[i] ) {
				num -= baseLong[i];
				count++;
			}
			cbuff[digit - i - 1] = ( char )( '0' + count );
		}
		writeBuff( cbuff );
		if ( autoFlush ) {
			flush();
		}
	}
 
	public void println ( long[] array ) {
		print( array, false );
		newLine();
	}
 
	public void println ( long[] array, boolean useSquareBrackets ) {
		print( array, useSquareBrackets );
		newLine();
	}
 
	public void println ( long[] array, String str ) {
		print( array, str );
		newLine();
	}
 
	public void println ( long[] array, char c ) {
		print( array, c );
		newLine();
	}
 
	public void print ( long[] array ) {
		print( array, false );
	}
 
	public void print ( long[] array, boolean useSquareBrackets ) {
		if ( useSquareBrackets ) {
			print( Arrays.toString( array ) );
		}
		else {
			print( array[0] );
			for ( int i = 1; i < array.length; i++ ) {
				print( ' ' );
				print( array[i] );
			}
		}
	}
 
	public void print ( long[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( str );
			print( array[i] );
		}
	}
 
	public void print ( long[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( c );
			print( array[i] );
		}
	}
 
	public void println ( char c ) {
		print( c );
		newLine();
	}
 
	public void print ( char c ) {
		if ( buffSize == pointer ) {
			write( buff, 0, pointer );
			pointer = 0;
		}
		buff[pointer++] = c;
		if ( autoFlush ) {
			flush();
		}
	}
 
	public void println ( char[] chars ) {
		print( chars );
		newLine();
	}
 
	public void print ( char[] chars ) {
		if ( buffSize <= pointer + chars.length ) {
			write( buff, 0, pointer );
			pointer = 0;
		}
		writeBuff( chars );
		if ( autoFlush ) {
			flush();
		}
	}
 
	public <E> void println ( E value ) {
		print( value.toString() );
		newLine();
	}
 
	public <E> void print ( E value ) {
		print( value.toString() );
	}
 
	public <E> void println ( E[] array ) {
		print( array, false );
		newLine();
	}
 
	public <E> void println ( E[] array, boolean useSquareBrackets ) {
		print( array, useSquareBrackets );
		newLine();
	}
 
	public <E> void println ( E[] array, String str ) {
		print( array, str );
		newLine();
	}
 
	public <E> void println ( E[] array, char c ) {
		print( array, c );
		newLine();
	}
 
	public <E> void print ( E[] array ) {
		print( array, false );
	}
 
	public <E> void print ( E[] array, boolean useSquareBrackets ) {
		if ( useSquareBrackets ) {
			print( Arrays.toString( array ) );
		}
		else {
			print( array[0] );
			for ( int i = 1; i < array.length; i++ ) {
				print( ' ' );
				print( array[i] );
			}
		}
	}
 
	public <E> void print ( E[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( str );
			print( array[i] );
		}
	}
 
	public <E> void print ( E[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( c );
			print( array[i] );
		}
	}
 
	public void print ( String str ) {
		int len = str.length();
		if ( buffSize <= pointer + len ) {
			write( buff, 0, pointer );
			pointer = 0;
		}
		writeBuff( str.toCharArray() );
		if ( autoFlush ) {
			flush();
		}
	}
 
	public void println () {
		newLine();
	}
 
	public void newLine () {
		if ( buffSize == pointer ) {
			write( buff, 0, pointer );
			pointer = 0;
		}
		buff[pointer++] = '\n';
		if ( autoFlush ) {
			flush();
		}
	}
 
	private void writeBuff ( char[] cbuff ) {
		if ( buffSize <= cbuff.length + pointer ) {
			write( buff, 0, pointer );
			write( cbuff, 0, cbuff.length );
			pointer = 0;
		}
		else {
			System.arraycopy( cbuff, 0, buff, pointer, cbuff.length );
			pointer += cbuff.length;
		}
	}
 
	@Override
	public void write ( char[] cbuff, int from, int len ) {
		try {
			os.write( cbuff, from, len );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
 
	@Override
	public void flush () {
		write( buff, 0, pointer );
		pointer = 0;
		try {
			os.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
 
	@Override
	public void close () {
		flush();
		try {
			os.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
 
	private static int digit ( int num ) {
		if ( num < 10 ) {
			return 1;
		}
		if ( num < 100 ) {
			return 2;
		}
		if ( num < 1000 ) {
			return 3;
		}
		if ( num < 10000 ) {
			return 4;
		}
		if ( num < 100000 ) {
			return 5;
		}
		if ( num < 1000000 ) {
			return 6;
		}
		if ( num < 10000000 ) {
			return 7;
		}
		if ( num < 100000000 ) {
			return 8;
		}
		if ( num < 1000000000 ) {
			return 9;
		}
		return 10;
	}
 
	private static int digit ( long num ) {
		if ( num < 10L ) {
			return 1;
		}
		if ( num < 100L ) {
			return 2;
		}
		if ( num < 1000L ) {
			return 3;
		}
		if ( num < 10000L ) {
			return 4;
		}
		if ( num < 100000L ) {
			return 5;
		}
		if ( num < 1000000L ) {
			return 6;
		}
		if ( num < 10000000L ) {
			return 7;
		}
		if ( num < 100000000L ) {
			return 8;
		}
		if ( num < 1000000000L ) {
			return 9;
		}
		if ( num < 10000000000L ) {
			return 10;
		}
		if ( num < 100000000000L ) {
			return 11;
		}
		if ( num < 1000000000000L ) {
			return 12;
		}
		if ( num < 10000000000000L ) {
			return 13;
		}
		if ( num < 100000000000000L ) {
			return 14;
		}
		if ( num < 1000000000000000L ) {
			return 15;
		}
		if ( num < 10000000000000000L ) {
			return 16;
		}
		if ( num < 100000000000000000L ) {
			return 17;
		}
		if ( num < 1000000000000000000L ) {
			return 18;
		}
		return 19;
	}
}
