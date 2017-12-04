import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件

public class TreeModelEX extends JFrame {

	JLabel lbChildNodeNum = new JLabel(), 
				lbLeafNode = new JLabel(),
				lbNodeLevel = new JLabel(), 
				lbNodePath  = new JLabel(),
				lbPreSibleNode = new JLabel(), 
				lbNextSibleNode = new JLabel();
	//宣告顯示節點資料的標籤

	JTextField tfNode = new JTextField(); //輸入節點名稱的文字欄

	JButton  btnAdd = new JButton("新增"),
		           btnRemove = new JButton("移除"),
				   btnRemoveAllChildren = new JButton("移除所有節點");
	//宣告執行新增、移除節點的指令按鈕

	JButton btnNodeInfo = new JButton("節點資訊");
	//宣告取得節點資訊的指令按鈕

	JTree trBook = new JTree(); //宣告建立樹狀結構控制項的JTree物件

	DefaultTreeModel dtmBook;
	//JTree物件使用的Model物件

	JCheckBox cbAddChild = new JCheckBox("可新增子節點");
	//設定是否新增子節點的核取方塊

	TreeModelEX() {

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

		dtmBook = new DefaultTreeModel(dmtnRoot);
		//取得JTree物件使用的Model物件

		trBook.setModel(dtmBook);
		//設定JTree物件使用dtmBook物件為Model物件
		
		//運用匿名類別定義、宣告監聽TreeSelectionEvent事件
		//的監聽器物件
		trBook.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				updateNodeInfo(); //更新節點顯示資訊
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

				updateNodeInfo(); //更新節點顯示資訊
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

					if(dmtnParent == null) //若沒有父節點則終止執行方法
						return;

					dtmBook.removeNodeFromParent(dmtnNode);
					//移除節點

					updateNodeInfo(); //更新節點顯示資訊
				}
			}
		});

		//以匿名類別定義、宣告監聽ActionEvent事件的監聽器物件
		btnRemoveAllChildren.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				TreePath tpSelNode = trBook.getSelectionPath();
				//取得被選取節點的路徑

				if(tpSelNode != null){

					DefaultMutableTreeNode dmtnNode = 
						(DefaultMutableTreeNode) 
							tpSelNode.getLastPathComponent();
					//取得節點路徑內最後一個節點

					dmtnNode.removeAllChildren(); //移除所有子節點
					dtmBook.reload(dmtnNode);
					//更新dmtnNode節點的樹狀結構

					updateNodeInfo(); //更新節點顯示資訊
				}
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("節點名稱 : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(tfNode); //加入輸入節點名稱的文字欄
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cbAddChild);
		//加入選擇是否可加入子節點的核取方塊

		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnAdd); //加入新增節點的按鈕
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemove); //加入移除節點的按鈕
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(btnRemoveAllChildren); //加入移除節點的按鈕

		Box bxNodePath = new Box(BoxLayout.X_AXIS);
		bxNodePath.add(
			new JLabel("節點路徑經過的節點 : ", JLabel.LEFT));
		bxNodePath.add(lbNodePath);

		JPanel jpNodeInfo = new JPanel(new GridLayout(3, 2, 5, 5));
		jpNodeInfo.add(new JLabel("子節點個數 : ", JLabel.RIGHT));
		jpNodeInfo.add(lbChildNodeNum);
		jpNodeInfo.add(new JLabel("葉節點個數 : ", JLabel.RIGHT));
		jpNodeInfo.add(lbLeafNode);
		jpNodeInfo.add(
			new JLabel("節點層次	: ", JLabel.RIGHT));
		jpNodeInfo.add(lbNodeLevel);
		
		Box bxMsg = new Box(BoxLayout.Y_AXIS);
		bxMsg.add(Box.createVerticalStrut(20));
		bxMsg.add(jpNodeInfo);
		bxMsg.add(Box.createVerticalStrut(250));
		
		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(5, 5));

		cp.add(trBook); //將元件加入中間區域
		cp.add(bxOper, BorderLayout.NORTH);
		cp.add(bxNodePath, BorderLayout.SOUTH);
		cp.add(bxMsg, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(630, 430);
		setVisible(true);
	}

	private void updateNodeInfo(){

		TreePath tpSelNode = trBook.getSelectionPath();
		//取得被選取節點的路徑

		if(tpSelNode != null){
			DefaultMutableTreeNode dmtnNode = 
				(DefaultMutableTreeNode) 
					tpSelNode.getLastPathComponent();
			//取得節點路徑內最後一個節點

			lbChildNodeNum.setText(
				String.valueOf(dtmBook.getChildCount(dmtnNode)));
			//設定標籤顯示節點的子節點個數

			lbLeafNode.setText(
				String.valueOf((dtmBook.isLeaf(dmtnNode) 
										 ? "是葉節點" : "不是葉節點")));
			//設定標籤顯示節點包含之葉節點個數

			lbNodeLevel.setText(String.valueOf(
				dtmBook.getPathToRoot(dmtnNode).length));
			//設定標籤顯示節點的層次

			lbNodePath.setText(String.valueOf(
				Arrays.toString(dtmBook.getPathToRoot(dmtnNode))));
			//設定標籤顯示節點至根節點之路徑經過的節點
		}
	}

	public static void main(String args[]) {
		new TreeModelEX(); //宣告視窗框架物件
	}
}