import javax.swing.*;
import javax.swing.text.*; //引用包含Document介面的套件
import javax.swing.event.*; //引用包含CaretListener介面的套件
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class TextAreaEX extends JFrame{

	JTextArea taText = new JTextArea(5, 30); //宣告文字區物件, 並定義為5列30行
	JScrollPane spText = new JScrollPane(taText); //以文字區物件建立捲軸面版
	JLabel lbText = new JLabel("文字區 :");
	JLabel lbTextPos = new JLabel("顯示游標位置");
	JLabel lbDocAct = new JLabel("顯示文字區內容的編輯動作");

	//定義實作CaretListener介面回應CaretEvent事件的監聽器
	CaretListener cl = new CaretListener(){
		public void caretUpdate(CaretEvent e){			
			lbTextPos.setText("文字區游標位置 : " + e.getDot());

			if(e.getDot() != e.getMark()) //判斷是否執行範圍選取
				lbTextPos.setText(lbTextPos.getText() 
					+ "  選取範圍從 " + e.getDot() + "至" + e.getMark());
		}
	};

	TextAreaEX(){
		taText.setLineWrap(true); //設定文字區自動斷行
		taText.setFont(new Font("Times-Roman", Font.BOLD, 15)); //設定使用的字型

		taText.addCaretListener(cl); //註冊回應CaretEvent事件的監聽器

		Document doc = taText.getDocument(); //取得文字區的Document物件

		//註冊Document物件之事件的監聽器, 以回應文字內容的新增與刪除動作,
		//並示範在呼叫addDocumentListener()時, 定義監聽器類別
		doc.addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e) { //資料新增動作
				lbDocAct.setText("資料新增至文字區");
			}
			public void removeUpdate(DocumentEvent e) { //資料刪除動作
				lbDocAct.setText("移除文字區內容");
			}
			public void changedUpdate(DocumentEvent e) { }
		});

		//建立包含顯示文字區狀態之標籤的Box容器
		Box bxShow = new Box(BoxLayout.Y_AXIS);
		bxShow.add(lbTextPos);
		bxShow.add(lbDocAct);

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new FlowLayout(FlowLayout.LEFT)); //設定使用FlowLayout配置

		cp.add(lbText); //將元件加入面版
		cp.add(spText);
		cp.add(bxShow);

		//設定視窗關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 230);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TextAreaEX(); //建立視窗框架
	}
}