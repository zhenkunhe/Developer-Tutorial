import javax.swing.*; //引用Swing套件
import java.awt.*; //引用AWT套件

public class GridBagLayoutEX01 extends JFrame {

	GridBagLayoutEX01() {
		Container cp = getContentPane(); //取得版面

		cp.setLayout(new GridBagLayout());
		//設定使用GridBagLayout版面配置

		JButton btnOne = new JButton("Button One");
		
		btnOne.setPreferredSize(new Dimension(120, 40)); //設定元件的喜好大小

		cp.add(btnOne); //將按鈕加到版面
		cp.add(new JButton("2"));
		cp.add(new JButton("Button Three"));
		cp.add(new JButton("Button 4"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗時，將結束應用程式

		setSize(400, 80); //調整視窗大小
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {
		new GridBagLayoutEX01(); //宣告建立視窗畫面的frame物件
	}
}