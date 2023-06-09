import java.util.*;
class Main{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt();
    int L = sc.nextInt();
    String decoy = sc.nextLine();
    String[] str = new String[N];
    for(int i=0;i<N;i++){
      str[i] = sc.nextLine();
    }
    Arrays.sort(str);
    for(int i=0;i<N;i++){
      System.out.print(str[i]);
    }
    System.out.println();
  }
}