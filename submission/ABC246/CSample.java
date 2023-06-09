import java.util.Random;
import java.io.*;

class CSample{
	public static void main(String[] args)throws IOException{
		Random random = new Random();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("input.txt")));
		pw.println(200000+" "+random.nextInt(1000000000)+" "+random.nextInt(1000000000));
		for(int i=0;i<200000;i++){
			pw.print(random.nextInt(1000000000)+" ");
		}
		pw.println();
		pw.close();
		System.out.println("出力完了");
	}
}