class IntSet{
	private TreeInt[] table;
	private int size,hash;
	public IntSet(){
		this(16);
	}
	public IntSet(int length){
		if(length<16)
			length = 16;
		table = new TreeInt[length];
		for(int i=0;i<length;i++)
			table[i] = new TreeInt();
	}
	public boolean add(int value){
		int h = value%table.length;
		if(table[h].add(value)){
			++size;
			hash ^= value;
			if(table.length*10<size)
				rehash();
			return true;
		}
		return false;
	}
	public boolean contains(int value){
		int h = value%table.length;
		return table[h].contains(value);
	}
	public boolean remove(int value){
		int h = value%table.length;
		if(table[h].remove(value)){
			--size;
			hash ^= value;
			return true;
		}
		return false;
	}
	private void rehash(){
		TreeInt[] newTable = new TreeInt[table.length<<1];
		for(int i=0;i<newTable.length;i++)
			newTable[i] = new TreeInt();
		for(TreeInt tree:table)
			for(int num:tree.toArray())
				newTable[num%newTable.length].add(num);
	}
	@Override
	public int hashCode(){
		return hash;
	}
}
