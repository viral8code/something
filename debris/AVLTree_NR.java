class Main{
	public static void main(String[] args){

		final int size = 1000000;
		java.util.Random rm = new java.util.Random();

		long[] array = new long[size];
		for(int i=0;i<size;i++)
			array[i] = rm.nextLong();

		AVLTree_Multi_Long tree = new AVLTree_Multi_Long();
		for(int i=0;i<size;i++)
			tree.add(array[i]);

		if(tree.sumSize()!=size)
			System.out.println("size not correct");

		java.util.Arrays.sort(array);
		for(int i=0;i<size;i++)
			if(array[i]!=tree.get(i))
				System.out.println(array[i]+": is not contains!!!");
	}
}
