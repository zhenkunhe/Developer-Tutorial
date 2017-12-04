import javax.swing.*;  //引用套件
import java.awt.*;

public class SeparatorEX extends JFrame{

	SeparatorEX(){

		JLabel lbLeft = 
						new JLabel("位元文化",  JLabel.CENTER),
					lbRight= 
						new JLabel("位元文化",  JLabel.CENTER),
					lbTop = 
						new JLabel("位元文化",  JLabel.CENTER),
					lbBottom = 
						new JLabel("位元文化",  JLabel.CENTER)	;
		//宣告標籤

		JSeparator sprHorizontal = new JSeparator(); //宣告水平分隔線
		Box bxVertical = new Box(BoxLayout.Y_AXIS);

		bxVertical.add(lbTop);
		bxVertical.add(sprHorizontal); //加入水平分隔線
		bxVertical.add(lbBottom);

		JSeparator sprVertical = new JSeparator(JSeparator.VERTICAL);
		 //宣告垂直分隔線

		Box bxHorizontal = new Box(BoxLayout.X_AXIS);

		bxHorizontal.add(lbLeft);
		bxHorizontal.add(sprVertical); //加入垂直分隔線
		bxHorizontal.add(lbRight);

		/**** 建立第一種樣式的分隔線 ***/		
		JSeparator sprSytle01 = new JSeparator(JSeparator.HORIZONTAL);
		//宣告水平分隔線

		Dimension dimStyle01 = sprSytle01.getPreferredSize();
		//取得水平分隔線大小

		sprSytle01.setPreferredSize(
							new Dimension(100, dimStyle01.height));
		//設定水平分隔線的寬度為100
	
		JPanel jpStyle01 = new JPanel();
		jpStyle01.add(new JLabel("樣式一"));
		jpStyle01.add(sprSytle01);

		/**** 建立第二種樣式的分隔線 ***/		
		JSeparator sprSytle02 = new JSeparator();
		Dimension dimStyle02 = sprSytle02.getPreferredSize();
		sprSytle02.setPreferredSize(
							new Dimension(100, dimStyle02.height));
	
		JPanel jpStyle02 = new JPanel(new GridLayout(2, 1));

		jpStyle02.add(new JLabel("樣式二"));
		jpStyle02.add(sprSytle02);

		/**** 建立第三種樣式的分隔線 ***/		
		JLabel lbStyle03 = new JLabel("樣式三");
		JSeparator sprSytle03 = new JSeparator(JSeparator.VERTICAL);
		//宣告垂直分隔線

		Dimension dimStyle03 = sprSytle03.getPreferredSize();
		sprSytle03.setPreferredSize(
						new Dimension(dimStyle03.width, 100));
		//設定分隔線的寬度
	
		Box bxStyle03 = new Box(BoxLayout.X_AXIS);
		bxStyle03.add(Box.createHorizontalStrut(
							lbStyle03.getPreferredSize().width / 2));
		//建立寬度為標籤寬度一半的支架

		bxStyle03.add(sprSytle03);

		JPanel jpStyle03 = new JPanel(new BorderLayout());

		jpStyle03.add(new JLabel("樣式三", JLabel.LEFT), 
								BorderLayout.NORTH);
		jpStyle03.add(bxStyle03);

		JPanel jpTop = new JPanel();
		jpTop.add(bxVertical);
		jpTop.add(bxHorizontal);

		JPanel jpBottom = new JPanel();
		jpBottom.add(jpStyle01);
		jpBottom.add(jpStyle02);
		jpBottom.add(jpStyle03);

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		//設定內容面版的佈局管理員物件

		cp.add(jpTop);
		cp.add(jpBottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式

		pack(); //以最適大小顯示視窗
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new SeparatorEX(); //宣告視窗框架物件
	}
}