import javax.swing.*;  //引用Swing套件
import java.awt.*;

public class ComponentEX extends JFrame {

	ComponentEX() {

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new GridLayout(2, 1));
		//設定內容面版使用GridLayout佈局管理員

		JPanel jpFirst = new JPanel(); //宣告JPanel容器

		jpFirst.setLayout(null); //設定容器不使用佈	局管理員

		JButton btnA = new JButton("按鈕A"); //宣告按鈕元件
		JButton btnB = new JButton("按鈕B");

		Rectangle rec = new Rectangle(50, 50, 100, 100);
		//宣告表示位置與大小的Rectangle物件

		btnA.setBounds(rec); //設定按鈕元件的位置與大小

		btnB.setSize(new Dimension(100,100)); //設定按鈕大小
		//btnB.setPreferredSize(new Dimension(100, 100));
		btnB.setLocation(200, 50); //設定元件位置

		jpFirst.add(btnA); //將元件加入容器
		jpFirst.add(btnB);

		JPanel jpSecond = new JPanel();

		JButton btnC = new JButton("按鈕C");
		JButton btnD = new JButton("按鈕D");

		btnC.setMinimumSize(new Dimension(50, 50)); //設定最小大小
		btnC.setPreferredSize(new Dimension(75, 75)); //設定喜好大小
		btnC.setMaximumSize(new Dimension(100, 100)); //設定最大大小

		btnD.setSize(new Dimension(100,100)); //設定元件的大小

		jpSecond.add(btnC); //將元件加入容器
		jpSecond.add(btnD);

		cp.add(jpFirst); //將容器加入內容面版
		cp.add(jpSecond);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定按下視窗右上角關閉按鈕將關閉視窗並結束應用程式的執行
		setSize(350, 350); //設定視窗的大小
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {
		new ComponentEX(); //建立視窗框架物件
	}
}