import java.util.Scanner;
import java.util.Random;
import java.util.ArrayDeque;
import java.awt.Point;
class Main{

	private static final int odd = 2;

	public static void main(String[] args){

		/* メモ

			Nは交差点の数		:500～1000
			Mは道路の数		:500～3000
			Dは工事できる日数	:  5～  30
			Kは一日に工事できる上限	:M/D～2M/D(切り上げ)

		*/
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int D = sc.nextInt();
		int K = sc.nextInt();
		Road[] road = new Road[M];
		for(int i=0;i<M;i++){
			int u = sc.nextInt();
			int v = sc.nextInt();
			int w = sc.nextInt();
			road[i] = new Road(u,v,w,i+1);
		}
		/*
			ここより先の情報は不要
			受け取らなくても良いみたい
		*/
		Point[] crossPoint = new Point[N];
		for(int i=0;i<N;i++){
			int x = sc.nextInt();
			int y = sc.nextInt();
			crossPoint[i] = new Point(x,y);
		}
		/*
			ここまで不要な情報
		*/



		/*
			以降上手い具合にやる
		*/
		Random rm = new Random(System.nanoTime());
		int[] ans = new int[M];
		ArrayDeque<Point> deq = new ArrayDeque<Point>();
		for(int i=1;i<=D;i++)
			deq.add(new Point(i,K));
		for(int i=0;i<M;i++){
			if(rm.nextInt(odd)==0){
				Point p = deq.pollFirst();
				p.y--;
				ans[i] = p.x;
				if(p.y!=0)
					deq.add(p);
			}else if(rm.nextInt(odd)==0){
				Point p = deq.pollLast();
				p.y--;
				ans[i] = p.x;
				if(p.y!=0)
					deq.add(p);
			}else
				i--;
			if(!deq.isEmpty())
				deq.add(deq.pollFirst());
		}
		System.out.print(ans[0]);
		for(int i=1;i<M;i++)
			System.out.print(" "+ans[i]);
		System.out.println();
	}
}
class Road{
	//first、secondは頂点番号、weightは道路の距離、numberは道路番号
	//first<second
	final int first,second,weight,number;
	public Road(int u,int v,int w,int id){
		first = Math.min(u,v);
		second = Math.min(u,v);
		weight = w;
		number = id;
	}
}