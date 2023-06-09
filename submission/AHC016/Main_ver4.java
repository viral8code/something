import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
class Main{

	private static final boolean autoFlush = false;
	private static final SimpleScanner in = new SimpleScanner(System.in);
	private static final PrintWriter out = new PrintWriter(System.out,autoFlush);

	private static int odds;
	private static int limit;
	private static final Random rm = new Random(0);

	public static void main(String[] args){

		/*メモ////////
		何も思いついてません(11/17)(致命的)
		そもそもどうすれば良いのかすら思いついてません()
		ランダムで頂点入れ替えられちまうなら全通り試すしか無いんだけど･･･
		次数で判定するとか･･･？
		11/18
		グラフが一致してるか調べる方法ミスってね･･･？
		修正してみた。上手くいくといいな･･･
		点数は上がりました
		やったね！
		とはいえ、Mが大きい時は比較回数がすんごいことになるから
		N>10の時はサンプルと同じ動きをするようにした
		多分ここなんだよな･･･
		点数計算を考えると1/N * (9/10)^ミスの回数
		なんだからN<34ならミスの回数よりもNを減らす効果の方に重点を置いた方が良い気がする
		とはいえ、比較をどうするか･･･
		次数比較だけで判定してもいいけど･･･うーん･･･
		というか、εのこと何も考慮に入れてないんだよな
		これをどうするか
		なるべく余計な比較は避けてるんだけど、これを諦めて差を返すようにさせるか･･･？
		ただ、そんなんで点数上がるなら苦労しねぇんだよな！クソが！
		はぁ･･･しょうがねぇ･･･書くか･･･
		ちょっと待って･･･？N=10でもTLEすんの･･･？じゃあ9に下げるか･･･
		あ、O(N!)じゃなくてO(N!M)か！そりゃTLEすっか･･･
		普通にN>6じゃないとTLEするわ･･･
		え？じゃあ全然通んなくない？無理やんけ･･･
		*/////////////

		int M = in.nextInt();
		char[] eps = in.nextCharArray();
		odds = (eps[2]-'0')*10+eps[3]-'0';
		solve(M);
		out.close();
	}
	private static final void check(int M){
		limit = 10_000_000/M;
	}
	private static final void solve(int M){
		check(M);
		final ArrayList<Graph> list = prepare(M);
		final int N = list.get(0).getN();
		out.println(N);
		for(int i=0;i<M;++i)
			out.println(list.get(i));
		out.flush();
		final Integer brokenCount = odds*N*(N-1)/100;
		String H;
		for(int i=0;i<100;++i){
			H = in.next();
			int s = -1;
			final Graph g = new Graph(N);
			for(int j=0;j<N;++j)
				for(int k=j+1;k<N;++k)
					if(H.charAt(++s)=='1')
						g.set(j,k);
			TreeMap<Integer,Integer> dist = new TreeMap<>();
			for(int j=0;j<M;j++)
				dist.put(g.dist(list.get(j)),j);
			Map.Entry<Integer,Integer> ans = dist.firstEntry();
			out.println(ans.getValue());
			out.flush();
		}
	}
	private final static ArrayList<Graph> prepare(int M){
		HashSet<Graph> list = new HashSet<Graph>();
		int max = 1<<15;
		Graph.makeTable(new ArrayList<Integer>());
		for(int i=0;i<max;++i){
			boolean[][] l = new boolean[6][6];
			int[] c = new int[6];
			int index = 0;
			for(int j=0;j<6;++j){
				for(int k=j;k<6;k++){
					if((i&1<<index++)>0){
						l[j][k] = true;
						l[k][j] = true;
						++c[j];
						++c[k];
					}
				}
			}
			Graph g = new Graph(6);
			g.setData(l,c);
			list.add(g);
		}
		final ArrayList<Graph> ans = new ArrayList<>(list);
		int n = 6;
		int sum = list.size();
		while(sum<M){
			sum += 2*n;
			++n;
		}
		for(Graph g:ans)
			g.setN(n);
		for(int i=16;i<=sum;++i){
			Graph g = new Graph(n);
			int s = i;
			Make:for(int j=0;j<n;++j)
				for(int k=j+1;k<n;k++){
					g.set(j,k);
					if(--s==0)
						break Make;
				}
			ans.add(g);
		}
		return ans;
	}
}
class Graph{
	final public static int num = 6;
	final public static ArrayList<ArrayList<Integer>> numberTable = new ArrayList<ArrayList<Integer>>();
	private boolean[][] list;
	private int[] count;
	private int N,sum;
	public Graph(final int n){
		N = n;
		list = new boolean[n][n];
		count = new int[n];
		sum = 0;
	}
	final public void set(int i,int j){
		list[i][j] = true;
		list[i][j] = true;
		++count[i];
		++count[j];
		sum++;
	}
	final public void setN(final int n){
		if(N>n)
			throw new IllegalArgumentException("n is too small");
		if(N==n)
			return;
		boolean[][] newList = new boolean[n][n];
		for(int i=0;i<N;++i)
			for(int j=0;j<N;++j)
				newList[i][j] = list[i][j];
		int[] newCount = new int[n];
		System.arraycopy(count,0,newCount,0,N);
		list = newList;
		count = newCount;
		N = n;
	}
	final public int getN(){
		return N;
	}
	final public int getSum(){
		return sum;
	}
	final public boolean contains(int i,int j){
		return list[i][j];
	}
	final public int getCount(int i){
		return count[i];
	}
	final public boolean isBig(){
		return sum>15;
	}
	final public int dist(Graph g){
		if(N!=g.getN())
			return Integer.MAX_VALUE;
		if(isBig())
			return Math.abs(sum-g.getSum());
		int ans = Integer.MAX_VALUE;
		Search:for(ArrayList<Integer> temp:numberTable){
			int cnt = 0;
			for(int i=0;i<5;i++){
				for(int num=i+1;num<5;++num)
					if(list[temp.get(i)][temp.get(num)]^g.contains(temp.get(i),temp.get(num)))
						cnt++;
				cnt += Math.abs(count[i]-g.getCount(temp.get(i)));
			}
			if(ans>cnt)
				ans = cnt;
		}
		return ans;
	}
	@Override
	final public String toString(){
		final StringBuilder ans = new StringBuilder();
		for(int i=0;i<N;i++)
			for(int j=i+1;j<N;j++)
				ans.append(list[i][j]?1:0);
		return ans.toString();
	}
	@Override
	final public boolean equals(Object o){
		if(!(o instanceof Graph))
			return false;
		final Graph g = (Graph)o;
		if(N!=g.getN())
			return false;
		if(isBig())
			return sum==g.getSum();
		Search:for(ArrayList<Integer> temp:numberTable){
			for(int i=0;i<N;i++){
				if(count[i]!=g.getCount(temp.get(i)))
					continue Search;
				for(int num=0;num<N;++num)
					if(list[temp.get(i)][temp.get(num)]^g.contains(temp.get(i),temp.get(num)))
						continue Search;
			}
			return true;
		}
		return false;
	}
	final public Graph copy(){
		final Graph g = new Graph(N);
		g.setData(list,count);
		return g;
	}
	final public void setData(boolean[][] l,int[] c){
		for(int i=0;i<N;i++)
			System.arraycopy(l[i],0,list[i],0,N);
		System.arraycopy(count,0,c,0,N);
	}
	@Override
	final public int hashCode(){
		int hash = 0;
		for(int s:count)
			hash += 1<<s;
		return hash;
	}
	private static boolean[] isUsed = new boolean[6];
	final public static void makeTable(ArrayList<Integer> lists){
		if(lists.size()==num){
			final ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.addAll(lists);
			numberTable.add(temp);
			return;
		}
		for(int i=0;i<num;i++){
			if(isUsed[i])
				continue;
			isUsed[i] = true;
			lists.add(i);
			makeTable(lists);
			lists.remove(lists.size()-1);
			isUsed[i] = false;
		}
	}
}
/*////////////////////////////////////////////////
	* My Scanner *

	@auther viral
*/////////////////////////////////////////////////
class SimpleScanner{
	private final int buff_size = 1<<15;
	private final InputStream is;
	private byte[] buff;
	private int point,length;
	public SimpleScanner(InputStream is){
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private final void reload(){
		do{
			try{
				length = is.read(buff,point = 0,buff_size);
			}catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
		}while(length==-1);
	}
	private final byte read(){
		if(point==length)reload();
		return buff[point++];
	}
	public final int nextInt(){
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
	public final String next(){
		StringBuilder ans = new StringBuilder();
		byte c = read();
		while(c<=' ')c = read();
		while(c>' '){
			ans.append((char)c);
			c = read();
		}
		return ans.toString();
	}
	public final String[] next(int n){
		String[] ans = new String[n];
		for(int i=0;i<n;i++){
			ans[i] = next();
		}
		return ans;
	}
	public final char[] nextCharArray(){
		return next().toCharArray();
	}
	public final void close(){
		try{
			is.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
