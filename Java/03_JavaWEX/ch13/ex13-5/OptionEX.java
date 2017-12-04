import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class OptionEX extends JFrame{

	JLabel lbUser = new JLabel("選取結果 : ");
	//宣告顯示結果的標籤

	OptionEX(){

		JButton btnOption1 = new JButton("自訂對話盒(自訂形式一)");
		//宣告呼叫第一種方式建立之自訂對話盒的按鈕

		//註冊回應ActionEvent事件的監聽器物件
		btnOption1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String strMsg = "以下所列的公司中,\n" + 
						  "哪一個最有能力成為代表台灣的企業?",
						   strTitle = "自訂對話盒";
				//顯示於自訂對話盒的訊息

				JOptionPane pane = new JOptionPane(strMsg);
				//宣告自訂對話盒

				String[] options = 
					new String[] {"台積電", "鴻海", "華碩", "宏碁"};
				//宣告自訂對話盒的按鈕項目

				pane.setOptions(options); //設定自訂對話盒的選項
				pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
				//設定自訂對話盒的訊息類型

				JDialog dialog = pane.createDialog(
										OptionEX.this, strTitle);
				//建立自訂對話盒的對話盒物件

				dialog.setVisible(true); //顯示對話盒

				lbUser.setText("選取結果 : " + (String)pane.getValue());
				//顯示使用者按下的按鈕
			}
		});

		JButton btnOption2 = new JButton("關於對話盒(自訂形式二)");
		//宣告呼叫第二種方式建立之自訂對話盒的按鈕

		//註冊回應ActionEvent事件的監聽器物件
		btnOption2.addActionListener(new ActionListener(){

			private final ImageIcon ICON_BITC = 
				new ImageIcon("icon/Bitc_Logo_Only.gif");
			//宣告引用圖示的靜態常數

			public void actionPerformed(ActionEvent e){

				JOptionPane.showOptionDialog(null, 
					"Java 2 視窗程式設計 範例13-5 第一版\n" +
					"位元文化(R) 郭尚君 版權所有 (c) \n" + 
					"Copyright (c) 1998-2005 BitCultrue Inc. " +
					"All Rights Reserved.", 
					"關於", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE,  ICON_BITC,
					null, null);
				//顯示自訂的關於對話盒
			}
		});

		Box bxOption = new Box(BoxLayout.X_AXIS);
		bxOption.add(btnOption1); //將指令按鈕加入Box容器
		bxOption.add(Box.createHorizontalGlue());
		bxOption.add(btnOption2);

		Container cp = getContentPane(); //取得內容面版
		cp.add(bxOption); //將Box容器加入內容面版
		cp.add(lbUser, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(370, 100);
		setVisible(true);
	}

	public static void main(String args[]) {
		new OptionEX(); //宣告視窗框架物件
	}
}