import java.io.*;
import java.util.*;
class Main{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter pw = new PrintWriter(System.out);
	static Random rm = new Random();
	static boolean[][][] BigMap;
	static String[] movement = {"U","R","D","L"};
	static int[] limit = new int[4];
	static int[] move = {-1,1,1,-1};
	static int[] emp;
	static int rand = 10000000;
	public static void main(String[] args)throws IOException{
		new subMain().start();
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
		copyMap(map);
		emp = emptyTail;
		process(T);
		exit();
	}
	public static void process(int t){
		int before = -1;
		//int count = 0;
		for(int i=0;i<t;i++){
			//if(t<count/20) break;
			int temp = rm.nextInt(4);
			if(Math.abs(temp-before)==2){
				//count++;
				i--;
			}
			else if(!canSelect(temp)){
				//count++;
				i--;
			}
			else{
				before = temp;
			}
		}
	}
	public static boolean canSelect(int select){
		if(emp[select%2]!=limit[select]){
			if(isGood(select)>=isntBad(select)){
				rand = Math.max(rand/2,1);
				pri(movement[select]);
				moveTail(select);
				emp[(select%2)] += move[select];
				return true;
			}
			if(rm.nextInt(rand)<1){
				rand = Math.max(rand,rand*50);
				pri(movement[select]);
				moveTail(select);
				emp[(select%2)] += move[select];
				return true;
			}
		}
		return false;
	}
	public static byte isGood(int select){
		byte count = 0;
		if(select==0){
			if(BigMap[emp[0]-1][emp[1]][1]&&emp[1]!=limit[1]&&BigMap[emp[0]][emp[1]+1][3]) count++;
			if(BigMap[emp[0]-1][emp[1]][2]&&emp[0]!=limit[2]&&BigMap[emp[0]+1][emp[1]][0]) count++;
			if(BigMap[emp[0]-1][emp[1]][3]&&emp[1]!=limit[3]&&BigMap[emp[0]][emp[1]-1][1]) count++;
		}
		else if(select==1){
			if(BigMap[emp[0]][emp[1]+1][0]&&emp[0]!=limit[0]&&BigMap[emp[0]-1][emp[1]][2]) count++;
			if(BigMap[emp[0]][emp[1]+1][2]&&emp[0]!=limit[2]&&BigMap[emp[0]+1][emp[1]][0]) count++;
			if(BigMap[emp[0]][emp[1]+1][3]&&emp[1]!=limit[3]&&BigMap[emp[0]][emp[1]-1][1]) count++;
		}
		else if(select==2){
			if(BigMap[emp[0]+1][emp[1]][0]&&emp[0]!=limit[0]&&BigMap[emp[0]-1][emp[1]][2]) count++;
			if(BigMap[emp[0]+1][emp[1]][1]&&emp[1]!=limit[1]&&BigMap[emp[0]][emp[1]+1][3]) count++;
			if(BigMap[emp[0]+1][emp[1]][3]&&emp[1]!=limit[3]&&BigMap[emp[0]][emp[1]-1][1]) count++;
		}
		else if(select==3){
			if(BigMap[emp[0]][emp[1]-1][0]&&emp[0]!=limit[0]&&BigMap[emp[0]-1][emp[1]][2]) count++;
			if(BigMap[emp[0]][emp[1]-1][1]&&emp[1]!=limit[1]&&BigMap[emp[0]][emp[1]+1][3]) count++;
			if(BigMap[emp[0]][emp[1]-1][2]&&emp[0]!=limit[2]&&BigMap[emp[0]+1][emp[1]][0]) count++;
		}
		else if(select<0||select>3){
			System.out.println("System Error");
			System.exit(1);
		}
		return count;
	}
	public static byte isntBad(int select){
		byte count = 0;
		if(select==0){
			if(BigMap[emp[0]-1][emp[1]][0]&&emp[0]-1>limit[0]&&BigMap[emp[0]-2][emp[1]][2]) count++;
			if(BigMap[emp[0]-1][emp[1]][1]&&emp[1]!=limit[1]&&BigMap[emp[0]-1][emp[1]+1][3]) count++;
			if(BigMap[emp[0]-1][emp[1]][3]&&emp[1]!=limit[3]&&BigMap[emp[0]-1][emp[1]-1][1]) count++;
		}
		else if(select==1){
			if(BigMap[emp[0]][emp[1]+1][0]&&emp[0]!=limit[0]&&BigMap[emp[0]-1][emp[1]+1][2]) count++;
			if(BigMap[emp[0]][emp[1]+1][1]&&emp[1]+1<limit[1]&&BigMap[emp[0]][emp[1]+2][3]) count++;
			if(BigMap[emp[0]][emp[1]+1][2]&&emp[0]!=limit[2]&&BigMap[emp[0]+1][emp[1]+1][0]) count++;
		}
		else if(select==2){
			if(BigMap[emp[0]+1][emp[1]][1]&&emp[1]!=limit[1]&&BigMap[emp[0]+1][emp[1]+1][3]) count++;
			if(BigMap[emp[0]+1][emp[1]][2]&&emp[0]+1<limit[2]&&BigMap[emp[0]+2][emp[1]][0]) count++;
			if(BigMap[emp[0]+1][emp[1]][3]&&emp[1]!=limit[3]&&BigMap[emp[0]+1][emp[1]-1][1]) count++;
		}
		else if(select==3){
			if(BigMap[emp[0]][emp[1]-1][0]&&emp[0]!=limit[0]&&BigMap[emp[0]-1][emp[1]-1][2]) count++;
			if(BigMap[emp[0]][emp[1]-1][2]&&emp[0]!=limit[2]&&BigMap[emp[0]+1][emp[1]-1][0]) count++;
			if(BigMap[emp[0]][emp[1]-1][3]&&emp[1]-1>limit[3]&&BigMap[emp[0]][emp[1]-2][1]) count++;
		}
		else if(select<0||select>3){
			System.out.println("System Error");
			System.exit(1);
		}
		return count;
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
}
class subMain extends Thread{
	public void run(){
		try{
			Thread.sleep(2800);
			Main.exit();
		}catch(Exception e){}
	}
}