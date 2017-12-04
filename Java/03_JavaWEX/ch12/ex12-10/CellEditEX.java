import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import javax.swing.event.*;
//引用包含CellEditorEvent與CellEditorListener的套件

import java.util.EventObject; //引用代表事件的EventObject物件
import java.util.Locale; //引用Locale類別
import javax.swing.text.*;  //提供DefaultFormatter, 及其子類別的套件
import java.text.NumberFormat; //引用NumberFormat類別
import java.awt.*;
import java.awt.event.*;

public class CellEditEX extends JFrame {

	JTable tbBook = new JTable(new BookTableModel());
	//以TableModel物件宣告建立表格的JTable元件

	public CellEditEX() {

		JFormattedTextField ftfPrice = 
			new JFormattedTextField(NumberFormat.getNumberInstance());
		//宣告使用數字格式的格式化文字欄

		NumberFormatter nfrPrice = 
			(NumberFormatter)ftfPrice.getFormatter();
		//取得格式化文字欄使用的格式物件

		nfrPrice.setMinimum(new Double(0.0));
		//設定允許輸入之數字最小值

		FormattedCellEditor fcePrice= new FormattedCellEditor(ftfPrice);
		//宣告格式化編輯器物件

		tbBook.setDefaultEditor(Double.class, fcePrice);
		//設定Double型別使用格式化的編輯器物件

		tbBook.setDefaultEditor(
			String.class, new PublisherCellEditor());
		//設定處理String類別資料使用的轉譯器物件

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
		setSize(580, 147);
		setVisible(true);
	}

	public static void main(String args[]) {
		new CellEditEX(); //宣告視窗框架物件	 
	}
}

//定義處理書籍資料的Model物件
class BookTableModel extends AbstractTableModel {

	Object[][] bookData = {
		{"Java 2 入門進階 – 適用JDK6.0", "文魁資訊", 590.0,
		 "P4137",	"2005-5-1", true},
		{"Visual C++.NET 入門進階", "文魁資訊", 750.0, 
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
		String.class, String.class, Double.class, String.class,
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

//以繼承AbstractCellEditor類別實作TableCellEditor介面的方式
//宣告編輯器類別
class PublisherCellEditor extends AbstractCellEditor
											implements TableCellEditor {

	String[] publishers = {"文魁資訊", "位元文化", "勁魁資訊"};
	//宣告組合方塊的選項

	JComboBox cmbEditor = new JComboBox(publishers);
	//宣告組合方塊

	JTextField tfEditor = new JTextField(); //宣告文字欄

	Component curComponent; //目前使用的元件

	//判斷是否可編輯
	public boolean isCellEditable(EventObject anEvent){

		boolean blRet = false;

		//若無引發事件或引發事件的型態為滑鼠事件, 
		//且按下滑鼠按鍵兩下, 則編輯器可編輯
		if((anEvent == null) || 
			(anEvent instanceof MouseEvent) && 
				(((MouseEvent)anEvent).getClickCount() == 2))
			blRet = true;

		System.out.println("isCellEditable() 傳回值 : " + blRet);

		return blRet;
	}

	//取得目前編輯器物件的值
	public Object getCellEditorValue() {

		Object currentValue; //目前輸入元件的資料

		//依照目前使用的編輯元件取得設定值
		if(curComponent == cmbEditor){
			currentValue = cmbEditor.getSelectedItem();
			//取得目前組合方塊的選取值
		}
		else{
			currentValue = tfEditor.getText();
			//取得目前文字欄的輸入值
		}

		System.out.println("getCellEditorValue() 傳回值 : " 
			+ currentValue);

		return currentValue;
	}
	
	public Component getTableCellEditorComponent(
										JTable table, //運用編輯器物件的表格物件
										Object value, //儲存格的值
										boolean isSelected, //儲存格是否被選取
										int row, //儲存格的列索引
										int column){ //儲存格的欄索引		

		//判斷欄位名稱是否為"出版商", 是則使用組合方塊為編輯器
		if(table.getColumnName(column).equals("出版商")){
			cmbEditor.setSelectedItem(value); //設定組合方塊的選取值
			cmbEditor.setBorder(null); //設定不使用框線
			curComponent = cmbEditor; //設定目前使用編輯器為組合方塊
		}
		else{
			tfEditor.setText(value.toString()); //設定文字欄的內容
			tfEditor.setBorder(null); //設定不使用框線
			curComponent = tfEditor; //設定目前使用編輯器為文字欄
		}
		System.out.println("getTableCellEditorComponent() 傳回值 : " 
			+	curComponent.getClass().getName());

		return curComponent; //傳回編輯器
	}

	public boolean stopCellEditing(){ //覆蓋停止編輯動作的方法

		boolean blRet = super.stopCellEditing();
		//呼叫基礎類別的stopCellEditing()方法

		System.out.println("stopCellEditing() 傳回值 : " + blRet);

		return blRet;
		//呼叫基礎類別的stopCellEditing()方法, 並傳回值
	}
}