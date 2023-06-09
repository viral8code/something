import java.io.*;
import java.util.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] tail = new int[30][30];
		for(int i=0;i<30;i++){
			tail[i] = subMain.parInt(br.readLine());
		}
		String answer = "";
		if(tail[0][0]==1){
			answer += "1";
			tail[0][0]++;
		}
		else if(tail[0][0]==5){
			answer += "1";
			tail[0][0]--;
		}
		else
			answer += "0";
		for(int j=1;j<tail[0].length-1;j++){
			if(tail[0][j]==0&&
				(tail[1][j]==0||tail[1][j]==3||tail[1][j]==4||tail[1][j]==5||tail[1][j]==7||tail[1][j]==2)&&
				(tail[0][j-1]==2||tail[0][j-1]==3||tail[0][j-1]==4||tail[0][j-1]==5||tail[0][j-1]==6)
			){
				answer = answer + "1";
				tail[0][j]++;
			}
			else if((tail[0][j]==1||tail[0][j]==5)&&
				!(tail[0][j-1]==2||tail[0][j-1]==3||tail[0][j-1]==4||tail[0][j-1]==5||tail[0][j-1]==6)&&
				(tail[0][j+1]==0||tail[0][j+1]==1||tail[0][j+1]==4||tail[0][j+1]==5||tail[0][j+1]==6||tail[0][j+1]==3)&&
				(tail[1][j]==0||tail[1][j]==3||tail[1][j]==4||tail[1][j]==5||tail[1][j]==7||tail[1][j]==2)
			){
				answer = answer + "1";
				tail[0][j]++;
			}
			else if(tail[0][j]==7){
				answer += "1";
				tail[0][j]--;
			}
			else
				answer += "0";
		}
		if(tail[0][29]==0||tail[0][29]==3){
			answer += "1";
			tail[0][29] = (tail[0][29]+1)%4;
		}
		else if(tail[0][29]==4){
			answer += "1";
			tail[0][29]++;
		}
		else
			answer += "0";
		for(int i=1;i<tail.length-1;i++){
			if(tail[i][0]==1||tail[i][0]==6){
				answer += "1";
				tail[i][0]++;
			}
			else
				answer += "0";
			for(int j=1;j<tail[i].length-1;j++){
				if(tail[i][j]==0&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
					(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					!(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
				){
					answer = answer + "1";
					tail[i][j]++;
				}
				else if(tail[i][j]==1&&
					!(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)
				){
					answer = answer + "1";
					tail[i][j]++;
				}
				else if(tail[i][j]==2&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)&&
					!(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
				){
					answer = answer + "1";
					tail[i][j]++;
				}
				else if(tail[i][j]==3&&
					(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)&&
					!(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)
				){
					answer = answer + "1";
					tail[i][j] = 0;
				}
				else if(tail[i][j]==6&&
					!((tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3))&&
					(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
				){
					answer = answer + "1";
					tail[i][j]++;
				}
				else if(tail[i][j]==7&&
					(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
					!((tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
					(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7))&&
					(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)
				){
					answer = answer + "1";
					tail[i][j]--;
				}
				else
					answer = answer + "0";
			}
			if(tail[i][29]==3){
				answer += "1";
				tail[i][29] = 0;
			}
			else if(tail[i][29]==6){
				answer += "1";
				tail[i][29]++;
			}
			else
				answer += "0";
		}
		if(tail[29][0]==2||tail[29][0]==4){
			answer += "1";
			tail[29][0]++;
		}
		else
			answer += "0";
		for(int j=1;j<tail[29].length-1;j++){
			if(tail[29][j]==2){
				answer = answer + "1";
				tail[29][j]++;
			}
			else if(tail[29][j]==3&&
				(tail[29][j-1]==2||tail[29][j-1]==3||tail[29][j-1]==4||tail[29][j-1]==5||tail[29][j-1]==6)&&
				(tail[28][j]==1||tail[28][j]==2||tail[28][j]==4||tail[28][j]==5||tail[28][j]==7)&&
				!(tail[29][j+1]==0||tail[29][j+1]==1||tail[29][j+1]==4||tail[29][j+1]==5||tail[29][j+1]==6||tail[29][j+1]==3)
			){
				answer = answer + "1";
				tail[29][j] = 0;
			}
			else if(tail[29][j]==7){
				answer = answer + "1";
				tail[29][j]--;
			}
			else
				answer = answer + "0";
		}
		if(tail[29][29]==3){
			answer += "1";
			tail[29][29] = 0;
		}
		else if(tail[29][29]==5){
			answer += "1";
			tail[29][29]--;
		}
		else
			answer += "0";
		Random random = new Random();
		for(int loop=0;loop<5000;loop++){
			for(int i=1;i<tail.length-1;i++){
				for(int j=1;j<tail[i].length-1;j++){
					if(tail[i][j]==0&&answer.substring(i*30+j,i*30+j+1).equals("0")&&
						(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
						(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
						!(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
					){
						answer = answer.substring(0,i*30+j)+"1"+answer.substring(i*30+j+1,answer.length());
						tail[i][j]++;
					}
					else if(tail[i][j]==1&&answer.substring(i*30+j,i*30+j+1).equals("0")&&
						!(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
						(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)&&
						(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)
					){
						answer = answer.substring(0,i*30+j)+"1"+answer.substring(i*30+j+1,answer.length());
						tail[i][j]++;
					}
					else if(tail[i][j]==2&&answer.substring(i*30+j,i*30+j+1).equals("0")&&
						(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)&&
						!(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
						(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
					){
						answer = answer.substring(0,i*30+j)+"1"+answer.substring(i*30+j+1,answer.length());
						tail[i][j]++;
					}
					else if(tail[i][j]==3&&answer.substring(i*30+j,i*30+j+1).equals("0")&&
						(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
						(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)&&
						!(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)
					){
						answer = answer.substring(0,i*30+j)+"1"+answer.substring(i*30+j+1,answer.length());
						tail[i][j] = 0;
					}
					else if(tail[i][j]==6&&answer.substring(i*30+j,i*30+j+1).equals("0")&&
						!((tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
						(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3))&&
						(tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
						(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7)
					){
						answer = answer.substring(0,i*30+j)+"1"+answer.substring(i*30+j+1,answer.length());
						tail[i][j]++;
					}
					else if(tail[i][j]==7&&answer.substring(i*30+j,i*30+j+1).equals("0")&&
						(tail[i][j-1]==2||tail[i][j-1]==3||tail[i][j-1]==4||tail[i][j-1]==5||tail[i][j-1]==6)&&
						!((tail[i+1][j]==0||tail[i+1][j]==3||tail[i+1][j]==4||tail[i+1][j]==5||tail[i+1][j]==7||tail[i+1][j]==2)&&
						(tail[i-1][j]==1||tail[i-1][j]==2||tail[i-1][j]==4||tail[i-1][j]==5||tail[i-1][j]==7))&&
						(tail[i][j+1]==0||tail[i][j+1]==1||tail[i][j+1]==4||tail[i][j+1]==5||tail[i][j+1]==6||tail[i][j+1]==3)
					){
						answer = answer.substring(0,i*30+j)+"1"+answer.substring(i*30+j+1,answer.length());
						tail[i][j]--;
					}
					if((random.nextInt(100)+1)<=10){
						int temp = (Integer.parseInt(answer.substring(i*30+j,i*30+j+1))+1)%2;
						answer = answer.substring(0,i*30+j)+
							String.valueOf(temp)+
							answer.substring(i*30+j+1,answer.length());
						if(temp==1){
							if(tail[i][j]<3||tail[i][j]==4||tail[i][j]==6)
								tail[i][j]++;
							else if(tail[i][j]==3)
								tail[i][j] = 0;
							else
								tail[i][j]--;
						}
						else{
							if(tail[i][j]==4||tail[i][j]==6)
								tail[i][j]++;
							else if(tail[i][j]==0)
								tail[i][j] = 3;
							else
								tail[i][j]--;
						}
					}
				}
			}
		}
		System.out.println(answer);
	}
}
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
}
