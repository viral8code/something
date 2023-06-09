final class TreeListInt {
	private Node root,head,tail;
	private int size, hash;

	public TreeListInt () {
		size = 0;
		root = head = tail = null;
		hash = 0;
	}

	static final private class Node {
		int value;
		int height, size;
		Node left, right, parent,previous,next;

		public Node ( final Node p, final int v ,final Node pre,final Node n) {
			value = v;
			parent = p;
			height = 1;
			size = 1;
			previous = pre;
			next = n;
		}
	}

	public int add ( final int x ) {
		if(root==null){
			head = tail = root = new Node(null,x,null,null);
		}
		else{
			Node max = getLastNode(root);
			max.right = new Node(max,x,max,null);
			max.next = max.right;
			tail = max.right;
			fix(max);
		}
		size++;
		hash ^= x;
		return x;
	}

	public int add ( final int x ,final int index ) {
		if(size==index){
			add(x);
		}
		else{
			Node node = getNode(index);
			insertNode(node,x);
			if(index==0){
				head = head.previous;
			}
			size++;
		}
		hash ^= x;
		return x;
	}

	private void insertNode(final Node node,final int x){
		assert node!=null;
		Node prev = node.previous;
		if(node.left!=null){
			Node par = getLastNode(node.left);
			Node ins = new Node(par,x,prev,node);
			node.previous = ins;
			if(prev!=null){
				prev.next = ins;
			}
			par.right = ins;
			fix(par);
		}
		else{
			Node ins = new Node(node,x,prev,node);
			node.previous = ins;
			if(prev!=null){
				prev.next = ins;
			}
			node.left = ins;
			fix(node);
		}
	}

	private Node getNode ( int index ) {
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
		return now;
	}

	public int get ( int index ) {
		return getNode( index ).value;
	}

	public int remove ( final int index ) {
		Node n = getNode( index );
		if(head==n){
			head = head.next;
		}
		if(tail==n){
			tail = tail.previous;
		}
		--size;
		int ans = n.value;
		hash ^= ans;
		delete( n );
		return ans;
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
				fixPath(node);
			}
			else {
				if ( node.left != null && node.right != null ) {
					Node rep = node.next;
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
					fixPath(node);
				}
			}
		}
	}

	private static void fixPath(Node node){
		Node prev = node.previous;
		Node next = node.next;
		if(prev!=null){
			prev.next = next;
		}
		if(next!=null){
			next.previous = prev;
		}
	}

	public int first () {
		if ( head == null ) {
			throw new NullPointerException();
		}
		return head.value;
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
		if ( tail == null ) {
			throw new NullPointerException();
		}
		return tail.value;
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

	public int pollFirst () {
		if ( head == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node min = head;
		head = head.next;
		int ans = min.value;
		hash ^= ans;
		delete( min );
		return ans;
	}

	public int pollLast () {
		if ( tail == null ) {
			throw new NullPointerException();
		}
		--size;
		final Node max = tail;
		tail = tail.previous;
		int ans = max.value;
		hash ^= ans;
		delete( max );
		return ans;
	}

	public void clear () {
		root = head = tail = null;
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
		Node now = head;
		for(int i=0;i<size;i++){
			list[i] = now.value;
			now = now.next;
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
		if ( o instanceof TreeListInt ) {
			final TreeListInt tree = ( TreeListInt )o;
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
class Main{
	public static void main(String[] args){
		TreeListInt tree = new TreeListInt();
		int size = 500000;
		for(int i=0;i<size;i++)
			tree.add(i);
		for(int i=0;i<size;i++)
			if(i!=tree.get(i))
				System.out.println("!:"+i+"->"+tree.get(i));
		System.out.println(tree.size());
		for(int i=size;i>0;i--){
			tree.remove(i-1);
			if(tree.size()!=i-1)
				System.out.println("size is illigal("+i+")");
		}
		for(int i=0;i<size;i++)
			tree.add(i,0);
		System.out.println(tree.size());
	}
}
