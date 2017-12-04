import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*;
//包含協助JTree類別建立樹狀結構之類別的套件

import javax.swing.border.*; //引用包含框線類別的套件

public class TreeUIEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel();
	//宣告顯示節點與事件型態資料的標籤

	JTree trBook; //宣告樹狀結構元件

	TreeUIEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("物件導向程式語言", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("動態網頁", true),
			dmtnRoot = new DefaultMutableTreeNode("位元文化", true);
		//宣告節點物件

		//以下敘述將把子節點加入節點
		dmtnC.add(	new DefaultMutableTreeNode(
			"C/C++ 入門進階", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Visual C++.Net 入門進階", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Linux C/C++ 入門進階", false));

		dmtnJava.add(new DefaultMutableTreeNode(
			"Java 2 入門進階", false));
		dmtnJava.add(new DefaultMutableTreeNode(
			"Java 2 物件導向程式語言", false));

		dmtnHtml.add(new DefaultMutableTreeNode(
			"ASP動態網頁入門實務", false));
		dmtnHtml.add(new DefaultMutableTreeNode(
			"JSP動態網頁入門實務", false));
		dmtnHtml.add(new DefaultMutableTreeNode(
			"DHTML動態網頁入門實務", false));

		dmtnOOP.add(dmtnC);
		dmtnOOP.add(dmtnJava);

		dmtnRoot.add(dmtnOOP); //將節點加入根節點
		dmtnRoot.add(dmtnHtml);

		trBook = new JTree(dmtnRoot); //宣告JTree物件

		JRadioButton rbDef = new  JRadioButton("Java預設", true),
			rbUser = new JRadioButton("自訂顯示");
		//宣告選擇鈕

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(rbDef); //將選擇鈕加入按鈕群組
		bg.add(rbUser);

		//以匿名類別定義、宣告回應ActionEvent事件的監聽器物件
		ActionListener al = new ActionListener(){

			Object leafIcon , openIcon, closedIcon,
				        expandedIcon, collapsedIcon;

			//運用實體初始化執行建構子的工作
			{
				UIDefaults uid  = UIManager.getDefaults();
				//取得預設外視感覺的預設值

				leafIcon =  uid.get("Tree.leafIcon");
				openIcon = uid.get("Tree.openIcon");
				closedIcon = uid.get("Tree.closedIcon");
				expandedIcon = uid.get("Tree.expandedIcon");
				collapsedIcon = uid.get("Tree.collapsedIcon");
				//取得樹狀結構預設使用的圖示設定值
			}

			public void actionPerformed(ActionEvent e){

				if(e.getActionCommand().equals("Java預設")){
					UIManager.put("Tree.leafIcon", leafIcon);
					UIManager.put("Tree.openIcon", openIcon);
					UIManager.put("Tree.closedIcon", closedIcon);
					UIManager.put("Tree.expandedIcon", expandedIcon);
					UIManager.put("Tree.collapsedIcon", collapsedIcon);
					//還原系統樹狀結構預設使用的圖示
				}
				else {
					UIManager.put("Tree.leafIcon", 
						new ImageIcon("icon/File.gif"));
					UIManager.put("Tree.openIcon", 
						new ImageIcon("icon/OpenFolder.jpg"));
					UIManager.put("Tree.closedIcon", 
						new ImageIcon("icon/ClosedFolder.jpg"));
					UIManager.put("Tree.expandedIcon", 
						new ImageIcon("icon/unlocked.jpg"));
					UIManager.put("Tree.collapsedIcon", 
						new ImageIcon("icon/locked.jpg"));
					//更改系統設定樹狀結構使用的圖示
				}

				trBook.updateUI(); //更新樹狀結構元件
			}
		};

		rbDef.addActionListener(al);	
		rbUser.addActionListener(al);
		//註冊回應選擇鈕ActionEvent事件的監聽器物件

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
				}

				//回應節點收合動作
				public void treeCollapsed(
							TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//設定顯示引發事件之節點的路徑

					lbType.setText("收合"); //顯示引發節點收合事件
				}
			});

		//運用匿名類別定義、宣告監聽TreeWillExpansionEvent事件
		//的監聽器物件
		trBook.addTreeWillExpandListener(
			new TreeWillExpandListener(){

				//回應節點即將展開的動作
				public void 
					treeWillExpand(TreeExpansionEvent event){

					System.out.println(
						event.getPath().toString() + "節點即將展開");
					//將即將收合某節點的訊息輸出至命令提示字元視窗
				}

				//回應節點即將收合的動作
				public void 
					treeWillCollapse(TreeExpansionEvent event){
					System.out.println(
						event.getPath().toString() + "節點即將收合");
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
			}
		});

		//註冊回應MouseEvent事件監聽器物件
		trBook.addMouseListener(new MouseAdapter(){

			//回應放開滑鼠按鈕的動作
			public void mouseReleased(MouseEvent e){

				JTree tbSource = (JTree) e.getSource();
				//取得引發事件的元件

				TreePath tpNode = 
					tbSource.getPathForLocation(e.getX(), e.getY());
				//以滑鼠游標位置取得JTree元件對應的項目

				if(tpNode == null) //若未取得項目則終止方法的執行
					return;

				lbNode.setText(tpNode.toString());
				//顯示引發事件的節點

				lbType.setText(lbType.getText() + "; 滑鼠點選");
				//顯示引發的事件
			}
		});

		JPanel jpType = new JPanel();
		jpType.add(rbUser); //加入選擇鈕
		jpType.add(rbDef);	

		Box bxNode = new Box(BoxLayout.X_AXIS);
		bxNode.add(new JLabel("引發事件的節點 : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbNode); //顯示引發事件節點的標籤

		Box bxType = new Box(BoxLayout.X_AXIS);
		bxType.add(new JLabel("事件型態 : ", JLabel.RIGHT));
		bxType.add(Box.createHorizontalStrut(5));
		bxType.add(lbType); //顯示引發事件型態的標籤

		JPanel jpMsg = new JPanel(new GridLayout(2, 2, 5, 5));
		jpMsg.add(bxNode);
		jpMsg.add(bxType);
		jpMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpType, BorderLayout.NORTH);
		cp.add(new JScrollPane(trBook)); //將元件加入中間區域
		cp.add(jpMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 380);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeUIEX(); //宣告視窗框架物件
	}
}