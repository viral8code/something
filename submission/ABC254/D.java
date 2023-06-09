import java.io.*;
import java.util.*;
import java.math.*;
class Main{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);

	public static void main(String[] args)throws IOException{
		int N = readInt();
		long ans = 0;
		int[] pows = new int[N+1];
		for(int i=1;i<=N;i++){
			for(int j=1;;j++){
				if(j*j<=i){
					if((i%(j*j))==0){
						pows[i] = j*j;
					}
				}
				else break;
			}
		}
		for(int i=1;i<=N;i++){
			int temp = i/pows[i];
			for(int j=1;;j++){
				if((j*j*temp)>N) break;
				ans++;
			}
		}
		println(ans);
		flush();
	}

	/*
		テンプレ一覧
		○読み取り
		・int readInt()				：int型取得(行まるごと)
		・long readLong()			：long型取得(行まるごと)
		・String read()				：一行取得(String型)
		・int[] readInt(整数)			：int型配列取得(一行分)(空白あり)
		・long[] readLong(整数)			：long型配列取得(一行分)(空白あり)
		・String[] readSplit()			：一行分を一文字ずつ取得(String型配列)
		・String[] reads()			：一行分を空白区切りで取得(String型配列)
		○出力
		・void print(何か)			：改行なし出力
		・void println(何か)			：改行あり出力(引数無しも可)
		・void printlnAll(何か)			：全て出力(要素毎に改行を入れる)
		・void printAll(何か,String)		：全て出力(Stringは区切り文字)
		・void printAllln(何か,String)		：全て出力(Stringは区切り文字、最後に改行)
		・void flush()				：出力のflush
		○計算
		・int[] prime(int)			：intまでの素数をint型配列で返す(intも含む)
		・long gcd(long,long)			：最大公約数
		・long lcm(long,long)			：最小公倍数
		○その他
		・void exit()				：戻り値0を返してプログラムを終了
		・void sort(int[])			：[0]を比較して昇順並べ替え(Integer[]、int[][]、Integer[][]も可)
		・void deepSort(int[][])		：[i][0]を比較して昇順並べ替え([i][0]が同じなら[i][1]で比較)(Integer[][]も可)
		・int[] changeintDim1(整数[])		：整数型配列をint型配列に変換(二次元配列ならchangeintDim2)
		・Integer[] changeIntDim1(整数[])	：整数型配列をInteger型配列に変換(二次元配列ならchangeIntDim2)
		・long[] changelongDim1(整数[])		：整数型配列をlong型配列に変換(二次元配列ならchangelongDim2)
		・Long[] changelongDim1(整数[])		：整数型配列をLong型配列に変換(二次元配列ならchangeLongDim2)
	*/

	public static int readInt()throws IOException{return Integer.parseInt(br.readLine());}
	public static long readLong()throws IOException{return Long.parseLong(br.readLine());}
	public static String read()throws IOException{return br.readLine();}

	public static int[] readInt(int n)throws IOException{int[] ans=new int[n];String[] str=br.readLine().split("");for(int i=0;i<n;i++)ans[i]=Integer.parseInt(str[i]);return ans;}
	public static long[] readLong(int n)throws IOException{long[] ans=new long[n];String[] str=br.readLine().split("");for(int i=0;i<n;i++)ans[i]=Long.parseLong(str[i]);return ans;}
	public static String[] readSplit()throws IOException{return br.readLine().split("");}
	public static String[] reads()throws IOException{return br.readLine().split(" ");}

	public static int[] readInt(long n)throws IOException{int[] ans=new int[(int)n];String[] str=br.readLine().split("");for(int i=0;i<n;i++)ans[i]=Integer.parseInt(str[i]);return ans;}
	public static long[] readLong(long n)throws IOException{long[] ans=new long[(int)n];String[] str=br.readLine().split("");for(int i=0;i<n;i++)ans[i]=Long.parseLong(str[i]);return ans;}

	public static void print(Object T){pw.print(T);}
	public static void println(Object T){pw.println(T);}

	public static void printlnAll(int... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(long... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(char... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(byte... T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(Object[] T){for(int i=0;i<T.length;i++)println(T[i]);}
	public static void printlnAll(List<?> T){for(int i=0;i<T.size();i++)println(T.get(i));}

	public static void printAll(int[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(long[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(char[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(byte[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(Object[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);}print(T[T.length-1]);}
	public static void printAll(List<?> T,String str){for(int i=0;i<T.size()-1;i++){pw.print(T.get(i));pw.print(str);}print(T.get(T.size()-1));}

	public static void printAllln(int[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);};println(T[T.length-1]);}
	public static void printAllln(long[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);};println(T[T.length-1]);}
	public static void printAllln(char[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);};println(T[T.length-1]);}
	public static void printAllln(byte[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);};println(T[T.length-1]);}
	public static void printAllln(Object[] T,String str){for(int i=0;i<T.length-1;i++){pw.print(T[i]);pw.print(str);};println(T[T.length-1]);}
	public static void printAllln(List<?> T,String str){for(int i=0;i<T.size()-1;i++){pw.print(T.get(i));pw.print(str);};println(T.get(T.size()-1));}

	public static void println(){pw.println();}
	public static void flush(){pw.flush();}

	public static long gcd(long a,long b){long temp;while((temp=a%b)!=0){a=b;b=temp;}return b;}
	public static long lcm(long a,long b){return a*b/gcd(a,b);}

	public static void sort(int[] lis){Integer[] list=changeIntDim1(lis);if(list.length<2)return;Integer[] list1=new Integer[list.length/2];Integer[] list2=new Integer[list.length-list1.length];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Integer> newList=new ArrayList<Integer>();int i=0,j=0;while(i<list1.length&&j<list2.length){int temp1=list1[i];int temp2=list2[j];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}
	public static void sort(Integer[] list){if(list.length<2)return;Integer[] list1=new Integer[list.length/2];Integer[] list2=new Integer[list.length-list1.length];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Integer> newList=new ArrayList<Integer>();int i=0,j=0;while(i<list1.length&&j<list2.length){int temp1=list1[i];int temp2=list2[j];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}

	public static void sort(long[] lis){Long[] list=changeLongDim1(lis);if(list.length<2)return;Long[] list1=new Long[list.length/2];Long[] list2=new Long[list.length-list1.length];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Long> newList=new ArrayList<Long>();int i=0,j=0;while(i<list1.length&&j<list2.length){long temp1=list1[i],temp2=list2[j];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}
	public static void sort(Long[] list){if(list.length<2)return;Long[] list1=new Long[list.length/2];Long[] list2=new Long[list.length-list1.length];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Long> newList=new ArrayList<Long>();int i=0,j=0;while(i<list1.length&&j<list2.length){long temp1=list1[i],temp2=list2[j];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}

	public static void sort(int[][] lis){Integer[][] list=changeIntDim2(lis);if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){int temp1=list1[i][0],temp2=list2[j][0];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)for(int l=0;l<list.length;l++)lis[k][l]=newList.get(k)[l];}
	public static void sort(Integer[][] list){if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){int temp1=list1[i][0],temp2=list2[j][0];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}

	public static void deepSort(Integer[][] list){if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);deepSort(list1);deepSort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){Integer[] temp1=list1[i],temp2=list2[j];if(temp1[0]<temp2[0])newList.add(list1[i++]);else if(temp1[0]==temp2[0]&&temp1[1]<temp2[1])newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}
	public static void deepSort(int[][] lis){Integer[][] list=changeIntDim2(lis);if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);deepSort(list1);deepSort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){Integer[] temp1=list1[i],temp2=list2[j];if(temp1[0]<temp2[0])newList.add(list1[i++]);else if(temp1[0]==temp2[0]&&temp1[1]<temp2[1])newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)for(int l=0;l<list[k].length;l++)lis[k][l]=newList.get(k)[l];}

	public static int[] changeintDim1(Integer[] list){int[] ans=new int[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static int[] changeintDim1(long[] list){int[] ans=new int[list.length];for(int i=0;i<list.length;i++)ans[i]=(int)list[i];return ans;}
	public static int[] changeintDim1(Long[] list){int[] ans=new int[list.length];for(int i=0;i<list.length;i++)ans[i]=(int)(long)list[i];return ans;}
	public static int[][] changeintDim2(Integer[][] list){int[][] ans=new int[list.length][];for(int i=0;i<list.length;i++){ans[i]=new int[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}
	public static int[][] changeintDim2(long[][] list){int[][] ans=new int[list.length][];for(int i=0;i<list.length;i++){ans[i]=new int[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=(int)list[i][j];}return ans;}
	public static int[][] changeintDim2(Long[][] list){int[][] ans=new int[list.length][];for(int i=0;i<list.length;i++){ans[i]=new int[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=(int)(long)list[i][j];}return ans;}

	public static Integer[] changeIntDim1(int[] list){Integer[] ans=new Integer[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static Integer[] changeIntDim1(long[] list){Integer[] ans=new Integer[list.length];for(int i=0;i<list.length;i++)ans[i]=(int)list[i];return ans;}
	public static Integer[] changeIntDim1(Long[] list){Integer[] ans=new Integer[list.length];for(int i=0;i<list.length;i++)ans[i]=(int)(long)list[i];return ans;}
	public static Integer[][] changeIntDim2(int[][] list){Integer[][] ans=new Integer[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Integer[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}
	public static Integer[][] changeIntDim2(long[][] list){Integer[][] ans=new Integer[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Integer[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=(int)list[i][j];}return ans;}
	public static Integer[][] changeIntDim2(Long[][] list){Integer[][] ans=new Integer[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Integer[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=(int)(long)list[i][j];}return ans;}

	public static long[] changelongDim1(int[] list){long[] ans=new long[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static long[] changelongDim1(Integer[] list){long[] ans=new long[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static long[] changelongDim1(Long[] list){long[] ans=new long[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static long[][] changelongDim2(int[][] list){long[][] ans=new long[list.length][];for(int i=0;i<list.length;i++){ans[i]=new long[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}
	public static long[][] changelongDim2(Integer[][] list){long[][] ans=new long[list.length][];for(int i=0;i<list.length;i++){ans[i]=new long[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}
	public static long[][] changelongDim2(Long[][] list){long[][] ans=new long[list.length][];for(int i=0;i<list.length;i++){ans[i]=new long[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}

	public static Long[] changeLongDim1(int[] list){Long[] ans=new Long[list.length];for(int i=0;i<list.length;i++)ans[i]=(long)list[i];return ans;}
	public static Long[] changeLongDim1(Integer[] list){Long[] ans=new Long[list.length];for(int i=0;i<list.length;i++)ans[i]=(long)list[i];return ans;}
	public static Long[] changeLongDim1(long[] list){Long[] ans=new Long[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static Long[][] changeLongDim2(int[][] list){Long[][] ans=new Long[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Long[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=(long)list[i][j];}return ans;}
	public static Long[][] changeLongDim2(Integer[][] list){Long[][] ans=new Long[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Long[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=(long)list[i][j];}return ans;}
	public static Long[][] changeLongDim2(long[][] list){Long[][] ans=new Long[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Long[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}

	public static int[] prime(int num){BitSet nums=new BitSet(num+1);nums.set(2,num+1);for(int i=2;i<=Math.sqrt(num);i++)if(nums.get(i))for(int j=i*i;j<=num;j+=i)nums.clear(j);int[] answer=new int[nums.cardinality()];int i=2,index=0;while(true){i=nums.nextSetBit(i);answer[index++]=i++;if(index==answer.length)break;}return answer;}
	public static boolean isPrime(long num){BigInteger bi=BigInteger.valueOf(num);return bi.isProbablePrime(20);}

	public static void exit(){System.exit(0);}
}