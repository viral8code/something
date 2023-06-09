import java.util.*;
class Test {

	// 相異なる数からなる順列permInに対して、
	// 辞書順で次の順列を返す。
	// ない場合(permInが最後の場合）はnullを返す
	public static int[] next(int[] permIn) {
		int[] perm = permIn.clone(); // 戻り値用配列
		//(1)末尾の降順部分(少なくとも1個)+1個をpermから除去して
		// 集合wに入れる
		TreeSet<Integer> w = new TreeSet<Integer>();
		int descendingHead = -1; //降順部分の先頭の数の記憶
		int key = -1; //+1個の数の記憶
		int idx = perm.length; //perm内の有効データ数の記憶
		while (true) {
			if (idx == 0) return null; // permIn全体が降順列だった場合
			key = perm[--idx]; // permの末尾の数を除去し集合wに入れる
			w.add(key);				
			if (key < descendingHead) break; // keyが降順列にないならば抜ける
			descendingHead = key; // 降順部分の先頭の更新
		}
		// この時点で、wは除去した数の集合。keyはwの中の最大値ではない。
		//(2)集合wの中でkeyの次に大きい数key2を調べてwから削除
		// permに入れる
		int key2 = w.higher(key); 
		w.remove(key2);
		perm[idx++] = key2;
		//(3)集合wの元を昇順にpermに入れる
		for (int i : w) {
			perm[idx++] = i;
		}
		return perm;
	}
	
	public static void main(String[] args){
		int[] perm = {1,2,3,4,5};
		int sum = 0;
		while (perm != null) {
			System.out.print(Arrays.toString(perm));
			BIT bit = new BIT(perm.length);
			boolean canSort = true;
			for(int num:perm){
				canSort &= bit.sum(num)<=num;
				bit.add(num,num);
			}
			System.out.println(canSort?"Yes":"No");
			sum += canSort?1:0;
			perm = next(perm);
		};
		System.out.println(sum);
	}
}