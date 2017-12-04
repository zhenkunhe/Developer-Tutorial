import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonMenuEX extends JFrame {

	public ButtonMenuEX(){

		MenuButton mb = new MenuButton("測試"); //宣告MenuButton物件

		JPopupMenu pm  = mb.getPopupMenu();
		//取得MenuButton物件使用的蹦現功能表

		pm.add(new JMenuItem("選項一")); //將選項加入蹦現功能表
		pm.add(new JMenuItem("選項二"));
		pm.add(new JMenuItem("選項三"));

		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());	
		cp.add(mb); //將MenuButton物件加入容器

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String... args){
		new ButtonMenuEX();
	}
}

//繼承JComponent類別定義擁有蹦現功能表的功能表按鈕元件類別
class MenuButton extends JComponent {

	final static int BTNRIGHT_WIDTH = 20;
	//右方按鈕的寬度

	private JButton btnLeft = new JButton(), 
							  btnRight = new JButton("▼");
	//宣告左右兩邊的指令按鈕

	private JPopupMenu pm = new JPopupMenu();
	//宣告蹦現功能表

	public MenuButton(String text){ //建構子

		btnLeft.setText(text); //設定btnLeft按鈕顯示的字串

		btnRight.setBorder(new CompoundBorder(
			((CompoundBorder)btnRight.getBorder()).getOutsideBorder(),
			new EmptyBorder(0, 0 ,0 ,0)));
		//設定btnRight按鈕使用的框線, 
		//新的合成框線將取消按鈕內文字與四週框線的空白區域

		//註冊回應右邊按鈕ActionEvent事件的監聽器物件,
		btnRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pm.show(btnLeft, 0, btnLeft.getSize().height);
				//在btnLeft按鈕下方顯示蹦現功能表
			}
		});

		SpringLayout sl = new SpringLayout(); //宣告佈局管理員
		setLayout(sl); //設定配置版面的佈局管理員
		add(btnLeft); //加入btnLeft按鈕

		SpringLayout.Constraints consBtnLeft = sl.getConstraints(btnLeft);
		//取得控制btnLeft按鈕配置的SpringLayout.Constraints物件

		add(btnRight, 
			new SpringLayout.Constraints(
				consBtnLeft.getConstraint(SpringLayout.EAST),
				Spring.constant(0),
				Spring.constant(BTNRIGHT_WIDTH ), 
				consBtnLeft.getHeight()));
		//加入btnRight按鈕,
		//並同時傳入控制配置的SpringLayout.Constraints物件

		setPreferredSize(new Dimension(
			(new Double(btnLeft.getPreferredSize().getWidth()
													+ BTNRIGHT_WIDTH).intValue()),
			(new Double(btnLeft.getPreferredSize().getHeight()).intValue())));
		//設定整個元件的偏好大小
	}

	//設定蹦現功能表
	public void setPopupMenu(JPopupMenu pm){
		this.pm = pm;
	}

	//傳出蹦現功能表
	public JPopupMenu getPopupMenu(){
		return pm;
	}

	//設定左邊按鈕
	public void setLeftButton(JButton btn){
		this.btnLeft = btn;
	}

	//傳出左邊按鈕
	public JButton getLeftButton(){
		return btnLeft;
	}
}