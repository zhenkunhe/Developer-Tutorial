import javax.swing.*;  //引用套件
import java.awt.*;

public class IconEX extends JFrame{

	IconEX(){

		JLabel lbIcon = new JLabel(new BitcIcon(80, 80));
		//以標籤顯示圖示

		JLabel lbImageIcon = new JLabel(new ImageIcon(".\\Icon\\Bitc.gif"));
		//以圖檔建立顯示於標籤的圖示

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(1, 2, 10, 10));
		//取得佈局管理員

		cp.add(lbIcon); //將元件加入面版
		cp.add(lbImageIcon);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		pack(); //以適當大小顯示視窗
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new IconEX(); //宣告視窗框架物件
	}
}

class BitcIcon implements Icon { //宣告實作Icon介面的圖示類別

	private int width, height;

	public BitcIcon(int w, int h){ //建構子
		width = w;
		height = h;
	}

	//繪製圖示
	public void paintIcon(Component c, Graphics g, int x, int y){
		g.drawRect(5, 5, 70, 70); //繪製矩形
		g.drawString("位元文化", 15, 45); //繪製文字
	}

	public int getIconWidth(){ //取得圖示寬度
		return width;
	}

	public int getIconHeight(){ //取得圖示高度
		return height;
	}
}