import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	private static int N,K;
	private static ArrayList<Integer[]> answer;
	private static boolean[][] isMarked;
	private static LinkedList<Integer> numberStacker = new LinkedList<Integer>();

	private static Random dice = new Random();

	public static void main(String[] args)throws IOException{

		long startTime = System.nanoTime();

		SimpleScanner in = new SimpleScanner(System.in);
		SimplePrinter out = new SimplePrinter(System.out);

		//入力受け取り
		N = in.nextInt();
		int M = in.nextInt();
		isMarked = new boolean[N][N];
		boolean[][] originalMark = new boolean[N][N];
		while(M-->0){
			int x = in.nextInt();
			int y = in.nextInt();
			isMarked[x][y] = true;
			originalMark[x][y] = true;
		}
		for(int i=0;i<N;i++)numberStacker.addLast(i);

		//出力用
		answer = new ArrayList<Integer[]>();
		int masterK = 0;
		ArrayList<Integer[]> masterAnswer = null;
		long maxScore = 0;

		//何か上手いことやる
		boolean slip = false;
		while((java.lang.System.nanoTime()-startTime)/1e9<4.85){
			if(slip){
				reset();
				long temp = score();
				if(temp>maxScore){
					maxScore = temp;
					masterAnswer = answer;
					masterK = K;
				}
				for(int i=0;i<N;i++){
					System.arraycopy(originalMark[i],0,isMarked[i],0,N);
				}
				answer = new ArrayList<Integer[]>();
				K = 0;
			}
			slip = true;
			for(int i:numberStacker){
				int limit = N-i-1;
				for(int j=i;j<=limit;j++){
					if(isMarked[i][j])
						continue;
					makeObliqueRect(i,j);
					if(isMarked[i][j]){
						slip = false;
						continue;
					}
					makeStraightRect(i,j);
					if(isMarked[i][j])
						slip = false;
				}
				for(int j=i;j<=limit;j++){
					if(isMarked[limit][j])
						continue;
					makeObliqueRect(limit,j);
					if(isMarked[limit][j]){
						slip = false;
						continue;
					}
					makeStraightRect(limit,j);
					if(isMarked[limit][j])
						slip = false;
				}
				for(int j=i+1;j<limit;j++){
					if(isMarked[j][i])
						continue;
					makeObliqueRect(j,i);
					if(isMarked[j][i]){
						slip = false;
						continue;
					}
					makeStraightRect(j,i);
					if(isMarked[j][i])
						slip = false;
				}
				for(int j=i+1;j<limit;j++){
					if(isMarked[j][limit])
						continue;
					makeObliqueRect(j,limit);
					if(isMarked[j][limit]){
						slip = false;
						continue;
					}
					makeStraightRect(j,limit);
					if(isMarked[j][N-i-1])
						slip = false;
				}
			}
		}
		//何か上手いことやった

		//出力
		System.out.println(masterK);
		for(Integer[] temp:masterAnswer){
			out.println(temp," ");
		}
		out.close();
	}

	private static void makeStraightRect(int x1,int y1){
		int x2 = getLeftP(x1,y1);
		int y3 = getDownP(x1,y1);
		if(x2!=-1&&y3!=-1&&isMarked[x2][y3]){
			if(straightCheck(x1,y1,x2,y1,x2,y3,x1,y3)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				answer.add(temp);
				K++;
				return;
			}
		}
		x2 = getRightP(x1,y1);
		if(x2!=N&&y3!=-1&&isMarked[x2][y3]){
			if(straightCheck(x1,y1,x2,y1,x2,y3,x1,y3)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				answer.add(temp);
				K++;
				return;
			}
		}
		y3 = getUpP(x1,y1);
		if(x2!=N&&y3!=N&&isMarked[x2][y3]){
			if(straightCheck(x1,y1,x2,y1,x2,y3,x1,y3)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				answer.add(temp);
				K++;
				return;
			}
		}
		x2 = getLeftP(x1,y1);
		if(x2!=-1&&y3!=N&&isMarked[x2][y3]){
			if(straightCheck(x1,y1,x2,y1,x2,y3,x1,y3)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				answer.add(temp);
				K++;
				return;
			}
		}
	}
	private static void makeObliqueRect(int x1,int y1){
		int sub1 = getUpObliqueR(x1,y1);
		int sub2 = getDownObliqueR(x1,y1);
		int x2 = x1+sub1;
		int y2 = y1+sub1;
		int x4 = x1+sub2;
		int y4 = y1-sub2;
		int x3 = x2+sub2;
		int y3 = y2-sub2;
		if(0<=x2&&x2<N&&0<=y2&&y2<N&&
		   0<=x3&&x3<N&&0<=y3&&y3<N&&
		   0<=x4&&x4<N&&0<=y4&&y4<N&&
		   isMarked[x3][y3]){
			if(obliqueCheck(x1,y1,x2,y2,x3,y3,x4,y4)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x2,y2,x3,y3,x4,y4};
				answer.add(temp);
				K++;
				return;
			}
		}
		x2 = x1;
		y2 = y1;
		sub1 = getDownObliqueL(x2,y2);
		x1 = x2-sub1;
		y1 = y2-sub1;
		sub2 = getDownObliqueR(x1,y1);
		x4 = x1+sub2;
		y4 = y1-sub2;
		x3 = x2+sub2;
		y3 = y2-sub2;
		if(0<=x1&&x1<N&&0<=y1&&y1<N&&
		   0<=x3&&x3<N&&0<=y3&&y3<N&&
		   0<=x4&&x4<N&&0<=y4&&y4<N&&
		   isMarked[x3][y3]){
			if(obliqueCheck(x1,y1,x2,y2,x3,y3,x4,y4)){
				isMarked[x2][y2] = true;
				Integer[] temp = new Integer[]{x2,y2,x3,y3,x4,y4,x1,y1};
				answer.add(temp);
				K++;
				return;
			}
		}
		x3 = x2;
		y3 = y2;
		sub1 = getUpObliqueL(x3,y3);
		x2 = x3-sub1;
		y2 = y3+sub1;
		sub2 = getDownObliqueL(x2,y2);
		x1 = x2-sub2;
		y1 = y2-sub2;
		x4 = x1+sub1;
		y4 = y1-sub1;
		if(0<=x1&&x1<N&&0<=y1&&y1<N&&
		   0<=x2&&x2<N&&0<=y2&&y2<N&&
		   0<=x4&&x4<N&&0<=y4&&y4<N&&
		   isMarked[x4][y4]){
			if(obliqueCheck(x1,y1,x2,y2,x3,y3,x4,y4)){
				isMarked[x3][y3] = true;
				Integer[] temp = new Integer[]{x3,y3,x4,y4,x1,y1,x2,y2};
				answer.add(temp);
				K++;
				return;
			}
		}
		x4 = x3;
		y4 = y3;
		sub1 = getUpObliqueL(x4,y4);
		x1 = x4-sub1;
		y1 = y4+sub1;
		sub2 = getUpObliqueR(x1,y1);
		x2 = x1+sub2;
		y2 = y1+sub2;
		x3 = x2+sub1;
		y3 = y2-sub1;
		if(0<=x1&&x1<N&&0<=y1&&y1<N&&
		   0<=x2&&x2<N&&0<=y2&&y2<N&&
		   0<=x3&&x3<N&&0<=y3&&y3<N&&
		   isMarked[x3][y3]){
			if(obliqueCheck(x1,y1,x2,y2,x3,y3,x4,y4)){
				isMarked[x4][y4] = true;
				Integer[] temp = new Integer[]{x4,y4,x1,y1,x2,y2,x3,y3};
				answer.add(temp);
				K++;
				return;
			}
		}
	}
	private static boolean straightCheck(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		for(int i=Math.min(x1,x3)+1;i<Math.max(x1,x3);i++){
			if(isMarked[i][y1]||isMarked[i][y3])
				return false;
		}
		for(int i=Math.min(y1,y3)+1;i<Math.max(y1,y3);i++){
			if(isMarked[x1][i]||isMarked[x3][i])
				return false;
		}
		return isOrigin(x1,y1,x2,y2,x3,y3,x4,y4);
	}
	private static boolean obliqueCheck(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		int sub = x2-x1;
		while(0<--sub){
			if(isMarked[x1+sub][y1+sub])
				return false;
			if(isMarked[x4+sub][y4+sub])
				return false;
		}
		sub = x4-x1;
		while(0<--sub){
			if(isMarked[x1+sub][y1-sub])
				return false;
			if(isMarked[x2+sub][y2-sub])
				return false;
		}
		return isOrigin(x1,y1,x2,y2,x3,y3,x4,y4);
	}
	private static boolean isOrigin(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		for(Integer[] rect:answer){
			for(int i=0;i<7;i+=2){
				if(isOverlapped(x1,y1,x2,y2,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;
				if(isOverlapped(x2,y2,x3,y3,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;
				if(isOverlapped(x3,y3,x4,y4,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;
				if(isOverlapped(x1,y1,x4,y4,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;
			}
		}
		return true;
	}
	private static boolean isOverlapped(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){
		//1:縦にまっすぐ
		//2:横にまっすぐ
		//3:斜め
		int type1 = x1==x2 ? 1 : y1==y2 ? 2 : 3;
		int type2 = x3==x4 ? 1 : y3==y4 ? 2 : 3;

		boolean result = false;
		if(type1==3&&type2==3){
			result = (y2-y1)/(x2-x1)==(y4-y3)/(x4-x3);
			int s1 = (x1-x2)*(y3-y1)-(y1-y2)*(x3-x1);
			int t1 = (x1-x2)*(y4-y1)-(y1-y2)*(x4-x1);
			int s2 = (x3-x4)*(y1-y3)-(y3-y4)*(x1-x3);
			int t2 = (x3-x4)*(y2-y3)-(y3-y4)*(x2-x3);
			result &= s1*t1==0&&s2*t2==0;
			if(result)
				result &= !(Math.max(y1,y2)<=Math.min(y3,y4)||Math.max(y3,y4)<=Math.min(y1,y2));
		}
		return type1==type2&&
			(type1==1 ? x1==x3&&!(Math.max(y1,y2)<=Math.min(y3,y4)||Math.max(y3,y4)<=Math.min(y1,y2)) :
			 type1==2 ? y1==y3&&!(Math.max(x1,x2)<=Math.min(x3,x4)||Math.max(x3,x4)<=Math.min(x1,x2)) :
			 result);
	}
	private static int getLeftP(int x,int y){
		x--;
		while(x>=0&&!isMarked[x][y])x--;
		return x;
	}
	private static int getRightP(int x,int y){
		x++;
		while(x<N&&!isMarked[x][y])x++;
		return x;
	}
	private static int getDownP(int x,int y){
		y--;
		while(y>=0&&!isMarked[x][y])y--;
		return y;
	}
	private static int getUpP(int x,int y){
		y++;
		while(y<N&&!isMarked[x][y])y++;
		return y;
	}
	private static int getUpObliqueR(int x,int y){
		if(x<0||y<0)
			return N+1;
		x++;
		y++;
		int ans = 1;
		while(x<N&&y<N&&!isMarked[x][y]){
			x++;
			y++;
			ans++;
		}
		return ans;
	}
	private static int getDownObliqueR(int x,int y){
		if(x<0||N<=y)
			return N+1;
		x++;
		y--;
		int ans = 1;
		while(x<N&&0<=y&&!isMarked[x][y]){
			x++;
			y--;
			ans++;
		}
		return ans;
	}
	private static int getUpObliqueL(int x,int y){
		if(N<=x||y<0)
			return N+1;
		x--;
		y++;
		int ans = 1;
		while(0<=x&&y<N&&!isMarked[x][y]){
			x--;
			y++;
			ans++;
		}
		return ans;
	}
	private static int getDownObliqueL(int x,int y){
		if(N<=x||N<=y)
			return N+1;
		x--;
		y--;
		int ans = 1;
		while(0<=x&&0<=y&&!isMarked[x][y]){
			x--;
			y--;
			ans++;
		}
		return ans;
	}
	private static void reset(){
		LinkedList<Integer> nextStacker = new LinkedList<Integer>();
		for(Integer i:numberStacker){
			if(dice.nextInt(2)==0)
				nextStacker.addLast(i);
			else
				nextStacker.addFirst(i);
		}
		numberStacker = nextStacker;
	}
	private static long score(){
		int c = (N-1)/2;
		long answer = 0;
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(isMarked[i][j])
					answer += (i-c)*(i-c)+(j-c)*(j-c)+1;
			}
		}
		return answer;
	}
}
/*////////////////////////////////////////////////
	* My Scanner *

	@auther viral
*/////////////////////////////////////////////////
class SimpleScanner{
	final private int buff_size = 1<<12;
	private InputStream is;
	private byte[] buff;
	private int point,length;
	public SimpleScanner(InputStream is){
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private void reload()throws IOException{
		do{
			length = is.read(buff,point = 0,buff_size);
		}while(length==-1);
	}
	private byte read()throws IOException{
		if(point==length)reload();
		return buff[point++];
	}
	public byte nextByte()throws IOException{
		byte c = read();
		while(c<=' ')c = read();
		return c;
	}
	public int nextInt()throws IOException{
		int ans = 0;
		byte c = read();
		while(c<=' ')c = read();
		boolean negate = c == '-';
		if(c=='-')c = read();
		while('0'<=c&&c<='9'){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public long nextLong()throws IOException{
		long ans = 0;
		byte c = read();
		while(c<=' ')c = read();
		boolean negate = c == '-';
		if(c=='-')c = read();
		while('0'<=c&&c<='9'){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public char nextChar()throws IOException{
		byte c = read();
		while(c<=' ')c = read();
		return (char)c;
	}
	public String next()throws IOException{
		StringBuilder ans = new StringBuilder();
		byte c = read();
		while(c<=' ')c = read();
		while(c>' '){
			ans.append((char)c);
			c = read();
		}
		return ans.toString();
	}
	public byte[] nextByte(int n)throws IOException{
		byte[] ans = new byte[n];
		for(int i=0;i<n;i++){
			ans[i] = nextByte();
		}
		return ans;
	}
	public int[] nextInt(int n)throws IOException{
		int[] ans = new int[n];
		for(int i=0;i<n;i++){
			ans[i] = nextInt();
		}
		return ans;
	}
	public long[] nextLong(int n)throws IOException{
		long[] ans = new long[n];
		for(int i=0;i<n;i++){
			ans[i] = nextLong();
		}
		return ans;
	}
	public String[] next(int n)throws IOException{
		String[] ans = new String[n];
		for(int i=0;i<n;i++){
			ans[i] = next();
		}
		return ans;
	}
	public byte[][] nextByte(int n,int m)throws IOException{
		byte[][] ans = new byte[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextByte(m);
		}
		return ans;
	}
	public int[][] nextInt(int n,int m)throws IOException{
		int[][] ans = new int[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextInt(m);
		}
		return ans;
	}
	public long[][] nextLong(int n,int m)throws IOException{
		long[][] ans = new long[n][];
		for(int i=0;i<n;i++){
			ans[i] = nextLong(m);
		}
		return ans;
	}
	public String[][] next(int n,int m)throws IOException{
		String[][] ans = new String[n][];
		for(int i=0;i<n;i++){
			ans[i] = next(m);
		}
		return ans;
	}
	public char[] nextCharArray()throws IOException{
		return next().toCharArray();
	}
	public char[][] nextCharArray(int n)throws IOException{
		char[][] ans = new char[n][];
		for(int i=0;i<n;i++){
			ans[i] = next().toCharArray();
		}
		return ans;
	}
	public void close()throws IOException{
		is.close();
	}
}
/*////////////////////////////////////////////////
	* My Printer *

	@auther viral
*/////////////////////////////////////////////////
class SimplePrinter extends PrintWriter{
	public SimplePrinter(OutputStream os){
		super(os);
	}
	public void println(byte[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(int[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(long[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Byte[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Integer[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(Long[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
	public void println(String[] strs,String str){
		println(String.join(str,strs));
	}
	public void println(char[] cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(str);
			print(cs[i]);
		}
		println();
	}
	public void println(char[][] cs){
		for(int i=0;i<cs.length;i++){
			println(cs[i]);
		}
	}
	public void println(char[][] cs,String str){
		print(cs[0]);
		for(int i=1;i<cs.length;i++){
			print(str);
			print(cs[i]);
		}
		println();
	}
}
