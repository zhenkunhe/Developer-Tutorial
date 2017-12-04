import com.BitC.Controls.*;
//引入自訂套件

import javax.swing.*;  //引用Swing套件
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//定義繼承JFrame類別的圖片瀏覽器視窗框架
public class ImageViewer extends JFrame {

	private ImageCanvas pad; //顯示影像的畫布
	private int maxZoom = 100; //最大放大比率

	private JButton
		zoomIn = new JButton(new ZoomAction("放大")), //放大
		zoomOut = new JButton(new ZoomAction("縮小")), //縮小
		normal = new JButton(new ZoomAction("適當大小")), //正常
		fitWindow = new JButton(new ZoomAction("原始大小")),
		//調整至視窗大小
		center = new JButton(new ZoomAction("置中")),
		//調整至視窗中央
		fitWidth = new JButton(new ZoomAction("適當寬度")),
		//調整至視窗寬度
		fitHeight = new JButton(new ZoomAction("適當高度"));
		//調整至視窗高度

	File defaultPath = new File("C:\\"); //樹狀結構的預設瀏覽路徑

	FolderTreeViewer ftv = new FolderTreeViewer(defaultPath);
	//資料夾樹狀結構檢視器

	JComboBox cmbDevice = new JComboBox(File.listRoots());
	//選取檔案路徑的組合方塊

	File flListPath = new File(
		ImageViewer.class.getResource("/").getPath() + "pic");
	//宣告檔案清單方塊預設瀏覽的路徑

	//宣告篩選檔案名稱的物件
	FilenameFilter nameFilter = new FilenameFilter(){

		//判斷檔案是否被接受
		public boolean accept(File dir, String name){

			name = name.toLowerCase();
			//將檔案名稱轉換為英文小寫

			//判斷檔案名稱的副檔名是否為gif或jpg
			if(name.endsWith(".gif") || name.endsWith(".jpg"))
				return true; //傳回true

			return false; //傳回false
		}
	};

	FileList fl = new FileList(flListPath, nameFilter);
	//宣告顯示檔案列表的清單方塊

	ImageViewer() {

		addWindowListener(wa); //註冊監聽WindowEvent事件的監聽器物件

		BorderLayout blObj = new BorderLayout(10,  10);
		setLayout(blObj); //設定版面配置
			
		Container cp = getContentPane(); //取得內容面版

		Dimension d = size(); //取得視窗框架的大小
		pad = new ImageCanvas(d.width, d.height, maxZoom);
		//建立顯示圖檔的畫布

		cp.add(pad); //將面版加入內容面版
		
		JPanel jpButton = new JPanel(); //宣告容納指令按鈕的容器

		jpButton.add(zoomIn);
		jpButton.add(zoomOut);
		jpButton.add(fitWindow);
		jpButton.add(normal);
		jpButton.add(center);
		jpButton.add(fitWidth);
		jpButton.add(fitHeight);
		//建立控制圖檔瀏覽的控制項

		cp.add(jpButton, BorderLayout.NORTH);
		//將容器加入內容面版

		//註冊監聽TreeSelectionEvent事件的監聽器物件
		ftv.addTreeSelectionListener(tslTree);

		Box bxFolderTree = new Box(BoxLayout.Y_AXIS);
		//宣告放置樹狀結構的Box容器

		bxFolderTree.add(cmbDevice); //將選取磁碟的組合方塊
		bxFolderTree.add(Box.createVerticalStrut(10));
		bxFolderTree.add(new JScrollPane(ftv));
		//以樹狀結構建立捲軸面版並加入Box容器

		cmbDevice.setSelectedItem(defaultPath);
		//設定組合方塊選取的路徑

		cmbDevice.addItemListener(ilCMB);
		//註冊監聽ItemEvent事件的監聽器物件

		JPanel jpList = new JPanel(new BorderLayout());
		jpList.add(fl);
		//將顯示檔案列表加入清單方塊

		fl.addListSelectionListener(lsl);
		//註冊監聽ListSelectionEvent事件的監聽器物件

		JPanel jpBrowser = 
			new JPanel(new GridLayout(1, 2, 10, 0), true);
		jpBrowser.add(bxFolderTree, BorderLayout.WEST);
		jpBrowser.add(new JScrollPane(jpList));
		//將清單方塊加入JPanel容器

		cp.add(jpBrowser, BorderLayout.SOUTH);
		//將瀏覽器之面版加入視窗框架的內容面版

		getRootPane().setBorder(
			new javax.swing.border.EmptyBorder(5, 5, 5, 5));
		//設定根面版使用寬度為5的空白框線

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);

		bxFolderTree.setPreferredSize(new Dimension(
			new Double(getPreferredSize().width).intValue(),
			new Double(getPreferredSize().height/3).intValue()));
		//設定容納資料夾樹狀結構控制項Box容器的大小

		setVisible(true);
	}

	//定義繼承AbstractAction類別調整圖片大小的Action物件
	class ZoomAction extends AbstractAction {
		
		ZoomAction(String name){ //建構子

			super(name);
			//將動作Action物件名稱傳入基礎類別建構子

			putValue(Action.SHORT_DESCRIPTION, name);
			//設定Action物件的簡短描述文字
		}

		//回應ActionEvent事件的動作
		public void actionPerformed(ActionEvent e){

			String name = (String) getValue(Action.NAME);
			//取得Action物件名稱的設定值

			//比對Action物件名稱設定對應的文字標籤為有效
			//以及相關選項為選取
			if(name.equals("放大"))
				pad.zoomIn();
			else if(name.equals("縮小"))
				pad.zoomOut();
			else if(name.equals("適當大小"))
				pad.fitWindow();
			else if(name.equals("原始大小"))
				pad.normal();
			else if(name.equals("置中"))
				pad.imagecenter();
			else if(name.equals("適當寬度"))
				pad.fitWidth();
			else if(name.equals("適當高度"))
				pad.fitHeight();
		}
	}

	//宣告監聽TreeSelectionEvent事件的監聽器物件
	TreeSelectionListener tslTree = new TreeSelectionListener(){

		//回應樹狀結構選取狀態改變的方法
		public void valueChanged(TreeSelectionEvent event){

			Object treenode = ftv.getTreeNode(event.getPath());
			//取得樹狀結構的節點

			File node = (File) treenode; //狀態結構的節點
			fl.setListFile(node, nameFilter); //設定檔案清單
		}

		//回應展開節點的方法
		public void treeCollapsed(TreeExpansionEvent e)	{}
	};

	//宣告監聽組合方塊ItemEvent事件的監聽器物件
	ItemListener ilCMB = new ItemListener(){

		File file = null;

		//回應項目狀態改變的方法
		public void itemStateChanged(ItemEvent e){

			//判斷改變的狀態是否為選取狀態
			if(e.getStateChange() == ItemEvent.SELECTED){

				file = (File)e.getItem();
				//取得選取的磁碟

				File[] files = file.listFiles();
				//列出該磁碟內的檔案

				//判斷File物件陣列必須有檔案
				if(files != null && files.length !=0){

					//宣告將檔案結構載入樹狀結構的執行緒物件
					Thread runner = new Thread(){

						public void run() { //執行緒欲執行的動作

							String node = "載入目錄中....";

							ftv.setModel(
								new DefaultTreeModel(
								new DefaultMutableTreeNode(node)));
							//設定樹狀結構尚未完成載入,
							//顯示載入訊息的Model物件

							ftv.setModel(new FolderTreeModel(file));
							//設定儲存樹狀結構節點內容的Model物件

							fl.setListFile(file, nameFilter);
							//設定清單方塊顯示之檔案列表的清單方塊
							//與檔案篩選物件
						}
					};
					runner.start(); //啟動執行緒
				}
				else
					((JComboBox)e.getSource()).setSelectedItem(defaultPath);
					//設定組合方塊的選取項目
			}
		}
	};

	//宣告監聽清單方塊ListSelectionEvent事件的監聽器物件
	ListSelectionListener lsl = new ListSelectionListener(){

		File file = null; //宣告File物件

		//回應選取清單方塊狀態改變動作
		public void valueChanged(ListSelectionEvent e){

			if(!e.getValueIsAdjusting()){ //判斷選取值是否改變

				JList source = (JList) e.getSource();
				//取得引發事件的來源

				file = (File) source.getSelectedValue();
				//取得選取的檔案

				if(file != null){ //判斷是否為null
					pad.setup(); //設定畫布
					pad.loadImage(file.getPath());
					//載入圖片

					pad.fitWindow(); //調整畫布為適當大小
				}
			}
		}
	};

	//宣告回應WindowEvent事件的監聽器物件
	WindowAdapter wa = new WindowAdapter(){

		//回應視窗開啟動作的方法
		public void windowOpened(WindowEvent e){

			File selFile = new File(
				ImageViewer.class.getResource("/").getPath() + 
				"pic//people.jpg");
			//宣告預設選取圖片的File物件

			fl.setSelectedValue(selFile, true);
			//設定檔案清單方塊選取指定的File物件

			pad.setup(); //設定畫布
			pad.fitWindow(); //將圖片調整為視窗大小
		}
	};

	public static void main(String args[]) {
		new ImageViewer(); //建立視窗框架物件
	}
}