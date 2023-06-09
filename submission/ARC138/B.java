import java.io.*;
import java.util.*;
class subMain{
	public static int[] parInt(String someInt){
		String[] str = someInt.split("");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
	public static int[] parIntWithS(String someInt){
		String[] str = someInt.split(" ");
		int[] Intel = new int[str.length];
		for(int i=0;i<str.length;i++){
			Intel[i] = Integer.parseInt(str[i]);
		}
		return Intel;
	}
	public static String[] parStrWithS(String someStr){
		String[] str = someStr.split(" ");
		return str;
	}
	public static int[] readInts(int num){
		Scanner sc = new Scanner(System.in);
		int[] ans = new int[num];
		for(int i=0;i<num;i++){
			ans[i] = sc.nextInt();
		}
		return ans;
	}
}
class Main{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		List<Byte> list = new ArrayList<Byte>();
		for(int i=0;i<N;i++){
			list.add(sc.nextByte());
		}
		while(list.size()>3){
			if(list.get(0)==1){
				System.out.println("No");
				System.exit(0);
			}
			else if(list.get(list.size()-1)==0)
				list.remove(list.size()-1);
			else{
				list.remove(0);
				for(int i=0;i<list.size();i++){
					if(list.get(i)==1)
						list.set(i,(byte)0);
					else
						list.set(i,(byte)1);
				}
			}
		}
		if(list.get(0)==1){
			System.out.println("No");
			System.exit(0);
		}
		if(list.size()==3&&(list.get(0)==1||(list.get(0)==0&&list.get(1)==0&&list.get(2)==1))){
			System.out.println("No");
			System.exit(0);
		}
		System.out.println("Yes");
	}
}
