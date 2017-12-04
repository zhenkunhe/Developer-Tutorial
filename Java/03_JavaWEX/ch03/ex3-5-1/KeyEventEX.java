import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class KeyEventEX extends JFrame {

	JLabel lbKey = new JLabel("鍵盤事件"),
				 lbInput = new JLabel("輸入字元");

	//以匿名類別的方式實作KeyListener介面, 
	//宣告回應滑鼠事件的監聽器
	KeyListener klFrame = new KeyListener() {

		//處理鍵盤按鍵被按下的方法
		public void keyPressed(KeyEvent e) {
			lbKey.setText(" [" + KeyEvent.getKeyText(e.getKeyCode()) + "] 鍵被按下");
		}

		//處理鍵盤按鍵被放開的方法
		public void keyReleased(KeyEvent e) {
			lbKey.setText(" [" + KeyEvent.getKeyText(e.getKeyCode()) + "] 鍵被放開");
		}

		//處理由鍵盤輸入字元的方法
		public void keyTyped(KeyEvent e) {
			lbInput.setText("輸入 '" + e.getKeyChar() + "'");
		}
	};

	KeyEventEX(){

		Container cp = getContentPane(); //取得內容面版

		cp.add(lbKey); //將顯示訊息的標籤加入內容面版
		cp.add(lbInput, BorderLayout.SOUTH);

		addKeyListener(klFrame);
		//註冊由klFrame監聽視窗接收到的鍵盤事件

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new KeyEventEX(); //產生視窗框架物件
	}
}