import java.io.*;
import java.util.Arrays;
import java.util.stream.LongStream;
import java.math.BigInteger;
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
				Test test = new Test(file);
				test.start();
				try{
					test.join();
				}catch(Exception e){
					e.printStackTrace();
					return -1;
				}
				String scoreLabel;
				if(test.score==-2){
					scoreLabel = "0(Over Request)";
				}
				else if(test.score==-1){
					scoreLabel = "0(Illegal Output)";
				}
				else{
					scoreLabel = String.valueOf(test.score);
				}
				System.out.println(file.getName()+":"+scoreLabel+"("+(long)(test.time/1e6)+"ms)");
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
	public int N,D,Q;
	private int[] w;

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
				process.waitFor();
				long t = System.nanoTime();

				//Set Execution Time
				time = t-s;

				//Cumpute and Set Score
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
		D = in.nextInt();
		Q = in.nextInt();
		w = new int[N];
		for(int i=0;i<N;i++){
			w[i] = in.nextInt();
		}
		in.close();

		//Give Param
		out.println(N+" "+D+" "+Q);
	}
	private void calculate(Scanner sc,PrintStream out)throws Exception{
		while(Q-->0){

			//Execute Query
			int nL = sc.nextInt();
			int nR = sc.nextInt();
			long sumL = 0;
			while(nL-->0){
				sumL += sc.nextInt();
			}
			long sumR = 0;
			while(nR-->0){
				sumR += sc.nextInt();
			}
			out.println(sumL<sumR?"<":sumL>sumR?">":"=");
		}
	}

	//Compute Score
	private long score(Scanner sc,PrintStream out)throws Exception{
		long[] sum = new long[D];
		for(int i=0;i<N;i++){
			sum[sc.nextInt()] += w[i];
		}
		RationalNumber average = RationalNumber.ZERO;
		for(long num:sum){
			average = average.add(RationalNumber.valueOf(num));
		}
		average = average.divide(RationalNumber.valueOf(D));
		RationalNumber variance = RationalNumber.ZERO;
		for(long num:sum){
			variance = variance.add(average.subtract(RationalNumber.valueOf(num)).pow(2));
		}
		variance = variance.divide(RationalNumber.valueOf(D));
		RationalNumber s = variance.sqrt(2).movePointRight(2);
		return s.longValue()+1;
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
