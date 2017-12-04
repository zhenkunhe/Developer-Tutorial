import javax.swing.*; //引用Swing套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ActionEX extends JFrame
	implements ActionListener { //實作ActionListener介面的監聽器類別

	JButton clickME = new JButton("請按我!"); //建立元件
	JLabel label = new JLabel("按鈕未被按下");

	int clickCount = 0; //按鈕被按下的次數

	ActionEX(){
		Container cp = getContentPane(); //取得內容面版

		cp.add(clickME, BorderLayout.CENTER); //將元件加入面版
		cp.add(label, BorderLayout.SOUTH);

		clickME.addActionListener(this); //將事件委任給視窗框架監聽

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 90);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
			clickCount ++; //增加按鈕被按下次數
		
			//取得發出事件之元件的動作命令字串, 並設定標籤的內容
			label.setText("[" + e.getActionCommand()
				+ "] 按鈕被按了" + clickCount + "次");
	}

	public static void main(String args[]) {
		new ActionEX(); //產生視窗框架物件
	}
}