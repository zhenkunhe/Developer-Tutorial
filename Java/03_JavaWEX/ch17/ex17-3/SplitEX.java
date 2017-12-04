import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class SplitEX extends JFrame {

	JLabel lbImage;	 //顯示圖片的畫布

	JScrollPane
		spTop = new JScrollPane(
				new JLabel(new ImageIcon("res\\butterfly.jpg"))),
		spBottom = new JScrollPane(
				new JLabel(new ImageIcon("res\\butterfly.jpg")));
	//宣告顯示圖片的捲軸面版

	JSplitPane sltPane = new JSplitPane(
		JSplitPane.VERTICAL_SPLIT, spTop, spBottom);
	//宣告分割面版

	SplitEX(){

		spTop.setMinimumSize(new Dimension(100, 100));
		//設定左邊顯示之捲軸面版的最小尺寸

		sltPane.setDividerSize(10); //設定分割器的寬度
		sltPane.setDividerLocation(150); //設定分割器的位置
		sltPane.setOneTouchExpandable(true);
		//設定支援單鍵展開

		//宣告回應設定面版分割方式選擇鈕
		//ActionEvent事件的監聽器物件
		ActionListener alSplit = new ActionListener(){

			//回應ActionEvent事件的方法
			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//取得動作命令字串

				//判斷動作命令字串並設定執行對應的分割方式
				if("垂直分割".equals(strAction))
					sltPane.setOrientation(
						JSplitPane.VERTICAL_SPLIT);
				else if("水平分割".equals(strAction))
					sltPane.setOrientation(
						JSplitPane.HORIZONTAL_SPLIT);
			}
		};

		JRadioButton rbVer = new JRadioButton("垂直分割", true),
								rbHor = new JRadioButton("水平分割");
		//宣告控制面版分割方式的選擇鈕

		rbVer.addActionListener(alSplit);
		rbHor.addActionListener(alSplit);
		//註冊監聽選擇鈕ActionEvent事件的監聽器物件

		//將控制面版分割方式的選擇鈕宣告為同一個按鈕群組
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbVer);
		bg.add(rbHor);

		Box bxSplit = new Box(BoxLayout.Y_AXIS);
		bxSplit.add(Box.createVerticalStrut(5));
		bxSplit.add(rbVer);
		bxSplit.add(Box.createVerticalStrut(5));
		bxSplit.add(rbHor);
		//將選擇鈕加入Box容器內

		JButton btnReset = new JButton("重設");
		//宣告指令按鈕

		//註冊回應ActionEvent事件的監聽器物件
		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sltPane.resetToPreferredSizes();
				//重設分割面版的尺寸
			}
		});

		Container cp = getContentPane(); //取得內容面版
		cp.add(sltPane); //將面版加入內容面版
		cp.add(bxSplit, BorderLayout.EAST);
		cp.add(btnReset, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(415, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new SplitEX(); //宣告視窗框架物件
	}
}