import javax.swing.*;  //引用套件
import java.awt.*;

public class RootPaneEX extends JFrame {

	RootPaneEX() {

		int gap = 5; //空白框線的寬度

		getRootPane().setBorder(
			BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		//宣告根面版四週使用寬度為5的空白框線

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		//將各按鈕控制項，加入版面的指定位置
		cp.add(new JButton("EAST"), BorderLayout.EAST);		
		cp.add(new JButton("WEST"), BorderLayout.WEST);
		cp.add(new JButton("SOUTH"), BorderLayout.SOUTH);
		cp.add(new JButton("NORTH"), BorderLayout.NORTH);
		cp.add(new JButton("CENTER"));
		//將元件加入中間區域,
		//相當於cp.add(new JButton("CENTER", BorderLayout.CENTER));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new RootPaneEX();
	}
}