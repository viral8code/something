import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);

	public static void main(String[] args)throws IOException{

		int N = readInt();
		char[] ch = read().toCharArray();
		int count = 0;
		for(int i=0;i<N;i++){
			if(ch[i]=='1')
				count++;
		}
		if(count==N||count==0){
			println(N);
			flush();
			System.exit(0);
		}
		int[] adult = new int[count];
		int[] child = new int[N-count];
		int a=0,c=0;
		String[] str = reads(" ");
		TreeMap<Integer,Integer> changeOfAns = new TreeMap<Integer,Integer>();
		for(int i=0;i<str.length;i++){
			if(ch[i]=='1'){
				adult[a] = parseInt(str[i]);
				if(changeOfAns.get(adult[a]+1)==null){
					changeOfAns.put(adult[a]+1,-1);
				}
				else{
					changeOfAns.replace(adult[a]+1,changeOfAns.get(adult[a]+1)-1);
				}
				a++;
			}
			else{
				child[c] = parseInt(str[i]);
				if(changeOfAns.get(child[c]+1)==null){
					changeOfAns.put(child[c]+1,1);
				}
				else{
					changeOfAns.replace(child[c]+1,changeOfAns.get(child[c]+1)+1);
				}
				c++;
			}
		}
		heapSort(adult);
		heapSort(child);
		flush();
		int temp = adult.length;
		int answer = temp;
		for(int i : changeOfAns.keySet()){
			temp += changeOfAns.get(i);
			if(answer<temp)
				answer = temp;
		}
		println(answer);
		flush();
	}
	
	
	/*
	
	
		@出力系
		readInt()				:1行に数字一つのみある時用
		readLong()				:1行に数字一つのみある時用
		read()					:1行をそのまま取得
		readInt(n)				:n型配列として取得(空白区切り)
		readLong(n)				:n個のlong型配列として取得(空白区切り)
		reads(str)				:String型配列として取得(str区切り)
		print(T)				:Tを出力
		println(T)				:Tを出力(改行アリ)
		println()				:改行を出力
		printAll(T,str)			:str区切りでTをすべて出力
		printlnAll(T)			:Tを改行区切りですべて出力
		printAllln(T,str)		:str区切りでTをすべて出力(改行アリ)
		flush()					:flushする
		
		@計算系
		gcd(a,b)				:aとbの最大公約数
		lcm(a,b)				:aとbの最小公倍数
		isPrime(num)			:素数判定
		Prime(num)				:numまで(num含む)の素数すべてをint型配列にして返す
		pow(a,b)				:a^b
		modPow(a,b,mod)			:a^b % mod
		
		@変換系
		parseInt(str)			:strをintに変換(変換ミスを検知できない)
		parseLong(str)			:strをlongに変換(変換ミスを検知できない)
		
		@ソート系
		insertSort(array)		:挿入ソート
		selectSort(array)		:選択ソート
		margeSort(array)		:マージソート
		bubbleSort(array)		:バブルソート
		bubbleSortMark2(array)	:バブルソート(ほぼソート済みだと早い)
		bubbleSortMark3(array)	:改良バブルソート(ほぼ挿入ソート)
		heapSort(array)			:ヒープソート
		
	
	*/
	
	
	public static void insertSort(int[] list){
	
		for(int i=1;i<list.length;i++){
			
			int temp = list[i];
			int j = i - 1;
			
			while(j>=0 && list[j]>temp){
			
				list[j+1] = list[j--];
				
			}
			
			list[j+1] = temp;
			
		}
	}
	
	
	public static void selectSort(int[] list){
	
		for(int i=0;i<list.length-1;i++){
		
			int min = i;
			
			for(int j=i+1;j<list.length;j++){
			
				if(list[min]>list[j]){
				
					min = j;
					
				}
			}
			
			int temp = list[min];
			list[min] = list[i];
			list[i] = temp;
			
		}
	}
	
	
	public static void margeSort(int[] list){
		
		if(list.length<2) return;
		
		int[] list1 = new int[list.length/2];
		int[] list2 = new int[list.length-list1.length];
		
		System.arraycopy(list,0,list1,0,list1.length);
		System.arraycopy(list,list1.length,list2,0,list2.length);
		
		margeSort(list1);
		margeSort(list2);
		
		ArrayList<Integer> newList = new ArrayList<Integer>();
		int i=0,j=0;
		
		while(i<list1.length&&j<list2.length){
			
			int temp1 = list1[i];
			int temp2 = list2[j];
			
			if(temp1<temp2){
			
				newList.add(list1[i++]);
				
			}
			
			else{
			
				newList.add(list2[j++]);
				
			}
		}
		
		while(i<list1.length){
		
			newList.add(list1[i++]);
		
		}
		
		while(j<list2.length){
		
			newList.add(list2[j++]);
			
		}
			
		for(int k=0;k<list.length;k++){
		
			list[k]=newList.get(k);
			
		}
	}
	
	
	public static void bubbleSort(int[] list){
	
		for(int i=0;i<list.length;i++){
		
			for(int j=0;j<list.length-1-i;j++){
			
				if(list[j]>list[j+1]){
				
					int temp = list[j];
					list[j] = list[j+1];
					list[j+1] = temp;
					
				}
			}
		}
	}
	
	
	public static void bubbleSortMark2(int[] list){
	
		boolean notChange = false;
		for(int i=0;i<list.length;i++){
		
			if(notChange){
			
				break;
				
			}
			
			notChange = true;
		
			for(int j=0;j<list.length-1-i;j++){
			
				if(list[j]>list[j+1]){
				
					int temp = list[j];
					list[j] = list[j+1];
					list[j+1] = temp;
					
					notChange = false;
					
				}
			}
		}
	}
	
	
	public static void bubbleSortMark3(int[] list){
		
		for(int j=0;j<list.length-1;j++){
			
			if(list[j]>list[j+1]){
				
				int temp = list[j];
				list[j] = list[j+1];
				list[j+1] = temp;
					
				j = Math.max(j-2,-1);
				continue;
					
			}
		}
	}
	
	
	public static void heapSort(int[] list){
	
		for(int i=0;i<list.length;i++){
		
			int j = i;
			
			while(j>0 && list[(j-1)/2]<list[j]){
			
				int temp = list[(j-1)/2];
				list[(j-1)/2] = list[j];
				list[j] = temp;
				
				j = (j-1)/2;
				
			}
		}
		
		for(int i=list.length;i>0;i--){
		
			int temp = list[i-1];
			list[i-1] = list[0];
			list[0] = temp;
			
			int j = 0;
			
			while((2*j+1<i-1 && list[j] < list[2*j+1]) ||
					(2*j+2<i-1 && list[j] < list[2*j+2]))
			{
			
				if(2*j+2==i-1 || list[2*j+1]>list[2*j+2]){
				
					temp = list[2*j+1];
					list[2*j+1] = list[j];
					list[j] = temp;
					
					j <<=1;
					j++;
					
				}
				
				else{
				
					temp = list[2*j+2];
					list[2*j+2] = list[j];
					list[j] = temp;
					
					j <<=1;
					j += 2;
					
				}
			}
		}
	}
	
	
	public static int readInt()throws IOException{
	
		return Integer.parseInt(br.readLine());
		
	}
	
	
	public static long readLong()throws IOException{
	
		return Long.parseLong(br.readLine());
		
	}
	
	
	public static String read()throws IOException{
	
		return br.readLine();
		
	}
	
	
	public static int[] readInt(int n)throws IOException{
	
		int[] ans = new int[n];
		String[] str = br.readLine().split(" ");
		
		for(int i=0;i<n;i++){
		
			ans[i] = Integer.parseInt(str[i]);
			
		}
		
		return ans;
	}
	
	
	public static long[] readLong(int n)throws IOException{
	
		long[] ans = new long[n];
		String[] str = br.readLine().split(" ");
		
		for(int i=0;i<n;i++){
		
			ans[i] = Long.parseLong(str[i]);
			
		}
		
		return ans;
	}
	
	
	public static String[] reads(String str)throws IOException{
	
		return br.readLine().split(str);
		
	}
	
	
	public static void print(Object T){
	
		pw.print(T);
		
	}
	
	
	public static void println(Object T){
	
		pw.println(T);
		
	}
	
	
	public static void println(){
	
		pw.println();
		
	}
	
	
	public static void printlnAll(int... T){
	
		for(int i=0;i<T.length;i++){
		
			pw.println(T[i]);
			
		}
	}
	
	
	public static void printlnAll(long... T){
	
		for(int i=0;i<T.length;i++){
		
			pw.println(T[i]);
			
		}
	}
	
	
	public static void printlnAll(char... T){
	
		for(int i=0;i<T.length;i++){
		
			pw.println(T[i]);
			
		}
	}
	
	
	public static void printlnAll(byte... T){
	
		for(int i=0;i<T.length;i++){
		
			pw.println(T[i]);
			
		}
	}
	
	
	public static void printlnAll(Object[] T){
	
		for(int i=0;i<T.length;i++){
		
			pw.println(T[i]);
			
		}
	}
	
	
	public static void printlnAll(List<?> T){
	
		for(int i=0;i<T.size();i++){
		
			pw.println(T.get(i));
			
		}
	}
	
	
	public static void printAll(int[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.print(T[T.length-1]);
	}
	
	
	public static void printAll(long[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.print(T[T.length-1]);
	}
	
	
	public static void printAll(char[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.print(T[T.length-1]);
	}
	
	
	public static void printAll(byte[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.print(T[T.length-1]);
	}
	
	
	public static void printAll(Object[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.print(T[T.length-1]);
	}
	
	
	public static void printAll(List<?> T,String str){
	
		for(int i=0;i<T.size()-1;i++){
		
			pw.print(T.get(i));
			pw.print(str);
			
		}
		
		pw.print(T.get(T.size()-1));
	}
	
	
	public static void printAllln(int[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.println(T[T.length-1]);
	}
	
	
	public static void printAllln(long[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.println(T[T.length-1]);
	}
	
	
	public static void printAllln(char[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.println(T[T.length-1]);
	}
	
	
	public static void printAllln(byte[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.println(T[T.length-1]);
	}
	
	
	public static void printAllln(Object[] T,String str){
	
		for(int i=0;i<T.length-1;i++){
		
			pw.print(T[i]);
			pw.print(str);
			
		}
		
		pw.println(T[T.length-1]);
	}
	
	
	public static void printAllln(List<?> T,String str){
	
		for(int i=0;i<T.size()-1;i++){
		
			pw.print(T.get(i));
			pw.print(str);
			
		}
		
		pw.println(T.get(T.size()-1));
	}
	
	
	public static void flush(){
	
		pw.flush();
		
	}
	
	
	public static long gcd(long a,long b){
	
		long temp;
		
		while((temp = a % b) != 0){
		
			a = b;
			b = temp;
			
		}
		
		return b;
	}
	
	
	public static long lcm(long a,long b){
	
		long mult = a*b;
		long temp;
		
		while((temp = a % b) != 0){
		
			a = b;
			b = temp;
			
		}
		
		return mult / b;
	}
	
	
	public static boolean isPrime(long num){
	
		BigInteger bi = BigInteger.valueOf(num);
		return bi.isProbablePrime(20);
		
	}
	
	
	public static int[] prime(int num){
	
		BitSet nums = new BitSet(num+1);
		nums.set(2,num+1);
		
		for(int i=2;i<=Math.sqrt(num);i++){
		
			if(nums.get(i)){
			
				for(int j=i*i;j<=num;j+=i){
				
					nums.clear(j);
					
				}
			}
		}
		
		int[] answer = new int[nums.cardinality()];
		
		int i=2,index=0;
		
		while(true){
			
			i = nums.nextSetBit(i);
			answer[index++] = i++;
			
			if(index==answer.length){
				break;
			}
		}
		
		return answer;
	}
	
	
	public static long pow(long a,long b){
	
		long ans = 1;
		
		while(b>0){
			
			if((b&1)==1){
				ans *= a;
			}
			
			a *= a;
			b >>= 1;
		}
		
		return ans;
	}
	
	
	public static long modPow(long a,long b,long mod){
	
		long ans = 1;
		a %= mod;
		
		while(b>0){
		
			if((b&1)==1){
				ans *= a;
			}
			
			ans %= mod;
			a *= a;
			a %= mod;
			b >>= 1;
		}
		
		return ans;
	}
	
	
	public static int parseInt(String str){
	
		char[] nums = str.toCharArray();
		int ans = 0;
		boolean plus = true;
		
		if(nums[0]=='-'){
			plus = false;
			nums[0]='0';
		}
		
		for(int i=0;i<nums.length;i++){
			ans = ans*10 + (int)(nums[i]-'0');
		}
		
		return plus ? ans : -ans;
	}
	
	
	public static long parseLong(String str){
		
		char[] nums = str.toCharArray();
		long ans = 0;
		boolean plus = true;
		
		if(nums[0]=='-'){
			plus = false;
			nums[0]='0';
		}
		
		for(int i=0;i<nums.length;i++){
			ans = ans*10 + (int)(nums[i]-'0');
		}
		
		return plus ? ans : -ans;
	}
	
	public static int downsearch(int[] nums,int num){
	
		int a = 0,b = nums.length-1;
		if(nums[a]>num)		return -1;
		if(nums[b]<=num)	return b;
		
		int ans = (a+b)/2;
		while(b-a>1){
			
			if(nums[ans]<num)	a = ans;
			else 				b = ans;
			
			ans=(a+b)/2;
		}
		
		if(nums[ans]>num)	return ans-1;
		else				return ans;
	}
	
	public static int upsearch(int[] nums,int num){
	
		int a = 0,b = nums.length-1;
		if(nums[a]>=num)	return a;
		if(nums[b]<num)		return -1;
		
		int ans = (a+b)/2;
		while(b-a>1){
			
			if(nums[ans]>num)	b = ans;
			else 				a = ans;
			
			ans=(a+b)/2;
		}
		
		if(nums[ans]>num)	return ans+1;
		else				return ans;
	}
}
