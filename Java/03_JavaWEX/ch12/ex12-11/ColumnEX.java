import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import javax.swing.event.*;
//引用包含CellEditorListener的套件

import javax.swing.text.NumberFormatter;
//引用NumberFormatter類別

import java.awt.*;
import java.awt.event.*;

import java.text.NumberFormat; //引用NumberFormat類別

public class ColumnEX extends JFrame {

	BookTableModel btmBook = new BookTableModel();
	//宣告儲存表格欄位資料的TableModel物件

	JTable tbBook = new JTable(btmBook);
	//以TableModel物件宣告建立表格的JTable元件

	public ColumnEX() {

		tbBook.setRowSelectionInterval(0, 0); //設定選取第1列的資料

		TableColumnModel tcmBook = tbBook.getColumnModel();
		//取得表格欄位資料的TableColumnModel物件

		//註冊監聽TableColumnModel物件的TableColumnModelEvent事件
		tcmBook.addColumnModelListener(
			new TableColumnModelListener(){

			//新增欄位
			public void columnAdded(TableColumnModelEvent e){
				System.out.println(
					"columnAdded(TableColumnModelEvent e) " +
					" formIndex = " + e.getFromIndex() + 
					" toIndex = " + e.getToIndex());
			}

			//移除欄位
			public void columnRemoved(TableColumnModelEvent e){
				System.out.println(
					"columnRemoved(TableColumnModelEvent e)" +
					" formIndex = " + e.getFromIndex() + 
					" toIndex = " + e.getToIndex());
			}

			//移動欄位
			public void columnMoved(TableColumnModelEvent e){
				System.out.println(
					"columnMoved(TableColumnModelEvent e)" +
					" formIndex = " + e.getFromIndex() + 
					" toIndex = " + e.getToIndex());
			}

			//改變欄位邊界
			public void columnMarginChanged(ChangeEvent e){
				System.out.println(
					"columnMarginChanged(ChangeEvent e)");
			}

			//欄位選取改變
			public void columnSelectionChanged(
											ListSelectionEvent e){
				System.out.println(
					"void columnSelectionChanged(ListSelectionEvent e)");
			}
		});

		TableColumn colTitle = tcmBook.getColumn(0);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第1欄的TableColumn物件

		colTitle.setPreferredWidth(200);
		//設定欄位的喜好寬度為200

		colTitle.setCellRenderer(new BasicRenderer());
		//設定使用BasicRenderer轉譯器物件

		JComboBox cmbPublisher = new JComboBox();
		cmbPublisher.addItem("文魁資訊");
		cmbPublisher.addItem("位元文化");
		cmbPublisher.addItem("勁魁資訊");
		cmbPublisher.addItem("其他");
		//建立供出版商欄使用的組合方塊

		TableColumn colPublisher =  tcmBook.getColumn(1);
		//取得控制出版商欄的TableColumn
			
		colPublisher.setCellEditor(
			new DefaultCellEditor(cmbPublisher));
		//設定出版商欄使用組合方塊做為編輯器

		colPublisher.setCellRenderer(new BasicRenderer());
		//設定使用BasicRenderer轉譯器物件

		colPublisher.setPreferredWidth(95);
		//設定欄位的喜好寬度為95

		JFormattedTextField ftfPrice = 
			new JFormattedTextField(
				NumberFormat.getNumberInstance());
		//宣告使用數字格式的格式化文字欄

		NumberFormatter nfrPrice = 
			(NumberFormatter)ftfPrice.getFormatter();
		//取得格式化文字欄使用的格式物件

		nfrPrice.setMinimum(new Double(0.0));
		//設定允許輸入之數字最小值

		FormattedCellEditor fcePrice= 
			new FormattedCellEditor(ftfPrice);
		//宣告格式化編輯器物件

		TableColumn colPrice =  tcmBook.getColumn(2);
		//取得控制售價欄的TableColumn

		colPrice.setCellEditor(fcePrice);
		//設定售價欄使用格式化的編輯器物件

		colPrice.setCellRenderer(new PriceRenderer());
		//設定使用BasicRenderer轉譯器物件

		TableColumn colID =  tcmBook.getColumn(3);
		//取得控制書號欄的TableColumn

		colID.setCellRenderer(new BasicRenderer());
		//設定使用BasicRenderer轉譯器物件

		TableColumn colPDate =  tcmBook.getColumn(4);
		//取得控制書號欄的TableColumn

		colPDate.setCellRenderer(new BasicRenderer());
		//設定使用BasicRenderer轉譯器物件

		TableColumn colInPrint =  tcmBook.getColumn(5);
		//取得控制是否發行欄的TableColumn

		BooleanRenderer brInPrint = new BooleanRenderer();

		colInPrint.setCellRenderer(brInPrint);
		//設定使用BasicRenderer轉譯器物件

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
		new ColumnEX(); //宣告視窗框架物件	 
	}
}

//定義處理書籍資料的TableModel物件
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 入門進階 – 適用JDK5.0", "文魁資訊", 590.0,
		 "P4137", "2005-5-1", true},
		{"Visual C++.NET 入門進階", "位元文化", 750.0, 
		 "P3237", "2003-9-1", true},
		{"Access 2003 徹底研究", "文魁資訊", 590.0, 
		 "A4023", "2004-3-1", true},
		{"Access 2003 程式設計", "文魁資訊", 660.0, 
		 "A4033", "2004-5-1", true},
		{"JSP 動態網頁入門實務", "文魁資訊", 720.0, 
		 "W3135", "2003-12-1", true},
		{"ASP 動態網頁入門實務", "文魁資訊", 580.0, 
		 "W4075", "2004-7-1", true},
		{"Access 2000 程式設計", "文魁資訊", 690.0,
		 "A9193", "2000-9-1", false}};
	//宣告儲存書籍資料的二維陣列

	String[] colName = 
		{"書名", "出版商", "售價", "書號", "出版日期", "是否發行"};
	//宣告儲存欄位名稱的字串

	Class[] types = new Class[] { 
		String.class, 
		String.class, //列舉型態
		Double.class, String.class,
		String.class, //請注意! Date經過格式化後, 型別為String
		Boolean.class};
	//為配合TableCellEditor物件, 
	//必須宣告儲存檔案資料之型別的Class陣列

	private int[] HAlign = {JLabel.LEFT, JLabel.LEFT, JLabel.RIGHT,
									   JLabel.LEFT, JLabel.RIGHT, JLabel.CENTER};
	//欄位資料的對齊方式

	private int[] colWidth = {200, 100, 50, 50, 50, 50};
	//各欄位的寬度

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

	//取得欄位文字的對齊方式
	public int getTextAlignment(int column){
		return HAlign[column];
	}

	//取得欄位的最適寬度
	public int getColumnWidth(int column){
		return colWidth[column];
	}
}

//以繼承DefaultCellEditor類別的方式, 定義
class FormattedCellEditor extends DefaultCellEditor {
	FormattedCellEditor(final JFormattedTextField formattedTextField){ //建構子

		super(formattedTextField); //呼叫基礎類別的建構子

		formattedTextField.removeActionListener(delegate);
		//移除監聽ActionEvent事件的delegate監聽器物件

		//以匿名類別的方式定義、宣告新的EditorDelegate物件
		delegate = new EditorDelegate(){

			//覆蓋設定delegate物件值的setValue()方法
			public void setValue(Object value){
				formattedTextField.setValue(value);
			}

			//覆蓋取得delegate物件編輯值的getCellEditorValue()方法
			public Object getCellEditorValue(){
				return formattedTextField.getValue();
			}
		};

		formattedTextField.addActionListener(delegate);
		//註冊監聽ActionEvent事件的delegate監聽器物件

		formattedTextField.setBorder(null);
		//設定不使用框線
	}
}

//以繼承JCheckBox類別與實作TableCellRenderer介面的方式
//定義顯示Boolean型別資料的轉譯器物件
class BooleanRenderer extends JCheckBox
							implements TableCellRenderer {

	boolean repaint = false; //用於判斷是否執行重繪動作的屬性

	public Component getTableCellRendererComponent(
							JTable table, //使用轉譯器物件的表格元件
							Object value, //轉譯器欲處理之表格儲存格的值
							boolean isSelected, //被處理儲存格是否被選取
							boolean hasFocus, //被選取儲存格是否擁有焦點
							int row, //被選取儲存格所在位置的列數
							int column){ //被選取儲存格所在位置的欄數
	
		DefaultTableColumnModel tcmBook = 
			(DefaultTableColumnModel) table.getColumnModel();
		//取得JTable元件使用的ColumnModel物件

		TableColumn col = tcmBook.getColumn(column);
		//取得JTable元件指定索引值所代表的欄之TableColumn物件

		int modelIndex = col.getModelIndex();
		//取得TableColumn物件在Model物件的欄位模型索引

		BookTableModel  botm = 
			(BookTableModel) table.getModel();
		//取得儲存JTable元件顯示之書籍資料的Model物件

		setHorizontalAlignment(botm.getTextAlignment(modelIndex));
		//從Model物件取得指定欄位的水平對齊方式

		setSelected((Boolean)value);
		//設定核取方塊是否選取

		//判斷是否擁有焦點, 
		//是則設定背景顏色為粉紅色
		//重繪同列的其他儲存格
		if(hasFocus){
			setBackground(Color.pink);
			 //設定擁有焦點時以粉紅色為背景顏色

			if(!repaint){
				table.repaint(); //重繪表格
				repaint = true; //設定將執行重繪
			}
			else
				repaint = false; //重設重繪控制

			return this;
		}

		if(!(Boolean)value){			
			setBackground(Color.blue);
			//若設定值為true, 則設定背景顏色為藍色
		}
		else{

			//依照選取狀態設定儲存格的背景與前景顏色
			if(isSelected){
				setBackground(Color.lightGray);
				setForeground(Color.darkGray);
			}
			else{
				setBackground(Color.white);
				setForeground(Color.black);
			}
		}

		return this;
	}
}

//以繼承DefaultTableCellRenderer類別的方式
//定義處理各類型資料的BasicRenderer轉譯器類別
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

		DefaultTableColumnModel tcmBook = 
			(DefaultTableColumnModel) table.getColumnModel();
		//取得JTable元件使用的ColumnModel物件

		TableColumn col = tcmBook.getColumn(column);
		//取得JTable元件指定索引值所代表的欄之TableColumn物件

		int modelIndex = col.getModelIndex();
		//取得TableColumn物件在Model物件的索引值

		BookTableModel  botm = 
			(BookTableModel) table.getModel();
		//取得儲存JTable元件顯示之書籍資料的Model物件

		setHorizontalAlignment(botm.getTextAlignment(modelIndex));
		//從TableModel物件取得指定欄位的水平對齊方式

		//設定擁有焦點時的背景顏色, 並終止方法的執行
		if(hasFocus){
			setBackground(Color.pink);
			return this;
		}

		if(!(Boolean)botm.getValueAt(row, 5)){
			setBackground(Color.blue);
			//若是否發行欄位被選取, 則設定背景顏色為藍色
		}
		else{

			//依照選取狀態設定儲存格的背景與前景顏色
			if(isSelected){
				setBackground(Color.lightGray);
				setForeground(Color.darkGray);
			}
			else{
				setBackground(Color.white);
				setForeground(Color.black);
			}
		}

		return this;
	}
}

//定義PriceRenderer類別繼承自BasicRenderer類別
class PriceRenderer extends BasicRenderer {

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

		Double price = (Double) value; //取得售價

		setForeground((price > 700.0 ? Color.red : Color.black));
		//判斷是否大於700, 是則設定背景顏色為紅色, 
		//反之設定為黑色

		return this;
	}
}