import java.io.*;
import java.util.*;
class subMain{
	public boolean superVisor = false, triggerH = false;
	public int hook, beforeS, beforeC, kindOfS, numOfProcess = 0, remain = 0, rest = 0, period = -1, pin = 0;
	public int[] hang, lastPin;
	public int[][] intervalS, intervalC, now, virtualNow, inventory, past;
	public int[][][][] cost;
	public List<String> MasterAns = new ArrayList<String>();
	public subMain(int h, int[] hangar, int[][] s, int[][] c, int[][] num){
		hook = h;
		hang = hangar;
		intervalS = s;
		intervalC = c;
		kindOfS = s.length;
		lastPin = new int[s.length+1];
		for(int i=0;i<lastPin.length;i++){
			lastPin[i] = c.length - 1;
		}
		past = new int[lastPin[0]][2];
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
		virtualNow = new int[hook][2];
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
		int number = num;
		if(num<=0)
			return 0;
		for(int i=0;i<now.length;i++){
			virtualNow[i][0] = now[i][0];
			virtualNow[i][1] = now[i][1];
		}
		while(true){
			numOfProcess++;
			period = (period + 1) % hook;
			if(hangerCheck(s)||intervalCheck(rest,beforeS,beforeC,s,c)){
				rest++;
				if(now[period][0]==0)
					MasterAns.add("-2");
				else{
					virtualNow[period][0] = 0;
					virtualNow[period][1] = 0;
					MasterAns.add("-1");
				}
			}
			else{
				rest = 0;
				virtualNow[period][0] = beforeS = s;
				virtualNow[period][1] = beforeC = c;
				for(int i=0;i<virtualNow.length;i++){
					now[i][0] = virtualNow[i][0];
					now[i][1] = virtualNow[i][1];
				}
				MasterAns.add(s+" "+c);
				remain--;
				number--;
				triggerH = false;
				if(past[0][0]!=0){
					for(int i=0;i<past.length;i++){
						past[i][0] = 0;
						past[i][1] = 0;
					}
					pin = 0;
				}
			}
			if(rest>40&&debug(pin,lastPin[s],triggerH,s,c)){
				superVisor = true;
				past[pin][0] = s;
				past[pin][1] = c;
				pin++;
				return number;
			}
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
		int num = 0;
		for(int i=0;i<now.length;i++){
			if(now[i][0]==s)
				num++;
		}
		if(num<hang[s-1])
			return false;
		if(s==now[period][0])
			return false;
		return true;
	}
	public boolean intervalCheck(int num,int s,int c,int ss,int cc){
		int max = Math.max(intervalS[s-1][ss-1],intervalC[c-1][cc-1]);
		if(num>=max||(s==ss&&c==cc))
			return false;
		return true;
	}
	public int[] next(int s,int c){
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
			int max2 = 0;
			int maxC = -1;
			for(int i=0;i<inventory[maxS].length;i++){
				if(inventory[maxS][i]>max2&&inventory[maxS][i]<max1){
					max2 = inventory[maxS][i];
					maxC = i;
				}
			}
			if(maxC==-1){
				maxS = 0;
				maxC = 0;
			}
			beforeS = ans[0] = (maxS + 1);
			beforeC = ans[1] = (maxC + 1);
		}
		else{
			if(superVisor){
				undo();
				superVisor = false;
			}
			int small = 99;
			int pickS = 0;
			int pickC = 0;
			searchS:for(int i=period+1;i<=period+40;i++){
				int ii = i % hook;
				if(now[ii][0]!=0){
					if(inventoryCheck(now[ii][0])){
						if(loopCheck(now[ii][0],-1)){
							pickS = now[ii][0];
							triggerH = true;
							break searchS;
						}
					}
				}
			}
			if(pickS==0){
				triggerH = false;
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
		lastPin[s+1]--;
		if(lastPin[s+1]==-1)
			kindOfS--;
	}
	public boolean inventoryCheck(int s){
		for(int i=0;i<intervalC.length;i++){
			if(inventory[s-1][i]==0)
				return false;
		}
		return true;
	}
	public void undo(){
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
		if(s==0)
			return false;
		if(c==-1){
			List<Integer> C = new ArrayList<Integer>();
			for(int i=0;i<past.length;i++){
				if(past[i][0]==s)
					C.add(past[i][1]);
			}
			for(int i=0;i<past.length;i++){
				for(int j=0;j<C.size();j++){
					if(past[i][0]==s&&past[i][1]==C.get(j))
						return false;
				}
			}
			return true;
		}
		else{
			for(int i=0;i<past.length;i++){
				if(past[i][0]==s&&past[i][1]==c)
					return false;
			}
			return true;
		}
	}
	public boolean debug(int p,int lp,boolean tri,int s,int c){
		if(p>=past.length)
			return false;
		if(tri){
			if(p<lp)
				return true;
			else
				return false;
		}
		if(kindOfS<=1){
			if(p<lp)
				return true;
			return false;
		}
		int[] temp = new int[intervalS.length];
		for(int i=0;i<intervalS.length;i++){
			for(int j=0;j<intervalC.length;j++){
				if((i+1)==s&&(j+1)==c)
					continue;
				if(inventory[i][j]>0&&loopCheck(i+1,j+1))
					return true;
			}
		}
		return false;
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
			int N = 0;
			N = AI.process(nowS,nowC,AI.inventory[nowS-1][nowC-1]);
			AI.inventory[nowS-1][nowC-1] = N;
			if(N==0)
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
