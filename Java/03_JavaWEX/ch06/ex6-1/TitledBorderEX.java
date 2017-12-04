import javax.swing.*;  //引用套件
import java.awt.*;

import javax.swing.border.*; //定義框線類別的套件

public class TitledBorderEX extends JFrame{

	TitledBorderEX(){

		JLabel lbTitledTop = 
						new JLabel("標題(Titled) : 預設", 
											JLabel.CENTER),
					lbTitledBottom = 
						new JLabel("標題(Titled) : 圓角線條框線、底端框線之下", 
											JLabel.CENTER);
		//宣告標籤

		TitledBorder titledbordertop = new TitledBorder("標題文字");

		lbTitledTop.setBorder(titledbordertop); //設定標籤使用標題框線

		LineBorder lineborderround = 
								new LineBorder(Color.GRAY, 1, true);
		//宣告線條框線

		TitledBorder titledborderbottom = 
			BorderFactory.createTitledBorder(
					lineborderround, "標題文字",
					TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM);
		//以BorderFactory類別的靜態方法建立標題框線
	
		titledborderbottom.setTitleColor(Color.blue);
		//設定標題文字的顏色

		lbTitledBottom.setBorder(titledborderbottom);
		//設定標籤使用標題框線

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new GridLayout(1, 2, 10, 10));
		//設定內容面版的佈局管理員物件

		cp.add(lbTitledTop); //將標籤加入面版
		cp.add(lbTitledBottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式

		setSize(600, 120); //以最適大小顯示視窗
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new TitledBorderEX(); //宣告視窗框架物件
	}
}