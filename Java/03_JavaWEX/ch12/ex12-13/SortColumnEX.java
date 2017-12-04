import javax.swing.*;  //引用套件
import javax.swing.border.EmptyBorder; //引用EmptyBorder類別
import javax.swing.event.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat; //引用SimpleDateFormat類別

public class SortColumnEX extends JFrame {

	static final File DEFAULT_PATH = new File("C:\\");
	//預設顯示的磁碟路徑

	JComboBox cmbHardDrive = new JComboBox();
	//宣告控制顯示磁碟路徑的組合方塊

	FileTableModel ftmDir = new FileTableModel(DEFAULT_PATH);
	//宣告列出指定路徑之資料夾內檔案之TabelModel物件

	JTable tbDir = new JTable(ftmDir);
	//以Model物件宣告建立表格的JTable元件

	JLabel lbCurPath = new JLabel(DEFAULT_PATH.getPath());
	//顯示目前檢視的路徑

	public SortColumnEX() {

		tbDir.setDefaultRenderer(String.class, new FileNameRenderer());
		tbDir.setDefaultRenderer(Boolean.class, new BasicRenderer());
		tbDir.setDefaultRenderer(Long.class, new BasicRenderer());
		//設定處理各類型資料使用的轉譯器物件

		File[] roots = File.listRoots(); //取得電腦內的磁碟路徑

		for(File file: roots)
			cmbHardDrive.addItem(file);
			//將磁碟路徑的File物件加入組合方塊

		cmbHardDrive.setSelectedItem(DEFAULT_PATH);
		//設定預設選取的磁碟路徑

		//註冊回應ItemEvent事件的監聽器物件
		cmbHardDrive.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				//判斷是否為選取動作
				if(e.getStateChange() == e.SELECTED){
					ftmDir.setCurrentPath((File)e.getItem());
					//取得選取的節點, 
					//並設定為表格欲顯示檔案內容的資料夾路徑

					tbDir.updateUI(); //更新表格的畫面
				}
			}
		});

		//以匿名類別的方式, 定義、註冊監聽TableModel物件
		//TableModelEvent事件的監聽器物件
		ftmDir.addTableModelListener(new TableModelListener(){
			public void tableChanged(TableModelEvent e){
				File curDir = 
					((FileTableModel) e.getSource()).getCurrentPath();
				//取得目前瀏覽的資料夾路徑

				lbCurPath.setText(curDir.getPath());
				//設定標籤顯示目前資料夾的路徑
			}
		});

		//註冊回應JTable元件的MouseEvent事件的監聽器物件
		tbDir.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){

				if(e.getClickCount() == 2){ //判斷滑鼠是否連按二下

					//若滑鼠點選的不是第1欄則將終止方法的執行
					if(tbDir.columnAtPoint(e.getPoint()) != 0)
						return;

					int selRow = tbDir.rowAtPoint(e.getPoint());
					//取得滑鼠點選位置所在之資料的列索引

					String strFile = (String) ftmDir.getValueAt(selRow, 0);
					//取得被點選資料列的第1欄的值, 其內容為檔案名稱

					File selFile = new File(ftmDir.getCurrentPath(), strFile);
					//建立被選取資料所代表的檔案

					if(selFile.isDirectory()){ //判斷檔案是否為資料夾
						ftmDir.setCurrentPath(selFile);
						//設定FileTableModel物件將顯示此資料夾內的檔案

						tbDir.updateUI(); //更新表格的畫面
					}
				}
			}
		});

		DefaultTableColumnModel dtcmDir = 
			(DefaultTableColumnModel) tbDir.getColumnModel();
		//取得TableColumnModel物件

		TableColumn colTitle = dtcmDir.getColumn(0);
		//取得控制第1欄的TableColumn物件

		colTitle.setPreferredWidth(220); //設定欄位的喜好寬度為220

		TableColumn colDate = dtcmDir.getColumn(3);
		colDate.setPreferredWidth(150); //設定欄位的喜好寬度為150

		//以for迴圈設定各欄位標題使用的轉譯器物件
		for(int i=0; i < dtcmDir.getColumnCount(); i++){
			TableColumn tc = dtcmDir.getColumn(i);
			//取得代表表格各欄位的TableColumn物件

			tc.setHeaderRenderer(createHeaderCellRenderer());
			//呼叫createHeaderCellRenderer()方法
			//產生標題使用的轉譯器物件
		}

		JTableHeader header = tbDir.getTableHeader();
		//取得JTable元件使用的標題列

		//註冊回應標題列MouseEvent事件的監聽器物件
		header.addMouseListener(new MouseAdapter(){

			//回應滑鼠按下動作
			public void mouseClicked(MouseEvent e){

				TableColumnModel  tcmDir = tbDir.getColumnModel();
				//取得JTable元件的TableColumnModel元件

				int viewIndex = 
							tcmDir.getColumnIndexAtX(e.getX());
				//將滑鼠游標之 x 座標轉換為JTable元件的畫面欄位索引值

				TableColumn col = 
							tcmDir.getColumn(viewIndex);
				//以JTable元件的欄索引值取得該欄的TableColumn物件

				int modelIndex = col.getModelIndex();
				//取得TableColumn物件在TableModel元件內的索引值

				if(modelIndex < 0) //若索引值小於0, 則終止方法的執行
					return;

				boolean asc = false;
				//預設值為false, 預設以遞減方式排序

				//判斷滑鼠點選欲執行排序的欄位
				//是否原本就正在排序
				//是則變更原先的排序方式
				if(ftmDir.getSortColumn() == modelIndex){
					asc = !ftmDir.isSortAscend();
					//變更排序方式, 
					//原先是遞減則改為遞增, 遞增則改成遞減
				}

				ftmDir.sortData(modelIndex, asc);
				//執行指定欄位模式索引的排序
			}
		});

		JButton btnUp = new JButton("回上一層");
		//宣告切換至目前檢視路徑上一層資料夾的指令按鈕

		//宣告、註冊回應指令按鈕ActionEvent事件的監聽器物件
		btnUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File curPath = ftmDir.getCurrentPath();
				//取得目前FileTableModel物件顯示的路徑

				//判斷目前檢視路徑是否有上一層資料夾
				if(curPath.getParentFile() != null){
					ftmDir.setCurrentPath(curPath.getParentFile());
					//設定FileTableModel物件
					//將顯示上一層資料夾內的檔案

					tbDir.updateUI(); //更新表格的畫面
				}
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("磁碟 : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cmbHardDrive); //加入組合方塊

		Box bxPath = new Box(BoxLayout.X_AXIS);
		bxPath.add(new JLabel("目前檢視路徑 : "));
		bxPath.add(Box.createHorizontalStrut(5));
		bxPath.add(lbCurPath);
		bxPath.add(Box.createHorizontalGlue());
		bxPath.add(btnUp);

		Box bxNorth = new Box(BoxLayout.Y_AXIS);
		bxNorth.add(bxOper);
		bxNorth.add(Box.createVerticalStrut(5));
		bxNorth.add(bxPath);

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		cp.add(bxNorth, BorderLayout.NORTH);	
		cp.add(new JScrollPane(tbDir));		
		//將元件加入內容面版

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 250);
		setVisible(true);
	}

	//建立標題儲存格的轉譯器
	protected TableCellRenderer createHeaderCellRenderer(){

		//以匿名類別的方式定義繼承DefaultTableCellRenderer類別
		//的轉譯器類別, 並傳回物件
		return new DefaultTableCellRenderer(){

			private final ImageIcon ICON_ASCEND = 
				new ImageIcon("icon/asc.jpg");
			//定義顯示於欄位標頭, 代表執行遞減排序的圖示

			private final ImageIcon ICON_DESCEND = 
				new ImageIcon("icon/dec.jpg");
			//定義顯示於欄位標頭, 代表執行遞減排序的圖示

			public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int col){

				super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, col);
				//呼叫基礎類別的getTableCellRendererComponent()方法

				if(table != null){ //table參數不可為null
					JTableHeader header = table.getTableHeader();
					//取得標題列物件

					if(header != null){ //判斷標題列物件不可為null
						setForeground(header.getForeground());
						setBackground(header.getBackground());
						//設定轉譯器物件傳出元件
						//使用標題列物件的背景顏色與前景顏色

						setFont(header.getFont());
						//設定傳出元件使用標題列的字型
					}

					TableColumn tc = tbDir.getColumnModel().getColumn(col);
					//以JTable元件的欄索引值取得代表該欄的TableColumn物件

					int modelIndex = tc.getModelIndex();
					//取得TableColumn物件在Model元件內的索引值

					//設定為執行排序的欄位標頭不顯示圖示
					if(ftmDir.getSortColumn() != modelIndex){
						setIcon(null); //設定不顯示圖示
					}
					else{
						setIcon((ftmDir.isSortAscend() 
									? ICON_ASCEND: ICON_DESCEND));
						//依照排序方式設定顯示排序方式的圖示
						setHorizontalTextPosition(JLabel.LEFT);
						//設定圖示與文字的對齊方式
					}
				}

				setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				//設定使用UIManager預設的標題列儲存格框線
				return this;
			}
		};		
	}

	public static void main(String args[]) {
		new SortColumnEX(); //宣告視窗框架物件	 
	}
}

//定義處理檔案資料的TableModel物件
class FileTableModel extends AbstractTableModel {

	private File dir;
	private ArrayList<File> files = new ArrayList<File>();
	//儲存資料夾內檔案的陣列

	String[] colName = {"名稱", "大小", "是否為檔案", 
									 "最後修改日期", "可讀取", "可修改"};
	//宣告儲存欄位名稱的字串

	Class[] types = new Class[] { 
		String.class, Long.class, Boolean.class, 
		String.class, //請注意! Date型別資料經過格式化後, 型別為String
		Boolean.class, Boolean.class};
	//為配合TableCellRenderer物件, 
	//必須宣告儲存檔案資料之型別的Class陣列

	SimpleDateFormat sdf = 
		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	//定義最後修改日期使用的日期格式

	private int sortCol = -1; //執行排序的欄位畫面索引
	private boolean sortAsc = false; //儲存排序方式

	FileTableModel(){
		this(new File(".")); //呼叫FileTableModel(String path)建構子
	}

	FileTableModel(File path){
		setCurrentPath(path); //設定欲檢視的資料夾
	}

	public int getColumnCount(){ return colName.length; }
	//取得欄位的個數

	public int getRowCount(){ return files.size(); }
	//取得資料的列數

	 public Class getColumnClass(int col) { return types[col]; }
	//取得檔案某欄位資料之類別的Class物件,
	//此方法將供TableCellRenderer物件運作使用

	//取得指定欄、列位置的儲存格資料
	public Object getValueAt(int row, int col){
		
		//判斷列索引值是否正確
		if(row < 0 || row >= getRowCount())
			return "";

		//判斷欄索引值是否正確
		if(col < 0 || col >= getColumnCount())
			return "";

		File file = (File) files.get(row);
		//以列數做為索引值取得檔案物件

		switch(col){ //以欄索引判斷欲取得的檔案資料
			case 0:
				return file.getName(); //取得檔案名稱
			case 1:
				return file.length(); //取得檔案大小
			case 2:
				return file.isFile(); //判斷是否為檔案
			case 3:
				return sdf.format(new Date(file.lastModified()));
							//最後修改日期
			case 4:
				return file.canRead(); //是否可讀取
			case 5:
				return file.canWrite(); //是否可寫入
		}

		return "";
	}

	//取得欄位名稱, 可不定義
	public String getColumnName(int column){
		return colName[column];
	}

	//設定目前檢視之資料夾的路徑
	public void setCurrentPath(File dir) {
		if(!dir.isDirectory()){
			System.err.println("路徑必須代表資料夾");
			return;
		}

		this.dir  = dir; //設定欲檢視的資料夾

		//若儲存檔案的ArrayList容器不是空的, 則清除
		if(!files.isEmpty()){
			files.clear(); //清空ArrayList容器
			sortCol = -1; //重設控制欄位排序的屬性
			sortAsc = false;
		}

		//列出資料夾內的所有檔案, 並加入ArrayList容器
		for(File elm : dir.listFiles())
			files.add(elm);

		fireTableChanged(new TableModelEvent(this));
		//觸發表格發生改變的事件, 並丟出TableModelEvent物件
	}

	//取得目前檢視之資料夾的路徑
	public File getCurrentPath(){
		return dir;
	}

	//取得執行排序的欄位
	public int getSortColumn(){
		return sortCol;
	}

	//取得是否執行遞增排序
	public boolean isSortAscend(){
		return sortAsc;
	}

	//執行資料排序
	public void sortData(int col, boolean asc){
		sortCol = col;
		sortAsc = asc;

		Collections.sort(files, new FileComparator<File>(col, asc));
		//以FileComparator比較物件執行檔案資料的排序
	}
}

//定義處理檔案各種資料排序的比較類別
class FileComparator<T extends File> 
						implements Comparator<T> {
	private int col; //欲執行排序欄位的索引
	private boolean asc; //遞增或遞減

	public FileComparator(int col, boolean asc){ //建構子
		this.col = col;
		this.asc = asc;
	}

	public int compare(T one, T another){ //執行比對的compare()方法

		int result = 0;

		switch(col){
		case 0 :
			result = one.getName().compareTo(another.getName());
			//比對檔案名稱
			break;
		case 1 :
			result = new Long(one.length() 
							- another.length()).intValue();
			//比對檔案長度
			break;
		case 2 :
			result = (one.isFile() ? -1 : 1);
			//比對是否為檔案
			break;
		case 3 :
			result = new Long(one.lastModified()
							- another.lastModified()).intValue();
			//比對最後修改日期
			break;
		case 4 :
			result = (one.canRead() ? -1 : 1);
			//比對是否可讀取
			break;
		case 5 :
			result = (one.canWrite() ? -1 : 1);
			//比對是否可寫入
			break;
		}

		if(asc)	//判斷是否為遞增, 是則傳回執行結果
			return result;

		return -result; //若為遞減時, 則傳回相反的執行結果
	}
}

//定義轉譯器類別
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

		return this;
	}
}

//定義處理檔案名稱資料的轉譯器類別
class FileNameRenderer extends BasicRenderer {

	private final static ImageIcon ICON_CLOSED_FOLDER = 
		new ImageIcon("icon/ClosedFolder.jpg");
	//定義代表資料夾圖示的靜態常數

	private final static ImageIcon ICON_FILE = 
		new ImageIcon("icon/File.gif");
	//定義代表檔案圖示的靜態常數

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

		if(column ==  0){
			TableModel tm = table.getModel();
			//取得表格元件資料的TableModel物件

			setIcon("true".equals(tm.getValueAt(row, 2).toString()) 
								? ICON_FILE : ICON_CLOSED_FOLDER);
			//判斷選取列第3欄的資料是否為true, 
			//是則設定使用ICON_FILE圖示
			//若不是則設定使用ICON_CLOSED_FOLDER圖示
		}
		else
			setIcon(null); //設定不使用任何圖示

		return this;
	}
}