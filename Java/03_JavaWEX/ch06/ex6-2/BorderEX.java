import javax.swing.*;  //引用套件
import java.awt.*;

import javax.swing.border.*; //定義框線類別的套件

public class BorderEX extends JFrame{

	BorderEX(){

		BevelBorder bevelraised = new BevelBorder(BevelBorder.RAISED),
			bevellowered = (BevelBorder)BorderFactory.createBevelBorder(
										BevelBorder.LOWERED, Color.CYAN, Color.BLUE);
		//宣告斜角框線

		JLabel lbBevelRaised = new JLabel(
														"斜角(Bevel) : 凸起", JLabel.CENTER),
					lbBevelLowered = new JLabel(
														"斜角(Bevel) : 凹下", JLabel.CENTER);

		lbBevelRaised.setBorder(bevelraised); //設定標籤使用斜角框線
		lbBevelLowered.setBorder(bevellowered);

		JPanel jpBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpBevel.add(lbBevelRaised);
		jpBevel.add(lbBevelLowered);

		SoftBevelBorder softbevelraised = new SoftBevelBorder(
										BevelBorder.RAISED),
									 softbevellowered = new SoftBevelBorder(
										BevelBorder.LOWERED, Color.CYAN, Color.BLUE);
		//宣告柔角框線

		JLabel	lbSoftBevelRaised = new JLabel(
									"柔角(Soft Bevel) : 凸起", JLabel.CENTER),
					lbSoftBevelLowered = new JLabel(
									"柔角(Soft Bevel) : 凹下", JLabel.CENTER);

		lbSoftBevelRaised.setBorder(softbevelraised); //設定標籤使用柔角框線
		lbSoftBevelLowered.setBorder(softbevellowered);

		JPanel jpSoftBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpSoftBevel.add(lbSoftBevelRaised);
		jpSoftBevel.add(lbSoftBevelLowered);

		EtchedBorder etchedborderraised = new EtchedBorder(
					EtchedBorder.RAISED, Color.gray, Color.DARK_GRAY),
			etchedborderlowered = 
					(EtchedBorder) BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, Color.gray, Color.DARK_GRAY);
		//宣告刻痕框線

		JLabel lbEtchedRaised = new JLabel(
														"刻痕(Etched) : 凸起", JLabel.CENTER),
					lbEtchedLowered = new JLabel(
														"刻痕(Etched) : 凹下", JLabel.CENTER);

		lbEtchedRaised.setBorder(etchedborderraised); //設定使用刻痕框線
		lbEtchedLowered.setBorder(etchedborderlowered);

		JPanel jpEtchedBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpEtchedBevel.add(lbEtchedRaised);
		jpEtchedBevel.add(lbEtchedLowered);
		
		LineBorder lineborderround = new LineBorder(
																Color.DARK_GRAY, 5, true),
			linebordersquare = (LineBorder)BorderFactory.createLineBorder(
																Color.DARK_GRAY, 3);
		//宣告線條框線

		JLabel lbLineSquare = new JLabel(
												"線條(Line) : 方角, 寬 5 像素", JLabel.CENTER),
					lbLineRound = new JLabel(
												"線條(Line) : 圓角, 寬 3 像素", JLabel.CENTER);

		lbLineSquare.setBorder(linebordersquare); //設定使用線條框線
		lbLineRound.setBorder(lineborderround);

		JPanel jpLineBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpLineBevel.add(lbLineSquare);
		jpLineBevel.add(lbLineRound);

		MatteBorder matteborderimage = 
					new MatteBorder(20, 20, 20, 20, new ImageIcon(".\\Icon\\Bitc.gif")),
				mattebordercolor = (MatteBorder) BorderFactory.createMatteBorder(
					20, 20, 20, 20, Color.GRAY);
		//宣告襯邊框線

		JLabel	lbMatteImage = new JLabel("襯邊(Matte) : 圖示", JLabel.CENTER),
					lbMatteColor = new JLabel("襯邊(Matte) : 顏色", JLabel.CENTER);

		lbMatteImage.setBorder(matteborderimage); //設定襯底框線
		lbMatteColor.setBorder(mattebordercolor);

		JPanel jpMatteBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpMatteBevel.add(lbMatteImage);
		jpMatteBevel.add(lbMatteColor);

		EmptyBorder empty = new EmptyBorder(5, 5, 5, 5);
		//宣告空白框線

		JLabel lbEmpty = new JLabel("空白(Empty)", JLabel.CENTER);

		lbEmpty.setBorder(empty); //設定使用空白框線

		JLabel	lbCompound = 
			new JLabel("合成(Compound)", JLabel.CENTER);

		lbCompound.setBorder(BorderFactory.createCompoundBorder(
				new CompoundBorder(new EtchedBorder(), 
														new EmptyBorder(5, 5, 5, 5)),
				new MatteBorder(10, 10, 10, 10, Color.red)));
		//以BorderFactory類別的靜態方法建立合成框線
		//將以蝕刻框線、空白框線與襯邊框線組合出新的框線

		JPanel jpOtherBevel = new JPanel(new GridLayout(1, 2, 10, 10));
		jpOtherBevel.add(lbEmpty);
		jpOtherBevel.add(lbCompound);

		JPanel jpMain =  new JPanel(new GridLayout(6, 1, 10, 10));
		jpMain.add(jpBevel); //加入容納各標籤的面版容器
		jpMain.add(jpSoftBevel);
		jpMain.add(jpEtchedBevel);
		jpMain.add(jpLineBevel);
		jpMain.add(jpMatteBevel);
		jpMain.add(jpOtherBevel);

		Box bxTop = new Box(BoxLayout.Y_AXIS);
		bxTop.add(Box.createVerticalStrut(10));

		bxTop.add(jpMain); //將主面版加入內容面版

		bxTop.add(Box.createVerticalStrut(10));

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		//設定內容面版的佈局管理員物件

		cp.add(Box.createHorizontalStrut(10));
		cp.add(bxTop);
		cp.add(Box.createHorizontalStrut(10));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式

		pack(); //以最適大小顯示視窗
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new BorderEX(); //宣告視窗框架物件
	}
}