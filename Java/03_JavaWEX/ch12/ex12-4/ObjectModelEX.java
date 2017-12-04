import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import java.awt.*;
import java.util.*;

public class ObjectModelEX extends JFrame {

	BookData[] bookData = {
		new BookData("Java 2 入門進階 – 適用JDK6.0", "文魁資訊",
			590.0, "P4137",	"2005-5-1", false),
		new BookData("Visual C++.NET 入門進階", "文魁資訊", 
			750.0, "P3237", "2003-9-1", false),
		new BookData("Access 2003 徹底研究", "文魁資訊", 
			590.0, "A4023", "2004-3-1", false),
		new BookData("Access 2003 程式設計", "文魁資訊", 
			660.0,  "A4033", "2004-5-1", false),
		new BookData("JSP 動態網頁入門實務", "文魁資訊", 
			720.0, "W3135", "2003-12-1", false),
		new BookData("ASP 動態網頁入門實務", "文魁資訊", 
			580.0, "W4075", "2004-7-1", false),
		new BookData("Access 2000 程式設計", "文魁資訊", 
			690.0, "A9193", "2000-9-1", true)};
	//宣告儲存書籍資料的二維陣列

	String[] colName = {"書名", "出版商", "售價", "書號", "出版日期"};
	//宣告儲存欄位名稱的字串

	public ObjectModelEX() {

		BookObjectTableModel botm = new BookObjectTableModel(colName);
		//以欄位名稱宣告BookObjectTableModel物件
		
		for(BookData elm : bookData)
			botm.addBookData(elm);

		JTable tbBook = new JTable(botm);
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
		setSize(550, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new ObjectModelEX(); //宣告視窗框架物件	 
	}
}

class BookData { //定義儲存書籍資料的物件

	//定義書籍資料的屬性
	private String title, //書籍標題
						   publisher, //出版商
						   ID, //書號
						   p_Date; //出版日期
	private Double price; //售價
	private boolean out_of_print; //是否絕版

	//建構子
	BookData(String title, String publisher, Double price, 
		String ID, String p_Date, boolean out_of_print){

		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.ID = ID;
		this.p_Date =  p_Date;
		this.out_of_print = out_of_print;
		//設定書籍資料
	}

	//定義取得書籍資料的方法
	public String getTitle(){ return title; }
	public String getPublisher(){ return publisher; }
	public Double getPrice(){ return price; }
	public String getID(){ return ID; }
	public String getPDate(){ return p_Date; }
	public boolean getOutOfPrint(){ return out_of_print; }
}

//定義處理書籍資料的Model物件
class BookObjectTableModel extends AbstractTableModel {

	private Vector bookData =  new Vector();
	//定義儲存書籍資料的Vector容器

	String[] colName;
	//宣告儲存欄位名稱的字串陣列

	BookObjectTableModel(String[] cols){ //建構子, 將傳入欄位標題字串
		colName = cols;
	}

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
		
			//判斷列索引值是否正確, 不正確則傳回空字串
			if(row < 0 || row >= getRowCount())
				return "";
	
			//判斷欄索引值是否正確, 不正確則傳回空字串
			if(col < 0 || col >= getColumnCount())
				return "";
	
			BookData bdRow = (BookData) bookData.elementAt(row);
			//以列數做為索引值取得容器的書籍資料物件
	
			switch(col){ //以欄索引判斷欲取得的書籍資料
			case 0: //第1欄
				return bdRow.getTitle(); //取得標題
			case 1: //第2欄
				return bdRow.getPublisher(); //取得出版商
			case 2:  //第3欄
				return bdRow.getPrice(); //取得售價
			case 3: //第4欄
				return bdRow.getID(); //取得書號
			case 4: //第5欄
				return bdRow.getPDate(); //取得出版日期
			case 5: //第6欄
				return bdRow.getOutOfPrint(); //取得是否絕版
		}

		return ""; //若switch敘述無法取得指定欄位的值, 將傳回空字串
	}


	//取得欄位名稱, 可不定義
	public String getColumnName(int column){
		return colName[column];
	}
}