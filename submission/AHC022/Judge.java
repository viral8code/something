import java.io.*;
import java.util.Arrays;
import java.util.stream.LongStream;
class Judge extends Thread{
	private static LongStream stream;
	private static File[] files;
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
				var test = new Test(file);
				test.start();
				try{
					test.join();
				}catch(Exception e){
					e.printStackTrace();
					return -1;
				}
				System.out.println(file.getName()+":"+test.score
					+"("+(long)(test.time/1e6)+"ms) accept="+test.accept+"/"+test.N);
				return test.score;
			})
			.filter(i->i>-1);
	}

}
class Test extends Thread{
	private static final int MAX_MEASURE_CNT = 10_000;
	private final File file;
	private final ProcessBuilder pb = new ProcessBuilder(new String[]{
			"java",
			"Main"
		});;
	private int L,S,indexOfNoise,indexOfCoordinate;
	private int[] A,E;
	private long[] noises;
	private int[][] outCells,P,coordinate;
	public long score,time;
	public int N,accept;
	public Test(File f){
		file = f;
	}
	public void run(){
		Process process = null;
		try{
			process = pb.start();
			var out = new PrintStream(process.getOutputStream(),false);
			try(
				var in = new Scanner(process.getInputStream());
			){
				long s = System.nanoTime();
				init(in,out);
				measure(in,out);
				process.waitFor();
				long t = System.nanoTime();
				time = t-s;
				E = new int[N];
				for(int i=0;i<N;i++){
					E[i] = in.nextInt();
				}
				long measureCost = calcMeasureCost();
				long coordinateCost = calcCoordinateCost();
				int W = check();
				accept = N-W;
				score = (long)Math.ceil(1e14*Math.pow(0.8,W)/(measureCost+coordinateCost+1e5));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(process!=null)
				process.destroyForcibly();
		}
	}
	private void init(Scanner sc,PrintStream out)throws FileNotFoundException{
		var in = new Scanner(new FileInputStream(file));
		L = in.nextInt();
		N = in.nextInt();
		S = in.nextInt();
		out.println(L+" "+N+" "+S);
		outCells = new int[N][2];
		for(int i=0;i<N;i++){
			outCells[i][0] = in.nextInt();
			outCells[i][1] = in.nextInt();
			out.println(outCells[i][0]+" "+outCells[i][1]);
		}
		out.flush();
		A = new int[N];
		for(int i=0;i<N;i++){
			A[i] = in.nextInt();
		}
		noises = new long[MAX_MEASURE_CNT];
		for(int i=0;i<MAX_MEASURE_CNT;i++){
			noises[i] = in.nextLong();
		}
		indexOfNoise = 0;
		coordinate = new int[MAX_MEASURE_CNT][2];
		indexOfCoordinate = 0;
		E = new int[N];
		P = new int[L][L];
		for(int i=0;i<L;i++){
			for(int j=0;j<L;j++){
				P[i][j] = sc.nextInt();
			}
		}
		in.close();
	}
	private void measure(Scanner sc,PrintStream out){
		int i = sc.nextInt();
		int y = sc.nextInt();
		int x = sc.nextInt();
		while(i!=-1){
			if(indexOfNoise==MAX_MEASURE_CNT){
				out.println(-1);
				out.flush();
				throw new IllegalArgumentException();
			}
			coordinate[indexOfCoordinate][0] = y;
			coordinate[indexOfCoordinate++][1] = x;
			y += outCells[A[i]][0];
			if(y<0){
				y += L;
			}
			if(L<=y){
				y -= L;
			}
			x += outCells[A[i]][1];
			if(x<0){
				x += L;
			}
			if(L<=x){
				x -= L;
			}

			int num = P[y][x];
			num += noises[indexOfNoise++];
			num = Math.min(1000,num);
			out.println(Math.max(0,num));
			out.flush();
			i = sc.nextInt();
			y = sc.nextInt();
			x = sc.nextInt();
		}
	}
	private long calcMeasureCost(){
		long ans = 0;
		for(int i=0;i<indexOfCoordinate;i++){
			ans += 100*(10+Math.abs(coordinate[i][0])+Math.abs(coordinate[i][1]));
		}
		return ans;
	}
	private long calcCoordinateCost(){
		long ans = 0;
		for(int i=0;i<L;i++){
			for(int j=0;j<L;j++){
				long a = P[i][j]-P[i][(j+1)%L];
				long b = P[i][j]-P[(i+1)%L][j];
				ans += a*a+b*b;
			}
		}
		return ans;
	}
	private int check(){
		int ans = 0;
		for(int i=0;i<N;i++){
			if(A[i]!=E[i]){
				ans++;
			}
		}
		return ans;
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
		} while ( length == -1 );
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