class IntQeque{
	private int[] queue;
	private int head,tail,size;
	public IntQeque(){
		size = (int)2e5;
		queue = new int[size];
		head = 0;
		tail = 0;
	}
	final public boolean empty(){
		return head==tail;
	}
	final public int size(){
		return tail-head;
	}
	final public int add(int x){
		queue[tail++] = x;
		if(tail==size)
			grow();
		return x;
	}
	final public int poll(){
		if(empty())
			throw new NullPointerException("Qeque is empty");
		return queue[head++];
	}
	final private void grow(){
		if(head>0){
			int[] newQueue = new int[size];
			System.arraycopy(queue,head,newQueue,0,tail-head);
			queue = newQueue;
		}else
			throw new ArrayIndexOutOfBoundsException("Can't add over 2e5");
	}
}