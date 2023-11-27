import java.util.*;
class Main{
	public static void main(String[] args){
		Random rm = new Random();
		int[] array = new int[1_000_000];
		Arrays.setAll(array,i->rm.nextInt());
		Arrays.sort(array);
		long sum = 0;
		for(int i=0;i<1_000_000;i++){
			long s = System.nanoTime();
			int index = binarySearch(array,rm.nextInt());
			long f = System.nanoTime();
			sum += (f-s);
		}
		System.out.println("binarySearch\t"+sum/1e6+"ms");
		sum = 0;
		for(int i=0;i<1_000_000;i++){
			long s = System.nanoTime();
			int index = napierSearch(array,rm.nextInt());
			long f = System.nanoTime();
			sum += (f-s);
		}
		System.out.println("napierSearch\t"+sum/1e6+"ms");
	}
	private static int binarySearch(int[] array,int num){
		int left = 0;
		int right = array.length-1;
		int ans = array.length;
		while(left-right<1){
			int mid = (left+right)/2;
			if(array[mid]>=num)
				right = (ans=mid)-1;
			else
				left = mid+1;
		}
		return ans;
	}
	private static int napierSearch(int[] array,int num){
		int left = 0;
		int right = array.length-1;
		int ans = array.length;
		while(left-right<1){
			int sub = (int)((right-left)/Math.E);
			int mid1 = left+sub;
			int mid2 = mid1+sub;
			if(array[mid1]>=num)
				right = (ans=mid1)-1;
			else if(array[mid2]>=num){
				right = (ans=mid2)-1;
				left = mid1+1;
			}
			else
				left = mid2+1;
		}
		return ans;
	}
}