import javax.swing.*;  //引用Swing套件
import java.awt.*; //引用AWT套件

public class SizeLocationEX extends JFrame {

	SizeLocationEX() {
		super("HelloSwing"); //呼叫JFrame的建構子, 並傳入視窗標題

		//STEP 2、取得可放置元件的內容面版
		Container cp = getContentPane();

		//STEP 3、宣告加入視窗的按鈕元件
		JButton button = new JButton("Hello Swing!");

		//STEP 4、將元件加入面版
		cp.add(button);

		Dimension dim = getToolkit().getScreenSize(); //取得螢幕大小

		setSize((int)dim.getWidth()/5, (int)dim.getHeight()/5); //設定視窗畫面的大小

		setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
		//設定視窗顯示在螢幕的中央

		//STEP 5、設定視窗關閉動作，調整視窗大小，並顯示
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定按下視窗右上角關閉按鈕將關閉視窗並結束應用程式的執行
		
		setVisible(true); //顯示視窗
	}

	public static void main(String args[]) {

		//STEP 1、建立視窗框架
		new SizeLocationEX();
	}
}