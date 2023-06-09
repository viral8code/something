final class ArrayFunction{
	public static void sort(final int[] array){
		if(array.length<10)
			insertSort(array,0,array.length);
		else{
			int limit = 1;
			while(array.length>1<<limit)
				limit++;
			quickSort(array,0,array.length,limit);
		}
	}
	private static void quickSort(final int[] array,final int l,final int r,final int limit){
		final int len = r-l;
		if(len<2)
			return;
		if(len<10){
			insertSort(array,l,r);
			return;
		}
		if(limit<0){
			heapSort(array,l,r);
			return;
		}
		final int pivot = array[len/2+l];
		int i = l;
		int j = r-1;
		while(i<=j){
			while(array[i]<pivot)
				i++;
			while(array[j]>pivot)
				j--;
			if(i<=j){
				final int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				i++;
				j--;
			}
		}
		quickSort(array,l,j+1,limit-1);
		quickSort(array,i,r,limit-1);
	}
	private static void insertSort(final int[] array,final int l,final int r){
		for(int i=l+1;i<r;i++){
			final int num = array[i];
			int j = i-1;
			while(l<=j&&num<array[j])
				array[j+1] = array[j--];
			j++;
			array[j] = num;
		}
	}
	private static void heapSort(final int[] array,final int l,final int r){
		final int len = r-l;
		for(int i=0;i<len;i++){
			int j = i;
			while(0<j&&array[(j-1)/2+l]<array[j+l]){
				final int temp = array[(j-1)/2+l];
				array[(j-1)/2+l] = array[j+l];
				array[j+l] = temp;
				j = (j-1)/2;
			}
		}
		for(int i=len;i>0;i--){
			final int temp1 = array[i-1+l];
			array[i-1+l] = array[l];
			array[l] = temp1;
			int j = 0;
			while((2*j+1<i-1&&array[j+l]<array[2*j+1+l])||
			      (2*j+2<i-1&&array[j+l]<array[2*j+2+l])){
				if(2*j+2==i-1||array[2*j+2+l]<array[2*j+1+l]){
					final int temp2 = array[2*j+1+l];
					array[2*j+1+l] = array[j+l];
					array[j+l] = temp2;
					j <<= 1;
					j++;
				}
				else{
					final int temp2 = array[2*j+2+l];
					array[2*j+2+l] = array[j+l];
					array[j+l] = temp2;
					j <<= 1;
					j += 2;
				}
			}
		}
	}
}
