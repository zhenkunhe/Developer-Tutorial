import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TableHeaderEX extends JFrame {

	BookData[] bookData = {
		new BookData("Visual C++.NET 入門進階", "文魁資訊", 
			750.0, "P3237", "2003-9-1", true),
		new BookData("Access 2003 徹底研究", "文魁資訊", 
			590.0, "A4023", "2004-3-1", true),
		new BookData("Java 2 入門進階 – 適用JDK6.0", "文魁資訊",
			590.0, "P4137",	"2005-5-1", true),
		new BookData("Access 2003 程式設計", "文魁資訊", 
			660.0,  "A4033", "2004-5-1", true),
		new BookData("JSP 動態網頁入門實務", "文魁資訊", 
			720.0, "W3135", "2003-12-1", true),
		new BookData("ASP 動態網頁入門實務", "文魁資訊", 
			580.0, "W4075", "2004-7-1", true),
		new BookData("Access 2000 程式設計", "文魁資訊", 
			690.0, "A9193", "2000-9-1", false)};
	//宣告儲存書籍資料的二維陣列

	BookObjectTableModel botm = new BookObjectTableModel();
	//宣告處理表格內書籍資料的BookObjectTableModel物件

	JTable tbBook = new JTable(botm);
	//以Model物件宣告建立表格的JTable元件

	TableColumnModel  tcmBook; //宣告TableColumnModel物件

	public TableHeaderEX() {

		tcmBook = tbBook.getColumnModel();
		//取得JTable元件的TableColumnModel元件

		//以匿名類別的方式定義繼承JTableHeader類別的標題列類別,
		//必須傳入表格運用的TableColumnModel元件,
		//否則無法顯示標題列的名稱
		JTableHeader header = new JTableHeader(tcmBook){

			//新增欄位
			public void columnAdded(TableColumnModelEvent e){
				System.out.println(
					"columnAdded(TableColumnModelEvent e)");
				super.columnAdded(e);
				//呼叫基礎類別的columnAdded()方法,
				//否則標題列無法正常執行
			}

			//移除欄位
			public void columnRemoved(TableColumnModelEvent e){
				System.out.println(
					"columnRemoved(TableColumnModelEvent e)");
				super.columnRemoved(e);
				//呼叫基礎類別的columnRemoved()方法,
				//否則標題列無法正常執行
			}

			//移動欄位
			public void columnMoved(TableColumnModelEvent e){
				System.out.println(
					"columnMoved(TableColumnModelEvent e)");
				super.columnMoved(e);
				//呼叫基礎類別的columnMoved()方法,
				//否則標題列無法正常執行
			}

			//欄位邊界改變
			public void columnMarginChanged(ChangeEvent e){
				System.out.println(
					"columnMarginChanged(ChangeEvent e)");
				super.columnMarginChanged(e);
				//呼叫基礎類別的columnMarginChanged()方法, 
				//否則標題列無法正常執行
			}

			//欄位選取改變
			public void columnSelectionChanged(ListSelectionEvent e){
				System.out.println(
					"columnSelectionChanged(ListSelectionEvent e)");
				super.columnSelectionChanged(e);
				//呼叫基礎類別的columnSelectionChanged()方法,
				//否則標題列無法正常執行
			}
		};

		tbBook.setTableHeader(header);
		//取得JTable元件使用的標題列

		tbBook.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//設定表格不隨視窗大小自動調整

		for(BookData elm : bookData){ //BookData物件新增至TableModel物件
			botm.addBookData(elm);
		}

		tbBook.setCellSelectionEnabled(true); //設定允許儲存格選取

		//註冊回應標題列MouseEvent事件的監聽器物件
		header.addMouseListener(new MouseAdapter(){

			//回應滑鼠按下動作
			public void mouseClicked(MouseEvent e){

				int viewIndex = tcmBook.getColumnIndexAtX(e.getX());
				//將滑鼠游標之 x 座標轉換為JTable元件的欄位畫面索引值

				//若不是按下滑鼠按鍵2次則執行選取, 並終止方法的執行
				if(e.getClickCount() != 2){
					tbBook.setColumnSelectionInterval(
						viewIndex, viewIndex);
					//設定選取目前的欄位

					tbBook.setRowSelectionInterval(
						0, tbBook.getRowCount()-1);

					return;
				}

				TableColumn col = tcmBook.getColumn(viewIndex);
				//以JTable元件的欄位畫面索引值取得代表該欄的TableColumn物件

				int modelIndex = col.getModelIndex();
				//取得TableColumn物件在TableModel元件內的索引值

				if(modelIndex < 0) //若索引值小於0, 則終止方法的執行
					return;

				col.setPreferredWidth(botm.getColumnFitWidth(modelIndex));
				//從TableModel物件取得索引值指定欄位的最適寬度,
				//然後設定為欄位的最適寬度
			}
		});

		DefaultTableColumnModel dtcBook = 
			(DefaultTableColumnModel) tbBook.getColumnModel();
		//取得JTable元件使用的ColumnModel物件

		for(int i = 0; i < dtcBook.getColumnCount() ; i++){
			tcmBook.getColumn(i).setCellRenderer(new BasicRenderer());
			//設定欄位使用的轉譯器物件
		}

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		cp.add(new JScrollPane(tbBook));		
		//將元件加入中間區域

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TableHeaderEX(); //宣告視窗框架物件	 
	}
}

class BookData { //定義儲存書籍資料的物件

	//定義書籍資料的屬性
	private String title, //書籍標題
						   publisher, //出版商
						   ID, //書號
						   p_Date; //出版日期
	private Double price; //售價
	private boolean inPrint; //是否絕版

	//建構子
	BookData(String title, String publisher, Double price, 
		String ID, String p_Date, boolean inPrint){

		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.ID = ID;
		this.p_Date =  p_Date;
		this.inPrint = inPrint;
		//設定書籍資料
	}

	//定義取得書籍資料的方法
	public String getTitle(){ return title; }
	public String getPublisher(){ return publisher; }
	public Double getPrice(){ return price; }
	public String getID(){ return ID; }
	public String getPDate(){ return p_Date; }
	public boolean getInPrint(){ return inPrint; }
}

//定義處理書籍資料的TableModel物件
class BookObjectTableModel extends AbstractTableModel {

	private Vector bookData =  new Vector();
	//定義儲存書籍資料的Vector容器

	String[] colName = 
		{"書名", "出版商", "售價", "書號", "出版日期", "是否發行"};
	//宣告儲存欄位名稱的字串

	private int[] fitWidth = {50, 50, 50, 50, 50, 50};
	//儲存欄位的最適寬度

	private int[] HAlign = {JLabel.LEFT, JLabel.LEFT, JLabel.RIGHT,
										JLabel.LEFT, JLabel.RIGHT, JLabel.RIGHT};
	//欄位資料的對齊方式

	//加入書籍資料物件
	public void addBookData(BookData bd){
		bookData.add(bd); //將物件加入Vector容器
	}

	public int getColumnCount(){ return colName.length; }
	//取得欄位的個數

	public int getRowCount(){ return bookData.size(); }
	//取得資料的列數

	//取得指定欄、列位置的儲存格資料
	public Object getValueAt(int row, int col){
		
		//判斷列索引值是否正確
		if(row < 0 || row >= getRowCount())
			return "";

		//判斷欄索引值是否正確
		if(col < 0 || col >= getColumnCount())
			return "";

		BookData bdRow = (BookData) bookData.elementAt(row);
		//以列數做為索引值取得容器的書籍資料物件

		switch(col){ //以欄索引判斷欲取得的書籍資料
			case 0:
				return bdRow.getTitle(); //取得書籍名稱
			case 1:
				return bdRow.getPublisher(); //取得出版商
			case 2:
				return bdRow.getPrice(); //取得售價
			case 3:
				return bdRow.getID(); //取得書號
			case 4:
				return bdRow.getPDate(); //取得出版日期
			case 5:
				return bdRow.getInPrint();
		}

		return "";
	}

	//取得欄位名稱, 可不定義
	public String getColumnName(int column){
		return colName[column];
	}

	//取得欄位的最適寬度
	public int getColumnFitWidth(int column){
		return fitWidth[column];
	}

	//設定欄位的最適寬度
	public void setColumnFitWidth(int column, int width){
		fitWidth[column] = width;
	}

	//取得欄位文字的對齊方式
	public int getTextAlignment(int column){
		return HAlign[column];
	}
}

//以繼承DefaultTableCellRenderer類別的方式, 
//自訂處理儲存格顯示的轉譯器物件
class BasicRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(
							JTable table, //使用轉譯器物件的表格元件
							Object value, //轉譯器欲處理之表格儲存格的值
							boolean isSelected, //被處理儲存格是否被選取
							boolean hasFocus, //被選取儲存格是否擁有焦點
							int row, //被選取儲存格所在位置的列數
							int column){ //被選取儲存格所在位置的欄數

		super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
		//呼叫基礎類別的getTreeCellRendererComponent()方法

		//依照選取狀態設定儲存格的背景與前景顏色
		if(isSelected){
			setBackground(Color.lightGray);
			setForeground(Color.darkGray);
		}
		else{
			setBackground(Color.white);
			setForeground(Color.black);
		}

		if(hasFocus) //設定擁有焦點時的背景顏色
			setBackground(Color.pink);

		int modelIndex = table.convertColumnIndexToModel(column);
		//將欄位畫面索引轉換為欄位模型索引

		BookObjectTableModel  botm = 
			(BookObjectTableModel) table.getModel();
		//取得儲存JTable元件顯示之書籍資料的TableModel物件

		setHorizontalAlignment(botm.getTextAlignment(modelIndex));
		//從TableModel物件取得指定欄位的水平對齊方式

		int colWidth = botm.getColumnFitWidth(modelIndex);
		//從TableModel物件取得目前欄位設定的最適寬度

		int lbWidth = new Double(getPreferredSize().getWidth()).intValue() + 5;
		//取得目前顯示的欄位寬度

		//若顯示欄位寬度大於設定的最適寬度, 
		//則將欄位寬度設定為新的最適寬度
		if(lbWidth> colWidth){
			botm.setColumnFitWidth(modelIndex, lbWidth);
		}

		return this;
	}
}