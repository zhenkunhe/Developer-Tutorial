import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*; //引用提供File類別的套件

public class RendererEX extends JFrame {

	JList ltFile = new JList(); //宣告清單方塊

	DefaultListModel dlmFile = new DefaultListModel(),
								 dlmDir = new DefaultListModel(),
								 dlmAll = new DefaultListModel();
	//宣告包含清單方塊選項內容的Model物件

	//以匿名類別的方式定義並宣告監聽器物件
	ActionListener alOrientation = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			//運用動作命令字串判斷欲使用的Model物件
			if(e.getActionCommand().equals("檔案")){
				ltFile.setModel(dlmFile);
				//設定使用包含檔案名稱的Model物件
			}
			else if(e.getActionCommand().equals("資料夾")){
				ltFile.setModel(dlmDir);
				//設定使用包含資料夾名稱的Model物件
			}
			else{
				ltFile.setModel(dlmAll);
				//設定使用包含檔案與資料夾名稱的Model物件
			}
		}
	};

	//以匿名類別的方式定義並宣告監聽器物件
	ActionListener alRenderer = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			//運用動作命令字串判斷欲使用的轉譯器物件
			if(e.getActionCommand().equals("顯示文字")){
				ltFile.setCellRenderer(new FileDirTextCellRenderer());
				//設定使用顯示文字的轉譯器物件
			}
			else{
				ltFile.setCellRenderer(new FileDirIconCellRenderer());
				//設定使用同時顯示圖示與文字的轉譯器物件
			}
		}
	};

	RendererEX(){
		File flCur = new File("C:\\"); //欲列出檔案的路徑
		File[] files = flCur.listFiles();
		//取得路徑下所有檔案的File物件

		//運用加強型for迴圈建立各種Model物件
		for(File elm : files){
			dlmAll.addElement(elm);
			//加入代表檔案或資料夾的File物件

			if(elm.isDirectory()){
				dlmDir.addElement(elm);
				//加入代表資料夾的File物件
			}
			else{
				dlmFile.addElement(elm);
				//加入代表檔案的File物件
			}
		}

		ltFile.setModel(dlmFile); //設定清單方塊的顯示內容
		ltFile.setLayoutOrientation(JList.VERTICAL_WRAP);
		//設定選項的設定方式

		ltFile.setVisibleRowCount(5); //設定可視列數為5列
		ltFile.setSelectionForeground(Color.darkGray);
		ltFile.setSelectionBackground(Color.lightGray);

		JRadioButton rdFile = new JRadioButton("顯示檔案", true),
								rdDir = new JRadioButton("顯示資料夾"),
								rdAll = new JRadioButton("全部顯示");
		//宣告控制清單方塊內選項排列方式的選擇按鈕

		rdFile.setActionCommand("檔案");
		rdDir.setActionCommand("資料夾");
		rdAll.setActionCommand("全部");
		//設定控制清單方塊內容的動作命令字串

		rdFile.addActionListener(alOrientation);
		rdDir.addActionListener(alOrientation);
		rdAll.addActionListener(alOrientation);
		//註冊回應ActionEvent事件的監聽器物件

		ButtonGroup bgOrientation = new ButtonGroup(); //宣告按鈕群組
		bgOrientation.add(rdFile); //將選擇按鈕加入按鈕群組
		bgOrientation.add(rdDir);
		bgOrientation.add(rdAll);

		JToggleButton tbText = new JToggleButton("顯示文字", true),
				  tbIconText = new JToggleButton("顯示文字與圖示");

		ltFile.setCellRenderer(new FileDirTextCellRenderer());
		//設定預設的項目轉譯器物件

		tbText.addActionListener(alRenderer);
		tbIconText.addActionListener(alRenderer);
		//註冊監聽ActionEvent事件的監聽器物件

		ButtonGroup bgRenderer = new ButtonGroup(); //宣告按鈕群組
		bgRenderer.add(tbText); //將選擇按鈕加入按鈕群組
		bgRenderer.add(tbIconText);

		JPanel jpRenderer = new JPanel(new GridLayout(2, 1, 5, 10));
		jpRenderer.add(tbText);
		jpRenderer.add(tbIconText);

		JScrollPane spList = new JScrollPane(ltFile);
		//以清單方塊宣告捲軸面版

		JPanel jpLayout = new JPanel(
			new FlowLayout(FlowLayout.CENTER, 10, 5));
		jpLayout.add(rdFile); //將選擇按鈕加入面版
		jpLayout.add(rdDir);
		jpLayout.add(rdAll);
		
		Container cp = getContentPane(); //取得內容面版
		cp.add(spList); //將元件加入內容面版
		cp.add(jpLayout,BorderLayout.NORTH);
		cp.add(jpRenderer, BorderLayout.EAST);

		//設定視窗預設的關閉動作、視窗大小, 並顯示視窗
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(570, 220);
		setVisible(true);
	}

	public static void main(String args[]) {
		new RendererEX(); //宣告視窗框架物件
	}
}

//以繼承JLabel類別並實作ListCellRenderer介面的方式
//定義處理清單方塊選項顯示的轉譯器類別
class FileDirTextCellRenderer  extends JLabel
										implements ListCellRenderer {

	public FileDirTextCellRenderer(){
		setBorder(new EmptyBorder(1, 1, 1, 1));
		//設定擁有四週寬度為1的空白框線

		setOpaque(true); //設定元件為不透明, 背景顏色才會顯現
	}

	//取得清單方塊選項轉譯器物件
	public Component getListCellRendererComponent(
						JList list, //欲執行選項轉譯器的清單方塊
						Object value, //取得選項物件
						int index, //執行轉譯之選項的索引值
						boolean isSelected, //判斷選項是否被選取
						boolean cellHasFocus //判斷選項是否取得焦點
						){
		String name = ((File)value).getName();
		//取得選項物件並將型別轉換為File物件, 再取得檔案名稱

		setText(name); //設定標籤顯示的字串

		setBackground(isSelected 
			? list.getSelectionBackground() : list.getBackground());
		//設定選項標籤的背景顏色

		setForeground(isSelected 
			? list.getSelectionForeground() : list.getForeground());
		//設定選項標籤的前景顏色

		setBorder(cellHasFocus 
			? new CompoundBorder(
					new LineBorder(Color.darkGray, 1), 
					new EmptyBorder(1, 1, 1, 1)) //合成框線
			: new EmptyBorder(1, 1, 1, 1)); //空白框線
		//設定標籤使用的框線

		return this;
	}
}

//以繼承DefaultListCellRenderer類別的方式
//定義處理清單方塊選項顯示的轉譯器類別
class FileDirIconCellRenderer  
						extends DefaultListCellRenderer {

	private static ImageIcon ICON_FILE = 
								new ImageIcon("icon\\File.gif");
	private static ImageIcon ICON_FOLDER = 
								new ImageIcon("icon\\ClosedFolder.jpg");
	//定義代表圖示的常數

	public FileDirIconCellRenderer(){
		setBorder(new EmptyBorder(1, 1, 1, 1));
		//設定標籤使用空白框線
	}

	public Component getListCellRendererComponent(
										JList list,
										Object value,
										int index,
										boolean isSelected,
										boolean cellHasFocus){

		super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
		//呼叫基礎類別的getListCellRendererComponent()方法

		String name = ((File)value).getName();
		//取得選項物件並將型別轉換為File物件, 再取得檔案名稱

		setText(name); //設定標籤顯示的字串

		setIcon(((File)value).isDirectory() ? ICON_FOLDER : ICON_FILE);
		//判斷File物件是否代表資料夾, 以決定使用的圖示

		setBackground(isSelected 
			? list.getSelectionBackground() : list.getBackground());
		//設定選項標籤的背景顏色

		setForeground(isSelected 
			? list.getSelectionForeground() : list.getForeground());
		//設定選項標籤的前景顏色

		setBorder(cellHasFocus 
			? new CompoundBorder(
					new LineBorder(Color.darkGray, 1), 
					new EmptyBorder(1, 1, 1, 1)) //合成框線
			: new EmptyBorder(1, 1, 1, 1)); //空白框線
		//設定標籤使用的框線

		return this;
	}
}