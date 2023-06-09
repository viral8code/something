import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;
final class Main {
	public static void main ( String[] args ) {
		final SimpleScanner sc = new SimpleScanner( System.in );
		final int N = sc.nextInt();
		final int[] subA = new int[N+1];
		final BIT bit = new BIT(N);
		boolean isEven = true;
		for(int i=0;i<N;++i){
			final int num = sc.nextInt();
			isEven ^= bit.isEven(num);
			bit.add(num);
			++subA[num];
		}
		final int[] subB = new int[N+1];
		bit.clear();
		for(int i=0;i<N;++i){
			final int num = sc.nextInt();
			isEven ^= bit.isEven(num);
			bit.add(num);
			++subB[num];
		}
		if(!Arrays.equals(subA,subB)){
			System.out.println("No");
			return;
		}
		for(int i=1;i<=N;++i){
			if(subA[i]>1){
				System.out.println("Yes");
				return;
			}
		}
		if(isEven)
			System.out.println("Yes");
		else
			System.out.println("No");
	}
}
final class BIT{
	private final int size;
	private final boolean[] tree;
	public BIT(final int n){
		size = n;
		tree = new boolean[n+1];
	}
	public boolean isEven(int i){
		boolean isEven = true;
		while(i>0){
			if(tree[i])
				isEven = !isEven;
			i ^= i&(-i);
		}
		return isEven;
	}
	public void add(int i){
		while(i<=size){
			tree[i] = !tree[i];
			i += i&(-i);
		}
	}
	public void clear(){
		Arrays.fill(tree,false);
	}
}

/*


             ／￣＼
            |     |
             ＼＿／
               |
          ／ ￣  ￣  ＼
        ／    ＼  ／    ＼
     ／      ⌒    ⌒      ＼      よくぞこの提出結果を開いてくれた
     |      （__人__）      |      褒美としてオプーナを買う権利をやる
     ＼       ｀⌒´       ／   ☆
      /ヽ､--ー､＿＿, -‐ ´  ＼─／
    ／ >     ヽ▼●▼<＼       ||ｰ､.
   /ヽ､    ＼ i |｡| |/  ヽ (ニ､`ヽ.
  l    ヽ     l |｡| | r-､y ｀ﾆ  ﾉ ＼
  l     |     |ー─ | ￣ l   ｀~ヽ＿ノ＿_
      ／￣￣￣￣ヽ-'ヽ--'  ／ オープナ  ／|
     |￣￣￣￣￣￣|／|    |￣￣￣￣￣￣|／| ＿＿＿＿＿＿
 ／￣オプーナ／|￣|__｣／_オープナ ／|￣|__,」＿_      ／|
|￣￣￣￣￣|／オープナ￣／￣￣￣￣|／オプーナ ／|   ／  |
|￣￣￣￣￣|￣￣￣￣￣|／l￣￣￣￣|￣￣￣￣￣|／| ／
|￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣|
*/

/*////////////////////////////////////////////////
	* My Library *

	@author viral
*/////////////////////////////////////////////////
// MyScanner
final class SimpleScanner {
	final private int buff_size = 1 << 15;
	private final InputStream is;
	private final byte[] buff;
	private int point, length;
	public SimpleScanner ( InputStream is ) {
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
	public int[] nextInt ( int n ) {
		int[] ans = new int[n];
		for ( int i = 0; i < n; i++ ) {
			ans[i] = nextInt();
		}
		return ans;
	}
	public void close () {
		try {
			is.close();
		} catch ( IOException e ) {
			e.printStackTrace();
			System.exit( 1 );
		}
	}
}
