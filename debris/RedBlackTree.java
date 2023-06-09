public class RedBlackTree<E extends Comparable<? super E>>{

	private enum Color{RED,BLACK};
	Node root;
	private int size;
	private boolean isBreaking;

	public RedBlackTree(){
		root = null;
		size = 0;
		isBreaking = false;
	}

	public boolean add(E x){
		if(root==null){
			root = new Node(x,null,Color.BLACK);
			return true;
		}
		final boolean ans = root.add(x);
		root.color = Color.BLACK;
		isBreaking = false;
		return ans;
	}

	public boolean remove(E x){
		if(root==null)
			return false;
		return root.remove(x);
	}

	public boolean contains(E x){
		if(root==null)
			return false;
		return root.contains(x);
	}

	class Node{
		E value;
		Node left,right,parent;
		Color color;
		Node(E val,Node par,Color c){
			value = val;
			parent = par;
			color = c;
			left = right = null;
		}
		boolean add(E x){
			final int result = value.compareTo(x);
			if(result==0)
				return false;
			if(result>0){
				if(left!=null){
					final boolean canAdd = left.add(x);
					if(isBreaking)
						check(Color.RED);
					return canAdd;
				}
				left = new Node(x,this,Color.RED);
				if(color==Color.RED){
					isBreaking = true;
					check(Color.RED);
				}
				return true;
			}
			if(right!=null){
				final boolean canAdd = right.add(x);
				if(isBreaking)
					check(Color.RED);
				return canAdd;
			}
			right = new Node(x,this,Color.RED);
			isBreaking = color==Color.RED;
			if(isBreaking)
				check(Color.RED);
			return true;
		}
		boolean contains(E x){
			final int result = value.compareTo(x);
			if(result==0)
				return true;
			if(result>0){
				if(left!=null)
					return left.contains(x);
				return false;
			}
			if(right!=null)
				return right.contains(x);
			return false;
		}
		boolean remove(E x){
			final int result = value.compareTo(x);
			if(result==0){
				if(left==null&&right==null){
					if(parent.left==this)
						parent.left = null;
					else
						parent.right = null;
					parent = null;
					value = null;
					isBreaking = color==Color.BLACK;
					color = null;
				}
				else if(left==null){
					final E temp = right.pollFirst();
					value = temp;
					if(isBreaking)
						checkL(color);
				}
				else{
					final E temp = left.pollLast();
					value = temp;
					if(isBreaking)
						checkR(color);						
				}
				return true;
			}
			if(result>0){
				if(left!=null){
					boolean ans = left.remove(x);
					if(isBreaking)
						checkR(color);
					return ans;
				}
				return false;
			}
			if(right!=null){
				boolean ans = right.remove(x);
				if(isBreaking)
					checkL(color);
				return ans;
			}
			return false;
		}
		void check(Color c){
			if(left!=null&&color==Color.RED&&left.color==Color.RED){
				if(parent.left==this)
					rotateR(c);
				else
					rotateLR(c);
			}
			else if(right!=null&&color==Color.RED&&right.color==Color.RED){
				if(parent.right==this)
					rotateL(c);
				else
					rotateRL(c);
			}
		}
		///////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////
		void checkR(Color c){
			if(right!=null){
				if(right.color==Color.RED){
					right.rotate
				}
				else if(right.left!=null&&right.left.color==Color.RED){
					
				}
		}
		void rotateR(Color c){
			parent.left = right;
			if(right!=null)
				right.parent = parent;
			right = parent;
			parent = right.parent;
			right.parent = this;
			if(parent==null)
				root = this;
			else{
				if(parent.left==right)
					parent.left = this;
				else
					parent.right = this;
			}
			color = c;
			left.color = Color.BLACK;
			right.color = Color.BLACK;
		}
		void rotateRL(Color c){
			final Node temp = right;
			right = temp.left;
			parent.left = temp.right;
			if(temp.right!=null)
				temp.right.parent = parent;
			temp.right = parent;
			parent = temp;
			temp.left = this;
			if(right!=null)
				right.parent = this;
			temp.parent = temp.right.parent;
			temp.right.parent = temp;
			if(temp.parent==null)
				root = temp;
			else{
				if(temp.parent.left==temp.right)
					temp.parent.left = temp;
				else
					temp.parent.right = temp;
			}
			temp.color = c;
			color = Color.BLACK;
			temp.right.color = Color.BLACK;
		}
		void rotateL(Color c){
			parent.right = left;
			if(left!=null)
				left.parent = parent;
			left = parent;
			parent = left.parent;
			left.parent = this;
			if(parent==null)
				root = this;
			else{
				if(parent.left==right)
					parent.left = this;
				else
					parent.right = this;
			}
			color = c;
			left.color = Color.BLACK;
			right.color = Color.BLACK;
		}
		void rotateLR(Color c){
			final Node temp = left;
			left = temp.right;
			parent.right = temp.left;
			if(temp.left!=null)
				temp.left.parent = parent;
			temp.left = parent;
			parent = temp;
			temp.right = this;
			if(left!=null)
				left.parent = this;
			temp.parent = temp.left.parent;
			temp.left.parent = temp;
			if(temp.parent==null)
				root = temp;
			else{
				if(temp.parent.left==temp.left)
					temp.parent.left = temp;
				else
					temp.parent.right = temp;
			}
			temp.color = c;
			color = Color.BLACK;
			temp.left.color = Color.BLACK;
		}
		public String toString(){
			return value.toString();
		}
	}
}
class Test{
	public static void main(String[] args){
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		java.util.Random rm = new java.util.Random(0);
		for(int i=0;i<7;i++){
			//System.out.println("try->"+i);
			tree.add(rm.nextInt(100));
		java.util.LinkedList<RedBlackTree.Node> bfs = new java.util.LinkedList<>();
		bfs.add(tree.root);
		while(!bfs.isEmpty()){
			java.util.LinkedList<RedBlackTree.Node> next = new java.util.LinkedList<>();
			for(RedBlackTree.Node now:bfs){
				if(now.left!=null)
					next.add(now.left);
				if(now.right!=null)
					next.add(now.right);
				System.out.println("("+now.color+")"+now+"->"+now.left+":"+now.right);
			}
			bfs = next;
		}System.out.println();}
		//*/
	}
}