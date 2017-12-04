import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class CardLayoutEX extends JFrame {

	JLabel[] lbImage = { //宣告顯示圖片的標籤物件
		new JLabel( new ImageIcon(
			CardLayoutEX.class.getResource("images/A4023.jpg"),
			"Access 2003 徹底研究")),
		new JLabel( new ImageIcon(
			CardLayoutEX.class.getResource("images/A4033.jpg"),
			"Access 2003 程式設計")),
		new JLabel( new ImageIcon(
			CardLayoutEX.class.getResource("images/A4043.jpg"),
			"Access 2003 網路應用")),
		new JLabel( new ImageIcon(
			CardLayoutEX.class.getResource("images/W4075.jpg"),
			"ASP動態網頁入門實務")),
		new JLabel( new ImageIcon(
			CardLayoutEX.class.getResource("images/W3135.jpg"),
			"JSP動態網頁入門實務")) };

	JButton btnPre = new JButton("上一頁"), //宣告控制CardLayout面版顯示內容的按鈕
		btnNext = new JButton("下一頁"),
		btnFirst = new JButton("第一頁"),
		btnLast = new JButton("最後一頁"),
		btnName = new JButton("ASP動態網頁入門實務");

	ActionListener alBtn = new ActionListener(){ //定義監聽ActionEvent事件的監聽器類別
		public void actionPerformed(ActionEvent e){

			String strCommand = e.getActionCommand(); //取得動作命令字串

			if(e.getActionCommand().equals("上一頁"))
				cl.previous(jpImage); //移至上一張卡片
			else if(e.getActionCommand().equals("下一頁"))
				cl.next(jpImage); //移至下一張卡片
			else if(e.getActionCommand().equals("第一頁"))
				cl.first(jpImage); //移至第一張卡片
			else if(e.getActionCommand().equals("最後一頁"))
				cl.last(jpImage); //移至最後一張卡片
			else if(e.getActionCommand().equals("ASP動態網頁入門實務"))
				cl.show(jpImage, "ASP動態網頁入門實務");
				//移至描述內容為"ASP動態網頁入門實務"的卡片
		}
	};

	CardLayout cl = new CardLayout(10, 5);
	//宣告配置版面的CardLayout物件

	JPanel jpImage = new JPanel(cl); //宣告容納圖片的JPanel容器

	CardLayoutEX(){

		//以for迴圈將包含圖片的標籤元件與敘述加入容器
		for(JLabel elm: lbImage)
			jpImage.add(elm
				, ((ImageIcon)elm.getIcon()).getDescription());

		JPanel jpBtn = new JPanel(new GridLayout(5, 1, 20, 10));
		//宣告JPanel物件並設定以GridLayout配置版面

		jpBtn.add(btnFirst); //將按鈕元件加入JPanel容器
		jpBtn.add(btnPre);
		jpBtn.add(btnNext);
		jpBtn.add(btnLast);
		jpBtn.add(btnName);

		//註冊處理滑鼠事件的監聽器
		btnFirst.addActionListener(alBtn);
		btnPre.addActionListener(alBtn);
		btnNext.addActionListener(alBtn);
		btnLast.addActionListener(alBtn);
		btnName.addActionListener(alBtn);

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new FlowLayout()); //設定使用FlowLayout配置版面
		cp.add(jpBtn); //將元件加入內容面版
		cp.add(jpImage);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(450, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new CardLayoutEX(); //宣告視窗框架物件
	}
}