import javax.swing.*;  //引用套件
import javax.swing.event.*;
//引用包含JTree類別可引發事件之類別的套件
import javax.swing.tree.*;
//包含協助JTree類別建立樹狀結構之類別的套件
import javax.swing.border.*; //引用包含框線類別的套件

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class FolderRendererEX extends JFrame {

	JLabel lbChildCount = new JLabel(), lbParent = new JLabel();
	//宣告顯示資料夾訊息的標籤

	static final File DEFAULT_PATH = new File("C:\\");
	//預設顯示的磁碟路徑

	FolderTreeModel dtmDir = new FolderTreeModel(DEFAULT_PATH);
	//宣告列出磁碟內資料夾的Model物件

	JTree trDir = new JTree(dtmDir);
	//宣告建立樹狀結構控制項的JTree物件

	JComboBox cmbHardDrive = new JComboBox(); //宣告組合方塊

	FolderRendererEX() {

		File[] roots = File.listRoots(); //取得電腦內的磁碟路徑

		for(File file: roots)
			cmbHardDrive.addItem(file);
			//將磁碟路徑的File物件加入組合方塊

		cmbHardDrive.setSelectedItem(DEFAULT_PATH);
		//設定預設選取的磁碟路徑

		trDir.setCellRenderer(new FolderTreeCellRenderer());
		//設定使用自訂轉譯器物件

		//註冊回應ItemEvent事件的監聽器物件
		cmbHardDrive.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e){
				//判斷是否為選取動作
				if(e.getStateChange() == e.SELECTED){
					dtmDir.setRoot((File)e.getItem());
					//取得選取的節點

					trDir.updateUI(); //更新樹狀結構的畫面
				}
			}
		});

		//運用匿名類別定義、宣告監聽TreeSelectionEvent事件
		//的監聽器物件
		trDir.addTreeSelectionListener(
						new TreeSelectionListener(){

			public void valueChanged(TreeSelectionEvent e){

				File fileSel = 
					(File)trDir.getLastSelectedPathComponent();
				//取得選取路徑最後一個節點代表的資料夾

				if(fileSel == null) //若未選取節點則終止執行方法
					return;

				File fileParent = fileSel.getParentFile();
				//取得選取檔案的上一層資料夾

				if(fileParent == null) //判斷是否沒有上一層資料夾
					lbParent.setText("無");
				else{
					lbParent.setText("".equals(fileParent.getName()) 
												? fileParent.getPath() 
												: fileParent.getName());
					//判斷上一層資料夾的名稱是否為空字串, 
					//是則顯示上一層資料夾的路徑,
					//反之, 則顯示資料夾的名稱
				}

				int childCount = dtmDir.getChildCount(fileSel);
				//取得選取資料夾下的子資料夾個數

				lbChildCount.setText(String.valueOf(childCount));
				//設定顯示子資料夾個數
			}
		});

		Box bxOper = new Box(BoxLayout.X_AXIS);
		bxOper.add(new JLabel("磁碟 : "));
		bxOper.add(Box.createHorizontalStrut(5));
		bxOper.add(cmbHardDrive); //加入組合方塊
		bxOper.setBorder(new EmptyBorder(0, 0, 3, 0));
		//設定使用下方寬度為3的空白框線

		JPanel jpMsg = new JPanel(new GridLayout(1, 4, 10, 10));
		jpMsg.add(new JLabel("子資料夾個數 : ", JLabel.RIGHT));
		jpMsg.add(lbChildCount); //顯示引發事件節點的標籤
		jpMsg.add(new JLabel("上一層資料夾 : ", JLabel.RIGHT));
		jpMsg.add(lbParent); //顯示引發事件節點的標籤
		jpMsg.setBorder(new EmptyBorder(3, 3, 3,  3));

		Container cp = getContentPane(); //取得內容面版

		cp.add(new JScrollPane(trDir)); //將元件加入中間區域
		cp.add(jpMsg, BorderLayout.SOUTH);
		cp.add(bxOper, BorderLayout.NORTH);

		getRootPane().setBorder(new EmptyBorder(3, 3, 3, 3));
		//設定根面版使用寬度為3的透明框線

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 355);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FolderRendererEX(); //宣告視窗框架物件
	}
}

//取得指定磁碟內的資料夾
class FolderTreeModel implements TreeModel {

	private File root; //根節點
	private Vector listeners = new Vector();
	//儲存監聽器物件的容器

	//定義與宣告用於篩選資料夾的FileFilter物件
	FileFilter ffDir = new FileFilter(){
		public boolean accept(File file){
			return file.isDirectory();
			//判斷File物件是否代表資料夾
		}
	};

	//傳入指定路徑的建構子
	public FolderTreeModel(File rootDirectory){

		//判斷根節點下有資料夾才設定root屬性
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
	}

	public void setRoot(File rootDirectory){

		//判斷根節點下是否有資料夾
		//若沒有資料夾, 則設定根節點為null
		if(rootDirectory.listFiles(ffDir) != null)
			root = rootDirectory;
		else
			root = null;
	}
   
	//取得根節點
	public Object getRoot() {
		return root;
	}

	//取得某節點下指定索引值所代表的子節點
	public Object getChild(Object parent, int index) {

		File directory = (File) parent; //取得節點

		File[] files = directory.listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件

		return new File(directory, files[index].getName());
		//回傳取得的File物件
	}

	//取得節點所包含子節點的個數
	public int getChildCount(Object parent){
		File[] files = ((File)parent).listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件
      
		return files.length; //回傳子節點個數
	}

	//判斷節點是否為不包含子節點的葉節點
	public boolean isLeaf(Object node) {

		File[] files = ((File)node).listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件

		//若files為null或者沒有檔案則傳回true
		if(files == null || files.length == 0)
			return true;

		return false;
	}

	//取得節點內某子節點的索引值
	public int getIndexOfChild(Object parent, Object child) {

		File directory = (File)parent; //傳入的節點
		File file = (File)child; //傳入的子節點

		File[] children = directory.listFiles(ffDir);
		//取得節點內符合ffDir篩選物件的File物件
     
		if(children == null) //若無子節點傳回-1
			return -1;

		//運用for迴圈尋找子節點內相同的節點
		//找到則傳回索引值
		for(int i = 0; i < children.length; i++ ) {
			if(file.equals(children[i])) { //判斷節點是否相同
				return i; //傳回索引值
			}
		}
      
		return -1; //沒有找到則傳回-1
	}
 
	//不提供節點修改功能
	public void valueForPathChanged(TreePath path, Object value) {
	}

	//註冊監聽TreeModelEvent事件的監聽器物件
	public void addTreeModelListener(TreeModelListener listener){

		if(listener == null)
			return;

		listeners.add(listener); //將監聽器物件加入容器
	}
   
	//移除監聽TreeModelEvent事件的監聽器物件
	public void removeTreeModelListener(TreeModelListener listener){
		listeners.remove(listener); //從容器移除監聽器物件
	}
}

//定義樹狀結構元件顯示畫面使用的轉譯器
class FolderTreeCellRenderer 
	extends DefaultTreeCellRenderer {

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

		File item = (File)value;
		//取得被選取節點的物件

		if(((File)tree.getModel().getRoot()).equals(item)){
			//判斷是否為根節點

			setText(item.getPath());
			//是則設定顯示文字為File物件的路徑
		}
		else{
			setText(item.getName());
			//設定節點顯示的文字為File物件所代表檔案的名稱
		}

		setIcon(
			(expanded ? ICON_OPEN_FOLDER : ICON_CLOSED_FOLDER));
		//依照節點是否展開設定使用的圖示

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