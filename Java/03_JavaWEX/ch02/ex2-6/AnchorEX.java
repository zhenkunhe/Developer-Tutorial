import javax.swing.*; //引用Swing套件
import java.awt.*; //引用AWT套件

public class AnchorEX extends JFrame {

	AnchorEX() {
		Container cp = getContentPane(); //取得版面

		cp.setLayout(new GridBagLayout());
		//設定使用GridBagLayout版面配置

		GridBagConstraints gbc = new GridBagConstraints();
		//宣告控制元件版面配置的GridBagConstraints物件

		gbc.gridx =  gbc.gridy =  0; //指定元件加入的位置
		gbc.weightx  = gbc.weighty = 1.0;
		//設定當有多餘空間時, 元件將分配全部的空間

		cp.add(new JButton("CENTER"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.NORTH;
		cp.add(new JButton("NORTH"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.SOUTH;
		cp.add(new JButton("SOUTH"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.EAST;
		cp.add(new JButton("EAST"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.WEST;
		cp.add(new JButton("WEST"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.NORTHEAST;
		cp.add(new JButton("NORTHEAST"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.NORTHWEST;
		cp.add(new JButton("NORTHEAST"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.SOUTHEAST;
		cp.add(new JButton("SOUTHEAST"), gbc); //將按鈕加到版面

		gbc.anchor = GridBagConstraints.SOUTHWEST;
		cp.add(new JButton("SOUTHWEST"), gbc); //將按鈕加到版面

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗時，將結束應用程式

		setSize(350, 250); //設定視窗大小
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {
		new AnchorEX(); //宣告建立視窗畫面的frame物件
	}
}