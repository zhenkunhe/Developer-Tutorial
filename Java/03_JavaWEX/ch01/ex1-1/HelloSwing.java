import javax.swing.*;  //引用Swing套件
import java.awt.*; //引用AWT套件

public class HelloSwing {

	public static void main(String args[]) {

		//STEP 1、建立視窗框架
		JFrame frame = new JFrame("HelloSwing");

		//STEP 2、取得可放置元件的內容面版
		Container cp = frame.getContentPane();

		//STEP 3、宣告加入視窗的按鈕元件
		JButton button = new JButton("Hello Swing!");

		//STEP 4、將元件加入面版
		cp.add(button);

		//STEP 5、設定視窗關閉動作，調整視窗大小，並顯示視窗
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定按下視窗右上角關閉按鈕將關閉視窗並結束應用程式的執行

		frame.pack(); //調整視窗大小, 否則將僅顯示視窗的標題列
		frame.setVisible(true); //顯示視窗
	}
}