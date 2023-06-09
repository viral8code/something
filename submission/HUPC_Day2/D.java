import java.io.*;
import java.math.*;
import java.awt.Point;
import java.awt.Dimension;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
class Main{
	public static void main(String[]args){
		var sc = new SScanner(System.in);var out = new SPrinter(System.out,false);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int Q = sc.nextInt();
		int[][] query = new int[Q][];
		TreeInt set = new TreeInt();
		for(int i=0;i<Q;i++){
			int q = sc.nextInt();
			int[] subQuery;
			if(q==1){
				subQuery = new int[5];
				subQuery[0] = q;
				subQuery[1] = sc.nextInt();
				subQuery[2] = sc.nextInt();
				subQuery[3] = sc.nextInt()-1;
				subQuery[4] = sc.nextInt()-1;
				set.add(subQuery[2]);
			}
			else if(q==2){
				subQuery = new int[3];
				subQuery[0] = q;
				subQuery[1] = sc.nextInt();
				subQuery[2] = sc.nextInt();
			}
			else{
				subQuery = new int[3];
				subQuery[0] = q;
				subQuery[1] = sc.nextInt();
				subQuery[2] = sc.nextInt();
			}
			query[i] = subQuery;
		}
		TreeMultiInt id = new TreeMultiInt();
		int index = 1;
		for(int key:set.toArray())
			id.add(key,index++);
		int[] array = new int[M];
		Arrays.setAll(array,i->i);
		ArrayList<SegmentTree<int[]>> list = new ArrayList<>();
		for(int i=0;i<=N;i++)
			list.add(new SegmentTree<>(Q,array,true){
				int[] function(int[] a,int[] b){
					int[] ans = new int[M];
					for(int i=0;i<M;i++)
						ans[i] = a[b[i]];
					return ans;
				}
			});
		SegmentTree<int[]> ans = new SegmentTree<>(N,array,true){
			int[] function(int[] a,int[] b){
				int[] ans = new int[M];
				for(int i=0;i<M;i++)
					ans[i] = a[b[i]];
				return ans;
			}
		};
		for(int[] q:query){
			if(q[0]==1){
				int[] arr = new int[M];
				Arrays.setAll(arr,i->i);
				for(int i=q[3];i<q[4];i++)
					arr[i] = i+1;
				arr[q[4]] = q[3];
				list.get(q[1]).update((int)id.sum(q[2]),arr);
				ans.update(q[1],list.get(q[1]).answer());
			}
			else if(q[0]==2){
				SegmentTree<int[]> temp = list.get(q[1]);
				list.set(q[1],list.get(q[2]));
				list.set(q[2],temp);
				ans.update(q[1],list.get(q[1]).answer());
				ans.update(q[2],list.get(q[2]).answer());
			}
			else{
				int[] answer = new int[M];
				int[] func = ans.query(q[1],q[2]);
				for(int i=0;i<M;i++)
					answer[i] = func[i]+1;
				out.println(answer);
			}
		}
						
		out.close();
	}
}
class TreeMultiInt{
	Node root;long size;int uniqueSize,hash;
	TreeMultiInt(){
		size = 0;
		uniqueSize = 0;
		root = null;
		hash = 0;
	}
	static class Node{
		int value,height;long count,size;Node left,right,parent;
		public Node(Node p,int v,long c){
			value=v;parent=p;count=c;height=1;size=c;
		}
	}
	public void add(int x,long sum){
		if(root==null){
			root=new Node(null,x,sum);
			++uniqueSize;
		}else{
			Node par,now=root;boolean bool=true;
			do{
				par=now;
				if(x<now.value)now=now.left;
				else if(x>now.value)now = now.right;
				else{
					bool=false;
					now.count+=sum;
					fix(now);
					break;
				}
			}while(now!=null);
			if(bool){
				++uniqueSize;
				if(x<par.value)par.left=new Node(par,x,sum);
				else par.right=new Node(par,x,sum);
				fix(par);
			}
		}
		size+=sum;
		if(sum%2==1)hash^=x;
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
	private Node getFirstNode ( Node node ) {
		assert node != null;
		Node par = null;
		while ( node != null ) {
			par = node;
			node = par.left;
		}
		return par;
	}
	public long sum ( final int x ) {
		if ( root == null ) {
			return 0;
		}
		Node node = getNode( x );
		return node != null ? node.count : 0;
	}
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
abstract class SegmentTree<E>{
	int N,size;E def;Object[]node;
	SegmentTree(int n,E def,boolean include){
		N=2;size=1;
		while(N<n<<1){N<<=1;size<<=1;}
		size-=include?1:0;
		node=new Object[N];
		this.def=def;
		Arrays.fill(node,this.def);
	}
	SegmentTree(E[]arr,E def,boolean include){
		N=2;size=1;
		while(N<arr.length<<1){N<<=1;size<<=1;}
		node=new Object[N];
		this.def=def;
		System.arraycopy(arr,0,node,size,arr.length);
		for(int i=arr.length+size;i<N;i++)node[i]=def;
		updateAll();
		size-=include?1:0;
	}
	SegmentTree(int n,E def){this(n,def,false);}
	@SuppressWarnings("unchecked")
	void updateAll(){for(int i=size-1;i>0;i--)node[i]=function((E)node[i<<1],(E)node[(i<<1)+1]);}
	@SuppressWarnings("unchecked")
	void update(int n,E value){
		n+=size;node[n]=value;n>>=1;
		while(n>0){node[n]=function((E)node[n<<1],(E)node[(n<<1)+1]);n>>=1;}
	}
	@SuppressWarnings("unchecked")
	E get(int a){return(E)node[a+size];}
	@SuppressWarnings("unchecked")
	E answer(){return(E)node[1];}
	@SuppressWarnings("unchecked")
	E query(int l,int r){
		l+=size;r+=size;
		E answer1=def;
		E answer2=def;
		while(l>0&&r>0&&l<=r){
			if(l%2==1)answer1=function(answer1,(E)node[l++]);l>>=1;
			if(r%2==0)answer2=function((E)node[r--],answer2);r>>=1;
		}
		return function(answer1,answer2);
	}
	abstract E function(E a,E b);
}
class UnionFind{
	int[]par,rank,size,path;int count;
	UnionFind(int N){
		count=N;
		par=new int[N];rank=new int[N];size=new int[N];path=new int[N];
		Arrays.fill(par,-1);Arrays.fill(size,1);
	}
	int root(int x){if(par[x]==-1)return x;return par[x]=root(par[x]);}
	boolean isSame(int x,int y){return root(x)==root(y);}
	boolean unite(int x,int y){
		int rx=root(x);
		int ry=root(y);
		++path[rx];
		if(rx==ry)return false;
		if(rank[rx]<rank[ry]){
			int temp=rx;
			rx=ry;
			ry=temp;
		}
		par[ry]=rx;
		if(rank[rx]==rank[ry])++rank[rx];
		path[rx]+=path[ry];
		size[rx]+=size[ry];
		--count;
		return true;
	}
	int groupCount(){return count;}
	int pathCount(int x){return path[root(x)];}
	int size(int x){return size[root(x)];}
}
class Pair<K extends Comparable<K>,V extends Comparable<V>>implements Comparable<Pair<K,V>>{
	AbstractMap.SimpleEntry<K,V>map;
	Pair(K key,V value){map=new AbstractMap.SimpleEntry<>(key,value);}
	K getKey(){return map.getKey();}
	V getValue(){return map.getValue();}
	K setKey(K key){
		K oldKey=map.getKey();
		V value=map.getValue();
		map=new AbstractMap.SimpleEntry<>(key,value);
		return oldKey;
	}
	V setValue(V value){return map.setValue(value);}
	public int compareTo(Pair<K,V>pair){
		int com=getKey().compareTo(pair.getKey());
		return com!=0?com:getValue().compareTo(pair.getValue());
	}
	public boolean equals(Object o){
		if(o instanceof Pair<?,?>){
			var pair=(Pair<?,?>)o;
			return getKey().equals(pair.getKey())&&getValue().equals(pair.getValue());
		}
		return false;
	}
	public String toString(){return getKey()+"="+getValue();}
	public int hashCode(){return(getKey().hashCode()<<2)^(getValue().hashCode());}
}
class SScanner{
	int buff_size=1<<12,point,length;InputStream is;byte[]buff;
	SScanner(InputStream is){
		this.is=is;
		buff=new byte[buff_size];
		point=length=0;
	}
	void reload(){do{try{length=is.read(buff,point=0,buff_size);}catch(IOException e){e.printStackTrace();System.exit(1);}}while(length==-1);}
	byte read(){
		if(point==length)reload();
		return buff[point++];
	}
	byte nextByte(){
		var c=read();
		while(c<=' ')c=read();
		return c;
	}
	int nextInt(){
		int ans=0;
		var c=read();
		while(c<=' ')c=read();
		var negate=c=='-';
		if(c=='-')c=read();
		while('0'<=c&&c<='9'){ans=ans*10+c-'0';c=read();}
		return negate?-ans:ans;
	}
	long nextLong(){
		var ans=0L;
		var c=read();
		while(c<=' ')c=read();
		var negate=c=='-';
		if(c=='-')c=read();
		while('0'<=c&&c<='9'){ans=ans*10+c-'0';c=read();}
		return negate?-ans:ans;
	}
	char nextChar(){
		byte c=read();
		while(c<=' ')c=read();
		return(char)c;
	}
	String next(){
		var ans=new StringBuilder();
		byte c=read();
		while(c<=' ')c=read();
		while(c>' '){ans.append((char)c);c=read();}
		return ans.toString();
	}
	byte[]nextByte(int n){
		var ans=new byte[n];
		for(int i=0;i<n;++i)ans[i]=nextByte();
		return ans;
	}
	int[]nextInt(int n){
		var ans=new int[n];
		for(int i=0;i<n;++i)ans[i]=nextInt();
		return ans;
	}
	long[]nextLong(int n){
		var ans=new long[n];
		for(int i=0;i<n;++i)ans[i]=nextLong();
		return ans;
	}
	String[]next(int n){
		var ans=new String[n];
		for(int i=0;i<n;++i)ans[i]=next();
		return ans;
	}
	byte[][]nextByte(int n,int m){
		var ans=new byte[n][];
		for(int i=0;i<n;++i)ans[i]=nextByte(m);
		return ans;
	}
	int[][]nextInt(int n,int m){
		var ans=new int[n][];
		for(int i=0;i<n;++i)ans[i]=nextInt(m);
		return ans;
	}
	long[][]nextLong(int n,int m){
		var ans=new long[n][];
		for(int i=0;i<n;++i)ans[i]=nextLong(m);
		return ans;
	}
	String[][]next(int n,int m){
		var ans=new String[n][];
		for(int i=0;i<n;++i)ans[i]=next(m);
		return ans;
	}
	char[]nextCharArray(){return next().toCharArray();}
	char[][]nextCharArray(int n){
		var ans=new char[n][];
		for(int i=0;i<n;++i)ans[i]=nextCharArray();
		return ans;
	}
	int[][]nextGraph(int N,int M){
		if(M==0)return new int[N+1][0];
		var ans=new int[N+1][];
		var path=nextInt(M,2);
		var count=new int[N+1];
		for(int[]temp:path){++count[temp[0]];++count[temp[1]];}
		for(int i=1;i<=N;++i)ans[i]=new int[count[i]];
		for(int[]temp:path){ans[temp[0]][--count[temp[0]]]=temp[1];ans[temp[1]][--count[temp[1]]]=temp[0];}
		ans[0]=new int[0];
		return ans;
	}
	Point nextPoint(){return new Point(nextInt(),nextInt());}
	Point[]nextPoint(int n){
		var ans=new Point[n];
		for(int i=0;i<n;++i)ans[i]=nextPoint();
		return ans;
	}
	void close(){try{is.close();}catch(IOException e){e.printStackTrace();System.exit(1);}}
}
class SPrinter extends PrintWriter{
	SPrinter(PrintStream os,boolean bool){super(os,bool);}
	void println(int[]ar){println(ar,' ');}
	void println(int[]ar,String str){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(str);
			print(ar[i]);
		}
		println();
	}
	void println(int[]ar,char c){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(c);
			print(ar[i]);
		}
		println();
	}
	void println(int[][]ar){println(ar,' ');}
	void println(int[][]ars,String str){for(var ar:ars)println(ar,str);}
	void println(int[][]ars,char c){for(var ar:ars)println(ar,c);}
	void println(long[]ar){println(ar,' ');}
	void println(long[]ar,String str){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(str);
			print(ar[i]);
		}
		println();
	}
	void println(long[]ar,char c){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(c);
			print(ar[i]);
		}
		println();
	}
	void println(long[][]ar){println(ar,' ');}
	void println(long[][]ars,String str){for(var ar:ars)println(ar,str);}
	void println(long[][]ars,char c){for(var ar:ars)println(ar,c);}
	void println(char[]cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(str);
			print(cs[i]);
		}
		println();
	}
	void println(char[]cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(c);
			print(cs[i]);
		}
		println();
	}
	void println(char[][]cs){for(var c:cs)println(c);}
	void println(char[][]cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(str);
			print(cs[i]);
		}
		println();
	}
	void println(char[][]cs,char c){
		print(cs[0]);
		for(int i=1;i<cs.length;++i){
			print(c);
			print(cs[i]);
		}
		println();
	}
	<E>void println(E[]ar){println(ar,' ');}
	<E>void println(E[]ar,String str){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(str);
			print(ar[i]);
		}
		println();
	}
	<E>void println(E[]ar,char c){
		print(ar[0]);
		for(int i=1;i<ar.length;++i){
			print(c);
			print(ar[i]);
		}
		println();
	}
	<E>void println(E[][]ars){println(ars,' ');}
	<E>void println(E[][]ars,String str){for(E[]ar:ars)println(ar,str);}
	<E>void println(E[][]ars,char c){for(E[]ar:ars)println(ar,c);}
}