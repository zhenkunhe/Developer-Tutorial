import javax.swing.*;  //引用Swing套件
import java.awt.*;

public class PanelEX extends JFrame {

	PanelEX() {
		JPanel jpBottom = new JPanel(); //建立群組控制項的JPanel類別
		JPanel jpLeft = new JPanel();
		JPanel jpRight = new JPanel();

		//運用awt套件內的Color類別之RED屬性設定各面版類別的背景顏色
		jpLeft.setBackground(Color.RED);
		jpBottom.setBackground(Color.YELLOW);
		jpRight.setBackground(Color.BLUE);

		jpLeft.add(new JButton("按鈕 1")); //將按鈕加入面版
		jpLeft.add(new JButton("按鈕 2"));

		jpRight.add(new JButton("按鈕 3"));
		jpRight.add(new JButton("按鈕 4"));
		jpRight.add(new JButton("按鈕 5"));

		jpBottom.add(jpLeft); //將面版加入底層面版
		jpBottom.add(jpRight);

		getContentPane().add(jpBottom); //將面版加入內容面版

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new PanelEX(); //建立視窗框架物件
	}
}