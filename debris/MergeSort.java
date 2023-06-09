import java.util.*;
class Main{
	public static void main(String[] args){

		final int size = 2_000_000;

		int[] array = new int[size];
		Random rm = new Random(System.nanoTime());
		for(int i=0;i<size;i++)
			array[i] = rm.nextInt();

		long s,f;

		s = System.nanoTime();
		sort(array);
		f = System.nanoTime();
		boolean sorted = true;
		for(int i=1;i<size;i++)
			sorted &= array[i-1]<=array[i];
		System.out.println("sorted:"+sorted);
		System.out.println((f-s)/1e6+"ms");
	}
	private static long sort(final int[] array){
		long ans = 0;
		for(int i=1;i<array.length;i<<=1)
			for(int j=0;j<array.length;j+=i<<1)
				ans += merge(array,j,i);
		return ans;
	}
	private static long merge(final int[] array,int l,final int len){
		int ll = l;
		int l2 = l+len;
		final int r2 = Math.min(l2+len,array.length);
		if(l2>=r2)
			return 0;
		final int[] subArray = new int[len];
		int l1 = 0;
		final int r1 = len;
		System.arraycopy(array,l,subArray,0,len);
		long ans = 0;
		while(l1<r1&&l2<r2){
			if(subArray[l1]>array[l2]){
				ans += r1-l1;
				array[l++] = array[l2++];
			}
			else
				array[l++] = subArray[l1++];
		}
		while(l1<r1)
			array[l++] = subArray[l1++];
		return ans;
	}
}
