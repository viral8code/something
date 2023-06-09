import java.io.*;
import java.util.*;
class subMain{
	public int hook, beforeS, beforeC, numOfProcess = 0, remain = 0, rest = 0, period = -1;
	public int[] hang;
	public int[][] intervalS, intervalC, now, inventory;
	public int[][][][] cost;
	public List<String> MasterAns = new ArrayList<String>();
	public subMain(int h, int[] hangar, int[][] s, int[][] c, int[][] num){
		hook = h;
		hang = hangar;
		intervalS = s;
		intervalC = c;
		cost = new int[s.length][c.length][s.length][c.length];
		for(int i=0;i<s.length;i++){
			for(int j=0;j<c.length;j++){
				for(int k=0;k<s.length;k++){
					for(int l=0;l<c.length;l++){
						cost[i][j][k][l] = Math.max(intervalS[i][k],intervalC[j][l]);
					}
				}
			}
		}
		inventory = new int[num.length][num[0].length];
		for(int i=0;i<num.length;i++){
			for(int j=0;j<num[i].length;j++){
				remain += num[i][j];
				inventory[i][j] = num[i][j];
			}
		}
		now = new int[hook][2];
	}

	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}

	public void process(int s,int c,int num){
		if(num<=0)
			return;
		while(true){
			numOfProcess++;
			period = ++period % hook;
			if(hangerCheck(s)){
				rest++;
				if(now[period][0]==0)
					MasterAns.add("-2");
				else{
					now[period][0] = 0;
					now[period][1] = 0;
					MasterAns.add("-1");
				}
			}
			else if(intervalCheck(rest,beforeS,beforeC,s,c)){
				rest++;
				if(now[period][0]==0)
					MasterAns.add("-2");
				else{
					now[period][0] = 0;
					now[period][1] = 0;
					MasterAns.add("-1");
				}
			}
			else{
				rest = 0;
				now[period][0] = s;
				now[period][1] = c;
				beforeS = s;
				beforeC = c;
				MasterAns.add(s+" "+c);
				remain--;
				num--;
			}
			switch(junction(MasterAns.get(MasterAns.size()-1),num)){
				case 0:
				case 1:
					break;
				default:
					return;
			}
		}
	}
	public static int junction(String ans,int num){
		if(num==0)
			return 2;
		if(ans.equals("-1")||ans.equals("-2"))
			return 1;
		return 0;
	}
	public boolean hangerCheck(int s){
		if(s==now[period][0])
			return false;
		int num = 0;
		for(int i=0;i<now.length;i++){
			if(now[i][0]==s)
				num++;
		}
		if(num<hang[s-1])
			return false;
		return true;
	}
	public boolean intervalCheck(int num,int s,int c,int ss,int cc){
		int max = Math.max(intervalS[s-1][ss-1],intervalC[c-1][cc-1]);
		if(num>=max||(s==ss&&c==cc))
			return false;
		else
			return true;
	}
	public int[] next(int s,int c){
		int[] ans = new int[2];
		if(s==-1&&c==-1){
			beforeS = ans[0] = 1;
			beforeC = ans[1] = 1;
		}
		else{
			int small = 99;
			int pickS = 0;
			int pickC = 0;
			boolean flag = true;
			for(int i=period+1;i<=period+40;i++){
				int ii = i % hook;
				if(now[ii][0]!=0&&inventoryCheck(now[ii][0])){
					pickS = now[ii][0];
					flag = false;
					break;
				}
			}
			for(int i=0;i<intervalS.length;i++){
				for(int j=0;j<intervalC.length;j++){
					int temp = cost[s-1][c-1][i][j];
					if(temp<small){
						small = temp;
						if(flag)
							pickS = i + 1;
						pickC = j + 1;
					}
				}
			}
			ans[0] = pickS;
			ans[1] = pickC;
		}
		return ans;
	}
	public void rejection(int s,int c){
		for(int i=0;i<intervalS.length;i++){
			for(int j=0;j<intervalC.length;j++){
				cost[i][j][s][c] = 99;
			}
		}
	}
	public boolean inventoryCheck(int s){
		for(int i=0;i<intervalC.length;i++){
			if(inventory[s-1][i]==0)
				return false;
		}
		return true;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] SCHab = subMain.parIntWithS(br.readLine());
		int S=SCHab[0],C=SCHab[1],H=SCHab[2];
		int[][] num = new int[S][C];
		for(int i=0;i<S;i++){
			num[i] = subMain.parIntWithS(br.readLine());
		}
		int[] hangar = subMain.parIntWithS(br.readLine());
		int[][] costOfS = new int[S][S];
		for(int i=0;i<S;i++){
			costOfS[i] = subMain.parIntWithS(br.readLine());
		}
		int[][] costOfC = new int[C][C];
		for(int i=0;i<C;i++){
			costOfC[i] = subMain.parIntWithS(br.readLine());
		}
		subMain AI = new subMain(H,hangar,costOfS,costOfC,num);
		List<String> ans = new ArrayList<String>();
		int nowS = -1, nowC = -1;
		while(AI.remain>0){
			int[] nextSC = AI.next(nowS,nowC);
			nowS = nextSC[0];
			nowC = nextSC[1];
			AI.process(nowS,nowC,num[nowS-1][nowC-1]);
			AI.inventory[nowS-1][nowC-1] = 0;
			AI.rejection(nowS-1,nowC-1);
		}
		String answer = String.join("\n",AI.MasterAns);
		while(true){
			if(answer.substring(answer.length()-2,answer.length()-1).equals("-"))
				answer = answer.substring(0,(answer.length()-3));
			else
				break;
		}
		System.out.println(AI.numOfProcess);
		System.out.println(answer);
	}
}
