import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class FocusKeyEX extends JFrame {

	JLabel lbKey = new JLabel("鍵盤事件 : "), //宣告標籤元件
				 lbInput = new JLabel("輸入字元 : ");

	JButton btnOne = new JButton("按鈕一"), //宣告按鈕元件
				  btnTwo = new JButton("按鈕二");

	//實作FocusListener介面的監聽器類別
	class BtnFocusListener implements FocusListener {

		private String btnName;

		public BtnFocusListener(String name){ //建構子
			btnName = name;
		}

		public void focusGained(FocusEvent e){ //處理取得焦點的方法
			((JButton) e.getSource()).setText(" [" 
				+ btnName + "] 取得焦點");
		}

		public void focusLost(FocusEvent e){ //處理失去焦點的方法
			((JButton) e.getSource()).setText(" [" 
				+ btnName + "] 失去焦點");
		}
	};

	//以匿名類別的方式實作KeyListener介面, 
	//宣告回應滑鼠事件的監聽器
	KeyListener klButton = new KeyListener() {

		//處理鍵盤按鍵被按下的方法
		public void keyPressed(KeyEvent e) {
			lbKey.setText("鍵盤事件 : [" + 
				KeyEvent.getKeyText(e.getKeyCode()) + "] 鍵被按下");
		}

		//處理鍵盤按鍵被放開的方法
		public void keyReleased(KeyEvent e) {
			lbKey.setText("鍵盤事件 : [" + 
				KeyEvent.getKeyText(e.getKeyCode()) + "] 鍵被放開");
		}

		//處理由鍵盤輸入字元的方法
		public void keyTyped(KeyEvent e) {
			lbInput.setText("輸入字元 : '" + e.getKeyChar() + "'");
		}
	};

	FocusKeyEX(){

		btnOne.addFocusListener(new BtnFocusListener("按鈕一"));
		btnTwo.addFocusListener(new BtnFocusListener("按鈕二"));
		//註冊監聽按鈕焦點事件的監聽器物件

		btnTwo.addKeyListener(klButton);
		//註冊由klButton監聽視窗接收到的鍵盤事件

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(2, 2, 5, 5));
		//設定使用GridLayout管理版面佈局

		cp.add(btnOne); //將按鈕元件加入內容面版
		cp.add(btnTwo);

		cp.add(lbKey); //將顯示訊息的標籤加入內容面版
		cp.add(lbInput);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 130);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FocusKeyEX(); //產生視窗框架物件
	}
}