import java.util.*;
import java.io.*;
import java.util.stream.*;
class Main{
	public static void main(String[] args){
		final int count = 100;
		final int cycle = 10;
		long sumA = 0;
		long sumB = 0;
		long sumC = 0;
		long s,f;
		for(int $=0;$<cycle;$++){
			s = System.nanoTime();
			IntStream.range(1,count+1).sequential().forEach(i->run(i));
			f = System.nanoTime();
			sumA += (f-s)/1000000;
			s = System.nanoTime();
			IntStream.range(1,count+1).parallel().forEach(i->run(i));
			f = System.nanoTime();
			sumB += (f-s)/1000000;
			s = System.nanoTime();
			IntStream.range(1,count+1).forEach(i->run(i));
			f = System.nanoTime();
			sumC += (f-s)/1000000;
		}
		System.out.println("Sequential:"+sumA+"ms");
		System.out.println("Parallel:"+sumB+"ms");
		System.out.println("NoMarked:"+sumC+"ms");
	}
	public static void run(int i){
		test(String.format("file/%05d.txt",i));
	}
	public static void test(String filePath){
		try{
			FileWriter fw = new FileWriter(filePath);
			final int size = 200000;
			Random rm = new Random(System.nanoTime());
			for(int i=0;i<size;i++){
				fw.write(""+rm.nextInt());
				fw.write('\n');
			}
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		File f = new File(filePath);
		f.delete();
	}
}
