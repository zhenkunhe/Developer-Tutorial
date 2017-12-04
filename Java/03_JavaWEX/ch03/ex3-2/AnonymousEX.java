import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class AnonymousEX extends JFrame{

	JButton clickME = new JButton("請按我!"); //建立元件
	JLabel label = new JLabel("按鈕未被按下");

	int clickCount = 0; //按鈕被按下的次數

	//宣告並定義回應ActionEvent事件的匿名內部類別
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			clickCount ++; //增加按鈕被按下次數
		
			//取得發出事件之元件的動作命令字串, 並設定標籤的內容
			label.setText("[" + e.getActionCommand()
				+ "] 按鈕被按了" + clickCount + "次");
		}	
	}; //請注意結尾的分號

	AnonymousEX(){
		Container cp = getContentPane(); //取得內容面版

		cp.add(clickME, BorderLayout.CENTER); //將元件加入面版
		cp.add(label, BorderLayout.SOUTH);

		clickME.addActionListener(al);
		//註冊由al監聽clickME元件的ActionEvent事件

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new AnonymousEX(); //產生視窗框架物件
	}
}