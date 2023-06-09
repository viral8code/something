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

		*/////////////

		int M = in.nextInt();
		char[] eps = in.nextCharArray();
		odds = (eps[2]-'0')*10+eps[3]-'0';
		solve(M);
		out.close();
		
	}
	private static final void solve(int M){
		int brokenCount = odds*M*3/100;
		ArrayList<Graph> list = prepare(M);
		int N = list.get(0).getN();
		out.println(N);
		for(int i=0;i<M;i++)
			out.println(list.get(i));
		out.flush();
		for(int i=0;i<100;i++){
			String H = in.next();
			int s = 0;
			Graph g = new Graph(N);
			for(int j=0;j<N;j++)
				for(int k=j+1;k<N;k++)
					if(H.charAt(s++)=='1')
						g.set(j,k);
			TreeMap<Integer,Graph> dist = new TreeMap<>();
			for(int j=0;j<M;j++)
				dist.put(g.distance(list.get(j)),list.get(j));
			Integer key = dist.floorKey(brokenCount);
			if(key==null)
				key = dist.firstKey();
			out.println(key);
			out.flush();
		}
	}
	private final static ArrayList<Graph> prepare(int M){
		HashSet<Graph> list = new HashSet<Graph>();
		Graph temp = new Graph(1);
		list.add(temp);
		for(int i=2;i<5;i++){
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
		}
		int i = 5;
		while(list.size()<M){
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
}
class Graph{
	private TreeSet<Edge> list;
	private int N,size;
	public Graph(int n){
		N = n;
		size = n*(n+1)/2;
		list = new TreeSet<Edge>();
	}
	final public void set(int i,int j){
		Edge temp = new Edge(i,j);
		list.add(temp);
	}
	final public boolean contains(Edge e){
		return list.contains(e);
	}
	final public boolean contains(int i,int j){
		return list.contains(new Edge(i,j));
	}
	final public void setN(int n){
		if(N>n)
			throw new IllegalArgumentException("n is too small");
		N = n;
		size = n+(n+1)/2;
	}
	final public int getN(){
		return N;
	}
	@Override
	final public String toString(){
		StringBuilder ans = new StringBuilder();
		for(int i=0;i<N;i++)
			for(int j=i+1;j<N;j++)
				ans.append(contains(new Edge(i,j))?1:0);
		return ans.toString();
	}
	final public int distance(Graph g){
		if(N!=g.getN())
			return Integer.MAX_VALUE;
		int[] count1 = new int[N];
		int[] count2 = new int[N];
		for(Edge e:list){
			count1[e.top1]++;
			count1[e.top2]++;
		}
		for(Edge e:g.list){
			count2[e.top1]++;
			count2[e.top2]++;
		}
		Arrays.sort(count1);
		Arrays.sort(count2);
		int dist = 0;
		for(int i=0;i<N;i++)
			dist += Math.abs(count1[i]-count2[i]);
		return dist;
	}
	final public Graph copy(){
		Graph g = new Graph(N);
		for(Edge e:list)
			g.set(e.top1,e.top2);
		return g;
	}
	@Override
	final public int hashCode(){
		return list.hashCode();
	}
}
class Edge implements Comparable<Edge>{
	int top1,top2;
	public Edge(int i,int j){
		top1 = Math.min(i,j);
		top2 = Math.max(i,j);
	}
	@Override
	final public int compareTo(Edge e){
		return top1>e.top1?1:top1<e.top1?-1:top2>e.top2?1:top2<e.top2?-1:0;
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
