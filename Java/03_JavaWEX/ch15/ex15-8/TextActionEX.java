import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
//包含處理文字面版內容相關介面與類別的套件

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class TextActionEX extends JFrame {

	JTextPane tpMsg = new JTextPane(); //顯示文字內容的文字面版

	HashMap actions = new HashMap();
	//宣告儲存Action物件的雜湊表

	JComboBox cmbFont = new JComboBox();
	//宣告供設定字型的組合方塊

	//定義實作CaretListener介面
	//回應CaretEvent事件的監聽器物件
	CaretListener cl = new CaretListener(){

		//回應游標位置的更新動作
		public void caretUpdate(CaretEvent e){

			AttributeSet asCur = tpMsg.getCharacterAttributes();
			//取得目前設定的AttributeSet物件

			mtaItalic.setSelected(false);
			mtaUnderline.setSelected(false);
			mtaBold.setSelected(false);
			//先設定不選取斜體、下加底線與粗體的設定

			//取得文字設定的屬性
			for (Enumeration obj = asCur.getAttributeNames();
							obj.hasMoreElements() ;) {

				String strAtt = obj.nextElement().toString();
					//取得下一個文字屬性設定

				if("size".equals(strAtt)){
					int size = (Integer)asCur.getAttribute(StyleConstants.FontSize);
					//取得設定的字體大小

					if(size == 12)
						mta12.setSelected(true);
					else if(size == 14)
						mta14.setSelected(true);
					else if(size == 16)
						mta16.setSelected(true);
				}
				else	if("foreground".equals(strAtt)){
					Color color = (Color) asCur.getAttribute(
												StyleConstants.Foreground);
					//取得設定的背景顏色

					//判斷內文設定的顏色
					if(Color.red.equals(color))
						mtaRed.setSelected(true);
					else if(Color.green.equals(color))
						mtaGreen.setSelected(true);
					else if(Color.blue.equals(color))
						mtaBlue.setSelected(true);
					else if(Color.black.equals(color))
						mtaBlack.setSelected(true);
				}
				else	if("italic".equals(strAtt)){
					mtaItalic.setSelected(
						(Boolean) asCur.getAttribute(StyleConstants.Italic));
					//取得設定的斜體設定
				}
				else	if("underline".equals(strAtt)){
					mtaUnderline.setSelected(
						(Boolean) asCur.getAttribute(StyleConstants.Underline));
					//取得設定的下加底線設定
				}
				else	if("bold".equals(strAtt)){
					mtaBold.setSelected(
						(Boolean) asCur.getAttribute(StyleConstants.Bold));
					//取得設定的粗體設定
				}
				else	if("family".equals(strAtt)){
					cmbFont.setSelectedItem(
						asCur.getAttribute(StyleConstants.FontFamily));
					//設定組合方塊顯示的選取字型
				}
			}
		}
	};

	MyTextAction 
		mta12 = new MyTextAction(
							new StyledEditorKit.FontSizeAction("12", 12)),
		mta14 = new MyTextAction(
							new StyledEditorKit.FontSizeAction("14", 14), true),
		mta16 = new MyTextAction(
							new StyledEditorKit.FontSizeAction("16", 16));
	//宣告建立工具列上控制字體大小的指令按鈕

	MyTextAction 
		mtaRed = new MyTextAction(
			new StyledEditorKit.ForegroundAction("Red", Color.red)),
		mtaGreen = new MyTextAction(
			new StyledEditorKit.ForegroundAction(
				"Green", Color.green)),
		mtaBlue = new MyTextAction(
			new StyledEditorKit.ForegroundAction("Blue", Color.blue)),
		mtaBlack = new MyTextAction(
			new StyledEditorKit.ForegroundAction(
				"Black", Color.black),	true);
	//宣告建立工具列上控制前景顏色的指令按鈕

	MyTextAction 
		mtaBold = new MyTextAction(
							new StyledEditorKit.BoldAction()),
		mtaItalic = new MyTextAction(
							new StyledEditorKit.ItalicAction()),
		mtaUnderline = new MyTextAction(
							new StyledEditorKit.UnderlineAction());
	//宣告建立工具列上控制文字粗體、斜體與下加底線的指令按鈕

	String[] strInit = {
		"Java 2 視窗程式設計\n","\n",
		"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
		"而不是空泛地介紹各類別與方法, \n",
		"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
		"更可學習實際的應用, \n", 
		"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
	//樣式文字面版的內容

	TextActionEX(){

		tpMsg.addCaretListener(cl); //註冊回應CaretEvent事件的監聽器

		SimpleAttributeSet asInput = (SimpleAttributeSet)tpMsg.getInputAttributes();
		//取得資料輸入使用的樣式

		StyleConstants.setFontSize(asInput, 14);
		StyleConstants.setFontFamily(asInput, "新細明體");
		StyleConstants.setForeground(asInput, Color.black);
		//設定內文樣式屬性的字型為細明體,
		//字體大小為14, 前景顏色為黑色

		//將文字內容新增至文字面版
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//以置換的方式新增內容

		mtaBold.putValue(Action.NAME, "Bold");
		mtaItalic.putValue(Action.NAME, "Italic");
		mtaUnderline.putValue(Action.NAME, "Underline");
		//設定Action物件的名稱性質

		createActionTable(tpMsg);
		//建立樣式文字面版之動作物件的表格

		JMenuBar mb = new JMenuBar(); //宣告功能表列
		mb.add(createEditMenu()); //將功能表加入功能表列
		mb.add(createStyleMenu());
		mb.add(createSizeMenu());
		mb.add(createColorMenu());

		setJMenuBar(mb); //設定視窗框架使用的功能表列

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5,  5));

		cp.add(new JScrollPane(tpMsg)); //加入文字面版

		cp.add(createFontToolBar(), BorderLayout.NORTH);
		//加入工具列

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 450);
		setVisible(true);
	}

	private Action getActionByName(String name) {
		return (Action)(actions.get(name));
		//以名稱性質取得Action物件
	}

	//運用描述文字元件之預設動作的Action物件與其名稱建立雜湊表
	private void createActionTable(JTextComponent textComponent) {

		Action[] actionsArray = textComponent.getActions();
		//取得文字元件所有預設建立描述可執行動作的Action物件

		//以Action物件與其名稱建立雜湊表
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
			//將Action物件與其名稱放入雜湊表
		}
	}

	//以文字元件描述可執行動作之Aciton物件建立編輯功能表
    protected JMenu createEditMenu() {
		JMenu mnEdit = new JMenu("編輯");

		//從DefaultEditorKit類別取得描述編輯動作的預設Action物件
		//以下分別將描述剪下、複製與貼上之Action物件加入功能表
		mnEdit.add(getActionByName(DefaultEditorKit.cutAction));
		mnEdit.add(getActionByName(DefaultEditorKit.copyAction));
		mnEdit.add(getActionByName(DefaultEditorKit.pasteAction));

		mnEdit.addSeparator(); //新增分隔符號

		mnEdit.add(getActionByName(DefaultEditorKit.selectAllAction));
		//將描述全選動作的Action物件加入功能表

		return mnEdit;
    }

    //建立樣式功能表
	protected JMenu createStyleMenu() {

		JMenu mnStyle = new JMenu("字型樣式");
		JCheckBoxMenuItem 
			cbmiBold = new JCheckBoxMenuItem(mtaBold),
			cbmiItalic = new JCheckBoxMenuItem(mtaItalic),
			cbmiUnderline = new JCheckBoxMenuItem(mtaUnderline);
		//以MyTextAction物件宣告功能表的核取方塊選項

		mtaBold.add(cbmiBold); //將核取方塊選項加入動作物件
		mtaItalic.add(cbmiItalic);
		mtaUnderline.add(cbmiUnderline);

		mnStyle.add(cbmiBold); //將動作加入功能表
		mnStyle.add(cbmiItalic);
		mnStyle.add(cbmiUnderline);

		return mnStyle;
	}

    //建立字體大小功能表
	protected JMenu createSizeMenu() {

		JMenu mnSize = new JMenu("字體大小");

		JRadioButtonMenuItem
			rbmi12 = new JRadioButtonMenuItem(mta12),
			rbmi14 = new JRadioButtonMenuItem(mta14),
			rbmi16 = new JRadioButtonMenuItem(mta16);
		//以MyTextAction物件宣告功能表的選擇鈕選項

		mta12.add(rbmi12); //將選擇鈕選項加入動作物件
		mta14.add(rbmi14);
		mta16.add(rbmi16);
	
		ButtonGroup bgMnFontSize = new ButtonGroup();
		bgMnFontSize.add(rbmi12);
		bgMnFontSize.add(rbmi14);
		bgMnFontSize.add(rbmi16);
		//將選擇鈕選項加入按鈕群組

		mnSize.add(rbmi12);
		mnSize.add(rbmi14);
		mnSize.add(rbmi16);
		//將樣式編輯工具的設定文字大小的Action物件加入功能表

		return mnSize;
	}

    //建立前景顏色功能表
	protected JMenu createColorMenu() {

		JMenu mnColor = new JMenu("前景顏色");

		JRadioButtonMenuItem 
			rbmiRed = new JRadioButtonMenuItem(mtaRed),
			rbmiGreen = new JRadioButtonMenuItem(mtaGreen),
			rbmiBlue = new JRadioButtonMenuItem(mtaBlue),
			rbmiBlack = new JRadioButtonMenuItem(mtaBlack);
		//宣告控制前景顏色的選擇鈕選項

		mtaRed.add(rbmiRed);
		mtaGreen.add(rbmiGreen);
		mtaBlue.add(rbmiBlue);
		mtaBlack.add(rbmiBlack);
		//將選擇鈕選項加入動作物件

		mnColor.add(rbmiRed);
		mnColor.add(rbmiGreen);
		mnColor.add(rbmiBlue);
		mnColor.add(rbmiBlack);
		//將設定前景顏色的Action物件加入功能表

		ButtonGroup bgMnColor = new ButtonGroup();
        bgMnColor.add(rbmiRed);
		bgMnColor.add(rbmiGreen);
		bgMnColor.add(rbmiBlue);
		bgMnColor.add(rbmiBlack);
		//將選擇鈕選項加入按鈕群組

		return mnColor;
	}

	//建立控制設定文字屬性的工具列
	protected JToolBar createFontToolBar(){

		JToolBar tbFont = new JToolBar();
		//宣告工具列物件

		JToggleButton 
			tbBold = new JToggleButton(mtaBold),
			tbItalic = new JToggleButton(mtaItalic),
			tbUnderline = new JToggleButton(mtaUnderline);
		//宣告控制字元粗體、斜體與下加底線的切換按鈕

		mtaBold.add(tbBold);
		mtaItalic.add(tbItalic);
		mtaUnderline.add(tbUnderline);
		//將切換按鈕加入MyTextAction物件

		tbFont.add(tbBold); //將Action物件加入工具列
		tbFont.add(tbItalic);
		tbFont.add(tbUnderline);

		tbFont.addSeparator(); //加入分隔符號

		JToggleButton 
			tb12 = 	new JToggleButton(mta12),
			tb14 = 	new JToggleButton(mta14),
			tb16 = 	new JToggleButton(mta16);
		//宣告控制字體大小的切換按鈕

		mta12.add(tb12);
		mta14.add(tb14);
		mta16.add(tb16);
		//將切換按鈕加入工具列

		ButtonGroup bgFontSize = new ButtonGroup();
		bgFontSize.add(tb12);
		bgFontSize.add(tb14);
		bgFontSize.add(tb16);
		//將控制字體大小的切換按鈕加入按鈕群組

		//以設定文字大小的FontSizeAction物件宣告MyTextAction物件,
		//然後加入工具列
		tbFont.add(tb12);
		tbFont.add(tb14);
		tbFont.add(tb16);

		tbFont.addSeparator(); //加入分隔符號

		JToggleButton 
			tbRed = new JToggleButton(mtaRed),
			tbGreen = new JToggleButton(mtaGreen),
			tbBlue = new JToggleButton(mtaBlue),
			tbBlack = new JToggleButton(mtaBlack);
		//宣告控制前景顏色的切換按鈕

		mtaRed.add(tbRed);
		mtaGreen.add(tbGreen);
		mtaBlue.add(tbBlue);
		mtaBlack.add(tbBlack);
		//將切換按鈕加入動作物件

		ButtonGroup bgColor = new ButtonGroup();
        bgColor.add(tbRed);
		bgColor.add(tbGreen);
		bgColor.add(tbBlue);
		bgColor.add(tbBlack);
		//將控制前景顏色的切換按鈕加入按鈕群組

		tbFont.add(tbRed);
		tbFont.add(tbGreen);
		tbFont.add(tbBlue);
		tbFont.add(tbBlack);
		//以設定文字顏色的ForegroundAction物件宣告MyTextAction物件,
		//然後加入工具列

		tbFont.addSeparator(); //加入分隔符號

		//以執行緒取得系統提供的所有字型, 
		//然後將字型名稱加入組合方塊的模型物件內
		Thread td = new Thread(){

			public void run(){

				String[] strFonts = 
					GraphicsEnvironment.getLocalGraphicsEnvironment(
					).getAvailableFontFamilyNames();
				//取得系統提供的所有字型之名稱的字串陣列
								
				DefaultComboBoxModel 
					dcbm = new DefaultComboBoxModel(strFonts);
				//宣告儲存自行選取組合方塊選項內容
				//的DefaultComboBoxModel物件

				cmbFont.setModel(dcbm); //設定組合方塊的內容

				cmbFont.setSelectedItem("新細明體");
				//設定組合方塊選取的字型名稱
			}
		};

		td.start(); //啟動執行
		
		//註冊監聽ActionEvent事件的監聽器物件
		cmbFont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				new MyTextAction(new StyledEditorKit.FontFamilyAction(					
						(String)cmbFont.getSelectedItem(),
						(String)cmbFont.getSelectedItem())
														).actionPerformed(e);
				//以組合方塊選取的字型宣告FontFamilyAction物件,
				//並呼叫actionPerformed()方法設定字型

				tpMsg.grabFocus();
				//設定引發動作的文字元件取得焦點
			}
		});

		tbFont.add(cmbFont); //將組合方塊加入工具列

		return tbFont;		
	}

	public static void main(String args[]) {
		new TextActionEX(); //宣告視窗框架物件
	}
}

//以繼承TextAction類別的方式宣告MyTextAction類別
class MyTextAction extends TextAction {

	TextAction action; //實際執行編輯動作的Action物件
	ArrayList<AbstractButton> alBtn = new ArrayList<AbstractButton>();
	//將按鈕元件加入ArrayList物件內

	boolean selected = false; //控制是否選取的布林值

	MyTextAction(TextAction action){

		super((String)action.getValue(Action.NAME));
		//呼叫基礎類別的建構子並傳入Action物件的NAME性質

		this.action = action;
	}

	MyTextAction(TextAction action, boolean selected){

		super((String)action.getValue(Action.NAME));
		//呼叫基礎類別的建構子並傳入Action物件的NAME性質

		this.action = action; //將動作物件設定給action屬性

		this.selected = selected; //設定動作物件的選取設定
	}

	public void actionPerformed(ActionEvent e){

		JTextComponent tc = getTextComponent(e);
		//取得引發事件的文字元件

		//以加強型for迴圈設定以TextAction物件建立的元件為選取
		for(AbstractButton elm :  alBtn){
			Object source = e.getSource();
			//取得引發事件的來源

			if(elm != source){
				elm.setSelected(((AbstractButton)source).isSelected());
				//設定元件為選取
			}
		}

		if(tc == null) return; //若文字元件為null則終止執行方法

		action.actionPerformed(e);
		//執行基礎類別回應ActionEvent事件的actionPerformed()方法

		tc.grabFocus();
		//設定引發動作的文字元件取得焦點
	}

	//將以MyTextAction物件建立的控制項加入ArrayList容器
	public void add(AbstractButton ab){
		alBtn.add(ab); //將控制項加入ArrayList容器
		ab.setSelected(selected); //設定控制項的起始選取狀態
	}

	//設定MyTextAction物件建立的所有控制項之選取狀態
	public void setSelected(boolean bl){
		for(AbstractButton elm : alBtn)
			elm.setSelected(bl);
	}
}