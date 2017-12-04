import javax.swing.*;  //引用套件
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class DialogEX extends JFrame {

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

	String[] colName = {"書名", "出版商", "售價",
									 "書號", "出版日期"};
	//宣告儲存欄位名稱的字串

	JTextField tfTitle = new JTextField(),
						tfPublisher = new JTextField(),
						tfPrice = new JTextField(),
						tfID = new JTextField(),
						tfPDate = new JTextField();
	//宣告顯示、編輯表格內資料的文字欄

	JTextField[] tfInput = 
		{tfTitle, tfPublisher, tfPrice, tfID, tfPDate};
	//透過操作所有新增、編輯資料的文字欄

	JButton btnNew = new JButton("新增"),
				  btnDel = new JButton("刪除"),
				  btnMod = new JButton("修改");
	//宣告操作資料的指令按鈕

	//宣告建立表格的JTable元件,
	//並以匿名類別的方式覆蓋valueChanged()方法,
	//回應列選取範圍的變更動作
	JTable tbBook = new JTable(){
		public void valueChanged(ListSelectionEvent e){
			super.valueChanged(e);
			//呼叫基礎類別的valueChanged()方法, 
			//否則選取動作無法正常執行

			int selRow = tbBook.getSelectedRow();
			//取得表格目前的選取列

			//若沒有選取列則終止執行方法
			if(selRow == -1) return;

			//將選取列的資料, 依序設定給文字欄
			for(int i = 0; i < tfInput.length; i++){
				tfInput[i].setText(
					dtmBook.getValueAt(selRow, i).toString());
			}
		}
	};
	
	DefaultTableModel dtmBook;
	//宣告處理表格資料的TableModel物件

	public DialogEX() {

		//將所有文字欄設定為不可編輯
		for(JTextField elm : tfInput)
			elm.setEditable(false);
			
		dtmBook = (DefaultTableModel) tbBook.getModel();
		//取得處理表格資料的TableModel物件

		for(String elm : colName) //以欄位名稱新增欄位
			dtmBook.addColumn(elm);

		for(Object[] elm :  bookData)
			dtmBook.addRow(elm); //將資料新增至表格

		tbBook.setRowSelectionInterval(0, 0);
		//必須放在表格資料新增後, 否則將丟出例外

		TableColumn colTitle = 
			tbBook.getColumnModel().getColumn(0);
		//取得JTable元件使用的TableColumnModel物件,
		//再取得控制第一欄的TableColumn物件

		colTitle.setPreferredWidth(200);
		//設定欄位的喜好寬度為200

		//以匿名類別的方式定義、宣告監聽器物件,
		//並透過回應指令按鈕ActionEvent事件的機會,
		//執行資料的新增與儲存
		btnNew.addActionListener(new ActionListener(){

			//回應按鈕按下事件
			public void actionPerformed(ActionEvent e){

				Object[] newRowData = BookDataDialog.showDialog(
											DialogEX.this, "新增資料");
				//顯示新增資料對話盒

				//若傳回的資料陣列長度為0, 則終止執行方法
				if(newRowData.length == 0)
					return;

				dtmBook.addRow(newRowData);
				//將資料新增至TableModel物件內

				int selRow = dtmBook.getRowCount()-1;
				//設定新選取列的索引值

				tbBook.setRowSelectionInterval(selRow, selRow);
				//設定新的選取列
			}
		});

		//以匿名類別的方式定義、宣告監聽器物件,
		//並透過回應指令按鈕ActionEvent事件的機會,
		//執行資料的修改與儲存
		btnMod.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				int selRow = tbBook.getSelectedRow();
				//取得表格目前的選取列

				Vector vetBookData =  (Vector)dtmBook.getDataVector();
				//取得儲存TableModel物件內所有資料的Vector物件

				Vector vetSelRow = 
					(Vector)vetBookData.elementAt(selRow);
				//取得儲存指定列之資料的Vector物件

				Object[] selRowData = vetSelRow.toArray();
				//將儲存選取列資料的Vector容器之元素轉換為Object陣列

				Object[] newRowData = BookDataDialog.showDialog(
						DialogEX.this, "修改資料" , selRowData);
				//顯示修改資料對話盒

				//若傳回的資料陣列長度為0, 則終止執行方法
				if(newRowData.length == 0)
					return;

				//以修改資料對話盒修改後的資料
				//更新TableModel物件的內容
				for(int i=0; i<newRowData.length; i++)
					dtmBook.setValueAt(newRowData[i], selRow, i);

				tbBook.clearSelection(); //清除表格的選取
				tbBook.setRowSelectionInterval(selRow, selRow);
				//重新選取列, 以便更新視窗上方文字欄顯示的資料
			}
		});

		//以匿名類別的方式定義、宣告監聽器物件,
		//並透過回應指令按鈕ActionEvent事件的機會,
		//執行資料的刪除
		btnDel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int[] selRows = tbBook.getSelectedRows();
				//取得選取列的索引值

				if(selRows.length == 0) return;
				//若未選取任何列則終止執行方法

				//依照選取列的索引值移除資料
				for(int i = selRows.length-1; i>=0; i--){
					dtmBook.removeRow(selRows[i]);
					//必須從後面開始移除, 
					//因為移除一列資料時, 後面選取列的索引值將改變
					//從最後一列開始移除則可解決此問題
				}

				int rowCount = dtmBook.getRowCount();
				//取得移除選取列後, 表格的列數

				//若表格剩餘列數為0 , 則終止執行方法
				if(rowCount == 0){

					//清除文字欄內容
					for(JTextField elm : tfInput){
						elm.setText(null);
					}
					return;
				}

				int afterSelIndex = 
					selRows[selRows.length - 1] - selRows.length + 1;
				//計算新選取列的索引值

				//若新選取列的索引值大於表格剩餘列數減1,
				//則將索引值設定為剩餘列數減1
				if(afterSelIndex > (rowCount-1))
					afterSelIndex = (rowCount-1);

				tbBook.setRowSelectionInterval(
					afterSelIndex, afterSelIndex);
				//設定表格新的選取列
			}
		});

		JPanel jpInput = new JPanel(new GridBagLayout());
		//宣告容納新增與編輯資料之文字欄的面版容器

		GridBagConstraints gbc = new GridBagConstraints();
		//宣告控制元件版面配置的GridBagConstraints物件

		gbc.insets = new Insets(2, 5 , 2, 5);
		gbc.ipadx = gbc.ipady = 2;
		//設定元件內文字與四周邊界的間距均為15
		gbc.gridwidth = 1; gbc.gridheight = 1;
		//設定寬度為1欄高度為1列
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//設定元件填滿顯示空間的水平空間

		/***加入第一列的元件***/		
		gbc.gridx = 0; gbc.gridy = 0; //設定元件的行列位置
		jpInput.add(new JLabel("名稱 : "), gbc);

		gbc.gridx = 1; gbc.gridy = 0; //設定元件的行列位置
		gbc.gridwidth = 4; //設定欄位的寬度
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpInput.add(tfTitle, gbc);

		gbc.weightx = 0.0; //恢復設定值
		gbc.gridwidth = 1;

		/***加入第二列的元件***/		
		gbc.gridx = 0; gbc.gridy = 1; //設定元件的行列位置
		jpInput.add(new JLabel("出版社 : "), gbc);

		gbc.gridx = 1; gbc.gridy = 1; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpInput.add(tfPublisher, gbc);

		gbc.weightx = 0.0; //恢復設定值

		gbc.gridx = 2; gbc.gridy = 1; //設定元件的行列位置
		jpInput.add(new JLabel("售價 : "), gbc);

		gbc.gridx = 3; gbc.gridy = 1; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpInput.add(tfPrice, gbc);

		gbc.weightx = 0.0; //恢復設定值

		/***加入第三列的元件***/
		gbc.gridx = 0; gbc.gridy = 2; //設定元件的行列位置
		jpInput.add(new JLabel("書號 : "), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpInput.add(tfID, gbc);

		gbc.weightx = 0.0; //恢復設定值

		gbc.gridx = 2; gbc.gridy = 2; //設定元件的行列位置
		jpInput.add(new JLabel("出版日期 : "), gbc);

		gbc.gridx = 3; gbc.gridy = 2; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpInput.add(tfPDate, gbc);

		//建立包含指令按鈕的Box容器
		Box bxButton = new Box(BoxLayout.X_AXIS);
		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnNew);
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnDel);
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnMod);

		//宣告置於內容面版北邊的Box容器
		Box bxNorth = new Box(BoxLayout.Y_AXIS);
		bxNorth.add(jpInput);
		bxNorth.add(Box.createVerticalStrut(5));
		bxNorth.add(bxButton);

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(15, 15));
		//建立各區域水平、垂直間距為15的BorderLayout物件

		cp.add(bxNorth, BorderLayout.NORTH);
		cp.add(new JScrollPane(tbBook));
		//將元件加入中間區域

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		new DialogEX(); //宣告視窗框架物件	 
	}
}

//定義顯示執行資料新增、修改之對話盒的BookDataDialog類別
//將繼承BookDataDialog類別並實作ActionListener介面
//以回應對話盒的指令按鈕
class BookDataDialog extends JDialog
										implements ActionListener {

	private JTextField tfTitle = new JTextField(),
						tfPublisher = new JTextField(),
						tfPrice = new JTextField(),
						tfID = new JTextField(),
						tfPDate = new JTextField();
	//宣告顯示、編輯表格內資料的文字欄

	private JButton btnSave = new JButton("儲存"),
				  btnCancel = new JButton("取消");
	//宣告指令按鈕

	private JTextField[] tfInput = 
		{tfTitle, tfPublisher, tfPrice, tfID, tfPDate};
	//透過操作所有新增、編輯資料的文字欄

	private Vector values = new Vector();
	//儲存文字欄的設定值

	//宣告修改資料對話盒
	BookDataDialog(JFrame parent, String title, Object[] data){

		super(parent, title, true); //呼叫基礎類別的建構子

		createMainPanel(); //建立主要對話盒的主要面版

		//以傳入資料設定對話盒的文字欄
		for(int i = 0; i < tfInput.length; i++)
			tfInput[i].setText(data[i].toString());

		setSize(350, 170); //設定對話盒的大小
	}
	//宣告新增資料對話盒
	BookDataDialog(JFrame parent, String title){

		super(parent, title, true); //呼叫基礎類別的建構子

		createMainPanel(); //建立主要對話盒的主要面版

		setSize(350, 170); //設定對話盒的大小
	}
	
	//回應指令按鈕按下動作的方法
	public void actionPerformed(ActionEvent e){

		//若使用者按下 儲存 按鈕, 
		//將把文字欄的資料加入values容器內
		if("儲存".equals(e.getActionCommand())){
			values.add(tfTitle.getText());
			values.add(tfPublisher.getText());
			values.add(Double.valueOf(tfPrice.getText()));
			values.add(tfID.getText());
			values.add(tfPDate.getText());
		}

		setVisible(false); //設定不顯示對話盒
	}

	//取得對話盒內文字欄的設定值
	public Object[] getValues(){
		return values.toArray();
	}

	private void createMainPanel(){ //建立對話盒版面的方法

		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		//註冊由BookDataDialog物件監聽指令按鈕的動作

		JPanel jpDataInput = new JPanel(new GridBagLayout());
		//宣告容納新增與編輯資料之文字欄的面版容器

		GridBagConstraints gbc = new GridBagConstraints();
		//宣告控制元件版面配置的GridBagConstraints物件

		gbc.insets = new Insets(2, 5 , 2, 5);
		gbc.ipadx = gbc.ipady = 2;
		//設定元件內文字與四周邊界的間距均為15
		gbc.gridwidth = 1; gbc.gridheight = 1;
		//設定寬度為1欄高度為1列
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//設定元件填滿顯示空間的水平空間

		/***加入第一列的元件***/
		gbc.gridx = 0; gbc.gridy = 0; //設定元件的行列位置
		jpDataInput.add(new JLabel("名稱 : "), gbc);

		gbc.gridx = 1; gbc.gridy = 0; //設定元件的行列位置
		gbc.gridwidth = 4; //設定欄位的寬度
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpDataInput.add(tfTitle, gbc);

		gbc.weightx = 0.0; //恢復設定值
		gbc.gridwidth = 1;

		/***加入第二列的元件***/
		gbc.gridx = 0; gbc.gridy = 1; //設定元件的行列位置
		jpDataInput.add(new JLabel("出版社 : "), gbc);

		gbc.gridx = 1; gbc.gridy = 1; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpDataInput.add(tfPublisher, gbc);

		gbc.weightx = 0.0; //恢復設定值

		gbc.gridx = 2; gbc.gridy = 1; //設定元件的行列位置
		jpDataInput.add(new JLabel("售價 : "), gbc);

		gbc.gridx = 3; gbc.gridy = 1; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpDataInput.add(tfPrice, gbc);

		gbc.weightx = 0.0; //恢復設定值

		/***加入第三列的元件***/
		gbc.gridx = 0; gbc.gridy = 2; //設定元件的行列位置
		jpDataInput.add(new JLabel("書號 : "), gbc);

		gbc.gridx = 1; gbc.gridy = 2; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpDataInput.add(tfID, gbc);

		gbc.weightx = 0.0; //恢復設定值

		gbc.gridx = 2; gbc.gridy = 2; //設定元件的行列位置
		jpDataInput.add(new JLabel("出版日期 : "), gbc);

		gbc.gridx = 3; gbc.gridy = 2; //設定元件的行列位置
		gbc.weightx = 1.0; //設定分配額外水平空間的比例
		jpDataInput.add(tfPDate, gbc);

		Box bxButton = new Box(BoxLayout.X_AXIS);
		//容納指令按鈕的Box容器

		bxButton.add(Box.createHorizontalGlue());
		bxButton.add(btnSave);  //加入儲存指令
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnCancel);  //加入取消指令

		Container cp = getContentPane(); //取得內容面版
		cp.add(bxButton, BorderLayout.SOUTH);
		cp.add(jpDataInput);
		//將包含各控制項的容器加入內容面版

		getRootPane().setBorder(new EmptyBorder( 5, 5, 5, 5));
		//設定根面版四週為寬度為5的空白框線
	}

	//產生、顯示新增資料對話盒的靜態方法
	public static Object[] showDialog(JFrame parent, String title){

		BookDataDialog dialog = new BookDataDialog(parent, title);
		//宣告新增資料對話盒

		dialog.setVisible(true); //設定顯示對話盒

		return dialog.getValues(); //傳回對話盒內文字欄的設定值
	}

	//產生、顯示修改資料對話盒的靜態方法
	public static Object[] showDialog(
		JFrame parent, String title, Object[] data){

		BookDataDialog dialog = new BookDataDialog(parent, title, data);
		//宣告修改資料對話盒

		dialog.setLocationRelativeTo(parent);
		//設定對話盒顯示於視窗中央

		dialog.setVisible(true); //設定顯示對話盒

		return dialog.getValues(); //傳回對話盒內文字欄的設定值
	}
}