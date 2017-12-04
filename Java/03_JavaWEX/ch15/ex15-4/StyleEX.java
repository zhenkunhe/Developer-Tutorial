import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class StyleEX extends JFrame {

	JTextPane tpStyle = new JTextPane(); //樣式文字面版

	String[] strInit = {
			"Java 2 視窗程式設計\n","\n",
			"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
			"而不是空泛地介紹各類別與方法, \n",
			"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
			"更可學習實際的應用, \n", 
			"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
	//樣式文字面版的內容

	Style slContent = tpStyle.addStyle("內文", null),
			  slHeading = tpStyle.addStyle("標題", slContent);
	//建立代表內文與標題樣式的Style物件

	JRadioButtonMenuItem 
		rbmiHeading = new JRadioButtonMenuItem("標題"),
		rbmiContent = new JRadioButtonMenuItem("內文"),
		rbmiNone = new JRadioButtonMenuItem("無樣式", true);
	//宣告控制段落樣式的選擇鈕選項

	JLabel lbStyle = new JLabel();
	//顯示段落套用之邏輯樣式名稱的標籤

	//定義實作CaretListener介面
	//回應CaretEvent事件的監聽器物件
	CaretListener cl = new CaretListener(){

		//回應游標位置的更新動作
		public void caretUpdate(CaretEvent e){			

			Style slPara = tpStyle.getLogicalStyle();
			//取得目前段落套用的邏輯樣式

			//比對目前段落使用的邏輯樣式,
			//並設定對應的選項為選取
			if(slPara == slContent)
				rbmiContent.setSelected(true);
			else if(slPara == slHeading)
				rbmiHeading.setSelected(true);
			else
				rbmiNone.setSelected(true);

			lbStyle.setText(
				slPara == null? "無樣式": slPara.getName());
			//設定標籤顯示段落套用的樣式
		}
	};

	StyleEX(){

		StyleConstants.setSpaceAbove(slContent, 3);
		StyleConstants.setSpaceBelow(slContent, 3);
		StyleConstants.setFontSize(slContent, 12);
		StyleConstants.setLeftIndent(slContent, 5.0f);
		//定義slContent樣式的樣式屬性
		
		StyleConstants.setBold(slHeading, true);
		StyleConstants.setFontSize(slHeading, 16);
		StyleConstants.setSpaceAbove(slHeading, 10);
		StyleConstants.setForeground(slHeading, Color.red);
		StyleConstants.setLeftIndent(slHeading, 0.0f);
		//定義slHeading樣式的樣式屬性

		tpStyle.addCaretListener(cl);
		//註冊回應CaretEvent事件的監聽器

		//將文字內容套用指定的邏輯樣式新增至文字面版
		for(int i = (strInit.length-1); i>=0; i--){
			tpStyle.replaceSelection(strInit[i]);
			tpStyle.setLogicalStyle(slContent);
		}

		//宣告監聽段落樣式的監聽器物件
		ActionListener alStyle = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String command = e.getActionCommand();
				//取得動作命令字串

				if("無樣式".equals(command)){
					tpStyle.setLogicalStyle(null);
					//設定文字段落不套用任何邏輯樣式
				}
				else{
					tpStyle.setLogicalStyle(
						tpStyle.getStyle(command));
					//以動作命令字串取得段落
					//欲套用邏輯樣式的Style物件,
					//並設定為文字段落欲套用的樣式
				}

				lbStyle.setText(command);
				//顯示段落套用的邏輯樣式名稱
			}
		};

		rbmiHeading.addActionListener(alStyle);
		rbmiContent.addActionListener(alStyle);
		rbmiNone.addActionListener(alStyle);
		//註冊監聽ActionEvent事件的監聽器物件

		//宣告並建立控制段落樣式的功能表,
		//並加入選擇鈕選項
		JMenu mnStyle = new JMenu("樣式");
		mnStyle.add(rbmiHeading);
		mnStyle.add(rbmiContent);
		mnStyle.add(rbmiNone);

		//宣告按鈕群組, 並加入設定段落樣式的選擇鈕選項
		ButtonGroup bgStyle = new ButtonGroup();
		bgStyle.add(rbmiHeading);
		bgStyle.add(rbmiContent);
		bgStyle.add(rbmiNone);

		Box bxMsg = new Box(BoxLayout.X_AXIS);
		bxMsg.add(new JLabel("段落樣式 : "));
		bxMsg.add(lbStyle);

		//宣告功能表列, 並加入功能表
		JMenuBar mb = new JMenuBar();
		mb.add(mnStyle);

		setJMenuBar(mb); //設定功能表列

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5,  5));
		//設定使用間隔寬度為5的BorderLayout物件配置版面

		cp.add(new JScrollPane(tpStyle));
		cp.add(bxMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new StyleEX(); //宣告視窗框架物件
	}
}