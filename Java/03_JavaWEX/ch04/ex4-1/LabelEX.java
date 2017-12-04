import javax.swing.*;  //引用套件
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LabelEX extends JFrame {

	LabelEX() {

		JLabel lbRight = new JLabel("靠右", JLabel.RIGHT);
		JLabel lbLeft = new JLabel("靠左", JLabel.LEFT);
		JLabel lbCenter = new JLabel("置中", JLabel.CENTER);

		JLabel lbLeadingTop = new JLabel("靠前緣", JLabel.LEADING);
		lbLeadingTop.setVerticalAlignment(SwingConstants.TOP);
		//設定垂直方向靠上對齊

		JLabel lbTrailingBottom = new JLabel("靠後緣", JLabel.TRAILING);
		lbTrailingBottom.setVerticalAlignment(SwingConstants.BOTTOM);
		//設定垂直方向靠下對齊
	
		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(2, 3, 10, 10)); //取得佈局管理員

		cp.add(lbLeft); //將元件加入面版
		cp.add(lbCenter);
		cp.add(lbRight);
		cp.add(lbLeadingTop);
		cp.add(lbTrailingBottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		setSize(300, 250); //設定視窗的大小為300x250
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new LabelEX(); //宣告視窗框架物件
	}
}