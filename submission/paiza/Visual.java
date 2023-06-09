import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;

public class Visual extends Frame
{
	Path pIn = Paths.get("in").toAbsolutePath();
	Path pOut = Paths.get("out").toAbsolutePath();
	Choice cbIn;
	Choice cbOut;
	int N = 0;
	int K = 0;
	int M = 0;
	boolean[][] nail = new boolean[1001][1001];
	int[] connect = new int[1];

	public static void main(String[] args)
	{
		Visual vs = new Visual();
	}
	public Visual()
	{
		super("Visualizer");
		setSize(1000, 1000);
		setBackground(Color.darkGray);
		setLayout(new FlowLayout());
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept(File file,String str){
				if(str.indexOf(".txt")!=-1) return true;
				else return false;
			}
		};
		File[] listIn = new File(pIn.toString()).listFiles(filter);
		File[] listOut = new File(pOut.toString()).listFiles(filter);
		cbIn = new Choice();
		if(listIn==null){
			System.out.println("Error : Can't find any in file.");
			System.exit(0);
		}
		for(int i=0;i<list.length;i++){
			String str = listIn[i].toString();
			str = str.substring(str.length()-20,str.length());
			int temp;
			while((temp=str.indexOf("\\"))!=-1){
				str = str.substring(temp+1,str.length());
			}
			cbIn.add(str);
		}
		cbOut = new Choice();
		if(listOut==null){
			System.out.println("Error : Can't find any out file.");
			System.exit(0);
		}
		for(int i=0;i<listOut.length;i++){
			String str = listOut[i].toString();
			str = str.substring(str.length()-20,str.length());
			int temp;
			while((temp=str.indexOf("\\"))!=-1){
				str = str.substring(temp+1,str.length());
			}
			cbOut.add(str);
		}
		cbIn.setFont(new Font("Serif",Font.PLAIN,20));
		cbOut.setFont(new Font("Serif",Font.PLAIN,20));

		cbIn.addItemListener(new VisualItemListener());
		cbOut.addItemListener(new VisualItemListener());
		add(cbIn);
		add(cbOut);

		addWindowListener(new VisualWindowListener());

		String str = "";
		try{
			str = cbIn.getSelectedItem();
			BufferedReader br = new BufferedReader(new FileReader(p+"\\"+str));
			String[] temp = br.readLine().split(" ");
			N = Integer.parseInt(temp[0]);
			K = Integer.parseInt(temp[1]);
			nail = new boolean[1001][1001];
			for(int i=0;i<N;i++){
				temp = br.readLine().split(" ");
				nail[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = true;
			}
			str = cbOut.getSelectedItem();
			br = new BufferedReader(new FileReader(p+"\\"+str));
			M = Integer.parseInt(br.readLine());
			for(int i=0;i<M;i++){
				temp = br.readLine().split(" ");
				int a = Integer.parseInt(temp[0]);
				int b = Integer.parseInt(temp[1]);
				if(nail[a][b]){
					JOptionPane.showMessageDialog(new Frame(), "The point of nail is wrong("+a+","+b+")","Error for \""+str+"\"",JOptionPane.ERROR_MESSAGE);
					nail = new boolean[1001][1001];
					N = 0;
					K = 0;
					break;
				}
				nail[a][b] = true;
			}
			connect = new int[N+M];
			for(int i=0;i<connect.length;i++){
				connect[i] = Integer.parseInt(br.readLine());
			}
		}catch(Exception err){
			JOptionPane.showMessageDialog(new Frame(), "Can't read this file as maze.","Error for \""+str+"\"",JOptionPane.ERROR_MESSAGE);
			nail = new boolean[1001][1001];
			N = 0;
			K = 0;
		}

		setVisible(true);
	}

	class VisualWindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}

	class VisualItemListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			String str = "";
			try{
				str = cbIn.getSelectedItem();
				BufferedReader br = new BufferedReader(new FileReader(p+"\\"+str));
				String[] temp = br.readLine().split(" ");
				N = Integer.parseInt(temp[0]);
				K = Integer.parseInt(temp[1]);
				nail = new boolean[1001][1001];
				for(int i=0;i<N;i++){
					temp = br.readLine().split(" ");
					nail[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = true;
				}
				str = cbOut.getSelectedItem();
				br = new BufferedReader(new FileReader(p+"\\"+str));
				M = Integer.parseInt(br.readLine());
				for(int i=0;i<M;i++){
					temp = br.readLine().split(" ");
					int a = Integer.parseInt(temp[0]);
					int b = Integer.parseInt(temp[1]);
					if(nail[a][b]){
						JOptionPane.showMessageDialog(new Frame(), "The point of nail is wrong("+a+","+b+")","Error for \""+str+"\"",JOptionPane.ERROR_MESSAGE);
						nail = new boolean[1001][1001];
						N = 0;
						K = 0;
						break;
					}
					nail[a][b] = true;
				}
				connect = new int[N+M];
				for(int i=0;i<connect.length;i++){
					connect[i] = Integer.parseInt(br.readLine());
				}
				repaint();
			}catch(Exception err){
				JOptionPane.showMessageDialog(new Frame(), "Can't read this file as maze.","Error for \""+str+"\"",JOptionPane.ERROR_MESSAGE);
				maze = new int[1][1];
				H = 1;
				W = 1;
			}
		}
	}
	public void paint(Graphics g){
		int w = ;
		int h = 160;
		g.setColor(Color.white);
		g.drawRect(w-1,h-1,W*10+2,H*10+2);
		for(int i=0;i<maze.length;i++){
			for(int j=0;j<maze[i].length;j++){
				if(maze[i][j]==0){
					g.setColor(Color.lightGray);
					g.fillRect(w+j*10,h+i*10,10,10);
					g.setColor(Color.black);
					g.drawRect(w+j*10,h+i*10,10,10);
				}
				if(maze[i][j]==1){
					g.setColor(Color.red);
					g.fillRect(w+j*10,h+i*10,10,10);
				}
				if(maze[i][j]==2){
					g.setColor(Color.yellow);
					g.fillRect(w+j*10,h+i*10,10,10);
				}
				if(maze[i][j]==9){
					g.setColor(Color.black);
					g.fillRect(w+j*10,h+i*10,10,10);
				}
			}
		}
	}
}
