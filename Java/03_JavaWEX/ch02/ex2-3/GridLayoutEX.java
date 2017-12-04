import javax.swing.*;  //引用套件
import java.awt.*;

public class GridLayoutEX extends JFrame {

	GridLayoutEX() {
		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(3, 4, 10, 10));
		//指定版面運用3列4行的格狀佈局管理員, 水平與垂直間距分別為10

		for(int i=1; i<=7; i++)
			cp.add(new JButton("Button_" + i));
			//將7個按鈕元件加入版面

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new GridLayoutEX();
	}
}