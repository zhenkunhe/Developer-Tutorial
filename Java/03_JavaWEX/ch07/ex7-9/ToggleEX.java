import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*; //引用包含ChangeListener介面的套件

import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class ToggleEX extends JFrame{

	JLabel lbTitle = new JLabel("設定對齊方式");

	JToggleButton tbLeft = new JToggleButton("靠上"),
							tbCenter = new JToggleButton("置中",  true),
							tbRight = new JToggleButton("靠下");
	//宣告切換按鈕

	ButtonGroup bgVAlign = new ButtonGroup();
	//按鈕群組

	//定義並宣告回應ChangeEvent事件的監聽器
	ChangeListener cl = new ChangeListener(){

		public void stateChanged(ChangeEvent e){

			JToggleButton tb = (JToggleButton) e.getSource();
			//取得觸發事件的切換按鈕

			String command = tb.getActionCommand();
			//取得觸發事件之切換按鈕的動作命令字串

			//判斷動作命令字串以設定標籤內文字的對齊方式
			if(command.equals("Top"))
				lbTitle.setVerticalAlignment(SwingConstants.TOP);
			else if(command.equals("Center"))
				lbTitle.setVerticalAlignment(SwingConstants.CENTER);
			else if(command.equals("Bottom"))
				lbTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		}	
	};

	ToggleEX(){

		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		//設定標籤內文字的水平方向對齊方式

		JPanel jpVAlign = new JPanel(new GridLayout(3, 1, 5, 5));
		//宣告放置切換按鈕的JPanel容器

		jpVAlign.add(tbLeft); //將切換按鈕加入JPanel內
		jpVAlign.add(tbCenter);
		jpVAlign.add(tbRight);

		bgVAlign.add(tbLeft); //將切換按鈕加入ButtonGroup
		bgVAlign.add(tbCenter);
		bgVAlign.add(tbRight);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			"設定文字的垂直對齊方式"
			, TitledBorder.LEFT, TitledBorder.TOP);
		//宣告標題框線物件, 標題文字將靠左靠上對齊, 顏色為淺灰, 寬度為2

		jpVAlign.setBorder(border); //設定JPanel使用的框線

		tbLeft.setActionCommand("Top"); //設定切換按鈕的動作命令字串
		tbCenter.setActionCommand("Center");
		tbRight.setActionCommand("Bottom");

		tbLeft.addChangeListener(cl); //註冊回應ActionEvent事件的監聽器
		tbCenter.addChangeListener(cl);
		tbRight.addChangeListener(cl);
		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(1, 2));
		//設定使用GridLayout管理版面
		cp.add(lbTitle); //將元件加入內容面版
		cp.add(jpVAlign);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ToggleEX(); //宣告視窗框架物件
	}
}