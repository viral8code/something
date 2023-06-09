class AVLTree_Long{
	private Node root;
	private boolean hasChanged;
	private int size;
	public AVLTree_Long() {
		size = 0;
		root = null;
		hasChanged = false;
	}
	final public boolean add( final long x ) {
		boolean bool;
		if( root == null ) {
			root = new Node( null, x );
			bool = true;
		}
		else
			bool = root.add( x );
		if( hasChanged ) {
			root = root.parent;
			hasChanged = false;
		}
		if( bool )
			++size;
		return bool;
	}
	final public boolean remove( final long x ) {
		boolean bool;
		if( root == null )
			bool = false;
		else {
			bool = root.remove( x );
			if( hasChanged ) {
				root = root.parent;
				hasChanged = false;
			}
		}
		if( bool )
			--size;
		return bool;
	}
	final public boolean contains( final long x ) {
		if( root == null )
			return false;
		return root.find( x );
	}
	final public long pollFirst() {
		if( root == null )
			throw new NullPointerException();
		--size;
		final long min = root.pollMin();
		if( hasChanged ) {
			root = root.parent;
			hasChanged = false;
		}
		return min;
	}
	final public long pollLast() {
		if( root == null ) 
			throw new NullPointerException();
		--size;
		final long max = root.pollMax();
		if( hasChanged ) {
			root = root.parent;
			hasChanged = false;
		}
		return max;
	}
	final public long ceiling( final long x ) {
		if( root == null )
			throw new NullPointerException();
		return root.ceiling( x, Long.MAX_VALUE );
	}
	final public long floor( final long x ) {
		if( root == null )
			throw new NullPointerException();
		return root.floor( x, Long.MIN_VALUE );
	}
	final public int size(){
		return size;
	}
	@Override
	final public String toString(){
		final long[] list = new long[size];
		if(root!=null)
			root.string(list,0);
		return java.util.Arrays.toString(list);
	}
	final public long[] toArray(){
		final long[] list = new long[size];
		if(root!=null)
			root.string(list,0);
		return list;
	}
	final private class Node{
		private long value;
		int height;
		Node left, right, parent;
		public Node( final Node p, final long v ) {
			value = v;
			height = 0;
			parent = p;
		}
		private int getHeight() {
			return Math.max( left == null ? -1:left.height , right == null ? -1:right.height );
		}
		public boolean add( final long x ) {
			if( x > value ) {
				if( right == null ) {
					right = new Node( this, x );
					height = getHeight() + 1;
					check();
					return true;
				}
				else {
					if( right.add( x ) ) {
						height = getHeight() + 1;
						check();
						return true;
					}
				}
			}
			else if( x < value ) {
				if( left == null ) {
					left = new Node( this, x );
					height = getHeight() + 1;
					check();
					return true;
				}
				else {
					if( left.add( x ) ) {
						height = getHeight() + 1;
						check();
						return true;
					}
				}
			}
			return false;
		}
		public boolean remove( final long x ) {
			if( x > value ) {
				if( right == null )
					return false;
				else {
					if( right.remove( x ) ) {
						height = getHeight() + 1;
						check();
						return true;
					}
				}
			}
			else if( x < value ) {
				if( left == null )
					return false;
				else {
					if( left.remove( x ) ) {
						height = getHeight() + 1;
						check();
						return true;
					}
				}
			}
			else {
				if( parent == null ) {
					if( left == null  &&  right == null )
						hasChanged = true;
					else {
						value = left != null ? left.pollMax():right.pollMin();
						height = getHeight() + 1;
						check();
					}
				}
				else {
					final boolean flag = parent.left == this;
					if( left == null  &&  right == null ) {
						if( flag )
							parent.left = null;
						else
							parent.right = null;
						parent = null;
					}
					else {
						value = left != null ? left.pollMax():right.pollMin();
						height = getHeight() + 1;
						check();
					}
				}
				return true;
			}
			return false;
		}
		public boolean find( final long x ) {
			if( x == value )
				return true;
			if( x > value ) {
				if( right == null )
					return false;
				else
					return right.find( x );
			}
			else {
				if( left == null )
					return false;
				else
					return left.find( x );
			}
		}
		public long ceiling( final long x, final long before ) {
			if( x == value )
				return value;
			if( x < value ) {
				if( left == null )
					return value;
				else
					return left.ceiling( x, value );
			}
			else {
				if( right == null )
					return before;
				else
					return right.ceiling( x, before );
			}
		}
		public long floor( final long x, final long before ) {
			if( x == value )
				return value;
			if( x > value ) {
				if( right == null )
					return value;
				else
					return right.floor( x, value );
			}
			else {
				if( left == null )
					return before;
				else
					return left.floor( x, before );
			}
		}
		public long pollMax() {
			if( right == null ) {
				if( parent == null ) {
					hasChanged = true;
					parent = left;
					if( parent != null )
						parent.parent = null;
				}
				else {
					if( parent.left == this )
						parent.left = left;
					else
						parent.right = left;
					if( left != null )
						left.parent = parent;
					parent = null;
				}
				return value;
			}
			final long max = right.pollMax();
			height = getHeight() + 1;
			check();
			return max;
		}
		public long pollMin() {
			if( left == null ) {
				if( parent == null ) {
					hasChanged = true;
					parent = right;
					if( parent != null )
						parent.parent = null;
				}
				else {
					if( parent.left == this )
						parent.left = right;
					else
						parent.right = right;
					if( right != null )
						right.parent = parent;
					parent = null;
				}
				return value;
			}
			final long min = left.pollMin();
			height = getHeight() + 1;
			check();
			return min;
		}
		private void check() {
			final int lh = left == null ? -1:left.height;
			final int rh = right == null ? -1:right.height;
			if( lh - rh  >  1 )
				rotateR();
			else if( rh - lh  >  1 )
				rotateL();
			height = getHeight() + 1;
		}
		private void rotateR() {
			final Node temp = left;
			left = left.right;
			if( left != null )
				left.parent = this;
			temp.right = this;
			temp.parent = parent;
			if( parent != null ) {
				if( parent.left == this )
					parent.left = temp;
				else
					parent.right = temp;
			}
			else
				hasChanged = true;
			parent = temp;
			check();
		}
		private void rotateL() {
			final Node temp = right;
			right = right.left;
			if( right != null )
				right.parent = this;
			temp.left = this;
			temp.parent = parent;
			if( parent != null ) {
				if( parent.left == this )
					parent.left = temp;
				else
					parent.right = temp;
			}
			else
				hasChanged = true;
			parent = temp;
			check();
		}
		public int string( final long[] list, int index ) {
			if( left != null )
				index = left.string( list, index );
			list[index++] = value;
			if( right != null )
				index = right.string( list, index );
			return index;
		}
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
}
