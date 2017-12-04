import javax.swing.*; //引用Swing套件
import java.awt.*; //引用AWT套件

public class WeightEX extends JFrame {

	WeightEX() {
		Container cp = getContentPane(); //取得版面

		cp.setLayout(new GridBagLayout());
		//設定使用GridBagLayout版面配置

		GridBagConstraints gbc = new GridBagConstraints();
		//宣告控制元件版面配置的GridBagConstraints物件

		gbc.insets  = new Insets(5, 5, 5, 5); //設定元件與四周元件的距離

		gbc.weightx = gbc.weighty = 1.0; //設定每個元件分配多餘空間的權重

		/***加入第一列的元件***/
		gbc.ipadx = gbc.ipady = 15;	//設定元件內文字與四周邊界的間距均為15
		gbc.gridx = 0; gbc.gridy = 0; //設定元件的行列位置
		cp.add(new JButton("Button One"), gbc); //將按鈕加到版面

		gbc.gridx = 1; gbc.gridy = 0; //設定元件的行列位置
		cp.add(new JButton("2"), gbc);

		/***加入第二列的元件***/
		gbc.ipadx = gbc.ipady = 0;
		//設定元件內文字與四周邊界的間距均為0

		gbc.gridx = 0; gbc.gridy = 1; //設定元件的行列位置
		cp.add(new JButton("Button Three"), gbc);
		
		gbc.gridx = 1; gbc.gridy = 1; //設定元件的行列位置
		cp.add(new JButton("Button 4"), gbc);

		/***加入第三列的元件***/
		gbc.insets  = new Insets(0, 0, 0, 0); //設定元件與四周元件的距離
		gbc.gridx = 0; gbc.gridy = 2; //設定元件的行列位置
		cp.add(new JButton("Button 5"), gbc); //將按鈕加到版面

		gbc.gridx = 1; gbc.gridy = 2; //設定元件的行列位置
		cp.add(new JButton("Button 6"), gbc);

		/***加入第四列的元件***/
		gbc.gridx = 0; gbc.gridy = 3; //設定元件的行列位置
		cp.add(new JButton("Button 7"), gbc);
		
		gbc.gridx = 1; gbc.gridy = 3; //設定元件的行列位置
		cp.add(new JButton("Button 8"), gbc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗時，將結束應用程式

		pack(); //調整視窗大小
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {
		new WeightEX(); //宣告建立視窗畫面的frame物件
	}
}