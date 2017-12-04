import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class AttributeEX extends JFrame {

	JTextPane tpStyle = new JTextPane(); //樣式文字面版

	String[] strInit = {
			"Java 2 視窗程式設計\n","\n",
			"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
			"而不是空泛地介紹各類別與方法, \n",
			"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
			"更可學習實際的應用, \n", 
			"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
	//樣式文字面版的內容

	JMenuItem miFirst = new JMenuItem("首行縮排"),
						miRight = new JMenuItem("右邊界縮排"),
						miLeft = new JMenuItem("左邊界縮排");
	//宣告控制段落縮排的選項

	JMenuItem mi12 = new JMenuItem("12"),
						mi14 = new JMenuItem("14"),
						mi16 = new JMenuItem("16");
	//宣告控制字體大小的選項

	JMenuItem 	miRed = new JMenuItem("紅色"),
						miGreen = new JMenuItem("綠色"),
						miBlue = new JMenuItem("藍色"),
						miBlack = new JMenuItem("黑色");
	//宣告控制前景顏色的選項

	String strSize = "字體大小 : ",
			   strColor = "前景顏色 : ";
	//顯示字體大小與前景顏色之標籤的預設字串

	JLabel lbFontSize = new JLabel(strSize),
				lbFontColor = new JLabel(strColor);
	//宣告顯示字體大小與前景顏色設定的標籤

	//定義實作CaretListener介面
	//回應CaretEvent事件的監聽器物件
	CaretListener cl = new CaretListener(){

		//回應游標位置的更新動作
		public void caretUpdate(CaretEvent e){

			AttributeSet asInput = tpStyle.getInputAttributes();
			//取得目前輸入文字使用的樣式屬性

			lbFontSize.setText(strSize + 
				asInput.getAttribute(StyleConstants.FontSize));
			//取得字體大小樣式屬性的設定值

			lbFontColor.setText(strColor + 
				asInput.getAttribute(StyleConstants.Foreground));
			//取得前景顏色樣式屬性的設定值
		}
	};

	AttributeEX(){

		tpStyle.addCaretListener(cl);
		//註冊回應CaretEvent事件的監聽器

		SimpleAttributeSet sasInput =
			(SimpleAttributeSet) tpStyle.getInputAttributes();
		//取得資料輸入使用的樣式

		StyleConstants.setFontSize(sasInput, 14);
		StyleConstants.setForeground(sasInput, Color.black);
		//設定內文樣式屬性的字型大小為14, 前景顏色為黑色

		//將文字內容新增至文字面版
		for(int i = (strInit.length - 1); i>=0; i--)
			tpStyle.replaceSelection(strInit[i]);
			//以置換的方式新增內容

		//宣告回應設定字體大小選項ActionEvent事件的監聽器物件
		ActionListener alFontSize = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				int size = Integer.valueOf(e.getActionCommand());
				//取得選項所指定的大小

				SimpleAttributeSet sas = new SimpleAttributeSet();
				//宣告樣式屬性物件

				StyleConstants.setFontSize(sas, size);
				//依照選項的動作命令字串設定樣式屬性的字體大小

				tpStyle.setCharacterAttributes(sas, false);
				//以樣式屬性增加字元的樣式設定

				lbFontSize.setText(strSize + size);
				//設定標籤顯示指定的字型大小
			}
		};

		mi12.addActionListener(alFontSize);
		mi14.addActionListener(alFontSize);
		mi16.addActionListener(alFontSize);
		//註冊回應字體大小設定之選項的監聽器物件

		//宣告監聽字體顏色的監聽器物件
		ActionListener alFontColor = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Color clFont = null;
				String strCommand = e.getActionCommand();
				//取得動作命令字串

				//依照動作命令字串設定欲設定的字元顏色
				if("紅色".equals(strCommand))
					clFont = Color.red;
				else if("綠色".equals(strCommand))
					clFont = Color.green;
				else if("藍色".equals(strCommand))
					clFont = Color.blue;
				else if("黑色".equals(strCommand))
					clFont = Color.black;

				SimpleAttributeSet sas = new SimpleAttributeSet();
				//宣告樣式屬性物件

				StyleConstants.setForeground(sas, clFont);
				//設定樣式屬性物件的顏色

				tpStyle.setCharacterAttributes(sas, false);
				//以樣式屬性增加字元的樣式設定

				lbFontColor.setText(strColor + clFont.toString());
				//設定標籤顯示前景顏色之值
			}
		};

		miRed.addActionListener(alFontColor);
		miGreen.addActionListener(alFontColor);
		miBlue.addActionListener(alFontColor);
		miBlack.addActionListener(alFontColor);
		//註冊監聽ActionEvent事件的監聽器物件

		//宣告監聽字體顏色的監聽器物件
		ActionListener alParapraph = new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Color clFont = null;
				String strCommand = e.getActionCommand();
				//取得動作命令字串

				float indent = 0.0f; //宣告欲設定之縮排空間的大小

				SimpleAttributeSet sas = new SimpleAttributeSet();
				//宣告樣式屬性物件

				//依照動作命令字串判斷欲設定的段落縮排
				if("首行縮排".equals(strCommand)){

					Object paraIndent =
						tpStyle.getParagraphAttributes().getAttribute(
							StyleConstants.FirstLineIndent);
					//取得首行縮排的原始設定

					float firstIndent = 
						(paraIndent == null ? 0.0f: (Float)paraIndent);
					//取得首行縮排設定的浮點數值

					Object objIndent = JOptionPane.showInputDialog(
						AttributeEX.this, "設定首行縮排大小", "段落縮排",
						JOptionPane.INFORMATION_MESSAGE,
						null, null, firstIndent);
					//取得使用者輸入之首行縮排大小的設定值

					indent = (objIndent == null ? 0.0f:
									Float.valueOf((String) objIndent));
					//將設定值轉換為浮點數

					StyleConstants.setFirstLineIndent(sas, indent);
					//設定樣式屬性物件的首行縮排大小
				}
				else if("左邊界縮排".equals(strCommand)){

					Object paraIndent = 
						tpStyle.getParagraphAttributes().getAttribute(
							StyleConstants.LeftIndent);
					//取得左邊界縮排的原始設定

					float leftIndent =	
						(paraIndent == null ? 0: (Float)paraIndent);
					//取得左邊界縮排設定的浮點數值

					Object objIndent = JOptionPane.showInputDialog(
						AttributeEX.this, "設定左邊界縮排大小", "段落縮排",
						JOptionPane.INFORMATION_MESSAGE,
						null, null, leftIndent);
					//取得使用者輸入之左邊界縮排大小的設定值

					indent = (objIndent == null ? 0.0f :
									Float.valueOf((String) objIndent));
					//將設定值轉換為浮點數

					StyleConstants.setLeftIndent(sas, indent);
					//設定樣式屬性物件的左邊界縮排大小			
				}
				else if("右邊界縮排".equals(strCommand)){

					Object paraIndent =
						tpStyle.getParagraphAttributes().getAttribute(
							StyleConstants.RightIndent);
					//取得右邊界縮排的原始設定

					float rightIndent = 
						(paraIndent == null ? 0: (Float) paraIndent);
					//取得右邊界縮排設定的浮點數值

					Object objIndent = JOptionPane.showInputDialog(
						AttributeEX.this, "設定右邊界縮排大小", "段落縮排",
						JOptionPane.INFORMATION_MESSAGE,
						null, null, rightIndent);
					//取得使用者輸入之右邊界縮排大小的設定值

					indent = (objIndent == null ? 0.0f :
									Float.valueOf((String) objIndent));
					//將設定值轉換為浮點數

					StyleConstants.setRightIndent(sas, indent);
					//設定樣式屬性物件的右邊界縮排大小				
				}

				tpStyle.setParagraphAttributes(sas, false);
				//以樣式屬性增加字元的樣式設定
			}
		};

		miFirst.addActionListener(alParapraph);
		miRight.addActionListener(alParapraph);
		miLeft.addActionListener(alParapraph);
		//註冊回應字體大小設定之選項的監聽器物件

		//宣告並建立控制字體大小的功能表,
		//並加入選項
		JMenu mnFontSize = new JMenu("字體大小");
		mnFontSize.add(mi12);
		mnFontSize.add(mi14);
		mnFontSize.add(mi16);

		//宣告並建立控制前景顏色的功能表,
		//並加入選項
		JMenu mnFontColor = new JMenu("顏色");
		mnFontColor.add(miRed);
		mnFontColor.add(miGreen);
		mnFontColor.add(miBlue);
		mnFontColor.add(miBlack);

		//宣告並建立控制段落縮排的功能表,
		//並加入選項
		JMenu mnParapraph = new JMenu("段落");
		mnParapraph.add(miFirst);
		mnParapraph.add(miRight);
		mnParapraph.add(miLeft);

		//宣告功能表列, 並加入功能表
		JMenuBar mb = new JMenuBar();
		mb.add(mnFontSize);
		mb.add(mnFontColor);
		mb.add(mnParapraph);

		setJMenuBar(mb); //設定功能表列

		//將容納顯示字型大小與前景顏色標籤的Box容器
		Box bxMsg = new Box(BoxLayout.Y_AXIS);
		bxMsg.add(lbFontSize);
		bxMsg.add(lbFontColor);

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
		new AttributeEX(); //宣告視窗框架物件
	}
}