import java.io.*;
import java.util.*;
class BrainOfTakahashi{
	public int[] parsingTakahashi(String someInt){
		int[] twoInt = new int[2];
		String[] str = someInt.split(" ");
		if(str.length==1)
			str = someInt.split("	");
		twoInt[0] = Integer.parseInt(str[0]);
		twoInt[1] = Integer.parseInt(str[1]);
		return twoInt;
	}
}

class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		BrainOfTakahashi bot = new BrainOfTakahashi();
		int[] NX = bot.parsingTakahashi(br.readLine());
		int[][] AB = new int[NX[0]][2];
		int max = 0;
		int min = 0;
		for(int i=0;i<NX[0];i++){
			AB[i] = bot.parsingTakahashi(br.readLine());
			max += Math.max(AB[i][0],AB[i][1]);
			min += Math.min(AB[i][0],AB[i][1]);
		}
		if(max<NX[1] || min>NX[1]){
			System.out.println("No");
			System.exit(0);
		}
		int[] distances = new int[NX[0]];
		for(int i=0;i<NX[0];i++){
			distances[i] = Math.max(AB[i][0],AB[i][1]) - Math.min(AB[i][0],AB[i][1]);
		}
		Arrays.sort(distances);
		int distance = NX[1] - min;
		int[] check = Arrays.stream(distances).filter(value -> value<=distance).toArray();
		for(int i=1;i<=check.length;i++){
			int nowDistance = distance;
			int pick = -1;
			for(int j=check.length-i;j>=0;j--){
				if(nowDistance<check[j])
					continue;
				if(nowDistance==distance)
					pick = j;
				nowDistance -= check[j];
			}
			for(int j=check.length-i;j>=0;j--){
				if(nowDistance<check[j])
					continue;
				if(j==pick){
					pick = -1;
					continue;
				}
				nowDistance -= check[j];
			}
			if(nowDistance==0){
				System.out.println("Yes");
				System.exit(0);
			}
		}
		System.out.println("No");
	}
}
