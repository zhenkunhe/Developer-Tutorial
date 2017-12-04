import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.tree.*; //包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件

public class TreeSelectionEX extends JFrame {

	JLabel lbNode = new JLabel(), lbType = new JLabel();
	//宣告顯示節點與事件型態資料的標籤

	JTree trBook; //定義JTree物件
	DefaultTreeSelectionModel dtsm;
	//處理節點選取的TreeSelectionModel物件

	TreeSelectionEX() {

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

		trBook = new JTree(dmtnRoot); //宣告JTree物件

		dtsm = (DefaultTreeSelectionModel)trBook.getSelectionModel();

		JRadioButton rbSTS = new  JRadioButton("SINGLE_TREE_SELECTION"),
			rbCTS = new JRadioButton("CONTIGUOUS_TREE_SELECTION"),
			rbDTS = new JRadioButton(
						"DISCONTIGUOUS_TREE_SELECTION", true);
		//宣告選擇鈕

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(rbDTS); //將選擇鈕加入按鈕群組
		bg.add(rbCTS);
		bg.add(rbSTS);

		//註冊回應選擇鈕ActionEvent事件的監聽器物件
		rbSTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dtsm.clearSelection(); //清除選取
				dtsm.setSelectionMode(
					DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
				//設定為單選模式
			}
		});	

		rbCTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dtsm.clearSelection(); //清除選取
				dtsm.setSelectionMode(
					DefaultTreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
				//設定為連續複選模式
			}
		});	

		rbDTS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dtsm.clearSelection(); //清除選取
				dtsm.setSelectionMode(
					DefaultTreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
				//設定為不連續複選模式
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
				}

				//回應節點收合動作
				public void treeCollapsed(
							TreeExpansionEvent event){
					lbNode.setText(event.getPath().toString());
					//設定顯示引發事件之節點的路徑

					lbType.setText("收合"); //顯示引發節點收合事件
				}
			});

		//運用匿名類別定義、宣告監聽TreeSelectionEvent事件
		//的監聽器物件
		trBook.addTreeSelectionListener(new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){

				TreePath[] tp = trBook.getSelectionPaths();

				if(tp == null)
					return;

				lbNode.setText(Arrays.toString(tp));
				//設定顯示引發事件之節點的路徑

				for(TreePath elm : tp)
					System.out.println(elm.toString());

				System.out.println("-------------------------------------------\n");

				lbType.setText("選取"); //顯示引發節點選取事件
			}
		});

		JPanel jpType = new JPanel();
		jpType.add(rbDTS); //加入選擇鈕
		jpType.add(rbCTS);
		jpType.add(rbSTS);		

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
		cp.add(trBook); //將元件加入中間區域
		cp.add(jpMsg, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 380);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeSelectionEX(); //宣告視窗框架物件
	}
}