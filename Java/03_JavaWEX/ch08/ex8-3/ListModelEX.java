import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //引用提供File類別的套件

public class ListModelEX extends JFrame {

	JList ltFile = new JList(); //宣告清單方塊

	DefaultListModel dlmFile = new DefaultListModel(),
								 dlmDir = new DefaultListModel(),
								 dlmAll = new DefaultListModel();
	//宣告包含清單方塊選項內容的Model物件

	//以匿名類別的方式定義並宣告監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			//運用動作命令字串判斷欲使用的Model物件
			if(e.getActionCommand().equals("檔案")){
				ltFile.setModel(dlmFile);
				//設定使用包含檔案名稱的Model物件
			}
			else if(e.getActionCommand().equals("資料夾")){
				ltFile.setModel(dlmDir);
				//設定使用包含資料夾名稱的Model物件
			}
			else{
				ltFile.setModel(dlmAll);
				//設定使用包含檔案與資料夾名稱的Model物件
			}
		}
	};

	ListModelEX(){
		File flCur = new File("C:\\"); //欲列出檔案的路徑
		File[] files = flCur.listFiles();
		//取得路徑下所有檔案的File物件

		//運用加強型for迴圈建立各種Model物件
		for(File elm : files){
			dlmAll.addElement(elm.getName());
			//加入檔案或資料夾的名稱

			if(elm.isDirectory()){
				dlmDir.addElement(elm.getName());
				//加入資料夾的名稱
			}
			else{
				dlmFile.addElement(elm.getName());
				//加入檔案的名稱
			}
		}

		ltFile.setModel(dlmFile); //設定清單方塊的顯示內容
		ltFile.setLayoutOrientation(JList.VERTICAL_WRAP);
		//設定選項的設定方式

		ltFile.setVisibleRowCount(5); //設定可視列數為5列

		JRadioButton rdFile = new JRadioButton("顯示檔案", true),
								rdDir = new JRadioButton("顯示資料夾"),
								rdAll = new JRadioButton("全部顯示");
		//宣告控制清單方塊內選項排列方式的選擇按鈕

		rdFile.setActionCommand("檔案");
		rdDir.setActionCommand("資料夾");
		rdAll.setActionCommand("全部");
		//設定控制清單方塊內容的動作命令字串

		rdFile.addActionListener(al);
		rdDir.addActionListener(al);
		rdAll.addActionListener(al);
		//註冊回應ActionEvent事件的監聽器

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(rdFile); //將選擇按鈕加入按鈕群組
		bg.add(rdDir);
		bg.add(rdAll);

		JScrollPane spList = new JScrollPane(ltFile);
		//以清單方塊宣告捲軸面版

		JPanel jpLayout = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		jpLayout.add(rdFile); //將選擇按鈕加入面版
		jpLayout.add(rdDir);
		jpLayout.add(rdAll);
		
		Container cp = getContentPane(); //取得內容面版
		cp.add(spList); //將元件加入內容面版
		cp.add(jpLayout,BorderLayout.NORTH);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListModelEX(); //宣告視窗框架物件
	}
}