import javax.swing.*;  //引用套件
import java.awt.*;

public class FlowLayoutEX extends JFrame {

	FlowLayoutEX() {
		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//設定使用版面配置將運用靠右對齊的FlowLayout物件

		for(int i=1; i<=5; i++)
			cp.add(new JButton("按鈕 " + i));
			//將按鈕控制項加入面版

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150); //設定視窗的寬度為300, 高度為150
		setVisible(true);
	}

	public static void main(String args[]) {
		new FlowLayoutEX();
	}
}