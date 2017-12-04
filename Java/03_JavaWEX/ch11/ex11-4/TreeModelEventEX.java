import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件

public class TreeModelEventEX extends JFrame {

	JLabel lbType = new JLabel();
	//宣告顯示節點與事件型態資料的標籤

	JLabel lbChildNodeIndices = new JLabel(), 
				lbChildNode = new JLabel(),
				lbPath = new JLabel(), 
				lbTreePath = new JLabel();

	JTextField tfNode = new JTextField(); //輸入節點名稱的文字欄

	JButton  btnAdd = new JButton("新增"),
		           btnRemove = new JButton("移除"),
				   btnRemoveAllChild = new JButton("移除所有節點");
	//宣告執行新增、移除節點的指令按鈕

	JTree trBook; //宣告建立樹狀結構控制項的JTree物件

	DefaultTreeModel dtmBook;
	//JTree物件使用的Model物件

	JCheckBox cbAddChild = new JCheckBox("可新增子節點");
	//設定是否新增子節點的核取方塊

	TreeModelEventEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("物件導向程式語言", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("動態網頁", true),
			dmtnRoot = new DefaultMutableTreeNode("位元文化", true);
		//宣告節點物件

		//以下敘述將把子節點加入節點
		dmtnC.add(new DefaultMutableTreeNode("C/C++ 入門進階", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Visual C++.Net 入門進階", false));
		dmtnC.add(
			new DefaultMutableTreeNode("Linux C/C++ 入門進階", false));

		dmtnJava.add(
			new DefaultMutableTreeNode("Java 2 入門進階", false));
		dmtnJava.add(new DefaultMutableTreeNode(
			"Java 2 物件導向程式語言", false));

		dmtnHtml.add(
			new DefaultMutableTreeNode("ASP動態網頁入門實務", false));
		dmtnHtml.add(
			new DefaultMutableTreeNode("JSP動態網頁入門實務", false));
		dmtnHtml.add(new DefaultMutableTreeNode(
			"DHTML動態網頁入門實務", false));

		dmtnOOP.add(dmtnC);
		dmtnOOP.add(dmtnJava);

		dmtnRoot.add(dmtnOOP); //將節點加入根節點
		dmtnRoot.add(dmtnHtml);

		trBook = new JTree(dmtnRoot); //宣告JTree物件

		dtmBook = (DefaultTreeModel) trBook.getModel();
		//取得JTree物件使用的Model物件

		//以匿名類別定義、宣告
		//回應TreeModelEvent事件的監聽器物件
		dtmBook.addTreeModelListener(new TreeModelListener(){

			//回應節點改變動作
			public void treeNodesChanged(TreeModelEvent e){
				lbType.setText("改變節點"); //設定顯示引發事件的名稱
				updateNodeInfo(e); //更新節點訊息
			}

			//回應節點插入動作
			public void treeNodesInserted(TreeModelEvent e){
				lbType.setText("新增節點");
				updateNodeInfo(e); //更新節點訊息
			}

			//回應節點移除動作
			public void treeNodesRemoved(TreeModelEvent e){
				lbType.setText("移除節點");
				updateNodeInfo(e); //更新節點訊息
			}

			//回應樹狀結構改變
			public void treeStructureChanged(TreeModelEvent e){
			
				System.out.println("事件型態 : 結構改變");

				System.out.println("引發事件的節點路徑 : "
								+ e.getTreePath().toString());
				//取得節點路徑

				System.out.println("子節點索引值 : " 
								+ Arrays.toString(e.getChildIndices()));
				//設定標籤顯示子節點索引值

				System.out.println("子節點 : " 
								+ Arrays.toString(e.getChildren()));
				//設定標籤顯示子節點

				System.out.println("節點路徑經過的物件 : " 
								+ Arrays.toString(e.getPath()));
				//設定標籤顯示節點路徑經過的物件

				System.out.println("---------------------------------"
								+ "------------------------\n");
			}

			private void updateNodeInfo(TreeModelEvent e){

				lbChildNodeIndices.setText(Arrays.toString(e.getChildIndices()));
				//設定標籤顯示節點的子節點個數

				lbChildNode.setText(Arrays.toString(e.getChildren()));
				//設定標籤顯示節點包含之葉節點個數

				lbPath.setText(Arrays.toString(e.getPath()));
				//設定標籤顯示節點的層次

				lbTreePath.setText(e.getTreePath().toString());
				//取得節點路徑的字串
			}
		});

		//以匿名類別定義、宣告監聽ActionEvent事件的監聽器物件
		btnAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				String strNode = tfNode.getText();
				//取得文字欄內的節點名稱

				if("".equals(strNode)) //不可以改為 strNode.equals("")
					return;

				TreePath tpSelNode = trBook.getSelectionPath();
				//取得被選取節點的路徑

				DefaultMutableTreeNode dmtnParent = 
					(DefaultMutableTreeNode) tpSelNode.getLastPathComponent();
				//取得節點路徑的最後一個節點

				DefaultMutableTreeNode dmtnNode = 
					new DefaultMutableTreeNode(
						strNode, cbAddChild.isSelected());
				//建立欲新增的節點

				dtmBook.insertNodeInto(
					dmtnNode, dmtnParent, dmtnParent.getChildCount());
				//插入新增節點

				dtmBook.reload(dmtnNode);
				//更新dmtnNode節點的樹狀結構
			}
		});

		//以匿名類別定義、宣告監聽ActionEvent事件的監聽器物件
		btnRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				TreePath tpSelNode = trBook.getSelectionPath();
				//取得被選取節點的路徑

				if(tpSelNode != null){

					DefaultMutableTreeNode dmtnNode = 
						(DefaultMutableTreeNode) 
							tpSelNode.getLastPathComponent();
					//取得節點路徑內最後一個節點

					DefaultMutableTreeNode dmtnParent = 
						(DefaultMutableTreeNode)dmtnNode.getParent();
					//取得被選取節點的父節點

					dtmBook.removeNodeFromParent(dmtnNode);
					//移除節點

					if(dmtnParent == null) //若沒有父節點則終止執行方法
						return;

					dtmBook.reload(dmtnParent);
					//更新dmtnParent節點的樹狀結構
				}
			}
		});

		//以匿名類別定義、宣告監聽ActionEvent事件的監聽器物件
		btnRemoveAllChild.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				TreePath tpSelNode = trBook.getSelectionPath();
				//取得被選取節點的路徑

				if(tpSelNode != null){

					DefaultMutableTreeNode dmtnNode = 
						(DefaultMutableTreeNode) 
							tpSelNode.getLastPathComponent();
					//取得節點路徑內最後一個節點

					DefaultMutableTreeNode dmtnParent = 
						(DefaultMutableTreeNode)dmtnNode.getParent();
					//取得被選取節點的父節點

					if(dmtnParent == null) //若沒有父節點則終止執行方法
						return;

					dmtnNode.removeAllChildren(); //移除所有子節點
					dtmBook.reload(dmtnNode);
					//更新dmtnNode節點的樹狀結構
				}
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("節點名稱 : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(tfNode); //加入輸入節點名稱的文字欄
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cbAddChild); //加入選擇是否可加入子節點的核取方塊
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnAdd); //加入新增節點的按鈕
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemove); //加入移除節點的按鈕
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemoveAllChild); //加入移除節點的按鈕

		Box bxType = new Box(BoxLayout.X_AXIS);
		bxType.add(new JLabel("事件型態 : ", JLabel.RIGHT));
		bxType.add(Box.createHorizontalStrut(5));
		bxType.add(lbType); //顯示事件型態的節點

		Box bxTreePath = new Box(BoxLayout.X_AXIS);
		bxTreePath.add(new JLabel("引發事件的節點 : ", JLabel.RIGHT));
		bxTreePath.add(Box.createHorizontalStrut(5));
		bxTreePath.add(lbTreePath); //顯示引發事件的節點

		Box bxChildNodeIndices = new Box(BoxLayout.X_AXIS);
		bxChildNodeIndices.add(new JLabel("子節點索引值 : ", JLabel.RIGHT));
		bxChildNodeIndices.add(Box.createHorizontalStrut(5));
		bxChildNodeIndices.add(lbChildNodeIndices);

		Box bxChildNode = new Box(BoxLayout.X_AXIS);
		bxChildNode.add(new JLabel("子節點 : ", JLabel.RIGHT));
		bxChildNode.add(Box.createHorizontalStrut(5));
		bxChildNode.add(lbChildNode);

		Box bxPath = new Box(BoxLayout.X_AXIS);
		bxPath.add(new JLabel("節點路徑經過的物件 : ", JLabel.RIGHT));
		bxPath.add(Box.createHorizontalStrut(5));
		bxPath.add(lbPath);

		JPanel jpNodeInfo = new JPanel(new GridLayout(5, 1, 5, 5));
		jpNodeInfo.add(bxTreePath);
		jpNodeInfo.add(bxType); 
		jpNodeInfo.add(bxChildNodeIndices);
		jpNodeInfo.add(bxChildNode);
		jpNodeInfo.add(bxPath);

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(10, 10));

		cp.add(trBook); //將元件加入中間區域
		cp.add(bxOper, BorderLayout.NORTH);
		cp.add(jpNodeInfo, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 460);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeModelEventEX(); //宣告視窗框架物件
	}
}