import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.*;
//包含處理文字面版內容相關介面與類別的套件

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class DocumentEX extends JFrame {

	JTextComponent tcContent; //顯示文字內容的文字元件
	JTextArea taMsg = new JTextArea(); //顯示訊息的文字區
	JTree trDoc; //顯示文件結構的樹狀結構

	//定義實作CaretListener介面
	//回應CaretEvent事件的監聽器物件
	CaretListener cl = new CaretListener(){

		//回應游標位置的更新動作
		public void caretUpdate(CaretEvent e){			

			int dot = e.getDot(), mark = e.getMark();
			//取得游標位置與選取範圍的結尾位置

			if(dot == mark){ //判斷是否執行範圍選取
				taMsg.append("游標位置 : " + dot + "\n");
				//顯示游標位置
			}
			else{
				//若游標位置大於選取範圍結尾,
				//則兩者的數值互換
				if(dot > mark){
					int temp = mark;
					mark = dot;
					dot = temp;
				}

				taMsg.append("選取範圍 : [ " + dot + " 至 " + mark + " ]\n");
				//顯示選取範圍資訊
			}
		}
	};

	//定義實作DocumentListener介面
	//回應DocumentEvent事件的監聽器物件
	DocumentListener dl = new DocumentListener(){

		Document document;

		//回應新增內容動作
		public void insertUpdate(DocumentEvent e) {
			document = (Document)e.getDocument();
			//取得處理文字元件內容的Document物件

			displayEditInfo(e); //顯示DocumentEvent事件的資訊
			trDoc.updateUI(); //更新文件內容樹狀結構的顯示
		}

		//回應刪除內容動作
		public void removeUpdate(DocumentEvent e) {
			document = (Document)e.getDocument();
			displayEditInfo(e);
			trDoc.updateUI();
		}

		//回應內容設定更新動作
		public void changedUpdate(DocumentEvent e) {
			document = (Document)e.getDocument();
			displayEditInfo(e);
			trDoc.updateUI();
		}

		//顯示DocumentEvent事件的資訊
		private void displayEditInfo(DocumentEvent e) {

			int changeLength = e.getLength();
			//取得文字元件內容的長度

			taMsg.append(e.getType().toString() + " : " +
				changeLength + "個字元, 從" + e.getOffset() + 
				"到" + (e.getOffset()+changeLength) + "\n");
			//輸出引發事件之內容的長度與起始位置
		}
	};

	int start = -1, finish = -1;
	//宣告儲存上次選取範圍的起始位置與結束位置

	DefaultListModel dlmAtt = new DefaultListModel();
	//宣告儲存顯示文件元件之屬性的清單方塊

	DocumentEX(){

		tcContent = createTextArea(); //建立文字區

		tcContent.addCaretListener(cl);
		//註冊回應CaretEvent事件的監聽器

		taMsg.setRows(6); //設定文字區的列數
		taMsg.setLineWrap(true); //設定自動斷行
        taMsg.setWrapStyleWord(true); //不在文字間斷行		

		Document doc = tcContent.getDocument();
		//取得文字元件的Document物件

		doc.addDocumentListener(dl);
		//註冊回應Document物件之DocumentEvent事件的監聽器物件

		trDoc = new JTree(
			new DocumentTreeModel(doc.getDefaultRootElement()));
		//宣告顯示文件結構的樹狀結構控制項

		//註冊、定義監聽MouseEvent事件的監聽器物件
		trDoc.addMouseListener(
										new MouseAdapter(){

			//回應按下滑鼠按鍵動作
			public void mouseClicked(MouseEvent e){
				
				JTree source = (JTree)e.getSource();
				//取得引發事件的樹狀結構控制項

				TreePath path = 
					source.getPathForLocation(e.getX(), e.getY());
				//取得滑鼠游標位置對應的選取路徑

				if(path == null) return;
				//若選取路徑為null則終止執行

				Element node = 
					(Element) path.getLastPathComponent();
				//取得路徑最後選取的節點, 並轉型為Element物件

				start = finish = -1; //清除上次的選取範圍

				tcContent.setSelectionStart(node.getStartOffset());
				tcContent.setSelectionEnd(node.getEndOffset());
				//設定文件節點的選取範圍

				tcContent.grabFocus(); //設定文字面版取得焦點

				AttributeSet asNode = node.getAttributes();
				//取得文件元素的文字屬性
				
				dlmAtt.clear(); //清除清單方塊的選項內容

				dlmAtt.addElement(
					"屬性個數 : " + asNode.getAttributeCount());
				//將文字屬性的個數加入清單方塊

				//以for迴圈取得所有文字屬性設定的名稱
				for (Enumeration elm = asNode.getAttributeNames();
							elm.hasMoreElements() ;) {

					String strAtt = elm.nextElement().toString();
					//取得下一個文字屬性設定

					//以文字屬性設定的名稱, 呼叫AttributeSet類別
					//getAttribute()方法取得設定內容
					if("size".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.FontSize);
						//取得字型大小設定
					}
					else if("foreground".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Foreground);
						//取得文字顏色設定
					}
					else if("family".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Family);
						//取得字型設定
					}
					else if("bold".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Bold);
						//取得是否為粗體的設定
					}
					else if("italic".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Italic);
						//取得是否為斜體的設定
					}
					else if("underline".equals(strAtt)){
						strAtt += " = " + 
							asNode.getAttribute(StyleConstants.Underline);
						//取得是否下加底線的設定
					}
					else if("resolver".equals(strAtt))
						strAtt = "屬性待解析"; //設定顯示屬性待解析
					
					dlmAtt.addElement(strAtt);
					//將屬性顯示新增至清單方塊
				}
			}
		});
			
		JList lstAtt = new JList(dlmAtt);
		//宣告顯示屬性內容的清單方塊

		lstAtt.setVisibleRowCount(7);  //設定清單方塊的可視列數

		JMenuBar mb = new JMenuBar(); //宣告功能表列
		mb.add(createStyleMenu()); //將功能表加入功能表列
		mb.add(createSizeMenu());
		mb.add(createColorMenu());

		setJMenuBar(mb); //設定視窗框架使用的功能表列

		JPanel jpTree = new JPanel(new BorderLayout(5, 5));
		jpTree.add(new JScrollPane(trDoc));
		//加入顯示文件結構的樹狀結構

		jpTree.add(new JScrollPane(lstAtt), BorderLayout.SOUTH);
		//將顯示屬性設定的清單方塊加入容器

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5,  5));

		cp.add(tcContent); //加入文字面版
		cp.add(jpTree, BorderLayout.EAST);
		//加入顯示文件內容結構的面版

		cp.add(createToolBar(), BorderLayout.NORTH);
		//將工具列加入視窗框架內

		cp.add(new JScrollPane(taMsg), BorderLayout.SOUTH);
		//加入文字區

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 550);
		setVisible(true);
	}

	private JToolBar createToolBar(){

		JRadioButton 
			rbPlain =	new JRadioButton("Plain Document (JTextArea)", true),
			rbSimpleStyle = 
				new JRadioButton("Style Document (簡要內容、JTextPane)"),
			rbStyle = new JRadioButton("Style Document (JTextPane)");
		//宣告控制選取文字元件的JRadioButton物件

		//宣告監聽ActionEvent事件的監聽器物件
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				String strAction = (String) e.getActionCommand();
				//取得動作命令字串
					
				tcContent.removeCaretListener(cl);
				//移除監聽CaretEvent事件的監聽器物件

				DocumentEX.this.getContentPane().remove(tcContent);
				//移除文字元件

				//判斷動作命令字串建立對應的文字元件
				if("Plain Document (JTextArea)".equals(strAction))
					tcContent = createTextArea();
				else if("Style Document (簡要內容、JTextPane)".equals(strAction))
					tcContent = createSimpleTextPane();
				else if("Style Document (JTextPane)".equals(strAction))
					tcContent = createTextPane();
				
				taMsg.setText(null); //清除文字區的內容

				DocumentEX.this.getContentPane().add(tcContent);
				//重新加入文字元件

				tcContent.addCaretListener(cl);
				//註冊回應CaretEvent事件的監聽器
	
				Document doc = tcContent.getDocument();
				//取得文字元件的Document物件

				doc.addDocumentListener(dl);
				//註冊回應Document物件之DocumentEvent事件的監聽器物件

				trDoc.setModel(
					new DocumentTreeModel(
						doc.getDefaultRootElement()));
				//宣告顯示文件結構的樹狀結構控制項

				DocumentEX.this.getRootPane().updateUI();
				//更新根面版的畫面
			}
		};

		rbPlain.addActionListener(al);
		rbSimpleStyle.addActionListener(al);
		rbStyle.addActionListener(al);
		//註冊回應ActionEvent事件的監聽器物件

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbPlain);
		bg.add(rbSimpleStyle);
		bg.add(rbStyle);
		//將JRadioButton元件加入按鈕群組

		JToolBar tb = new JToolBar();
		tb.add(rbPlain);
		tb.add(rbSimpleStyle);
		tb.add(rbStyle);
		//將JRadioButton元件加入工具列

		return tb;
	}

	private JTextArea createTextArea(){

		JTextArea ta = new JTextArea();
		//宣告文字區元件

		String[] strInit = {
			"Java 2 視窗程式設計\n",
			"\n",
			"位元文化 編著\n",
			"文魁資訊 出版"};
		//文字區的內容
		
		for(String elm: strInit)
			ta.append(elm);
			//將字串加入文字區

		return ta;
	}

	private JTextPane createTextPane(){

		String[] strInit = {
			"Java 2 視窗程式設計", "\n\n",
			"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
			"而不是空泛地介紹各類別與方法, \n",
			"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
			"更可學習實際的應用, \n", 
			"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
		//文件的內容

		JTextPane tp = new JTextPane(); //宣告樣式文字面版
		Document doc = tp.getDocument();
		//取得文字元件的Document物件

		try {
			SimpleAttributeSet sas = new SimpleAttributeSet();
			//設定文字屬性

			StyleConstants.setFontFamily(sas, "華康儷中黑");
			StyleConstants.setFontSize(sas, 14);
			StyleConstants.setForeground(sas, Color.black);
			//設定字型、字型大小與前景顏色

			//將文字內容與文字屬性新增至文字面版
			for(int i = 0; i<strInit.length; i++)
				doc.insertString(doc.getLength(), 
					strInit[i], sas);

		} catch (BadLocationException ble) {
			System.err.println("無法正確起始內容");
		}

		return tp;
	}

	private JTextPane createSimpleTextPane(){

		String str1 = "Java 2 ",
				   str2 = "視窗",
				   str3 = "程式設計"; //宣告字串

		JTextPane tp = new JTextPane(); //宣告樣式文字面版
		Document doc = tp.getDocument();
		//取得文字元件的Document物件

		try {
			SimpleAttributeSet sas = new SimpleAttributeSet();
			//設定文字屬性

			StyleConstants.setFontFamily(sas, "華康儷中黑");
			StyleConstants.setFontSize(sas, 16);
			StyleConstants.setForeground(sas, Color.blue);
			//設定字型、字型大小與前景顏色

			doc.insertString(doc.getLength(), str1, sas);
			//將文字內容與文字屬性新增至文字面版			
			
			StyleConstants.setForeground(sas, Color.red);
			//設定字型、字型大小與前景顏色

			doc.insertString(doc.getLength(), str2, sas);
			//將文字內容與文字屬性新增至文字面版			

			StyleConstants.setForeground(sas, Color.green);
			//設定字型、字型大小與前景顏色

			doc.insertString(doc.getLength(), str3, sas);
			//將文字內容與文字屬性新增至文字面版
		} catch (BadLocationException ble) {
			System.err.println("無法正確起始內容");
		}

		return tp;
	}


    //建立樣式功能表
	protected JMenu createStyleMenu() {

		JMenu mnStyle = new JMenu("文字樣式");

		Action action = new StyledEditorKit.BoldAction();
		//宣告設定文字為粗體之Action物件

		action.putValue(Action.NAME, "Bold");
		//將設定文字粗體的Action物件加入雜湊表

		mnStyle.add(action); //將動作加入功能表

		action = new StyledEditorKit.ItalicAction();
		//宣告設定文字為斜體之Action物件

		action.putValue(Action.NAME, "Italic");
		//將設定文字斜體的Action物件加入雜湊表

		mnStyle.add(action); //將動作加入功能表

		return mnStyle;
	}

	//建立字體大小功能表
	protected JMenu createSizeMenu() {

		JMenu mnSize = new JMenu("字體大小");

		//將樣式編輯工具的設定文字大小的Action物件加入功能表
		mnSize.add(
			new StyledEditorKit.FontSizeAction("12", 12));
		mnSize.add(
			new StyledEditorKit.FontSizeAction("14", 14));
		mnSize.add(
			new StyledEditorKit.FontSizeAction("18", 18));

		return mnSize;
	}

	//建立前景顏色功能表
	protected JMenu createColorMenu() {
		JMenu mnColor = new JMenu("前景顏色");

		//將樣式編輯工具的設定文字前景顏色的Action物件加入功能表
        mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Red", Color.red));
		mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Green", Color.green));
		mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Blue", Color.blue));
		mnColor.add(
			new StyledEditorKit.ForegroundAction(
				"Black", Color.black));

		return mnColor;
	}

	public static void main(String args[]) {
		new DocumentEX(); //宣告視窗框架物件
	}
}