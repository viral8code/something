import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.awt.Point;
import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

final class Main {

	private static final boolean autoFlush = false;
	private static final SimpleScanner sc = new SimpleScanner( System.in );
	private static final SimplePrinter out = new SimplePrinter( System.out, autoFlush );

	public static void main ( String[] args ) {

		char[] S = sc.nextCharArray();
		for(int i=1;i<=S.length/2;i++){
			char c = S[2*i-2];
			S[2*i-2] = S[2*i-1];
			S[2*i-1] = c;
		}
		out.println(S);

		out.close();
	}
}

/*////////////////////////////////////////////////
	* My Library *

	@author viral
*/////////////////////////////////////////////////
final class Factorial {

	//階乗とその逆元
	private final long[] fact, inFact;
	private final long mod;

	/**
	 * 1～Nの階乗とその逆元をmodで割ったあまりを事前に計算します。
	 *
	 * @param N 計算範囲
	 * @param mod 法
	 */
	public Factorial ( int N, long mod ) {
		fact = new long[N + 1];
		fact[0] = fact[1] = 1;
		for ( int i = 2; i <= N; i++ ) {
			fact[i] = fact[i - 1] * i % mod;
		}

		inFact = new long[N + 1];
		inFact[N] = MathFunction.modPow( fact[N], mod - 2, mod );
		for ( int i = N; i > 0; i-- ) {
			inFact[i - 1] = inFact[i] * i % mod;
		}
		inFact[0] = 1;

		this.mod = mod;
	}

	/**
	 * num!をmodで割ったあまりを返します。
	 *
	 * @param num
	 *
	 * @return num!
	 */
	public long getFact ( int num ) {
		return fact[num];
	}

	/**
	 * aCbをmodで割ったあまりを返します。
	 *
	 * @param a
	 * @param b
	 *
	 * @return aCb
	 */
	public long getCombi ( int a, int b ) {
		if ( a < b || a <= 0 || b <= 0 ) {
			throw new IllegalArgumentException( "Factorial's index must be positive" );
		}
		return ( fact[a] * inFact[a - b] % mod ) * inFact[b] % mod;
	}
}

final class ArrayFunction {

	/**
	 * int型配列をソートします。
	 *
	 * @param array ソートする配列
	 */
	public static void sort ( int[] array ) {
		for ( int i = 0; i < array.length; i++ ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2] < array[j] ) {
				int temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; i-- ) {
			int temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j] < array[2 * j + 1] ) || ( 2 * j + 2 < i - 1 && array[j] < array[2 * j + 2] ) ) {
				if ( 2 * j + 2 == i - 1 || array[2 * j + 1] > array[2 * j + 2] ) {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					j++;
				}
				else {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
			}
		}
	}

	/**
	 * int型配列をソートします。
	 * 序列は配列を一つの文字列として見たときの辞書順と等しいです。
	 *
	 * @param arrays ソートする配列
	 */
	public static void sort ( int[][] arrays ) {
		for ( int i = 0; i < arrays.length; i++ ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				int[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; i-- ) {
			int[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 == i - 1 || compare( arrays[2 * j + 1], arrays[2 * j + 2] ) > 0 ) {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j++;
				}
				else {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
			}
		}
	}

	/**
	 * long型配列をソートします。
	 *
	 * @param array ソートする配列
	 */
	public static void sort ( long[] array ) {
		for ( int i = 0; i < array.length; i++ ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2] < array[j] ) {
				long temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; i-- ) {
			long temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j] < array[2 * j + 1] ) || ( 2 * j + 2 < i - 1 && array[j] < array[2 * j + 2] ) ) {
				if ( 2 * j + 2 == i - 1 || array[2 * j + 1] > array[2 * j + 2] ) {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					j++;
				}
				else {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
			}
		}
	}

	/**
	 * long型配列をソートします。
	 * 序列は配列を一つの文字列として見たときの辞書順と等しいです。
	 *
	 * @param arrays ソートする配列
	 */
	public static void sort ( long[][] arrays ) {
		for ( int i = 0; i < arrays.length; i++ ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				long[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; i-- ) {
			long[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 == i - 1 || compare( arrays[2 * j + 1], arrays[2 * j + 2] ) > 0 ) {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j++;
				}
				else {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
			}
		}
	}

	/**
	 * 比較可能なクラスの配列をソートします。
	 *
	 * @param array ソートする配列
	 */
	public static <E extends Comparable<E>> void sort ( E[] array ) {
		for ( int i = 0; i < array.length; i++ ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2].compareTo( array[j] ) < 0 ) {
				E temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; i-- ) {
			E temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j].compareTo( array[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && array[j].compareTo( array[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 == i - 1 || array[2 * j + 1].compareTo( array[2 * j + 2] ) > 0 ) {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					j++;
				}
				else {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
			}
		}
	}

	/**
	 * 比較可能なクラスの配列をソートします。
	 * 序列は配列を一つの文字列として見たときの辞書順と等しいです。
	 *
	 * @param arrays ソートする配列
	 */
	public static <E extends Comparable<E>> void sort ( E[][] arrays ) {
		for ( int i = 0; i < arrays.length; i++ ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				E[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; i-- ) {
			E[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 == i - 1 || compare( arrays[2 * j + 1], arrays[2 * j + 2] ) > 0 ) {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j++;
				}
				else {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
			}
		}
	}

	/**
	 * int型配列を比較します。
	 *
	 * @param a
	 * @param b
	 *
	 * @return a.compareTo(b)として想定されるint型戻り値
	 */
	private static int compare ( int[] a, int[] b ) {
		int len = Math.min( a.length, b.length );
		for ( int i = 0; i < len; i++ ) {
			if ( a[i] > b[i] ) {
				return 1;
			}
			if ( a[i] < b[i] ) {
				return -1;
			}
		}
		return Integer.compare( a.length, b.length );
	}

	/**
	 * long型配列を比較します。
	 *
	 * @param a
	 * @param b
	 *
	 * @return a.compareTo(b)として想定されるint型戻り値
	 */
	private static int compare ( long[] a, long[] b ) {
		int len = Math.min( a.length, b.length );
		for ( int i = 0; i < len; i++ ) {
			if ( a[i] > b[i] ) {
				return 1;
			}
			if ( a[i] < b[i] ) {
				return -1;
			}
		}
		return Integer.compare( a.length, b.length );
	}

	/**
	 * 比較可能なクラスの配列を比較します。
	 *
	 * @param a
	 * @param b
	 *
	 * @return a.compareTo(b)として想定されるint型戻り値
	 */
	private static <E extends Comparable<E>> int compare ( E[] a, E[] b ) {
		int len = Math.min( a.length, b.length );
		for ( int i = 0; i < len; i++ ) {
			int result = a[i].compareTo( b[i] );
			if ( result != 0 ) {
				return result;
			}
		}
		return Integer.compare( a.length, b.length );
	}

	/**
	 * カウントソートによるソートです。
	 * 各要素が0以上であり最大値が十分小さい時はこちらの使用を推奨します。
	 *
	 * @param array ソート対象のint型配列
	 * @param maximumLimit array内の最大要素
	 */
	public static void countSort ( int[] array, int maximumLimit ) {
		int[] list = new int[maximumLimit + 1];
		for ( int num: array ) {
			list[num]++;
		}
		int temp = 0;
		for ( int i = 0; i < list.length; i++ ) {
			for ( int j = 0; j < list[i]; j++ ) {
				array[temp++] = i;
			}
		}
	}

	/**
	 * 配列内の指定された要素を探します。
	 * 配列内で見つかった場合はその要素と同一で最大のインデックスを返します。
	 * 見つからなかった場合は指定された要素未満で最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 指定された要素以下で最大のインデックス
	 */
	public static int downSearch ( int[] array, int value ) {
		int a = 0, b = array.length - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素を探します。
	 * 配列内で見つかった場合はその要素と同一で最大のインデックスを返します。
	 * 見つからなかった場合は指定された要素未満で最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 指定された要素以下で最大のインデックス
	 */
	public static int downSearch ( long[] array, long value ) {
		int a = 0, b = array.length - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素を探します。
	 * 配列内で見つかった場合はその要素と同一で最大のインデックスを返します。
	 * 見つからなかった場合は指定された要素未満で最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 指定された要素以下で最大のインデックス
	 */
	public static <E extends Comparable<E>> int downSearch ( E[] array, E value ) {
		int a = 0, b = array.length - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c].compareTo( value ) > 0 ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * リスト内の指定された要素を探します。
	 * リスト内で見つかった場合はその要素と同一で最大のインデックスを返します。
	 * 見つからなかった場合は指定された要素未満で最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param list 探索対象のリスト
	 * @param value 探索要素
	 *
	 * @return 指定された要素以下で最大のインデックス
	 */
	public static <E extends Comparable<E>> int downSearch ( List<E> list, E value ) {
		int a = 0, b = list.size() - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( list.get( c ).compareTo( value ) > 0 ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素を探します。
	 * 配列内で見つかった場合はその要素と同一で最小のインデックスを返します。
	 * 見つからなかった場合は指定された要素以上で最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はarray.lengthを返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 指定された要素以上で最小のインデックス
	 */
	public static int upSearch ( int[] array, int value ) {
		int a = 0, b = array.length - 1, ans = array.length, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] >= value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素を探します。
	 * 配列内で見つかった場合はその要素と同一で最小のインデックスを返します。
	 * 見つからなかった場合は指定された要素以上で最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はarray.lengthを返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 指定された要素以上で最小のインデックス
	 */
	public static int upSearch ( long[] array, long value ) {
		int a = 0, b = array.length - 1, ans = array.length, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] >= value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素を探します。
	 * 配列内で見つかった場合はその要素と同一で最小のインデックスを返します。
	 * 見つからなかった場合は指定された要素以上で最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はarray.lengthを返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 指定された要素以上で最小のインデックス
	 */
	public static <E extends Comparable<E>> int upSearch ( E[] array, E value ) {
		int a = 0, b = array.length - 1, ans = array.length, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c].compareTo( value ) >= 0 ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * リスト内の指定された要素を探します。
	 * リスト内で見つかった場合はその要素と同一で最小のインデックスを返します。
	 * 見つからなかった場合は指定された要素以上で最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はlist.size()を返します。
	 *
	 * @param list 探索対象のリスト
	 * @param value 探索要素
	 *
	 * @return 指定された要素以上で最小のインデックス
	 */
	public static <E extends Comparable<E>> int upSearch ( List<E> list, E value ) {
		int a = 0, b = list.size() - 1, ans = list.size(), c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( list.get( c ).compareTo( value ) >= 0 ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素より小さい要素を探します。
	 * 配列内で見つかった場合は条件を満たす最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最大のインデックス
	 */
	public static int underSearch ( int[] array, int value ) {
		int a = 0, b = array.length - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] >= value ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素より小さい要素を探します。
	 * 配列内で見つかった場合は条件を満たす最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最大のインデックス
	 */
	public static int underSearch ( long[] array, long value ) {
		int a = 0, b = array.length - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] >= value ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素より小さい要素を探します。
	 * 配列内で見つかった場合は条件を満たす最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最大のインデックス
	 */
	public static <E extends Comparable<E>> int underSearch ( E[] array, E value ) {
		int a = 0, b = array.length - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c].compareTo( value ) >= 0 ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * リスト内の指定された要素より小さい要素を探します。
	 * リスト内で見つかった場合は条件を満たす最大のインデックスを返します。
	 * もしそのような要素が存在しない場合は-1を返します。
	 *
	 * @param list 探索対象のリスト
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最大のインデックス
	 */
	public static <E extends Comparable<E>> int underSearch ( List<E> list, E value ) {
		int a = 0, b = list.size() - 1, ans = -1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( list.get( c ).compareTo( value ) >= 0 ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素より大きい要素を探します。
	 * 配列内で見つかった場合は条件を満たす最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はarray.lengthを返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最小のインデックス
	 */
	public static int overSearch ( int[] array, int value ) {
		int a = 0, b = array.length - 1, ans = array.length, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素より大きい要素を探します。
	 * 配列内で見つかった場合は条件を満たす最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はarray.lengthを返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最小のインデックス
	 */
	public static int overSearch ( long[] array, long value ) {
		int a = 0, b = array.length - 1, ans = array.length, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 配列内の指定された要素より大きい要素を探します。
	 * 配列内で見つかった場合は条件を満たす最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はarray.lengthを返します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最小のインデックス
	 */
	public static <E extends Comparable<E>> int overSearch ( E[] array, E value ) {
		int a = 0, b = array.length - 1, ans = array.length, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c].compareTo( value ) > 0 ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * リスト内の指定された要素より大きい要素を探します。
	 * リスト内で見つかった場合は条件を満たす最小のインデックスを返します。
	 * もしそのような要素が存在しない場合はlist.size()を返します。
	 *
	 * @param list 探索対象のリスト
	 * @param value 探索要素
	 *
	 * @return 条件を満たす最小のインデックス
	 */
	public static <E extends Comparable<E>> int overSearch ( List<E> list, E value ) {
		int a = 0, b = list.size() - 1, ans = list.size(), c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( list.get( c ).compareTo( value ) > 0 ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 引数の配列の最長狭義増加部分列の長さを返します。
	 *
	 * @param array 最長狭義増加部分列の長さを求める配列
	 *
	 * @return 最長狭義増加部分列の長さ
	 */
	public static int lis ( int[] array ) {
		return lis( array, false );
	}

	/**
	 * 引数の配列指定されたインデックスの最長狭義増加部分列の長さを返します。
	 *
	 * @param arrays 最長狭義増加部分列の長さを求める配列
	 * @param p 探索する配列のインデックス
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)の最長狭義増加部分列の長さ
	 */
	public static int lis ( int[][] arrays, int p ) {
		return lis( arrays, p, false );
	}

	/**
	 * 引数の配列の最長狭義増加部分列の長さを返します。
	 *
	 * @param array 最長狭義増加部分列の長さを求める配列
	 *
	 * @return 最長狭義増加部分列の長さ
	 */
	public static int lis ( long[] array ) {
		return lis( array, false );
	}

	/**
	 * 引数の配列指定されたインデックスの最長狭義増加部分列の長さを返します。
	 *
	 * @param arrays 最長狭義増加部分列の長さを求める配列
	 * @param p 探索する配列のインデックス
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)の最長狭義増加部分列の長さ
	 */
	public static int lis ( long[][] arrays, int p ) {
		return lis( arrays, p, false );
	}

	/**
	 * 引数の配列の最長増加部分列の長さを返します。
	 *
	 * @param array 最長増加部分列の長さを求める配列
	 * @param include 広義増加列ならtrue、狭義増加列ならfalse
	 *
	 * @return 最長狭義増加部分列の長さ
	 */
	public static int lis ( int[] array, boolean include ) {
		int[] list = new int[array.length];
		Arrays.fill( list, Integer.MAX_VALUE );
		for ( int num: array ) {
			int index = include ? overSearch( list, num ) : upSearch( list, num );
			list[index] = Math.min( list[index], num );
		}
		int answer = underSearch( list, Integer.MAX_VALUE );
		return answer + 1;
	}

	/**
	 * 引数の配列指定されたインデックスの最長狭義増加部分列の長さを返します。
	 *
	 * @param arrays 最長狭義増加部分列の長さを求める配列
	 * @param p 探索する配列のインデックス
	 * @param include 広義増加列ならtrue、狭義増加列ならfalse
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)の最長狭義増加部分列の長さ
	 */
	public static int lis ( int[][] arrays, int p, boolean include ) {
		int[] list = new int[arrays.length];
		Arrays.fill( list, Integer.MAX_VALUE );
		for ( int[] array: arrays ) {
			int index = include ? overSearch( list, array[p] ) : upSearch( list, array[p] );
			list[index] = Math.min( list[index], array[p] );
		}
		int answer = underSearch( list, Integer.MAX_VALUE );
		return answer + 1;
	}

	/**
	 * 引数の配列の最長増加部分列の長さを返します。
	 *
	 * @param array 最長増加部分列の長さを求める配列
	 * @param include 広義増加列ならtrue、狭義増加列ならfalse
	 *
	 * @return 最長狭義増加部分列の長さ
	 */
	public static int lis ( long[] array, boolean include ) {
		long[] list = new long[array.length];
		Arrays.fill( list, Long.MAX_VALUE );
		for ( long num: array ) {
			int index = include ? overSearch( list, num ) : upSearch( list, num );
			list[index] = Math.min( list[index], num );
		}
		int answer = underSearch( list, Long.MAX_VALUE );
		return answer + 1;
	}

	/**
	 * 引数の配列指定されたインデックスの最長狭義増加部分列の長さを返します。
	 *
	 * @param arrays 最長狭義増加部分列の長さを求める配列
	 * @param p 探索する配列のインデックス
	 * @param include 広義増加列ならtrue、狭義増加列ならfalse
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)の最長狭義増加部分列の長さ
	 */
	public static int lis ( long[][] arrays, int p, boolean include ) {
		long[] list = new long[arrays.length];
		Arrays.fill( list, Long.MAX_VALUE );
		for ( long[] array: arrays ) {
			int index = include ? overSearch( list, array[p] ) : upSearch( list, array[p] );
			list[index] = Math.min( list[index], array[p] );
		}
		int answer = underSearch( list, Long.MAX_VALUE );
		return answer + 1;
	}

	/**
	 * 引数の情報から求められる有向辺に対してトポロジカルソートを行ないます。
	 * 戻り値はint型二次元配列です。
	 *
	 * @param route 有向グラフの隣接リスト
	 *
	 * @return トポロジカルソート済み有向グラフ
	 */
	public static int[][] topologicalSort ( ArrayList<ArrayList<Integer>> route ) {
		int[] count = new int[route.size()];
		int pathCount = 0;
		for ( ArrayList<Integer> path: route ) {
			for ( int point: path ) {
				pathCount++;
				count[point]++;
			}
		}
		final ArrayDeque<Integer> deq = new ArrayDeque<>();
		for ( int i = 1; i < count.length; i++ ) {
			if ( count[i] == 0 ) {
				deq.add( i );
			}
		}
		final int[][] ans = new int[pathCount][2];
		int index = 0;
		while ( deq.size() > 0 ) {
			int nowP = deq.pollFirst();
			for ( int nextP: route.get( nowP ) ) {
				ans[index][0] = nowP;
				ans[index++][1] = nextP;
				if ( --count[nextP] == 0 ) {
					deq.add( nextP );
				}
			}
		}
		return ans;
	}

	/**
	 * 引数の情報から求められる有向辺に対してトポロジカルソートを行ないます。
	 * 戻り値はint型二次元配列です。
	 *
	 * @param route 有向グラフの隣接リスト
	 *
	 * @return トポロジカルソート済み有向グラフ
	 */
	public static int[][] topologicalSort ( int[][] route ) {
		int[] count = new int[route.length];
		int pathCount = 0;
		for ( int[] path: route ) {
			for ( int point: path ) {
				pathCount++;
				count[point]++;
			}
		}
		final ArrayDeque<Integer> deq = new ArrayDeque<>();
		for ( int i = 1; i < count.length; i++ ) {
			if ( count[i] == 0 ) {
				deq.add( i );
			}
		}
		final int[][] ans = new int[pathCount][2];
		int index = 0;
		while ( deq.size() > 0 ) {
			int nowP = deq.pollFirst();
			for ( int nextP: route[nowP] ) {
				ans[index][0] = nowP;
				ans[index++][1] = nextP;
				if ( --count[nextP] == 0 ) {
					deq.add( nextP );
				}
			}
		}
		return ans;
	}
}

final class MathFunction {

	/**
	 * aとbの最大公約数を求めます。
	 *
	 * @param a
	 * @param b
	 *
	 * @return aとbの最大公約数
	 */
	public static long gcd ( long a, long b ) {
		a = Math.abs( a );
		b = Math.abs( b );
		if ( b == 0 ) {
			return a;
		}
		long temp;
		while ( ( temp = a % b ) != 0 ) {
			a = b;
			b = temp;
		}
		return b;
	}

	/**
	 * aとbの最小公倍数を求めます。
	 * オーバーフロー検知は出来ません。
	 *
	 * @param a
	 * @param b
	 *
	 * @return aとbの最小公倍数
	 */
	public static long lcm ( long a, long b ) {
		return a / gcd( a, b ) * b;
	}

	/**
	 * 引数が素数か判定します。
	 * 合成数を誤判定する確率は1/2^20以下です。
	 *
	 * @param num 検査対象
	 *
	 * @return numが素数である可能性があるならtrue、確実に合成数ならfalse
	 */
	public static boolean isPrime ( long num ) {
		return BigInteger.valueOf( num ).isProbablePrime( 20 );
	}

	/**
	 * num以下の素数を列挙します。
	 *
	 * @param num 素数を探す上限値
	 *
	 * @return num以下の素数のint型配列
	 */
	public static int[] primes ( int num ) {
		BitSet numbers = new BitSet( num + 1 );
		numbers.set( 2, num + 1 );
		for ( int i = 2; i <= Math.sqrt( num ); i++ ) {
			if ( numbers.get( i ) ) {
				for ( int j = i * i; j <= num; j += i ) {
					numbers.clear( j );
				}
			}
		}
		int[] answer = new int[numbers.cardinality()];
		int i = 2, index = 0;
		do {
			i = numbers.nextSetBit( i );
			answer[index++] = i++;
		} while ( index != answer.length );
		return answer;
	}

	/**
	 * a**bを計算します。
	 *
	 * @param a
	 * @param b
	 *
	 * @return a**b
	 */
	public static long pow ( long a, long b ) {
		long ans = 1;
		while ( b > 0 ) {
			if ( ( b & 1 ) == 1 ) {
				ans *= a;
			}
			a *= a;
			b >>= 1;
		}
		return ans;
	}

	/**
	 * a**bをmodで割ったあまりを計算します。
	 *
	 * @param a
	 * @param b
	 * @param mod
	 *
	 * @return a**bをmodで割ったあまり
	 */
	public static long modPow ( long a, long b, long mod ) {
		long ans = 1;
		a %= mod;
		while ( b > 0 ) {
			if ( ( b & 1 ) == 1 ) {
				ans *= a;
			}
			ans %= mod;
			a *= a;
			a %= mod;
			b >>= 1;
		}
		return ans;
	}

	/**
	 * nCrを計算します。
	 *
	 * @param n
	 * @param r
	 *
	 * @return nCr
	 */
	public static long combi ( long n, long r ) {
		long ans = 1;
		if ( r <= 0 || n < r ) {
			throw new IllegalArgumentException( "index is illegal:(" + n + "," + r + ")" );
		}
		r = Math.min( n - r, r );
		for ( int i = 0; i < r; i++ ) {
			ans *= n - i;
			ans /= i + 1;
		}
		return ans;
	}

	/**
	 * nCrをmodで割ったあまりを計算します。
	 *
	 * @param n
	 * @param r
	 * @param mod
	 *
	 * @return nCrをmodで割ったあまり
	 */
	public static long modCombi ( long n, long r, long mod ) {
		long ans = 1;
		r = Math.min( n - r, r );
		for ( int i = 0; i < r; i++ ) {
			ans *= n - i;
			ans %= mod;
			ans *= modPow( i + 1, mod - 2, mod );
			ans %= mod;
		}
		return ans;
	}

	/**
	 * 引数の前半二点、後半二点で構成される二線分が交差しているか返します。
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 *
	 * @return 交差している(線分の端が他方の線分上に存在する場合も含む)場合は1、同一線分直線上なら0、それ以外は-1
	 */
	public static int isCrossed ( int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4 ) {
		double s1 = ( x1 - x2 ) * ( y3 - y1 ) - ( y1 - y2 ) * ( x3 - x1 );
		double t1 = ( x1 - x2 ) * ( y4 - y1 ) - ( y1 - y2 ) * ( x4 - x1 );
		double s2 = ( x3 - x4 ) * ( y1 - y3 ) - ( y3 - y4 ) * ( x1 - x3 );
		double t2 = ( x3 - x4 ) * ( y2 - y3 ) - ( y3 - y4 ) * ( x2 - x3 );
		double temp1 = s1 * t1;
		double temp2 = s2 * t2;
		if ( temp1 > 0 || temp2 > 0 ) {
			return -1;
		}
		if ( temp1 == 0 && temp2 == 0 ) {
			return 0;
		}
		return 1;
	}

	/**
	 * 引数の前半二点、後半二点で構成される二線分が交差しているか返します。
	 *
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 *
	 * @return 交差している(線分の端が他方の線分上に存在する場合も含む)場合は1、同一線分直線上なら0、それ以外は-1
	 */
	public static int isCrossed ( Point p1, Point p2, Point p3, Point p4 ) {
		return isCrossed( p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y );
	}

	/**
	 * 指定された頂点を順に結んで出来上がる多角形が凸多角形か判定します。
	 *
	 * @param points 多角形を構成する点
	 *
	 * @return 多角形が凸多角形ならtrue
	 */
	public static boolean isConvex ( Point... points ) {
		int n = points.length;
		if ( n < 3 ) {
			return false;
		}
		if ( n == 3 ) {
			return true;
		}
		boolean conv = true;
		for ( int i = 0; i < n; i++ ) {
			int result = isCrossed( points[i], points[( i + 2 ) % n], points[( i + 1 ) % n], points[( i + 1 + n / 2 ) % n] );
			conv &= result >= 0;
		}
		return conv;
	}
}

final class Converter {

	/**
	 * Stringをintに変換します。
	 *
	 * @param str 変換対象
	 *
	 * @return 変換結果
	 */
	public static int parseInt ( String str ) {
		char[] array = str.toCharArray();
		int ans = 0;
		boolean plus = true;
		if ( array[0] == '-' ) {
			plus = false;
			array[0] = '0';
		}
		for ( char num: array ) {
			ans = ans * 10 + num - '0';
		}
		return plus ? ans : -ans;
	}

	/**
	 * Stringをlongに変換します。
	 *
	 * @param str 変換対象
	 *
	 * @return 変換結果
	 */
	public static long parseLong ( String str ) {
		char[] array = str.toCharArray();
		long ans = 0;
		boolean plus = true;
		if ( array[0] == '-' ) {
			plus = false;
			array[0] = '0';
		}
		for ( char c: array ) {
			ans = ans * 10 + c - '0';
		}
		return plus ? ans : -ans;
	}
}

// Binary Indexed Tree
final class BIT {
	final int size;
	final long[] tree;

	public BIT ( int n ) {
		size = n;
		tree = new long[n + 1];
	}

	public long sum ( int i ) {
		long sum = 0;
		while ( i > 0 ) {
			sum += tree[i];
			i -= i & ( -i );
		}
		return sum;
	}

	public void add ( int i, long x ) {
		while ( i <= size ) {
			tree[i] += x;
			i += i & ( -i );
		}
	}
}

// Bit Set
final class Bitset implements Cloneable {
	private final long[] bit;
	private final int size, len;
	private final long MASK;

	public Bitset ( final int len ) {
		this.len = len;
		size = ( len + 63 ) >> 6;
		bit = new long[size];
		MASK = ( -1L ) >>> ( ( size << 6 ) - len );
	}

	public void set ( final int index ) {
		if ( index >= size << 6 ) {
			throw new ArrayIndexOutOfBoundsException( index + " is out of this bitset's size " + size );
		}
		bit[index >> 6] |= ( 1L << ( index & 0b111111 ) );
	}

	public void clear ( final int index ) {
		if ( index >= size << 6 ) {
			throw new ArrayIndexOutOfBoundsException( index + " is out of this bitset's size " + size );
		}
		long m = ~( 1L << ( index & 0b111111 ) );
		bit[index >> 6] &= m;
	}

	public boolean get ( final int index ) {
		if ( index >= size << 6 ) {
			throw new ArrayIndexOutOfBoundsException( index + " is out of this bitset's size " + size );
		}
		return ( bit[index >> 6] & ( 1L << ( index & 0b111111 ) ) ) != 0;
	}

	public void shiftLeft ( int num ) {
		if ( num >= size << 6 ) {
			Arrays.fill( bit, 0L );
			return;
		}
		final int n = num >> 6;
		num &= 0b111111;
		for ( int i = size - 1; i >= n; i-- ) {
			bit[i] = ( bit[i - n] << num ) | ( i != n && num != 0 ? bit[i - n - 1] >>> ( 64 - num ) : 0L );
		}
		for ( int i = 0; i < n; i++ ) {
			bit[i] = 0L;
		}
		bit[size - 1] &= MASK;
	}

	public void shiftRight ( int num ) {
		if ( num >= size << 6 ) {
			Arrays.fill( bit, 0L );
			return;
		}
		final int n = num >> 6;
		num &= 0b111111;
		for ( int i = 0; i < size - n; i++ ) {
			bit[i] = ( bit[i + n] >>> num ) | ( i + n + 1 != size && num != 0 ? bit[i + n + 1] << ( 64 - num ) : 0L );
		}
		for ( int i = size - 1; i >= size - n; i-- ) {
			bit[i] = 0L;
		}
	}

	public long[] longValues () {
		return bit;
	}

	public long longValue () {
		return bit[0];
	}

	public void and ( final Bitset b ) {
		final long[] bit2 = b.longValues();
		final int m = Math.min( bit2.length, size );
		for ( int i = 0; i < m; i++ ) {
			bit[i] &= bit2[i];
		}
		for ( int i = m; i < size; i++ ) {
			bit[i] = 0;
		}
		bit[size - 1] &= MASK;
	}

	public void or ( final Bitset b ) {
		final long[] bit2 = b.longValues();
		final int m = Math.min( bit2.length, size );
		for ( int i = 0; i < m; i++ ) {
			bit[i] |= bit2[i];
		}
		bit[size - 1] &= MASK;
	}

	public void xor ( final Bitset b ) {
		final long[] bit2 = b.longValues();
		final int m = Math.min( bit2.length, size );
		for ( int i = 0; i < m; i++ ) {
			bit[i] ^= bit2[i];
		}
		bit[size - 1] &= MASK;
	}

	public Bitset clone () throws CloneNotSupportedException {
		super.clone();
		final Bitset b = new Bitset( len );
		b.or( this );
		return b;
	}
}

// Segment Tree
abstract class SegmentTree<E> {
	int N, size;
	E def;
	Object[] node;

	public SegmentTree ( int n, E def, boolean include ) {
		N = 2;
		size = 1;
		while ( N < n << 1 ) {
			N <<= 1;
			size <<= 1;
		}
		size -= include ? 1 : 0;
		node = new Object[N];
		this.def = def;
		Arrays.fill( node, this.def );
	}

	public SegmentTree ( int n, E def ) {
		this( n, def, false );
	}

	@SuppressWarnings( "unchecked" )
	public void update ( int n, E value ) {
		n += size;
		node[n] = value;
		n >>= 1;
		while ( n > 0 ) {
			node[n] = function( ( E )node[n << 1], ( E )node[( n << 1 ) + 1] );
			n >>= 1;
		}
	}

	@SuppressWarnings( "unchecked" )
	public E get ( int a ) {
		return ( E )node[a + size];
	}

	@SuppressWarnings( "unchecked" )
	public E answer () {
		return ( E )node[1];
	}

	@SuppressWarnings( "unchecked" )
	public E query ( int l, int r ) {
		l += size;
		r += size;
		E answer = def;
		while ( l > 0 && r > 0 && l <= r ) {
			if ( l % 2 == 1 ) {
				answer = function( ( E )node[l++], answer );
			}
			l >>= 1;
			if ( r % 2 == 0 ) {
				answer = function( answer, ( E )node[r--] );
			}
			r >>= 1;
		}
		return answer;
	}

	abstract public E function ( E a, E b );
}

// Union Find
final class UnionFind {
	private final int[] par, rank, size;
	private int count;

	public UnionFind ( int N ) {
		count = N;
		par = new int[N];
		rank = new int[N];
		size = new int[N];
		Arrays.fill( par, -1 );
		Arrays.fill( size, 1 );
	}

	public int root ( int x ) {
		if ( par[x] == -1 ) {
			return x;
		}
		else {
			return par[x] = root( par[x] );
		}
	}

	public boolean isSame ( int x, int y ) {
		return root( x ) == root( y );
	}

	public boolean unite ( int x, int y ) {
		int rx = root( x );
		int ry = root( y );
		if ( rx == ry ) {
			return false;
		}
		if ( rank[rx] < rank[ry] ) {
			int temp = rx;
			rx = ry;
			ry = temp;
		}
		par[ry] = rx;
		if ( rank[rx] == rank[ry] ) {
			++rank[rx];
		}
		size[rx] += size[ry];
		--count;
		return true;
	}

	public int groupCount () {
		return count;
	}

	public int size ( int x ) {
		return size[root( x )];
	}
}

// Rolling Hash
final class RollingHash implements Comparable<RollingHash> {
	private static final int base = 100;
	private static final int mod1 = 1_000_000_007;
	private static final int mod2 = Integer.MAX_VALUE - 1;
	private long[] hash1, hash2;
	private String string;

	public RollingHash ( String str ) {
		string = str;
		hash1 = new long[str.length() + 1];
		hash2 = new long[str.length() + 1];
		roll();
	}

	private void roll () {
		int len = string.length();
		for ( int i = 1; i <= len; i++ ) {
			hash1[i] = hash1[i - 1] * base + string.charAt( i - 1 ) - ' ' + 1;
			hash2[i] = hash2[i - 1] * base + string.charAt( i - 1 ) - ' ' + 1;
			hash1[i] %= mod1;
			hash2[i] %= mod2;
		}
	}

	public long getHash1 ( int l, int r ) {
		return ( hash1[r] - hash1[l] * modPow( base, r - l, mod1 ) % mod1 + mod1 ) % mod1;
	}

	public long getHash2 ( int l, int r ) {
		return ( hash2[r] - hash2[l] * modPow( base, r - l, mod2 ) % mod2 + mod2 ) % mod2;
	}

	private long modPow ( long a, long b, long mod ) {
		a %= mod;
		b %= mod - 1;
		long ans = 1;
		while ( b > 0 ) {
			if ( ( b & 1 ) == 1 ) {
				ans *= a;
				ans %= mod;
			}
			a *= a;
			a %= mod;
			b >>= 1;
		}
		return ans;
	}

	public boolean equals ( RollingHash rh, int l1, int r1, int l2, int r2 ) {
		if ( r1 - l1 != r2 - l2 ) {
			return false;
		}
		long hashValue1 = getHash1( l1, r1 );
		long hashValue2 = getHash2( l1, r1 );
		return hashValue1 == rh.getHash1( l2, r2 )
			   && hashValue2 == rh.getHash2( l2, r2 )
			   && check( rh, l1, l2, r1 - l1 );
	}

	private boolean check ( RollingHash rh, int l1, int l2, int len ) {
		return check( rh.toString(), l1, l2, len );
	}

	private boolean check ( String str, int l1, int l2, int len ) {
		for ( int i = 0; i < len; i++ ) {
			if ( string.charAt( l1 + i ) != str.charAt( l2 + i ) ) {
				return false;
			}
		}
		return true;
	}

	public int length () {
		return string.length();
	}

	@Override
	public int hashCode () {
		return string.hashCode();
	}

	@Override
	public String toString () {
		return string;
	}

	@Override
	public boolean equals ( Object o ) {
		if ( o instanceof RollingHash ) {
			RollingHash rh = ( RollingHash )o;
			return equals( rh, 1, length(), 1, rh.length() );
		}
		return false;
	}

	@Override
	public int compareTo ( RollingHash rh ) {
		return string.compareTo( rh.toString() );
	}

	public int compareTo ( String str ) {
		return string.compareTo( str );
	}

	public char charAt ( int i ) {
		return string.charAt( i );
	}

	public int compareToIgnoreCase ( RollingHash rh ) {
		return string.compareToIgnoreCase( rh.toString() );
	}

	public int compareToIgnoreCase ( String str ) {
		return string.compareToIgnoreCase( str );
	}

	public void concat ( RollingHash rh ) {
		concat( rh.toString() );
	}

	public void concat ( String str ) {
		string = string.concat( str );
		hash1 = new long[string.length() + 1];
		hash2 = new long[string.length() + 1];
		roll();
	}

	public boolean contains ( RollingHash rh ) {
		long hash1 = rh.getHash1( 0, rh.length() );
		long hash2 = rh.getHash2( 0, rh.length() );
		boolean isContain = false;
		int len = length() - rh.length();
		for ( int i = 0; i <= len; i++ ) {
			if ( hash1 == getHash1( i, rh.length() + i )
				 && hash2 == getHash2( i, rh.length() + i ) ) {
				isContain |= check( rh, i, 0, rh.length() );
			}
		}
		return isContain;
	}

	public boolean contains ( String str ) {
		return indexOf( str ) != -1;
	}

	public int indexOf ( int ch ) {
		return indexOf( ch, 0 );
	}

	public int indexOf ( int ch, int fromIndex ) {
		int len = length();
		for ( int i = fromIndex; i < len; i++ ) {
			if ( string.charAt( i ) == ch ) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf ( String str ) {
		return indexOf( str, 0 );
	}

	public int indexOf ( String str, int fromIndex ) {
		long hash1 = 0;
		long hash2 = 0;
		for ( char c: str.toCharArray() ) {
			hash1 = hash1 * base + c - ' ' + 1;
			hash2 = hash2 * base + c - ' ' + 1;
			hash1 %= mod1;
			hash2 %= mod2;
		}
		boolean isContain = false;
		int len = length() - str.length();
		for ( int i = fromIndex; i <= len; i++ ) {
			if ( hash1 == getHash1( i, str.length() + i )
				 && hash2 == getHash2( i, str.length() + i )
				 && check( str, i, 0, str.length() ) ) {
				return i;
			}
		}
		return -1;
	}

	public boolean isEmpty () {
		return length() == 0;
	}

	public int lastIndexOf ( int ch, int fromIndex ) {
		for ( int i = fromIndex; i >= 0; i-- ) {
			if ( string.charAt( i ) == ch ) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf ( int ch ) {
		return lastIndexOf( ch, length() - 1 );
	}

	public static RollingHash valueOf ( boolean b ) {
		return new RollingHash( b ? "true" : "false" );
	}

	public static RollingHash valueOf ( char c ) {
		return new RollingHash( "" + c );
	}

	public static RollingHash valueOf ( char[] c ) {
		return new RollingHash( String.valueOf( c, 0, c.length ) );
	}

	public static RollingHash valueOf ( char[] c, int offset, int count ) {
		return new RollingHash( String.valueOf( c, offset, count ) );
	}

	public static RollingHash valueOf ( double d ) {
		return new RollingHash( String.valueOf( d ) );
	}

	public static RollingHash valueOf ( float f ) {
		return new RollingHash( String.valueOf( f ) );
	}

	public static RollingHash valueOf ( int i ) {
		return new RollingHash( String.valueOf( i ) );
	}

	public static RollingHash valueOf ( long l ) {
		return new RollingHash( String.valueOf( l ) );
	}

	public static RollingHash valueOf ( Object obj ) {
		return new RollingHash( String.valueOf( obj ) );
	}
}

// Pair
class Pair<K extends Comparable<K>, V extends Comparable<V>>
		implements Comparable<Pair<K, V>> {
	private AbstractMap.SimpleEntry<K, V> map;

	public Pair ( K key, V value ) {
		map = new AbstractMap.SimpleEntry<>( key, value );
	}

	public K getKey () {
		return map.getKey();
	}

	public V getValue () {
		return map.getValue();
	}

	public K setKey ( K key ) {
		K oldKey = map.getKey();
		V value = map.getValue();
		map = new AbstractMap.SimpleEntry<>( key, value );
		return oldKey;
	}

	public V setValue ( V value ) {
		return map.setValue( value );
	}

	@Override
	public int compareTo ( Pair<K, V> pair ) {
		int com = getKey().compareTo( pair.getKey() );
		return com != 0 ? com : getValue().compareTo( pair.getValue() );
	}

	@Override
	public boolean equals ( Object o ) {
		if ( o instanceof Pair<?, ?> ) {
			Pair<?, ?> pair = ( Pair<?, ?> )o;
			return getKey().equals( pair.getKey() ) && getValue().equals( pair.getValue() );
		}
		return false;
	}

	@Override
	public String toString () {
		return getKey() + "=" + getValue();
	}

	@Override
	public int hashCode () {
		return ( getKey().hashCode() << 2 ) ^ ( getValue().hashCode() );
	}
}

// MyScanner
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

	public int[][] nextGraph ( int N, int M ) {
		if ( M == 0 ) {
			return new int[N+1][0];
		}
		int[][] ans = new int[N + 1][];
		int[] count = new int[N + 1];
		int[][] path = nextInt( M, 2 );
		for ( int[] temp: path ) {
			count[temp[0]]++;
			count[temp[1]]++;
		}
		for ( int i = 1; i <= N; i++ ) {
			ans[i] = new int[count[i]];
		}
		for ( int[] temp: path ) {
			ans[temp[0]][--count[temp[0]]] = temp[1];
			ans[temp[1]][--count[temp[1]]] = temp[0];
		}
		return ans;
	}

	public Point nextPoint () {
		return new Point( nextInt(), nextInt() );
	}

	public Point[] nextPoint ( int n ) {
		Point[] ans = new Point[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextPoint();
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

// MyPrinter
final class SimplePrinter extends PrintWriter {
	public SimplePrinter ( PrintStream os ) {
		super( os );
	}

	public SimplePrinter ( PrintStream os, boolean bool ) {
		super( os, bool );
	}

	public void println ( int[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( str );
			print( array[i] );
		}
		println();
	}

	public void println ( int[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( c );
			print( array[i] );
		}
		println();
	}

	public void println ( int[][] arrays, String str ) {
		for ( int[] array: arrays ) {
			println( array, str );
		}
	}

	public void println ( int[][] arrays, char c ) {
		for ( int[] array: arrays ) {
			println( array, c );
		}
	}

	public void println ( long[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( str );
			print( array[i] );
		}
		println();
	}

	public void println ( long[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( c );
			print( array[i] );
		}
		println();
	}

	public void println ( long[][] arrays, String str ) {
		for ( long[] array: arrays ) {
			println( array, str );
		}
	}

	public void println ( long[][] arrays, char c ) {
		for ( long[] array: arrays ) {
			println( array, c );
		}
	}

	public void println ( char[] cs, String str ) {
		print( cs[0] );
		for ( int i = 1; i < cs.length; i++ ) {
			print( str );
			print( cs[i] );
		}
		println();
	}

	public void println ( char[] cs, char c ) {
		print( cs[0] );
		for ( int i = 1; i < cs.length; i++ ) {
			print( c );
			print( cs[i] );
		}
		println();
	}

	public void println ( char[][] cs ) {
		for ( char[] c: cs ) {
			println( c );
		}
	}

	public void println ( char[][] cs, String str ) {
		print( cs[0] );
		for ( int i = 1; i < cs.length; i++ ) {
			print( str );
			print( cs[i] );
		}
		println();
	}

	public void println ( char[][] cs, char c ) {
		print( cs[0] );
		for ( int i = 1; i < cs.length; i++ ) {
			print( c );
			print( cs[i] );
		}
		println();
	}

	public <E> void println ( E[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( str );
			print( array[i] );
		}
		println();
	}

	public <E> void println ( E[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; i++ ) {
			print( c );
			print( array[i] );
		}
		println();
	}

	public <E> void println ( E[][] arrays, String str ) {
		for ( E[] array: arrays ) {
			println( array, str );
		}
	}

	public <E> void println ( E[][] arrays, char c ) {
		for ( E[] array: arrays ) {
			println( array, c );
		}
	}
}
