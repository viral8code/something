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
	private static final int numOfLoop = 10;

	public static void main(String[] args){

		/*メモ////////

		*/////////////

		int M = in.nextInt();
		char[] eps = in.nextCharArray();
		odds = (eps[2]-'0')*10+eps[3]-'0';
		if(odds<5)
			solveFast(M);
		else
			solveCompletely(M);
		out.close();
		
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
			out.println(Math.min(count,M-1));
			out.flush();
		}
	}
	private static final void solveFast(int M){
		int N = 1;
		while(M>1<<N)++N;
		N = findN(N);
		out.println(N);
		String[] list = new String[M];
		for(int i=0;i<M;i++){
			String str = Integer.toBinaryString(i);
			while(str.length()<N*(N-1)/2)str = '0'+str;
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
			ArrayList<Integer> select = new ArrayList<Integer>();
			for(int j=0;j<M;j++){
				if(Integer.bitCount(j)!=count)
					continue;
				select.add(j);
			}
			out.println(select.get(rm.nextInt(select.size())));
			out.flush();
		}
		out.close();
	}
	private final static int findN(int M){
		int N = 1;
		while(M>N*(N-1)/2)++N;
		return N;
	}
}
/*
                     ／）
                  ／／／）
               ／,=ﾞ"／      
  ／         i f  ,.r='"-‐'つ＿＿＿_こまけぇこたぁいいんだよ！！
/           /      _,.‐'￣／⌒     ⌒＼
    ／     ,i      ,二ﾆ⊃（ ⚫）.（⚫）＼
  /       ﾉ       ilﾞフ::⌒（__人__）⌒:: ＼
        ,ｲ｢ﾄ､    ,!|         |r┬-|        |
      /  iﾄヾヽ_/ｨ"＼         `ー'´      ／
*/
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
