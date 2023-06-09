import java.io.*;
class subMain{
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] A = subMain.parStr(br.readLine());
		String[] B = subMain.parStr(br.readLine());
		String[] C = subMain.parStr(br.readLine());
		String turn = "a";
		int pickOfA = 0,pickOfB = 0,pickOfC = 0;
		Game:while(true){
			if(turn.equals("a")){
				if(pickOfA==A.length){
					System.out.println("A");
					break Game;
				}
				turn = A[pickOfA];
				pickOfA++;
			}
			else if(turn.equals("b")){
				if(pickOfB==B.length){
					System.out.println("B");
					break Game;
				}
				turn = B[pickOfB];
				pickOfB++;
			}
			else if(turn.equals("c")){
				if(pickOfC==C.length){
					System.out.println("C");
					break Game;
				}
				turn = C[pickOfC];
				pickOfC++;
			}
		}
	}
}