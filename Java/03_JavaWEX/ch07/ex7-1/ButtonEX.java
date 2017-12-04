import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ButtonEX extends JFrame{

	JButton enable = new JButton("使圖示按鈕有效");
	JButton disable = new JButton();
	JButton iconBtn = new JButton(new ImageIcon(
		ButtonEX.class.getResource("images/bitc.gif"), "位元文化LOGO"));
	//宣告指令按鈕物件

	//定義並宣告監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			//取得動作命令字串, 並判斷觸發事件的按鈕
			if(e.getActionCommand().equals("enable")){
				enable.setEnabled(false); //依照需求設定按鈕是否有效
				disable.setEnabled(true);
				iconBtn.setEnabled(true);
			}
			else{
				enable.setEnabled(true);
				disable.setEnabled(false);
				iconBtn.setEnabled(false);
			}
		}	
	};

	ButtonEX(){

		disable.setText("使圖示按鈕無效"); //設定按鈕顯示的文字

		enable.setActionCommand("enable"); //設定按鈕的動作命令字串
		disable.setActionCommand("disable");

		disable.setEnabled(false); //將按鈕的起始狀態設定為無效
		iconBtn.setEnabled(false);

		enable.addActionListener(al); //註冊監聽器
		disable.addActionListener(al);

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new FlowLayout()); //設定以FlowLayout配置版面

		cp.add(enable); //將元件加入面版
		cp.add(iconBtn);
		cp.add(disable);

		//設定視窗關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(600, 150);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ButtonEX(); //宣告視窗框架物件
	}
}