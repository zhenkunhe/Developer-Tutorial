import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ColorDialogEX extends JFrame{

	JLabel lbColor = new JLabel("選取的顏色", JLabel.CENTER);
	//宣告背景顏色與前景顏色設定的標籤

	JButton btnBGColor = new JButton("選取顏色");
	//宣告呼叫顏色對話盒的按鈕

	ColorDialogEX(){

		lbColor.setOpaque(true); //設定標籤背景不是透明的

		//註冊回應按鈕ActionEvent事件的監聽器
		btnBGColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				Color c = JColorChooser.showDialog(
										ColorDialogEX.this, 
										"選取顏色",
										lbColor.getBackground());
				//顯示選取顏色的顏色對話盒,
				//並以lbColor標籤的背景顏色為預設值,
				//最後並傳回使用者選取的顏色

				lbColor.setBackground(c); 
				//以對話盒傳回的顏色做為lbColor標籤的背景顏色
			}
		});

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(5, 5));

		cp.add(lbColor); //將包含按鈕的JPanel容器加入內容面版
		cp.add(btnBGColor, BorderLayout.SOUTH); //加入顯示訊息的標籤
		
		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 120);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ColorDialogEX(); //宣告視窗框架物件
	}
}