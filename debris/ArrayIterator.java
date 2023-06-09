import java.util.Iterator;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.function.IntFunction;
final class Converter{
	private Converter(){}
	public static int[][] shiftMatrix(final int[]... source){
		return setAll(new int[source[0].length][],i->setAll(new int[source.length],j->source[j][i]));
	}
	private static  int[][] setAll(int[][] array,IntFunction<int[]> func){
		for(int i=0;i<array.length;i++)
			array[i] = func.apply(i);
		return array;
	}
	private static int[] setAll(int[] array,IntUnaryOperator func){
		for(int i=0;i<array.length;i++)
			array[i] = func.applyAsInt(i);
		return array;
	}
	public static int[][] unite(final int[]... source){
		return source;
	}
	public static Converter.OfByte of(final byte[]... source){
		return new Converter.OfByte(source);
	}
	public static Converter.OfShort of(final short[]... source){
		return new Converter.OfShort(source);
	}
	public static Converter.OfInt of(final int[]... source){
		return new Converter.OfInt(source);
	}
	public static Converter.OfLong of(final long[]... source){
		return new Converter.OfLong(source);
	}
	public static Converter.OfFloat of(final float[]... source){
		return new Converter.OfFloat(source);
	}
	public static Converter.OfDouble of(final double[]... source){
		return new Converter.OfDouble(source);
	}
	public static Converter.OfBoolean of(final boolean[]... source){
		return new Converter.OfBoolean(source);
	}
	public static Converter.OfChar of(final char[]... source){
		return new Converter.OfChar(source);
	}
	public static <E> Converter.OfGenerics<E> of(final E[] source1,final E[] source2){
		if(source1.length!=source2.length)
			throw new IllegalArgumentException();
		return new Converter.OfGenerics<>(source1,source2);
	}
	static class OfByte implements Iterator<byte[]>,Iterable<byte[]>{
		private final byte[][] iterator;
		private int index;
		public OfByte(final byte[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public byte[] next(){
			final byte[] ans = new byte[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<byte[]> iterator(){
			return this;
		}
	}
	static class OfShort implements Iterator<short[]>,Iterable<short[]>{
		private final short[][] iterator;
		private int index;
		public OfShort(final short[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public short[] next(){
			final short[] ans = new short[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<short[]> iterator(){
			return this;
		}
	}
	static class OfInt implements Iterator<int[]>,Iterable<int[]>{
		private final int[][] iterator;
		private int index;
		public OfInt(final int[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public int[] next(){
			final int[] ans = new int[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<int[]> iterator(){
			return this;
		}
	}
	static class OfLong implements Iterator<long[]>,Iterable<long[]>{
		private final long[][] iterator;
		private int index;
		public OfLong(final long[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public long[] next(){
			final long[] ans = new long[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<long[]> iterator(){
			return this;
		}
	}
	static class OfFloat implements Iterator<float[]>,Iterable<float[]>{
		private final float[][] iterator;
		private int index;
		public OfFloat(final float[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public float[] next(){
			final float[] ans = new float[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<float[]> iterator(){
			return this;
		}
	}
	static class OfDouble implements Iterator<double[]>,Iterable<double[]>{
		private final double[][] iterator;
		private int index;
		public OfDouble(final double[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public double[] next(){
			final double[] ans = new double[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<double[]> iterator(){
			return this;
		}
	}
	static class OfBoolean implements Iterator<boolean[]>,Iterable<boolean[]>{
		private final boolean[][] iterator;
		private int index;
		public OfBoolean(final boolean[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public boolean[] next(){
			final boolean[] ans = new boolean[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<boolean[]> iterator(){
			return this;
		}
	}
	static class OfChar implements Iterator<char[]>,Iterable<char[]>{
		private final char[][] iterator;
		private int index;
		public OfChar(final char[]... source){
			iterator = source;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator[0].length;
		}
		public char[] next(){
			final char[] ans = new char[iterator.length];
			for(int i=0;i<iterator.length;i++)
				ans[i] = iterator[i][index];
			++index;
			return ans;
		}
		public Iterator<char[]> iterator(){
			return this;
		}
	}
	static class OfGenerics<E> implements Iterator<Pair<E>>,Iterable<Pair<E>>{
		private final E[] iterator1,iterator2;
		private int index;
		public OfGenerics(final E[] source1,final E[] source2){
			iterator1 = source1;
			iterator2 = source2;
			index = 0;
		}
		public boolean hasNext(){
			return index<iterator1.length;
		}
		public Pair<E> next(){
			final Pair<E> ans = new Pair<>(iterator1[index],iterator2[index]);
			++index;
			return ans;
		}
		public Iterator<Pair<E>> iterator(){
			return this;
		}
	}
	static class Pair<E>{
		public E first,second;
		public Pair(E v1,E v2){
			first = v1;
			second = v2;
		}
		@Override
		public String toString(){
			StringBuilder ans = new StringBuilder();
			ans.append('[');
			ans.append(first.toString());
			ans.append(',');
			ans.append(' ');
			ans.append(second.toString());
			ans.append(']');
			return ans.toString();
		}
	}
}
class Main{
	public static void main(String[] args){
		final int size = 20_000_000;
		final int[] a = new int[size];
		for(int i=0;i<size;i++)
			a[i] = 2*i;
		final int[] b = new int[size];
		for(int i=0;i<size;i++)
			b[i] = 2*i+1;
		for(int i=0;i<10;i++){
			long first = System.currentTimeMillis();
			int[][] array = Converter.shiftMatrix(a,b);
			long second = System.currentTimeMillis();
			System.out.println(second-first+"ms");
			System.gc();
		}
	}
}
