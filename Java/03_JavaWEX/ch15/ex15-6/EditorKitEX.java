import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
//包含處理文字面版內容相關介面與類別的套件

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class EditorKitEX extends JFrame {

	JTextPane tpMsg = new JTextPane(); //顯示文字內容的文字面版

	HashMap actions = new HashMap();
	//宣告儲存Action物件的雜湊表

	String[] strInit = {
		"Java 2 視窗程式設計\n","\n",
		"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
		"而不是空泛地介紹各類別與方法, \n",
		"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
		"更可學習實際的應用, \n", 
		"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
	//樣式文字面版的內容

	EditorKitEX(){

		//將文字內容新增至文字面版
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//以置換的方式新增內容

		createActionTable(tpMsg);
		//建立樣式文字面版之動作物件的表格

		JMenuBar mb = new JMenuBar(); //宣告功能表列
		mb.add(createEditMenu()); //將功能表加入功能表列

		setJMenuBar(mb); //設定視窗框架使用的功能表列

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5,  5));

		cp.add(new JScrollPane(tpMsg)); //加入文字面版

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 250);
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

		menu.add(getActionByName(DefaultEditorKit.cutAction));
		menu.add(getActionByName(DefaultEditorKit.copyAction));
		//從DefaultEditorKit類別取得描述
		//剪下、複製編輯動作的預設Action物件

		menu.add((TextAction)
			tpMsg.getActionMap().get(DefaultEditorKit.pasteAction));
		//從文字元件的ActionMap內取得描述貼上動作的Action物件

		menu.addSeparator(); //新增分隔符號

		Action acSelectAll =
			getActionByName(DefaultEditorKit.selectAllAction);
		//取得執行全選動作的功能表選項

		acSelectAll.putValue(Action.NAME, "全選");
		//更改全選動作的字串

		menu.add(acSelectAll);
		//將描述全選動作的Action物件加入功能表

		return menu;
    }

	public static void main(String args[]) {
		new EditorKitEX(); //宣告視窗框架物件
	}
}