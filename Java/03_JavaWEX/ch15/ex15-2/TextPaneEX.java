import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextPaneEX extends JFrame {

	JTextPane tpPane = new JTextPane(); //樣式文字面版

	TextPaneEX(){

		JButton  btnText = new JButton("插入文字");
		//宣告將文字插入樣式文字面版的指令按鈕

		//註冊回應ActionEvent事件的監聽器物件,
		//並將文字插入樣式文字面版
		btnText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tpPane.replaceSelection("位元文化");
				//插入文字
			}
		});

		JButton  btnIcon = new JButton("插入圖示");
		//宣告將圖示插入樣式文字面版的指令按鈕

		//註冊回應ActionEvent事件的監聽器物件,
		//並將圖示插入樣式文字面版
		btnIcon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tpPane.insertIcon(new ImageIcon("icon\\bitc.gif"));
				//插入圖示
			}
		});

		JButton  btnButton = new JButton("插入按鈕元件");
		//宣告將按鈕元件插入樣式文字面版的指令按鈕

		//註冊回應ActionEvent事件的監聽器物件,
		//並將按鈕元件插入樣式文字面版
		btnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JButton btn = new JButton("我是按鈕");
				//宣告按鈕元件

				//註冊回應按鈕元件之ActionEvent事件的監聽器物件
				btn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JOptionPane.showMessageDialog(
							TextPaneEX.this, "樣式文字面版內的按鈕被按下");
						//顯示訊息對話盒
					}
				});

				tpPane.insertComponent(btn);
				//將按鈕元件插入樣式文字面版
			}
		});

		Box bxButton = new Box(BoxLayout.X_AXIS);
		//宣告容納指令按鈕的Box容器

		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnText); //將按鈕加入容器
		bxButton.add(Box.createHorizontalStrut(5));
		bxButton.add(btnIcon);
		bxButton.add(Box.createHorizontalStrut(5));
		bxButton.add(btnButton);
		bxButton.add(Box.createHorizontalGlue());


		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5,  5));
		//設定使用間隔寬度為5的BorderLayout物件配置版面

		cp.add(new JScrollPane(tpPane));
		cp.add(bxButton, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TextPaneEX(); //宣告視窗框架物件
	}
}