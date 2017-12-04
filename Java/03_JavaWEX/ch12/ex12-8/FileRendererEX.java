import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.io.*;
import java.text.*; //引用包含SimpleDateFormat類別的套件

public class FileRendererEX extends JFrame {

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

	public FileRendererEX() {
		
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

					//若滑鼠點選的不是第1欄則終止方法的執行
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
					//設定FileTableModel物件將顯示上一層資料夾內的檔案

					tbDir.updateUI(); //更新表格的畫面
				}
			}
		});

		TableColumn colTitle = tbDir.getColumnModel().getColumn(0);
		//取得JTable元件使用的TableColumnModel物件,
		//再取得控制第一欄的TableColumn物件

		colTitle.setPreferredWidth(200); //設定欄位的喜好寬度為200

		TableColumn colDate = tbDir.getColumnModel().getColumn(3);
		colDate.setPreferredWidth(150); //設定欄位的喜好寬度為150

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

	public static void main(String args[]) {
		new FileRendererEX(); //宣告視窗框架物件	 
	}
}

//定義處理檔案資料的Model物件
class FileTableModel extends AbstractTableModel {

	private File dir;
	private File[] files;
	//儲存資料夾內檔案的陣列

	String[] colName = {"名稱", "大小", "是否為檔案", 
									 "最後修改日期", "可讀取", "可修改"};
	//宣告儲存欄位名稱的字串

	SimpleDateFormat sdf = 
		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	//定義最後修改日期使用的日期格式

	Class[] types = new Class[] { 
		String.class, Long.class, Boolean.class, 
		String.class, //請注意! Date經過格式化後, 型別為String
		Boolean.class, Boolean.class};
	//為配合TableCellRenderer物件, 
	//必須宣告儲存檔案資料之型別的Class陣列

	FileTableModel(){
		this(new File(".")); //呼叫FileTableModel(String path)建構子
	}

	FileTableModel(File path){
		setCurrentPath(path); //設定欲檢視的資料夾
	}

	public int getColumnCount(){ return colName.length; }
	//取得欄位的個數

	public int getRowCount(){

		if(files == null) return 0;
		//若資料夾內沒有檔案則傳回0

		return files.length; //傳回取得檔案的個數
	}
	//取得資料的列數

	//取得指定欄、列位置的儲存格資料
	public Object getValueAt(int row, int col){
		
		//判斷列索引值是否正確
		if(row < 0 || row >= getRowCount())
			return "";

		//判斷欄索引值是否正確
		if(col < 0 || col >= getColumnCount())
			return "";

		File file = files[row];
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
		files = dir.listFiles(); //列出資料夾內的所有檔案

		fireTableChanged(new TableModelEvent(this));
		//觸發表格發生改變的事件, 並丟出TableModelEvent物件
	}

	//取得目前檢視之資料夾的路徑
	public File getCurrentPath(){
		return dir;
	}

	public Class getColumnClass(int col) { return types[col]; }
	//取得檔案某欄位資料之類別的Class物件,
	//此方法將供TableCellRenderer物件運作使用
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
		//呼叫基礎類別的getTableCellRendererComponent()方法

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
	private final static ImageIcon ICON_FILE = 
		new ImageIcon("icon/File.gif");
	//定義代表圖示的靜態常數

	public Component getTableCellRendererComponent(
							JTable table, //使用轉譯器物件的表格元件
							Object value, //轉譯器欲處理之表格儲存格的值
							boolean isSelected, //被處理儲存格是否被選取
							boolean hasFocus, //被選取儲存格是否擁有焦點
							int row, //被選取儲存格所在位置的列數
							int column){ //被選取儲存格所在位置的欄數

		super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
		//呼叫基礎類別的getTableCellRendererComponent()方法

		if(table.getColumnName(column).equals("名稱")){
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