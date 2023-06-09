import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
class Main{

	private static final boolean autoFlush = false;
	private static final SimpleScanner in = new SimpleScanner(System.in);
	private static final PrintWriter out = new PrintWriter(System.out,autoFlush);

	private static int odds;
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
		普通にN>7じゃないとTLEするわ･･･
		え？じゃあ全然通んなくない？無理やんけ･･･
		*/////////////

		int M = in.nextInt();
		char[] eps = in.nextCharArray();
		odds = (eps[2]-'0')*10+eps[3]-'0';
		solve(M);
		out.close();
		
	}
	private static final void solve(int M){
		ArrayList<Graph> list = prepare(M);
		if(list.size()==0){
			solveCompletely(M);
			return;
		}
		int N = list.get(0).getN();
		out.println(N);
		for(int i=0;i<M;i++)
			out.println(list.get(i));
		out.flush();
		int brokenCount = odds*N*(N-1)/100;
		for(int i=0;i<100;i++){
			String H = in.next();
			int s = 0;
			Graph g = new Graph(N);
			for(int j=0;j<N;j++)
				for(int k=j+1;k<N;k++)
					if(H.charAt(s++)=='1')
						g.set(j,k);
			TreeMap<Integer,Integer> dist = new TreeMap<>();
			for(int j=0;j<M;j++)
				dist.put(g.dist(list.get(j)),j);
			Map.Entry<Integer,Integer> ans = dist.floorEntry(brokenCount-rm.nextInt(Math.max(1,rm.nextInt(Math.max(1,brokenCount/2)))));
			if(ans==null)
				ans = dist.firstEntry();
			out.println(ans.getValue());
			out.flush();
		}
	}
	private final static ArrayList<Graph> prepare(int M){
		HashSet<Graph> list = new HashSet<Graph>();
		Graph temp = new Graph(1);
		list.add(temp);
		long sum = 1;
		for(int i=2;i<5;i++){
			Graph.num = i;
			Graph.isUsed = new boolean[i];
			Graph.numberTable = new ArrayList<>();
			Graph.makeTable(new ArrayList<Integer>());
			for(Graph g:list)
				g.setN(i);
			HashSet<Graph> next = new HashSet<Graph>();
			for(Graph g:list){
				next.add(g);
				for(int j=0;j<i;j++){
					for(int k=j+1;k<i;k++){
						if(g.contains(j,k))
							continue;
						temp = g.copy();
						temp.set(j,k);
						next.add(temp);
					}
				}
			}
			list = next;
			sum *= i;
		}
		int i = 5;
		while(list.size()<M){
			sum *= i;
			if(i>6){
				return new ArrayList<Graph>();
			}
			Graph.num = i;
			Graph.isUsed = new boolean[i];
			Graph.numberTable = new ArrayList<>();
			Graph.makeTable(new ArrayList<Integer>());
			for(Graph g:list)
				g.setN(i);
			HashSet<Graph> next = new HashSet<Graph>();
			for(Graph g:list){
				next.add(g);
				for(int j=0;j<i;j++){
					for(int k=j+1;k<i;k++){
						if(g.contains(j,k))
							continue;
						temp = g.copy();
						temp.set(j,k);
						next.add(temp);
					}
				}
			}
			list = next;
			++i;
		}
		ArrayList<Graph> ans = new ArrayList<>(list);
		return ans;
	}
	private static final void solveCompletely(int M){
		int N = findN(M);
		out.println(N);
		String[] list = new String[M];
		for(int i=0;i<M;i++){
			StringBuilder sb = new StringBuilder();
			for(int j=0;j<i;j++)
				sb.append(1);
			for(int j=i;j<N*(N-1)/2;j++)
				sb.append(0);
			String str = sb.toString();
			out.println(str);
			list[i] = str;
		}
		out.flush();
		for(int i=0;i<100;i++){
			char[] H = in.nextCharArray();
			int count = 0;
			for(char c:H)
				if(c=='1')
					count++;
			out.println(Math.min(count+Integer.signum(rm.nextInt())*rm.nextInt(Math.max(1,count*odds/100)),M-1));
			out.flush();
		}
	}
	private final static int findN(int M){
		int N = 1;
		while(M>N*(N-1)/2)++N;
		return N;
	}
}
class Graph{
	public static int num;
	public static ArrayList<ArrayList<Integer>> numberTable;
	private ArrayList<HashSet<Integer>> list;
	private int N;
	public Graph(int n){
		N = n;
		list = new ArrayList<>();
		for(int i=0;i<N;i++)
			list.add(new HashSet<>());
	}
	final public void set(int i,int j){
		list.get(i).add(j);
		list.get(j).add(i);
	}
	final public void setN(int n){
		if(N>n)
			throw new IllegalArgumentException("n is too small");
		for(int i=N+1;i<=n;i++)
			list.add(new HashSet<>());
		N = n;
	}
	final public int getN(){
		return N;
	}
	final public HashSet<Integer> get(int i){
		return list.get(i);
	}
	final public boolean contains(int i,int j){
		return list.get(i).contains(j);
	}
	final public int dist(Graph g){
		if(N!=g.getN())
			return Integer.MAX_VALUE;
		int ans = Integer.MAX_VALUE;
		Search:for(ArrayList<Integer> temp:numberTable){
			int count = 0;
			for(int i=0;i<N;i++){
				for(int num:list.get(i))
					if(!g.contains(temp.get(i),temp.get(num)))
						count++;
				count += Math.abs(list.get(i).size()-g.get(temp.get(i)).size());
			}
			if(ans>count)
				ans = count;
		}
		return ans;
	}
	@Override
	final public String toString(){
		StringBuilder ans = new StringBuilder();
		for(int i=0;i<N;i++)
			for(int j=i+1;j<N;j++)
				ans.append(list.get(i).contains(j)?1:0);
		return ans.toString();
	}
	@Override
	final public boolean equals(Object o){
		if(!(o instanceof Graph))
			return false;
		Graph g = (Graph)o;
		if(N!=g.getN())
			return false;
		Search:for(ArrayList<Integer> temp:numberTable){
			for(int i=0;i<N;i++){
				if(list.get(i).size()!=g.get(temp.get(i)).size())
					continue Search;
				for(int num:list.get(i)){
					if(!g.contains(temp.get(i),temp.get(num)))
						continue Search;
				}
			}
			return true;
		}
		return false;
	}
	final public Graph copy(){
		Graph g = new Graph(N);
		for(int i=0;i<N;i++)
			g.get(i).addAll(list.get(i));
		return g;
	}
	@Override
	final public int hashCode(){
		int hash = 0;
		for(HashSet<Integer> s:list)
			hash += s.size();
		return hash;
	}
	static boolean[] isUsed;
	final public static void makeTable(ArrayList<Integer> lists){
		if(lists.size()==num){
			ArrayList<Integer> temp = new ArrayList<Integer>();
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
	private InputStream is;
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
