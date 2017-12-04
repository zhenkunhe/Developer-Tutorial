import javax.swing.*;  //引用套件
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
//引用包含協助JTable類別建立表格的介面與類別

import java.awt.*;
import java.awt.event.*;

public class TableEX extends JFrame {

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

	JTable tbBook = new JTable(bookData, colName);
	//以儲存書籍資料的二維陣列
	//與表格欄位名稱字串陣列宣告JTable元件

	JLabel lbSel = new JLabel(),
				 lbRange = new JLabel();

	JCheckBox cbCell = new JCheckBox(),
						cbRow = new JCheckBox(),
						cbCol = new JCheckBox();
	//宣告控制表格選取方式的核取方塊控制項

	JCheckBox cbVer = new JCheckBox(),
						cbHor = new JCheckBox();
	//宣告控制表格欄位格線是否顯示的核取方塊控制項
	
	public TableEX() {

		tbBook.setSelectionBackground(Color.gray);
		//設定選取背景顏色

		tbBook.setSelectionForeground(Color.lightGray);
		//設定選取前景顏色

		TableColumn colTitle = tbBook.getColumnModel().getColumn(0);
		//取得JTable元件使用的ColumnModel物件,
		//再取得控制第一欄的TableColumn物件

		colTitle.setPreferredWidth(200);
		//設定欄位的喜好寬度為200

		cbRow.setSelected(tbBook.getRowSelectionAllowed());
		//設定代表列選取的核取方塊為選取

		cbVer.setSelected(tbBook.getShowVerticalLines());
		cbHor.setSelected(tbBook.getShowHorizontalLines());
		//設定預設為選取

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

		//註冊回應ItemEvent事件的監聽器物件
		cbVer.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				tbBook.setShowVerticalLines(
					(e.getStateChange() ==  ItemEvent.SELECTED));
				//設定表格是否顯示垂直線
			}
		});

		//註冊回應ItemEvent事件的監聽器物件
		cbHor.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				tbBook.setShowHorizontalLines(
					(e.getStateChange() ==  ItemEvent.SELECTED));
				//設定表格是否顯示水平線
			}
		});

		//註冊回應MouseMotionEvent事件
		tbBook.addMouseMotionListener(new MouseInputAdapter(){
			public void mouseDragged(MouseEvent e){
				updateMsg(); //更新選取範圍訊息
			}
		});
		
		//註冊回應MouseEvent事件
		tbBook.addMouseListener(new MouseInputAdapter(){
			public void mouseClicked(MouseEvent e){
				updateMsg(); //更新選取範圍訊息
			}
		});

		JButton btnSelAll = new JButton("選取全部"),
					  btnUnSel = new JButton("取消選取");
		//宣告執行選取或取消選取動作的按鈕
		
		//註冊回應ActionEvent事件的監聽器物件
		btnSelAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.selectAll(); //設定選取全部
				lbRange.setText("全部選取");
			}
		});

		btnUnSel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tbBook.clearSelection(); //設定取消選取
				lbRange.setText("未選取");
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

		//建立包含控制框線繪製方式核取方塊的Box容器
		Box bxLine = new Box(BoxLayout.X_AXIS);
		bxLine.add(new JLabel("框線的繪製 : "));
		bxLine.add(cbVer);
		bxLine.add(new JLabel("垂直線"));
		bxLine.add(Box.createHorizontalStrut(5));
		bxLine.add(cbHor);
		bxLine.add(new JLabel("水平線"));

		JPanel jpNorth = new JPanel(new GridLayout(2, 1));
		jpNorth.add(bxSel);
		jpNorth.add(bxLine);

		Box bxSelStatus = new Box(BoxLayout.X_AXIS);
		bxSelStatus.add(new JLabel("選取範圍 : "));
		bxSelStatus.add(lbRange);
		//將顯示選取範圍的標籤加入容器

		Container cp = getContentPane(); //取得內容面版

		cp.setLayout(new BorderLayout(10, 10));
		//建立各區域水平、垂直間距為10的BorderLayout物件

		cp.add(jpNorth, BorderLayout.NORTH);
		cp.add(new JScrollPane(tbBook));
		cp.add(bxSelStatus, BorderLayout.SOUTH);
		//將各容器元件加入面版

		getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//設定根面版四周將使用寬度為5的空白框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 259);
		setVisible(true);
	}

	private void updateMsg(){ //更新顯示訊息

		lbRange.setText(""); //清除顯示內容

		int [] selCols = tbBook.getSelectedColumns(),
				selRows = tbBook.getSelectedRows();
		//取得被選取欄、列的索引值陣列

		//依照被選取的核取方塊設定標籤顯示的字串
		if(cbCell.isSelected()){
			String strLeftUp = "( " + selCols[0] + ", " + selRows[0]	+ " )",
					   strRightDown = "( " + selCols[selCols.length - 1] 
				+ ", " + selRows[selRows.length - 1] + " )";
			//設定顯示範圍的左上角與右下角

			lbRange.setText(strLeftUp + " 至 " + strRightDown);
			//設定顯示選取範圍的左上角與右下角
		}
		else{
			if(cbRow.isSelected()){
				lbRange.setText("第 " + selRows[0] + " 至 "
					+ selRows[selRows.length - 1] + " 列");
				//列選取範圍的字串
			}
			else if(cbCol.isSelected()){
				lbRange.setText("第 " + selCols[0] + " 至 "
					+ selCols[selCols.length - 1] + " 欄");
				//欄選取範圍的字串
			}					
		}
	}

	public static void main(String args[]) {
		new TableEX(); //宣告視窗框架物件
	}
}