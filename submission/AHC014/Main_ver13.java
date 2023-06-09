import java.io.*;
import java.util.*;
class Main{

	private static int N,K,masterK;
	private static long maxScore;
	private static LinkedList<Integer[]> answer = new LinkedList<Integer[]>();
	private static LinkedList<Integer[]> masterAnswer = new LinkedList<Integer[]>();
	private static boolean[][] isMarked;
	private static boolean[][] originalMark;
	private static LinkedList<Integer> numberStacker = new LinkedList<Integer>();

	final private static boolean takeSmaller = true;
	final private static Random dice = new Random();

	private static int countOfUpdate = 0;
	final private static int limiter = 5;
	final private static int odd = 5;
	final private static double limitOfTime = 4.85;

	public static void main(String[] args){

		//�^�C�}�[���Z�b�g
		Timer timer = new Timer();

		//���͎󂯎��
		inputData();

		//numberStacker�̍쐻
		if(takeSmaller)
			for(int i=0;i<=(N-1)/2;i++)numberStacker.addFirst(i);
		else
			for(int i=0;i<=(N-1)/2;i++)numberStacker.addLast(i);


		//������肢���Ƃ��

		int count = 0;
		while(timer.getTime()<limitOfTime){

			//���łĂ�ꏊ�S��
			ArrayDeque<Integer[]> list = canMarks(getNext());

			//������Ȃ������H
			if(list.size()==0){

				//count���Z
				++count;

				//�S�T����H
				if(count==(N+1)/2){

					//�X�R�A�v�Z�ƍX�V
					updateAnswer();
					breakAnswer();
					reset();
					count = 0;

				}

				continue;
			}

			count = 0;


			//�����l�ݒ�
			int max = Integer.MIN_VALUE;

			Integer[] ans = null;

			int x = 0;
			int y = 0;


			//�ڐ�ŗǂ̓_��T��
			for(Integer[] tmp:list){

				//�_��ł������Ƃɂ��łĂ�ӏ��̐��̕ϓ�
				int flu = nextFluct(tmp,list);

				//�ō��l�X�V�H
				if(max<flu){

					max = flu;

					x = tmp[0];
					y = tmp[1];

					ans = tmp;

				}
			}

			//�_��ǉ�
			isMarked[x][y] = true;
			++K;
			answer.add(ans);

			if(dice.nextInt(odd)==0)
				rewind();

		}
		updateAnswer();

		//������肢���Ƃ����

		//�o��
		outputData();
	}

	//�l�̎󂯎��
	private static void inputData(){

		//throws IOException�ł��ǂ�����try��
		//������main()�ɂ��t����̂߂�ǂ������񂾂��`��
		try{

			//����Scanner
			SimpleScanner in = new SimpleScanner(System.in);

			//N�AM�󂯎��
			N = in.nextInt();
			int M = in.nextInt();

			//������
			isMarked = new boolean[N][N];
			originalMark = new boolean[N][N];

			//�e�_�擾
			while(M-->0){

				int x = in.nextInt();
				int y = in.nextInt();

				//���t����
				isMarked[x][y] = true;
				originalMark[x][y] = true;

			}

			//�O�̂���
			in.close();

		}catch(IOException e){

			//�Ȃ񂩂�������f���Ă�(���ł�RE�ɂ��Ă�)
			System.err.println(e);
			System.exit(1);

		}
	}

	//���S����̋����̓��(���_)
	private static int dict(int x,int y){

		//���S���W
		int c = (N-1)/2;

		//�����̓���Ԃ�
		return (x-c)*(x-c)+(y-c)*(y-c);

	}

	//�󂪕t������ꏊ��
	private static ArrayDeque<Integer[]> canMarks(int i){

		//�߂�l�p
		ArrayDeque<Integer[]> stack = new ArrayDeque<Integer[]>();

		//����
		for(int j=i;j<N-i;++j){

			//���Ɉ�t���Ă�H
			if(isMarked[i][j])
				continue;

			//�t����ꂽ��stack�ɐς�
			Integer[] tmp = tryMark(i,j);
			if(tmp!=null)
				stack.add(tmp);

		}

		//�E��
		for(int j=i;j<N-i;++j){

			//���Ɉ�t���Ă�H
			if(isMarked[N-i-1][j])
				continue;

			//�t����ꂽ��stack�ɐς�
			Integer[] tmp = tryMark(N-i-1,j);
			if(tmp!=null)
				stack.add(tmp);

		}

		//����
		for(int j=i+1;j<N-i-1;++j){

			//���Ɉ�t���Ă�H
			if(isMarked[j][i])
				continue;

			//�t����ꂽ��stack�ɐς�
			Integer[] tmp = tryMark(j,i);
			if(tmp!=null)
				stack.add(tmp);

		}

		//�㑤
		for(int j=i+1;j<N-i-1;++j){

			//���Ɉ�t���Ă�H
			if(isMarked[j][N-i-1])
				continue;

			//�t����ꂽ��stack�ɐς�
			Integer[] tmp = tryMark(j,N-i-1);
			if(tmp!=null)
				stack.add(tmp);

		}

		//���ʂ�Ԃ�
		return stack;

	}

	//�����𖞂��������`��T��(���t������Ȃ炻���Ԃ�)
	private static Integer[] tryMark(int x,int y){

		//sr���܂������Ȓ����`�Aor���΂߂̒����`
		Integer[] sr = sRect(x,y);
		Integer[] or = oRect(x,y);

		//�܂������Ȃ̂�������Ȃ������H
		if(sr==null)
			return or;

		//�΂߂Ȃ̂�������Ȃ������H
		if(or==null)
			return sr;

		//�ǂ��������������Ȃ�ʐς�����������
		int s = areaOfsRect(sr);
		int o = areaOfoRect(or);

		//�����Ȃ�΂߂�(�Ȃ�ƂȂ�)
		return s<o^!takeSmaller ? sr : or;

	}

	//�܂������Ȓ����`���m�Ŗʐς��r
	private static Integer[] compareS(Integer[] rect1,Integer[] rect2){

		//��ڂ�null�H
		if(rect1==null)
			return rect2;

		//��ڂ�null�H
		if(rect2==null)
			return rect1;

		//�ʐόv�Z
		int a = areaOfsRect(rect1);
		int b = areaOfsRect(rect2);

		//������(�傫��)����Ԃ�
		return a<b^!takeSmaller ? rect1 : rect2;

	}

	//�΂߂̒����`���m�Ŗʐς��r
	private static Integer[] compareO(Integer[] rect1,Integer[] rect2){

		//��ڂ�null�H
		if(rect1==null)
			return rect2;

		//��ڂ�null�H
		if(rect2==null)
			return rect1;

		//�ʐόv�Z
		int a = areaOfoRect(rect1);
		int b = areaOfoRect(rect2);

		//������(�傫��)����Ԃ�
		return a<b^!takeSmaller ? rect1 : rect2;

	}

	//�܂������Ȓ����`�̖ʐς�Ԃ�
	private static int areaOfsRect(Integer[] rect){

		return Math.abs((rect[0]-rect[4])*(rect[1]-rect[5]));

	}

	//�΂߂̒����`�̖ʐς�Ԃ�
	private static int areaOfoRect(Integer[] rect){

		//��ӂƍ���(�΂߂̂܂܂��ƌv�Z���ɂ����̂ŕ��s�l�ӌ`�Ɛ����`�~2�ɕ�����)
		int bottom,height;

		//��ӂ̒��������߂�(��ԍ��̓_����܂������L�΂��ďo����������ӂƂ��镽�s�l�ӌ`)
		//�����ł͌�X�y�Ȃ̂Œ�ӂ̒���/2�ɂȂ��Ă���
		if(Math.abs(rect[0]-rect[6])<Math.abs(rect[0]-rect[2]))
			bottom = Math.abs(rect[0]-rect[6]);
		else
			bottom = Math.abs(rect[0]-rect[2]);

		//��L�̕��s�l�ӌ`�̍��������߂�(��ԍ����̓_�ƈ�ԉE���̓_��y���W�̍�)
		height = Math.abs(rect[1]-rect[5]);

		//�؂�o�������s�l�ӌ`+�c��̕����ō��鐳���`�~2
		//((2�~���)�~����)+(��Ӂ~��Ӂ~2)
		return 2*bottom*height+bottom*bottom*2;

	}

	//�܂������Ȓ����`�����
	private static Integer[] sRect(int x1,int y1){

		//�߂�l�p
		Integer[] ans = null;

		//x1�Ax1���E��
		int x2 = getLeftP(x1,y1); //���̓_��T��
		int y3 = getDownP(x1,y1); //���̓_��T��

		//�s���l�łȂ��A���Ίp�������ɓ_�͂��邩�H
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//�����𖞂����Ă���H
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp);

			}
		}

		//x1�Ay1������
		x2 = getRightP(x1,y1); //�E�̓_��T��

		//�s���l�łȂ��A���Ίp�������ɓ_�͂��邩�H
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//�����𖞂����Ă���H
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp);

			}
		}

		//x1�Ay1������
		y3 = getUpP(x1,y1); //��̓_��T��

		//�s���l�łȂ��A���Ίp�������ɓ_�͂��邩�H
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//�����𖞂����Ă���H
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp);

			}
		}

		//x1�Ay1���E��
		x2 = getLeftP(x1,y1); //�����̓_��T��

		//�s���l�łȂ��A���Ίp�������ɓ_�͂��邩�H
		if(isProper(x2,y3)&&isMarked[x2][y3]){

			//�����𖞂����Ă���H
			if(sCheck(x1,y1,x2,y1,x2,y3,x1,y3)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x1,y1,x2,y1,x2,y3,x1,y3};
				ans = compareS(ans,tmp);

			}
		}


		//���ʂ�Ԃ�
		return ans;

	}

	//���̓_��T��
	private static int getLeftP(int x,int y){

		//�s���l���_��������܂ō���
		--x;
		while(x>=0&&!isMarked[x][y])--x;

		//x���W��ԋp
		return x;

	}

	//�E�̓_��T��
	private static int getRightP(int x,int y){

		//�s���l���_��������܂ŉE��
		++x;
		while(x<N&&!isMarked[x][y])++x;

		//x���W��ԋp
		return x;

	}

	//���̓_��T��
	private static int getDownP(int x,int y){

		//�s���l���_��������܂ŉ���
		--y;
		while(y>=0&&!isMarked[x][y])--y;

		//y���W��ԋp
		return y;

	}

	//��̓_��T��
	private static int getUpP(int x,int y){

		//�s���l���_��������܂ŏ��
		++y;
		while(y<N&&!isMarked[x][y])++y;

		//y���W��ԋp
		return y;

	}

	//�΂߂̒����`�����
	private static Integer[] oRect(int x1,int y1){

		//�߂�l�p
		Integer[] ans = null;

		//x1�Ay1����
		//x2�Ay2����
		//x3�Ay3���E
		//x4�Ay4����

		int sub1 = getUpObliqueR(x1,y1); //��̓_��T��
		int sub2 = getDownObliqueR(x1,y1); //���̓_��T��

		//��
		int x2 = x1+sub1;
		int y2 = y1+sub1;

		//��
		int x4 = x1+sub2;
		int y4 = y1-sub2;

		//�E
		int x3 = x2+sub2;
		int y3 = y2-sub2;

		//�K���l���E�̓_�͑��݂���H
		if(isProper(x2,y2) && isProper(x3,y3) && isProper(x4,y4) && isMarked[x3][y3]){

			//�����𖞂����Ă���H
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x1,y1,x2,y2,x3,y3,x4,y4};
				ans = compareO(ans,tmp);

			}
		}

		//��
		x2 = x1;
		y2 = y1;

		sub1 = getDownObliqueL(x2,y2); //�̓_��T��

		//��
		x1 = x2-sub1;
		y1 = y2-sub1;

		sub2 = getDownObliqueR(x1,y1); //���̓_��T��

		//��
		x4 = x1+sub2;
		y4 = y1-sub2;

		//�E
		x3 = x2+sub2;
		y3 = y2-sub2;

		//�K���l���E�̓_�͑��݂���H
		if(isProper(x1,y1) && isProper(x3,y3) && isProper(x4,y4) && isMarked[x3][y3]){

			//�����𖞂����Ă���H
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x2,y2,x3,y3,x4,y4,x1,y1};
				ans = compareO(ans,tmp);

			}
		}

		//��
		x3 = x2;
		y3 = y2;

		sub1 = getUpObliqueL(x3,y3); //��̓_��T��

		//��
		x2 = x3-sub1;
		y2 = y3+sub1;

		sub2 = getDownObliqueL(x2,y2); //���̓_��T��

		//��
		x1 = x2-sub2;
		y1 = y2-sub2;

		//��
		x4 = x1+sub1;
		y4 = y1-sub1;

		//�K���l�����̓_�͑��݂���H
		if(isProper(x1,y1) && isProper(x2,y2) && isProper(x4,y4) && isMarked[x4][y4]){

			//�����𖞂����Ă���H
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x3,y3,x4,y4,x1,y1,x2,y2};
				ans = compareO(ans,tmp);

			}
		}

		//��
		x4 = x3;
		y4 = y3;

		sub1 = getUpObliqueL(x4,y4); // ���̓_��T��

		//��
		x1 = x4-sub1;
		y1 = y4+sub1;

		sub2 = getUpObliqueR(x1,y1); //��̓_��T��

		//��
		x2 = x1+sub2;
		y2 = y1+sub2;

		//�E
		x3 = x2+sub1;
		y3 = y2-sub1;

		//�K���l���E�̓_�͑��݂���H
		if(isProper(x1,y1) && isProper(x2,y2) && isProper(x3,y3) && isMarked[x3][y3]){

			//�����𖞂����Ă���H
			if(oCheck(x1,y1,x2,y2,x3,y3,x4,y4)){

				//�ʐϔ�r
				Integer[] tmp = new Integer[]{x4,y4,x1,y1,x2,y2,x3,y3};
				ans = compareO(ans,tmp);

			}
		}

		//������Ԃ�
		return ans;

	}

	//�E������ɓ_��T��
	private static int getUpObliqueR(int x,int y){

		//���͒l���s���H
		if(x<0||y<0)
			return N+1;

		++x;
		++y;

		//����
		int ans = 1;

		//�E������ɒT���Ă���
		while(x<N&&y<N&&!isMarked[x][y]){

			++x;
			++y;

			++ans;

		}

		//������Ԃ�
		return ans;

	}

	//�E�������ɓ_��T��
	private static int getDownObliqueR(int x,int y){

		//���͒l���s���H
		if(x<0||N<=y)
			return N+1;

		++x;
		--y;

		//����
		int ans = 1;

		//�E�������ɒT���Ă���
		while(x<N&&0<=y&&!isMarked[x][y]){

			++x;
			--y;

			++ans;

		}

		//������Ԃ�
		return ans;

	}

	//��������̓_��T��
	private static int getUpObliqueL(int x,int y){

		//���͒l���s���H
		if(N<=x||y<0)
			return N+1;

		--x;
		++y;

		//����
		int ans = 1;

		//��������ɒT���Ă���
		while(0<=x&&y<N&&!isMarked[x][y]){

			--x;
			++y;

			++ans;

		}

		//������Ԃ�
		return ans;

	}

	//���������̓_��T��
	private static int getDownObliqueL(int x,int y){

		//���͒l���s���H
		if(N<=x||N<=y)
			return N+1;

		--x;
		--y;

		//����
		int ans = 1;

		//���������ɒT���Ă���
		while(0<=x&&0<=y&&!isMarked[x][y]){

			--x;
			--y;

			++ans;

		}

		//������Ԃ�
		return ans;

	}

	//�_�̓K���l����
	private static boolean isProper(int x,int y){

		//�����ƕ��ᎆ�̒��ɂ��邩�H
		return (0 <= x  &&  x < N)   &&   (0 <= y  &&  y < N);

	}

	//�����𖞂����Ă��邩���肷��(�܂������Ȓ����`�p)
	private static boolean sCheck(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){

		//��ӂƒ�ӏ�ɓ_�����݂�����false
		for(int i=Math.min(x1,x3)+1;i<Math.max(x1,x3);++i){

			if(isMarked[i][y1]||isMarked[i][y3])
				return false;

		}

		//���E�̕ӏ�ɓ_�����݂�����false
		for(int i=Math.min(y1,y3)+1;i<Math.max(y1,y3);++i){

			if(isMarked[x1][i]||isMarked[x3][i])
				return false;

		}

		//�ӂ̂��Ԃ肪���݂��Ȃ����H
		return isOrigin(x1,y1,x2,y2,x3,y3,x4,y4);

	}

	//�����𖞂����Ă��邩���肷��(�΂߂̒����`�p)
	private static boolean oCheck(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){

		//���̌v�Z
		int sub = x2-x1;

		//�ӏ�ɓ_�����݂����false(��->��A��->�E)
		while(0<--sub){

			if(isMarked[x1+sub][y1+sub])
				return false;

			if(isMarked[x4+sub][y4+sub])
				return false;

		}

		//���̌v�Z
		sub = x4-x1;

		//�ӏ�ɓ_�����݂����false(��->���A��->�E)
		while(0<--sub){

			if(isMarked[x1+sub][y1-sub])
				return false;

			if(isMarked[x2+sub][y2-sub])
				return false;

		}

		//�ӂ̂��Ԃ肪���݂��Ȃ����H
		return isOrigin(x1,y1,x2,y2,x3,y3,x4,y4);

	}

	//�d�������ӂ����݂��Ȃ������肷��
	private static boolean isOrigin(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){


		//���ɍ��ꂽ�ӑS�ĂŔ���
		for(Integer[] rect:answer){

			//�����ꂩ�̕ӂ�����Ă�����false
			for(int i=0;i<7;i+=2){

				if(isOverlapped(x1,y1,x2,y2,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;

				if(isOverlapped(x2,y2,x3,y3,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;

				if(isOverlapped(x3,y3,x4,y4,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;

				if(isOverlapped(x1,y1,x4,y4,rect[i%8],rect[(i+1)%8],rect[(i+2)%8],rect[(i+3)%8]))
					return false;

			}
		}

		//�S������ĂȂ�������true
		return true;

	}

	//�ӓ��m������Ă��邩����
	private static boolean isOverlapped(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4){

		//1:�c�ɂ܂�����
		//2:���ɂ܂�����
		//3:�΂�

		//�^�C�v�ʂɕ�����
		int type1 = x1==x2 ? 1 : y1==y2 ? 2 : 3;
		int type2 = x3==x4 ? 1 : y3==y4 ? 2 : 3;

		boolean result = false;

		//�΂ߓ��m�Ȃ炿����Ƃ߂�ǂ����������
		if(type1==3&&type2==3){

			//�����X���H
			result = (y2-y1)/(x2-x1)==(y4-y3)/(x4-x3);

			//�ӂ͓���������ɑ��݂��邩
			int s1 = (x1-x2)*(y3-y1)-(y1-y2)*(x3-x1);
			int t1 = (x1-x2)*(y4-y1)-(y1-y2)*(x4-x1);
			int s2 = (x3-x4)*(y1-y3)-(y3-y4)*(x1-x3);
			int t2 = (x3-x4)*(y2-y3)-(y3-y4)*(x2-x3);

			result &= s1*t1==0&&s2*t2==0;

			//�������݂���Ȃ�y���W�Ŕ���Ă镔�������邩�Ŕ���
			if(result)
				result &= !(Math.max(y1,y2)<=Math.min(y3,y4)||Math.max(y3,y4)<=Math.min(y1,y2));

		}

		//�^�C�v�ʂɔ���(���������Ⴄ�^�C�v�Ȃ���Ȃ�)
		return type1==type2&&

			 //�^�C�v1���m�Ȃ�x���W�̈�v��y���W�͈̔͂�����Ă��邩�Ŕ���
			(type1==1 ? x1==x3&&!(Math.max(y1,y2)<=Math.min(y3,y4)||Math.max(y3,y4)<=Math.min(y1,y2)) :

			 //�^�C�v2���m�Ȃ�y���W�̈�v��x���W�͈̔͂�����Ă��邩�Ŕ���
			 type1==2 ? y1==y3&&!(Math.max(x1,x2)<=Math.min(x3,x4)||Math.max(x3,x4)<=Math.min(x1,x2)) :

			 //�^�C�v3�Ȃ��L�̌��ʂ��g��
			 result);

	}

	//���t������̌��̑���
	private static int nextFluct(Integer[] rect,ArrayDeque<Integer[]> list){

		int x = rect[0];
		int y = rect[1];

		//�����ɒǉ����Ă݂�
		answer.add(rect);
		isMarked[x][y] = true;

		//�ǉ���̌����L�^
		int ret = 0;
		for(Integer[] temp:list){
			if(isOrigin(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7]))
				++ret;
		}

		//�ǉ�������
		answer.remove(answer.size()-1);
		isMarked[x][y] = false;

		//���ʂ�Ԃ�
		return ret;

	}

	//�����̏o��
	private static void outputData(){

		//����Printer
		SimplePrinter out = new SimplePrinter(System.out);

		//K���o��
		out.println(masterK);

		//�󔒋�؂�o��
		for(Integer[] ans:masterAnswer){

			out.println(ans," ");

		}

		//�O�̂���
		out.close();

	}

	//numberStacker����l�����o��
	private static int getNext(){

		//�擪���o��
		Integer num = numberStacker.pollFirst();

		//���ɖ߂��Ă���
		numberStacker.addLast(num);

		//���o�����l��Ԃ�
		return (int)num;

	}

	//numberStacker�̒l����O�ɖ߂�
	private static void rewind(){

		//�������o��
		Integer num = numberStacker.pollLast();

		//�擪�ɖ߂��Ă���
		numberStacker.addFirst(num);

	}

	//�����_���_�C�X�ɂ��V���b�t��
	private static void reset(){

		//����numberStacker�p
		LinkedList<Integer> nextStacker = new LinkedList<Integer>();

		//�O������Add���Ă���(���m��)
		for(Integer i:numberStacker){
			if(dice.nextInt(2)==0)
				nextStacker.addLast(i);
			else
				nextStacker.addFirst(i);
		}

		//numberStacker�X�V
		numberStacker = nextStacker;

	}

	//�]���֐�(���݂̓��_���U�b�ƌv�Z����)
	private static long score(){

		//�ԋp�p
		long answer = 0;

		//�S�}�X���Ă���
		for(int i=0;i<N;++i){
			for(int j=0;j<N;++j){

				//�󂪕t���Ă�����X�R�A�v�Z
				if(isMarked[i][j])
					answer += dict(i,j)+1;

			}
		}

		//�ԋp
		return answer;

	}

	//�����X�V
	private static void updateAnswer(){

		//���݂̓��_���v�Z
		long score = score();

		//�ō��l�X�V�H
		if(score>maxScore){

			//�e�ϐ��X�V
			maxScore = score;
			masterAnswer = answer;
			masterK = K;
			countOfUpdate = 0;

		}
		else
			++countOfUpdate;

	}

	//�������������j�󂷂�
	private static void breakAnswer(){

		//������߁H
		if(countOfUpdate==limiter){

			//������
			answer = new LinkedList<Integer[]>();
			K = 0;
			for(int i=0;i<N;++i)
				System.arraycopy(originalMark[i],0,isMarked[i],0,N);

			countOfUpdate = 0;

		}

		else{

			//�j�󂷂钷���`�̐�
			int breaker = dice.nextInt(K)+1;

			//�X�V��p
			LinkedList<Integer[]> next = new LinkedList<Integer[]>();
			next.addAll(answer);

			//��납��breaker���j�󂷂�
			for(int i=0;i<breaker;++i){

				Integer[] tmp = next.pollLast();
				int x = tmp[0];
				int y = tmp[1];

				isMarked[x][y] = false;

			}

			//�X�V
			answer = next;
			K -= breaker;

		}

	}
}


//�^�C�}�[
class Timer{

	private long startTime;

	public Timer(){

		//�J�n�����̃Z�b�g
		startTime = System.nanoTime();

	}

	//�o�ߎ����̎擾
	public double getTime(){

		long nowTime = System.nanoTime();

		//�b�ɕϊ�(double)
		return (nowTime - startTime) / 1e9;

	}

}





/*////////////////////////////////////////////////
	* My Scanner *

	@auther viral
 *////////////////////////////////////////////////
class SimpleScanner{
	final private int buff_size = 1<<12;
	private InputStream is;
	private byte[] buff;
	private int point,length;
	public SimpleScanner(InputStream is){
		this.is = is;
		buff = new byte[buff_size];
		point = length = 0;
	}
	private void reload()throws IOException{
		do{
			length = is.read(buff,point = 0,buff_size);
		}while(length==-1);
	}
	private byte read()throws IOException{
		if(point==length)reload();
		return buff[point++];
	}
	public int nextInt()throws IOException{
		int ans = 0;
		byte c = read();
		while(c<=' ')c = read();
		boolean negate = c == '-';
		if(negate)c = read();
		while('0'<=c&&c<='9'){
			ans = ans*10+c-'0';
			c = read();
		}
		return negate ? -ans : ans;
	}
	public void close()throws IOException{
		is.close();
	}
}
/*////////////////////////////////////////////////
	* My Printer *

	@auther viral
 *////////////////////////////////////////////////
class SimplePrinter extends PrintWriter{
	public SimplePrinter(OutputStream os){
		super(os);
	}
	public void println(Integer[] nums,String str){
		print(nums[0]);
		for(int i=1;i<nums.length;i++){
			print(str);
			print(nums[i]);
		}
		println();
	}
}
