import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件
import javax.swing.tree.*; //包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件
import java.awt.*;
import java.awt.event.*;

public class TreeRendererEX extends JFrame {

	JTree trBook; //宣告樹狀結構元件

	TreeRendererEX() {

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

		trBook.setCellRenderer(new BookTreeCellRenderer());
		//設定使用自訂轉譯器物件

		JRadioButton rbDef = new  JRadioButton("Java預設"),
			rbUser = new JRadioButton("自訂顯示", true);
		//宣告選擇鈕

		ButtonGroup bg = new ButtonGroup(); //宣告按鈕群組
		bg.add(rbDef); //將選擇鈕加入按鈕群組
		bg.add(rbUser);

		//註冊回應選擇鈕ActionEvent事件的監聽器物件
		rbDef.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.setCellRenderer(null);
				//設定使用預設的轉譯器物件
			}
		});	

		rbUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				trBook.setCellRenderer(new BookTreeCellRenderer());
				//設定使用自訂轉譯器物件
			}
		});	

		JPanel jpType = new JPanel();
		jpType.add(rbUser); //加入選擇鈕
		jpType.add(rbDef);	

		Container cp = getContentPane(); //取得內容面版
		cp.add(jpType, BorderLayout.NORTH);
		cp.add(trBook); //將元件加入中間區域

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 330);
		setVisible(true);
	}

	public static void main(String args[]) {
		new TreeRendererEX(); //宣告視窗框架物件
	}
}

//定義樹狀結構元件顯示畫面使用的轉譯器
class BookTreeCellRenderer 
	extends DefaultTreeCellRenderer {

	private final static ImageIcon ICON_FILE =
		new ImageIcon("icon/File.gif");
	private final static ImageIcon ICON_CLOSED_FOLDER = 
		new ImageIcon("icon/ClosedFolder.jpg");
	private final static ImageIcon ICON_OPEN_FOLDER = 
		new ImageIcon("icon/OpenFolder.jpg");
	//定義代表圖示的靜態常數

	public Component getTreeCellRendererComponent(
		JTree tree, //使用轉譯器物件的樹狀結構元件
		Object value, //轉譯器欲處理的樹狀結構之節點
		boolean isSelected, //被處理節點是否被選取
		boolean expanded, //被處理節點是否展開
		boolean leaf, //被選取節點是否為葉節點
		int row, //被選取節點的列數
		boolean hasFocus) { //被選取節點是否擁有焦點

		super.getTreeCellRendererComponent(
			tree, value, isSelected, expanded, leaf, row, hasFocus);
		//呼叫基礎類別的getTreeCellRendererComponent()方法

		DefaultMutableTreeNode item = (DefaultMutableTreeNode)value;
		//取得被選取節點的物件

		//判斷被選取節點是否容許加入子節點
		if(!item.getAllowsChildren()){ //不允許加入子節點
			setIcon(ICON_FILE); //設定圖示為ICON_FILE
		}
		else{
			setIcon(expanded? ICON_OPEN_FOLDER: ICON_CLOSED_FOLDER);
			//設定可容納子節點的節點之圖示
			//若節點的狀態為展開則使用ICON_OPEN_FOLDER圖示
			//若狀態為收合則使用ICON_CLOSED_FOLDER圖示
		}

		setTextNonSelectionColor(Color.black);
		//設定非選取節點的文字顏色

		setTextSelectionColor(Color.darkGray);
		//設定選取節點的文字顏色

		setBackgroundSelectionColor(Color.lightGray);
		//設定選取節點的背景顏色

		setBorderSelectionColor(Color.darkGray);
		//設定選取節點的框線顏色

		return this;
	}
}