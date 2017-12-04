import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //引用提供File類別的套件

public class ListLayoutEX extends JFrame {

	JList ltFile = new JList(); //宣告清單方塊

	//以匿名類別的方式定義並宣告監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			ltFile.setLayoutOrientation(
				Integer.parseInt(e.getActionCommand()));
			//將動作命令字串轉換為控制清單方塊選項排列方式的整數值
		}
	};

	ListLayoutEX(){
		File flCur = new File("C:\\"); //欲列出檔案的路徑
		File[] files = flCur.listFiles();
		//取得路徑下所有檔案的File物件

		ltFile.setListData(files); //設定清單方塊的顯示內容
		ltFile.setVisibleRowCount(5);

		JRadioButton rdVertical = new JRadioButton("垂直排列", true),
				rdHorizontalWrap = new JRadioButton("水平排列並斷行"),
				rdVerticalWrap = new JRadioButton("垂直排列並斷行");
		//宣告控制清單方塊內選項排列方式的選擇按鈕

		rdVertical.setActionCommand(String.valueOf(JList.VERTICAL));
		rdHorizontalWrap.setActionCommand(
							String.valueOf(JList.HORIZONTAL_WRAP));
		rdVerticalWrap.setActionCommand(
			String.valueOf(JList.VERTICAL_WRAP));
		//將控制清單方塊選項排列方式的常數轉換為字串, 
		//並設定為動作命令字串

		rdVertical.addActionListener(al);
		rdHorizontalWrap.addActionListener(al);
		rdVerticalWrap.addActionListener(al);
		//註冊回應ActionEvent事件的監聽器

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(rdVertical); //將選擇按鈕加入按鈕群組
		bg.add(rdHorizontalWrap);
		bg.add(rdVerticalWrap);

		JScrollPane spList = new JScrollPane(ltFile);
		//以清單方塊宣告捲軸面版

		JPanel jpLayout = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		jpLayout.add(rdVertical); //將選擇按鈕加入面版
		jpLayout.add(rdHorizontalWrap);
		jpLayout.add(rdVerticalWrap);
		
		Container cp = getContentPane(); //取得內容面版
		cp.add(spList); //將元件加入內容面版
		cp.add(jpLayout,BorderLayout.NORTH);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ListLayoutEX(); //宣告視窗框架物件
	}
}