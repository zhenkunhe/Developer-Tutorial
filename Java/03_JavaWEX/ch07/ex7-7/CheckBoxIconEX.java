import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class CheckBoxIconEX extends JFrame{

	JCheckBox cbIcon = new JCheckBox(); //宣告核取方塊物件

	CheckBoxIconEX(){

		 cbIcon.setIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Normal.gif")));
		//設定核取方塊圖示

		cbIcon.setPressedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Pressed.gif")));
		//設定按下核取方塊顯示的圖示

		cbIcon.setRolloverIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Rollover.gif")));
		//設定滑鼠游標略過核取方塊顯示的圖示

		cbIcon.setRolloverSelectedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\RolloverSelected.gif")));

		cbIcon.setDisabledSelectedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\DisabledSelected.gif")));
		//設定核取方塊被選取但為無效狀態的圖示

		cbIcon.setSelectedIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Selected.gif")));
		//設定核取方塊被選取時顯示的圖示
	
		cbIcon.setDisabledIcon(new ImageIcon(
			CheckBoxIconEX.class.getResource("images\\Disabled.gif")));
		//設定核取方塊無效時顯示的圖示

		JButton btnDisabled = new JButton("設定按鈕為無效");
		//將按鈕狀態設定為無效的按鈕

		//註冊並宣告監聽ActionEvent事件的監聽器物件
		btnDisabled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("設定按鈕為無效")){
					cbIcon.setEnabled(false); //將核取方塊設定為無效
					((JButton)e.getSource()).setText("設定按鈕為有效");
					//設定按鈕顯示的文字
				}
				else{
					cbIcon.setEnabled(true); //將核取方塊狀態設定為有效
					((JButton)e.getSource()).setText("設定按鈕為無效");
					//設定按鈕顯示的文字
				}
			}
		});

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		cp.add(cbIcon); //將元件加入Box容器
		cp.add(Box.createVerticalStrut(20));
		cp.add(btnDisabled);

		//設定視窗關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new CheckBoxIconEX(); //宣告視窗框架物件
	}
}