import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Iterator;
public final class Main{

	static Library System = new Library(java.lang.System.in,java.lang.System.out);

	static ArrayList<Point> list;
	static ArrayList<Integer> answer;
	static ArrayDeque<Point> newNail;

	static int depth = 10;

	public static void main(String[] args)throws IOException{

		long startTime = java.lang.System.nanoTime();

		int N = System.in.nextInt();
		int K = System.in.nextInt();

		list = new ArrayList<Point>(N);
		answer = new ArrayList<Integer>(N+K);
		newNail = new ArrayDeque<Point>(K);
		boolean[][] map = new boolean[1001][1001];

		for(int i=0;i<N;i++){
			int x = System.in.nextInt();
			int y = System.in.nextInt();
			list.add(new Point(x,y));
			map[x][y] = true;
		}
		System.in.close();

		for(int i=1;i<=N;i++)answer.add(i);

		int now = N+1;

		for(int i=N-1;i>1&&now<=N+K;i--){
			int count = 0;
			ArrayDeque<Point[]> bfs = new ArrayDeque<Point[]>();
			Point q1 = list.get(i);
			Point q2 = list.get(i-1);
			for(int j=0;j<i-1;j++){
				if(check(q1,q2,list.get(j),list.get(j+1))){
					count++;
					bfs.add(new Point[]{list.get(j),list.get(j+1)});
				}
			}
			if(0<count){
				long ST = java.lang.System.nanoTime();
				Loop:for(int k=0;k<=depth;k++){
					for(int j=0;k*k+j*j<=depth*depth;j++){
						for(Point[] p:bfs){
							if(k!=0&&p[0].x<=1000-k&&!map[p[0].x+k][p[0].y]){
								Point tmp = new Point(p[0].x+k,p[0].y);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&p[0].x>=k&&!map[p[0].x-k][p[0].y]){
								Point tmp = new Point(p[0].x-k,p[0].y);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(j!=0&&p[0].y<=1000-j&&!map[p[0].x][p[0].y+j]){
								Point tmp = new Point(p[0].x,p[0].y+j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(j!=0&&p[0].y>=j&&!map[p[0].x][p[0].y-j]){
								Point tmp = new Point(p[0].x,p[0].y-j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&p[1].x<=1000-k&&!map[p[1].x+k][p[1].y]){
								Point tmp = new Point(p[1].x+k,p[1].y);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&p[1].x>=k&&!map[p[1].x-k][p[1].y]){
								Point tmp = new Point(p[1].x-k,p[1].y);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(j!=0&&p[1].y<=1000-j&&!map[p[1].x][p[1].y+j]){
								Point tmp = new Point(p[1].x,p[1].y+j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(j!=0&&p[1].y>=j&&!map[p[1].x][p[1].y-j]){
								Point tmp = new Point(p[1].x,p[1].y-j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[0].x<=1000-k&&p[0].y<=1000-j&&!map[p[0].x+k][p[0].y+j]){
								Point tmp = new Point(p[0].x+k,p[0].y+j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[0].x>=k&&p[0].y<=1000-j&&!map[p[0].x-k][p[0].y+j]){
								Point tmp = new Point(p[0].x-k,p[0].y+j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[0].x<=1000-k&&p[0].y>=j&&!map[p[0].x+k][p[0].y-j]){
								Point tmp = new Point(p[0].x+k,p[0].y-j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[0].x>=k&&p[0].y>=j&&!map[p[0].x-k][p[0].y-j]){
								Point tmp = new Point(p[0].x-k,p[0].y-j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[1].x<=1000-k&&p[1].y<=1000-j&&!map[p[1].x+k][p[1].y+j]){
								Point tmp = new Point(p[1].x+k,p[1].y+j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[1].x>=k&&p[1].y<=1000-j&&!map[p[1].x-k][p[1].y+j]){
								Point tmp = new Point(p[1].x-k,p[1].y+j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[1].x<=1000-k&&p[1].y>=j&&!map[p[1].x+k][p[1].y-j]){
								Point tmp = new Point(p[1].x+k,p[1].y-j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if(k!=0&&j!=0&&p[1].x>=k&&p[1].y>=j&&!map[p[1].x-k][p[1].y-j]){
								Point tmp = new Point(p[1].x-k,p[1].y-j);
								if(!check(q1,tmp,p[0],p[1])&&!check(q2,tmp,p[0],p[1])){
									if(checkAll(q1,tmp,q2)<count){
										answer.add(i,0);
										newNail.addFirst(tmp);
										now++;
										map[tmp.x][tmp.y] = true;
										break Loop;
									}
								}
							}
							if((java.lang.System.nanoTime()-ST)/1e6>100)break;
						}
					}
				}
			}
			if((java.lang.System.nanoTime()-startTime)/1e9>4.85)break;
		}
		map = null;
		System.out.println(newNail.size());
		for(Point tmp:newNail){
			System.out.print(tmp.x);
			System.out.print(" ");
			System.out.println(tmp.y);
		}
		int counter = N+1;
		for(int tmp:answer){
			if(tmp==0){
				System.out.println(counter++);
			}else{
				System.out.println(tmp);
			}
		}

		System.out.close();
	}
	public static boolean check(Point a,Point b,Point c,Point d){
		int s = (a.x-b.x)*(c.y-a.y)-(a.y-b.y)*(c.x-a.x);
		int t = (a.x-b.x)*(d.y-a.y)-(a.y-b.y)*(d.x-a.x);
		if(s*t>0)return false;
		s = (c.x-d.x)*(a.y-c.y)-(c.y-d.y)*(a.x-c.x);
		t = (c.x-d.x)*(b.y-c.y)-(c.y-d.y)*(b.x-c.x);
		if(s*t>0)return false;
		return true;
	}
	public static int checkAll(Point p,Point newP,Point q){
		int counter = 0;
		Point r = list.get(answer.get(0)-1);
		Iterator<Point> itr = newNail.iterator();
		for(int i=1;i<answer.size();i++){
			Point s;
			if(answer.get(i)==0)s = itr.next();
			else s = list.get(answer.get(i)-1);
			if(	r.x==p.x&&r.y==p.y||
				r.x==q.x&&r.y==q.y||
				s.x==p.x&&s.y==p.y||
				s.x==q.x&&s.y==q.y
			){}
			else{
				if(check(p,newP,r,s))counter++;
				if(check(q,newP,r,s))counter++;
			}
			r = s;
		}
		return counter;
	}
}
final class Point{
	int x;
	int y;
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
}
/*////////////////////////////////////////////////
	* My Library *
*/////////////////////////////////////////////////
final class Library{
	SimpleScanner in = null;
	PrintWriter out = null;
	public Library(InputStream in,OutputStream out){
		this.in = new SimpleScanner(in);
		this.out = new PrintWriter(out);
	}
}
final class SimpleScanner{
	private final int buff_size=1<<16;
	private InputStream is=null;
	private byte[] buff=new byte[buff_size];
	private int length=0,point=0;
	public SimpleScanner(InputStream is){
		this.is = is;
	}
	private void reload()throws IOException{
		point=0;
		length=is.read(buff);
	}
	private void load(int n)throws IOException{
		if(n<=length-point)return;
		for(int i=point,j=0;i<length;i++,j++)buff[j]=buff[i];
		length-=point;
		point=0;
		int temp=is.read(buff,length,buff_size-length);
		if(temp==-1)return;
		length+=temp;
	}
	private void skip()throws IOException{
		while(true){
			while(point<length&&buff[point]<33)point++;
			if(point<length)return;
			reload();
		}
	}
	public int nextInt()throws IOException{
		skip();
		load(12);
		int ans=0;
		boolean negate=false;
		if(buff[point++]=='-')negate=true;
		else point--;
		while(47<buff[point]&&buff[point]<58)ans=ans*10+buff[point++]-48;
		return negate?-ans:ans;
	}
	public int[] nextInt(int num)throws IOException{
		int[] array = new int[num];
		for(int i=0;i<num;i++){
			array[i] = nextInt();
		}
		return array;
	}
	public void close()throws IOException{
		is.close();
	}
}
