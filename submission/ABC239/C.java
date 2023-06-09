import java.io.*;
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int x1;
		int x2;
		int y1;
		int y2;
		String str1 = br.readLine();
		String[] str = str1.split(" ");
		if(str.length==1)
			str = str1.split("	");
		x1 = Integer.parseInt(str[0]);
		y1 = Integer.parseInt(str[1]);
		x2 = Integer.parseInt(str[2]);
		y2 = Integer.parseInt(str[3]);
		int x = Math.abs(x1-x2);
		int y = Math.abs(y1-y2);
		if((x==0&&(y==0||y==2||y==4))||((x==1||x==3)&&(y==1||y==3))||(x==2&&(y==0||y==4))||(x==4&&(y==0||y==2)))
			System.out.println("Yes");
		else
			System.out.println("No");
	}
}
