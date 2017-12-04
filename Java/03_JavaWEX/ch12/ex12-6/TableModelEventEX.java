import javax.swing.*;  //引用套件
import javax.swing.border.*;
import javax.swing.table.*;
//引用定義協助JTable元件處理表格之介面、類別的套件
import javax.swing.event.*; //增加引用的套件
import java.awt.*;
import java.awt.event.*;

public class TableModelEventEX extends JFrame {

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
				  btnMod = new JButton("修改"),
				  btnCancel = new JButton("取消");
	//宣告操作資料的指令按鈕

	//宣告建立表格的JTable元件,
	//並以匿名類別的方式覆蓋valueChanged()方法,
	//回應列選取範圍的變更動作
	JTable tbBook = new JTable(){
		public void valueChanged(ListSelectionEvent e){
			super.valueChanged(e); //呼叫基礎類別的valueChanged()方法, 否則選取動作無法正常執行

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

	public TableModelEventEX() {

		//將所有文字欄設定為不可編輯
		for(JTextField elm : tfInput){
			elm.setEditable(false);
		}
	
		dtmBook = (DefaultTableModel)tbBook.getModel();
		//取得處理表格資料的Model物件

		for(String elm : colName) //以欄位名稱新增欄位
			dtmBook.addColumn(elm);

		for(Object [] elm :  bookData)
			dtmBook.addRow(elm); //將資料新增至表格

		tbBook.setRowSelectionInterval(0, 0);
		//必須放在表格資料新增後, 否則將丟出例外

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第一欄的TableColumn物件

		colTitle.setPreferredWidth(200); //設定欄位的喜好寬度為200

		//以匿名類別的方式定義、宣告監聽器物件,
		//並透過回應指令按鈕ActionEvent事件的機會,
		//執行資料的新增與儲存
		btnNew.addActionListener(new ActionListener(){

			//回應按鈕按下事件
			public void actionPerformed(ActionEvent e){

				//判斷按鈕顯示字串為新增或儲存,
				//以便執行不同處理
				if(e.getActionCommand().equals("新增")){

					//將輸出資料的文字欄設定為可編輯,
					//並設定沒有任何內容
					for(JTextField elm : tfInput){
						elm.setEditable(true);
						elm.setText(null);
					}

					btnNew.setText("儲存");
					//將指令按鈕文字設定為儲存

					btnDel.setEnabled(false);
					btnMod.setEnabled(false);
					//將刪除與修改按鈕設定為無效

					btnCancel.setEnabled(true);
					//將取消按鈕設定為有效
				}
				else if(e.getActionCommand().equals("儲存")){

					Object[] newRow = { 
								tfTitle.getText(),
								tfPublisher.getText(),
								Double.valueOf(tfPrice.getText()),
								tfID.getText(),
								tfPDate.getText()};
					//宣告儲存新資料的物件陣列

					dtmBook.addRow(newRow);
					//將資料新增至表格的TableModel物件

					int newRowIndex =  dtmBook.getRowCount() - 1;
					//取得新增資料位置的索引值

					tbBook.setRowSelectionInterval(
						newRowIndex, newRowIndex);
					//設定選取的列

					//將文字欄設定為不可編輯
					for(JTextField elm : tfInput){
						elm.setEditable(false);
					}

					btnNew.setText("新增");
					//將指令按鈕文字設定為新增

					btnDel.setEnabled(true);
					btnMod.setEnabled(true);
					//將新增、修改按鈕設定為有效

					btnCancel.setEnabled(false);
					//將取消按鈕設定為無效
				}
			}
		});

		//以匿名類別的方式定義、宣告監聽器物件,
		//並透過回應指令按鈕ActionEvent事件的機會,
		//執行資料的修改與儲存
		btnMod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				//判斷按鈕顯示字串為修改或儲存,
				//以便執行不同處理
				if(e.getActionCommand().equals("修改")){

					//判斷是否選取不止一列, 是則設定僅選取第一列
					if(tbBook.getSelectedRows().length != 1){
						int selRow = tbBook.getSelectedRow();
						//取得選取範圍的第一列

						tbBook.setRowSelectionInterval(
							selRow, selRow);
						//設定選取範圍的第一列
					}

					//將文字欄設定為可編輯
					for(JTextField elm : tfInput){
						elm.setEditable(true);
					}

					btnMod.setText("儲存");
					//將指令按鈕文字設定為儲存

					btnNew.setEnabled(false);
					btnDel.setEnabled(false);	
					//將新增、刪除按鈕設定為無效

					btnCancel.setEnabled(true);
					//將取消按鈕設定為無效
				}
				else if(e.getActionCommand().equals("儲存")){

					Object[] newData = {
									tfTitle.getText(),
									tfPublisher.getText(),
									Double.valueOf(tfPrice.getText()),
									tfID.getText(),
									tfPDate.getText()};
					//宣告儲存修改後新資料的物件陣列

					int selRow = tbBook.getSelectedRow();
					//取得表格目前的選取列

					for(int i=0; i < tfInput.length; i++){
						tfInput[i].setEditable(false);
						//將文字欄設定為不可編輯

						dtmBook.setValueAt(newData[i], selRow, i);
						//以文字欄的資料修改表格內的資料
					}

					btnMod.setText("修改");
					//將指令按鈕文字設定為修改

					btnNew.setEnabled(true);
					btnDel.setEnabled(true);
					//將新增、刪除按鈕修改為有效

					btnCancel.setEnabled(false);
					//將取消按鈕設定為無效
				}
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

		//以匿名類別的方式定義、宣告監聽器物件,
		//並透過回應指令按鈕ActionEvent事件的機會,
		//取消資料的操作
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){			

				int selRow = tbBook.getSelectedRow();
				//取得目前的選取列

				tbBook.clearSelection(); //清除選取

				tbBook.setRowSelectionInterval(selRow, selRow);
				//重設選取範圍, 以便引發ListSelectionEvent事件

				//將文字欄設定為不可編輯
				for(JTextField elm : tfInput){
					elm.setEditable(false);
				}

				btnCancel.setEnabled(false);
				//將取消按鈕設定為無效

				btnNew.setText("新增");
				btnMod.setText("修改");
				//將指令按鈕文字恢復設定為新增與修改

				btnNew.setEnabled(true);
				btnDel.setEnabled(true);
				btnMod.setEnabled(true);
				//將新增、刪除、修改按鈕設定為有效
			}
		});

		//以匿名類別的方式, 註冊回應TableModelEvent事件的監聽器物件
		dtmBook.addTableModelListener(new TableModelListener(){

			public void tableChanged(TableModelEvent e){

				//依TableModelEvent事件執行動作的型態, 
				//決定欲執行的動作
				switch(e.getType()){
				case TableModelEvent.INSERT : //新增資料
					System.out.println(
						"INSERT : firstRow = " + e.getFirstRow() +
						" lastRow = " + e.getLastRow());
					break;
				case TableModelEvent.DELETE : //刪除資料
					System.out.println(
						"DELETE : firstRow = " + e.getFirstRow() +  
						" lastRow = " + e.getLastRow());
					break;
				case TableModelEvent.UPDATE : //修改資料
					System.out.println(
						"UPDATE : firstRow = " + e.getFirstRow() +  
						" lastRow = " + e.getLastRow());
					break;
				}
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
		bxButton.add(Box.createHorizontalStrut(10));
		bxButton.add(btnCancel);

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
		new TableModelEventEX(); //宣告視窗框架物件	 
	}
}