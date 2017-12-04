import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ColorPopupEX extends JFrame{

	JPopupMenu pm = new JPopupMenu();
	//宣告蹦現功能表

	JButton btnColor = new JButton("顏色蹦現功能表");
	//宣告選取顏色的蹦現功能表

	JLabel lbColor = new JLabel(); //顯示顏色設定標籤
	ColorMenuItem cmi; //功能表選項

	ColorPopupEX(){

		lbColor.setHorizontalAlignment(JLabel.CENTER);
		//設定水平對齊方式

		lbColor.setOpaque(true); //設定標籤背景不透明
		lbColor.setText(lbColor.getBackground().toString());
		//設定顯示標籤背景顏色的RGB值

		cmi = new ColorMenuItem(lbColor.getBackground());
		//顏色功能表選項

		pm.add(cmi);
		//將選項加入功能表

		JLabel lbOK = cmi.getOKLabel(); //取得 確定 標籤

		//註冊回應MouseEvent事件的監聽器物件
		lbOK.addMouseListener(new MouseAdapter(){

			//回應按下滑鼠按鍵動作
			public void mouseClicked(MouseEvent e){
				Color selColor = cmi.getSelectedColor();
				//取得調色板選取的顏色

				lbColor.setBackground(selColor);
				//設定標籤的背景顏色

				lbColor.setText(selColor.toString());
				//設定顯示標籤背景顏色的RGB值
			}
		});

		//註冊監聽ActionEvent事件的監聽器物件,
		//回應時, 將顯示蹦現功能表
		btnColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JButton btnSource = (JButton) e.getSource();

				pm.show(btnSource, 0, btnSource.getSize().height);
				//在按鈕下方顯示蹦現功能表
			}
		});

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(5, 5));
		cp.add(lbColor);
		cp.add(btnColor, BorderLayout.SOUTH); //將按鈕加入

		getRootPane().setBorder(new EmptyBorder(5, 5 , 5 , 5));

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorPopupEX(); //宣告視窗框架物件
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