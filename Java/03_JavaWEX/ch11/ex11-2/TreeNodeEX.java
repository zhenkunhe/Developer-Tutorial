import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件

public class TreeNodeEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel();
	//宣告顯示節點與事件型態資料的標籤

	JLabel lbChildNodeNum = new JLabel(), 
				lbLeafNodeNum = new JLabel(),
				lbNodeDeep = new JLabel(), 
				lbNodeLevel  = new JLabel(),
				lbPreSibleNode = new JLabel(), 
				lbNextSibleNode = new JLabel();
	//宣告顯示節點訊息的標籤

	JTextField tfNode = new JTextField(); //輸入節點名稱的文字欄

	JButton  btnAdd = new JButton("新增"),
		           btnRemove = new JButton("移除");
	//宣告執行新增、移除節點的指令按鈕

	JButton btnNodeInfo = new JButton("節點資訊");
	//宣告取得節點資訊的指令按鈕

	JTree trBook; //宣告建立樹狀結構控制項的JTree物件

	JCheckBox cbAddChild = new JCheckBox("可新增子節點");
	//設定是否新增子節點的核取方塊

	TreeNodeEX() {

		DefaultMutableTreeNode 
			dmtnOOP = 
				new DefaultMutableTreeNode("物件導向程式語言", true),
			dmtnC = new DefaultMutableTreeNode("C/C++", true),
			dmtnJava = new DefaultMutableTreeNode("Java", true),
			dmtnHtml = new DefaultMutableTreeNode("動態網頁", true),
			dmtnRoot = new DefaultMutableTreeNode("位元文化", true);
		//宣告節點物件

		//以下敘述將把子節點加入節點
		dmtnC.add(new DefaultMutableTreeNode(
			"C/C++ 入門進階", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Visual C++.Net 入門進階", false));
		dmtnC.add(	new DefaultMutableTreeNode(
			"Linux C/C++ 入門進階", false));

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

		//運用匿名類別定義、宣告監聽TreeSelectionEvent事件
		//的監聽器物件
		trBook.addTreeSelectionListener(new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){
				lbNode.setText(e.getPath().toString());
				//設定顯示引發事件之節點的路徑

				lbType.setText("選取"); //顯示引發節點選取事件

				updateNodeInfo(); //更新節點顯示資訊
			}
		});

		Box bxNode = new Box(BoxLayout.X_AXIS);
		bxNode.add(new JLabel("被選取的節點 : ", JLabel.RIGHT));
		bxNode.add(Box.createHorizontalStrut(5));
		bxNode.add(lbNode); //顯示引發事件的節點

		Box bxEvent = new Box(BoxLayout.X_AXIS);
		bxEvent.add(new JLabel("事件型態 : ", JLabel.RIGHT));
		bxEvent.add(Box.createHorizontalStrut(5));
		bxEvent.add(lbType); //顯示事件型態的節點

		JPanel jpEvent = new JPanel(new GridLayout(2, 1, 5, 5));
		jpEvent.add(bxNode); //顯示引發事件的節點
		jpEvent.add(bxEvent); //顯示引發事件的節點

		JPanel jpNodeInfo = new JPanel(new GridLayout(6, 2, 5, 5));
		jpNodeInfo.add(new JLabel("子節點個數 : ", JLabel.RIGHT));
		jpNodeInfo.add(lbChildNodeNum);
		jpNodeInfo.add(new JLabel("葉節點個數 : ", JLabel.RIGHT));
		jpNodeInfo.add(lbLeafNodeNum);
		jpNodeInfo.add(
			new JLabel("節點深度(至葉節點)	: ", JLabel.RIGHT));
		jpNodeInfo.add(lbNodeDeep);
		jpNodeInfo.add(
			new JLabel("節點層次(從根節點算起) : ", JLabel.RIGHT));
		jpNodeInfo.add(lbNodeLevel);
		jpNodeInfo.add(new JLabel("前一個兄弟節點 : ", JLabel.RIGHT));
		jpNodeInfo.add(lbPreSibleNode);
		jpNodeInfo.add(new JLabel("下一個兄弟節點 : ", JLabel.RIGHT));
		jpNodeInfo.add(lbNextSibleNode);

		Box bxMsg = new Box(BoxLayout.Y_AXIS);
		bxMsg.add(Box.createVerticalStrut(20));
		bxMsg.add(jpNodeInfo);
		bxMsg.add(Box.createVerticalStrut(150));
		bxMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //取得內容面版
		cp.setLayout(new BorderLayout(10, 10));

		cp.add(trBook); //將元件加入中間區域
		cp.add(bxMsg, BorderLayout.EAST);
		cp.add(jpEvent, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(630, 350);
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
				String.valueOf(dmtnNode.getChildCount()));
			//設定標籤顯示節點的子節點個數

			lbLeafNodeNum.setText(
				String.valueOf(dmtnNode.getLeafCount()));
			//設定標籤顯示節點包含之葉節點個數

			lbNodeDeep.setText(
				String.valueOf(dmtnNode.getDepth()));
			//設定標籤顯示節點到最後一個節點的深度

			lbNodeLevel.setText(
				String.valueOf(dmtnNode.getLevel()));
			//設定標籤顯示節點的層次

			lbPreSibleNode.setText(
				(dmtnNode.getPreviousSibling() == null ? 
				 "null"
				 : dmtnNode.getPreviousSibling().toString()));
			//設定標籤顯示節點的前一個兄弟節點

			lbNextSibleNode.setText(
				(dmtnNode.getNextSibling() == null ? 
				 "null" 
				 : dmtnNode.getNextSibling().toString()));
			//設定標籤顯示節點的下一個兄弟節點
		}
	}

	public static void main(String args[]) {
		new TreeNodeEX(); //宣告視窗框架物件
	}
}