import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import javax.swing.event.*;
//引用包含CellEditorEvent與CellEditorListener的套件

import java.awt.*;
import java.awt.event.*;

public class DefaultCellEditEX extends JFrame {

	JTable tbBook = new JTable(new BookTableModel());
	//以TableModel物件宣告建立表格的JTable元件

	public DefaultCellEditEX() {

		JComboBox cmbPublisher = new JComboBox();
		cmbPublisher.addItem(Publisher.KingsInfo);
		cmbPublisher.addItem(Publisher.BitC);
		cmbPublisher.addItem(Publisher.GKInfo);
		cmbPublisher.addItem(Publisher.Other);
		//將列舉子加入組合方塊成為選項

		tbBook.setDefaultEditor(
			Publisher.class, new DefaultCellEditor(cmbPublisher));	
		//設定輸入Publisher列舉型態使用的組合方塊

		TableCellEditor tceDouble = tbBook.getDefaultEditor(Double.class);

		//以匿名類別的方式
		//定義、註冊監聽ChangeEvent事件的CellEditorListener監聽器物件
		tceDouble.addCellEditorListener(new CellEditorListener(){

			//回應結束編輯動作
			public void editingStopped(ChangeEvent e){
				System.out.println("結束編輯售價");
			}

			//回應取消編輯動作
			public void editingCanceled(ChangeEvent e){
				System.out.println("取消編輯售價");
			}
		});

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第1欄的TableColumn物件

		colTitle.setPreferredWidth(200);
		//設定欄位的喜好寬度為200

		TableColumn colPublisher = 
			tbBook.getColumnModel().getColumn(1);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第2欄的TableColumn物件

		colPublisher.setPreferredWidth(95);
		//設定欄位的喜好寬度為95

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		cp.add(new JScrollPane(tbBook));		
		//將元件加入中間區域

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(570, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new DefaultCellEditEX(); //宣告視窗框架物件	 
	}
}

enum Publisher { //定義處理書籍分類的列舉型態

	KingsInfo("文魁資訊"), BitC("位元文化"),
	GKInfo("勁魁資訊"), Other("其他");
	//呼叫建構子定義各列舉子
	
	String name; //儲存列舉子代表之出版商名稱的字串

	Publisher(String name){ this.name = name; }
	//列舉型態的建構子, 將設定列舉子代表的出版商名稱字串

	public String toString(){ return name;}
	//將列舉子的值
}

//定義處理書籍資料的Model物件
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 入門進階 – 適用JDK6.0", Publisher.KingsInfo, 590.0,
		 "P4137", "2005-5-1", true},
		{"Visual C++.NET 入門進階", Publisher.KingsInfo, 750.0, 
		 "P3237", "2003-9-1", true},
		{"Access 2003 徹底研究", Publisher.KingsInfo, 590.0, 
		 "A4023", "2004-3-1", true},
		{"Access 2003 程式設計", Publisher.KingsInfo, 660.0, 
		 "A4033", "2004-5-1", true},
		{"JSP 動態網頁入門實務", Publisher.BitC, 720.0, 
		 "W3135", "2003-12-1", true},
		{"ASP 動態網頁入門實務", Publisher.BitC, 580.0, 
		 "W4075", "2004-7-1", true},
		{"Access 2000 程式設計", Publisher.KingsInfo, 690.0,
		 "A9193", "2000-9-1", false}};
	//宣告儲存書籍資料的二維陣列

	String[] colName = 
		{"書名", "出版商", "售價", "書號", "出版日期", "是否發行"};
	//宣告儲存欄位名稱的字串

	Class[] types = new Class[] { 
		String.class, 
		Publisher.class, //列舉型態
		Double.class, String.class,
		String.class, //請注意! Date經過格式化後, 型別為String
		Boolean.class};
	//為配合TableCellEditor物件, 
	//必須宣告儲存檔案資料之型別的Class陣列

	public int getColumnCount(){ return colName.length; }
	//取得欄位的個數

	public int getRowCount(){ return bookData.length; }
	//取得資料的列數

	//取得指定欄、列位置的儲存格資料
	public Object getValueAt(int row, int col){
		return bookData[row][col];
	}

	//取得欄位名稱, 可不定義
	public String getColumnName(int column){
		return colName[column];
	}

	public Class getColumnClass(int col) { return types[col]; }
	//取得檔案某欄位資料之類別的Class物件,
	//此方法將供TableCellEditor物件運作使用

	//若允許更改表格資料必須實作此方法
	public void setValueAt(Object value, int row, int col) {
		bookData[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	//若允許編輯表格資料必須實作此方法
	public boolean isCellEditable(int row, int col) {
		return true;        
    }
}