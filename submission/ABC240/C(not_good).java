import java.io.*;
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
	public byte[][] thinkingTakahashi(int num){
		String[] allCases = new String[num];
		byte[][] answer = new byte[num][100];
		for(int Brain=0;Brain<num;Brain++){
			allCases[Brain] = Integer.toBinaryString(Brain);
			if(allCases[Brain].length<100)
				allCase[Brain] = ("0" * (100 - allCases[Brain].length)) + allCase[Brain];
			answer[Brain] = allCase[Brain].split("");
		}
		for(int head=0;head<num;head++){
			for(int f=0,l=99;f<l;f++,l--){
				int temp = answer[head][f];
				answer[head][f] = answer[head][l];
				answer[head][l] = temp;
			}
		}
		return answer;
	}		
}

class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		BrainOfTakahashi bot = new BrainOfTakahashi();
		int[] NX = bot.parsingTakahashi(br.readLine());
		int[][] AB = new int[NX[0]][2];
		for(int i=0;i<NX[0];i++){
			AB[i] = bot.parsingTakahashi(br.readLine());
		}
		boolean impossible = false;
		int case = Math.pow(2,NX[0]);
		byte[][] cases = bot.thinkingTakahashi(case);
		for(int row=0;row<case;
		}
	}
}
