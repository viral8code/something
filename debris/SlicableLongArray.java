class SlicableLongArray{
	private final long[] array;
	private final int l,r,length;
	public SlicableLongArray(int len){
		array = new long[len];
		l = 0;
		r = len;
		length = len;
	}
	private SlicableLongArray(long[] arr,int l,int r,int base){
		array = arr;
		this.l = l+base;
		this.r = r+base;
		length = r-l;
		if(arr.length<r)
			throw new ArrayIndexOutOfBoundsException();
	}
	public void set(int index,long value){
		if(length<=index)
			throw new ArrayIndexOutOfBoundsException();
		array[index+l] = value;
	}
	public long get(int index){
		if(length<=index)
			throw new ArrayIndexOutOfBoundsException();
		return array[index+l];
	}
	public SlicableLongArray slice(int l,int r){
		if(l<0||length<r)
			throw new ArrayIndexOutOfBoundsException();
		return new SlicableLongArray(array,l,r,this.l);
	}
	public static void main(String[] args){
		SlicableLongArray array = new SlicableLongArray(100);
		array.set(10,1000);
		SlicableLongArray array2 = array.slice(10,100);
		array2 = array2.slice(0,1);
		System.out.println(array2.get(0));
	}
}
