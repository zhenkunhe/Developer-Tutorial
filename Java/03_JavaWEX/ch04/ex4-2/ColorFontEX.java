import javax.swing.*;  //引用套件
import java.awt.*;

public class ColorFontEX extends JFrame {

	ColorFontEX() {

		JLabel lbRed = new JLabel("紅");
		lbRed.setForeground(Color.RED);
		//以Color類別的常數取得代表紅色的Color物件,
		//並設定為標籤的前景顏色

		JLabel lbGreen = new JLabel("綠");
		lbGreen.setForeground(new Color(0, 255, 0));
		//以整數的RGB值宣告代表綠色的Color物件,
		//並設定為標籤的前景顏色

		JLabel lbBlue = new JLabel("藍");
		lbBlue.setForeground(new Color(0.0f, 0.0f, 1.0f));
		//以浮點數的RGB值宣告代表藍色的Colord物件,
		//並設定為標籤的前景顏色

		JLabel lbTimesBold15 = new JLabel("Times New Roman");
		lbTimesBold15.setFont(
					new Font("Times New Roman", Font.BOLD, 20));
		//設定標籤內文字使用的字型
	
		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(2, 2, 10, 10)); //取得佈局管理員

		cp.add(lbRed); //將元件加入面版
		cp.add(lbGreen);
		cp.add(lbBlue);
		cp.add(lbTimesBold15);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		pack(); //設定以適當大小顯示
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new ColorFontEX(); //宣告視窗框架物件
	}
}