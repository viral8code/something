import java.util.*;
class Treap<E>{
	private Node root;
	private int size;
	private final MTRandom rm;
	public Treap{
		size = 0;
		root = null;
		rm = new MTRandom();
	}
	public void add(E x){
		if(root==null)
			root = new Node(x,null);
		else
			root.add(x);
	}
	public boolean remove(E x){
		if(root==null)
			return false;
		return root.remove(x);
	}
	public E ceiling(E x){
		if(root==null)
			return null;
		return root.ceiling(x,null);
	}
	public E floor(E x){
		if(root==null)
			return null;
		return root.ceiling(x,null);
	}
	class Node{
		E value;
		int id;
		Node left,right,parent;
		Node(E x,Node p){
			value = x;
			id = rm.nextInt();
			parent = p;
		}
		void add(E x){
			if(value.compareTo(x)>0){
				if(left==null){
					left = new Node(x,this);
				}else{
					left.add(x);
				}
				check();
			}
			else if(value.compareTo(x)<0){
				if(right==null){
					right = new Node(x.this);
				}else{
					right.add(x);
				}
				check();
			}
		}
		void remove(E x){
			if(value.compareTo(x)>0){
				if(left!=null){
					left.remove(x);
				}
			}
			else if(value.compareTo(x)<0){
				if(right!=null){
					right.remove(x);
				}
			}
			else{
				if(left==null||right==null){
					if((left==null)^(right==null)){
						final Node temp = left==null?right:left;
						if(parent==null){
							root = temp;
							root.parent = null;
						}
						else{
							if(parent.left==this){
								parent.left = temp;
							}else{
								parent.right = temp;
							}
							temp.parent = parent;
						}
					}
				}else{
					final Node temp = left.pollLast();
					temp.parent = parent;
					if(parent==null){
						root = temp;
					}else{
						if(parent.left==this){
							parent.left = temp;
						}else{
							parent.right = temp;
						}
					}
					temp.right = right;
					temp.left = left;
					temp.check();
				}
				deleteObject();
			}
		}
		E ceiling(E x,E remind){
			if(value.compareTo(x)<0){
				if(right==null){
					return remind;
				}else{
					return right.ceiling(x,remind);
				}
			}else{
				if(left==null){
					return value;
				}else{
					return left.ceiling(x,value);
				}
			}
		}
		E floor(E x,E remind){
			if(value.compareTo(x)>0){
				if(left==null){
					return remind;
				}else{
					return left.ceiling(x,remind);
				}
			}else{
				if(right==null){
					return value;
				}else{
					return right.ceiling(x,value);
				}
			}
		}
	}
}
class MTRandom extends Random {
	private final static int[] MAGIC = {0,0x9908b0df};
	private transient int[] mt;
	private transient int mti;
	public MTRandom() {
            this.setSeed((int)System.currentTimeMillis());
	}
	private final void setSeed(int seed){
		if(mt==null)mt=new int[624];
		mt[0]=seed;
		for(mti=1;mti<624;mti++)mt[mti]=(1812433253*(mt[mti-1]^(mt[mti-1]>>>30))+mti);
	}
	protected final int next(int bits) {
		int y=0,kk;
		if(mti>=624){
			for(kk=0;kk<227;kk++){
				y=(mt[kk]&0x80000000)|(mt[kk+1]&0x7fffffff);
				mt[kk]=mt[kk+397]^(y>>>1)^MAGIC[y&1];
			}
			for(;kk<623;kk++){
				y=(mt[kk]&0x80000000)|(mt[kk+1]&0x7fffffff);
				mt[kk]=mt[kk-227]^(y>>>1)^MAGIC[y&1];
			}
			y=(mt[623]&0x80000000)|(mt[0]&0x7fffffff);
			mt[623]=mt[396]^(y>>>1)^MAGIC[y&1];
			mti=0;
		}
		y=mt[mti++]^(y>>>11)^((y<<7)&0x9d2c5680)^((y<<15)&1664525)^(y>>>18);
		return(y>>>(32-bits));
	}
}