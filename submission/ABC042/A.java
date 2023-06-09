import java.util.*;
class Main{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int A = sc.nextInt();
    int B = sc.nextInt();
    int C = sc.nextInt();
    int count5 = 0;
    int count7 = 0;
    if(A==5)
      count5++;
    else if(A==7)
      count7++;
    if(B==5)
      count5++;
    else if(B==7)
      count7++;
    if(C==5)
      count5++;
    else if(C==7)
      count7++;
    if(count5==2&&count7==1)
      System.out.println("YES");
    else
      System.out.println("NO");
  }
}