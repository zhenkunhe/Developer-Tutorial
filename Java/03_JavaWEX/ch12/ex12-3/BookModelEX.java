import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import java.awt.*;

public class BookModelEX extends JFrame {

	public BookModelEX() {

		JTable tbBook = new JTable(new BookTableModel());
		//以Model物件宣告建立表格的JTable元件

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第一欄的TableColumn物件

		colTitle.setPreferredWidth(200); //設定欄位的喜好寬度為150

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		cp.add(new JScrollPane(tbBook));		
		//將元件加入中間區域

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new BookModelEX(); //宣告視窗框架物件	 
	}
}

//定義處理書籍資料的Model物件
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 入門進階 – 適用JDK6.0", "文魁資訊", 590.0,
		 "P4137",	"2005-5-1"},
		{"Visual C++.NET 入門進階", "文魁資訊", 750.0, 
		 "P3237", "2003-9-1"},
		{"Access 2003 徹底研究", "文魁資訊", 590.0, 
		 "A4023", "2004-3-1"},
		{"Access 2003 程式設計", "文魁資訊", 660.0, 
		 "A4033",	"2004-5-1"},
		{"JSP 動態網頁入門實務", "文魁資訊", 720.0, 
		 "W3135", "2003-12-1"},
		{"ASP 動態網頁入門實務", "文魁資訊", 580.0, 
		 "W4075", "2004-7-1"}};
	//宣告儲存書籍資料的二維陣列

	String[] colName = {"書名", "出版商", "售價", "書號", "出版日期"};
	//宣告儲存欄位名稱的字串

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
}