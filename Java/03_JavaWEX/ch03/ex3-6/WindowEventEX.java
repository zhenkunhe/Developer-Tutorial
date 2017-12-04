import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class WindowEventEX extends JFrame {

	JLabel lbMsg = new JLabel("WindowEvent事件的回應",
													JLabel.CENTER);

	//以匿名類別實作WindowListener介面, 
	//並宣告監聽WindowEvent事件的監聽器物件
	WindowListener wlFrame = new WindowListener(){

		//回應視窗進入有效狀態(取得焦點)的方法
		public void windowActivated(WindowEvent e){
			System.out.println("WindowListener : 視窗取得焦點");
		}
		//回應視窗關閉行為的方法
		public void windowClosed(WindowEvent e){
			System.out.println("WindowListener : 視窗已經關閉");
		}
		//回應視窗正在關閉中的方法
		public void windowClosing(WindowEvent e){
			System.out.println("WindowListener : 視窗正在關閉中");
		}
		//回應視窗失去焦點的方法
		public void windowDeactivated(WindowEvent e){
			System.out.println("WindowListener : 視窗失效");
		}
		//回應視窗從圖示化還原的方法
		public void windowDeiconified(	WindowEvent e){
			System.out.println("WindowListener : 視窗從圖示化還原");
		}
		//回應視窗圖示化的方法
		public void windowIconified(WindowEvent e){
			System.out.println("WindowListener : 視窗圖示化");
		}
		//回應開啟視窗的方法
		public void windowOpened(WindowEvent e){
			System.out.println("WindowListener : 視窗已經開啟");
		}
	};

	//以匿名類別實作WindowFocusListener介面, 並宣告監聽器物件
	WindowFocusListener wflFrame = new WindowFocusListener(){
		//回應視窗取得焦點
		public void windowGainedFocus(WindowEvent e){
			System.out.println("WindowFocusListener : 視窗取得焦點");
		}
		//回應視窗失去焦點
		public void windowLostFocus(WindowEvent e){
			System.out.println("WindowFocusListener : 視窗取得焦點");
		}
	};

	//以匿名類別實作WindowStateListener介面, 並宣告監聽器物件
	WindowStateListener wslFrame = new WindowStateListener(){
		//回應視窗狀態改變
		public void windowStateChanged(WindowEvent e){
			System.out.println("WindowStateListener : 視窗狀態改變");
		}
	};

	WindowEventEX(){

		addWindowListener(wlFrame);
		//註冊由wlFrame監聽視窗的WindowEvent

		addWindowFocusListener(wflFrame);
		//註冊由wflFrame監聽視窗的WindowEvent

		addWindowStateListener(wslFrame);
		//註冊由wslFrame監聽視窗的WindowEvent

		add(lbMsg); //將顯示訊息的標籤加入內容面版

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new WindowEventEX(); //產生視窗框架物件
	}
}