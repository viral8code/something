import java.util.*;
class test{
	public static void main(String[] args){
		Random rm = new Random();
		System.out.println(10000);
		long limit = 1000000000;
		limit *= limit;
		for(int i=0;i<10000;i++){
			System.out.println(Math.max(Math.min(rm.nextLong(),limit),11));
		}
	}
}