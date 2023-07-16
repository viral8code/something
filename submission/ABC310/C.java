import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.awt.Point;
import java.awt.Dimension;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

final class Main {

	private static final boolean autoFlush = false;
	private static final SimpleScanner sc = new SimpleScanner( System.in );
	private static final SimplePrinter out = new SimplePrinter( System.out, autoFlush );

	public static void main ( String[] args ) {

		int N = sc.nextInt();
		String[] S = sc.next(N);
		for(int i=0;i<N;i++){
			String s1 = S[i];
			String s2 = new StringBuilder(S[i]).reverse().toString();
			if(s1.compareTo(s2)<0)
				S[i] = s1;
			else
				S[i] = s2;
		}
		HashSet<String> set = new HashSet<>();
		for(String s:S)
			set.add(s);
		out.println(set.size());

		out.close();
	}
}
/*


             ／￣＼
            |     |
             ＼＿／
               |
          ／ ￣  ￣  ＼
        ／    ＼  ／    ＼
     ／      ⌒    ⌒      ＼      よくぞこの提出結果を開いてくれた
     |      （__人__）      |      褒美としてオプーナを買う権利をやる
     ＼       ｀⌒´       ／   ☆
      /ヽ､--ー､＿＿, -‐ ´  ＼─／
    ／ >     ヽ▼●▼<＼       ||ｰ､.
   /ヽ､    ＼ i |｡| |/  ヽ (ニ､`ヽ.
  l    ヽ     l |｡| | r-､y ｀ﾆ  ﾉ ＼
  l     |     |ー─ | ￣ l   ｀~ヽ＿ノ＿_
      ／￣￣￣￣ヽ-'ヽ--'  ／ オープナ  ／|
     |￣￣￣￣￣￣|／|    |￣￣￣￣￣￣|／| ＿＿＿＿＿＿
 ／￣オプーナ／|￣|__｣／_オープナ ／|￣|__,」＿_      ／|
|￣￣￣￣￣|／オープナ￣／￣￣￣￣|／オプーナ ／|   ／  |
|￣￣￣￣￣|￣￣￣￣￣|／l￣￣￣￣|￣￣￣￣￣|／| ／
|￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣|
*/

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
		for ( int i = 2; i <= N; ++i ) {
			fact[i] = fact[i - 1] * i % mod;
		}

		inFact = new long[N + 1];
		inFact[N] = MathFunction.modPow( fact[N], mod - 2, mod );
		for ( int i = N; i > 0; --i ) {
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
	 * num!^-1をmodで割ったあまりを返します。
	 *
	 * @param num
	 *
	 * @return num!
	 */
	public long getInFact ( int num ) {
		return inFact[num];
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
		if ( a < b || a < 0 || b < 0 ) {
			return 0;
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
		for ( int i = 0; i < array.length; ++i ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2] < array[j] ) {
				int temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; --i ) {
			int temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j] < array[2 * j + 1] ) || ( 2 * j + 2 < i - 1 && array[j] < array[2 * j + 2] ) ) {
				if ( 2 * j + 2 < i - 1 && array[2 * j + 1] < array[2 * j + 2] ) {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					++j;
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
		for ( int i = 0; i < arrays.length; ++i ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				int[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; --i ) {
			int[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 < i - 1 && compare( arrays[2 * j + 1], arrays[2 * j + 2] ) < 0 ) {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					++j;
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
		for ( int i = 0; i < array.length; ++i ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2] < array[j] ) {
				long temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; --i ) {
			long temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j] < array[2 * j + 1] ) || ( 2 * j + 2 < i - 1 && array[j] < array[2 * j + 2] ) ) {
				if ( 2 * j + 2 < i - 1 && array[2 * j + 1] < array[2 * j + 2] ) {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					++j;
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
		for ( int i = 0; i < arrays.length; ++i ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				long[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; --i ) {
			long[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 < i - 1 && compare( arrays[2 * j + 1], arrays[2 * j + 2] ) < 0 ) {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					++j;
				}
			}
		}
	}

	/**
	 * double型配列をソートします。
	 *
	 * @param array ソートする配列
	 */
	public static void sort ( double[] array ) {
		for ( int i = 0; i < array.length; ++i ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2] < array[j] ) {
				double temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; --i ) {
			double temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j] < array[2 * j + 1] ) || ( 2 * j + 2 < i - 1 && array[j] < array[2 * j + 2] ) ) {
				if ( 2 * j + 2 < i - 1 && array[2 * j + 1] < array[2 * j + 2] ) {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					++j;
				}
			}
		}
	}

	/**
	 * double型配列をソートします。
	 * 序列は配列を一つの文字列として見たときの辞書順と等しいです。
	 *
	 * @param arrays ソートする配列
	 */
	public static void sort ( double[][] arrays ) {
		for ( int i = 0; i < arrays.length; ++i ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				double[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; --i ) {
			double[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 < i - 1 && compare( arrays[2 * j + 1], arrays[2 * j + 2] ) < 0 ) {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					++j;
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
		for ( int i = 0; i < array.length; ++i ) {
			int j = i;
			while ( j > 0 && array[( j - 1 ) / 2].compareTo( array[j] ) < 0 ) {
				E temp = array[( j - 1 ) / 2];
				array[( j - 1 ) / 2] = array[j];
				array[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = array.length; i > 0; --i ) {
			E temp = array[i - 1];
			array[i - 1] = array[0];
			array[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && array[j].compareTo( array[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && array[j].compareTo( array[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 < i - 1 && array[2 * j + 1].compareTo( array[2 * j + 2] ) < 0 ) {
					temp = array[2 * j + 2];
					array[2 * j + 2] = array[j];
					array[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = array[2 * j + 1];
					array[2 * j + 1] = array[j];
					array[j] = temp;
					j <<= 1;
					++j;
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
		for ( int i = 0; i < arrays.length; ++i ) {
			int j = i;
			while ( j > 0 && compare( arrays[( j - 1 ) / 2], arrays[j] ) < 0 ) {
				E[] temp = arrays[( j - 1 ) / 2];
				arrays[( j - 1 ) / 2] = arrays[j];
				arrays[j] = temp;
				j = ( j - 1 ) / 2;
			}
		}
		for ( int i = arrays.length; i > 0; --i ) {
			E[] temp = arrays[i - 1];
			arrays[i - 1] = arrays[0];
			arrays[0] = temp;
			int j = 0;
			while ( ( 2 * j + 1 < i - 1 && compare( arrays[j], arrays[2 * j + 1] ) < 0 ) ||
					( 2 * j + 2 < i - 1 && compare( arrays[j], arrays[2 * j + 2] ) < 0 ) ) {
				if ( 2 * j + 2 < i - 1 && compare( arrays[2 * j + 1], arrays[2 * j + 2] ) < 0 ) {
					temp = arrays[2 * j + 2];
					arrays[2 * j + 2] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					j += 2;
				}
				else {
					temp = arrays[2 * j + 1];
					arrays[2 * j + 1] = arrays[j];
					arrays[j] = temp;
					j <<= 1;
					++j;
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
		for ( int i = 0; i < len; ++i ) {
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
		for ( int i = 0; i < len; ++i ) {
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
	 * double型配列を比較します。
	 *
	 * @param a
	 * @param b
	 *
	 * @return a.compareTo(b)として想定されるint型戻り値
	 */
	private static int compare ( double[] a, double[] b ) {
		int len = Math.min( a.length, b.length );
		for ( int i = 0; i < len; ++i ) {
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
		for ( int i = 0; i < len; ++i ) {
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
			++list[num];
		}
		int temp = 0;
		for ( int i = 0; i < list.length; ++i ) {
			for ( int j = 0; j < list[i]; ++j ) {
				array[temp++] = i;
			}
		}
	}
 
	/**
	 * 配列を右周りに90度回転させたものを返します。
	 * 長方形でない配列に関しての動作は保証していません。
	 *
	 * @param array 回転させる行列
	 *
	 * @return 回転させた配列
	 */
	public static int[][] rotateR ( final int[][] array ) {
		final int[][] ans = new int[array[0].length][array.length];
		for ( int i = 0; i < ans.length; ++i ) {
			for ( int j = 0; j < ans[i].length; ++j ) {
				ans[i][j] = array[ans[i].length - j - 1][i];
			}
		}
		return ans;
	}
 
	/**
	 * 配列を右周りに90度回転させたものを返します。
	 * 長方形でない配列に関しての動作は保証していません。
	 *
	 * @param array 回転させる行列
	 *
	 * @return 回転させた配列
	 */
	public static long[][] rotateR ( final long[][] array ) {
		final long[][] ans = new long[array[0].length][array.length];
		for ( int i = 0; i < ans.length; ++i ) {
			for ( int j = 0; j < ans[i].length; ++j ) {
				ans[i][j] = array[ans[i].length - j - 1][i];
			}
		}
		return ans;
	}
 
	/**
	 * 配列を右周りに90度回転させたものを返します。
	 * 長方形でない配列に関しての動作は保証していません。
	 *
	 * @param array 回転させる行列
	 *
	 * @return 回転させた配列
	 */
	public static char[][] rotateR ( final char[][] array ) {
		final char[][] ans = new char[array[0].length][array.length];
		for ( int i = 0; i < ans.length; ++i ) {
			for ( int j = 0; j < ans[i].length; ++j ) {
				ans[i][j] = array[ans[i].length - j - 1][i];
			}
		}
		return ans;
	}
 
	/**
	 * 配列を左周りに90度回転させたものを返します。
	 * 長方形でない配列に関しての動作は保証していません。
	 *
	 * @param array 回転させる行列
	 *
	 * @return 回転させた配列
	 */
	public static int[][] rotateL ( final int[][] array ) {
		final int[][] ans = new int[array[0].length][array.length];
		for ( int i = 0; i < ans.length; ++i ) {
			final int index = i;
			Arrays.setAll( ans[i], k -> array[k][ans.length - index - 1] );
		}
		return ans;
	}
 
	/**
	 * 配列を左周りに90度回転させたものを返します。
	 * 長方形でない配列に関しての動作は保証していません。
	 *
	 * @param array 回転させる行列
	 *
	 * @return 回転させた配列
	 */
	public static long[][] rotateL ( final long[][] array ) {
		final long[][] ans = new long[array[0].length][array.length];
		for ( int i = 0; i < ans.length; ++i ) {
			final int index = i;
			Arrays.setAll( ans[i], k -> array[k][ans.length - index - 1] );
		}
		return ans;
	}
 
	/**
	 * 配列を左周りに90度回転させたものを返します。
	 * 長方形でない配列に関しての動作は保証していません。
	 *
	 * @param array 回転させる行列
	 *
	 * @return 回転させた配列
	 */
	public static char[][] rotateL ( final char[][] array ) {
		final char[][] ans = new char[array[0].length][array.length];
		for ( int i = 0; i < ans.length; ++i ) {
			final int index = i;
			for ( int j = 0; j < ans[i].length; ++j ) {
				ans[i][j] = array[j][ans.length - i - 1];
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
			int index = include ? Searcher.overSearch( list, num ) : Searcher.upSearch( list, num );
			list[index] = Math.min( list[index], num );
		}
		int answer = Searcher.underSearch( list, Integer.MAX_VALUE );
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
			int index = include ? Searcher.overSearch( list, array[p] ) : Searcher.upSearch( list, array[p] );
			list[index] = Math.min( list[index], array[p] );
		}
		int answer = Searcher.underSearch( list, Integer.MAX_VALUE );
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
			int index = include ? Searcher.overSearch( list, num ) : Searcher.upSearch( list, num );
			list[index] = Math.min( list[index], num );
		}
		int answer = Searcher.underSearch( list, Long.MAX_VALUE );
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
			int index = include ? Searcher.overSearch( list, array[p] ) : Searcher.upSearch( list, array[p] );
			list[index] = Math.min( list[index], array[p] );
		}
		int answer = Searcher.underSearch( list, Long.MAX_VALUE );
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
				++pathCount;
				++count[point];
			}
		}
		final ArrayDeque<Integer> deq = new ArrayDeque<>();
		for ( int i = 1; i < count.length; ++i ) {
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
				++pathCount;
				++count[point];
			}
		}
		final ArrayDeque<Integer> deq = new ArrayDeque<>();
		for ( int i = 1; i < count.length; ++i ) {
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

final class Searcher {

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
	public static int downSearch ( double[] array, double value ) {
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
	 * 広義単調増加な関数内の指定された値を探します。
	 * 関数内で見つかった場合はその値と同一で最大の引数を返します。
	 * 見つからなかった場合は指定された値未満で最大の引数を返します。
	 * もしそのような要素が存在しない場合は下限-1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 指定された値以下で最大の引数
	 */
	public static int downSearch ( int a, int b, int value, IntUnaryOperator func ) {
		int ans = a - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsInt( c ) > value ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 広義単調増加な関数内の指定された値を探します。
	 * 関数内で見つかった場合はその値と同一で最大の引数を返します。
	 * 見つからなかった場合は指定された値未満で最大の引数を返します。
	 * もしそのような要素が存在しない場合は下限-1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 指定された値以下で最大の引数
	 */
	public static long downSearch ( long a, long b, long value, LongUnaryOperator func ) {
		long ans = a - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsLong( c ) > value ) {
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
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayにvalueが含まれているかを表すboolean
	 */
	public static boolean contains ( int[] array, int value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else if ( array[c] < value ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayにvalueが含まれているかを表すboolean
	 */
	public static boolean contains ( long[] array, long value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else if ( array[c] < value ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayにvalueが含まれているかを表すboolean
	 */
	public static boolean contains ( double[] array, double value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else if ( array[c] < value ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayにvalueが含まれているかを表すboolean
	 */
	public static <E extends Comparable<E>> boolean contains ( E[] array, E value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			int result = array[c].compareTo( value );
			if ( result > 0 ) {
				b = c - 1;
			}
			else if ( result < 0 ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * リスト内の指定された要素を探します。
	 *
	 * @param list 探索対象のリスト
	 * @param value 探索要素
	 *
	 * @return listにvalueが含まれているかを表すboolean
	 */
	public static <E extends Comparable<E>> boolean contains ( List<E> list, E value ) {
		int a = 0, b = list.size() - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			int result = list.get( c ).compareTo( value );
			if ( result > 0 ) {
				b = c - 1;
			}
			else if ( result < 0 ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 広義単調増加な関数内の指定された値を探します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return この関数がa以上b以下で探索値を取るかを表すboolean
	 */
	public static boolean contains ( int a, int b, int value, IntUnaryOperator func ) {
		int c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			int num = func.applyAsInt( c );
			if ( num > value ) {
				b = c - 1;
			}
			else if ( num < value ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 広義単調増加な関数内の指定された値を探します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return この関数がa以上b以下で探索値を取るかを表すboolean
	 */
	public static boolean contains ( long a, long b, long value, LongUnaryOperator func ) {
		long c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			long num = func.applyAsLong( c );
			if ( num > value ) {
				b = c - 1;
			}
			else if ( num < value ) {
				a = c + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayのvalueのインデックス(無ければ-1)
	 */
	public static int search ( int[] array, int value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else if ( array[c] < value ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return -1;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayのvalueのインデックス(無ければ-1)
	 */
	public static int search ( long[] array, long value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else if ( array[c] < value ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return -1;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayのvalueのインデックス(無ければ-1)
	 */
	public static int search ( double[] array, double value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( array[c] > value ) {
				b = c - 1;
			}
			else if ( array[c] < value ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return -1;
	}

	/**
	 * 配列内の指定された要素を探します。
	 *
	 * @param array 探索対象の配列
	 * @param value 探索要素
	 *
	 * @return arrayにvalueが含まれているかを表すboolean
	 */
	public static <E extends Comparable<E>> int search ( E[] array, E value ) {
		int a = 0, b = array.length - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			int result = array[c].compareTo( value );
			if ( result > 0 ) {
				b = c - 1;
			}
			else if ( result < 0 ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return -1;
	}

	/**
	 * リスト内の指定された要素を探します。
	 *
	 * @param list 探索対象のリスト
	 * @param value 探索要素
	 *
	 * @return listのvalueのインデックス(無ければ-1)
	 */
	public static <E extends Comparable<E>> int search ( List<E> list, E value ) {
		int a = 0, b = list.size() - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			int result = list.get( c ).compareTo( value );
			if ( result > 0 ) {
				b = c - 1;
			}
			else if ( result < 0 ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return -1;
	}

	/**
	 * 広義単調増加な関数内の指定された値を探します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return この関数がvalueを取る引数(無ければa-1)
	 */
	public static int search ( int a, int b, int value, IntUnaryOperator func ) {
		int c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			int num = func.applyAsInt( c );
			if ( num > value ) {
				b = c - 1;
			}
			else if ( num < value ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return a-1;
	}

	/**
	 * 広義単調増加な関数内の指定された値を探します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return この関数がvalueを取る引数(無ければa-1)
	 */
	public static long search ( long a, long b, long value, LongUnaryOperator func ) {
		long c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			long num = func.applyAsLong( c );
			if ( num > value ) {
				b = c - 1;
			}
			else if ( num < value ) {
				a = c + 1;
			}
			else {
				return c;
			}
		}
		return a-1;
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
	public static int upSearch ( double[] array, double value ) {
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
	 * 広義単調増加な関数内の指定された値を探します。
	 * 関数内で見つかった場合はその値と同一で最小の引数を返します。
	 * 見つからなかった場合は指定された値以上で最大の引数を返します。
	 * もしそのような要素が存在しない場合は上限+1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 指定された値以上で最小の引数
	 */
	public static int upSearch ( int a, int b, int value, IntUnaryOperator func ) {
		int ans = b + 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsInt( c ) >= value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 広義単調増加な関数内の指定された値を探します。
	 * 関数内で見つかった場合はその値と同一で最小の引数を返します。
	 * 見つからなかった場合は指定された値以上で最大の引数を返します。
	 * もしそのような要素が存在しない場合は上限+1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 指定された値以上で最小の引数
	 */
	public static long upSearch ( long a, long b, long value, LongUnaryOperator func ) {
		long ans = b + 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsLong( c ) >= value ) {
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
	public static int underSearch ( double[] array, double value ) {
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
	 * 広義単調増加な関数内の指定された値より小さい値を探します。
	 * 関数内で見つかった場合は条件を満たす最大の引数を返します。
	 * もしそのような要素が存在しない場合は下限-1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 条件を満たす最大の引数
	 */
	public static int underSearch ( int a, int b, int value, IntUnaryOperator func ) {
		int ans = a - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsInt( c ) >= value ) {
				b = c - 1;
			}
			else {
				a = ( ans = c ) + 1;
			}
		}
		return ans;
	}

	/**
	 * 広義単調増加な関数内の指定された値より小さい値を探します。
	 * 関数内で見つかった場合は条件を満たす最大の引数を返します。
	 * もしそのような要素が存在しない場合は下限-1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 条件を満たす最大の引数
	 */
	public static long underSearch ( long a, long b, long value, LongUnaryOperator func ) {
		long ans = a - 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsLong( c ) >= value ) {
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
	public static int overSearch ( double[] array, double value ) {
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
	 * 広義単調増加な関数内の指定された値より大きい値を探します。
	 * 関数内で見つかった場合は条件を満たす最小の引数を返します。
	 * もしそのような要素が存在しない場合は上限+1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 条件を満たす最小の引数
	 */
	public static int overSearch ( int a, int b, int value, IntUnaryOperator func ) {
		int ans = b + 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsInt( c ) > value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
			}
		}
		return ans;
	}

	/**
	 * 広義単調増加な関数内の指定された値より大きい値を探します。
	 * 関数内で見つかった場合は条件を満たす最小の引数を返します。
	 * もしそのような要素が存在しない場合は上限+1を返します。
	 *
	 * @param a 探索範囲の下限
	 * @param b 探索範囲の上限
	 * @param value 探索値
	 *
	 * @return 条件を満たす最小の引数
	 */
	public static long overSearch ( long a, long b, long value, LongUnaryOperator func ) {
		long ans = b + 1, c;
		while ( a - b < 1 ) {
			c = ( a + b ) / 2;
			if ( func.applyAsLong( c ) > value ) {
				b = ( ans = c ) - 1;
			}
			else {
				a = c + 1;
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
	
	public static boolean isPrime(long n){
		n = Math.abs(n);
		if(n==2)
			return true;
		if(n==1||(n&1)==0)
			return false;
		long d = n-1;
		while((d&1)==0)
			d >>= 1;
		for(long a:new long[]{2,7,61,325,9375,28178,450775,9780504,1795265022}){
			if(a>=n)
				return true;
			long t = d;
			long y = bigModPow(a,t,n);
			while(t<n-1&&y!=1&&y!=n-1){
				y = bigModPow(y,2,n);
				t <<= 1;
			}
			if(y!=n-1&&(t&1)==0)
				return false;
		}
		return true;
	}
	private static long bigModPow(long a,long b,long m){
		return BigInteger.valueOf(a).modPow(BigInteger.valueOf(b),BigInteger.valueOf(m)).longValue();
	}

	/**
	 * num以下の素数を列挙します。
	 *
	 * @param num 素数を探す上限値
	 *
	 * @return num以下の素数のint型配列
	 */
	public static int[] primes ( int num ) {
		final BitSet numbers = new BitSet( num + 1 );
		numbers.set( 2, num + 1 );
		final int limit = ( int )Math.sqrt( num );
		for ( int i = 2; i <= limit ; ++i ) {
			if ( numbers.get( i ) ) {
				for ( int j = i * i; j <= num; j += i ) {
					numbers.clear( j );
				}
			}
		}
		final int[] answer = new int[numbers.cardinality()];
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
		if ( r < 0 || n < r ) {
			return 0;
		}
		long ans = 1;
		r = Math.min( n - r, r );
		for ( int i = 0; i < r; ++i ) {
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
		if ( r < 0 || n < r ) {
			return 0;
		}
		long ans = 1;
		r = Math.min( n - r, r );
		for ( int i = 0; i < r; ++i ) {
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
		long s1 = ( x1 - x2 ) * ( y3 - y1 ) - ( y1 - y2 ) * ( x3 - x1 );
		long t1 = ( x1 - x2 ) * ( y4 - y1 ) - ( y1 - y2 ) * ( x4 - x1 );
		long s2 = ( x3 - x4 ) * ( y1 - y3 ) - ( y3 - y4 ) * ( x1 - x3 );
		long t2 = ( x3 - x4 ) * ( y2 - y3 ) - ( y3 - y4 ) * ( x2 - x3 );
		long temp1 = s1 * t1;
		long temp2 = s2 * t2;
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
		for ( int i = 0; i < n; ++i ) {
			int result = isCrossed( points[i], points[( i + 2 ) % n], points[( i + 1 ) % n], points[( i + 1 + n / 2 ) % n] );
			conv &= result >= 0;
		}
		return conv;
	}

	/**
	 * numをmodで割ったあまりを返します。
	 * 戻り値は0以上mod未満であることが保証されます。
	 *
	 * @param num 被除算数
	 * @param mod 法とする値
	 *
	 * @return numをmodで割ったあまり
	 */
	public static long remainder ( long num, final long mod ) {
		num %= mod;
		if ( num < 0 ) {
			num += mod;
		}
		return num;
	}

	/**
	 * numが何桁かを返します。
	 * 0は1桁として捉えます。
	 *
	 * @param num
	 *
	 * @return numの桁数
	 */
	public static int digit ( final long num ) {
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

	public static int max ( final int... nums ) {
		int ans = Integer.MIN_VALUE;
		for ( int num : nums ) {
			ans = Math.max( ans, num );
		}
		return ans;
	}

	public static long max ( final long... nums ) {
		long ans = Long.MIN_VALUE;
		for ( long num : nums ) {
			ans = Math.max( ans, num );
		}
		return ans;
	}

	public static double max ( final double... nums ) {
		double ans = -Double.MIN_VALUE;
		for ( double num : nums ) {
			ans = Math.max( ans, num );
		}
		return ans;
	}

	public static <E extends Comparable<E>> E max ( final E[] nums ) {
		E ans = nums[0];
		for ( E value : nums ) {
			if ( ans.compareTo( value ) > 0 ) {
				ans = value;
			}
		}
		return ans;
	}

	public static int min ( final int... nums ) {
		int ans = Integer.MAX_VALUE;
		for ( int num : nums ) {
			ans = Math.min( ans, num );
		}
		return ans;
	}

	public static long min ( final long... nums ) {
		long ans = Long.MAX_VALUE;
		for ( long num : nums ) {
			ans = Math.min( ans, num );
		}
		return ans;
	}

	public static double min ( final double... nums ) {
		double ans = Double.MAX_VALUE;
		for ( double num : nums ) {
			ans = Math.min( ans, num );
		}
		return ans;
	}

	public static <E extends Comparable<E>> E min ( final E[] nums ) {
		E ans = nums[0];
		for ( E value : nums ) {
			if ( ans.compareTo( value ) < 0 ) {
				ans = value;
			}
		}
		return ans;
	}

	public static long sum ( final int... nums ) {
		long ans = 0;
		for ( int num : nums ) {
			ans += num;
		}
		return ans;
	}

	public static long sum ( final long... nums ) {
		long ans = 0;
		for ( long num : nums ) {
			ans += num;
		}
		return ans;
	}

	public static long modSum ( final long mod, final int... nums ) {
		long ans = 0;
		for ( int num : nums ) {
			ans += num;
			ans %= mod;
		}
		return ans;
	}

	public static long modSum ( final long mod, final long... nums ) {
		long ans = 0;
		for ( long num : nums ) {
			ans += num;
			ans %= mod;
		}
		return ans;
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

	/**
	 * intをStringに変換します。
	 *
	 * @param num 変換対象
	 *
	 * @return 変換結果
	 */
	public static String parseString ( int num ) {
		if ( num == Integer.MIN_VALUE ) {
			return String.valueOf( Integer.MIN_VALUE );
		}
		char[] buff = new char[( num < 0 ? 1 : 0 ) + MathFunction.digit( Math.abs( num ) )];
		int index = 0;
		if ( num < 0 ) {
			buff[index++] = '-';
			num = Math.abs( num );
		}
		int d = maxDigit( num );
		while ( d > 0 ) {
			int count = 0;
			while ( d <= num ) {
				num -= d;
				++count;
			}
			buff[index++] = ( char )( count + '0' );
			d = nextMinMaxDigit( d );
		}
		return new String( buff );
	}

	/**
	 * longをStringに変換します。
	 *
	 * @param num 変換対象
	 *
	 * @return 変換結果
	 */
	public static String parseString ( long num ) {
		if ( num == Long.MIN_VALUE ) {
			return String.valueOf( Long.MIN_VALUE );
		}
		char[] buff = new char[( num < 0 ? 1 : 0 ) + MathFunction.digit( Math.abs( num ) )];
		int index = 0;
		if ( num < 0 ) {
			buff[index++] = '-';
			num = Math.abs( num );
		}
		long d = maxDigit( num );
		while ( d > 0 ) {
			int count = 0;
			while ( d <= num ) {
				num -= d;
				++count;
			}
			buff[index++] = ( char )( count + '0' );
			d = nextMinMaxDigit( d );
		}
		return new String( buff );
	}

	private static int maxDigit ( int num ) {
		if ( num < 10 ) {
			return 1;
		}
		if ( num < 100 ) {
			return 10;
		}
		if ( num < 1000 ) {
			return 100;
		}
		if ( num < 10000 ) {
			return 1000;
		}
		if ( num < 100000 ) {
			return 10000;
		}
		if ( num < 1000000 ) {
			return 100000;
		}
		if ( num < 10000000 ) {
			return 1000000;
		}
		if ( num < 100000000 ) {
			return 10000000;
		}
		if ( num < 1000000000 ) {
			return 100000000;
		}
		return 1000000000;
	}

	private static long maxDigit ( long num ) {
		if ( num < 10L ) {
			return 1L;
		}
		if ( num < 100L ) {
			return 10L;
		}
		if ( num < 1000L ) {
			return 100L;
		}
		if ( num < 10000L ) {
			return 1000L;
		}
		if ( num < 100000L ) {
			return 10000L;
		}
		if ( num < 1000000L ) {
			return 100000L;
		}
		if ( num < 10000000L ) {
			return 1000000L;
		}
		if ( num < 100000000L ) {
			return 10000000L;
		}
		if ( num < 1000000000L ) {
			return 100000000L;
		}
		if ( num < 10000000000L ) {
			return 1000000000L;
		}
		if ( num < 100000000000L ) {
			return 10000000000L;
		}
		if ( num < 1000000000000L ) {
			return 100000000000L;
		}
		if ( num < 10000000000000L ) {
			return 1000000000000L;
		}
		if ( num < 100000000000000L ) {
			return 10000000000000L;
		}
		if ( num < 1000000000000000L ) {
			return 100000000000000L;
		}
		if ( num < 10000000000000000L ) {
			return 1000000000000000L;
		}
		if ( num < 100000000000000000L ) {
			return 10000000000000000L;
		}
		if ( num < 1000000000000000000L ) {
			return 100000000000000000L;
		}
		return 1000000000000000000L;
	}

	private static int nextMinMaxDigit ( int num ) {
		if ( num == 1 ) {
			return 0;
		}
		if ( num == 10 ) {
			return 1;
		}
		if ( num == 100 ) {
			return 10;
		}
		if ( num == 1000 ) {
			return 100;
		}
		if ( num == 10000 ) {
			return 1000;
		}
		if ( num == 100000 ) {
			return 10000;
		}
		if ( num == 1000000 ) {
			return 100000;
		}
		if ( num == 10000000 ) {
			return 1000000;
		}
		if ( num == 100000000 ) {
			return 10000000;
		}
		return 100000000;
	}

	private static long nextMinMaxDigit ( long num ) {
		if ( num == 1L ) {
			return 0L;
		}
		if ( num == 10L ) {
			return 1L;
		}
		if ( num == 100L ) {
			return 10L;
		}
		if ( num == 1000L ) {
			return 100L;
		}
		if ( num == 10000L ) {
			return 1000L;
		}
		if ( num == 100000L ) {
			return 10000L;
		}
		if ( num == 1000000L ) {
			return 100000L;
		}
		if ( num == 10000000L ) {
			return 1000000L;
		}
		if ( num == 100000000L ) {
			return 10000000L;
		}
		if ( num == 1000000000L ) {
			return 100000000L;
		}
		if ( num == 10000000000L ) {
			return 1000000000L;
		}
		if ( num == 100000000000L ) {
			return 10000000000L;
		}
		if ( num == 1000000000000L ) {
			return 100000000000L;
		}
		if ( num == 10000000000000L ) {
			return 1000000000000L;
		}
		if ( num == 100000000000000L ) {
			return 10000000000000L;
		}
		if ( num == 1000000000000000L ) {
			return 100000000000000L;
		}
		if ( num == 10000000000000000L ) {
			return 1000000000000000L;
		}
		if ( num == 100000000000000000L ) {
			return 10000000000000000L;
		}
		return 100000000000000000L;
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
			i ^= i & ( -i );
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
		for ( int i = size - 1; i >= n; --i ) {
			bit[i] = ( bit[i - n] << num ) | ( i != n && num != 0 ? bit[i - n - 1] >>> ( 64 - num ) : 0L );
		}
		for ( int i = 0; i < n; ++i ) {
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
		for ( int i = 0; i < size - n; ++i ) {
			bit[i] = ( bit[i + n] >>> num ) | ( i + n + 1 != size && num != 0 ? bit[i + n + 1] << ( 64 - num ) : 0L );
		}
		for ( int i = size - 1; i >= size - n; --i ) {
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
		for ( int i = 0; i < m; ++i ) {
			bit[i] &= bit2[i];
		}
		for ( int i = m; i < size; ++i ) {
			bit[i] = 0;
		}
		bit[size - 1] &= MASK;
	}

	public void or ( final Bitset b ) {
		final long[] bit2 = b.longValues();
		final int m = Math.min( bit2.length, size );
		for ( int i = 0; i < m; ++i ) {
			bit[i] |= bit2[i];
		}
		bit[size - 1] &= MASK;
	}

	public void xor ( final Bitset b ) {
		final long[] bit2 = b.longValues();
		final int m = Math.min( bit2.length, size );
		for ( int i = 0; i < m; ++i ) {
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

	public SegmentTree ( E[] arr, E def, boolean include ) {
		N = 2;
		size = 1;
		while ( N < arr.length << 1 ) {
			N <<= 1;
			size <<= 1;
		}
		node = new Object[N];
		this.def = def;
		System.arraycopy(arr,0,node,size,arr.length);
		for(int i=arr.length+size;i<N;i++)
			node[i] = def;
		updateAll();
		size -= include ? 1 : 0;
	}

	public SegmentTree ( int n, E def ) {
		this( n, def, false );
	}

	@SuppressWarnings( "unchecked" )
	private void updateAll(){
		for(int i=size-1;i>0;i--)
			node[i] = function( ( E )node[i << 1], ( E )node[( i << 1 ) + 1] );
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
	private final int[] par, rank, size, path;
	private int count;

	public UnionFind ( int N ) {
		count = N;
		par = new int[N];
		rank = new int[N];
		size = new int[N];
		path = new int[N];
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
		++path[rx];
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
		path[rx] += path[ry];
		size[rx] += size[ry];
		--count;
		return true;
	}

	public int groupCount () {
		return count;
	}

	public int pathCount ( int x ) {
		return path[root( x )];
	}

	public int size ( int x ) {
		return size[root( x )];
	}
}

// Rolling Hash
final class RollingHash implements Comparable<RollingHash> {
	private static final int base = 100;
	private static final int mod1 = 1_000_000_007;
	private static final int mod2 = Integer.MAX_VALUE;
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
		for ( int i = 1; i <= len; ++i ) {
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
		for ( int i = 0; i < len; ++i ) {
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
		for ( int i = 0; i <= len; ++i ) {
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
		for ( int i = fromIndex; i < len; ++i ) {
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
		int len = length() - str.length();
		for ( int i = fromIndex; i <= len; ++i ) {
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
		for ( int i = fromIndex; i >= 0; --i ) {
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

//Matrix
final class Matrix {
	private final long[][] matrix;

	public Matrix ( final int H, final int W, final long def ) {
		matrix = new long[H][W];
		if ( def != 0 ) {
			for ( long[] mat: matrix ) {
				Arrays.fill( mat, def );
			}
		}
	}

	public Matrix ( final int H, final int W ) {
		this( H, W, 0 );
	}

	public Matrix ( final Dimension d, final long def ) {
		this( d.height, d.width, def );
	}

	public Matrix ( final long[][] mat ) {
		matrix = new long[mat.length][];
		for ( int i = 0; i < mat.length; ++i ) {
			matrix[i] = Arrays.copyOf( mat[i], mat[i].length );
		}
	}

	public long get ( final int i, final int j ) {
		return matrix[i][j];
	}

	public long set ( final int i, final int j, final long value ) {
		return matrix[i][j] = value;
	}

	public Matrix copy () {
		return new Matrix( matrix );
	}

	public Dimension size () {
		return new Dimension( matrix[0].length, matrix.length );
	}

	public Matrix add ( final Matrix m ) {
		if ( !size().equals( m.size() ) ) {
			throw new IllegalArgumentException( "matrix size is not same" );
		}
		final Matrix ans = new Matrix( size(), 0 );
		for ( int i = 0; i < matrix.length; ++i ) {
			for ( int j = 0; j < matrix[i].length; ++j ) {
				ans.set( i, j, matrix[i][j] + m.get( i, j ) );
			}
		}
		return ans;
	}

	public Matrix subtract ( final Matrix m ) {
		if ( !size().equals( m.size() ) ) {
			throw new IllegalArgumentException( "matrix size is not same" );
		}
		final Matrix ans = new Matrix( size(), 0 );
		for ( int i = 0; i < matrix.length; ++i ) {
			for ( int j = 0; j < matrix[i].length; ++j ) {
				ans.set( i, j, matrix[i][j] - m.get( i, j ) );
			}
		}
		return ans;
	}

	public Matrix multiply ( final Matrix m ) {
		if ( size().width != m.size().height ) {
			throw new IllegalArgumentException( "matrix length is not same" );
		}
		final Matrix ans = new Matrix( size().height, m.size().width );
		final Dimension size = ans.size();
		final int len = size().width;
		for ( int i = 0; i < size.height; ++i ) {
			for ( int j = 0; j < size.width; ++j ) {
				long sum = 0;
				for ( int k = 0; k < len; ++k ) {
					sum += matrix[i][k] * m.get( k, j );
				}
				ans.set( i, j, sum );
			}
		}
		return ans;
	}

	public Matrix modAdd ( final Matrix m, final long mod ) {
		if ( !size().equals( m.size() ) ) {
			throw new IllegalArgumentException( "matrix size is not same" );
		}
		final Matrix ans = new Matrix( size(), 0 );
		for ( int i = 0; i < matrix.length; ++i ) {
			for ( int j = 0; j < matrix[i].length; ++j ) {
				ans.set( i, j, MathFunction.remainder( matrix[i][j] + m.get( i, j ), mod ) );
			}
		}
		return ans;
	}

	public Matrix modSubtract ( final Matrix m, final long mod ) {
		if ( !size().equals( m.size() ) ) {
			throw new IllegalArgumentException( "matrix size is not same" );
		}
		final Matrix ans = new Matrix( size(), 0 );
		for ( int i = 0; i < matrix.length; ++i ) {
			for ( int j = 0; j < matrix[i].length; ++j ) {
				ans.set( i, j, MathFunction.remainder( matrix[i][j] - m.get( i, j ), mod ) );
			}
		}
		return ans;
	}

	public Matrix modMultiply ( final Matrix m, final long mod ) {
		if ( size().width != m.size().height ) {
			throw new IllegalArgumentException( "matrix length is not same" );
		}
		final Matrix ans = new Matrix( size().height, m.size().width );
		final Dimension size = ans.size();
		final int len = size().width;
		for ( int i = 0; i < size.height; ++i ) {
			for ( int j = 0; j < size.width; ++j ) {
				long sum = 0;
				for ( int k = 0; k < len; ++k ) {
					sum = MathFunction.remainder( sum + matrix[i][k] * m.get( k, j ), mod );
				}
				ans.set( i, j, sum );
			}
		}
		return ans;
	}

	public static Matrix pow ( final Matrix original, final Matrix pw, long exp ) {
		Matrix a = original.copy();
		Matrix b = pw.copy();
		while ( 0 < exp ) {
			if ( ( exp & 1 ) == 1 ) {
				a = b.multiply( a );
			}
			b = b.multiply( b );
			exp >>= 1;
		}
		return a;
	}

	public static Matrix modPow ( final Matrix original, final Matrix pw, long exp, final long mod ) {
		Matrix a = original.copy();
		Matrix b = pw.copy();
		while ( 0 < exp ) {
			if ( ( exp & 1 ) == 1 ) {
				a = b.modMultiply( a, mod );
			}
			b = b.modMultiply( b, mod );
			exp >>= 1;
		}
		return a;
	}

	public long determinant () {
		return determinant( matrix );
	}

	private static long determinant ( long[][] mat ) {
		if ( mat.length == 1 ) {
			return mat[0][0];
		}
		long[][] miniMat = new long[mat.length-1][mat.length-1];
		for ( int i = 1; i < mat.length; ++i ) {
			System.arraycopy( mat[i], 1, miniMat[i-1], 0, miniMat.length );
		}
		long ans = mat[0][0] * determinant( miniMat );
		for ( int i = 1; i < mat.length; ++i ) {
			for ( int j = 1; j < mat.length; ++j ) {
				miniMat[j-1][i-1] = mat[j][i-1];
			}
			long num = mat[0][i] * determinant( miniMat );
			ans += i % 2 == 0 ? num : -num;
		}
		return ans;
	}

	@Override
	public String toString () {
		final StringBuilder ans = new StringBuilder();
		ans.append( Arrays.toString( matrix[0] ) );
		for ( int i = 1; i < matrix.length; ++i ) {
			ans.append( "\n" );
			ans.append( Arrays.toString( matrix[i] ) );
		}
		return ans.toString();
	}
}

//AVLTree series
final class TreeInt {
	private Node root;
	private int size, hash;

	public TreeInt () {
		size = 0;
		root = null;
		hash = 0;
	}

	static final private class Node {
		int value;
		int height, size;
		Node left, right, parent;

		public Node ( final Node p, final int v ) {
			value = v;
			parent = p;
			height = 1;
			size = 1;
		}
	}

	public boolean add ( final int x ) {
		boolean bool = true;
		if ( root == null ) {
			root = new Node( null, x );
		}
		else {
			Node par;
			Node now = root;
			do {
				par = now;
				if ( x < now.value ) {
					now = now.left;
				}
				else if ( x > now.value ) {
					now = now.right;
				}
				else {
					bool = false;
					break;
				}
			} while ( now != null );
			if ( bool ) {
				if ( x < par.value ) {
					par.left = new Node( par, x );
				}
				else {
					par.right = new Node( par, x );
				}
				fix( par );
			}
		}
		if ( bool ) {
			++size;
			hash ^= x;
		}
		return bool;
	}

	public int get ( int index ) {
		if ( root == null || size <= index ) {
			throw new NullPointerException();
		}
		Node now = root;
		while ( true ) {
			assert now != null;
			int ls = now.left != null ? now.left.size : 0;
			if ( index < ls ) {
				now = now.left;
			}
			else if ( ls < index ) {
				now = now.right;
				index -= ls + 1;
			}
			else {
				break;
			}
		}
		return now.value;
	}

	public boolean remove ( final int x ) {
		Node n = getNode( x );
		if ( n == null ) {
			return false;
		}
		--size;
		hash ^= n.value;
		delete( n );
		return true;
	}

	private void delete ( final Node node ) {
		if ( node != null ) {
			if ( node.left == null && node.right == null ) {
				if ( node.parent != null ) {
					if ( node.parent.left == node ) {
						node.parent.left = null;
					}
					else {
						node.parent.right = null;
					}
					fix( node.parent );
				}
				else {
					root = null;
				}
				node.parent = null;
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node rep = getFirstNode( node.right );
					node.value = rep.value;
					delete( rep );
				}
				else {
					Node rep = node.left != null ? node.left : node.right;
					rep.parent = node.parent;
					if ( node.parent != null ) {
						if ( node.parent.left == node ) {
							node.parent.left = rep;
						}
						else {
							node.parent.right = rep;
						}
						fix( node.parent );
					}
					else {
						root = rep;
					}
					node.parent = null;
				}
			}
		}
	}

	private Node getNode ( final int x ) {
		Node now = root;
		while ( now != null ) {
			if ( x < now.value ) {
				now = now.left;
			}
			else if ( x > now.value ) {
				now = now.right;
			}
			else {
				break;
			}
		}
		return now;
	}

	public int first () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getFirstNode( root ).value;
	}

	private Node getFirstNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}

	public int last () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getLastNode( root ).value;
	}

	private Node getLastNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains ( final int x ) {
		if ( root == null ) {
			return false;
		}
		return getNode( x ) != null;
	}

	public int pollFirst () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode( root );
		hash ^= min.value;
		delete( min );
		return min.value;
	}

	public int pollLast () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode( root );
		hash ^= max.value;
		delete( max );
		return max.value;
	}

	public int ceiling ( final int x ) {
		return ceiling( root, x );
	}

	private int ceiling ( Node node, final int x ) {
		Node ans = new Node( null, x - 1 );
		while ( node != null ) {
			if ( x > node.value ) {
				node = node.right;
			}
			else if ( x < node.value ) {
				ans = node;
				node = node.left;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public int higher ( final int x ) {
		return higher( root, x );
	}

	private int higher ( Node node, final int x ) {
		Node ans = new Node( null, x - 1 );
		while ( node != null ) {
			if ( x >= node.value ) {
				node = node.right;
			}
			else {
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public int floor ( final int x ) {
		return floor( root, x );
	}

	private int floor ( Node node, final int x ) {
		Node ans = new Node( null, x + 1 );
		while ( node != null ) {
			if ( x < node.value ) {
				node = node.left;
			}
			else if ( x > node.value ) {
				ans = node;
				node = node.right;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public int lower ( final int x ) {
		return lower( root, x );
	}

	private int lower ( Node node, final int x ) {
		Node ans = new Node( null, x + 1 );
		while ( node != null ) {
			if ( x <= node.value ) {
				node = node.left;
			}
			else {
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public void clear () {
		root = null;
		size = 0;
		hash = 0;
	}

	public boolean isEmpty () {
		return size == 0;
	}

	public int size () {
		return size;
	}

	public int[] toArray () {
		final int[] list = new int[size];
		if ( root != null ) {
			int index = 0;
			java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>();
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node now = deq.pollLast();
				if ( index == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						list[index++] = now.value;
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list[index - 1] < now.left.value ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					list[index++] = now.value;
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString () {
		final int[] list = toArray();
		return java.util.Arrays.toString( list );
	}

	@Override
	public boolean equals ( final Object o ) {
		if ( o instanceof TreeInt ) {
			final TreeInt tree = ( TreeInt )o;
			if ( size == tree.size() ) {
				return false;
			}
			final int[] array1 = toArray();
			final int[] array2 = tree.toArray();
			for ( int i = 0; i < size; ++i ) {
				if ( array1[i] != array2[i] ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode () {
		return hash;
	}

	/*
	 * 以下、平衡用メソッド
	 */

	private void fix ( Node node ) {
		while ( node != null ) {
			final int lh = node.left == null ? 0 : node.left.height;
			final int rh = node.right == null ? 0 : node.right.height;
			if ( lh - rh > 1 ) {
				assert node.left != null;
				if ( node.left.right != null && node.left.right.height == lh - 1 ) {
					rotateL( node.left );
				}
				rotateR( node );
			}
			else if ( rh - lh > 1 ) {
				assert node.right != null;
				if ( node.right.left != null && node.right.left.height == rh - 1 ) {
					rotateR( node.right );
				}
				rotateL( node );
			}
			else {
				setStates( node );
			}
			node = node.parent;
		}
	}

	private void rotateR ( Node node ) {
		final Node temp = node.left;
		node.left = temp.right;
		if ( node.left != null ) {
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void rotateL ( Node node ) {
		final Node temp = node.right;
		node.right = temp.left;
		if ( node.right != null ) {
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void setStates ( Node node ) {
		int lh = node.left != null ? node.left.height : 0;
		int rh = node.right != null ? node.right.height : 0;
		node.height = Math.max( lh, rh ) + 1;
		int ls = node.left != null ? node.left.size : 0;
		int rs = node.right != null ? node.right.size : 0;
		node.size = ls + rs + 1;
	}
}

final class TreeLong {
	private Node root;
	private int size, hash;

	public TreeLong () {
		size = 0;
		root = null;
		hash = 0;
	}

	static final private class Node {
		long value;
		int height, size;
		Node left, right, parent;

		public Node ( final Node p, final long v ) {
			value = v;
			parent = p;
			height = 1;
			size = 1;
		}
	}

	public boolean add ( final long x ) {
		boolean bool = true;
		if ( root == null ) {
			root = new Node( null, x );
		}
		else {
			Node par;
			Node now = root;
			do {
				par = now;
				if ( x < now.value ) {
					now = now.left;
				}
				else if ( x > now.value ) {
					now = now.right;
				}
				else {
					bool = false;
					break;
				}
			} while ( now != null );
			if ( bool ) {
				if ( x < par.value ) {
					par.left = new Node( par, x );
				}
				else {
					par.right = new Node( par, x );
				}
				fix( par );
			}
		}
		if ( bool ) {
			++size;
			hash ^= ( int )x;
		}
		return bool;
	}

	public long get ( int index ) {
		if ( root == null || size <= index ) {
			throw new NullPointerException();
		}
		Node now = root;
		while ( true ) {
			assert now != null;
			int ls = now.left != null ? now.left.size : 0;
			if ( index < ls ) {
				now = now.left;
			}
			else if ( ls < index ) {
				now = now.right;
				index -= ls + 1;
			}
			else {
				break;
			}
		}
		return now.value;
	}

	public boolean remove ( final long x ) {
		Node n = getNode( x );
		if ( n == null ) {
			return false;
		}
		--size;
		hash ^= ( int )x;
		delete( n );
		return true;
	}

	private void delete ( final Node node ) {
		if ( node != null ) {
			if ( node.left == null && node.right == null ) {
				if ( node.parent != null ) {
					if ( node.parent.left == node ) {
						node.parent.left = null;
					}
					else {
						node.parent.right = null;
					}
					fix( node.parent );
				}
				else {
					root = null;
				}
				node.parent = null;
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node rep = getFirstNode( node.right );
					node.value = rep.value;
					delete( rep );
				}
				else {
					Node rep = node.left != null ? node.left : node.right;
					rep.parent = node.parent;
					if ( node.parent != null ) {
						if ( node.parent.left == node ) {
							node.parent.left = rep;
						}
						else {
							node.parent.right = rep;
						}
						fix( node.parent );
					}
					else {
						root = rep;
					}
					node.parent = null;
				}
			}
		}
	}

	private Node getNode ( final long x ) {
		Node now = root;
		while ( now != null ) {
			if ( x < now.value ) {
				now = now.left;
			}
			else if ( x > now.value ) {
				now = now.right;
			}
			else {
				break;
			}
		}
		return now;
	}

	public long first () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getFirstNode( root ).value;
	}

	private Node getFirstNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}

	public long last () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getLastNode( root ).value;
	}

	private Node getLastNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains ( final long x ) {
		if ( root == null ) {
			return false;
		}
		return getNode( x ) != null;
	}

	public long pollFirst () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode( root );
		hash ^= ( int )min.value;
		delete( min );
		return min.value;
	}

	public long pollLast () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode( root );
		hash ^= ( int )max.value;
		delete( max );
		return max.value;
	}

	public long ceiling ( final long x ) {
		return ceiling( root, x );
	}

	private long ceiling ( Node node, final long x ) {
		Node ans = new Node( null, x - 1 );
		while ( node != null ) {
			if ( x > node.value ) {
				node = node.right;
			}
			else if ( x < node.value ) {
				ans = node;
				node = node.left;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public long higher ( final long x ) {
		return higher( root, x );
	}

	private long higher ( Node node, final long x ) {
		Node ans = new Node( null, x - 1 );
		while ( node != null ) {
			if ( x >= node.value ) {
				node = node.right;
			}
			else {
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public long floor ( final long x ) {
		return floor( root, x );
	}

	private long floor ( Node node, final long x ) {
		Node ans = new Node( null, x + 1 );
		while ( node != null ) {
			if ( x < node.value ) {
				node = node.left;
			}
			else if ( x > node.value ) {
				ans = node;
				node = node.right;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public long lower ( final long x ) {
		return lower( root, x );
	}

	private long lower ( Node node, final long x ) {
		Node ans = new Node( null, x + 1 );
		while ( node != null ) {
			if ( x <= node.value ) {
				node = node.left;
			}
			else {
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size () {
		return size;
	}

	public long[] toArray () {
		final long[] list = new long[size];
		if ( root != null ) {
			int index = 0;
			java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>();
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node now = deq.pollLast();
				if ( index == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						list[index++] = now.value;
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list[index - 1] < now.left.value ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					list[index++] = now.value;
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString () {
		final long[] list = toArray();
		return java.util.Arrays.toString( list );
	}

	@Override
	public boolean equals ( final Object o ) {
		if ( o instanceof TreeLong ) {
			final TreeLong tree = ( TreeLong )o;
			if ( size == tree.size() ) {
				return false;
			}
			final long[] array1 = toArray();
			final long[] array2 = tree.toArray();
			for ( int i = 0; i < size; ++i ) {
				if ( array1[i] != array2[i] ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode () {
		return hash;
	}

	/*
	 * 以下、平衡用メソッド
	 */

	private void fix ( Node node ) {
		while ( node != null ) {
			final int lh = node.left == null ? 0 : node.left.height;
			final int rh = node.right == null ? 0 : node.right.height;
			if ( lh - rh > 1 ) {
				assert node.left != null;
				if ( node.left.right != null && node.left.right.height == lh - 1 ) {
					rotateL( node.left );
				}
				rotateR( node );
			}
			else if ( rh - lh > 1 ) {
				assert node.right != null;
				if ( node.right.left != null && node.right.left.height == rh - 1 ) {
					rotateR( node.right );
				}
				rotateL( node );
			}
			else {
				setStates( node );
			}
			node = node.parent;
		}
	}

	private void rotateR ( Node node ) {
		final Node temp = node.left;
		node.left = temp.right;
		if ( node.left != null ) {
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void rotateL ( Node node ) {
		final Node temp = node.right;
		node.right = temp.left;
		if ( node.right != null ) {
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void setStates ( Node node ) {
		int lh = node.left != null ? node.left.height : 0;
		int rh = node.right != null ? node.right.height : 0;
		node.height = Math.max( lh, rh ) + 1;
		int ls = node.left != null ? node.left.size : 0;
		int rs = node.right != null ? node.right.size : 0;
		node.size = ls + rs + 1;
	}
}

final class Tree<E extends Comparable<E>> {
	private Node<E> root;
	private int size, hash;

	public Tree () {
		size = 0;
		root = null;
		hash = 0;
	}

	static final private class Node<E> {
		E value;
		int height, size;
		Node<E> left, right, parent;

		public Node ( final Node<E> p, final E v ) {
			value = v;
			parent = p;
			height = 1;
			size = 1;
		}
	}

	public boolean add ( final E x ) {
		boolean bool = true;
		if ( root == null ) {
			root = new Node<>( null, x );
		}
		else {
			Node<E> par;
			Node<E> now = root;
			do {
				par = now;
				int result = x.compareTo(now.value);
				if ( result < 0 ) {
					now = now.left;
				}
				else if ( result > 0 ) {
					now = now.right;
				}
				else {
					bool = false;
					break;
				}
			} while ( now != null );
			if ( bool ) {
				int result = x.compareTo(par.value);
				if ( result < 0 ) {
					par.left = new Node<>( par, x );
				}
				else {
					par.right = new Node<>( par, x );
				}
				fix( par );
			}
		}
		if ( bool ) {
			++size;
			hash ^= x.hashCode();
		}
		return bool;
	}

	public E get ( int index ) {
		if ( root == null || size <= index ) {
			throw new NullPointerException();
		}
		Node<E> now = root;
		while ( true ) {
			assert now != null;
			int ls = now.left != null ? now.left.size : 0;
			if ( index < ls ) {
				now = now.left;
			}
			else if ( ls < index ) {
				now = now.right;
				index -= ls + 1;
			}
			else {
				break;
			}
		}
		return now.value;
	}

	public boolean remove ( final E x ) {
		Node<E> n = getNode( x );
		if ( n == null ) {
			return false;
		}
		--size;
		hash ^= n.value.hashCode();
		delete( n );
		return true;
	}

	private void delete ( final Node<E> node ) {
		if ( node != null ) {
			if ( node.left == null && node.right == null ) {
				if ( node.parent != null ) {
					if ( node.parent.left == node ) {
						node.parent.left = null;
					}
					else {
						node.parent.right = null;
					}
					fix( node.parent );
				}
				else {
					root = null;
				}
				node.parent = null;
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node<E> rep = getFirstNode( node.right );
					node.value = rep.value;
					delete( rep );
				}
				else {
					Node<E> rep = node.left != null ? node.left : node.right;
					rep.parent = node.parent;
					if ( node.parent != null ) {
						if ( node.parent.left == node ) {
							node.parent.left = rep;
						}
						else {
							node.parent.right = rep;
						}
						fix( node.parent );
					}
					else {
						root = rep;
					}
					node.parent = null;
				}
			}
		}
	}

	private Node<E> getNode ( final E x ) {
		Node<E> now = root;
		while ( now != null ) {
			int result = x.compareTo(now.value);
			if ( result < 0 ) {
				now = now.left;
			}
			else if ( result > 0 ) {
				now = now.right;
			}
			else {
				break;
			}
		}
		return now;
	}

	public E first () {
		if ( root == null ) {
			return null;
		}
		return getFirstNode( root ).value;
	}

	private Node<E> getFirstNode ( Node<E> node ) {
		assert node != null;
		Node<E> par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}

	public E last () {
		if ( root == null ) {
			return null;
		}
		return getLastNode( root ).value;
	}

	private Node<E> getLastNode ( Node<E> node ) {
		assert node != null;
		Node<E> par = null;
		while ( node != null ) {
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains ( final E x ) {
		if ( root == null ) {
			return false;
		}
		return getNode( x ) != null;
	}

	public E pollFirst () {
		if ( root == null ) {
			return null;
		}
		--size;
		final Node<E> min = getFirstNode( root );
		hash ^= min.value.hashCode();
		delete( min );
		return min.value;
	}

	public E pollLast () {
		if ( root == null ) {
			return null;
		}
		--size;
		final Node<E> max = getLastNode( root );
		hash ^= max.value.hashCode();
		delete( max );
		return max.value;
	}

	public E ceiling ( final E x ) {
		return ceiling( root, x );
	}

	private E ceiling ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result > 0 ) {
				node = node.right;
			}
			else if ( result < 0 ) {
				ans = node;
				node = node.left;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public E higher ( final E x ) {
		return higher( root, x );
	}

	private E higher ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result >= 0 ) {
				node = node.right;
			}
			else {
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public E floor ( final E x ) {
		return floor( root, x );
	}

	private E floor ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result < 0 ) {
				node = node.left;
			}
			else if ( result > 0 ) {
				ans = node;
				node = node.right;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public E lower ( final E x ) {
		return lower( root, x );
	}

	private E lower ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result <= 0 ) {
				node = node.left;
			}
			else {
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public void clear () {
		root = null;
		size = 0;
		hash = 0;
	}

	public boolean isEmpty () {
		return size == 0;
	}

	public int size () {
		return size;
	}

	public java.util.ArrayList<E> toList () {
		final java.util.ArrayList<E> list = new java.util.ArrayList<>();
		if ( root != null ) {
			java.util.ArrayDeque<Node<E>> deq = new java.util.ArrayDeque<>();
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node<E> now = deq.pollLast();
				if ( list.size() == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						list.add(now.value);
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list.get(list.size() - 1).compareTo( now.left.value ) < 0 ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					list.add(now.value);
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString () {
		final java.util.ArrayList<E> list = toList();
		return list.toString();
	}

	@Override
	public boolean equals ( final Object o ) {
		if ( o instanceof Tree ) {
			final Tree tree = ( Tree )o;
			if ( size == tree.size() ) {
				return false;
			}
			final java.util.ArrayList<E> list1 = toList();
			final java.util.ArrayList list2 = tree.toList();
			for ( int i = 0; i < size; ++i ) {
				if ( !list1.get(i).equals( list2.get(i) ) ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode () {
		return hash;
	}

	/*
	 * 以下、平衡用メソッド
	 */

	private void fix ( Node<E> node ) {
		while ( node != null ) {
			final int lh = node.left == null ? 0 : node.left.height;
			final int rh = node.right == null ? 0 : node.right.height;
			if ( lh - rh > 1 ) {
				assert node.left != null;
				if ( node.left.right != null && node.left.right.height == lh - 1 ) {
					rotateL( node.left );
				}
				rotateR( node );
			}
			else if ( rh - lh > 1 ) {
				assert node.right != null;
				if ( node.right.left != null && node.right.left.height == rh - 1 ) {
					rotateR( node.right );
				}
				rotateL( node );
			}
			else {
				setStates( node );
			}
			node = node.parent;
		}
	}

	private void rotateR ( Node<E> node ) {
		final Node<E> temp = node.left;
		node.left = temp.right;
		if ( node.left != null ) {
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void rotateL ( Node<E> node ) {
		final Node<E> temp = node.right;
		node.right = temp.left;
		if ( node.right != null ) {
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void setStates ( Node<E> node ) {
		int lh = node.left != null ? node.left.height : 0;
		int rh = node.right != null ? node.right.height : 0;
		node.height = Math.max( lh, rh ) + 1;
		int ls = node.left != null ? node.left.size : 0;
		int rs = node.right != null ? node.right.size : 0;
		node.size = ls + rs + 1;
	}
}

final class TreeMultiInt {
	private Node root;
	private long size;
	private int uniqueSize;
	private int hash;

	public TreeMultiInt () {
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}

	static final private class Node {
		int value;
		long count, size;
		int height;
		Node left, right, parent;

		public Node ( final Node p, final int v, final long c ) {
			value = v;
			parent = p;
			count = c;
			height = 1;
			size = c;
		}
	}

	public void add ( final int x ) {
		if ( root == null ) {
			root = new Node( null, x, 1 );
			++uniqueSize;
		}
		else {
			Node par;
			Node now = root;
			boolean bool = true;
			do {
				par = now;
				if ( x < now.value ) {
					now = now.left;
				}
				else if ( x > now.value ) {
					now = now.right;
				}
				else {
					bool = false;
					++now.count;
					break;
				}
			} while ( now != null );
			if ( bool ) {
				++uniqueSize;
				if ( x < par.value ) {
					par.left = new Node( par, x, 1 );
				}
				else {
					par.right = new Node( par, x, 1 );
				}
			}
			fix( par );
		}
		++size;
		hash ^= x;
	}

	public void add ( final int x, final long sum ) {
		if ( root == null ) {
			root = new Node( null, x, sum );
			++uniqueSize;
		}
		else {
			Node par;
			Node now = root;
			boolean bool = true;
			do {
				par = now;
				if ( x < now.value ) {
					now = now.left;
				}
				else if ( x > now.value ) {
					now = now.right;
				}
				else {
					bool = false;
					now.count += sum;
					fix( now );
					break;
				}
			} while ( now != null );
			if ( bool ) {
				++uniqueSize;
				if ( x < par.value ) {
					par.left = new Node( par, x, sum );
				}
				else {
					par.right = new Node( par, x, sum );
				}
				fix( par );
			}
		}
		size += sum;
		if ( sum % 2 == 1 ) {
			hash ^= x;
		}
	}

	public int get ( long index ) {
		if ( root == null || size <= index ) {
			throw new NullPointerException();
		}
		Node now = root;
		while ( true ) {
			assert now != null;
			long ls = now.left != null ? now.left.size : 0;
			if ( index < ls ) {
				now = now.left;
			}
			else if ( ls + now.count <= index ) {
				index -= ls + now.count;
				now = now.right;
			}
			else {
				break;
			}
		}
		return now.value;
	}

	public boolean remove ( final int x ) {
		Node n = getNode( x );
		if ( n == null ) {
			return false;
		}
		--size;
		hash ^= x;
		delete( n );
		return true;
	}

	public long remove ( final int x, final long sum ) {
		Node n = getNode( x );
		if ( n == null ) {
			return 0;
		}
		long ans = Math.min( sum, n.count );
		size -= ans;
		n.count -= ans - 1;
		if ( ans % 2 == 1 ) {
			hash ^= x;
		}
		delete( n );
		return ans;
	}

	public long removeAll ( final int x ) {
		Node n = getNode( x );
		if ( n == null ) {
			return 0;
		}
		size -= n.count;
		long ans = n.count;
		if ( n.count % 2 == 1 ) {
			hash ^= x;
		}
		n.count = 0;
		delete( n );
		return ans;
	}

	private void delete ( final Node node ) {
		if ( node != null ) {
			if ( node.count > 1 ) {
				--node.count;
				fix( node );
				return;
			}
			if ( node.left == null && node.right == null ) {
				if ( node.parent != null ) {
					if ( node.parent.left == node ) {
						node.parent.left = null;
					}
					else {
						node.parent.right = null;
					}
					fix( node.parent );
				}
				else {
					root = null;
				}
				node.parent = null;
				--uniqueSize;
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node rep = getFirstNode( node.right );
					node.value = rep.value;
					node.count = rep.count;
					rep.count = 0;
					delete( rep );
				}
				else {
					Node rep = node.left != null ? node.left : node.right;
					rep.parent = node.parent;
					if ( node.parent != null ) {
						if ( node.parent.left == node ) {
							node.parent.left = rep;
						}
						else {
							node.parent.right = rep;
						}
						fix( node.parent );
					}
					else {
						root = rep;
					}
					node.parent = null;
					--uniqueSize;
				}
			}
		}
	}

	private Node getNode ( final int x ) {
		Node now = root;
		while ( now != null ) {
			if ( x < now.value ) {
				now = now.left;
			}
			else if ( x > now.value ) {
				now = now.right;
			}
			else {
				break;
			}
		}
		return now;
	}

	public int first () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getFirstNode( root ).value;
	}

	private Node getFirstNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}

	public int last () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getLastNode( root ).value;
	}

	private Node getLastNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains ( final int x ) {
		if ( root == null ) {
			return false;
		}
		return getNode( x ) != null;
	}

	public long sum ( final int x ) {
		if ( root == null ) {
			return 0;
		}
		Node node = getNode( x );
		return node != null ? node.count : 0;
	}

	public int pollFirst () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode( root );
		hash ^= min.value;
		delete( min );
		return min.value;
	}

	public int pollLast () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode( root );
		hash ^= max.value;
		delete( max );
		return max.value;
	}

	public int ceiling ( final int x ) {
		return ceiling( root, x );
	}

	private int ceiling ( Node node, final int x ) {
		Node ans = new Node( null, x - 1, 0 );
		while ( node != null ) {
			if ( x > node.value ) {
				node = node.right;
			}
			else if ( x < node.value ) {
				ans = node;
				node = node.left;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public int higher ( final int x ) {
		return higher( root, x );
	}

	private int higher ( Node node, final int x ) {
		Node ans = new Node( null, x - 1, 0 );
		while ( node != null ) {
			if ( x >= node.value ) {
				node = node.right;
			}
			else {
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public int floor ( final int x ) {
		return floor( root, x );
	}

	private int floor ( Node node, final int x ) {
		Node ans = new Node( null, x + 1, 0 );
		while ( node != null ) {
			if ( x < node.value ) {
				node = node.left;
			}
			else if ( x > node.value ) {
				ans = node;
				node = node.right;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public int lower ( final int x ) {
		return lower( root, x );
	}

	private int lower ( Node node, final int x ) {
		Node ans = new Node( null, x + 1, 0 );
		while ( node != null ) {
			if ( x <= node.value ) {
				node = node.left;
			}
			else {
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size () {
		return uniqueSize;
	}

	public long sumSize () {
		return size;
	}

	public long[][] toArray () {
		final long[][] list = new long[uniqueSize][2];
		if ( root != null ) {
			int index = 0;
			java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>( root.height << 1 );
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node now = deq.pollLast();
				if ( index == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						list[index][0] = now.value;
						list[index++][1] = now.count;
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list[index - 1][0] < now.left.value ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					list[index][0] = now.value;
					list[index++][1] = now.count;
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	public int[] toOneArray () {
		final int[] list = new int[( int )size];
		if ( root != null ) {
			int index = 0;
			java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>();
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node now = deq.pollLast();
				if ( index == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						for ( int i = 0; i < now.count; ++i ) {
							list[index++] = now.value;
						}
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list[index - 1] < now.left.value ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					for ( int i = 0; i < now.count; ++i ) {
						list[index++] = now.value;
					}
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString () {
		final int[] list = toOneArray();
		return java.util.Arrays.toString( list );
	}

	@Override
	public boolean equals ( final Object o ) {
		if ( o instanceof TreeMultiInt ) {
			final TreeMultiInt tree = ( TreeMultiInt )o;
			if ( size == tree.size() ) {
				return false;
			}
			final long[][] array1 = toArray();
			final long[][] array2 = tree.toArray();
			for ( int i = 0; i < size; ++i ) {
				if ( array1[i][0] != array2[i][0] || array1[i][1] != array2[i][1] ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode () {
		return hash;
	}

	/*
	 * 以下、平衡用メソッド
	 */

	private void fix ( Node node ) {
		while ( node != null ) {
			final int lh = node.left == null ? 0 : node.left.height;
			final int rh = node.right == null ? 0 : node.right.height;
			if ( lh - rh > 1 ) {
				assert node.left != null;
				if ( node.left.right != null && node.left.right.height == lh - 1 ) {
					rotateL( node.left );
					setStates( node.left );
				}
				rotateR( node );
			}
			else if ( rh - lh > 1 ) {
				assert node.right != null;
				if ( node.right.left != null && node.right.left.height == rh - 1 ) {
					rotateR( node.right );
					setStates( node.right );
				}
				rotateL( node );
			}
			setStates( node );
			node = node.parent;
		}
	}

	private void rotateR ( Node node ) {
		final Node temp = node.left;
		node.left = temp.right;
		if ( node.left != null ) {
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void rotateL ( Node node ) {
		final Node temp = node.right;
		node.right = temp.left;
		if ( node.right != null ) {
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void setStates ( Node node ) {
		int lh = node.left != null ? node.left.height : 0;
		int rh = node.right != null ? node.right.height : 0;
		node.height = Math.max( lh, rh ) + 1;
		long ls = node.left != null ? node.left.size : 0;
		long rs = node.right != null ? node.right.size : 0;
		node.size = ls + rs + node.count;
	}
}

final class TreeMultiLong {
	private Node root;
	private long size;
	private int uniqueSize;
	private int hash;

	public TreeMultiLong () {
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}

	static final private class Node {
		long value;
		long count, size;
		int height;
		Node left, right, parent;

		public Node ( final Node p, final long v, final long c ) {
			value = v;
			parent = p;
			count = c;
			height = 1;
			size = c;
		}
	}

	public void add ( final long x ) {
		if ( root == null ) {
			root = new Node( null, x, 1 );
			++uniqueSize;
		}
		else {
			Node par;
			Node now = root;
			boolean bool = true;
			do {
				par = now;
				if ( x < now.value ) {
					now = now.left;
				}
				else if ( x > now.value ) {
					now = now.right;
				}
				else {
					bool = false;
					++now.count;
					break;
				}
			} while ( now != null );
			if ( bool ) {
				++uniqueSize;
				if ( x < par.value ) {
					par.left = new Node( par, x, 1 );
				}
				else {
					par.right = new Node( par, x, 1 );
				}
			}
			fix( par );
		}
		++size;
		hash ^= ( int )x;
	}

	public void add ( final long x, final long sum ) {
		if ( root == null ) {
			root = new Node( null, x, sum );
			++uniqueSize;
		}
		else {
			Node par;
			Node now = root;
			boolean bool = true;
			do {
				par = now;
				if ( x < now.value ) {
					now = now.left;
				}
				else if ( x > now.value ) {
					now = now.right;
				}
				else {
					bool = false;
					now.count += sum;
					fix( now );
					break;
				}
			} while ( now != null );
			if ( bool ) {
				++uniqueSize;
				if ( x < par.value ) {
					par.left = new Node( par, x, sum );
				}
				else {
					par.right = new Node( par, x, sum );
				}
				fix( par );
			}
		}
		size += sum;
		if ( sum % 2 == 1 ) {
			hash ^= ( int )x;
		}
	}

	public long get ( long index ) {
		if ( root == null || size <= index ) {
			throw new NullPointerException();
		}
		Node now = root;
		while ( true ) {
			assert now != null;
			long ls = now.left != null ? now.left.size : 0;
			if ( index < ls ) {
				now = now.left;
			}
			else if ( ls + now.count <= index ) {
				index -= ls + now.count;
				now = now.right;
			}
			else {
				break;
			}
		}
		return now.value;
	}

	public boolean remove ( final long x ) {
		Node n = getNode( x );
		if ( n == null ) {
			return false;
		}
		--size;
		hash ^= x;
		delete( n );
		return true;
	}

	public long remove ( final long x, final long sum ) {
		Node n = getNode( x );
		if ( n == null ) {
			return 0;
		}
		long ans = Math.min( sum, n.count );
		size -= ans;
		n.count -= ans - 1;
		if ( ans % 2 == 1 ) {
			hash ^= ( int )x;
		}
		delete( n );
		return ans;
	}

	public long removeAll ( final long x ) {
		Node n = getNode( x );
		if ( n == null ) {
			return 0;
		}
		size -= n.count;
		long ans = n.count;
		n.count = 0;
		if ( n.count % 2 == 1 ) {
			hash ^= ( int )x;
		}
		delete( n );
		return ans;
	}

	private void delete ( final Node node ) {
		if ( node != null ) {
			if ( node.count > 1 ) {
				--node.count;
				fix( node );
				return;
			}
			if ( node.left == null && node.right == null ) {
				if ( node.parent != null ) {
					if ( node.parent.left == node ) {
						node.parent.left = null;
					}
					else {
						node.parent.right = null;
					}
					fix( node.parent );
				}
				else {
					root = null;
				}
				node.parent = null;
				--uniqueSize;
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node rep = getFirstNode( node.right );
					node.value = rep.value;
					node.count = rep.count;
					rep.count = 0;
					delete( rep );
				}
				else {
					Node rep = node.left != null ? node.left : node.right;
					rep.parent = node.parent;
					if ( node.parent != null ) {
						if ( node.parent.left == node ) {
							node.parent.left = rep;
						}
						else {
							node.parent.right = rep;
						}
						fix( node.parent );
					}
					else {
						root = rep;
					}
					node.parent = null;
					--uniqueSize;
				}
			}
		}
	}

	private Node getNode ( final long x ) {
		Node now = root;
		while ( now != null ) {
			if ( x < now.value ) {
				now = now.left;
			}
			else if ( x > now.value ) {
				now = now.right;
			}
			else {
				break;
			}
		}
		return now;
	}

	public long first () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getFirstNode( root ).value;
	}

	private Node getFirstNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}

	public long last () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		return getLastNode( root ).value;
	}

	private Node getLastNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains ( final long x ) {
		if ( root == null ) {
			return false;
		}
		return getNode( x ) != null;
	}

	public long sum ( final long x ) {
		if ( root == null ) {
			return 0;
		}
		Node node = getNode( x );
		return node != null ? node.count : 0;
	}

	public long pollFirst () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode( root );
		hash ^= ( int )min.value;
		delete( min );
		return min.value;
	}

	public long pollLast () {
		if ( root == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode( root );
		hash ^= ( int )max.value;
		delete( max );
		return max.value;
	}

	public long ceiling ( final long x ) {
		return ceiling( root, x );
	}

	private long ceiling ( Node node, final long x ) {
		Node ans = new Node( null, x - 1, 0 );
		while ( node != null ) {
			if ( x > node.value ) {
				node = node.right;
			}
			else if ( x < node.value ) {
				ans = node;
				node = node.left;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public long higher ( final long x ) {
		return higher( root, x );
	}

	private long higher ( Node node, final long x ) {
		Node ans = new Node( null, x - 1, 0 );
		while ( node != null ) {
			if ( x >= node.value ) {
				node = node.right;
			}
			else {
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public long floor ( final long x ) {
		return floor( root, x );
	}

	private long floor ( Node node, final long x ) {
		Node ans = new Node( null, x + 1, 0 );
		while ( node != null ) {
			if ( x < node.value ) {
				node = node.left;
			}
			else if ( x > node.value ) {
				ans = node;
				node = node.right;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public long lower ( final long x ) {
		return lower( root, x );
	}

	private long lower ( Node node, final long x ) {
		Node ans = new Node( null, x + 1, 0 );
		while ( node != null ) {
			if ( x <= node.value ) {
				node = node.left;
			}
			else {
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size () {
		return uniqueSize;
	}

	public long sumSize () {
		return size;
	}

	public long[][] toArray () {
		final long[][] list = new long[uniqueSize][2];
		if ( root != null ) {
			int index = 0;
			java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>( root.height << 1 );
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node now = deq.pollLast();
				if ( index == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						list[index][0] = now.value;
						list[index++][1] = now.count;
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list[index - 1][0] < now.left.value ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					list[index][0] = now.value;
					list[index++][1] = now.count;
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	public long[] toOneArray () {
		final long[] list = new long[( int )size];
		if ( root != null ) {
			int index = 0;
			java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>();
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node now = deq.pollLast();
				if ( index == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						for ( int i = 0; i < now.count; ++i ) {
							list[index++] = now.value;
						}
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list[index - 1] < now.left.value ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					for ( int i = 0; i < now.count; ++i ) {
						list[index++] = now.value;
					}
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString () {
		final long[] list = toOneArray();
		return java.util.Arrays.toString( list );
	}

	@Override
	public boolean equals ( final Object o ) {
		if ( o instanceof TreeMultiInt ) {
			final TreeMultiInt tree = ( TreeMultiInt )o;
			if ( size == tree.size() ) {
				return false;
			}
			final long[][] array1 = toArray();
			final long[][] array2 = tree.toArray();
			for ( int i = 0; i < size; ++i ) {
				if ( array1[i][0] != array2[i][0] || array1[i][1] != array2[i][1] ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode () {
		return hash;
	}

	/*
	 * 以下、平衡用メソッド
	 */

	private void fix ( Node node ) {
		while ( node != null ) {
			final int lh = node.left == null ? 0 : node.left.height;
			final int rh = node.right == null ? 0 : node.right.height;
			if ( lh - rh > 1 ) {
				assert node.left != null;
				if ( node.left.right != null && node.left.right.height == lh - 1 ) {
					rotateL( node.left );
					setStates( node.left );
				}
				rotateR( node );
			}
			else if ( rh - lh > 1 ) {
				assert node.right != null;
				if ( node.right.left != null && node.right.left.height == rh - 1 ) {
					rotateR( node.right );
					setStates( node.right );
				}
				rotateL( node );
			}
			setStates( node );
			node = node.parent;
		}
	}

	private void rotateR ( Node node ) {
		final Node temp = node.left;
		node.left = temp.right;
		if ( node.left != null ) {
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void rotateL ( Node node ) {
		final Node temp = node.right;
		node.right = temp.left;
		if ( node.right != null ) {
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void setStates ( Node node ) {
		int lh = node.left != null ? node.left.height : 0;
		int rh = node.right != null ? node.right.height : 0;
		node.height = Math.max( lh, rh ) + 1;
		long ls = node.left != null ? node.left.size : 0;
		long rs = node.right != null ? node.right.size : 0;
		node.size = ls + rs + node.count;
	}
}

final class TreeMulti<E extends Comparable<E>> {
	private Node<E> root;
	private long size;
	private int uniqueSize;
	private int hash;

	public TreeMulti () {
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}

	static final private class Node<E> {
		E value;
		long count, size;
		int height;
		Node<E> left, right, parent;

		public Node ( final Node<E> p, final E v, final long c ) {
			value = v;
			parent = p;
			count = c;
			height = 1;
			size = c;
		}
	}

	public void add ( final E x ) {
		if ( root == null ) {
			root = new Node<>( null, x, 1 );
			++uniqueSize;
		}
		else {
			Node<E> par;
			Node<E> now = root;
			boolean bool = true;
			do {
				par = now;
				int result = x.compareTo(now.value);
				if ( result < 0 ) {
					now = now.left;
				}
				else if ( result > 0 ) {
					now = now.right;
				}
				else {
					bool = false;
					++now.count;
					break;
				}
			} while ( now != null );
			if ( bool ) {
				++uniqueSize;
				int result = x.compareTo(now.value);
				if ( result < 0 ) {
					par.left = new Node<>( par, x, 1 );
				}
				else {
					par.right = new Node<>( par, x, 1 );
				}
			}
			fix( par );
		}
		++size;
		hash ^= x.hashCode();
	}

	public void add ( final E x, final long sum ) {
		if ( root == null ) {
			root = new Node<>( null, x, sum );
			++uniqueSize;
		}
		else {
			Node<E> par;
			Node<E> now = root;
			boolean bool = true;
			do {
				par = now;
				int result = x.compareTo(now.value);
				if ( result < 0 ) {
					now = now.left;
				}
				else if ( result > 0 ) {
					now = now.right;
				}
				else {
					bool = false;
					now.count += sum;
					fix( now );
					break;
				}
			} while ( now != null );
			if ( bool ) {
				++uniqueSize;
				int result = x.compareTo(now.value);
				if ( result < 0 ) {
					par.left = new Node<>( par, x, sum );
				}
				else {
					par.right = new Node<>( par, x, sum );
				}
				fix( par );
			}
		}
		size += sum;
		hash ^= sum%2==1?x.hashCode():0;
	}

	public E get ( long index ) {
		if ( root == null || size <= index ) {
			throw new NullPointerException();
		}
		Node<E> now = root;
		while ( true ) {
			assert now != null;
			long ls = now.left != null ? now.left.size : 0;
			if ( index < ls ) {
				now = now.left;
			}
			else if ( ls + now.count <= index ) {
				index -= ls + now.count;
				now = now.right;
			}
			else {
				break;
			}
		}
		return now.value;
	}

	public boolean remove ( final E x ) {
		Node<E> n = getNode( x );
		if ( n == null ) {
			return false;
		}
		--size;
		hash ^= n.hashCode();
		delete( n );
		return true;
	}

	public long remove ( final E x, final long sum ) {
		Node<E> n = getNode( x );
		if ( n == null ) {
			return 0;
		}
		long ans = Math.min( sum, n.count );
		size -= ans;
		hash ^= ans%2==1?n.hashCode():0;
		n.count -= ans - 1;
		delete( n );
		return ans;
	}

	public long removeAll ( final E x ) {
		Node<E> n = getNode( x );
		if ( n == null ) {
			return 0;
		}
		size -= n.count;
		long ans = n.count;
		hash ^= ans%2==1?n.hashCode():0;
		n.count = 0;
		delete( n );
		return ans;
	}

	private void delete ( final Node<E> node ) {
		if ( node != null ) {
			if ( node.count > 1 ) {
				--node.count;
				fix( node );
				return;
			}
			if ( node.left == null && node.right == null ) {
				if ( node.parent != null ) {
					if ( node.parent.left == node ) {
						node.parent.left = null;
					}
					else {
						node.parent.right = null;
					}
					fix( node.parent );
				}
				else {
					root = null;
				}
				node.parent = null;
				--uniqueSize;
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node<E> rep = getFirstNode( node.right );
					node.value = rep.value;
					node.count = rep.count;
					rep.count = 0;
					delete( rep );
				}
				else {
					Node<E> rep = node.left != null ? node.left : node.right;
					rep.parent = node.parent;
					if ( node.parent != null ) {
						if ( node.parent.left == node ) {
							node.parent.left = rep;
						}
						else {
							node.parent.right = rep;
						}
						fix( node.parent );
					}
					else {
						root = rep;
					}
					node.parent = null;
					--uniqueSize;
				}
			}
		}
	}

	private Node<E> getNode ( final E x ) {
		Node<E> now = root;
		while ( now != null ) {
			int result = x.compareTo(now.value);
			if ( result < 0 ) {
				now = now.left;
			}
			else if ( result > 0 ) {
				now = now.right;
			}
			else {
				break;
			}
		}
		return now;
	}

	public E first () {
		if ( root == null ) {
			return null;
		}
		return getFirstNode( root ).value;
	}

	private Node<E> getFirstNode ( Node<E> node ) {
		assert node != null;
		Node<E> par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}

	public E last () {
		if ( root == null ) {
			return null;
		}
		return getLastNode( root ).value;
	}

	private Node<E> getLastNode ( Node<E> node ) {
		assert node != null;
		Node<E> par = null;
		while ( node != null ) {
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains ( final E x ) {
		if ( root == null ) {
			return false;
		}
		return getNode( x ) != null;
	}

	public long sum ( final E x ) {
		if ( root == null ) {
			return 0;
		}
		Node<E> node = getNode( x );
		return node != null ? node.count : 0;
	}

	public E pollFirst () {
		if ( root == null ) {
			return null;
		}
		--size;
		final Node<E> min = getFirstNode( root );
		hash ^= min.value.hashCode();
		delete( min );
		return min.value;
	}

	public E pollLast () {
		if ( root == null ) {
			return null;
		}
		--size;
		final Node<E> max = getLastNode( root );
		hash ^= max.value.hashCode();
		delete( max );
		return max.value;
	}

	public E ceiling ( final E x ) {
		return ceiling( root, x );
	}

	private E ceiling ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result > 0 ) {
				node = node.right;
			}
			else if ( result < 0 ) {
				ans = node;
				node = node.left;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public E higher ( final E x ) {
		return higher( root, x );
	}

	private E higher ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result >= 0 ) {
				node = node.right;
			}
			else {
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public E floor ( final E x ) {
		return floor( root, x );
	}

	private E floor ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result < 0 ) {
				node = node.left;
			}
			else if ( result > 0 ) {
				ans = node;
				node = node.right;
			}
			else {
				return x;
			}
		}
		return ans.value;
	}

	public E lower ( final E x ) {
		return lower( root, x );
	}

	private E lower ( Node<E> node, final E x ) {
		Node<E> ans = null;
		while ( node != null ) {
			int result = x.compareTo(node.value);
			if ( result <= 0 ) {
				node = node.left;
			}
			else {
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size () {
		return uniqueSize;
	}

	public long sumSize () {
		return size;
	}

	public java.util.ArrayList<E> toList () {
		final java.util.ArrayList<E> list = new java.util.ArrayList<>();
		if ( root != null ) {
			java.util.ArrayDeque<Node<E>> deq = new java.util.ArrayDeque<>();
			deq.add( root );
			while ( !deq.isEmpty() ) {
				Node<E> now = deq.pollLast();
				if ( list.size() == 0 ) {
					if ( now.left != null ) {
						deq.add( now );
						deq.add( now.left );
					}
					else {
						for ( int i = 0; i < now.count; ++i ) {
							list.add(now.value);
						}
						if ( now.right != null ) {
							deq.add( now.right );
						}
					}
				}
				else if ( now.left != null && list.get( list.size() - 1 ).compareTo( now.left.value ) < 0 ) {
					deq.add( now );
					deq.add( now.left );
				}
				else {
					for ( int i = 0; i < now.count; ++i ) {
						list.add(now.value);
					}
					if ( now.right != null ) {
						deq.add( now.right );
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString () {
		final java.util.ArrayList<E> list = toList();
		return list.toString();
	}

	@Override
	public boolean equals ( final Object o ) {
		if ( o instanceof TreeMulti ) {
			final TreeMulti tree = ( TreeMulti )o;
			if ( size == tree.size() ) {
				return false;
			}
			final java.util.ArrayList<E> array1 = toList();
			final java.util.ArrayList array2 = tree.toList();
			for ( int i = 0; i < size; ++i ) {
				if ( !array1.get( i ).equals( array2.get( i ) ) ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode () {
		return hash;
	}

	/*
	 * 以下、平衡用メソッド
	 */

	private void fix ( Node<E> node ) {
		while ( node != null ) {
			final int lh = node.left == null ? 0 : node.left.height;
			final int rh = node.right == null ? 0 : node.right.height;
			if ( lh - rh > 1 ) {
				assert node.left != null;
				if ( node.left.right != null && node.left.right.height == lh - 1 ) {
					rotateL( node.left );
					setStates( node.left );
				}
				rotateR( node );
			}
			else if ( rh - lh > 1 ) {
				assert node.right != null;
				if ( node.right.left != null && node.right.left.height == rh - 1 ) {
					rotateR( node.right );
					setStates( node.right );
				}
				rotateL( node );
			}
			setStates( node );
			node = node.parent;
		}
	}

	private void rotateR ( Node<E> node ) {
		final Node<E> temp = node.left;
		node.left = temp.right;
		if ( node.left != null ) {
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void rotateL ( Node<E> node ) {
		final Node<E> temp = node.right;
		node.right = temp.left;
		if ( node.right != null ) {
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if ( node.parent != null ) {
			if ( node.parent.left == node ) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		else {
			root = temp;
		}
		node.parent = temp;
		setStates( node );
	}

	private void setStates ( Node<E> node ) {
		int lh = node.left != null ? node.left.height : 0;
		int rh = node.right != null ? node.right.height : 0;
		node.height = Math.max( lh, rh ) + 1;
		long ls = node.left != null ? node.left.size : 0;
		long rs = node.right != null ? node.right.size : 0;
		node.size = ls + rs + node.count;
	}
}

// MyScanner
final class SimpleScanner {
	final private int buff_size = 1 << 12;
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
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextByte();
		}
		return ans;
	}

	public int[] nextInt ( int n ) {
		int[] ans = new int[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextInt();
		}
		return ans;
	}

	public long[] nextLong ( int n ) {
		long[] ans = new long[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextLong();
		}
		return ans;
	}

	public String[] next ( int n ) {
		String[] ans = new String[n];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = next();
		}
		return ans;
	}

	public byte[][] nextByte ( int n, int m ) {
		byte[][] ans = new byte[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextByte( m );
		}
		return ans;
	}

	public int[][] nextInt ( int n, int m ) {
		int[][] ans = new int[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextInt( m );
		}
		return ans;
	}

	public long[][] nextLong ( int n, int m ) {
		long[][] ans = new long[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextLong( m );
		}
		return ans;
	}

	public String[][] next ( int n, int m ) {
		String[][] ans = new String[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = next( m );
		}
		return ans;
	}

	public char[] nextCharArray () {
		return next().toCharArray();
	}

	public char[][] nextCharArray ( int n ) {
		char[][] ans = new char[n][];
		for ( int i = 0; i < n; ++i ) {
			ans[i] = nextCharArray();
		}
		return ans;
	}

	public int[][] nextGraph ( int N, int M ) {
		if ( M == 0 ) {
			return new int[N + 1][0];
		}
		int[][] ans = new int[N + 1][];
		int[] count = new int[N + 1];
		int[][] path = nextInt( M, 2 );
		for ( int[] temp: path ) {
			++count[temp[0]];
			++count[temp[1]];
		}
		for ( int i = 1; i <= N; ++i ) {
			ans[i] = new int[count[i]];
		}
		for ( int[] temp: path ) {
			ans[temp[0]][--count[temp[0]]] = temp[1];
			ans[temp[1]][--count[temp[1]]] = temp[0];
		}
		ans[0] = new int[0];
		return ans;
	}

	public Point nextPoint () {
		return new Point( nextInt(), nextInt() );
	}

	public Point[] nextPoint ( int n ) {
		Point[] ans = new Point[n];
		for ( int i = 0; i < n; ++i ) {
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

	public void println ( int[] array ) {
		println( array, ' ' );
	}

	public void println ( int[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( str );
			print( array[i] );
		}
		println();
	}

	public void println ( int[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( c );
			print( array[i] );
		}
		println();
	}

	public void println ( int[][] array ) {
		println( array, ' ' );
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

	public void println ( long[] array ) {
		println( array, ' ' );
	}

	public void println ( long[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( str );
			print( array[i] );
		}
		println();
	}

	public void println ( long[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( c );
			print( array[i] );
		}
		println();
	}

	public void println ( long[][] array ) {
		println( array, ' ' );
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
		for ( int i = 1; i < cs.length; ++i ) {
			print( str );
			print( cs[i] );
		}
		println();
	}

	public void println ( char[] cs, char c ) {
		print( cs[0] );
		for ( int i = 1; i < cs.length; ++i ) {
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
		for ( int i = 1; i < cs.length; ++i ) {
			print( str );
			print( cs[i] );
		}
		println();
	}

	public void println ( char[][] cs, char c ) {
		print( cs[0] );
		for ( int i = 1; i < cs.length; ++i ) {
			print( c );
			print( cs[i] );
		}
		println();
	}

	public <E> void println ( E[] array ) {
		println( array, ' ' );
	}

	public <E> void println ( E[] array, String str ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( str );
			print( array[i] );
		}
		println();
	}

	public <E> void println ( E[] array, char c ) {
		print( array[0] );
		for ( int i = 1; i < array.length; ++i ) {
			print( c );
			print( array[i] );
		}
		println();
	}

	public <E> void println ( E[][] arrays ) {
		println( arrays, ' ' );
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
