import javax.swing.*;  //引用套件
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LabelIconEX extends JFrame{

	LabelIconEX(){

		JLabel lbBitcCenter = new JLabel("文字置於圖示中央",
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//同時顯示文字與圖示的標籤, 圖示置中

		lbBitcCenter.setHorizontalTextPosition(SwingConstants.CENTER);
		//設定文字的水平方向相對於圖示採置中對齊

		JLabel lbBitcTop = new JLabel("文字置於圖示中央、靠上", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//同時顯示文字與圖示的標籤, 圖示置中

		lbBitcTop.setHorizontalTextPosition(SwingConstants.CENTER);
		//設定文字的水平方向相對於圖示採置中對齊

		lbBitcTop.setVerticalTextPosition(SwingConstants.TOP);
		//設定文字的垂直方向相對於圖示採置上對齊

		JLabel lbBitcBottom = new JLabel("文字置於圖示中央、靠下", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//同時顯示文字與圖示的標籤, 圖示置中

		lbBitcBottom.setHorizontalTextPosition(SwingConstants.CENTER);
		//設定文字的水平方向相對於圖示採置中對齊

		lbBitcBottom.setVerticalTextPosition(SwingConstants.BOTTOM);
		//設定文字的垂直方向相對於圖示採置下對齊

		JLabel lbBitcLeft = new JLabel("文字置於圖示左方、靠下", 
			new ImageIcon(".\\Icon\\Bitc.gif"),
			SwingConstants.CENTER);
		//同時顯示文字與圖示的標籤, 圖示置中

		lbBitcLeft.setHorizontalTextPosition(SwingConstants.LEFT);
		//設定文字的水平方向相對於圖示採置左對齊

		lbBitcLeft.setVerticalTextPosition(SwingConstants.BOTTOM);
		//設定文字的垂直方向相對於圖示採置下對齊

		JLabel lbBitcRight = new JLabel("文字置於圖示右方、靠上", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.CENTER);
		//同時顯示文字與圖示的標籤, 圖示置中

		lbBitcRight.setHorizontalTextPosition(SwingConstants.RIGHT);
		//設定文字的水平方向相對於圖示採置右對齊

		lbBitcRight.setVerticalTextPosition(SwingConstants.TOP);
		//設定文字的水平方向相對於圖示採置上對齊

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(2, 3, 10, 10));
		//取得佈局管理員

		cp.add(lbBitcCenter); //將元件加入面版
		cp.add(lbBitcTop);
		cp.add(lbBitcBottom);
		cp.add(lbBitcLeft);
		cp.add(lbBitcRight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		pack(); //以適當大小顯示視窗
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new LabelIconEX(); //宣告視窗框架物件
	}
}