import javax.swing.*; //引用Swing套件
import java.awt.*; //引用AWT套件

public class GridBagLayoutEX05 extends JFrame {

	GridBagLayoutEX05() {
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

		gbc.anchor = GridBagConstraints.SOUTH;
		//設定對齊顯示空間的SOUTH邊

		cp.add(new JButton("Button 1"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.CENTER; //恢復預設值

		gbc.gridx = 1; gbc.gridy = 0; //設定元件的行列位置
		gbc.gridwidth = 2; gbc.gridheight = 1; //設定寬度為2欄高度為1列
		cp.add(new JButton("Button 2"), gbc);

		/***加入第二列的元件***/
		gbc.gridx = 1; gbc.gridy = 1; //設定元件的行列位置
		gbc.gridwidth = gbc.gridheight = 1;  //設定寬度為1欄高度均為2列
		cp.add(new JButton("Button 3"), gbc);

		gbc.gridx = 2; gbc.gridy = 1; //設定元件的行列位置
		cp.add(new JButton("Button 4"), gbc);

		/***加入第三列的元件***/
		gbc.gridx = 0; gbc.gridy = 2; //設定元件的行列位置
		cp.add(new JButton("Button 5"), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //設定元件的行列位置
		gbc.gridwidth = gbc.gridheight = 2;  //設定寬度為2欄高度為2列

		gbc.anchor = GridBagConstraints.SOUTHEAST;
		//設定對齊顯示空間的SOUTHEAST邊

		cp.add(new JButton("Button 6"), gbc);

		gbc.anchor = GridBagConstraints.CENTER; //還原設定

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
		new GridBagLayoutEX05(); //宣告建立視窗畫面的frame物件
	}
}