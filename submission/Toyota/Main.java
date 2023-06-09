import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.awt.Point;
import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

final class Main {

	private static final boolean autoFlush = false;
	private static final SimpleScanner sc = new SimpleScanner( System.in );
	private static final SimplePrinter out = new SimplePrinter( System.out, autoFlush );

	private static int M, W, H, B, D;
	private static Baggage[] box;
	private final static ArrayList<int[]> ans = new ArrayList<>();

	public static void main ( String[] args ) {

		input();

		Arrays.sort( box, ( b1, b2 ) -> {
			if ( b1.stackable == b2.stackable ) {
				int min1 = Math.min( b1.height, Math.min( b1.width, b1.depth ) );
				int max1 = Math.max( b1.height, Math.max( b1.width, b1.depth ) );
				int min2 = Math.min( b2.height, Math.min( b2.width, b2.depth ) );
				int max2 = Math.max( b2.height, Math.max( b2.width, b2.depth ) );
				return Integer.compare( min1 * max1, min2 * max2 );
			}
			if ( b1.stackable ) {
				return -1;
			}
			else {
				return 1;
			}
		} );
		solver_ver1();

		output();
	}

	private static void solver_ver1 () {
		/* メモ
		 *
		 * これどうやって解けば良いんだろ…
		 * 普通に端っこに載せたいけど、柱がかなり厄介
		 * 柱分内側に寄せて考えても良さそうだが、その分得点は低くなるよねぇ
		 * ま、とりあえずは解を出すところからか
		 */
		RedBlackTree set = new RedBlackTree();
		for ( int i = 0; i < M; i++ ) {
			set.add( i );
		}
		SegmentTree2D<MaxNum> map = new SegmentTree2D<>( H, W, new MaxNum( 0, 1 ) ) {
			MaxNum function ( MaxNum mn1, MaxNum mn2 ) {
				int num = Math.max( mn1.num, mn2.num );
				int sum = num == mn1.num ? mn1.count : 0;
				sum += num == mn2.num ? mn2.count : 0;
				return new MaxNum( num, sum );
			}
		};
		MaxNum def = new MaxNum( 0, 1 );
		for ( int i = B; i <= H - B; i++ ) {
			for ( int j = B; j <= W - B; j++ ) {
				map.update( i, j, def );
			}
		}
		SegmentTree2D<Boolean> canStack = new SegmentTree2D<>( H, W, true ) {
			Boolean function ( Boolean b1, Boolean b2 ) {
				return b1 && b2;
			}
		};
		def = new MaxNum( D, 1 );
		for ( int i = 0; i < B; i++ ) {
			for ( int j = 0; j < B; j++ ) {
				map.update( i, j, def );
				map.update( i, W - j - 1, def );
				map.update( H - i - 1, j, def );
				map.update( H - i - 1, W - j - 1, def );
			}
		}
		while ( 0 < set.size() ) {
			int index = ( int )set.ceiling( 0 );
			int[] point = search( index, map, canStack );
			makeAns( index, point[0], point[1], point[2], point[3] );
			check( index, point, map, canStack );
			if ( box[index].count == 0 ) {
				set.remove( index );
			}
		}
	}

	private static int[] search ( int p, SegmentTree2D<MaxNum> map, SegmentTree2D<Boolean> canStack ) {
		int r = getR( p );
		int[] ans1 = search( r, p, map, canStack );
		int[] ans2 = search( r / 2 * 2 + 1 - r % 2, p, map, canStack );
		if ( ans1[3] < ans2[3] ) {
			return ans1;
		}
		if ( ans2[3] == Integer.MAX_VALUE ) {
			int[] ans = null;
			int min = Integer.MAX_VALUE;
			for ( int i = 0; i < 6; i++ ) {
				if ( i / 2 == r / 2 ) {
					continue;
				}
				int[] temp = search( i, p, map, canStack );
				if ( temp[3] < min ) {
					ans = temp;
					min = temp[3];
				}
			}
			return ans;
		}
		return ans2;
	}

	private static int[] search2 ( int p, SegmentTree2D<MaxNum> map, SegmentTree2D<Boolean> canStack ) {
		int[] ans = null;
		int min = Integer.MAX_VALUE;
		int maxInd = box[p].rotatable ? 6 : 2;
		for ( int i = 0; i < maxInd; i++ ) {
			int d = i / 2 == 0 ? box[p].depth : i / 2 == 1 ? box[p].width : box[p].height;
			int[] temp = search( i, p, map, canStack );
			System.err.println( box[p].id + ":(r,x,y,z)=(" + temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + ")(d=" + d + ")" );
			if ( temp[3] != Integer.MAX_VALUE && temp[3] + d < min ) {
				ans = temp;
				min = temp[3] + d;
			}
		}
		return ans;
	}

	private static int[] search ( int r, int p, SegmentTree2D<MaxNum> map, SegmentTree2D<Boolean> canStack ) {
		int h, w;
		switch ( r ) {
			case 0:
				h = box[p].height;
				w = box[p].width;
				break;
			case 1:
				h = box[p].width;
				w = box[p].height;
				break;
			case 2:
				h = box[p].height;
				w = box[p].depth;
				break;
			case 3:
				h = box[p].depth;
				w = box[p].height;
				break;
			case 4:
				h = box[p].width;
				w = box[p].depth;
				break;
			default:
				h = box[p].depth;
				w = box[p].width;
				break;
		}
		int x = -1, y = -1, z = Integer.MAX_VALUE;
		int limit = h * w * 3 / 5;
		for ( int s = 0; s <= H - h; s++ ) {
			for ( int t = 0; t <= W - w; t++ ) {
				if ( !canStack.query( s, t, s + h, t + w ) ) {
					continue;
				}
				MaxNum m = map.query( s, t, s + h, t + w );
				if ( m.num < z && limit <= m.count ) {
					x = s;
					y = t;
					z = m.num;
				}
			}
		}
		return new int[]{r, x, y, z};
	}

	private static int getR ( int p ) {
		int r;
		if ( box[p].rotatable ) {
			int min = Math.min( box[p].height, Math.min( box[p].width, box[p].depth ) );
			int max = Math.max( box[p].height, Math.max( box[p].width, box[p].depth ) );
			if ( min == box[p].width ) {
				if ( max == box[p].depth ) {
					r = 0;
				}
				else {
					r = 4;
				}
			}
			else if ( min == box[p].height ) {
				if ( max == box[p].depth ) {
					r = 1;
				}
				else {
					r = 3;
				}
			}
			else {
				if ( min == box[p].width ) {
					r = 2;
				}
				else {
					r = 5;
				}
			}
		}
		else {
			if ( box[p].height <= box[p].width ) {
				r = 0;
			}
			else {
				r = 1;
			}
		}
		return r;
	}

	private static void check ( int p, int[] point, SegmentTree2D<MaxNum> map, SegmentTree2D<Boolean> canStack ) {
		int w1 = point[2], w2, h1 = point[1], h2, d;
		switch ( point[0] ) {
			case 0:
				w2 = w1 + box[p].width;
				h2 = h1 + box[p].height;
				d = point[3] + box[p].depth;
				break;
			case 1:
				w2 = w1 + box[p].height;
				h2 = h1 + box[p].width;
				d = point[3] + box[p].depth;
				break;
			case 2:
				w2 = w1 + box[p].depth;
				h2 = h1 + box[p].height;
				d = point[3] + box[p].width;
				break;
			case 3:
				w2 = w1 + box[p].height;
				h2 = h1 + box[p].depth;
				d = point[3] + box[p].width;
				break;
			case 4:
				w2 = w1 + box[p].depth;
				h2 = h1 + box[p].width;
				d = point[3] + box[p].height;
				break;
			default:
				w2 = w1 + box[p].width;
				h2 = h1 + box[p].depth;
				d = point[3] + box[p].height;
				break;
		}

		for ( int i = h1; i < h2; i++ ) {
			for ( int j = w1; j < w2; j++ ) {
				map.update( i, j, new MaxNum( d, 1 ) );
				canStack.update( i, j, box[p].stackable );
			}
		}
	}

	private static void input () {

		//箱の種類
		M = sc.nextInt();
		//コンテナの横の長さ
		W = sc.nextInt();
		//コンテナの縦の長さ
		H = sc.nextInt();
		//コンテナ内の柱の一辺(B*Bの正方形)
		B = sc.nextInt();
		//コンテナの高さ
		D = sc.nextInt();

		//荷物
		box = new Baggage[M];
		for ( int i = 0; i < M; i++ ) {
			int h = sc.nextInt();
			int w = sc.nextInt();
			int d = sc.nextInt();
			int a = sc.nextInt();
			char f = sc.nextChar();
			char g = sc.nextChar();
			box[i] = new Baggage( i, h, w, d, a, f == 'Y', g == 'Y' );
		}
	}

	private static void output () {
		for ( int[] a: ans ) {
			out.println( a, " " );
		}
		out.close();
	}

	private static void makeAns ( int p, int r, int x, int y, int z ) {
		int[] array = new int[5];
		array[0] = box[p].id;
		array[1] = r;
		array[3] = x;
		array[2] = y;
		array[4] = z;
		box[p].count--;
		ans.add( array );
	}
}

final class Baggage {
	final int height, width, depth, id;
	int count;
	final boolean rotatable, stackable;

	Baggage ( int i, int h, int w, int d, int a, boolean f, boolean g ) {
		id = i;
		height = h;
		width = w;
		depth = d;
		count = a;
		rotatable = f;
		stackable = g;
	}
}

final class MaxNum {
	int num, count;

	MaxNum ( int n, int c ) {
		num = n;
		count = c;
	}
}


/*


            ／￣＼
           |     |
            ＼＿／
              |
         ／ ￣  ￣  ＼
       ／   ＼  ／    ＼
     ／    ⌒    ⌒     ＼      よくぞこの提出結果を開いてくれた
     |    （__人__）     |      褒美としてオプーナを買う権利をやる
     ＼     ｀⌒´      ／    ☆
      /ヽ､--ー､＿＿,-‐´＼─／
    ／ >    ヽ▼●▼<＼    ||ｰ､.
   /ヽ､   ＼ i |｡| |/  ヽ  (ニ､`ヽ.
  l    ヽ    l |｡| | r-､y  ｀ﾆ  ﾉ ＼
  l     |    |ー─ | ￣ l    ｀~ヽ＿ノ＿_
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
// Segment Tree Two-Dimension version
abstract class SegmentTree2D<E> {
	private int H, W;
	private final Object[] node;
	private final E def;

	public SegmentTree2D ( int h, int w, E def ) {
		H = W = 1;
		while ( H < h ) {
			H <<= 1;
		}
		while ( W < w ) {
			W <<= 1;
		}
		node = new Object[4 * H * W];
		Arrays.fill( node, def );
		this.def = def;
	}

	@SuppressWarnings( "unchecked" )
	public E get ( int h, int w ) {
		return ( E )node[id( h + H, w + W )];
	}

	@SuppressWarnings( "unchecked" )
	public void update ( int h, int w, E x ) {
		h += H;
		w += W;
		node[id( h, w )] = x;
		int i = h >> 1;
		while ( 0 < i ) {
			node[id( i, w )] = function( ( E )node[id( 2 * i, w )], ( E )node[id( 2 * i + 1, w )] );
			i >>= 1;
		}
		while ( 0 < h ) {
			int j = w >> 1;
			while ( 0 < j ) {
				node[id( h, j )] = function( ( E )node[id( h, 2 * j )], ( E )node[id( h, 2 * j + 1 )] );
				j >>= 1;
			}
			h >>= 1;
		}
	}

	@SuppressWarnings( "unchecked" )
	private E query ( int h, int w1, int w2 ) {
		E ans = def;
		while ( w1 < w2 ) {
			if ( ( w1 & 1 ) == 1 ) {
				ans = function( ans, ( E )node[id( h, w1 )] );
				w1++;
			}
			if ( ( w2 & 1 ) == 1 ) {
				w2--;
				ans = function( ans, ( E )node[id( h, w2 )] );
			}
			w1 >>= 1;
			w2 >>= 1;
		}
		return ans;
	}

	public E query ( int h1, int w1, int h2, int w2 ) {
		if ( h1 >= h2 || w1 >= w2 ) {
			return def;
		}
		E ans = def;
		h1 += H;
		h2 += H;
		w1 += W;
		w2 += W;
		while ( h1 < h2 ) {
			if ( ( h1 & 1 ) == 1 ) {
				ans = function( ans, query( h1, w1, w2 ) );
				h1++;
			}
			if ( ( h2 & 1 ) == 1 ) {
				h2--;
				ans = function( ans, query( h2, w1, w2 ) );
			}
			h1 >>= 1;
			h2 >>= 1;
		}
		return ans;
	}

	private int id ( int h, int w ) {
		return h * 2 * W + w;
	}

	abstract E function ( E e1, E e2 );
}

//Red-Black Tree
final class RedBlackTree {
	private int size;
	private Entry root;

	public RedBlackTree () {
		size = 0;
		root = null;
	}

	public int size () {
		return size;
	}

	public boolean contains ( final long key ) {
		return getEntry( key ) != null;
	}

	private Entry getEntry ( final long key ) {
		Entry p = root;
		while ( p != null ) {
			if ( key < p.getValue() ) {
				p = p.left;
			}
			else if ( key > p.getValue() ) {
				p = p.right;
			}
			else {
				return p;
			}
		}
		return null;
	}

	public long ceiling ( final long key ) {
		Entry p = root;
		while ( p != null ) {
			if ( key < p.getValue() ) {
				if ( p.left != null ) {
					p = p.left;
				}
				else {
					return p.getValue();
				}
			}
			else if ( key > p.getValue() ) {
				if ( p.right != null ) {
					p = p.right;
				}
				else {
					Entry parent = p.parent;
					Entry ch = p;
					while ( parent != null && ch == parent.right ) {
						ch = parent;
						parent = parent.parent;
					}
					return parent != null ? parent.getValue() : key - 1;
				}
			}
			else {
				return key;
			}
		}
		return key - 1;
	}

	public long floor ( final long key ) {
		Entry p = root;
		while ( p != null ) {
			if ( key > p.getValue() ) {
				if ( p.right != null ) {
					p = p.right;
				}
				else {
					return p.getValue();
				}
			}
			else if ( key < p.getValue() ) {
				if ( p.left != null ) {
					p = p.left;
				}
				else {
					Entry parent = p.parent;
					Entry ch = p;
					while ( parent != null && ch == parent.left ) {
						ch = parent;
						parent = parent.parent;
					}
					return parent != null ? parent.getValue() : key + 1;
				}
			}
			else {
				return p.getValue();
			}
		}
		return key + 1;
	}

	public long higher ( final long key ) {
		Entry p = root;
		while ( p != null ) {
			if ( key < p.getValue() ) {
				if ( p.left != null ) {
					p = p.left;
				}
				else {
					return p.getValue();
				}
			}
			else {
				if ( p.right != null ) {
					p = p.right;
				}
				else {
					Entry parent = p.parent;
					Entry ch = p;
					while ( parent != null && ch == parent.right ) {
						ch = parent;
						parent = parent.parent;
					}
					return parent != null ? parent.getValue() : key - 1;
				}
			}
		}
		return key - 1;
	}

	public long lower ( final long key ) {
		Entry p = root;
		while ( p != null ) {
			if ( key > p.getValue() ) {
				if ( p.right != null ) {
					p = p.right;
				}
				else {
					return p.getValue();
				}
			}
			else {
				if ( p.left != null ) {
					p = p.left;
				}
				else {
					Entry parent = p.parent;
					Entry ch = p;
					while ( parent != null && ch == parent.left ) {
						ch = parent;
						parent = parent.parent;
					}
					return parent != null ? parent.getValue() : key + 1;
				}
			}
		}
		return key + 1;
	}

	private void addEntry ( final long key, final Entry parent, final boolean addToLeft ) {
		final Entry e = new Entry( key, parent );
		if ( addToLeft ) {
			parent.left = e;
		}
		else {
			parent.right = e;
		}
		fixAfterInsertion( e );
		size++;
	}

	private void addEntryToEmptySet ( final long key ) {
		root = new Entry( key, null );
		size = 1;
	}

	public boolean add ( final long key ) {
		Entry t = root;
		if ( t == null ) {
			addEntryToEmptySet( key );
			return true;
		}
		Entry parent;
		do {
			parent = t;
			if ( key < parent.getValue() ) {
				t = t.left;
			}
			else if ( key > parent.getValue() ) {
				t = t.right;
			}
			else {
				return false;
			}
		} while ( t != null );
		addEntry( key, parent, key < parent.getValue() );
		return true;
	}

	public boolean remove ( final long key ) {
		final Entry p = getEntry( key );
		if ( p == null ) {
			return false;
		}
		deleteEntry( p );
		return true;
	}

	public void clear () {
		size = 0;
		root = null;
	}

	private static final boolean RED = false;
	private static final boolean BLACK = true;

	private static final class Entry {
		private long value;
		private Entry left;
		private Entry right;
		private Entry parent;
		private boolean color = BLACK;

		private Entry ( final long value, final Entry parent ) {
			this.value = value;
			this.parent = parent;
		}

		private long getValue () {
			return value;
		}

		public boolean equals ( final Object o ) {
			if ( !( o instanceof Entry ) ) {
				return false;
			}
			Entry e = ( Entry )o;
			return value == e.getValue();
		}

		public int hashCode () {
			return Long.hashCode( value );
		}

		public String toString () {
			return String.valueOf( value );
		}
	}

	public long first () {
		Entry p = root;
		if ( p != null ) {
			while ( p.left != null ) {
				p = p.left;
			}
		}
		else {
			throw new NullPointerException();
		}
		return p.getValue();
	}

	public long last () {
		Entry p = root;
		if ( p != null ) {
			while ( p.right != null ) {
				p = p.right;
			}
		}
		else {
			throw new NullPointerException();
		}
		return p.getValue();
	}

	private static Entry successor ( final Entry t ) {
		if ( t == null ) {
			return null;
		}
		else if ( t.right != null ) {
			Entry p = t.right;
			while ( p.left != null ) {
				p = p.left;
			}
			return p;
		}
		else {
			Entry p = t.parent;
			Entry ch = t;
			while ( p != null && ch == p.right ) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}

	private static boolean colorOf ( final Entry p ) {
		return ( p == null ? BLACK : p.color );
	}

	private static Entry parentOf ( final Entry p ) {
		return ( p == null ? null : p.parent );
	}

	private static void setColor ( final Entry p, final boolean c ) {
		if ( p != null ) {
			p.color = c;
		}
	}

	private static Entry leftOf ( final Entry p ) {
		return ( p == null ) ? null : p.left;
	}

	private static Entry rightOf ( final Entry p ) {
		return ( p == null ) ? null : p.right;
	}

	private void rotateLeft ( final Entry p ) {
		if ( p != null ) {
			final Entry r = p.right;
			p.right = r.left;
			if ( r.left != null ) {
				r.left.parent = p;
			}
			r.parent = p.parent;
			if ( p.parent == null ) {
				root = r;
			}
			else if ( p.parent.left == p ) {
				p.parent.left = r;
			}
			else {
				p.parent.right = r;
			}
			r.left = p;
			p.parent = r;
		}
	}

	private void rotateRight ( final Entry p ) {
		if ( p != null ) {
			final Entry l = p.left;
			p.left = l.right;
			if ( l.right != null ) {
				l.right.parent = p;
			}
			l.parent = p.parent;
			if ( p.parent == null ) {
				root = l;
			}
			else if ( p.parent.right == p ) {
				p.parent.right = l;
			}
			else {
				p.parent.left = l;
			}
			l.right = p;
			p.parent = l;
		}
	}

	private void fixAfterInsertion ( Entry x ) {
		x.color = RED;
		while ( x != null && x != root && x.parent.color == RED ) {
			if ( parentOf( x ) == leftOf( parentOf( parentOf( x ) ) ) ) {
				final Entry y = rightOf( parentOf( parentOf( x ) ) );
				if ( colorOf( y ) == RED ) {
					setColor( parentOf( x ), BLACK );
					setColor( y, BLACK );
					setColor( parentOf( parentOf( x ) ), RED );
					x = parentOf( parentOf( x ) );
				}
				else {
					if ( x == rightOf( parentOf( x ) ) ) {
						x = parentOf( x );
						rotateLeft( x );
					}
					setColor( parentOf( x ), BLACK );
					setColor( parentOf( parentOf( x ) ), RED );
					rotateRight( parentOf( parentOf( x ) ) );
				}
			}
			else {
				final Entry y = leftOf( parentOf( parentOf( x ) ) );
				if ( colorOf( y ) == RED ) {
					setColor( parentOf( x ), BLACK );
					setColor( y, BLACK );
					setColor( parentOf( parentOf( x ) ), RED );
					x = parentOf( parentOf( x ) );
				}
				else {
					if ( x == leftOf( parentOf( x ) ) ) {
						x = parentOf( x );
						rotateRight( x );
					}
					setColor( parentOf( x ), BLACK );
					setColor( parentOf( parentOf( x ) ), RED );
					rotateLeft( parentOf( parentOf( x ) ) );
				}
			}
		}
		root.color = BLACK;
	}

	private void deleteEntry ( Entry p ) {
		size--;
		if ( p.left != null && p.right != null ) {
			final Entry s = successor( p );
			p.value = s.value;
			p = s;
		}
		final Entry replacement = ( p.left != null ? p.left : p.right );
		if ( replacement != null ) {
			replacement.parent = p.parent;
			if ( p.parent == null ) {
				root = replacement;
			}
			else if ( p == p.parent.left ) {
				p.parent.left = replacement;
			}
			else {
				p.parent.right = replacement;
			}

			p.left = p.right = p.parent = null;

			if ( p.color == BLACK ) {
				fixAfterDeletion( replacement );
			}
		}
		else if ( p.parent == null ) {
			root = null;
		}
		else {
			if ( p.color == BLACK ) {
				fixAfterDeletion( p );
			}

			if ( p.parent != null ) {
				if ( p == p.parent.left ) {
					p.parent.left = null;
				}
				else if ( p == p.parent.right ) {
					p.parent.right = null;
				}
				p.parent = null;
			}
		}
	}

	private void fixAfterDeletion ( Entry x ) {
		while ( x != root && colorOf( x ) == BLACK ) {
			if ( x == leftOf( parentOf( x ) ) ) {
				Entry sib = rightOf( parentOf( x ) );

				if ( colorOf( sib ) == RED ) {
					setColor( sib, BLACK );
					setColor( parentOf( x ), RED );
					rotateLeft( parentOf( x ) );
					sib = rightOf( parentOf( x ) );
				}

				if ( colorOf( leftOf( sib ) ) == BLACK &&
					 colorOf( rightOf( sib ) ) == BLACK ) {
					setColor( sib, RED );
					x = parentOf( x );
				}
				else {
					if ( colorOf( rightOf( sib ) ) == BLACK ) {
						setColor( leftOf( sib ), BLACK );
						setColor( sib, RED );
						rotateRight( sib );
						sib = rightOf( parentOf( x ) );
					}
					setColor( sib, colorOf( parentOf( x ) ) );
					setColor( parentOf( x ), BLACK );
					setColor( rightOf( sib ), BLACK );
					rotateLeft( parentOf( x ) );
					x = root;
				}
			}
			else {
				Entry sib = leftOf( parentOf( x ) );

				if ( colorOf( sib ) == RED ) {
					setColor( sib, BLACK );
					setColor( parentOf( x ), RED );
					rotateRight( parentOf( x ) );
					sib = leftOf( parentOf( x ) );
				}

				if ( colorOf( rightOf( sib ) ) == BLACK &&
					 colorOf( leftOf( sib ) ) == BLACK ) {
					setColor( sib, RED );
					x = parentOf( x );
				}
				else {
					if ( colorOf( leftOf( sib ) ) == BLACK ) {
						setColor( rightOf( sib ), BLACK );
						setColor( sib, RED );
						rotateLeft( sib );
						sib = leftOf( parentOf( x ) );
					}
					setColor( sib, colorOf( parentOf( x ) ) );
					setColor( parentOf( x ), BLACK );
					setColor( leftOf( sib ), BLACK );
					rotateRight( parentOf( x ) );
					x = root;
				}
			}
		}
		setColor( x, BLACK );
	}

	public long[] toArray () {
		index = 0;
		return search( new long[size], root );
	}

	@Override
	public String toString () {
		return java.util.Arrays.toString( toArray() );
	}

	private int index;

	private long[] search ( final long[] array, final Entry now ) {
		if ( now == null ) {
			return array;
		}
		search( array, now.left );
		array[index++] = now.getValue();
		search( array, now.right );
		return array;
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
			return new int[N + 1][0];
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
