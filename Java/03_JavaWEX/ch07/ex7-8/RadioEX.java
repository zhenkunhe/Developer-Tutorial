import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class RadioEX extends JFrame{

	JLabel lbTitle = new JLabel("設定對齊方式");

	JRadioButton rbLeft = new JRadioButton("靠左",  true),
							rbCenter = new JRadioButton("置中"),
							rbRight = new JRadioButton("靠右");

	ButtonGroup bgHAlign = new ButtonGroup();
	//按鈕群組

	//定義並宣告監聽器
	ActionListener al = new ActionListener(){

		public void actionPerformed(ActionEvent e){

			String command = e.getActionCommand();
			//取得觸發事件之元件的動作命令字串

			//判斷動作命令字串以設定標籤內文字的對齊方式
			if(command.equals("Left"))
				lbTitle.setHorizontalAlignment(SwingConstants.LEFT);
			else if(command.equals("Center"))
				lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
			else if(command.equals("Right"))
				lbTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		}	
	};

	RadioEX(){

		lbTitle.setVerticalAlignment(SwingConstants.CENTER);
		//設定標籤內文字的垂直對齊方式

		JPanel jpHAlign = 
			new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
			//建立放置核取方塊的JPanel

		jpHAlign.add(rbLeft); //將選擇鈕加入JPanel內
		jpHAlign.add(rbCenter);
		jpHAlign.add(rbRight);

		bgHAlign.add(rbLeft); //將選擇鈕加入ButtonGroup
		bgHAlign.add(rbCenter);
		bgHAlign.add(rbRight);

		Border border = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.gray, 2),
			"設定文字的水平對齊方式"
			, TitledBorder.LEFT, TitledBorder.TOP);
		//宣告標題框線物件, 標題文字將靠左靠上對齊, 顏色為淺灰, 寬度為2

		jpHAlign.setBorder(border); //設定JPanel使用的框線

		rbLeft.setActionCommand("Left"); //設定選擇鈕的動作命令字串
		rbCenter.setActionCommand("Center");
		rbRight.setActionCommand("Right");

		rbLeft.addActionListener(al); //註冊回應ActionEvent事件的監聽器
		rbCenter.addActionListener(al);
		rbRight.addActionListener(al);

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new GridLayout(2, 1)); //設定使用GridLayout管理版面
		cp.add(lbTitle); //將元件加入內容面版
		cp.add(jpHAlign);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}

	public static void main(String args[]) {
		new RadioEX(); //宣告視窗框架物件
	}
}