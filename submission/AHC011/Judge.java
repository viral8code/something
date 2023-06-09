import java.io.*;
class Judge extends Thread{
	int n;
	public Judge(int n){
		this.n = n;
	}
	public static void main(String[] args){
		try{
			Runtime rt1 = Runtime.getRuntime();
			rt1.exec("javac -encoding UTF-8 Main_ver2.java");
			judge:for(int i=0;i<200;i++){
				Judge jg = new Judge(i);
				jg.start();
			}
		} catch(Exception e){
			System.out.println("Fuck!We found exception!");
			System.out.println(e);
		}
	}
	public void run(){
		try{
			Runtime rt = Runtime.getRuntime();
			String[] pro = {"cmd","/c","java Main < in\\"+String.format("%04d", n)+".txt > out\\"+String.format("%04d", n)+".txt"};
			rt.exec(pro);
			Thread.sleep(3000);
			System.out.println(String.format("%04d",n)+" is finished.");
		} catch(Exception e){
			System.out.println("Fuck!We found exception!");
			System.out.println(e);
		}
	}
}
