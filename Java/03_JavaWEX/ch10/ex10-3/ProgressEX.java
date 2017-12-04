import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ProgressEX extends JFrame{

	final static int MAX_VALUE = 20; //定義進度列的最大值

	JProgressBar pb = new JProgressBar(); //宣告進度列

	JButton btnStart = new JButton("開始"); //啟動計時器的按鈕

	Timer timer = new Timer(500, //每隔0.5秒發出ActionEvent事件
		new ActionListener(){ //定義回應事件的監聽器物件

			private int value = 0; //起始計次變數

			public void actionPerformed(ActionEvent e){

				if(value <  MAX_VALUE){ //判斷value是否小於最大值
					pb.setValue(++value);
					//將先value值加1, 再設定進度列的值
				}
				else{
					value = 0; //重設計次變數
					pb.setValue(value); //重設進度列
					btnStart.setText("開始"); //設定指令按鈕的文字
					timer.stop(); //終止計時器
				}
			}
		});
	//定義執行計時的Timer物件

	ProgressEX(){

		pb.setOrientation(JProgressBar.HORIZONTAL);
		//設定進度列為水平方向

		pb.setMinimum(0); //設定最小值為0
		pb.setMaximum(MAX_VALUE); //設定最大值
		pb.setValue(0); //設定起始值
		pb.setStringPainted(true); //設定進度列是否顯示進度字串
		
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				if(e.getActionCommand().equals("開始")){
					((JButton) e.getSource()).setText("終止");
					//將按鈕顯示的文字更改為"終止"

					timer.start(); //啟動計時器
				}
				else{
					((JButton) e.getSource()).setText("開始");
					//將按鈕顯示的文字更改為"開始"

					timer.stop(); //終止計時器
					pb.setValue(0); //重設進度列
				}
			}
		});
		//註冊回應按鈕ActionEvent事件的監聽器物件

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		//設定內容面版水平與垂直間距均為10的BorderLayout物件管理版面

		add(pb); //將元件加入
		add(Box.createHorizontalStrut(10));
		add(btnStart);

		getRootPane().setBorder(new EmptyBorder(4, 4, 4, 4));
		//設定根面版將使用

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);
	}

	public static void main(String args[]) {
		new ProgressEX(); //宣告視窗框架物件
	}
}