import java.io.*;
import java.util.*;
class subMain{
	public boolean superVisor = false;
	public int hook, beforeS, beforeC, lastPin, numOfProcess = 0, remain = 0, rest = 0, period = -1, pin = 0;
	public int[] hang;
	public int[][] intervalS, intervalC, now, virtualNow, inventory, past;
	public int[][][][] cost;
	public List<String> MasterAns = new ArrayList<String>();
	public subMain(int h, int[] hangar, int[][] s, int[][] c, int[][] num){
		//System.out.println("コンストラクタ起動");
		hook = h;
		hang = hangar;
		intervalS = s;
		intervalC = c;
		lastPin = s.length * c.length - 1;
		past = new int[lastPin][2];
		cost = new int[s.length][c.length][s.length][c.length];
		for(int i=0;i<s.length;i++){
			for(int j=0;j<c.length;j++){
				for(int k=0;k<s.length;k++){
					for(int l=0;l<c.length;l++){
						if(i==k&&j==l)
							cost[i][j][k][l] = 99;
						else
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

	public int process(int s,int c,int num){
		//System.out.println("process起動");
		int number = num;
		if(num<=0){
			//System.out.println("製品数エラー！");
			return 0;
		}
		while(true){
			virtualNow = now.clone();
			numOfProcess++;
			period = ++period % hook;
			if(hangerCheck(s)||intervalCheck(rest,beforeS,beforeC,s,c)){
				//System.out.println("条件を満たしていないです。");
				rest++;
				if(virtualNow[period][0]==0)
					MasterAns.add("-2");
				else{
					virtualNow[period][0] = 0;
					virtualNow[period][1] = 0;
					MasterAns.add("-1");
				}
			}
			else{
				//System.out.println("条件を満たしています。");
				now = virtualNow.clone();
				rest = 0;
				now[period][0] = beforeS = s;
				now[period][1] = beforeC = c;
				MasterAns.add(s+" "+c);
				remain--;
				number--;
				if(past[0][0]!=0){
					for(int i=0;i<past.length;i++){
						past[i][0] = 0;
						past[i][1] = 0;
					}
					pin = 0;
				}
			}
			if(rest>40&&pin==lastPin){
				//System.out.println("superVisorフラグ起動");
				superVisor = true;
				past[pin][0] = s;
				past[pin][1] = c;
				pin++;
				return number;
			}
			//System.out.println(MasterAns.get(MasterAns.size()-1));
			//try {
			//	Thread.sleep(300); //for debug
			//} catch (InterruptedException e) {
			//}
			switch(junction(MasterAns.get(MasterAns.size()-1),number)){
				case 0:
				case 1:
					break;
				default:
					return 0;
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
		//System.out.println("next起動");
		int[] ans = new int[2];
		if(s==-1&&c==-1){
			int max1 = 0;
			int maxS = -1;
			for(int i=0;i<hang.length;i++){
				if(max1<hang[i]){
					max1 = hang[i];
					maxS = i;
				}
			}
			//System.out.println("初回のS候補は"+(maxS+1)+"です。");
			int max2 = 0;
			int maxC = -1;
			for(int i=0;i<inventory[maxS].length;i++){
				if(inventory[maxS][i]>max2&&inventory[maxS][i]<max1){
					max2 = inventory[maxS][i];
					maxC = i;
				}
			}
			if(maxC==-1){
				//System.out.println("S候補に見合ったC候補が見つかりませんでした。");
				maxS = 0;
				maxC = 0;
			}
			beforeS = ans[0] = (maxS + 1);
			beforeC = ans[1] = (maxC + 1);
		}
		else{
			if(superVisor){
				//System.out.println("superVisorフラグ検知");
				undo();
				superVisor = false;
			}
			int small = 99;
			int pickS = 0;
			int pickC = 0;
			boolean flag = true;
			//System.out.println("S探査開始");
			searchS:for(int i=period+1;i<=period+40;i++){
				int ii = i % hook;
				if(now[ii][0]!=0){
					if(inventoryCheck(now[ii][0])){
						if(loopCheck(now[ii][0],-1)){
							//System.out.println("条件に合致するSが見つかりました。");
							pickS = now[ii][0];
							flag = false;
							break searchS;
						}
					}
				}
			}
			//System.out.println("C探査開始");
			if(pickS==0){
				for(int i=0;i<intervalS.length;i++){
					for(int j=0;j<intervalC.length;j++){
						int temp = cost[s-1][c-1][i][j];
						if(temp<small){
							if(loopCheck(i+1,j+1)){
								small = temp;
								pickS = i + 1;
								pickC = j + 1;
							}
						}
					}
				}
			}
			else{
				for(int j=0;j<intervalC.length;j++){
					int temp = cost[s-1][c-1][pickS-1][j];
					if(temp<small){
						if(loopCheck(pickS,j+1)){
							small = temp;
							pickC = j + 1;
						}
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
	public void undo(){
		//System.out.println("undo起動(rest:"+rest+")");
		for(int i=0;i<rest;i++){
			MasterAns.remove(MasterAns.size()-1);
		}
		numOfProcess -= rest;
		period -= rest;
		while(period<0){
			period+=hook;
		}
		rest = 0;
	}
	public boolean loopCheck(int s,int c){
		for(int i=0;i<past.length;i++){
			if(past[i][0]==s&&(past[i][1]==c||c==-1))
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
		//System.out.println("データを受け取りました。");
		subMain AI = new subMain(H,hangar,costOfS,costOfC,num);
		//System.out.println("オブジェクト生成完了");
		List<String> ans = new ArrayList<String>();
		int nowS = -1, nowC = -1;
		while(AI.remain>0){
			int[] nextSC = AI.next(nowS,nowC);
			nowS = nextSC[0];
			nowC = nextSC[1];
			//System.out.println(nowS+","+nowC+"を試行します。");
			int N = AI.process(nowS,nowC,AI.inventory[nowS-1][nowC-1]);
			AI.inventory[nowS-1][nowC-1] = N;
			if(N==0){
				//System.out.println(nowS+","+nowC+"に対するrejection起動");
				AI.rejection(nowS-1,nowC-1);
			}
			//System.out.println("現在のremainは"+AI.remain+"です。");
		}
		//System.out.println("無事終了しました。");
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
