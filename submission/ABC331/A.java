import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FilterOutputStream;
import java.io.Flushable;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

//Maybe use
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

final class Main{
	private static final boolean autoFlush = false;
	private static final SimpleScanner sc = new SimpleScanner(System.in);
	private static final SimpleWriter out = new SimpleWriter(System.out,autoFlush);

	public static void main(String[] args){

		int M = sc.nextInt();
		int D = sc.nextInt();
		int y = sc.nextInt();
		int m = sc.nextInt();
		int d = sc.nextInt();
		d++;
		if(D<d){
			m++;
			d = 1;
		}
		if(M<m){
			y++;
			m = 1;
		}
		out.printf("%d %d %d\n",y,m,d);

		out.close();
	}
}

/*
                                                ,.��
   ___                                          �   i
 �u�_i�r                                        �T �q
  � �m                                           i�j()
  i {                     �Q�Q�Q_                |  �R
  i  i                  �^_,  ,�]-�_              i   }
  |   i              �^�i��)   ( �� )�_           {�  ��
  �g�|��.          �^    �i__�l__�j   �_       ,�m�P,!
  i    �T�_       |        �L�P`        |  ,. '�L�  ,!
   �R�A     �M`�,__�_                 �^"  �_  �R�^
      �_�m �      ʁP  r/::r�\--�\/:�V   �       �^
          �R.          �R:<:'::'|:/   /     ,."
               `� �      �_�R:::|/      ��'"
          �^�P������������������R
         | �� |          A          C          ��|
          �_�Q������������������m
*/
/*////////////////////////////////////////////////////////////////////////////////////////////
                                        * My Library *

                                        @author  viral
*/////////////////////////////////////////////////////////////////////////////////////////////
final class Factorial{
	//�K��Ƃ��̋t��
	private final long[] fact, inFact;
	private final long mod;

	/**
	 * 1�`N�̊K��Ƃ��̋t����mod�Ŋ��������܂�����O�Ɍv�Z���܂��B
	 *
	 * @param N �v�Z�͈�
	 * @param mod �@
	 */
	public Factorial(final int N,final long mod){
		fact = new long[N+1];
		fact[0] = fact[1] = 1;
		for(int i = 2;i<=N;++i){
			fact[i] = fact[i-1]*i%mod;
		}
		inFact = new long[N+1];
		inFact[N] = MathFunction.modPow(fact[N],mod-2,mod);
		for(int i = N;i>0;--i){
			inFact[i-1] = inFact[i]*i%mod;
		}
		inFact[0] = 1;
		this.mod = mod;
	}

	/**
	 * num!��mod�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param num num!�ƂȂ�num
	 *
	 * @return num!
	 */
	public long getFact(final int num){
		return fact[num];
	}

	/**
	 * num!^-1��mod�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param num num!^-1�ƂȂ�num
	 *
	 * @return num!
	 */
	public long getInFact(final int num){
		return inFact[num];
	}

	/**
	 * mod��@�Ƃ�������num^-1��Ԃ��܂��B
	 *
	 * @param num num^-1 mod m�ƂȂ�num
	 *
	 * @return num!
	 */
	public long getInverse(final int num){
		return fact[num-1]*inFact[num]%mod;
	}

	/**
	 * aCb��mod�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param a aCb�ƂȂ�a
	 * @param b aCb�ƂȂ�b
	 *
	 * @return aCb
	 */
	public long getCombi(final int a,final int b){
		if(a<b||a<0||b<0){
			return 0;
		}
		return (fact[a]*inFact[a-b]%mod)*inFact[b]%mod;
	}
}

final class ArrayFunction{

	/**
	 * �J�E���g�\�[�g�ɂ��\�[�g�ł��B
	 * �e�v�f��0�ȏ�ł���ő�l���\�����������͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�int�^�z��
	 * @param maximumLimit array���̍ő�v�f
	 */
	public static void countSort(final int[] array,final int maximumLimit){
		countSort(array,0,array.length,maximumLimit);
	}

	/**
	 * �J�E���g�\�[�g�ɂ��\�[�g�ł��B
	 * �e�v�f��0�ȏ�ł���ő�l���\�����������͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�int�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 * @param maximumLimit array���̍ő�v�f
	 */
	public static void countSort(final int[] array,final int l,final int r,final int maximumLimit){
		final int[] list = new int[maximumLimit+1];
		for(int i = l;i<r;++i){
			++list[array[i]];
		}
		int temp = l;
		for(int i = 0;i<list.length;++i){
			while(list[i]-->0){
				array[temp++] = i;
			}
		}
	}

	/**
	 * �}���\�[�g�ɂ��\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�int�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static void insertSort(final int[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			int num = array[j];
			while(l<j&&array[j-1]>array[j]){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�long�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static void insertSort(final long[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			long num = array[j];
			while(l<j&&array[j-1]>array[j]){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�char�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static void insertSort(final char[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			char num = array[j];
			while(l<j&&array[j-1]>array[j]){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ̔z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static <E extends Comparable<E>> void insertSort(final E[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			E num = array[j];
			while(l<j&&array[j-1].compareTo(array[j])>0){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��t���\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�int�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseInsertSort(final int[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			int num = array[j];
			while(l<j&&array[j-1]<array[j]){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��t���\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�long�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseInsertSort(final long[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			long num = array[j];
			while(l<j&&array[j-1]<array[j]){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��t���\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ�char�^�z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseInsertSort(final char[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			char num = array[j];
			while(l<j&&array[j-1]<array[j]){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �}���\�[�g�ɂ��t���\�[�g�ł��B
	 * �e�v�f���قƂ�ǃ\�[�g����Ă���ꍇ�͂�����̎g�p�𐄏����܂��B
	 *
	 * @param array �\�[�g�Ώۂ̔z��
	 * @param l �\�[�g�͈͂̍��[
	 * @param r �\�[�g�͈͂̉E�[(r���܂܂Ȃ�)
	 */
	public static <E extends Comparable<E>> void reverseInsertSort(final E[] array,final int l,final int r){
		for(int i = l+1;i<r;i++){
			int j = i;
			E num = array[j];
			while(l<j&&array[j-1].compareTo(array[j])<0){
				array[j] = array[j-1];
				--j;
			}
			array[j] = num;
		}
	}

	/**
	 * �z����E�����90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static int[][] rotateR(final int[][] array){
		final int[][] ans = new int[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[ans[i].length-j-1][i];
			}
		}
		return ans;
	}

	/**
	 * �z����E�����90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static long[][] rotateR(final long[][] array){
		final long[][] ans = new long[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[ans[i].length-j-1][i];
			}
		}
		return ans;
	}

	/**
	 * �z����E�����90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static char[][] rotateR(final char[][] array){
		final char[][] ans = new char[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[ans[i].length-j-1][i];
			}
		}
		return ans;
	}

	/**
	 * �z����E�����90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static double[][] rotateR(final double[][] array){
		final double[][] ans = new double[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[ans[i].length-j-1][i];
			}
		}
		return ans;
	}

	/**
	 * �z����E�����90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static boolean[][] rotateR(final boolean[][] array){
		final boolean[][] ans = new boolean[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[ans[i].length-j-1][i];
			}
		}
		return ans;
	}

	/**
	 * �z����E�����90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static <E> E[][] rotateR(final E[][] array,final E[][] ans){
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[ans[i].length-j-1][i];
			}
		}
		return ans;
	}

	/**
	 * �z����������90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static int[][] rotateL(final int[][] array){
		final int[][] ans = new int[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			final int index = i;
			Arrays.setAll(ans[i],k->array[k][ans.length-index-1]);
		}
		return ans;
	}

	/**
	 * �z����������90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static long[][] rotateL(final long[][] array){
		final long[][] ans = new long[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			final int index = i;
			Arrays.setAll(ans[i],k->array[k][ans.length-index-1]);
		}
		return ans;
	}

	/**
	 * �z����������90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static char[][] rotateL(final char[][] array){
		final char[][] ans = new char[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[j][ans.length-i-1];
			}
		}
		return ans;
	}

	/**
	 * �z����������90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static double[][] rotateL(final double[][] array){
		final double[][] ans = new double[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[j][ans.length-i-1];
			}
		}
		return ans;
	}

	/**
	 * �z����������90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static boolean[][] rotateL(final boolean[][] array){
		final boolean[][] ans = new boolean[array[0].length][array.length];
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[j][ans.length-i-1];
			}
		}
		return ans;
	}

	/**
	 * �z����������90�x��]���������̂�Ԃ��܂��B
	 * �����`�łȂ��z��Ɋւ��Ă̓���͕ۏ؂��Ă��܂���B
	 *
	 * @param array ��]������s��
	 *
	 * @return ��]�������z��
	 */
	public static <E> E[][] rotateL(final E[][] array,final E[][] ans){
		for(int i = 0;i<ans.length;++i){
			for(int j = 0;j<ans[i].length;++j){
				ans[i][j] = array[j][ans.length-i-1];
			}
		}
		return ans;
	}

	/**
	 * �����̔z��̍Œ����`����������̒�����Ԃ��܂��B
	 *
	 * @param array �Œ����`����������̒��������߂�z��
	 *
	 * @return �Œ����`����������̒���
	 */
	public static int lis(int[] array){
		return lis(array,false);
	}

	/**
	 * �����̔z��w�肳�ꂽ�C���f�b�N�X�̍Œ����`����������̒�����Ԃ��܂��B
	 *
	 * @param arrays �Œ����`����������̒��������߂�z��
	 * @param p �T������z��̃C���f�b�N�X
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)�̍Œ����`����������̒���
	 */
	public static int lis(int[][] arrays,int p){
		return lis(arrays,p,false);
	}

	/**
	 * �����̔z��̍Œ����`����������̒�����Ԃ��܂��B
	 *
	 * @param array �Œ����`����������̒��������߂�z��
	 *
	 * @return �Œ����`����������̒���
	 */
	public static int lis(long[] array){
		return lis(array,false);
	}

	/**
	 * �����̔z��w�肳�ꂽ�C���f�b�N�X�̍Œ����`����������̒�����Ԃ��܂��B
	 *
	 * @param arrays �Œ����`����������̒��������߂�z��
	 * @param p �T������z��̃C���f�b�N�X
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)�̍Œ����`����������̒���
	 */
	public static int lis(long[][] arrays,int p){
		return lis(arrays,p,false);
	}

	/**
	 * �����̔z��̍Œ�����������̒�����Ԃ��܂��B
	 *
	 * @param array �Œ�����������̒��������߂�z��
	 * @param include �L�`������Ȃ�true�A���`������Ȃ�false
	 *
	 * @return �Œ����`����������̒���
	 */
	public static int lis(int[] array,boolean include){
		int[] list = new int[array.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int num: array){
			int index = include?Searcher.overSearch(list,num):Searcher.upSearch(list,num);
			list[index] = Math.min(list[index],num);
		}
		int answer = Searcher.underSearch(list,Integer.MAX_VALUE);
		return answer+1;
	}

	/**
	 * �����̔z��w�肳�ꂽ�C���f�b�N�X�̍Œ����`����������̒�����Ԃ��܂��B
	 *
	 * @param arrays �Œ����`����������̒��������߂�z��
	 * @param p �T������z��̃C���f�b�N�X
	 * @param include �L�`������Ȃ�true�A���`������Ȃ�false
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)�̍Œ����`����������̒���
	 */
	public static int lis(int[][] arrays,int p,boolean include){
		int[] list = new int[arrays.length];
		Arrays.fill(list,Integer.MAX_VALUE);
		for(int[] array: arrays){
			int index = include?Searcher.overSearch(list,array[p]):Searcher.upSearch(list,array[p]);
			list[index] = Math.min(list[index],array[p]);
		}
		int answer = Searcher.underSearch(list,Integer.MAX_VALUE);
		return answer+1;
	}

	/**
	 * �����̔z��̍Œ�����������̒�����Ԃ��܂��B
	 *
	 * @param array �Œ�����������̒��������߂�z��
	 * @param include �L�`������Ȃ�true�A���`������Ȃ�false
	 *
	 * @return �Œ����`����������̒���
	 */
	public static int lis(long[] array,boolean include){
		long[] list = new long[array.length];
		Arrays.fill(list,Long.MAX_VALUE);
		for(long num: array){
			int index = include?Searcher.overSearch(list,num):Searcher.upSearch(list,num);
			list[index] = Math.min(list[index],num);
		}
		int answer = Searcher.underSearch(list,Long.MAX_VALUE);
		return answer+1;
	}

	/**
	 * �����̔z��w�肳�ꂽ�C���f�b�N�X�̍Œ����`����������̒�����Ԃ��܂��B
	 *
	 * @param arrays �Œ����`����������̒��������߂�z��
	 * @param p �T������z��̃C���f�b�N�X
	 * @param include �L�`������Ȃ�true�A���`������Ȃ�false
	 *
	 * @return arrays[i][p](0 < = p < = arrays.length)�̍Œ����`����������̒���
	 */
	public static int lis(long[][] arrays,int p,boolean include){
		long[] list = new long[arrays.length];
		Arrays.fill(list,Long.MAX_VALUE);
		for(long[] array: arrays){
			int index = include?Searcher.overSearch(list,array[p]):Searcher.upSearch(list,array[p]);
			list[index] = Math.min(list[index],array[p]);
		}
		int answer = Searcher.underSearch(list,Long.MAX_VALUE);
		return answer+1;
	}

	/**
	 * �����̏�񂩂狁�߂���L���ӂɑ΂��ăg�|���W�J���\�[�g���s�Ȃ��܂��B
	 * �߂�l�͕ӂ�\��int�^�񎟌��z��ł��B
	 *
	 * @param route �L���O���t�̗אڃ��X�g
	 *
	 * @return �g�|���W�J���\�[�g�ςݗL���O���t
	 */
	public static int[][] topologicalSort(final ArrayList<ArrayList<Integer>> route){
		final int[] count = new int[route.size()];
		int pathCount = 0;
		for(final ArrayList<Integer> path: route){
			for(final int point: path){
				++pathCount;
				++count[point];
			}
		}
		final ArrayDeque<Integer> deq = new ArrayDeque<>();
		for(int i = 1;i<count.length;++i){
			if(count[i]==0){
				deq.add(i);
			}
		}
		final int[][] ans = new int[pathCount][2];
		int index = 0;
		while(deq.size()>0){
			int nowP = deq.pollFirst();
			for(final int nextP: route.get(nowP)){
				ans[index][0] = nowP;
				ans[index++][1] = nextP;
				if(--count[nextP]==0){
					deq.add(nextP);
				}
			}
		}
		return ans;
	}

	/**
	 * �����̏�񂩂狁�߂���L���ӂɑ΂��ăg�|���W�J���\�[�g���s�Ȃ��܂��B
	 * �߂�l�͕ӂ�\��int�^�񎟌��z��ł��B
	 *
	 * @param route �L���O���t�̗אڃ��X�g
	 *
	 * @return �g�|���W�J���\�[�g�ςݗL���O���t
	 */
	public static int[][] topologicalSort(final int[][] route){
		final int[] count = new int[route.length];
		int pathCount = 0;
		for(final int[] path: route){
			for(final int point: path){
				++pathCount;
				++count[point];
			}
		}
		final ArrayDeque<Integer> deq = new ArrayDeque<>();
		for(int i = 1;i<count.length;++i){
			if(count[i]==0){
				deq.add(i);
			}
		}
		final int[][] ans = new int[pathCount][2];
		int index = 0;
		while(deq.size()>0){
			int nowP = deq.pollFirst();
			for(final int nextP: route[nowP]){
				ans[index][0] = nowP;
				ans[index++][1] = nextP;
				if(--count[nextP]==0){
					deq.add(nextP);
				}
			}
		}
		return ans;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a ������
	 * @param b ������
	 */
	public static void swap(final int[] array,final int a,final int b){
		final int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a ������
	 * @param b ������
	 */
	public static void swap(final long[] array,final int a,final int b){
		final long temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a ������
	 * @param b ������
	 */
	public static void swap(final double[] array,final int a,final int b){
		final double temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a ������
	 * @param b ������
	 */
	public static void swap(final char[] array,final int a,final int b){
		final char temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a ������
	 * @param b ������
	 */
	public static void swap(final boolean[] array,final int a,final int b){
		final boolean temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a ������
	 * @param b ������
	 */
	public static <E> void swap(final E[] array,final int a,final int b){
		final E temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a �������̑�1�C���f�b�N�X
	 * @param b �������̑�2�C���f�b�N�X
	 * @param c ������̑�1�C���f�b�N�X
	 * @param d ������̑�2�C���f�b�N�X
	 */
	public static void swap(final int[][] array,final int a,final int b,final int c,final int d){
		final int temp = array[a][b];
		array[a][b] = array[c][d];
		array[c][d] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a �������̑�1�C���f�b�N�X
	 * @param b �������̑�2�C���f�b�N�X
	 * @param c ������̑�1�C���f�b�N�X
	 * @param d ������̑�2�C���f�b�N�X
	 */
	public static void swap(final long[][] array,final int a,final int b,final int c,final int d){
		final long temp = array[a][b];
		array[a][b] = array[c][d];
		array[c][d] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a �������̑�1�C���f�b�N�X
	 * @param b �������̑�2�C���f�b�N�X
	 * @param c ������̑�1�C���f�b�N�X
	 * @param d ������̑�2�C���f�b�N�X
	 */
	public static void swap(final double[][] array,final int a,final int b,final int c,final int d){
		final double temp = array[a][b];
		array[a][b] = array[c][d];
		array[c][d] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a �������̑�1�C���f�b�N�X
	 * @param b �������̑�2�C���f�b�N�X
	 * @param c ������̑�1�C���f�b�N�X
	 * @param d ������̑�2�C���f�b�N�X
	 */
	public static void swap(final char[][] array,final int a,final int b,final int c,final int d){
		final char temp = array[a][b];
		array[a][b] = array[c][d];
		array[c][d] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a �������̑�1�C���f�b�N�X
	 * @param b �������̑�2�C���f�b�N�X
	 * @param c ������̑�1�C���f�b�N�X
	 * @param d ������̑�2�C���f�b�N�X
	 */
	public static void swap(final boolean[][] array,final int a,final int b,final int c,final int d){
		final boolean temp = array[a][b];
		array[a][b] = array[c][d];
		array[c][d] = temp;
	}

	/**
	 * �����̈ʒu�̗v�f���������܂��B
	 *
	 * @param array ��������v�f�̊܂܂��z��
	 * @param a �������̑�1�C���f�b�N�X
	 * @param b �������̑�2�C���f�b�N�X
	 * @param c ������̑�1�C���f�b�N�X
	 * @param d ������̑�2�C���f�b�N�X
	 */
	public static <E> void swap(final E[][] array,final int a,final int b,final int c,final int d){
		final E temp = array[a][b];
		array[a][b] = array[c][d];
		array[c][d] = temp;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̎��ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Ŏ��ɓ�����z�񂪂���ꍇ��true�Aarray���~���ɕ���ł���Ȃ�false
	 */
	public static boolean nextPermutation(final int[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1]<array[i]){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = 0;
		int min = Integer.MAX_VALUE;
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){
				min = array[i];
				index2 = i;
			}
		}
		swap(array,index1,index2);
		reverseRange(array,index1+1,array.length);
		insertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̎��ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Ŏ��ɓ�����z�񂪂���ꍇ��true�Aarray���~���ɕ���ł���Ȃ�false
	 */
	public static boolean nextPermutation(final long[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1]<array[i]){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = 0;
		long min = Long.MAX_VALUE;
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){
				min = array[i];
				index2 = i;
			}
		}
		swap(array,index1,index2);
		reverseRange(array,index1+1,array.length);
		insertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̎��ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Ŏ��ɓ�����z�񂪂���ꍇ��true�Aarray���~���ɕ���ł���Ȃ�false
	 */
	public static boolean nextPermutation(final char[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1]<array[i]){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = 0;
		int min = Integer.MAX_VALUE;
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){
				min = array[i];
				index2 = i;
			}
		}
		swap(array,index1,index2);
		reverseRange(array,index1+1,array.length);
		insertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̎��ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Ŏ��ɓ�����z�񂪂���ꍇ��true�Aarray���~���ɕ���ł���Ȃ�false
	 */
	public static <E extends Comparable<E>> boolean nextPermutation(final E[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1].compareTo(array[i])<0){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = -1;
		E min = MathFunction.max(array);
		int subIndex = -1;
		E max = array[index1];
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],array[index1],min)){
				min = array[i];
				index2 = i;
			}
			if(max.compareTo(array[i])<0){
				subIndex = i;
				max = array[i];
			}
		}
		if(index2==-1){
			swap(array,index1,subIndex);
		}
		else{
			swap(array,index1,index2);
		}
		reverseRange(array,index1+1,array.length);
		insertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̒��O�ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Œ��O�ɓ�����z�񂪂���ꍇ��true�Aarray�������ɕ���ł���Ȃ�false
	 */
	public static boolean previousPermutation(final int[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1]>array[i]){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = 0;
		int max = Integer.MIN_VALUE;
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],max,array[index1])){
				max = array[i];
				index2 = i;
			}
		}
		swap(array,index1,index2);
		reverseRange(array,index1+1,array.length);
		reverseInsertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̒��O�ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Œ��O�ɓ�����z�񂪂���ꍇ��true�Aarray�������ɕ���ł���Ȃ�false
	 */
	public static boolean previousPermutation(final long[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1]>array[i]){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = 0;
		long max = Long.MIN_VALUE;
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],max,array[index1])){
				max = array[i];
				index2 = i;
			}
		}
		swap(array,index1,index2);
		reverseRange(array,index1+1,array.length);
		reverseInsertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̒��O�ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Œ��O�ɓ�����z�񂪂���ꍇ��true�Aarray�������ɕ���ł���Ȃ�false
	 */
	public static boolean previousPermutation(final char[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1]>array[i]){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = 0;
		int max = Integer.MIN_VALUE;
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],max,array[index1])){
				max = array[i];
				index2 = i;
			}
		}
		swap(array,index1,index2);
		reverseRange(array,index1+1,array.length);
		reverseInsertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��̗v�f����ёւ������́A�������ň����̒��O�ɓ����鏇�Ԃɕ��ёւ��܂��B
	 * �����Ƃ��āA�v�f�ɏd�����������̂Ƃ��ď������܂��B�d��������ꍇ�̖߂�l�͕ۏ؂���Ă��܂���B
	 *
	 * @param array ���ёւ��Ώۂ̔z��
	 *
	 * @return �������Œ��O�ɓ�����z�񂪂���ꍇ��true�Aarray�������ɕ���ł���Ȃ�false
	 */
	public static <E extends Comparable<E>> boolean previousPermutation(final E[] array){
		int index1 = 0;
		for(int i = 1;i<array.length;++i){
			if(array[i-1].compareTo(array[i])>0){
				index1 = i;
			}
		}
		if(--index1<0){
			return false;
		}
		int index2 = -1;
		E max = MathFunction.min(array);
		int subIndex = -1;
		E min = array[index1];
		for(int i = index1+1;i<array.length;++i){
			if(MathFunction.rangeCheckOpen(array[i],max,array[index1])){
				max = array[i];
				index2 = i;
			}
			if(min.compareTo(array[i])>0){
				subIndex = i;
				min = array[i];
			}
		}
		if(index2==-1){
			swap(array,index1,subIndex);
		}
		else{
			swap(array,index1,index2);
		}
		reverseRange(array,index1+1,array.length);
		reverseInsertSort(array,index1+1,array.length);
		return true;
	}

	/**
	 * �����̔z��Ɗ֐������ɔz����č\�z���܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param func �z��Ɏ{���֐�
	 */
	public static int[] reBuild(final int[] array,final IntUnaryOperator func){
		final int[] ans = new int[array.length];
		for(int i = 0;i<array.length;++i){
			ans[i] = func.applyAsInt(array[i]);
		}
		return ans;
	}

	/**
	 * �����̔z��Ɗ֐������ɔz����č\�z���܂��B
	 *
	 * @param array �@���ƂȂ�z��
	 * @param func �z��Ɏ{���֐�
	 */
	public static int[] reBuild(final int[] array,final IntBinaryOperator func){
		final int[] ans = new int[array.length];
		for(int i = 0;i<array.length;++i){
			ans[i] = func.applyAsInt(i,array[i]);
		}
		return ans;
	}

	/**
	 * �����̔z��Ɗ֐������ɔz����č\�z���܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param func �z��Ɏ{���֐�
	 */
	public static long[] reBuild(final long[] array,final LongUnaryOperator func){
		final long[] ans = new long[array.length];
		for(int i = 0;i<array.length;++i){
			ans[i] = func.applyAsLong(array[i]);
		}
		return ans;
	}

	/**
	 * �����̔z��Ɗ֐������ɔz����č\�z���܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param func �z��Ɏ{���֐�
	 */
	public static long[] reBuild(final long[] array,final LongBinaryOperator func){
		final long[] ans = new long[array.length];
		for(int i = 0;i<array.length;++i){
			ans[i] = func.applyAsLong(i,array[i]);
		}
		return ans;
	}

	/**
	 * �z��̗v�f�����Ɉ�����Consumer�ɍ�p�����܂��B
	 *
	 * @param array ��p������z��
	 * @param func ��p���Consumer
	 */
	public static void computeByArray(final int[] array,final IntConsumer func){
		for(final int num: array){
			func.accept(num);
		}
	}

	/**
	 * �����̋�Ԃ̗v�f���t���ɂ��܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param l ��Ԃ̍��[
	 * @param r ��Ԃ̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseRange(final int[] array,int l,int r){
		while(l<r)
			swap(array,l++,--r);
	}

	/**
	 * �����̋�Ԃ̗v�f���t���ɂ��܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param l ��Ԃ̍��[
	 * @param r ��Ԃ̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseRange(final long[] array,int l,int r){
		while(l<r)
			swap(array,l++,--r);
	}

	/**
	 * �����̋�Ԃ̗v�f���t���ɂ��܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param l ��Ԃ̍��[
	 * @param r ��Ԃ̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseRange(final double[] array,int l,int r){
		while(l<r)
			swap(array,l++,--r);
	}

	/**
	 * �����̋�Ԃ̗v�f���t���ɂ��܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param l ��Ԃ̍��[
	 * @param r ��Ԃ̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseRange(final char[] array,int l,int r){
		while(l<r)
			swap(array,l++,--r);
	}

	/**
	 * �����̋�Ԃ̗v�f���t���ɂ��܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param l ��Ԃ̍��[
	 * @param r ��Ԃ̉E�[(r���܂܂Ȃ�)
	 */
	public static void reverseRange(final boolean[] array,int l,int r){
		while(l<r)
			swap(array,l++,--r);
	}

	/**
	 * �����̋�Ԃ̗v�f���t���ɂ��܂��B
	 *
	 * @param array ���ƂȂ�z��
	 * @param l ��Ԃ̍��[
	 * @param r ��Ԃ̉E�[(r���܂܂Ȃ�)
	 */
	public static <E> void reverseRange(final E[] array,int l,int r){
		while(l<r)
			swap(array,l++,--r);
	}

	/**
	 * Mo's Algorithm�p��Comparator��Ԃ��܂��B
	 *
	 * @param ������
	 * @return Mo's Algorithm�p��Comparator
	 */
	public static Comparator<int[]> mo_sComparator(final int M){
		return (a,b)->a[0]/M!=b[0]/M?Integer.compare(a[0]/M,b[0]/M):
			Integer.compare(a[1]/M,b[1]/M)*((a[0]/M&1)==0?-1:1);
	}
}

final class Converter{
	/**
	 * �n���ꂽ��������t���ɂ����������Ԃ��܂��B
	 *
	 * @param str ���̕�����
	 *
	 * @return str���t���ɂ���������
	 */
	public static String reverse(final String str){
		final StringBuilder sb = new StringBuilder();
		for(int i = str.length()-1;i>=0;--i){
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static int[] toIntArray(final List<Integer> list){
		final int[] ans = new int[list.size()];
		int index = 0;
		for(int num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static long[] toLongArray(final List<Long> list){
		final long[] ans = new long[list.size()];
		int index = 0;
		for(long num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static char[] toCharArray(final List<Character> list){
		final char[] ans = new char[list.size()];
		int index = 0;
		for(char num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static double[] toDoubleArray(final List<Double> list){
		final double[] ans = new double[list.size()];
		int index = 0;
		for(double num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static boolean[] toBooleanArray(final List<Boolean> list){
		final boolean[] ans = new boolean[list.size()];
		int index = 0;
		for(boolean num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static byte[] toByteArray(final List<Byte> list){
		final byte[] ans = new byte[list.size()];
		int index = 0;
		for(byte num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static short[] toShortArray(final List<Short> list){
		final short[] ans = new short[list.size()];
		int index = 0;
		for(short num: list){
			ans[index++] = num;
		}
		return ans;
	}

	/**
	 * �n���ꂽ���b�p�[�N���X��List���v���~�e�B���^�̔z��ɕϊ����܂��B
	 *
	 * @param list ����List
	 *
	 * @return list�̗v�f���v���~�e�B���^�ɕϊ������z��
	 */
	public static float[] toFloatArray(final List<Float> list){
		final float[] ans = new float[list.size()];
		int index = 0;
		for(float num: list){
			ans[index++] = num;
		}
		return ans;
	}
}

final class MathFunction{
	private static final int[] numberForIntPrime = {2,7,61};
	private static final long[] numberForLongPrime = {2,7,61,325,9375,28178,450775,9780504,1795265022};

	/**
	 * a��b�̍ő���񐔂����߂܂��B�߂�l��0�ȏ�ł��邱�Ƃ��ۏ؂���܂��B
	 *
	 * @param a ���񐔂����߂鐮��
	 * @param b ���񐔂����߂鐮��
	 *
	 * @return a��b�̍ő����
	 */
	public static long gcd(long a,long b){
		a = Math.abs(a);
		b = Math.abs(b);
		if(b==0){
			return a;
		}
		long temp;
		while((temp = a%b)!=0){
			a = b;
			b = temp;
		}
		return b;
	}

	/**
	 * a��b�̍ŏ����{�������߂܂��B
	 * �I�[�o�[�t���[���m�͏o���܂���B
	 *
	 * @param a ���{�������߂鐮��
	 * @param b ���{�������߂鐮��
	 *
	 * @return a��b�̍ŏ����{��
	 */
	public static long lcm(final long a,final long b){
		return a/gcd(a,b)*b;
	}

	/**
	 * �������f�������肵�܂��B
	 *
	 * @param n �����Ώ�
	 *
	 * @return n���f���ł���Ȃ�true�A�f���łȂ��Ȃ�false
	 */
	public static boolean isPrime(long n){
		n = Math.abs(n);
		if(n==2L){
			return true;
		}
		if(n==1L||(n&1L)==0L){
			return false;
		}
		if(n<=4_759_123_141L){
			return isPrimeForInt(n);
		}
		return isPrimeForBigInteger(n);
	}

	/**
	 * �������f�������肵�܂��B
	 *
	 * @param n �����Ώ�
	 *
	 * @return n���f���ł���Ȃ�true�A�f���łȂ��Ȃ�false
	 */
	private static boolean isPrimeForInt(long n){
		long d = n-1;
		while((d&1)==0L){
			d >>= 1;
		}
		for(final long a: numberForIntPrime){
			if(a>=n){
				return true;
			}
			long t = d;
			long y = MathFunction.modPow(a,t,n);
			while(t<n-1L&&y!=1&&y!=n-1){
				y = y*y%n;
				t <<= 1;
			}
			if(y!=n-1&&(t&1L)==0){
				return false;
			}
		}
		return true;
	}

	/**
	 * �������f�������肵�܂��B
	 *
	 * @param n �����Ώ�
	 *
	 * @return n���f���ł���Ȃ�true�A�f���łȂ��Ȃ�false
	 */
	private static boolean isPrimeForBigInteger(long n){
		long d = n-1L;
		while((d&1)==0L){
			d >>= 1;
		}
		final BigInteger bigN = BigInteger.valueOf(n);
		final BigInteger bigNSubOne = bigN.subtract(BigInteger.ONE);
		final BigInteger bigD = BigInteger.valueOf(d);
		for(final long a: numberForLongPrime){
			if(a>=n){
				return true;
			}
			BigInteger t = bigD;
			BigInteger y = BigInteger.valueOf(a).modPow(t,bigN);
			while(t.compareTo(bigNSubOne)<0&&!y.equals(BigInteger.ONE)&&!y.equals(bigNSubOne)){
				y = y.multiply(y).mod(bigN);
				t = t.shiftLeft(1);
			}
			if(!y.equals(bigNSubOne)&&(t.intValue()&1)==0){
				return false;
			}
		}
		return true;
	}

	/**
	 * num�ȉ��̑f����񋓂��܂��B
	 *
	 * @param num �f����T������l
	 *
	 * @return num�ȉ��̑f����int�^�z��
	 */
	public static int[] primes(final int num){
		if(num<2){
			return new int[0];
		}
		final BitSet numbers = new BitSet(num+1);
		numbers.set(2,num+1);
		final int limit = (int)Math.sqrt(num);
		int bit = 0;
		while(
			rangeCheckClose(
				bit=numbers.nextSetBit(bit+1),
				2,
				limit)){
			for(int j = bit*bit;j<=num;j+=bit){
				numbers.clear(j);
			}
		}
		final int[] answer = new int[numbers.cardinality()];
		bit = 0;
		for(int i=0;i<answer.length;++i)
			bit = (answer[i] = numbers.nextSetBit(bit+1));
		return answer;
	}

	/**
	 * a**b���v�Z���܂��B
	 *
	 * @param a ��ݏ搔
	 * @param b �w��
	 *
	 * @return a**b
	 */
	public static long pow(long a,long b){
		long ans = 1;
		while(b>0){
			if((b&1)==1){
				ans *= a;
			}
			a *= a;
			b >>= 1;
		}
		return ans;
	}

	/**
	 * a**b��mod�Ŋ��������܂���v�Z���܂��B
	 *
	 * @param a ��ݏ搔
	 * @param b �w��
	 * @param mod �@�Ƃ��鐮��
	 *
	 * @return a**b��mod�Ŋ��������܂�
	 */
	public static long modPow(long a,long b,final long mod){
		long ans = 1;
		a %= mod;
		while(b>0){
			if((b&1)==1){
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
	 * N!���v�Z���܂��B
	 *
	 * @param N �K������߂�̂ɗp����l
	 *
	 * @return N!
	 */
	public static long fact(final int N){
		long ans = 1;
		for(int i = 2;i<=N;++i){
			ans *= i;
		}
		return ans;
	}

	/**
	 * N!��mod�Ŋ��������܂���v�Z���܂��B
	 *
	 * @param N �K������߂�̂ɗp����l
	 * @param mod �@�Ƃ��鐮��
	 *
	 * @return N!��mod�Ŋ��������܂�
	 */
	public static long modFact(final int N,final long mod){
		long ans = 1;
		for(int i = 2;i<=N;++i){
			ans *= i;
			ans %= mod;
		}
		return ans;
	}

	/**
	 * nCr���v�Z���܂��B
	 *
	 * @param n �񍀌W�������߂�̂ɗp����l
	 * @param r �񍀌W�������߂�̂ɗp����l
	 *
	 * @return nCr
	 */
	public static long combi(final long n,long r){
		if(r<0||n<r){
			return 0;
		}
		long ans = 1;
		r = Math.min(n-r,r);
		for(int i = 0;i<r;++i){
			ans *= n-i;
			ans /= i+1;
		}
		return ans;
	}

	/**
	 * nCr��mod�Ŋ��������܂���v�Z���܂��B
	 *
	 * @param n �񍀌W�������߂�̂ɗp����l
	 * @param r �񍀌W�������߂�̂ɗp����l
	 * @param mod �@�Ƃ��鐮��
	 *
	 * @return nCr��mod�Ŋ��������܂�
	 */
	public static long modCombi(final long n,long r,final long mod){
		if(r<0||n<r){
			return 0;
		}
		long ans = 1;
		r = Math.min(n-r,r);
		for(int i = 0;i<r;++i){
			ans *= (n-i)%mod;
			ans %= mod;
			ans *= modPow(i+1,mod-2,mod);
			ans %= mod;
		}
		return ans;
	}

	/**
	 * �����̑O����_�A�㔼��_�ō\��������������������Ă��邩�Ԃ��܂��B
	 *
	 * @param x1 �_1��x���W
	 * @param y1 �_1��y���W
	 * @param x2 �_2��x���W
	 * @param y2 �_2��y���W
	 * @param x3 �_3��x���W
	 * @param y3 �_3��y���W
	 * @param x4 �_4��x���W
	 * @param y4 �_4��y���W
	 *
	 * @return �������Ă���(�����̒[�������̐�����ɑ��݂���ꍇ���܂�)�ꍇ��1�A�������������Ȃ�0�A����ȊO��-1
	 */
	public static int isCrossed(final int x1,final int y1,final int x2,final int y2,final int x3,final int y3,final int x4,final int y4){
		final long s1 = (long)(x1-x2)*(y3-y1)-(long)(y1-y2)*(x3-x1);
		final long t1 = (long)(x1-x2)*(y4-y1)-(long)(y1-y2)*(x4-x1);
		final long s2 = (long)(x3-x4)*(y1-y3)-(long)(y3-y4)*(x1-x3);
		final long t2 = (long)(x3-x4)*(y2-y3)-(long)(y3-y4)*(x2-x3);
		final long temp1 = s1*t1;
		final long temp2 = s2*t2;
		if(temp1>0||temp2>0){
			return -1;
		}
		if(temp1==0&&temp2==0){
			return 0;
		}
		return 1;
	}

	/**
	 * �����̑O����_�A�㔼��_�ō\��������������������Ă��邩�Ԃ��܂��B
	 *
	 * @param p1 �_1
	 * @param p2 �_2
	 * @param p3 �_3
	 * @param p4 �_4
	 *
	 * @return �������Ă���(�����̒[�������̐�����ɑ��݂���ꍇ���܂�)�ꍇ��1�A�������������Ȃ�0�A����ȊO��-1
	 */
	public static int isCrossed(final Point p1,final Point p2,final Point p3,final Point p4){
		return isCrossed(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y,p4.x,p4.y);
	}

	/**
	 * �w�肳�ꂽ���_�����Ɍ���ŏo���オ�鑽�p�`���ʑ��p�`�����肵�܂��B
	 *
	 * @param points ���p�`���\������_
	 *
	 * @return ���p�`���ʑ��p�`�Ȃ�true
	 */
	public static boolean isConvex(final Point... points){
		final int n = points.length;
		if(n<3){
			return false;
		}
		if(n==3){
			return true;
		}
		boolean conv = true;
		for(int i = 0;i<n;++i){
			int result = isCrossed(points[i],points[(i+2)%n],points[(i+1)%n],points[(i+1+n/2)%n]);
			conv &= result>=0;
		}
		return conv;
	}

	/**
	 * num��mod�Ŋ��������܂��Ԃ��܂��B
	 * �߂�l��0�ȏ�mod�����ł��邱�Ƃ��ۏ؂���܂��B
	 *
	 * @param num �폜�Z��
	 * @param mod �@�Ƃ���l
	 *
	 * @return num��mod�Ŋ��������܂�
	 */
	public static long remainder(long num,final long mod){
		num %= mod;
		if(num<0){
			num += mod;
		}
		return num;
	}

	/**
	 * num����������Ԃ��܂��B
	 * 0��1���Ƃ��đ����܂��B
	 *
	 * @param num ���ׂ鐮��
	 *
	 * @return num�̌���
	 */
	public static int digit(final long num){
		if(num<10L){
			return 1;
		}
		if(num<100L){
			return 2;
		}
		if(num<1000L){
			return 3;
		}
		if(num<10000L){
			return 4;
		}
		if(num<100000L){
			return 5;
		}
		if(num<1000000L){
			return 6;
		}
		if(num<10000000L){
			return 7;
		}
		if(num<100000000L){
			return 8;
		}
		if(num<1000000000L){
			return 9;
		}
		if(num<10000000000L){
			return 10;
		}
		if(num<100000000000L){
			return 11;
		}
		if(num<1000000000000L){
			return 12;
		}
		if(num<10000000000000L){
			return 13;
		}
		if(num<100000000000000L){
			return 14;
		}
		if(num<1000000000000000L){
			return 15;
		}
		if(num<10000000000000000L){
			return 16;
		}
		if(num<100000000000000000L){
			return 17;
		}
		if(num<1000000000000000000L){
			return 18;
		}
		return 19;
	}

	/**
	 * �����̒��ōő�̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōő�̗v�f
	 */
	public static int max(final int... nums){
		int ans = Integer.MIN_VALUE;
		for(int num: nums){
			ans = Math.max(ans,num);
		}
		return ans;
	}

	/**
	 * �����̒��ōő�̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōő�̗v�f
	 */
	public static long max(final long... nums){
		long ans = Long.MIN_VALUE;
		for(long num: nums){
			ans = Math.max(ans,num);
		}
		return ans;
	}

	/**
	 * �����̒��ōő�̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōő�̗v�f
	 */
	public static double max(final double... nums){
		double ans = -Double.MIN_VALUE;
		for(double num: nums){
			ans = Math.max(ans,num);
		}
		return ans;
	}

	/**
	 * �����̒��ōő�̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōő�̗v�f
	 */
	public static <E extends Comparable<E>> E max(final E[] nums){
		E ans = nums[0];
		for(E value: nums){
			if(ans.compareTo(value)>0){
				ans = value;
			}
		}
		return ans;
	}

	/**
	 * �����̒��ōŏ��̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōŏ��̗v�f
	 */
	public static int min(final int... nums){
		int ans = Integer.MAX_VALUE;
		for(int num: nums){
			ans = Math.min(ans,num);
		}
		return ans;
	}

	/**
	 * �����̒��ōŏ��̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōŏ��̗v�f
	 */
	public static long min(final long... nums){
		long ans = Long.MAX_VALUE;
		for(long num: nums){
			ans = Math.min(ans,num);
		}
		return ans;
	}

	/**
	 * �����̒��ōŏ��̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōŏ��̗v�f
	 */
	public static double min(final double... nums){
		double ans = Double.MAX_VALUE;
		for(double num: nums){
			ans = Math.min(ans,num);
		}
		return ans;
	}

	/**
	 * �����̒��ōŏ��̒l��Ԃ��܂��B
	 *
	 * @param nums �T���Ώ�
	 *
	 * @return �����ōŏ��̗v�f
	 */
	public static <E extends Comparable<E>> E min(final E[] nums){
		E ans = nums[0];
		for(E value: nums){
			if(ans.compareTo(value)<0){
				ans = value;
			}
		}
		return ans;
	}

	/**
	 * �����̑��a��Ԃ��܂��B
	 *
	 * @param nums ���Z�Ώ�
	 *
	 * @return �����̑��a
	 */
	public static long sum(final int... nums){
		long ans = 0;
		for(int num: nums){
			ans += num;
		}
		return ans;
	}

	/**
	 * �����̑��a��Ԃ��܂��B
	 *
	 * @param nums ���Z�Ώ�
	 *
	 * @return �����̑��a
	 */
	public static long sum(final long... nums){
		long ans = 0;
		for(long num: nums){
			ans += num;
		}
		return ans;
	}

	/**
	 * �����̑��a���w�肳�ꂽ�l�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param mod �@�Ƃ���l
	 * @param nums ���Z�Ώ�
	 *
	 * @return �����̑��a���w�肳�ꂽ�l�Ŋ��������܂�
	 */
	public static long modSum(final long mod,final int... nums){
		long ans = 0;
		for(int num: nums){
			ans += num;
			ans %= mod;
		}
		if(ans<0){
			ans += mod;
		}
		return ans;
	}

	/**
	 * �����̑��a���w�肳�ꂽ�l�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param mod �@�Ƃ���l
	 * @param nums ���Z�Ώ�
	 *
	 * @return �����̑��a���w�肳�ꂽ�l�Ŋ��������܂�
	 */
	public static long modSum(final long mod,final long... nums){
		long ans = 0;
		for(long num: nums){
			ans += num;
			ans %= mod;
		}
		if(ans<0){
			ans += mod;
		}
		return ans;
	}

	/**
	 * �w�肳�ꂽ��Ԃ̑��a��Ԃ��܂��B
	 *
	 * @param nums ���Z�Ώ�
	 * @param from ��Ԃ̍��[
	 * @param to ��Ԃ̉E�[(to���܂܂Ȃ�)
	 *
	 * @return �w�肳�ꂽ��Ԃ̑��a
	 */
	public static long sum(final int[] nums,int from,int to){
		long ans = 0;
		for(int i = from;i<to;++i){
			ans += nums[i];
		}
		return ans;
	}

	/**
	 * �w�肳�ꂽ��Ԃ̑��a��Ԃ��܂��B
	 *
	 * @param nums ���Z�Ώ�
	 * @param from ��Ԃ̍��[
	 * @param to ��Ԃ̉E�[(to���܂܂Ȃ�)
	 *
	 * @return �w�肳�ꂽ��Ԃ̑��a
	 */
	public static long sum(final long[] nums,int from,int to){
		long ans = 0;
		for(int i = from;i<to;++i){
			ans += nums[i];
		}
		return ans;
	}

	/**
	 * �w�肳�ꂽ��Ԃ̑��a���w�肳�ꂽ�l�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param nums ���Z�Ώ�
	 * @param from ��Ԃ̍��[
	 * @param to ��Ԃ̉E�[(to���܂܂Ȃ�)
	 * @param mod �@�Ƃ���l
	 *
	 * @return �����̑��a���w�肳�ꂽ�l�Ŋ��������܂�
	 */
	public static long modSum(final int[] nums,int from,int to,long mod){
		long ans = 0;
		for(int i = from;i<to;++i){
			ans += nums[i];
			ans %= mod;
		}
		if(ans<0){
			ans += mod;
		}
		return ans;
	}

	/**
	 * �w�肳�ꂽ��Ԃ̑��a���w�肳�ꂽ�l�Ŋ��������܂��Ԃ��܂��B
	 *
	 * @param nums ���Z�Ώ�
	 * @param from ��Ԃ̍��[
	 * @param to ��Ԃ̉E�[(to���܂܂Ȃ�)
	 * @param mod �@�Ƃ���l
	 *
	 * @return �����̑��a���w�肳�ꂽ�l�Ŋ��������܂�
	 */
	public static long modSum(final long[] nums,int from,int to,long mod){
		long ans = 0;
		for(int i = from;i<to;++i){
			ans += nums[i];
			ans %= mod;
		}
		if(ans<0){
			ans += mod;
		}
		return ans;
	}

	/**
	 * ����num��l�ȏ�r�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l <= num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheck(final int num,final int l,final int r){
		return l<=num&&num<r;
	}

	/**
	 * ����num��l�ȏ�r�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l <= num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheck(final long num,final long l,final long r){
		return l<=num&&num<r;
	}

	/**
	 * ����num��l�ȏ�r�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l <= num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheck(final double num,final double l,final double r){
		return l<=num&&num<r;
	}

	/**
	 * ����num��l�ȏ�r�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l <= num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static <E extends Comparable<E>> boolean rangeCheck(final E num,final E l,final E r){
		return 0<=l.compareTo(num)&&0<num.compareTo(r);
	}

	/**
	 * ����num��l���傫���Ar�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂܂Ȃ�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l < num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheckOpen(final int num,final int l,final int r){
		return l<num&&num<r;
	}

	/**
	 * ����num��l���傫���Ar�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂܂Ȃ�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l < num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheckOpen(final long num,final long l,final long r){
		return l<num&&num<r;
	}

	/**
	 * ����num��l���傫���Ar�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂܂Ȃ�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l < num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheckOpen(final double num,final double l,final double r){
		return l<num&&num<r;
	}

	/**
	 * ����num��l���傫���Ar�����͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂܂Ȃ�)
	 * @param r ���(r���܂܂Ȃ�)
	 *
	 * @return l < num < r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static <E extends Comparable<E>> boolean rangeCheckOpen(final E num,final E l,final E r){
		return 0<l.compareTo(num)&&0<num.compareTo(r);
	}

	/**
	 * ����num��l�ȏ�r�ȉ��͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂�)
	 *
	 * @return l <= num <= r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheckClose(final int num,final int l,final int r){
		return l<=num&&num<=r;
	}

	/**
	 * ����num��l�ȏ�r�ȉ��͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂�)
	 *
	 * @return l <= num <= r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheckClose(final long num,final long l,final long r){
		return l<=num&&num<=r;
	}

	/**
	 * ����num��l�ȏ�r�ȉ��͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂�)
	 *
	 * @return l <= num <= r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static boolean rangeCheckClose(final double num,final double l,final double r){
		return l<=num&&num<=r;
	}

	/**
	 * ����num��l�ȏ�r�ȉ��͈͓̔������肵�܂��B
	 *
	 * @param num ���肷��l
	 * @param l ����(l���܂�)
	 * @param r ���(r���܂�)
	 *
	 * @return l <= num <= r�𖞂����Ă����true �A �������Ă��Ȃ����false
	 */
	public static <E extends Comparable<E>> boolean rangeCheckClose(final E num,final E l,final E r){
		return 0<=l.compareTo(num)&&0<=num.compareTo(r);
	}

	/**
	 * �����̒��ł�mex�����߂܂��B
	 *
	 * @param nums ����
	 */
	public static int mex(final int... nums){
		final IntegerSet set = new IntegerSet(nums.length<<1);
		for(final int num: nums){
			set.add(num);
		}
		int ans = 0;
		while(set.contains(ans)){
			++ans;
		}
		return ans;
	}
}

final class Searcher{
	private static final int CYCLE_COUNT = Double.MAX_EXPONENT+53;

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�����ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȉ��ōő�̃C���f�b�N�X
	 */
	public static int downSearch(final int[] array,final int value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�����ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȉ��ōő�̃C���f�b�N�X
	 */
	public static int downSearch(final long[] array,final long value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�����ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȉ��ōő�̃C���f�b�N�X
	 */
	public static int downSearch(final double[] array,final double value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�����ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȉ��ōő�̃C���f�b�N�X
	 */
	public static int downSearch(final char[] array,final int value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�����ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȉ��ōő�̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int downSearch(final E[] array,final E value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c].compareTo(value)>0){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * ���X�g���̎w�肳�ꂽ�v�f��T���܂��B
	 * ���X�g���Ō��������ꍇ�͂��̗v�f�Ɠ���ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�����ōő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param list �T���Ώۂ̃��X�g
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȉ��ōő�̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int downSearch(final List<E> list,final E value){
		int a = 0, b = list.size()-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(list.get(c).compareTo(value)>0){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 * �֐����Ō��������ꍇ�͂��̒l�Ɠ���ōő�̈�����Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�l�����ōő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͉���-1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �w�肳�ꂽ�l�ȉ��ōő�̈���
	 */
	public static int downSearch(int a,int b,final int value,final IntUnaryOperator func){
		int ans = a-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsInt(c)>value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 * �֐����Ō��������ꍇ�͂��̒l�Ɠ���ōő�̈�����Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�l�����ōő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͉���-1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �w�肳�ꂽ�l�ȉ��ōő�̈���
	 */
	public static long downSearch(long a,long b,final long value,final LongUnaryOperator func){
		long ans = a-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsLong(c)>value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 * �֐����Ō��������ꍇ�͂��̒l�Ɠ���ōő�̈�����Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�l�����ōő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͉�����菬�����l��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �w�肳�ꂽ�l�ȉ��ōő�̈���
	 */
	public static double search(double a,double b,final double value,final DoubleUnaryOperator func){
		double ans = a-Math.abs(a), c;
		for(int $ = 0;$<CYCLE_COUNT;++$){
			c = (a+b)/2;
			if(func.applyAsDouble(c)>value){
				b = c;
			}
			else{
				a = (ans = c);
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value���܂܂�Ă��邩��\��boolean
	 */
	public static boolean contains(final int[] array,final int value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value���܂܂�Ă��邩��\��boolean
	 */
	public static boolean contains(final long[] array,final long value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value���܂܂�Ă��邩��\��boolean
	 */
	public static boolean contains(final double[] array,final double value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value���܂܂�Ă��邩��\��boolean
	 */
	public static boolean contains(final char[] array,final char value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value���܂܂�Ă��邩��\��boolean
	 */
	public static <E extends Comparable<E>> boolean contains(final E[] array,final E value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			int result = array[c].compareTo(value);
			if(result>0){
				b = c-1;
			}
			else if(result<0){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * ���X�g���̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param list �T���Ώۂ̃��X�g
	 * @param value �T���v�f
	 *
	 * @return list��value���܂܂�Ă��邩��\��boolean
	 */
	public static <E extends Comparable<E>> boolean contains(final List<E> list,final E value){
		int a = 0, b = list.size()-1, c;
		while(a-b<1){
			c = (a+b)/2;
			int result = list.get(c).compareTo(value);
			if(result>0){
				b = c-1;
			}
			else if(result<0){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return ���̊֐���a�ȏ�b�ȉ��ŒT���l����邩��\��boolean
	 */
	public static boolean contains(int a,int b,final int value,final IntUnaryOperator func){
		int c;
		while(a-b<1){
			c = (a+b)/2;
			int num = func.applyAsInt(c);
			if(num>value){
				b = c-1;
			}
			else if(num<value){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return ���̊֐���a�ȏ�b�ȉ��ŒT���l����邩��\��boolean
	 */
	public static boolean contains(long a,long b,final long value,final LongUnaryOperator func){
		long c;
		while(a-b<1){
			c = (a+b)/2;
			long num = func.applyAsLong(c);
			if(num>value){
				b = c-1;
			}
			else if(num<value){
				a = c+1;
			}
			else{
				return true;
			}
		}
		return false;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value�̃C���f�b�N�X(������� - 1)
	 */
	public static int search(final int[] array,final int value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value�̃C���f�b�N�X(������� - 1)
	 */
	public static int search(final long[] array,final long value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value�̃C���f�b�N�X(������� - 1)
	 */
	public static int search(final double[] array,final double value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value�̃C���f�b�N�X(������� - 1)
	 */
	public static int search(final char[] array,final char value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = c-1;
			}
			else if(array[c]<value){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return array��value���܂܂�Ă��邩��\��boolean
	 */
	public static <E extends Comparable<E>> int search(final E[] array,final E value){
		int a = 0, b = array.length-1, c;
		while(a-b<1){
			c = (a+b)/2;
			int result = array[c].compareTo(value);
			if(result>0){
				b = c-1;
			}
			else if(result<0){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return -1;
	}

	/**
	 * ���X�g���̎w�肳�ꂽ�v�f��T���܂��B
	 *
	 * @param list �T���Ώۂ̃��X�g
	 * @param value �T���v�f
	 *
	 * @return list��value�̃C���f�b�N�X(������� - 1)
	 */
	public static <E extends Comparable<E>> int search(final List<E> list,final E value){
		int a = 0, b = list.size()-1, c;
		while(a-b<1){
			c = (a+b)/2;
			int result = list.get(c).compareTo(value);
			if(result>0){
				b = c-1;
			}
			else if(result<0){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return -1;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return ���̊֐���value��������(�������a - 1)
	 */
	public static int search(int a,int b,final int value,final IntUnaryOperator func){
		int c;
		while(a-b<1){
			c = (a+b)/2;
			int num = func.applyAsInt(c);
			if(num>value){
				b = c-1;
			}
			else if(num<value){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return a-1;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return ���̊֐���value��������(�������a - 1)
	 */
	public static long search(long a,long b,final long value,final LongUnaryOperator func){
		long c;
		while(a-b<1){
			c = (a+b)/2;
			long num = func.applyAsLong(c);
			if(num>value){
				b = c-1;
			}
			else if(num<value){
				a = c+1;
			}
			else{
				return c;
			}
		}
		return a-1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X
	 */
	public static int upSearch(final int[] array,final int value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X
	 */
	public static int upSearch(final long[] array,final long value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X
	 */
	public static int upSearch(final double[] array,final double value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X
	 */
	public static int upSearch(final char[] array,final char value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͂��̗v�f�Ɠ���ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int upSearch(final E[] array,final E value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c].compareTo(value)>=0){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * ���X�g���̎w�肳�ꂽ�v�f��T���܂��B
	 * ���X�g���Ō��������ꍇ�͂��̗v�f�Ɠ���ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��list.size()��Ԃ��܂��B
	 *
	 * @param list �T���Ώۂ̃��X�g
	 * @param value �T���v�f
	 *
	 * @return �w�肳�ꂽ�v�f�ȏ�ōŏ��̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int upSearch(final List<E> list,final E value){
		int a = 0, b = list.size()-1, ans = list.size(), c;
		while(a-b<1){
			c = (a+b)/2;
			if(list.get(c).compareTo(value)>=0){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 * �֐����Ō��������ꍇ�͂��̒l�Ɠ���ōŏ��̈�����Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�l�ȏ�ōő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͏��+1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �w�肳�ꂽ�l�ȏ�ōŏ��̈���
	 */
	public static int upSearch(int a,int b,final int value,final IntUnaryOperator func){
		int ans = b+1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsInt(c)>=value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��T���܂��B
	 * �֐����Ō��������ꍇ�͂��̒l�Ɠ���ōŏ��̈�����Ԃ��܂��B
	 * ������Ȃ������ꍇ�͎w�肳�ꂽ�l�ȏ�ōő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͏��+1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �w�肳�ꂽ�l�ȏ�ōŏ��̈���
	 */
	public static long upSearch(long a,long b,final long value,final LongUnaryOperator func){
		long ans = b+1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsLong(c)>=value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��菬�����v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ő�̃C���f�b�N�X
	 */
	public static int underSearch(final int[] array,final int value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��菬�����v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ő�̃C���f�b�N�X
	 */
	public static int underSearch(final long[] array,final long value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��菬�����v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ő�̃C���f�b�N�X
	 */
	public static int underSearch(final double[] array,final double value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��菬�����v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ő�̃C���f�b�N�X
	 */
	public static int underSearch(final char[] array,final char value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>=value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��菬�����v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ő�̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int underSearch(final E[] array,final E value){
		int a = 0, b = array.length-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c].compareTo(value)>=0){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * ���X�g���̎w�肳�ꂽ�v�f��菬�����v�f��T���܂��B
	 * ���X�g���Ō��������ꍇ�͏����𖞂����ő�̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param list �T���Ώۂ̃��X�g
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ő�̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int underSearch(final List<E> list,final E value){
		int a = 0, b = list.size()-1, ans = -1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(list.get(c).compareTo(value)>=0){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��菬�����l��T���܂��B
	 * �֐����Ō��������ꍇ�͏����𖞂����ő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͉���-1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �����𖞂����ő�̈���
	 */
	public static int underSearch(int a,int b,final int value,final IntUnaryOperator func){
		int ans = a-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsInt(c)>=value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l��菬�����l��T���܂��B
	 * �֐����Ō��������ꍇ�͏����𖞂����ő�̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͉���-1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �����𖞂����ő�̈���
	 */
	public static long underSearch(long a,long b,final long value,final LongUnaryOperator func){
		long ans = a-1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsLong(c)>=value){
				b = c-1;
			}
			else{
				a = (ans = c)+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f���傫���v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int overSearch(final int[] array,final int value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f���傫���v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int overSearch(final long[] array,final long value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f���傫���v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int overSearch(final double[] array,final double value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f���傫���v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int overSearch(final char[] array,final char value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c]>value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f���傫���v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��array.length��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int overSearch(final E[] array,final E value){
		int a = 0, b = array.length-1, ans = array.length, c;
		while(a-b<1){
			c = (a+b)/2;
			if(array[c].compareTo(value)>0){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * ���X�g���̎w�肳�ꂽ�v�f���傫���v�f��T���܂��B
	 * ���X�g���Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��list.size()��Ԃ��܂��B
	 *
	 * @param list �T���Ώۂ̃��X�g
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int overSearch(final List<E> list,final E value){
		int a = 0, b = list.size()-1, ans = list.size(), c;
		while(a-b<1){
			c = (a+b)/2;
			if(list.get(c).compareTo(value)>0){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l���傫���l��T���܂��B
	 * �֐����Ō��������ꍇ�͏����𖞂����ŏ��̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͏��+1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �����𖞂����ŏ��̈���
	 */
	public static int overSearch(int a,int b,final int value,final IntUnaryOperator func){
		int ans = b+1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsInt(c)>value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �L�`�P�������Ȋ֐����̎w�肳�ꂽ�l���傫���l��T���܂��B
	 * �֐����Ō��������ꍇ�͏����𖞂����ŏ��̈�����Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ�͏��+1��Ԃ��܂��B
	 *
	 * @param a �T���͈͂̉���
	 * @param b �T���͈͂̏��
	 * @param value �T���l
	 *
	 * @return �����𖞂����ŏ��̈���
	 */
	public static long overSearch(long a,long b,final long value,final LongUnaryOperator func){
		long ans = b+1, c;
		while(a-b<1){
			c = (a+b)/2;
			if(func.applyAsLong(c)>value){
				b = (ans = c)-1;
			}
			else{
				a = c+1;
			}
		}
		return ans;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int linearSearch(final int[] array,final int value){
		for(int i = 0;i<array.length;++i){
			if(array[i]==value){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int linearSearch(final long[] array,final long value){
		for(int i = 0;i<array.length;++i){
			if(array[i]==value){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int linearSearch(final double[] array,final double value){
		for(int i = 0;i<array.length;++i){
			if(array[i]==value){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static int linearSearch(final char[] array,final char value){
		for(int i = 0;i<array.length;++i){
			if(array[i]==value){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param array �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int linearSearch(final E[] array,final E value){
		for(int i = 0;i<array.length;++i){
			if(array[i].compareTo(value)==0){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �z����̎w�肳�ꂽ�v�f��T���܂��B
	 * �z����Ō��������ꍇ�͏����𖞂����ŏ��̃C���f�b�N�X��Ԃ��܂��B
	 * �������̂悤�ȗv�f�����݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param list �T���Ώۂ̔z��
	 * @param value �T���v�f
	 *
	 * @return �����𖞂����ŏ��̃C���f�b�N�X
	 */
	public static <E extends Comparable<E>> int linearSearch(final List<E> list,final E value){
		for(int i = 0;i<list.size();++i){
			if(list.get(i).compareTo(value)==0){
				return i;
			}
		}
		return -1;
	}
}

/**
 * Binary Indexed Tree�N���X�ł��B
 * ������1-indexed�ł��邱�Ƃ�v�����܂��B
 */
final class BIT{
	final int size;
	final private long[] tree;

	/**
	 * �傫��n��BIT�𐶐����܂��B
	 *
	 * @param n ��������BIT�̑傫��
	 */
	public BIT(int n){
		size = n;
		tree = new long[n+1];
	}

	/**
	 * [1,i]�̑��a�����߂܂��B
	 *
	 * @param i ���߂��Ԃ̉E�[(i���܂�)
	 *
	 * @return [1,i]�̑��a
	 */
	public long sum(int i){
		long sum = 0;
		while(i>0){
			sum += tree[i];
			i ^= i&(-i);
		}
		return sum;
	}

	/**
	 * i�ԖڂɈ����̒l�����Z���܂��B
	 *
	 * @param i ���Z����ʒu
	 * @param x ���Z����l
	 */
	public void add(int i,long x){
		while(i<=size){
			tree[i] += x;
			i += i&(-i);
		}
	}

	/**
	 * ����BIT�̒l��S��0�ɂ��܂��B
	 */
	public void clear(){
		Arrays.fill(tree,0L);
	}
}

/**
 * Binary Indexed Tree��int�����N���X�ł��B
 * ������1-indexed�ł��邱�Ƃ�v�����܂��B
 */
final class BITInt{
	final int size;
	final private int[] tree;

	/**
	 * �傫��n��BIT�𐶐����܂��B
	 *
	 * @param n ��������BIT�̑傫��
	 */
	public BITInt(int n){
		size = n;
		tree = new int[n+1];
	}

	/**
	 * [1,i]�̑��a�����߂܂��B
	 *
	 * @param i ���߂��Ԃ̉E�[(i���܂�)
	 *
	 * @return [1,i]�̑��a
	 */
	public int sum(int i){
		int sum = 0;
		while(i>0){
			sum += tree[i];
			i ^= i&(-i);
		}
		return sum;
	}

	/**
	 * i�ԖڂɈ����̒l�����Z���܂��B
	 *
	 * @param i ���Z����ʒu
	 * @param x ���Z����l
	 */
	public void add(int i,int x){
		while(i<=size){
			tree[i] += x;
			i += i&(-i);
		}
	}

	/**
	 * ����BIT�̒l��S��0�ɂ��܂��B
	 */
	public void clear(){
		Arrays.fill(tree,0);
	}
}

/**
 * Bitset�N���X�ł��B
 * java.util.BitSet�Ɠ��l�̏������o����ق��A�r�b�g�V�t�g�ɑΉ����Ă��܂��B
 */
final class Bitset implements Cloneable{
	private final long[] bit;
	private final int size;
	private final long MASK;

	/**
	 * �����̒l�܂ł��\���Ɉ�����傫����Bitset���\�z���܂��B
	 *
	 * @param len ����Bitset�ň����ő�l(len���܂܂Ȃ�)
	 */
	public Bitset(final int len){
		size = (len+63)>>6;
		bit = new long[size];
		MASK = (-1L) >>> ((size<<6)-len);
	}

	/**
	 * ������long�^�z�������Bitset���\�z���܂��B
	 *
	 * @param arr ���ƂȂ�long�^�z��
	 */
	private Bitset(final long[] arr){
		this(arr.length);
		System.arraycopy(arr,0,bit,0,size);
	}

	/**
	 * �����̈ʒu�̃t���O��true�ɂ��܂��B
	 *
	 * @param index �t���O��true�ɂ���ʒu
	 */
	public void set(final int index){
		if(index>=size<<6){
			throw new ArrayIndexOutOfBoundsException(index+" is out of this bitset's size "+size);
		}
		bit[index>>6] |= (1L<<(index&0b111111));
	}

	/**
	 * �����̈ʒu�̃t���O��false�ɂ��܂��B
	 *
	 * @param index �t���O��false�ɂ���ʒu
	 */
	public void clear(final int index){
		if(index>=size<<6){
			throw new ArrayIndexOutOfBoundsException(index+" is out of this bitset's size "+size);
		}
		long m = ~(1L<<(index&0b111111));
		bit[index>>6] &= m;
	}

	/**
	 * �����̈ʒu�̃t���O���擾���܂��B
	 *
	 * @param index �擾����t���O�̈ʒu
	 *
	 * @return �����̈ʒu�̃t���O
	 */
	public boolean get(final int index){
		if(index>=size<<6){
			throw new ArrayIndexOutOfBoundsException(index+" is out of this bitset's size "+size);
		}
		return (bit[index>>6]&(1L<<(index&0b111111)))!=0;
	}

	/**
	 * �����̒l�̕��������Ƀr�b�g�V�t�g���܂��B
	 * �r�b�g���͕ω������A��ꂽ���̃r�b�g�͔j������܂��B
	 *
	 * @param num �r�b�g�V�t�g�����
	 */
	public void shiftLeft(int num){
		if(num>=size<<6){
			Arrays.fill(bit,0L);
			return;
		}
		final int n = num>>6;
		num &= 0b111111;
		for(int i = size-1;i>=n;--i){
			bit[i] = (bit[i-n]<<num)|(i!=n&&num!=0?bit[i-n-1] >>> (64-num):0L);
		}
		for(int i = 0;i<n;++i){
			bit[i] = 0L;
		}
		bit[size-1] &= MASK;
	}

	/**
	 * �����̒l�̕������E�Ƀr�b�g�V�t�g���܂��B
	 * �r�b�g���͕ω������A��ꂽ���̃r�b�g�͔j������܂��B
	 *
	 * @param num �r�b�g�V�t�g�����
	 */
	public void shiftRight(int num){
		if(num>=size<<6){
			Arrays.fill(bit,0L);
			return;
		}
		final int n = num>>6;
		num &= 0b111111;
		for(int i = 0;i<size-n;++i){
			bit[i] = (bit[i+n] >>> num)|(i+n+1!=size&&num!=0?bit[i+n+1]<<(64-num):0L);
		}
		for(int i = size-1;i>=size-n;--i){
			bit[i] = 0L;
		}
	}

	/**
	 * ����Bitset��\��long�^�z���Ԃ��܂��B
	 * ����long�^�z���Bitset�ƘA�����Ă��邽�߁A�ύX���������Ƃ��̋����͕ۏ؂���܂���B
	 *
	 * @return ����Bitset��\��long�^�z��
	 */
	public long[] longValues(){
		return bit;
	}

	/**
	 * ����Bitset��long�Ɖ��߂������̒l��Ԃ��܂��B
	 *
	 * @return ����Bitset��long�Ɖ��߂������̒l
	 */
	public long longValue(){
		return bit[0];
	}

	/**
	 * ������Bitset�Ƃ̘_���ς����܂��B
	 *
	 * @param b �_���ς����Bitset
	 */
	public void and(final Bitset b){
		final long[] bit2 = b.longValues();
		final int m = Math.min(bit2.length,size);
		for(int i = 0;i<m;++i){
			bit[i] &= bit2[i];
		}
		for(int i = m;i<size;++i){
			bit[i] = 0;
		}
		bit[size-1] &= MASK;
	}

	/**
	 * ������Bitset�Ƃ̘_���a�����܂��B
	 *
	 * @param b �_���a�����Bitset
	 */
	public void or(final Bitset b){
		final long[] bit2 = b.longValues();
		final int m = Math.min(bit2.length,size);
		for(int i = 0;i<m;++i){
			bit[i] |= bit2[i];
		}
		bit[size-1] &= MASK;
	}

	/**
	 * ������Bitset�Ƃ̔r���I�_���a�����܂��B
	 *
	 * @param b �r���I�_���a�����Bitset
	 */
	public void xor(final Bitset b){
		final long[] bit2 = b.longValues();
		final int m = Math.min(bit2.length,size);
		for(int i = 0;i<m;++i){
			bit[i] ^= bit2[i];
		}
		bit[size-1] &= MASK;
	}

	/**
	 * ����Bitset�Ɠ����́A�قȂ�Bitset�𐶐����܂��B
	 *
	 * @return ����Bitset�Ɠ����́A�قȂ�Bitset
	 *
	 * @throws CloneNotSupportedException �����N���Ȃ�
	 */
	public Bitset clone() throws CloneNotSupportedException{
		super.clone();
		return new Bitset(bit);
	}
}

/**
 * int������HashSet�N���X�ł��B
 * java.util.HashSet<Integer>���������ȏ�����񋟂��܂��B
 */
final class IntegerSet{
	/**
	 * ����Set�̊e�l��ێ�����Node�N���X�ł��B
	 */
	private class Node{
		private final int value;
		private Node next;

		private Node(final int val){
			value = val;
		}

		private boolean add(final int x){
			if(value==x){
				return false;
			}
			if(next!=null){
				return next.add(x);
			}
			++size;
			next = new Node(x);
			return true;
		}

		private void add(final Node n){
			if(value!=n.value){
				if(next!=null){
					next.add(n);
				}
				else{
					next = n;
				}
			}
		}

		private boolean remove(final int x){
			if(next==null){
				return false;
			}
			if(next.value!=x){
				return next.remove(x);
			}
			--size;
			next = next.next;
			return true;
		}

		private boolean contains(final int x){
			if(value==x){
				return true;
			}
			if(next!=null){
				return next.contains(x);
			}
			return false;
		}
	}

	private Node[] table;
	private int size;

	/**
	 * �����e��16��IntegerSet���\�z���܂��B
	 */
	public IntegerSet(){
		this(16);
	}

	/**
	 * �����e��s��IntegerSet���\�z���܂��B
	 *
	 * @param s �����e��
	 */
	public IntegerSet(final int s){
		if(s<1){
			throw new NegativeArraySizeException("hash table's size must be positive");
		}
		table = new Node[s];
		size = 0;
	}

	/**
	 * �����̒l��ǉ����܂��B
	 * �����̒l�����ɒǉ�����Ă���ꍇ�͕ύX����܂���B
	 *
	 * @param x �ǉ�����l
	 *
	 * @return �����̒l�����ɒǉ�����Ă���ꍇ��false�A����ȊO��true
	 */
	public boolean add(final int x){
		sizeCheck();
		final int index = hash(x);
		if(table[index]!=null){
			return table[index].add(x);
		}
		++size;
		table[index] = new Node(x);
		return true;
	}

	/**
	 * �����̒l��ǉ����܂��B
	 * �����̒l�����ɒǉ�����Ă���ꍇ�͕ύX����܂���B
	 *
	 * @param nums �ǉ�����l
	 */
	public void addAll(final int[] nums){
		for(int x:nums)
			add(x);
	}

	/**
	 * ���n�b�V���p�̍����ǉ����\�b�h�ł��B
	 * �ǉ��ς݂��͐������܂���B
	 *
	 * @param n �ǉ�����Node
	 */
	private void add(final Node n){
		final int index = hash(n.value);
		if(table[index]!=null){
			table[index].add(n);
		}
		else{
			table[index] = n;
		}
	}

	/**
	 * �����̒l���폜���܂��B
	 * ����IntegerSet�Ɉ����̒l�������ꍇ�͕ύX����܂���B
	 *
	 * @param x �폜����l
	 *
	 * @return �����̒l�����݂��Ă����Ȃ�true�A����ȊO��false
	 */
	public boolean remove(final int x){
		final int index = hash(x);
		if(table[index]==null){
			return false;
		}
		if(table[index].value!=x){
			return table[index].remove(x);
		}
		--size;
		table[index] = table[index].next;
		return true;
	}

	/**
	 * �����̒l�����݂��邩���肵�܂��B
	 *
	 * @param x ���݂��m�F����l
	 *
	 * @return �����̒l�����݂��邩��\��boolean
	 */
	public boolean contains(final int x){
		final int index = hash(x);
		if(table[index]==null){
			return false;
		}
		return table[index].contains(x);
	}

	/**
	 * ���n�b�V���p���\�b�h�ł��B
	 */
	private void reHash(){
		final Node[] oldTable = table;
		table = new Node[table.length<<1];
		for(Node node: oldTable){
			while(node!=null){
				final Node next = node.next;
				node.next = null;
				add(node);
				node = next;
			}
		}
	}

	/**
	 * ���n�b�V�����ׂ��Ȃ烊�n�b�V�����܂��B
	 */
	private void sizeCheck(){
		if(table.length*0.6<size){
			reHash();
		}
	}

	/**
	 * �ǉ����ׂ��C���f�b�N�X��Ԃ��܂��B
	 *
	 * @param val �ǉ��������l
	 *
	 * @return �ǉ����ׂ��C���f�b�N�X
	 */
	private int hash(final int val){
		final int h = val%table.length;
		return h<0?h+table.length:h;
	}

	/**
	 * ����IntegerSet�̗v�f����Ԃ��܂��B
	 *
	 * @return ����IntegerSet�̗v�f��
	 */
	public int size(){
		return size;
	}

	/**
	 * ����IntegerSet�ɑ��݂���v�f��z��ɂ��ĕԂ��܂��B
	 * �����͕ۏ؂���܂���B
	 *
	 * @return ����IntegerSet��\���z��
	 */
	public int[] toArray(){
		final int[] array = new int[size];
		int index = 0;
		for(Node node: table){
			while(node!=null){
				array[index++] = node.value;
				node = node.next;
			}
		}
		return array;
	}

	/**
	 * ����IntegerSet��\��String��Ԃ��܂��B
	 * ���̃��\�b�h��java.util.Arrays.toString(toArray())�Ɠ����ł��B
	 *
	 * @return ����IntegerSet��\��String
	 */
	public String toString(){
		return Arrays.toString(toArray());
	}
}

/**
 * long������HashSet�N���X�ł��B
 * java.util.HashSet<Long>���������ȏ�����񋟂��܂��B
 */
class LongSet {
	private interface LinkableNode {
		public boolean add(long x);

		public boolean remove(long x);

		public boolean contains(long x);
	}

	private abstract class Node implements LinkableNode {
		Node next, prev;
		protected long value;
		int height;
	}

	private class LinkedNode extends Node {
		LinkableNode parent;
		LinkedNode son;

		private LinkedNode(long val) {
			value = val;
			height = 1;
			if (head == null) {
				head = tail = this;
			} else {
				tail.next = this;
				prev = tail;
				tail = this;
			}
			size++;
		}

		public boolean add(long x) {
			LinkedNode par = null;
			LinkedNode now = this;
			while (now != null) {
				par = now;
				if (now.value == x) {
					return false;
				}
				now = now.son;
			}
			par.son = new LinkedNode(x);
			par.son.parent = par;
			int h = 1;
			LinkableNode prev = par;
			while (prev instanceof LinkedNode p) {
				p.height = ++h;
				prev = p.parent;
			}
			return true;
		}

		private void add(LinkedNode n) {
			LinkedNode now = this;
			while (now.son != null) {
				LinkedNode next = now.son;
				now = next;
			}
			now.son = n;
			n.parent = now;
			int h = 1;
			LinkableNode prev = now;
			while (prev instanceof LinkedNode p) {
				p.height = ++h;
				prev = p.parent;
			}
		}

		public boolean remove(long x) {
			LinkedNode now = this;
			while (now != null) {
				if (now.value == x) {
					delete(now);
					return true;
				}
				now = now.son;
			}
			return false;
		}

		public boolean contains(long x) {
			LinkedNode now = this;
			while (now != null) {
				if (now.value == x) {
					return true;
				}
				now = now.son;
			}
			return false;
		}

		private void delete(LinkedNode node) {
			LinkableNode par = node.parent;
			LinkedNode son = node.son;
			if (par instanceof DummyNode p) {
				p.next = son;
			} else if (par instanceof LinkedNode p) {
				p.son = son;
			} else {
				throw new AssertionError("Unexpected Error : Bad type of node ");
			}
			if (son != null) {
				son.parent = par;
			} 
			Node prev = node.prev;
			Node next = node.next;
			if (prev == null) {
				head = head.next;
			} else {
				prev.next = next;
			}
			if (next == null) {
				tail = tail.prev;
			} else {
				next.prev = prev;
			}
			setNull(node);
			--size;
		}

		private static void setNull(LinkedNode node) {
			node.parent = null;
			node.son = null;
			node.prev = null;
			node.next = null;
		}
	}

	private class TreeNode extends Node {
		LinkableNode parent;
		TreeNode left, right;

		private TreeNode(long val) {
			value = val;
			height = 1;
			if (head == null) {
				head = tail = this;
			} else {
				tail.next = this;
				prev = tail;
				tail = this;
			}
			size++;
		}

		public boolean add(long x) {
			TreeNode par = null;
			TreeNode now = this;
			while (now != null) {
				par = now;
				if (x < now.value) {
					now = now.left;
				} else if (x > now.value) {
					now = now.right;
				} else {
					return false;
				}
			}
			assert par != null;
			if (x < par.value) {
				par.left = new TreeNode(x);
				par.left.parent = par;
			} else {
				par.right = new TreeNode(x);
				par.right.parent = par;
			}
			fix(par);
			return true;
		}

		public void add(TreeNode n) {
			TreeNode par = null;
			TreeNode now = this;
			while (now != null) {
				par = now;
				if (n.value < now.value) {
					now = now.left;
				} else {
					now = now.right;
				}
			}
			assert par != null;
			if (n.value < par.value) {
				par.left = n;
				n.parent = par;
			} else {
				par.right = n;
				n.parent = par;
			}
			fix(par);
		}

		public boolean remove(long x) {
			TreeNode now = this;
			while (now != null) {
				if (now.value == x) {
					delete(now);
					return true;
				}
				if (x < now.value) {
					now = now.left;
				} else {
					now = now.right;
				}
			}
			return false;
		}

		public boolean contains(long x) {
			TreeNode now = this;
			while (now != null) {
				if (now.value == x) {
					return true;
				}
				if (x < now.value) {
					now = now.left;
				} else {
					now = now.right;
				}
			}
			return false;
		}

		private void delete(TreeNode node) {
			if (node.left == null) {
				if (node.parent instanceof DummyNode p) {
					p.next = node.right;
					if (node.right != null) {
						node.right.parent = p;
					}
				} else if (node.parent instanceof TreeNode p) {
					if (p.left == node)
						p.left = node.right;
					else
						p.right = node.right;
					if (node.right != null) {
						node.right.parent = p;
					}
					fix(p);
				} else {
					throw new AssertionError("Unexpected Error : Bad type of node");
				}
				if (node.next != null)
					node.next.prev = node.prev;
				else
					tail = node.prev;
				if (node.prev != null)
					node.prev.next = node.next;
				else
					head = node.next;
				setNull(node);
				size--;
			} else if (node.right == null) {
				if (node.parent instanceof DummyNode p) {
					p.next = node.left;
					if (node.left != null) {
						node.left.parent = p;
					}
				} else if (node.parent instanceof TreeNode p) {
					if (p.left == node)
						p.left = node.left;
					else
						p.right = node.left;
					if (node.left != null) {
						node.left.parent = p;
					}
					fix(p);
				} else {
					throw new AssertionError("Unexpected Error : Bad type of node");
				}
				if (node.next != null)
					node.next.prev = node.prev;
				else
					tail = node.prev;
				if (node.prev != null)
					node.prev.next = node.next;
				else
					head = node.next;
				setNull(node);
				size--;
			} else {
				long sub = deleteMax(node.left);
				node.value = sub;
			}
		}

		private long deleteMax(TreeNode node) {
			assert node != null;
			TreeNode ans = node;
			while (ans.right != null)
				ans = ans.right;
			long max = ans.value;
			delete(ans);
			return max;
		}

		private static void fix(LinkableNode node) {
			while (node instanceof TreeNode n) {
				final int lh = n.left == null ? 0 : n.left.height;
				final int rh = n.right == null ? 0 : n.right.height;
				if (lh - rh > 1) {
					assert n.left != null;
					if (n.left.right != null && n.left.right.height == lh - 1) {
						rotateL(n.left);
					}
					rotateR(n);
				} else if (rh - lh > 1) {
					assert n.right != null;
					if (n.right.left != null && n.right.left.height == rh - 1) {
						rotateR(n.right);
					}
					rotateL(n);
				} else {
					setStates(n);
				}
				node = n.parent;
			}
		}

		private static void rotateR(final TreeNode node) {
			final TreeNode temp = node.left;
			node.left = temp.right;
			if (node.left != null) {
				node.left.parent = node;
			}
			temp.right = node;
			temp.parent = node.parent;
			if (node.parent instanceof DummyNode p) {
				p.next = temp;
			} else if (node.parent instanceof TreeNode p) {
				if (p.left == node) {
					p.left = temp;
				} else {
					p.right = temp;
				}
			}
			node.parent = temp;
			setStates(node);
		}

		private static void rotateL(final TreeNode node) {
			final TreeNode temp = node.right;
			node.right = temp.left;
			if (node.right != null) {
				node.right.parent = node;
			}
			temp.left = node;
			temp.parent = node.parent;
			if (node.parent instanceof DummyNode p) {
				p.next = temp;
			} else if (node.parent instanceof TreeNode p) {
				if (p.left == node) {
					p.left = temp;
				} else {
					p.right = temp;
				}
			}
			node.parent = temp;
			setStates(node);
		}

		private static void setStates(final TreeNode node) {
			final int lh = node.left != null ? node.left.height : 0;
			final int rh = node.right != null ? node.right.height : 0;
			node.height = Math.max(lh, rh) + 1;
		}

		private static void setNull(TreeNode node) {
			node.left = null;
			node.right = null;
			node.parent = null;
			node.prev = null;
			node.next = null;
		}
	}

	private class DummyNode implements LinkableNode {
		private static final int LOWER_BOUND = 16;
		Node next;

		public boolean add(long x) {
			if (next == null) {
				LinkedNode son = new LinkedNode(x);
				next = son;
				son.parent = this;
				return true;
			} else if (next instanceof LinkedNode ln) {
				boolean ans = ln.add(x);
				if (LOWER_BOUND <= ln.height) {
					treefy();
				}
				return ans;
			} else if (next instanceof TreeNode tn) {
				return tn.add(x);
			} else {
				throw new AssertionError("Unexpected Error : Bad type of node");
			}
		}

		private void add(Node n) {
			if (next == null) {
				LinkedNode son;
				if (n instanceof LinkedNode ln) {
					son = ln;
				} else if (n instanceof TreeNode tn) {
					son = new LinkedNode(tn.value);
				} else {
					throw new AssertionError("Unexpected Error : Bad type of node");
				}
				next = son;
				son.parent = this;
			} else if (next instanceof LinkedNode ln) {
				LinkedNode son;
				if (n instanceof LinkedNode node) {
					son = node;
				} else if (n instanceof TreeNode tn) {
					son = new LinkedNode(tn.value);
				} else {
					throw new AssertionError("Unexpected Error : Bad type of node");
				}
				ln.add(son);
				if (LOWER_BOUND <= ln.height) {
					treefy();
				}
			} else if (next instanceof TreeNode tn) {
				TreeNode son;
				if (n instanceof TreeNode node) {
					son = node;
				} else if (n instanceof LinkedNode ln) {
					son = new TreeNode(ln.value);
				} else {
					throw new AssertionError("Unexpected Error : Bad type of node");
				}
				tn.add(son);
			} else {
				throw new AssertionError("Unexpected Error : Bad type of node");
			}
		}

		public boolean remove(long x) {
			if (next == null) {
				return false;
			}
			return next.remove(x);
		}

		public boolean contains(long x) {
			if (next == null) {
				return false;
			}
			return next.contains(x);
		}

		private void treefy() {
			assert next instanceof LinkedNode;
			LinkedNode node = (LinkedNode) next;
			TreeNode newNode = new TreeNode(node.value);
			newNode.parent = this;
			next = newNode;
			node = node.son;
			while (node != null) {
				next.add(node.value);
				node = node.son;
			}
		}
	}

	private DummyNode[] table;
	private int size;
	private Node head, tail;
	private static final int BIG_TREE = 15;

	public LongSet() {
		this(21);
	}

	public LongSet(final int s) {
		if (s < 1) {
			throw new NegativeArraySizeException("hash table's size must be positive");
		}
		table = new DummyNode[s];
		size = 0;
	}

	public boolean add(final long x) {
		sizeCheck();
		final int index = hash(x);
		if (table[index] == null) {
			table[index] = new DummyNode();
		}
		final boolean ans = table[index].add(x);
		treeCheck(index);
		return ans;
	}

	private void add(final Node n) {
		final int index = hash(n.value);
		if (table[index] == null) {
			table[index] = new DummyNode();
		}
		table[index].add(n);
	}

	public boolean remove(final long x) {
		final int index = hash(x);
		if (table[index] == null) {
			return false;
		}
		return table[index].remove(x);
	}

	public boolean contains(final long x) {
		final int index = hash(x);
		if (table[index] == null) {
			return false;
		}
		return table[index].contains(x);
	}

	private void reHash() {
		final DummyNode[] oldTable = table;
		int nextSize = table.length << 2 | 1;
		table = new DummyNode[nextSize];
		Node node = head;
		while (node != null) {
			if (node instanceof LinkedNode n) {
				n.son = null;
			} else if (node instanceof TreeNode n) {
				n.left = null;
				n.right = null;
			} else {
				throw new AssertionError("Unexpected Error : Bad type of node");
			}
			add(node);
			node = node.next;
		}
	}

	private void sizeCheck() {
		if (table.length * 0.65 < size) {
			reHash();
		}
	}

	private void treeCheck(int index) {
		if (BIG_TREE <= table[index].next.height) {
			reHash();
		}
	}

	private int hash(final long val) {
		final int h = (int)(val % table.length);
		return h < 0 ? h + table.length : h;
	}

	public int size() {
		return size;
	}

	public long[] toArray() {
		final long[] array = new long[size];
		int index = 0;
		Node node = head;
		while (node != null) {
			array[index++] = node.value;
			node = node.next;
		}
		return array;
	}

	public String toString() {
		return java.util.Arrays.toString(toArray());
	}
}

final class IntegerMap {

	private class Node {
		private int key, value;
		private Node next;

		private Node ( final int k, final int val ) {
			key = k;
			value = val;
		}

		private boolean put ( final int k, final int v ) {
			if ( key == k ) {
				value = v;
				return true;
			}
			if ( next != null ) {
				return next.put( k, v );
			}
			++size;
			next = new Node( k, v );
			return true;
		}

		private void putFast ( final Node n ) {
			if ( value != n.value ) {
				if ( next != null ) {
					next.putFast( n );
				}
				else {
					next = n;
				}
			}
		}

		private int merge ( final int k, final int v, IntBinaryOperator func ) {
			if ( key == k ) {
				value = func.applyAsInt(value,v);
				return value;
			}
			if ( next != null ) {
				return next.merge( k, v, func );
			}
			++size;
			next = new Node( k, v );
			return v;
		}

		private int mergeOrRemove ( final int k, final int v, final int removeValue, IntBinaryOperator func ) {
			if ( next == null ) {
				if(v!=removeValue){
					next = new Node( k, v );
				}
				return v;
			}
			if ( next.key != k ) {
				return next.mergeOrRemove( k, v, removeValue, func );
			}
			next.value = func.applyAsInt(next.value,v);
			if(next.value==removeValue){
				--size;
				next = next.next;
				return removeValue;
			}
			return next.value;
		}

		private boolean remove ( final int k ) {
			if ( next == null ) {
				return false;
			}
			if ( next.key != k ) {
				return next.remove( k );
			}
			--size;
			next = next.next;
			return true;
		}

		private boolean containsKey ( final int k ) {
			if ( key == k ) {
				return true;
			}
			if ( next != null ) {
				return next.containsKey( k );
			}
			return false;
		}

		private int getOrDefault ( final int k, final int def ) {
			if ( key == k ) {
				return value;
			}
			if ( next != null ) {
				return next.getOrDefault( k, def );
			}
			return def;
		}

		public String toString(){
			return key+"="+value;
		}
	}

	private Node[] table;
	private int size;

	public IntegerMap () {
		this( 16 );
	}

	public IntegerMap ( final int s ) {
		if ( s < 1 ) {
			throw new NegativeArraySizeException( "hash table's size must be positive" );
		}
		table = new Node[s];
		size = 0;
	}

	public boolean put ( final int k, final int v ) {
		sizeCheck();
		final int index = hash( k );
		if ( table[index] != null ) {
			return table[index].put( k, v );
		}
		++size;
		table[index] = new Node( k, v );
		return true;
	}

	private void put ( final Node n ) {
		final int index = hash( n.key );
		if ( table[index] != null ) {
			table[index].putFast( n );
		}
		else {
			table[index] = n;
		}
	}

	public boolean remove ( final int k ) {
		final int index = hash( k );
		if ( table[index] == null ) {
			return false;
		}
		if ( table[index].key != k ) {
			return table[index].remove( k );
		}
		--size;
		table[index] = table[index].next;
		return true;
	}

	public int mergeOrRemove ( final int k, final int v, final int removeValue, final IntBinaryOperator func ) {
		final int index = hash( k );
		if ( table[index] == null ) {
			if ( v != removeValue ) {
				++size;
				table[index] = new Node( k, v );
			}
			return v;
		}
		else if ( table[index].key != k ) {
			return table[index].mergeOrRemove( k, v, removeValue, func );
		}
		table[index].value = func.applyAsInt( table[index].value, v );
		if(table[index].value==removeValue){
			--size;
			table[index] = table[index].next;
			return removeValue;
		}
		return table[index].value;
	}

	public int merge ( final int k, final int v, final IntBinaryOperator func ) {
		final int index = hash( k );
		if ( table[index] == null ) {
			++size;
			table[index] = new Node( k, v );
			return v;
		}
		else if ( table[index].key != k ) {
			return table[index].merge( k, v, func );
		}
		table[index].value = func.applyAsInt( table[index].value, v );
		return table[index].value;
	}

	public boolean containsKey ( final int k ) {
		final int index = hash( k );
		if ( table[index] == null ) {
			return false;
		}
		return table[index].containsKey( k );
	}

	public int getOrDefault ( final int k, final int def ) {
		final int index = hash( k );
		if ( table[index] == null ) {
			return def;
		}
		return table[index].getOrDefault( k, def );
	}

	private void reHash () {
		final Node[] oldTable = table;
		table = new Node[table.length << 1];
		for ( Node node: oldTable ) {
			while ( node != null ) {
				final Node next = node.next;
				node.next = null;
				put( node );
				node = next;
			}
		}
	}

	private void sizeCheck () {
		if ( table.length * 0.6 < size ) {
			reHash();
		}
	}

	private int hash ( final int key ) {
		final int h = key % table.length;
		return h < 0 ? h + table.length : h;
	}

	public int size () {
		return size;
	}

	public String toString () {
		final Node[] array = new Node[size];
		int index = 0;
		for ( Node node: table ) {
			while ( node != null ) {
				array[index++] = node;
				node = node.next;
			}
		}
		return Arrays.toString(array);
	}
}

/**
 * Matrix�N���X�ł��B
 * �s���ێ�����N���X�ŁA�ȒP�Ȏl�����Z�ɍs�񎮁A�ݏ�Ȃǂ�񋟂��Ă��܂��B
 */
final class Matrix{
	private final long[][] matrix;

	/**
	 * H�sW��́A�S�v�f��def�̍s����\�z���܂��B
	 *
	 * @param H �s��
	 * @param W ��
	 * @param def �S�v�f�̒l
	 */
	public Matrix(final int H,final int W,final long def){
		matrix = new long[H][W];
		if(def!=0){
			for(final long[] mat: matrix){
				Arrays.fill(mat,def);
			}
		}
	}

	/**
	 * H�sW��́A�S�v�f��0�ł���s����\�z���܂��B
	 *
	 * @param H �s��
	 * @param W ��
	 */
	public Matrix(final int H,final int W){
		this(H,W,0);
	}

	/**
	 * Dimension�ŕ\�����s���A�񐔂őS�v�f��def�̍s����\�z���܂��B
	 *
	 * @param d �s�E��
	 * @param def �S�v�f�̒l
	 */
	public Matrix(final Dimension d,final long def){
		this(d.height,d.width,def);
	}

	/**
	 * �����̓񎟌��z������ɍs����\�z���܂��B
	 * �l���R�s�[���č\�z���邽�߁A�����̍s��Ƃ͘A�����Ă��܂���B
	 *
	 * @param mat ���ƂȂ�񎟌��z��
	 */
	public Matrix(final long[][] mat){
		matrix = new long[mat.length][];
		for(int i = 0;i<mat.length;++i){
			matrix[i] = Arrays.copyOf(mat[i],mat[i].length);
		}
	}

	/**
	 * i�sj��ڂ̗v�f���擾���܂��B
	 *
	 * @param i �s�ԍ�
	 * @param j ��ԍ�
	 *
	 * @return i�sj��ڂ̗v�f
	 */
	public long get(final int i,final int j){
		return matrix[i][j];
	}

	/**
	 * i�sj��ڂ̗v�f�������̒l�ŏ㏑�����܂��B
	 *
	 * @param i �s�ԍ�
	 * @param j ��ԍ�
	 * @param value �㏑������l
	 *
	 * @return value
	 */
	public long set(final int i,final int j,final long value){
		return matrix[i][j] = value;
	}

	/**
	 * ���̍s��𕡐����܂��B
	 *
	 * @return ���̍s��Ɠ����ȍs��(�A�����Ă��Ȃ�)
	 */
	public Matrix copy(){
		return new Matrix(matrix);
	}

	/**
	 * ���̍s��̍s�E�񐔂�\��Dimension��Ԃ��܂��B
	 *
	 * @return ���̍s��̍s�E�񐔂�\��Dimension
	 */
	public Dimension size(){
		return new Dimension(matrix[0].length,matrix.length);
	}

	/**
	 * ���̍s��Ɉ����̍s������Z�����s���Ԃ��܂��B
	 *
	 * @param m ���Z����s��
	 *
	 * @return ���̍s��Ɉ����̍s������Z�����s��Ɠ����ȍs��
	 */
	public Matrix add(final Matrix m){
		if(!size().equals(m.size())){
			throw new IllegalArgumentException("matrix size is not same");
		}
		final Matrix ans = new Matrix(size(),0);
		for(int i = 0;i<matrix.length;++i){
			for(int j = 0;j<matrix[i].length;++j){
				ans.set(i,j,matrix[i][j]+m.get(i,j));
			}
		}
		return ans;
	}

	/**
	 * ���̍s��Ɉ����̍s������Z�����s���Ԃ��܂��B
	 *
	 * @param m ���Z����s��
	 *
	 * @return ���̍s��Ɉ����̍s������Z�����s��Ɠ����ȍs��
	 */
	public Matrix subtract(final Matrix m){
		if(!size().equals(m.size())){
			throw new IllegalArgumentException("matrix size is not same");
		}
		final Matrix ans = new Matrix(size(),0);
		for(int i = 0;i<matrix.length;++i){
			for(int j = 0;j<matrix[i].length;++j){
				ans.set(i,j,matrix[i][j]-m.get(i,j));
			}
		}
		return ans;
	}

	/**
	 * ���̍s��Ɉ����̍s�����Z�����s���Ԃ��܂��B
	 *
	 * @param m ��Z����s��
	 *
	 * @return ���̍s��Ɉ����̍s�����Z�����s��Ɠ����ȍs��
	 */
	public Matrix multiply(final Matrix m){
		if(size().width!=m.size().height){
			throw new IllegalArgumentException("matrix length is not same");
		}
		final Matrix ans = new Matrix(size().height,m.size().width);
		final Dimension size = ans.size();
		final int len = size().width;
		for(int i = 0;i<size.height;++i){
			for(int j = 0;j<size.width;++j){
				long sum = 0;
				for(int k = 0;k<len;++k){
					sum += matrix[i][k]*m.get(k,j);
				}
				ans.set(i,j,sum);
			}
		}
		return ans;
	}

	/**
	 * ���̍s��Ɉ����̍s������Z�����s���Ԃ��܂��B
	 * �e�v�f��mod�ŏ��Z�������܂�ɕϊ�����܂��B
	 *
	 * @param m ���Z����s��
	 * @param mod �@�ƂȂ�l
	 *
	 * @return ���̍s��Ɉ����̍s������Z�����s��Ɠ����ȍs��
	 */
	public Matrix modAdd(final Matrix m,final long mod){
		if(!size().equals(m.size())){
			throw new IllegalArgumentException("matrix size is not same");
		}
		final Matrix ans = new Matrix(size(),0);
		for(int i = 0;i<matrix.length;++i){
			for(int j = 0;j<matrix[i].length;++j){
				ans.set(i,j,MathFunction.remainder(matrix[i][j]+m.get(i,j),mod));
			}
		}
		return ans;
	}

	/**
	 * ���̍s��Ɉ����̍s������Z�����s���Ԃ��܂��B
	 * �e�v�f��mod�ŏ��Z�������܂�ɕϊ�����܂��B
	 *
	 * @param m ���Z����s��
	 * @param mod �@�ƂȂ�l
	 *
	 * @return ���̍s��Ɉ����̍s������Z�����s��Ɠ����ȍs��
	 */
	public Matrix modSubtract(final Matrix m,final long mod){
		if(!size().equals(m.size())){
			throw new IllegalArgumentException("matrix size is not same");
		}
		final Matrix ans = new Matrix(size(),0);
		for(int i = 0;i<matrix.length;++i){
			for(int j = 0;j<matrix[i].length;++j){
				ans.set(i,j,MathFunction.remainder(matrix[i][j]-m.get(i,j),mod));
			}
		}
		return ans;
	}

	/**
	 * ���̍s��Ɉ����̍s�����Z�����s���Ԃ��܂��B
	 * �e�v�f��mod�ŏ�Z�������܂�ɕϊ�����܂��B
	 *
	 * @param m ��Z����s��
	 * @param mod �@�ƂȂ�l
	 *
	 * @return ���̍s��Ɉ����̍s�����Z�����s��Ɠ����ȍs��
	 */
	public Matrix modMultiply(final Matrix m,final long mod){
		if(size().width!=m.size().height){
			throw new IllegalArgumentException("matrix length is not same");
		}
		final Matrix ans = new Matrix(size().height,m.size().width);
		final Dimension size = ans.size();
		final int len = size().width;
		for(int i = 0;i<size.height;++i){
			for(int j = 0;j<size.width;++j){
				long sum = 0;
				for(int k = 0;k<len;++k){
					sum = MathFunction.remainder(sum+matrix[i][k]*m.get(k,j),mod);
				}
				ans.set(i,j,sum);
			}
		}
		return ans;
	}

	/**
	 * ���ƂȂ�s��Ɉ����̍s����w�肳�ꂽ�񐔂�����Z���܂��B
	 *
	 * @param original ���ƂȂ�s��
	 * @param pw ��Z����z��
	 * @param exp ��Z�����
	 *
	 * @return ���ƂȂ�s��Ɉ����̍s����w�肳�ꂽ�񐔂�����Z�����s��Ɠ����ȍs��
	 */
	public static Matrix pow(final Matrix original,final Matrix pw,long exp){
		Matrix a = original.copy();
		Matrix b = pw.copy();
		while(0<exp){
			if((exp&1)==1){
				a = b.multiply(a);
			}
			b = b.multiply(b);
			exp >>= 1;
		}
		return a;
	}

	/**
	 * ���ƂȂ�s��Ɉ����̍s����w�肳�ꂽ�񐔂�����Z���܂��B
	 * �e�v�f��mod�ŏ�Z�������܂�ɕϊ�����܂��B
	 *
	 * @param original ���ƂȂ�s��
	 * @param pw ��Z����z��
	 * @param exp ��Z�����
	 * @param mod �@�ƂȂ�l
	 *
	 * @return ���ƂȂ�s��Ɉ����̍s����w�肳�ꂽ�񐔂�����Z�����s��Ɠ����ȍs��
	 */
	public static Matrix modPow(final Matrix original,final Matrix pw,long exp,final long mod){
		Matrix a = original.copy();
		Matrix b = pw.copy();
		while(0<exp){
			if((exp&1)==1){
				a = b.modMultiply(a,mod);
			}
			b = b.modMultiply(b,mod);
			exp >>= 1;
		}
		return a;
	}

	/**
	 * ���̍s��̍s�񎮂�Ԃ��܂��B
	 *
	 * @return ���̍s��̍s��
	 */
	public long determinant(){
		return determinant(matrix);
	}

	/**
	 * �����̓񎟌��z��̍s�񎮂�Ԃ��܂��B
	 *
	 * @param mat ���ƂȂ�񎟌��z��
	 *
	 * @return �񎟌��z��̍s��
	 */
	private static long determinant(final long[][] mat){
		if(mat.length==1){
			return mat[0][0];
		}
		final long[][] miniMat = new long[mat.length-1][mat.length-1];
		for(int i = 1;i<mat.length;++i){
			System.arraycopy(mat[i],1,miniMat[i-1],0,miniMat.length);
		}
		long ans = mat[0][0]*determinant(miniMat);
		for(int i = 1;i<mat.length;++i){
			for(int j = 1;j<mat.length;++j){
				miniMat[j-1][i-1] = mat[j][i-1];
			}
			final long num = mat[0][i]*determinant(miniMat);
			ans += i%2==0?num:-num;
		}
		return ans;
	}

	/**
	 * ���̍s���\��String��Ԃ��܂��B
	 *
	 * @return ���̍s���\��String
	 */
	@Override public String toString(){
		final StringBuilder ans = new StringBuilder();
		ans.append(Arrays.toString(matrix[0]));
		for(int i = 1;i<matrix.length;++i){
			ans.append("\n");
			ans.append(Arrays.toString(matrix[i]));
		}
		return ans.toString();
	}
}

/**
 * RollingHash�N���X�ł��B
 * �ꉞ������A�����\�b�h�Ȃǂ��������Ă��܂����A�g�p�͐�������܂���B
 */
final class RollingHash implements Comparable<RollingHash>{
	private static final long BASE = new Random().nextInt(1000)+Character.MAX_VALUE+1;
	private static final long MASK30 = (1L<<30)-1;
	private static final long MASK31 = (1L<<31)-1;
	private static final long MOD = (1L<<61)-1;
	private static final long MASK61 = MOD;
	private long[] hash;
	private String string;

	/**
	 * �����̕����������RollingHash���\�z���܂��B
	 *
	 * @param str ���ƂȂ�String
	 */
	public RollingHash(final String str){
		string = str;
		hash = new long[str.length()+1];
		roll();
	}

	/**
	 * �t�B�[���h��string�����Ƀn�b�V���l���v�Z���܂��B
	 */
	private void roll(){
		final int len = string.length();
		for(int i = 1;i<=len;++i){
			hash[i] = multiply(hash[i-1],BASE)+string.charAt(i-1)-' '+1;
			if(MOD<=hash[i]){
				hash[i] -= MOD;
			}
		}
	}

	/**
	 * long*long�p�̓��ʃ��\�b�h�ł��B
	 *
	 * @param a ��Z����l
	 * @param b ��Z�����l
	 *
	 * @return a*b mod 2^61-1
	 */
	private static long multiply(final long a,final long b){
		final long au = a>>31;
		final long ad = a&MASK31;
		final long bu = b>>31;
		final long bd = b&MASK31;
		final long mid = ad*bu+au*bd;
		final long midu = mid>>30;
		final long midd = mid&MASK30;
		return mod(au*bu*2+midu+(midd<<31)+ad*bd);
	}

	/**
	 * x mod 2^61-1���v�Z���܂��B
	 *
	 * @param x ���ƂȂ�l
	 *
	 * @return x mod 2^61-1
	 */
	private static long mod(final long x){
		final long xu = x>>61;
		final long xd = x&MASK61;
		long ans = xu+xd;
		if(MOD<=ans){
			ans -= MOD;
		}
		return ans;
	}

	/**
	 * �n�b�V���l���v�Z���܂��B
	 *
	 * @param l ���[
	 * @param r �E�[
	 *
	 * @return [l,r]�̃n�b�V���l
	 */
	public long getHash(final int l,final int r){
		return (hash[r]-multiply(hash[l],modBasePow(r-l))+MOD)%MOD;
	}

	/**
	 * BASE^b mod 2^61-1���v�Z���܂��B
	 *
	 * @param b �w��
	 *
	 * @return BASE^b mod 2^61-1
	 */
	private static long modBasePow(long b){
		long ans = 1;
		long a = BASE;
		while(b>0){
			if((b&1)==1){
				ans = multiply(ans,a);
			}
			a = multiply(a,a);
			b >>= 1;
		}
		return ans;
	}

	/**
	 * �w�肳�ꂽ��Ԃƈ����̎w�肳�ꂽ��Ԃ����������Ԃ��܂��B
	 *
	 * @param rh ��r�Ώۂ�RollingHash
	 * @param l1 ����RollingHash�̍��[
	 * @param r1 ����RollingHash�̉E�[
	 * @param l2 ��r�Ώۂ̍��[
	 * @param r2 ��r�Ώۂ̉E�[
	 *
	 * @return �������\��������Ȃ�true�A�m���ɈقȂ�Ȃ�false
	 */
	public boolean equals(final RollingHash rh,final int l1,final int r1,final int l2,final int r2){
		if(r1-l1!=r2-l2){
			return false;
		}
		return getHash(l1,r1)==rh.getHash(l2,r2);
	}

	/**
	 * ����RollingHash�̒�����Ԃ��܂��B
	 *
	 * @return ����RollingHash�̒���
	 */
	public int length(){
		return string.length();
	}

	/**
	 * ����RollingHash�̌��ƂȂ�String�̃n�b�V���l��Ԃ��܂��B
	 *
	 * @return ����RollingHash�̌��ƂȂ�String�̃n�b�V���l
	 */
	@Override public int hashCode(){
		return string.hashCode();
	}

	/**
	 * ����RollingHash�̌��ƂȂ�String��Ԃ��܂��B
	 *
	 * @return ����RollingHash�̌��ƂȂ�String
	 */
	@Override public String toString(){
		return string;
	}

	/**
	 * �����Ǝ��g�����������Ԃ��܂��B
	 *
	 * @param o ��r�Ώ�
	 *
	 * @return �������\��������Ȃ�true�A�m���ɈقȂ�Ȃ�false
	 */
	@Override public boolean equals(Object o){
		if(o instanceof final RollingHash rh){
			return equals(rh,0,length(),0,rh.length());
		}
		return false;
	}

	/**
	 * ������RollingHash�̌��ƂȂ�String�Ǝ������Ŕ�r���܂��B
	 *
	 * @param rh the object to be compared.
	 *
	 * @return �������Ŏ��g���������Ȃ�-1�A�������Ȃ�0�A����ȊO��1
	 */
	@Override public int compareTo(RollingHash rh){
		return string.compareTo(rh.toString());
	}

	/**
	 * ������String�Ǝ������Ŕ�r���܂��B
	 *
	 * @param str the object to be compared.
	 *
	 * @return �������Ŏ��g���������Ȃ�-1�A�������Ȃ�0�A����ȊO��1
	 */
	public int compareTo(String str){
		return string.compareTo(str);
	}

	/**
	 * ����RollingHash��i�����ڂ�Ԃ��܂��B
	 *
	 * @param i �C���f�b�N�X
	 *
	 * @return i�Ԗڂ̕�����\��char
	 */
	public char charAt(final int i){
		return string.charAt(i);
	}

	/**
	 * ������RollingHash�̌��ƂȂ�String�Ƒ啶���������𖳎����Ď������Ŕ�r���܂��B
	 *
	 * @param rh the object to be compared.
	 *
	 * @return �������Ŏ��g���������Ȃ�-1�A�������Ȃ�0�A����ȊO��1
	 */
	public int compareToIgnoreCase(final RollingHash rh){
		return string.compareToIgnoreCase(rh.toString());
	}

	/**
	 * ������String�Ƒ啶���������𖳎����Ď������Ŕ�r���܂��B
	 *
	 * @param str the object to be compared.
	 *
	 * @return �������Ŏ��g���������Ȃ�-1�A�������Ȃ�0�A����ȊO��1
	 */
	public int compareToIgnoreCase(final String str){
		return string.compareToIgnoreCase(str);
	}

	/**
	 * ������RollingHash�𖖔��ɘA�����܂��B
	 * �v�Z�ʂ�O(length+rh.length)�ł��B
	 *
	 * @param rh �A�����镶�����\��RollingHash
	 */
	public void concat(final RollingHash rh){
		concat(rh.toString());
	}

	/**
	 * ������String�𖖔��ɘA�����܂��B
	 * �v�Z�ʂ�O(length+rh.length)�ł��B
	 *
	 * @param str �A�����镶����
	 */
	public void concat(final String str){
		string = string.concat(str);
		hash = new long[string.length()+1];
		roll();
	}

	/**
	 * ����RollingHash�̘A��������Ƃ��Ĉ�����RollingHash�����݂��邩���肵�܂��B
	 *
	 * @param rh �A��������Ƃ��đ��݂��邩��������RollingHash
	 *
	 * @return ������Ƃ��đ��݂���\��������Ȃ�true�A�m���Ɋ܂܂�Ă��Ȃ��Ȃ�false
	 */
	public boolean contains(final RollingHash rh){
		final long hash = rh.getHash(0,rh.length());
		final int len = length()-rh.length();
		for(int i = 0;i<=len;++i){
			if(hash==getHash(i,rh.length()+i)){
				return true;
			}
		}
		return false;
	}

	/**
	 * ������String���A��������Ƃ��đ��݂��邩���肵�܂��B
	 *
	 * @param str ���ݔ��������String
	 *
	 * @return �A��������Ƃ��đ��݂���Ȃ�true�A����ȊO��false
	 */
	public boolean contains(final String str){
		return indexOf(str)!=-1;
	}

	/**
	 * �����̕������擪���牽�Ԗڂɑ��݂��邩�Ԃ��܂��B
	 * ���݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param ch �T�����镶����\��int
	 *
	 * @return �ł������ɂ�������̕����̃C���f�b�N�X(������� - 1)
	 */
	public int indexOf(final int ch){
		return indexOf(ch,0);
	}

	/**
	 * �����̕������w�肳�ꂽ�C���f�b�N�X�ȍ~�ŒT�����܂��B
	 * ���݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param ch �T�����镶����\��int
	 * @param fromIndex �T���J�n�ʒu
	 *
	 * @return �ł������ɂ�������̕����̃C���f�b�N�X(������� - 1)
	 */
	public int indexOf(final int ch,final int fromIndex){
		final int len = length();
		for(int i = fromIndex;i<len;++i){
			if(string.charAt(i)==ch){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �����̕����񂪐擪���牽�Ԗڂɑ��݂��邩�Ԃ��܂��B
	 * ���݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param str �T�����镶����
	 *
	 * @return �ł������ɂ�������̕�����̃C���f�b�N�X(������� - 1)
	 */
	public int indexOf(final String str){
		return indexOf(str,0);
	}

	/**
	 * �����̕�������w�肳�ꂽ�C���f�b�N�X�ȍ~�ŒT�����܂��B
	 * ���݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param str �T�����镶����
	 * @param fromIndex �T���J�n�ʒu
	 *
	 * @return �ł������ɂ�������̕�����̃C���f�b�N�X(������� - 1)
	 */
	public int indexOf(final String str,final int fromIndex){
		long hash = 0;
		for(final char c: str.toCharArray()){
			hash = multiply(hash,BASE)+c-' '+1;
			if(MOD<=hash){
				hash -= MOD;
			}
		}
		final int len = length()-str.length();
		for(int i = fromIndex;i<=len;++i){
			if(hash==getHash(i,str.length()+i)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * ����RollingHash���\�������񂪋󕶎��񂩔��肵�܂��B
	 *
	 * @return ""�Ɠ������Ȃ�true�A����ȊO��false
	 */
	public boolean isEmpty(){
		return length()==0;
	}

	/**
	 * �����̕������w�肳�ꂽ�C���f�b�N�X�ȍ~�ŒT�����܂��B
	 * ���݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param ch �T�����镶����\��int
	 * @param fromIndex �T���J�n�ʒu
	 *
	 * @return �ł��E���ɂ�������̕����̃C���f�b�N�X(������� - 1)
	 */
	public int lastIndexOf(final int ch,final int fromIndex){
		for(int i = fromIndex;i>=0;--i){
			if(string.charAt(i)==ch){
				return i;
			}
		}
		return -1;
	}

	/**
	 * �����̕������擪���牽�Ԗڂɑ��݂��邩�Ԃ��܂��B
	 * ���݂��Ȃ��ꍇ��-1��Ԃ��܂��B
	 *
	 * @param ch �T�����镶����\��int
	 *
	 * @return �ł������ɂ�������̕����̃C���f�b�N�X(������� - 1)
	 */
	public int lastIndexOf(final int ch){
		return lastIndexOf(ch,length()-1);
	}

	/**
	 * ������boolean��\�������������RollingHash���\�z���܂��B
	 *
	 * @param b ���ƂȂ�boolean
	 *
	 * @return ������boolean��\��RollingHash
	 */
	public static RollingHash valueOf(final boolean b){
		return new RollingHash(b?"true":"false");
	}

	/**
	 * ������char������RollingHash���\�z���܂��B
	 *
	 * @param c ���ƂȂ�char
	 *
	 * @return ������char��\��RollingHash
	 */
	public static RollingHash valueOf(final char c){
		return new RollingHash(""+c);
	}

	/**
	 * ������char[]��\�������������RollingHash���\�z���܂��B
	 *
	 * @param c ���ƂȂ�char[]
	 *
	 * @return ������char[]��\��RollingHash
	 */
	public static RollingHash valueOf(final char[] c){
		return new RollingHash(String.valueOf(c,0,c.length));
	}

	/**
	 * ������char[]��\��������́A�����̋�Ԃ�����RollingHash���\�z���܂��B
	 *
	 * @param c ���ƂȂ�char[]
	 * @param offset ���[
	 * @param count ������
	 *
	 * @return ������char[]�̈����̋�Ԃ�\��RollingHash
	 */
	public static RollingHash valueOf(final char[] c,final int offset,final int count){
		return new RollingHash(String.valueOf(c,offset,count));
	}

	/**
	 * ������double��\�������������RollingHash���\�z���܂��B
	 *
	 * @param d ���ƂȂ�double
	 *
	 * @return ������double��\��RollingHash
	 */
	public static RollingHash valueOf(final double d){
		return new RollingHash(String.valueOf(d));
	}

	/**
	 * ������float��\�������������RollingHash���\�z���܂��B
	 *
	 * @param f ���ƂȂ�float
	 *
	 * @return ������float��\��RollingHash
	 */
	public static RollingHash valueOf(final float f){
		return new RollingHash(String.valueOf(f));
	}

	/**
	 * ������int��\�������������RollingHash���\�z���܂��B
	 *
	 * @param i ���ƂȂ�int
	 *
	 * @return ������int��\��RollingHash
	 */
	public static RollingHash valueOf(final int i){
		return new RollingHash(String.valueOf(i));
	}

	/**
	 * ������long��\�������������RollingHash���\�z���܂��B
	 *
	 * @param l ���ƂȂ�long
	 *
	 * @return ������long��\��RollingHash
	 */
	public static RollingHash valueOf(final long l){
		return new RollingHash(String.valueOf(l));
	}

	/**
	 * ������Object��\�������������RollingHash���\�z���܂��B
	 *
	 * @param obj ���ƂȂ�Object
	 *
	 * @return ������Object��\��RollingHash
	 */
	public static RollingHash valueOf(final Object obj){
		return new RollingHash(String.valueOf(obj));
	}
}

/**
 * SegmentTree�N���X�ł��B
 * �ڂ��鉉�Z��function���`���Ďg�p���ĉ������B
 *
 * @param <E> ���Z��K�p����v�f�̌^
 */
@SuppressWarnings("unchecked") abstract class SegmentTree<E>{
	final int N, size;
	final E def;
	final Object[] node;

	/**
	 * �傫��n�́A�P�ʌ���def�ł���SegmentTree���\�z���܂��B
	 *
	 * @param n �����͈�
	 * @param def �P�ʌ�
	 * @param is1indexed �e���\�b�h�ɓn���C���f�b�N�X��1-indexed��
	 */
	public SegmentTree(final int n,final E def,final boolean is1indexed){
		int num = 2;
		while(num<n<<1){
			num <<= 1;
		}
		N = num;
		size = num>>1-(is1indexed?1:0);
		node = new Object[N];
		this.def = def;
		Arrays.fill(node,this.def);
	}

	/**
	 * �����̔z������ɁA�P�ʌ���def�ł���SegmentTree���\�z���܂��B
	 * �󂢃R�s�[�Ȃ̂ŁA�����̔z��̗v�f�ɕύX���������Ƃ��̓���͕ۏ؂���܂���B
	 *
	 * @param arr �@���ƂȂ�z��
	 * @param def �P�ʌ�
	 * @param is1indexed �e���\�b�h�ɓn���C���f�b�N�X��1-indexed��
	 */
	public SegmentTree(final E[] arr,final E def,final boolean is1indexed){
		int num = 2;
		while(num<arr.length<<1){
			num <<= 1;
		}
		N = num;
		size = num>>1-(is1indexed?1:0);
		node = new Object[N];
		this.def = def;
		System.arraycopy(arr,0,node,N>>1,arr.length);
		for(int i = arr.length+(N>>1);i<N;i++){
			node[i] = def;
		}
		updateAll();
	}

	/**
	 * �傫��n�A�P�ʌ���def��SegmentTree���\�z���܂��B
	 * �e���\�b�h�ɓn���C���f�b�N�X��0-indexed�ł��邱�Ƃ��v������܂��B
	 *
	 * @param n �傫��
	 * @param def �P�ʌ�
	 */
	public SegmentTree(final int n,final E def){
		this(n,def,false);
	}

	/**
	 * ����SegmentTree�̃e�[�u�����ꊇ�ōX�V���܂��B
	 */
	private void updateAll(){
		for(int i = (N>>1)-1;i>0;i--){
			node[i] = function((E)node[i<<1],(E)node[(i<<1)+1]);
		}
	}

	/**
	 * �����̈ʒu�̒l�������̒l�ŏ㏑�����܂��B
	 *
	 * @param n �C���f�b�N�X
	 * @param value �㏑������l
	 */
	public void update(int n,final E value){
		n += size;
		node[n] = value;
		n >>= 1;
		while(n>0){
			node[n] = function((E)node[n<<1],(E)node[(n<<1)+1]);
			n >>= 1;
		}
	}

	/**
	 * �����̈ʒu�̗v�f��Ԃ��܂��B
	 *
	 * @param a �C���f�b�N�X
	 *
	 * @return �����̈ʒu�̗v�f
	 */
	public E get(final int a){
		return (E)node[a+size];
	}

	/**
	 * ����SegmentTree��̗v�f�S�Ă�function��K�p�������ʂ�Ԃ��܂��B
	 *
	 * @return �v�f�S�Ă�function��K�p��������
	 */
	public E answer(){
		return (E)node[1];
	}

	/**
	 * �����̋�Ԃ̗v�f��function��K�p�������ʂ�Ԃ��܂��B
	 * ��Ԃł��邱�Ƃɒ��ӂ��ĉ������B
	 *
	 * @param l ���[(l���܂�)
	 * @param r �E�[(r���܂�)
	 *
	 * @return [l,r]�̗v�f��function��K�p��������
	 */
	public E query(int l,int r){
		l += size;
		r += size;
		E answer = def;
		while(l>0&&r>0&&l<=r){
			if(l%2==1){
				answer = function((E)node[l++],answer);
			}
			l >>= 1;
			if(r%2==0){
				answer = function(answer,(E)node[r--]);
			}
			r >>= 1;
		}
		return answer;
	}

	/**
	 * ����SegmentTree��œK�p����֐����`�������\�b�h�ł��B
	 *
	 * @param a �v�f1
	 * @param b �v�f2
	 *
	 * @return a��b�Ɋ֐���K�p��������
	 */
	abstract public E function(E a,E b);
}

/**
 * LazySegmentTree�N���X�ł��B
 * �ڂ��鉉�Z��function�A���Z�̍�����composition�A���Z�̓K�p��mapping�ɒ�`���Ďg�p���ĉ������B
 *
 * @param <S> ���Z��K�p����v�f�̌^
 * @param <F> ���Z�Ɏg�p����p�����[�^
 */
@SuppressWarnings("unchecked") abstract class LazySegmentTree<S,F>{
	private final int size, log;
	private final S defaultS;
	private final F defaultF;
	private final S[] node;
	private final F[] lazy;

	/**
	 * �傫��N�A�P�ʌ���defaultS�A�P���ʑ���defaultF�ł���LazySegmentTree���\�z���܂��B
	 * �e���\�b�h�ɓn���C���f�b�N�X��0-indexed�ł��邱�Ƃ��v������܂��B
	 *
	 * @param N �傫��
	 * @param defaultS �P�ʌ�
	 * @param defaultF �P���ʑ�
	 */
	public LazySegmentTree(final int N,final S defaultS,final F defaultF){
		this(N,defaultS,defaultF,false);
	}

	/**
	 * �傫��N�A�P�ʌ���defaultS�A�P���ʑ���defaultF�ł���LazySegmentTree���\�z���܂��B
	 *
	 * @param N �傫��
	 * @param defaultS �P�ʌ�
	 * @param defaultF �P���ʑ�
	 * @param is1indexed �e���\�b�h�ɓn���C���f�b�N�X��1-indexed��
	 */
	public LazySegmentTree(final int N,final S defaultS,final F defaultF,final boolean is1indexed){
		this.log = 32-Integer.numberOfLeadingZeros(N-1);
		this.size = (1<<log)-(is1indexed?1:0);
		this.defaultS = defaultS;
		this.defaultF = defaultF;
		this.node = (S[])new Object[1<<log+1];
		this.lazy = (F[])new Object[1<<log+1];
		clear();
	}

	/**
	 * �����̔z������ɁA�P�ʌ���defaultS�A�P���ʑ���defaultF�ł���LazySegmentTree���\�z���܂��B
	 *
	 * @param defaultValues �傫��
	 * @param defaultS �P�ʌ�
	 * @param defaultF �P���ʑ�
	 * @param is1indexed �e���\�b�h�ɓn���C���f�b�N�X��1-indexed��
	 */
	public LazySegmentTree(final S[] defaultValues,final S defaultS,final F defaultF,final boolean is1indexed){
		this(defaultValues.length,defaultS,defaultF,is1indexed);
		updateAll(defaultValues);
	}

	/**
	 * �S�Ă̗v�f��P�ʌ��ɁA���Z���P���ʑ��ɂ��܂��B
	 */
	public void clear(){
		Arrays.fill(this.node,this.defaultS);
		Arrays.fill(this.lazy,this.defaultF);
	}

	/**
	 * �����̗v�f�����Ƀe�[�u�����č\�z���܂��B
	 *
	 * @param defaultValues ���ƂȂ�z��
	 */
	private void updateAll(final S[] defaultValues){
		System.arraycopy(defaultValues,0,node,1<<log,defaultValues.length);
		for(int i = (1<<log)-1;i>0;i--){
			node[i] = function(node[i<<1],node[i<<1|1]);
		}
	}

	/**
	 * �����̈ʒu�̉��Z��K�p���A�q�ɓ`�d���܂��B
	 *
	 * @param index ���Z��K�p����ʒu
	 */
	private void spread(final int index){
		if(lazy[index]!=defaultF){
			int l = index<<1;
			int r = index<<1|1;
			node[l] = mapping(node[l],lazy[index]);
			node[r] = mapping(node[r],lazy[index]);
			lazy[l] = composition(lazy[index],lazy[l]);
			lazy[r] = composition(lazy[index],lazy[r]);
			lazy[index] = defaultF;
		}
	}

	/**
	 * �e���珇�Ɉ����̈ʒu�܂ł̉��Z��K�p���܂��B
	 *
	 * @param from ���Z��K�p����I�_
	 */
	private void spreadLine(final int from){
		for(int i = log;i>0;i--)
			spread(from>>i);
	}

	/**
	 * �����̋�Ԃ̉��Z��K�p���܂��B
	 *
	 * @param l ���[(l���܂�)
	 * @param r �E�[(r���܂܂Ȃ�)
	 */
	private void spreadRange(final int l,final int r){
		for(int i = log;i>0;i--){
			if((l>>i<<i)!=l)
				spread(l>>i);
			if((r>>i<<i)!=r)
				spread(r>>i);
		}
	}

	/**
	 * �����̈ʒu�̗v�f�̏���e�Ɍ������ē`�d���܂��B
	 *
	 * @param index �`�d�������v�f�̃C���f�b�N�X
	 */
	private void update(int index){
		while((index >>= 1)>0)
			node[index] = function(node[index<<1],node[index<<1|1]);
	}

	/**
	 * ��Ԃ̗v�f�̏���e�Ɍ������ē`�d���܂��B
	 *
	 * @param l ���[(l���܂�)
	 * @param r �E�[(r���܂܂Ȃ�)
	 */
	private void update(final int l,final int r){
		for(int i = 1;i<=log;i++){
			int subL = l>>i;
			int subR = r>>i;
			if((subL<<i)!=l)
				node[subL] = function(node[subL<<1],node[subL<<1|1]);
			if((subR<<i)!=r)
				node[subR] = function(node[subR<<1],node[subR<<1|1]);
		}
	}

	/**
	 * �����̈ʒu�̗v�f�������̒l�ŏ㏑�����܂��B
	 *
	 * @param index �C���f�b�N�X
	 * @param x �㏑������l
	 */
	public void update(int index,final S x){
		index += size;
		spreadLine(index);
		node[index] = x;
		update(index);
	}

	/**
	 * �����̈ʒu�̗v�f���擾���܂�
	 *
	 * @param index �C���f�b�N�X
	 *
	 * @return �����̈ʒu�̗v�f
	 */
	public S get(int index){
		index += size;
		spreadLine(index);
		return node[index];
	}

	/**
	 * �w�肳�ꂽ��Ԃ�function��K�p�������ʂ�Ԃ��܂��B
	 *
	 * @param l ���[(l���܂�)
	 * @param r �E�[(r���܂܂Ȃ�)
	 *
	 * @return [l,r)�̗v�f��function��K�p��������
	 */
	public S query(int l,int r){
		l += size;
		r += size;
		spreadRange(l,r);
		S sumL = defaultS;
		S sumR = defaultS;
		while(l<r){
			if((l&1)==1)
				sumL = function(sumL,node[l++]);
			if((r&1)==0)
				sumR = function(node[--r],sumR);
			l >>= 1;
			r >>= 1;
		}
		return function(sumL,sumR);
	}

	/**
	 * �S�v�f��function��K�p�������ʂ�Ԃ��܂��B
	 *
	 * @return �S�v�f��function��K�p��������
	 */
	public S answer(){
		return node[1];
	}

	/**
	 * �����̈ʒu�̗v�f�Ɏw�肳�ꂽ�p�����[�^�ɂ�鉉�Z�𔽉f���܂��B
	 *
	 * @param index �C���f�b�N�X
	 * @param f ���f���鉉�Z��\���p�����[�^
	 */
	public void apply(int index,final F f){
		index += size;
		spreadLine(index);
		node[index] = mapping(node[index],f);
		update(index);
	}

	/**
	 * �w�肳�ꂽ��Ԃ̗v�f�Ɏw�肳�ꂽ�p�����[�^�ɂ�鉉�Z�𔽉f���܂��B
	 *
	 * @param l ���[(l���܂�)
	 * @param r �E�[(r���܂܂Ȃ�)
	 * @param f ���f���鉉�Z��\���p�����[�^
	 */
	public void apply(int l,int r,final F f){
		l += size;
		r += size;
		spreadRange(l,r);
		int subL = l;
		int subR = r;
		while(subL<subR){
			if((subL&1)==1){
				node[subL] = mapping(node[subL],f);
				lazy[subL] = composition(f,lazy[subL++]);
			}
			if((subR&1)==1){
				node[--subR] = mapping(node[subR],f);
				lazy[subR] = composition(f,lazy[subR]);
			}
			subL >>= 1;
			subR >>= 1;
		}
		update(l,r);
	}

	/**
	 * �e�v�f�ɓK�p����֐����`���郁�\�b�h�ł��B
	 *
	 * @param s1 �v�f1
	 * @param s2 �v�f2
	 *
	 * @return �v�f�Ɋ֐���K�p��������
	 */
	abstract public S function(S s1,S s2);

	/**
	 * �p�����[�^���������郁�\�b�h�ł��B
	 * �p�����[�^��\���C���X�^���X���s�ςł��邱�ƁA
	 * �����ŕԂ��C���X�^���X�����ɉe�����y�ڂ��Ȃ����Ƃ��v������܂��B
	 *
	 * @param f1 �p�����[�^1
	 * @param f2 �p�����[�^2
	 *
	 * @return �p�����[�^�������������ʂ�\���p�����[�^
	 */
	abstract public F composition(F f1,F f2);

	/**
	 * �p�����[�^�����ɗv�f�ɉ��Z��K�p���܂��B
	 *
	 * @param s �v�f
	 * @param f �p�����[�^
	 *
	 * @return �v�f�ɉ��Z��K�p��������
	 */
	abstract public S mapping(S s,F f);
}

/**
 * UnionFind�N���X�ł��B
 * �A�������̑傫���̑��A�ӂ̐��A�A�������̐����擾�ł��܂��B
 */
final class UnionFind{
	private final int[] par, rank, size, path;
	private int count;

	/**
	 * �傫��N��UnionFind���\�z���܂��B
	 *
	 * @param N �傫��
	 */
	public UnionFind(final int N){
		count = N;
		par = new int[N];
		rank = new int[N];
		size = new int[N];
		path = new int[N];
		Arrays.fill(par,-1);
		Arrays.fill(size,1);
	}

	/**
	 * �����̒��_�̑�\�����e��Ԃ��܂��B
	 *
	 * @param ind ��\���擾���钸�_
	 *
	 * @return ��\�����e
	 */
	public int root(final int ind){
		if(par[ind]==-1){
			return ind;
		}
		else{
			return par[ind] = root(par[ind]);
		}
	}

	/**
	 * �����̓�̒��_������̘A�������ɑ����邩�Ԃ��܂��B
	 *
	 * @param x ���_1
	 * @param y ���_2
	 *
	 * @return ����̘A�������ɑ�����Ȃ�true�A����ȊO��false
	 */
	public boolean isSame(final int x,final int y){
		return root(x)==root(y);
	}

	/**
	 * �����̓�̒��_��A�����܂��B
	 * �A���ς݂ł����Ă��ӂ̓J�E���g����邱�Ƃɒ��ӂ��ĉ������B
	 *
	 * @param x ���_1
	 * @param y ���_2
	 *
	 * @return ���ɓ񒸓_������̘A�������ɑ����Ă���Ȃ�false�A����ȊO��true
	 */
	public boolean unite(final int x,final int y){
		int rx = root(x);
		int ry = root(y);
		++path[rx];
		if(rx==ry){
			return false;
		}
		if(rank[rx]<rank[ry]){
			int temp = rx;
			rx = ry;
			ry = temp;
		}
		par[ry] = rx;
		if(rank[rx]==rank[ry]){
			++rank[rx];
		}
		path[rx] += path[ry];
		size[rx] += size[ry];
		--count;
		return true;
	}

	/**
	 * ����UnionFind�̘A�������̐���Ԃ��܂��B
	 *
	 * @return �A�������̐�
	 */
	public int groupCount(){
		return count;
	}

	/**
	 * �����̒��_�̘A�������̕ӂ̑�����Ԃ��܂��B
	 *
	 * @param x ���_
	 *
	 * @return �A�������̕ӂ̑���
	 */
	public int pathCount(final int x){
		return path[root(x)];
	}

	/**
	 * �����̒��_�̘A�������̒��_����Ԃ��܂��B
	 *
	 * @param x ���_
	 *
	 * @return �A�������̒��_��
	 */
	public int size(final int x){
		return size[root(x)];
	}
}

/**
 * Tree�N���X�ł��B
 * java.util.TreeSet�Ɗ�{�ς��܂��񂪁A�C���f�b�N�X���w�肵�ẴA�N�Z�X��O(log N)�ŉ\�ł��B
 *
 * @param <E> Tree�ɍڂ���v�f�̌^
 */
final class Tree<E extends Comparable<E>>{
	private Node<E> root;
	private int size, hash;

	public Tree(){
		size = 0;
		root = null;
		hash = 0;
	}

	static final private class Node<E>{
		E value;
		int height, size;
		Node<E> left, right, parent;

		public Node(final Node<E> p,final E v){
			value = v;
			parent = p;
			height = 1;
			size = 1;
		}
	}

	public boolean add(final E x){
		boolean bool = true;
		if(root==null){
			root = new Node<>(null,x);
		}
		else{
			Node<E> par;
			Node<E> now = root;
			do{
				par = now;
				final int result = x.compareTo(now.value);
				if(result<0){
					now = now.left;
				}
				else if(result>0){
					now = now.right;
				}
				else{
					bool = false;
					break;
				}
			}
			while(now!=null);
			if(bool){
				final int result = x.compareTo(par.value);
				if(result<0){
					par.left = new Node<>(par,x);
				}
				else{
					par.right = new Node<>(par,x);
				}
				fix(par);
			}
		}
		if(bool){
			++size;
			hash ^= x.hashCode();
		}
		return bool;
	}

	public E get(int index){
		if(root==null||size<=index){
			throw new NullPointerException();
		}
		Node<E> now = root;
		while(true){
			assert now!=null;
			final int ls = now.left!=null?now.left.size:0;
			if(index<ls){
				now = now.left;
			}
			else if(ls<index){
				now = now.right;
				index -= ls+1;
			}
			else{
				break;
			}
		}
		return now.value;
	}

	public boolean remove(final E x){
		final Node<E> n = getNode(x);
		if(n==null){
			return false;
		}
		--size;
		hash ^= n.value.hashCode();
		delete(n);
		return true;
	}

	private void delete(final Node<E> node){
		if(node!=null){
			if(node.left==null&&node.right==null){
				if(node.parent!=null){
					if(node.parent.left==node){
						node.parent.left = null;
					}
					else{
						node.parent.right = null;
					}
					fix(node.parent);
				}
				else{
					root = null;
				}
				node.parent = null;
			}
			else{
				if(node.left!=null&&node.right!=null){
					final Node<E> rep = getFirstNode(node.right);
					node.value = rep.value;
					delete(rep);
				}
				else{
					final Node<E> rep = node.left!=null?node.left:node.right;
					rep.parent = node.parent;
					if(node.parent!=null){
						if(node.parent.left==node){
							node.parent.left = rep;
						}
						else{
							node.parent.right = rep;
						}
						fix(node.parent);
					}
					else{
						root = rep;
					}
					node.parent = null;
				}
			}
		}
	}

	private Node<E> getNode(final E x){
		Node<E> now = root;
		while(now!=null){
			final int result = x.compareTo(now.value);
			if(result<0){
				now = now.left;
			}
			else if(result>0){
				now = now.right;
			}
			else{
				break;
			}
		}
		return now;
	}

	public E first(){
		if(root==null){
			return null;
		}
		return getFirstNode(root).value;
	}

	private Node<E> getFirstNode(Node<E> node){
		assert node!=null;
		Node<E> par = null;
		while(node!=null){
			par = node;
			node = par.left;
		}
		return par;
	}

	public E last(){
		if(root==null){
			return null;
		}
		return getLastNode(root).value;
	}

	private Node<E> getLastNode(Node<E> node){
		assert node!=null;
		Node<E> par = null;
		while(node!=null){
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains(final E x){
		if(root==null){
			return false;
		}
		return getNode(x)!=null;
	}

	public E pollFirst(){
		if(root==null){
			return null;
		}
		--size;
		final Node<E> min = getFirstNode(root);
		hash ^= min.value.hashCode();
		delete(min);
		return min.value;
	}

	public E pollLast(){
		if(root==null){
			return null;
		}
		--size;
		final Node<E> max = getLastNode(root);
		hash ^= max.value.hashCode();
		delete(max);
		return max.value;
	}

	public E ceiling(final E x){
		return ceiling(root,x);
	}

	private E ceiling(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result>0){
				node = node.right;
			}
			else if(result<0){
				ans = node;
				node = node.left;
			}
			else{
				return x;
			}
		}
		return ans!=null?ans.value:null;
	}

	public E higher(final E x){
		return higher(root,x);
	}

	private E higher(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result>=0){
				node = node.right;
			}
			else{
				ans = node;
				node = node.left;
			}
		}
		return ans!=null?ans.value:null;
	}

	public E floor(final E x){
		return floor(root,x);
	}

	private E floor(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result<0){
				node = node.left;
			}
			else if(result>0){
				ans = node;
				node = node.right;
			}
			else{
				return x;
			}
		}
		return ans!=null?ans.value:null;
	}

	public E lower(final E x){
		return lower(root,x);
	}

	private E lower(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result<=0){
				node = node.left;
			}
			else{
				ans = node;
				node = node.right;
			}
		}
		return ans!=null?ans.value:null;
	}

	public void clear(){
		root = null;
		size = 0;
		hash = 0;
	}

	public boolean isEmpty(){
		return size==0;
	}

	public int size(){
		return size;
	}

	public ArrayList<E> toList(){
		final ArrayList<E> list = new ArrayList<>();
		if(root!=null){
			final ArrayDeque<Node<E>> deq = new ArrayDeque<>();
			deq.add(root);
			while(!deq.isEmpty()){
				final Node<E> now = deq.pollLast();
				if(list.size()==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						list.add(now.value);
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list.get(list.size()-1).compareTo(now.left.value)<0){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					list.add(now.value);
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	@Override public String toString(){
		final ArrayList<E> list = toList();
		return list.toString();
	}

	@Override public boolean equals(final Object o){
		if(o instanceof final Tree<?> tree){
			if(size!=tree.size()){
				return false;
			}
			final ArrayList<E> list1 = toList();
			final ArrayList<?> list2 = tree.toList();
			for(int i = 0;i<size;++i){
				if(!list1.get(i).equals(list2.get(i))){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override public int hashCode(){
		return hash;
	}

	/*
	 * �ȉ��A���t�p���\�b�h
	 */
	private void fix(Node<E> node){
		while(node!=null){
			final int lh = node.left==null?0:node.left.height;
			final int rh = node.right==null?0:node.right.height;
			if(lh-rh>1){
				assert node.left!=null;
				if(node.left.right!=null&&node.left.right.height==lh-1){
					rotateL(node.left);
				}
				rotateR(node);
			}
			else if(rh-lh>1){
				assert node.right!=null;
				if(node.right.left!=null&&node.right.left.height==rh-1){
					rotateR(node.right);
				}
				rotateL(node);
			}
			else{
				setStates(node);
			}
			node = node.parent;
		}
	}

	private void rotateR(final Node<E> node){
		final Node<E> temp = node.left;
		node.left = temp.right;
		if(node.left!=null){
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void rotateL(final Node<E> node){
		final Node<E> temp = node.right;
		node.right = temp.left;
		if(node.right!=null){
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void setStates(final Node<E> node){
		final int lh = node.left!=null?node.left.height:0;
		final int rh = node.right!=null?node.right.height:0;
		node.height = Math.max(lh,rh)+1;
		final int ls = node.left!=null?node.left.size:0;
		final int rs = node.right!=null?node.right.size:0;
		node.size = ls+rs+1;
	}
}

final class TreeInt{
	private Node root;
	private int size, hash;

	public TreeInt(){
		size = 0;
		root = null;
		hash = 0;
	}

	static final private class Node{
		int value;
		int height, size;
		Node left, right, parent;

		public Node(final Node p,final int v){
			value = v;
			parent = p;
			height = 1;
			size = 1;
		}
	}

	public boolean add(final int x){
		boolean bool = true;
		if(root==null){
			root = new Node(null,x);
		}
		else{
			Node par;
			Node now = root;
			do{
				par = now;
				if(x<now.value){
					now = now.left;
				}
				else if(x>now.value){
					now = now.right;
				}
				else{
					bool = false;
					break;
				}
			}
			while(now!=null);
			if(bool){
				if(x<par.value){
					par.left = new Node(par,x);
				}
				else{
					par.right = new Node(par,x);
				}
				fix(par);
			}
		}
		if(bool){
			++size;
			hash ^= x;
		}
		return bool;
	}

	public int get(int index){
		if(root==null||size<=index){
			throw new NullPointerException();
		}
		Node now = root;
		while(true){
			assert now!=null;
			final int ls = now.left!=null?now.left.size:0;
			if(index<ls){
				now = now.left;
			}
			else if(ls<index){
				now = now.right;
				index -= ls+1;
			}
			else{
				break;
			}
		}
		return now.value;
	}

	public boolean remove(final int x){
		final Node n = getNode(x);
		if(n==null){
			return false;
		}
		--size;
		hash ^= n.value;
		delete(n);
		return true;
	}

	private void delete(final Node node){
		if(node!=null){
			if(node.left==null&&node.right==null){
				if(node.parent!=null){
					if(node.parent.left==node){
						node.parent.left = null;
					}
					else{
						node.parent.right = null;
					}
					fix(node.parent);
				}
				else{
					root = null;
				}
				node.parent = null;
			}
			else{
				if(node.left!=null&&node.right!=null){
					final Node rep = getFirstNode(node.right);
					node.value = rep.value;
					delete(rep);
				}
				else{
					final Node rep = node.left!=null?node.left:node.right;
					rep.parent = node.parent;
					if(node.parent!=null){
						if(node.parent.left==node){
							node.parent.left = rep;
						}
						else{
							node.parent.right = rep;
						}
						fix(node.parent);
					}
					else{
						root = rep;
					}
					node.parent = null;
				}
			}
		}
	}

	private Node getNode(final int x){
		Node now = root;
		while(now!=null){
			if(x<now.value){
				now = now.left;
			}
			else if(x>now.value){
				now = now.right;
			}
			else{
				break;
			}
		}
		return now;
	}

	public int first(){
		if(root==null){
			throw new NullPointerException();
		}
		return getFirstNode(root).value;
	}

	private Node getFirstNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.left;
		}
		return par;
	}

	public int last(){
		if(root==null){
			throw new NullPointerException();
		}
		return getLastNode(root).value;
	}

	private Node getLastNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains(final int x){
		if(root==null){
			return false;
		}
		return getNode(x)!=null;
	}

	public int pollFirst(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode(root);
		hash ^= min.value;
		delete(min);
		return min.value;
	}

	public int pollLast(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode(root);
		hash ^= max.value;
		delete(max);
		return max.value;
	}

	public int ceiling(final int x){
		return ceiling(root,x);
	}

	private int ceiling(Node node,final int x){
		Node ans = new Node(null,x-1);
		while(node!=null){
			if(x>node.value){
				node = node.right;
			}
			else if(x<node.value){
				ans = node;
				node = node.left;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public int higher(final int x){
		return higher(root,x);
	}

	private int higher(Node node,final int x){
		Node ans = new Node(null,x-1);
		while(node!=null){
			if(x>=node.value){
				node = node.right;
			}
			else{
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public int floor(final int x){
		return floor(root,x);
	}

	private int floor(Node node,final int x){
		Node ans = new Node(null,x+1);
		while(node!=null){
			if(x<node.value){
				node = node.left;
			}
			else if(x>node.value){
				ans = node;
				node = node.right;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public int lower(final int x){
		return lower(root,x);
	}

	private int lower(Node node,final int x){
		Node ans = new Node(null,x+1);
		while(node!=null){
			if(x<=node.value){
				node = node.left;
			}
			else{
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public void clear(){
		root = null;
		size = 0;
		hash = 0;
	}

	public boolean isEmpty(){
		return size==0;
	}

	public int size(){
		return size;
	}

	public int[] toArray(){
		final int[] list = new int[size];
		if(root!=null){
			int index = 0;
			final ArrayDeque<Node> deq = new ArrayDeque<>();
			deq.add(root);
			while(!deq.isEmpty()){
				final Node now = deq.pollLast();
				if(index==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						list[index++] = now.value;
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list[index-1]<now.left.value){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					list[index++] = now.value;
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	@Override public String toString(){
		final int[] list = toArray();
		return Arrays.toString(list);
	}

	@Override public boolean equals(final Object o){
		if(o instanceof final TreeInt tree){
			if(size!=tree.size()){
				return false;
			}
			final int[] array1 = toArray();
			final int[] array2 = tree.toArray();
			for(int i = 0;i<size;++i){
				if(array1[i]!=array2[i]){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override public int hashCode(){
		return hash;
	}

	/*
	 * �ȉ��A���t�p���\�b�h
	 */
	private void fix(Node node){
		while(node!=null){
			final int lh = node.left==null?0:node.left.height;
			final int rh = node.right==null?0:node.right.height;
			if(lh-rh>1){
				assert node.left!=null;
				if(node.left.right!=null&&node.left.right.height==lh-1){
					rotateL(node.left);
				}
				rotateR(node);
			}
			else if(rh-lh>1){
				assert node.right!=null;
				if(node.right.left!=null&&node.right.left.height==rh-1){
					rotateR(node.right);
				}
				rotateL(node);
			}
			else{
				setStates(node);
			}
			node = node.parent;
		}
	}

	private void rotateR(final Node node){
		final Node temp = node.left;
		node.left = temp.right;
		if(node.left!=null){
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void rotateL(final Node node){
		final Node temp = node.right;
		node.right = temp.left;
		if(node.right!=null){
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void setStates(final Node node){
		final int lh = node.left!=null?node.left.height:0;
		final int rh = node.right!=null?node.right.height:0;
		node.height = Math.max(lh,rh)+1;
		final int ls = node.left!=null?node.left.size:0;
		final int rs = node.right!=null?node.right.size:0;
		node.size = ls+rs+1;
	}
}

final class TreeLong{
	private Node root;
	private int size, hash;

	public TreeLong(){
		size = 0;
		root = null;
		hash = 0;
	}

	static final private class Node{
		long value;
		int height, size;
		Node left, right, parent;

		public Node(final Node p,final long v){
			value = v;
			parent = p;
			height = 1;
			size = 1;
		}
	}

	public boolean add(final long x){
		boolean bool = true;
		if(root==null){
			root = new Node(null,x);
		}
		else{
			Node par;
			Node now = root;
			do{
				par = now;
				if(x<now.value){
					now = now.left;
				}
				else if(x>now.value){
					now = now.right;
				}
				else{
					bool = false;
					break;
				}
			}
			while(now!=null);
			if(bool){
				if(x<par.value){
					par.left = new Node(par,x);
				}
				else{
					par.right = new Node(par,x);
				}
				fix(par);
			}
		}
		if(bool){
			++size;
			hash ^= (int)x;
		}
		return bool;
	}

	public long get(int index){
		if(root==null||size<=index){
			throw new NullPointerException();
		}
		Node now = root;
		while(true){
			assert now!=null;
			final int ls = now.left!=null?now.left.size:0;
			if(index<ls){
				now = now.left;
			}
			else if(ls<index){
				now = now.right;
				index -= ls+1;
			}
			else{
				break;
			}
		}
		return now.value;
	}

	public boolean remove(final long x){
		final Node n = getNode(x);
		if(n==null){
			return false;
		}
		--size;
		hash ^= (int)x;
		delete(n);
		return true;
	}

	private void delete(final Node node){
		if(node!=null){
			if(node.left==null&&node.right==null){
				if(node.parent!=null){
					if(node.parent.left==node){
						node.parent.left = null;
					}
					else{
						node.parent.right = null;
					}
					fix(node.parent);
				}
				else{
					root = null;
				}
				node.parent = null;
			}
			else{
				if(node.left!=null&&node.right!=null){
					final Node rep = getFirstNode(node.right);
					node.value = rep.value;
					delete(rep);
				}
				else{
					final Node rep = node.left!=null?node.left:node.right;
					rep.parent = node.parent;
					if(node.parent!=null){
						if(node.parent.left==node){
							node.parent.left = rep;
						}
						else{
							node.parent.right = rep;
						}
						fix(node.parent);
					}
					else{
						root = rep;
					}
					node.parent = null;
				}
			}
		}
	}

	private Node getNode(final long x){
		Node now = root;
		while(now!=null){
			if(x<now.value){
				now = now.left;
			}
			else if(x>now.value){
				now = now.right;
			}
			else{
				break;
			}
		}
		return now;
	}

	public long first(){
		if(root==null){
			throw new NullPointerException();
		}
		return getFirstNode(root).value;
	}

	private Node getFirstNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.left;
		}
		return par;
	}

	public long last(){
		if(root==null){
			throw new NullPointerException();
		}
		return getLastNode(root).value;
	}

	private Node getLastNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains(final long x){
		if(root==null){
			return false;
		}
		return getNode(x)!=null;
	}

	public long pollFirst(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode(root);
		hash ^= (int)min.value;
		delete(min);
		return min.value;
	}

	public long pollLast(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode(root);
		hash ^= (int)max.value;
		delete(max);
		return max.value;
	}

	public long ceiling(final long x){
		return ceiling(root,x);
	}

	private long ceiling(Node node,final long x){
		Node ans = new Node(null,x-1);
		while(node!=null){
			if(x>node.value){
				node = node.right;
			}
			else if(x<node.value){
				ans = node;
				node = node.left;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public long higher(final long x){
		return higher(root,x);
	}

	private long higher(Node node,final long x){
		Node ans = new Node(null,x-1);
		while(node!=null){
			if(x>=node.value){
				node = node.right;
			}
			else{
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public long floor(final long x){
		return floor(root,x);
	}

	private long floor(Node node,final long x){
		Node ans = new Node(null,x+1);
		while(node!=null){
			if(x<node.value){
				node = node.left;
			}
			else if(x>node.value){
				ans = node;
				node = node.right;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public long lower(final long x){
		return lower(root,x);
	}

	private long lower(Node node,final long x){
		Node ans = new Node(null,x+1);
		while(node!=null){
			if(x<=node.value){
				node = node.left;
			}
			else{
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size(){
		return size;
	}

	public long[] toArray(){
		final long[] list = new long[size];
		if(root!=null){
			int index = 0;
			final ArrayDeque<Node> deq = new ArrayDeque<>();
			deq.add(root);
			while(!deq.isEmpty()){
				final Node now = deq.pollLast();
				if(index==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						list[index++] = now.value;
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list[index-1]<now.left.value){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					list[index++] = now.value;
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	@Override public String toString(){
		final long[] list = toArray();
		return Arrays.toString(list);
	}

	@Override public boolean equals(final Object o){
		if(o instanceof final TreeLong tree){
			if(size!=tree.size()){
				return false;
			}
			final long[] array1 = toArray();
			final long[] array2 = tree.toArray();
			for(int i = 0;i<size;++i){
				if(array1[i]!=array2[i]){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override public int hashCode(){
		return hash;
	}

	/*
	 * �ȉ��A���t�p���\�b�h
	 */
	private void fix(Node node){
		while(node!=null){
			final int lh = node.left==null?0:node.left.height;
			final int rh = node.right==null?0:node.right.height;
			if(lh-rh>1){
				assert node.left!=null;
				if(node.left.right!=null&&node.left.right.height==lh-1){
					rotateL(node.left);
				}
				rotateR(node);
			}
			else if(rh-lh>1){
				assert node.right!=null;
				if(node.right.left!=null&&node.right.left.height==rh-1){
					rotateR(node.right);
				}
				rotateL(node);
			}
			else{
				setStates(node);
			}
			node = node.parent;
		}
	}

	private void rotateR(final Node node){
		final Node temp = node.left;
		node.left = temp.right;
		if(node.left!=null){
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void rotateL(final Node node){
		final Node temp = node.right;
		node.right = temp.left;
		if(node.right!=null){
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void setStates(final Node node){
		final int lh = node.left!=null?node.left.height:0;
		final int rh = node.right!=null?node.right.height:0;
		node.height = Math.max(lh,rh)+1;
		final int ls = node.left!=null?node.left.size:0;
		final int rs = node.right!=null?node.right.size:0;
		node.size = ls+rs+1;
	}
}

final class TreeMulti<E extends Comparable<E>>{
	private Node<E> root;
	private long size;
	private int uniqueSize;
	private int hash;

	public TreeMulti(){
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}

	static final private class Node<E>{
		E value;
		long count, size;
		int height;
		Node<E> left, right, parent;

		public Node(final Node<E> p,final E v,final long c){
			value = v;
			parent = p;
			count = c;
			height = 1;
			size = c;
		}
	}

	public void add(final E x){
		if(root==null){
			root = new Node<>(null,x,1);
			++uniqueSize;
		}
		else{
			Node<E> par;
			Node<E> now = root;
			boolean bool = true;
			do{
				par = now;
				final int result = x.compareTo(now.value);
				if(result<0){
					now = now.left;
				}
				else if(result>0){
					now = now.right;
				}
				else{
					bool = false;
					++now.count;
					break;
				}
			}
			while(now!=null);
			if(bool){
				++uniqueSize;
				final int result = x.compareTo(par.value);
				if(result<0){
					par.left = new Node<>(par,x,1);
				}
				else{
					par.right = new Node<>(par,x,1);
				}
			}
			fix(par);
		}
		++size;
		hash ^= x.hashCode();
	}

	public void add(final E x,final long sum){
		if(root==null){
			root = new Node<>(null,x,sum);
			++uniqueSize;
		}
		else{
			Node<E> par;
			Node<E> now = root;
			boolean bool = true;
			do{
				par = now;
				final int result = x.compareTo(now.value);
				if(result<0){
					now = now.left;
				}
				else if(result>0){
					now = now.right;
				}
				else{
					bool = false;
					now.count += sum;
					fix(now);
					break;
				}
			}
			while(now!=null);
			if(bool){
				++uniqueSize;
				final int result = x.compareTo(par.value);
				if(result<0){
					par.left = new Node<>(par,x,sum);
				}
				else{
					par.right = new Node<>(par,x,sum);
				}
				fix(par);
			}
		}
		size += sum;
		hash ^= sum%2==1?x.hashCode():0;
	}

	public E get(long index){
		if(root==null||size<=index){
			throw new NullPointerException();
		}
		Node<E> now = root;
		while(true){
			assert now!=null;
			final long ls = now.left!=null?now.left.size:0;
			if(index<ls){
				now = now.left;
			}
			else if(ls+now.count<=index){
				index -= ls+now.count;
				now = now.right;
			}
			else{
				break;
			}
		}
		return now.value;
	}

	public boolean remove(final E x){
		final Node<E> n = getNode(x);
		if(n==null){
			return false;
		}
		--size;
		hash ^= n.hashCode();
		delete(n);
		return true;
	}

	public long remove(final E x,final long sum){
		final Node<E> n = getNode(x);
		if(n==null){
			return 0;
		}
		final long ans = Math.min(sum,n.count);
		size -= ans;
		hash ^= ans%2==1?n.hashCode():0;
		n.count -= ans-1;
		delete(n);
		return ans;
	}

	public long removeAll(final E x){
		final Node<E> n = getNode(x);
		if(n==null){
			return 0;
		}
		size -= n.count;
		final long ans = n.count;
		hash ^= ans%2==1?n.hashCode():0;
		n.count = 0;
		delete(n);
		return ans;
	}

	private void delete(final Node<E> node){
		if(node!=null){
			if(node.count>1){
				--node.count;
				fix(node);
				return;
			}
			if(node.left==null&&node.right==null){
				if(node.parent!=null){
					if(node.parent.left==node){
						node.parent.left = null;
					}
					else{
						node.parent.right = null;
					}
					fix(node.parent);
				}
				else{
					root = null;
				}
				node.parent = null;
				--uniqueSize;
			}
			else{
				if(node.left!=null&&node.right!=null){
					final Node<E> rep = getFirstNode(node.right);
					node.value = rep.value;
					node.count = rep.count;
					rep.count = 0;
					delete(rep);
				}
				else{
					final Node<E> rep = node.left!=null?node.left:node.right;
					rep.parent = node.parent;
					if(node.parent!=null){
						if(node.parent.left==node){
							node.parent.left = rep;
						}
						else{
							node.parent.right = rep;
						}
						fix(node.parent);
					}
					else{
						root = rep;
					}
					node.parent = null;
					--uniqueSize;
				}
			}
		}
	}

	private Node<E> getNode(final E x){
		Node<E> now = root;
		while(now!=null){
			final int result = x.compareTo(now.value);
			if(result<0){
				now = now.left;
			}
			else if(result>0){
				now = now.right;
			}
			else{
				break;
			}
		}
		return now;
	}

	public E first(){
		if(root==null){
			return null;
		}
		return getFirstNode(root).value;
	}

	private Node<E> getFirstNode(Node<E> node){
		assert node!=null;
		Node<E> par = null;
		while(node!=null){
			par = node;
			node = par.left;
		}
		return par;
	}

	public E last(){
		if(root==null){
			return null;
		}
		return getLastNode(root).value;
	}

	private Node<E> getLastNode(Node<E> node){
		assert node!=null;
		Node<E> par = null;
		while(node!=null){
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains(final E x){
		if(root==null){
			return false;
		}
		return getNode(x)!=null;
	}

	public long sum(final E x){
		if(root==null){
			return 0;
		}
		Node<E> node = getNode(x);
		return node!=null?node.count:0;
	}

	public E pollFirst(){
		if(root==null){
			return null;
		}
		--size;
		final Node<E> min = getFirstNode(root);
		hash ^= min.value.hashCode();
		final E ans = min.value;
		delete(min);
		return ans;
	}

	public E pollLast(){
		if(root==null){
			return null;
		}
		--size;
		final Node<E> max = getLastNode(root);
		hash ^= max.value.hashCode();
		final E ans = max.value;
		delete(max);
		return ans;
	}

	public E ceiling(final E x){
		return ceiling(root,x);
	}

	private E ceiling(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result>0){
				node = node.right;
			}
			else if(result<0){
				ans = node;
				node = node.left;
			}
			else{
				return x;
			}
		}
		return ans!=null?ans.value:null;
	}

	public E higher(final E x){
		return higher(root,x);
	}

	private E higher(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result>=0){
				node = node.right;
			}
			else{
				ans = node;
				node = node.left;
			}
		}
		return ans!=null?ans.value:null;
	}

	public E floor(final E x){
		return floor(root,x);
	}

	private E floor(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result<0){
				node = node.left;
			}
			else if(result>0){
				ans = node;
				node = node.right;
			}
			else{
				return x;
			}
		}
		return ans!=null?ans.value:null;
	}

	public E lower(final E x){
		return lower(root,x);
	}

	private E lower(Node<E> node,final E x){
		Node<E> ans = null;
		while(node!=null){
			final int result = x.compareTo(node.value);
			if(result<=0){
				node = node.left;
			}
			else{
				ans = node;
				node = node.right;
			}
		}
		return ans!=null?ans.value:null;
	}

	public int size(){
		return uniqueSize;
	}

	public long sumSize(){
		return size;
	}

	public ArrayList<E> toList(){
		final ArrayList<E> list = new ArrayList<>();
		if(root!=null){
			final ArrayDeque<Node<E>> deq = new ArrayDeque<>();
			deq.add(root);
			while(!deq.isEmpty()){
				final Node<E> now = deq.pollLast();
				if(list.size()==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						for(int i = 0;i<now.count;++i){
							list.add(now.value);
						}
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list.get(list.size()-1).compareTo(now.left.value)<0){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					for(int i = 0;i<now.count;++i){
						list.add(now.value);
					}
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	@Override public String toString(){
		final ArrayList<E> list = toList();
		return list.toString();
	}

	@Override public boolean equals(final Object o){
		if(o instanceof final TreeMulti<?> tree){
			if(size!=tree.size()){
				return false;
			}
			final ArrayList<E> array1 = toList();
			final ArrayList<?> array2 = tree.toList();
			for(int i = 0;i<size;++i){
				if(!array1.get(i).equals(array2.get(i))){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override public int hashCode(){
		return hash;
	}

	/*
	 * �ȉ��A���t�p���\�b�h
	 */
	private void fix(Node<E> node){
		while(node!=null){
			final int lh = node.left==null?0:node.left.height;
			final int rh = node.right==null?0:node.right.height;
			if(lh-rh>1){
				assert node.left!=null;
				if(node.left.right!=null&&node.left.right.height==lh-1){
					rotateL(node.left);
					setStates(node.left);
				}
				rotateR(node);
			}
			else if(rh-lh>1){
				assert node.right!=null;
				if(node.right.left!=null&&node.right.left.height==rh-1){
					rotateR(node.right);
					setStates(node.right);
				}
				rotateL(node);
			}
			setStates(node);
			node = node.parent;
		}
	}

	private void rotateR(final Node<E> node){
		final Node<E> temp = node.left;
		node.left = temp.right;
		if(node.left!=null){
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void rotateL(final Node<E> node){
		final Node<E> temp = node.right;
		node.right = temp.left;
		if(node.right!=null){
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void setStates(final Node<E> node){
		final int lh = node.left!=null?node.left.height:0;
		final int rh = node.right!=null?node.right.height:0;
		node.height = Math.max(lh,rh)+1;
		final long ls = node.left!=null?node.left.size:0;
		final long rs = node.right!=null?node.right.size:0;
		node.size = ls+rs+node.count;
	}
}

final class TreeMultiInt{
	private Node root;
	private long size;
	private int uniqueSize;
	private int hash;

	public TreeMultiInt(){
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}

	static final private class Node{
		int value;
		long count, size;
		int height;
		Node left, right, parent;

		public Node(final Node p,final int v,final long c){
			value = v;
			parent = p;
			count = c;
			height = 1;
			size = c;
		}
	}

	public void add(final int x){
		if(root==null){
			root = new Node(null,x,1);
			++uniqueSize;
		}
		else{
			Node par;
			Node now = root;
			boolean bool = true;
			do{
				par = now;
				if(x<now.value){
					now = now.left;
				}
				else if(x>now.value){
					now = now.right;
				}
				else{
					bool = false;
					++now.count;
					break;
				}
			}
			while(now!=null);
			if(bool){
				++uniqueSize;
				if(x<par.value){
					par.left = new Node(par,x,1);
				}
				else{
					par.right = new Node(par,x,1);
				}
			}
			fix(par);
		}
		++size;
		hash ^= x;
	}

	public void add(final int x,final long sum){
		if(root==null){
			root = new Node(null,x,sum);
			++uniqueSize;
		}
		else{
			Node par;
			Node now = root;
			boolean bool = true;
			do{
				par = now;
				if(x<now.value){
					now = now.left;
				}
				else if(x>now.value){
					now = now.right;
				}
				else{
					bool = false;
					now.count += sum;
					fix(now);
					break;
				}
			}
			while(now!=null);
			if(bool){
				++uniqueSize;
				if(x<par.value){
					par.left = new Node(par,x,sum);
				}
				else{
					par.right = new Node(par,x,sum);
				}
				fix(par);
			}
		}
		size += sum;
		if(sum%2==1){
			hash ^= x;
		}
	}

	public int get(long index){
		if(root==null||size<=index){
			throw new NullPointerException();
		}
		Node now = root;
		while(true){
			assert now!=null;
			final long ls = now.left!=null?now.left.size:0;
			if(index<ls){
				now = now.left;
			}
			else if(ls+now.count<=index){
				index -= ls+now.count;
				now = now.right;
			}
			else{
				break;
			}
		}
		return now.value;
	}

	public boolean remove(final int x){
		final Node n = getNode(x);
		if(n==null){
			return false;
		}
		--size;
		hash ^= x;
		delete(n);
		return true;
	}

	public long remove(final int x,final long sum){
		final Node n = getNode(x);
		if(n==null){
			return 0;
		}
		final long ans = Math.min(sum,n.count);
		size -= ans;
		n.count -= ans-1;
		if(ans%2==1){
			hash ^= x;
		}
		delete(n);
		return ans;
	}

	public long removeAll(final int x){
		final Node n = getNode(x);
		if(n==null){
			return 0;
		}
		size -= n.count;
		final long ans = n.count;
		if(n.count%2==1){
			hash ^= x;
		}
		n.count = 0;
		delete(n);
		return ans;
	}

	private void delete(final Node node){
		if(node!=null){
			if(node.count>1){
				--node.count;
				fix(node);
				return;
			}
			if(node.left==null&&node.right==null){
				if(node.parent!=null){
					if(node.parent.left==node){
						node.parent.left = null;
					}
					else{
						node.parent.right = null;
					}
					fix(node.parent);
				}
				else{
					root = null;
				}
				node.parent = null;
				--uniqueSize;
			}
			else{
				if(node.left!=null&&node.right!=null){
					final Node rep = getFirstNode(node.right);
					node.value = rep.value;
					node.count = rep.count;
					rep.count = 0;
					delete(rep);
				}
				else{
					final Node rep = node.left!=null?node.left:node.right;
					rep.parent = node.parent;
					if(node.parent!=null){
						if(node.parent.left==node){
							node.parent.left = rep;
						}
						else{
							node.parent.right = rep;
						}
						fix(node.parent);
					}
					else{
						root = rep;
					}
					node.parent = null;
					--uniqueSize;
				}
			}
		}
	}

	private Node getNode(final int x){
		Node now = root;
		while(now!=null){
			if(x<now.value){
				now = now.left;
			}
			else if(x>now.value){
				now = now.right;
			}
			else{
				break;
			}
		}
		return now;
	}

	public int first(){
		if(root==null){
			throw new NullPointerException();
		}
		return getFirstNode(root).value;
	}

	private Node getFirstNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.left;
		}
		return par;
	}

	public int last(){
		if(root==null){
			throw new NullPointerException();
		}
		return getLastNode(root).value;
	}

	private Node getLastNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains(final int x){
		if(root==null){
			return false;
		}
		return getNode(x)!=null;
	}

	public long sum(final int x){
		if(root==null){
			return 0;
		}
		Node node = getNode(x);
		return node!=null?node.count:0;
	}

	public int pollFirst(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode(root);
		hash ^= min.value;
		final int ans = min.value;
		delete(min);
		return ans;
	}

	public int pollLast(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode(root);
		hash ^= max.value;
		final int ans = max.value;
		delete(max);
		return ans;
	}

	public int ceiling(final int x){
		return ceiling(root,x);
	}

	private int ceiling(Node node,final int x){
		Node ans = new Node(null,x-1,0);
		while(node!=null){
			if(x>node.value){
				node = node.right;
			}
			else if(x<node.value){
				ans = node;
				node = node.left;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public int higher(final int x){
		return higher(root,x);
	}

	private int higher(Node node,final int x){
		Node ans = new Node(null,x-1,0);
		while(node!=null){
			if(x>=node.value){
				node = node.right;
			}
			else{
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public int floor(final int x){
		return floor(root,x);
	}

	private int floor(Node node,final int x){
		Node ans = new Node(null,x+1,0);
		while(node!=null){
			if(x<node.value){
				node = node.left;
			}
			else if(x>node.value){
				ans = node;
				node = node.right;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public int lower(final int x){
		return lower(root,x);
	}

	private int lower(Node node,final int x){
		Node ans = new Node(null,x+1,0);
		while(node!=null){
			if(x<=node.value){
				node = node.left;
			}
			else{
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size(){
		return uniqueSize;
	}

	public long sumSize(){
		return size;
	}

	public long[][] toArray(){
		final long[][] list = new long[uniqueSize][2];
		if(root!=null){
			int index = 0;
			final ArrayDeque<Node> deq = new ArrayDeque<>(root.height<<1);
			deq.add(root);
			while(!deq.isEmpty()){
				final Node now = deq.pollLast();
				if(index==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						list[index][0] = now.value;
						list[index++][1] = now.count;
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list[index-1][0]<now.left.value){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					list[index][0] = now.value;
					list[index++][1] = now.count;
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	public int[] toOneArray(){
		final int[] list = new int[(int)size];
		if(root!=null){
			int index = 0;
			final ArrayDeque<Node> deq = new ArrayDeque<>();
			deq.add(root);
			while(!deq.isEmpty()){
				final Node now = deq.pollLast();
				if(index==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						for(int i = 0;i<now.count;++i){
							list[index++] = now.value;
						}
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list[index-1]<now.left.value){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					for(int i = 0;i<now.count;++i){
						list[index++] = now.value;
					}
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	@Override public String toString(){
		final int[] list = toOneArray();
		return Arrays.toString(list);
	}

	@Override public boolean equals(final Object o){
		if(o instanceof final TreeMultiInt tree){
			if(size!=tree.size()){
				return false;
			}
			final long[][] array1 = toArray();
			final long[][] array2 = tree.toArray();
			for(int i = 0;i<size;++i){
				if(array1[i][0]!=array2[i][0]||array1[i][1]!=array2[i][1]){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override public int hashCode(){
		return hash;
	}

	/*
	 * �ȉ��A���t�p���\�b�h
	 */
	private void fix(Node node){
		while(node!=null){
			final int lh = node.left==null?0:node.left.height;
			final int rh = node.right==null?0:node.right.height;
			if(lh-rh>1){
				assert node.left!=null;
				if(node.left.right!=null&&node.left.right.height==lh-1){
					rotateL(node.left);
					setStates(node.left);
				}
				rotateR(node);
			}
			else if(rh-lh>1){
				assert node.right!=null;
				if(node.right.left!=null&&node.right.left.height==rh-1){
					rotateR(node.right);
					setStates(node.right);
				}
				rotateL(node);
			}
			setStates(node);
			node = node.parent;
		}
	}

	private void rotateR(final Node node){
		final Node temp = node.left;
		node.left = temp.right;
		if(node.left!=null){
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void rotateL(final Node node){
		final Node temp = node.right;
		node.right = temp.left;
		if(node.right!=null){
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void setStates(final Node node){
		final int lh = node.left!=null?node.left.height:0;
		final int rh = node.right!=null?node.right.height:0;
		node.height = Math.max(lh,rh)+1;
		final long ls = node.left!=null?node.left.size:0;
		final long rs = node.right!=null?node.right.size:0;
		node.size = ls+rs+node.count;
	}
}

final class TreeMultiLong{
	private Node root;
	private long size;
	private int uniqueSize;
	private int hash;

	public TreeMultiLong(){
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}

	static final private class Node{
		long value;
		long count, size;
		int height;
		Node left, right, parent;

		public Node(final Node p,final long v,final long c){
			value = v;
			parent = p;
			count = c;
			height = 1;
			size = c;
		}
	}

	public void add(final long x){
		if(root==null){
			root = new Node(null,x,1);
			++uniqueSize;
		}
		else{
			Node par;
			Node now = root;
			boolean bool = true;
			do{
				par = now;
				if(x<now.value){
					now = now.left;
				}
				else if(x>now.value){
					now = now.right;
				}
				else{
					bool = false;
					++now.count;
					break;
				}
			}
			while(now!=null);
			if(bool){
				++uniqueSize;
				if(x<par.value){
					par.left = new Node(par,x,1);
				}
				else{
					par.right = new Node(par,x,1);
				}
			}
			fix(par);
		}
		++size;
		hash ^= (int)x;
	}

	public void add(final long x,final long sum){
		if(root==null){
			root = new Node(null,x,sum);
			++uniqueSize;
		}
		else{
			Node par;
			Node now = root;
			boolean bool = true;
			do{
				par = now;
				if(x<now.value){
					now = now.left;
				}
				else if(x>now.value){
					now = now.right;
				}
				else{
					bool = false;
					now.count += sum;
					fix(now);
					break;
				}
			}
			while(now!=null);
			if(bool){
				++uniqueSize;
				if(x<par.value){
					par.left = new Node(par,x,sum);
				}
				else{
					par.right = new Node(par,x,sum);
				}
				fix(par);
			}
		}
		size += sum;
		if(sum%2==1){
			hash ^= (int)x;
		}
	}

	public long get(long index){
		if(root==null||size<=index){
			throw new NullPointerException();
		}
		Node now = root;
		while(true){
			assert now!=null;
			final long ls = now.left!=null?now.left.size:0;
			if(index<ls){
				now = now.left;
			}
			else if(ls+now.count<=index){
				index -= ls+now.count;
				now = now.right;
			}
			else{
				break;
			}
		}
		return now.value;
	}

	public boolean remove(final long x){
		final Node n = getNode(x);
		if(n==null){
			return false;
		}
		--size;
		hash ^= x;
		delete(n);
		return true;
	}

	public long remove(final long x,final long sum){
		final Node n = getNode(x);
		if(n==null){
			return 0;
		}
		final long ans = Math.min(sum,n.count);
		size -= ans;
		n.count -= ans-1;
		if(ans%2==1){
			hash ^= (int)x;
		}
		delete(n);
		return ans;
	}

	public long removeAll(final long x){
		final Node n = getNode(x);
		if(n==null){
			return 0;
		}
		size -= n.count;
		final long ans = n.count;
		if(n.count%2==1){
			hash ^= (int)x;
		}
		n.count = 0;
		delete(n);
		return ans;
	}

	private void delete(final Node node){
		if(node!=null){
			if(node.count>1){
				--node.count;
				fix(node);
				return;
			}
			if(node.left==null&&node.right==null){
				if(node.parent!=null){
					if(node.parent.left==node){
						node.parent.left = null;
					}
					else{
						node.parent.right = null;
					}
					fix(node.parent);
				}
				else{
					root = null;
				}
				node.parent = null;
				--uniqueSize;
			}
			else{
				if(node.left!=null&&node.right!=null){
					final Node rep = getFirstNode(node.right);
					node.value = rep.value;
					node.count = rep.count;
					rep.count = 0;
					delete(rep);
				}
				else{
					final Node rep = node.left!=null?node.left:node.right;
					rep.parent = node.parent;
					if(node.parent!=null){
						if(node.parent.left==node){
							node.parent.left = rep;
						}
						else{
							node.parent.right = rep;
						}
						fix(node.parent);
					}
					else{
						root = rep;
					}
					node.parent = null;
					--uniqueSize;
				}
			}
		}
	}

	private Node getNode(final long x){
		Node now = root;
		while(now!=null){
			if(x<now.value){
				now = now.left;
			}
			else if(x>now.value){
				now = now.right;
			}
			else{
				break;
			}
		}
		return now;
	}

	public long first(){
		if(root==null){
			throw new NullPointerException();
		}
		return getFirstNode(root).value;
	}

	private Node getFirstNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.left;
		}
		return par;
	}

	public long last(){
		if(root==null){
			throw new NullPointerException();
		}
		return getLastNode(root).value;
	}

	private Node getLastNode(Node node){
		assert node!=null;
		Node par = null;
		while(node!=null){
			par = node;
			node = par.right;
		}
		return par;
	}

	public boolean contains(final long x){
		if(root==null){
			return false;
		}
		return getNode(x)!=null;
	}

	public long sum(final long x){
		if(root==null){
			return 0;
		}
		final Node node = getNode(x);
		return node!=null?node.count:0;
	}

	public long pollFirst(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node min = getFirstNode(root);
		hash ^= (int)min.value;
		final long ans = min.value;
		delete(min);
		return ans;
	}

	public long pollLast(){
		if(root==null){
			throw new NullPointerException();
		}
		--size;
		final Node max = getLastNode(root);
		hash ^= (int)max.value;
		final long ans = max.value;
		delete(max);
		return ans;
	}

	public long ceiling(final long x){
		return ceiling(root,x);
	}

	private long ceiling(Node node,final long x){
		Node ans = new Node(null,x-1,0);
		while(node!=null){
			if(x>node.value){
				node = node.right;
			}
			else if(x<node.value){
				ans = node;
				node = node.left;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public long higher(final long x){
		return higher(root,x);
	}

	private long higher(Node node,final long x){
		Node ans = new Node(null,x-1,0);
		while(node!=null){
			if(x>=node.value){
				node = node.right;
			}
			else{
				ans = node;
				node = node.left;
			}
		}
		return ans.value;
	}

	public long floor(final long x){
		return floor(root,x);
	}

	private long floor(Node node,final long x){
		Node ans = new Node(null,x+1,0);
		while(node!=null){
			if(x<node.value){
				node = node.left;
			}
			else if(x>node.value){
				ans = node;
				node = node.right;
			}
			else{
				return x;
			}
		}
		return ans.value;
	}

	public long lower(final long x){
		return lower(root,x);
	}

	private long lower(Node node,final long x){
		Node ans = new Node(null,x+1,0);
		while(node!=null){
			if(x<=node.value){
				node = node.left;
			}
			else{
				ans = node;
				node = node.right;
			}
		}
		return ans.value;
	}

	public int size(){
		return uniqueSize;
	}

	public long sumSize(){
		return size;
	}

	public long[][] toArray(){
		final long[][] list = new long[uniqueSize][2];
		if(root!=null){
			int index = 0;
			ArrayDeque<Node> deq = new ArrayDeque<>(root.height<<1);
			deq.add(root);
			while(!deq.isEmpty()){
				final Node now = deq.pollLast();
				if(index==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						list[index][0] = now.value;
						list[index++][1] = now.count;
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list[index-1][0]<now.left.value){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					list[index][0] = now.value;
					list[index++][1] = now.count;
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	public long[] toOneArray(){
		final long[] list = new long[(int)size];
		if(root!=null){
			int index = 0;
			final ArrayDeque<Node> deq = new ArrayDeque<>();
			deq.add(root);
			while(!deq.isEmpty()){
				final Node now = deq.pollLast();
				if(index==0){
					if(now.left!=null){
						deq.add(now);
						deq.add(now.left);
					}
					else{
						for(int i = 0;i<now.count;++i){
							list[index++] = now.value;
						}
						if(now.right!=null){
							deq.add(now.right);
						}
					}
				}
				else if(now.left!=null&&list[index-1]<now.left.value){
					deq.add(now);
					deq.add(now.left);
				}
				else{
					for(int i = 0;i<now.count;++i){
						list[index++] = now.value;
					}
					if(now.right!=null){
						deq.add(now.right);
					}
				}
			}
		}
		return list;
	}

	@Override public String toString(){
		final long[] list = toOneArray();
		return Arrays.toString(list);
	}

	@Override public boolean equals(final Object o){
		if(o instanceof final TreeMultiInt tree){
			if(size!=tree.size()){
				return false;
			}
			final long[][] array1 = toArray();
			final long[][] array2 = tree.toArray();
			for(int i = 0;i<size;++i){
				if(array1[i][0]!=array2[i][0]||array1[i][1]!=array2[i][1]){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override public int hashCode(){
		return hash;
	}

	/*
	 * �ȉ��A���t�p���\�b�h
	 */
	private void fix(Node node){
		while(node!=null){
			final int lh = node.left==null?0:node.left.height;
			final int rh = node.right==null?0:node.right.height;
			if(lh-rh>1){
				assert node.left!=null;
				if(node.left.right!=null&&node.left.right.height==lh-1){
					rotateL(node.left);
					setStates(node.left);
				}
				rotateR(node);
			}
			else if(rh-lh>1){
				assert node.right!=null;
				if(node.right.left!=null&&node.right.left.height==rh-1){
					rotateR(node.right);
					setStates(node.right);
				}
				rotateL(node);
			}
			setStates(node);
			node = node.parent;
		}
	}

	private void rotateR(final Node node){
		final Node temp = node.left;
		node.left = temp.right;
		if(node.left!=null){
			node.left.parent = node;
		}
		temp.right = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void rotateL(final Node node){
		final Node temp = node.right;
		node.right = temp.left;
		if(node.right!=null){
			node.right.parent = node;
		}
		temp.left = node;
		temp.parent = node.parent;
		if(node.parent!=null){
			if(node.parent.left==node){
				node.parent.left = temp;
			}
			else{
				node.parent.right = temp;
			}
		}
		else{
			root = temp;
		}
		node.parent = temp;
		setStates(node);
	}

	private void setStates(final Node node){
		final int lh = node.left!=null?node.left.height:0;
		final int rh = node.right!=null?node.right.height:0;
		node.height = Math.max(lh,rh)+1;
		final long ls = node.left!=null?node.left.size:0;
		final long rs = node.right!=null?node.right.size:0;
		node.size = ls+rs+node.count;
	}
}

/**
 * SimpleScanner�N���X�ł��B
 * java.util.Scanner�N���X���͑����ł����A���v���p�ɓ������Ă��邽��
 * char�̒l���X�y�[�X�ȉ��̕����͑S�ċ�؂蕶���Ɣ��f���܂��B
 * �d�l��A�K�����͂̍Ō�ɂ͋�؂蕶�����܂߂ĉ������B
 */
final class SimpleScanner{
	private final int BUFF_SIZE = 1<<17;
	private final InputStream is;
	private final byte[] buff;
	private int point, length;

	/**
	 * ������InputStream������SimpleScanner���\�z���܂��B
	 *
	 * @param is ���ƂȂ�InputStream
	 */
	public SimpleScanner(final InputStream is){
		this.is = is;
		buff = new byte[BUFF_SIZE];
		point = length = 0;
	}

	/**
	 * �o�b�t�@�ɍēǂݍ��݂��܂��B
	 * ����������͂������܂őҋ@���܂��B
	 */
	private void reload(){
		do{
			try{
				length = is.read(buff,point = 0,BUFF_SIZE);
			}catch(final IOException e){
				e.printStackTrace();
				System.exit(1);
			}
		}
		while(length==-1);
	}

	/**
	 * �o�b�t�@����ꕶ���ǂݍ��݂܂��B
	 *
	 * @return �ǂݍ��񂾕�����\��byte
	 */
	private byte read(){
		if(point==length){
			reload();
		}
		return buff[point++];
	}

	/**
	 * �o�b�t�@���當����\��byte���擾���܂��B
	 *
	 * @return �܂��ǂ�łȂ����ōŏ��ɓ��͂��ꂽ������\��byte
	 */
	public byte nextByte(){
		byte c = read();
		while(c<=' '){
			c = read();
		}
		return c;
	}

	/**
	 * �o�b�t�@����int��ǂݍ��݂܂��B
	 * �I�[�o�[�t���[�͌��m�ł��Ȃ��̂Œ��ӂ��ĉ������B
	 *
	 * @return ��؂蕶���܂ł̕������int�Ɖ��߂������̒l
	 */
	public int nextInt(){
		int ans = 0;
		byte c = nextByte();
		final boolean negate = c=='-';
		if(!MathFunction.rangeCheckClose(c,'0','9')){
			c = read();
		}
		while(MathFunction.rangeCheckClose(c,'0','9')){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate?-ans:ans;
	}

	/**
	 * �o�b�t�@����long��ǂݍ��݂܂��B
	 * �I�[�o�[�t���[�͌��m�ł��Ȃ��̂Œ��ӂ��ĉ������B
	 *
	 * @return ��؂蕶���܂ł̕������long�Ɖ��߂������̒l
	 */
	public long nextLong(){
		long ans = 0;
		byte c = nextByte();
		final boolean negate = c=='-';
		if(!MathFunction.rangeCheckClose(c,'0','9')){
			c = read();
		}
		while(MathFunction.rangeCheckClose(c,'0','9')){
			ans = ans*10L+c-'0';
			c = read();
		}
		return negate?-ans:ans;
	}

	/**
	 * �o�b�t�@����char��ǂݍ��݂܂��B
	 *
	 * @return ��؂蕶���𖳎������A��ԍŏ��Ɏ擾���ꂽ������\��char
	 */
	public char nextChar(){
		return (char)nextByte();
	}

	/**
	 * �o�b�t�@����String��ǂݍ��݂܂��B
	 *
	 * @return ��؂蕶���܂ł̕������\��String
	 */
	public String next(){
		final StringBuilder ans = new StringBuilder();
		byte c = nextByte();
		while(c>' '){
			ans.append((char)c);
			c = read();
		}
		return ans.toString();
	}

	/**
	 * �o�b�t�@����BigInteger��ǂݍ��݂܂��B
	 *
	 * @return ��؂蕶���܂ł̕�����𐮐��Ɖ��߂�������BigInteger
	 */
	public BigInteger nextBigInteger(){
		return new BigInteger(next());
	}

	/**
	 * �o�b�t�@����byte[]��ǂݍ��݂܂��B
	 *
	 * @param n �擾�����
	 *
	 * @return ��؂蕶���𖳎�����n�������擾�������ʂ�\��byte[]
	 */
	public byte[] nextByte(final int n){
		final byte[] ans = new byte[n];
		for(int i = 0;i<n;++i){
			ans[i] = nextByte();
		}
		return ans;
	}

	/**
	 * �o�b�t�@����int[]��ǂݍ��݂܂��B
	 *
	 * @param n �擾�����
	 *
	 * @return ��؂蕶���ŋ�؂���n��int���擾�������ʂ�\��int[]
	 */
	public int[] nextInt(final int n){
		final int[] ans = new int[n];
		for(int i = 0;i<n;++i){
			ans[i] = nextInt();
		}
		return ans;
	}

	/**
	 * �o�b�t�@����long[]��ǂݍ��݂܂��B
	 *
	 * @param n �擾�����
	 *
	 * @return ��؂蕶���ŋ�؂���n��long���擾�������ʂ�\��long[]
	 */
	public long[] nextLong(final int n){
		final long[] ans = new long[n];
		for(int i = 0;i<n;++i){
			ans[i] = nextLong();
		}
		return ans;
	}

	/**
	 * �o�b�t�@����String[]��ǂݍ��݂܂��B
	 *
	 * @param n �擾�����
	 *
	 * @return ��؂蕶���ŋ�؂���n��String���擾�������ʂ�\��String[]
	 */
	public String[] next(final int n){
		final String[] ans = new String[n];
		for(int i = 0;i<n;++i){
			ans[i] = next();
		}
		return ans;
	}

	public byte[][] nextByte(final int n,final int m){
		final byte[][] ans = new byte[n][];
		for(int i = 0;i<n;++i){
			ans[i] = nextByte(m);
		}
		return ans;
	}

	public int[][] nextInt(final int n,final int m){
		final int[][] ans = new int[n][];
		for(int i = 0;i<n;++i){
			ans[i] = nextInt(m);
		}
		return ans;
	}

	public long[][] nextLong(final int n,final int m){
		final long[][] ans = new long[n][];
		for(int i = 0;i<n;++i){
			ans[i] = nextLong(m);
		}
		return ans;
	}

	public String[][] next(final int n,final int m){
		final String[][] ans = new String[n][];
		for(int i = 0;i<n;++i){
			ans[i] = next(m);
		}
		return ans;
	}

	public char[] nextCharArray(){
		return next().toCharArray();
	}

	public char[][] nextCharArray(final int n){
		final char[][] ans = new char[n][];
		for(int i = 0;i<n;++i){
			ans[i] = nextCharArray();
		}
		return ans;
	}

	public int[][] nextGraph(final int N,final int M){
		if(M==0){
			return new int[N+1][0];
		}
		final int[][] ans = new int[N+1][];
		final int[] count = new int[N+1];
		final int[][] path = nextInt(M,2);
		for(final int[] temp: path){
			++count[temp[0]];
			++count[temp[1]];
		}
		for(int i = 1;i<=N;++i){
			ans[i] = new int[count[i]];
		}
		for(final int[] temp: path){
			ans[temp[0]][--count[temp[0]]] = temp[1];
			ans[temp[1]][--count[temp[1]]] = temp[0];
		}
		ans[0] = new int[0];
		return ans;
	}

	public Point nextPoint(){
		return new Point(nextInt(),nextInt());
	}

	public Point[] nextPoint(final int n){
		final Point[] ans = new Point[n];
		for(int i = 0;i<n;++i){
			ans[i] = nextPoint();
		}
		return ans;
	}

	public void close(){
		try{
			is.close();
		}catch(final IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}

/**
 * SimpleWriter�N���X�p��SimpleOutputStream�N���X�ł��B
 * ������Ƃ��������ł��B
 */
final class SimpleOutputStream extends FilterOutputStream{
	private final byte[] buf;
	private int count;

	public SimpleOutputStream(final OutputStream out){
		this(out,1<<17);
	}

	public SimpleOutputStream(final OutputStream out,final int size){
		super(out);
		if(size<=0){
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		buf = new byte[size];
	}

	private void flushBuffer() throws IOException{
		if(count>0){
			out.write(buf,0,count);
			count = 0;
		}
	}

	public void write(final int b) throws IOException{
		if(count>=buf.length){
			flushBuffer();
		}
		buf[count++] = (byte)b;
	}

	public void write(final byte[] b,final int off,final int len) throws IOException{
		if(len>=buf.length){
			flushBuffer();
			out.write(b,off,len);
			return;
		}
		if(len>buf.length-count){
			flushBuffer();
		}
		System.arraycopy(b,off,buf,count,len);
		count += len;
	}

	public void flush() throws IOException{
		flushBuffer();
		out.flush();
	}
}

/**
 * SimpleWriter�N���X�ł��B
 * �������������o�͂�񋟂��܂��B
 */
final class SimpleWriter implements Appendable, Closeable, Flushable, AutoCloseable{
	private Writer out;
	private final boolean autoFlush;
	private boolean trouble = false;
	private Formatter formatter;
	private PrintStream psOut = null;

	public SimpleWriter(final Writer out){
		this(out,false);
	}

	public SimpleWriter(final Writer out,final boolean autoFlush){
		this.out = out;
		this.autoFlush = autoFlush;
	}

	public SimpleWriter(final OutputStream out){
		this(out,false);
	}

	public SimpleWriter(final OutputStream out,final boolean autoFlush){
		this(out,autoFlush,Charset.defaultCharset());
	}

	public SimpleWriter(final OutputStream out,final boolean autoFlush,final Charset charset){
		this(new BufferedWriter(new OutputStreamWriter(new SimpleOutputStream(out),charset)),autoFlush);
		if(out instanceof PrintStream){
			psOut = (PrintStream)out;
		}
	}

	private void ensureOpen() throws IOException{
		if(out==null){
			throw new IOException("Stream closed");
		}
	}

	public void flush(){
		try{
			ensureOpen();
			out.flush();
		}catch(IOException x){
			trouble = true;
		}
	}

	public void close(){
		try{
			if(out==null){
				return;
			}
			out.close();
			out = null;
		}catch(IOException x){
			trouble = true;
		}
	}

	public boolean checkError(){
		if(out!=null){
			flush();
		}
		else if(psOut!=null){
			return psOut.checkError();
		}
		return trouble;
	}

	public void write(final int c){
		try{
			ensureOpen();
			out.write(c);
		}catch(InterruptedIOException x){
			Thread.currentThread().interrupt();
		}catch(IOException x){
			trouble = true;
		}
	}

	public void write(final char[] buf,final int off,final int len){
		try{
			ensureOpen();
			out.write(buf,off,len);
		}catch(InterruptedIOException x){
			Thread.currentThread().interrupt();
		}catch(IOException x){
			trouble = true;
		}
	}

	public void write(final char[] buf){
		write(buf,0,buf.length);
	}

	public void write(final String s,final int off,final int len){
		try{
			ensureOpen();
			out.write(s,off,len);
		}catch(InterruptedIOException x){
			Thread.currentThread().interrupt();
		}catch(IOException x){
			trouble = true;
		}
	}

	public void write(final String s){
		write(s,0,s.length());
	}

	private void newLine(){
		try{
			ensureOpen();
			out.write(System.lineSeparator());
			if(autoFlush){
				out.flush();
			}
		}catch(InterruptedIOException x){
			Thread.currentThread().interrupt();
		}catch(IOException x){
			trouble = true;
		}
	}

	public void print(final boolean b){
		write(b?"true":"false");
	}

	public void print(final char c){
		write(c);
	}

	public void print(final int i){
		write(String.valueOf(i));
	}

	public void print(final long l){
		write(String.valueOf(l));
	}

	public void print(final float f){
		write(String.valueOf(f));
	}

	public void print(final double d){
		write(String.valueOf(d));
	}

	public void print(final char[] s){
		write(s);
	}

	public void print(final String s){
		write(s);
	}

	public void print(final Object obj){
		write(obj.toString());
	}

	public void println(){
		newLine();
	}

	public void println(final boolean x){
		print(x);
		println();
	}

	public void println(final char x){
		print(x);
		println();
	}

	public void println(final int x){
		print(x);
		println();
	}

	public void println(final long x){
		print(x);
		println();
	}

	public void println(final float x){
		print(x);
		println();
	}

	public void println(final double x){
		print(x);
		println();
	}

	public void println(final char[] x){
		print(x);
		println();
	}

	public void println(final String x){
		print(x);
		println();
	}

	public void println(final Object x){
		print(x.toString());
		println();
	}

	public SimpleWriter printf(final String format,final Object... args){
		return format(format,args);
	}

	public SimpleWriter printf(final Locale l,final String format,final Object... args){
		return format(l,format,args);
	}

	public SimpleWriter format(final String format,final Object... args){
		try{
			ensureOpen();
			if((formatter==null)||(formatter.locale()!=Locale.getDefault())){
				formatter = new Formatter(this);
			}
			formatter.format(Locale.getDefault(),format,args);
			if(autoFlush){
				out.flush();
			}
		}catch(InterruptedIOException x){
			Thread.currentThread().interrupt();
		}catch(IOException x){
			trouble = true;
		}
		return this;
	}

	public SimpleWriter format(final Locale l,final String format,final Object... args){
		try{
			ensureOpen();
			if((formatter==null)||(formatter.locale()!=l)){
				formatter = new Formatter(this,l);
			}
			formatter.format(l,format,args);
			if(autoFlush){
				out.flush();
			}
		}catch(InterruptedIOException x){
			Thread.currentThread().interrupt();
		}catch(IOException x){
			trouble = true;
		}
		return this;
	}

	public SimpleWriter append(final CharSequence csq){
		write(String.valueOf(csq));
		return this;
	}

	public SimpleWriter append(CharSequence csq,final int start,final int end){
		if(csq==null){
			csq = "null";
		}
		return append(csq.subSequence(start,end));
	}

	public SimpleWriter append(final char c){
		write(c);
		return this;
	}

	public void print(final int[] array){
		print(array,' ');
	}

	public void print(final int[] array,final String str){
		if(array.length==0)
			return;
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(str);
			print(array[i]);
		}
	}

	public void print(final int[] array,final char c){
		if(array.length==0)
			return;
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(c);
			print(array[i]);
		}
	}

	public void print(final int[][] array){
		print(array,' ');
	}

	public void print(final int[][] arrays,final String str){
		if(arrays.length==0)
			return;
		print(arrays[0],str);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],str);
		}
	}

	public void print(final int[][] arrays,final char c){
		if(arrays.length==0)
			return;
		print(arrays[0],c);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],c);
		}
	}

	public void println(final int[] array){
		print(array,' ');
		println();
	}

	public void println(final int[] array,final String str){
		print(array,str);
		println();
	}

	public void println(final int[] array,final char c){
		print(array,c);
		println();
	}

	public void println(final int[][] array){
		print(array,' ');
		println();
	}

	public void println(final int[][] arrays,final String str){
		print(arrays,str);
		println();
	}

	public void println(final int[][] arrays,final char c){
		print(arrays,c);
		println();
	}

	public void print(final long[] array){
		print(array,' ');
	}

	public void print(final long[] array,final String str){
		if(array.length==0)
			return;
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(str);
			print(array[i]);
		}
	}

	public void print(final long[] array,final char c){
		if(array.length==0)
			return;
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(c);
			print(array[i]);
		}
	}

	public void print(final long[][] array){
		print(array,' ');
	}

	public void print(final long[][] arrays,final String str){
		if(arrays.length==0)
			return;
		print(arrays[0],str);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],str);
		}
	}

	public void print(final long[][] arrays,final char c){
		if(arrays.length==0)
			return;
		print(arrays[0],c);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],c);
		}
	}

	public void println(final long[] array){
		println(array,' ');
	}

	public void println(final long[] array,final String str){
		print(array,str);
		println();
	}

	public void println(final long[] array,final char c){
		print(array,c);
		println();
	}

	public void println(final long[][] array){
		println(array,' ');
	}

	public void println(final long[][] arrays,final String str){
		print(arrays,str);
		println();
	}

	public void println(final long[][] arrays,final char c){
		print(arrays,c);
		println();
	}

	public void print(final double[] array){
		print(array,' ');
	}

	public void print(final double[] array,final String str){
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(str);
			print(array[i]);
		}
	}

	public void print(final double[] array,final char c){
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(c);
			print(array[i]);
		}
	}

	public void print(final double[][] array){
		print(array,' ');
	}

	public void print(final double[][] arrays,final String str){
		print(arrays[0],str);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],str);
		}
	}

	public void print(final double[][] arrays,final char c){
		print(arrays[0],c);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],c);
		}
	}

	public void println(final double[] array){
		println(array,' ');
	}

	public void println(final double[] array,final String str){
		print(array,str);
		println();
	}

	public void println(final double[] array,final char c){
		print(array,c);
		println();
	}

	public void println(final double[][] array){
		println(array,' ');
	}

	public void println(final double[][] arrays,final String str){
		print(arrays,str);
		println();
	}

	public void println(final double[][] arrays,final char c){
		print(arrays,c);
		println();
	}

	public void println(final char[] cs,final String str){
		print(cs[0]);
		for(int i = 1;i<cs.length;++i){
			print(str);
			print(cs[i]);
		}
		println();
	}

	public void println(final char[] cs,final char c){
		print(cs[0]);
		for(int i = 1;i<cs.length;++i){
			print(c);
			print(cs[i]);
		}
		println();
	}

	public void println(final char[][] cs){
		for(final char[] c: cs){
			println(c);
		}
	}

	public void println(final char[][] cs,final String str){
		for(final char[] c: cs){
			println(c,str);
		}
	}

	public void println(final char[][] cs,final char c){
		for(final char[] cc: cs){
			println(cc,c);
		}
	}

	public <E> void print(final E[] array){
		print(array,' ');
	}

	public <E> void print(final E[] array,final String str){
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(str);
			print(array[i]);
		}
	}

	public <E> void print(final E[] array,final char c){
		print(array[0]);
		for(int i = 1;i<array.length;++i){
			print(c);
			print(array[i]);
		}
	}

	public <E> void print(final E[][] arrays){
		print(arrays,' ');
	}

	public <E> void print(final E[][] arrays,final String str){
		print(arrays[0],str);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],str);
		}
	}

	public <E> void print(final E[][] arrays,final char c){
		print(arrays[0],c);
		for(int i = 1;i<arrays.length;++i){
			println();
			print(arrays[i],c);
		}
	}

	public <E> void println(final E[] array){
		println(array,' ');
	}

	public <E> void println(final E[] array,final String str){
		print(array,str);
		println();
	}

	public <E> void println(final E[] array,final char c){
		print(array,c);
		println();
	}

	public <E> void println(final E[][] arrays){
		println(arrays,' ');
	}

	public <E> void println(final E[][] arrays,final String str){
		print(arrays,str);
		println();
	}

	public <E> void println(final E[][] arrays,final char c){
		print(arrays,c);
		println();
	}

	public <E> void println(final List<E> list){
		println(list,' ');
	}

	public <E> void println(final List<E> list,final String str){
		if(list.size()>0){
			print(list.get(0));
			for(int i = 1;i<list.size();++i){
				print(str);
				print(list.get(i));
			}
		}
		println();
	}

	public <E> void println(final List<E> list,final char c){
		if(list.size()>0){
			print(list.get(0));
			for(int i = 1;i<list.size();++i){
				print(c);
				print(list.get(i));
			}
		}
		println();
	}
}
