import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.LongStream;
import java.util.concurrent.TimeUnit;
import java.awt.Point;
class Judge extends Thread{
	private static LongStream stream;
	private static File[] files;

	private volatile boolean isAC = true;

	public static void main(String[] args)throws IOException,InterruptedException{

		files = new File("in").listFiles();

		if(files==null){
			System.err.println("Can't find any file");
			System.exit(1);
		}

		var process = new Judge();
		long s = System.nanoTime();
		process.run();
		process.join();

		if(stream!=null){
			long[] array = stream.toArray();
			System.out.println("finished!");
			long sum = 0;
			long max = 0;
			long min = Long.MAX_VALUE;
			for(long num:array){
				sum += num;
				max = Math.max(max,num);
				min = Math.min(min,num);
			}
			System.out.println("average:\t"+(double)sum/files.length);
			System.out.println("max:\t\t"+max);
			System.out.println("min:\t\t"+min);
			System.out.println("sum:\t\t"+sum);
			System.out.println("judge time:\t"+(System.nanoTime()-s)/1e9+"s");
			System.out.println("\nResult:"+(process.isAC?"AC":"WA"));
		}
	}
	public void run(){
		stream = Arrays.stream(files)
			.parallel()
			.mapToLong(file->{
				if(!file.getName().endsWith(".txt")){
					System.out.println(file.getName()+":ignored");
					return -1;
				}
				Test test = new Test(file);
				test.start();
				try{
					test.join();
				}catch(Exception e){
					e.printStackTrace();
					return -1;
				}
				String scoreLabel;
				if(test.score==-3){
					scoreLabel = "0(Time Over)";
				}
				else if(test.score==-2){
					scoreLabel = "0(Over Request)";
				}
				else if(test.score==-1){
					scoreLabel = "0(Illegal Output)";
				}
				else{
					scoreLabel = String.valueOf(test.score);
				}
				isAC &= 0<=test.score;
				System.out.println(file.getName()+":"+scoreLabel+" ("+(long)(test.time/1e6)+"ms)");
				return test.score;
			})
			.filter(i->i>-1);
	}

}
class Test extends Thread{
	private final File file;
	private final ProcessBuilder pb = new ProcessBuilder(new String[]{
			"java",
			"Main"
		});;

	//Set Param
	public int N,M;
	private Point[][] oilFields;
	private Point[] d;
	private int[][] v;
	private double[] e;
	private double eps,cost;

	public long score;
	public long time;
	public Test(File f){
		file = f;
	}
	public void run(){
		Process process = null;
		try{
			process = pb.start();
			var out = new PrintStream(process.getOutputStream(),true);
			try(
				var in = new Scanner(process.getInputStream());
			){
				long s = System.nanoTime();

				//Get and Give Param
				init(in,out);

				//Execute Query
				calculate(in,out);

				//Wait End of Process
				process.waitFor(3,TimeUnit.MINUTES);
				long t = System.nanoTime();

				//Set Execution Time
				time = t-s;

				//Cumpute and Set Score
				if(3_000_000_000L<=time)
					score = -3;
				else
					score = score(in,out);
			}
		}
		catch(Exception e){
			score = -1;
			e.printStackTrace();
		}
		finally{
			if(process!=null)
				process.destroyForcibly();
		}
	}
	private void init(Scanner sc,PrintStream out)throws FileNotFoundException{
		var in = new Scanner(new FileInputStream(file));

		//Get Param
		N = in.nextInt();
		M = in.nextInt();
		eps = Double.parseDouble(in.next());
		oilFields = new Point[M][];
		for(int i=0;i<M;i++){
			int d = in.nextInt();
			oilFields[i] = new Point[d];
			for(int j=0;j<d;j++){
				int x = in.nextInt();
				int y = in.nextInt();
				oilFields[i][j] = new Point(x,y);
			}
		}
		d = new Point[M];
		for(int i=0;i<M;i++){
			int x = in.nextInt();
			int y = in.nextInt();
			d[i] = new Point(x,y);
		}
		v = new int[N][N];
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				v[i][j] = in.nextInt();
			}
		}
		e = new double[2*N*N];
		for(int i=0;i<e.length;i++){
			e[i] = Double.parseDouble(in.next());
		}
		in.close();

		//Give Param
		out.println(N+" "+M+" "+eps);
		for(Point[] field:oilFields){
			out.print(field.length);
			for(Point p:field){
				out.printf(" %d %d",p.x,p.y);
			}
			out.println();
		}
	}
	private void calculate(Scanner sc,PrintStream out)throws Exception{
		cost = 0;
		for(int i=0;i<2*N*N;i++){
			//Execute Query
			String query = sc.next();
			if(query.length()!=1)
				throw new IllegalArgumentException("Incorrect query directive char.");
			int k = sc.nextInt();
			if(k<1)
				throw new IllegalArgumentException("k must be a positive");

			HashSet<Point> set = new HashSet<>();
			for(int j=0;j<k;j++){
				int x = sc.nextInt();
				int y = sc.nextInt();
				if(!set.add(new Point(x,y)))
					throw new IllegalArgumentException("Duplicate elements");
			}

			if(query.charAt(0)=='q'){
				int sum = 0;
				for(Point p:set)
					sum += v[p.x][p.y];
				if(k>1){
					double ans = e[i];
					ans *= Math.sqrt(k*eps*(1-eps));
					ans += (k-sum)*eps+sum*(1-eps);
					out.println(Math.max(0,(int)Math.round(ans)));
				}
				else
					out.println(sum);
				cost += 1/Math.sqrt(k);
			}
			else if(query.charAt(0)=='a'){
				if(check(set)){
					out.println(1);
					return;
				}
				out.println(0);
				cost += 1;
			}
			else
				throw new IllegalArgumentException("Incorrect query directive char.");
		}
		cost = 1000;
	}

	private boolean check(HashSet<Point> set){
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if((v[i][j]==0)==set.contains(new Point(i,j)))
					return false;
		return true;
	}

	//Compute Score
	private long score(Scanner sc,PrintStream out)throws Exception{
		try{
			sc.next();
			return -2;
		}catch(Exception e){
			return (long)Math.round(cost*1e6);
		}
	}
}

// MyScanner
final class Scanner implements AutoCloseable{
	final private int buff_size = 1 << 12;
	private final InputStream is;
	private final byte[] buff;
	private int point, length;
	public Scanner ( InputStream is ) {
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private void reload () {
		do {
			try {
				length = is.read( buff, point = 0, buff_size );
			} catch ( IOException e ) {
				e.printStackTrace();
				System.exit( 1 );
			}
		} while ( length == 0 );
		if ( length == -1 )
			throw new java.util.NoSuchElementException();
	}
	private byte read () {
		if ( point == length ) {
			reload();
		}
		return buff[point++];
	}
	public int nextInt () {
		int ans = 0;
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		boolean negate = c == '-';
		if ( c == '-' ) {
			c = read();
		}
		while ( '0' <= c && c <= '9' ) {
			ans = ans * 10 + c - '0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public long nextLong () {
		long ans = 0;
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		boolean negate = c == '-';
		if ( c == '-' ) {
			c = read();
		}
		while ( '0' <= c && c <= '9' ) {
			ans = ans * 10 + c - '0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public String next () {
		StringBuilder ans = new StringBuilder();
		byte c = read();
		while ( c <= ' ' ) {
			c = read();
		}
		while ( c > ' ' ) {
			ans.append( ( char )c );
			c = read();
		}
		return ans.toString();
	}
	public void close () {
		try {
			is.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}