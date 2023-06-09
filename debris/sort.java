import java.util.*;
import java.util.function.*;
class Main{
	public static void main(String[] args){

		final int size = 1000000;

		Random rm = new Random();
		int[] array1 = new int[size];
		int[] array2 = new int[size];
		for(int i=0;i<size;i++)
			array1[i] = array2[i] = rm.nextInt();

		long s = System.nanoTime();
		countSort(array1);
		long f = System.nanoTime();
		System.out.println("countSort:"+(int)((f-s)/1e6)+"ms");

		s = System.nanoTime();
		Arrays.sort(array2);
		f = System.nanoTime();
		System.out.println("Arrays.sort:"+(int)((f-s)/1e6)+"ms");
	}
	private static void countSort(final int[] array){
		HashMap_IntInt map = new HashMap_IntInt(100000);
		for(int num:array)
			map.merge(num,1,(i,j)->i+j);
		int index = 0;
		for(int[] m:map.toArray()){
			final int value = m[0];
			int count = m[1];
			while(count-->0)
				array[index++] = value;
		}
	}
}
final class HashMap_IntInt{
	private final Node[] table;
	private int allSize;
	private int hash;
	public HashMap_IntInt(){
		table = new AVLTree_Int[16];
		hash = 15;
		for(int i=0;i<table.length;i++)
			table[i] = new AVLTree_Int();
		allSize = 0;
	}
	public HashMap_IntInt(int defaultSize){
		table = new AVLTree_Int[Math.max(16,defaultSize)];
		hash = table.length-1;
		for(int i=0;i<table.length;i++)
			table[i] = new AVLTree_Int();
		allSize = 0;
	}
	public int put(final int key,final int value){
		return put(key,value,true);
	}
	public int put(final int key,final int value,final boolean force){
		return table[hash(key)].put(key,value,force);
	}
	public int merge(final int key,final int value,final IntBinaryOperator func){
		return table[hash(key)].merge(key,value,func);
	}
	public int remove(final int key,final int value){
		return table[hash(key)].remove(key,value);
	}
	public int[][] toArray(){
		int index = 0;
		int[][] ans = new int[allSize][2];
		for(AVLTree_Int tree:table){
			tree.toArray(ans,index);
			index += tree.size();
		}
		return ans;
	}
	public int size(){
		return allSize;
	}
	private int hash(int key){
		return (key^(key>>>16))&hash;
	}
}
