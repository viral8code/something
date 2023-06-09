import java.util.function.IntBinaryOperator;
class IntHeap{
	private IntArray heap;
	private IntBinaryOperator comparator;
	public IntHeap(){
		this((a,b)->a-b);
	}
	public IntHeap(IntBinaryOperator comp){
		comparator = comp;
		heap = new IntArray();
	}
	public int size(){
		return heap.size();
	}
	public void add(int value){
		heap.add(value);
		int n = heap.size();
		if(1<n&&comparator.applyAsInt(heap.get(n-1),heap.get((n>>1)-1))<0){
			int temp = heap.get(n-1);
			heap.set(n-1,heap.get((n>>1)-1));
			heap.set((n>>1)-1,temp);
			n >>= 1;
		}
	}
	public int poll(){
		int ans = heap.get(0);
		heap.set(0,heap.get(heap.size()-1));
		heap.removeLast();
		int n = 1;
		if(n<<1<=heap.size()){
			if(((n<<1)|1)<=heap.size()&&comparator.applyAsInt(heap.get(n<<1),heap.get((n<<1)|1))>0){
				int temp = heap.get((n<<1)|1);
				heap.set((n<<1)|1,heap.get(n));
				heap.set(n,temp);
				n <<= 1;
				++n;
			}
			else{
				int temp = heap.get(n<<1);
				heap.set(n<<1,heap.get(n));
				heap.set(n,temp);
				n <<= 1;
			}
		}
		return ans;
	}
}