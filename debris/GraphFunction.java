import java.util.*;
final class GraphFunction{
	public static long[] dijkstra(final int from,final ArrayList<ArrayList<int[]>> path){
		long[] ans = new int[path.length];
		Arrays.fill(ans,-1);
		PriorityQueue<Node> queue = new PriorityQueue<>();
		while(!queue.isEmpty()){
			Node now = queue.poll();
			if(ans[now.point]!=-1)
				continue;
			ans[now.point] = now.cost;
			for(int[] next:path.get(now))
				if(ans[next[0]]==-1)
					queue.add(new Node(next[0],now.cost+next[1]));
		}
		return ans;
	}
	private class Node implements Comparable<Node>{
		int point;
		long cost;
		Node(final int p,final long c){
			point = p;
			cost = c;
		}
		@Override
		public int compareTo(Node n){
			return Long.compare(cost,n.cost);
		}
	}
	public static ArrayList<ArrayList<int[]>> makePath(int N,int[][] path){
		ArrayList<ArrayList<int[]>> ans = new ArrayList<>();
		for(int i=0;i<N;i++)
			ans.add(new ArrayList<>());
		for(int[] p:path)
			ans.get(p[0]).add(new int[]{p[1],p[2]});
		return ans;
	}
}