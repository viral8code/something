import java.io.*;
import java.util.*;
class Main{

	private static int N,K;
	private static LinkedList<Integer[]> answer;
	private static boolean[][] isMarked;
	private static LinkedList<Integer> numberStacker = new LinkedList<Integer>();

	private static boolean takeSmaller = false;
	private static Random dice = new Random();

	public static void main(String[] args){

		//タイマーをセット
		Timer timer = new Timer();

		//入力受け取り
		inputData();
		boolean[][] originalMark = copyMap();

		//numberStackerの作製
		for(int i=0;i<N;i++)numberStacker.addLast(i);

		//出力用
		answer = new LinkedList<Integer[]>();
		int masterK = 0;
		LinkedList<Integer[]> masterAnswer = null;
		long maxScore = 0;

		//何か上手いことやる
		boolean slip = false;
		int limiter = 0;
		while(timer.getTime()<4.5){

			//今打てる場所全列挙
			ArrayDeque<Integer[]> list = canMarks();

			if(list.size()==0)
				break;


			//初期値設定
			int max = Integer.MIN_VALUE;

			Integer[] ans = null;

			int x = 0;
			int y = 0;


			//目先最良の点を探す
			for(Integer[] tmp:list){

				//点を打ったことによる打てる箇所の数の変動
				int flu = nextFluct(tmp)-list.size();

				//最高値更新？
				if(max<flu){

					max = flu;

					x = tmp[0];
					y = tmp[1];

					ans = tmp;

				}
			}

			//点を追加
			isMarked[x][y] = true;
			K++;
			answer.add(ans);

		}

		//何か上手いことやった

		//出力
		outputData();
	}

	//値の受け取り
	public static void inputData(){

		//throws IOExceptionでも良いけどtryで
		//だってmain()にも付けるのめんどくさいんだも～ん
		try{

			//自作Scanner
			SimpleScanner in = new SimpleScanner(System.in);

			//N、M受け取り
			N = in.nextInt();
			int M = in.nextInt();

			//初期化
			isMarked = new boolean[N][N];

			//各点取得
			while(M-->0){

				int x = in.nextInt();
				int y = in.nextInt();

				//印を付ける
				isMarked[x][y] = true;

			}

			//念のため
			in.close();

		}catch(IOException e){

			//なんかあったら吐いてね(ついでにREにしてね)
			System.err.println(e);
			System.exit(1);

		}
	}

	//中心からの距離の二乗(得点)
	public static int dict(Integer[] rect){

		//中心座標
		int c = (N-1)/2;

		//距離の二乗を返す
		return (rect[0]-c)*(rect[0]-c)+(rect[1]-c)*(rect[1]-c);

	}

	//印が付けられる場所全列挙
	public static ArrayDeque<Integer[]> canMarks(){

		//戻り値用
		ArrayDeque<Integer[]> stack = new ArrayDeque<Integer[]>();

		//外側から中心に向かう感じで
		for(int i=0;i<=(N-1)/2;i++){

			//左側
			for(int j=i;j<N-i;j++){

				//既に印付いてる？
				if(isMarked[i][j])
					continue;

				//付けられたらstackに積む
				Integer[] tmp = tryMark(i,j);
				if(tmp!=null)
					stack.add(tmp);

			}

			//右側
			for(int j=i;j<N-i;j++){

				//既に印付いてる？
				if(isMarked[N-i-1][j])
					continue;

				//付けられたらstackに積む
				Integer[] tmp = tryMark(N-i-1,j);
				if(tmp!=null)
					stack.add(tmp);

			}

			//下側
			for(int j=i+1;j<N-i-1;j++){

				//既に印付いてる？
				if(isMarked[j][i])
					continue;

				//付けられたらstackに積む
				Integer[] tmp = tryMark(j,i);
				if(tmp!=null)
					stack.add(tmp);

			}

			//上側
			for(int j=i+1;j<N-i-1;j++){

				//既に印付いてる？
				if(isMarked[j][N-i-1])
					continue;

				//付けられたらstackに積む
				Integer[] tmp = tryMark(j,N-i-1);
				if(tmp!=null)
					stack.add(tmp);

			}
		}

		//結果を返す
		return stack;

	}

	//条件を満たす長方形を探す(印を付けられるならそれを返す)
	public static Integer[] tryMark(int x,int y){

		//srがまっすぐな長方形、orが斜めの長方形
		Integer[] sr = sRect(x,y);
		Integer[] or = oRect(x,y);

		//まっすぐなのが見つからなかった？
		if(sr==null)
			return or;

		//斜めなのが見つからなかった？
		if(or==null)
			return sr;

		//どっちも見つかったなら面積が小さい方を
		int s = areaOfsRect(sr);
		int o = areaOfoRect(sr);

		//同じなら斜めで(なんとなく)
		return s<o ? sr : or;

	}

	//まっすぐな長方形同士で面積を比較
	public static Integer[] compareS(Integer[] rect1,Integer[] rect2,boolean smaller){

		//一個目がnull？
		if(rect1==null)
			return rect2;

		//二個目がnull？
		if(rect2==null)
			return rect1;

		//面積計算
		int a = areaOfsRect(rect1);
		int b = areaOfsRect(rect2);

		//小さい(大きい)方を返す
		return a < b && smaller ? rect1 : rect2;

	}

	//斜めの長方形同士で面積を比較
	public static Integer[] compareO(Integer[] rect1,Integer[] rect2,boolean smaller){

		//一個目がnull？
		if(rect1==null)
			return rect2;

		//二個目がnull？
		if(rect2==null)
			return rect1;

		//面積計算
		int a = areaOfoRect(rect1);
		int b = areaOfoRect(rect2);

		//小さい(大きい)方を返す
		return a < b && smaller ? rect1 : rect2;

	}

	//まっすぐな長方形の面積を返す
	public static int areaOfsRect(Integer[] rect){

		return Math.abs((rect[0]-rect[4])*(rect[1]-rect[5]));

	}

	//斜めの長方形の面積を返す
	public static int areaOfoRect(Integer[] rect){

		//底辺と高さ(斜めのままだと計算しにくいので平行四辺形と正方形×2に分ける)
		int bottom,height;

		//底辺の長さを求める(一番左の点からまっすぐ伸ばして出来る線分を底辺とする平行四辺形)
		//ここでは後々楽なので底辺の長さ/2になっている
		if(Math.abs(rect[0]-rect[6])<Math.abs(rect[0]-rect[2]))
			bottom = Math.abs(rect[0]-rect[6]);
		else
			bottom = Math.abs(rect[0]-rect[2]);

		//上記の平行四辺形の高さを求める(一番左側の点と一番右側の点のy座標の差)
		height = Math.abs(rect[1]-rect[5]);

		//切り出した平行四辺形+残りの部分で作れる正方形×2
		//((2×底辺)×高さ)+(底辺×底辺×2)
		return 2*bottom*height+bottom*bottom*2;

	}

	//まっすぐな長方形を作る
	public static Integer[] sRect(int x1,int y1){

		//戻り値用
		Integer[] ans = null;

		//x1、x1が右上
		int x2 = getLeftP(x1,y1); //左の点を探す
		int y3 = getDownP(x1,y1); //下の点を探す

		//不正値でなく、かつ対角線方向に点はあるか？
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//条件を満たしている？
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//面積比較
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//x1、y1が左上
		x2 = getRightP(x1,y1); //右の点を探す

		//不正値でなく、かつ対角線方向に点はあるか？
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//条件を満たしている？
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//面積比較
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//x1、y1が左下
		y3 = getUpP(x1,y1); //上の点を探す

		//不正値でなく、かつ対角線方向に点はあるか？
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//条件を満たしている？
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//面積比較
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//x1、y1が右下
		x2 = getLeftP(x1,y1); //左側の点を探す

		//不正値でなく、かつ対角線方向に点はあるか？
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//条件を満たしている？
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//面積比較
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}


		//結果を返す
		return ans;

	}

	//左の点を探す
	public static int getLeftP(int x,int y){

		//不正値か点が見つかるまで左に
		x--;
		while(x>=0&&!isMarked[x][y])x--;

		//x座標を返却
		return x;

	}

	//右の点を探す
	public static int getRightP(int x,int y){

		//不正値か点が見つかるまで右に
		x++;
		while(x<N&&!isMarked[x][y])x++;

		//x座標を返却
		return x;

	}

	//下の点を探す
	public static int getDownP(int x,int y){

		//不正値か点が見つかるまで下に
		y--;
		while(y>=0&&!isMarked[x][y])y--;

		//y座標を返却
		return y;

	}

	//上の点を探す
	public static int getUpP(int x,int y){

		//不正値か点が見つかるまで上に
		y++;
		while(y<N&&!isMarked[x][y])y++;

		//y座標を返却
		return y;

	}

	//斜めの長方形を作る
	public static Integer[] oRect(int x1,int y1){

		//戻り値用
		Integer[] ans = null;

		//x1、y1が左
		//x2、y2が上
		//x3、y3が右
		//x4、y4が下

		int sub1 = getUpObliqueR(x1,y1); //上の点を探す
		int sub2 = getDownObliqueR(x1,y1); //下の点を探す

		//上
		int x2 = x1+sub1;
		int y2 = y1+sub1;

		//下
		int x4 = x1+sub2;
		int y4 = y1-sub2;

		//右
		int x3 = x2+sub2;
		int y3 = y2-sub2;

		//適正値かつ右の点は存在する？
		if(isProper(x2,y2) && isProper(x3,y3) && isProper(x4,y4) && isMarked[x3][y3]){

			//条件を満たしている？
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//面積比較
				Integer[] tmp = new Integer[]{x1,y1,x2,y2,x3,y3,x4,y4};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//上
		x2 = x1;
		y2 = y1;

		sub1 = getDownObliqueL(x2,y2); //の点を探す

		//左
		x1 = x2-sub1;
		y1 = y2-sub1;

		sub2 = getDownObliqueR(x1,y1); //下の点を探す

		//下
		x4 = x1+sub2;
		y4 = y1-sub2;

		//右
		x3 = x2+sub2;
		y3 = y2-sub2;

		//適正値かつ右の点は存在する？
		if(isProper(x1,y1) && isProper(x3,y3) && isProper(x4,y4) && isMarked[x3][y3]){

			//条件を満たしている？
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//面積比較
				Integer[] tmp = new Integer[]{x2,y2,x3,y3,x4,y4,x1,y1};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//左
		x3 = x2;
		y3 = y2;

		sub1 = getUpObliqueL(x3,y3); //上の点を探す

		//上
		x2 = x3-sub1;
		y2 = y3+sub1;

		sub2 = getDownObliqueL(x2,y2); //左の点を探す

		//左
		x1 = x2-sub2;
		y1 = y2-sub2;

		//下
		x4 = x1+sub1;
		y4 = y1-sub1;

		//適正値かつ下の点は存在する？
		if(isProper(x1,y1) && isProper(x2,y2) && isProper(x4,y4) && isMarked[x4][y4]){

			//条件を満たしている？
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//面積比較
				Integer[] tmp = new Integer[]{x3,y3,x4,y4,x1,y1,x2,y2};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//下
		x4 = x3;
		y4 = y3;

		sub1 = getUpObliqueL(x4,y4); // 左の点を探す

		//左
		x1 = x4-sub1;
		y1 = y4+sub1;

		sub2 = getUpObliqueR(x1,y1); //上の点を探す

		//上
		x2 = x1+sub2;
		y2 = y1+sub2;

		//右
		x3 = x2+sub1;
		y3 = y2-sub1;

		//適正値かつ右の点は存在する？
		if(isProper(x1,y1) && isProper(x2,y2) && isProper(x3,y3) && isMarked[x3][y3]){

			//条件を満たしている？
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//面積比較
				Integer[] tmp = new Integer[]{x4,y4,x1,y1,x2,y2,x3,y3};
				ans = compareS(ans,tmp,takeSmaller);

			}
		}

		//答えを返す
		return ans;

	}

	//右上方向に点を探す
	public static int getUpObliqueR(int x,int y){

		//入力値が不正？
		if(x<0||y<0)
			return N+1;

		x++;
		y++;

		//差分
		int ans = 1;

		//右上方向に探していく
		while(x<N&&y<N&&!isMarked[x][y]){

			x++;
			y++;

			ans++;

		}

		//差分を返す
		return ans;

	}

	//右下方向に点を探す
	public static int getDownObliqueR(int x,int y){

		//入力値が不正？
		if(x<0||N<=y)
			return N+1;

		x++;
		y--;

		//差分
		int ans = 1;

		//右下方向に探していく
		while(x<N&&0<=y&&!isMarked[x][y]){

			x++;
			y--;

			ans++;

		}

		//差分を返す
		return ans;

	}

	//左上方向の点を探す
	public static int getUpObliqueL(int x,int y){

		//入力値が不正？
		if(N<=x||y<0)
			return N+1;

		x--;
		y++;

		//差分
		int ans = 1;

		//左上方向に探していく
		while(0<=x&&y<N&&!isMarked[x][y]){

			x--;
			y++;

			ans++;

		}

		//差分を返す
		return ans;

	}

	//左下方向の点を探す
	public static int getDownObliqueL(int x,int y){

		//入力値が不正？
		if(N<=x||N<=y)
			return N+1;

		x--;
		y--;

		//差分
		int ans = 1;

		//左下方向に探していく
		while(0<=x&&0<=y&&!isMarked[x][y]){

			x--;
			y--;

			ans++;

		}

		//差分を返す
		return ans;

	}

	//点の適正値判定
	public static boolean isProper(int x,int y){

		//ちゃんと方眼紙の中にあるか？
		return (0 <= x  &&  x < N)   &&   (0 <= y  &&  y < N);

	}

	//条件を満たしているか判定する(まっすぐな長方形用)
	public static boolean sCheck(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){

		//上辺と底辺上に点が存在したらfalse
		for(int i=Math.min(x1,x3)+1;i<Math.max(x1,x3);i++){

			if(isMarked[i][y1]||isMarked[i][y3])
				return false;

		}

		//左右の辺上に点が存在したらfalse
		for(int i=Math.min(y1,y3)+1;i<Math.max(y1,y3);i++){

			if(isMarked[x1][i]||isMarked[x3][i])
				return false;

		}

		//辺のかぶりが存在しないか？
		return isOrigin(x1,y1,x2,y2,x3,y3,x4,y4);

	}

	//条件を満たしているか判定する(斜めの長方形用)
	public static boolean oCheck(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){

		//差の計算
		int sub = x2-x1;

		//辺上に点が存在すればfalse(左->上、下->右)
		while(0<--sub){

			if(isMarked[x1+sub][y1+sub])
				return false;

			if(isMarked[x4+sub][y4+sub])
				return false;

		}

		//差の計算
		sub = x4-x1;

		//辺上に点が存在すればfalse(左->下、上->右)
		while(0<--sub){

			if(isMarked[x1+sub][y1-sub])
				return false;

			if(isMarked[x2+sub][y2-sub])
				return false;

		}

		//辺のかぶりが存在しないか？
		return isOrigin(x1,y1,x2,y2,x3,y3,x4,y4);

	}

	//重複した辺が存在しないか判定する
	public static boolean isOrigin(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){


		//既に作られた辺全てで判定
		for(Integer[] rect:answer){

			//いずれかの辺が被っていたらfalse
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

		//全部被ってなかったらtrue
		return true;

	}

	//辺同士が被っているか判定
	private static boolean isOverlapped(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){

		//1:縦にまっすぐ
		//2:横にまっすぐ
		//3:斜め

		//タイプ別に分ける
		int type1 = x1==x2 ? 1 : y1==y2 ? 2 : 3;
		int type2 = x3==x4 ? 1 : y3==y4 ? 2 : 3;

		boolean result = false;

		//斜め同士ならちょっとめんどくさい判定を
		if(type1==3&&type2==3){

			//辺は同じ直線上に存在するか
			int s1 = (x1-x2)*(y3-y1)-(y1-y2)*(x3-x1);
			int t1 = (x1-x2)*(y4-y1)-(y1-y2)*(x4-x1);
			int s2 = (x3-x4)*(y1-y3)-(y3-y4)*(x1-x3);
			int t2 = (x3-x4)*(y2-y3)-(y3-y4)*(x2-x3);

			result = s1*t1==0&&s2*t2==0;

			//もし存在するならy座標で被ってる部分があるかで判定
			if(result)
				result &= !(Math.max(y1,y2)<=Math.min(y3,y4)||Math.max(y3,y4)<=Math.min(y1,y2));

		}

		//タイプ別に判定(そもそも違うタイプなら被らない)
		return type1==type2&&

			 //タイプ1同士ならx座標の一致とy座標の範囲が被っているかで判定
			(type1==1 ? x1==x3&&!(Math.max(y1,y2)<=Math.min(y3,y4)||Math.max(y3,y4)<=Math.min(y1,y2)) :

			 //タイプ2同士ならy座標の一致とx座標の範囲が被っているかで判定
			 type1==2 ? y1==y3&&!(Math.max(x1,x2)<=Math.min(x3,x4)||Math.max(x3,x4)<=Math.min(x1,x2)) :

			 //タイプ3なら上記の結果を使う
			 result);

	}

	//印を付けた後の候補の増減
	public static int nextFluct(Integer[] rect){

		int x = rect[0];
		int y = rect[1];

		//試しに追加してみる
		answer.add(rect);
		isMarked[x][y] = true;

		//追加後の候補を記録
		int ret = canMarks().size();

		//追加を解除
		answer.remove(answer.size()-1);
		isMarked[x][y] = false;

		//結果を返す
		return ret;

	}

	//答えの出力
	public static void outputData(){

		//試作Printer
		SimplePrinter out = new SimplePrinter(System.out);

		//Kを出力
		out.println(K);

		//空白区切り出力
		for(Integer[] ans:answer){

			out.println(ans," ");

		}

		//念のため
		out.close();

	}

	//ランダムダイスによるシャッフル
	public static void reset(){

		//次のnumberStacker用
		LinkedList<Integer> nextStacker = new LinkedList<Integer>();

		//前か後ろにAddしていく(等確率)
		for(Integer i:numberStacker){
			if(dice.nextInt(2)==0)
				nextStacker.addLast(i);
			else
				nextStacker.addFirst(i);
		}

		//numberStacker更新
		numberStacker = nextStacker;

	}

	//評価関数(現在の得点をザッと計算する)
	private static long score(){

		//返却用
		long answer = 0;

		//全マス見ていく
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){

				//印が付いていたらスコア計算
				if(isMarked[i][j])
					answer += dict(i,j)+1;

			}
		}

		//返却
		return answer;

	}
}


//タイマー
class Timer{

	private long startTime;

	public Timer(){

		//開始時刻のセット
		startTime = System.nanoTime();

	}

	//経過時刻の取得
	public double getTime(){

		long nowTime = System.nanoTime();

		//秒に変換(double)
		return (nowTime - startTime) / 1e9;

	}

}





/*////////////////////////////////////////////////
	* My Scanner *

	@auther viral
 *////////////////////////////////////////////////
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
	public int nextInt()throws IOException{
		int ans = 0;
		byte c = read();
		while(c<=' ')c = read();
		boolean negate = c == '-';
		if(negate)c = read();
		while('0'<=c&&c<='9'){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public void close()throws IOException{
		is.close();
	}
}
/*////////////////////////////////////////////////
	* My Printer *

	@auther viral
 *////////////////////////////////////////////////
class SimplePrinter extends PrintWriter{
	public SimplePrinter(OutputStream os){
		super(os);
	}
	public void println(Integer[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
}
