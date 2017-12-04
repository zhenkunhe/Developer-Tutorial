import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class ScrollEX extends JFrame {

	private JLabel
		lbImage = new JLabel(new ImageIcon("res\\butterfly.jpg"));
	//顯示圖片的畫布

	JLabel 
		lbHor = new JLabel("欄標頭", JLabel.CENTER),
		lbVer = new JLabel("列標頭", JLabel.CENTER);

	JLabel lbUL = new JLabel(
					new ImageIcon("res\\UL.gif"), JLabel.RIGHT),
				lbLR = new JLabel(new ImageIcon("res\\LR.gif")),
				lbLL = new JLabel(
					new ImageIcon("res\\LL.gif"), JLabel.RIGHT),
				lbUR = new JLabel(new ImageIcon("res\\UR.gif"));

	JPanel jpImage = new JPanel();		
	JScrollPane spImage = new JScrollPane(jpImage);
	//將面版加入捲軸面版

	JScrollBar sbHor = spImage.getHorizontalScrollBar(),
						sbVer = spImage.getVerticalScrollBar();
	//取得捲軸面版的捲軸列

	ScrollEX(){

		//宣告、註冊回應ActionEvent事件的監聽器物件
		lbUL.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				sbHor.setValue(0);
				sbVer.setValue(0);
				//將捲動軸移動至左上角
			}
		});

		//宣告、註冊回應ActionEvent事件的監聽器物件
		lbUR.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				sbHor.setValue(sbHor.getMaximum());
				sbVer.setValue(0);
				//將捲動軸移動至右上角
			}
		});

		//宣告、註冊回應ActionEvent事件的監聽器物件
		lbLR.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				sbHor.setValue(sbHor.getMaximum());
				sbVer.setValue(sbVer.getMaximum());
				//將捲動軸移動至右下角
			}
		});

		//宣告、註冊回應ActionEvent事件的監聽器物件
		lbLL.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				sbHor.setValue(0);
				sbVer.setValue(sbVer.getMaximum());
				//將捲動軸移動至左下角
			}
		});

		jpImage.add(lbImage); //將標籤加入面版

		spImage.setHorizontalScrollBarPolicy(
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spImage.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//設定捲軸面版顯示捲軸的方式

		spImage.setRowHeaderView(lbHor);
		spImage.setColumnHeaderView(lbVer);
		//設定捲軸面版的列標頭與欄標頭

		spImage.setViewportBorder(
			BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		//設定檢視區的框線
	
		spImage.setCorner(
			JScrollPane.UPPER_LEFT_CORNER, lbUL);
		spImage.setCorner(
			JScrollPane.UPPER_RIGHT_CORNER, lbUR);
		spImage.setCorner(
			JScrollPane.LOWER_LEFT_CORNER, lbLL);
		spImage.setCorner(
			JScrollPane.LOWER_RIGHT_CORNER, lbLR);
		//設定捲軸面版四個角落使用調整檢視範圍的指令按鈕

		Container cp = getContentPane(); //取得內容面版
		cp.add(spImage); //將面版加入內容面版

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ScrollEX(); //宣告視窗框架物件
	}
}