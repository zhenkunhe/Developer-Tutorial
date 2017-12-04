import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ButtonIconEX extends JFrame{

	JButton btnIcon = new JButton(); 	//宣告指令按鈕物件

	ButtonIconEX(){

		btnIcon.setIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Normal.gif")));
		//設定按鈕圖示

		btnIcon.setPressedIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Pressed.gif")));
		//設定按下按鈕顯示的圖示

		btnIcon.setDisabledIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Disabled.gif")));
		//設定按鈕無效時顯示的圖示

		btnIcon.setRolloverIcon(new ImageIcon(
			ButtonIconEX.class.getResource("images\\Rollover.gif")));
		//設定滑鼠游標略過按鈕顯示的圖示

		JButton btnDisabled = new JButton("設定按鈕為無效");
		//將按鈕狀態設定為無效的按鈕

		//註冊並宣告監聽ActionEvent事件的監聽器物件
		btnDisabled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("設定按鈕為無效")){
					btnIcon.setEnabled(false); //將按鈕狀態設定為無效
					((JButton)e.getSource()).setText("設定按鈕為有效");
					//設定按鈕顯示的文字
				}
				else{
					btnIcon.setEnabled(true); //將按鈕狀態設定為有效
					((JButton)e.getSource()).setText("設定按鈕為無效");
					//設定按鈕顯示的文字
				}
			}
		});

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		cp.add(btnIcon); //將元件加入Box容器
		cp.add(Box.createVerticalStrut(20));
		cp.add(btnDisabled);

		//設定視窗關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new ButtonIconEX(); //宣告視窗框架物件
	}
}