import java.io.*;
class Main{
  public static void main(String[] args)throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    double H = (double)Integer.parseInt(br.readLine()) / 10000.0;
    double ans = Math.sqrt(H*(1280.0+H));
    System.out.println(ans*10000.0);
  }
}