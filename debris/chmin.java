class Test{
	public static void main(String[] args){
		int[] aa = {10};
		int a = 10;
		int b = 2;
		int c = 9;
		System.out.println(chmin(aa,a,b,c)[0]);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	private static int[] chmin(int... nums){
		if(nums.length<2)
			return nums;
		int min = Math.min(nums[0],nums[1]);
		for(int i=2;i<nums.length;i++)
			if(min>nums.length)
				min = nums[i];
		nums[0] = min;
		return nums;
	}
}
