import java.io.*;
class Main{
  public static void main(String[] args)throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    subMain sm = new subMain();
    int[] two = sm.parsingInt(br.readLine());
    if((two[0]-two[1])==1||(two[0]-two[1])==-1||(two[0]-two[1])==-9)
      System.out.println("Yes");
    else
      System.out.println("No");
  }
}
class subMain{
  public int[] parsingInt(String someInt){
    int[] twoInt = new int[2];
    String[] str = someInt.split(" ");
    if(str.length==1)
      str = someInt.split("	");
    twoInt[0] = Integer.parseInt(str[0]);
    twoInt[1] = Integer.parseInt(str[1]);
    return twoInt;
  }
}