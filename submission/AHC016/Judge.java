import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.math.BigInteger;
class Judge{
	public static void main(String[] args)throws IOException{
		FileWriter fw_log = new FileWriter(new File("log.txt"),true);
		FileWriter fw_in = new FileWriter(new File("in.txt"));
		FileInputStream fs_in = new FileInputStream(new File(args[0]));
		FileInputStream fs_out = new FileInputStream(new File("out.txt"));
		BufferedReader br_in = new BufferedReader(new InputStreamReader(fs_in));
		BufferedReader br_out = new BufferedReader(new InputStreamReader(fs_out));
		PrintWriter log = new PrintWriter(fw_log,true);
		log.println(args[0]+":Judge started");
		String st = br_in.readLine();
		int M = Integer.parseInt(st.split(" ")[0]);
		String eps = st.split(" ")[1];
		int odd = eps.charAt(2)*10+eps.charAt(3)-'0'*11;
		fw_in.write(st+'\n');
		fw_in.flush();
		log.println(args[0]+":M,eps printed");
		String[] str = new String[M];
		while(!br_out.ready()){}
		int N = Integer.parseInt(br_out.readLine());
		for(int i=0;i<M;i++){
			while(!br_out.ready()){}
			str[i] = br_out.readLine();
		}
		br_out.close();
		log.println(args[0]+":Judge got graphs");
		int[] nums = new int[100];
		for(int i=0;i<100;i++)
			nums[i] = Integer.parseInt(br_in.readLine());
		log.println(args[0]+":Judge got number table");
		long seed = new BigInteger(br_in.readLine()).mod(BigInteger.valueOf(Long.MAX_VALUE)).longValue();
		log.println(args[0]+":Judge got seed");
		br_in.close();
		Random rm = new Random(seed);
		int size = str[0].length();
		for(int index:nums){
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<size;i++){
				int s = str[index].charAt(i)-'0';
				sb.append(rm.nextInt(100)<odd?1-s:s);
			}
			fw_in.write(sb.toString());
			fw_in.write('\n');
			fw_in.flush();
		}
		fw_in.close();
		log.println(args[0]+":Judge made test case");
		log.println();
	}
}