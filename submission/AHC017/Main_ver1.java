import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.Point;
class Main{

	private static final int odd = 2;
	private static final Random rm = new Random();

	public static void main(String[] args){

		final long startTime = System.nanoTime();

		/* メモ

			Nは交差点の数		:500～1000
			Mは道路の数		:500～3000
			Dは工事できる日数	:  5～  30
			Kは一日に工事できる上限	:M/D～2M/D(切り上げ)

		*/
		final Scanner sc = new Scanner(System.in);
		final int N = sc.nextInt();
		final int M = sc.nextInt();
		final int D = sc.nextInt();
		final int K = sc.nextInt();
		final ArrayList<HashMap<Integer,Point>> road = new ArrayList<>();
		for(int i=0;i<=N;i++)
			road.add(new HashMap<>());
		for(int i=1;i<=M;i++){
			int u = sc.nextInt();
			int v = sc.nextInt();
			int w = sc.nextInt();
			road.get(u).put(v,new Point(w,i));
			road.get(v).put(u,new Point(w,i));
		}
		/*
			ここより先の情報は不要
			受け取らなくても良いみたい
		*/
		final Point[] crossPoint = new Point[N];
		for(int i=0;i<N;i++){
			int x = sc.nextInt();
			int y = sc.nextInt();
			crossPoint[i] = new Point(x,y);
		}
		/*
			ここまで不要な情報
		*/

		/*
			変数表

			頂点N
			道路M
			日数D
			上限K
			道路road
			(頂点座標crossPoint)
			パラメータ値odd
			乱数rm
			開始時刻startTime
			
		*/
		/*
			以降上手い具合にやる
		*/

		final int[] ans = new int[M+1];

		final ArrayDeque<Point> deq = new ArrayDeque<Point>();
		for(int i=1;i<=D;i++)
			deq.add(new Point(i,K));
		for(int i=1;i<=M;i++){
			if(rm.nextInt(2)==0){
				final Point p = deq.pollFirst();
				p.y--;
				ans[i] = p.x;
				if(p.y!=0)
					deq.add(p);
			}else{
				final Point p = deq.pollLast();
				p.y--;
				ans[i] = p.x;
				if(p.y!=0)
					deq.add(p);
			}
			if(!deq.isEmpty())
				deq.add(deq.pollFirst());
		}

		final long limitTime = 5_000_000_000L;

		prepare(road,N);

		long nowP = score(ans,D,N,road);

		System.err.println(nowP);

		while(System.nanoTime()-startTime<limitTime){

			final int road1 = rm.nextInt(M)+1;
			final int road2 = rm.nextInt(M)+1;

			if(ans[road1]==ans[road2])
				continue;

			final int temp = ans[road1];
			ans[road1] = ans[road2];
			ans[road2] = temp;

			final long nextP = score(ans,D,N,road);
			System.err.println(nextP);

			if(nextP<nowP){
				ans[road2] = ans[road1];
				ans[road1] = temp;
			}

		}

		out(ans);
	}
	private static final void out(int[] ans){
		System.out.print(ans[1]);
		for(int i=2;i<ans.length;i++)
			System.out.print(" "+ans[i]);
		System.out.println();
	}

	private static final int max = 1_000_000_000;
	private static int[][] minDist;
	private static final ArrayList<ArrayList<HashSet<Integer>>> route = new ArrayList<>();
	private static void prepare(final ArrayList<HashMap<Integer,Point>> road,final int N){
		for(int i=0;i<=N;i++){
			final ArrayList<HashSet<Integer>> temp = new ArrayList<>(N+1);
			for(int j=0;j<=N;j++)
				temp.add(null);
			route.add(temp);
		}
		minDist = new int[N+1][N+1];
		for(int[] temp:minDist)
			Arrays.fill(temp,max);
		for(int i=1;i<=N;i++)
			bfs2(i,road,minDist[i]);
	}
	private static final long score(final int[] ans,final int D,final int N,final ArrayList<HashMap<Integer,Point>> road){
		final ArrayList<TreeSet<Integer>> list = new ArrayList<>(D+1);
		for(int i=0;i<=D;i++)
			list.add(new TreeSet<>());
		for(int i=1;i<ans.length;i++)
			list.get(ans[i]).add(i);
		double score = 0;
		for(TreeSet<Integer> underConstruction:list){
			long sum = 0;
			for(int i=1;i<=N;i++){
				final int[] dist = new int[N+1];
				bfs(i,road,dist,underConstruction);
				for(int k=1;k<=N;k++)
					sum += dist[k]-minDist[i][k];
			}
			score += sum/(double)(N*(N-1));
		}
		return Math.round(score/D);
	}
	private static final boolean containsSome(final Set set1,final Set set2){
		for(Object obj:set1)
			if(set2.contains(obj))
				return true;
		return false;
	}
	private static final void bfs(final int from,final ArrayList<HashMap<Integer,Point>> road,final int[] costs,final TreeSet<Integer> brokenRoad){
		final PriorityQueue<Pair> bfs = new PriorityQueue<>();
		bfs.add(new Pair(from,0));
		Arrays.fill(costs,max);
		costs[from] = 0;
		while(!bfs.isEmpty()){
			final Pair pair = bfs.poll();
			final int point = pair.point;
			final int cost = pair.cost;
			if(cost>costs[point])
				continue;
			for(Integer next:road.get(point).keySet()){
				if(brokenRoad.contains(road.get(point).get(next).y))
					continue;
				final int c = road.get(point).get(next).x;
				if(costs[next]>cost+c){
					bfs.add(new Pair(next,cost+c));
					costs[next] = cost+c;
				}
			}
		}
	}
	private static final void bfs2(int from,ArrayList<HashMap<Integer,Point>> road,final int[] costs){
		final PriorityQueue<Pair2> bfs = new PriorityQueue<>();
		bfs.add(new Pair2(from,0,new HashSet<>()));
		Arrays.fill(costs,max);
		costs[from] = 0;
		while(!bfs.isEmpty()){
			final Pair2 pair = bfs.poll();
			final int point = pair.point;
			final int cost = pair.cost;
			final HashSet<Integer> set = pair.route;
			if(cost>costs[point])
				continue;
			for(Integer next:road.get(point).keySet()){
				final int c = road.get(point).get(next).x;
				if(costs[next]>=cost+c){
					final HashSet<Integer> temp = new HashSet<>();
					temp.addAll(set);
					temp.add(road.get(point).get(next).y);
					bfs.add(new Pair2(next,cost+c,temp));
					costs[next] = cost+c;
					route.get(from).set(next,temp);
				}
			}
		}
	}
}
class Pair implements Comparable<Pair>{
	final int point;
	final int cost;
	Pair(int p,int c){
		point = p;
		cost = c;
	}
	public int compareTo(Pair p){
		return cost<p.cost?-1:cost>p.cost?1:0;
	}
}
class Pair2 extends Pair{
	final HashSet<Integer> route;
	Pair2(int p,int c,HashSet<Integer> r){
		super(p,c);
		route = r;
	}
	public int compareTo(Pair2 p){
		return cost<p.cost?-1:cost>p.cost?1:0;
	}
}