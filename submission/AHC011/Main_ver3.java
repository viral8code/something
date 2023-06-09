import java.io.*;
import java.util.*;
class Main{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);
	static Random rm = new Random();
	static Random seed = new Random();
	static boolean[][][] BigMap;
	static String[] movement = {"L","U","R","D"};
	static int[] limit = new int[4];
	static int[] move = {-1,1,1,-1};
	static int[] emp = new int[2];
	static int rand = 10000000;
	static String Answer = "";
	public static boolean Mosaku = true;
	public static void main(String[] args)throws IOException{
		subMain sm = new subMain();
		sm.start();
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int T = Integer.parseInt(temp[1]);
		temp = null;
		limit[0] = limit[3] = 1;
		limit[1] = limit[2] = N-2;
		int[] emptyTail = new int[2];
		boolean[][][] map = new boolean[N][N][4];
		char[][] genshiMap = new char[N][N];
		BigMap = new boolean[N][N][4];
		for(int i=0;i<N;i++){
			temp = br.readLine().split("");
			for(int j=0;j<N;j++){
				genshimap[i][j] = temp[j].toCharArray()[0];
				int num = Integer.parseInt(temp[j],16);
				if(num==0){
					emptyTail[0] = i;
					emptyTail[1] = j;
				}
				if((num&1)==1) map[i][j][0] = true;
				if((num&2)==2) map[i][j][1] = true;
				if((num&4)==4) map[i][j][2] = true;
				if((num&8)==8) map[i][j][3] = true;
			}
		}
		BigMap = map;
		String before = prepare(genshimap,emptyTail);
		for(int i=0;Mosaku;i++){
			emp[0] = emptyTail[0];
			emp[1] = emptyTail[1];
			copyMap(map);
			Answer = before;
			process(T);
			sm.ans.put(Answer,getMap(BigMap));
			rm = new Random(seed.nextInt());
		}
	}
	public static String prepare(boolean[][][] map,char[][] genshiMap,int[] empty){
		if(genshiMap[0][0]!='6'){
			boolean notFound = true;
			for(int i=0;i<genshiMap.length;i++){
				for(int j=0;j<genshimap[i].length;j++){
					if(genshiMap[i][j]=='6'){
						moving(i,j,0,0,empty);
						notFound = false;
						break;
					}
				}
			}
			if(notFound){
				for(int i=0;i<genshiMap.length;i++){
					for(int j=0;j<genshimap[i].length;j++){
						if(genshiMap[i][j]=='2'||genshiMap[i][j]=='4'){
							moving(i,j,0,0,empty);
							notFound = false;
							break;
						}
					}
				}
			}
	public static void process(int t){
		int before = -1;
		int count = 0;
		for(int i=0;i<t&&Mosaku;i++){
			if(count>10) break;
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
		boolean[][] mapping = new boolean[BigMap.length][BigMap.length];
		for(int i=0;i<mapping.length;i++){
			Arrays.fill(mapping[i],true);
		}
		for(int i=0;i<mapping.length;i++){
			for(int j=0;j<mapping[i].length;j++){
				if(mapping[i][j]){
					int temp = dp(i,j,mapping);
					if(temp>answer)
						answer = temp;
				}
			}
		}
		return answer;
	}
	public static int dp(int i,int j,boolean[][] mapping){
		mapping[i][j] = false;
		int S = 1;
		if(i>0&&mapping[i-1][j]&&BigMap[i][j][0]&&BigMap[i-1][j][2])
			S += dp(i-1,j,mapping);
		if(j>0&&mapping[i][j-1]&&BigMap[i][j][3]&&BigMap[i][j-1][1])
			S += dp(i,j-1,mapping);
		if(i<mapping.length-1&&mapping[i+1][j]&&BigMap[i][j][2]&&BigMap[i+1][j][0])
			S += dp(i+1,j,mapping);
		if(j<mapping.length-1&&mapping[i][j+1]&&BigMap[i][j][1]&&BigMap[i][j+1][3])
			S += dp(i,j+1,mapping);
		return S;
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
			Main.pri(answer);
			Main.exit();
		}catch(Exception e){}
	}
}