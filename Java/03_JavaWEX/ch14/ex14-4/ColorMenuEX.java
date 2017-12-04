import javax.swing.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ColorMenuEX extends JFrame{

	JLabel lbColor = new JLabel();
	//宣告顯示選取顏色的標籤

	ColorMenuItem cmi; //宣告自訂的調色板功能表選項
	ColorMenu cmUser; //宣告自訂的調色板功能表

	ColorMenuEX(){

		lbColor.setOpaque(true); //設定標籤背景不透明
		lbColor.setHorizontalAlignment(JLabel.CENTER);
		//設定標籤內文字置中對齊

		lbColor.setText(lbColor.getBackground().toString());
		//設定顯示背景顏色的RGB值

		JMenu mnFile = new JMenu("檔案(F)");
		//宣告功能表

		mnFile.setMnemonic(KeyEvent.VK_F);
		//設定功能表的助憶符號

		JMenuItem miExit = 
			new JMenuItem("結束(E)", KeyEvent.VK_E);
		//宣告功能表選項

		//定義並宣告監聽ActionEvent事件的監聽器
		miExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
				//結束程式的執行
			}
		});

		mnFile.add(miExit); //將選項加入功能表

		cmUser = new ColorMenu(
			"選取顏色功能表", lbColor.getBackground());
		//宣告自訂的調色板功能表
		
		//註冊回應 確定 標籤MouseEvent事件的監聽器物件
		cmUser.getOKLabel().addMouseListener(new MouseAdapter(){

			//回應按下滑鼠按鍵動作
			public void mouseClicked(MouseEvent e){
				Color selColor = cmUser.getSelectedColor();
				//取得調色板選取的顏色

				lbColor.setText(selColor.toString());
				//設定標籤顯示選取顏色的值

				lbColor.setBackground(selColor);
				//設定標籤的背景顏色
			}
		});

		cmi = new ColorMenuItem(lbColor.getBackground());
		//宣告提供調色板的功能表選項
		
		//註冊回應 確定 標籤MouseEvent事件的監聽器物件
		cmi.getOKLabel().addMouseListener(new MouseAdapter(){

			//回應按下滑鼠按鍵動作
			public void mouseClicked(MouseEvent e){
				Color selColor = cmi.getSelectedColor();
				//取得調色板選取的顏色

				lbColor.setText(selColor.toString());
				//設定標籤顯示選取顏色的值

				lbColor.setBackground(selColor);
				//設定標籤的背景顏色
			}
		});

		JMenu mnColorMenu = new JMenu("顏色選取選項");
		mnColorMenu.add(cmi); //加入顏色選項

		JMenu mnColorItem = new JMenu("顏色選取");
		//宣告功能表

		mnColorItem.add(mnColorMenu); //加入子功能表
		mnColorItem.add(cmUser); //加入子功能表

		JMenuBar jmb = new JMenuBar(); //宣告功能表列物件
		setJMenuBar(jmb); //設定視窗框架使用的功能表
		jmb.add(mnFile); //將功能表加入功能表列
		jmb.add(mnColorItem);

		Container cp = getContentPane(); //取得內容面版

		cp.add(lbColor); //將文字區加入內容面版

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorMenuEX(); //宣告視窗框架物件
	}
}

//繼承JPanel類別實作MenuElement介面, 
//定義提供調色板選取顏色的功能表
class ColorMenuItem extends JPanel 
						implements MenuElement {

	CompoundBorder
		bdBtnRasied = new CompoundBorder(
			new BevelBorder(BevelBorder.RAISED),
			new EmptyBorder(2, 4, 2, 4)),
			//呈現凸起效果的框線
		bdBtnLowered = new CompoundBorder(
			new BevelBorder(BevelBorder.LOWERED),
			new EmptyBorder(2, 4, 2, 4));
			//呈現凹陷效果的框線

	AbstractColorChooserPanel accpPanel;
	//宣告顏色選取面版

	JLabel lbBtnOK = new JLabel("確定");
	//宣告提供按鈕功能的標籤

	public ColorMenuItem(Color defColor){ //建構子

		lbBtnOK.setOpaque(true);
		// 確定 標籤背景不透明

		lbBtnOK.setBorder(bdBtnRasied); //設定標籤使用凸起框線

		JColorChooser cc = new JColorChooser();
		//宣告顏色選取器

		//取得顏色選取器的調色板面版
		for(AbstractColorChooserPanel elm:
							cc.getChooserPanels()){

			//比對顏色選取面版名稱是否為"調色板(S)"
			if((elm.getDisplayName()).equals("調色板(S)")){
				accpPanel = elm;
				//將符合條件的顏色選取面版設定給accpPanel屬性

				add(elm); //將調色板加入內容面版
			}
		}

		accpPanel.getColorSelectionModel().setSelectedColor(defColor);
		//設定預設選取顏色

		//註冊監聽 確定 標籤MouseEvent事件的監聽器物件
		lbBtnOK.addMouseListener(new MouseAdapter(){

			private Color oldBackground;
			//儲存 確定 標籤原始的背景顏色

			//回應按下滑鼠按鍵的動作
			public void mouseClicked(MouseEvent e){

				MenuSelectionManager manager = 
							MenuSelectionManager.defaultManager();
				//取得管理功能表選取的管理員物件

				manager.clearSelectedPath(); //清除選取路徑

				lbBtnOK.setBackground(oldBackground);
				//恢復標籤的背景顏色

				lbBtnOK.setBorder(bdBtnRasied);
				//設定標籤使用凸起效果框線
			}

			//回應滑鼠進入標籤範圍的動作
			public void mouseEntered(MouseEvent e){	

				oldBackground = lbBtnOK.getBackground();
				//取得標籤的背景顏色

				lbBtnOK.setBackground(new Color(163, 184, 204));
				//設定滑鼠游標進入標籤範圍的背景顏色
			}

			//回應滑鼠離開標籤範圍的動作
			public void mouseExited(MouseEvent e){

				lbBtnOK.setBackground(oldBackground);
				//恢復標籤背景顏色的設定

				lbBtnOK.setBorder(bdBtnRasied);
				//設定標籤使用凸起效果框線
			}

			//回應按下滑鼠按鍵的動作
			public void mousePressed(MouseEvent e){	
				lbBtnOK.setBorder(bdBtnLowered);
				//設定標籤使用凹陷效果的框線
			}
		});

		add(lbBtnOK); //將 確定 標籤加入內容面版
	}

	//取得代替按鈕的 確定 標籤
	public JLabel getOKLabel(){ return lbBtnOK; }

	//設定調色板選取的顏色
	public void setSelectedColor(Color c){
		accpPanel.getColorSelectionModel().setSelectedColor(c);
	}

	//取得調色板選取的顏色
	public Color getSelectedColor(){
		return accpPanel.getColorSelectionModel().getSelectedColor();
	}

	//以下為實作MenuElement介面定義的方法
	public void processMouseEvent(MouseEvent event,
                       MenuElement[] path,
                       MenuSelectionManager manager){}

	public void processKeyEvent(KeyEvent event,
                     MenuElement[] path,
                     MenuSelectionManager manager){}

	public void menuSelectionChanged(boolean isIncluded){}

	public MenuElement[] getSubElements(){
		return new MenuElement[0];
	}

	public Component getComponent(){ return this; }
}

//以繼承JMenu類別的方式定義提供調色板選取顏色的功能表
class ColorMenu extends JMenu {

	CompoundBorder
		bdBtnRasied = new CompoundBorder(
			new BevelBorder(BevelBorder.RAISED),
			new EmptyBorder(2, 4, 2, 4)),
			//呈現凸起效果的框線
		bdBtnLowered = new CompoundBorder(
			new BevelBorder(BevelBorder.LOWERED),
			new EmptyBorder(2, 4, 2, 4));
			//呈現凹陷效果的框線

	AbstractColorChooserPanel accpPanel;
	//宣告顏色選取面版

	JLabel lbBtnOK = new JLabel("確定");
	//宣告提供按鈕功能的標籤

	JPanel jpColor = new JPanel();
	//宣告容納調色板與 確定 標籤的容器

	public ColorMenu(String name, Color defColor){ //建構子

		super(name); //呼叫基礎類別的建構子

		lbBtnOK.setOpaque(true); // 確定 標籤背景不透明
		lbBtnOK.setBorder(bdBtnRasied); //設定標籤使用凸起框線

		JColorChooser cc = new JColorChooser();
		//宣告顏色選擇器

		//取得顏色選取器的調色板面版
		for(AbstractColorChooserPanel elm:
							cc.getChooserPanels()){

			//比對顏色選取面版名稱是否為"調色板(S)"
			if((elm.getDisplayName()).equals("調色板(S)")){
				accpPanel = elm;
				//將符合條件的顏色選取面版設定給accpPanel屬性

				jpColor.add(elm); //將調色板加入面版容器
			}
		}

		accpPanel.getColorSelectionModel().setSelectedColor(defColor);
		//設定預設選取顏色

		//註冊監聽 確定 標籤MouseEvent事件的監聽器物件
		lbBtnOK.addMouseListener(new MouseAdapter(){

			private Color oldBackground;
			//儲存 確定 標籤原始的背景顏色

			//回應按下滑鼠按鍵的動作
			public void mouseClicked(MouseEvent e){

				MenuSelectionManager manager = 
							MenuSelectionManager.defaultManager();
				//取得管理功能表選取的管理員物件

				manager.clearSelectedPath(); //清除選取路徑

				lbBtnOK.setBackground(oldBackground);
				//恢復標籤的背景顏色

				lbBtnOK.setBorder(bdBtnRasied);
				//設定標籤使用凸起效果框線
			}

			//回應滑鼠進入標籤範圍的動作
			public void mouseEntered(MouseEvent e){	

				oldBackground = lbBtnOK.getBackground();
				//取得標籤的背景顏色

				lbBtnOK.setBackground(new Color(163, 184, 204));
				//設定滑鼠游標進入標籤範圍的背景顏色
			}

			//回應滑鼠離開標籤範圍的動作
			public void mouseExited(MouseEvent e){	
				lbBtnOK.setBackground(oldBackground);
				//恢復標籤背景顏色的設定
				
				lbBtnOK.setBorder(bdBtnRasied);
				//設定標籤使用凸起效果框線
			}

			//回應按下滑鼠按鍵的動作
			public void mousePressed(MouseEvent e){	
				lbBtnOK.setBorder(bdBtnLowered);
				//設定標籤使用凹陷效果的框線
			}
		});

		jpColor.add(lbBtnOK); //將 確定 標籤加入容器
		add(jpColor); //將jpColor容器加入內容面版
	}

	//取得代替按鈕的 確定 標籤
	public JLabel getOKLabel(){ return lbBtnOK; }

	//設定調色板選取的顏色
	public void setSelectedColor(Color c){
		accpPanel.getColorSelectionModel().setSelectedColor(c);
	}

	//取得調色板選取的顏色
	public Color getSelectedColor(){
		return accpPanel.getColorSelectionModel().getSelectedColor();
	}
}