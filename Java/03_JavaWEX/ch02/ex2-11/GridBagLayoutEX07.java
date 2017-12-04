import javax.swing.*; //引用Swing套件
import java.awt.*; //引用AWT套件

public class GridBagLayoutEX07 extends JFrame {

	GridBagLayoutEX07() {
		Container cp = getContentPane(); //取得版面

		cp.setLayout(new GridBagLayout());
		//設定使用GridBagLayout版面配置

		GridBagConstraints gbc = new GridBagConstraints();
		//宣告控制元件版面配置的GridBagConstraints物件

		gbc.insets  = new Insets(5, 5, 5, 5); //設定元件與四周元件的距離
		gbc.ipadx = gbc.ipady = 10;
		//設定元件內文字與四周邊界的間距均為10

		/***加入第一列的元件***/
		gbc.gridx = 0; gbc.gridy = 0; //設定元件的行列位置
		gbc.gridwidth = 1; gbc.gridheight = 2; //設定寬度為1欄高度為2列
		gbc.weightx = gbc.weighty = 1.0; //設定分配多餘空間的權重
		gbc.fill = GridBagConstraints.BOTH;
		//設定元件填滿顯示空間的垂直、水平空間

		cp.add(new JButton("Button 1"), gbc); //將按鈕加到版面

		gbc.gridx = 1; gbc.gridy = 0; //設定元件的行列位置
		gbc.gridwidth = 2; gbc.gridheight = 1; //設定寬度為2欄高度為1列
		gbc.weightx = 0.0; gbc.weighty = 1.0; //設定垂直方向權重為1.0
		gbc.fill = GridBagConstraints.HORIZONTAL;  //設定水平填滿顯示空間
		cp.add(new JButton("Button 2"), gbc);

		gbc.weightx = gbc.weighty = 0.0; //恢復預設值
		gbc.fill = GridBagConstraints.NONE; //恢復預設值不填滿顯示空間

		/***加入第二列的元件***/
		gbc.gridx = 1; gbc.gridy = 1; //設定元件的行列位置
		gbc.gridwidth = gbc.gridheight = 1;  //設定寬度為1欄高度為1列
		cp.add(new JButton("Button 3"), gbc);

		gbc.gridx = 2; gbc.gridy = 1; //設定元件的行列位置
		cp.add(new JButton("Button 4"), gbc);

		/***加入第三列的元件***/
		gbc.gridx = 0; gbc.gridy = 2; //設定元件的行列位置
		cp.add(new JButton("Button 5"), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //設定元件的行列位置
		gbc.gridwidth = gbc.gridheight = 2;  //設定寬度為2欄高度為2列
		gbc.weightx = 0.0; gbc.weighty = 1.0; //設定垂直方向權重為1.0
		gbc.fill = GridBagConstraints.VERTICAL;
		//設定元件垂直填滿顯示空間

		cp.add(new JButton("Button 6"), gbc);

		gbc.weightx = gbc.weighty = 0.0; //恢復預設值
		gbc.fill = GridBagConstraints.NONE; //恢復預設值不填滿顯示空間

		/***加入第四列的元件***/
		gbc.gridx = 0; gbc.gridy = 3; //設定元件的行列位置
		gbc.gridwidth = gbc.gridheight = 1;  //設定寬度為1欄高度為1列
		cp.add(new JButton("Button 7"), gbc);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗時，將結束應用程式

		pack(); //調整視窗大小
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {
		new GridBagLayoutEX07(); //宣告建立視窗畫面的frame物件
	}
}