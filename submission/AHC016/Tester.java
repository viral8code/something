import java.io.*;
import java.util.*;
import java.math.BigInteger;
class Tester{
	public static void main(String[] args)throws IOException,InterruptedException{
		Process pw = Runtime.getRuntime().exec(new String[]{"cmd","/c","javac Judge.java"});
		while(pw.isAlive()){}
		BufferedReader br = new BufferedReader(new InputStreamReader(pw.getErrorStream(),"Shift-JIS"));
		if(br.ready()){
			while(br.ready())
				System.out.println(br.readLine());
			return;
		}
		br.close();
		pw = Runtime.getRuntime().exec(new String[]{"cmd","/c","javac "+args[0]});
		br = new BufferedReader(new InputStreamReader(pw.getErrorStream(),"Shift-JIS"));
		while(pw.isAlive()){}
		if(br.ready()){
			while(br.ready())
				System.out.println(br.readLine());
			return;
		}
		br.close();
		System.out.println("Compiled!");
		long score = 0;
		boolean isAC = true;
		BigInteger NINE = BigInteger.valueOf(9L);
		for(int i=0;i<100;i++){
			fileReset();
			String name = "in/"+String.format("%04d",i)+".txt";
			Process pw1 = Runtime.getRuntime().exec(new String[]{"cmd","/c","java Judge "+name});
			System.out.println("Execution!");
			Process pw2 = Runtime.getRuntime().exec(new String[]{"cmd","/c","java Main < in.txt > out.txt"});
			long sTime = System.nanoTime();
			pw2.waitFor();
			long fTime = System.nanoTime();
			pw1.getInputStream().close();
			pw1.getOutputStream().close();
			System.out.println("Judge>");
			BufferedReader br_err = new BufferedReader(new InputStreamReader(pw1.getErrorStream(),"Shift-JIS"));
			while(br_err.ready())
				System.out.println(br_err.readLine());
			br_err.close();
			pw1.destroy();
			pw2.getInputStream().close();
			pw2.getOutputStream().close();
			br_err = new BufferedReader(new InputStreamReader(pw2.getErrorStream(),"Shift-JIS"));
			System.out.println("Main>");
			while(br_err.ready())
				System.out.println(br_err.readLine());
			br_err.close();
			pw2.destroy();
			BufferedReader br_ans = new BufferedReader(new InputStreamReader(new FileInputStream(new File(name))));
			BufferedReader br_out = new BufferedReader(new InputStreamReader(new FileInputStream(new File("out.txt"))));
			int M = Integer.parseInt(br_ans.readLine().split(" ")[0]);
			int N = Integer.parseInt(br_out.readLine());
			while(M-->0)br_out.readLine();
			int count = 0;
			for(int j=0;j<100;j++)
				if(!br_ans.readLine().equals(br_out.readLine()))
					count++;
			BigInteger ans = BigInteger.valueOf((long)1e9);
			for(int j=0;j<count;j++)
				ans = ans.multiply(NINE);
			while(count-->0)
				ans = ans.divide(BigInteger.TEN);
			long scr = ans.divide(BigInteger.valueOf(N)).longValue();
			score += scr;
			System.out.println(name+":"+scr+"("+(int)((fTime-sTime)/1e6)+"ms)");
			isAC &= (fTime-sTime)/1e6<5e3;
		}
		System.out.println(score);
		System.out.println(isAC?"AC":"TLE");
	}
	private final static void fileReset()throws IOException{
		FileWriter file1 = new FileWriter("in.txt");
		FileWriter file2 = new FileWriter("out.txt");
		PrintWriter pw;
		pw = new PrintWriter(new BufferedWriter(file1));
		pw.print("");
		pw.close();
		pw = new PrintWriter(new BufferedWriter(file2));
		pw.print("");
		pw.close();
	}
}