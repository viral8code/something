import java.io.*;
import java.util.*;
import java.math.*;
class Main{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);

	public static void main(String[] args)throws IOException{
		TreeMap<Integer,Integer> S = new TreeMap<Integer,Integer>();
		int Q = readInt();
		for(int i=0;i<Q;i++){
			String[] temp = reads();
			if(temp.length==2){
				int x = Integer.parseInt(temp[1]);
				if(S.get(x)==null)
					S.put(x,1);
				else
					S.replace(x,S.get(x)+1);
			}
			else if(temp.length==3){
				int x = Integer.parseInt(temp[1]);
				if(S.get(x)==null)
					continue;
				int subtract = S.get(x)-Integer.parseInt(temp[2]);
				if(subtract<=0)
					S.remove(x);
				else
					S.replace(x,subtract);
			}
			else
				println(S.lastKey()-S.firstKey());
		}
		exit();
	}

	/*
		テンプレ一覧
		○読み取り
		・int readInt()			：int型取得(行まるごと)
		・long readLong()		：long型取得(行まるごと)
		・String read()			：一行取得(String型)
		・int[] readInt(int)		：int型配列取得(一行分)
		・long[] readLong(int)		：long型配列取得(一行分)
		・String[] readSplit()		：一行分を一文字ずつ取得(String型配列)
		・String[] reads()		：一行分を空白区切りで取得(String型配列)
		○出力
		・void print(何か)		：改行なし出力
		・void println(何か)		：改行あり出力(引数無しも可)
		・void printlnAll(何か)		：全て出力(要素毎に改行を入れる)
		・void printAll(何か,String)	：全て出力(Stringは区切り文字)
		・void printAllln(何か,String)	：全て出力(Stringは区切り文字、最後に改行)
		・void exit()			：出力のflush
		○計算
		・number max(複数の数値)	：一番大きい数を返す(int、long、byte)(戻り値の型は入力値と同じ)
		・number min(複数の数値)	：一番小さい数を返す(int、long、byte)(戻り値の型は入力値と同じ)
		・int[] prime(int)		：intまでの素数をint型配列で返す(intも含む)
		・int[] primeL(int)		：intまでの素数をlong型配列で返す(intも含む)(int>500000の可能性があるときはこっち)
		○その他
		・void sort(int[][])		：[i][0]を比較して昇順並べ替え(Integer[][]も可)
		・void deepSort(int[][])	：[i][0]を比較して昇順並べ替え([i][0]が同じなら[i][1]で比較)(Integer[][]も可)
		・Integer[] changeIntDim1(int[])：int型配列をInteger型配列に変換(二次元配列ならchangeIntDim2)
		・int[] changeintDim1(Integer[])：long型配列かInteger型配列をint型配列に変換(二次元配列ならchangeintDim2)
		・long[] changelongDim1(int[])	：int型配列をlong型配列に変換
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
	public static void printlnAll(int... T){for(int i=0;i<T.length;i++)pw.println(T[i]);}
	public static void printlnAll(long... T){for(int i=0;i<T.length;i++)pw.println(T[i]);}
	public static void printlnAll(char... T){for(int i=0;i<T.length;i++)pw.println(T[i]);}
	public static void printlnAll(byte... T){for(int i=0;i<T.length;i++)pw.println(T[i]);}
	public static void printlnAll(Object[] T){for(int i=0;i<T.length;i++)pw.println(T[i]);}
	public static void printlnAll(List<?> T){for(int i=0;i<T.size();i++)pw.println(T.get(i));}
	public static void printAll(int[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);}}
	public static void printAll(long[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);}}
	public static void printAll(char[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);}}
	public static void printAll(byte[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);}}
	public static void printAll(Object[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);}}
	public static void printAll(List<?> T,String str){for(int i=0;i<T.size();i++){pw.print(T.get(i));pw.print(str);}}
	public static void printAllln(int[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);};println();}
	public static void printAllln(long[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);};println();}
	public static void printAllln(char[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);};println();}
	public static void printAllln(byte[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);};println();}
	public static void printAllln(Object[] T,String str){for(int i=0;i<T.length;i++){pw.print(T[i]);pw.print(str);};println();}
	public static void printAllln(List<?> T,String str){for(int i=0;i<T.size();i++){pw.print(T.get(i));pw.print(str);};println();}
	public static void println(){pw.println();}
	public static void exit(){pw.flush();}
	public static int max(int... nums){Arrays.sort(nums);return nums[nums.length-1];}
	public static int min(int... nums){Arrays.sort(nums);return nums[0];}
	public static long max(long... nums){Arrays.sort(nums);return nums[nums.length-1];}
	public static long min(long... nums){Arrays.sort(nums);return nums[0];}
	public static byte max(byte... nums){Arrays.sort(nums);return nums[nums.length-1];}
	public static byte min(byte... nums){Arrays.sort(nums);return nums[0];}
	public static void sort(Integer[][] list){if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){int temp1=list1[i][0],temp2=list2[j][0];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}
	public static void sort(int[][] lis){Integer[][] list=changeIntDim2(lis);if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);sort(list1);sort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){int temp1=list1[i][0],temp2=list2[j][0];if(temp1<temp2)newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)for(int l=0;l<list.length;l++)lis[k][l]=newList.get(k)[l];}
	public static void deepSort(Integer[][] list){if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);deepSort(list1);deepSort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){Integer[] temp1=list1[i],temp2=list2[j];if(temp1[0]<temp2[0])newList.add(list1[i++]);else if(temp1[0]==temp2[0]&&temp1[1]<temp2[1])newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)list[k]=newList.get(k);}
	public static void deepSort(int[][] lis){Integer[][] list=changeIntDim2(lis);if(list.length<2)return;Integer[][] list1=new Integer[list.length/2][],list2=new Integer[list.length-list1.length][];System.arraycopy(list,0,list1,0,list1.length);System.arraycopy(list,list1.length,list2,0,list2.length);deepSort(list1);deepSort(list2);ArrayList<Integer[]> newList=new ArrayList<Integer[]>();int i=0,j=0;while(i<list1.length&&j<list2.length){Integer[] temp1=list1[i],temp2=list2[j];if(temp1[0]<temp2[0])newList.add(list1[i++]);else if(temp1[0]==temp2[0]&&temp1[1]<temp2[1])newList.add(list1[i++]);else newList.add(list2[j++]);}while(i<list1.length)newList.add(list1[i++]);while(j<list2.length)newList.add(list2[j++]);for(int k=0;k<list.length;k++)for(int l=0;l<list[k].length;l++)lis[k][l]=newList.get(k)[l];}
	public static Integer[][] changeIntDim2(int[][] list){Integer[][] ans=new Integer[list.length][];for(int i=0;i<list.length;i++){ans[i]=new Integer[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}
	public static int[][] changeintDim2(Integer[][] list){int[][] ans=new int[list.length][];for(int i=0;i<list.length;i++){ans[i]=new int[list[i].length];for(int j=0;j<list[i].length;j++)ans[i][j]=list[i][j];}return ans;}
	public static Integer[] changeIntDim1(int[] list){Integer[] ans=new Integer[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static int[] changeintDim1(Integer[] list){int[] ans=new int[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static int[] changeintDim1(long[] list){int[] ans=new int[list.length];for(int i=0;i<list.length;i++)ans[i]=(int)list[i];return ans;}
	public static long[] changelongDim1(int[] list){long[] ans=new long[list.length];for(int i=0;i<list.length;i++)ans[i]=list[i];return ans;}
	public static int[] prime(int num){ArrayList<Integer> list=new ArrayList<Integer>();list.add(2);for(int i=3;i<=num;i+=2){boolean isPrime=true;for(int j=0;j<list.size();j++)if((i%list.get(j))==0){isPrime=false;break;}if(isPrime)list.add(i);}int[] ans=new int[list.size()];for(int i=0;i<ans.length;i++)ans[i]=list.get(i);return ans;}
	public static int[] primeL(int num){ArrayList<Integer> list=new ArrayList<Integer>();list.add(2);if(num<=500000)return prime(num);int[] te=prime(500000);for(int i=0;i<te.length;i++)list.add(te[i]);BigInteger limit=BigInteger.valueOf(num);BigInteger bi=BigInteger.valueOf(500000);while(true){bi=bi.nextProbablePrime();if(bi.compareTo(limit)>0)break;list.add((int)bi.longValue());}int[] ans=new int[list.size()];for(int i=0;i<ans.length;i++)ans[i]=list.get(i);return ans;}
}