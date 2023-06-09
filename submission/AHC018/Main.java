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

	//This problem is interactive.
	private static final boolean autoFlush = true;
	private static final SimpleScanner sc = new SimpleScanner( System.in );
	private static final SimplePrinter out = new SimplePrinter( System.out, autoFlush );

	private static int N,W,K,C;
	private static Point[] waterSpot,houseSpot;
	private static long startTime;

	private static final Random rm = new Random(0);
	private static final int odds = 3;

	public static void main ( String[] args ) {

		startTime = System.nanoTime();

		inputInfo();

		solver_ver0();
	}

	private static void solver_ver1 () {

		/*
			作成予定日:2/24
			ver0と一緒。
			移動方向を最短ルートではなく、周辺に微小掘削をすることで
			可能な限り楽をする方向に進むように。
			まだ作ってないからわからないけど、めんどくさそう。
		*/
		PriorityQueue<Pair<Point,Point>> queue = new PriorityQueue<Pair<Point,Point>>(
			(p1,p2) -> {
				final int dist1 =
					Math.abs(p1.getKey().x-p1.getValue().x)+Math.abs(p1.getKey().y-p1.getValue().y);
				final int dist2 =
					Math.abs(p2.getKey().x-p2.getValue().x)+Math.abs(p2.getKey().y-p2.getValue().y);
				return Integer.compare(dist1,dist2);
			}
		);
		for(int i=0;i<W;i++){
			for(int j=i+1;j<W;j++){
				queue.add(new Pair<>(waterSpot[i],waterSpot[j]));
			}
		}
		for(int i=0;i<K;i++){
			for(int j=i+1;j<K;j++){
				queue.add(new Pair<>(houseSpot[i],houseSpot[j]));
			}
		}
		for(int i=0;i<W;i++){
			for(int j=0;j<K;j++){
				queue.add(new Pair<>(waterSpot[i],houseSpot[j]));
			}
		}
		UnionFind uf = new UnionFind(K+W);
		boolean[][] isBroken = new boolean[N][N];
		while(true){
			Pair<Point,Point> pair = queue.poll();
			Point p1 = pair.getKey();
			Point p2 = pair.getValue();
			int index1 = -1,index2 = -1;
			for(int i=0;i<W;i++){
				if(p1.equals(waterSpot[i]))
					index1 = i;
				if(p2.equals(waterSpot[i]))
					index2 = i;
			}
			for(int i=0;i<K;i++){
				if(p1.equals(houseSpot[i]))
					index1 = i+W;
				if(p2.equals(houseSpot[i]))
					index2 = i+W;
			}
			if(uf.unite(index1,index2)){
				int x = p1.x;
				int y = p1.y;
				if(!isBroken[x][y]){
					breakBedrock(x,y);
					isBroken[x][y] = true;
				}
			}
		}
	}

	private static int lazyDamage = 100;
	private static int parameter;

	private static void setParameter () {

		//初期値
		parameter = 45;

		final int sub = 5;
		switch(C){
		case 128:parameter-=sub;
		case 64:parameter-=sub;
		case 32:parameter-=sub;
		case 16:parameter-=sub;
		case 8:parameter-=sub;
		case 4:parameter-=sub;
		case 2:parameter-=sub;
		case 1:parameter-=sub;
		}
	}

	private static void breakBedrock2 ( final int x, final int y ) {

		try{
			outputInfo(x,y,lazyDamage);
			int result = getResult();
			if(result==0){
				Loop:while(true){
					int damage = Math.max(1,lazyDamage/parameter);
					lazyDamage = Math.min(5000,lazyDamage+damage);
					outputInfo(x,y,damage);
					result = getResult();
					switch(result){
					case 0:
						continue Loop;
					case 1:
						break Loop;
					case 2:
						System.exit(0);
					default:
						throw new OutputException("("+x+","+y+"),P="+damage);
					}
				}
			}
			else if(result==1){
				lazyDamage -= lazyDamage/10;
				if(lazyDamage==0)
					lazyDamage = 1;
			}
			else if(result==2){
				System.exit(0);
			}
			else{
				throw new OutputException("("+x+","+y+"),P="+lazyDamage);
			}
		}
		catch(OutputException o){
			o.printStackTrace();
			System.exit(1);
		}
		catch(Exception e){
			System.err.println("This is unexpected exception.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void solver_ver0 () {

		/*
			作成日:2/22
			とりあえず、水源を含めた最小全域木でも構成してみるか。
			距離はまぁ、マンハッタン距離でも良いでしょ。というか許してくれ。
		*/
		PriorityQueue<Pair<Point,Point>> queue = new PriorityQueue<Pair<Point,Point>>(
			(p1,p2) -> {
				final int dist1 =
					Math.abs(p1.getKey().x-p1.getValue().x)+Math.abs(p1.getKey().y-p1.getValue().y);
				final int dist2 =
					Math.abs(p2.getKey().x-p2.getValue().x)+Math.abs(p2.getKey().y-p2.getValue().y);
				return Integer.compare(dist1,dist2);
			}
		);
		for(int i=0;i<W;i++){
			for(int j=i+1;j<W;j++){
				queue.add(new Pair<>(waterSpot[i],waterSpot[j]));
			}
		}
		for(int i=0;i<K;i++){
			for(int j=i+1;j<K;j++){
				queue.add(new Pair<>(houseSpot[i],houseSpot[j]));
			}
		}
		for(int i=0;i<W;i++){
			for(int j=0;j<K;j++){
				queue.add(new Pair<>(waterSpot[i],houseSpot[j]));
			}
		}
		setParameter();
		UnionFind uf = new UnionFind(K+W);
		boolean[][] isBroken = new boolean[N][N];
		while(true){
			Pair<Point,Point> pair = queue.poll();
			Point p1 = pair.getKey();
			Point p2 = pair.getValue();
			int index1 = -1,index2 = -1;
			for(int i=0;i<W;i++){
				if(p1.equals(waterSpot[i]))
					index1 = i;
				if(p2.equals(waterSpot[i]))
					index2 = i;
			}
			for(int i=0;i<K;i++){
				if(p1.equals(houseSpot[i]))
					index1 = i+W;
				if(p2.equals(houseSpot[i]))
					index2 = i+W;
			}
			if(uf.unite(index1,index2)){
				int[][] route = getRoute(p1,p2);
				int x = p1.x;
				int y = p1.y;
				if(!isBroken[x][y]){
					breakBedrock2(x,y);
					isBroken[x][y] = true;
				}
				for(int[] delta:route){
					if(delta[0]==0)
						x += delta[1];
					else
						y += delta[1];
					if(!isBroken[x][y]){
						breakBedrock2(x,y);
						isBroken[x][y] = true;
					}
				}
			}
		}
	}

	private static int[][] getRoute (final Point from, final Point to ) {

		/*
			どういうルートが一番なのかわからないけど、
			とりあえず最短でランダムに進んどきゃ良いでしょとか思ってる。
			というか、他に良い案が思いつかない。
		*/
		int dx = to.x-from.x;
		final int dy = to.y-from.y;
		final int subX = Integer.signum(dx);
		final int subY = Integer.signum(dy);
		int len = Math.abs(dx)+Math.abs(dy);
		final int[][] ans = new int[len][2];
		while(0<len){
			final int num = rm.nextInt(len);
			if(num<Math.abs(dx)){
				ans[--len][0] = 0;
				ans[len][1] = subX;
				dx -= subX;
			}else{
				ans[--len][0] = 1;
				ans[len][1] = subY;
			}
		}
		return ans;
	}

	private static void breakBedrock ( final int x, final int y ) {

		try{
			int expectedHP = 5000;
			int count = 0;
			Loop:while(true){
				int standardPower = powerfunction(expectedHP);
				if(rm.nextInt(odds)==0)
					standardPower = Math.min(expectedHP,standardPower<<count);
				final int criticalDamage = rm.nextInt(Math.max(1,expectedHP>>7));
				final int allDamage = Math.max(10,standardPower+criticalDamage);
				outputInfo(x,y,allDamage);
				expectedHP = Math.max(1,expectedHP-allDamage);
				final int result = getResult();
				count++;
				switch(result){
				case 0:
					continue Loop;
				case 1:
					break Loop;
				case 2:
					System.exit(0);
				default:
					throw new OutputException("("+x+","+y+"),P="+allDamage);
				}
			}
		}
		catch(OutputException o){
			o.printStackTrace();
			System.exit(1);
		}
		catch(Exception e){
			System.err.println("This is unexpected exception.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static int powerfunction () {

		/*
			C=128のときはなるべく少ない回数でやりたいから
			基礎パワーSPは(5000+SP-1)/SP*C<SPになるくらいが良いかなぁとか
			思ってるのよね。適当に今思いついた式だけど()。
			で、あとはそれをSPについて解いた式で当てはめれば良さそうね。
			もしかしてここの計算でオーバーフローしないためにCがあの制約…？
			まさかな…。
		*/
		final int num = C+(int)Math.round(Math.sqrt(C*C+4*4999*C));
		return (num+1)>>1;
	}

	private static int powerfunction ( int c ) {

		/*
			今現在の推定最高残量を元に計算するように修正
		*/
		final int num = C+(int)Math.round(Math.sqrt(C*C+4*c/10*C));
		return Math.max(1,(num+1)>>1);
	}

	private static void inputInfo () {

		//土地のサイズ(N*N)。N=200で固定らしい
		N = sc.nextInt();

		//水源の数。1個から4個らしい。少なめ…？
		W = sc.nextInt();

		//家の数。1個から10個らしい。思ったより少なめ…？
		K = sc.nextInt();

		//基礎体力消費量。掘削するたびに消費するからこれを抑える方向で。2^k(0<=k<=7)らしい。
		//なんで？
		C = sc.nextInt();

		//水源の座標。
		waterSpot = sc.nextPoint( W );

		//家の座標
		houseSpot = sc.nextPoint( K );
	}

	private static int getResult () {

		/*
			破壊出来なかった	->0
		    破壊できた
			->まだ完成してない	->1
			->完成！		->2
			不正な値が出力された	->-1
		*/
		return sc.nextInt();
	}

	private static void outputInfo ( final int y,final int x,final int P ) {

		//(y,x)に対してパワーPで掘削(1<=P<=5000)
		out.println( y + " " + x + " " + P );
	}
}

final class OutputException extends Exception{
	public OutputException(String message){
		super(message);
	}
}

/*////////////////////////////////////////////////
	* My Library *

	@author viral
*/////////////////////////////////////////////////
final class UnionFind {
	private final int[] par, rank;

	public UnionFind ( int N ) {
		par = new int[N];
		rank = new int[N];
		Arrays.fill( par, -1 );
	}

	public int root ( int x ) {
		if ( par[x] == -1 ) {
			return x;
		}
		else {
			return par[x] = root( par[x] );
		}
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
		return true;
	}
}

class Pair<K,V> {
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

	public int[] nextInt ( int n ) {
		int[] ans = new int[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextInt();
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

final class SimplePrinter extends PrintWriter {
	public SimplePrinter ( PrintStream os ) {
		super( os );
	}

	public SimplePrinter ( PrintStream os, boolean bool ) {
		super( os, bool );
	}
}
