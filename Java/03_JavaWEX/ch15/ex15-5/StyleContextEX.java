import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class StyleContextEX extends JFrame {

	JTextPane tpStyleA = new JTextPane(),
					   tpStyleB = new JTextPane(); //樣式文字面版

	class ThreadA extends Thread {
		public void run(){
			//將文字內容與文字屬性新增至文字面版
			for(int i = 0; i<100; i++){
				SimpleAttributeSet sasContent = 
					new SimpleAttributeSet();
				//宣告內文的樣式屬性

				StyleConstants.setFontSize(sasContent, 14);
				//設定內文樣式屬性的字型大小為14

				int len = tpStyleA.getText().length();
				//取得樣式文字面版內容的長度

				tpStyleA.setCaretPosition(len);
				//將游標移至最後, 將取消選取

				tpStyleA.setCharacterAttributes(sasContent, false);
				//設定文字的樣式屬性

				tpStyleA.replaceSelection("Java 2 視窗程式設計");
				//未選取文字, 故效果相當於插入文字
			}
		}
	};

	class ThreadB extends Thread {
		public void run(){
			//將文字內容與文字屬性新增至文字面版
			for(int i = 0; i<100; i++){
				StyleContext sc = StyleContext.getDefaultStyleContext();
				//取得預設的StyleContext物件
	
				AttributeSet sasContent =
					sc.addAttribute(SimpleAttributeSet.EMPTY,
									StyleConstants.FontSize, 14);
				//將字型大小為14的樣式屬性加入StyleContext物件, 並取得設定完成加入的AttributeSet物件

				int len = tpStyleB.getText().length();
				//取得樣式文字面版內容的長度

				tpStyleB.setCaretPosition(len);
				//將游標移至最後, 將取消選取

				tpStyleB.setCharacterAttributes(sasContent, false);
				//設定文字的樣式屬性

				tpStyleB.replaceSelection("Java 2 視窗程式設計");
				//未選取文字, 故將插入文字
			}
		}
	};

	StyleContextEX(){

		JButton btnLoad = new JButton("載入內容");

		btnLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				(new ThreadA()).start(); //啟動執行緒
				(new ThreadB()).start();
			}
		});

		JPanel jpPane = new JPanel(new GridLayout(1, 2, 5,  5));
		jpPane.add(new JScrollPane(tpStyleA));
		jpPane.add(new JScrollPane(tpStyleB));

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpPane);
		cp.add(btnLoad, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new StyleContextEX(); //宣告視窗框架物件
	}
}