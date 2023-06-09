class VariableLengthArray<E>{
	Node<E>[] array;
	public int length;
	@SuppressWarnings("unchecked")
	public VariableLengthArray(int len){
		length = len;
		array = new Node[len];
		for(int i=0;i<len;i++)
			array[i] = new Node<>();
	}
	public void set(int index,E x){
		array[index].value = x;
	}
	public E get(int index){
		return array[index].value;
	}
	private class Node<E>{
		public E value;
	}
}
class Main{
	public static void main(String[] args){
		VariableLengthArray<Integer> list = new VariableLengthArray<>(100);
		list.set(10,100);
		System.out.println(list.get(10));
		System.out.println(list.length);
	}
}