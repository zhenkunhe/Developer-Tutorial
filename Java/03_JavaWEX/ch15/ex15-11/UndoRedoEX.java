import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.text.*;
//包含處理文字面版內容相關介面與類別的套件

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class UndoRedoEX extends JFrame {

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

	UndoManager undoManager = new UndoManager();
	//宣告管理重做/還原動作的UndoManager物件

	UndoAction undoAction; //執行還原動作的Action物件
    RedoAction redoAction; //執行重做動作的Action物件

	//以宣告匿名類別的方式,
	//定義監聽UndoableEditEvent事件的監聽器物件
	UndoableEditListener uel = new UndoableEditListener(){

		//回應發生可還原編輯動作的方法
		public void
			undoableEditHappened(UndoableEditEvent e) {

			undoManager.addEdit(e.getEdit());
			//將引發可還原編輯動作的方法加入UndoManager物件

			undoAction.updateUndoState();
			redoAction.updateRedoState();
			//更新Action物件建立之選項的顯示內容
		}
	};

	UndoRedoEX(){

		//將文字內容新增至文字面版
		for(int i = (strInit.length - 1); i>=0; i--)
			tpMsg.replaceSelection(strInit[i]);
			//以置換的方式新增內容

		tpMsg.getDocument().addUndoableEditListener(uel);
		//註冊監聽UndoableEditEvent事件的監聽器物件

		createActionTable(tpMsg);
		//建立樣式文字面版之動作物件的表格

		JMenuBar mb = new JMenuBar(); //宣告功能表列
		mb.add(createEditMenu()); //將功能表加入功能表列
		mb.add(createUndoMenu()); //將功能表加入功能表列

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

	//以文字元件描述可執行動作之Aciton物件建立編輯功能表
    protected JMenu createUndoMenu() {

		undoAction = new UndoAction();
		//宣告執行重做動作的Action物件

		redoAction = new RedoAction();
		//宣告執行還原動作的Action物件

		JMenu menu = new JMenu("還原/重做");
		//宣告還原/重做功能表

		menu.add(undoAction); //將Action物件加入功能表
		menu.add(redoAction);

		return menu;
    }

	//定義執行還原動作的Action類別
	class UndoAction extends AbstractAction {

		public UndoAction() {
			super("還原"); //呼叫基礎類別的建構子
			setEnabled(false);
			//設定執行還原動作之Action物件的狀態為無效
		}

		public void actionPerformed(ActionEvent e) {

			try {
				undoManager.undo(); //執行還原動作
			}
			catch (CannotUndoException ex) {
				System.out.println("無法還原 : " + ex);
				ex.printStackTrace();
			}

			updateUndoState();
			//更新還原選項的顯示狀態

			redoAction.updateRedoState();
			//更新重做選項的顯示狀態
		}

		//更新執行還原動作之Action物件的顯示狀態
		protected void updateUndoState() {

			//判斷是否可以還原
			if (undoManager.canUndo()) {
				setEnabled(true);
				//設定還原選項狀態為有效

				putValue(Action.NAME, 
					undoManager.getUndoPresentationName());
				//取得目前可執行還原動作的名稱
			}
			else {
				setEnabled(false); //設定為無效狀態
				putValue(Action.NAME, "還原");
				//設定Action物件的NAME性質為還原
			}
		}
	}

	//定義執行重做動作的Action類別
	class RedoAction extends AbstractAction {

		public RedoAction() {
			super("重做"); //呼叫基礎類別的建構子
			setEnabled(false);
			//設定執行重做動作之Action物件的狀態為無效
		}

		public void actionPerformed(ActionEvent e) {

			try {
				undoManager.redo(); //執行重做動作
			}
			catch(CannotRedoException ex) {
				System.out.println("無法重做 : " + ex);
				ex.printStackTrace();
			}

			updateRedoState();
			//更新重做選項的顯示狀態

			undoAction.updateUndoState();
			//更新還原選項的顯示狀態
		}

		//更新執行重做動作之Action物件的顯示狀態
		protected void updateRedoState() {

			if (undoManager.canRedo()) {
				setEnabled(true);
				//設定還原選項狀態為有效

				putValue(Action.NAME, 
					undoManager.getRedoPresentationName());
				//取得目前可執行重做動作的名稱
			}
			else {
				setEnabled(false); //設定為無效狀態
				putValue(Action.NAME, "重做");
				//設定Action物件的NAME性質為重做
			}
		}
	}

	public static void main(String args[]) {
		new UndoRedoEX(); //宣告視窗框架物件
	}
}