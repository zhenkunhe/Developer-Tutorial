import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class KeymapEX extends JFrame {

	JTextPane tpStyle = new JTextPane(); //樣式文字面版

	String[] strInit = {
			"Java 2 視窗程式設計\n","\n",
			"這本書強調以範例講解Swing視窗程式設計的觀念, \n",
			"而不是空泛地介紹各類別與方法, \n",
			"讀者透過這本書除了可以瞭解如何運用各類別與方法, \n",
			"更可學習實際的應用, \n", 
			"即便是初學者亦可快速學會運用Java開發視窗程式\n"};
	//樣式文字面版的內容

	KeymapEX(){

		SimpleAttributeSet sasInput =
			(SimpleAttributeSet) tpStyle.getInputAttributes();
		//取得資料輸入使用的樣式

		StyleConstants.setFontSize(sasInput, 14);
		//設定資料輸入樣式屬性的字型大小為14

		StyleConstants.setForeground(sasInput, Color.black);
		//設定前景顏色為黑色

		tpStyle.replaceSelection("Alt + E 設定套用強調字型\n");

		StyleConstants.setForeground(sasInput, Color.red);
		//設定前景顏色為紅色

		tpStyle.replaceSelection("Alt + R 設定前景顏色為紅色\n\n");

		StyleConstants.setForeground(sasInput, Color.green);
		//設定前景顏色為綠色

		tpStyle.replaceSelection("Alt + G 設定前景顏色為綠色\n");

		StyleConstants.setForeground(sasInput, Color.blue);
		//設定前景顏色為藍色

		tpStyle.replaceSelection("\nAlt + B 設定前景顏色為藍色\n");

		StyleConstants.setForeground(sasInput, Color.black);
		//設定前景顏色為黑色

		//將文字內容新增至文字面版
		for(int i = (strInit.length - 1); i>=0; i--)
			tpStyle.replaceSelection(strInit[i]);
			//以置換的方式新增內容

		ActionMap am = tpStyle.getActionMap();
		//取得JTextPane物件使用的ActionMap物件

		am.put("emphasis", new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				new StyledEditorKit.BoldAction().actionPerformed(e);
				//設定字體為粗體

				new StyledEditorKit.FontSizeAction(
					"16", 16).actionPerformed(e);
				//設定字體大小為16
			}
		});
		//將定義文字的強調格式為粗體,
		//字體大小為16的Action物件置入ActionMap物件內

		tpStyle.getInputMap().put(
			KeyStroke.getKeyStroke(
				'E', java.awt.event.InputEvent.ALT_MASK),
				"emphasis");
		//定義按下Alt + E可設定使用強調樣式

		Keymap km = tpStyle.getKeymap();
		//取得共享的Keymap物件

		Keymap kmStyle = tpStyle.addKeymap("New Keymap", km);
		//建立供樣式文字面版運用的Keymap物件

		kmStyle.addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				'R', java.awt.event.InputEvent.ALT_MASK),
			new StyledEditorKit.ForegroundAction(
				"Red", Color.red));
		//定義按下Alt + R可設定前景顏色為紅色

		kmStyle.addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				'G', java.awt.event.InputEvent.ALT_MASK),
			new StyledEditorKit.ForegroundAction(
				"Green", Color.green));
		//定義按下Alt + G可設定前景顏色為綠色

		kmStyle.addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				'B', java.awt.event.InputEvent.ALT_MASK),
			new StyledEditorKit.ForegroundAction(
				"Blue", Color.blue));
		//定義按下Alt + B可設定前景顏色為藍色

		Container cp = getContentPane(); //取得內容面版

		cp.add(new JScrollPane(tpStyle));

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 350);
		setVisible(true);
	}

	public static void main(String args[]) {
		new KeymapEX(); //宣告視窗框架物件
	}
}