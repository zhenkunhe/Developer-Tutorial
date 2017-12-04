import javax.swing.*;  //引用套件
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LabelFieldEX extends JFrame{

	JLabel lbName = new JLabel("帳號(N) : ", JLabel.RIGHT);	
	JLabel lbPW = new JLabel("密碼(P) : ", JLabel.RIGHT);	
	JTextField tfName = new JTextField(20);
	JPasswordField pfPW = new JPasswordField(20);

	JLabel lbEnter = new JLabel("[密碼]欄按下 Enter 取得的資料 : "),
				 lbCaret = new JLabel("[帳號]欄游標的位置 : ");

	//定義實作CaretListener介面的監聽器類別
	class FieldCaretListener implements CaretListener {
		public void caretUpdate(CaretEvent e){ //回應事件的方法
			if( e.getDot() == e.getMark()){
				//判斷getDot()方法與getMark()方法的取得值是否相同

				lbCaret.setText("[帳號]欄游標的位置 : " + e.getDot());
				//取得游標目前的位置
			}
			else
				lbCaret.setText("[帳號]欄游標的選取範圍 :  [" 
								+ e.getDot() + "至" + e.getMark() + "]");
				//選取範圍的結尾位置
		}
	}

	FieldCaretListener fcl =  new FieldCaretListener();
	//宣告監聽CaretEvent事件的監聽器物件

	//以匿名內部類別的方式定義並宣告監聽器物件
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			JPasswordField source = (JPasswordField) e.getSource();
			//取得事件來源物件

			//回應ActionEvent事件時, 更新lbEnter標籤的內容
			lbEnter.setText("[密碼]欄按下 Enter 取得的資料 : [" 
			 + new String(pfPW.getPassword()) + "]");
		}
	};

	LabelFieldEX(){

		lbName.setDisplayedMnemonic('N');
		//設定使用N搭配Alt鍵做為助憶鍵
		lbName.setLabelFor(tfName);
		//設定lbName標籤為tfName文字欄位的名稱

		lbPW.setDisplayedMnemonic('P');
		//設定使用P搭配Alt鍵做為記憶鍵
		lbPW.setLabelFor(pfPW); //設定lbPW標籤為tfPW文字欄位的名稱

		pfPW.setEchoChar('@'); //設定密碼欄使用的遮罩字元

		tfName.addCaretListener(fcl); //註冊CaretEvent事件的監聽器
		pfPW.addActionListener(al);
		//註冊回應ActionEvent事件的監聽器

		JPanel jpCenter = new JPanel(new GridLayout(2, 2,  5, 5));
		jpCenter.add(lbName); //將元件加入JPanel子容器
		jpCenter.add(tfName);
		jpCenter.add(lbPW);
		jpCenter.add(pfPW);

		JPanel jpLabel = new JPanel(new GridLayout(2, 1, 5, 5));
		jpLabel.add(lbCaret);
		jpLabel.add(lbEnter);

		Container cp = getContentPane(); //取得內容面版

		BorderLayout bl = (BorderLayout)cp.getLayout();
		//取得佈局管理員
		bl.setVgap(10); //設定垂直間距為10

		cp.add(jpCenter); //將元件加入面版
		cp.add(jpLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式

		setSize(300, 150); //設定視窗框架大小
		setVisible(true); //顯示視窗框架
	}

	public static void main(String args[]) {
		new LabelFieldEX(); //宣告視窗框架物件
	}
}