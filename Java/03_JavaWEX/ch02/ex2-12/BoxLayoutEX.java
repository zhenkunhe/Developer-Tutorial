import javax.swing.*; //引用Swing套件
import java.awt.*; //引用AWT套件

public class BoxLayoutEX extends JFrame {

	BoxLayoutEX() {
		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		//設定使用水平方向排列的BoxLayout配置版面

		Box xBox = new Box(BoxLayout.X_AXIS);
		//宣告Box物件, 並設定依X軸排列元件

		xBox.add(new JButton("按鈕 1"));
		xBox.add(Box.createHorizontalGlue()); //在元件間加入膠水
		xBox.add(new JButton("按鈕 2"));

		Box vBox = new Box(BoxLayout.Y_AXIS);
		//宣告Box物件, 並設定依Y軸排A列元件

		vBox.add(new JButton("按鈕 3"));
		vBox.add(Box.createVerticalStrut(20));
		//在元件間加入高度為20的垂直支架

		vBox.add(new JButton("按鈕 4"));

		cp.add(xBox);
		cp.add(Box.createHorizontalStrut(40));
		cp.add(vBox);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗時，將結束應用程式

		setSize(400, 150); //調整視窗大小
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {
		new BoxLayoutEX();
	}
}