import javax.swing.*;  //引用套件
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
//引用包含協助JTable類別建立表格的介面與類別

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TableEventEX extends JFrame {

	Object[][] bookData = {
		{"Java 2 入門進階 – 適用JDK6.0", "文魁資訊", 590.0,
		 "P4137", "2005-5-1"},
		{"Visual C++.NET 入門進階", "文魁資訊", 750.0, 
		 "P3237", "2003-9-1"},
		{"Access 2003 徹底研究", "文魁資訊", 590.0, 
		 "A4023", "2004-3-1"},
		{"Access 2003 程式設計", "文魁資訊", 660.0, 
		 "A4033", "2004-5-1"},
		{"JSP 動態網頁入門實務", "文魁資訊", 720.0, 
		 "W3135", "2003-12-1"},
		{"ASP 動態網頁入門實務", "文魁資訊", 580.0, 
		 "W4075", "2004-7-1"}};
	//宣告儲存書籍資料的二維陣列

	String[] colName = {"書名", "出版商", "售價", "書號", "出版日期"};
	//宣告儲存欄位名稱的字串

	JLabel lbSelRow = new JLabel(), lbSelCol = new JLabel();
	//顯示選取列、欄索引值的標籤

	//此JTable類別將運用匿名類別的方式,
	//覆蓋實作介面的方法, 以回應欄、列的ListSelectionEvent事件
	//宣告物件時, 將儲存書籍資料的二維陣列與
	//表格欄位名稱字串陣列宣告
	JTable tbBook = new JTable(bookData, colName){

		//回應列選取範圍改變的方法
		public void valueChanged(ListSelectionEvent e){

			super.valueChanged(e);
			//呼叫基礎類別的valueChanged()方法, 
			//否則列選取動作無法正常運作

			//判斷是否允許列選取, 允許才顯示被選取列的索引值
			if(getRowSelectionAllowed()){
				lbSelRow.setText(Arrays.toString(getSelectedRows()));
				//將選取列的索引值設定給標籤元件			
			}
			else{
				lbSelRow.setText(null);
			}
		}

		//回應欄選取範圍改變的方法
		public void columnSelectionChanged(ListSelectionEvent e){

			super.columnSelectionChanged(e);
			//呼叫基礎類別的columnSelectionChanged()方法, 
			//否則欄選取動作無法正常運作

			//判斷是否允許欄選取, 允許才顯示被選取欄的索引值
			if(getColumnSelectionAllowed()){
				lbSelCol.setText(Arrays.toString(getSelectedColumns()));
				//將選取欄的索引值設定給標籤元件		
			}
			else{
				lbSelCol.setText(null);
			}
		}
	};

	JCheckBox cbCell = new JCheckBox(),
						cbRow = new JCheckBox(),
						cbCol = new JCheckBox();
	//宣告控制表格選取方式的核取方塊控制項
	
	public TableEventEX() {

		tbBook.setSelectionBackground(Color.gray);
		//設定選取背景顏色

		tbBook.setSelectionForeground(Color.lightGray);
		//設定選取前景顏色

		TableColumn colTitle = 
			tbBook.getColumnModel().getColumn(0);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第一欄的TableColumn物件

		colTitle.setPreferredWidth(200);
		//設定欄位的喜好寬度為200

		cbRow.setSelected(tbBook.getRowSelectionAllowed());
		//設定代表列選取的核取方塊為選取

		//註冊回應ItemEvent事件的監聽器物件
		cbCell.addItemListener(new ItemListener(){

			//回應項目狀態改變
			public void itemStateChanged(ItemEvent e){

				tbBook.setCellSelectionEnabled(
					(e.getStateChange() ==  ItemEvent.SELECTED));
				//設定儲存格是否可被選取

				cbRow.setSelected(tbBook.getRowSelectionAllowed());
				cbCol.setSelected(tbBook.getColumnSelectionAllowed());
				//設定控制列、欄選取狀態的核取方塊是否被選取
			}
		});

		//註冊回應ItemEvent事件的監聽器物件
		cbRow.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){

				boolean blSel = 
					(e.getStateChange() ==  ItemEvent.SELECTED);
				//判斷選取狀態是否為選取

				tbBook.setRowSelectionAllowed(blSel);
				//設定可執行列選取

				cbCell.setSelected(tbBook.getCellSelectionEnabled());
				//設定是否可選取儲存格
			}
		});

		//註冊回應ItemEvent事件的監聽器物件
		cbCol.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){

				boolean blSel = 
					(e.getStateChange() ==  ItemEvent.SELECTED);
				//判斷選取狀態是否為選取

				tbBook.setColumnSelectionAllowed(blSel);
				//設定允許欄選取

				cbCell.setSelected(tbBook.getCellSelectionEnabled());
				//設定是否可選取儲存格
			}
		});

		JButton btnSelAll = new JButton("選取全部"),
					  btnUnSel = new JButton("取消選取");
		//宣告執行選取或取消選取動作的按鈕
		
		//註冊回應ActionEvent事件的監聽器物件
		btnSelAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.selectAll(); //設定選取全部
			}
		});

		btnUnSel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.clearSelection(); //設定取消選取
			}
		});

		//建立包含控制選取方式核取方塊的Box容器
		Box bxSel = new Box(BoxLayout.X_AXIS);
		bxSel.add(new JLabel("選取方式設定 : "));
		bxSel.add(cbCell);
		bxSel.add(new JLabel("儲存格"));
		bxSel.add(Box.createHorizontalStrut(5));
		bxSel.add(cbRow);
		bxSel.add(new JLabel("列"));
		bxSel.add(Box.createHorizontalStrut(5));
		bxSel.add(cbCol);
		bxSel.add(new JLabel("欄"));
		bxSel.add(Box.createHorizontalStrut(20));
		bxSel.add(btnSelAll);
		bxSel.add(Box.createHorizontalStrut(5));
		bxSel.add(btnUnSel);

		Box bxSelRow = new Box(BoxLayout.X_AXIS);
		bxSelRow.add(new JLabel("選取列 : "));
		bxSelRow.add(Box.createHorizontalStrut(5));
		bxSelRow.add(lbSelRow); //加入顯示選取列之索引值的標籤
		bxSelRow.add(Box.createHorizontalGlue());

		Box bxSelCol = new Box(BoxLayout.X_AXIS);
		bxSelCol.add(new JLabel("選取欄 : "));
		bxSelCol.add(Box.createHorizontalStrut(5));
		bxSelCol.add(lbSelCol); //加入顯示選取欄之索引值的標籤
		bxSelCol.add(Box.createHorizontalGlue());

		Box bxSelStatus = new Box(BoxLayout.Y_AXIS);
		bxSelStatus.add(bxSelRow);
		bxSelStatus.add(bxSelCol);
		//將顯示選取範圍的標籤加入容器

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		cp.add(bxSel, BorderLayout.NORTH);
		cp.add(new JScrollPane(tbBook));
		cp.add(bxSelStatus, BorderLayout.SOUTH);
		//將各容器元件加入面版

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 249);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TableEventEX(); //宣告視窗框架物件
	}
}