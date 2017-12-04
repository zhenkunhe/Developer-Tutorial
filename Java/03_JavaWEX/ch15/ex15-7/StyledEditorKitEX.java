import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
//包含處理文字面版內容相關介面與類別的套件

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class StyledEditorKitEX extends JFrame {

	JTextPane tpMsg = new JTextPane();
	//顯示文字內容的文字面版

	HashMap actions = new HashMap();
	//宣告儲存Action物件的雜湊表

	JComboBox cmbFont = new JComboBox();
	//宣告供設定字型的組合方塊

	TextAction 
		ta12 = new StyledEditorKit.FontSizeAction("12", 12),
		ta14 = new StyledEditorKit.FontSizeAction("14", 14),
		//宣告建立工具列上控制字體大小指令按鈕的FontSizeAction物件

		ta16 = (TextAction) tpMsg.getActionMap().get("font-size-16");
		//從ActionMap取得對應的TextAction物件

	TextAction 
		taRed = 
			new StyledEditorKit.ForegroundAction("Red", Color.red),
		taGreen = new StyledEditorKit.ForegroundAction(
				"Green", Color.green),
		taBlue = 
			new StyledEditorKit.ForegroundAction("Blue", Color.blue),
		taBlack =
			new StyledEditorKit.ForegroundAction(
													"Black", Color.black);
	//宣告建立工具列上控制前景顏色的指令按鈕

	TextAction 
		taBold = new StyledEditorKit.BoldAction(),
		taItalic = new StyledEditorKit.ItalicAction(),
		taUnderline = new StyledEditorKit.UnderlineAction();
	//宣告建立工具列上控制文字粗體、斜體與下加底線的指令按鈕

	String[] strInit = {
		"Java 2 視窗程式設計\n","\n",
		"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
		"而不是空泛地介紹各類別與方法, \n",
		"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
		"更可學習實際的應用, \n", 
		"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
	//樣式文字面版的內容

	StyledEditorKitEX(){

		//將文字內容新增至文字面版
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//以置換的方式新增內容

		taBold.putValue(Action.NAME, "Bold");
		taItalic.putValue(Action.NAME, "Italic");
		taUnderline.putValue(Action.NAME, "Underline");
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
		setSize(800, 500);
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

		JMenu menu = new JMenu("編輯");

		//從DefaultEditorKit類別取得描述編輯動作的預設Action物件
		//以下分別將描述剪下、複製與貼上之Action物件加入功能表
		menu.add(getActionByName(DefaultEditorKit.cutAction));
		menu.add(getActionByName(DefaultEditorKit.copyAction));

		menu.add(
			tpMsg.getActionMap().get(DefaultEditorKit.pasteAction));
		//取得樣式文字面版的ActionMap物件, 
		//並傳入DefaultEditorKit.pasteAction,
		//取得執行貼上動作的Action物件

		menu.addSeparator(); //新增分隔符號

		menu.add(getActionByName(DefaultEditorKit.selectAllAction));
		//將描述全選動作的Action物件加入功能表

		return menu;
    }

    //建立字型樣式功能表
	protected JMenu createStyleMenu() {

		JMenu mnStyle = new JMenu("字型樣式");
		//宣告"字型樣式"功能表

		JMenuItem 
			miBold = new JMenuItem(taBold),
			miItalic = new JMenuItem(taItalic),
			miUnderline = new JMenuItem(taUnderline);
		//以MyTextAction物件宣告功能表的核取方塊選項

		mnStyle.add(miBold); //將動作加入功能表
		mnStyle.add(miItalic);
		mnStyle.add(miUnderline);

		return mnStyle;
	}

	//建立字體大小功能表
	protected JMenu createSizeMenu() {

		JMenu mnSize = new JMenu("字體大小");
		//宣告"字體大小"功能表

		JMenuItem
			mi12 = new JMenuItem(ta12),
			mi14 = new JMenuItem(ta14),
			mi16 = new JMenuItem(ta16);
		//以MyTextAction物件宣告功能表的選擇鈕選項
	
		mnSize.add(mi12);
		mnSize.add(mi14);
		mnSize.add(mi16);
		//將樣式編輯工具的設定文字大小的Action物件加入功能表

		return mnSize;
	}

	//建立前景顏色功能表
	protected JMenu createColorMenu() {

		JMenu mnColor = new JMenu("前景顏色");
		//宣告"前景顏色"功能表

		JMenuItem 
			miRed = new JMenuItem(taRed),
			miGreen = new JMenuItem(taGreen),
			miBlue = new JMenuItem(taBlue),
			miBlack = new JMenuItem(taBlack);
		//宣告控制前景顏色的選擇鈕選項

		mnColor.add(miRed);
		mnColor.add(miGreen);
		mnColor.add(miBlue);
		mnColor.add(miBlack);
		//將設定前景顏色的Action物件加入功能表

		return mnColor;
	}

	//建立控制設定文字屬性的工具列
	protected JToolBar createFontToolBar(){

		JToolBar tbFont = new JToolBar();
		//宣告工具列物件

		JButton 
			tbBold = new JButton(taBold),
			tbItalic = new JButton(taItalic),
			tbUnderline = new JButton(taUnderline);
		//宣告控制字元粗體、斜體與下加底線的切換按鈕

		tbFont.add(tbBold); //將Action物件加入工具列
		tbFont.add(tbItalic);
		tbFont.add(tbUnderline);

		tbFont.addSeparator(); //加入分隔符號

		JButton 
			tb12 = 	new JButton(ta12),
			tb14 = 	new JButton(ta14),
			tb16 = 	new JButton(ta16);
		//宣告控制字體大小的切換按鈕

		//以設定文字大小的FontSizeAction物件宣告MyTextAction物件,
		//然後加入工具列
		tbFont.add(tb12);
		tbFont.add(tb14);
		tbFont.add(tb16);

		tbFont.addSeparator(); //加入分隔符號

		JButton 
			tbRed = new JButton(taRed),
			tbGreen = new JButton(taGreen),
			tbBlue = new JButton(taBlue),
			tbBlack = new JButton(taBlack);
		//宣告控制前景顏色的切換按鈕

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

		td.start(); //啟動執行緒
		
		//註冊監聽ActionEvent事件的監聽器物件
		cmbFont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				(new StyledEditorKit.FontFamilyAction(
						(String)cmbFont.getSelectedItem(),
						(String)cmbFont.getSelectedItem())
														).actionPerformed(e);
				//以組合方塊選取的字型宣告FontFamilyAction物件,
				//並呼叫actionPerformed()方法設定字型
			}
		});

		tbFont.add(cmbFont); //將組合方塊加入工具列

		return tbFont;		
	}

	public static void main(String args[]) {
		new StyledEditorKitEX(); //宣告視窗框架物件
	}
}