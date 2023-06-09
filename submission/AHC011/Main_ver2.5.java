import java.io.*;
import java.util.*;
class Main{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);
	static Random rm = new Random();
	static Random seed = new Random();
	static boolean[][][] BigMap;
	static String[] movement = {"U","R","D","L"};
	static int[] limit = new int[4];
	static int[] move = {-1,1,1,-1};
	static int[] emp = new int[2];
	static int rand = 50000;
	static String Answer = "";
	public static boolean Mosaku = true;
	public static void main(String[] args)throws IOException{
		subMain sm = new subMain();
		sm.start();
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int T = Integer.parseInt(temp[1]);
		temp = null;
		limit[0] = limit[3] = 0;
		limit[1] = limit[2] = N-1;
		int[] emptyTail = new int[2];
		boolean[][][] map = new boolean[N][N][4];
		BigMap = new boolean[N][N][4];
		for(int i=0;i<N;i++){
			temp = br.readLine().split("");
			for(int j=0;j<N;j++){
				int num = Integer.parseInt(temp[j],16);
				if(num==0){
					emptyTail[0] = i;
					emptyTail[1] = j;
				}
				if((num&1)==1) map[i][j][3] = true;
				if((num&2)==2) map[i][j][0] = true;
				if((num&4)==4) map[i][j][1] = true;
				if((num&8)==8) map[i][j][2] = true;
			}
		}
		for(int i=0;Mosaku;i++){
			emp[0] = emptyTail[0];
			emp[1] = emptyTail[1];
			copyMap(map);
			Answer = "";
			process(T);
			sm.ans.put(Answer,getMap(BigMap));
			rm = new Random(seed.nextInt());
		}
	}
	public static void process(int t){
		int before = -1;
		int count = 0;
		for(int i=0;i<t&&Mosaku;i++){
			if(count>20) break;
			int temp = rm.nextInt(4);
			if(Math.abs(temp-before)==2){
				count++;
				i--;
			}
			else if(!canSelect(temp)){
				count++;
				i--;
			}
			else{
				count = 0;
				before = temp;
			}
		}
	}
	public static boolean canSelect(int select){
		if(emp[select%2]!=limit[select]){
			if(isBetter(select)){
				rand = Math.max(rand/5,50000);
				Answer += movement[select];
				moveTail(select);
				emp[(select%2)] += move[select];
				return true;
			}
			else if(seed.nextInt(rand)<1){
				rand = Math.max(rand*10,rand);
				Answer += movement[select];
				moveTail(select);
				emp[(select%2)] += move[select];
				return true;
			}
		}
		return false;
	}
	public static void moveTail(int select){
		if(select==0){
			BigMap[emp[0]][emp[1]] = BigMap[emp[0]-1][emp[1]];
			BigMap[emp[0]-1][emp[1]] = new boolean[4];
			BigMap[emp[0]-1][emp[1]][0] = false;
			BigMap[emp[0]-1][emp[1]][1] = false;
			BigMap[emp[0]-1][emp[1]][2] = false;
			BigMap[emp[0]-1][emp[1]][3] = false;
		}
		else if(select==1){
			BigMap[emp[0]][emp[1]] = BigMap[emp[0]][emp[1]+1];
			BigMap[emp[0]][emp[1]+1] = new boolean[4];
			BigMap[emp[0]][emp[1]+1][0] = false;
			BigMap[emp[0]][emp[1]+1][1] = false;
			BigMap[emp[0]][emp[1]+1][2] = false;
			BigMap[emp[0]][emp[1]+1][3] = false;
		}
		else if(select==2){
			BigMap[emp[0]][emp[1]] = BigMap[emp[0]+1][emp[1]];
			BigMap[emp[0]+1][emp[1]] = new boolean[4];
			BigMap[emp[0]+1][emp[1]][0] = false;
			BigMap[emp[0]+1][emp[1]][1] = false;
			BigMap[emp[0]+1][emp[1]][2] = false;
			BigMap[emp[0]+1][emp[1]][3] = false;
		}
		else if(select==3){
			BigMap[emp[0]][emp[1]] = BigMap[emp[0]][emp[1]-1];
			BigMap[emp[0]][emp[1]-1] = new boolean[4];
			BigMap[emp[0]][emp[1]-1][0] = false;
			BigMap[emp[0]][emp[1]-1][1] = false;
			BigMap[emp[0]][emp[1]-1][2] = false;
			BigMap[emp[0]][emp[1]-1][3] = false;
		}
		else{
			System.out.println("System Error");
			System.exit(1);
		}
	}
	public static boolean isBetter(int select){
		int reverse = ((select+2)%4);
		int before = score();
		moveTail(select);
		emp[(select%2)] += move[select];
		int after = score();
		moveTail(reverse);
		emp[(reverse%2)] += move[reverse];
		return (after>=before);
	}
	public static int score(){
		int answer = 0;
		boolean[][][] mapping = new boolean[BigMap.length][BigMap.length][2];
		for(int i=0;i<mapping.length;i++){
			for(int j=0;j<mapping[i].length;j++){
				Arrays.fill(mapping[i][j],true);
			}
		}
		for(int i=0;i<mapping.length;i++){
			for(int j=0;j<mapping[i].length;j++){
				if(mapping[i][j][0]){
					int temp = dp(i,j,mapping);
					if(temp>answer)
						answer = temp;
				}
			}
		}
		return answer;
	}
	public static int dp(int i,int j,boolean[][][] mapping){
		mapping[i][j][0] = false;
		int S = 1;
		if(i>0&&BigMap[i][j][0]&&BigMap[i-1][j][2]){
			if(mapping[i-1][j][0])
				S += dp(i-1,j,mapping);
			else if(!mapping[i-1][j][1])
				S = -100;
		}
		if(j>0&&BigMap[i][j][3]&&BigMap[i][j-1][1]){
			if(mapping[i][j-1][0])
				S += dp(i,j-1,mapping);
			else if(!mapping[i][j-1][1])
				S = -100;
		}
		if(i<mapping.length-1&&BigMap[i][j][2]&&BigMap[i+1][j][0]){
			if(mapping[i+1][j][0])
				S += dp(i+1,j,mapping);
			else if(!mapping[i+1][j][1])
				S = -100;
		}
		if(j<mapping.length-1&&BigMap[i][j][1]&&BigMap[i][j+1][3]){
			if(mapping[i][j+1][0])
				S += dp(i,j+1,mapping);
			else if(!mapping[i][j+1][1])
				S = -100;
		}
		mapping[i][j][1] = false;
		return Math.max(S,0);
	}
	public static void pri(String str){pw.print(str);}
	public static void exit(){pw.println();pw.close();System.exit(0);}
	public static void copyMap(boolean[][][] ma){
		for(int i=0;i<ma.length;i++){
			for(int j=0;j<ma[i].length;j++){
				for(int k=0;k<ma[i][j].length;k++){
					BigMap[i][j][k] = ma[i][j][k];
				}
			}
		}
	}
	public static Boolean[][][] getMap(boolean[][][] ma){
		Boolean[][][] ans = new Boolean[ma.length][][];
		for(int i=0;i<ma.length;i++){
			ans[i] = new Boolean[ma[i].length][];
			for(int j=0;j<ma[i].length;j++){
				ans[i][j] = new Boolean[ma[i][j].length];
				for(int k=0;k<ma[i][j].length;k++){
					ans[i][j][k] = ma[i][j][k];
				}
			}
		}
		return ans;
	}
	public static void setMap(Boolean[][][] ma){
		for(int i=0;i<ma.length;i++){
			for(int j=0;j<ma[i].length;j++){
				for(int k=0;k<ma[i][j].length;k++){
					BigMap[i][j][k] = (boolean)ma[i][j][k];
				}
			}
		}
	}
}
class subMain extends Thread{
	HashMap<String,Boolean[][][]> ans = new HashMap<String,Boolean[][][]>();
	public void run(){
		try{
			Thread.sleep(2800);
			Main.Mosaku = false;
			String answer = "";
			int max = -1;
			for(String temp : ans.keySet()){
				Main.setMap(ans.get(temp));
				int score = Main.score();
				if(max<score||(max==score&&answer.length()<temp.length())){
					max = score;
					answer = temp;
				}
			}
			Main.setMap(ans.get(answer));
			for(int i=0;i<Main.BigMap.length;i++){
				for(int j=0;j<Main.BigMap[i].length;j++){
					if(!(Main.BigMap[i][j][0]||
					   Main.BigMap[i][j][1]||
					   Main.BigMap[i][j][2]||
					   Main.BigMap[i][j][3])
					){
						Main.emp[0] = i;
						Main.emp[1] = j;
					}
				}
			}
			while(true){
				String str = answer.substring(answer.length()-1,answer.length());
				int temp = -1;
				for(int j=0;j<Main.movement.length;j++){
					if(Main.movement[j].equals(str)){
						temp = (j+2)%4;
						break;
					}
				}
				if(temp==-1) break;
				Main.moveTail(temp);
				Main.emp[temp%2] += Main.move[temp];
				if((temp=Main.score())>=max){
					max = temp;
					answer = answer.substring(0,answer.length()-1);
				}
				else break;
			}
			Main.pri(answer);
			Main.exit();
		}catch(Exception e){}
	}
}