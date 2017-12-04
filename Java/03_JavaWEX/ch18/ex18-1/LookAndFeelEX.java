import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //引用提供File類別的套件

import java.util.*;

public class LookAndFeelEX extends JFrame {

	String lookAndFeel = null;
	//欲使用外視感覺物件的名稱

	Hashtable htLF = new Hashtable();
	//宣告儲存外視感覺名稱與LookAndFeelInfo物件的雜湊表

	//以匿名類別的方式定義並宣告監聽器物件
	ActionListener al = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			String strAction = (String) e.getActionCommand();
			//取得動作命令字串
			
			try {
				String name = ((UIManager.LookAndFeelInfo) 
					htLF.get(strAction)).getClassName();
				//取得描述外視感覺的類別名稱

				UIManager.setLookAndFeel(name);
				//設定視窗框架使用的外視感覺

				SwingUtilities.updateComponentTreeUI(
					LookAndFeelEX.this);
				//更新視窗
			}
			catch (Exception ex) { }
		}
	};

	LookAndFeelEX(){

		JMenu mnLF = new JMenu("Look and Feel");	//宣告功能表

		JPanel jpLF = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		//宣告容納元件內容的JPanel

		ButtonGroup bgLF = new ButtonGroup(); //宣告按鈕群組

		UIManager.LookAndFeelInfo[] 
			lafs = UIManager.getInstalledLookAndFeels();
		//取得目前系統安裝的外視感覺

		for(UIManager.LookAndFeelInfo elm : lafs){
			
			String strName = elm.getName();
			//取得外視感覺物件的名稱

			htLF.put(strName, elm);
			//設定外視感覺物件的名稱與LookAndFeelInfo物件

			JMenuItem mi = new JMenuItem(strName);
			//功能表選項

			mi.addActionListener(al);
			//註冊監聽ActionEvent事件的監聽器物件

			mnLF.add(mi); //將選項加入功能表

			JToggleButton tb = new JToggleButton(strName);
			//宣告切換按鈕物件

			tb.addActionListener(al);
			//註冊監聽ActionEvent事件的監聽器物件

			jpLF.add(tb); //將切換按鈕加入面版
			bgLF.add(tb); //將切換按鈕加入按鈕群組
		}

		JRadioButton
			rbOne = new JRadioButton("選項 1", true),
			rbTwo = new JRadioButton("選項 2"),
			rbThree = new JRadioButton("選項 3");
		//宣告選擇鈕

		ButtonGroup bgRadio  = new ButtonGroup();
		bgRadio.add(rbOne);
		bgRadio.add(rbTwo);
		bgRadio.add(rbThree);
		//宣告按鈕群組, 並加入選擇鈕

		Box bxRadio = new Box(BoxLayout.Y_AXIS);
		bxRadio.add(rbOne);
		bxRadio.add(rbTwo);
		bxRadio.add(rbThree);
		//宣告容納選擇鈕的Box容器, 並加入選擇鈕

		JCheckBox
			cbOne = new JCheckBox("選項 1"),
			cbTwo = new JCheckBox("選項 2", true),
			cbThree = new JCheckBox("選項 3");
		//宣告核取方塊

		Box bxCheck = new Box(BoxLayout.Y_AXIS);
		bxCheck.add(cbOne);
		bxCheck.add(cbTwo);
		bxCheck.add(cbThree);
		//宣告容納核取方塊的Box容器, 並加入核取方塊

		String[] strItem = {"選項 1", "選項 2", "選項 3"};
		//宣告清單方塊的選項字串

		JList lst = new JList(strItem);
		//以選項字串陣列宣告JList物件

		JMenuBar mb = new JMenuBar();
		//宣告功能表列

		mb.add(mnLF); //將功能表加入功能表列

		setJMenuBar(mb); //設定視窗框架使用的功能表列

		//將容納元件的容器加入面版
		JPanel jpPanel = new JPanel(new GridLayout(1, 3,  5, 5));
		jpPanel.add(new JScrollPane(lst));
		jpPanel.add(bxRadio);
		jpPanel.add(bxCheck);

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpLF, BorderLayout.NORTH); //將容納元件的面版加入
		cp.add(jpPanel);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(530, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new LookAndFeelEX(); //宣告視窗框架物件
	}
}