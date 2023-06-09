import java.io.*;
import java.util.*;
import java.math.*;
class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);

	static int N,K;
	static ArrayList<Integer[]> answer;
	static boolean[][] isMarked;
	static boolean[][] originalMark;
	static LinkedList<Integer> numberStacker = new LinkedList<Integer>();
	static LinkedList<Integer> nextStacker = new LinkedList<Integer>();
	static long maxScore = 0;
	static int masterK = 0;
	static ArrayList<Integer[]> masterAnswer;
	static int slip = 0;

	static Random dice = new Random(0);

	public static void main(String[] args)throws IOException{

		long startTime = java.lang.System.nanoTime();

		//入力受け取り
		N = System.in.nextInt();
		int M = System.in.nextInt();
		isMarked = new boolean[N][N];
		originalMark = new boolean[N][N];
		while(M-->0){
			int x = System.in.nextInt();
			int y = System.in.nextInt();
			isMarked[x][y] = true;
			originalMark[x][y] = true;
		}
		for(int i=0;i<N;i++)numberStacker.addLast(i);

		//出力用
		K = 0;
		answer = new ArrayList<Integer[]>();

		//何か上手いことやる
		Search:while((java.lang.System.nanoTime()-startTime)/1e9<4.8){
			if(slip>=N*2){
				reset();
				long temp = score();
				if(temp>maxScore){
					maxScore = temp;
					masterAnswer = answer;
					masterK = K;
				}
				for(int i=0;i<N;i++){
					for(int j=0;j<N;j++){
						isMarked[i][j] = originalMark[i][j];
					}
				}
				answer = new ArrayList<Integer[]>();
				K = 0;
				slip = 0;
			}
			for(int i:numberStacker){
				for(int j=i;j<N-i;j++){
					if(isMarked[i][j])
						continue;
					makeObliqueRect(i,j);
					if(isMarked[i][j]){
						slip = 0;
						continue Search;
					}
					makeStraightRect(i,j);
					if(isMarked[i][j]){
						slip = 0;
						continue Search;
					}
				}
				for(int j=i;j<N-i;j++){
					if(isMarked[N-i-1][j])
						continue;
					makeObliqueRect(N-i-1,j);
					if(isMarked[N-i-1][j]){
						slip = 0;
						continue Search;
					}
					makeStraightRect(N-i-1,j);
					if(isMarked[N-i-1][j]){
						slip = 0;
						continue Search;
					}
				}
				for(int j=i+1;j<N-i-1;j++){
					if(isMarked[j][i])
						continue;
					makeObliqueRect(j,i);
					if(isMarked[j][i]){
						slip = 0;
						continue Search;
					}
					makeStraightRect(j,i);
					if(isMarked[j][i]){
						slip = 0;
						continue Search;
					}
				}
				for(int j=i+1;j<N-i-1;j++){
					if(isMarked[j][N-i-1])
						continue;
					makeObliqueRect(j,N-i-1);
					if(isMarked[j][N-i-1]){
						slip = 0;
						continue Search;
					}
					makeStraightRect(j,N-i-1);
					if(isMarked[j][N-i-1]){
						slip = 0;
						continue Search;
					}
				}
				slip++;
			}
		}
		//何か上手いことやった

		//出力
		System.out.println(masterK);
		for(Integer[] temp:masterAnswer){
			System.out.print(temp[0]);
			System.out.print(' ');
			System.out.print(temp[1]);
			System.out.print(' ');

			System.out.print(temp[2]);
			System.out.print(' ');
			System.out.print(temp[3]);
			System.out.print(' ');

			System.out.print(temp[4]);
			System.out.print(' ');
			System.out.print(temp[5]);
			System.out.print(' ');

			System.out.print(temp[6]);
			System.out.print(' ');
			System.out.println(temp[7]);
		}
		System.out.close();
	}

	//xとy逆にしちゃったりLeftとRight逆になってたり正負が逆だったりするけど
	//直すのめんどくさいからいいや()

	private static void makeStraightRect(int x1,int y1){
		int y2 = getLeftP(x1,y1);
		int x3 = getDownP(x1,y1);
		if(y2!=N&&x3!=N&&isMarked[x3][y2]){
			if(straightCheck(x1,y1,x1,y2,x3,y2,x3,y1)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x1,y2,x3,y2,x3,y1};
				answer.add(temp);
				K++;
				return;
			}
		}
		y2 = getRightP(x1,y1);
		if(y2!=-1&&x3!=N&&isMarked[x3][y2]){
			if(straightCheck(x1,y1,x1,y2,x3,y2,x3,y1)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x1,y2,x3,y2,x3,y1};
				answer.add(temp);
				K++;
				return;
			}
		}
		x3 = getUpP(x1,y1);
		if(y2!=-1&&x3!=-1&&isMarked[x3][y2]){
			if(straightCheck(x1,y1,x1,y2,x3,y2,x3,y1)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x1,y2,x3,y2,x3,y1};
				answer.add(temp);
				K++;
				return;
			}
		}
		y2 = getLeftP(x1,y1);
		if(y2!=N&&x3!=-1&&isMarked[x3][y2]){
			if(straightCheck(x1,y1,x1,y2,x3,y2,x3,y1)){
				isMarked[x1][y1] = true;
				Integer[] temp = new Integer[]{x1,y1,x1,y2,x3,y2,x3,y1};
				answer.add(temp);
				K++;
				return;
			}
		}
	}
	private static void makeObliqueRect(int x1,int y1){
		int x2 = x1-getUpObliqueR(x1,y1);
		int y2 = y1-(x2-x1);
		int x4 = x1+getDownObliqueR(x1,y1);
		int y4 = y1+(x4-x1);
		int x3 = x2+(x4-x1);
		int y3 = y2+(y4-y1);
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
		x1 = x1+getDownObliqueL(x2,y2);
		y1 = y2+(x2-x1);
		x4 = x1+getDownObliqueR(x1,y1);
		y4 = y1+(x4-x1);
		x3 = x2+(x4-x1);
		y3 = y2+(x4-x1);
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
		x2 = x3-getUpObliqueL(x3,y3);
		y2 = y3-(x3-x2);
		x1 = x2+getDownObliqueL(x2,y2);
		y1 = y2-(x1-x2);
		x4 = x1+(x3-x2);
		y4 = y1+(x3-x2);
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
		x1 = x4-getUpObliqueL(x4,y4);
		y1 = y4-(x4-x1);
		x2 = x1-getUpObliqueR(x1,y1);
		y2 = y1+(x1-x2);
		x3 = x2+(x4-x1);
		y3 = y2+(x4-x1);
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
		int sub = 0;
		while(x1-x2>++sub){
			if(isMarked[x1-sub][y1+sub])
				return false;
			if(isMarked[x4-sub][y4+sub])
				return false;
		}
		sub = x4-x1;
		while(0<--sub){
			if(isMarked[x1+sub][y1+sub])
				return false;
			if(isMarked[x2+sub][y2+sub])
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
		y++;
		while(y<N&&!isMarked[x][y])y++;
		return y;
	}
	private static int getRightP(int x,int y){
		y--;
		while(y>=0&&!isMarked[x][y])y--;
		return y;
	}
	private static int getDownP(int x,int y){
		x++;
		while(x<N&&!isMarked[x][y])x++;
		return x;
	}
	private static int getUpP(int x,int y){
		x--;
		while(x>=0&&!isMarked[x][y])x--;
		return x;
	}
	private static int getUpObliqueR(int x,int y){
		x--;
		y++;
		int ans = 1;
		while(x>=0&&y<N&&!isMarked[x][y]){
			x--;
			y++;
			ans++;
		}
		return ans;
	}
	private static int getDownObliqueR(int x,int y){
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
	private static int getUpObliqueL(int x,int y){
		x--;
		y--;
		int ans = 1;
		while(x>=0&&y>=0&&!isMarked[x][y]){
			x--;
			y--;
			ans++;
		}
		return ans;
	}
	private static int getDownObliqueL(int x,int y){
		x++;
		y--;
		int ans = 1;
		while(x<N&&y>=0&&!isMarked[x][y]){
			x++;
			y--;
			ans++;
		}
		return ans;
	}
	public static void reset(){
		for(Integer i:numberStacker){
			if(dice.nextInt(2)==0)
				nextStacker.addLast(i);
			else
				nextStacker.addFirst(i);
		}
		numberStacker = nextStacker;
		nextStacker = new LinkedList<Integer>();
	}
	public static long score(){
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
	* My Library *

	@auther viral
*/////////////////////////////////////////////////
class Library{
	SimpleScanner in;
	SimplePrinter out;
	private long[] Factorials,InFactorials;
	public long mod = 0;
	public Library(InputStream in,OutputStream out){
		this.in = new SimpleScanner(in);
		this.out = new SimplePrinter(out);
	}
	public Library(InputStream in,OutputStream out,int fac,long mod){
		this(in,out);
		Factorials=new long[fac+1];
		Factorials[1]=1;
		for(int i=2;i<=fac;i++)Factorials[i]=Factorials[i-1]*i%mod;
		InFactorials=new long[fac+1];
		InFactorials[fac]=modPow(Factorials[fac],mod-2,mod);
		for(int i=fac;i>1;i--)InFactorials[i-1]=InFactorials[i]*i%mod;
		this.mod = mod;
	}

	/**
	 * コンストラクタで渡された値までの
	 * num!、aCbをmodで割ったあまりを返します。
	 */
	public long getFact(int num){
		return Factorials[num];
	}
	public long getCombi(int a,int b){
		return (Factorials[a]*InFactorials[a-b]%mod)*InFactorials[b]%mod;
	}

	/**
	 * factではa!を、
	 * modFactでは引数のmodかコンストラクタで渡されたmodで
	 * 割ったあまりを返します。
	 */
	public static long fact(long a){
		long ans=1;
		for(long i=2;i<=a;i++)ans*=i;
		return ans;
	}
	public static long modFact(long a,long mod){
		long ans=1;
		for(long i=2;i<=a;i++){
			ans*=i%mod;
			ans%=mod;
		}
		return ans;
	}
	public long modFact(long a){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans=1;
		for(long i=2;i<=a;i++){
			ans*=i%mod;
			ans%=mod;
		}
		return ans;
	}

	/**
	 * CombiではaCb、
	 * modCombiでは引数のmodかコンストラクタで渡されたmodで
	 * 割ったあまりを返します。
	 */
	public static long combi(long a,long b){
		long ans=1;
		if(b==0||b==a)return 1;
		if(b<0||b>a)return 0;
		b=Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans*=a--;
			ans/=i;
		}
		return ans;
	}
	public static long modCombi(long a,long b,long mod){
		long ans=1;
		if(b==0||b==a)return 1;
		if(b<0||b>a)return 0;
		b=Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans*=a--;
			ans%=mod;
			ans*=modPow(i,mod-2,mod);
			ans%=mod;
		}
		return ans;
	}
	public long modCombi(long a,long b){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans=1;
		if(b==0||b==a)return 1;
		if(b<0||b>a)return 0;
		b=Math.min(a-b,b);
		for(long i=1;i<=b;i++){
			ans*=a--;
			ans%=mod;
			ans*=modPow(i,mod-2,mod);
			ans%=mod;
		}
		return ans;
	}

	/**
	 * int、long型配列をソートします。
	 * 二次元配列の場合は[i][0]と[i][1]の大きさで
	 * 昇順に並べ替えます。
	 */
	public static void sort(int[] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&list[(j-1)/2]<list[j]){
				int temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			int temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&list[j]<list[2*j+1])||(2*j+2<i-1&&list[j]<list[2*j+2])){
				if(2*j+2==i-1||list[2*j+1]>list[2*j+2]){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	public static void sort(int[][] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&(list[(j-1)/2][0]<list[j][0]||(list[(j-1)/2][0]==list[j][0]&&list[(j-1)/2][1]<list[j][1]))){
				int[] temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			int[] temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&(list[j][0]<list[2*j+1][0]||(list[j][0]==list[2*j+1][0]&&list[j][1]<list[2*j+1][1])))||
				(2*j+2<i-1&&(list[j][0]<list[2*j+2][0]||(list[j][0]==list[2*j+2][0]&&list[j][1]<list[2*j+2][1])))){
				if(2*j+2==i-1||(list[2*j+1][0]>list[2*j+2][0]||(list[2*j+1][0]==list[2*j+2][0]&&list[2*j+1][1]>list[2*j+2][1]))){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	public static void sort(long[] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&list[(j-1)/2]<list[j]){
				long temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			long temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&list[j]<list[2*j+1])||(2*j+2<i-1&&list[j]<list[2*j+2])){
				if(2*j+2==i-1||list[2*j+1]>list[2*j+2]){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}
	public static void sort(long[][] list){
		for(int i=0;i<list.length;i++){
			int j=i;
			while(j>0&&(list[(j-1)/2][0]<list[j][0]||(list[(j-1)/2][0]==list[j][0]&&list[(j-1)/2][1]<list[j][1]))){
				long[] temp=list[(j-1)/2];
				list[(j-1)/2]=list[j];
				list[j]=temp;
				j=(j-1)/2;
			}
		}
		for(int i=list.length;i>0;i--){
			long[] temp=list[i-1];
			list[i-1]=list[0];
			list[0]=temp;
			int j=0;
			while((2*j+1<i-1&&(list[j][0]<list[2*j+1][0]||(list[j][0]==list[2*j+1][0]&&list[j][1]<list[2*j+1][1])))||
				(2*j+2<i-1&&(list[j][0]<list[2*j+2][0]||(list[j][0]==list[2*j+2][0]&&list[j][1]<list[2*j+2][1])))){
				if(2*j+2==i-1||(list[2*j+1][0]>list[2*j+2][0]||(list[2*j+1][0]==list[2*j+2][0]&&list[2*j+1][1]>list[2*j+2][1]))){
					temp=list[2*j+1];
					list[2*j+1]=list[j];
					list[j]=temp;
					j<<=1;
					j++;
				}
				else{
					temp=list[2*j+2];
					list[2*j+2]=list[j];
					list[j]=temp;
					j<<=1;
					j+=2;
				}
			}
		}
	}

	/**
	 * int型配列をソートします。
	 * 最大値が小さい場合は有効です。
	 */
	public static void countSort(int[] nums,int limit){
		int[] list=new int[limit+1];
		for(int i=0;i<nums.length;i++)list[nums[i]]++;
		int temp=0;
		for(int i=0;i<list.length;i++)for(int j=0;j<list[i];j++)nums[temp++]=i;
	}

	/**
	 * gcdは最大公約数を、
	 * lcmは最小公倍数を返します。
	 * lcmはオーバーフローの可能性があるのでご注意を。
	 * 戻り値はlong型です。
	 */
	public static long gcd(long a,long b){
		if(a==0||b==0)
			return Math.max(a,b);
		long temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return b;
	}
	public static long lcm(long a,long b){
		if(a==0||b==0)
			return 0;
		long mult=a*b,temp;
		while((temp=a%b)!=0){
			a=b;
			b=temp;
		}
		return mult/b;
	}

	/**
	 * BigIntegerクラスのものを使用します。
	 * 素数である確率が高いならtrue、
	 * 確実に合成数ならfalseを返します。
	 * 誤判定の確率は1/2^20以下です。
	 */
	public static boolean isPrime(long num){
		return BigInteger.valueOf(num).isProbablePrime(20);
	}

	/**
	 * 引数まで(引数含む)の素数を返します。
	 * 2^30以上の素数列挙はできないのでご注意を。
	 */
	public static int[] prime(int num){
		BitSet nums=new BitSet(num+1);
		nums.set(2,num+1);
		for(int i=2;i<=Math.sqrt(num);i++)if(nums.get(i))for(int j=i*i;j<=num;j+=i)nums.clear(j);
		int[] answer=new int[nums.cardinality()];
		int i=2,index=0;
		while(true){
			i=nums.nextSetBit(i);
			answer[index++]=i++;
			if(index==answer.length)break;
		}
		return answer;
	}

	/**
	 * powではそのままの結果を、
	 * modPowでは引数のmodかコンストラクタで渡されたmodで
	 * 割ったあまりを返します。
	 */
	public static long pow(long a,long b){
		long ans=1;
		while(b>0){
			if((b&1)==1)ans*=a;
			a*=a;
			b>>=1;
		}
		return ans;
	}
	public static long modPow(long a,long b,long mod){
		long ans=1;
		a%=mod;
		while(b>0){
			if((b&1)==1)ans*=a;
			ans%=mod;
			a*=a;
			a%=mod;
			b>>=1;
		}
		return ans;
	}
	public long modPow(long a,long b){
		if(mod==0){
			System.err.println("\u001b[00;31m"+"#mod is not defined#");
			System.exit(1);
		}
		long ans=1;
		a%=mod;
		while(b>0){
			if((b&1)==1)ans*=a;
			ans%=mod;
			a*=a;
			a%=mod;
			b>>=1;
		}
		return ans;
	}

	/**
	 * Stringをint、longに変換します。
	 * 数値以外の文字であっても無理矢理数値変換してしまうので
	 * 使用の際はご注意を。
	 */
	public static int parseInt(String str){
		char[] nums=str.toCharArray();
		int ans=0;
		boolean plus=true;
		if(nums[0]=='-'){
			plus=!plus;
			nums[0]='0';
		}
		for(int i=0;i<nums.length;i++)ans=ans*10+nums[i]-'0';
		return plus?ans:-ans;
	}
	public static long parseLong(String str){
		char[] nums=str.toCharArray();
		long ans=0;
		boolean plus=true;
		if(nums[0]=='-'){
			plus=!plus;
			nums[0]='0';
		}
		for(int i=0;i<nums.length;i++)ans=ans*10+nums[i]-'0';
		return plus?ans:-ans;
	}

	/**
	 * downsearchではnum以下を、
	 * upsearchではnum以上を探します。
	 * 引数の配列(List)のインデックスを返します。
	 */
	public static int downsearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(int[] nums,int num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(long[] nums,long num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(Integer[] nums,int num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(Integer[] nums,int num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(Long[] nums,long num){
		int a=0,b=nums.length-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(Long[] nums,long num){
		int a=0,b=nums.length-1,ans=nums.length,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums[c]>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(ArrayList<Integer> nums,int num){
		int a=0,b=nums.size()-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(ArrayList<Integer> nums,int num){
		int a=0,b=nums.size()-1,ans=nums.size(),c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}
	public static int downsearch(ArrayList<Long> nums,long num){
		int a=0,b=nums.size()-1,ans=-1,c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>num)b=c-1;
			else a=(ans=c)+1;
		}
		return ans;
	}
	public static int upsearch(ArrayList<Long> nums,long num){
		int a=0,b=nums.size()-1,ans=nums.size(),c=-1;
		while(a-b<1){
			c=(a+b)/2;
			if(nums.get(c)>=num)b=(ans=c)-1;
			else a=c+1;
		}
		return ans;
	}

	public void exit(int num){
		try{
			in.close();
			out.close();
		}catch(Exception e){
		}finally{
			System.exit(num);
		}
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
