import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class InnerEX extends JFrame{

	JButton clickME = new JButton("請按我!"); //建立元件
	JLabel label = new JLabel("按鈕未被按下");

	int clickCount = 0; //按鈕被按下的次數

	class alClickME implements ActionListener {
		public void actionPerformed(ActionEvent e){
			clickCount ++; //增加按鈕被按下次數

			//取得發出事件之元件的動作命令字串, 並設定標籤的內容
			label.setText("[" + e.getActionCommand()
				+ "] 按鈕被按了" + clickCount + "次");
		}	
	}

	InnerEX(){
		Container cp = getContentPane(); //取得內容面版

		cp.add(clickME, BorderLayout.CENTER); //將元件加入面版
		cp.add(label, BorderLayout.SOUTH);

		alClickME al = new alClickME(); //宣告監聽器類別物件
		clickME.addActionListener(al);
		//註冊由al監聽clickME元件的ActionEvent事件

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new InnerEX(); //產生視窗框架物件
	}
}