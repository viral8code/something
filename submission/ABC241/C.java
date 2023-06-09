//標準入力読み取り用
import java.io.*;

//入力値分解用
class subMain{
	public static String[] parStr(String someStr){
		String[] str = someStr.split("");
		return str;
	}
}

//メインクラス
class Main{
	public static void main(String[] args)throws IOException{

		//オブジェクトの生成
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//行(列)数取得
		int N = Integer.parseInt(br.readLine());

		//マス生成
		String[][] BW = new String[N][N];
		for(int i=0;i<N;i++){
			BW[i] = subMain.parStr(br.readLine());
		}

		//横の検証
		for(int i=0;i<N;i++){
			for(int j=0;j<(N-5);j++){
				int sharp = 0;
				for(int k=j;k<(j+6);k++){
					if(BW[i][k].equals("#"))
						sharp++;
				}
				if(sharp>=4){
					System.out.println("Yes");
					System.exit(0);
				}
			}
		}

		//縦の検証
		for(int i=0;i<(N-5);i++){
			for(int j=0;j<N;j++){
				int sharp = 0;
				for(int k=i;k<(i+6);k++){
					if(BW[k][j].equals("#"))
						sharp++;
				}
				if(sharp>=4){
					System.out.println("Yes");
					System.exit(0);
				}
			}
		}

		//右下方向の検証
		for(int i=0;i<(N-5);i++){
			for(int j=0;j<(N-5);j++){
				int sharp = 0;
				for(int k=0;k<6;k++){
					if(BW[i+k][j+k].equals("#"))
						sharp++;
				}
				if(sharp>=4){
					System.out.println("Yes");
					System.exit(0);
				}
			}
		}

		//右上方向の検証
		for(int i=5;i<N;i++){
			for(int j=0;j<(N-5);j++){
				int sharp = 0;
				for(int k=0;k<6;k++){
					if(BW[i-k][j+k].equals("#"))
						sharp++;
				}
				if(sharp>=4){
					System.out.println("Yes");
					System.exit(0);
				}
			}
		}

		//検出できなかった場合
		System.out.println("No");
	}
}
