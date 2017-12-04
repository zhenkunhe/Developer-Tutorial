import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件

public class TreeEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel(),
				lbTotalRow = new JLabel(), lbRow = new JLabel(),
				lbCurRow = new JLabel(), lbMousePos = new JLabel(),
				lbMouse = new JLabel();
	//宣告顯示節點與事件型態資料的標籤
	
	JButton btnExpand = new JButton("收合根節點");
	//宣告執行根節點展開、收合的指令按鈕

	JTree trBook; //宣告顯示樹狀結構的JTree元件

	TreeEX() {
		
		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("物件導向程式語言", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("動態網頁", true),
			dmtnRoot = new DefaultMutableTreeNode("位元文化", true);
		//宣告節點物件

		//以下敘述將把子節點加入節點
		dmtnC.add(
			new DefaultMutableTreeNode("C/C++ 入門進階", false));
		dmtnC.add(	
			new DefaultMutableTreeNode("Visual C++.Net 入門進階", 
			false));
		dmtnC.add(
			new DefaultMutableTreeNode("Linux C/C++ 入門進階", 
			false));

		dmtnJava.add(
			new DefaultMutableTreeNode("Java 2 入門進階", false));
		dmtnJava.add(
			new DefaultMutableTreeNode("Java 2 物件導向程式語言", 
			false));

		dmtnHtml.add(
			new DefaultMutableTreeNode("ASP動態網頁入門實務", 
			false));
		dmtnHtml.add(
			new DefaultMutableTreeNode("JSP動態網頁入門實務", 
			false));
		dmtnHtml.add(
			new DefaultMutableTreeNode("DHTML動態網頁入門實務",
			false));

		dmtnOOP.add(dmtnC);
		dmtnOOP.add(dmtnJava);

		dmtnRoot.add(dmtnOOP); //將節點加入根節點
		dmtnRoot.add(dmtnHtml);

		DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot, true);
		//宣告處理JTree元件顯示資料的Model物件

		trBook = new JTree(dtm); //以Model物件宣告JTree物件

		trBook.setEditable(true); //設定節點可編輯
		trBook.setShowsRootHandles(true); //設定顯示根節點的展開按鈕

		JRadioButton rbNone = new  JRadioButton("無"),
			rbHor = new JRadioButton("資料夾間有水平分隔線"),
			rbAng = new JRadioButton("各節點間有直角連線", true);
		//宣告選擇鈕

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(rbNone); //將選擇鈕加入按鈕群組
		bg.add(rbHor);
		bg.add(rbAng);

		//註冊回應選擇鈕ActionEvent事件的監聽器物件
		rbNone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.putClientProperty("JTree.lineStyle", "None");
				//設定JTree元件內節點間沒有連結線

				trBook.updateUI(); //更新JTree元件的顯示畫面
			}
		});	

		rbHor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.putClientProperty("JTree.lineStyle", "Horizontal");
				//設定JTree元件內資料夾節點間有水平分隔線

				trBook.updateUI(); //更新JTree元件的顯示畫面
			}
		});	

		rbAng.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.putClientProperty("JTree.lineStyle", "Angled");
				//設定JTree元件內資料夾節點間有直角連結線

				trBook.updateUI(); //更新JTree元件的顯示畫面
			}
		});	

		//運用匿名類別定義、宣告監聽TreeExpansionEvent事件
		//的監聽器物件
		trBook.addTreeExpansionListener(
			new TreeExpansionListener(){

				//回應節點展開動作
				public void treeExpanded(
								TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//設定顯示引發事件之節點的路徑

					lbType.setText("展開"); //顯示引發節點展開事件
					
					updateRow(); //更新列數資訊
				}

				//回應節點收合動作
				public void treeCollapsed(
							TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//設定顯示引發事件之節點的路徑

					lbType.setText("收合"); //顯示引發節點收合事件
					
					updateRow(); //更新列數資訊
				}
			});

		//運用匿名類別定義、宣告監聽TreeWillExpansionEvent事件
		//的監聽器物件
		trBook.addTreeWillExpandListener(
			new TreeWillExpandListener(){

				//回應節點即將展開的動作
				public void treeWillExpand(TreeExpansionEvent event){
					System.out.println(
						event.getPath().toString() + " 節點即將展開");
					//將即將收合某節點的訊息輸出至命令提示字元視窗
				}

				//回應節點即將收合的動作
				public void treeWillCollapse(TreeExpansionEvent event){
					System.out.println(
						event.getPath().toString() + " 節點即將收合");
					//將即將收合某節點的訊息輸出至命令提示字元視窗
				}
			});

		//運用匿名類別定義、宣告監聽TreeSelectionEvent事件
		//的監聽器物件
		trBook.addTreeSelectionListener(new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){
				lbNode.setText(e.getPath().toString());
				//設定顯示引發事件之節點的路徑

				lbType.setText("選取"); //顯示引發節點選取事件
				updateRow(); //更新列數資訊
			}
		});

		//註冊回應MouseEvent事件監聽器物件
		trBook.addMouseListener(new MouseAdapter(){

			//回應放開滑鼠按鈕的動作
			public void mouseReleased(MouseEvent e){

				lbMousePos.setText(
					" (" + e.getX() + ", " + e.getY() + ") ");
				//設定標籤顯示滑鼠點選位置的座標

				JTree trSource = (JTree) e.getSource();
				//取得引發事件的元件

				TreePath tpNode = 
					trSource.getPathForLocation(e.getX(), e.getY());
				//以滑鼠游標位置取得JTree元件對應的節點

				if(tpNode == null){ //若未取得節點則終止方法的執行
					TreePath tpClosestNode = 
						trSource.getClosestPathForLocation(e.getX(), e.getY());

					lbMouse.setText("未選取節點, 最接近節點為 " + 
						tpClosestNode.toString());
					//設定標籤顯示滑鼠的點選結果

					return;
				}

				lbMouse.setText("選取 "
							+ tpNode.toString() + " 節點");
				//設定標籤顯示滑鼠的點選結果
			}
		});

		//註冊回應ActionEvent事件的監聽器物件
		btnExpand.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(trBook.isCollapsed(0)){ //判斷第0列節點是否收合
					trBook.expandRow(0); //展開第0列的節點
					btnExpand.setText("收合根節點");
				}
				else{
					trBook.collapseRow(0); //收合第0列的節點
					btnExpand.setText("展開根節點");
				}

			}
		});

		Box bxStyle = new Box(BoxLayout.X_AXIS);
		bxStyle.add(rbNone); //加入選擇鈕
		bxStyle.add(rbHor);
		bxStyle.add(rbAng);
		bxStyle.add(Box.createHorizontalGlue());
		bxStyle.add(btnExpand);
		bxStyle.add(Box.createHorizontalStrut(10));

		Box bxNode = new Box(BoxLayout.X_AXIS);
		bxNode.add(new JLabel("引發事件的節點 : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbNode); //顯示引發事件節點的標籤
		bxNode.add(Box.createHorizontalStrut(15));
		bxNode.add(new JLabel("事件型態 : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbType); //顯示引發事件型態的標籤

		Box bxRow = new Box(BoxLayout.X_AXIS);
		bxRow.add(new JLabel("總列數 : ", JLabel.RIGHT));
		bxRow.add(Box.createHorizontalStrut(5));
		bxRow.add(lbTotalRow);
		bxRow.add(Box.createHorizontalStrut(30));
		bxRow.add(new JLabel("目前顯示列數 : ", JLabel.RIGHT));
		bxRow.add(Box.createHorizontalStrut(5));
		bxRow.add(lbCurRow);
		bxRow.add(Box.createHorizontalStrut(30));
		bxRow.add(new JLabel("選取列 : ", JLabel.RIGHT));
		bxRow.add(Box.createHorizontalStrut(5));
		bxRow.add(lbRow); //顯示引發事件型態的標籤

		Box bxMouse = new Box(BoxLayout.X_AXIS);
		bxMouse.add(new JLabel("滑鼠座標 : ", JLabel.RIGHT));
		bxMouse.add(Box.createHorizontalStrut(5));
		bxMouse.add(lbMousePos); //顯示引發事件型態的標籤
		bxMouse.add(Box.createHorizontalStrut(15));
		bxMouse.add(new JLabel("滑鼠選取結果 : ", JLabel.RIGHT));
		bxMouse.add(Box.createHorizontalStrut(5));
		bxMouse.add(lbMouse); //顯示引發事件型態的標籤

		JPanel jpMsg = new JPanel(new GridLayout(3, 2, 5, 5));
		jpMsg.add(bxNode);
		jpMsg.add(bxRow);
		jpMsg.add(bxMouse);
		jpMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //取得內容面版
		cp.add(bxStyle, BorderLayout.NORTH);
		cp.add(trBook); //將元件加入中間區域
		cp.add(jpMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(690, 400);
		setVisible(true);
	}

	private void updateRow(){ //更新列數顯示資料的方法

		//以下敘述將設定標籤顯示JTree元件的總顯示列數、
		//目前顯示列數與選取節點所在列數
		lbTotalRow.setText(String.valueOf(trBook.getVisibleRowCount()));
		lbCurRow.setText(String.valueOf(trBook.getRowCount()));
		lbRow.setText(Arrays.toString(trBook.getSelectionRows()));
	}

	public static void main(String args[]) {
		new TreeEX(); //宣告視窗框架物件	 
	}
}