import java.io.*;
class SubMain{
	public int[] parsingInt(String someInt){
		int[] fourInt = new int[4];
		String[] str = someInt.split(" ");
		fourInt[0] = Integer.parseInt(str[0]);
		fourInt[1] = Integer.parseInt(str[1]);
		fourInt[2] = Integer.parseInt(str[2]);
		fourInt[3] = Integer.parseInt(str[3]);
		return fourInt;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SubMain sm = new SubMain();
		int[] ABCD = sm.parsingInt(br.readLine());
		int[] PrimeTable = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
				    43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
				    101, 103, 107, 109, 113, 127, 131, 137, 139, 149,
				    151, 157, 163, 167, 173, 179, 181, 191, 193, 197};
		int TakahashiCanSelect = ABCD[1] - ABCD[0] + 1;
		int AokiCanSelect = 0;
		for(int i=1;i<=100;i++){
			if(i>ABCD[1])
				break;
			else if(i<ABCD[0])
				continue;
			for(int j=ABCD[2];j<=ABCD[3];j++){
				int check = i + j;
				boolean breaker = false;
				for(int k=0;k<PrimeTable.length;k++){
					if(PrimeTable[k]==check){
						AokiCanSelect++;
						breaker = true;
						break;
					}
				}
				if(breaker)
					break;
			}
		}
		if(TakahashiCanSelect>AokiCanSelect)
			System.out.println("Takahashi");
		else
			System.out.println("Aoki");
	}
}
